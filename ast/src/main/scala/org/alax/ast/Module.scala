package org.alax.ast

import org.alax.ast
import org.alax.ast.Import.Identifier
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.ParseError
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition

object Module {

  case class Identifier(parts: Seq[ast.Identifier.LowerCase], metadata: Metadata) extends ast.base.Identifier(metadata = metadata) {

    def text: String = ast.base.Identifier.fold(parts, ".")
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

  case class Body(elements: Seq[Element], metadata: Metadata)
    extends ast.base.Node(metadata = metadata)

  type Element = Value.Definition | ParseError

}
