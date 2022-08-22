import org.alax.model.{ValueType, *}

class Test {


  def patternMatching(): Unit = {
    `type`: Type
    = ValueType(
      id: Id("com.ble.ble.Class")
    );
    `type` match {
      case ValueType(id) => `type`.
    }
  }

}
