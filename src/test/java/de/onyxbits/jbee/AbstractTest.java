package de.onyxbits.jbee;

import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Locale;

import org.junit.Before;

public abstract class AbstractTest implements MathLib {

	protected Evaluator evaluator;
	private MathLib mathLib;

	@Before
	public void setUp() throws Exception {
		mathLib = new DefaultMathLib();
		evaluator = new Evaluator((DecimalFormat) DecimalFormat.getInstance(Locale.US), this);
	}

	public MathContext getMathContext() {
		return null;
	}

	public BigDecimal onCall(String name, List<BigDecimal> args) throws NotDefinedException {
		return mathLib.onCall(name, args);
	}

	public BigDecimal onEmptyExpression() {
		return mathLib.onEmptyExpression();
	}

	public void onSyntaxError(int pos, String token) {
		mathLib.onSyntaxError(pos, token);
	}

	public void onTokenizeError(final char[] input, int offset) {
		mathLib.onTokenizeError(input, offset);
	}

	public BigDecimal onAddition(BigDecimal augend, BigDecimal addend) {
		return mathLib.onAddition(augend, addend);
	}

	public BigDecimal onSubtraction(BigDecimal minuend, BigDecimal subtrahend) {
		return mathLib.onSubtraction(minuend, subtrahend);
	}

	public BigDecimal onMultiplication(BigDecimal multiplicant, BigDecimal multiplier) {
		return mathLib.onMultiplication(multiplicant, multiplier);
	}

	public BigDecimal onDivision(BigDecimal dividend, BigDecimal divisor) {
		return mathLib.onDivision(dividend, divisor);
	}

	public BigDecimal onNegation(BigDecimal num) {
		return mathLib.onNegation(num);
	}

	public BigDecimal onModulation(BigDecimal dividend, BigDecimal moddivisor) {
		return mathLib.onModulation(dividend, moddivisor);
	}

	public BigDecimal onPercentAddition(BigDecimal base, BigDecimal percent) {
		return mathLib.onPercentAddition(base, percent);
	}

	public BigDecimal onPercentSubtraction(BigDecimal base, BigDecimal percent) {
		return mathLib.onPercentSubtraction(base, percent);
	}

	public BigDecimal onMovePoint(BigDecimal num, BigDecimal dir) {
		return mathLib.onMovePoint(num, dir);
	}

	public BigDecimal onExponentiation(BigDecimal base, BigDecimal exponent) {
		return mathLib.onExponentiation(base, exponent);
	}

	public BigDecimal onLookup(String name) {
		return mathLib.onLookup(name);
	}

	public void map(String name, BigDecimal value) {
		mathLib.map(name, value);
	}

	public void clearMappings() {
		mathLib.clearMappings();
	}

	public BigDecimal onBitwiseNot(BigDecimal num) throws ArithmeticException {
		return mathLib.onBitwiseNot(num);
	}

	public BigDecimal onBitwiseAnd(BigDecimal lhs, BigDecimal rhs) throws ArithmeticException {
		return mathLib.onBitwiseAnd(lhs, rhs);
	}

	public BigDecimal onBitwiseOr(BigDecimal lhs, BigDecimal rhs) throws ArithmeticException {
		return mathLib.onBitwiseOr(rhs, lhs);
	}

	public BigDecimal onBitwiseXor(BigDecimal lhs, BigDecimal rhs) throws ArithmeticException {
		return mathLib.onBitwiseXor(lhs, rhs);
	}

	public BigDecimal onBitshiftLeft(BigDecimal num, BigDecimal amount) throws ArithmeticException {
		return mathLib.onBitshiftLeft(num, amount);
	}

	public BigDecimal onBitshiftRight(BigDecimal num, BigDecimal amount) throws ArithmeticException {
		return mathLib.onBitshiftRight(num, amount);
	}

}
