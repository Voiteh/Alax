package test.org.alax.parser

import org.alax.ast
import org.alax.ast.{LanguageLexer, LanguageParser, Literals, Value}
import org.alax.parser.LanguageVisitor
import org.antlr.v4.runtime.{CharStream, CharStreams, CommonTokenStream}
import org.junit.jupiter.api.Test
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, contain, have, must, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.parser.base.AntlrSupport

class ParseModuleDefinitionTest extends AnyWordSpec {
  "text" when {
    s"${fixture.module.definition.`module com.ble.ble {value java.lang.Boolean bool=true;}`}" must {
      "parse to module definition" in {
        val result = AntlrSupport.language.tokenize(fixture.module.definition.`module com.ble.ble {value java.lang.Boolean bool=true;}`)
          .context((parser: LanguageParser) => parser.moduleDefinition())
          .visit((visitor, context) => visitor.visitModuleDefinition(context))
        result mustBe a[ast.Module.Definition]
        inside(result.asInstanceOf[ast.Module.Definition]) {
          case moduleDefinition: ast.Module.Definition => {
            moduleDefinition.identifier.text mustBe "com.ble.ble"
            moduleDefinition.body.elements must have length 1
            moduleDefinition.body.elements must contain(
              AntlrSupport.language.tokenize(fixture.value.definition.literal.`value java.lang.Boolean bool=true;`)
                .context((parser: LanguageParser) => parser.valueDefinition())
                .visit((visitor:LanguageVisitor, context: LanguageParser.ValueDefinitionContext) => visitor.visitValueDefinition(context))
            )
          }
        }

      }
    }
  }
}
