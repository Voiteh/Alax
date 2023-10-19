package test.org.alax.scala.complier.model


import org.alax.scala.compiler.model.Value
import org.scalatest.matchers.should.Matchers.shouldBe
import org.scalatest.wordspec.AnyWordSpec

import scala.language.postfixOps;

class ValueTest extends AnyWordSpec {
  type ScalaSourceCode = String
  "Value" when {
    "declared fully qualified, then" should {
      "correspond" in {
        val (source: Value.Declaration, target: ScalaSourceCode) = fixture.value.declaration.`java.lang.String abc`;
        source.scala.toString() shouldBe target

      }
    }
    "declared fully simple, then" should {
      "correspond" in {
        val (source: Value.Declaration, target: ScalaSourceCode) = fixture.value.declaration.`Integer def`;
        source.scala.toString() shouldBe target

      }
    }
  }

}
