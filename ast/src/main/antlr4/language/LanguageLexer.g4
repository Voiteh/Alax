lexer grammar LanguageLexer;


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

VALUE: 'value';
IMPORT:'import';
ALIAS: 'alias';
AS: 'as';
THIS :'this';
SUPER:'super';
OUTER:'outer';
SHARED:'shared';
PROTECTED:'protected';
RETURN: 'return';
PACKAGE: 'package';
MODULE: 'module';
BOOLEAN_LITERAL : TRUE|FALSE;
STRING_LITERAL: QUOTE CHARACTER* QUOTE;
CHARACTER_LITERAL: APOSTROPHE CHARACTER APOSTROPHE;
FLOAT_LITERAL: MINUS? DIGITS DOT DIGITS;
INTEGER_LITERAL: MINUS? DIGITS;

SYMBOL : LOWERCASE_LETTER|UPPERCASE_LETTER|DIGIT|UNDERSCORE;
WS : [ \t\r\n]+ -> skip ;


fragment UPPERCASE_IDENTIFIER_PART: UPPERCASE_LETTER SYMBOL* ;
fragment LOWERCASE_IDENTIFIER_PART: LOWERCASE_LETTER SYMBOL* ;
UPPERCASE_IDENTIFIER: UPPERCASE_IDENTIFIER_PART (WS UPPERCASE_IDENTIFIER_PART)* ;
LOWERCASE_IDENTIFIER: LOWERCASE_IDENTIFIER_PART (WS LOWERCASE_IDENTIFIER_PART)* ;



