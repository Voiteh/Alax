package test.org.alax.scala.compiler.transformation.ast.to.model

import org.alax.ast
import org.alax.scala.compiler.base.model.{CompilationError, CompilerError, Trace}
import org.alax.scala.compiler.model
import org.alax.scala.compiler.transformation.ast.to.model.{AstToModelTransformer, Contexts}
import org.scalatest.Inside
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.scala.compiler.transformation.ast.to.model.fixture.{Ast, Model}

class RoutineCallExpressionTest extends AnyWordSpec with Matchers with Inside {

  val astTransformer = AstToModelTransformer()
  type Testable = ast.Routine.Call.Expression
  type Context = Contexts.Package
  type Expected = model.Routine.Call


  "AstToModelTransformer" when {
    "transforming valid routine call expression" should {
      val matches: Seq[(Testable, Expected, Context)] = Seq(
        (
          Ast.Routine.Call.Expression.`abc.bleh()`,
          Model.Routine.Positional.Call.`abc.bleh()`,
          Model.Context.`package with import = scala.lang.Integer`
        ),
        (
          Ast.Routine.Call.Expression.`abc.bleh(1,"str")`,
          Model.Routine.Positional.Call.`abc.bleh(1,"str")`,
          Model.Context.`package with import = scala.lang.Integer`
        ),
        (
          Ast.Routine.Call.Expression.`abc.bleh(int=1,str="str")`,
          Model.Routine.Named.Call.`abc.bleh(int=1,str="str")`,
          Model.Context.`package with import = scala.lang.Integer`
        )
      )
      matches.foreach { case (testable, expected, context) =>
        s"correctly transform ${testable} to ${expected}" in {
          val result = astTransformer.transform.routine.call(testable)(context)

          inside(result) {
            case declaration: model.Routine.Call =>
              declaration should equal(expected)
            case other =>
              fail(s"Invalid result: expected routine call expression but was: $other")
          }
        }
      }
    }

    "transforming invalid routine call expression" should {
      val matches: Seq[(Testable, CompilerError, Context)] = Seq(
        (
          Ast.Routine.Call.Expression.`abc.bleh(1,str="str")`,
          CompilationError(trace = Trace.unknown, message = "Mixed arguments (positional/named) not supported"),
          Model.Context.`package with import = scala.lang.Integer`
        )
      )
      matches.foreach { case (testable, expected, context) =>
        s"correctly transform ${testable} to ${expected}" in {
          val result = astTransformer.transform.routine.call(testable)(context)

          inside(result) {
            case error: CompilerError =>
              error should equal(expected)
            case other =>
              fail(s"Invalid result: expected compiler error but was: $other")
          }
        }
      }

    }

  }
}