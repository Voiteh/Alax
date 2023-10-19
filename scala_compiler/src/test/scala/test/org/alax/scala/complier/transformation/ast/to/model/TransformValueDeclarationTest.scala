package test.org.alax.scala.complier.transformation.ast.to.model

import org.alax.ast.model as ast
import org.alax.scala.compiler.model
import org.alax.scala.compiler.transformation.ast.to.model.AstToModelTransformer
import org.junit.jupiter.api.Test
import org.scalatest.Inside.inside
import org.scalatest.matchers.must.Matchers.{a, mustBe}
import org.scalatest.wordspec.AnyWordSpec
import test.org.alax.scala.complier.transformation.ast.to.model.fixture

class TransformValueDeclarationTest extends AnyWordSpec {



  "ast value declaration: Integer int" when{
    val astTransformer = AstToModelTransformer()
    "unknown type, then" must{
      "transform to CompilationError" in {
        val result = astTransformer.transform.value.declaration(
          valueDeclaration = fixture.Ast.Value.Declaration.`Integer int`,
          context = fixture.Context.unit
        );
        result mustBe a[model.CompilerError]

      }

    }
    "provided proper import and, then" must{
      "transform to model.Value.Declaration" in {
        val result = astTransformer.transform.value.declaration(
          valueDeclaration = fixture.Ast.Value.Declaration.`Integer int`,
          context = fixture.Context.`unit with import`
        );
        result mustBe a[model.Value.Declaration]
        inside(result.asInstanceOf[model.Value.Declaration]){
          case model.Value.Declaration(name,tpe) =>
            name mustBe "int"
            tpe.id.value mustBe "scala.lang.Integer"
        }
      }
    }
    "provided 2 imports but only one matching with member, second aliased, then" must {
      "transform to model.Value.Declaration" in {
        val result = astTransformer.transform.value.declaration(
          valueDeclaration = fixture.Ast.Value.Declaration.`Integer int`,
          context = fixture.Context.`unit with import and alias`
        );
        result mustBe a[model.Value.Declaration]
        inside(result.asInstanceOf[model.Value.Declaration]) {
          case model.Value.Declaration(name, tpe) =>
            name mustBe "int"
            tpe.id.value mustBe "scala.lang.Integer"
        }
      }
    }
  }





}
