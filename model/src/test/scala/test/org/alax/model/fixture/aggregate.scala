package test.org.alax.model.fixture

import org.alax.model.{Aggregate, StringLiteral, Value, base}
import org.alax.model.base.Declaration.Type;

object aggregate {
  object declarationWithInitialization {
    // String str: "Asd"
    val simpleValueWithLiteral = Aggregate.DeclarationWithInitialization(
      declaration = Value.Declaration(
        name = "str",
        `type` = Value.Type(
          id = Type.Id(classOf[String].getName)
        )
      ),
      initialization = StringLiteral("Asd")
    )
  }
}
