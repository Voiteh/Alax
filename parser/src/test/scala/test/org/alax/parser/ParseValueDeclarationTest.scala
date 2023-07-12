package test.org.alax.parser

import org.alax.parser.LanguageVisitor
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import test.org.alax.parser.fixture.declaration
import org.junit.jupiter.api.Test
import org.alax.ast._

object ParseValueDeclarationTest {

  @Test
  def parseSimpleValueDeclaration(): Unit = {
    val lexer = LanguageLexer(CharStreams.fromString(declaration.value.simple));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx = parser.valueDeclaration();
    val result = LanguageVisitor(tokens).visitValueDeclaration(ctx);

    assert(result.isInstanceOf[model.statements.declarations.Value]);
    val cast = result.asInstanceOf[model.statements.declarations.Value];
    assert(cast.name.value == "value");
    assert(cast.`type`.isInstanceOf[model.partials.types.Value]);
    assert(cast.`type`.asInstanceOf[model.partials.types.Value].id.asInstanceOf[model.partials.names.Qualified].text() == "java.lang.String");
  }

  @Test
  def parseValueWithBooleanInitializationDeclaration(): Unit ={
    val lexer = LanguageLexer(CharStreams.fromString(declaration.value.withInitialization.literal.bool));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx = parser.valueDeclaratonWithInitialization();
    val result = LanguageVisitor(tokens).visitValueDeclaratonWithInitialization(ctx);

    assert(result.isInstanceOf[model.statements.declarations.ValueWithInitialization]);
    val cast = result.asInstanceOf[model.statements.declarations.ValueWithInitialization];
    assert(cast.name.value == "bool");
    assert(cast.`type`.isInstanceOf[model.partials.types.Value]);
    assert(cast.`type`.asInstanceOf[model.partials.types.Value].id.asInstanceOf[model.partials.names.Qualified].text()== "java.lang.Boolean");
    assert(cast.initialization.isInstanceOf[model.expressions.literals.Boolean]);
    assert(cast.initialization.asInstanceOf[model.expressions.literals.Boolean].value==true);

  }

  @Test
  def parseValueWithCharInitializationDeclaration(): Unit ={
    val lexer = LanguageLexer(CharStreams.fromString(declaration.value.withInitialization.literal.char));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx = parser.valueDeclaratonWithInitialization();
    val result = LanguageVisitor(tokens).visitValueDeclaratonWithInitialization(ctx);

    assert(result.isInstanceOf[model.statements.declarations.ValueWithInitialization]);
    val cast = result.asInstanceOf[model.statements.declarations.ValueWithInitialization];
    assert(cast.name.value == "char");
    assert(cast.`type`.isInstanceOf[model.partials.types.Value]);
    assert(cast.`type`.asInstanceOf[model.partials.types.Value].id.asInstanceOf[model.partials.names.Qualified].text() == "java.lang.Character");
    assert(cast.initialization.isInstanceOf[model.expressions.literals.Character]);
    assert(cast.initialization.asInstanceOf[model.expressions.literals.Character].value=='a');
  }
  @Test
  def parseValueWithIntegerInitializationDeclaration(): Unit ={
    val lexer = LanguageLexer(CharStreams.fromString(declaration.value.withInitialization.literal.integer));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx = parser.valueDeclaratonWithInitialization();
    val result = LanguageVisitor(tokens).visitValueDeclaratonWithInitialization(ctx);

    assert(result.isInstanceOf[model.statements.declarations.ValueWithInitialization]);
    val cast = result.asInstanceOf[model.statements.declarations.ValueWithInitialization];
    assert(cast.name.value == "int");
    assert(cast.`type`.isInstanceOf[model.partials.types.Value]);
    val value =cast.`type`.asInstanceOf[model.partials.types.Value];
    assert(value.id.isInstanceOf[model.partials.names.Qualified]);
    val valueName =value.id.asInstanceOf[model.partials.names.Qualified];
    assert(valueName.text() == "java.lang.Integer");
    assert(cast.initialization.isInstanceOf[model.expressions.literals.Integer]);
    assert(cast.initialization.asInstanceOf[model.expressions.literals.Integer].value == -3);
  }

  @Test
  def parseValueWithFloatInitializationDeclaration(): Unit ={
    val lexer = LanguageLexer(CharStreams.fromString(declaration.value.withInitialization.literal.float));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx = parser.valueDeclaratonWithInitialization();
    val result = LanguageVisitor(tokens).visitValueDeclaratonWithInitialization(ctx);

    assert(result.isInstanceOf[model.statements.declarations.ValueWithInitialization]);
    val cast = result.asInstanceOf[model.statements.declarations.ValueWithInitialization];
    assert(cast.name.value == "float");
    assert(cast.`type`.isInstanceOf[model.partials.types.Value]);
    assert(cast.`type`.asInstanceOf[model.partials.types.Value].id.asInstanceOf[model.partials.names.Qualified].text() == "java.lang.Float");
    assert(cast.initialization.isInstanceOf[model.expressions.literals.Float]);
    assert(cast.initialization.asInstanceOf[model.expressions.literals.Float].value == -3.12);
  }

  @Test
  def parseValueWithStringInitializationDeclaration(): Unit ={
    val lexer = LanguageLexer(CharStreams.fromString(declaration.value.withInitialization.literal.string));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx = parser.valueDeclaratonWithInitialization();
    val result = LanguageVisitor(tokens).visitValueDeclaratonWithInitialization(ctx);

    assert(result.isInstanceOf[model.statements.declarations.ValueWithInitialization]);
    val cast = result.asInstanceOf[model.statements.declarations.ValueWithInitialization];
    assert(cast.name.value == "string");
    assert(cast.`type`.isInstanceOf[model.partials.types.Value]);
    assert(cast.`type`.asInstanceOf[model.partials.types.Value].id.asInstanceOf[model.partials.names.Qualified].text() == "java.lang.String");
    assert(cast.initialization.isInstanceOf[model.expressions.literals.String]);
    assert(cast.initialization.asInstanceOf[model.expressions.literals.String].value=="asd");
  }

}
