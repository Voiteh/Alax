package org.alax.ast

import java.nio.file.Path


object compilation {

  abstract class Unit(val path: Path);


  object Unit {

    case class Package(
                        override val path: Path,
                        imports: Seq[model.statements.declarations.Import],
                        members: Seq[model.statements.Declaration]
                      )
      extends Unit(path = path);

    case class Type(
                     override val path: Path,
                     imports: Seq[model.statements.declarations.Import],
                     members: Seq[model.statements.Declaration]
                   ) extends Unit(path = path);
  }
}
