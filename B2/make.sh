lex javaTokenizer.l
cc lex.yy.c -o tokenize -ll
./tokenize
if 3>4
echo "Hi"
el
echo "Hello"
fi

