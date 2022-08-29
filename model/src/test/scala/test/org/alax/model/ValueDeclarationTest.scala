package test.org.alax.model

import org.junit.jupiter.api.Test
import test.org.alax.model.fixture._;


object ValueDeclarationTest {

  @Test
  def test(): Unit = {
    val simple = value.declaration.simple;
    assert(simple.name == "a");
    assert(simple.`type`.id.fullyQualifiedName == classOf[String].getName)


  }
}