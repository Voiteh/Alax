package org.alax.ast

import org.alax.ast
import org.alax.ast.base.Expression
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.Statement as BaseStatement
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition

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

  case class Reference(
                        typeId: Value.Type.Identifier | Null,
                        valueId: Value.Identifier,
                        metadata: Metadata
                      )
    extends ast.base.expressions.Reference(metadata = metadata) {

  }

  case class Identifier(value: String, metadata: Metadata) extends ast.base.Identifier(metadata = metadata) {
    assert(value.matches("^[a-z][a-zA-Z0-9\\s]*[a-zA-Z0-9]$"))

    override def text: String = value
  }

  object Identifier {
    def matches(value: String): Boolean = value.matches("^[a-z][a-z0-9\\s]*[a-z0-9]$")
  }

  case class Type() {

  }

  object Type {


    case class Identifier(prefix: Seq[ast.Identifier] = Seq(), suffix: ast.Identifier.UpperCase, metadata: Metadata = Metadata.unknown) extends ast.base.Identifier(metadata = metadata) {

      def text: String = if prefix.isEmpty
      then suffix.text
      else s"${ast.base.Identifier.fold(prefix, ".")}.${suffix.text}"

    }
  }


  case class Assignment(left: Value.Identifier, right: Chain.Expression, metadata: Metadata) extends BaseStatement(metadata = metadata)

}
