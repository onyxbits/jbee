package de.onyxbits.jbee;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.ParseException;
import java.util.List;
import java.util.concurrent.TimeoutException;

import org.junit.Before;
import org.junit.Test;

public class FunctionTest extends AbstractTest {

	@Before
	public void setUp() throws Exception {
		super.setUp();
		evaluator.map("test0", BigDecimal.ZERO);
		evaluator.map("test10", BigDecimal.TEN);
	}

	@Test
	public void testVars() {
		assertEquals(new BigDecimal(200), evaluator.evaluate("(test10 + test10)*test10"));
	}

	@Test
	public void testFunction1() {
		assertEquals(new BigDecimal(-1), evaluator.evaluate("_signum(-10)"));
	}

	@Test
	public void testFunction2() {
		assertEquals(BigDecimal.TEN, evaluator.evaluate("constant_10()"));
	}

	@Test(expected = NotDefinedException.class)
	public void testUnknownFunction() throws ArithmeticException, NotDefinedException,
			ParseException, TimeoutException {
		evaluator.evaluateOrThrow("doesnotexist()");
	}

	@Test
	public void testMixed() {
		assertEquals(BigDecimal.TEN, evaluator.evaluate("test10 * -(-(constant_10() / (8+2)))"));
	}

	@Test
	public void testExpressionInArgument() {
		assertEquals(BigDecimal.TEN, evaluator.evaluate("test10 * _signum(-1+2)"));
	}

	@Test
	public void testCountArguments() {
		assertEquals(BigDecimal.TEN, evaluator.evaluate("countargs(1;2;3;4;5;6;7;8;9;0)"));
	}

	@Test
	public void testTwoFunctions() {
		assertEquals(BigDecimal.TEN,
				evaluator.evaluate("(constant_10()*countargs(1;2;3;4;5;6;7;8;9;0))/10"));
	}

	@Test(expected = ArithmeticException.class)
	public void testIndirectDivByZero() throws ParseException, ArithmeticException,
			NotDefinedException, TimeoutException {
		evaluator.evaluateOrThrow("10/test0");
	}
	
	@Test
	public void testNested() {
		assertEquals(BigDecimal.TEN, evaluator.evaluate("8+countargs(constant_10();constant_10())"));
	}

	public BigDecimal onCall(String name, List<BigDecimal> params)
			throws NotDefinedException {
		if ("_signum".equals(name)) {
			return new BigDecimal(Math.signum(params.get(0).doubleValue()));
		}
		if ("constant_10".equals(name)) {
			return BigDecimal.TEN;
		}
		if ("countargs".equals(name)) {
			return new BigDecimal(params.size());
		}
		throw new NotDefinedException(name);
	}
}
