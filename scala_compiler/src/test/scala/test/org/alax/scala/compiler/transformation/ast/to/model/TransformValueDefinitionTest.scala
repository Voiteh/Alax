package test.org.alax.scala.compiler.transformation.ast.to.model

import org.alax.scala.compiler.model
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.alax.scala.compiler.transformation.ast.to.model.Contexts
import org.alax.ast
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import test.org.alax.scala.compiler.transformation.ast.to.model.fixture.{Ast, Model}
import org.scalatest.Inside

import scala.language.postfixOps

class TransformValueDefinitionTest extends AnyWordSpec with Matchers with Inside {

  val astTransformer = AstToModelTransformer()
  type Testable = ast.Value.Definition
  type Context = Contexts.Unit
  type Expected = model.Value.Definition

  val matches: Seq[(Testable, Expected, Context)] = Seq(
    (
      Ast.Value.Definition.`Integer int = 4;`,
      Model.Value.Definition.`val int: scala.lang.Integer = 4`,
      Model.Context.`package with import = scala.lang.Integer`
    )
  )

  "AstToModelTransformer" when {
    "transforming value definitions" should {
      matches.foreach { case (testable, expected, context) =>
        s"correctly transform ${testable}" in {
          val result = astTransformer.transform.value.definition(
            valueDefinition = testable,
            context = context
          )

          inside(result) {
            case definition: model.Value.Definition =>
              definition should equal(expected)
            case other =>
              fail(s"Invalid result: expected value definition but was: $other")
          }
        }
      }
    }
  }
}