package test.org.alax.scala.compiler.transformation.ast.to.model

import org.alax.ast
import org.alax.scala.compiler.base.model.CompilerError
import org.alax.scala.compiler.{base, model}
import org.alax.scala.compiler.model.{Module, Value}
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.junit.jupiter.api.Test
import org.scalatest.Inside
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.scala.compiler.transformation.ast.to.model.fixture.{Ast, Model}

class TransformModuleDeclarationTest extends AnyWordSpec with Matchers with Inside {

  val astTransformer = AstToModelTransformer()
  type Testable = ast.Module.Declaration
  type Expected = model.Module.Declaration

  val matches: Seq[(Testable, Expected)] = Seq(
    (
      Ast.Module.Declaration.`module abc.def`,
      Model.Module.Declaration.`module abc.def`
    )
  )

  "AstToModelTransformer" when {
    "transforming module declarations" should {
      matches.foreach { case (testable, expected) =>
        s"correctly transform ${testable}" in {
          val result = astTransformer.transform.module.declaration(
            declaration = testable
          )

          inside(result) {
            case declaration: model.Module.Declaration =>
              declaration should equal(expected)
            case other =>
              fail(s"Invalid result: expected module declaration but was: $other")
          }
        }
      }
    }
  }
}
