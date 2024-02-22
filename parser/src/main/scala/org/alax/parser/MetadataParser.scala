package org.alax.parser

import org.alax.ast.base.Node
import org.antlr.v4.runtime.{ParserRuleContext, Token}

class MetadataParser {

  object parse {
    def metadata(element: Token| ParserRuleContext): Node.Metadata = {
      return element match
        case token: Token => Node.Metadata(
        location = Node.Location(
          unit = token.getTokenSource.getSourceName,
          startLine = token.getLine,
          endLine = token.getLine,
          startIndex = token.getStartIndex,
          endIndex = token.getStopIndex
        )
      )
        case context: ParserRuleContext => Node.Metadata(
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
