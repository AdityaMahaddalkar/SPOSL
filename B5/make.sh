bison -d grammar.y
lex lexer.l
gcc grammar.tab.c lex.yy.c
