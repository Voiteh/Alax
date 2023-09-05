package test.org.alax.parser

import org.alax.ast.model.Partial.Name
import org.alax.ast.model.{Partial, Statement}
import org.alax.parser.LanguageVisitor
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.junit.jupiter.api.Test
import org.alax.ast.{LanguageLexer, LanguageParser, model}
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
          case Statement.Declaration.Value(name: Partial.Name.LowerCase, tpe: Partial.TypeReference, _) =>
              name.text() mustBe "value"
              tpe mustBe a[Partial.Type.ValueTypeReference]
              inside(tpe.asInstanceOf[Partial.Type.ValueTypeReference]){
                case Partial.Type.ValueTypeReference(id,_) =>
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
          case Statement.Declaration.Value(name: Partial.Name.LowerCase, tpe: Partial.TypeReference, _) =>
            name.text() mustBe "value"
            tpe mustBe a[Partial.Type.ValueTypeReference]
            inside(tpe.asInstanceOf[Partial.Type.ValueTypeReference]) {
              case Partial.Type.ValueTypeReference(id, _) =>
                id.text() mustBe "Integer"
            }
        }
      }
    }
  }


}
