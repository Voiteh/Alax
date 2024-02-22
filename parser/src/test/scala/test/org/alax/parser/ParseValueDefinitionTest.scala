package test.org.alax.parser

import org.alax.ast.LanguageParser.{AS, ValueDefinitionContext}
import org.alax.ast.base.Node.Metadata
import org.alax.ast.partial.Identifier
import org.alax.ast.base.{ParseError, Partial}
import org.alax.ast
import org.alax.ast.{LanguageLexer, LanguageParser, Literals}
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.junit.jupiter.api.Test
import org.alax.parser.LanguageVisitor
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.parser.base.AntlrSupport

import scala.runtime.stdLibPatches.Predef.assert
import scala.language.postfixOps;

class ParseValueDefinitionTest extends AnyWordSpec {


  "text" when {
    s"${fixture.value.definition.literal.`value java.lang.Boolean bool=true;`}" must {
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(
          fixture.value.definition.literal.`value java.lang.Boolean bool=true;`
        )
          .context((parser: LanguageParser) => parser.valueDefinition())
          .visit((visitor, context) => visitor.visitValueDefinition(context))
        inside(result) {
          case ast.Value.Definition(name, typeReference, initialization, _) =>
            name.text mustBe "bool"
            inside(typeReference) {
              case typeReference: ast.Value.Type.Identifier => {
                typeReference.text mustBe "java.lang.Boolean"
              }
            }

            inside(initialization) {
              case Literals.Boolean(value, _) => value mustBe true

            }
          case parseError: ParseError => assert(false)
        }
      }
    }

    s"${fixture.value.definition.literal.`value java.lang.Character char ='a';`}" must {
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`value java.lang.Character char ='a';`)
          .context((parser: LanguageParser) => parser.valueDefinition())
          .visit((visitor, context) => visitor.visitValueDefinition(context))
        result mustBe a[ast.Value.Definition];
        inside(result.asInstanceOf[ast.Value.Definition]) {
          case ast.Value.Definition(name, typeReference, initialization, _) =>
            name.text mustBe "char"
            typeReference mustBe a[ast.Value.Type.Identifier]
            inside(typeReference) {
              case typeReference: ast.Value.Type.Identifier =>
                typeReference.text mustBe "java.lang.Character"

            }
            initialization mustBe a[Literals.Character]
            inside(initialization) {
              case Literals.Character(value, _) => value mustBe 'a'
            }
        }
      }
    }
    s"${fixture.value.definition.literal.`value java.lang.String string= \"asd\"  ;`}" must {
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`value java.lang.String string= "asd"  ;`)
          .context((parser: LanguageParser) => parser.valueDefinition())
          .visit((visitor, context) => visitor.visitValueDefinition(context))
        result mustBe a[ast.Value.Definition];
        inside(result.asInstanceOf[ast.Value.Definition]) {
          case ast.Value.Definition(name, typeReference, initialization, _) =>
            name.text mustBe "string"
            typeReference mustBe a[ast.Value.Type.Identifier]
            inside(typeReference) {
              case typeReference: ast.Value.Type.Identifier => {
                typeReference.text mustBe "java.lang.String"
              }

            }
            initialization mustBe a[Literals.String]
            inside(initialization) {
              case Literals.String(value, _) => value mustBe "asd"
            }
        }
      }
    }
    s"${fixture.value.definition.literal.`value Integer int   = -3;`}" must {
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`value Integer int   = -3;`)
          .context((parser: LanguageParser) => parser.valueDefinition())
          .visit((visitor, context) => visitor.visitValueDefinition(context))
        result mustBe a[ast.Value.Definition];
        inside(result.asInstanceOf[ast.Value.Definition]) {
          case ast.Value.Definition(name, typeReference, initialization, _) =>
            name.text mustBe "int"
            typeReference mustBe a[ast.Value.Type.Identifier]
            inside(typeReference) {
              case typeReference: ast.Value.Type.Identifier =>
                typeReference.text mustBe "Integer"

            }
            initialization mustBe a[Literals.Integer]
            inside(initialization) {
              case Literals.Integer(value, _) => value mustBe -3
            }
        }
      }
    }
    s"${fixture.value.definition.literal.`value Float float= -3.12;`}" must {
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`value Float float= -3.12;`)
          .context((parser: LanguageParser) => parser.valueDefinition())
          .visit((visitor, context) => visitor.visitValueDefinition(context))
        result mustBe a[ast.Value.Definition];
        inside(result.asInstanceOf[ast.Value.Definition]) {
          case ast.Value.Definition(name, typeReference, initialization, _) =>
            name.text mustBe "float"
            typeReference mustBe a[ast.Value.Type.Identifier]
            inside(typeReference) {
              case typeReference: ast.Value.Type.Identifier =>
                typeReference.text mustBe "Float"

            }
            initialization mustBe a[Literals.Float]
            inside(initialization) {
              case Literals.Float(value, _) => value mustBe -3.12
            }
        }
      }
    }

  }

}
