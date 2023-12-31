package org.alax.ast

import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.ParseError
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition
import org.alax.ast.partial.Names

object Package {

  case class Declaration(name: Name,
                         metadata: Metadata
                        ) extends BaseDeclaration(metadata = metadata)

  case class Definition(name: Names.LowerCase,
                        body: Body,
                        metadata: Metadata
                       ) extends BaseDefinition(metadata = metadata)

  case class Body(elements: Seq[Element], override val metadata: Metadata)
    extends base.Partial.Scope(metadata = metadata)

  type Name = Names.LowerCase
  type Element = Value.Definition | ParseError
}
