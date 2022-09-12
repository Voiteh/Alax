parser grammar LanguageParser;
options { tokenVocab=LanguageLexer; }

valueDeclaratonWithInitialization: typedName ASSIGNMENT expression STATMENT_END ;

valueDeclaration: typedName STATMENT_END;

declaration: valueDeclaration|valueDeclaratonWithInitialization;

typedName: type LOWERCASE_NAME;

literal: BOOLEAN_LITERAL|CHARACTER_LITERAL|INTEGER_LITERAL|FLOAT_LITERAL|STRING_LITERAL;

type: VALUE_TYPE_NAME;


expression: literal;


