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

class ParseUppercaseIdentifierTest extends AnyWordSpec {

  "text" when {
    s"${fixture.identifier.uppercase.`Asd`}" must {
      "parse to uppercase identifier" in {
        val result = AntlrSupport.language.tokenize(fixture.identifier.uppercase.`Asd`)
          .context((parser: LanguageParser) => parser.uppercaseIdentifier())
          .visit((visitor, context) => visitor.visitUppercaseIdentifier(context))
        inside(result) {
          case id:ast.Identifier.UpperCase => id.text mustBe fixture.identifier.uppercase.`Asd`
        }
      }
    }
    s"${fixture.identifier.uppercase.`A_sd Def`}" must {
      "parse to uppercase identifier" in {
        val result = AntlrSupport.language.tokenize(fixture.identifier.uppercase.`A_sd Def`)
          .context((parser: LanguageParser) => parser.uppercaseIdentifier())
          .visit((visitor, context) => visitor.visitUppercaseIdentifier(context))
        inside(result) {
          case uppercase: ast.Identifier.UpperCase =>
            uppercase.text mustBe fixture.identifier.uppercase.`A_sd Def`
        }
      }
    }
    s"${fixture.identifier.uppercase.`123`}" must {
      "parse to uppercase identifier" in {
        val result = AntlrSupport.language.tokenize(fixture.identifier.uppercase.`123`)
          .context((parser: LanguageParser) => parser.uppercaseIdentifier())
          .visit((visitor, context) => visitor.visitUppercaseIdentifier(context))
        inside(result) {
          case error: ParseError=> error.message mustBe s"Invalid uppercase identifier"
        }
      }
    }
    s"${fixture.identifier.uppercase.`_Ad`}" must {
      "parse to uppercase identifier" in {
        val result = AntlrSupport.language.tokenize(fixture.identifier.uppercase.`_Ad`)
          .context((parser: LanguageParser) => parser.uppercaseIdentifier())
          .visit((visitor, context) => visitor.visitUppercaseIdentifier(context))
        inside(result) {
          case error: ParseError => error.message mustBe s"Invalid uppercase identifier"
        }
      }
    }


  }


}
