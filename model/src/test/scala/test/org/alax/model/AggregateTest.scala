package test.org.alax.model

import org.alax.model.{StringLiteral, Value}
import org.junit.jupiter.api.Test
import test.org.alax.model.fixture.aggregate;

object AggregateTest {

  @Test
  def simpleValueInitialization(): Unit = {
    val simple = aggregate.declarationWithInitialization.simpleValueWithLiteral;
    assert(simple.initialization.isInstanceOf[StringLiteral]);
    assert(simple.declaration.isInstanceOf[Value.Declaration]);

    val literal = simple.initialization.asInstanceOf[StringLiteral];
    val valueDeclaration = simple.declaration.asInstanceOf[Value.Declaration];

    assert(literal.value == "Asd");
    assert(valueDeclaration.name == "str");

    assert(valueDeclaration.`type`.isInstanceOf[Value.Type]);
    val `type` = valueDeclaration.`type`.asInstanceOf[Value.Type];
    assert(`type`.id.fullyQualifiedName == classOf[String].getName);
  }


}
