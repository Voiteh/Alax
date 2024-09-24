package test.org.alax.scala.compiler.transformation.ast.to.model.fixture

import org.alax.ast
import org.alax.scala.compiler
import org.alax.scala.compiler.base
import org.alax.scala.compiler.base.model.{Import, Trace, Type}
import org.alax.scala.compiler.model.{Literals, Value as CompilerValue, Package as PackageModel}
import org.alax.scala.compiler.transformation
import org.alax.scala.compiler.transformation.ast.to.model.Contexts
import os.Path

import java.nio.file.Path as JPath
import scala.annotation.targetName

object Model {

  object Trace {
    val `invalid trace` = new Trace(
      unit = "",
      startLine = -1,
      endLine = -1,
      startIndex = -1,
      endIndex = -1
    )
    val `empty trace` = new Trace(
      unit = "",
      startLine = 0,
      endLine = 0,
      startIndex = 0,
      endIndex = 0
    )
  }

  object Expression {
    object Literal {
      val `1`: compiler.model.Literals.Integer = compiler.model.Literals.Integer(1)
      val `"str"`: compiler.model.Literals.String = compiler.model.Literals.String("str")
    }
  }

  object Context {


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

    val `package bleh` = Contexts.Package(
      imports = Seq.empty,
      identifier = "bleh"
    )
    val `package with import = scala.lang.Integer` = Contexts.Package(
      identifier = "with import",
      imports = Seq(Import.`scala.lang.Integer`)
    )
    val `package empty` = Contexts.Package(identifier = "empty", imports = Seq.empty)
    val `package with import and alias`: Contexts.Package = Contexts.Package(
      identifier = "with import and alias",
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
      val `package abc` = compiler.model.Package.Reference(Seq("abc"))
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
        val `java.lang.Integer param` = compiler.model.Routine.Declaration.Parameter(
          identifier = "param",
          typeReference = compiler.model.Value.Type.Reference(
            packageReference = compiler.model.Package.Reference(Seq("java", "lang")),
            id = compiler.base.model.Type.Id("Integer")
          )
        )
      }

      val `procedure bleh()` = compiler.model.Procedure.Declaration(
        identifier = Identifier.bleh
      )

      val `function java.lang.Integer bleh()` = compiler.model.Function.Declaration(
        identifier = Identifier.bleh,
        returnType = compiler.model.Value.Type.Reference(
          packageReference = compiler.model.Package.Reference(Seq("java", "lang")),
          id = compiler.base.model.Type.Id("Integer")

        )
      )
      val `procedure bleh(java.lang.Integer param)` = compiler.model.Procedure.Declaration(
        identifier = Identifier.bleh,
        parameters = Seq(
          Parameter.`java.lang.Integer param`
        )
      )

    }
  }

  object Routine {
    object Reference {
      val `abc.bleh` = compiler.model.Evaluable.Reference(
        identifier = "bleh",
        `package` = Package.Reference.`package abc`
      )
    }

    object Positional {

      object Call {
        object Argument {
          val `1`: compiler.model.Routine.Positional.Call.Argument = compiler.model.Routine.Positional.Call.Argument(
            expression = Model.Expression.Literal.`1`
          )
          val `"str"`: compiler.model.Routine.Positional.Call.Argument = compiler.model.Routine.Positional.Call.Argument(
            expression = Model.Expression.Literal.`"str"`
          )
        }

        val `abc.bleh()` = compiler.model.Routine.Positional.Call(
          reference = Routine.Reference.`abc.bleh`,
        )
        val `abc.bleh(1,"str")` = compiler.model.Routine.Positional.Call(
          reference = Routine.Reference.`abc.bleh`,
          arguments = Seq(Argument.`1`, Argument.`"str"`)
        )
      }
    }

    object Named {
      object Call {
        object Argument {
          val `int=1`: compiler.model.Routine.Named.Call.Argument = compiler.model.Routine.Named.Call.Argument(
            identifier = "int",
            expression = Model.Expression.Literal.`1`
          )
          val `str="str"`: compiler.model.Routine.Named.Call.Argument = compiler.model.Routine.Named.Call.Argument(
            identifier = "str",
            expression = Model.Expression.Literal.`"str"`
          )
        }

        val `abc.bleh(int=1,str="str")` = compiler.model.Routine.Named.Call(
          reference = Routine.Reference.`abc.bleh`,
          arguments = Set(Argument.`int=1`, Argument.`str="str"`)
        )


      }
    }


    object Declaration {
      object Identifier {
        val bleh = "bleh";
      }

      object Parameter {
        val `java.lang.Integer param` = compiler.model.Routine.Declaration.Parameter(
          identifier = "param",
          typeReference = compiler.model.Value.Type.Reference(
            packageReference = compiler.model.Package.Reference(Seq("java", "lang")),
            id = compiler.base.model.Type.Id("Integer")
          )
        )
      }
    }
  }

  object Procedure {


    object Definition {

    }

    object Declaration {


      val `procedure bleh()` = compiler.model.Procedure.Declaration(
        identifier = Routine.Declaration.Identifier.bleh
      )

      val `procedure bleh(java.lang.Integer param)` = compiler.model.Procedure.Declaration(
        identifier = Routine.Declaration.Identifier.bleh,
        parameters = Seq(
          Routine.Declaration.Parameter.`java.lang.Integer param`
        )
      )

    }
  }
}
