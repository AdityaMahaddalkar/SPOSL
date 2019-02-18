%{
	#include <stdio.h>
	#include <stdlib.h>
%}
%token variable
%start expression
%%
expression: expression	{yyerror("This is error");}
	| variable
	;
%%
void yyerror (char const *s){
	fprintf (stderr, "Error Not a variable: %s\n", s);
}

