package test.org.alax.parser

import org.alax.ast
import org.alax.ast.LanguageParser
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.mustBe
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.parser.base.AntlrSupport

import scala.language.postfixOps

class ParseProcedureDefinitionTest extends AnyWordSpec {
  "text" when {
    s"${fixture.procedure.definition.`procedure abc(String one)=!>one;`}" must {
      "parse to pure function definition" in {
        val result = AntlrSupport.language.tokenize(fixture.procedure.definition.`procedure abc(String one)=!>one;`.text)
          .context((parser: LanguageParser) => parser.procedureDefinition())
          .visit((visitor, context) => visitor.visitProcedureDefinition(context))

        inside(result) {
          case definition: ast.Procedure.Definition =>
            definition mustBe fixture.procedure.definition.`procedure abc(String one)=!>one;`.node
        }

      }
    }
  }

}
