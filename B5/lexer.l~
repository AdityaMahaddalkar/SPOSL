%{
	#include <stdio.h>
	#include "grammar.tab.h"
%}
datatype	(int|float|char|String)
accessSpecifier	(public|protected|private)
left_bracket	\(
right_bracket	\)

id	[a-zA-Z][a-zA-Z0-9]*
%%
{datatype}	{return DATATYPE;}
{accessSpecifier}	{return AS;}
{left_bracket}	{return LB;}
{right_bracket}	{return RB;}
{id}    {return ID;}
;	{return SC;}
.   {}
%%

