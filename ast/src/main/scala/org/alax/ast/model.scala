package org.alax.ast

import org.alax.ast.model.node.Metadata
import org.alax.ast.model.node.Metadata.unknown.location
import org.alax.ast.model.partials.names.UpperCase

import scala.collection.mutable

object model {
  class ParseError(
                    val metadata: node.Metadata,
                    message: String,
                    cause: Throwable | Null = null
                  )
    extends Exception(message, cause)

  abstract class Node(metadata: node.Metadata);

  object node {
    class Metadata(val location: Location);

    object Metadata {
      val unknown: Metadata = Metadata(Location.unknown);
    }

    class Location(val unit: String, val lineNumber: Int, val startIndex: Int, val endIndex: Int)

    object Location {
      val unknown: Location = node.Location(
        unit = "",
        lineNumber = -1,
        startIndex = -1,
        endIndex = -1
      )
    }
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
    abstract class Name(metadata: Metadata) extends Partial(metadata = metadata) {
      def text(): String;
    }

    object types {
      case class Value(id: names.UpperCase | names.Qualified, metadata: Metadata = Metadata.unknown) extends partials.Type(metadata = metadata);
    }

    object names {

      case class Qualified(qualifications: Seq[LowerCase | UpperCase], metadata: Metadata = Metadata.unknown) extends partials.Name(metadata = metadata) {
        assert(qualifications.nonEmpty)

        override def text(): String = {
          return qualifications.foldLeft(mutable.StringBuilder())((acu: mutable.StringBuilder, item: LowerCase | UpperCase) =>
            if (acu.isEmpty) then acu.append(item.text()) else acu.append("." + item.text())).toString()
        };

      }

      case class LowerCase(value: String, metadata: Metadata = Metadata.unknown) extends partials.Name(metadata = metadata) {
        assert(value.matches("[a-z].*"))

        override def text(): String = value;
      }

      case class UpperCase(value: String, metadata: Metadata = Metadata.unknown) extends partials.Name(metadata = metadata) {
        assert(value.matches("[A-Z].*"))

        override def text(): String = value;
      }
    }

  }


  object statements {
    abstract class Declaration(metadata: Metadata) extends Statement(metadata = metadata);


    object declarations {


      abstract class Import(
                             val `package`: partials.names.Qualified | Null,
                             val metadata: Metadata = Metadata.unknown
                           ) extends Statement(metadata = metadata);

      object Import {
        case class Alias(
                          override val `package`: partials.names.Qualified | Null,
                          member: partials.names.LowerCase | partials.names.UpperCase,
                          alias: partials.names.LowerCase | partials.names.UpperCase,
                          override val metadata: Metadata = Metadata.unknown
                        )
          extends Import(`package` = `package`, metadata = metadata)

        case class Simple(
                           override val `package`: partials.names.Qualified | Null,
                           member: partials.names.LowerCase | partials.names.UpperCase,
                           override val metadata: Metadata = Metadata.unknown
                         )
          extends Import(`package` = `package`, metadata = metadata)


        case class Container(
                              override val `package`: partials.names.Qualified,
                              members: Seq[Import],
                              override val metadata: Metadata = Metadata.unknown
                            )
          extends Import(`package` = `package`, metadata = metadata)
      }


      case class Value(
                        name: partials.names.LowerCase,
                        `type`: partials.Type,
                        metadata: Metadata = Metadata.unknown
                      ) extends statements.Declaration(metadata = metadata);

      case class ValueWithInitialization(
                                          name: partials.names.LowerCase,
                                          `type`: partials.Type,
                                          initialization: model.Expression,
                                          metadata: Metadata = Metadata.unknown
                                        ) extends statements.Declaration(metadata = metadata);
    }
  }

  object expressions {

    abstract class Literal(metadata: Metadata) extends Expression(metadata = metadata);

    object literals {
      case class Boolean(value: java.lang.Boolean, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);

      case class Character(value: java.lang.Character, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);

      case class Integer(value: java.lang.Integer, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);

      case class Float(value: java.lang.Double, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);

      case class String(value: java.lang.String, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);
    }

  }
}
