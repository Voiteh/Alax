package org.alax.ast.base

import scala.collection.mutable
import org.alax.ast.base.Node
import org.alax.ast.base.Node.Metadata

//TODO make it case class extending some other common Error, so that we can use  org.alax.utilities.toString(this)
class ParseError(
                  val metadata: Node.Metadata,
                  val message: String,
                  val cause: Seq[ParseError] = Seq()
                ) {
  override def toString: String = s"${message} at ${metadata.location}, caused by: " +
    s"${
      cause.foldLeft(
        new mutable.StringBuilder()
      )((acc: mutable.StringBuilder, item: ParseError) => if acc.isEmpty then acc.append(item.toString) else acc.append(", ").append(item)).toString
    }"
}
