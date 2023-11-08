package org.alax.scala.compiler.model.sources

import org.alax.scala.compiler.base.model.{CompilerError, Declaration, Definition, Source}
import org.alax.scala.compiler.transformation.Context

case class Unit(
                 override val name: String,
                 override val members: Seq[Unit.Member],
                 override val errors: Seq[CompilerError],
                 override val context: Context.Package | Null = null
               ) extends Source[Context.Package](name, members, errors, context)

object Unit {
  type Member = Declaration | Definition
}