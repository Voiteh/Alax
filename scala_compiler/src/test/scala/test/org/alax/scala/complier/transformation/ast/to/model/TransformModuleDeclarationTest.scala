package test.org.alax.scala.complier.transformation.ast.to.model

import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.base.model.CompilerError
import org.alax.scala.compiler.model.{Module, Value}
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.junit.jupiter.api.Test
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.scala.complier.transformation.ast.to.model.fixture.{Ast, Model}

class TransformModuleDeclarationTest extends AnyWordSpec {
  val astTransformer = AstToModelTransformer()
  "ast module declaration" when {
    "module abc.def" must {
      "transform to model" in {
        val result = astTransformer.transform.module.declaration(
          declaration = Ast.Module.Declaration.`module abc.def`
        )

        inside(result) {
          case declaration: Module.Declaration =>  declaration mustBe Model.Module.Declaration.`module abc.def`
          case result => fail(s"invalid result expected module declaration but was: ${result}")
        }
      }
    }


  }
}
