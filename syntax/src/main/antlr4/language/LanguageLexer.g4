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



DECLARATION_NAME: LOWERCASE_LETTER  (LOWERCASE_LETTER|UPPERCASE_LETTER|DIGIT|UNDERSCORE)*;
VALUE_TYPE_NAME: UPPERCASE_LETTER  (LOWERCASE_LETTER|UPPERCASE_LETTER|DIGIT|UNDERSCORE)*;




WS : [ \t\r\n]+ -> skip ;
