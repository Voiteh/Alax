package org.alax.scala.compiler.transformation.model.to.file

import org.alax.scala.compiler.base.model.Virtual
import org.alax.scala.compiler.model.{Package, Module}
import org.apache.commons.io.IOUtils

import java.io.{File, PrintWriter}
import java.nio.file.{Files, Path, Paths}
import meta.prettyprinters.XtensionSyntax
import scala.util.Try

import org.apache.commons.io.FileUtils;

class ModelToFileTransformer(basePath: Path) {

  private object resolve {
    def path(context: Contexts.Package | Contexts.Module): Path = {
      context match {
        case packageContext: Contexts.Package => path(packageContext.parent).resolve(packageContext.declaration.identifier)
        case moduleContext: Contexts.Module => moduleContext.declaration.identifier.split("\\.")
          .foldLeft(basePath)((acc: Path, item: String) => acc.resolve(item))
      }
    }
  }


  object transform {
    def `package`(definition: Package.Definition, context: Contexts.Package | Contexts.Module): Virtual[File] = Virtual(
      () => {
        val path = resolve.path(context).resolve(definition.declaration.identifier).resolve("package.scala")
        val file: File = path.toFile;
        FileUtils.createParentDirectories(path.toFile);
        if (!file.exists()) then assert(file.createNewFile())
        assert(file.canWrite)
        val writer = new PrintWriter(file);
        //FIXME this will produce unusable package because its declaration is not fully qualified but contain name
        // Of given package only
        writer.write(definition.scala.syntax);
        writer.close();
        file
      });

    def module(definition: Module.Definition, context: Contexts.Project | Null = null): Virtual[File] = Virtual(
      () => {
        val moduleContext: Contexts.Module = Contexts.Module(declaration = definition.declaration, parent = context)
        val path = resolve.path(moduleContext).resolve("module.scala")
        val file: File = path.toFile;
        FileUtils.createParentDirectories(path.toFile);
        if (!file.exists()) then assert(file.createNewFile())
        assert(file.canWrite)
        val writer = new PrintWriter(file);
        writer.write(definition.scala.syntax);
        writer.close();
        file
      }
    )
  }


}
