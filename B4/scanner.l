%{
	#include <stdio.h>
	#include "scanner.tab.h"
%}
digit	[0-9]
int	{digit}+
float	{digit}+(.{digit}+)?((E|e){digit}+)?
char	'.'
string 	\".*\"
%%
=	{return ASSIGN;}
int	{return INTDECL;}
float	{return FLOATDECL;}
char	{return CHARDECL;}
String	{return STRINGDECL;}
{int}	{return INT;}
{float}	{return FLOAT;}
{char}	{return CHAR;}
{string}	{return STRING;}
,	{return COMMA;}
;	{return SEMICOLON;}
[a-zA-Z][a-zA-Z0-9]*	{return ID;}
\n	{return 0;}
.	{}
%%

int yywrap(void){
	return 1;
}
