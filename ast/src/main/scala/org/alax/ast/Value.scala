package org.alax.ast

import org.alax.ast.base.{Expression, Partial}
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition
import org.alax.ast.base.Partial.Type.Reference as BaseReference
import org.alax.ast.partial.Identifier

object Value {

  case class Declaration(name: Name,
                         typeReference: Value.Type.Reference,
                         metadata: Metadata = Metadata.unknown
                        ) extends BaseDeclaration(metadata = metadata);

  case class Definition(
                         name: Name,
                         typeReference: Value.Type.Reference,
                         initialization: Expression,
                         metadata: Metadata = Metadata.unknown
                       ) extends BaseDefinition(metadata = metadata) {

  }
  case class Name(value:String, metadata: Metadata) extends Partial.Identifier(metadata=metadata){
    assert(value.matches("^[a-z][a-zA-Z0-9\\s]*[a-zA-Z0-9]$"))
    override def text(): String = value
  }

  case class Type() {

  }

  object Type {
    case class Reference(id: Identifier.UpperCase | Identifier.Qualified, override val metadata: Metadata = Metadata.unknown)
      extends BaseReference(metadata = metadata);
  }

}
