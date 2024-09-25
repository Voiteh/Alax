package test.org.alax.scala.compiler.transformation.ast.to.model

import org.alax.ast
import org.alax.scala.compiler.base.model.CompilerError
import org.alax.scala.compiler.model
import org.alax.scala.compiler.transformation.ast.to.model.{AstToModelTransformer, Contexts}
import org.scalatest.Inside
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.scala.compiler.transformation.ast.to.model.fixture.{Ast, Model}

import scala.language.postfixOps

class TransformProcedureDeclarationTest extends AnyWordSpec with Matchers with Inside {

  val astTransformer = AstToModelTransformer()
  type Testable = ast.Procedure.Declaration
  type Context = Contexts.Package
  type Expected = model.Procedure.Declaration

  val matches: Seq[(Testable, Expected, Context | Null)] = Seq(
    (
      Ast.Procedure.Declaration.`procedure bleh()`,
      Model.Procedure.Declaration.`procedure bleh()`,
      null
    ),
    (
      Ast.Procedure.Declaration.`procedure bleh(Integer param)`,
      Model.Procedure.Declaration.`procedure bleh(java.lang.Integer param)`,
      Model.Context.`package with import = scala.lang.Integer`
    )
  )

  "AstToModelTransformer" when {
    "transforming function declarations" should {
      matches.foreach { case (testable, expected, context) =>
        s"correctly transform ${testable} to ${expected}" in {
          val result = astTransformer.transform.procedure.declaration(testable)(context)

          inside(result) {
            case declaration: model.Procedure.Declaration =>
              declaration should equal(expected)
            case other =>
              fail(s"Invalid result: expected function declaration but was: $other")
          }
        }
      }
    }
  }
}