package org.alax.ast.base

import org.alax.ast.base.Node.Metadata

import scala.collection.mutable

abstract class Identifier(metadata: Metadata)  extends Node(metadata){


  def text: String;

}

object Identifier {
  def fold(parts: Seq[Identifier], separator: String = ""): String = parts
    .foldLeft(new mutable.StringBuilder())((acc: mutable.StringBuilder, item: Identifier) =>
      if acc.isEmpty
      then acc.append(item.text)
      else acc.append(separator).append(item.text)
    )
    .toString()

}