package org.alax.ast.partial

import org.alax.ast.base.Node.Metadata

import scala.collection.mutable
import org.alax.ast.base.Partial
import org.alax.ast.partial.Identifier.LowerCase
import org.alax.ast.partial.Identifier.LowerCase.matches

object Identifier {

  object SpaceFull {

    object LowerCase {
      private def fold(qualifcations: Seq[Identifier.LowerCase]): String = qualifcations
        .foldLeft(new mutable.StringBuilder())((acc: mutable.StringBuilder, item: Identifier.LowerCase) =>
          if acc.isEmpty then acc.append(item.text()) else acc.append(" ").append(item.text()
          )).toString()

      def matches(qualifications: Seq[Identifier.LowerCase]): Boolean = fold(qualifications).matches("[a-z][a-z0-9_\\s]*")
    }

    case class LowerCase(items: Seq[Identifier.LowerCase], metadata: Metadata = Metadata.unknown) extends Partial.Identifier(metadata = metadata) {


      override def text(): String = LowerCase.fold(items)
    }

    object UpperCase {
      private def fold(qualifcations: Seq[Identifier.UpperCase]): String = qualifcations
        .foldLeft(new mutable.StringBuilder())((acc: mutable.StringBuilder, item: Identifier.UpperCase) =>
          if acc.isEmpty then acc.append(item.text()) else acc.append(" ").append(item.text()
          )).toString()
    }

    case class UpperCase(items: Seq[Identifier.UpperCase], metadata: Metadata = Metadata.unknown) extends Partial.Identifier(metadata = metadata) {


      def matches(qualifications: Seq[Identifier.UpperCase]): Boolean = UpperCase.fold(qualifications).matches("[A-Z][A-Z0-9_\\s]*")

      override def text(): String = UpperCase.fold(items)
    }

  }

  type SpaceFull = SpaceFull.LowerCase | SpaceFull.UpperCase;


  object Qualified {
    case class LowerCase(qualifications: Seq[Identifier.LowerCase] = Seq(), metadata: Metadata = Metadata.unknown) extends Partial.Identifier(metadata = metadata) {
      assert(Identifier.Qualified.LowerCase.matches(qualifications))

      def concat(name: Identifier.LowerCase): Identifier.Qualified.LowerCase = Identifier.Qualified.LowerCase(
        qualifications = qualifications.appended(name), metadata = metadata
      )

      override def text(): String = {
        return qualifications.foldLeft(mutable.StringBuilder())((acu: mutable.StringBuilder, item: Identifier.LowerCase) =>
          if (acu.isEmpty) then acu.append(item.text()) else acu.append("." + item.text())).toString()
      };
    }

    object LowerCase {
      def matches(qualifications: Seq[Identifier.LowerCase]): Boolean = qualifications.map(item => item.text())
        .foldLeft(true)((acc: Boolean, item: String) => if acc then Identifier.LowerCase.matches(item) else acc)
    }
  }

  case class Qualified(qualifications: Seq[LowerCase | UpperCase], metadata: Metadata = Metadata.unknown) extends Partial.Identifier(metadata = metadata) {
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
        if (acu.isEmpty) then acu.append(item.text()) else acu.append("." + item.text()))
        .toString()
    };

  }


  case class LowerCase(value: String, metadata: Metadata = Metadata.unknown) extends Partial.Identifier(metadata = metadata) {
    assert(matches(value))

    override def text(): String = value;

  }

  object LowerCase {
    def matches(value: String): Boolean = value.matches("[a-z].*")
  }

  case class UpperCase(value: String, metadata: Metadata = Metadata.unknown) extends Partial.Identifier(metadata = metadata) {
    assert(matches(value))

    override def text(): String = value;


  }

  object UpperCase {
    def matches(value: String): Boolean = value.matches("[A-Z].*")
  }
}
