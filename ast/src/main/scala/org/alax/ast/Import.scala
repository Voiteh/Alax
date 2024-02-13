package org.alax.ast

import org.alax.ast
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.partial.Identifier
import org.alax.ast.partial.Identifier.SpaceFull.LowerCase.{fold}

object Import {


  case class Identifier(parts: Seq[ast.partial.Identifier.LowerCase | ast.partial.Identifier.UpperCase], metadata: Metadata) extends ast.base.Partial.Identifier(metadata = metadata) {

    assert(Identifier.matches(parts))
    def text():String = ast.base.Partial.Identifier.fold(parts,".")
  }
  object Identifier{
    def matches(qualifications: Seq[ast.partial.Identifier.LowerCase | ast.partial.Identifier.UpperCase]): Boolean = ast.base.Partial.Identifier.fold(qualifications,".").matches("^[a-zA-Z][a-zA-Z0-9\\s]*[a-zA-Z0-9]$")
  }

  abstract class Declaration(
                              val metadata: Metadata = Metadata.unknown
                            ) extends BaseDeclaration(metadata = metadata);
}
