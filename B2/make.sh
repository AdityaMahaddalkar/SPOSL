lex javaTokenizer.l
cc lex.yy.c -o tokenize -ll
./tokenize
