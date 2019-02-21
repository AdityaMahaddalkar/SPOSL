%{
	#include <stdio.h>
	
	extern FILE *yyin;
	void yyerror(const char *s);
	int yylex();
	int line_no = 0;
	extern char *yytext;
%}
%token SPECIFIER RTYPE DTYPE LB RB ID COMMA
%start program
%%
program: program start | start; // Imp: https://stackoverflow.com/questions/22346554/reading-new-line-giving-syntax-error-in-lex-yacc

start: function {printf("Valid Function\n");}
	;

function: SPECIFIER RTYPE ID LB varlist RB
	| SPECIFIER DTYPE ID LB varlist RB
	;

varlist: varlist COMMA DTYPE ID | DTYPE ID;
%%

void yyerror(const char *s){printf("error '%s' %d\n", yytext, line_no++);} 
int main(){
    FILE *p=  fopen("myJava.java", "r");
    if(p != NULL){
        yyin = p;
    }
	do{
		yyparse();
		fflush(stdin);
	}while(1);
	return 0;
}	
