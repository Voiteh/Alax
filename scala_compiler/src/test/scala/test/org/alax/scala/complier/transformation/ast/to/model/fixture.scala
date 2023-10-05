package test.org.alax.scala.complier.transformation.ast.to.model

import org.alax.ast.model as ast
import org.alax.ast.model.Statement.Declaration.Value as ValueDeclaration
import org.alax.scala.compiler
import org.alax.scala.compiler.transformation
import org.alax.scala.compiler.Context.Module
import org.alax.scala.compiler.model.{Import, Trace}

import scala.annotation.targetName
import java.nio.file.Path

object fixture {


  object Context {
    object Trace {
      val `empty trace` = new Trace(unit = "", lineNumber = 0, startIndex = 0, endIndex = 0)
    }

    object Imports {

      val `duplicate member imports` = Seq(
        new Import(ancestor = "alax", member = "One", alias = null),
        new Import(ancestor = "other", member = "One", alias = null)

      )

      val `duplicate main ancestor imports` = Seq(
        new Import(ancestor = "alax", member = "One", alias = null),
        new Import(ancestor = "alax", member = "Two", alias = null)

      )
      val `duplicate nested ancestor imports` = Seq(
        new Import(ancestor = "alax.something", member = "One", alias = null),
        new Import(ancestor = "alax.something", member = "Two", alias = null)

      )
      val `duplicate nested with different top ancestor imports` = Seq(
        new Import(ancestor = "java.something", member = "One", alias = null),
        new Import(ancestor = "alax.something", member = "Two", alias = null)

      )
      val `non duplicating imports ` = Seq(
        new Import(ancestor = "different.something", member = "Foo", alias = null),
        new Import(ancestor = "something.different", member = "Foo", alias = "Bar")
      )
    }

    val unit = compiler.Context.Unit(path = Path.of(""), imports = Seq.empty)
    val `unit with import`: compiler.Context.Unit = new compiler.Context.Unit(path = Path.of(""), imports = Seq(Statement.`import`.`scala.lang.Integer`))
    val `unit with import and alias`: compiler.Context.Unit = compiler.Context.Unit(
      path = Path.of(""),
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
