%token datatype integer float variable access semicolon
%%
start: sent start| sent;
sent: access datatype variable '=' integer semicolon {printf("Valid\n");} |
	access datatype variable '=' float semicolon {printf("Valid\n");} |
	datatype variable '=' float semicolon {printf("Valid\n");}	|
	datatype variable '=' integer semicolon {printf("Valid\n");} ;
%%

