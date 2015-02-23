package de.onyxbits.jbee;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.concurrent.TimeoutException;

import org.junit.Test;


public class BracketTest extends AbstractTest {

	@Test
	public void testSuperflous() {
		assertEquals(BigDecimal.TEN, evaluator.evaluate("(20-5)-5"));
	}

	@Test
	public void testAssociativity() {
		assertEquals(BigDecimal.TEN, evaluator.evaluate("10-(5-5)"));
	}

	@Test
	public void testPrecedence() {
		assertEquals(BigDecimal.TEN, evaluator.evaluate("2 * (8-3)"));
		assertEquals(BigDecimal.TEN, evaluator.evaluate("(8-3) *2"));
	}

	@Test(expected = ArithmeticException.class)
	public void testOpenBracket() throws ArithmeticException, NotDefinedException, ParseException,
			TimeoutException {
		evaluator.evaluateOrThrow("2 * (8-3");
	}

}
