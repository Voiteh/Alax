package test.org.alax.scala.complier.scalameta

import org.alax.scala.compiler.model.Literals
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers.shouldBe
import test.org.alax.scala.complier.scalameta.fixture.literal

import scala.language.postfixOps;

class LiteralTest extends AnyWordSpec {

  "Literal" when {
    "4.21 float value, then" should {
      "correspond" in {
        val (source: Literals.Float, target: String) = fixture.literal.`4.21`;
        source.scala.toString() shouldBe target

      }
    }
    "'a' char value, then" should {
      "correspond" in {
        val (source: Literals.Character, target: String) = fixture.literal.a;
        source.scala.toString() shouldBe target

      }
    }
    "true boolean value, then" should {
      "correspond" in {
        val (source: Literals.Boolean, target: String) = fixture.literal.`true`;
        source.scala.toString() shouldBe target

      }
    }
    "str string value, then" should {
      "correspond" in {
        val (source: Literals.String, target: String) = fixture.literal.`str`;
        source.scala.toString() shouldBe target

      }
    }
  }

}
