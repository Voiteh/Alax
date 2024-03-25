package org.alax.ast

import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.Statement
import org.alax.ast.base

import scala.collection.mutable

object Evaluable {

  /**
   * To evaluate given expression it must first be defined, this is final condition
   *
   * @param identifier - Identifies definition
   * @param definable  - This differentiate Definition from Declaration in AST, it represent things that finalizes definition
   * @param metadata   - Metadata for definition
   */
  abstract class Definition[Definable](identifier: Identifier, definable: Definable, metadata: Metadata) extends Statement(metadata = metadata);


  /**
   * To evaluate expression it needs first to be identified, this is a necessary condition, not a final one
   *
   * @param metadata - Metadata for Operand Identifier
   */
  case class Identifier(value: String, metadata: Metadata=Metadata.unknown) extends base.Identifier(metadata) {
    assert(Identifier.matches(value))

    override def text: String = value
  }

  object Identifier {

    def matches(text: String): Boolean = text.matches("[a-z][a-z0-9_\\s]*")

  }

  /**
   * To evaluate expression it first needs to be declared, this is a necessary condition, not a final one
   *
   * @param metadata - Metadata for Operand Declaration
   */
  abstract class Declaration(identifier: Identifier, metadata: Metadata) extends Statement(metadata = metadata)

  case class Reference(
                        typeId: Value.Type.Identifier | Null = null,
                        valueId: Identifier,
                        metadata: Metadata = Metadata.unknown
                      )
    extends base.expressions.Reference(metadata = metadata) {

  }

}
