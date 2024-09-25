package test.org.alax.scala.compiler.transformation.ast.to.model

import org.alax.scala.compiler.base
import org.alax.scala.compiler.base.model.CompilerError
import org.alax.scala.compiler.model
import org.alax.scala.compiler.model.{Package, Value}
import org.alax.scala.compiler.transformation.ast.to.model.{AstToModelTransformer, Contexts}
import org.junit.jupiter.api.Test
import org.scalatest.Inside
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.scala.compiler.transformation.ast.to.model.fixture.{Ast, Model}
import org.alax.ast
import scala.language.postfixOps
class TransformPackageDefinitionTest extends AnyWordSpec with Matchers with Inside {

  val astTransformer = AstToModelTransformer()
  type Testable = ast.Package.Definition
  type Context = Contexts.Package
  type Expected = model.Package.Definition

  val matches: Seq[(Testable, Expected, Context)] = Seq(
    (
      Ast.Package.Definition.`package abc { Integer int = 4;}`,
      Model.Package.Definition.`package abc { int: scala.lang.Integer; }`,
      Contexts.Package(
        identifier= "abc",
        imports = Seq(Model.Context.Import.`scala.lang.Integer`)
      )
    )
  )

  "AstToModelTransformer" when {
    "transforming package definitions" should {
      matches.foreach { case (testable, expected, context) =>
        s"correctly transform ${testable}" in {
          val result = astTransformer.transform.`package`.definition(
            definition = testable,
            context = context
          )

          inside(result) {
            case definition: model.Package.Definition =>
              definition should equal(expected)
            case other =>
              fail(s"Invalid result: expected package definition but was: $other")
          }
        }
      }
    }

    "transforming package definition bodies" should {
      matches.foreach { case (testable, expected, context) =>
        s"correctly transform the body of ${testable}" in {
          val result = astTransformer.transform.`package`.definition.body(
            body = testable.body,
            context = context
          )

          inside(result) {
            case body: model.Package.Definition.Body =>
              body should equal(expected.body)
            case other =>
              fail(s"Invalid result: expected package definition body but was: $other")
          }
        }
      }
    }
  }
}