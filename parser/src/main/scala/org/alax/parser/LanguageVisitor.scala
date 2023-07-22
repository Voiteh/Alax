package org.alax.parser

import org.alax.ast._;
import org.alax.ast.model._
import org.antlr.v4.runtime.{Token, TokenStream}
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode}
import org.alax.ast.{LanguageParser, LanguageLexer, LanguageParserBaseVisitor}
import javax.management.ValueExp;

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

  override def visitType(ctx: LanguageParser.TypeContext): partials.Type | ParseError = {
    super.visitType(ctx)

    val typeName = ctx.FULLY_QUALIFIED_TYPE_NAME();

    return typeName.getSymbol.getType match {
      case LanguageParser.FULLY_QUALIFIED_TYPE_NAME => partials.types.Value(
        id = partials.names.Qualified(
          qualifications = typeName.getSymbol.getText.split("\\.")
            .map[partials.names.LowerCase | partials.names.UpperCase]
              (item => if (item.matches("[A-Z].*")) {
                partials.names.UpperCase(
                  value = item,
                  //FIXME probably invalid symbol for metadata
                  metadata(typeName.getSymbol)
                )
              } else {
                partials.names.LowerCase(
                  value = item,
                  //FIXME probably invalid symbol for metadata
                  metadata(typeName.getSymbol)
                )
              }).toSeq,
          metadata = metadata(typeName.getSymbol)
        ),
        metadata = metadata(ctx.getStart)
      )
      case _ => ParseError(
        metadata(ctx.getStart),
        message = "Unknown type name: " + typeName.getText
      );
    }

  }


  override def visitValueDeclaration(ctx: LanguageParser.ValueDeclarationContext): statements.declarations.Value | ParseError = {
    super.visitValueDeclaration(ctx);
    val `type`: partials.Type | ParseError = visitType(ctx.`type`());
    val name: partials.Name | ParseError = parseName(ctx.LOWERCASE_NAME());
    return `type` match {
      case shadowType: partials.Type =>
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

    val `type`: partials.Type | ParseError = visitType(ctx.`type`());
    val name: partials.Name | ParseError = parseName(ctx.LOWERCASE_NAME());
    val initialization: Expression | ParseError = visitExpression(ctx.expression());
    return `type` match {
      case shadowType: partials.Type =>
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
    return visitLiteral(ctx.literal());
  }


  override def visitLiteral(ctx: LanguageParser.LiteralContext): expressions.Literal | ParseError = {
    super.visitLiteral(ctx);
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
