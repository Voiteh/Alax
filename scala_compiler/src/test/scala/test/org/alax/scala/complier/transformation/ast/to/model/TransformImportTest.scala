package test.org.alax.scala.complier.transformation.ast.to.model

import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.must.Matchers
import test.org.alax.scala.complier.transformation.ast.to.model.fixture.Ast

class TransformImportTest extends AnyWordSpec with Matchers {

  val astTransformer = AstToModelTransformer()

  "AstToModelTransformer" when {
    "transforming a basic import with null package" must {
      val result = astTransformer.transform.imports(Ast.Import.`scala.lang.String`)
      val transformed = result.last.transformed

      "return a sequence with one element" in {
        result.size mustBe 1
      }

      "have the correct ancestor" in {
        transformed.ancestor.text mustBe "scala.lang"
      }

      "have the correct member" in {
        transformed.member mustBe "String"
      }
    }

    "transforming an import with an alias and null package" must {
      val result = astTransformer.transform.imports(Ast.Import.`scala.lang.Integer as Bleh`)
      val transformed = result.last.transformed

      "return a sequence with one element" in {
        result.size mustBe 1
      }

      "have the correct ancestor" in {
        transformed.ancestor.text mustBe "scala.lang"
      }

      "have the correct member" in {
        transformed.member mustBe "Integer"
      }

      "have the correct alias" in {
        transformed.alias mustBe "Bleh"
      }
    }

    "transforming a container with 2 imports and null package" must {
      val result = astTransformer.transform.imports(Ast.Import.`scala.lang [ String, Integer as Bleh ]`)

      "return a sequence with two elements" in {
        result.size mustBe 2
      }

      "have the correct ancestor for all elements" in {
        result.forall(_.transformed.ancestor.text == "scala.lang") mustBe true
      }

      "contain an import for String" in {
        result.exists(_.transformed.member == "String") mustBe true
      }

      "contain an import for Integer with alias Bleh" in {
        result.exists(element =>
          element.transformed.member == "Integer" && element.transformed.alias == "Bleh"
        ) mustBe true
      }
    }
  }
}