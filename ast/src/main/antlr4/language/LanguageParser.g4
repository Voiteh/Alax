parser grammar LanguageParser;
options { tokenVocab=LanguageLexer; }




definition: valueDefinition|functionDefinition|packageDefinition|moduleDefinition;
declaration: valueDeclaration|packageDeclaration|functionDeclaration|moduleDeclaration;


moduleDeclaration: MODULE moduleIdentifier SEMI_COLON ;
moduleDefinition: MODULE moduleIdentifier moduleBody;
moduleBody: OPEN_CURLY (valueDefinition)*  CLOSE_CURLY;
moduleIdentifier: lowercaseIdentifier (DOT lowercaseIdentifier)*;


packageDefinition: PACKAGE packageIdentifier packageBody;
packageDeclaration: PACKAGE packageIdentifier SEMI_COLON;
packageBody: OPEN_CURLY (valueDefinition|functionDefinition)*  CLOSE_CURLY;
packageIdentifier: lowercaseIdentifier;

functionalBodyStatement:  valueDeclaration|valueDefinition|returnStatement|valueAssignmentStatement;

functionDefinition: sideEffectFunctionDefinition|pureFunctionDefinition;
sideEffectFunctionDefinition: FUNCTION functionIdentifier OPEN_BRACKET functionParameters? CLOSE_BRACKET NOT_FAT_ARROW functionBody ;
pureFunctionDefinition: FUNCTION functionReturnType functionIdentifier OPEN_BRACKET functionParameters? CLOSE_BRACKET FAT_ARROW functionBody;
functionDeclaration: FUNCTION functionReturnType? functionIdentifier OPEN_BRACKET functionParameters? CLOSE_BRACKET SEMI_COLON;
functionReturnType: valueTypeIdentifier;

functionLambdaBody: valueAssignmentStatement|functionCallStatement SEMI_COLON;
functionBlockBody: OPEN_CURLY (valueDeclaration|valueDefinition|valueAssignmentStatement|functionCallStatement)* CLOSE_CURLY;
functionBody: functionBlockBody|functionLambdaBody;
functionIdentifier: lowercaseIdentifier;



functionParameters: functionParameter (COMMA functionParameter)*;
functionParameter: valueTypeIdentifier lowercaseIdentifier (EQUALS expressionChain)?;

valueDefinition: accessModifier? VALUE valueTypeIdentifier valueIdentifier EQUALS expression SEMI_COLON ;
valueDeclaration: accessModifier? VALUE valueTypeIdentifier valueIdentifier SEMI_COLON;
valueIdentifier: lowercaseIdentifier;



functionCallStatement: functionCallExpression SEMI_COLON;
functionCallExpression: functionIdentifier OPEN_BRACKET functionCallArguments?  CLOSE_BRACKET;
functionCallArguments:  positionalArguments| namedArguments ;
positionalArguments: expressionChain (COMMA expressionChain)*;
namedArguments: lowercaseIdentifier EQUALS expressionChain (COMMA lowercaseIdentifier EQUALS expression)*;


valueAssignmentStatement: valueReference EQUALS expressionChain SEMI_COLON;
returnStatement: RETURN expressionChain SEMI_COLON;

//Refernces
valueTypeIdentifier: (identifier (DOT identifier)* DOT)* uppercaseIdentifier  ;

functionReference: ((valueTypeIdentifier DOT)|(accessor DOT))? lowercaseIdentifier;
valueReference: ((valueTypeIdentifier DOT)|(accessor DOT))? lowercaseIdentifier;

referenceExpression: valueTypeIdentifier|valueReference|functionReference;

accessModifier: SHARED|PROTECTED;
accessor:THIS|SUPER|OUTER;

//Imports
nestedImportDeclaration: IMPORT imports SEMI_COLON;

simpleImportDeclaration: IMPORT importIdentifier SEMI_COLON;

aliasImportDeclaration: IMPORT importAlias  SEMI_COLON;


imports: importIdentifier nestedImports? ;
nestedImports: DOT OPEN_SQUARE (nestableImport (COMMA nestableImport)*)? CLOSE_SQUARE;
nestableImport: importIdentifier|imports|importAlias;
importAlias: aliased=importIdentifier ALIAS alias=importIdentifier;
importIdentifier:  identifier (DOT identifier)*;

lowercaseIdentifier: LOWERCASE_WORD (LOWERCASE_WORD)*;
uppercaseIdentifier: UPPERCASE_WORD (UPPERCASE_WORD)*;
identifier: (LOWERCASE_WORD|UPPERCASE_WORD)+;

literalExpression: BOOLEAN_LITERAL|CHARACTER_LITERAL|INTEGER_LITERAL|FLOAT_LITERAL|STRING_LITERAL;

expressionChain: expression (DOT expression)*;

expression: literalExpression|functionCallExpression|referenceExpression;
