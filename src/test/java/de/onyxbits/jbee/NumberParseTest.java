package de.onyxbits.jbee;

import static org.junit.Assert.*;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Locale;

import org.junit.Before;
import org.junit.Test;

import de.onyxbits.jbee.Evaluator;

public class NumberParseTest extends AbstractTest {

	private Evaluator fullstop;
	private Evaluator comma;

	@Before
	public void setUp() throws Exception {
		fullstop = new Evaluator((DecimalFormat) DecimalFormat.getInstance(Locale.US), new DefaultMathLib());
		comma = new Evaluator((DecimalFormat) DecimalFormat.getInstance(Locale.GERMANY), new DefaultMathLib());
	}

	@Test
	public void testFullStop() {
		assertEquals(9, fullstop.evaluate("2*4.5").intValue());
	}

	@Test
	public void testComma() {
		assertEquals(9, comma.evaluate("2*4,5").intValue());
	}

	@Test
	public void testCommaGrouper() {
		assertEquals(10001, comma.evaluate("2* 5.000,5").intValue());
	}

	@Test
	public void testFullStopGrouper() {
		assertEquals(10001, fullstop.evaluate("2* 5,000.5").intValue());
	}

	@Test
	public void testBinary() {
		assertEquals(BigDecimal.TEN, fullstop.evaluate("\\b1010"));
	}

	@Test
	public void testHexadecimal() {
		assertEquals(BigDecimal.TEN, fullstop.evaluate("\\xA"));
		assertEquals(BigDecimal.TEN, fullstop.evaluate("\\xa"));
	}
	
}
