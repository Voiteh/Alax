package org.alax.parser

import org.alax.ast.base.*
import org.alax.ast.base.Node.Metadata
import org.alax.ast.partial.Identifier
import org.alax.ast.{LanguageLexer, LanguageParser, LanguageParserBaseVisitor, Literals, Value, base}
import org.alax.ast
import org.antlr.v4.runtime.{ParserRuleContext, Token, TokenStream}
import org.antlr.v4.runtime.tree.{ParseTree, TerminalNode}

import javax.management.ValueExp
import scala.jdk.CollectionConverters._

class LanguageVisitor(
                       tokenParser: TokenRuleParser,
                       terminalNodeParser: TerminalNodeParser,
                     ) extends LanguageParserBaseVisitor[Node | ParseError] {


  private def parseIdentifier(terminalNode: TerminalNode): base.Partial.Identifier | ParseError = {
    return terminalNode.getSymbol.getType match {
      case LanguageParser.LOWERCASE_IDENTIFIER => Identifier.LowerCase(
        terminalNode.getText, tokenParser.parse.metadata(terminalNode.getSymbol)
      );
      case LanguageParser.UPPERCASE_IDENTIFIER => Identifier.UpperCase(
        terminalNode.getText, tokenParser.parse.metadata(terminalNode.getSymbol)
      );
      case _ => ParseError(
        metadata = tokenParser.parse.metadata(terminalNode.getSymbol),
        message = "Unknown name: " + terminalNode.getText
      );
    }
  }

  override def visitSpacefullLowercaseIdentifier(ctx: LanguageParser.SpacefullLowercaseIdentifierContext): ast.partial.Identifier.SpaceFull.LowerCase | ParseError = {
    super.visitSpacefullLowercaseIdentifier(ctx)
    val items: Seq[ast.partial.Identifier.LowerCase | ParseError] = ctx.LOWERCASE_IDENTIFIER().asScala
      .map(item => if ast.partial.Identifier.LowerCase.matches(item.getText)
      then ast.partial.Identifier.LowerCase(
          value = item.getText,
          metadata = tokenParser.parse.metadata(item.getSymbol)
        ) else new ParseError(
        metadata = tokenParser.parse.metadata(item.getSymbol),
        message = s"Invalid lowercase identifier: ${item.getSymbol}",
      )
      ).toSeq;
    val identifiersOrError = items.foldLeft(Seq[ast.partial.Identifier.LowerCase]())((acc: Seq[ast.partial.Identifier.LowerCase] | ParseError, item: ast.partial.Identifier.LowerCase | ParseError) =>
      acc match {
        case sequence: Seq[ast.partial.Identifier.LowerCase] => item match {
          case lowercase: ast.partial.Identifier.LowerCase => sequence.appended(lowercase)
          case error: ParseError => error
        }
        case error: ParseError => error
      }
    )
    return identifiersOrError match {
      case seq: Seq[ast.partial.Identifier.LowerCase] => ast.partial.Identifier.SpaceFull.LowerCase(
        items = seq,
        metadata = tokenParser.parse.ruleContext(ctx)
      )
      case error: ParseError => error
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


  //  override def visitImportedName(ctx: LanguageParser.ImportedNameContext): ast.Imports.ImportedName | ParseError = {
  //    super.visitImportedName(ctx);
  //    val qualifications: Seq[Identifier.LowerCase | Identifier.UpperCase] = ctx.children.asScala
  //      .filter(item => item.isInstanceOf[TerminalNode])
  //      .map(item => item.asInstanceOf[TerminalNode])
  //      .filter(item => item.getSymbol.getType == LanguageParser.UPPERCASE_IDENTIFIER || item.getSymbol.getType == LanguageParser.LOWERCASE_IDENTIFIER)
  //      .map(item => item.getSymbol.getType match {
  //        case LanguageParser.UPPERCASE_IDENTIFIER => Identifier.UpperCase(item.getText, tokenParser.parse.ruleContext(ctx));
  //        case LanguageParser.LOWERCASE_IDENTIFIER => Identifier.LowerCase(item.getText, tokenParser.parse.ruleContext(ctx));
  //        case _ => return ParseError(tokenParser.parse.metadata(item.getSymbol), f"Unknown token for imported name: ${item.getText}")
  //      }).toSeq;
  //    return if qualifications.size == 1 then qualifications.last else Identifier.Qualified(qualifications, tokenParser.parse.ruleContext(ctx));
  //  }

  override def visitValueTypeReference(ctx: LanguageParser.ValueTypeReferenceContext): ast.Value.Type.Reference | ParseError = {
    super.visitValueTypeReference(ctx)
    val typeName = ??? //Identifier.UpperCase(value = ctx.UPPERCASE_IDENTIFIER().getText, metadata = tokenParser.parse.ruleContext(ctx))
    val fullName: Identifier.Qualified | Identifier.UpperCase | ParseError = Option(???) //Option(ctx.importedName())
      .map(context => ???) //visitImportedName(context))
      .map[ParseError | Identifier.Qualified | Identifier.UpperCase] {
        case error: ParseError => error;
        case imported: Identifier.UpperCase => Identifier.Qualified(qualifications = Seq(imported, typeName), tokenParser.parse.ruleContext(ctx))
        case imported: Identifier.LowerCase => Identifier.Qualified(qualifications = Seq(imported, typeName), tokenParser.parse.ruleContext(ctx))
        case imported: Identifier.Qualified => Identifier.Qualified(qualifications = imported.asInstanceOf[Identifier.Qualified].qualifications :+ typeName, tokenParser.parse.ruleContext(ctx))
      }
      .getOrElse[ParseError | Identifier.Qualified | Identifier.UpperCase](typeName);
    return fullName match {
      case id@(_: Identifier.UpperCase | _: Identifier.Qualified) => ast.Value.Type.Reference(
        id = id, metadata = tokenParser.parse.ruleContext(ctx)
      )
      case default: ParseError => default
    }

  }

  override def visitValueName(ctx: LanguageParser.ValueNameContext): ast.Value.Name | ParseError = {
    super.visitValueName(ctx)
    val identifier = ctx.LOWERCASE_IDENTIFIER().getText
    if (identifier.matches("^[a-z][a-zA-Z0-9\\s]*[a-zA-Z0-9]$"))
    then ast.Value.Name(value = identifier, metadata = tokenParser.parse.ruleContext(ctx))
    else ParseError(
      metadata = tokenParser.parse.ruleContext(ctx),
      message = "Invalid package name, expecting lowercase letters"
    );
  }

  override def visitValueDeclaration(ctx: LanguageParser.ValueDeclarationContext): ast.Value.Declaration | ParseError = {
    super.visitValueDeclaration(ctx);
    val typeReference: Partial.Type.Reference | ParseError = visitValueTypeReference(ctx.valueTypeReference());
    val name: ast.Value.Name | ParseError = visitValueName(ctx.valueName());
    return typeReference match {
      case valueType: ast.Value.Type.Reference =>
        name match {
          case shadowName: ast.Value.Name =>
            ast.Value.Declaration(
              name = shadowName,
              typeReference = valueType,
              metadata = tokenParser.parse.ruleContext(ctx)
            )
          case error: ParseError => new ParseError(cause = error, message = "Invalid value declaration ", metadata = tokenParser.parse.ruleContext(ctx))
        }
      case error: ParseError => new ParseError(cause = error, message = "Invalid value declaration", metadata = tokenParser.parse.ruleContext(ctx))
      case other => ParseError(
        message = f"Not Implemented, parsing type: ${other}!",
        metadata = tokenParser.parse.ruleContext(ctx)
      )
    }
  }


  override def visitModuleDeclaration(ctx: LanguageParser.ModuleDeclarationContext): ast.Module.Declaration | ParseError = {
    super.visitModuleDeclaration(ctx)
    val moduleName: Identifier.Qualified.LowerCase | ParseError = visitModuleName(ctx.moduleName())
    return moduleName match {
      case name: Identifier.Qualified.LowerCase => ast.Module.Declaration(
        name = name,
        metadata = tokenParser.parse.ruleContext(ctx)
      )
      case error: ParseError => ParseError(
        message = "Invalid module declaration",
        metadata = tokenParser.parse.ruleContext(ctx),
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
      metadata = tokenParser.parse.ruleContext(ctx)
    )
  }

  override def visitModuleDefinition(ctx: LanguageParser.ModuleDefinitionContext): ast.Module.Definition | ParseError = {
    super.visitModuleDefinition(ctx)
    val moduleName: Identifier.Qualified.LowerCase | ParseError = visitModuleName(ctx.moduleName())
    val moduleBody: ast.Module.Body | ParseError = visitModuleBody(ctx.moduleBody())
    return moduleName match {
      case name: Identifier.Qualified.LowerCase => moduleBody match {
        case body: ast.Module.Body => ast.Module.Definition(
          name = name,
          body = body,
          metadata = tokenParser.parse.ruleContext(ctx)
        )
        case error: ParseError => error
      }
      case error: ParseError => ParseError(
        message = "Invalid module declaration",
        metadata = tokenParser.parse.ruleContext(ctx),
        cause = error
      )

    }
  }

  override def visitModuleName(ctx: LanguageParser.ModuleNameContext): ast.Module.Name | ParseError = {
    super.visitModuleName(ctx)
    val identifierOrError: Identifier.Qualified.LowerCase | ParseError =
      terminalNodeParser.parse.identifier.qualified.lowercase(???) //(ctx.QUALIFIED_LOWERCASE_NAME())
    return identifierOrError match {
      case identifier: Identifier.Qualified.LowerCase => identifier;
      case error: ParseError => new ParseError(
        metadata = tokenParser.parse.ruleContext(ctx),
        message = "Invalid module name", cause = error
      )
    }
  }

  override def visitPackageName(ctx: LanguageParser.PackageNameContext): ast.Package.Name | ParseError = {
    super.visitPackageName(ctx);
    val identifierOrError: Identifier.LowerCase | ParseError =
      terminalNodeParser.parse.identifier.lowercase(???) //ctx.LOWERCASE_NAME())
    return identifierOrError match {
      case identifier: Identifier.LowerCase => identifier;
      case error: ParseError => new ParseError(
        metadata = tokenParser.parse.ruleContext(ctx),
        message = "Invalid package name", cause = error
      )
    }
  }


  override def visitPackageDeclaration(ctx: LanguageParser.PackageDeclarationContext): ast.Package.Declaration | ParseError = {
    super.visitPackageDeclaration(ctx)
    val name: ast.Package.Name | ParseError = visitPackageName(ctx.packageName())
    name match {
      case shadowName: ast.Package.Name => ast.Package.Declaration(
        name = shadowName,
        metadata = tokenParser.parse.ruleContext(ctx)
      )
      case error: ParseError => new ParseError(cause = error, message = "Invalid package declaration ", metadata = tokenParser.parse.ruleContext(ctx))
    }

  }

  override def visitPackageDefinition(ctx: LanguageParser.PackageDefinitionContext): ast.Package.Definition | ParseError = {
    super.visitPackageDefinition(ctx)
    val name: ast.Package.Name | ParseError = visitPackageName(ctx.packageName())
    val body: ast.Package.Body | ParseError = visitPackageBody(ctx.packageBody());
    return name match {
      case shadowName: ast.Package.Name =>
        body match {
          case shadowBody: ast.Package.Body => ast.Package.Definition(
            name = shadowName,
            body = shadowBody,
            metadata = tokenParser.parse.ruleContext(ctx)
          )
          case parseError: ParseError => new ParseError(cause = parseError, message = "Invalid body definition", metadata = tokenParser.parse.ruleContext(ctx))
        }

      case error: ParseError => ParseError(cause = error, message = "Invalid package definition ", metadata = tokenParser.parse.ruleContext(ctx))
    }
  }


  override def visitPackageBody(ctx: LanguageParser.PackageBodyContext): ast.Package.Body | ParseError = {
    super.visitPackageBody(ctx)
    val valueDefinitions: Seq[ast.Value.Definition | ParseError] = ctx.valueDefinition().asScala
      .map(item => visitValueDefinition(item)).toSeq

    return ast.Package.Body(
      elements = valueDefinitions,
      metadata = tokenParser.parse.ruleContext(ctx)
    )
  }

  override def visitValueDefinition(ctx: LanguageParser.ValueDefinitionContext): ast.Value.Definition | ParseError = {
    super.visitValueDefinition(ctx);

    val typeReference: Partial.Type.Reference | ParseError = visitValueTypeReference(ctx.valueTypeReference());
    val name: ast.Value.Name | ParseError = visitValueName(ctx.valueName());
    val initialization: Expression | ParseError = visitExpression(ctx.expression());
    return typeReference match {
      case valueType: ast.Value.Type.Reference =>
        name match {
          case shadowName: ast.Value.Name =>
            initialization match {
              case expression: Expression => ast.Value.Definition(
                name = shadowName,
                typeReference = valueType,
                initialization = expression,
                metadata = tokenParser.parse.ruleContext(ctx)
              );
              case error: ParseError => new ParseError(
                cause = error,
                message = "Invalid value initialization expression ",
                metadata = tokenParser.parse.ruleContext(ctx)
              )

            }
          case error: ParseError => new ParseError(
            cause = error,
            message = "Invalid value name ",
            metadata = tokenParser.parse.ruleContext(ctx))
        }
      case error: ParseError => new ParseError(
        cause = error,
        message = "Invalid value type",
        metadata = tokenParser.parse.ruleContext(ctx)
      )
      case other => ParseError(
        message = f"Not Implemented, parsing type: ${other}!",
        metadata = tokenParser.parse.ruleContext(ctx)
      )


    }
  }


  override def visitExpression(ctx: LanguageParser.ExpressionContext): Expression | ParseError = {
    super.visitExpression(ctx);
    return Option(ctx.literalExpression())
      .map(expression => visitLiteralExpression(expression))
      .getOrElse(
        ParseError(message = s"Unknown expression: ${ctx.getText}",
          metadata = tokenParser.parse.ruleContext(ctx)
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
        metadata = tokenParser.parse.ruleContext(ctx)
      ));
    return result match {
      case terminalNode: TerminalNode => {
        val text = terminalNode.getText;
        terminalNode.getSymbol.getType match {
          case LanguageParser.BOOLEAN_LITERAL => Literals.Boolean(text.toBoolean, metadata = tokenParser.parse.ruleContext(ctx));
          case LanguageParser.INTEGER_LITERAL => Literals.Integer(text.toInt, metadata = tokenParser.parse.ruleContext(ctx));
          case LanguageParser.FLOAT_LITERAL => Literals.Float(text.toDouble, metadata = tokenParser.parse.ruleContext(ctx));
          case LanguageParser.CHARACTER_LITERAL => Literals.Character(text(1), metadata = tokenParser.parse.ruleContext(ctx));
          case LanguageParser.STRING_LITERAL => Literals.String(text.substring(1, text.length - 1), metadata = tokenParser.parse.ruleContext(ctx));
          case _ => ParseError(
            message = "Unknown literal: " + text,
            metadata = tokenParser.parse.ruleContext(ctx)
          );
        }
      }
      case error: ParseError => error
    }
  }


};

object LanguageVisitor {

}