package test.org.alax.scala.complier.transformer

import org.alax.ast.model as ast
import ast.statements.declarations.{Import, Value as ValueDeclaration}
import org.alax.scala.compiler

import scala.annotation.targetName

object fixture {

  object Compiler {
    object Model {

    }
  }

  object Context {
    val emptyModule: compiler.model.Context.Module = compiler.model.Context.Module(compiler.model.Context.Project())
    val emptyPackage: compiler.model.Context.Package = compiler.model.Context.Package(emptyModule)
    val emptyUnit: compiler.model.Context.Unit = compiler.model.Context.Unit(emptyPackage)
    val unitWithImport: compiler.model.Context.Unit = compiler.model.Context.Unit(emptyPackage, Seq(Statement.`import`.`scala.lang.Integer`))
    val unitWithImportAndAlias: compiler.model.Context.Unit = compiler.model.Context.Unit(
      parent = emptyPackage,
      imports =
        Seq(
          Statement.`import`.`scala.lang.Integer`,
          Statement.`import`.`java.lang.Integer as JInteger`
        ))
  }

  object Statement {
    object `import` {
      val `scala.lang.Integer`: compiler.model.Import = compiler.model.Import(
        `package` = "scala.lang",
        member = "Integer"
      );
      val `java.lang.Integer as JInteger`: compiler.model.Import = compiler.model.Import(
        `package` = "java.lang",
        member = "Integer", alias = "JInteger"
      );
    }
  }

  object Value {
    object Declaration {
      val `Integer int`: ValueDeclaration = ValueDeclaration(
        name = ast.partials.names.LowerCase(
          value = "int",
        ),
        `type` = ast.partials.types.Value(
          id = ast.partials.names.UpperCase("Integer"),
        ),
      )
    }
  }

  object Ast {
    object Import {
      val `scala.lang.String`: ast.statements.declarations.Import.Simple = ast.statements.declarations.Import.Simple(
        `package` = ast.partials.names.Qualified(
          qualifications = Seq(
            ast.partials.names.LowerCase(
              value = "scala",
            ),
            ast.partials.names.LowerCase(
              value = "lang",
            ),
          ),
        ),
        member = ast.partials.names.UpperCase("String"),
      )

      val `scala.lang.Integer as Bleh`: ast.statements.declarations.Import.Alias = ast.statements.declarations.Import.Alias(
        `package` = ast.partials.names.Qualified(
          qualifications = Seq(
            ast.partials.names.LowerCase(
              value = "scala",
            ),
            ast.partials.names.LowerCase(
              value = "lang",
            ),
          ),
        ),
        member = ast.partials.names.UpperCase("Integer"),
        alias = ast.partials.names.UpperCase("Bleh")
      )

      val `scala.lang [ String, Integer as Bleh ]`: ast.statements.declarations.Import.Container = ast.statements.declarations.Import.Container(
        `package` = ast.partials.names.Qualified(
          qualifications = Seq(
            ast.partials.names.LowerCase("scala"),
            ast.partials.names.LowerCase("lang")
          ),
        ),
        members = Seq(
          ast.statements.declarations.Import.Simple(
            `package` = null,
            member = ast.partials.names.UpperCase("String"),
          ),
          ast.statements.declarations.Import.Alias(
            `package` = null,
            member = ast.partials.names.UpperCase("Integer"),
            alias = ast.partials.names.UpperCase("Bleh")
          )
        )
      )
      val `scala. [ lang.String, lang.[Integer as Bleh] ]`: ast.statements.declarations.Import.Container = ast.statements.declarations.Import.Container(
        `package` = ast.partials.names.Qualified(
          qualifications = Seq(
            ast.partials.names.LowerCase("scala")
          ),
        ),
        members = Seq(
          ast.statements.declarations.Import.Container(
            `package` = ast.partials.names.Qualified(
              qualifications = Seq(
                ast.partials.names.LowerCase("lang")
              ),
            ),
            members = Seq(
              ast.statements.declarations.Import.Alias(
                `package` = ast.partials.names.Qualified(
                  qualifications = Seq(
                    ast.partials.names.LowerCase("lang"),
                  ),
                ),
                member = ast.partials.names.UpperCase("Integer"),
                alias = ast.partials.names.UpperCase("Bleh"),
              )
            )
          )
        )
      )
    }
  }

}
