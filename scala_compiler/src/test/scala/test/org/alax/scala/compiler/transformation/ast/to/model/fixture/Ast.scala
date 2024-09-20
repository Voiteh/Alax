package test.org.alax.scala.compiler.transformation.ast.to.model.fixture

import org.alax.ast
import org.alax.ast.{Literals, base}
import org.alax.ast.Value.Declaration as ValueDeclaration
import org.alax.ast.Value.Definition as ValueDefinition
import org.alax.ast.Package.Declaration as PackageDeclaration
import org.alax.ast.Package.Definition as PackageDefinition
import org.alax.ast.Package.Body as PackageBody
import org.alax.ast.Package.Identifier as PackageName
import org.alax.ast.Module.Declaration as ModuleDeclaration
import org.alax.ast.Module.Definition as ModuleDefinition
import org.alax.ast.Module.Body as ModuleBody
import org.alax.ast.base.Node.Metadata
import org.alax.scala.compiler
import org.alax.scala.compiler.base.model.{Import, Trace}
import org.alax.scala.compiler.transformation
import os.Path

import java.nio.file.Path as JPath
import scala.annotation.targetName

object Ast {

  object Literal {
    object Expression {
      val `1` = ast.Literals.Integer(1)
      val `"str"` = ast.Literals.String("str")
    }
  }

  object Module {
    object Declaration {
      val `module abc.def`: ModuleDeclaration = ModuleDeclaration(
        identifier = ast.Module.Identifier(parts = Seq(
          ast.Identifier.LowerCase("abc"),
          ast.Identifier.LowerCase("def")
        ), metadata = Metadata.unknown), metadata = Metadata.unknown
      )
    }

    object Definition {
      val `module abc.def { Integer int = 4;}`: ModuleDefinition = ModuleDefinition(
        identifier = ast.Module.Identifier(parts = Seq(
          ast.Identifier.LowerCase("abc"),
          ast.Identifier.LowerCase("def")
        ), metadata = Metadata.unknown), metadata = Metadata.unknown,
        body = ModuleBody(
          elements = Seq(Ast.Value.Definition.`Integer int = 4;`),
          metadata = Metadata.unknown,
        )
      )
    }
  }

  object Package {
    object Reference {
      val `package abc` = ast.Package.Reference(
        parts = Seq(
          ast.Identifier.LowerCase("abc")
        ),
        metadata = Metadata.unknown
      )
    }

    object Declaration {
      val `package abc`: PackageDeclaration = PackageDeclaration(
        identifier = ast.Package.Identifier(value = "abc", metadata = Metadata.unknown), metadata = Metadata.unknown
      )
    }

    object Definition {
      val `package abc { Integer int = 4;}`: PackageDefinition = PackageDefinition(
        identifier = ast.Package.Identifier(value = "abc", metadata = Metadata.unknown), metadata = Metadata.unknown,
        body = PackageBody(
          elements = Seq(Ast.Value.Definition.`Integer int = 4;`),
          metadata = Metadata.unknown,
        )
      )
    }
  }

  object Value {

    object Definition {
      val `Integer int = 4;`: ValueDefinition = ValueDefinition(
        identifier = ast.Evaluable.Identifier(value = "int"),
        typeReference = ast.Value.Type.Reference(
          `package` = null,
          identifier = ast.Identifier.UpperCase("Integer")
        ),
        initialization = Literals.Integer(4),
      )
      val `String text = "text";`: ValueDefinition = ValueDefinition(
        identifier = ast.Evaluable.Identifier(value = "text"),
        typeReference = ast.Value.Type.Reference(
          `package` = null,
          identifier = ast.Identifier.UpperCase("String")
        ),
        initialization = Literals.String("text"),
      )
    }

    object Declaration {
      val `Integer int`: ValueDeclaration = ValueDeclaration(
        identifier = ast.Evaluable.Identifier(value = "int"),
        typeReference = ast.Value.Type.Reference(
          `package` = null,
          identifier = ast.Identifier.UpperCase("Integer")
        ),
      )
      val `String str`: ValueDeclaration = ValueDeclaration(
        identifier = ast.Evaluable.Identifier(value = "int"),
        typeReference = ast.Value.Type.Reference(
          `package` = null,
          identifier = ast.Identifier.UpperCase("Integer"),
        ),
      )

    }
  }

  object Routine {
    object Identifier {
      val bleh = ast.Evaluable.Identifier(value = "bleh")
    }

    object Reference {
      val `abc.bleh` = ast.Evaluable.Reference(
        container = Package.Reference.`package abc`,
        identifier = Routine.Identifier.bleh,
        metadata = Metadata.unknown
      )
    }

    object Call {
      object Positional {
        object Argument {
          val `1` = ast.Routine.Call.Positional.Argument(
            expression = Ast.Literal.Expression.`1`
          )
          val `"str"` = ast.Routine.Call.Positional.Argument(
            expression = Ast.Literal.Expression.`"str"`
          )
        }
      }

      object Named {
        object Argument {
          val `int=1` = ast.Routine.Call.Named.Argument(
            identifier= ast.Identifier.LowerCase("int"),
            expression = Ast.Literal.Expression.`1`
          )
          val `str="str"` = ast.Routine.Call.Named.Argument(
            identifier= ast.Identifier.LowerCase("str"),
            expression = Ast.Literal.Expression.`"str"`
          )
        }
      }

      object Expression {
        val `abc.bleh()` = ast.Routine.Call.Expression(
          routineReference = Reference.`abc.bleh`
        )
        val `abc.bleh(1,"str")` = ast.Routine.Call.Expression(
          routineReference = Reference.`abc.bleh`,
          arguments = Seq(Positional.Argument.`1`, Positional.Argument.`"str"`)
        )
        val `abc.bleh(int=1,str="str")` =ast.Routine.Call.Expression(
          routineReference = Reference.`abc.bleh`,
          arguments = Seq(Named.Argument.`int=1`, Named.Argument.`str="str"`)
        )
      }
    }
  }

  object Function {
    object Declaration {

      val `function Integer bleh()` = ast.Function.Declaration(
        identifier = Routine.Identifier.bleh,
        returnTypeReference = ast.Value.Type.Reference(
          identifier = ast.Identifier.UpperCase("Integer")
        )
      )


      object Parameter {
        object Identifier {
          val param = ast.Identifier.LowerCase("param")
        }

        val `Integer param` = ast.Routine.Declaration.Parameter(
          identifier = Identifier.param,
          `type` = ast.Value.Type.Reference(
            identifier = ast.Identifier.UpperCase("Integer")
          )
        )
      }
    }

    object Definition {

    }
  }

  object Procedure {
    object Declaration {

      val `procedure bleh()` = ast.Procedure.Declaration(
        identifier = Identifier.bleh,
      )
      val `procedure bleh(Integer param)` = ast.Procedure.Declaration(
        identifier = Identifier.bleh,
        parameters = Seq(Parameter.`Integer param`)
      )

      object Identifier {
        val bleh = ast.Evaluable.Identifier(value = "bleh")
      }

      object Parameter {
        object Identifier {
          val param = ast.Identifier.LowerCase("param")
        }

        val `Integer param` = ast.Routine.Declaration.Parameter(
          identifier = Identifier.param,
          `type` = ast.Value.Type.Reference(
            identifier = ast.Identifier.UpperCase("Integer")
          )
        )
      }
    }

    object Definition {

    }
  }

  object Import {
    val `scala.lang.String`: ast.Imports.Simple = ast.Imports.Simple(
      member = ast.Import.Identifier(
        parts = Seq(
          ast.Identifier(
            value = "scala",
          ),
          ast.Identifier(
            value = "lang",
          ),
          ast.Identifier(value = "String")
        ),
      )
    )


    val `scala.lang.Integer as Bleh`: ast.Imports.Alias = ast.Imports.Alias(
      member = ast.Import.Identifier(
        parts = Seq(
          ast.Identifier(
            value = "scala",
          ),
          ast.Identifier(
            value = "lang",
          ),
          ast.Identifier(value = "Integer")
        ),
      ),
      alias = ast.Import.Identifier(Seq(ast.Identifier("Bleh"))),
    )


    val `scala.lang [ String, Integer as Bleh ]`: ast.Imports.Nested = ast.Imports.Nested(
      nest = ast.Import.Identifier(
        parts = Seq(
          ast.Identifier("scala"),
          ast.Identifier("lang")
        ),
      ),
      nestee = Seq(
        ast.Imports.Simple(
          member = ast.Import.Identifier(Seq(ast.Identifier("String")))
        ),
        ast.Imports.Alias(
          member = ast.Import.Identifier(Seq(ast.Identifier("Integer"))),
          alias = ast.Import.Identifier(Seq(ast.Identifier("Bleh"))),
        ),
      )
    )
    val `scala. [ lang.String, lang.[Integer as Bleh] ]`: ast.Imports.Nested = ast.Imports.Nested(
      nest = ast.Import.Identifier(
        parts = Seq(
          ast.Identifier("scala")
        ),
      ),
      nestee = Seq(
        ast.Imports.Nested(
          nest = ast.Import.Identifier(
            parts = Seq(
              ast.Identifier("lang")
            ),
          ),
          nestee = Seq(
            ast.Imports.Alias(
              member = ast.Import.Identifier(
                parts = Seq(
                  ast.Identifier("Integer")
                ),
              ),
              alias = ast.Import.Identifier(parts = Seq(ast.Identifier("Bleh")))
            )
          )
        )
      )
    )
  }
}
