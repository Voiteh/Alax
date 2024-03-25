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
//    s"${fixture.value.declaration.`value java.lang.String item;`}" should {
//      "parse to value declaration" in {
//        val result = AntlrSupport.language.tokenize(fixture.value.declaration.`value java.lang.String item;`)
//          .context((parser: LanguageParser) => parser.valueDeclaration())
//          .visit((visitor, context) => visitor.visitValueDeclaration(context))
//        result mustBe a[ast.Value.Declaration];
//        inside(result.asInstanceOf[ast.Value.Declaration]) {
//          case ast.Value.Declaration(name: ast.Value.Identifier, tpe: ast.Value.Type.Identifier, _) =>
//              name.text mustBe "item"
//              tpe mustBe a[ast.Value.Type.Identifier]
//              inside(tpe){
//                case reference: ast.Value.Type.Identifier=>
//                  reference.text mustBe "java.lang.String"
//              }
//        }
//      }
//    }
//
//    s"${fixture.value.declaration.`value Integer item;`}" should {
//      "parse to value declaration" in {
//        val result = AntlrSupport.language.tokenize(fixture.value.declaration.`value Integer item;`)
//          .context((parser: LanguageParser) => parser.valueDeclaration())
//          .visit((visitor, context) => visitor.visitValueDeclaration(context))
//        result mustBe a[ast.Value.Declaration];
//        inside(result.asInstanceOf[ast.Value.Declaration]) {
//          case ast.Value.Declaration(name: ast.Value.Identifier, tpe: ast.Value.Type.Identifier, _) =>
//            name.text mustBe "item"
//            tpe mustBe a[ast.Value.Type.Identifier]
//            inside(tpe) {
//              case typeReference:ast.Value.Type.Identifier =>
//                typeReference.text mustBe "Integer"
//            }
//        }
//      }
//    }
    s"${fixture.value.declaration.`value Long Integer some long item;`}" should {
      "parse to value declaration" in {
        val result = AntlrSupport.language.tokenize(fixture.value.declaration.`value Long Integer some long item;`)
          .context((parser: LanguageParser) => parser.valueDeclaration())
          .visit((visitor, context) => visitor.visitValueDeclaration(context))
        result mustBe a[ast.Value.Declaration];
        inside(result.asInstanceOf[ast.Value.Declaration]) {
          case ast.Value.Declaration(identifier: ast.Evaluable.Identifier, tpe: ast.Value.Type.Identifier, _) =>
            identifier.text mustBe "some long item"
            tpe mustBe a[ast.Value.Type.Identifier]
            inside(tpe) {
              case typeIdentifier:ast.Value.Type.Identifier =>
                typeIdentifier.text mustBe "Long Integer"
            }
        }
      }
    }
  }


}
