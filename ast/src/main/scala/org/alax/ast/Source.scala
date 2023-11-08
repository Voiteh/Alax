package org.alax.ast

import org.alax.ast.base.model

import java.nio.file.Path
import org.alax.ast.base.Source.Unit

// For further analysis -> I think object for example in scala/ceylon is actually a top level static Class
object Source {




  object Units {

    case class Project(override val path: Path) extends Unit(path = path)

    case class Module(override val path: Path) extends Unit(path = path) {

    }

    case class Package(
                        override val path: Path,
                        imports: Seq[model.Statement.Declaration.Import],
                        members: Seq[Package.Member]
                      )
      extends Unit(path = path);
    object Package {
      type Member = model.Statement.Definition.Value
    }
    case class Class(
                      override val path: Path,
                      imports: Seq[model.Statement.Declaration.Import],
                      members: Seq[Class.Member]
                    ) extends Unit(path = path);

    object Class {
      type Member = model.Statement.Definition.Value|model.Statement.Declaration
    }
    case class Interface(
                          override val path: Path,
                          imports: Seq[model.Statement.Declaration.Import],
                          members: Seq[Interface.Member]
                        ) extends Unit(path = path);

    object Interface {
      type Member = model.Statement.Definition.Value | model.Statement.Declaration
    }

  }


}