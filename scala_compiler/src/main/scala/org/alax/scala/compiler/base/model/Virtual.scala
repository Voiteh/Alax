package org.alax.scala.compiler.base.model

class Virtual[Item](realization: () => Item) {


  def realize: Item = realization.apply();

}
