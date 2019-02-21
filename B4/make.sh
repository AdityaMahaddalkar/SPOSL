bison -d scanner.y
flex scanner.l
gcc scanner.tab.c lex.yy.c -o scanner
