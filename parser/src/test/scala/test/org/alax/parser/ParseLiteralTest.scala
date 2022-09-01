package test.org.alax.parser
import org.alax.parser.LanguageVisitor
import org.alax.syntax.LanguageLexer
import org.alax.syntax.LanguageParser
import org.junit.jupiter.api.Test;
import fixture.literal;
import org.antlr.v4.runtime.CommonTokenStream
object ParseLiteralTest {


  @BeforeAll
  def setupParser(): Unit ={

  }



  @Test
  def parseBooleanLiteral(): Unit = {
    val lexer =LanguageLexer(literal.boolTrue);
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val literal=parser.literal();

    val visitor = LanguageVisitor().visitLiteral(literal);
  }

}
