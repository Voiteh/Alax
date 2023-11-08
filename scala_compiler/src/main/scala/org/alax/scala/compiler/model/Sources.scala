package org.alax.scala.compiler.model

import org.alax.scala.compiler.base.model.{CompilerError, Declaration, Definition, Source}
import org.alax.scala.compiler.transformation.Context
import os.Path

import scala.meta




object Sources {
  case class Unit(
                   override val name: String,
                   override val members: Seq[Unit.Member],
                   override val errors: Seq[CompilerError],
                   override val context: Context.Package | Null = null
                 ) extends Source[Context.Package](name, members, errors, context)

  object Unit {
    type Member = Declaration | Definition;
  }

  case class Package(
                      override val name: String,
                      override val members: Seq[Package.Member],
                      override val errors: Seq[CompilerError],
                      override val context: Context.Package | Context.Module | Null = null
                    ) extends Source[Context.Package | Context.Module](name, members, errors, context)

  object Package {
    type Member = Definition;
  }

  case class Module(
                     override val name: String,
                     override val members: Seq[Module.Member],
                     override val errors: Seq[CompilerError],
                     override val context: Context.Project | Null = null
                   ) extends Source[Context.Project](name, members, errors, context)

  object Module {
    type Member = Nothing;
  }

  case class Project(
                      override val name: String,
                      override val members: Seq[Project.Member],
                      override val errors: Seq[CompilerError],
                    ) extends Source[Null](name, members, errors, null)

  object Project {
    type Member = Nothing;
  }
}