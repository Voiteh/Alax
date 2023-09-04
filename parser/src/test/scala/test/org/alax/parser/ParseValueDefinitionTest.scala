package test.org.alax.parser

import org.alax.ast.LanguageParser.{AS, ValueDefinitionContext}
import org.alax.ast.model.Node.Metadata
import org.alax.ast.model.Partial.Name
import org.alax.ast.model.Partial.types.ValueTypeReference
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
            typeReference mustBe a[ValueTypeReference]
            inside(typeReference.asInstanceOf[ValueTypeReference]) {
              case ValueTypeReference(id, _) =>
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
  }

//  @Test
//  def parseValueWithBooleanInitializationDeclaration(): Unit = {
//    val lexer = LanguageLexer(CharStreams.fromString(fixture.declaration.value.withInitialization.literal.bool));
//    val tokens = new CommonTokenStream(lexer)
//    val parser = new LanguageParser(tokens);
//    val ctx = parser.valueDefinition();
//    val result = LanguageVisitor(tokens).visitValueDefinition(ctx);
//
//    assert(result.isInstanceOf[model.Statement.Definition.Value]);
//    val cast = result.asInstanceOf[model.Statement.Definition.Value];
//    assert(cast.name.value == "bool");
//    assert(cast.typeReference.isInstanceOf[model.Partial.types.ValueTypeReference]);
//    assert(cast.typeReference.asInstanceOf[model.Partial.types.ValueTypeReference].id.asInstanceOf[model.Partial.Name.Qualified].text() == "java.lang.Boolean");
//    assert(cast.initialization.isInstanceOf[model.Expression.Literal.Boolean]);
//    assert(cast.initialization.asInstanceOf[model.Expression.Literal.Boolean].value == true);
//
//  }
//
//  @Test
//  def parseValueWithCharInitializationDeclaration(): Unit = {
//    val lexer = LanguageLexer(CharStreams.fromString(fixture.declaration.value.withInitialization.literal.char));
//    val tokens = new CommonTokenStream(lexer)
//    val parser = new LanguageParser(tokens);
//    val ctx = parser.valueDefinition();
//    val result = LanguageVisitor(tokens).visitValueDefinition(ctx);
//
//    assert(result.isInstanceOf[model.Statement.Definition.Value]);
//    val cast = result.asInstanceOf[model.Statement.Definition.Value];
//    assert(cast.name.value == "char");
//    assert(cast.typeReference.isInstanceOf[model.Partial.types.ValueTypeReference]);
//    assert(cast.typeReference.asInstanceOf[model.Partial.types.ValueTypeReference].id.asInstanceOf[model.Partial.Name.Qualified].text() == "java.lang.Character");
//    assert(cast.initialization.isInstanceOf[model.Expression.Literal.Character]);
//    assert(cast.initialization.asInstanceOf[model.Expression.Literal.Character].value == 'a');
//  }
//
//  @Test
//  def parseValueWithIntegerInitializationDeclaration(): Unit = {
//    val lexer = LanguageLexer(CharStreams.fromString(fixture.declaration.value.withInitialization.literal.integer));
//    val tokens = new CommonTokenStream(lexer)
//    val parser = new LanguageParser(tokens);
//    val ctx = parser.valueDefinition();
//    val result = LanguageVisitor(tokens).visitValueDefinition(ctx);
//
//    assert(result.isInstanceOf[model.Statement.Definition.Value]);
//    val cast = result.asInstanceOf[model.Statement.Definition.Value];
//    assert(cast.name.value == "int");
//    assert(cast.typeReference.isInstanceOf[model.Partial.types.ValueTypeReference]);
//    val value = cast.typeReference.asInstanceOf[model.Partial.types.ValueTypeReference];
//    assert(value.id.isInstanceOf[model.Partial.Name.Qualified]);
//    val valueName = value.id.asInstanceOf[model.Partial.Name.Qualified];
//    assert(valueName.text() == "java.lang.Integer");
//    assert(cast.initialization.isInstanceOf[model.Expression.Literal.Integer]);
//    assert(cast.initialization.asInstanceOf[model.Expression.Literal.Integer].value == -3);
//  }
//
//  @Test
//  def parseValueWithFloatInitializationDeclaration(): Unit = {
//    val lexer = LanguageLexer(CharStreams.fromString(fixture.declaration.value.withInitialization.literal.float));
//    val tokens = new CommonTokenStream(lexer)
//    val parser = new LanguageParser(tokens);
//    val ctx = parser.valueDefinition();
//    val result = LanguageVisitor(tokens).visitValueDefinition(ctx);
//
//    assert(result.isInstanceOf[model.Statement.Definition.Value]);
//    val cast = result.asInstanceOf[model.Statement.Definition.Value];
//    assert(cast.name.value == "float");
//    assert(cast.typeReference.isInstanceOf[model.Partial.types.ValueTypeReference]);
//    assert(cast.typeReference.asInstanceOf[model.Partial.types.ValueTypeReference].id.asInstanceOf[model.Partial.Name.Qualified].text() == "java.lang.Float");
//    assert(cast.initialization.isInstanceOf[model.Expression.Literal.Float]);
//    assert(cast.initialization.asInstanceOf[model.Expression.Literal.Float].value == -3.12);
//  }
//
//  @Test
//  def parseValueWithStringInitializationDeclaration(): Unit = {
//    val lexer = LanguageLexer(CharStreams.fromString(fixture.declaration.value.withInitialization.literal.string));
//    val tokens = new CommonTokenStream(lexer)
//    val parser = new LanguageParser(tokens);
//    val ctx = parser.valueDefinition();
//    val result = LanguageVisitor(tokens).visitValueDefinition(ctx);
//
//    assert(result.isInstanceOf[model.Statement.Definition.Value]);
//    val cast = result.asInstanceOf[model.Statement.Definition.Value];
//    assert(cast.name.value == "string");
//    assert(cast.typeReference.isInstanceOf[model.Partial.types.ValueTypeReference]);
//    assert(cast.typeReference.asInstanceOf[model.Partial.types.ValueTypeReference].id.asInstanceOf[model.Partial.Name.Qualified].text() == "java.lang.String");
//    assert(cast.initialization.isInstanceOf[model.Expression.Literal.String]);
//    assert(cast.initialization.asInstanceOf[model.Expression.Literal.String].value == "asd");
//  }
}
