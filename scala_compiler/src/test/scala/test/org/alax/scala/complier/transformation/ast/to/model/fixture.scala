package test.org.alax.scala.complier.transformation.ast.to.model

import org.alax.ast
import org.alax.ast.model.Statement.Declaration.Value as ValueDeclaration
import org.alax.ast.model.Statement.Definition.Value as ValueDefinition
import org.alax.scala.compiler
import org.alax.scala.compiler.transformation
import org.alax.scala.compiler.transformation.Context.Module
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

    val `package` = transformation.Context.Package(path = Path.of(""), imports = Seq.empty)
    val `package with import = scala.lang.Integer` = transformation.Context.Package(path = Path.of(""), imports = Seq(Statement.`import`.`scala.lang.Integer`))
    val unit = transformation.Context.Unit(path = Path.of(""), imports = Seq.empty)
    val `unit with import`: transformation.Context.Unit = new transformation.Context.Unit(path = Path.of(""), imports = Seq(Statement.`import`.`scala.lang.Integer`))
    val `unit with import and alias`: transformation.Context.Unit = transformation.Context.Unit(
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

    object Declaration {
      val `int: scala.lang.Integer` = compiler.model.Value.Declaration(
        name = "int",
        `type` = compiler.model.Value.Type(
          id = compiler.model.Declaration.Type.Id(
            value = "scala.lang.Integer"
          )
        )
      )
    }

    object Definition {
      val `val int: scala.lang.Integer = 4` = compiler.model.Value.Definition(
        declaration = Declaration.`int: scala.lang.Integer`,
        initialization = compiler.model.Literal.Integer(4L)
      )
    }

  }


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
        val `empty package` = ast.Source.Unit.Package(path = Path.of(""), imports = Seq(), members = Seq())
        val `simple package with Integer value` = ast.Source.Unit.Package(path = Path.of(""),
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

}
