package org.alax.utilities



  def toString(product: Product): String = product.productElementNames.zip(product.productIterator)
    .map { case (name, value) => s"$name = $value" }
    .mkString("(", ", ", ")")

