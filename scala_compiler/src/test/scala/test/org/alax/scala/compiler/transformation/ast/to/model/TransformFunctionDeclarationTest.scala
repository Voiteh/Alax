package test.org.alax.scala.compiler.transformation.ast.to.model

import org.alax.scala.compiler.base.model.CompilerError
import org.alax.scala.compiler.model
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.alax.scala.compiler.transformation.ast.to.model.Contexts
import org.alax.ast
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import test.org.alax.scala.compiler.transformation.ast.to.model.fixture.{Ast, Model}
import org.scalatest.Inside

import scala.language.postfixOps

class TransformFunctionDeclarationTest extends AnyWordSpec with Matchers with Inside {

  val astTransformer = AstToModelTransformer()
  type Testable = ast.Function.Declaration
  type Context = Contexts.Unit
  type Expected = model.Function.Declaration

  val matches: Seq[(Testable, Expected, Context | Null)] = Seq(
    (
      Ast.Function.Declaration.`function bleh()`,
      Model.Function.Declaration.`function bleh()`,
      null
    ),
    (
      Ast.Function.Declaration.`function Integer bleh()`,
      Model.Function.Declaration.`function java.lang.Integer bleh()`,
      Model.Context.`package with import = scala.lang.Integer`
    ),
    (
      Ast.Function.Declaration.`function bleh(Integer param)`,
      Model.Function.Declaration.`function bleh(java.lang.Integer param)`,
      Model.Context.`package with import = scala.lang.Integer`
    )
  )

  "AstToModelTransformer" when {
    "transforming function declarations" should {
      matches.foreach { case (testable, expected, context) =>
        s"correctly transform ${testable} to ${expected}" in {
          val result = astTransformer.transform.function.declaration(
            declaration = testable,
            context = context
          )

          inside(result) {
            case declaration: model.Function.Declaration =>
              declaration should equal(expected)
            case other =>
              fail(s"Invalid result: expected function declaration but was: $other")
          }
        }
      }
    }
  }
}