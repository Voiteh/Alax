package test.org.alax.scala.compiler.transformation.ast.to.model

import org.alax.scala.compiler.base.model.{CompilationError, CompilerError}
import org.alax.scala.compiler.model
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.alax.scala.compiler.transformation.ast.to.model.Contexts
import org.alax.ast
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
import test.org.alax.scala.compiler.transformation.ast.to.model.fixture.{Ast, Model}
import org.scalatest.Inside
import org.alax.scala.compiler.base
import org.scalatest.matchers.must.Matchers.mustBe

import scala.language.postfixOps

class TransformValueDeclarationTest extends AnyWordSpec with Matchers with Inside {

  val astTransformer = AstToModelTransformer()

  "AstToModelTransformer" when {
    "Positive" should {
      type Testable = ast.Value.Declaration
      type Context = Contexts.Package | Null
      type Expected = model.Value.Declaration

      val matches: Seq[(Testable, Expected, Context)] = Seq(
        (
          Ast.Value.Declaration.`Integer int`,
          model.Value.Declaration(
            identifier = "int",
            typeReference = model.Value.Type.Reference(
              packageReference = new model.Package.Reference("scala.lang"),
              id = base.model.Type.Id("Integer")
            )
          ),
          fixture.Model.Context.`package with import = scala.lang.Integer`
        ),
        (
          Ast.Value.Declaration.`Integer int`,
          model.Value.Declaration(
            identifier = "int",
            typeReference = model.Value.Type.Reference(
              packageReference = new model.Package.Reference("scala.lang"),
              id = base.model.Type.Id("Integer")
            )
          ),
          Model.Context.`package with import and alias`
        )

      )
      matches.foreach { case (testable, expected, context) =>
        s"correctly transform ${testable} with context ${context}" in {
          val result = astTransformer.transform.value.declaration(
            valueDeclaration = testable,
            context = context
          )
          inside(result) {
            case declaration: model.Value.Declaration => declaration mustBe expected
            case other =>
              fail(s"Invalid result: expected value declaration but was: $other")
          }
        }
      }
    }

    "Negative " should {
      type Testable = ast.Value.Declaration
      type Context = Contexts.Package | Null
      type Expected = CompilerError

      val matches: Seq[(Testable, Expected, Context)] = Seq(
        (
          Ast.Value.Declaration.`Integer int`,
          CompilationError(path = "", message = "Unknown type: Integer, did You forget to import?"),
          fixture.Model.Context.`package empty`
        )
      )

      matches.foreach { case (testable, expected, context) =>
        s"correctly transform ${testable} with context ${context}" in {
          val result = astTransformer.transform.value.declaration(
            valueDeclaration = testable,
            context = context
          )
          inside(result) {
            case error: CompilerError => error mustBe expected
            case other =>
              fail(s"Invalid result: expected error but was: $other")
          }
        }
      }
    }
  }

}