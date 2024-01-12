package test.org.alax.parser

import org.alax.ast
import org.alax.ast.LanguageParser
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.parser.base.AntlrSupport

class ParseModuleDeclarationTest extends AnyWordSpec {
  "text" when {
    s"${fixture.module.declaration.`module com.ble.ble;`}" must {
      "parse to module declaration" in {
        val result = AntlrSupport.language.tokenize(fixture.module.declaration.`module com.ble.ble;`)
          .context((parser: LanguageParser) => parser.moduleDeclaration())
          .visit((visitor, context) => visitor.visitModuleDeclaration(context))
        result mustBe a[ast.Module.Declaration]
        inside(result.asInstanceOf[ast.Module.Declaration]) {
          case moduleDeclaration: ast.Module.Declaration => {
            moduleDeclaration.name.text() mustBe "com.ble.ble"
          }
        }

      }
    }
  }
}
