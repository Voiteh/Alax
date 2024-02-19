package org.alax.parser

import org.alax.ast.base.ParseError
import org.alax.ast.partial.Identifier
import org.antlr.v4.runtime.tree.{TerminalNode, TerminalNodeImpl}
import org.alax.ast.{LanguageLexer, LanguageParser, LanguageParserBaseVisitor, Literals, Value, base}
import org.antlr.v4.runtime.CommonToken

import scala.reflect.{TypeTest, Typeable}

class IdentifierParser(metadataParser: MetadataParser) {
//  object parse {
//    object identifier {
//      def lowercase(terminalNode: TerminalNode): Identifier.LowerCase | ParseError = {
//        return if (Identifier.LowerCase.matches(terminalNode.getText)) then
//          Identifier.LowerCase(
//            terminalNode.getText, metadataParser.parse.metadata(terminalNode.getSymbol)
//          )
//        else new ParseError(
//          metadata = metadataParser.parse.metadata(terminalNode.getSymbol),
//          message = "Invalid lowercase identifier: " + terminalNode.getText
//        )
//      }
//
//      def uppercase(terminalNode: TerminalNode): Identifier.UpperCase | ParseError = {
//        return if (Identifier.UpperCase.matches(terminalNode.getText)) then
//          Identifier.UpperCase(
//            terminalNode.getText, metadataParser.parse.metadata(terminalNode.getSymbol)
//          )
//        else new ParseError(
//          metadata = metadataParser.parse.metadata(terminalNode.getSymbol),
//          message = "Invalid uppercase identifier: " + terminalNode.getText
//        )
//      }
//
//
//
//
////      object qualified {
////
////        def lowercase(terminalNode: TerminalNode): Identifier.Qualified.LowerCase | ParseError = {
////          if (terminalNode.getSymbol.getType == ???.asInstanceOf[Int] ) then {//LanguageParser.QUALIFIED_LOWERCASE_NAME) then {
////            val lowercaseIdentifiers: Array[Identifier.LowerCase | ParseError] = terminalNode.getText.split("\\.")
////              .map(item => TerminalNodeImpl(
////                CommonToken(
////                  LanguageParser.LOWERCASE_IDENTIFIER, item
////                )))
////              .map(item => parse.identifier.lowercase(item))
////
////            val result: Seq[Identifier.LowerCase] | ParseError = lowercaseIdentifiers.foldLeft[Seq[Identifier.LowerCase] | ParseError](Seq[Identifier.LowerCase]())((acc: Seq[Identifier.LowerCase] | ParseError, item: Identifier.LowerCase | ParseError)=> acc match {
////              case error: ParseError => error
////              case accumulator: Seq[Identifier.LowerCase] => item match {
////                case error: ParseError => error
////                case item: Identifier.LowerCase => accumulator.appended(item)
////              }
////            })
////            return result match {
////              case sequence: Seq[Identifier.LowerCase] => Identifier.Qualified.LowerCase(
////                qualifications = sequence, metadata = metadataParser.parse.metadata(terminalNode.getSymbol)
////              )
////              case error: ParseError => error
////            }
////          }
////          else ParseError(
////            metadata = metadataParser.parse.metadata(terminalNode.getSymbol),
////            message = "Invalid lowercase qualified identifier: " + terminalNode.getText
////          );
////        }
////      }
//    }
//  }
}

