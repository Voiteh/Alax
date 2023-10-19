package test.org.alax.parser

object fixture {
  object value {

    object definition {
      object literal {
        val `java.lang.Boolean bool=true;`: String = """java.lang.Boolean bool=true ;"""
        val `java.lang.Character char ='a';`: String = """java.lang.Character char ='a';"""
        val `java.lang.String string= "asd"  ;`: String = """java.lang.String string= "asd"  ;"""
        val `Integer int   = -3;`: String = """Integer int   = -3;"""
        val `Float float= -3.12;`: String = """Float float= -3.12;"""
      }
    }

    object declaration {
      val `java.lang.String value;`: String = """java.lang.String value;""";
      val `Integer value;`: String = """Integer value;""";
    }

  }


  object literal {
    val `true` = "true";
    val `'a'` = "'a'";
    val `"str"` = "\"str\""
    val `-10` = "-10"
    val `-99.123` = "-99.123"
  }


}
