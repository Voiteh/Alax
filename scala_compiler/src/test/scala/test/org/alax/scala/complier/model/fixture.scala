package test.org.alax.scala.complier.model

import org.alax.scala.compiler.model.literals

object fixture {
  object literal {
    val `4.21`: (literals.Float, String) = (literals.Float(4.21), "4.21d")
    val `a`: (literals.Character, String) = (literals.Character('a'), "'a'")
    val `true`: (literals.Boolean, String) = (literals.Boolean(true), "true")
    val `str`: (literals.String, String) = (literals.String("str"), "\"str\"");
  }


}
