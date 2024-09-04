package test.org.alax.scala.complier.transformation.ast.to.model.fixture

import org.alax.ast
import org.alax.scala.compiler
import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.base.model.{Import, Trace, Type}
import org.alax.scala.compiler.model.{Literals, Value as CompilerValue, Package as PackageModel}
import org.alax.scala.compiler.transformation
import org.alax.scala.compiler.transformation.ast.to.model.Contexts
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
        ancestor = PackageModel.Reference(Seq("scala", "lang")),
        member = "Integer"
      );
      val `java.lang.Integer as JInteger`: Import = compiler.base.model.Import(
        ancestor = PackageModel.Reference(Seq("java", "lang")),
        member = "Integer", alias = "JInteger"
      );
      val `scala.lang.Integer as JInteger`: Import = compiler.base.model.Import(
        ancestor = PackageModel.Reference(Seq("scala", "lang")),
        member = "Integer", alias = "JInteger"
      );
    }

    object Imports {

      val `duplicate member imports` = Seq(
        new Import(ancestor = PackageModel.Reference(Seq("alax")), member = "One", alias = null),
        new Import(ancestor = PackageModel.Reference(Seq("other")), member = "One", alias = null)

      )

      val `duplicate main ancestor imports` = Seq(
        new Import(ancestor = PackageModel.Reference(Seq("alax")), member = "One", alias = null),
        new Import(ancestor = PackageModel.Reference(Seq("alax")), member = "Two", alias = null)

      )
      val `duplicate nested ancestor imports` = Seq(
        new Import(ancestor = PackageModel.Reference(Seq("alax", "something")), member = "One", alias = null),
        new Import(ancestor = PackageModel.Reference(Seq("alax", "something")), member = "Two", alias = null)

      )
      val `duplicate nested with different top ancestor imports` = Seq(
        new Import(ancestor = PackageModel.Reference(Seq("java", "something")), member = "One", alias = null),
        new Import(ancestor = PackageModel.Reference(Seq("alax", "something")), member = "Two", alias = null)

      )
      val `non duplicating imports ` = Seq(
        new Import(ancestor = PackageModel.Reference(Seq("different", "something")), member = "Foo", alias = null),
        new Import(ancestor = PackageModel.Reference(Seq("something", "different")), member = "Foo", alias = "Bar")
      )
    }

    val `package` = Contexts.Unit(imports = Seq.empty)
    val `package with import = scala.lang.Integer` = Contexts.Unit(imports = Seq(Import.`scala.lang.Integer`))
    val unit = Contexts.Unit(imports = Seq.empty)
    val `unit with import`: Contexts.Unit = Contexts.Unit(imports = Seq(Import.`scala.lang.Integer`))
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
        identifier = "int",
        typeReference = compiler.model.Value.Type.Reference(
          packageReference = Package.Reference.`package scala.lang`,
          id = Type.Id(
            value = "Integer"
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
    object Reference {
      val `package scala.lang` = compiler.model.Package.Reference(Seq("scala", "lang"))
    }

    object Declaration {
      val `package abc` = compiler.model.Package.Declaration(
        identifier = "abc"
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


  object Module {
    object Declaration {
      val `module abc.def` = compiler.model.Module.Declaration(
        identifier = "abc.def"
      )
    }

    object Definition {
      val `module abc.def { int: scala.lang.Integer; }` = compiler.model.Module.Definition(
        declaration = Declaration.`module abc.def`,
        body = compiler.model.Module.Definition.Body(elements = Seq(
          Model.Value.Definition.`val int: scala.lang.Integer = 4`
        ))
      )
    }
  }

  object Function {


    object Definition {

    }

    object Declaration {

      object Identifier {
        val bleh = "bleh";
      }

      object Parameter {
          val `java.lang.Integer param` = compiler.model.Function.Declaration.Parameter(
            identifier = "param",
            typeReference = compiler.model.Value.Type.Reference(
              packageReference = compiler.model.Package.Reference(Seq("java", "lang")),
              id = compiler.base.model.Type.Id("Integer")
            )
          )
      }

      val `function bleh()` = compiler.model.Function.Declaration(
        identifier = Identifier.bleh
      )

      val `function java.lang.Integer bleh()` = compiler.model.Function.Declaration(
        identifier = Identifier.bleh,
        returnType = compiler.model.Value.Type.Reference(
          packageReference = compiler.model.Package.Reference(Seq("java", "lang")),
          id = compiler.base.model.Type.Id("Integer")

        )
      )
      val `function bleh(java.lang.Integer param)` = compiler.model.Function.Declaration(
        identifier = Identifier.bleh,
        parameters = Seq(
          Parameter.`java.lang.Integer param`
        )
      )

    }
  }

}
