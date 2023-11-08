package org.alax.scala.compiler.model.sources

import org.alax.scala.compiler.base.model.{CompilerError, Declaration, Definition, Source}
import org.alax.scala.compiler.transformation.Context

case class Package(
                    override val name: String,
                    override val members: Seq[Package.Member],
                    override val errors: Seq[CompilerError],
                    override val context: Context.Package | Context.Module | Null = null
                  ) extends Source[Context.Package | Context.Module](name, members, errors, context)

object Package {
  type Member = Definition
}