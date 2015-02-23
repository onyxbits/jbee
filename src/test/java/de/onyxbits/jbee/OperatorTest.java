package de.onyxbits.jbee;

import static org.junit.Assert.*;

import java.math.BigDecimal;

import org.junit.Test;


public class OperatorTest extends AbstractTest {

	@Test
	public void testAddition() {
		assertEquals(BigDecimal.TEN, evaluator.evaluate("9+1"));
	}

	@Test
	public void testSubTraction() {
		assertEquals(BigDecimal.ZERO, evaluator.evaluate("5-5"));
	}

	@Test
	public void testMultiplication() {
		assertEquals(BigDecimal.TEN, evaluator.evaluate("5*2"));
	}

	@Test
	public void testDivision() {
		assertEquals(BigDecimal.TEN, evaluator.evaluate("20/2"));
	}

	@Test
	public void testAddPercent() {
		assertEquals(new BigDecimal(110), evaluator.evaluate("100 +% 10"));
	}

	@Test
	public void testSubtractPercent() {
		assertEquals(new BigDecimal(75), evaluator.evaluate("100 -% 25"));
	}

	@Test
	public void testNegate() {
		assertEquals(new BigDecimal(-50), evaluator.evaluate("- 50"));
	}
	
	@Test
	public void testPrecedence() {
		assertEquals(BigDecimal.TEN,evaluator.evaluate("4*5-10"));
		assertEquals(BigDecimal.TEN,evaluator.evaluate("2*(10-5)"));
		assertEquals(BigDecimal.TEN,evaluator.evaluate("\\b1000 | \\b100 >> 1"));
		assertEquals(BigDecimal.TEN,evaluator.evaluate("4+ \\b100 | \\b100 >> 1"));
		assertEquals(BigDecimal.TEN,evaluator.evaluate("9 +(1:1 ^ \\b1011)"));
	}

	@Test
	public void testRemainder() {
		assertEquals(new BigDecimal(2), evaluator.evaluate("8 % 3"));
	}
	
	@Test
	public void testBitwiseNot() {
		assertEquals(new BigDecimal(-6), evaluator.evaluate("~\\b101"));
	}
	
	@Test
	public void testBitwiseAnd() {
		assertEquals(new BigDecimal(1), evaluator.evaluate("\\b101 & \\b001"));
	}
	
	@Test
	public void testBitwiseOr() {
		assertEquals(new BigDecimal(7), evaluator.evaluate("\\b101 | \\b010"));
	}
	
	@Test
	public void testBitwiseXor() {
		assertEquals(new BigDecimal(6), evaluator.evaluate("\\b101 ^ \\b011"));
	}
	
	@Test
	public void testBitshiftLeft() {
		assertEquals(new BigDecimal(16), evaluator.evaluate("\\b100 << 2"));
	}
	
	@Test
	public void testBitshiftRight() {
		assertEquals(new BigDecimal(1), evaluator.evaluate("\\b100 >> 2"));
	}
	
	@Test
	public void testMovePoint() {
		assertEquals(new BigDecimal("1.2"), evaluator.evaluate("1.2:0"));
		assertEquals(new BigDecimal("12"), evaluator.evaluate("1.2:1"));
		assertEquals(new BigDecimal("0.12"), evaluator.evaluate("1.2:-1"));
		assertEquals(new BigDecimal("0.60"), evaluator.evaluate("5*1.2:-1"));
		assertEquals(new BigDecimal("1.2"), evaluator.evaluate("1.2:1/10"));
		assertEquals(new BigDecimal("120"), evaluator.evaluate("1.2:1:1"));
	}

}
