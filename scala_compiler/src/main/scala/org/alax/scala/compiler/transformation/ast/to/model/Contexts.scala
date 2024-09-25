package org.alax.scala.compiler.transformation.ast.to.model

import org.alax.scala.compiler.base.model.{Declaration, Import}
import org.alax.scala.compiler.transformation.Context
import org.alax.scala.compiler.model

object Contexts {

  case class Package(
                   imports: Seq[Import] = Seq(),
                   identifier: model.Package.Declaration.Identifier,
                   override val parent: Null = null,
                 ) extends Context(null) {

  }

  case class Routine(
                      override val parent: Package,
                      identifier: model.Routine.Declaration.Identifier
                    ) extends Context(parent)

  case class Scope(override val parent: Context) extends Context(parent)
}
