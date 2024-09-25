package test.org.alax.scala.compiler.scalameta

import org.alax.scala.compiler.base.model.{Declaration, Import, Type}
import org.alax.scala.compiler.model.{Evaluable, Function, Literals, Package, Procedure, Routine, Value}

object fixture {

  type ScalaSourceCode = String;

  object function{
    object declaration{
      val `java.lang.String abc()`: (Function.Declaration, ScalaSourceCode) = (
        Function.Declaration(
          identifier = "abc",
          returnType = Value.Type.Reference(
            id = Type.Id("java.lang.String")
          )
        ),
        "def abc(): `java.lang.String`"
      )
      val `Integer some name(java.lang.String abc, String def)`: (Function.Declaration, ScalaSourceCode) = (
        Function.Declaration(
          identifier = "some name", parameters = Seq(
            Routine.Declaration.Parameter(identifier = "abc",
              typeReference = Value.Type.Reference(
                id = Type.Id("java.lang.String")
              )),
            Routine.Declaration.Parameter(identifier = "def",
              typeReference = Value.Type.Reference(
                id = Type.Id("String")
              ))
          ),
          returnType = Value.Type.Reference(
            id = Type.Id("Integer")
          )
        ),
        "def `some name`(abc: `java.lang.String`, `def`: String): Integer"
      )
    }
  }
  object procedure {

    object declaration {
      val `abc()`: (Procedure.Declaration, ScalaSourceCode) = (
        Procedure.Declaration(
          identifier = "abc"
        ),
        "def abc(): `scala.Unit`"
      )

      val `abc(String abc,String def)`: (Procedure.Declaration, ScalaSourceCode) = (
        Procedure.Declaration(
          identifier = "abc", parameters = Seq(
            Routine.Declaration.Parameter(identifier = "abc",
              typeReference = Value.Type.Reference(
                id = Type.Id("String")
              )),
            Routine.Declaration.Parameter(identifier = "def",
              typeReference = Value.Type.Reference(
                id = Type.Id("String")
              ))
          ),
        ),
        "def abc(abc: String, `def`: String): `scala.Unit`"
      )




    }

    object definition {
      val `abc() =!> "abc"`: (Procedure.Definition, ScalaSourceCode) = (
        Procedure.Definition(
          declaration = declaration.`abc()`._1,
          body = Routine.Definition.Lambda.Body(
            Literals.String("abc")
          )
        ),
        "def abc(): `scala.Unit` = \"abc\""
      )
      val `abc() =!> abc`: (Procedure.Definition, ScalaSourceCode) = (
        Procedure.Definition(
          declaration = declaration.`abc()`._1,
          body = Routine.Definition.Lambda.Body(
            Evaluable.Reference(identifier = "abc")
          )
        ),
        "def abc(): `scala.Unit` = abc"
      )
      val `abc() =!> java.lang.blang.abc`: (Procedure.Definition, ScalaSourceCode) = (
        Procedure.Definition(
          declaration = declaration.`abc()`._1,
          body = Routine.Definition.Lambda.Body(
            Evaluable.Reference(
              `package` = Package.Reference(Seq(
                "java", "lang", "blang"
              )),
              identifier = "abc"
            )
          )
        ),
        "def abc(): `scala.Unit` = java.lang.blang.abc"
      )


    }
  }

  object value {

    object definition {
      val `java.lang.String abc="abc"`: (Value.Definition, ScalaSourceCode) = (Value.Definition(
        declaration = Value.Declaration(
          identifier = "def",
          typeReference = Value.Type.Reference(
            id = Type.Id("Integer")
          )
        ),
        meaning = Literals.String("abc")
      ),
        "val abc: `java.lang.String` = \"abc\""
      )
    }

    object declaration {

      val `Integer def`: (Value.Declaration, ScalaSourceCode) = (Value.Declaration(
        identifier = "def",
        typeReference = Value.Type.Reference(id = Type.Id(
          value = "Integer",
        ))
      ), "val `def`: Integer")

      val `java.lang.String abc`: (Value.Declaration, ScalaSourceCode) = (Value.Declaration(
        identifier = "abc", typeReference = Value.Type.Reference(id = Type.Id(
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
