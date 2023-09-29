package org.alax.ast

import java.nio.file.Path


// For further analysis -> I think object for example in scala/ceylon is actually a top level static Class
object Source {

  abstract class Unit(val path: Path);


  object Unit {

    case class Project(override val path: Path) extends Unit(path = path)

    case class Module(override val path: Path) extends Unit(path = path) {

    }

    case class Package(
                        override val path: Path,
                        imports: Seq[model.Statement.Declaration.Import],
                        members: Seq[model.Statement.Declaration]
                      )
      extends Unit(path = path);

    case class Class(
                      override val path: Path,
                      imports: Seq[model.Statement.Declaration.Import],
                      members: Seq[model.Statement.Declaration]
                    ) extends Unit(path = path);

    case class Interface(
                          override val path: Path,
                          imports: Seq[model.Statement.Declaration.Import],
                          members: Seq[model.Statement.Declaration]
                        ) extends Unit(path = path);

  }
}