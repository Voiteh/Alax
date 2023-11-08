package org.alax.scala.compiler.transformation.model.to.file

import org.alax.scala.compiler.base.model.{CompilationError, CompilerBugException, CompilerError}
import org.alax.scala.compiler.model.Sources

import scala.meta.inputs.Input.File
import org.alax.scala.compiler.transformation.Context

import java.nio.charset.Charset
import java.nio.file.Path

class CompilerSourceToScalaUnitsFileTransformer(charset: Charset) {


  object transform {

    def path(path: Path, parentContext: Context | Null): os.Path | CompilerError = {
      if (path.isAbsolute) {
        return os.Path(path);
      }
      return parentContext match {
        case context: Context => transform.path(path = context.path, parentContext = context.parent) match {
          //It should be absolute as now
          case parentPath: os.Path => os.Path(Path.of(parentPath.toString(), path.toString))
          case error: CompilerError => error
        }
        case null => CompilerBugException(new Exception(s"Couldn't resolve path: $path, to absolute "))
      }
    }

    def unit(source: Sources.Unit, context: Context): Seq[File | CompilationError] = {
      ???
    }

    def `package`(source: Sources.Package, context: Context): Seq[File | CompilationError] = {
      ???
    }

  }


}
