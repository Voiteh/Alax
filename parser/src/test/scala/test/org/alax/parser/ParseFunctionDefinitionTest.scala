package test.org.alax.parser

import org.alax.ast
import org.alax.ast.LanguageParser
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.mustBe
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.parser.base.AntlrSupport

import scala.language.postfixOps

class ParseFunctionDefinitionTest extends AnyWordSpec {
  "text" when {
    s"${fixture.function.definition.`function String abc(String one)=>one;`}" must {
      "parse to pure function definition" in {
        val result = AntlrSupport.language.tokenize(fixture.function.definition.`function String abc(String one)=>one;`.text)
          .context((parser: LanguageParser) => parser.functionDefinition())
          .visit((visitor, context) => visitor.visitFunctionDefinition(context))

        inside(result) {
          case definition: ast.Function.Definition =>
            definition mustBe fixture.function.definition.`function String abc(String one)=>one;`.node
        }

      }
    }
  }

}
