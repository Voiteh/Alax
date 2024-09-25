package org.alax.ast

import org.alax.ast.base.Node.Metadata
import org.alax.ast.Import

object Imports {

  case class Alias(
                    member: Import.Identifier,
                    alias: Import.Identifier,
                    override val metadata: Metadata = Metadata.unknown
                  )
    extends Import.Declaration(metadata = metadata)

  case class Simple(
                     member: Import.Identifier,
                     override val metadata: Metadata = Metadata.unknown
                   )
    extends Import.Declaration(metadata = metadata)


  case class Nested(
                     nest: Import.Identifier,
                     nestee: Seq[Import.Declaration],
                     override val metadata: Metadata = Metadata.unknown
                   )
    extends Import.Declaration(metadata = metadata)
}
