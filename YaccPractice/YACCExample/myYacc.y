%{
	#include <stdio.h>
	#include <stdlib.h>
%}
%token VARIABLE
%start expression
%%
expression: expression	{yyerror("This is error");}
	| VARIABLE
	;
%%
void yyerror (char const *s){
	fprintf (stderr, "Error Not a variable: %s\n", s);
}

