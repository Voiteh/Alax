package org.alax.scala.compiler.base.model

import scala.meta.{Stat, Tree}

trait ScalaMetaNode {
  def scala: Tree;

  override def equals(other: Any): Boolean = {
    return other match {
      case node: ScalaMetaNode => scala.toString().equals(node.scala.toString())
    }
  }

}
