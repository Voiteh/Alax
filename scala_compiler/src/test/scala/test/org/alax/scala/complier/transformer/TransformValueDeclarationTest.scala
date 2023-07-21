package test.org.alax.scala.complier.transformer

import org.alax.ast.model as ast
import org.junit.jupiter.api.Test
import org.alax.scala.compiler.transformer.AstTransformer;
import org.alax.scala.compiler.model as model

class TransformValueDeclarationTest {
  val astTransformer = AstTransformer()


  @Test
  def `when unknown type, then should return CompilationError`(): Unit = {
    val result = astTransformer.transform.value(
      valueDeclaration = fixture.Value.Declaration.`Integer int`,
      context = fixture.Context.emptyUnit
    );
    assert(result.isInstanceOf[model.CompilationError])
  }

  @Test
  def `when provided proper import, then should return Value Declaration`(): Unit = {
    val result = astTransformer.transform.value(
      valueDeclaration = fixture.Value.Declaration.`Integer int`,
      context = fixture.Context.unitWithImport
    );
    assert(result.isInstanceOf[model.Value.Declaration])
    val `Value Declaration` = result.asInstanceOf[model.Value.Declaration];
    assert(`Value Declaration`.name == "int");
    assert(`Value Declaration`.`type` == model.Value.Type(
      id = model.Declaration.Type.Id("scala.lang.Integer")
    ));
  }

  @Test
  def `when provided 2 imports matching but one aliased, then should return Value Declaration`(): Unit = {
    val result = astTransformer.transform.value(
      valueDeclaration = fixture.Value.Declaration.`Integer int`,
      context = fixture.Context.unitWithImportAndAlias
    );
    val `Value Declaration` = result.asInstanceOf[model.Value.Declaration];
    assert(`Value Declaration`.name == "int");
    assert(`Value Declaration`.`type` == model.Value.Type(
      id = model.Declaration.Type.Id("scala.lang.Integer")
    ));
  }


}
