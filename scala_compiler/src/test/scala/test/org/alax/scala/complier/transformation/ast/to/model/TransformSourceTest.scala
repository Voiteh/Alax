package test.org.alax.scala.complier.transformation.ast.to.model

import org.scalatest.wordspec.AnyWordSpec
import org.alax.scala.compiler.model.Source
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import test.org.alax.scala.complier.transformation.ast.to.model.fixture.Ast;

class TransformSourceTest extends AnyWordSpec {
  val astTransformer = new AstToModelTransformer();
  "given package source: " when {
    val source = Ast.Source.Package.`simple package with Integer value`
    "provided " must {
      "convert " in {
        val result =astTransformer.transform.source.`package`( source = source );
        //TODO implement
      }
    }
  }


}
