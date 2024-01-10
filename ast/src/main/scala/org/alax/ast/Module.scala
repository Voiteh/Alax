package org.alax.ast

import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.ParseError
import org.alax.ast.partial.Names
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition

object Module {
  case class Declaration(
                          name: Name,
                          metadata: Metadata
                        ) extends BaseDeclaration(metadata = metadata)

  case class Definition(
                         name: Name,
                         body: Body,
                         metadata: Metadata
                       ) extends BaseDefinition(metadata = metadata)

  case class Body(elements: Seq[Element], override val metadata: Metadata)
    extends base.Partial.Scope(metadata = metadata)

  type Name = Names.Qualified.LowerCase
  type Element = Value.Definition | ParseError

}
