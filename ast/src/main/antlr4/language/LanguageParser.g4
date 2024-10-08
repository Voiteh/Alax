parser grammar LanguageParser;
options { tokenVocab=LanguageLexer; }

definition: valueDefinition|routineDefinition|packageDefinition|moduleDefinition;
declaration: valueDeclaration|packageDeclaration|functionDeclaration|moduleDeclaration;

moduleDeclaration: MODULE moduleIdentifier SEMI_COLON ;
moduleDefinition: MODULE moduleIdentifier moduleBody;
moduleBody: OPEN_CURLY (valueDefinition)* CLOSE_CURLY;
//TODO find another way for identifying modules, it is cumbersome with all that com.bleh.blah.bluh especially when not owning com.bleh domain
moduleIdentifier: lowercaseIdentifier (DOT lowercaseIdentifier)*;

packageDefinition: PACKAGE packageIdentifier packageBody;
packageDeclaration: PACKAGE packageIdentifier SEMI_COLON;
packageBody: OPEN_CURLY (valueDefinition|routineDefinition)* CLOSE_CURLY;
packageIdentifier: lowercaseIdentifier;
packageReference: lowercaseIdentifier (DOT lowercaseIdentifier)*;
routineDefinition: procedureDefinition|functionDefinition;
routineParameter: valueTypeReference lowercaseIdentifier (EQUALS chainExpression)?;
procedureDefinition: PROCEDURE evaluableIdentifier OPEN_BRACKET (routineParameter (COMMA routineParameter)*)? CLOSE_BRACKET NOT_FAT_ARROW functionBody ;
procedureDeclaration: PROCEDURE evaluableIdentifier OPEN_BRACKET (routineParameter (COMMA routineParameter)*)? CLOSE_BRACKET SEMI_COLON;

functionDefinition: FUNCTION functionReturnType evaluableIdentifier OPEN_BRACKET (routineParameter (COMMA routineParameter)*)? CLOSE_BRACKET FAT_ARROW functionBody;
functionDeclaration: FUNCTION functionReturnType evaluableIdentifier OPEN_BRACKET (routineParameter (COMMA routineParameter)*)? CLOSE_BRACKET SEMI_COLON;
functionReturnType: valueTypeReference;

functionLambdaBody: chainExpression|functionCallExpression|evaluableReference SEMI_COLON;
functionBlockBody: OPEN_CURLY (valueDeclaration|valueDefinition|valueAssignmentExpression|functionCallExpression|evaluableReference)* CLOSE_CURLY;
functionBody: functionBlockBody|functionLambdaBody;

valueDefinition: accessModifier? VALUE valueTypeReference evaluableIdentifier EQUALS expression SEMI_COLON ;
valueDeclaration: accessModifier? VALUE valueTypeReference evaluableIdentifier SEMI_COLON;
valueTypeReference: (packageReference DOT)? valueTypeIdentifier;
valueTypeIdentifier: uppercaseIdentifier;


functionCallExpression: evaluableReference OPEN_BRACKET (functionCallArgument (COMMA functionCallArgument)*)?  CLOSE_BRACKET;
functionCallPositionalArgument: chainExpression;
functionCallNamedArgument: lowercaseIdentifier EQUALS chainExpression;
functionCallArgument: functionCallPositionalArgument|functionCallNamedArgument;

valueAssignmentExpression: evaluableReference EQUALS chainExpression;

//Refernces
containerReference: packageReference;
evaluableReference: (containerReference DOT)? evaluableIdentifier;

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
