package org.alax.scala.compiler.model

import org.alax.scala.compiler.base
import org.alax.scala.compiler.base.model.CompilerError

object Expression {

  case class Chain(
                    elements: Seq[base.model.Expression],
                  ) extends base.model.Expression {

  }


}
