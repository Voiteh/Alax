package org.alax.ast

import org.alax.ast.base.Node.Metadata
import org.alax.ast.base.statements.Declaration as BaseDeclaration
object Import {
  abstract class Declaration(
                          val metadata: Metadata = Metadata.unknown
                        ) extends BaseDeclaration(metadata = metadata);
}
