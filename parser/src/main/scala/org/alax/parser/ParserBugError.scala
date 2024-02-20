package org.alax.parser

import org.alax.ast.base.{Node, ParseError}

class ParserBugError(override val metadata: Node.Metadata,
                     override val cause: Seq[ParseError] = Seq()) extends ParseError(
  metadata = metadata, message = "You found a Bug! in Alax Parser", cause = cause) {

}
