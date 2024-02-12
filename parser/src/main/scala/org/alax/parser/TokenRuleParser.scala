package org.alax.parser

import org.alax.ast.base.Node
import org.antlr.v4.runtime.{ParserRuleContext, Token}

class TokenRuleParser {

  object parse {
    def metadata(token: Token): Node.Metadata = {
      return Node.Metadata(
        location = Node.Location(
          unit = token.getTokenSource.getSourceName,
          startLine = token.getLine,
          endLine = token.getLine,
          startIndex = token.getStartIndex,
          endIndex = token.getStopIndex
        )
      )
    }

    def ruleContext(context: ParserRuleContext): Node.Metadata = {
      return Node.Metadata(
        location = Node.Location(
          unit = context.start.getTokenSource.getSourceName,
          startLine = context.start.getLine,
          endLine = context.stop.getLine,
          startIndex = context.start.getStartIndex,
          endIndex = context.stop.getStopIndex
        )
      )

    }
  }

}
