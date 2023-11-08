package test.org.alax.scala.complier.transformation.ast.to.model.fixture
import org.alax.ast
import org.alax.ast.{Literals, base}
import org.alax.ast.base.model
import org.alax.ast.base.model.Statement.Declaration.Value as ValueDeclaration
import org.alax.ast.base.model.Statement.Definition.Value as ValueDefinition
import org.alax.scala.compiler
import org.alax.scala.compiler.base.model.{Import, Trace}
import org.alax.scala.compiler.transformation
import org.alax.scala.compiler.transformation.Context.Module
import os.Path

import java.nio.file.Path as JPath
import scala.annotation.targetName
object Ast {

  object Value {

    object Definition {
      val `Integer int = 4;`: ValueDefinition = ValueDefinition(
        name = model.Partial.Name.LowerCase("int"),
        typeReference = base.model.Partial.Type.Reference.Value(
          id = base.model.Partial.Name.UpperCase("Integer"),
        ),
        initialization = Literals.Integer(4),
      )
      val `String text = "text";`: ValueDefinition = ValueDefinition(
        name = base.model.Partial.Name.LowerCase("text"),
        typeReference = base.model.Partial.Type.Reference.Value(
          id = base.model.Partial.Name.UpperCase("String"),
        ),
        initialization = Literals.String("text"),
      )
    }

    object Declaration {
      val `Integer int`: ValueDeclaration = ValueDeclaration(
        name = base.model.Partial.Name.LowerCase(
          value = "int",
        ),
        typeReference = base.model.Partial.Type.Reference.Value(
          id = base.model.Partial.Name.UpperCase("Integer"),
        ),
      )
      val `String str`: ValueDeclaration = ValueDeclaration(
        name = base.model.Partial.Name.LowerCase(
          value = "int",
        ),
        typeReference = base.model.Partial.Type.Reference.Value(
          id = base.model.Partial.Name.UpperCase("Integer"),
        ),
      )

    }
  }

  object Source {
    object Package {
      val `empty package` = ast.Source.Units.Package(path = JPath.of(""), imports = Seq(), members = Seq())
      val `simple package with Integer value` = ast.Source.Units.Package(path = JPath.of(""),
        imports = Seq(Import.`scala.lang.String`),
        members = Seq(Value.Definition.`Integer int = 4;`)
      )


    }
  }

  object Import {
    val `scala.lang.String`: model.Statement.Declaration.Import.Simple = base.model.Statement.Declaration.Import.Simple(
      member = base.model.Partial.Name.Qualified(
        qualifications = Seq(
          base.model.Partial.Name.LowerCase(
            value = "scala",
          ),
          base.model.Partial.Name.LowerCase(
            value = "lang",
          ),
          base.model.Partial.Name.UpperCase(value = "String")
        ),
      )
    )


    val `scala.lang.Integer as Bleh`: model.Statement.Declaration.Import.Alias = base.model.Statement.Declaration.Import.Alias(
      member = base.model.Partial.Name.Qualified(
        qualifications = Seq(
          base.model.Partial.Name.LowerCase(
            value = "scala",
          ),
          base.model.Partial.Name.LowerCase(
            value = "lang",
          ),
          base.model.Partial.Name.UpperCase("Integer")

        )
      ),
      alias = base.model.Partial.Name.UpperCase("Bleh"),
    )


    val `scala.lang [ String, Integer as Bleh ]`: model.Statement.Declaration.Import.Nested = base.model.Statement.Declaration.Import.Nested(
      nest = base.model.Partial.Name.Qualified(
        qualifications = Seq(
          base.model.Partial.Name.LowerCase("scala"),
          base.model.Partial.Name.LowerCase("lang")
        ),
      ),
      nestee = Seq(
        base.model.Statement.Declaration.Import.Simple(
          member = base.model.Partial.Name.UpperCase("String")
        ),
        base.model.Statement.Declaration.Import.Alias(
          member = base.model.Partial.Name.UpperCase("Integer"),
          alias = base.model.Partial.Name.UpperCase("Bleh"),
        ),
      )
    )
    val `scala. [ lang.String, lang.[Integer as Bleh] ]`: model.Statement.Declaration.Import.Nested = base.model.Statement.Declaration.Import.Nested(
      nest = base.model.Partial.Name.Qualified(
        qualifications = Seq(
          base.model.Partial.Name.LowerCase("scala")
        ),
      ),
      nestee = Seq(
        base.model.Statement.Declaration.Import.Nested(
          nest = base.model.Partial.Name.Qualified(
            qualifications = Seq(
              base.model.Partial.Name.LowerCase("lang")
            ),
          ),
          nestee = Seq(
            base.model.Statement.Declaration.Import.Alias(
              member = base.model.Partial.Name.Qualified(
                qualifications = Seq(
                  base.model.Partial.Name.UpperCase("Integer")
                ),
              ),
              alias = base.model.Partial.Name.UpperCase("Bleh")
            )
          )
        )
      )
    )
  }
}
