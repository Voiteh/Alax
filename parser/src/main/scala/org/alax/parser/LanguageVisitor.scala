package org.alax.parser

import org.alax.ast.base.{ParseError, *}
import org.alax.ast.base.Node.Metadata
import org.alax.ast.partial.Names
import org.alax.ast.{LanguageLexer, LanguageParser, LanguageParserBaseVisitor, Literals, Value, base}
import org.alax.ast
import org.antlr.v4.runtime.{ParserRuleContext, Token, TokenStream}
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode}

import javax.management.ValueExp
import scala.jdk.CollectionConverters._

class LanguageVisitor() extends LanguageParserBaseVisitor[Node | ParseError] {


  private def tokenMetadata(token: Token): Node.Metadata = {
    return Node.Metadata(
      location = Node.Location(
        unit = token.getTokenSource.getSourceName,
        startLine = token.getLine,
        endLine = token.getLine,
        startIndex = token.getStartIndex,
        endIndex = token.getStopIndex
      )
    )
  }

  private def contextMetadata(context: ParserRuleContext): Node.Metadata = {
    return Node.Metadata(
      location = Node.Location(
        unit = context.start.getTokenSource.getSourceName,
        startLine = context.start.getLine,
        endLine = context.stop.getLine,
        startIndex = context.start.getStartIndex,
        endIndex = context.stop.getStopIndex
      )
    )

  }


  private def parseName(terminalNode: TerminalNode): base.Partial.Name | ParseError = {
    return terminalNode.getSymbol.getType match {
      case LanguageParser.LOWERCASE_NAME => Names.LowerCase(
        terminalNode.getText, tokenMetadata(terminalNode.getSymbol)
      );
      case LanguageParser.UPPERCASE_NAME => Names.UpperCase(
        terminalNode.getText, tokenMetadata(terminalNode.getSymbol)
      );
      case _ => ParseError(
        metadata = tokenMetadata(terminalNode.getSymbol),
        message = "Unknown name: " + terminalNode.getText
      );
    }
  }



  //  override def visitSimpleImportDeclaration(ctx: LanguageParser.SimpleImportDeclarationContext): statements.declarations.Import.Simple | ParseError = {
  //    super.visitSimpleImportDeclaration(ctx)
  //    val importedName: partials.names.Qualified | ParseError = visitImportedName(ctx.importedName());
  //    return importedName match {
  //      case e: ParseError => wrapParseError(e, "Invalid import declaration", metadata(ctx.start));
  //      case qualified: partials.names.Imported => {
  //      }
  //    }
  //  }


  override def visitImportedName(ctx: LanguageParser.ImportedNameContext): ast.Imports.ImportedName | ParseError = {
    super.visitImportedName(ctx);
    val qualifications: Seq[Names.LowerCase | Names.UpperCase] = ctx.children.asScala
      .filter(item => item.isInstanceOf[TerminalNode])
      .map(item => item.asInstanceOf[TerminalNode])
      .filter(item => item.getSymbol.getType == LanguageParser.UPPERCASE_NAME || item.getSymbol.getType == LanguageParser.LOWERCASE_NAME)
      .map(item => item.getSymbol.getType match {
        case LanguageParser.UPPERCASE_NAME => Names.UpperCase(item.getText, contextMetadata(ctx));
        case LanguageParser.LOWERCASE_NAME => Names.LowerCase(item.getText, contextMetadata(ctx));
        case _ => return ParseError(tokenMetadata(item.getSymbol), f"Unknown token for imported name: ${item.getText}")
      }).toSeq;
    return if qualifications.size == 1 then qualifications.last else Names.Qualified(qualifications, contextMetadata(ctx));
  }

  override def visitValueTypeReference(ctx: LanguageParser.ValueTypeReferenceContext): ast.Value.Type.Reference | ParseError = {
    super.visitValueTypeReference(ctx)
    val typeName = Names.UpperCase(value = ctx.typeName.getText, metadata = contextMetadata(ctx))
    val fullName: Names.Qualified | Names.UpperCase | ParseError = Option(ctx.importedName())
      .map(context => visitImportedName(context))
      .map[ParseError | Names.Qualified | Names.UpperCase] {
        case error: ParseError => error;
        case imported: Names.UpperCase => Names.Qualified(qualifications = Seq(imported, typeName), contextMetadata(ctx))
        case imported: Names.LowerCase => Names.Qualified(qualifications = Seq(imported, typeName), contextMetadata(ctx))
        case imported: Names.Qualified => Names.Qualified(qualifications = imported.qualifications :+ typeName, contextMetadata(ctx))
      }
      .getOrElse[ParseError | Names.Qualified | Names.UpperCase](typeName);
    return fullName match {
      case id@(_: Names.UpperCase | _: Names.Qualified) => ast.Value.Type.Reference(
        id = id, metadata = contextMetadata(ctx)
      )
      case default: ParseError => default
    }

  }


  override def visitValueDeclaration(ctx: LanguageParser.ValueDeclarationContext): ast.Value.Declaration | ParseError = {
    super.visitValueDeclaration(ctx);
    val typeReference: Partial.Type.Reference | ParseError = visitValueTypeReference(ctx.valueTypeReference());
    val name: base.Partial.Name | ParseError = parseName(ctx.LOWERCASE_NAME());
    return typeReference match {
      case valueType: ast.Value.Type.Reference =>
        name match {
          case shadowName: Names.LowerCase =>
            ast.Value.Declaration(
              name = shadowName,
              typeReference = valueType,
              metadata = contextMetadata(ctx)
            )
          case error: ParseError => new ParseError(cause = error, message = "Invalid value name ", metadata = contextMetadata(ctx))
          case _ => new ParserBugError(metadata = contextMetadata(ctx));
        }
      case error: ParseError => new ParseError(cause = error, message = "Invalid value type", metadata = contextMetadata(ctx))
      case other => ParseError(
        message = f"Not Implemented, parsing type: ${other}!",
        metadata = contextMetadata(ctx)
      )
    }
  }


  override def visitModuleDeclaration(ctx: LanguageParser.ModuleDeclarationContext): ast.Module.Declaration | ParseError = {
    super.visitModuleDeclaration(ctx)
    val moduleName: Names.Qualified.LowerCase | ParseError = visitModuleName(ctx.moduleName())
    return moduleName match {
      case name: Names.Qualified.LowerCase => ast.Module.Declaration(
        name = name,
        metadata = contextMetadata(ctx)
      )
      case error: ParseError => ParseError(
        message = "Invalid module declaration",
        metadata = contextMetadata(ctx),
        cause = error
      )

    }
  }

  override def visitModuleBody(ctx: LanguageParser.ModuleBodyContext): ast.Module.Body | ParseError = {
    super.visitModuleBody(ctx)
    val valueDefinitions: Seq[ast.Value.Definition | ParseError] = ctx.valueDefinition().asScala
      .map(item => visitValueDefinition(item)).toSeq

    return ast.Module.Body(
      elements = valueDefinitions,
      metadata = contextMetadata(ctx)
    )
  }

  override def visitModuleDefinition(ctx: LanguageParser.ModuleDefinitionContext): ast.Module.Definition | ParseError = {
    super.visitModuleDefinition(ctx)
    val moduleName: Names.Qualified.LowerCase | ParseError = visitModuleName(ctx.moduleName())
    val moduleBody: ast.Module.Body | ParseError = visitModuleBody(ctx.moduleBody())
    return moduleName match {
      case name: Names.Qualified.LowerCase => moduleBody match {
        case body: ast.Module.Body => ast.Module.Definition(
          name = name,
          body = body,
          metadata = contextMetadata(ctx)
        )
        case error: ParseError => error
      }
      case error: ParseError => ParseError(
        message = "Invalid module declaration",
        metadata = contextMetadata(ctx),
        cause = error
      )

    }
  }

  override def visitModuleName(ctx: LanguageParser.ModuleNameContext): Names.Qualified.LowerCase | ParseError = {
    super.visitModuleName(ctx)
    return ctx.LOWERCASE_NAME().asScala.map(item => parseName(item))
      .foldLeft[Names.Qualified.LowerCase | ParseError](Names.Qualified.LowerCase(metadata = contextMetadata(ctx)))((accumulator: Names.Qualified.LowerCase | ParseError, item: base.Partial.Name | ParseError) =>
        accumulator match {
          case parseError: ParseError => parseError;
          case shadowAccumulator: Names.Qualified.LowerCase => item match {
            case apendee: base.Partial.Name => apendee match {
              case name: Names.LowerCase => shadowAccumulator.concat(name);
              case _ => ParserBugError(metadata = contextMetadata(ctx));
            };
            case error: ParseError => ParseError(
              message = s"Invalid module name",
              metadata = shadowAccumulator.metadata,
              cause = error
            );
          }
        })
  }

  override def visitPackageDeclaration(ctx: LanguageParser.PackageDeclarationContext): ast.Package.Declaration | ParseError = {
    super.visitPackageDeclaration(ctx)
    val name: base.Partial.Name | ParseError = parseName(ctx.LOWERCASE_NAME())
    return name match {
      case shadowName: Names.LowerCase =>
        ast.Package.Declaration(
          name = shadowName,
          metadata = contextMetadata(ctx)
        )
      case error: ParseError => new ParseError(cause = error, message = "Invalid package name ", metadata = contextMetadata(ctx))
      case _ => new ParserBugError(metadata = contextMetadata(ctx))
    }

  }

  override def visitPackageDefinition(ctx: LanguageParser.PackageDefinitionContext): ast.Package.Definition | ParseError = {
    super.visitPackageDefinition(ctx)
    val name: base.Partial.Name | ParseError = parseName(ctx.LOWERCASE_NAME())
    val body: ast.Package.Body | ParseError = visitPackageBody(ctx.packageBody());
    return name match {
      case shadowName: Names.LowerCase =>
        body match {
          case shadowBody: ast.Package.Body => ast.Package.Definition(
            name = shadowName,
            body = shadowBody,
            metadata = contextMetadata(ctx)
          )
          case parseError: ParseError => new ParseError(cause = parseError, message = "Invalid body definition", metadata = contextMetadata(ctx))
        }

      case error: ParseError => ParseError(cause = error, message = "Invalid package name ", metadata = contextMetadata(ctx))
      case _ => new ParserBugError(metadata = contextMetadata(ctx))
    }
  }


  override def visitPackageBody(ctx: LanguageParser.PackageBodyContext): ast.Package.Body | ParseError = {
    super.visitPackageBody(ctx)
    val valueDefinitions: Seq[ast.Value.Definition | ParseError] = ctx.valueDefinition().asScala
      .map(item => visitValueDefinition(item)).toSeq

    return ast.Package.Body(
      elements = valueDefinitions,
      metadata = contextMetadata(ctx)
    )
  }

  override def visitValueDefinition(ctx: LanguageParser.ValueDefinitionContext): ast.Value.Definition | ParseError = {
    super.visitValueDefinition(ctx);

    val typeReference: Partial.Type.Reference | ParseError = visitValueTypeReference(ctx.valueTypeReference());
    val name: base.Partial.Name | ParseError = parseName(ctx.LOWERCASE_NAME());
    val initialization: Expression | ParseError = visitExpression(ctx.expression());
    return typeReference match {
      case valueType: ast.Value.Type.Reference =>
        name match {
          case shadowName: Names.LowerCase =>
            initialization match {
              case expression: Expression => ast.Value.Definition(
                name = shadowName,
                typeReference = valueType,
                initialization = expression,
                metadata = contextMetadata(ctx)
              );
              case error: ParseError => new ParseError(
                cause = error,
                message = "Invalid value initialization expression ",
                metadata = contextMetadata(ctx)
              )

            }
          case error: ParseError => new ParseError(
            cause = error,
            message = "Invalid value name ",
            metadata = contextMetadata(ctx))
          case _ => ParserBugError(metadata = contextMetadata(ctx));
        }
      case error: ParseError => new ParseError(
        cause = error,
        message = "Invalid value type",
        metadata = contextMetadata(ctx)
      )
      case other => ParseError(
        message = f"Not Implemented, parsing type: ${other}!",
        metadata = contextMetadata(ctx)
      )


    }
  }


  override def visitExpression(ctx: LanguageParser.ExpressionContext): Expression | ParseError = {
    super.visitExpression(ctx);
    return Option(ctx.literalExpression())
      .map(expression => visitLiteralExpression(expression))
      .getOrElse(
        ParseError(message = s"Unknown expression: ${ctx.getText}",
          metadata = contextMetadata(ctx)
        )
      );
  }


  override def visitLiteralExpression(ctx: LanguageParser.LiteralExpressionContext): base.expressions.Literal | ParseError = {
    super.visitLiteralExpression(ctx);
    val result = ctx.children.stream()
      .filter((parseTree: ParseTree) => parseTree.isInstanceOf[TerminalNode])
      .map[TerminalNode | ParseError](node => node.asInstanceOf[TerminalNode])
      .findFirst()
      .orElseGet(() => new ParserBugError(
        metadata = contextMetadata(ctx)
      ));
    return result match {
      case terminalNode: TerminalNode => {
        val text = terminalNode.getText;
        terminalNode.getSymbol.getType match {
          case LanguageParser.BOOLEAN_LITERAL => Literals.Boolean(text.toBoolean, metadata = contextMetadata(ctx));
          case LanguageParser.INTEGER_LITERAL => Literals.Integer(text.toInt, metadata = contextMetadata(ctx));
          case LanguageParser.FLOAT_LITERAL => Literals.Float(text.toDouble, metadata = contextMetadata(ctx));
          case LanguageParser.CHARACTER_LITERAL => Literals.Character(text(1), metadata = contextMetadata(ctx));
          case LanguageParser.STRING_LITERAL => Literals.String(text.substring(1, text.length - 1), metadata = contextMetadata(ctx));
          case _ => ParseError(
            message = "Unknown literal: " + text,
            metadata = contextMetadata(ctx)
          );
        }
      }
      case error: ParseError => error
    }
  }


};

object LanguageVisitor {

}