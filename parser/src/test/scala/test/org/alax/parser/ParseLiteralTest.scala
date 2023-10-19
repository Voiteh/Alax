package test.org.alax.parser

import org.alax.parser.LanguageVisitor
import org.junit.jupiter.api.Test
import org.antlr.v4.runtime.{CharStream, CharStreams, CommonTokenStream}
import org.alax.ast.{LanguageLexer, LanguageParser}
import org.alax.ast.model.*
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
        result mustBe a[Expression.Literal.Boolean];
        inside(result.asInstanceOf[Expression.Literal.Boolean]) {
          case Expression.Literal.Boolean(value, _) => value mustBe true
        }
      }
    }

    "'a'" should {
      "parse to literal" in {
        val result = AntlrSupport.language.tokenize(fixture.literal.`'a'`)
          .context((parser: LanguageParser) => parser.literalExpression())
          .visit((visitor, context) => visitor.visitLiteralExpression(context))
        result mustBe a[Expression.Literal.Character];
        inside(result.asInstanceOf[Expression.Literal.Character]) {
          case Expression.Literal.Character(value, _) => value mustBe 'a'
        }
      }
    }

    "-10" should {
      "parse to literal" in {
        val result = AntlrSupport.language.tokenize(fixture.literal.`-10`)
          .context((parser: LanguageParser) => parser.literalExpression())
          .visit((visitor, context) => visitor.visitLiteralExpression(context))
        result mustBe a[Expression.Literal.Integer];
        inside(result.asInstanceOf[Expression.Literal.Integer]) {
          case Expression.Literal.Integer(value, _) => value mustBe -10
        }
      }
    }
    "-99.123" should {
      "parse to literal" in {
        val result = AntlrSupport.language.tokenize(fixture.literal.`-99.123`)
          .context((parser: LanguageParser) => parser.literalExpression())
          .visit((visitor, context) => visitor.visitLiteralExpression(context))
        result mustBe a[Expression.Literal.Float];
        inside(result.asInstanceOf[Expression.Literal.Float]) {
          case Expression.Literal.Float(value, _) => value mustBe -99.123
        }
      }
    }
    "\"str\"" should {
      "parse to literal" in {
        val result = AntlrSupport.language.tokenize(fixture.literal.`"str"`)
          .context((parser: LanguageParser) => parser.literalExpression())
          .visit((visitor, context) => visitor.visitLiteralExpression(context))
        result mustBe a[Expression.Literal.String];
        inside(result.asInstanceOf[Expression.Literal.String]) {
          case Expression.Literal.String(value, _) => value mustBe "str"
        }
      }
    }
  }

}
