package test.org.alax.scala.complier.transformation.ast.to.model.fixture

import org.alax.ast
import org.alax.ast.{Literals, base}
import org.alax.ast.Value.Declaration as ValueDeclaration
import org.alax.ast.Value.Definition as ValueDefinition
import org.alax.ast.Package.Declaration as PackageDeclaration
import org.alax.ast.Package.Definition as PackageDefinition
import org.alax.ast.Package.Body as PackageBody
import org.alax.ast.Package.Name as PackageName
import org.alax.ast.Module.Declaration as ModuleDeclaration
import org.alax.ast.Module.Definition as ModuleDefinition
import org.alax.ast.Module.Body as ModuleBody
import org.alax.ast.base.Node.Metadata
import org.alax.scala.compiler
import org.alax.scala.compiler.base.model.{Import, Trace}
import org.alax.scala.compiler.transformation
import os.Path
import org.alax.ast.partial.Identifier

import java.nio.file.Path as JPath
import scala.annotation.targetName

object Ast {

  object Module {
    object Declaration {
      val `module abc.def`: ModuleDeclaration = ModuleDeclaration(
        name = Identifier.Qualified.LowerCase(qualifications = Seq(
          Identifier.LowerCase("abc"),
          Identifier.LowerCase("def")
        ), metadata = Metadata.unknown), metadata = Metadata.unknown
      )
    }

    object Definition {
      val `module abc.def { Integer int = 4;}`: ModuleDefinition = ModuleDefinition(
        name = Identifier.Qualified.LowerCase(qualifications = Seq(
          Identifier.LowerCase("abc"),
          Identifier.LowerCase("def")
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
        name = Identifier.LowerCase(value = "abc", metadata = Metadata.unknown), metadata = Metadata.unknown
      )
    }

    object Definition {
      val `package abc { Integer int = 4;}`: PackageDefinition = PackageDefinition(
        name = Identifier.LowerCase(value = "abc", metadata = Metadata.unknown), metadata = Metadata.unknown,
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
        name = ast.Value.Name(value="int",metadata = Metadata.unknown),
        typeReference = ast.Value.Type.Reference(
          id = ast.partial.Identifier.UpperCase("Integer"),
        ),
        initialization = Literals.Integer(4),
      )
      val `String text = "text";`: ValueDefinition = ValueDefinition(
        name = ast.Value.Name(value="text",metadata = Metadata.unknown),
        typeReference = ast.Value.Type.Reference(
          id = ast.partial.Identifier.UpperCase("String"),
        ),
        initialization = Literals.String("text"),
      )
    }

    object Declaration {
      val `Integer int`: ValueDeclaration = ValueDeclaration(
        name = ast.Value.Name(value="int",metadata = Metadata.unknown),
        typeReference = ast.Value.Type.Reference(
          id = ast.partial.Identifier.UpperCase("Integer"),
        ),
      )
      val `String str`: ValueDeclaration = ValueDeclaration(
        name = ast.Value.Name(value="int",metadata = Metadata.unknown),
        typeReference = ast.Value.Type.Reference(
          id = ast.partial.Identifier.UpperCase("Integer"),
        ),
      )

    }
  }


  object Import {
    val `scala.lang.String`: ast.Imports.Simple = ast.Imports.Simple(
      member = ast.partial.Identifier.Qualified(
        qualifications = Seq(
          ast.partial.Identifier.LowerCase(
            value = "scala",
          ),
          ast.partial.Identifier.LowerCase(
            value = "lang",
          ),
          ast.partial.Identifier.UpperCase(value = "String")
        ),
      )
    )


    val `scala.lang.Integer as Bleh`: ast.Imports.Alias = ast.Imports.Alias(
      member = ast.partial.Identifier.Qualified(
        qualifications = Seq(
          ast.partial.Identifier.LowerCase(
            value = "scala",
          ),
          ast.partial.Identifier.LowerCase(
            value = "lang",
          ),
          ast.partial.Identifier.UpperCase("Integer")

        )
      ),
      alias = ast.partial.Identifier.UpperCase("Bleh"),
    )


    val `scala.lang [ String, Integer as Bleh ]`: ast.Imports.Nested = ast.Imports.Nested(
      nest = ast.partial.Identifier.Qualified(
        qualifications = Seq(
          ast.partial.Identifier.LowerCase("scala"),
          ast.partial.Identifier.LowerCase("lang")
        ),
      ),
      nestee = Seq(
        ast.Imports.Simple(
          member = ast.partial.Identifier.UpperCase("String")
        ),
        ast.Imports.Alias(
          member = ast.partial.Identifier.UpperCase("Integer"),
          alias = ast.partial.Identifier.UpperCase("Bleh"),
        ),
      )
    )
    val `scala. [ lang.String, lang.[Integer as Bleh] ]`: ast.Imports.Nested = ast.Imports.Nested(
      nest = ast.partial.Identifier.Qualified(
        qualifications = Seq(
          ast.partial.Identifier.LowerCase("scala")
        ),
      ),
      nestee = Seq(
        ast.Imports.Nested(
          nest = ast.partial.Identifier.Qualified(
            qualifications = Seq(
              ast.partial.Identifier.LowerCase("lang")
            ),
          ),
          nestee = Seq(
            ast.Imports.Alias(
              member = ast.partial.Identifier.Qualified(
                qualifications = Seq(
                  ast.partial.Identifier.UpperCase("Integer")
                ),
              ),
              alias = ast.partial.Identifier.UpperCase("Bleh")
            )
          )
        )
      )
    )
  }
}
