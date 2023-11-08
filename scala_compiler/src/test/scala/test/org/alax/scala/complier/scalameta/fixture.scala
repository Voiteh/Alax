package test.org.alax.scala.complier.scalameta

import org.alax.scala.compiler.base.model.{Declaration, Import}
import org.alax.scala.compiler.model.{Literals, Value}

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
        initialization = Literals.String("abc")
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
    val `4.21`: (Literals.Float, ScalaSourceCode) = (Literals.Float(4.21), "4.21d")
    val `a`: (Literals.Character, ScalaSourceCode) = (Literals.Character('a'), "'a'")
    val `true`: (Literals.Boolean, ScalaSourceCode) = (Literals.Boolean(true), "true")
    val `str`: (Literals.String, ScalaSourceCode) = (Literals.String("str"), "\"str\"");
  }


}
