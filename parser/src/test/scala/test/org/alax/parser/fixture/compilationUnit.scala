package test.org.alax.parser.fixture
import java.net.URL;
object compilationUnit {

  def readFromPath(path: String): URL = {
    return Thread.currentThread().getContextClassLoader().getResource(path);
  }

  val gibberish=readFromPath("fixture/compilation/unit/gibberish.alax")

  object literal{

  }


}
