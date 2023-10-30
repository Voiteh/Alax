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

object Model {


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

    val `package` = transformation.Context.Package(path = JPath.of(""), imports = Seq.empty)
    val `package with import = scala.lang.Integer` = transformation.Context.Package(path = JPath.of(""), imports = Seq(Statement.`import`.`scala.lang.Integer`))
    val unit = transformation.Context.Unit(path = JPath.of(""), imports = Seq.empty)
    val `unit with import`: transformation.Context.Unit = new transformation.Context.Unit(path = JPath.of(""), imports = Seq(Statement.`import`.`scala.lang.Integer`))
    val `unit with import and alias`: transformation.Context.Unit = transformation.Context.Unit(
      path = JPath.of(""),
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




}
