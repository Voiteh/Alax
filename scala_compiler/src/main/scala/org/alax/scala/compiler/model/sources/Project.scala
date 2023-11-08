package org.alax.scala.compiler.model.sources

import org.alax.scala.compiler.base.model.{CompilerError, Declaration, Definition, Source}
import org.alax.scala.compiler.transformation.Context

case class Project(
                    override val name: String,
                    override val members: Seq[Project.Member],
                    override val errors: Seq[CompilerError],
                  ) extends Source[Null](name, members, errors, null)

object Project {
  type Member = Nothing
}
