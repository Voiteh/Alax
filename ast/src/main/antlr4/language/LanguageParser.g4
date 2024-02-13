parser grammar LanguageParser;
options { tokenVocab=LanguageLexer; }




definition: valueDefinition|functionDefinition|packageDefinition|moduleDefinition;
declaration: valueDeclaration|packageDeclaration|functionDeclaration|moduleDeclaration;


moduleDeclaration: MODULE moduleIdentifier SEMI_COLON ;
moduleDefinition: MODULE moduleIdentifier moduleBody;
moduleBody: OPEN_CURLY (valueDefinition*)  CLOSE_CURLY;
moduleIdentifier: LOWERCASE_IDENTIFIER (DOT LOWERCASE_IDENTIFIER)*;


packageDefinition: PACKAGE packageIdentifier packageBody;
packageDeclaration: PACKAGE packageIdentifier SEMI_COLON;
packageBody:OPEN_CURLY (valueDefinition|functionDefinition)*  CLOSE_CURLY;
packageIdentifier: LOWERCASE_IDENTIFIER;

functionDefinition: valueTypeReference? functionIdentifier OPEN_BRACKET functionParameters? CLOSE_BRACKET FAT_ARROW|NOT_FAT_ARROW functionalBody;
functionDeclaration: valueTypeReference? functionIdentifier OPEN_BRACKET functionParameters? CLOSE_BRACKET SEMI_COLON;
functionalBody: (expression SEMI_COLON)| OPEN_CURLY functionalBodyStatement* CLOSE_CURLY;
functionalBodyStatement:  valueDeclaration|valueDefinition|returnStatement|assignmentStatement;
functionIdentifier: LOWERCASE_IDENTIFIER;


functionParameters: functionParameter (COMMA functionParameter)*;
functionParameter: valueTypeReference LOWERCASE_IDENTIFIER (EQUALS literalExpression|referenceExpression)?;

valueDefinition: accessModifier? VALUE valueTypeReference valueIdentifier EQUALS expression SEMI_COLON ;
valueDeclaration: accessModifier? VALUE valueTypeReference valueIdentifier SEMI_COLON;
valueIdentifier: LOWERCASE_IDENTIFIER;




functionCallExpression: accessor? OPEN_BRACKET functionCallArguments?  CLOSE_BRACKET;
functionCallArguments:  positionalArguments| namedArguments ;
positionalArguments: expression (COMMA expression)*;
namedArguments: LOWERCASE_IDENTIFIER EQUALS expression (COMMA LOWERCASE_IDENTIFIER EQUALS expression)*;

assignmentStatement: accessor? functionOrValueReference EQUALS expression SEMI_COLON;
returnStatement: RETURN expression SEMI_COLON;

//Refernces
//TODO we need to find out how to handle all references: packages or modules may have same token as function or value references how to distinguish those 3 ?
valueTypeReference: (importIdentifier DOT)? UPPERCASE_IDENTIFIER ;
functionOrValueReference: (accessor DOT)* (importIdentifier DOT)* memberName=LOWERCASE_IDENTIFIER;

referenceExpression: valueTypeReference|functionOrValueReference;

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
importIdentifier: (LOWERCASE_IDENTIFIER|UPPERCASE_IDENTIFIER) (DOT LOWERCASE_IDENTIFIER|UPPERCASE_IDENTIFIER)*;


literalExpression: BOOLEAN_LITERAL|CHARACTER_LITERAL|INTEGER_LITERAL|FLOAT_LITERAL|STRING_LITERAL;

expression: literalExpression|functionCallExpression|referenceExpression;
