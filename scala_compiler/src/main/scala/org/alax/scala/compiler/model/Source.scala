package org.alax.scala.compiler.model

import org.alax.scala.compiler.model.Statement
import org.alax.scala.compiler.transformation.Context

import os.Path
import scala.meta

/**
 * Language construct that provides scoping and encapsulation
 */
abstract class Source[C <: Context](
                                     val name: String,
                                     val members: Seq[Statement],
                                     val errors: Seq[CompilerError],
                                     val context: Context | Null
                                   ) {


  def scala: meta.Tree = meta.Source(
    stats = members.map(statement => statement.scala).toList
  )
}


object Source {
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