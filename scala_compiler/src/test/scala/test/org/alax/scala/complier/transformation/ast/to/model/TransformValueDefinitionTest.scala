package test.org.alax.scala.complier.transformation.ast.to.model

import org.alax.ast
import org.scalatest.wordspec.AnyWordSpec
import org.alax.scala.compiler
import org.alax.scala.compiler.model.Value
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import test.org.alax.scala.complier.transformation.ast.to.model.fixture.Model
import test.org.alax.scala.complier.transformation.ast.to.model.fixture.Ast.Value.Definition.*

class TransformValueDefinitionTest extends AnyWordSpec {

  val astTransformer = AstToModelTransformer();
  val ast = `Integer int = 4;`
  "Integer int = 4;" when {
    val `package` = Model.Context.`package with import = scala.lang.Integer`
    "in context of package with import" must {
      val result = astTransformer.transform.value.definition(valueDefinition = `Integer int = 4;`, context = `package`)
      "transform to Definition " in {
        result mustBe a[Value.Definition];
        inside(result) {
          case definition: Value.Definition =>
            definition mustBe Model.Statement.Definition.`val int: scala.lang.Integer = 4`;
          case other => fail(s"Invalid definition type for: ${other}");

        }
      }
    }
  }
}
