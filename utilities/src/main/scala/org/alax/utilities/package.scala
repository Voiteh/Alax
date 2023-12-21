package org.alax.utilities


def toString(product: Product): String = product.productElementNames.zip(product.productIterator)
  .map { case (name, value) => s"$name = $value" }
  .mkString("(", ", ", ")")

def equals(left: Product, right: Product, filter: (String, Any) => Boolean): Boolean = {
  val leftIterator: Iterator[(String, Any)] = left.productElementNames.zip(left.productIterator)
    .filter { case (name, value) => filter(name, value) }
  val rightIterator: Iterator[(String, Any)] = right.productElementNames.zip(right.productIterator)
    .filter { case (name, value) => filter(name, value) }
  leftIterator.zip(rightIterator).foldLeft(true)((acc: Boolean, items: ((String, Any), (String, Any))) => {
    if acc then {
      val value1 = items._1._2
      val value2 = items._2._2
      //null and self reference
      return if value1 == value2 then true
      //equality check for objects
      else if value1 != null && value2 != null then value1.equals(value2)
      // all other cases
      else false
    } else false
  }
  )
}