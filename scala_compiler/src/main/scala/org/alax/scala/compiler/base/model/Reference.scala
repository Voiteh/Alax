package org.alax.scala.compiler.base.model

import scala.meta.{Tree, Term}

/**
 * Reference to specific member
 */
abstract class Reference extends Expression{
  override def scala: Term.Name|Term.Select = ???;
}
