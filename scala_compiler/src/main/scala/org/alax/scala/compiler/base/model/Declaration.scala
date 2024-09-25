package org.alax.scala.compiler.base.model


import scala.meta.{Decl, Stat}


/**
 * Something that changes structure of program constructs add new type / member / variable etc.
 *
 * @param identifier   - Name of the declaration
 * @param `type` - Internal property of declaration indicates the declaration type. It is not type declaration itself like Class, Interface etc. It can be a value / union / intersection / object type (reference)
 */
abstract class Declaration(val identifier: Declaration.Identifier)
  extends Statement

object Declaration {

  /**
   * Name of the declaration
   */
  type Identifier = String;



}