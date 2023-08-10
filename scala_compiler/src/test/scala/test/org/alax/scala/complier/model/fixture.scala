package test.org.alax.scala.complier.model

import org.alax.scala.compiler.model.{literals, Value, Declaration, Import}

object fixture {


  object value {
    object declaration {

      val `Integer def`: (Value.Declaration, String) = (Value.Declaration(
        name = "def", `type` = Value.Type(Declaration.Type.Id(
          name = "Integer",
        ))
      ), "val `def`: Integer")

      val `java.lang.String abc`: (Value.Declaration, String) = (Value.Declaration(
        name = "abc", `type` = Value.Type(Declaration.Type.Id(
          name = "java.lang.String"
        ))
      ), "val abc: `java.lang.String`")
    }
  }

  object literal {
    val `4.21`: (literals.Float, String) = (literals.Float(4.21), "4.21d")
    val `a`: (literals.Character, String) = (literals.Character('a'), "'a'")
    val `true`: (literals.Boolean, String) = (literals.Boolean(true), "true")
    val `str`: (literals.String, String) = (literals.String("str"), "\"str\"");
  }


}
