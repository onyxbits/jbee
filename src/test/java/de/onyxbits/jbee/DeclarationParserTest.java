package de.onyxbits.jbee;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.HashMap;

import org.junit.Test;

public class DeclarationParserTest extends AbstractTest {

	@Test
	public void testDeclarationParser() throws ParseException {
		assertEquals(0, check("").size());
		assertEquals(BigDecimal.TEN, check("hello=10\n").get("hello"));
		assertEquals(BigDecimal.TEN, check("hello = 10;").get("hello"));
		assertEquals(BigDecimal.TEN, check("hello = 10;\n").get("hello"));
		assertEquals(BigDecimal.TEN, check("hello = 1; hello = 10").get("hello"));
		assertEquals(BigDecimal.TEN, check("hello = 1; hello = 10;").get("hello"));
		assertEquals(BigDecimal.TEN, check("hello = 1; hello = 10;\n").get("hello"));
		assertEquals(BigDecimal.TEN, check("hello = 1;\n hello = 10").get("hello"));
		assertEquals(BigDecimal.TEN, check("hello = 1;\n hello = 10;").get("hello"));
		assertEquals(BigDecimal.TEN, check("hello = 1;\n hello = 10;\n").get("hello"));

		assertEquals(BigDecimal.TEN, check("\nhello=10\n").get("hello"));
		assertEquals(BigDecimal.TEN, check("\nhello = 10;").get("hello"));
		assertEquals(BigDecimal.TEN, check("\nhello = 10;\n").get("hello"));
		assertEquals(BigDecimal.TEN, check("\nhello = 1; hello = 10").get("hello"));
		assertEquals(BigDecimal.TEN, check("\nhello = 1; hello = 10;").get("hello"));
		assertEquals(BigDecimal.TEN, check("\nhello = 1; hello = 10;\n").get("hello"));
		assertEquals(BigDecimal.TEN, check("\nhello = 1;\n hello = 10").get("hello"));
		assertEquals(BigDecimal.TEN, check("\nhello = 1;\n hello = 10;").get("hello"));
		assertEquals(BigDecimal.TEN, check("\nhello = 1;\n hello = 10;\n").get("hello"));

		assertEquals(BigDecimal.TEN, check("// A comment\nhello = 1;\n hello = 10;\n").get("hello"));
		assertEquals(BigDecimal.TEN, check("hello = \\x1;\n hello = 10;\n//A comment").get("hello"));
		assertEquals(BigDecimal.TEN, check("hello = \\b1;\n hello = \\xa;\n//A comment\n").get("hello"));
		assertEquals(
				BigDecimal.TEN,
				check("// First comment\n//Second comment\nhello = 1;\n hello = 10;\n//A comment\n").get(
						"hello"));
	}

	@Test(expected = ParseException.class)
	public void testSyntax() throws ParseException {
		check("hello==10");
	}

	@Test(expected = ParseException.class)
	public void testTokenizer() throws ParseException {
		check("1hello==10");
	}

	@Test
	public void testFullTest() throws ParseException {
		evaluator.mapAll(evaluator.createMapping("test1=20; test2 = 10 test3= 3"));
		assertEquals(BigDecimal.TEN, evaluator.evaluate("(test1+test2)/test3"));
	}

	private HashMap<String, BigDecimal> check(String decl) throws ParseException {
		Lexer l = new Lexer((DecimalFormat) DecimalFormat.getInstance(), decl);
		DeclarationParser p = new DeclarationParser(l);
		p.yyparse();
		return p.declarations;
	}
}
