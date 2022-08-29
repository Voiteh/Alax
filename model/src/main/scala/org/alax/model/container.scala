package org.alax.model

import org.alax.model.base.Statement;

/**
 * Basically a file contains code
 *
 * @param statements
 */
class CompilationUnit(val statements: Seq[Statement])

/**
 * Package of files containg code and other packages
 *
 * @param children
 */
class Package(val children: Seq[CompilationUnit | Package])

/**
 *
 * @param descriptor - Describes how module should interact with language services and other modules
 * @param children   - Packages contained in module, at least one package must be present.
 */
class Module(val descriptor: Module.Descriptor, val children: Seq[Package])

object Module {
  /**
   * Describes module interactions with language service and other Modules
   */
  class Descriptor();
}

class Project(val descriptor: Project.Descriptor, val children: Seq[Module]);

object Project {
  /**
   * Describes common configurations for all included modules
   */
  class Descriptor();
}