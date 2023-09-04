package test.org.alax.parser
import org.alax.parser.LanguageVisitor
import org.junit.jupiter.api.Test
import org.antlr.v4.runtime.{CharStream, CharStreams, CommonTokenStream}
import org.alax.ast.{LanguageLexer, LanguageParser}
import org.alax.ast.model.*
import test.org.alax.parser.fixture.literal
//TODO refactor to scala test instead of junit
object ParseLiteralTest {





  @Test
  def parseBooleanLiteral(): Unit = {
    val value=CharStreams.fromString(literal.boolTrue);
    val lexer =LanguageLexer(value);
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx=parser.literalExpression();

    val result = LanguageVisitor(tokens).visitLiteralExpression(ctx);
    assert(result.isInstanceOf[Expression.Literal.Boolean]);
    assert(result.asInstanceOf[Expression.Literal.Boolean].value == true);
  }


  @Test
  def parseCharLiteral(): Unit = {
    val lexer =LanguageLexer(CharStreams.fromString(literal.charLiteral));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx=parser.literalExpression();

    val result = LanguageVisitor(tokens).visitLiteralExpression(ctx);
    assert(result.isInstanceOf[Expression.Literal.Character]);
    assert(result.asInstanceOf[Expression.Literal.Character].value == 'a');
  }

  @Test
  def parseIntegerLiteral(): Unit = {
    val lexer =LanguageLexer(CharStreams.fromString(literal.intLiteral));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx=parser.literalExpression();

    val result = LanguageVisitor(tokens).visitLiteralExpression(ctx);
    assert(result.isInstanceOf[Expression.Literal.Integer]);
    assert(result.asInstanceOf[Expression.Literal.Integer].value == -10);
  }

  @Test
  def parseFloatLiteral(): Unit = {
    val lexer =LanguageLexer(CharStreams.fromString(literal.floatLiteral));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx=parser.literalExpression();

    val result = LanguageVisitor(tokens).visitLiteralExpression(ctx);
    assert(result.isInstanceOf[Expression.Literal.Float]);
    assert(result.asInstanceOf[Expression.Literal.Float].value == -99.123);
  }

  @Test
  def parseStringLiteral(): Unit ={
    val lexer =LanguageLexer(CharStreams.fromString(literal.stringLiteral));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx=parser.literalExpression();

    val result = LanguageVisitor(tokens).visitLiteralExpression(ctx);
    assert(result.isInstanceOf[Expression.Literal.String]);
    assert(result.asInstanceOf[Expression.Literal.String].value == "str");
  }


}
