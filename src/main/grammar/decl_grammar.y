 %{
import java.util.HashMap;
import java.math.BigDecimal;
import java.text.ParseException;
%}

/* YACC Declarations */
%token NUM IDENT '='

/* Grammar follows */
%%

input: /* empty */
 | linelst decllst
 ;
 
linelst: /* empty */
 | linelst '\n'
 ;
 
decllst: decl 
 | decllst decl 
 ;
  
decl: IDENT '=' NUM declsep { declarations.put($1.sval, $3.nval); } ;

declsep: /* empty */
 | ';'
 | '\n'
 | ';' '\n'
 ;

%%

private Lexer lexer;
protected HashMap<String, BigDecimal> declarations;

protected DeclarationParser(Lexer lexer) {
  this.lexer = lexer;
  declarations = new HashMap<String, BigDecimal>();
  if (lexer == null) {
    throw new NullPointerException();
  }
}

void yyerror(String s) throws ParseException {
  throw new ParseException(lexer.lastMatch().trim(), lexer.getPosition());
}

int yylex() throws ParseException {
  int ret = lexer.nextDeclarationToken();
  if (ret==NUM || ret==IDENT) {
    yylval = lexer.value;
  }
  return ret;
}

