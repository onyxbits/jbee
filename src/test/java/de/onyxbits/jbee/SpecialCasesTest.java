package de.onyxbits.jbee;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.TimeoutException;

import org.junit.Test;

import de.onyxbits.jbee.Evaluator;

public class SpecialCasesTest extends AbstractTest {

	@Test
	public void testNotTrimmed() {
		assertEquals(BigDecimal.TEN, evaluator.evaluate("   8 + 2  "));
	}

	@Test(expected = ArithmeticException.class)
	public void testDivisionByZero() throws ArithmeticException, NotDefinedException, ParseException,
			TimeoutException {
		evaluator.evaluateOrThrow("1/0");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyInput() throws ArithmeticException, NotDefinedException, ParseException,
			TimeoutException {
		evaluator.evaluateOrThrow("");
	}

	@Test(expected = IllegalArgumentException.class)
	public void testEmptyExpression() throws ArithmeticException, NotDefinedException,
			ParseException, TimeoutException {
		evaluator.evaluateOrThrow(" ");
	}

	@Test(expected = TimeoutException.class)
	public void testTakesTooLong() throws ArithmeticException, NotDefinedException, ParseException,
			TimeoutException {
		Evaluator e = new Evaluator((DecimalFormat) DecimalFormat.getInstance(Locale.US), this);
		e.setTimeout(50);
		e.evaluateOrThrow("1+1");
		e.evaluateOrThrow("block()");
	}

	@Test(expected = ArithmeticException.class)
	public void testSyntaxError() throws ArithmeticException, NotDefinedException, ParseException,
			TimeoutException {
		Evaluator e = new Evaluator((DecimalFormat) DecimalFormat.getInstance(Locale.US), this);
		e.evaluateOrThrow("3++");
	}

	@Test
	public void testImplicitMultiplication() {
		Evaluator e = new Evaluator((DecimalFormat) DecimalFormat.getInstance(Locale.US), this);
		e.map("hello", 5);
		e.map("world1", 10);
		assertEquals(BigDecimal.TEN, e.evaluate("2 hello"));
		assertEquals(BigDecimal.TEN, e.evaluate("2hello"));
		assertEquals(BigDecimal.TEN, e.evaluate("15-hello"));
		assertEquals(BigDecimal.TEN, e.evaluate("3*2hello -20"));
		assertEquals(BigDecimal.TEN, e.evaluate("100/2hello"));
		assertEquals(BigDecimal.TEN, e.evaluate("0+world1"));
		assertEquals(BigDecimal.TEN.negate(), e.evaluate("2* -hello"));
	}

	@Test
	public void testPrecissionloss() {
		DefaultMathLib dml = new DefaultMathLib();
		dml.setMathContext(new MathContext(2));
		Evaluator e = new Evaluator((DecimalFormat) DecimalFormat.getInstance(Locale.US), dml);
		assertEquals(BigDecimal.TEN, e.evaluate("5.00000001 + 5.00000002"));
	}

	@Test(expected = IllegalArgumentException.class)
	public void testTokenizeError() throws ArithmeticException, NotDefinedException,
			IllegalArgumentException, TimeoutException {
		evaluator.evaluateOrThrow("    }");
	}

	public BigDecimal onCall(String name, List<BigDecimal> args) throws NotDefinedException {
		if (name.equals("block")) {
			while (true)
				;
		}
		throw new NotDefinedException(name);
	}

	public BigDecimal onEmptyExpression() {
		throw new IllegalArgumentException();
	}

	public void onSyntaxError(int pos, String token) {
		throw new ArithmeticException("Syntax error: \'" + token + "\' at " + pos);
	}
}
