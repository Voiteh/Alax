package org.alax.parser

import org.alax.syntax.model._;
import org.alax.syntax._
import org.antlr.v4.runtime.TokenStream
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode}

import javax.management.ValueExp;


class LanguageVisitor(tokenStream: TokenStream)
  extends LanguageParserBaseVisitor[Node | ParseError] {


  private def parseName(terminalNode: TerminalNode): partials.Name | ParseError = {
    return terminalNode.getSymbol.getType match {
      case LanguageParser.LOWERCASE_NAME => partials.names.LowerCaseName(terminalNode.getText);
      case LanguageParser.UPPERCASE_NAME => partials.names.UpperCaseName(terminalNode.getText);
      case _ => ParseError(
        compilationUnit = tokenStream.getSourceName,
        startIndex = terminalNode.getSymbol.getStartIndex,
        endIndex = terminalNode.getSymbol.getStopIndex,
        message = "Unknown name: " + terminalNode.getText
      );
    }
  }

  private def wrapParseError(wrappe: ParseError, message: String): model.ParseError = model.ParseError(
    compilationUnit = wrappe.compilationUnit,
    startIndex = wrappe.startIndex,
    endIndex = wrappe.endIndex,
    message = message,
    cause = wrappe
  );

  override def visitType(ctx: LanguageParser.TypeContext): model.partials.Type | ParseError = {
    super.visitType(ctx)
    val typeName = ctx.FULLY_QUALIFIED_TYPE_NAME();
    return typeName.getSymbol.getType match {
      case LanguageParser.FULLY_QUALIFIED_TYPE_NAME => model.partials.types.Value(typeName.getText)
      case _ => ParseError(
        compilationUnit = tokenStream.getSourceName,
        startIndex = typeName.getSymbol.getStartIndex,
        endIndex = typeName.getSymbol.getStopIndex,
        message = "Unknown type name: " + typeName.getText
      );
    }

  }


  override def visitValueDeclaration(ctx: LanguageParser.ValueDeclarationContext): model.statements.declarations.Value | model.ParseError = {
    super.visitValueDeclaration(ctx);
    val `type`: model.partials.Type | ParseError = visitType(ctx.`type`());
    val name: partials.Name | ParseError = parseName(ctx.LOWERCASE_NAME());
    return `type` match {
      case shadowType: model.partials.Type =>
        name match {
          case shadowName: model.partials.names.LowerCaseName =>
            model.statements.declarations.Value(
              name = shadowName,
              `type` = shadowType
            )
          case error: model.ParseError => wrapParseError(wrappe = error, message = "Invalid value name ")
          case _ => throw ParserBugException();
        }
      case error: model.ParseError => wrapParseError(wrappe = error, message = "Invalid value type")
    }
  }

  override def visitValueDeclaratonWithInitialization(ctx: LanguageParser.ValueDeclaratonWithInitializationContext): model.statements.declarations.ValueWithInitialization = {
    super.visitValueDeclaratonWithInitialization(ctx);

  }

  override def visitExpression(ctx: LanguageParser.ExpressionContext): model.Expression = {
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
      case LanguageParser.BOOLEAN_LITERAL => expressions.literals.Boolean(text.toBoolean);
      case LanguageParser.INTEGER_LITERAL => expressions.literals.Integer(text.toInt);
      case LanguageParser.FLOAT_LITERAL => expressions.literals.Float(text.toDouble);
      case LanguageParser.CHARACTER_LITERAL => expressions.literals.Character(text(1));
      case LanguageParser.STRING_LITERAL => expressions.literals.String(text);
      case _ => ParseError(
        compilationUnit = tokenStream.getSourceName,
        startIndex = terminalNode.getSymbol.getStartIndex,
        endIndex = terminalNode.getSymbol.getStopIndex,
        message = "Unknown literal: " + text
      );
    }
  }
}

object LanguageVisitor {

}
