package test.org.alax.scala.complier.transformation.ast.to.model.fixture
import org.alax.ast
import org.alax.ast.{Literals, base}
import org.alax.ast.Value.Declaration as ValueDeclaration
import org.alax.ast.Value.Definition as ValueDefinition
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
        name = ast.partial.Names.LowerCase("int"),
        typeReference = ast.Value.Type.Reference(
          id = ast.partial.Names.UpperCase("Integer"),
        ),
        initialization = Literals.Integer(4),
      )
      val `String text = "text";`: ValueDefinition = ValueDefinition(
        name = ast.partial.Names.LowerCase("text"),
        typeReference =  ast.Value.Type.Reference(
          id = ast.partial.Names.UpperCase("String"),
        ),
        initialization = Literals.String("text"),
      )
    }

    object Declaration {
      val `Integer int`: ValueDeclaration = ValueDeclaration(
        name = ast.partial.Names.LowerCase(
          value = "int",
        ),
        typeReference =  ast.Value.Type.Reference(
          id = ast.partial.Names.UpperCase("Integer"),
        ),
      )
      val `String str`: ValueDeclaration = ValueDeclaration(
        name = ast.partial.Names.LowerCase(
          value = "int",
        ),
        typeReference =  ast.Value.Type.Reference(
          id = ast.partial.Names.UpperCase("Integer"),
        ),
      )

    }
  }


  object Import {
    val `scala.lang.String`: ast.Imports.Simple =ast.Imports.Simple(
      member = ast.partial.Names.Qualified(
        qualifications = Seq(
          ast.partial.Names.LowerCase(
            value = "scala",
          ),
          ast.partial.Names.LowerCase(
            value = "lang",
          ),
          ast.partial.Names.UpperCase(value = "String")
        ),
      )
    )


    val `scala.lang.Integer as Bleh`: ast.Imports.Alias =ast.Imports.Alias(
      member = ast.partial.Names.Qualified(
        qualifications = Seq(
          ast.partial.Names.LowerCase(
            value = "scala",
          ),
          ast.partial.Names.LowerCase(
            value = "lang",
          ),
          ast.partial.Names.UpperCase("Integer")

        )
      ),
      alias = ast.partial.Names.UpperCase("Bleh"),
    )


    val `scala.lang [ String, Integer as Bleh ]`: ast.Imports.Nested =ast.Imports.Nested(
      nest = ast.partial.Names.Qualified(
        qualifications = Seq(
          ast.partial.Names.LowerCase("scala"),
          ast.partial.Names.LowerCase("lang")
        ),
      ),
      nestee = Seq(
       ast.Imports.Simple(
          member = ast.partial.Names.UpperCase("String")
        ),
       ast.Imports.Alias(
          member = ast.partial.Names.UpperCase("Integer"),
          alias = ast.partial.Names.UpperCase("Bleh"),
        ),
      )
    )
    val `scala. [ lang.String, lang.[Integer as Bleh] ]`: ast.Imports.Nested =ast.Imports.Nested(
      nest = ast.partial.Names.Qualified(
        qualifications = Seq(
          ast.partial.Names.LowerCase("scala")
        ),
      ),
      nestee = Seq(
       ast.Imports.Nested(
          nest = ast.partial.Names.Qualified(
            qualifications = Seq(
              ast.partial.Names.LowerCase("lang")
            ),
          ),
          nestee = Seq(
           ast.Imports.Alias(
              member = ast.partial.Names.Qualified(
                qualifications = Seq(
                  ast.partial.Names.UpperCase("Integer")
                ),
              ),
              alias = ast.partial.Names.UpperCase("Bleh")
            )
          )
        )
      )
    )
  }
}
