package org.alax.ast.base

import org.alax.ast.base.Node
import org.alax.ast.base.Node.Metadata

import scala.collection.mutable


abstract class Partial(metadata: Node.Metadata) extends Node(metadata = metadata);


/**
 * Can be used as parts of statements and expressions
 */
object Partial {


  /**
   * Identifiers of the statements and expressions, type references and other language constructs
   */
  abstract class Identifier(metadata: Metadata) extends Partial(metadata = metadata) {
    def text(): String;
  }

  object Identifier {
    def fold(parts: Seq[Identifier], separator: String = ""): String = parts
      .foldLeft(new mutable.StringBuilder())((acc: mutable.StringBuilder, item: Identifier) =>
        if acc.isEmpty then acc.append(item.text()) else acc.append(separator).append(item.text()
        )).toString()
  }

  object Type {
    abstract class Reference(val metadata: Metadata) extends Partial(metadata = metadata);
  }

  abstract class Scope(val metadata: Metadata) extends Partial(metadata = metadata) {

  }

}
