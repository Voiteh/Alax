package org.alax.ast.base

abstract class Node(metadata: Node.Metadata);

object Node {
  class Metadata(val location: Location);

  object Metadata {
    val unknown: Metadata = Metadata(Location.unknown);
  }

  class Location(val unit: String, val lineNumber: Int, val startIndex: Int, val endIndex: Int)

  object Location {
    val unknown: Location = Node.Location(
      unit = "",
      lineNumber = -1,
      startIndex = -1,
      endIndex = -1
    )
  }
}