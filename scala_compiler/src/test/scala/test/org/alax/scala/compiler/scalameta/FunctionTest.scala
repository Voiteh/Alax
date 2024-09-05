package test.org.alax.scala.compiler.scalameta


import org.alax.scala.compiler.model.Function
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.must.Matchers.mustBe

class FunctionTest extends AnyWordSpec {
  type ScalaSourceCode = String

  "Function" when {

    "Declared " must {

      "Correspond" in {
        Seq(
          fixture.function.declaration.`abc()`,
          fixture.function.declaration.`java.lang.String abc()`,
          fixture.function.declaration.`abc(String abc,String def)`,
          fixture.function.declaration.`Integer some name(java.lang.String abc, String def)`
        ).foreach((source: Function.Declaration, target: ScalaSourceCode) => source.scala.toString() mustBe target)
      }

    }
    "Defined" must {
      "Correspond" in {
        Seq(
          fixture.function.definition.`abc() =!> "abc"`,
          fixture.function.definition.`abc() =!> abc`,
          fixture.function.definition.`abc() =!> java.lang.blang.abc`
        ).foreach((source: Function.Definition, target: ScalaSourceCode) => source.scala.toString() mustBe target)
      }
    }

    "Defined and Called " must {

    }

  }

}

