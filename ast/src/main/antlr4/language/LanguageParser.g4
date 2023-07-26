parser grammar LanguageParser;
options { tokenVocab=LanguageLexer; }




//Declarations

//TODO
moduleDeclaration: MODULE LOWERCASE_NAME (DOT LOWERCASE_NAME)* OPEN_CURLY  CLOSE_CURLY;


packageDeclaration: PACKAGE LOWERCASE_NAME (DOT LOWERCASE_NAME)* packageBody;
//FIXME this will change when types will come
packageBody:OPEN_CURLY (valueDeclaration|valueDeclarationWithInitialization|functionDeclaration)*  CLOSE_CURLY;

functionDeclaration: valueTypeReference? LOWERCASE_NAME OPEN_BRACKET functionParameters? CLOSE_BRACKET functionalBody|SEMI_COLON;
functionalBody: FAT_ARROW|NOT_FAT_ARROW (expression SEMI_COLON)| OPEN_CURLY functionalBodyStatement* CLOSE_CURLY;
functionalBodyStatement:  valueDeclaration|valueDeclarationWithInitialization|returnStatement|assignmentStatement;



functionParameters: functionParameter (COMMA functionParameter)*;
functionParameter: valueTypeReference LOWERCASE_NAME (COLON literalExpression|referenceExpression)?;

valueDeclarationWithInitialization: accessModifier? valueTypeReference LOWERCASE_NAME COLON expression SEMI_COLON ;
valueDeclaration: accessModifier? valueTypeReference LOWERCASE_NAME SEMI_COLON;


declaration: valueDeclaration|valueDeclarationWithInitialization|packageDeclaration|functionDeclaration;



functionCallExpression: accessor? OPEN_BRACKET functionCallArguments?  CLOSE_BRACKET;
functionCallArguments:  positionalArguments| namedArguments ;
positionalArguments: expression (COMMA expression)*;
namedArguments: LOWERCASE_NAME COLON expression (COMMA LOWERCASE_NAME COLON expression)*;

assignmentStatement: accessor? functionOrValueReference COLON expression SEMI_COLON;
returnStatement: RETURN expression SEMI_COLON;

//Refernces
//TODO we need to find out how to handle all references: packages or modules may have same token as function or value references how to distinguish those 3 ?
valueTypeReference: (importedName DOT)? typeName=UPPERCASE_NAME;
functionOrValueReference: (accessor DOT)* (importedName DOT)* memberName=LOWERCASE_NAME;

referenceExpression: valueTypeReference|functionOrValueReference;

accessModifier: SHARED|PROTECTED;
accessor:THIS|SUPER|OUTER;

//Imports
nestedImportStatement: IMPORT nestableImport SEMI_COLON;

simpleImportStatement: IMPORT importedName SEMI_COLON;

aliasImportStatement: IMPORT importAlias  SEMI_COLON;


nestableImport: importedName OPEN_SQUARE importedName|importAlias|nestableImport (COMMA (importedName|importAlias|nestableImport))* CLOSE_SQUARE;

importAlias: aliased=importedName ALIAS alias=importedName;

importedName: (UPPERCASE_NAME|LOWERCASE_NAME) (DOT (UPPERCASE_NAME|LOWERCASE_NAME))*;

literalExpression: BOOLEAN_LITERAL|CHARACTER_LITERAL|INTEGER_LITERAL|FLOAT_LITERAL|STRING_LITERAL;

expression: literalExpression|functionCallExpression|referenceExpression;


