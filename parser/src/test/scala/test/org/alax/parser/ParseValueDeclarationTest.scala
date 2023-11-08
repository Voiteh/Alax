package test.org.alax.parser

import org.alax.ast.base.model
import org.alax.ast.base.model.Partial.Name
import org.alax.ast.base.model.{Partial, Statement}
import org.alax.parser.LanguageVisitor
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
        result mustBe a[Statement.Declaration.Value];
        inside(result.asInstanceOf[Statement.Declaration.Value]) {
          case Statement.Declaration.Value(name: Partial.Name.LowerCase, tpe: Partial.Type.Reference, _) =>
              name.text() mustBe "value"
              tpe mustBe a[Partial.Type.Reference.Value]
              inside(tpe){
                case Partial.Type.Reference.Value(id,_) =>
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
        result mustBe a[Statement.Declaration.Value];
        inside(result.asInstanceOf[Statement.Declaration.Value]) {
          case Statement.Declaration.Value(name: Partial.Name.LowerCase, tpe: Partial.Type.Reference.Value, _) =>
            name.text() mustBe "value"
            tpe mustBe a[Partial.Type.Reference.Value]
            inside(tpe) {
              case Partial.Type.Reference.Value(id, _) =>
                id.text() mustBe "Integer"
            }
        }
      }
    }
  }


}
