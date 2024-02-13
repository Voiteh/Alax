package test.org.alax.parser

import org.alax.ast
import org.alax.parser.LanguageVisitor
import org.junit.jupiter.api.Test
import org.antlr.v4.runtime.{CharStream, CharStreams, CommonTokenStream}
import org.alax.ast.{LanguageLexer, LanguageParser, Literals, Value}
import org.scalatest.matchers.must.Matchers.{a, contain, have, must, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.parser.base.AntlrSupport
import org.scalatest.Inside.inside

class ParsePackageDefinitionTest extends AnyWordSpec {
  "text" when {
    s"${fixture.`package`.definition.`package abc {java.lang.Boolean bool=true;}`}" must {
      "parse to package definition" in {
        val result = AntlrSupport.language.tokenize(fixture.`package`.definition.`package abc {java.lang.Boolean bool=true;}`)
          .context((parser: LanguageParser) => parser.packageDefinition())
          .visit((visitor, context) => visitor.visitPackageDefinition(context))
        result mustBe a[ast.Package.Definition]
        inside(result.asInstanceOf[ast.Package.Definition]) {
          case packageDefinition: ast.Package.Definition => {
            packageDefinition.identifier.text() mustBe "abc"
            packageDefinition.body.elements must have length 1
            packageDefinition.body.elements must contain(
              AntlrSupport.language.tokenize(fixture.value.definition.literal.`java.lang.Boolean bool=true;`)
                .context((parser: LanguageParser) => parser.valueDefinition())
                .visit((visitor:LanguageVisitor, context: LanguageParser.ValueDefinitionContext) => visitor.visitValueDefinition(context))
            )
          }
        }

      }
    }
  }
}
