package org.alax.scala.compiler.transformation.model.to.file

import org.alax.scala.compiler.transformation.Context
import org.alax.scala.compiler.model.Package.Declaration as PackageDeclaration
import org.alax.scala.compiler.model.Module.Declaration as ModuleDeclaration

object Contexts {

  case class Module(
                     declaration: ModuleDeclaration,
                   ) extends Context(null) {

  }

  case class Package(
                      declaration: PackageDeclaration,
                      override val parent: Package | Module ,
                    ) extends Context(parent) {

  }
}
