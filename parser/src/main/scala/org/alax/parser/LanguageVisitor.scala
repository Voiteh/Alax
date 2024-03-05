package org.alax.parser

import org.alax.ast.base.*
import org.alax.ast.base.Node.Metadata
import org.alax.ast.{Chain, LanguageLexer, LanguageParser, LanguageParserBaseVisitor, Literals, Value, base}
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

  override def visitFunctionIdentifier(ctx: LanguageParser.FunctionIdentifierContext): ast.Function.Identifier | ParseError = {
    super.visitFunctionIdentifier(ctx)
    val idOrError: ast.Identifier.LowerCase | ParseError = visitLowercaseIdentifier(ctx.lowercaseIdentifier());
    return idOrError match {
      case id: ast.Identifier.LowerCase => id
      case error: ParseError => ParseError(
        metadata = metadataParser.parse.metadata(ctx),
        message = s"Invalid value identifier",
        cause = Seq(error)
      )
    }
  }

  override def visitFunctionBlockBody(ctx: LanguageParser.FunctionBlockBodyContext): ast.Function.Block.Body | ParseError = {
    super.visitFunctionBlockBody(ctx)
    ast.Function.Block.Body(
      elements = ctx.children.asScala.filter(item => item.isInstanceOf[
          LanguageParser.ValueDeclarationContext
            | LanguageParser.ValueDefinitionContext
            | LanguageParser.ValueAssignmentExpressionContext
            | LanguageParser.FunctionCallExpressionContext
        ])
          .map {
            case valueDeclaration: LanguageParser.ValueDeclarationContext => visitValueDeclaration(valueDeclaration)
            case valueDefinition: LanguageParser.ValueDefinitionContext => visitValueDefinition(valueDefinition)
            case assignmentExpression: LanguageParser.ValueAssignmentExpressionContext => visitValueAssignmentExpression(assignmentExpression)
            case functionCallExpression: LanguageParser.FunctionCallExpressionContext => visitFunctionCallExpression(functionCallExpression)
            case _ => new ParserBugError(metadata = metadataParser.parse.metadata(ctx))
          }
          .toSeq,
      metadata = metadataParser.parse.metadata(ctx)
    )


  }

  override def visitFunctionLambdaBody(ctx: LanguageParser.FunctionLambdaBodyContext): ast.Function.Lambda.Body | ParseError = {
    super.visitFunctionLambdaBody(ctx)
    Option(ctx.functionCallExpression()).map(item => visitFunctionCallExpression(item))
      .orElse(
        Option(ctx.valueAssignmentExpression())
          .map(item => visitValueAssignmentExpression(item))
      ).getOrElse(new ParserBugError(metadata = metadataParser.parse.metadata(ctx)))
  }

  override def visitFunctionBody(ctx: LanguageParser.FunctionBodyContext): ast.Function.Body | ParseError = {
    super.visitFunctionBody(ctx)
    Option(ctx.functionBlockBody())
      .map(item => visitFunctionBlockBody(item))
      .orElse(
        Option(
          ctx.functionLambdaBody()
        ).map(item => visitFunctionLambdaBody(item))
      )
      .getOrElse(new ParserBugError(metadata = metadataParser.parse.metadata(ctx)))
  }

  override def visitChainExpression(ctx: LanguageParser.ChainExpressionContext): Chain.Expression | ParseError = {
    super.visitChainExpression(ctx)
    val expressionOrParseError: ast.base.Expression | ParseError = visitExpression(ctx.expression())
    val nextOrError: Chain.Expression | ParseError | Null = Option(ctx.chainExpression())
      .map((item: LanguageParser.ChainExpressionContext) => visitChainExpression(item))
      .orNull;
    return expressionOrParseError match {
      case expression: ast.base.Expression =>
        nextOrError match {
          case chain: Chain.Expression => Chain.Expression(
            expression = expression,
            next = chain,
            metadata = metadataParser.parse.metadata(ctx)
          )
          case null: Null => Chain.Expression(
            expression = expression,
            next = null,
            metadata = metadataParser.parse.metadata(ctx)
          )
        }
      case error: ParseError => ParseError(
        message = "Invalid chain expression",
        metadata = metadataParser.parse.metadata(ctx),
        cause = Seq(error)
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

  override def visitFunctionCallNamedArgument(ctx: LanguageParser.FunctionCallNamedArgumentContext): ast.Function.Call.Named.Argument | ParseError = {
    super.visitFunctionCallNamedArgument(ctx)
    val idOrError: ast.Identifier.LowerCase | ParseError = visitLowercaseIdentifier(ctx.lowercaseIdentifier())
    val chainExpressionOrError: ast.Chain.Expression | ParseError = visitChainExpression(ctx.chainExpression())

    return idOrError match {
      case parseError: ParseError => new ParseError(
        metadata = metadataParser.parse.metadata(ctx),
        message = "Invalid function call named argument",
        cause = Seq(parseError)
      )
      case id: ast.Identifier.LowerCase => chainExpressionOrError match {
        case chainExpression: ast.Chain.Expression => ast.Function.Call.Named.Argument(
          identifier = id,
          expression = chainExpression,
          metadata = metadataParser.parse.metadata(ctx)
        )
        case error: ParseError => new ParseError(
          metadata = metadataParser.parse.metadata(ctx),
          message = "Invalid value type",
          cause = Seq(error)
        )
      }
    }


  }

  override def visitFunctionCallPositionalArgument(ctx: LanguageParser.FunctionCallPositionalArgumentContext): ast.Function.Call.Positional.Argument | ParseError = {
    super.visitFunctionCallPositionalArgument(ctx)
    val chainExpressionOrError: ast.Chain.Expression | ParseError = visitChainExpression(ctx.chainExpression())
    return chainExpressionOrError match {
      case chainExpression: ast.Chain.Expression => ast.Function.Call.Positional.Argument(
        expression = chainExpression, metadata = metadataParser.parse.metadata(ctx)
      )
      case error: ParseError => new ParseError(
        metadata = metadataParser.parse.metadata(ctx),
        message = "Invalid value type",
        cause = Seq(error)
      )
    }

  }

  override def visitFunctionCallArgument(ctx: LanguageParser.FunctionCallArgumentContext): ast.Function.Call.Argument | ParseError = {
    super.visitFunctionCallArgument(ctx)
    return Option(ctx.functionCallPositionalArgument())
      .map(item => visitFunctionCallPositionalArgument(item))
      .orElse(
        Option(ctx.functionCallNamedArgument())
          .map(item => visitFunctionCallNamedArgument(item))
      ).getOrElse(new ParserBugError(
      metadata = metadataParser.parse.metadata(ctx)
    ))
  }

  override def visitValueAssignmentExpression(ctx: LanguageParser.ValueAssignmentExpressionContext): Value.Assignment.Expression | ParseError = {
    super.visitValueAssignmentExpression(ctx)
    val referenceOrError: Value.Reference | ParseError = visitValueReference(ctx.valueReference())
    val chainExpressionOrError: Chain.Expression | ParseError = visitChainExpression(ctx.chainExpression())
    return referenceOrError match {
      case reference: Value.Reference => chainExpressionOrError match {
        case chain: Chain.Expression => Value.Assignment.Expression(left = reference, right = chain, metadata = metadataParser.parse.metadata(ctx))
        case error: ParseError => new ParseError(metadata = metadataParser.parse.metadata(ctx), message = "Invalid value assignment expression", cause = Seq(error))
      }
      case error: ParseError => new ParseError(metadata = metadataParser.parse.metadata(ctx), message = "Invalid value assignment expression", cause = Seq(error))
    }
  }


  override def visitFunctionCallExpression(ctx: LanguageParser.FunctionCallExpressionContext): ast.Function.Call.Expression | ParseError = {
    super.visitFunctionCallExpression(ctx)
    val referenceOrError: ast.Function.Reference | ParseError = visitFunctionReference(ctx.functionReference())
    return referenceOrError match {
      case reference: ast.Function.Reference => {
        val arguments = mutable.Buffer[ast.Function.Call.Argument]();
        val errors = mutable.Buffer[ParseError]();
        ctx.functionCallArgument().asScala
          .map(item => visitFunctionCallArgument(item))
          .foreach {
            case id: ast.Function.Call.Argument => arguments.addOne(id)
            case error: ParseError => errors.addOne(error)
          }
        if (errors.isEmpty) then ast.Function.Call.Expression(
          functionReference = reference,
          arguments = arguments.toSeq,
          metadata = metadataParser.parse.metadata(ctx),
        ) else new ParseError(
          metadata = metadataParser.parse.metadata(ctx),
          message = "Invalid function call expression",
          cause = errors.toSeq
        )
      }
      case error: ParseError => new ParseError(
        metadata = metadataParser.parse.metadata(ctx),
        message = "Invalid function call expression",
        cause = Seq(error)
      )
    }

  }

  override def visitReferenceExpression(ctx: LanguageParser.ReferenceExpressionContext): ast.base.expressions.Reference | ParseError = {
    super.visitReferenceExpression(ctx)
    Option(ctx.valueReference())
      .map(item => visitValueReference(item))
      .orElse(
        Option(ctx.functionReference()).map(item => visitFunctionReference(item))
      )
      .getOrElse(
        new ParseError(message = s"Unknown reference expression",
          metadata = metadataParser.parse.metadata(ctx)
        )
      )
  }

  override def visitFunctionReference(ctx: LanguageParser.FunctionReferenceContext): ast.Function.Reference | ParseError = {
    super.visitFunctionReference(ctx)
    val idOrError: ast.Function.Identifier | ParseError = visitFunctionIdentifier(ctx.functionIdentifier())
    val typeOrError: ast.Value.Type.Identifier | Null | ParseError = Option(ctx.valueTypeIdentifier())
      .map(item => visitValueTypeIdentifier(item))
      .orNull
    return idOrError match {
      case valueId: ast.Function.Identifier => typeOrError match {
        case typeId: ast.Value.Type.Identifier => ast.Function.Reference(
          functionId = valueId,
          valueTypeIdentifier = typeId,
          metadata = metadataParser.parse.metadata(ctx)
        )
        case typeError: ParseError => new ParseError(
          metadata = metadataParser.parse.metadata(ctx),
          message = "Invalid function reference",
          cause = Seq(typeError)
        )
        case null => null
      }
      case idError: ParseError => new ParseError(
        metadata = metadataParser.parse.metadata(ctx),
        message = "Invalid function reference",
        cause = Seq(idError)
      )
    }
  }

  override def visitValueReference(ctx: LanguageParser.ValueReferenceContext): ast.Value.Reference | ParseError = {
    super.visitValueReference(ctx)
    val idOrError: ast.Value.Identifier | ParseError = visitValueIdentifier(ctx.valueIdentifier())
    val typeOrError: ast.Value.Type.Identifier | Null | ParseError = Option(ctx.valueTypeIdentifier())
      .map(item => visitValueTypeIdentifier(item))
      .orNull
    return idOrError match {
      case valueId: ast.Value.Identifier => typeOrError match {
        case typeId: ast.Value.Type.Identifier => ast.Value.Reference(
          valueId = valueId,
          typeId = typeId,
          metadata = metadataParser.parse.metadata(ctx)
        )
        case typeError: ParseError => new ParseError(
          metadata = metadataParser.parse.metadata(ctx),
          message = "Invalid value reference",
          cause = Seq(typeError)
        )
        case null => null
      }
      case idError: ParseError => new ParseError(
        metadata = metadataParser.parse.metadata(ctx),
        message = "Invalid value reference",
        cause = Seq(idError)
      )
    }
  }


  override def visitExpression(ctx: LanguageParser.ExpressionContext): Expression | ParseError = {
    super.visitExpression(ctx);
    Option(ctx.functionCallExpression())
      .map(item => visitFunctionCallExpression(item))
      .orElse(
        Option(ctx.referenceExpression())
          .map(item => visitReferenceExpression(item))
      )
      .orElse(
        Option(ctx.literalExpression())
          .map(item => visitLiteralExpression(item))
      )
      .getOrElse(
        new ParseError(message = s"Unknown expression",
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