package test.org.alax.parser

object fixture {
  object value {

    object definition {
      object literal {
        val `java.lang.Boolean bool:true;`: String = """java.lang.Boolean bool:true ;"""
        val `java.lang.Character char :'a';`: String = """java.lang.Character char :'a';"""
        val `java.lang.String string: "asd"  ;`: String = """java.lang.String string: "asd"  ;"""
        val `java.lang.Integer int   : -3;`: String = """java.lang.Integer int   : -3;"""
        val `java.lang.Float float: -3.12;`: String = """java.lang.Float float: -3.12;"""
      }
    }

    object declaration {
      val `java.lang.String value;`: String = """java.lang.String value;""";

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
