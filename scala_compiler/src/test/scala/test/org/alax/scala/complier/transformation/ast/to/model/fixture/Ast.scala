package test.org.alax.scala.complier.transformation.ast.to.model.fixture
import org.alax.ast
import org.alax.ast.model.Statement.Declaration.Value as ValueDeclaration
import org.alax.ast.model.Statement.Definition.Value as ValueDefinition
import org.alax.scala.compiler
import org.alax.scala.compiler.model.{Import, Trace}
import org.alax.scala.compiler.transformation
import org.alax.scala.compiler.transformation.Context.Module
import os.Path

import java.nio.file.Path as JPath
import scala.annotation.targetName
object Ast {

  object Value {

    object Definition {
      val `Integer int = 4;`: ValueDefinition = ValueDefinition(
        name = ast.model.Partial.Name.LowerCase("int"),
        typeReference = ast.model.Partial.Type.Reference.Value(
          id = ast.model.Partial.Name.UpperCase("Integer"),
        ),
        initialization = ast.model.Expression.Literal.Integer(4),
      )
      val `String text = "text";`: ValueDefinition = ValueDefinition(
        name = ast.model.Partial.Name.LowerCase("text"),
        typeReference = ast.model.Partial.Type.Reference.Value(
          id = ast.model.Partial.Name.UpperCase("String"),
        ),
        initialization = ast.model.Expression.Literal.String("text"),
      )
    }

    object Declaration {
      val `Integer int`: ValueDeclaration = ValueDeclaration(
        name = ast.model.Partial.Name.LowerCase(
          value = "int",
        ),
        typeReference = ast.model.Partial.Type.Reference.Value(
          id = ast.model.Partial.Name.UpperCase("Integer"),
        ),
      )
      val `String str`: ValueDeclaration = ValueDeclaration(
        name = ast.model.Partial.Name.LowerCase(
          value = "int",
        ),
        typeReference = ast.model.Partial.Type.Reference.Value(
          id = ast.model.Partial.Name.UpperCase("Integer"),
        ),
      )

    }
  }

  object Source {
    object Package {
      val `empty package` = ast.Source.Unit.Package(path = JPath.of(""), imports = Seq(), members = Seq())
      val `simple package with Integer value` = ast.Source.Unit.Package(path = JPath.of(""),
        imports = Seq(Import.`scala.lang.String`),
        members = Seq(Value.Definition.`Integer int = 4;`)
      )


    }
  }

  object Import {
    val `scala.lang.String`: ast.model.Statement.Declaration.Import.Simple = ast.model.Statement.Declaration.Import.Simple(
      member = ast.model.Partial.Name.Qualified(
        qualifications = Seq(
          ast.model.Partial.Name.LowerCase(
            value = "scala",
          ),
          ast.model.Partial.Name.LowerCase(
            value = "lang",
          ),
          ast.model.Partial.Name.UpperCase(value = "String")
        ),
      )
    )


    val `scala.lang.Integer as Bleh`: ast.model.Statement.Declaration.Import.Alias = ast.model.Statement.Declaration.Import.Alias(
      member = ast.model.Partial.Name.Qualified(
        qualifications = Seq(
          ast.model.Partial.Name.LowerCase(
            value = "scala",
          ),
          ast.model.Partial.Name.LowerCase(
            value = "lang",
          ),
          ast.model.Partial.Name.UpperCase("Integer")

        )
      ),
      alias = ast.model.Partial.Name.UpperCase("Bleh"),
    )


    val `scala.lang [ String, Integer as Bleh ]`: ast.model.Statement.Declaration.Import.Nested = ast.model.Statement.Declaration.Import.Nested(
      nest = ast.model.Partial.Name.Qualified(
        qualifications = Seq(
          ast.model.Partial.Name.LowerCase("scala"),
          ast.model.Partial.Name.LowerCase("lang")
        ),
      ),
      nestee = Seq(
        ast.model.Statement.Declaration.Import.Simple(
          member = ast.model.Partial.Name.UpperCase("String")
        ),
        ast.model.Statement.Declaration.Import.Alias(
          member = ast.model.Partial.Name.UpperCase("Integer"),
          alias = ast.model.Partial.Name.UpperCase("Bleh"),
        ),
      )
    )
    val `scala. [ lang.String, lang.[Integer as Bleh] ]`: ast.model.Statement.Declaration.Import.Nested = ast.model.Statement.Declaration.Import.Nested(
      nest = ast.model.Partial.Name.Qualified(
        qualifications = Seq(
          ast.model.Partial.Name.LowerCase("scala")
        ),
      ),
      nestee = Seq(
        ast.model.Statement.Declaration.Import.Nested(
          nest = ast.model.Partial.Name.Qualified(
            qualifications = Seq(
              ast.model.Partial.Name.LowerCase("lang")
            ),
          ),
          nestee = Seq(
            ast.model.Statement.Declaration.Import.Alias(
              member = ast.model.Partial.Name.Qualified(
                qualifications = Seq(
                  ast.model.Partial.Name.UpperCase("Integer")
                ),
              ),
              alias = ast.model.Partial.Name.UpperCase("Bleh")
            )
          )
        )
      )
    )
  }
}
