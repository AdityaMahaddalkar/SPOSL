%{
	#include <stdio.h>
	#include "scanner.tab.h"
%}

specifier	public|private|protected
parameterDtype	int|float|char|String|boolean
returnType	void|{parameterDtype}
identifier	[a-zA-Z][a-zA-Z0-9]*
%%
{specifier}	{ return SPECIFIER;}
{parameterDtype}	{  return DTYPE;}
{returnType}	{ return RTYPE;}
{identifier}	{ return ID;}
\(	{return LB;}
\)	{return RB;}
,   {return COMMA;}
\n	{}
%%

int yywrap(void){
	return 1;
}
