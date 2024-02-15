package test.org.alax.parser

object fixture {


  object `import` {
    object statement {
      object simple {

        val `import java.lang.String;`: String = "import java.lang.String;"
        val `import java.lang;`: String = "import java.lang;"
        val `import java.lang long.String;`: String = "import java.lang long.String;"
        val `import 1ava.lang.String;`: String = "import 1ava.lang.String;"
      }

      object alias {
        val `import java.lang.String alias Text;`: String = "import java.lang.String alias Text;"
        val `import java.lang long.String alias Some Text;`: String = "import java.lang long.String alias Some Text;"
        val `import java.lang alias java lang;`: String = "import java.lang alias java lang;"
      }

      object nested {
        val `import java.lang.[String];`: String = "import java.lang.[String];"
        val `import java.[lang.[String]];`: String = "import java.[lang.[String]];";
        val `import java.[lang[String],lang.Integer,javax.[Validator];` = "import java.[lang[String],lang.Integer,javax.[Validator];"
        val `import java.[lang[String.[Builder]],lang.Integer,javax.[Validator];` = "import java.[lang[String.[Builder]],lang.Integer,javax.[Validator];"
      }
    }
  }

  object identifier {
    val `asd`: String = "asd"
    val `a_sd 123`: String = "a_sd def"
    val `_ad`: String = "_ad"
    val `123`: String = "123"
  }

  object module {
    object declaration {
      val `module com.ble.ble;`: String = """module com.ble.ble;"""
    }

    object definition {
      val `module com.ble.ble {}`: String = """module com.ble.ble {}"""
      val `module com.ble.ble {java.lang.Boolean bool=true;}`: String = """module com.ble.ble {java.lang.Boolean bool=true;}"""
    }
  }

  object `package` {
    object declaration {
      val `package abc;`: String = """package abc;"""
    }

    object definition {
      val `package abc {}`: String = """package abc {}"""
      val `package abc {java.lang.Boolean bool=true;}`: String = """package abc {java.lang.Boolean bool=true;}"""
    }
  }

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
      val `Long Integer some long value;` = """Long Integer some long value;"""
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
