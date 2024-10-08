package org.alax.ast.base

import scala.collection.mutable
import scala.runtime.ScalaRunTime
import scala.util.hashing.MurmurHash3

abstract class Node(metadata: Node.Metadata) extends Product {
  override def toString: String = org.alax.utilities.toString(this)

  override def equals(obj: Any): Boolean = obj match {
    case node: Node => org.alax.utilities.equals(this, node, (name, value) => !value.isInstanceOf[Node.Metadata])
    case _ => false
  }

}

object Node {
  case class Metadata(location: Location) {

    override def toString: String = org.alax.utilities.toString(this)

    override def equals(obj: Any): Boolean = {
      super.equals(obj)
    }
  }

  object Metadata {
    val unknown: Metadata = Metadata(Location.unknown);
  }

  case class Location(unit: String, startLine: Int, startIndex: Int, endIndex: Int, endLine: Int) {
    assert(endLine >= startLine)


    override def toString: String = org.alax.utilities.toString(this)

  }

  object Location {
    val unknown: Location = Node.Location(
      unit = "",
      startLine = -1,
      endLine = -1,
      startIndex = -1,
      endIndex = -1
    )
  }
}