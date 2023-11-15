package org.alax.ast

import java.nio.file.Path
import org.alax.ast.base.Source.Unit
import org.alax.ast.base.Statement
import org.alax.ast.base.statements.Declaration
import org.alax.ast.Value
// For further analysis -> I think object for example in scala/ceylon is actually a top level static Class
object Source {




  object Units {

    case class Project(override val path: Path) extends Unit(path = path)

    case class Module(override val path: Path) extends Unit(path = path) {

    }

    case class Package(
                        override val path: Path,
                        imports: Seq[Import.Declaration],
                        members: Seq[Package.Member]
                      )
      extends Unit(path = path);
    object Package {
      type Member = Value.Definition
    }
    case class Class(
                      override val path: Path,
                      imports: Seq[Import.Declaration],
                      members: Seq[Class.Member]
                    ) extends Unit(path = path);

    object Class {
      type Member = Value.Definition
    }
    case class Interface(
                          override val path: Path,
                          imports: Seq[Import.Declaration],
                          members: Seq[Interface.Member]
                        ) extends Unit(path = path);

    object Interface {
      type Member = Value.Definition
    }

  }


}