package test.org.alax.parser.base

import org.alax.ast.LanguageParser
import org.alax.ast.model.Node
import org.alax.parser.LanguageVisitor
import org.antlr.v4.runtime.{CharStreams, CommonTokenStream, ParserRuleContext}
import org.alax.ast.{LanguageLexer, LanguageParser, model}
import scala.language.postfixOps

object AntlrSupport {

  class Contextual[Context](context: Context, tokens: CommonTokenStream) {
    def visit[Model](visitation: (LanguageVisitor, Context) => Model): Model = {
      return visitation(new LanguageVisitor(tokens), context);
    }
  }

  class Tokenization(tokens: CommonTokenStream) {
    def context[Context](contextResolver: (LanguageParser) => Context): Contextual[Context] = {
      new Contextual(context = contextResolver(new LanguageParser(tokens)), tokens)
    }
  }

  object language {

    def tokenize(text: String): Tokenization = {
      val lexer = LanguageLexer(CharStreams.fromString(text))
      return new Tokenization(new CommonTokenStream(lexer));
    }


  }


}
