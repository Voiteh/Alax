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

functionDefinition: sideEffectFunctionDefinition|pureFunctionDefinition;
sideEffectFunctionDefinition: FUNCTION evaluableIdentifier OPEN_BRACKET (functionParameter (COMMA functionParameter)*)? CLOSE_BRACKET NOT_FAT_ARROW functionBody ;
pureFunctionDefinition: FUNCTION functionReturnType evaluableIdentifier OPEN_BRACKET (functionParameter (COMMA functionParameter)*)? CLOSE_BRACKET FAT_ARROW functionBody;
functionDeclaration: FUNCTION functionReturnType? evaluableIdentifier OPEN_BRACKET (functionParameter (COMMA functionParameter)*)? CLOSE_BRACKET SEMI_COLON;
functionParameter: valueTypeIdentifier lowercaseIdentifier (EQUALS chainExpression)?;
functionReturnType: valueTypeIdentifier;

functionLambdaBody: chainExpression|valueAssignmentExpression|functionCallExpression|evaluableReference SEMI_COLON;
functionBlockBody: OPEN_CURLY (valueDeclaration|valueDefinition|valueAssignmentExpression|functionCallExpression|evaluableReference)* CLOSE_CURLY;
functionBody: functionBlockBody|functionLambdaBody;

valueDefinition: accessModifier? VALUE valueTypeIdentifier evaluableIdentifier EQUALS expression SEMI_COLON ;
valueDeclaration: accessModifier? VALUE valueTypeIdentifier evaluableIdentifier SEMI_COLON;
valueTypeIdentifier: (identifier (DOT identifier)* DOT)* uppercaseIdentifier  ;


functionCallExpression: evaluableReference OPEN_BRACKET (functionCallArgument (COMMA functionCallArgument)*)?  CLOSE_BRACKET;
functionCallPositionalArgument: chainExpression;
functionCallNamedArgument: lowercaseIdentifier EQUALS chainExpression;
functionCallArgument: functionCallPositionalArgument|functionCallNamedArgument;

valueAssignmentExpression: evaluableReference EQUALS chainExpression;



//Refernces
evaluableReference: (valueTypeIdentifier DOT)? evaluableIdentifier;

evaluableIdentifier:lowercaseIdentifier;

accessModifier: SHARED|PROTECTED;

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

chainExpression: expression (DOT chainExpression)?;

expression: literalExpression|functionCallExpression|evaluableReference;
