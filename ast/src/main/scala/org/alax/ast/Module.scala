package org.alax.ast

import org.alax.ast
import org.alax.ast.Import.Identifier
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.ParseError
import org.alax.ast.partial.Identifier
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition

object Module {

  case class Identifier(parts: Seq[ast.partial.Identifier.LowerCase], metadata: Metadata) extends ast.base.Partial.Identifier(metadata = metadata) {

    assert(Identifier.matches(parts))

    def text: String = ast.base.Partial.Identifier.fold(parts, ".")
  }
  object Identifier{

    def matches(qualifications: Seq[ast.partial.Identifier.LowerCase]): Boolean = ast.base.Partial.Identifier.fold(qualifications,".").matches("^[a-z][a-z0-9\\s]*[a-z0-9]$")
  }
  case class Declaration(
                          identifier: Identifier,
                          metadata: Metadata
                        ) extends BaseDeclaration(metadata = metadata)

  case class Definition(
                         identifier: Identifier,
                         body: Body,
                         metadata: Metadata
                       ) extends BaseDefinition(metadata = metadata)

  case class Body(elements: Seq[Element], override val metadata: Metadata)
    extends base.Partial.Scope(metadata = metadata)

  type Element = Value.Definition | ParseError

}
