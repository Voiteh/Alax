package test.org.alax.scala.compiler.transformation.ast.to.model

import org.alax.scala.compiler.base
import org.alax.scala.compiler.base.model.CompilerError
import org.alax.scala.compiler.model.{Module, Value}
import org.alax.scala.compiler.transformation.ast.to.model.{AstToModelTransformer, Contexts}
import org.junit.jupiter.api.Test
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.scala.compiler.transformation.ast.to.model.fixture.{Ast, Model}
import org.alax.scala.compiler.model
import org.alax.scala.compiler.transformation.ast.to.model.{AstToModelTransformer, Contexts}
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import test.org.alax.scala.compiler.transformation.ast.to.model.fixture.{Ast, Model}
import org.scalatest.Inside
import org.alax.ast
import scala.language.postfixOps
class TransformModuleDefinitionTest extends AnyWordSpec with Matchers with Inside {

  val astTransformer = AstToModelTransformer()
  type Testable = ast.Module.Definition
  type Context = Contexts.Package
  type Expected = model.Module.Definition

  val matches: Seq[(Testable, Expected, Context)] = Seq(
    (
      Ast.Module.Definition.`module abc.def { Integer int = 4;}`,
      Model.Module.Definition.`module abc.def { int: scala.lang.Integer; }`,
      Contexts.Package(
        identifier="some package",
        imports = Seq(Model.Context.Import.`scala.lang.Integer`)
      )
    )
  )

  "AstToModelTransformer" when {
    "transforming module definitions" should {
      matches.foreach { case (testable, expected, context) =>
        s"correctly transform ${testable}" in {
          val result = astTransformer.transform.module.definition(
            definition = testable,
            context = context
          )

          inside(result) {
            case definition: model.Module.Definition =>
              definition should equal(expected)
            case other =>
              fail(s"Invalid result: expected module definition but was: $other")
          }
        }
      }
    }
  }
}