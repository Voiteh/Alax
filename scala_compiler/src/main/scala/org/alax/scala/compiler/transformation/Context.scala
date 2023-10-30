package org.alax.scala.compiler.transformation

import org.alax.scala.compiler.model.Import

import java.nio.file.Path


abstract class Context(val path: Path, val parent: Context | Null = null) {


}

object Context {

  case class Project(override val path: Path) extends Context(path, null)

  case class Module(override val path: Path, override val parent: Project | Null = null) extends Context(path, parent) {
  }

  case class Package(override val path: Path, imports: Seq[Import] = Seq(), override val parent: Context.Module | Context.Package | Null = null) extends Context(path = path, parent = parent) {

  }

  case class Unit(
                   override val path: Path,
                   imports: Seq[Import] = Seq(),
                   override val parent: Context.Package | Null = null,
                 ) extends Context(path, parent) {

  }
}