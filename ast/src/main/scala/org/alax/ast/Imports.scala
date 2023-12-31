package org.alax.ast

import org.alax.ast.base.Node.Metadata
import org.alax.ast.Import
import org.alax.ast.partial.Names.*

object Imports {
  type ImportedName = Qualified | LowerCase | UpperCase;

  case class Alias(
                    member: ImportedName,
                    alias: ImportedName,
                    override val metadata: Metadata = Metadata.unknown
                  )
    extends Import.Declaration(metadata = metadata)

  case class Simple(
                     member: ImportedName,
                     override val metadata: Metadata = Metadata.unknown
                   )
    extends Import.Declaration(metadata = metadata)


  case class Nested(
                     nest: Qualified,
                     nestee: Seq[Import.Declaration],
                     override val metadata: Metadata = Metadata.unknown
                   )
    extends Import.Declaration(metadata = metadata)
}
