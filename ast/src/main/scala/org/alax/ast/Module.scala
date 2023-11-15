package org.alax.ast

import org.alax.ast.base.Node.Metadata
import org.alax.ast.partial.Names
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition

object Module {
  case class Declaration(name: Names.Qualified.LowerCase,
                         metadata: Metadata
                        ) extends BaseDeclaration(metadata = metadata)

  case class Definition(name: Names.Qualified.LowerCase,
                        members: Seq[Member],
                        metadata: Metadata
                       ) extends BaseDefinition(metadata = metadata)

  type Member = Nothing
}
