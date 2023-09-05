package org.alax.ast

import org.alax.ast.model.Node.Metadata
import org.alax.ast.model.Node.Metadata.unknown.location
import org.alax.ast.model.Partial.Name.UpperCase

import scala.collection.mutable

//TODO change naming of objects to uppercase singular
object model {
  class ParseError(
                    val metadata: Node.Metadata,
                    message: String,
                    cause: Throwable | Null = null
                  )
    extends Exception(message, cause)


  abstract class Node(metadata: Node.Metadata);

  object Node {
    class Metadata(val location: Location);

    object Metadata {
      val unknown: Metadata = Metadata(Location.unknown);
    }

    class Location(val unit: String, val lineNumber: Int, val startIndex: Int, val endIndex: Int)

    object Location {
      val unknown: Location = Node.Location(
        unit = "",
        lineNumber = -1,
        startIndex = -1,
        endIndex = -1
      )
    }
  }

  abstract class Statement(metadata: Node.Metadata) extends Node(metadata = metadata);

  abstract class Expression(metadata: Node.Metadata) extends Node(metadata = metadata);

  abstract class Partial(metadata: Node.Metadata) extends Node(metadata = metadata);


  /**
   * Can be used as parts of statements and expressions
   */
  object Partial {
    abstract class TypeReference(metadata: Metadata) extends Partial(metadata = metadata);

    /**
     * Identifiers of the statements and expressions
     */
    abstract class Name(metadata: Metadata) extends Partial(metadata = metadata) {
      def text(): String;
    }

    object Type {
      case class ValueTypeReference(id: Name.UpperCase | Name.Qualified, metadata: Metadata = Metadata.unknown) extends Partial.TypeReference(metadata = metadata);
    }

    object Name {

      type Imported = Qualified | LowerCase | UpperCase;

      case class Qualified(qualifications: Seq[LowerCase | UpperCase], metadata: Metadata = Metadata.unknown) extends Partial.Name(metadata = metadata) {
        assert(qualifications.nonEmpty)

        def prefix: Seq[LowerCase | UpperCase] = qualifications.dropRight(1);

        /**
         * Not empty
         *
         * @return
         */
        def suffix: LowerCase | UpperCase = qualifications.last;

        override def text(): String = {
          return qualifications.foldLeft(mutable.StringBuilder())((acu: mutable.StringBuilder, item: LowerCase | UpperCase) =>
            if (acu.isEmpty) then acu.append(item.text()) else acu.append("." + item.text())).toString()
        };

      }

      case class LowerCase(value: String, metadata: Metadata = Metadata.unknown) extends Partial.Name(metadata = metadata) {
        assert(value.matches("[a-z].*"))

        override def text(): String = value;
      }

      case class UpperCase(value: String, metadata: Metadata = Metadata.unknown) extends Partial.Name(metadata = metadata) {
        assert(value.matches("[A-Z].*"))

        override def text(): String = value;
      }

    }

  }


  object Statement {
    abstract class Declaration(metadata: Metadata) extends Statement(metadata = metadata);

    abstract class Definition(metadata: Metadata) extends Statement(metadata = metadata);

    object Definition {
      case class Value(
                        name: Partial.Name.LowerCase,
                        typeReference: Partial.TypeReference,
                        initialization: model.Expression,
                        metadata: Metadata = Metadata.unknown
                      ) extends Statement.Definition(metadata = metadata);
    }


    object Declaration {


      abstract class Import(
                             val metadata: Metadata = Metadata.unknown
                           ) extends Statement.Declaration(metadata = metadata);

      object Import {
        case class Alias(
                          member: Partial.Name.Imported,
                          alias: Partial.Name.Imported,
                          override val metadata: Metadata = Metadata.unknown
                        )
          extends Import(metadata = metadata)

        case class Simple(
                           member: Partial.Name.Imported,
                           override val metadata: Metadata = Metadata.unknown
                         )
          extends Import(metadata = metadata)


        case class Nested(
                           nest: Partial.Name.Qualified,
                           nestee: Seq[Import],
                           override val metadata: Metadata = Metadata.unknown
                         )
          extends Import(metadata = metadata)
      }


      case class Value(
                        name: Partial.Name.LowerCase,
                        `type`: Partial.TypeReference,
                        metadata: Metadata = Metadata.unknown
                      ) extends Statement.Declaration(metadata = metadata);


    }
  }

  object Expression {

    abstract class Literal(metadata: Metadata) extends Expression(metadata = metadata);

    object Literal {
      case class Boolean(value: java.lang.Boolean, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);

      case class Character(value: java.lang.Character, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);

      case class Integer(value: java.lang.Integer, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);

      case class Float(value: java.lang.Double, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);

      case class String(value: java.lang.String, metadata: Metadata = Metadata.unknown) extends Literal(metadata = metadata);
    }

  }

}