package org.alax.utilities

import scala.reflect.ClassTag


def toString(product: Product): String = product.productElementNames.zip(product.productIterator)
  .map { case (name, value) => s"$name = $value" }
  .mkString("(", ", ", ")")

def equals(left: Product, right: Product, filter: (String, Any) => Boolean = (_, _) => true): Boolean = {
  val leftIterator: Seq[(String, Any)] = left.productElementNames.zip(left.productIterator)
    .toSeq
    .filter { case (name, value) => filter(name, value) }
  val rightIterator: Seq[(String, Any)] = right.productElementNames.zip(right.productIterator)
    .toSeq
    .filter { case (name, value) => filter(name, value) }
  leftIterator.zip(rightIterator).foldLeft(true)((acc: Boolean, items: ((String, Any), (String, Any))) => {
    val result = if acc then {
      val (leftName, leftValue) = items._1;
      val (rightName, rightValue) = items._2;

      //null and self reference
      if leftValue == rightValue then {
        println(s"Comparing references: ${leftName} <> ${rightName} => true")
        true
      }
      else if (leftValue.isInstanceOf[Product] && rightValue.isInstanceOf[Product]) {
        val result = equals(leftValue.asInstanceOf[Product], rightValue.asInstanceOf[Product], filter)
        println(s"Comparing products: ${leftName} <> ${rightName} => ${result}")
        result
      }
      //equality check for objects
      else if leftValue != null && rightValue != null then {
        val result = leftValue.equals(rightValue)
        println(s"Comparing equals: ${leftName} <> ${rightName} => ${result}")
        result
      }
      // all other cases
      else {
        println(s"Unknown comparing equals: ${leftName} <> ${rightName} (${leftValue} with ${rightValue})=> false")
        false
      }
    } else {
      println("Previous comparison returned false ")
      false
    }
    result
  }

  )
}
extension [Item](seq: Seq[Item]) {
  def unwrap[Left <: Item: ClassTag,Right <:Item:ClassTag,Unwrapped]
  (unwrapper: (lefts: Seq[Left], rights: Seq[Right]) => Unwrapped)
  : Unwrapped = {
    val (lefts, rights) = seq.foldRight((Seq.empty[Left], Seq.empty[Right])) {
      case (item, (lefts, rights)) => item match {
        case left:Left => (left +: lefts, rights)
        case right:Right => (lefts, right +: rights)
      }
    }
    unwrapper(lefts, rights)

  }
}
