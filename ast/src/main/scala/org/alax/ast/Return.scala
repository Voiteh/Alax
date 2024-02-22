package org.alax.ast

import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.Statement as BaseStatement

object Return {
  case class Statement(expression: Chain.Expression, metadata: Metadata) extends BaseStatement(metadata = metadata)
}
