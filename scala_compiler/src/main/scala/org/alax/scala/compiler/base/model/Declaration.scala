package org.alax.scala.compiler.base.model

import scala.meta.{Decl, Stat}


/**
 * Something that changes structure of program constructs add new type / member / variable etc.
 *
 * @param name   - Name of the declaration
 * @param `type` - Internal property of declaration indicates the declaration type. It is not type declaration itself like Class, Interface etc. It can be a value / union / intersection / object type (reference)
 */
abstract class Declaration(val name: Declaration.Name | Null, val `type`: Declaration.Type)
  extends Statement {
  override def scala: Decl = ???
}

object Declaration {

  /**
   * Name of the declaration
   */
  type Name = String;

  /**
   * Internal property of declaration indicates the declaration type. It is not type declaration itself like Class, Interface etc.
   */
  abstract class Type() {

  }

  object Type {
    /**
     * Identifier of type that has been used to declare a declaration
     *
     */
    class Id(val value: String) {
      override def equals(obj: Any): Boolean = {
        return obj match {
          case id: Id => value == id.value
          case _ => false
        }
      }
    }
  }

}