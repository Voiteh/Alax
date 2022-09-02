package test.org.alax.parser

import org.alax.parser.{LanguageVisitor, ParseError}
import org.alax.syntax.{LanguageLexer, LanguageParser}
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream}
import org.junit.jupiter.api.Test
import test.org.alax.parser.fixture.literal
import java.nio.file.Path;
import java.nio.charset.Charset
import scala.io.Source;

object ParseCompilationUnitTest {


  @Test
  def parseGibberishFileFromResource(): Unit = {
    val path=Path.of(fixture.compilationUnit.gibberish.toURI);
    val lexer =LanguageLexer(CharStreams.fromPath(path));
    val tokens = new CommonTokenStream(lexer)
    val parser = new LanguageParser(tokens);
    val ctx=parser.compilationUnit();
    val result = LanguageVisitor(tokens).visitCompilationUnit(ctx);
    assert(result.isInstanceOf[ParseError]);
    assert(result.asInstanceOf[ParseError].compilationUnit==path.toString);
  }



}
