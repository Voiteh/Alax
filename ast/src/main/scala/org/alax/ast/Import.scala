package org.alax.ast

import org.alax.ast
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.partial.Identifier
import org.alax.ast.partial.Identifier.{LowerCase, UpperCase}

object Import {


  case class Identifier(parts: Seq[ast.partial.Identifier.LowerCase | ast.partial.Identifier.UpperCase], metadata: Metadata = Metadata.unknown) extends ast.base.Partial.Identifier(metadata = metadata) {

    assert(Identifier.matches(parts))

    def text(): String = ast.base.Partial.Identifier.fold(parts, ".")

    def prefix: Seq[ast.partial.Identifier.LowerCase | ast.partial.Identifier.UpperCase] = parts.dropRight(1);

    def suffix: ast.partial.Identifier.LowerCase | ast.partial.Identifier.UpperCase = parts.last;
  }

  object Identifier {
    def matches(qualifications: Seq[ast.partial.Identifier.LowerCase | ast.partial.Identifier.UpperCase]): Boolean = {
      return qualifications.map(item => item.text().matches("^[a-zA-Z][a-zA-Z0-9\\s]*[a-zA-Z0-9]$"))
        .foldLeft(true)((acc: Boolean, item: Boolean) => if !acc then acc else item)
    }
  }

  abstract class Declaration(
                              val metadata: Metadata = Metadata.unknown
                            ) extends BaseDeclaration(metadata = metadata);
}
