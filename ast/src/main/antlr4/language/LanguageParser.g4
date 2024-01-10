parser grammar LanguageParser;
options { tokenVocab=LanguageLexer; }




definition: valueDefinition|functionDefinition|packageDefinition|moduleDefinition;
declaration: valueDeclaration|packageDeclaration|functionDeclaration|moduleDeclaration;


moduleDeclaration: MODULE moduleName SEMI_COLON ;
moduleDefinition: MODULE moduleName moduleBody;
moduleBody: OPEN_CURLY (valueDefinition*)  CLOSE_CURLY;
moduleName: LOWERCASE_NAME (DOT LOWERCASE_NAME)*;


packageDefinition: PACKAGE LOWERCASE_NAME packageBody;
packageDeclaration: PACKAGE LOWERCASE_NAME SEMI_COLON;
packageBody:OPEN_CURLY (valueDefinition|functionDefinition)*  CLOSE_CURLY;

functionDefinition: valueTypeReference? LOWERCASE_NAME OPEN_BRACKET functionParameters? CLOSE_BRACKET FAT_ARROW|NOT_FAT_ARROW functionalBody;
functionDeclaration: valueTypeReference? LOWERCASE_NAME OPEN_BRACKET functionParameters? CLOSE_BRACKET SEMI_COLON;
functionalBody: (expression SEMI_COLON)| OPEN_CURLY functionalBodyStatement* CLOSE_CURLY;
functionalBodyStatement:  valueDeclaration|valueDefinition|returnStatement|assignmentStatement;



functionParameters: functionParameter (COMMA functionParameter)*;
functionParameter: valueTypeReference LOWERCASE_NAME (EQUALS literalExpression|referenceExpression)?;

valueDefinition: accessModifier? valueTypeReference LOWERCASE_NAME EQUALS expression SEMI_COLON ;
valueDeclaration: accessModifier? valueTypeReference LOWERCASE_NAME SEMI_COLON;





functionCallExpression: accessor? OPEN_BRACKET functionCallArguments?  CLOSE_BRACKET;
functionCallArguments:  positionalArguments| namedArguments ;
positionalArguments: expression (COMMA expression)*;
namedArguments: LOWERCASE_NAME EQUALS expression (COMMA LOWERCASE_NAME EQUALS expression)*;

assignmentStatement: accessor? functionOrValueReference EQUALS expression SEMI_COLON;
returnStatement: RETURN expression SEMI_COLON;

//Refernces
//TODO we need to find out how to handle all references: packages or modules may have same token as function or value references how to distinguish those 3 ?
valueTypeReference: (importedName DOT)? typeName=UPPERCASE_NAME;
functionOrValueReference: (accessor DOT)* (importedName DOT)* memberName=LOWERCASE_NAME;

referenceExpression: valueTypeReference|functionOrValueReference;

accessModifier: SHARED|PROTECTED;
accessor:THIS|SUPER|OUTER;

//Imports
nestedImportDeclaration: IMPORT nestableImport SEMI_COLON;

simpleImportDeclaration: IMPORT importedName SEMI_COLON;

aliasImportDeclaration: IMPORT importAlias  SEMI_COLON;


nestableImport: importedName DOT OPEN_SQUARE importedName|importAlias|nestableImport (COMMA (importedName|importAlias|nestableImport))* CLOSE_SQUARE;

importAlias: aliased=importedName ALIAS alias=importedName;

importedName: (UPPERCASE_NAME|LOWERCASE_NAME) (DOT (UPPERCASE_NAME|LOWERCASE_NAME))*;

literalExpression: BOOLEAN_LITERAL|CHARACTER_LITERAL|INTEGER_LITERAL|FLOAT_LITERAL|STRING_LITERAL;

expression: literalExpression|functionCallExpression|referenceExpression;


