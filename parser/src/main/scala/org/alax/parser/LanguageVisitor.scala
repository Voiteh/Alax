package org.alax.parser

import org.alax.model.{BooleanLiteral, CharacterLiteral, FlaotLiteral, IntegerLiteral, StringLiteral}
import org.alax.model.base.{Literal, *}
import org.alax.syntax.*
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode};

class LanguageVisitor extends LanguageParserBaseVisitor[Statement | Expression | ParseError] {
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
        compilationUnit = "dummy.alax",
        startIndex = terminalNode.getSymbol.getStartIndex,
        endIndex = terminalNode.getSymbol.getStopIndex,
        message = "Unknown literal: " + text
      );
    }
  }

}

object LanguageVisitor {

}
