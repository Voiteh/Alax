package test.org.alax.parser

import org.alax.ast
import org.alax.ast.base.ParseError
import org.alax.parser.LanguageVisitor
import org.junit.jupiter.api.Test
import org.antlr.v4.runtime.{CharStream, CharStreams, CommonTokenStream}
import org.alax.ast.{LanguageLexer, LanguageParser, Literals}
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.parser.base.AntlrSupport
import org.scalatest.Inside.inside

class ParseIdentifierTest extends AnyWordSpec {

  "text" when {
    s"${fixture.identifier.`asd`}" must {
      "parse to identifier" in {
        val result = AntlrSupport.language.tokenize(fixture.identifier.`asd`)
          .context((parser: LanguageParser) => parser.spacefullLowercaseIdentifier())
          .visit((visitor, context) => visitor.visitSpacefullLowercaseIdentifier(context))
        inside(result) {
          case lowercase:ast.partial.Identifier.SpaceFull.LowerCase => lowercase.text() mustBe fixture.identifier.`asd`
        }
      }
    }
    s"${fixture.identifier.`a_sd 123`}" must {
      "parse to identifier" in {
        val result = AntlrSupport.language.tokenize(fixture.identifier.`a_sd 123`)
          .context((parser: LanguageParser) => parser.spacefullLowercaseIdentifier())
          .visit((visitor, context) => visitor.visitSpacefullLowercaseIdentifier(context))
        inside(result) {
          case lowercase: ast.partial.Identifier.SpaceFull.LowerCase =>
            lowercase.text() mustBe fixture.identifier.`a_sd 123`
        }
      }
    }
    s"${fixture.identifier.`123`}" must {
      "parse to identifier" in {
        val result = AntlrSupport.language.tokenize(fixture.identifier.`a_sd 123`)
          .context((parser: LanguageParser) => parser.spacefullLowercaseIdentifier())
          .visit((visitor, context) => visitor.visitSpacefullLowercaseIdentifier(context))
        inside(result) {
          case error: ParseError=> error.message mustBe s"Invalid lowercase identifier: ${fixture.identifier.`123`}"
        }
      }
    }
    s"${fixture.identifier.`_ad`}" must {
      "parse to identifier" in {
        val result = AntlrSupport.language.tokenize(fixture.identifier.`a_sd 123`)
          .context((parser: LanguageParser) => parser.spacefullLowercaseIdentifier())
          .visit((visitor, context) => visitor.visitSpacefullLowercaseIdentifier(context))
        inside(result) {
          case error: ParseError => error.message mustBe s"Invalid lowercase identifier: ${fixture.identifier.`_ad`}"
        }
      }
    }


  }


}
