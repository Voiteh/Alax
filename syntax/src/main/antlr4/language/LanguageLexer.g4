lexer grammar LanguageLexer;



fragment TRUE: 'true';
fragment FALSE: 'false';
fragment QUOTE :'"';
fragment APOSTROPHE: '\'';
fragment DOT: '.';
fragment SEMI_COLON: ';';
fragment COLON: ':';
fragment COMMA: ',';
fragment OPEN_CURLY: '{';
fragment CLOSE_CURLY: '}';
fragment OPEN_SQUARE: '[';
fragment CLOSE_SQUARE: ']';
fragment OPEN_TRIANGLE: '<';
fragment CLOSE_TRIANGLE: '>';
fragment OPEN_BRACKET: '(';
fragment CLOSE_BRACKET: ')';
fragment MINUS: '-';
fragment PLUS: '+';
fragment AND: '&';
fragment PIPE: '|';
fragment UNDERSCORE: '_';
//TODO include UTF-? literals
fragment CHARACTER: LOWERCASE_LETTER|UPPERCASE_LETTER|DIGIT|UNDERSCORE;
fragment DIGITS : DIGIT+;
fragment DIGIT: [0-9];
fragment UPPERCASE_LETTER:[A-Z];
fragment LOWERCASE_LETTER:[a-z];




THIS :'this';
SUPER:'super';
OUTER:'outer';


BOOLEAN_LITERAL : TRUE|FALSE;
STRING_LITERAL: QUOTE CHARACTER* QUOTE;
CHARACTER_LITERAL: APOSTROPHE CHARACTER APOSTROPHE;
FLOAT_LITERAL: MINUS? DIGITS DOT DIGITS;
INTEGER_LITERAL: MINUS? DIGITS;

UPPERCASE_NAME: UPPERCASE_LETTER (LOWERCASE_LETTER|UPPERCASE_LETTER|DIGIT|UNDERSCORE)*;
LOWERCASE_NAME: LOWERCASE_LETTER (LOWERCASE_LETTER|UPPERCASE_LETTER|DIGIT|UNDERSCORE)*;

FULLY_QUALIFIED_TYPE_NAME: PACKAGE_NAME DOT UPPERCASE_NAME;
PACKAGE_NAME: LOWERCASE_NAME (DOT LOWERCASE_NAME)*;

ASSIGNMENT: COLON;
STATEMENT_END: SEMI_COLON;

WS : [ \t\r\n]+ -> skip ;
