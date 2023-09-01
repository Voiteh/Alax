package test.org.alax.parser

import org.alax.ast.{LanguageLexer, LanguageParser, model}
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.junit.jupiter.api.Test
import org.alax.parser.LanguageVisitor
import scala.runtime.stdLibPatches.Predef.assert

object ParseValueDefinitionTest {
  @Test
  def parseValueWithBooleanInitializationDeclaration(): Unit = {
    val lexer = LanguageLexer(CharStreams.fromString(fixture.declaration.value.withInitialization.literal.bool));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx = parser.valueDefinition();
    val result = LanguageVisitor(tokens).visitValueDefinition(ctx);

    assert(result.isInstanceOf[model.Statement.Definition.Value]);
    val cast = result.asInstanceOf[model.Statement.Definition.Value];
    assert(cast.name.value == "bool");
    assert(cast.`type`.isInstanceOf[model.Partial.types.ValueTypeReference]);
    assert(cast.`type`.asInstanceOf[model.Partial.types.ValueTypeReference].id.asInstanceOf[model.Partial.Name.Qualified].text() == "java.lang.Boolean");
    assert(cast.initialization.isInstanceOf[model.Expression.Literal.Boolean]);
    assert(cast.initialization.asInstanceOf[model.Expression.Literal.Boolean].value == true);

  }

  @Test
  def parseValueWithCharInitializationDeclaration(): Unit = {
    val lexer = LanguageLexer(CharStreams.fromString(fixture.declaration.value.withInitialization.literal.char));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx = parser.valueDefinition();
    val result = LanguageVisitor(tokens).visitValueDefinition(ctx);

    assert(result.isInstanceOf[model.Statement.Definition.Value]);
    val cast = result.asInstanceOf[model.Statement.Definition.Value];
    assert(cast.name.value == "char");
    assert(cast.`type`.isInstanceOf[model.Partial.types.ValueTypeReference]);
    assert(cast.`type`.asInstanceOf[model.Partial.types.ValueTypeReference].id.asInstanceOf[model.Partial.Name.Qualified].text() == "java.lang.Character");
    assert(cast.initialization.isInstanceOf[model.Expression.Literal.Character]);
    assert(cast.initialization.asInstanceOf[model.Expression.Literal.Character].value == 'a');
  }

  @Test
  def parseValueWithIntegerInitializationDeclaration(): Unit = {
    val lexer = LanguageLexer(CharStreams.fromString(fixture.declaration.value.withInitialization.literal.integer));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx = parser.valueDefinition();
    val result = LanguageVisitor(tokens).visitValueDefinition(ctx);

    assert(result.isInstanceOf[model.Statement.Definition.Value]);
    val cast = result.asInstanceOf[model.Statement.Definition.Value];
    assert(cast.name.value == "int");
    assert(cast.`type`.isInstanceOf[model.Partial.types.ValueTypeReference]);
    val value = cast.`type`.asInstanceOf[model.Partial.types.ValueTypeReference];
    assert(value.id.isInstanceOf[model.Partial.Name.Qualified]);
    val valueName = value.id.asInstanceOf[model.Partial.Name.Qualified];
    assert(valueName.text() == "java.lang.Integer");
    assert(cast.initialization.isInstanceOf[model.Expression.Literal.Integer]);
    assert(cast.initialization.asInstanceOf[model.Expression.Literal.Integer].value == -3);
  }

  @Test
  def parseValueWithFloatInitializationDeclaration(): Unit = {
    val lexer = LanguageLexer(CharStreams.fromString(fixture.declaration.value.withInitialization.literal.float));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx = parser.valueDefinition();
    val result = LanguageVisitor(tokens).visitValueDefinition(ctx);

    assert(result.isInstanceOf[model.Statement.Definition.Value]);
    val cast = result.asInstanceOf[model.Statement.Definition.Value];
    assert(cast.name.value == "float");
    assert(cast.`type`.isInstanceOf[model.Partial.types.ValueTypeReference]);
    assert(cast.`type`.asInstanceOf[model.Partial.types.ValueTypeReference].id.asInstanceOf[model.Partial.Name.Qualified].text() == "java.lang.Float");
    assert(cast.initialization.isInstanceOf[model.Expression.Literal.Float]);
    assert(cast.initialization.asInstanceOf[model.Expression.Literal.Float].value == -3.12);
  }

  @Test
  def parseValueWithStringInitializationDeclaration(): Unit = {
    val lexer = LanguageLexer(CharStreams.fromString(fixture.declaration.value.withInitialization.literal.string));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx = parser.valueDefinition();
    val result = LanguageVisitor(tokens).visitValueDefinition(ctx);

    assert(result.isInstanceOf[model.Statement.Definition.Value]);
    val cast = result.asInstanceOf[model.Statement.Definition.Value];
    assert(cast.name.value == "string");
    assert(cast.`type`.isInstanceOf[model.Partial.types.ValueTypeReference]);
    assert(cast.`type`.asInstanceOf[model.Partial.types.ValueTypeReference].id.asInstanceOf[model.Partial.Name.Qualified].text() == "java.lang.String");
    assert(cast.initialization.isInstanceOf[model.Expression.Literal.String]);
    assert(cast.initialization.asInstanceOf[model.Expression.Literal.String].value == "asd");
  }
}
