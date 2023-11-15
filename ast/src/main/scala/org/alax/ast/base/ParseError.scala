package org.alax.ast.base
import org.alax.ast.base.Node
import org.alax.ast.base.Node.Metadata

class ParseError(
                  val metadata: Node.Metadata,
                  message: String,
                  cause: Throwable | Null = null
                )
  extends Exception(message, cause)
