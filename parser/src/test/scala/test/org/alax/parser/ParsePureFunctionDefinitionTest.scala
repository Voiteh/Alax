package test.org.alax.parser

import org.alax.ast
import org.alax.ast.LanguageParser
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.mustBe
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.parser.base.AntlrSupport

import scala.language.postfixOps

class ParsePureFunctionDefinitionTest extends AnyWordSpec {
  "text" when {
    s"${fixture.function.pure.definition.`function String abc(String one)=>one;`}" must {
      "parse to pure function definition" in {
        val result = AntlrSupport.language.tokenize(fixture.function.pure.definition.`function String abc(String one)=>one;`.text)
          .context((parser: LanguageParser) => parser.pureFunctionDefinition())
          .visit((visitor, context) => visitor.visitPureFunctionDefinition(context))

        inside(result) {
          case definition: ast.Function.Pure.Definition =>
            definition mustBe fixture.function.pure.definition.`function String abc(String one)=>one;`.node
        }

      }
    }
  }

}
