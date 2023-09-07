package test.org.alax.scala.complier.transformation.ast.to.model

import org.alax.ast.model as ast
import org.alax.ast.model.Statement.Declaration.{Import, Value as ValueDeclaration}
import org.alax.scala.compiler
import org.alax.scala.compiler.transformation
import org.alax.scala.compiler.Context.Module

import scala.annotation.targetName

object fixture {


  object Context {
    val `module`: Module = compiler.Context.Module(compiler.Context.Project())
    val `package`: compiler.Context.Package = compiler.Context.Package(`module`)
    val unit: compiler.Context.Unit = compiler.Context.Unit(`package`)
    val `unit with import`: compiler.Context.Unit = compiler.Context.Unit(`package`, Seq(Statement.`import`.`scala.lang.Integer`))
    val `unit with import and alias`: compiler.Context.Unit = compiler.Context.Unit(
      parent = `package`,
      imports =
        Seq(
          Statement.`import`.`scala.lang.Integer`,
          Statement.`import`.`java.lang.Integer as JInteger`
        ))
  }

  object Statement {
    object `import` {
      val `scala.lang.Integer`: compiler.model.Import = compiler.model.Import(
        ancestor = "scala.lang",
        member = "Integer"
      );
      val `java.lang.Integer as JInteger`: compiler.model.Import = compiler.model.Import(
        ancestor = "java.lang",
        member = "Integer", alias = "JInteger"
      );
      val `scala.lang.Integer as JInteger`: compiler.model.Import = compiler.model.Import(
        ancestor = "scala.lang",
        member = "Integer", alias = "JInteger"
      );
    }
  }

  object Value {
    object Declaration {
      val `Integer int`: ValueDeclaration = ValueDeclaration(
        name = ast.Partial.Name.LowerCase(
          value = "int",
        ),
        `type` = ast.Partial.Type.ValueTypeReference(
          id = ast.Partial.Name.UpperCase("Integer"),
        ),
      )
    }
  }

  object Ast {
    object Import {
      val `scala.lang.String`: ast.Statement.Declaration.Import.Simple = ast.Statement.Declaration.Import.Simple(
        member = ast.Partial.Name.Qualified(
          qualifications = Seq(
            ast.Partial.Name.LowerCase(
              value = "scala",
            ),
            ast.Partial.Name.LowerCase(
              value = "lang",
            ),
            ast.Partial.Name.UpperCase(value = "String")
          ),
        )
      )


      val `scala.lang.Integer as Bleh`: ast.Statement.Declaration.Import.Alias = ast.Statement.Declaration.Import.Alias(
        member = ast.Partial.Name.Qualified(
          qualifications = Seq(
            ast.Partial.Name.LowerCase(
              value = "scala",
            ),
            ast.Partial.Name.LowerCase(
              value = "lang",
            ),
            ast.Partial.Name.UpperCase("Integer")

          )
        ),
        alias = ast.Partial.Name.UpperCase("Bleh"),
      )


      val `scala.lang [ String, Integer as Bleh ]`: ast.Statement.Declaration.Import.Nested = ast.Statement.Declaration.Import.Nested(
        nest = ast.Partial.Name.Qualified(
          qualifications = Seq(
            ast.Partial.Name.LowerCase("scala"),
            ast.Partial.Name.LowerCase("lang")
          ),
        ),
        nestee = Seq(
          ast.Statement.Declaration.Import.Simple(
            member = ast.Partial.Name.UpperCase("String")
          ),
          ast.Statement.Declaration.Import.Alias(
            member = ast.Partial.Name.UpperCase("Integer"),
            alias = ast.Partial.Name.UpperCase("Bleh"),
          ),
        )
      )
      val `scala. [ lang.String, lang.[Integer as Bleh] ]`: ast.Statement.Declaration.Import.Nested = ast.Statement.Declaration.Import.Nested(
        nest = ast.Partial.Name.Qualified(
          qualifications = Seq(
            ast.Partial.Name.LowerCase("scala")
          ),
        ),
        nestee = Seq(
          ast.Statement.Declaration.Import.Nested(
            nest = ast.Partial.Name.Qualified(
              qualifications = Seq(
                ast.Partial.Name.LowerCase("lang")
              ),
            ),
            nestee = Seq(
              ast.Statement.Declaration.Import.Alias(
                member = ast.Partial.Name.Qualified(
                  qualifications = Seq(
                    ast.Partial.Name.UpperCase("Integer")
                  ),
                ),
                alias = ast.Partial.Name.UpperCase("Bleh")
              )
            )
          )
        )
      )
    }
  }

}
