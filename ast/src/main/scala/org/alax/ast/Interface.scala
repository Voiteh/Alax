package org.alax.ast

import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.statements.Definition as BaseDefinition
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.partial.Identifier.UpperCase;

object Interface {


  case class Declaration(
                          name: UpperCase,
                          metadata: Metadata
                        ) extends BaseDeclaration(metadata = metadata)

  case class Definition(name: UpperCase, members: Seq[Member], metadata: Metadata)


  type Member = Value.Definition;

}

