package org.alax.ast


import scala.collection.mutable
import org.alax.ast.base
import org.alax.ast.base.Node.Metadata;

case class Identifier(value: String, metadata: Metadata = Metadata.unknown) extends base.Identifier(metadata) {
  assert(Identifier.matches(value))

  override def text: String = value
}

object Identifier {

  def matches(text: String): Boolean = text.matches("[a-zA-Z][a-zA-Z0-9_\\s]*")


  object LowerCase {

    def matches(text: String): Boolean = text.matches("[a-z][a-z0-9_\\s]*")
  }

  case class LowerCase(value: String, metadata: Metadata = Metadata.unknown) extends base.Identifier(metadata) {
    assert(Identifier.LowerCase.matches(value))

    override def text: String = value
  }

  object UpperCase {
    def matches(text: String): Boolean = text.matches("[A-Z][a-zA-Z0-9_\\s]*")
  }

  case class UpperCase(value: String, metadata: Metadata = Metadata.unknown) extends base.Identifier(metadata = metadata) {
    assert(UpperCase.matches(value))

    override def text: String = value
  }

}
