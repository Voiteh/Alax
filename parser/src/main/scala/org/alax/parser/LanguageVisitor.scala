package org.alax.parser

import org.alax.model.{BooleanLiteral, CharacterLiteral, CompilationUnit, FlaotLiteral, IntegerLiteral, StringLiteral, Value}
import org.alax.model.base.*
import org.alax.syntax.*
import org.antlr.v4.runtime.TokenStream
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode}

import javax.management.ValueExp;


class LanguageVisitor(tokenStream: TokenStream)
  extends LanguageParserBaseVisitor[Statement | Expression | Container | Declaration.Type | ParseError] {


  // String a;
  override def visitValueDeclaration(ctx: LanguageParser.ValueDeclarationContext): Value.Declaration | ParseError = {
    super.visitValueDeclaration(ctx);
    val declarationName = ctx.DECLARATION_NAME();
    val declarationType = visitType(ctx.`type`);
    return Value.Declaration(
      name = declarationName.getText,
      `type` = declarationType.asInstanceOf[Value.Type]
    );
  }

  override def visitType(ctx: LanguageParser.TypeContext): Declaration.Type | ParseError = {
    super.visitType(ctx);
    val terminalNode = ctx.children.stream()
      .filter((parseTree: ParseTree) => parseTree.isInstanceOf[TerminalNode])
      .map(node => node.asInstanceOf[TerminalNode])
      .findFirst()
      .orElseThrow(() => new ParserBugException());
    return terminalNode.getSymbol.getType match {
      case LanguageParser.VALUE_TYPE_NAME => Value.Type(
        id = Declaration.Type.Id(terminalNode.getSymbol.getText)
      );
    }
  }

  override def visitLiteral(ctx: LanguageParser.LiteralContext): Literal | ParseError = {
    super.visitLiteral(ctx);
    val terminalNode = ctx.children.stream()
      .filter((parseTree: ParseTree) => parseTree.isInstanceOf[TerminalNode])
      .map(node => node.asInstanceOf[TerminalNode])
      .findFirst()
      .orElseThrow(() => new ParserBugException());
    val text = terminalNode.getText;
    return terminalNode.getSymbol.getType match {
      case LanguageParser.BOOLEAN_LITERAL => BooleanLiteral(text.toBoolean);
      case LanguageParser.INTEGER_LITERAL => IntegerLiteral(text.toInt);
      case LanguageParser.FLOAT_LITERAL => FlaotLiteral(text.toDouble);
      case LanguageParser.CHARACTER_LITERAL => CharacterLiteral(text(1));
      case LanguageParser.STRING_LITERAL => StringLiteral(text);
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
