package org.alax.parser
import java.lang.Exception;

class ParseError(
                  val compilationUnit: String,
                  val startIndex: Int,
                  val endIndex: Int,
                  message: String
                )
  extends Exception(message, new Exception());
