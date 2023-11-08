package test.org.alax.scala.complier.transformation.ast.to.model.fixture

import org.alax.ast
import org.alax.ast.base.model.Statement.Declaration.Value as ValueDeclaration
import org.alax.ast.base.model.Statement.Definition.Value as ValueDefinition
import org.alax.scala.compiler
import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.base.model.{Import, Trace}
import org.alax.scala.compiler.model.{Literals, Value}
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
      val `scala.lang.Integer`: Import = compiler.base.model.Import(
        ancestor = "scala.lang",
        member = "Integer"
      );
      val `java.lang.Integer as JInteger`: Import = compiler.base.model.Import(
        ancestor = "java.lang",
        member = "Integer", alias = "JInteger"
      );
      val `scala.lang.Integer as JInteger`: Import = compiler.base.model.Import(
        ancestor = "scala.lang",
        member = "Integer", alias = "JInteger"
      );
    }

    object Declaration {
      val `int: scala.lang.Integer` = Value.Declaration(
        name = "int",
        `type` = Value.Type(
          id = model.Declaration.Type.Id(
            value = "scala.lang.Integer"
          )
        )
      )
    }

    object Definition {
      val `val int: scala.lang.Integer = 4` = compiler.model.Value.Definition(
        declaration = Declaration.`int: scala.lang.Integer`,
        initialization = Literals.Integer(4L)
      )
    }

  }




}
