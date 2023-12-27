package test.org.alax.scala.complier.transformation.ast.to.model

import org.scalatest.wordspec.AnyWordSpec
import org.alax.scala.compiler.base.model.CompilerError
import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.model.Value
import org.junit.jupiter.api.Test
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.scala.complier.transformation.ast.to.model.fixture.{Ast, Model}
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.alax.scala.compiler.model.Package

class TransformPackageDeclarationTest extends AnyWordSpec {
  val astTransformer = AstToModelTransformer()
  "ast package declaration" when {
    "package abc" must {
      "transform to model" in {
        val result = astTransformer.transform.`package`.declaration(declaration =
          Ast.Package.Declaration.`package abc`
        )

        inside(result) {
          case declaration: Package.Declaration =>  declaration mustBe Model.Package.Declaration.`package abc`
          case result => fail(s"invalid result expected package declaration but was: ${result}")
        }
      }
    }


  }
}
