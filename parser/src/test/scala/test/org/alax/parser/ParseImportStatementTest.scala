package test.org.alax.parser

import org.alax.parser.LanguageVisitor
import org.junit.jupiter.api.Test
import org.antlr.v4.runtime.{CharStream, CharStreams, CommonTokenStream}
import org.alax.ast.{LanguageLexer, LanguageParser, Literals}
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.parser.base.AntlrSupport
import org.scalatest.Inside.inside
import org.alax.ast
import org.alax.ast.base.ParseError
import scala.language.postfixOps
//TODO rename to something different than declaration
class ParseImportStatementTest extends AnyWordSpec {
  "text" when {
    s"${fixture.`import`.statement.simple.`import java.lang;`}" must  {
      "parse to import declaration " in {
        val result: ast.Imports.Simple|ParseError = AntlrSupport.language.tokenize(fixture.`import`.statement.simple.`import java.lang;`)
          .context((parser: LanguageParser) => parser.simpleImportDeclaration())
          .visit((visitor, context) => visitor.visitSimpleImportDeclaration(context))

        inside(result){
          case simple: ast.Imports.Simple => {
            simple.member.text() mustBe "java.lang"
          }
          case error: ParseError => fail(s"Invalid result:${error}");
        }

      }
    }
    s"${fixture.`import`.statement.simple.`import java.lang.String;`}" must {
      "parse to import declaration " in {
        val result: ast.Imports.Simple | ParseError = AntlrSupport.language.tokenize(fixture.`import`.statement.simple.`import java.lang.String;`)
          .context((parser: LanguageParser) => parser.simpleImportDeclaration())
          .visit((visitor, context) => visitor.visitSimpleImportDeclaration(context))

        inside(result) {
          case simple: ast.Imports.Simple => {
            simple.member.text() mustBe "java.lang.String"
          }
          case error: ParseError => fail(s"Invalid result:${error}");
        }

      }
    }
    s"${fixture.`import`.statement.simple.`import java.lang long.String;`}" must {
      "parse to import declaration " in {
        val result: ast.Imports.Simple | ParseError = AntlrSupport.language.tokenize(fixture.`import`.statement.simple.`import java.lang long.String;`)
          .context((parser: LanguageParser) => parser.simpleImportDeclaration())
          .visit((visitor, context) => visitor.visitSimpleImportDeclaration(context))

        inside(result) {
          case simple: ast.Imports.Simple => {
            simple.member.text() mustBe "java.lang long.String"
          }
          case error: ParseError => fail(s"Invalid result:${error}");
        }

      }
    }
  }
}
