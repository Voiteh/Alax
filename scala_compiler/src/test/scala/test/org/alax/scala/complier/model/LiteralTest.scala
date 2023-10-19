package test.org.alax.scala.complier.model

import org.alax.scala.compiler.model.Literal
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.shouldBe
import test.org.alax.scala.complier.model.fixture.literal

import scala.language.postfixOps;

class LiteralTest extends AnyWordSpec {

  "Literal" when {
    "4.21 float value, then" should {
      "correspond" in {
        val (source: Literal.Float, target: String) = fixture.literal.`4.21`;
        source.scala.toString() shouldBe target

      }
    }
    "'a' char value, then" should {
      "correspond" in {
        val (source: Literal.Character, target: String) = fixture.literal.a;
        source.scala.toString() shouldBe target

      }
    }
    "true boolean value, then" should {
      "correspond" in {
        val (source: Literal.Boolean, target: String) = fixture.literal.`true`;
        source.scala.toString() shouldBe target

      }
    }
    "str string value, then" should {
      "correspond" in {
        val (source: Literal.String, target: String) = fixture.literal.`str`;
        source.scala.toString() shouldBe target

      }
    }
  }

}
