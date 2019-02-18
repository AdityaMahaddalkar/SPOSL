bison -d scanner.y
flex scanner.l
gcc y.tab.c lex.yy.c -o scanner
