package test.org.alax.scala.complier.transformation.ast.to.model

import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.base.model.CompilerError
import org.alax.scala.compiler.model.{Module, Value}
import org.alax.scala.compiler.transformation.ast.to.model.{AstToModelTransformer, Contexts}
import org.junit.jupiter.api.Test
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.scala.complier.transformation.ast.to.model.fixture.{Ast, Model}

class TransformModuleDefinitionTest extends AnyWordSpec {
  val astTransformer = AstToModelTransformer()
  "ast module definition" when {
    "module abc.def with value definition" must {
      "transform to model" in {
        val result = astTransformer.transform.module.definition(
          definition = Ast.Module.Definition.`module abc.def { Integer int = 4;}`,
          context = Contexts.Unit(imports = Seq(Model.Context.Import.`scala.lang.Integer`))
        )
        inside(result) {
          case definition: Module.Definition => definition mustBe Model.Module.Definition.`module abc.def { int: scala.lang.Integer; }`
          case result => fail(s"invalid result expected module definition but was: ${result}")

        }
      }
    }


  }
}