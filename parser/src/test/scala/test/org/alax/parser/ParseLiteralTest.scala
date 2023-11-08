package test.org.alax.parser

import org.alax.parser.LanguageVisitor
import org.junit.jupiter.api.Test
import org.antlr.v4.runtime.{CharStream, CharStreams, CommonTokenStream}
import org.alax.ast.{LanguageLexer, LanguageParser, Literals}
import org.alax.ast.base.model.*
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.parser.base.AntlrSupport
import org.scalatest.Inside.inside

class ParseLiteralTest extends AnyWordSpec {

  "text" when {
    "true" should {
      "parse to literal" in {
        val result = AntlrSupport.language.tokenize(fixture.literal.`true`)
          .context((parser: LanguageParser) => parser.literalExpression())
          .visit((visitor, context) => visitor.visitLiteralExpression(context))
        result mustBe a[Literals.Boolean];
        inside(result.asInstanceOf[Literals.Boolean]) {
          case Literals.Boolean(value, _) => value mustBe true
        }
      }
    }

    "'a'" should {
      "parse to literal" in {
        val result = AntlrSupport.language.tokenize(fixture.literal.`'a'`)
          .context((parser: LanguageParser) => parser.literalExpression())
          .visit((visitor, context) => visitor.visitLiteralExpression(context))
        result mustBe a[Literals.Character];
        inside(result.asInstanceOf[Literals.Character]) {
          case Literals.Character(value, _) => value mustBe 'a'
        }
      }
    }

    "-10" should {
      "parse to literal" in {
        val result = AntlrSupport.language.tokenize(fixture.literal.`-10`)
          .context((parser: LanguageParser) => parser.literalExpression())
          .visit((visitor, context) => visitor.visitLiteralExpression(context))
        result mustBe a[Literals.Integer];
        inside(result.asInstanceOf[Literals.Integer]) {
          case Literals.Integer(value, _) => value mustBe -10
        }
      }
    }
    "-99.123" should {
      "parse to literal" in {
        val result = AntlrSupport.language.tokenize(fixture.literal.`-99.123`)
          .context((parser: LanguageParser) => parser.literalExpression())
          .visit((visitor, context) => visitor.visitLiteralExpression(context))
        result mustBe a[Literals.Float];
        inside(result.asInstanceOf[Literals.Float]) {
          case Literals.Float(value, _) => value mustBe -99.123
        }
      }
    }
    "\"str\"" should {
      "parse to literal" in {
        val result = AntlrSupport.language.tokenize(fixture.literal.`"str"`)
          .context((parser: LanguageParser) => parser.literalExpression())
          .visit((visitor, context) => visitor.visitLiteralExpression(context))
        result mustBe a[Literals.String];
        inside(result.asInstanceOf[Literals.String]) {
          case Literals.String(value, _) => value mustBe "str"
        }
      }
    }
  }

}
