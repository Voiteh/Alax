package test.org.alax.parser
import org.alax.parser.LanguageVisitor
import org.alax.syntax.LanguageLexer
import org.alax.syntax.LanguageParser
import org.junit.jupiter.api.Test
import fixture.literal
import org.alax.model.{BooleanLiteral, CharacterLiteral, FlaotLiteral, IntegerLiteral}
import org.antlr.v4.runtime.{CharStream, CharStreams, CommonTokenStream}
object ParseLiteralTest {





  @Test
  def parseBooleanLiteral(): Unit = {
    val lexer =LanguageLexer(CharStreams.fromString(literal.boolTrue));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx=parser.literal();

    val result = LanguageVisitor(tokens).visitLiteral(ctx);
    assert(result.isInstanceOf[BooleanLiteral]);
    assert(result.asInstanceOf[BooleanLiteral].value == true);
  }


  @Test
  def parseCharLiteral(): Unit = {
    val lexer =LanguageLexer(CharStreams.fromString(literal.charLiteral));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx=parser.literal();

    val result = LanguageVisitor(tokens).visitLiteral(ctx);
    assert(result.isInstanceOf[CharacterLiteral]);
    assert(result.asInstanceOf[CharacterLiteral].value == 'a');
  }

  @Test
  def parseIntegerLiteral(): Unit = {
    val lexer =LanguageLexer(CharStreams.fromString(literal.intLiteral));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx=parser.literal();

    val result = LanguageVisitor(tokens).visitLiteral(ctx);
    assert(result.isInstanceOf[IntegerLiteral]);
    assert(result.asInstanceOf[IntegerLiteral].value == -10);
  }
  @Test
  def parseFloatLiteral(): Unit = {
    val lexer =LanguageLexer(CharStreams.fromString(literal.floatLiteral));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx=parser.literal();

    val result = LanguageVisitor(tokens).visitLiteral(ctx);
    assert(result.isInstanceOf[FlaotLiteral]);
    assert(result.asInstanceOf[FlaotLiteral].value == -99.123);
  }

}
