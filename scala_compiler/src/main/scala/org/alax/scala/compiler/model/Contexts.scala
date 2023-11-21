package org.alax.scala.compiler.model

import org.alax.scala.compiler.base.model.Import
import org.alax.scala.compiler.base.model.Declaration

object Contexts {
  case class Project() extends Declaration.Context(null)

  case class Module(override val parent: Project | Null = null) extends Declaration.Context(parent) {
  }


  case class Unit(
                   imports: Seq[Import] = Seq(),
                   override val parent: Unit | Module | Null = null,
                 ) extends Declaration.Context(parent) {

  }
}
