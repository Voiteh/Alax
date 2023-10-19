package org.alax.scala.compiler.model
import scala.meta.{Tree,Term}

/**
 * Reference to specific member
 */
abstract  class Reference extends Expression {
  override val scala: Term = ???
}
