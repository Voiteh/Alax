package test.org.alax.scala.complier.transformation.ast.to.model

import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.scalatest.wordspec.AnyWordSpec
import org.alax.scala.compiler.model.Source;

class TransformSourceTest extends AnyWordSpec {
  val astTransformer = new AstToModelTransformer();
  "given package source: " when {
    val source = fixture.Ast.Source.Package.`simple package with Integer value`
    "provided " must {
      "convert " in {
        val result =astTransformer.transform.source.`package`( source = source );
        //TODO implement
      }
    }
  }


}
