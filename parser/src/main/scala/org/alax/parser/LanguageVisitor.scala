package org.alax.parser

import org.alax.ast.*
import org.alax.ast.base.model
import org.alax.ast.base.model.*
import org.alax.ast.base.model.Node.Metadata
import org.antlr.v4.runtime.{ParserRuleContext, Token, TokenStream}
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode}
import org.alax.ast.{LanguageLexer, LanguageParser, LanguageParserBaseVisitor}

import javax.management.ValueExp
import scala.jdk.CollectionConverters.*

class LanguageVisitor(tokenStream: TokenStream)
  extends LanguageParserBaseVisitor[Node | ParseError] {


  //FIXME should operate on ParseRuleContext rather than child token of that context
  private def metadata(token: Token): Node.Metadata = {
    return Node.Metadata(
      location = Node.Location(
        unit = token.getTokenSource.getSourceName,
        lineNumber = token.getLine,
        startIndex = token.getStartIndex,
        endIndex = token.getStopIndex
      )
    )
  }


  private def parseName(terminalNode: TerminalNode): Partial.Name | ParseError = {
    return terminalNode.getSymbol.getType match {
      case LanguageParser.LOWERCASE_NAME => Partial.Name.LowerCase(
        terminalNode.getText, metadata(terminalNode.getSymbol)
      );
      case LanguageParser.UPPERCASE_NAME => Partial.Name.UpperCase(
        terminalNode.getText, metadata(terminalNode.getSymbol)
      );
      case _ => model.ParseError(
        metadata = metadata(terminalNode.getSymbol),
        message = "Unknown name: " + terminalNode.getText
      );
    }
  }

  private def wrapParseError(wrappe: ParseError, message: String, metadata: Node.Metadata): ParseError = ParseError(
    metadata = metadata,
    message = message,
    cause = wrappe
  );


  //  override def visitSimpleImportDeclaration(ctx: LanguageParser.SimpleImportDeclarationContext): statements.declarations.Import.Simple | ParseError = {
  //    super.visitSimpleImportDeclaration(ctx)
  //    val importedName: partials.names.Qualified | ParseError = visitImportedName(ctx.importedName());
  //    return importedName match {
  //      case e: ParseError => wrapParseError(e, "Invalid import declaration", metadata(ctx.start));
  //      case qualified: partials.names.Imported => {
  //      }
  //    }
  //  }

  override def visitImportedName(ctx: LanguageParser.ImportedNameContext): Partial.Name.Imported | ParseError = {
    super.visitImportedName(ctx);
    val qualifications: Seq[Partial.Name.LowerCase | Partial.Name.UpperCase] = ctx.children.asScala
      .filter(item => item.isInstanceOf[TerminalNode])
      .map(item => item.asInstanceOf[TerminalNode])
      .filter(item => item.getSymbol.getType == LanguageParser.UPPERCASE_NAME || item.getSymbol.getType == LanguageParser.LOWERCASE_NAME)
      .map(item => item.getSymbol.getType match {
        case LanguageParser.UPPERCASE_NAME => Partial.Name.UpperCase(item.getText, metadata(item.getSymbol));
        case LanguageParser.LOWERCASE_NAME => Partial.Name.LowerCase(item.getText, metadata(item.getSymbol));
        case _ => return ParseError(metadata(item.getSymbol), f"Unknown token for imported name: ${item.getText}")
      }).toSeq;
    return if qualifications.size == 1 then qualifications.last else Partial.Name.Qualified(qualifications, metadata(ctx.start));
  }

  override def visitValueTypeReference(ctx: LanguageParser.ValueTypeReferenceContext): Partial.Type.Reference.Value | ParseError = {
    super.visitValueTypeReference(ctx)
    val typeName = Partial.Name.UpperCase(value = ctx.typeName.getText, metadata = metadata(ctx.typeName))
    val fullName: Partial.Name.Qualified | Partial.Name.UpperCase | ParseError = Option(ctx.importedName())
      .map(context => visitImportedName(context))
      .map[ParseError | Partial.Name.Qualified | Partial.Name.UpperCase] {
        case error: ParseError => error;
        case imported: Partial.Name.UpperCase => Partial.Name.Qualified(qualifications = Seq(imported, typeName), metadata(ctx.start))
        case imported: Partial.Name.LowerCase => Partial.Name.Qualified(qualifications = Seq(imported, typeName), metadata(ctx.start))
        case imported: Partial.Name.Qualified => Partial.Name.Qualified(qualifications = imported.qualifications :+ typeName, metadata(ctx.start))
      }
      .getOrElse[ParseError | Partial.Name.Qualified | Partial.Name.UpperCase](typeName);
    return fullName match {
      case id@(_: Partial.Name.UpperCase | _: Partial.Name.Qualified) => Partial.Type.Reference.Value(
        id = id, metadata = metadata(ctx.start)
      )
      case default: ParseError => default
    }

  }


  override def visitValueDeclaration(ctx: LanguageParser.ValueDeclarationContext): Statement.Declaration.Value | ParseError = {
    super.visitValueDeclaration(ctx);
    val typeReference: Partial.Type.Reference | ParseError = visitValueTypeReference(ctx.valueTypeReference());
    val name: Partial.Name | ParseError = parseName(ctx.LOWERCASE_NAME());
    return typeReference match {
      case valueType: Partial.Type.Reference.Value =>
        name match {
          case shadowName: Partial.Name.LowerCase =>
            Statement.Declaration.Value(
              name = shadowName,
              typeReference = valueType,
              metadata = metadata(ctx.getStart)
            )
          case error: ParseError => wrapParseError(wrappe = error, message = "Invalid value name ", metadata = metadata(ctx.getStart))
          case _ => throw ParserBugException();
        }
      case error: ParseError => wrapParseError(wrappe = error, message = "Invalid value type", metadata = metadata(ctx.getStart))
      case other => ParseError(
        message = f"Not Implemented, parsing type: ${other}!",
        metadata = metadata(ctx.getStart)
      )
    }
  }

  override def visitValueDefinition(ctx: LanguageParser.ValueDefinitionContext): Statement.Definition.Value | ParseError = {
    super.visitValueDefinition(ctx);

    val typeReference: Partial.Type.Reference | ParseError = visitValueTypeReference(ctx.valueTypeReference());
    val name: Partial.Name | ParseError = parseName(ctx.LOWERCASE_NAME());
    val initialization: Expression | ParseError = visitExpression(ctx.expression());
    return typeReference match {
      case valueType: Partial.Type.Reference.Value =>
        name match {
          case shadowName: Partial.Name.LowerCase =>
            initialization match {
              case expression: Expression => Statement.Definition.Value(
                name = shadowName,
                typeReference = valueType,
                initialization = expression,
                metadata = metadata(ctx.getStart)
              );
              case error: ParseError => wrapParseError(
                wrappe = error,
                message = "Invalid value initialization expression ",
                metadata = metadata(ctx.getStart)
              )

            }
          case error: ParseError => wrapParseError(
            wrappe = error,
            message = "Invalid value name ",
            metadata = metadata(ctx.getStart))
          case _ => throw ParserBugException();
        }
      case error: ParseError => wrapParseError(
        wrappe = error,
        message = "Invalid value type",
        metadata = metadata(ctx.getStart)
      )
      case other => ParseError(
        message = f"Not Implemented, parsing type: ${other}!",
        metadata = metadata(ctx.getStart)
      )


    }
  }


  override def visitExpression(ctx: LanguageParser.ExpressionContext): Expression | ParseError = {
    super.visitExpression(ctx);
    return Option(ctx.literalExpression())
      .map(expression => visitLiteralExpression(expression))
      .getOrElse(
        ParseError(message = s"Unknown expression: ${ctx.getText}", metadata = metadata(ctx.getStart))
      );
  }


  override def visitLiteralExpression(ctx: LanguageParser.LiteralExpressionContext): base.expressions.Literal | ParseError = {
    super.visitLiteralExpression(ctx);
    val terminalNode = ctx.children.stream()
      .filter((parseTree: ParseTree) => parseTree.isInstanceOf[TerminalNode])
      .map(node => node.asInstanceOf[TerminalNode])
      .findFirst()
      .orElseThrow(() => new ParserBugException());
    val text = terminalNode.getText;
    return terminalNode.getSymbol.getType match {
      case LanguageParser.BOOLEAN_LITERAL => Literals.Boolean(text.toBoolean, metadata = metadata(ctx.getStart));
      case LanguageParser.INTEGER_LITERAL => Literals.Integer(text.toInt, metadata = metadata(ctx.getStart));
      case LanguageParser.FLOAT_LITERAL => Literals.Float(text.toDouble, metadata = metadata(ctx.getStart));
      case LanguageParser.CHARACTER_LITERAL => Literals.Character(text(1), metadata = metadata(ctx.getStart));
      case LanguageParser.STRING_LITERAL => Literals.String(text.substring(1, text.length - 1), metadata = metadata(ctx.getStart));
      case _ => ParseError(
        message = "Unknown literal: " + text,
        metadata = metadata(ctx.getStart)
      );
    }
  }


  object LanguageVisitor {

  }
}