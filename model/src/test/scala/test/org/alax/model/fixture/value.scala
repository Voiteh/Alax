package test.org.alax.model.fixture

import org.alax.model.Value
import org.alax.model.base.Declaration

object value {

  object declaration {
    //String a;
    val simple: Value.Declaration = Value.Declaration(
      name = "a",
      `type` = Value.Type(
        id = Declaration.Type.Id("java.lang.String")
      )
    );

  }
}
