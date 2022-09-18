parser grammar LanguageParser;
options { tokenVocab=LanguageLexer; }

valueDeclaratonWithInitialization: type LOWERCASE_NAME ASSIGNMENT expression STATEMENT_END ;

valueDeclaration: type LOWERCASE_NAME STATEMENT_END;

declaration: valueDeclaration|valueDeclaratonWithInitialization;

literal: BOOLEAN_LITERAL|CHARACTER_LITERAL|INTEGER_LITERAL|FLOAT_LITERAL|STRING_LITERAL;

type: FULLY_QUALIFIED_TYPE_NAME;


expression: literal;


