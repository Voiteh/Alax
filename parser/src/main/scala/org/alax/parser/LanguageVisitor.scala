package org.alax.parser

import org.alax.ast.base.*
import org.alax.ast.base.Node.Metadata
import org.alax.ast.partial.Names
import org.alax.ast.{LanguageLexer, LanguageParser, LanguageParserBaseVisitor, Literals, Value, base}
import org.alax.ast
import org.antlr.v4.runtime.{ParserRuleContext, Token, TokenStream}
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode}

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


  private def parseName(terminalNode: TerminalNode): base.Partial.Name | ParseError = {
    return terminalNode.getSymbol.getType match {
      case LanguageParser.LOWERCASE_NAME => Names.LowerCase(
        terminalNode.getText, metadata(terminalNode.getSymbol)
      );
      case LanguageParser.UPPERCASE_NAME => Names.UpperCase(
        terminalNode.getText, metadata(terminalNode.getSymbol)
      );
      case _ => ParseError(
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


  override def visitImportedName(ctx: LanguageParser.ImportedNameContext): ast.Imports.ImportedName | ParseError = {
    super.visitImportedName(ctx);
    val qualifications: Seq[Names.LowerCase | Names.UpperCase] = ctx.children.asScala
      .filter(item => item.isInstanceOf[TerminalNode])
      .map(item => item.asInstanceOf[TerminalNode])
      .filter(item => item.getSymbol.getType == LanguageParser.UPPERCASE_NAME || item.getSymbol.getType == LanguageParser.LOWERCASE_NAME)
      .map(item => item.getSymbol.getType match {
        case LanguageParser.UPPERCASE_NAME => Names.UpperCase(item.getText, metadata(item.getSymbol));
        case LanguageParser.LOWERCASE_NAME => Names.LowerCase(item.getText, metadata(item.getSymbol));
        case _ => return ParseError(metadata(item.getSymbol), f"Unknown token for imported name: ${item.getText}")
      }).toSeq;
    return if qualifications.size == 1 then qualifications.last else Names.Qualified(qualifications, metadata(ctx.start));
  }

  override def visitValueTypeReference(ctx: LanguageParser.ValueTypeReferenceContext): ast.Value.Type.Reference | ParseError = {
    super.visitValueTypeReference(ctx)
    val typeName = Names.UpperCase(value = ctx.typeName.getText, metadata = metadata(ctx.typeName))
    val fullName: Names.Qualified | Names.UpperCase | ParseError = Option(ctx.importedName())
      .map(context => visitImportedName(context))
      .map[ParseError | Names.Qualified | Names.UpperCase] {
        case error: ParseError => error;
        case imported: Names.UpperCase => Names.Qualified(qualifications = Seq(imported, typeName), metadata(ctx.start))
        case imported: Names.LowerCase => Names.Qualified(qualifications = Seq(imported, typeName), metadata(ctx.start))
        case imported: Names.Qualified => Names.Qualified(qualifications = imported.qualifications :+ typeName, metadata(ctx.start))
      }
      .getOrElse[ParseError | Names.Qualified | Names.UpperCase](typeName);
    return fullName match {
      case id@(_: Names.UpperCase | _: Names.Qualified) => ast.Value.Type.Reference(
        id = id, metadata = metadata(ctx.start)
      )
      case default: ParseError => default
    }

  }

  override def visitPackageDeclaration(ctx: LanguageParser.PackageDeclarationContext): ast.Package.Declaration | ParseError = {
    super.visitPackageDeclaration(ctx)
    val name: base.Partial.Name | ParseError = parseName(ctx.LOWERCASE_NAME())
    return name match {
      case shadowName: Names.LowerCase =>
        ast.Package.Declaration(
          name = shadowName,
          metadata = metadata(ctx.getStart)
        )
      case error: ParseError => wrapParseError(wrappe = error, message = "Invalid package name ", metadata = metadata(ctx.getStart))
      case _ => new ParseError(message = "Invalid package name ", metadata = metadata(ctx.getStart))
    }

  }

  override def visitValueDeclaration(ctx: LanguageParser.ValueDeclarationContext): ast.Value.Declaration | ParseError = {
    super.visitValueDeclaration(ctx);
    val typeReference: Partial.Type.Reference | ParseError = visitValueTypeReference(ctx.valueTypeReference());
    val name: base.Partial.Name | ParseError = parseName(ctx.LOWERCASE_NAME());
    return typeReference match {
      case valueType: ast.Value.Type.Reference =>
        name match {
          case shadowName: Names.LowerCase =>
            ast.Value.Declaration(
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

  override def visitPackageDefinition(ctx: LanguageParser.PackageDefinitionContext): ast.Package.Definition | ParseError = {
    super.visitPackageDefinition(ctx)
    val name: base.Partial.Name | ParseError = parseName(ctx.LOWERCASE_NAME())
    val body: ast.Package.Body | ParseError = visitPackageBody(ctx.packageBody());
    return name match {
      case shadowName: Names.LowerCase =>
        body match {
          case shadowBody: ast.Package.Body => ast.Package.Definition(
            name = shadowName,
            body = shadowBody,
            metadata = metadata(ctx.getStart)
          )
          case parseError: ParseError => wrapParseError(wrappe = parseError, message = "Invalid body definition", metadata = metadata(ctx.getStart))
        }

      case error: ParseError => wrapParseError(wrappe = error, message = "Invalid package name ", metadata = metadata(ctx.getStart))
      case _ => new ParseError(message = "Invalid package name ", metadata = metadata(ctx.getStart))
    }
  }

  override def visitPackageBody(ctx: LanguageParser.PackageBodyContext): ast.Package.Body | ParseError = {
    super.visitPackageBody(ctx)
    val valueDefinitions: Seq[ast.Value.Definition | ParseError] = ctx.valueDefinition().asScala
      .map(item => visitValueDefinition(item)).toSeq

    return ast.Package.Body(
      elements = valueDefinitions.filter(item => item.isInstanceOf[Value.Definition]),
      metadata = metadata(ctx.getStart)
    )
  }

  override def visitValueDefinition(ctx: LanguageParser.ValueDefinitionContext): ast.Value.Definition | ParseError = {
    super.visitValueDefinition(ctx);

    val typeReference: Partial.Type.Reference | ParseError = visitValueTypeReference(ctx.valueTypeReference());
    val name: base.Partial.Name | ParseError = parseName(ctx.LOWERCASE_NAME());
    val initialization: Expression | ParseError = visitExpression(ctx.expression());
    return typeReference match {
      case valueType: ast.Value.Type.Reference =>
        name match {
          case shadowName: Names.LowerCase =>
            initialization match {
              case expression: Expression => ast.Value.Definition(
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