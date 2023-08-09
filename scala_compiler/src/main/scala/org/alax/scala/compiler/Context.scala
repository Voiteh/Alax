package org.alax.scala.compiler

import org.alax.scala.compiler.model.Import

abstract class Context {
}

object Context {
  case class Project() extends Context()

  case class Module(project: Project) extends Context() {
  }

  case class Package(parent: Context.Module | Context.Package,
                     imports: Seq[Import] = Seq()) extends Context() {

  }

  case class Unit(
                   parent: Context.Package,
                   imports: Seq[Import] = Seq()
                 ) extends Context() {

  }
}