package org.alax.scala.compiler.model

/**
 * Aggregation of different language constructs reducing effort to write code
 */
object Aggregate {

  /**
   *
   * @param declaration - Embed declaration
   *
   * @param initialization - Initialization of given declaration
   */
  class DeclarationWithInitialization(
                                       val declaration: Declaration,
                                       val initialization: Expression
                                     )
    extends Statement


}
