package test.org.alax.parser

import org.alax.ast.LanguageParser.{AS, ValueDefinitionContext}
import org.alax.ast.model.Node.Metadata
import org.alax.ast.model.Partial.Name
import org.alax.ast.model.Partial
import org.alax.ast.model.{ParseError, Statement}
import org.alax.ast.{LanguageLexer, LanguageParser, model}
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
    "java.lang.Boolean bool:true;" should {
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`java.lang.Boolean bool:true;`)
          .context( (parser:LanguageParser )=>  parser.valueDefinition())
          .visit((visitor,context)=>visitor.visitValueDefinition(context))
        result mustBe a[Statement.Definition.Value];
        inside(result.asInstanceOf[Statement.Definition.Value]) {
          case Statement.Definition.Value(name, typeReference, initialization, _) =>
            name.text() mustBe "bool"
            typeReference mustBe a[Partial.Type.Reference.Value]
            inside(typeReference) {
              case Partial.Type.Reference.Value(id, _) =>
                id mustBe a[model.Partial.Name.Qualified]
                id.text() mustBe "java.lang.Boolean"

            }
            initialization mustBe a[model.Expression.Literal.Boolean]
            inside(initialization) {
              case model.Expression.Literal.Boolean(value, _) => value mustBe true
            }
        }
      }
    }

    "java.lang.Character char :'a';" should{
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`java.lang.Character char :'a';`)
          .context((parser: LanguageParser) => parser.valueDefinition())
          .visit((visitor, context) => visitor.visitValueDefinition(context))
        result mustBe a[Statement.Definition.Value];
        inside(result.asInstanceOf[Statement.Definition.Value]) {
          case Statement.Definition.Value(name, typeReference, initialization, _) =>
            name.text() mustBe "char"
            typeReference mustBe a[Partial.Type.Reference.Value]
            inside(typeReference.asInstanceOf[Partial.Type.Reference.Value]) {
              case Partial.Type.Reference.Value(id, _) =>
                id mustBe a[model.Partial.Name.Qualified]
                id.text() mustBe "java.lang.Character"

            }
            initialization mustBe a[model.Expression.Literal.Character]
            inside(initialization) {
              case model.Expression.Literal.Character(value, _) => value mustBe 'a'
            }
        }
      }
    }
    "java.lang.String string: \"asd\"  ;" should {
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`java.lang.String string: "asd"  ;`)
          .context((parser: LanguageParser) => parser.valueDefinition())
          .visit((visitor, context) => visitor.visitValueDefinition(context))
        result mustBe a[Statement.Definition.Value];
        inside(result.asInstanceOf[Statement.Definition.Value]) {
          case Statement.Definition.Value(name, typeReference, initialization, _) =>
            name.text() mustBe "string"
            typeReference mustBe a[Partial.Type.Reference.Value]
            inside(typeReference.asInstanceOf[Partial.Type.Reference.Value]) {
              case Partial.Type.Reference.Value(id, _) =>
                id mustBe a[model.Partial.Name.Qualified]
                id.text() mustBe "java.lang.String"

            }
            initialization mustBe a[model.Expression.Literal.String]
            inside(initialization) {
              case model.Expression.Literal.String(value, _) => value mustBe "asd"
            }
        }
      }
    }
    "Integer int   : -3;" should {
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`Integer int   : -3;`)
          .context((parser: LanguageParser) => parser.valueDefinition())
          .visit((visitor, context) => visitor.visitValueDefinition(context))
        result mustBe a[Statement.Definition.Value];
        inside(result.asInstanceOf[Statement.Definition.Value]) {
          case Statement.Definition.Value(name, typeReference, initialization, _) =>
            name.text() mustBe "int"
            typeReference mustBe a[Partial.Type.Reference.Value]
            inside(typeReference.asInstanceOf[Partial.Type.Reference.Value]) {
              case Partial.Type.Reference.Value(id, _) =>
                id mustBe a[model.Partial.Name.UpperCase]
                id.text() mustBe "Integer"

            }
            initialization mustBe a[model.Expression.Literal.Integer]
            inside(initialization) {
              case model.Expression.Literal.Integer(value, _) => value mustBe -3
            }
        }
      }
    }
    "Float float: -3.12;" should {
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`Float float: -3.12;`)
          .context((parser: LanguageParser) => parser.valueDefinition())
          .visit((visitor, context) => visitor.visitValueDefinition(context))
        result mustBe a[Statement.Definition.Value];
        inside(result.asInstanceOf[Statement.Definition.Value]) {
          case Statement.Definition.Value(name, typeReference, initialization, _) =>
            name.text() mustBe "float"
            typeReference mustBe a[Partial.Type.Reference.Value]
            inside(typeReference.asInstanceOf[Partial.Type.Reference.Value]) {
              case Partial.Type.Reference.Value(id, _) =>
                id mustBe a[model.Partial.Name.UpperCase]
                id.text() mustBe "Float"

            }
            initialization mustBe a[model.Expression.Literal.Float]
            inside(initialization) {
              case model.Expression.Literal.Float(value, _) => value mustBe -3.12
            }
        }
      }
    }

  }

}
