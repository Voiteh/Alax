package org.alax.ast

import org.alax.ast.model.node.Metadata

object model {
  class ParseError(
                    val metadata: node.Metadata,
                    message: String,
                    cause: Throwable | Null = null
                  )
    extends Exception(message, cause)

  abstract class Node(metadata: node.Metadata);

  object node {
    class Metadata(val location: Location)

    class Location(val unit: String, val lineNumber: Int, val startIndex: Int, val endIndex: Int)
  }

  abstract class Statement(metadata: node.Metadata) extends Node(metadata = metadata);

  abstract class Expression(metadata: node.Metadata) extends Node(metadata = metadata);

  abstract class Partial(metadata: node.Metadata) extends Node(metadata = metadata);


  /**
   * Can be used as parts of statements and expressions
   */
  object partials {
    abstract class Type(metadata: Metadata) extends Partial(metadata = metadata);

    /**
     * Identifiers of the statements and expressions
     */
    abstract class Name(metadata: Metadata) extends Partial(metadata = metadata);

    object types {
      case class Value(id: names.UpperCase | names.Qualified, metadata: Metadata) extends partials.Type(metadata = metadata);
    }

    object names {

      case class Qualified(qualifications: Seq[LowerCase | UpperCase], metadata: Metadata) extends partials.Name(metadata = metadata){
        toString => qualifications.foldLeft(StringBuilder())((acu, item) => if(acu.isEmpty) then acu.append(item) else acu.append("."+item));
      }

      case class LowerCase(value: String, metadata: Metadata) extends partials.Name(metadata = metadata);

      case class UpperCase(value: String, metadata: Metadata) extends partials.Name(metadata = metadata);
    }

  }


  object statements {
    abstract class Declaration(metadata: Metadata) extends Statement(metadata = metadata);


    object declarations {


      class Import(
                    `package`: partials.names.Qualified,
                    members: Seq[partials.names.UpperCase | partials.names.LowerCase],
                    metadata: Metadata
                  ) extends Statement(metadata = metadata);

      case class Value(
                        name: partials.names.LowerCase,
                        `type`: partials.Type,
                        metadata: Metadata
                      ) extends statements.Declaration(metadata = metadata);

      case class ValueWithInitialization(
                                          name: partials.names.LowerCase,
                                          `type`: partials.Type,
                                          initialization: model.Expression,
                                          metadata: Metadata
                                        ) extends statements.Declaration(metadata = metadata);
    }
  }

  object expressions {

    abstract class Literal(metadata: Metadata) extends Expression(metadata = metadata);

    object literals {
      case class Boolean(value: java.lang.Boolean, metadata: Metadata) extends Literal(metadata = metadata);

      case class Character(value: java.lang.Character, metadata: Metadata) extends Literal(metadata = metadata);

      case class Integer(value: java.lang.Integer, metadata: Metadata) extends Literal(metadata = metadata);

      case class Float(value: java.lang.Double, metadata: Metadata) extends Literal(metadata = metadata);

      case class String(value: java.lang.String, metadata: Metadata) extends Literal(metadata = metadata);
    }

  }
}
