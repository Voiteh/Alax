parser grammar LanguageParser;
options { tokenVocab=LanguageLexer; }




definition: valueDefinition|functionDefinition|packageDefinition|moduleDefinition;
declaration: valueDeclaration|packageDeclaration|functionDeclaration|moduleDeclaration;


moduleDeclaration: MODULE moduleName SEMI_COLON ;
moduleDefinition: MODULE moduleName moduleBody;
moduleBody: OPEN_CURLY (valueDefinition*)  CLOSE_CURLY;
moduleName: LOWERCASE_IDENTIFIER (DOT LOWERCASE_IDENTIFIER)*;


packageDefinition: PACKAGE packageName packageBody;
packageDeclaration: PACKAGE packageName SEMI_COLON;
packageBody:OPEN_CURLY (valueDefinition|functionDefinition)*  CLOSE_CURLY;
packageName: LOWERCASE_IDENTIFIER;

functionDefinition: valueTypeReference? LOWERCASE_IDENTIFIER OPEN_BRACKET functionParameters? CLOSE_BRACKET FAT_ARROW|NOT_FAT_ARROW functionalBody;
functionDeclaration: valueTypeReference? LOWERCASE_IDENTIFIER OPEN_BRACKET functionParameters? CLOSE_BRACKET SEMI_COLON;
functionalBody: (expression SEMI_COLON)| OPEN_CURLY functionalBodyStatement* CLOSE_CURLY;
functionalBodyStatement:  valueDeclaration|valueDefinition|returnStatement|assignmentStatement;



functionParameters: functionParameter (COMMA functionParameter)*;
functionParameter: valueTypeReference LOWERCASE_IDENTIFIER (EQUALS literalExpression|referenceExpression)?;

valueDefinition: accessModifier? valueTypeReference valueName EQUALS expression SEMI_COLON ;
valueDeclaration: accessModifier? valueTypeReference valueName SEMI_COLON;
valueName: LOWERCASE_IDENTIFIER;




functionCallExpression: accessor? OPEN_BRACKET functionCallArguments?  CLOSE_BRACKET;
functionCallArguments:  positionalArguments| namedArguments ;
positionalArguments: expression (COMMA expression)*;
namedArguments: LOWERCASE_IDENTIFIER EQUALS expression (COMMA LOWERCASE_IDENTIFIER EQUALS expression)*;

assignmentStatement: accessor? functionOrValueReference EQUALS expression SEMI_COLON;
returnStatement: RETURN expression SEMI_COLON;

//Refernces
//TODO we need to find out how to handle all references: packages or modules may have same token as function or value references how to distinguish those 3 ?
valueTypeReference: (importedName DOT)? UPPERCASE_IDENTIFIER ;
functionOrValueReference: (accessor DOT)* (importedName DOT)* memberName=LOWERCASE_IDENTIFIER;

referenceExpression: valueTypeReference|functionOrValueReference;

accessModifier: SHARED|PROTECTED;
accessor:THIS|SUPER|OUTER;

//Imports
nestedImportDeclaration: IMPORT nestableImport SEMI_COLON;

simpleImportDeclaration: IMPORT importedName SEMI_COLON;

aliasImportDeclaration: IMPORT importAlias  SEMI_COLON;


nestableImport: importedName DOT OPEN_SQUARE importedName|importAlias|nestableImport (COMMA (importedName|importAlias|nestableImport))* CLOSE_SQUARE;

importAlias: aliased=importedName ALIAS alias=importedName;

importedName: LOWERCASE_IDENTIFIER (DOT LOWERCASE_IDENTIFIER)*;

literalExpression: BOOLEAN_LITERAL|CHARACTER_LITERAL|INTEGER_LITERAL|FLOAT_LITERAL|STRING_LITERAL;

expression: literalExpression|functionCallExpression|referenceExpression;


