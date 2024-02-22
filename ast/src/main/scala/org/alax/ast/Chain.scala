package org.alax.ast

import org.alax.ast.base.{ParseError, Expression as BaseExpression}
import org.alax.ast.base.Node.Metadata

object Chain {
  case class Expression(expressions: Seq[BaseExpression|ParseError], metadata: Metadata) extends BaseExpression(metadata)
}
