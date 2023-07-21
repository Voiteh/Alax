package test.org.alax.scala.complier.transformer

import org.alax.scala.compiler.transformer.AstTransformer
import org.alax.scala.compiler.model
import org.junit.jupiter.api.Test;


class TransformImportTest {

  val astTransformer = AstTransformer()


  @Test
  def `when provided null package and basic import then should return sequence of tracable import`(): Unit = {
    val result = astTransformer.transform.`import`(fixture.Ast.Import.`scala.lang.String`);

    assert(result.size == 1)
    val transformed = result.last.transformed;
    assert(transformed.`package` == "scala.lang")
    assert(transformed.member == "String")


  }


  @Test
  def `when provided null package and alias then should return sequence of tracable import`(): Unit = {
    val result = astTransformer.transform.`import`(fixture.Ast.Import.`scala.lang.Integer as Bleh`);

    assert(result.size == 1)
    val transformed = result.last.transformed;
    assert(transformed.`package` == "scala.lang")
    assert(transformed.member == "Integer")
    assert(transformed.alias == "Bleh")


  }


  @Test
  def `when provided null package and container with 2 imports then should return sequence of tracable import`(): Unit = {
    val result = astTransformer.transform.`import`(fixture.Ast.Import.`scala.lang [ String, Integer as Bleh ]`);

    assert(result.size == 2)
    result.foreach(element => assert(element.transformed.`package` == "scala.lang"))
    assert(result.exists(element => element.transformed.member == "String"))
    assert(result.exists(element => element.transformed.member == "Integer" && element.transformed.alias == "Bleh"))


  }


}
