package test.org.alax.parser

import test.org.alax.parser.base.Match
import org.alax.ast;

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

    object lowercase {
      val `asd`: String = "asd"
      val `a_sd 123`: String = "a_sd def"
      val `_ad`: String = "_ad"
      val `123`: String = "123"
    }

    object uppercase {
      val `Asd`: String = "Asd"
      val `A_sd Def`: String = "A_sd Def"
      val `_Ad`: String = "_Ad"
      val `123`: String = "123"
    }
  }

  object module {
    object declaration {
      val `module com.ble.ble;`: String = """module com.ble.ble;"""
    }

    object definition {
      val `module com.ble.ble {}`: String = """module com.ble.ble {}"""
      val `module com.ble.ble {value java.lang.Boolean bool=true;}`: String = """module com.ble.ble {value java.lang.Boolean bool=true;}"""
    }
  }

  object `package` {
    object declaration {
      val `package abc;`: String = """package abc;"""
    }

    object definition {
      val `package abc {}`: String = """package abc {}"""
      val `package abc {value java.lang.Boolean bool=true;}`: String = """package abc {value java.lang.Boolean bool=true;}"""
    }
  }

  object value {

    object definition {
      object literal {
        val `value java.lang.Boolean bool=true;`: String = """value java.lang.Boolean bool=true ;"""
        val `value java.lang.Character char ='a';`: String = """value java.lang.Character char ='a';"""
        val `value java.lang.String string= "asd"  ;`: String = """value java.lang.String string= "asd"  ;"""
        val `value Integer int   = -3;`: String = """value Integer int   = -3;"""
        val `value Float float= -3.12;`: String = """value Float float= -3.12;"""
      }
    }

    object declaration {
      val `value java.lang.String item;`: String = """value java.lang.String item;""";
      val `value Integer item;`: String = """value Integer item;""";
      val `value Long Integer some long item;` = """value Long Integer some long item;"""
    }

  }


  object literal {
    val `true` = "true";
    val `'a'` = "'a'";
    val `"str"` = "\"str\""
    val `-10` = "-10"
    val `-99.123` = "-99.123"
  }

  object function {
    object pure {
      object definition {
        val `function String abc(String one)=>one;` = Match(
          text = "function String abc(String one)=>one;",
          node = ast.Function.Pure.Definition(
            returnTypeReference = ast.Value.Type.Identifier(
              suffix = ast.Identifier.UpperCase(
                "String"
              ),
            ),
            identifier = ast.Identifier.LowerCase("abc"),
            parameters = Seq(
              ast.Function.Parameter(
                identifier = ast.Identifier.LowerCase("one"),
                `type` = ast.Value.Type.Identifier(
                  suffix = ast.Identifier.UpperCase("String")
                )
              )
            ),
            body = ast.Function.Lambda.Body(
              element = ast.Value.Reference(
                valueId = ast.Identifier.LowerCase("one"),
              )
            ),
          )
        )
      }
    }
  }

}
