package org.alax.scala.compiler.model

import org.alax.scala.compiler.model.Statement
import java.nio.file.Path;


/**
 * Language construct that provides scoping and encapsulation
 */
abstract class Container


object Container {

  /**
   * Basically a file contains code
   *
   * @param statements
   */
  class CompilationUnit(val statements: Seq[Statement], val path: Path) extends Container

  /**
   * Package of files containg code and other packages
   *
   * @param children
   */
  class Package(val children: Seq[CompilationUnit | Package], val path: Path) extends Container

  /**
   *
   * @param descriptor - Describes how module should interact with language services and other modules
   * @param children   - Packages contained in module, at least one package must be present.
   */
  class Module(val descriptor: Module.Descriptor, val children: Seq[Package], val path: Path) extends Container

  object Module {
    /**
     * Describes module interactions with language service and other Modules
     */
    class Descriptor();
  }

  class Project(val descriptor: Project.Descriptor, val children: Seq[Module], val path: Path) extends Container;

  object Project {
    /**
     * Describes common configurations for all included modules
     */
    class Descriptor();
  }
}