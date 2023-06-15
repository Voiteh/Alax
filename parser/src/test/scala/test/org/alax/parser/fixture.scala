package test.org.alax.parser

object fixture {

  object declaration {

    object value {

      val simple: String = """java.lang.String value;""";

      object withInitialization {
        object literal {
          val bool: String = """java.lang.Boolean bool:true ;"""
          val char: String = """java.lang.Character char :'a';"""
          val string: String = """java.lang.String string: "asd"  ;"""
          val integer: String = """java.lang.Integer int   : -3;"""
          val float: String = """java.lang.Float float: -3.12;"""
        }
      }
    }

  }

  object literal {
    val boolTrue = "true";
    val charLiteral = "'a'";
    val stringLiteral = "\"str\""
    val intLiteral = "-10"
    val floatLiteral = "-99.123"
  }


}
