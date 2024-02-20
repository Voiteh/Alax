package org.alax.ast.base

import org.alax.ast.base.Node
import org.alax.ast.base.Node.Metadata

class ParseError(
                  val metadata: Node.Metadata,
                  val message: String,
                  val cause: Seq[ParseError] = Seq()
                )
