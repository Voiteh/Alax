package test.org.alax.scala.compiler.scalameta

import org.alax.scala.compiler.model.Procedure
import org.scalatest.matchers.must.Matchers.mustBe
import org.scalatest.wordspec.AnyWordSpec

class ProcedureTest extends AnyWordSpec {
  type ScalaSourceCode = String

  "Procedure" when {

    "Declared " must {

      "Correspond" in {
        Seq(
          fixture.procedure.declaration.`abc()`,
          fixture.procedure.declaration.`abc(String abc,String def)`,
        ).foreach((source: Procedure.Declaration, target: ScalaSourceCode) => source.scala.toString() mustBe target)
      }

    }
    "Defined" must {
      "Correspond" in {
        Seq(
          fixture.procedure.definition.`abc() =!> "abc"`,
          fixture.procedure.definition.`abc() =!> abc`,
          fixture.procedure.definition.`abc() =!> java.lang.blang.abc`
        ).foreach((source: Procedure.Definition, target: ScalaSourceCode) => source.scala.toString() mustBe target)
      }
    }

    "Defined and Called " must {

    }

  }

}

