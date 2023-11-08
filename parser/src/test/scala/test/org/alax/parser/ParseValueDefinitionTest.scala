package test.org.alax.parser

import org.alax.ast.LanguageParser.{AS, ValueDefinitionContext}
import org.alax.ast.base.model
import org.alax.ast.base.model.Node.Metadata
import org.alax.ast.base.model.Partial.Name
import org.alax.ast.base.model.Partial
import org.alax.ast.base.model.{ParseError, Statement}
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
    "java.lang.Boolean bool:true;" should {
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`java.lang.Boolean bool=true;`)
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
            initialization mustBe a[Literals.Boolean]
            inside(initialization) {
              case Literals.Boolean(value, _) => value mustBe true
            }
        }
      }
    }

    "java.lang.Character char :'a';" should{
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`java.lang.Character char ='a';`)
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
            initialization mustBe a[Literals.Character]
            inside(initialization) {
              case Literals.Character(value, _) => value mustBe 'a'
            }
        }
      }
    }
    "java.lang.String string: \"asd\"  ;" should {
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`java.lang.String string= "asd"  ;`)
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
            initialization mustBe a[Literals.String]
            inside(initialization) {
              case Literals.String(value, _) => value mustBe "asd"
            }
        }
      }
    }
    "Integer int   : -3;" should {
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`Integer int   = -3;`)
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
            initialization mustBe a[Literals.Integer]
            inside(initialization) {
              case Literals.Integer(value, _) => value mustBe -3
            }
        }
      }
    }
    "Float float: -3.12;" should {
      "parse to value definition" in {
        val result = AntlrSupport.language.tokenize(fixture.value.definition.literal.`Float float= -3.12;`)
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
            initialization mustBe a[Literals.Float]
            inside(initialization) {
              case Literals.Float(value, _) => value mustBe -3.12
            }
        }
      }
    }

  }

}
