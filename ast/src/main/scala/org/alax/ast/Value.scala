package org.alax.ast

import org.alax.ast.base.{Expression, Partial}
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition
import org.alax.ast.base.Partial.Type.Reference as BaseReference
import org.alax.ast.partial.Names

object Value {

  case class Declaration(name: Names.LowerCase,
                         typeReference: Value.Type.Reference,
                         metadata: Metadata = Metadata.unknown
                        ) extends BaseDeclaration(metadata = metadata);

  case class Definition(
                         name: Names.LowerCase,
                         typeReference: Value.Type.Reference,
                         initialization: Expression,
                         metadata: Metadata = Metadata.unknown
                       ) extends BaseDefinition(metadata = metadata) {

  }

  case class Type() {

  }

  object Type {
    case class Reference(id: Names.UpperCase | Names.Qualified, override val metadata: Metadata = Metadata.unknown)
      extends BaseReference(metadata = metadata);
  }

}
