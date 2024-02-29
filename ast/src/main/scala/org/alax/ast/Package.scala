package org.alax.ast

import org.alax.ast
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.ParseError
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition
object Package {

  case class Identifier(value: String, metadata: Metadata) extends ast.base.Identifier(metadata = metadata) {
    assert(value.matches(value))

    override def text: String = value
  }

  object Identifier {
    def matches(value: String): Boolean = value.matches("[a-z]*")
  }

  case class Declaration(identifier: Identifier,
                         metadata: Metadata
                        ) extends BaseDeclaration(metadata = metadata)

  case class Definition(identifier: Identifier,
                        body: Body,
                        metadata: Metadata
                       ) extends BaseDefinition(metadata = metadata)

  case class Body(elements: Seq[Element], metadata: Metadata)
    extends ast.base.Node(metadata = metadata)


  type Element = Value.Definition |  ParseError
}
