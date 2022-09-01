parser grammar LanguageParser;
options { tokenVocab=LanguageLexer; }

compilationUnit: declaration*;


valueDeclaratonWithInitialization: WS* type WS* DECLARATION_NAME WS* COLON literal WS* SEMI_COLON ;
valueDeclaration: WS* type WS* DECLARATION_NAME WS* SEMI_COLON;



declaration: valueDeclaration;

type: VALUE_TYPE_NAME;
literal: BOOLEAN_LITERAL|CHARACTER_LITERAL|INTEGER_LITERAL|FLOAT_LITERAL|STRING_LITERAL;



