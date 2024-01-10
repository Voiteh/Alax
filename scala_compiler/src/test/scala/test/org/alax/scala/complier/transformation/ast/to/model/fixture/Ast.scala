package test.org.alax.scala.complier.transformation.ast.to.model.fixture

import org.alax.ast
import org.alax.ast.{Literals, base}
import org.alax.ast.Value.Declaration as ValueDeclaration
import org.alax.ast.Value.Definition as ValueDefinition
import org.alax.ast.Package.Declaration as PackageDeclaration
import org.alax.ast.Package.Definition as PackageDefinition
import org.alax.ast.Package.Body as PackageBody
import org.alax.ast.Module.Declaration as ModuleDeclaration
import org.alax.ast.Module.Definition as ModuleDefinition
import org.alax.ast.Module.Body as ModuleBody
import org.alax.ast.base.Node.Metadata
import org.alax.scala.compiler
import org.alax.scala.compiler.base.model.{Import, Trace}
import org.alax.scala.compiler.transformation
import os.Path
import org.alax.ast.partial.Names

import java.nio.file.Path as JPath
import scala.annotation.targetName

object Ast {

  object Module {
    object Declaration {
      val `module abc.def`: ModuleDeclaration = ModuleDeclaration(
        name = Names.Qualified.LowerCase(qualifications = Seq(
          Names.LowerCase("abc"),
          Names.LowerCase("def")
        ), metadata = Metadata.unknown), metadata = Metadata.unknown
      )
    }

    object Definition {
      val `module abc.def { Integer int = 4;}`: ModuleDefinition = ModuleDefinition(
        name = Names.Qualified.LowerCase(qualifications = Seq(
          Names.LowerCase("abc"),
          Names.LowerCase("def")
        ), metadata = Metadata.unknown), metadata = Metadata.unknown,
        body = ModuleBody(
          elements = Seq(Ast.Value.Definition.`Integer int = 4;`),
          metadata = Metadata.unknown,
        )
      )
    }
  }

  object Package {
    object Declaration {
      val `package abc`: PackageDeclaration = PackageDeclaration(
        name = Names.LowerCase(value = "abc", metadata = Metadata.unknown), metadata = Metadata.unknown
      )
    }

    object Definition {
      val `package abc { Integer int = 4;}`: PackageDefinition = PackageDefinition(
        name = Names.LowerCase(value = "abc", metadata = Metadata.unknown), metadata = Metadata.unknown,
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
        name = ast.partial.Names.LowerCase("int"),
        typeReference = ast.Value.Type.Reference(
          id = ast.partial.Names.UpperCase("Integer"),
        ),
        initialization = Literals.Integer(4),
      )
      val `String text = "text";`: ValueDefinition = ValueDefinition(
        name = ast.partial.Names.LowerCase("text"),
        typeReference = ast.Value.Type.Reference(
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
        typeReference = ast.Value.Type.Reference(
          id = ast.partial.Names.UpperCase("Integer"),
        ),
      )
      val `String str`: ValueDeclaration = ValueDeclaration(
        name = ast.partial.Names.LowerCase(
          value = "int",
        ),
        typeReference = ast.Value.Type.Reference(
          id = ast.partial.Names.UpperCase("Integer"),
        ),
      )

    }
  }


  object Import {
    val `scala.lang.String`: ast.Imports.Simple = ast.Imports.Simple(
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


    val `scala.lang.Integer as Bleh`: ast.Imports.Alias = ast.Imports.Alias(
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


    val `scala.lang [ String, Integer as Bleh ]`: ast.Imports.Nested = ast.Imports.Nested(
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
    val `scala. [ lang.String, lang.[Integer as Bleh] ]`: ast.Imports.Nested = ast.Imports.Nested(
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
