yacc -d myYacc.y
lex myLex.l
gcc -o obj lex.yy.c y.tab.c -ll
./obj
