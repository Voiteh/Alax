package org.alax.scala.compiler.model

import org.alax.scala.compiler.model.Statement

import java.nio.file.Path
import scala.meta
import org.alax.scala.compiler.Context

/**
 * Language construct that provides scoping and encapsulation
 */
abstract class Source[C <: Context](
                                     val path: Path,
                                     val statements: Seq[Statement],
                                     val context: Context | Null
                                   ) {


  def scala: meta.Tree = meta.Source(
    stats = statements.map(statement => statement.scala).toList
  )
}


object Source {

  //TODO reduce Statements type
  case class Unit(
                 override val path: Path,
                   override val statements: Seq[Statement],
                   override val context: Context.Package | Null = null
                 ) extends Source[Context.Package](path,statements, context)

  //TODO reduce Statements type
  case class Package(
                      override val path: Path,
                      override val statements: Seq[Statement],
                      override val context: Context.Package | Context.Module | Null = null
                    ) extends Source[Context.Package | Context.Module](path,statements, context)

  //TODO reduce Statements type
  case class Module(
                     override val path: Path,
                     override val statements: Seq[Statement],
                     override val context: Context.Project | Null = null
                   ) extends Source[Context.Project](path,statements, context)


  //TODO reduce Statements type
  case class Project(
                      override val path: Path,
                      override val statements: Seq[Statement]
                    ) extends Source[Null](path,statements, null)


}