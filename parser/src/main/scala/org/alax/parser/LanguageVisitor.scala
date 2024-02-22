package org.alax.parser

import org.alax.ast.base.{ParseError, *}
import org.alax.ast.base.Node.Metadata
import org.alax.ast.partial.Identifier
import org.alax.ast.{Chain, LanguageLexer, LanguageParser, LanguageParserBaseVisitor, Literals, Return, Value, base}
import org.alax.ast
import org.antlr.v4.runtime.{ParserRuleContext, Token, TokenStream}
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode}

import javax.management.ValueExp
import scala.collection.mutable
import scala.jdk.CollectionConverters.*

class LanguageVisitor(
                       metadataParser: MetadataParser
                     ) extends LanguageParserBaseVisitor[Node | ParseError] {


  override def visitIdentifier(ctx: LanguageParser.IdentifierContext): ast.Identifier | ParseError = {
    super.visitIdentifier(ctx)
    val text = ctx.children.asScala
      .map(item => item.getText).foldLeft(new mutable.StringBuilder(""))((acc: mutable.StringBuilder, item: String) =>
      if acc.isEmpty then acc.append(item) else acc.append(" ").append(item))
      .toString()
    return if ast.Identifier.matches(text)
    then ast.Identifier(text, metadataParser.parse.metadata(ctx))
    else new ParseError(metadata = metadataParser.parse.metadata(ctx), message = "Invalid identifier")
  }

  override def visitLowercaseIdentifier(ctx: LanguageParser.LowercaseIdentifierContext): ast.Identifier.LowerCase | ParseError = {
    super.visitLowercaseIdentifier(ctx)
    val text = ctx.children.asScala
      .map(item => item.getText).foldLeft(new mutable.StringBuilder(""))((acc: mutable.StringBuilder, item: String) =>
      if acc.isEmpty then acc.append(item) else acc.append(" ").append(item))
      .toString()
    return if ast.Identifier.LowerCase.matches(text)
    then ast.Identifier.LowerCase(text, metadataParser.parse.metadata(ctx))
    else new ParseError(metadata = metadataParser.parse.metadata(ctx), message = "Invalid lowercase identifier")
  }

  override def visitUppercaseIdentifier(ctx: LanguageParser.UppercaseIdentifierContext): ast.Identifier.UpperCase | ParseError = {
    super.visitUppercaseIdentifier(ctx)
    val text = ctx.children.asScala
      .map(item => item.getText).foldLeft(new mutable.StringBuilder(""))((acc: mutable.StringBuilder, item: String) =>
      if acc.isEmpty then acc.append(item) else acc.append(" ").append(item))
      .toString()
    return if ast.Identifier.UpperCase.matches(text)
    then ast.Identifier.UpperCase(text, metadataParser.parse.metadata(ctx))
    else new ParseError(metadata = metadataParser.parse.metadata(ctx), message = "Invalid uppercase identifier")
  }


  override def visitSimpleImportDeclaration(ctx: LanguageParser.SimpleImportDeclarationContext): ast.Imports.Simple | ParseError = {
    super.visitSimpleImportDeclaration(ctx)
    val identifierOrError = visitImportIdentifier(ctx.importIdentifier());
    return identifierOrError match {
      case e: ParseError => new ParseError(metadata = metadataParser.parse.metadata(ctx), message = "Invalid import declaration", cause = Seq(e));
      case identifier: ast.Import.Identifier => {
        ast.Imports.Simple(member = identifier, metadata = metadataParser.parse.metadata(ctx))
      }
    }
  }


  override def visitImportIdentifier(ctx: LanguageParser.ImportIdentifierContext): ast.Import.Identifier | ParseError = {
    super.visitImportIdentifier(ctx)
    val identifiers = mutable.Buffer[ast.Identifier]();
    val errors = mutable.Buffer[ParseError]();
    ctx.identifier().asScala
      .map(item => visitIdentifier(item))
      .foreach {
        case id: ast.Identifier => identifiers.addOne(id)
        case error: ParseError => errors.addOne(error)
      }
    return if errors.nonEmpty then new ParseError(
      metadata = metadataParser.parse.metadata(ctx),
      message = "Invalid import identifier",
      cause = errors.toSeq
    ) else ast.Import.Identifier(
      parts = identifiers.toSeq, metadataParser.parse.metadata(ctx)
    )
  }

  override def visitValueTypeIdentifier(ctx: LanguageParser.ValueTypeIdentifierContext): ast.Value.Type.Identifier | ParseError = {
    super.visitValueTypeIdentifier(ctx)
    val typeIdOrError: ast.Identifier.UpperCase | ParseError = visitUppercaseIdentifier(ctx.uppercaseIdentifier())
    val identifiers = mutable.Buffer[ast.Identifier]();
    val errors = mutable.Buffer[ParseError]();
    ctx.identifier().asScala
      .map(item => visitIdentifier(item))
      .foreach {
        case id: ast.Identifier => identifiers.addOne(id)
        case error: ParseError => errors.addOne(error)
      }
    return if errors.nonEmpty then new ParseError(
      metadata = metadataParser.parse.metadata(ctx),
      message = "Invalid value type identifier",
      cause = errors.toSeq
    ) else typeIdOrError match {
      case typeId: ast.Identifier.UpperCase => ast.Value.Type.Identifier(
        prefix = identifiers.toSeq,
        suffix = typeId,
        metadata = metadataParser.parse.metadata(ctx)
      )
      case parseError: ParseError => new ParseError(
        metadata = metadataParser.parse.metadata(ctx),
        message = "Invalid value type identifier",
        cause = Seq(parseError)
      )
    }
  }

  override def visitValueIdentifier(ctx: LanguageParser.ValueIdentifierContext): ast.Value.Identifier | ParseError = {
    super.visitValueIdentifier(ctx)
    val idOrError: ast.Identifier.LowerCase | ParseError = visitLowercaseIdentifier(ctx.lowercaseIdentifier());
    return idOrError match {
      case id: ast.Identifier.LowerCase => ast.Value.Identifier(
        value = id.text, metadata = metadataParser.parse.metadata(ctx),
      )
      case error: ParseError => ParseError(
        metadata = metadataParser.parse.metadata(ctx),
        message = s"Invalid value identifier",
        cause = Seq(error)
      )
    }
  }

  override def visitValueDeclaration(ctx: LanguageParser.ValueDeclarationContext): ast.Value.Declaration | ParseError = {
    super.visitValueDeclaration(ctx);
    val valueTypeIdentifier: ast.Value.Type.Identifier | ParseError = visitValueTypeIdentifier(ctx.valueTypeIdentifier());
    val name: ast.Value.Identifier | ParseError = visitValueIdentifier(ctx.valueIdentifier());
    return valueTypeIdentifier match {
      case valueType: ast.Value.Type.Identifier =>
        name match {
          case shadowName: ast.Value.Identifier =>
            ast.Value.Declaration(
              identifier = shadowName,
              typeReference = valueType,
              metadata = metadataParser.parse.metadata(ctx)
            )
          case error: ParseError => new ParseError(cause = Seq(error), message = "Invalid value declaration ", metadata = metadataParser.parse.metadata(ctx))
        }
      case error: ParseError => new ParseError(cause = Seq(error), message = "Invalid value declaration", metadata = metadataParser.parse.metadata(ctx))
    }
  }

//  override def visitFunctionalParameter(ctx: LanguageParser.FunctionParameterContext): Any = {
//    super.visitFunctionParameter(ctx)
//  }



  override def visitReturnStatement(ctx: LanguageParser.ReturnStatementContext): Return.Statement | ParseError = {
    super.visitReturnStatement(ctx)
    val expressionOrError: Chain.Expression | ParseError = visitExpressionChain(ctx.expressionChain())
    return expressionOrError match {
      case chain: Chain.Expression => Return.Statement(
        expression = chain, metadata = metadataParser.parse.metadata(ctx)
      )
      case parseError: ParseError => parseError
    }
  }

  override def visitExpressionChain(ctx: LanguageParser.ExpressionChainContext): Chain.Expression | ParseError = {
    super.visitExpressionChain(ctx)
    return Chain.Expression(
      expressions = ctx.expression().asScala.map(item => visitExpression(item)).toSeq,
      metadata = metadataParser.parse.metadata(ctx)
    )
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
        cause = Seq(error)
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
        cause = Seq(error)
      )

    }
  }

  override def visitModuleIdentifier(ctx: LanguageParser.ModuleIdentifierContext): ast.Module.Identifier | ParseError = {
    super.visitModuleIdentifier(ctx)
    val identifiers = mutable.Buffer[ast.Identifier.LowerCase]();
    val errors = mutable.Buffer[ParseError]();
    ctx.lowercaseIdentifier()
      .asScala.map(item => visitLowercaseIdentifier(item))
      .foreach {
        case id: ast.Identifier.LowerCase => identifiers.addOne(id)
        case error: ParseError => errors.addOne(error)
      }
    if errors.isEmpty then ast.Module.Identifier(
      parts = identifiers.toSeq,
      metadata = metadataParser.parse.metadata(ctx),
    ) else new ParseError(
      metadata = metadataParser.parse.metadata(ctx),
      message = s"Invalid module identifier",
      cause = errors.toSeq
    )
  }

  override def visitPackageIdentifier(ctx: LanguageParser.PackageIdentifierContext): ast.Package.Identifier | ParseError = {
    super.visitPackageIdentifier(ctx);
    val identifierOrError: ast.Identifier.LowerCase | ParseError = visitLowercaseIdentifier(ctx.lowercaseIdentifier());
    return identifierOrError match {
      case id: ast.Identifier.LowerCase => ast.Package.Identifier(
        value = id.text,
        metadata = metadataParser.parse.metadata(ctx),
      )
      case error: ParseError => ParseError(
        metadata = metadataParser.parse.metadata(ctx),
        message = s"Invalid package identifier",
        cause = Seq(error)
      )
    }
  }


  override def visitPackageDeclaration(ctx: LanguageParser.PackageDeclarationContext): ast.Package.Declaration | ParseError = {
    super.visitPackageDeclaration(ctx)
    val name: ast.Package.Identifier | ParseError = visitPackageIdentifier(ctx.packageIdentifier())
    name match {
      case shadowName: ast.Package.Identifier => ast.Package.Declaration(
        identifier = shadowName,
        metadata = metadataParser.parse.metadata(ctx)
      )
      case error: ParseError => new ParseError(cause = Seq(error), message = "Invalid package declaration ", metadata = metadataParser.parse.metadata(ctx))
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
          case parseError: ParseError => new ParseError(cause = Seq(parseError), message = "Invalid body definition", metadata = metadataParser.parse.metadata(ctx))
        }

      case error: ParseError => ParseError(cause = Seq(error), message = "Invalid package definition ", metadata = metadataParser.parse.metadata(ctx))
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

    val typeIdentifier: ast.Value.Type.Identifier | ParseError = visitValueTypeIdentifier(ctx.valueTypeIdentifier());
    val name: ast.Value.Identifier | ParseError = visitValueIdentifier(ctx.valueIdentifier());
    val initialization: Expression | ParseError = visitExpression(ctx.expression());
    return typeIdentifier match {
      case valueType: ast.Value.Type.Identifier =>
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
                cause = Seq(error),
                message = "Invalid value initialization expression ",
                metadata = metadataParser.parse.metadata(ctx)
              )

            }
          case error: ParseError => new ParseError(
            cause = Seq(error),
            message = "Invalid value name ",
            metadata = metadataParser.parse.metadata(ctx))
        }
      case error: ParseError => new ParseError(
        cause = Seq(error),
        message = "Invalid value type",
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