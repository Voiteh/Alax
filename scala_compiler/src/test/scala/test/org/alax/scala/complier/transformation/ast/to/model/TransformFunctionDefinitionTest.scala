package test.org.alax.scala.complier.transformation.ast.to.model

import org.alax.scala.compiler.base.model.CompilerError
import org.alax.scala.compiler
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.must.Matchers
import test.org.alax.scala.complier.transformation.ast.to.model.fixture.Ast
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import scala.language.postfixOps

class TransformFunctionDefinitionTest extends AnyWordSpec {

  val astTransformer = AstToModelTransformer()

  "AstToModelTransformer" when {
    {
      val transformable = fixture.Ast.Function.Declaration.`function bleh()`
      val expected =fixture.Model.Function.Declaration.`function bleh()`
      s"transforming ${transformable} " must {

        s"transforms to ${expected}" in {
          val result = astTransformer.transform.function.declaration(
            declaration = transformable
          )
          inside(result) {
            case declaration: compiler.model.Function.Declaration => declaration mustBe expected
            case other => fail(s"invalid result expected function declaration but was: ${other}")
          }

        }
      }
    }
    {
      val transformable =fixture.Ast.Function.Declaration.`function Integer bleh() `
      val context = fixture.Model.Context.`package with import = scala.lang.Integer`
      val expected = fixture.Model.Function.Declaration.`function java.lang.Integer bleh()`
      s"transforming ${transformable} with context ${context}" must {

        s"transforms to ${expected}" in {
          val result = astTransformer.transform.function.declaration(
            declaration = transformable,
            context = context
          )
          inside(result) {
            case declaration: compiler.model.Function.Declaration => declaration mustBe expected
            case other => fail(s"invalid result expected function declaration but was: ${other}")
          }

        }
      }
    }
    {
      val transformable = fixture.Ast.Function.Declaration.`function Integer bleh() `
      val context = fixture.Model.Context.`package with import = scala.lang.Integer`
      val expected = fixture.Model.Function.Declaration.`function java.lang.Integer bleh()`
      s"transforming ${transformable} with ${context}" must {
        s"transforms to ${expected}" in {
          val result = astTransformer.transform.function.declaration(
            declaration = transformable,
            context = context
          )
          inside(result) {
            case declaration: compiler.model.Function.Declaration => declaration mustBe expected
            case other => fail(s"invalid result expected function declaration but was: ${other}")
          }

        }
      }
    }
  }


}
