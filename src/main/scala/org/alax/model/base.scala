package org.alax.model

object base {

  /**
   * Something that is fully blown language construct, it is final form, statments are separated by semi-colons
   */
  abstract class Statement

  /**
   * Something that can be assigned
   */
  abstract class Expression

  /**
   * Something that changes structure of program constructs add new type / member / variable etc.
   * @param name - Name of the declaration
   * @param `type` - Internal property of declaration indicates the declaration type. It is not type declaration itself like Class, Interface etc. It can be a value / union / intersection / object type (reference)
   */
  abstract class Declaration(val name: Declaration.Name | Null, val `type`: base.Declaration.Type)
    extends Statement

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
    object Type{
      /**
       * Identifier of type that has been used to declarate a declaration
       * @param fullyQualifiedName
       */
      class Id(val fullyQualifiedName: String)
    }

  }


  /**
   * The most basic language construct indicating some hardcoded value
   * @param value
   */
  abstract class Literal(val value:Object) extends Expression;


  /**
   * Something representing state change of language constructs like values
   * @param rightSide
   */
  abstract class Assignment(val rightSide: Expression) extends Statement{

  }


}

