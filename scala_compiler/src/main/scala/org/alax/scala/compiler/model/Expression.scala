package org.alax.scala.compiler.model

import scala.meta.{Term,Ref}

/**
 * Something that can be assigned
 */
abstract class Expression {
  def scala: Term = ???;
}
