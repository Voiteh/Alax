package org.alax.ast

import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.base.statements.Definition as BaseDefinition
import org.alax.ast.partial.Names
//TODO potencjalnie mogli bysmy uzyc Declaracji jako pole w Definicji
object Package {

  case class Declaration(name: Names.LowerCase,
                         metadata: Metadata
                        ) extends BaseDeclaration(metadata = metadata)

  case class Definition(name: Names.LowerCase,
                        members: Seq[Member],
                        metadata: Metadata
                       ) extends BaseDefinition(metadata = metadata)

  type Member = Value.Definition
}
