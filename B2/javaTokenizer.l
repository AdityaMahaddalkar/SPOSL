%{
	/* Program to generate Java tokens */
%}
digit	[0-9]
integer	{digit}*
floatingPoint	{digit}*\.{digit}*(E|e)?{digit}+
identifier	[_a-zA-Z]+[_a-zA-Z0-9]*
unaryOperator	--|\+\+
binaryOperator	\+|-|\*|\/|%|<<|>>|>>>
comparisionOperator	<|>|<=|>=|==|!=|instance\sof
bitwiseOperator	&|\||\^
logicalOperator	&&|\|\|
assignmentOperator	\+=|-=|\*=|\/=|%=|<<=|>>=|>>>=|&=|\|=|\^=|=
loopKeywords	for|while|do
conditionalKeywords	if|else\sif|else
exceptionKeywords	try|catch|finally
datatypes	int|float|char|boolean|void
nullKeyword	null
booleanKeywords	true|false
accessSpecifier	public|private|protected
classTokens	class|static|final|abstract|implements|extends|interface|new
importToken	^(import)
string	"[a-zA-Z0-9\?\*!@#\$&]*"
comment	(\/\/.*|\/\*(.|\n)*\*\/)
%%
{comment}	{printf("(Comment:%s)", yytext);}
{nullKeyword}	{printf("(Null:%s)", yytext);}
{importToken}	{printf("(Import token:%s)", yytext);}
{unaryOperator}	{printf("(Unary Operator:%s)", yytext);}
{binaryOperator}	{printf("(Binary Operator:%s)", yytext);}
{comparisionOperator}	{printf("(Comparision Operator:%s)", yytext);}
{bitwiseOperator}	{printf("(Bitwise Operator:%s)", yytext);}
{logicalOperator}	{printf("(Logical Operator:%s)", yytext);}
{assignmentOperator}	{printf("(Assignment Operator:%s)", yytext);}
{booleanKeywords}	{printf("(Boolean:%s)", yytext);}
{datatypes}	{printf("(Datatype:%s)", yytext);}
{loopKeywords}	{printf("(Loop Keyword:%s)", yytext);}
{conditionalKeywords}	{printf("(Conditional:%s)", yytext);}
{exceptionKeywords}	{printf("(Exception keyword:%s)", yytext);}
{accessSpecifier}	{printf("(Access Specifier:%s)", yytext);}
{classTokens}	{printf("(Class Token:%s)", yytext);}
{identifier}	{printf("(Identifier:%s)", yytext);}
{integer}	{printf("(Integer:%s)", yytext);}
{floatingPoint}	{printf("(Float:%s)", yytext);}
{string}	{printf("(String:%s)", yytext);}
%%

int yywrap(){
	return 1;
}

int main()
{
	extern FILE *yyin;
	yyin = fopen("myJava.java", "r");
	yylex();
	return 0;
}
