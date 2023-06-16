package org.alax.ast

object model {
  class ParseError(
                    val compilationUnit: String,
                    val startIndex: Int,
                    val endIndex: Int,
                    message: String,
                    cause: Throwable | Null = null
                  )
    extends Exception(message, cause)

  abstract class Node;

  abstract class Statement extends Node;

  abstract class Expression extends Node;

  abstract class Partial extends Node;


  /**
   * Can be used as parts of statements and expressions
   */
  object partials {
    trait Type extends Partial;

    /**
     * Identifiers of the statements and expressions
     */
    trait Name extends Partial;

    object types {
      case class Value(id: String) extends partials.Type;
    }

    object names {

      case class Qualified(qualifications: Seq[String]) extends partials.Name;

      case class LowerCase(value: String) extends partials.Name;

      case class UpperCase(value: String) extends partials.Name;
    }

  }


  object statements {
    abstract class Declaration extends Statement;


    object declarations {
as


      class Import(
                  `package`: partials.names.Qualified,
                  members: Seq[partials.names.UpperCase|partials.names.LowerCase]
                  ) extends statements.Declaration;

      case class Value(
                        name: partials.names.LowerCase | partials.names.Qualified,
                        `type`: partials.Type
                      ) extends statements.Declaration;

      case class ValueWithInitialization(
                                          name: partials.names.LowerCase,
                                          `type`: partials.Type,
                                          initialization: model.Expression
                                        ) extends statements.Declaration;
    }
  }

  object expressions {

    abstract class Literal extends Expression;

    object literals {
      case class Boolean(value: java.lang.Boolean) extends Literal;

      case class Character(value: java.lang.Character) extends Literal;

      case class Integer(value: java.lang.Integer) extends Literal;

      case class Float(value: java.lang.Double) extends Literal;

      case class String(value: java.lang.String) extends Literal;
    }

  }
}
