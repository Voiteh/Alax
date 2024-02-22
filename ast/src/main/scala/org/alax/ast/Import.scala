package org.alax.ast

import org.alax.ast
import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.statements.Declaration as BaseDeclaration
import org.alax.ast.partial.Identifier
import org.alax.ast.partial.Identifier.{LowerCase, UpperCase}

object Import {


  case class Identifier(parts: Seq[ast.Identifier], metadata: Metadata = Metadata.unknown) extends ast.base.Identifier(metadata = metadata) {

    def text: String = ast.base.Identifier.fold(parts, ".")

    def prefix: Seq[ast.Identifier] = parts.dropRight(1);

    def suffix: ast.Identifier = parts.last;
  }

  object Identifier {
  }

  abstract class Declaration(
                              val metadata: Metadata = Metadata.unknown
                            ) extends BaseDeclaration(metadata = metadata);
}
