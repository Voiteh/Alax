package test.org.alax.scala.compiler.transformation.ast.to.model

import org.alax.ast
import org.alax.scala.compiler.base.model.CompilerError
import org.alax.scala.compiler.{base, model}
import org.alax.scala.compiler.model.{Package, Value}
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.junit.jupiter.api.Test
import org.scalatest.Inside
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.scala.compiler.transformation.ast.to.model.fixture
import test.org.alax.scala.compiler.transformation.ast.to.model.fixture.{Ast, Model}
class TransformPackageDeclarationTest extends AnyWordSpec with Matchers with Inside {

  val astTransformer = AstToModelTransformer()
  type Testable = ast.Package.Declaration
  type Expected = model.Package.Declaration

  val matches: Seq[(Testable, Expected)] = Seq(
    (
      fixture.Ast.Package.Declaration.`package abc`,
      fixture.Model.Package.Declaration.`package abc`
    )
  )

  "AstToModelTransformer" when {
    "transforming package declarations" should {
      matches.foreach { case (testable, expected) =>
        s"correctly transform ${testable}" in {
          val result = astTransformer.transform.`package`.declaration(
            declaration = testable
          )

          inside(result) {
            case declaration: model.Package.Declaration =>
              declaration should equal(expected)
            case other =>
              fail(s"Invalid result: expected package declaration but was: $other")
          }
        }
      }
    }
  }
}
