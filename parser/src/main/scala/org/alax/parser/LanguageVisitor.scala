package org.alax.parser

import org.alax.model.{BooleanLiteral, CharacterLiteral, CompilationUnit, FlaotLiteral, IntegerLiteral, StringLiteral}
import org.alax.model.base._
import org.alax.syntax.*
import org.antlr.v4.runtime.TokenStream;
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode}


class LanguageVisitor(tokenStream: TokenStream) extends LanguageParserBaseVisitor[Statement | Expression | Container| ParseError] {


  override def visitCompilationUnit(ctx: LanguageParser.CompilationUnitContext): CompilationUnit|ParseError = {
    super.visitCompilationUnit(ctx)
    ParseError(
      compilationUnit = tokenStream.getSourceName,
      startIndex = 0,
      endIndex = -1,
      message = "Unknown container at path: "
    );
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
