package test.org.alax.parser

import org.alax.ast
import org.alax.ast.base.ParseError
import org.alax.ast.{LanguageLexer, LanguageParser, Literals}
import org.alax.parser.LanguageVisitor
import org.antlr.v4.runtime.{CharStream, CharStreams, CommonTokenStream}
import org.junit.jupiter.api.Test
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.parser.base.AntlrSupport

class ParseLowercaseIdentifierTest extends AnyWordSpec {

  "text" when {
    s"${fixture.identifier.lowercase.`asd`}" must {
      "parse to lowercase identifier" in {
        val result = AntlrSupport.language.tokenize(fixture.identifier.lowercase.`asd`)
          .context((parser: LanguageParser) => parser.lowercaseIdentifier())
          .visit((visitor, context) => visitor.visitLowercaseIdentifier(context))
        inside(result) {
          case id:ast.Identifier.LowerCase => id.text mustBe fixture.identifier.lowercase.`asd`
        }
      }
    }
    s"${fixture.identifier.lowercase.`a_sd 123`}" must {
      "parse to lowercase identifier" in {
        val result = AntlrSupport.language.tokenize(fixture.identifier.lowercase.`a_sd 123`)
          .context((parser: LanguageParser) => parser.lowercaseIdentifier())
          .visit((visitor, context) => visitor.visitLowercaseIdentifier(context))
        inside(result) {
          case lowercase: ast.Identifier.LowerCase =>
            lowercase.text mustBe fixture.identifier.lowercase.`a_sd 123`
        }
      }
    }
    s"${fixture.identifier.lowercase.`123`}" must {
      "parse to lowercase identifier" in {
        val result = AntlrSupport.language.tokenize(fixture.identifier.lowercase.`123`)
          .context((parser: LanguageParser) => parser.lowercaseIdentifier())
          .visit((visitor, context) => visitor.visitLowercaseIdentifier(context))
        inside(result) {
          case error: ParseError=> error.message mustBe s"Invalid lowercase identifier"
        }
      }
    }
    s"${fixture.identifier.lowercase.`_ad`}" must {
      "parse to lowercase identifier" in {
        val result = AntlrSupport.language.tokenize(fixture.identifier.lowercase.`_ad`)
          .context((parser: LanguageParser) => parser.lowercaseIdentifier())
          .visit((visitor, context) => visitor.visitLowercaseIdentifier(context))
        inside(result) {
          case error: ParseError => error.message mustBe s"Invalid lowercase identifier"
        }
      }
    }


  }


}
