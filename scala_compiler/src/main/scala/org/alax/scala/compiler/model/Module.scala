package org.alax.scala.compiler.model

object Module {
  case class Declaration(name: String) {

  }

  case class Definition(declaration: Declaration) {

  }
}
