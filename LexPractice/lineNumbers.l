%{
	unsigned lineNumber=0;
%}
%%
.*\n	{lineNumber++; printf("%d. %s", lineNumber, yytext);}
%%
main(){
	extern FILE *yyin;
	yyin = fopen("myFile", "r");
	yylex();
	return 0;
}
