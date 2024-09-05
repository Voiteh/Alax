package test.org.alax.scala.compiler.transformation.ast.to.model

import org.alax.scala.compiler.model
import org.alax.scala.compiler.base
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.scalatest.Inside
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.must.Matchers
import test.org.alax.scala.compiler.transformation.ast.to.model.fixture.Ast
import org.alax.ast
import org.scalatest.matchers.should.Matchers.should

class TransformImportTest extends AnyWordSpec with Matchers with Inside {

  val astTransformer = AstToModelTransformer()
  type Testable = ast.Import.Declaration
  type Expected = Seq[base.model.Import]

  val matches: Seq[(Testable, Expected)] = Seq(
    (
      Ast.Import.`scala.lang.String`,
      Seq(
        base.model.Import(
          ancestor = new model.Package.Reference("scala.lang"),
          member = "String",
          alias = null
        )
      )
    ),
    (
      Ast.Import.`scala.lang.Integer as Bleh`,
      Seq(
        base.model.Import(
          ancestor = new model.Package.Reference("scala.lang"),
          member = "Integer",
          alias = "Bleh"
        )
      )
    ),
    (
      Ast.Import.`scala.lang [ String, Integer as Bleh ]`,
      Seq(
        base.model.Import(
          ancestor = new model.Package.Reference("scala.lang"),
          member = "String",
          alias = null
        ),
        base.model.Import(
          ancestor = new model.Package.Reference("scala.lang"),
          member = "Integer",
          alias = "Bleh"
        )
      )
    )
  )
  "AstToModelTransformer" when {
    "transforming imports" should {
      matches.foreach { case (testable, expected) =>
        s"correctly transform ${testable}" in {
          val result = astTransformer.transform.imports(testable)

          result should have size expected.size
          result.zip(expected).foreach { case (actual, exp) =>
            inside(actual.transformed) { case transformed: base.model.Import =>
              transformed.ancestor.text should equal(exp.ancestor.text)
              transformed.member should equal(exp.member)
              transformed.alias should equal(exp.alias)
            }
          }
        }
      }
    }
  }
}