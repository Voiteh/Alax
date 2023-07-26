package org.alax.parser

import org.alax.ast.*
import org.alax.ast.model.*
import org.antlr.v4.runtime.{ParserRuleContext, Token, TokenStream}
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode}
import org.alax.ast.{LanguageLexer, LanguageParser, LanguageParserBaseVisitor}

import javax.management.ValueExp
import scala.jdk.CollectionConverters.*

class LanguageVisitor(tokenStream: TokenStream)
  extends LanguageParserBaseVisitor[Node | ParseError] {
  private def metadata(token: Token): node.Metadata = {
    return node.Metadata(
      location = node.Location(
        unit = token.getTokenSource.getSourceName,
        lineNumber = token.getLine,
        startIndex = token.getStartIndex,
        endIndex = token.getStopIndex
      )
    )
  }


  private def parseName(terminalNode: TerminalNode): partials.Name | ParseError = {
    return terminalNode.getSymbol.getType match {
      case LanguageParser.LOWERCASE_NAME => partials.names.LowerCase(
        terminalNode.getText, metadata(terminalNode.getSymbol)
      );
      case LanguageParser.UPPERCASE_NAME => partials.names.UpperCase(
        terminalNode.getText, metadata(terminalNode.getSymbol)
      );
      case _ => model.ParseError(
        metadata = metadata(terminalNode.getSymbol),
        message = "Unknown name: " + terminalNode.getText
      );
    }
  }

  private def wrapParseError(wrappe: ParseError, message: String, metadata: node.Metadata): ParseError = ParseError(
    metadata = metadata,
    message = message,
    cause = wrappe
  );

  override def visitImportedName(ctx: LanguageParser.ImportedNameContext): partials.names.Qualified | ParseError = {
    super.visitImportedName(ctx);
    val qualifications: Seq[partials.names.LowerCase | partials.names.UpperCase] = ctx.children.asScala
      .filter(item => item.isInstanceOf[TerminalNode])
      .map(item => item.asInstanceOf[TerminalNode])
      .filter(item => item.getSymbol.getType == LanguageParser.UPPERCASE_NAME || item.getSymbol.getType == LanguageParser.LOWERCASE_NAME)
      .map(item => item.getSymbol.getType match {
        case LanguageParser.UPPERCASE_NAME => partials.names.UpperCase(item.getText, metadata(item.getSymbol));
        case LanguageParser.LOWERCASE_NAME => partials.names.LowerCase(item.getText, metadata(item.getSymbol));
        case _ => return ParseError(metadata(item.getSymbol), f"Unknown token for imported name: ${item.getText}")
      }).toSeq;
    return partials.names.Qualified(qualifications = qualifications, metadata = metadata(ctx.getStart));
  }

  override def visitValueTypeReference(ctx: LanguageParser.ValueTypeReferenceContext): partials.types.ValueTypeReference | ParseError = {
    super.visitValueTypeReference(ctx)

    val typeName = partials.names.UpperCase(value = ctx.typeName.getText, metadata = metadata(ctx.typeName))
    val importedName: partials.names.Qualified | ParseError = visitImportedName(ctx.importedName());
    return importedName match {
      case error: ParseError => return error;
      case qualified: partials.names.Qualified => partials.types.ValueTypeReference(
        id = qualified.qualifications.isEmpty match {
          case true => typeName
          case false => partials.names.Qualified(
            qualifications = qualified.qualifications :+ typeName,
            //TODO fixme probably invalid symbol for metadata
            metadata = metadata(ctx.start)
          )
        },
        //TODO fixme probably invalid symbol for metadata
        metadata = metadata(ctx.start)
      )
    }
  }


  override def visitValueDeclaration(ctx: LanguageParser.ValueDeclarationContext): statements.declarations.Value | ParseError = {
    super.visitValueDeclaration(ctx);
    val typeReference: partials.TypeReference | ParseError = visitValueTypeReference(ctx.valueTypeReference());
    val name: partials.Name | ParseError = parseName(ctx.LOWERCASE_NAME());
    return typeReference match {
      case shadowType: partials.TypeReference =>
        name match {
          case shadowName: partials.names.LowerCase =>
            statements.declarations.Value(
              name = shadowName,
              `type` = shadowType,
              metadata = metadata(ctx.getStart)
            )
          case error: ParseError => wrapParseError(wrappe = error, message = "Invalid value name ", metadata = metadata(ctx.getStart))
          case _ => throw ParserBugException();
        }
      case error: ParseError => wrapParseError(wrappe = error, message = "Invalid value type", metadata = metadata(ctx.getStart))
    }
  }

  override def visitValueDeclarationWithInitialization(ctx: LanguageParser.ValueDeclarationWithInitializationContext):
  statements.declarations.ValueWithInitialization | ParseError = {
    super.visitValueDeclarationWithInitialization(ctx);

    val typeReference: partials.TypeReference | ParseError = visitValueTypeReference(ctx.valueTypeReference());
    val name: partials.Name | ParseError = parseName(ctx.LOWERCASE_NAME());
    val initialization: Expression | ParseError = visitExpression(ctx.expression());
    return typeReference match {
      case shadowType: partials.TypeReference =>
        name match {
          case shadowName: partials.names.LowerCase =>
            initialization match {
              case expression: Expression => statements.declarations.ValueWithInitialization(
                name = shadowName,
                `type` = shadowType,
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
    }
  }


  override def visitExpression(ctx: LanguageParser.ExpressionContext): Expression | ParseError = {
    super.visitExpression(ctx);
    return Option(ctx.literalExpression())
      .map(expression=> visitLiteralExpression(expression))
      .getOrElse(
        ParseError(message = s"Unknown expression: ${ctx.getText}", metadata = metadata(ctx.getStart))
      );
  }


  override def visitLiteralExpression(ctx: LanguageParser.LiteralExpressionContext): expressions.Literal | ParseError = {
    super.visitLiteralExpression(ctx);
    val terminalNode = ctx.children.stream()
      .filter((parseTree: ParseTree) => parseTree.isInstanceOf[TerminalNode])
      .map(node => node.asInstanceOf[TerminalNode])
      .findFirst()
      .orElseThrow(() => new ParserBugException());
    val text = terminalNode.getText;
    return terminalNode.getSymbol.getType match {
      case LanguageParser.BOOLEAN_LITERAL => expressions.literals.Boolean(text.toBoolean, metadata = metadata(ctx.getStart));
      case LanguageParser.INTEGER_LITERAL => expressions.literals.Integer(text.toInt, metadata = metadata(ctx.getStart));
      case LanguageParser.FLOAT_LITERAL => expressions.literals.Float(text.toDouble, metadata = metadata(ctx.getStart));
      case LanguageParser.CHARACTER_LITERAL => expressions.literals.Character(text(1), metadata = metadata(ctx.getStart));
      case LanguageParser.STRING_LITERAL => expressions.literals.String(text.substring(1, text.length - 1), metadata = metadata(ctx.getStart));
      case _ => ParseError(
        message = "Unknown literal: " + text,
        metadata = metadata(ctx.getStart)
      );
    }
  }
}

object LanguageVisitor {

}
