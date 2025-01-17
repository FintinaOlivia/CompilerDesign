/* A Bison parser, made by GNU Bison 3.8.2.  */

/* Bison interface for Yacc-like parsers in C

   Copyright (C) 1984, 1989-1990, 2000-2015, 2018-2021 Free Software Foundation,
   Inc.

   This program is free software: you can redistribute it and/or modify
   it under the terms of the GNU General Public License as published by
   the Free Software Foundation, either version 3 of the License, or
   (at your option) any later version.

   This program is distributed in the hope that it will be useful,
   but WITHOUT ANY WARRANTY; without even the implied warranty of
   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
   GNU General Public License for more details.

   You should have received a copy of the GNU General Public License
   along with this program.  If not, see <https://www.gnu.org/licenses/>.  */

/* As a special exception, you may create a larger work that contains
   part or all of the Bison parser skeleton and distribute that work
   under terms of your choice, so long as that work isn't itself a
   parser generator using the skeleton or a modified version thereof
   as a parser skeleton.  Alternatively, if you modify or redistribute
   the parser skeleton itself, you may (at your option) remove this
   special exception, which will cause the skeleton and the resulting
   Bison output files to be licensed under the GNU General Public
   License without this special exception.

   This special exception was added by the Free Software Foundation in
   version 2.2 of Bison.  */

/* DO NOT RELY ON FEATURES THAT ARE NOT DOCUMENTED in the manual,
   especially those whose name start with YY_ or yy_.  They are
   private implementation details that can be changed or removed.  */

#ifndef YY_YY_LANGUAGESPECIFIC_POSTCOMPILATION_LANG_TAB_H_INCLUDED
# define YY_YY_LANGUAGESPECIFIC_POSTCOMPILATION_LANG_TAB_H_INCLUDED
/* Debug traces.  */
#ifndef YYDEBUG
# define YYDEBUG 0
#endif
#if YYDEBUG
extern int yydebug;
#endif

/* Token kinds.  */
#ifndef YYTOKENTYPE
# define YYTOKENTYPE
  enum yytokentype
  {
    YYEMPTY = -2,
    YYEOF = 0,                     /* "end of file"  */
    YYerror = 256,                 /* error  */
    YYUNDEF = 257,                 /* "invalid token"  */
    PLUS = 258,                    /* PLUS  */
    MINUS = 259,                   /* MINUS  */
    MULTIPLY = 260,                /* MULTIPLY  */
    DIVIDE = 261,                  /* DIVIDE  */
    LT = 262,                      /* LT  */
    LE = 263,                      /* LE  */
    GT = 264,                      /* GT  */
    GE = 265,                      /* GE  */
    EQ = 266,                      /* EQ  */
    NE = 267,                      /* NE  */
    ASSIGN = 268,                  /* ASSIGN  */
    MOD = 269,                     /* MOD  */
    COMMA = 270,                   /* COMMA  */
    DOT = 271,                     /* DOT  */
    LPAREN = 272,                  /* LPAREN  */
    RPAREN = 273,                  /* RPAREN  */
    LBRACKET = 274,                /* LBRACKET  */
    RBRACKET = 275,                /* RBRACKET  */
    LACCOLADE = 276,               /* LACCOLADE  */
    RACCOLADE = 277,               /* RACCOLADE  */
    COLON = 278,                   /* COLON  */
    SEMICOLON = 279,               /* SEMICOLON  */
    BLOCK = 280,                   /* BLOCK  */
    INT = 281,                     /* INT  */
    CHAR = 282,                    /* CHAR  */
    STRING = 283,                  /* STRING  */
    IF = 284,                      /* IF  */
    ELSE = 285,                    /* ELSE  */
    WHILE = 286,                   /* WHILE  */
    FOR = 287,                     /* FOR  */
    RETURN = 288,                  /* RETURN  */
    READ = 289,                    /* READ  */
    PRINT = 290,                   /* PRINT  */
    VOID = 291,                    /* VOID  */
    AND = 292,                     /* AND  */
    OR = 293,                      /* OR  */
    NOT = 294,                     /* NOT  */
    IDENTIFIER = 295,              /* IDENTIFIER  */
    INTEGER_CONST = 296,           /* INTEGER_CONST  */
    STRING_CONST = 297,            /* STRING_CONST  */
    CHAR_CONST = 298,              /* CHAR_CONST  */
    IF_NO_ELSE = 299               /* IF_NO_ELSE  */
  };
  typedef enum yytokentype yytoken_kind_t;
#endif

/* Value type.  */
#if ! defined YYSTYPE && ! defined YYSTYPE_IS_DECLARED
typedef int YYSTYPE;
# define YYSTYPE_IS_TRIVIAL 1
# define YYSTYPE_IS_DECLARED 1
#endif


extern YYSTYPE yylval;


int yyparse (void);


#endif /* !YY_YY_LANGUAGESPECIFIC_POSTCOMPILATION_LANG_TAB_H_INCLUDED  */
