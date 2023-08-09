package test.org.alax.scala.complier.model

import org.alax.scala.compiler.model.literals
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.shouldBe
import test.org.alax.scala.complier.model.fixture.literal

import scala.language.postfixOps;

class LiteralTest extends AnyWordSpec {

  "Literal" when {
    "4.21 float value, then" should {
      "correspond" in {
        val (source: literals.Float, target: String) = fixture.literal.`4.21`;
        source.scala shouldBe target

      }
    }
    "'a' char value, then" should {
      "correspond" in {
        val (source: literals.Character, target: String) = fixture.literal.a;
        source.scala shouldBe target

      }
    }
    "true boolean value, then" should {
      "correspond" in {
        val (source: literals.Boolean, target: String) = fixture.literal.`true`;
        source.scala shouldBe target

      }
    }
    "str string value, then" should {
      "correspond" in {
        val (source: literals.String, target: String) = fixture.literal.`str`;
        source.scala shouldBe target

      }
    }
  }

}
