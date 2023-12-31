package org.alax.scala.compiler.base.model

import scala.meta.{Term,Ref}

/**
 * Something that can be assigned
 */
abstract class Expression extends ScalaMetaNode {
  def scala: Term = ???;
}
