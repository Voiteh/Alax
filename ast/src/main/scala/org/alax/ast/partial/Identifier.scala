package org.alax.ast.partial

import org.alax.ast.base.Node.Metadata

import scala.collection.mutable
import org.alax.ast.base.Partial
import org.alax.ast.partial.Identifier.LowerCase

object Identifier {

  object Qualified {
    case class LowerCase(qualifications: Seq[Identifier.LowerCase] = Seq(), metadata: Metadata = Metadata.unknown) extends Partial.Identifier(metadata = metadata) {
      qualifications.map(item => item.text()).foreach(value => assert(value.matches("[a-z].*")));

      def concat(name: Identifier.LowerCase): Identifier.Qualified.LowerCase = Identifier.Qualified.LowerCase(
        qualifications = qualifications.appended(name), metadata = metadata
      )

      override def text(): String = {
        return qualifications.foldLeft(mutable.StringBuilder())((acu: mutable.StringBuilder, item: Identifier.LowerCase) =>
          if (acu.isEmpty) then acu.append(item.text()) else acu.append("." + item.text())).toString()
      };
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
    assert(value.matches("[a-z].*"))

    override def text(): String = value;

  }

  case class UpperCase(value: String, metadata: Metadata = Metadata.unknown) extends Partial.Identifier(metadata = metadata) {
    assert(value.matches("[A-Z].*"))

    override def text(): String = value;


  }
}
