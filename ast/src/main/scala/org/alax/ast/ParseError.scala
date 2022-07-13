package org.alax.ast

class ParseError(
                  val filePath: String,
                  val start: Int,
                  val stop: Int,
                  val message: String,
                )