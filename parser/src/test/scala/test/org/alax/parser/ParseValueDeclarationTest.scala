package test.org.alax.parser

import org.alax.ast
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.junit.jupiter.api.Test
import org.alax.ast.{LanguageLexer, LanguageParser}
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.parser.base.AntlrSupport
import org.scalatest.Inside.inside

class ParseValueDeclarationTest extends AnyWordSpec {


  "text" when {
    "java.lang.String value;" should {
      "parse to value declaration" in {
        val result = AntlrSupport.language.tokenize(fixture.value.declaration.`java.lang.String value;`)
          .context((parser: LanguageParser) => parser.valueDeclaration())
          .visit((visitor, context) => visitor.visitValueDeclaration(context))
        result mustBe a[ast.Value.Declaration];
        inside(result.asInstanceOf[ast.Value.Declaration]) {
          case ast.Value.Declaration(name: ast.Value.Name, tpe: ast.Value.Type.Reference, _) =>
              name.text() mustBe "value"
              tpe mustBe a[ast.Value.Type.Reference]
              inside(tpe){
                case ast.Value.Type.Reference(id,_) =>
                  id.text() mustBe "java.lang.String"
              }
        }
      }
    }

    "Integer value;" should {
      "parse to value declaration" in {
        val result = AntlrSupport.language.tokenize(fixture.value.declaration.`Integer value;`)
          .context((parser: LanguageParser) => parser.valueDeclaration())
          .visit((visitor, context) => visitor.visitValueDeclaration(context))
        result mustBe a[ast.Value.Declaration];
        inside(result.asInstanceOf[ast.Value.Declaration]) {
          case ast.Value.Declaration(name: ast.Value.Name, tpe: ast.Value.Type.Reference, _) =>
            name.text() mustBe "value"
            tpe mustBe a[ast.Value.Type.Reference]
            inside(tpe) {
              case ast.Value.Type.Reference(id, _) =>
                id.text() mustBe "Integer"
            }
        }
      }
    }
    "Long Integer some long value;" should {
      "parse to value declaration" in {
        val result = AntlrSupport.language.tokenize(fixture.value.declaration.`Long Integer some long value;`)
          .context((parser: LanguageParser) => parser.valueDeclaration())
          .visit((visitor, context) => visitor.visitValueDeclaration(context))
        result mustBe a[ast.Value.Declaration];
        inside(result.asInstanceOf[ast.Value.Declaration]) {
          case ast.Value.Declaration(name: ast.Value.Name, tpe: ast.Value.Type.Reference, _) =>
            name.text() mustBe "some long value"
            tpe mustBe a[ast.Value.Type.Reference]
            inside(tpe) {
              case ast.Value.Type.Reference(id, _) =>
                id.text() mustBe "Long Integer"
            }
        }
      }
    }
  }


}
