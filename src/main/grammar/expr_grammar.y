 %{
import java.util.Vector;
import java.math.BigDecimal;
import java.text.ParseException;
%}

/* YACC Declarations */
%token NUM IDENT LSTSEP 
%left '&' '|' '#'
%left BSHIFTL BSHIFTR
%left '-' '+'
%left PLUSPERCENT MINUSPERCENT
%left '*' '/' '%'
%left NEG
%right '^'
%right '~'
%left ':'

/* Grammar follows */
%%
input: /* empty input */ { $$ = actEmpty(); } 
  | exp
  ;
  
exp: NUM { $$ = $1; }
 | NUM IDENT { $$ = actMultiply($1, actMemory($2)); }
 | IDENT { $$ = actMemory($1); }
 | IDENT '('explst ')' { $$ = actCall($1, $3); }
 | exp BSHIFTR exp { $$ = actShiftRight($1, $3); }
 | exp BSHIFTL exp { $$ = actShiftLeft($1, $3); }
 | exp '&' exp { $$ = actAnd($1, $3); }
 | exp '|' exp { $$ = actOr($1, $3); }
 | exp '#' exp { $$ = actXor($1, $3); }
 | exp '+' exp { $$ = actAdd($1, $3); }
 | exp '-' exp { $$ = actSubtract($1, $3); }
 | exp '%' exp { $$ = actRemainder($1, $3); }
 | exp PLUSPERCENT exp { $$ = actAddPercent($1, $3); }
 | exp MINUSPERCENT exp { $$ = actSubtractPercent($1, $3); }
 | exp '*' exp { $$ = actMultiply($1, $3); }
 | exp '/' exp { $$ = actDivide($1, $3); }
 | '-' exp %prec NEG { $$ = actNegate($2); }
 | exp '^' exp { $$ = actPower($1, $3); }
 | '~' exp { $$ = actNot($2); }
 | exp ':' exp { $$ = actMove($1, $3); }
 | '(' exp ')' { $$ = $2; }
 ;

explst: /* empty */ { $$ = actCollect(null, null); }
 | explst LSTSEP exp { $$ = actCollect($1, $3.nval); }
 | exp { $$ = actCollect(null, $1.nval); }
 ;
 
%%

private Lexer lexer;
private MathLib mathLib;

protected ExpressionParser(MathLib mathLib, Lexer lexer) {
  this.lexer = lexer;
  this.mathLib = mathLib;
  if (lexer == null || mathLib == null) {
    throw new NullPointerException();
  }
}

private TokenValue actCollect(TokenValue lst, BigDecimal val) {
  TokenValue ret = lst;
  if (ret == null) {
    ret = new TokenValue(new Vector<BigDecimal>());
  }
  if (val != null) {
    ret.lstval.add(val);
  }
  return ret;
}

private TokenValue actMemory(TokenValue x) {
  BigDecimal res = mathLib.onLookup(x.sval);
  if (res == null) {
    // Don't rely on a properly implemented mathlib
    throw new NotDefinedException(x.sval);
  }
  return new TokenValue(res);
}

private TokenValue actEmpty() {
  return new TokenValue(mathLib.onEmptyExpression());
}

private TokenValue actCall(TokenValue name, TokenValue param) {
  return new TokenValue(mathLib.onCall(name.sval, param.lstval));
}

private TokenValue actPower(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onExponentiation(x.nval, y.nval));
}

private TokenValue actNot(TokenValue num) {
  return new TokenValue(mathLib.onBitwiseNot(num.nval));
}

private TokenValue actAnd(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onBitwiseAnd(x.nval, y.nval));
}

private TokenValue actOr(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onBitwiseOr(x.nval, y.nval));
}

private TokenValue actXor(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onBitwiseXor(x.nval, y.nval));
}

private TokenValue actShiftLeft(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onBitshiftLeft(x.nval, y.nval));
}

private TokenValue actShiftRight(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onBitshiftRight(x.nval, y.nval));
}

private TokenValue actAdd(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onAddition(x.nval, y.nval));
}

private TokenValue actSubtract(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onSubtraction(x.nval, y.nval));
}

private TokenValue actRemainder(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onModulation(x.nval, y.nval));
}

private TokenValue actAddPercent(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onPercentAddition(x.nval, y.nval));
}

private TokenValue actSubtractPercent(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onPercentSubtraction(x.nval, y.nval));
}

private TokenValue actMultiply(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onMultiplication(x.nval, y.nval));
}

private TokenValue actDivide(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onDivision(x.nval, y.nval));
}

private TokenValue actMove(TokenValue x, TokenValue y) {
  return new TokenValue(mathLib.onMovePoint(x.nval, y.nval));
}

private TokenValue actNegate(TokenValue x) {
  return new TokenValue(mathLib.onNegation(x.nval));
}

void yyerror(String s) {
  String token = lexer.lastMatch();
  mathLib.onSyntaxError(lexer.getPosition()-token.length()+1, token.trim());
  // Don't rely on a properly implemented mathlib
  throw new ArithmeticException("syntax error - also your mathlib is buggy");
}

int yylex() {
  try {
    int ret = lexer.nextExpressionToken();
    if (ret==NUM || ret==IDENT) {
      yylval = lexer.value;
    }
    return ret;
  }
  catch (ParseException e) {
    mathLib.onTokenizeError(lexer.inp, e.getErrorOffset());
  }
  // Don't rely on a properly implemented mathlib
  throw new IllegalArgumentException("Won't tokenize - also your mathlib is buggy");
}

