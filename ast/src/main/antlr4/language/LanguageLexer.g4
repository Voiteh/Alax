lexer grammar LanguageLexer;

SHARED:'shared';
PROTECTED:'protected';
VALUE: 'value' ;
IMPORT:'import';
ALIAS: 'alias';
AS: 'as'  ;
THIS :'this'  ;
SUPER:'super' ;
OUTER:'outer';
RETURN: 'return' ;
PACKAGE: 'package' ;
MODULE: 'module' ;
FUNCTION: 'function';
PROCEDURE: 'procedure';
fragment TRUE: 'true';
fragment FALSE: 'false';


NOT_FAT_ARROW: '=!>';
FAT_ARROW: '=>';
QUOTE :'"';
APOSTROPHE: '\'';
DOT:'.';
SEMI_COLON: ';';
COLON: ':';
COMMA: ',';
OPEN_CURLY: '{';
CLOSE_CURLY: '}';
OPEN_SQUARE: '[';
CLOSE_SQUARE: ']';
OPEN_TRIANGLE: '<';
CLOSE_TRIANGLE: '>';
OPEN_BRACKET: '(';
CLOSE_BRACKET: ')';
MINUS: '-';
PLUS: '+';
AND: '&';
PIPE: '|';
UNDERSCORE: '_';
EQUALS : '=';

//TODO include UTF-? literals
fragment CHARACTER: LOWERCASE_LETTER|UPPERCASE_LETTER|DIGIT|UNDERSCORE;
fragment DIGITS : DIGIT+;
fragment DIGIT: [0-9];
fragment UPPERCASE_LETTER:[A-Z];
fragment LOWERCASE_LETTER:[a-z];
fragment SYMBOL : LOWERCASE_LETTER|UPPERCASE_LETTER|DIGIT|UNDERSCORE;


BOOLEAN_LITERAL : TRUE|FALSE;
STRING_LITERAL: QUOTE CHARACTER* QUOTE;
CHARACTER_LITERAL: APOSTROPHE CHARACTER APOSTROPHE;
FLOAT_LITERAL: MINUS? DIGITS DOT DIGITS;
INTEGER_LITERAL: MINUS? DIGITS;

UPPERCASE_WORD: UPPERCASE_LETTER SYMBOL*;
LOWERCASE_WORD: LOWERCASE_LETTER SYMBOL*;


SKIP_WS : [ \t\r\n]+ -> skip ;






