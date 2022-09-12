package test.org.alax.parser

import org.alax.parser.LanguageVisitor
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import test.org.alax.parser.fixture.declaration
import org.junit.jupiter.api.Test
import org.alax.syntax._

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
    assert(cast.`type`.asInstanceOf[model.partials.types.Value].id == "java.lang.String");
  }


}
