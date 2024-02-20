package test.org.alax.parser

import org.alax.ast
import org.alax.ast.LanguageParser
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.a
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import test.org.alax.parser.base.AntlrSupport

class ParsePackageDeclarationTest extends AnyWordSpec {
  "text" when {
    s"${fixture.`package`.declaration.`package abc;`}" must {
      "parse to package declaration" in {
        val result = AntlrSupport.language.tokenize(fixture.`package`.declaration.`package abc;`)
          .context((parser: LanguageParser) => parser.packageDeclaration())
          .visit((visitor, context) => visitor.visitPackageDeclaration(context))
        result mustBe a[ast.Package.Declaration]
        inside(result.asInstanceOf[ast.Package.Declaration]) {
          case packageDeclaration: ast.Package.Declaration => {
            packageDeclaration.identifier.text mustBe "abc"
          }
        }

      }
    }
  }
}
