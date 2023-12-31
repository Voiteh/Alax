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
   * Identifiers of the statements and expressions
   */
  abstract class Name(metadata: Metadata) extends Partial(metadata = metadata) {
    def text(): String;
  }

  object Type {
    abstract class Reference(val metadata: Metadata) extends Partial(metadata = metadata);
  }

  abstract class Scope(val metadata: Metadata) extends Partial(metadata = metadata){

  }

}
