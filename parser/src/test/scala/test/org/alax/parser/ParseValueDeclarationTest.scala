package test.org.alax.parser

import org.alax.parser.LanguageVisitor
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.junit.jupiter.api.Test
import org.alax.ast.{model,LanguageLexer,LanguageParser}
//TODO refactor to scala test instead of junit
object ParseValueDeclarationTest {

  @Test
  def parseSimpleValueDeclaration(): Unit = {
    val lexer = LanguageLexer(CharStreams.fromString(fixture.value.declaration.`java.lang.String value;`));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx = parser.valueDeclaration();
    val result = LanguageVisitor(tokens).visitValueDeclaration(ctx);

    assert(result.isInstanceOf[model.Statement.Declaration.Value]);
    val cast = result.asInstanceOf[model.Statement.Declaration.Value];
    assert(cast.name.value == "value");
    assert(cast.`type`.isInstanceOf[model.Partial.types.ValueTypeReference]);
    assert(cast.`type`.asInstanceOf[model.Partial.types.ValueTypeReference].id.asInstanceOf[model.Partial.Name.Qualified].text() == "java.lang.String");
  }



}
