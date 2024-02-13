package org.alax.parser

import org.alax.ast.base.{ParseError, *}
import org.alax.ast.base.Node.Metadata
import org.alax.ast.partial.Identifier
import org.alax.ast.{LanguageLexer, LanguageParser, LanguageParserBaseVisitor, Literals, Value, base}
import org.alax.ast
import org.antlr.v4.runtime.{ParserRuleContext, Token, TokenStream}
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode}

import javax.management.ValueExp
import scala.collection.mutable
import scala.jdk.CollectionConverters.*

class LanguageVisitor(
                       metadataParser: MetadataParser,
                       identifierParser: IdentifierParser,
                     ) extends LanguageParserBaseVisitor[Node | ParseError] {


  override def visitSimpleImportDeclaration(ctx: LanguageParser.SimpleImportDeclarationContext): ast.Imports.Simple | ParseError = {
    super.visitSimpleImportDeclaration(ctx)
    val identifierOrError: ast.Import.Identifier | ParseError = visitImportIdentifier(ctx.importIdentifier());
    return identifierOrError match {
      case e: ParseError => new ParseError(metadata = metadataParser.parse.metadata(ctx), message = "Invalid import declaration", cause = e);
      case identifier: ast.Import.Identifier => {
        ast.Imports.Simple(member = identifier, metadata = metadataParser.parse.metadata(ctx))
      }
    }
  }


  override def visitImportIdentifier(ctx: LanguageParser.ImportIdentifierContext): ast.Import.Identifier | ParseError = {
    super.visitImportIdentifier(ctx)
    val parts: Seq[Identifier.LowerCase | Identifier.UpperCase] = ctx.children.asScala
      .filter(item => item.isInstanceOf[TerminalNode])
      .map(item => item.asInstanceOf[TerminalNode])
      .filter(item => item.getSymbol.getType == LanguageParser.UPPERCASE_IDENTIFIER || item.getSymbol.getType == LanguageParser.LOWERCASE_IDENTIFIER)
      .map(item => item.getSymbol.getType match {
        case LanguageParser.UPPERCASE_IDENTIFIER => Identifier.UpperCase(item.getText, metadataParser.parse.metadata(item.getSymbol));
        case LanguageParser.LOWERCASE_IDENTIFIER => Identifier.LowerCase(item.getText, metadataParser.parse.metadata(item.getSymbol));
        case _ => return ParserBugError(metadataParser.parse.metadata(item.getSymbol))
      }).toSeq;
    val isMatching: Boolean = ast.Import.Identifier.matches(parts);
    return if isMatching then ast.Import.Identifier(parts = parts, metadata = metadataParser.parse.metadata(ctx))
    else new ParseError(metadata = metadataParser.parse.metadata(ctx), message = s"Invalid import identifier: ${ast.base.Partial.Identifier.fold(parts, ".")}")
  }

  override def visitValueTypeReference(ctx: LanguageParser.ValueTypeReferenceContext): ast.Value.Type.Reference | ParseError = {
    super.visitValueTypeReference(ctx)
    //TODO check null ?
    val importIdentifierOrError: ast.Import.Identifier | ParseError = visitImportIdentifier(ctx.importIdentifier())
    val partialTypeIdentifierOrError: ast.partial.Identifier.UpperCase | ParseError = if (ast.partial.Identifier.UpperCase.matches(ctx.UPPERCASE_IDENTIFIER().getText))
    then ast.partial.Identifier.UpperCase(value = ctx.UPPERCASE_IDENTIFIER().getText, metadata = metadataParser.parse.metadata(ctx.UPPERCASE_IDENTIFIER().getSymbol))
    else new ParseError(metadata = metadataParser.parse.metadata(ctx), message = "Invalid type reference")
    return importIdentifierOrError match {
      case identifier: ast.Import.Identifier => partialTypeIdentifierOrError match {
        case typeIdentifier: ast.partial.Identifier.UpperCase => ast.Value.Type.Reference(importIdentifier = identifier, typeIdentifier = typeIdentifier, metadata = metadataParser.parse.metadata(ctx))
        case parseError: ParseError => new ParseError(metadata = metadataParser.parse.metadata(ctx), message = "Invalid type reference", cause = parseError)
      }
      case error: ParseError => new ParseError(metadata = metadataParser.parse.metadata(ctx), message = "Invalid type reference", cause = error)
    }
  }

  override def visitValueIdentifier(ctx: LanguageParser.ValueIdentifierContext): ast.Value.Identifier | ParseError = {
    super.visitValueIdentifier(ctx)
    if (ast.Value.Identifier.matches(ctx.LOWERCASE_IDENTIFIER().getText))
    then ast.Value.Identifier(value = ctx.LOWERCASE_IDENTIFIER().getText, metadata = metadataParser.parse.metadata(ctx))
    else ParseError(
      metadata = metadataParser.parse.metadata(ctx),
      message = s"Invalid value identifier"
    );
  }

  override def visitValueDeclaration(ctx: LanguageParser.ValueDeclarationContext): ast.Value.Declaration | ParseError = {
    super.visitValueDeclaration(ctx);
    val typeReference: Partial.Type.Reference | ParseError = visitValueTypeReference(ctx.valueTypeReference());
    val name: ast.Value.Identifier | ParseError = visitValueIdentifier(ctx.valueIdentifier());
    return typeReference match {
      case valueType: ast.Value.Type.Reference =>
        name match {
          case shadowName: ast.Value.Identifier =>
            ast.Value.Declaration(
              identifier = shadowName,
              typeReference = valueType,
              metadata = metadataParser.parse.metadata(ctx)
            )
          case error: ParseError => new ParseError(cause = error, message = "Invalid value declaration ", metadata = metadataParser.parse.metadata(ctx))
        }
      case error: ParseError => new ParseError(cause = error, message = "Invalid value declaration", metadata = metadataParser.parse.metadata(ctx))
      case other => ParseError(
        message = f"Not Implemented, parsing type: ${other}!",
        metadata = metadataParser.parse.metadata(ctx)
      )
    }
  }


  override def visitModuleDeclaration(ctx: LanguageParser.ModuleDeclarationContext): ast.Module.Declaration | ParseError = {
    super.visitModuleDeclaration(ctx)
    val moduleName: ast.Module.Identifier | ParseError = visitModuleIdentifier(ctx.moduleIdentifier())
    return moduleName match {
      case name: ast.Module.Identifier => ast.Module.Declaration(
        identifier = name,
        metadata = metadataParser.parse.metadata(ctx)
      )
      case error: ParseError => ParseError(
        message = "Invalid module declaration",
        metadata = metadataParser.parse.metadata(ctx),
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
      metadata = metadataParser.parse.metadata(ctx)
    )
  }

  override def visitModuleDefinition(ctx: LanguageParser.ModuleDefinitionContext): ast.Module.Definition | ParseError = {
    super.visitModuleDefinition(ctx)
    val moduleIdentifier: ast.Module.Identifier | ParseError = visitModuleIdentifier(ctx.moduleIdentifier())
    val moduleBody: ast.Module.Body | ParseError = visitModuleBody(ctx.moduleBody())
    return moduleIdentifier match {
      case name: ast.Module.Identifier => moduleBody match {
        case body: ast.Module.Body => ast.Module.Definition(
          identifier = name,
          body = body,
          metadata = metadataParser.parse.metadata(ctx)
        )
        case error: ParseError => error
      }
      case error: ParseError => ParseError(
        message = "Invalid module definition",
        metadata = metadataParser.parse.metadata(ctx),
        cause = error
      )

    }
  }

  override def visitModuleIdentifier(ctx: LanguageParser.ModuleIdentifierContext): ast.Module.Identifier | ParseError = {
    super.visitModuleIdentifier(ctx)
    val parts: Seq[Identifier.LowerCase] = ctx.LOWERCASE_IDENTIFIER().asScala
      .map(item => ast.partial.Identifier.LowerCase(value = item.getText, metadata = metadataParser.parse.metadata(item.getSymbol)))
      .toSeq
    if ast.Module.Identifier.matches(parts) then ast.Module.Identifier(parts = parts, metadata = metadataParser.parse.metadata(ctx))
    else new ParseError(
      metadata = metadataParser.parse.metadata(ctx),
      message = s"Invalid module identifier"
    )
  }

  override def visitPackageIdentifier(ctx: LanguageParser.PackageIdentifierContext): ast.Package.Identifier | ParseError = {
    super.visitPackageIdentifier(ctx);
    if ast.Package.Identifier.matches(ctx.LOWERCASE_IDENTIFIER().getText) then ast.Package.Identifier(value = ctx.LOWERCASE_IDENTIFIER().getText, metadata = metadataParser.parse.metadata(ctx))
    else new ParseError(
      metadata = metadataParser.parse.metadata(ctx),
      message = s"Invalid package identifier"
    )
  }


  override def visitPackageDeclaration(ctx: LanguageParser.PackageDeclarationContext): ast.Package.Declaration | ParseError = {
    super.visitPackageDeclaration(ctx)
    val name: ast.Package.Identifier | ParseError = visitPackageIdentifier(ctx.packageIdentifier())
    name match {
      case shadowName: ast.Package.Identifier => ast.Package.Declaration(
        identifier = shadowName,
        metadata = metadataParser.parse.metadata(ctx)
      )
      case error: ParseError => new ParseError(cause = error, message = "Invalid package declaration ", metadata = metadataParser.parse.metadata(ctx))
    }

  }

  override def visitPackageDefinition(ctx: LanguageParser.PackageDefinitionContext): ast.Package.Definition | ParseError = {
    super.visitPackageDefinition(ctx)
    val name: ast.Package.Identifier | ParseError = visitPackageIdentifier(ctx.packageIdentifier())
    val body: ast.Package.Body | ParseError = visitPackageBody(ctx.packageBody());
    return name match {
      case shadowName: ast.Package.Identifier =>
        body match {
          case shadowBody: ast.Package.Body => ast.Package.Definition(
            identifier = shadowName,
            body = shadowBody,
            metadata = metadataParser.parse.metadata(ctx)
          )
          case parseError: ParseError => new ParseError(cause = parseError, message = "Invalid body definition", metadata = metadataParser.parse.metadata(ctx))
        }

      case error: ParseError => ParseError(cause = error, message = "Invalid package definition ", metadata = metadataParser.parse.metadata(ctx))
    }
  }


  override def visitPackageBody(ctx: LanguageParser.PackageBodyContext): ast.Package.Body | ParseError = {
    super.visitPackageBody(ctx)
    val valueDefinitions: Seq[ast.Value.Definition | ParseError] = ctx.valueDefinition().asScala
      .map(item => visitValueDefinition(item)).toSeq

    return ast.Package.Body(
      elements = valueDefinitions,
      metadata = metadataParser.parse.metadata(ctx)
    )
  }

  override def visitValueDefinition(ctx: LanguageParser.ValueDefinitionContext): ast.Value.Definition | ParseError = {
    super.visitValueDefinition(ctx);

    val typeReference: Partial.Type.Reference | ParseError = visitValueTypeReference(ctx.valueTypeReference());
    val name: ast.Value.Identifier | ParseError = visitValueIdentifier(ctx.valueIdentifier());
    val initialization: Expression | ParseError = visitExpression(ctx.expression());
    return typeReference match {
      case valueType: ast.Value.Type.Reference =>
        name match {
          case shadowName: ast.Value.Identifier =>
            initialization match {
              case expression: Expression => ast.Value.Definition(
                name = shadowName,
                typeReference = valueType,
                initialization = expression,
                metadata = metadataParser.parse.metadata(ctx)
              );
              case error: ParseError => new ParseError(
                cause = error,
                message = "Invalid value initialization expression ",
                metadata = metadataParser.parse.metadata(ctx)
              )

            }
          case error: ParseError => new ParseError(
            cause = error,
            message = "Invalid value name ",
            metadata = metadataParser.parse.metadata(ctx))
        }
      case error: ParseError => new ParseError(
        cause = error,
        message = "Invalid value type",
        metadata = metadataParser.parse.metadata(ctx)
      )
      case other => ParseError(
        message = f"Not Implemented, parsing type: ${other}!",
        metadata = metadataParser.parse.metadata(ctx)
      )


    }
  }


  override def visitExpression(ctx: LanguageParser.ExpressionContext): Expression | ParseError = {
    super.visitExpression(ctx);
    return Option(ctx.literalExpression())
      .map(expression => visitLiteralExpression(expression))
      .getOrElse(
        ParseError(message = s"Unknown expression",
          metadata = metadataParser.parse.metadata(ctx)
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
        metadata = metadataParser.parse.metadata(ctx)
      ));
    return result match {
      case terminalNode: TerminalNode => {
        val text = terminalNode.getText;
        terminalNode.getSymbol.getType match {
          case LanguageParser.BOOLEAN_LITERAL => Literals.Boolean(text.toBoolean, metadata = metadataParser.parse.metadata(ctx));
          case LanguageParser.INTEGER_LITERAL => Literals.Integer(text.toInt, metadata = metadataParser.parse.metadata(ctx));
          case LanguageParser.FLOAT_LITERAL => Literals.Float(text.toDouble, metadata = metadataParser.parse.metadata(ctx));
          case LanguageParser.CHARACTER_LITERAL => Literals.Character(text(1), metadata = metadataParser.parse.metadata(ctx));
          case LanguageParser.STRING_LITERAL => Literals.String(text.substring(1, text.length - 1), metadata = metadataParser.parse.metadata(ctx));
          case _ => ParseError(
            message = "Unknown literal",
            metadata = metadataParser.parse.metadata(ctx)
          );
        }
      }
      case error: ParseError => error
    }
  }


};

object LanguageVisitor {

}