package org.alax.ast

import org.alax.ast
import org.alax.ast.base.{Expression, Partial}
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition
import org.alax.ast.base.Partial.Type.Reference as BaseReference
import org.alax.ast.partial.Identifier

object Value {

  case class Declaration(identifier: Identifier,
                         typeReference: Value.Type.Identifier,
                         metadata: Metadata = Metadata.unknown
                        ) extends BaseDeclaration(metadata = metadata);

  case class Definition(
                         name: Identifier,
                         typeReference: Value.Type.Identifier,
                         initialization: Expression,
                         metadata: Metadata = Metadata.unknown
                       ) extends BaseDefinition(metadata = metadata) {

  }

  case class Identifier(value: String, metadata: Metadata) extends Partial.Identifier(metadata = metadata) {
    assert(value.matches("^[a-z][a-zA-Z0-9\\s]*[a-zA-Z0-9]$"))

    override def text: String = value
  }

  object Identifier {
    def matches(value: String): Boolean = value.matches("^[a-z][a-z0-9\\s]*[a-z0-9]$")
  }

  case class Type() {

  }

  object Type {

    case class Identifier(prefix: Seq[ast.Identifier] =Seq(), suffix: ast.Identifier.UpperCase, metadata: Metadata = Metadata.unknown) extends ast.base.Identifier(metadata = metadata) {

      def text: String = if prefix.isEmpty
      then suffix.text
      else s"${ast.base.Identifier.fold(prefix,".")}.${suffix.text}"

    }
  }


}
