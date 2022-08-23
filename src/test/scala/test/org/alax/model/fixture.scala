package test.org.alax.model
import org.alax.model._
object fixture {

  object value {
    object declaration {
      //String a;
      val simple: Value.Declaration = Value.Declaration(
        name = "a",
        `type` = Valuable.Type(
          id = Type.Id("java.lang.String")
        )
      );
      //String|Integer b;
      val union: Value.Declaration = Value.Declaration(
        name = "b",
        `type` = Union.Type(
          ids = Seq(
            Type.Id("java.lang.String"),
            Type.Id("java.lang.Integer")
          )
        )
      );
      //String&Integer c;
      val intersection: Value.Declaration = Value.Declaration(
        name = "b",
        `type` = Intersection.Type(
          ids = Seq(
            Type.Id("java.lang.String"),
            Type.Id("java.lang.Integer")
          )
        )
      );
    }
  }


}
