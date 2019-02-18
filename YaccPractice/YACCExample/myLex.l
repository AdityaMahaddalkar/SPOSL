%{
	#include<stdio.h>
	#include "y.tab.h"
%}
digit	[0-9]
letter	[a-zA-Z]
%%
^{letter}({letter}|{digit})*$	{return variable;}
%%
int main(){
	extern FILE *yyin;
	yyin = fopen("myFile.txt", "r");
	yylex();
}
