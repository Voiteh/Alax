package org.alax.syntax

package object model {


  class ParseError(
                    val compilationUnit: String,
                    val startIndex: Int,
                    val endIndex: Int,
                    message: String,
                    cause: Throwable|Null = null
                  )
    extends Exception(message, cause)

  abstract class Node;

  abstract class Statement extends Node;

  abstract class Expression extends Node;

  abstract class Type extends Node;


  object statements {
    abstract class Declaration extends Statement;
  }

  object expressions {

    abstract class Literal extends Expression;

    object literals {
      case class Boolean(val value: java.lang.Boolean) extends Literal;

      case class Character(val value: java.lang.Character) extends Literal;

      case class Integer(val value: java.lang.Integer) extends Literal;

      case class Float(val value: java.lang.Double) extends Literal;

      case class String(val value: java.lang.String) extends Literal;
    }

  }


}

