package org.alax.parser

import org.alax.syntax.model._;
import org.alax.syntax._
import org.antlr.v4.runtime.TokenStream
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode}

import javax.management.ValueExp;


class LanguageVisitor(tokenStream: TokenStream)
  extends LanguageParserBaseVisitor[Node | ParseError] {


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
