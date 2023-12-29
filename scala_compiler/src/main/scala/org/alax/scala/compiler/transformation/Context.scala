package org.alax.scala.compiler.transformation

/**
 * Context in which given transformation is executed
 *
 * @param parent
 */
abstract class Context(val parent: Context | Null = null)