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
                         typeReference: Value.Type.Reference,
                         metadata: Metadata = Metadata.unknown
                        ) extends BaseDeclaration(metadata = metadata);

  case class Definition(
                         name: Identifier,
                         typeReference: Value.Type.Reference,
                         initialization: Expression,
                         metadata: Metadata = Metadata.unknown
                       ) extends BaseDefinition(metadata = metadata) {

  }

  case class Identifier(value: String, metadata: Metadata) extends Partial.Identifier(metadata = metadata) {
    assert(value.matches("^[a-z][a-zA-Z0-9\\s]*[a-zA-Z0-9]$"))

    override def text(): String = value
  }

  object Identifier {
    def matches(value: String): Boolean = value.matches("^[a-z][a-z0-9\\s]*[a-z0-9]$")
  }

  case class Type() {

  }

  object Type {

    case class Reference( typeIdentifier: ast.partial.Identifier.UpperCase,importIdentifier: Import.Identifier | Null=null, override val metadata: Metadata=Metadata.unknown) extends Partial.Type.Reference(metadata = metadata) {

      def text():String = importIdentifier match {
        case importIdentifier: Import.Identifier => s"${importIdentifier.text()}.${typeIdentifier.text()}."
        case null => typeIdentifier.text()
      }


    }
  }



}
