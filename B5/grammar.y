%{
	#include <stdio.h>
	int yylex();
	int yywrap();
	void yyerror(const char* s);
%}
%token ID AS DATATYPE LB RB SC
%start expression
%%
expression: function	{printf("Valid function");}
	;
function: AS DATATYPE ID LB RB SC ;
%%

void yyerror(const char *s){
	printf("Error\n");
}

int main(){
	do{
		yyparse();
	}while(1);
	return 1;
}

int yywrap(){
	return 1;
}
