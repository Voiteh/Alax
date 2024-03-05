package org.alax.ast

import org.alax.ast.base.{ParseError, Expression as BaseExpression,Statement as BaseStatement}
import org.alax.ast.base.Node.Metadata

object Chain {
  case class Expression(
                         expression: BaseExpression|ParseError,
                         next:Chain.Expression|Null,
                         metadata: Metadata
                       ) extends BaseExpression(metadata)

}
