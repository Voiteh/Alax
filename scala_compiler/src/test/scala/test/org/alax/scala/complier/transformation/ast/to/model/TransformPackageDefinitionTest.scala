package test.org.alax.scala.complier.transformation.ast.to.model

import org.scalatest.wordspec.AnyWordSpec
import org.alax.scala.compiler.base.model.CompilerError
import org.alax.scala.compiler.base.model
import org.alax.scala.compiler.model.{Contexts, Package, Value}
import org.junit.jupiter.api.Test
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.scala.complier.transformation.ast.to.model.fixture.{Ast, Model}
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import test.org.alax.scala.complier.transformation.ast.to.model.fixture.{Ast, Model}

class TransformPackageDefinitionTest extends AnyWordSpec {
  val astTransformer = AstToModelTransformer()
  "ast package definition" when {
    "package abc with value definition" must {
      "transform to model" in {
        val result = astTransformer.transform.`package`.definition(
          definition = Ast.Package.Definition.`package abc { Integer int = 4;}`,
          context = Contexts.Unit(imports = Seq(Model.Context.Import.`scala.lang.Integer`))
        )
        inside(result) {
          case definition: Package.Definition => definition mustBe Model.Package.Definition.`package abc { int: scala.lang.Integer; }`
          case result => fail(s"invalid result expected package definition but was: ${result}")

        }
      }
    }


  }
}