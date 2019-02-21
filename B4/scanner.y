%{
	#include <stdio.h>
	extern FILE *yyin;
	void yyerror(const char *s);
	int yylex();
	extern char *yytext;
	int line_no = 0;
%}
%token INT STRING FLOAT CHAR ASSIGN STRINGDECL INTDECL FLOATDECL CHARDECL COMMA SEMICOLON ID SPACE
%start start
%%
start: STRINGDECL strvarlist SEMICOLON {printf("Valid\n");}
	| INTDECL intvarlist SEMICOLON {printf("Valid\n");}
	| FLOATDECL floatvarlist SEMICOLON {printf("Valid\n");}
	| CHARDECL charvarlist SEMICOLON {printf("Valid\n");}
	;
strvarlist: strvarlist COMMA ID ASSIGN STRING | strvarlist COMMA ID | ID | ID ASSIGN STRING;
intvarlist: intvarlist COMMA ID ASSIGN INT | intvarlist COMMA ID | ID | ID ASSIGN INT;
floatvarlist: floatvarlist COMMA ID ASSIGN FLOAT | floatvarlist COMMA ID ASSIGN INT | floatvarlist COMMA ID | ID | ID ASSIGN FLOAT | ID ASSIGN INT;
charvarlist: charvarlist COMMA ID ASSIGN CHAR | charvarlist COMMA ID | ID | ID ASSIGN CHAR;
%%

void yyerror(const char *s){printf("error %s : %d\n", yytext, line_no++);} 
int main(){
	do{
		yyparse();
	}while(1);
	return 0;
}	
