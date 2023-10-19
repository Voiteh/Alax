package test.org.alax.scala.complier.transformation.ast.to.model

import org.alax.ast
import org.scalatest.wordspec.AnyWordSpec
import fixture.Ast.Value.Definition.*
import org.alax.scala.compiler
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}

class TransformValueDefinitionTest extends AnyWordSpec {

  val astTransformer = AstToModelTransformer();
  val ast = fixture.Ast.Value.Definition.`Integer int = 4;`
  "Integer int = 4;" when {
    val `package` = fixture.Context.`package with import = scala.lang.Integer`
    "in context of package with import" must {
      val result = astTransformer.transform.value.definition(valueDefinition = `Integer int = 4;`, context = `package`)
      "transform to Definition " in {
        result mustBe a[compiler.model.Value.Definition];
        inside(result) {
          case definition: compiler.model.Value.Definition =>
            definition mustBe fixture.Statement.Definition.`val int: scala.lang.Integer = 4`;
          case other => fail(s"Invalid definition type for: ${other}");

        }
      }
    }
  }
}
