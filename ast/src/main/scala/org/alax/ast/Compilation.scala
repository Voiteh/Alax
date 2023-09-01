package org.alax.ast

import java.nio.file.Path


object Compilation {

  abstract class Unit(val path: Path);


  object Unit {

    case class Package(
                        override val path: Path,
                        imports: Seq[model.Statement.Declaration.Import],
                        members: Seq[model.Statement.Declaration]
                      )
      extends Unit(path = path);

    case class Type(
                     override val path: Path,
                     imports: Seq[model.Statement.Declaration.Import],
                     members: Seq[model.Statement.Declaration]
                   ) extends Unit(path = path);
  }
}
