package org.alax.scala.compiler.model

import org.alax.scala.compiler.model.Statement
import java.nio.file.Path;


/**
 * Language construct that provides scoping and encapsulation
 */
abstract class Source[S >: Statement] {
  val statements: Seq[S]
  val path: Path;
}


object Source {

  //TODO reduce Statements
  case class CompilationUnit(override val statements: Seq[Statement], path: Path) extends Source[Statement]

  //TODO reduce Statements
  case class Package(override val statements: Seq[Statement], path: Path) extends Source[Statement]

  //TODO reduce Statements
  case class Module(override val statements: Seq[Statement], path: Path) extends Source[Statement]


  //TODO reduce Statements
  case class Project(statements: Seq[Statement], path: Path) extends Source[Statement];


}