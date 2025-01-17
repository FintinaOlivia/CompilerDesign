%{
#include <stdio.h>
#include <stdlib.h>
extern int yylineno;
extern char *yytext;

void yyerror(const char *msg);
int yylex();
int yylexi();
%}

%token PLUS MINUS MULTIPLY DIVIDE LT LE GT GE EQ NE ASSIGN MOD
%token COMMA DOT LPAREN RPAREN LBRACKET RBRACKET LACCOLADE RACCOLADE COLON SEMICOLON BLOCK
%token INT CHAR STRING IF ELSE WHILE FOR RETURN READ PRINT VOID AND OR NOT
%token IDENTIFIER INTEGER_CONST STRING_CONST CHAR_CONST

%precedence IF_NO_ELSE

%%
program:
    statement_list
;

statement_list:
    statement_list statement
    | statement
;

statement:
    assignment SEMICOLON
    | io_statement SEMICOLON
    | compound_statement
    | if_statement
    | while_statement
    | for_statement
    | function_declaration
    | return_statement SEMICOLON
;

assignment:
    IDENTIFIER ASSIGN expression
;

io_statement:
    PRINT LPAREN expression RPAREN
    | IDENTIFIER ASSIGN READ LPAREN RPAREN
;

compound_statement:
    LACCOLADE statement_list RACCOLADE
;

if_statement:
    IF LPAREN condition RPAREN COLON statement 
    | if_statement_with_else
;

if_statement_with_else:
    IF LPAREN condition RPAREN statement ELSE statement
;

while_statement:
    WHILE LPAREN condition RPAREN statement
;

for_statement:
    FOR LPAREN declaration COMMA constant COMMA condition COMMA assignment RPAREN statement
;

function_declaration:
    return_type IDENTIFIER LPAREN parameter_list RPAREN compound_statement
;

return_statement:
    RETURN expression
;

parameter_list:
    /* Empty */
    | type IDENTIFIER
    | parameter_list COMMA type IDENTIFIER
;

declaration:
    type IDENTIFIER
;

condition:
    expression relational_operator expression
    | NOT condition
;

expression:
    term
    | expression PLUS term
    | expression MINUS term
    | expression MOD term
;

term:
    factor
    | term MULTIPLY factor
    | term DIVIDE factor
;

factor:
    IDENTIFIER
    | constant
    | LPAREN expression RPAREN
;

constant:
    INTEGER_CONST
    | STRING_CONST
    | CHAR_CONST
;

relational_operator:
    LT
    | LE
    | GT
    | GE
    | EQ
    | NE
;

return_type:
    INT
    | CHAR
    | STRING
    | VOID
;

type:
    INT
    | CHAR
    | STRING
;

%%
int yylexi() {
    int token = yylex();
    printf("Lexing token: %d (yytext: '%s')\n", token, yytext);
    return token;
}

void yyerror(const char *msg) {
    // Check for non-printable characters and print them as their escaped form
    if (yytext && yytext[0] != '\0') {
        fprintf(stderr, "Debug: yytext = '%s', first char = '%c' (ASCII: %d)\n", yytext, yytext[0], yytext[0]);
        if (yytext[0] == '\n') {
            fprintf(stderr, "Error: %s, at line %d near the endline '\\n'\n", msg, yylineno);
        } else if (yytext[0] == ' ') {
            fprintf(stderr, "Error: %s, at line %d near the space ' '\\''\n", msg, yylineno);
        } else if (yytext[0] == '\t') {
            fprintf(stderr, "Error: %s, at line %d near the tab '\\t'\n", msg, yylineno);
        } else if (yytext[0] == '\r') {
            fprintf(stderr, "Error: %s, at line %d near the carriage return '\\r'\n", msg, yylineno);
        } else {
            fprintf(stderr, "Error: %s, at line %d near '-%s-'\n", msg, yylineno, yytext);
        }
    } else {
        fprintf(stderr, "Error: %s, at line %d near nothing %s\n", msg, yylineno, yytext);
    }
}

int main() {
    return yyparse();
}
