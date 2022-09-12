package test.org.alax.parser

import org.alax.parser.LanguageVisitor
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import test.org.alax.parser.fixture.declaration
import org.alax.model.Value
import org.junit.jupiter.api.Test
import org.alax.syntax._
object ParseValueDeclarationTest {

  @Test
  def parseSimpleValueDeclaration(): Unit ={
    val lexer =LanguageLexer(CharStreams.fromString(declaration.value.simple));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx=parser.valueDeclaration();
    val result = LanguageVisitor(tokens).visitValueDeclaration(ctx);

    assert(result.isInstanceOf[Value.Declaration]);
    val cast=result.asInstanceOf[Value.Declaration];
    assert(cast.name == "value");
    assert(cast.`type`.id.fullyQualifiedName=="java.lang.String")

  }


}
