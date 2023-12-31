package org.alax.scala.compiler.base.model

abstract class Scope extends Product, ScalaMetaNode {

  override def equals(other: Any): Boolean = other match {
    case scope: Scope => org.alax.utilities.equals(this, scope)
    case _ => false
  }

}
