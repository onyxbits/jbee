# Transform the grammar file into it's java classes. This step is normally
# not needed and this makefile exists for documentation purposes only

all: clean
	byaccj -d -v -Jpackage=de.onyxbits.jbee -Jclass=ExpressionParser -Jsemantic=TokenValue -Jfinal -Jnodebug -Jnorun -Jnoconstruct expr_grammar.y
	byaccj -d -v -Jpackage=de.onyxbits.jbee -Jclass=DeclarationParser -Jthrows=ParseException -Jsemantic=TokenValue -Jfinal -Jnodebug -Jnorun -Jnoconstruct decl_grammar.y
	cp *.java ../java/de/onyxbits/jbee
	
clean:
	rm -f y.output Parser.java ParserTokens.java *~ DEADJOE
	