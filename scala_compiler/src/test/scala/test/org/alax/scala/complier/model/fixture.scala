package test.org.alax.scala.complier.model

import org.alax.scala.compiler.model.{Declaration, Import, Value, Literal}
import org.alax.scala.compiler.model.Literal

object fixture {

  type ScalaSourceCode = String;

  object value {

    object definition {
      val `java.lang.String abc="abc"`: (Value.Definition, ScalaSourceCode) = (Value.Definition(
        declaration = Value.Declaration(
          name = "def",
          `type` = Value.Type(
            Declaration.Type.Id(
              value = "Integer"
            )
          )
        ),
        initialization = Literal.String("abc")
      ),
        "val abc: `java.lang.String` = \"abc\""
      )
    }

    object declaration {

      val `Integer def`: (Value.Declaration, ScalaSourceCode) = (Value.Declaration(
        name = "def",
        `type` = Value.Type(Declaration.Type.Id(
          value = "Integer",
        ))
      ), "val `def`: Integer")

      val `java.lang.String abc`: (Value.Declaration, ScalaSourceCode) = (Value.Declaration(
        name = "abc", `type` = Value.Type(Declaration.Type.Id(
          value = "java.lang.String"
        ))
      ), "val abc: `java.lang.String`")

    }


  }

  object literal {
    val `4.21`: (Literal.Float, ScalaSourceCode) = (Literal.Float(4.21), "4.21d")
    val `a`: (Literal.Character, ScalaSourceCode) = (Literal.Character('a'), "'a'")
    val `true`: (Literal.Boolean, ScalaSourceCode) = (Literal.Boolean(true), "true")
    val `str`: (Literal.String, ScalaSourceCode) = (Literal.String("str"), "\"str\"");
  }


}
