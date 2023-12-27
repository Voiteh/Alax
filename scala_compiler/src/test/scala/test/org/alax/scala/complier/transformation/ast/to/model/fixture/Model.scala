package test.org.alax.scala.complier.transformation.ast.to.model.fixture

import org.alax.ast
import org.alax.scala.compiler
import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.base.model.{Import, Trace, Type}
import org.alax.scala.compiler.model.{Contexts, Literals, Value as CompilerValue}
import org.alax.scala.compiler.transformation
import os.Path

import java.nio.file.Path as JPath
import scala.annotation.targetName

object Model {


  object Context {


    object Trace {
      val `empty trace` = new Trace(unit = "", lineNumber = 0, startIndex = 0, endIndex = 0)
    }

    object Import {
      val `scala.lang.Integer`: Import = compiler.base.model.Import(
        ancestor = "scala.lang",
        member = "Integer"
      );
      val `java.lang.Integer as JInteger`: Import = compiler.base.model.Import(
        ancestor = "java.lang",
        member = "Integer", alias = "JInteger"
      );
      val `scala.lang.Integer as JInteger`: Import = compiler.base.model.Import(
        ancestor = "scala.lang",
        member = "Integer", alias = "JInteger"
      );
    }
    object Imports {

      val `duplicate member imports` = Seq(
        new Import(ancestor = "alax", member = "One", alias = null),
        new Import(ancestor = "other", member = "One", alias = null)

      )

      val `duplicate main ancestor imports` = Seq(
        new Import(ancestor = "alax", member = "One", alias = null),
        new Import(ancestor = "alax", member = "Two", alias = null)

      )
      val `duplicate nested ancestor imports` = Seq(
        new Import(ancestor = "alax.something", member = "One", alias = null),
        new Import(ancestor = "alax.something", member = "Two", alias = null)

      )
      val `duplicate nested with different top ancestor imports` = Seq(
        new Import(ancestor = "java.something", member = "One", alias = null),
        new Import(ancestor = "alax.something", member = "Two", alias = null)

      )
      val `non duplicating imports ` = Seq(
        new Import(ancestor = "different.something", member = "Foo", alias = null),
        new Import(ancestor = "something.different", member = "Foo", alias = "Bar")
      )
    }

    val `package` = Contexts.Unit(imports = Seq.empty)
    val `package with import = scala.lang.Integer` = Contexts.Unit(imports = Seq(Import.`scala.lang.Integer`))
    val unit = Contexts.Unit(imports = Seq.empty)
    val `unit with import`: Contexts.Unit = new Contexts.Unit(imports = Seq(Import.`scala.lang.Integer`))
    val `unit with import and alias`: Contexts.Unit = Contexts.Unit(
      imports =
        Seq(
          Import.`scala.lang.Integer`,
          Import.`java.lang.Integer as JInteger`
        ))
  }

  object Value {


    object Declaration {
      val `int: scala.lang.Integer` = compiler.model.Value.Declaration(
        name = "int",
        `type` = compiler.model.Value.Type.Reference(
          id = Type.Id(
            value = "scala.lang.Integer"
          )
        )
      )
    }

    object Definition {
      val `val int: scala.lang.Integer = 4` = compiler.model.Value.Definition(
        declaration = Declaration.`int: scala.lang.Integer`,
        meaning = Literals.Integer(4L)
      )
    }

  }

  object Package {
    object Declaration {
      val `package abc` = compiler.model.Package.Declaration(
        name = "abc"
      )
    }

    object Definition {
      val `package abc { int: scala.lang.Integer; }` = compiler.model.Package.Definition(
        declaration = Declaration.`package abc`,
        body = compiler.model.Package.Definition.Body(elements = Seq(
          Model.Value.Definition.`val int: scala.lang.Integer = 4`
        ))
      )
    }
  }


}
