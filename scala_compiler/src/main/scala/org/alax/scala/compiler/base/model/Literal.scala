package org.alax.scala.compiler.base.model

import scala.meta.Lit

/**
 * The most basic language construct indicating some hardcoded value
 *
 * @param value
 */
abstract class Literal(val value: Object) extends Expression {
  override def scala: Lit = ???
}

