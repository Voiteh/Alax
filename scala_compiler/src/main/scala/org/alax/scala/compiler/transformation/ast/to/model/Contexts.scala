package org.alax.scala.compiler.transformation.ast.to.model

import org.alax.scala.compiler.base.model.{Declaration, Import}
import org.alax.scala.compiler.transformation.Context

object Contexts {

  case class Unit(
                   imports: Seq[Import] = Seq(),
                   override val parent: Null = null,
                 ) extends Context(null) {

  }
}
