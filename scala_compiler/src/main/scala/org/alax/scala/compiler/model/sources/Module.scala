package org.alax.scala.compiler.model.sources

import org.alax.scala.compiler.base.model.{CompilerError, Declaration, Definition, Source}
import org.alax.scala.compiler.transformation.Context

case class Module(
                   override val name: String,
                   override val members: Seq[Module.Member],
                   override val errors: Seq[CompilerError],
                   override val context: Context.Project | Null = null
                 ) extends Source[Context.Project](name, members, errors, context)

object Module {
  type Member = Nothing
}
