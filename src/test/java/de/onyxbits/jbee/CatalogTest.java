package de.onyxbits.jbee;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

public class CatalogTest {

	private Evaluator evaluator = new Evaluator((DecimalFormat) DecimalFormat.getInstance(Locale.US),
			new FunctionCatalogMathLib());

	@Before
	public void setUp() throws Exception {
		MathLib mathLib = new FunctionCatalogMathLib();
		evaluator = new Evaluator((DecimalFormat) DecimalFormat.getInstance(Locale.US), mathLib);
	}

	@Test
	public void testPower() {

		assertEquals(new BigDecimal(16), evaluator.evaluate("pow(2;4)"));
	}

	@Test
	public void testAbs() {
		assertEquals(BigDecimal.TEN, evaluator.evaluate("abs(-10)"));
	}

	@Test
	public void testSignum() {
		assertEquals(new BigDecimal(-1), evaluator.evaluate("signum(-10)"));
	}
}
