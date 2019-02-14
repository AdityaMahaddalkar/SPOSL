%{
	#include "y.tab.h"
	extern int yylval;
%}
digit	[0-9]
letter	[a-zA-Z]
access	public|private|protected
datatype int|float|double|void|long|char|boolean
integer	{digit}+
float	{digit}+\.?{digit}+(E|e)?{digit}+
variable	[_{letter}]+[_{digit}{letter}]*
semicolon   ;
%%
{datatype}	{return datatype; }
{integer}	{return integer; }
{float}	{return float; }
{access}	{return access; }
{variable}	{return variable; }
{semicolon}	{return semicolon; }
%%
int yywrap(){
	return 1;
}
