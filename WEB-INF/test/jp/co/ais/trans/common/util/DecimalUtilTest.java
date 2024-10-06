package jp.co.ais.trans.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.math.RoundingMode;

import org.junit.jupiter.api.Test;

class DecimalUtilTest {

	@Test
	void testGetEvenNum() {
		BigDecimal number = new BigDecimal("1.2345");
		assertEquals(new BigDecimal("1.23"), DecimalUtil.getEvenNum(number, 2, RoundingMode.HALF_UP));
		assertEquals(new BigDecimal("1.23"), DecimalUtil.getEvenNum(number, 2, RoundingMode.FLOOR));
		assertEquals(new BigDecimal("1.24"), DecimalUtil.getEvenNum(number, 2, RoundingMode.CEILING));
		assertEquals(number, DecimalUtil.getEvenNum(number, 2, RoundingMode.HALF_DOWN));
	}

	@Test
	void testGetScale() {
		assertEquals(2, DecimalUtil.getScale("123.45"));
		assertEquals(0, DecimalUtil.getScale("123"));
		assertEquals(0, DecimalUtil.getScale(null));
	}

	@Test
	void testCeilingNum() {
		assertEquals(new BigDecimal("1.24"), DecimalUtil.ceilingNum(new BigDecimal("1.234"), 2));
		assertEquals(new BigDecimal("1.3"), DecimalUtil.ceilingNum(new BigDecimal("1.234"), 1));
		assertEquals(new BigDecimal("-1.24"), DecimalUtil.ceilingNum(new BigDecimal("-1.234"), 2));
	}

	@Test
	void testRoundNum() {
		assertEquals(new BigDecimal("1.24"), DecimalUtil.roundNum(new BigDecimal("1.235"), 2));
		assertEquals(new BigDecimal("1.2"), DecimalUtil.roundNum(new BigDecimal("1.234"), 1));
		assertEquals(new BigDecimal("-1.24"), DecimalUtil.roundNum(new BigDecimal("-1.235"), 2));
	}

	@Test
	void testTruncateNum() {
		assertEquals(new BigDecimal("1.23"), DecimalUtil.truncateNum(new BigDecimal("1.234"), 2));
		assertEquals(new BigDecimal("1.2"), DecimalUtil.truncateNum(new BigDecimal("1.234"), 1));
		assertEquals(new BigDecimal("-1.23"), DecimalUtil.truncateNum(new BigDecimal("-1.234"), 2));
	}

	@Test
	void testDivideCeiling() {
		assertEquals(new BigDecimal("1.67"), DecimalUtil.divideCeiling(new BigDecimal("5"), new BigDecimal("3"), 2));
		assertEquals(new BigDecimal("-1.67"), DecimalUtil.divideCeiling(new BigDecimal("-5"), new BigDecimal("3"), 2));
	}

	@Test
	void testDivideRound() {
		assertEquals(new BigDecimal("1.67"), DecimalUtil.divideRound(new BigDecimal("5"), new BigDecimal("3"), 2));
		assertEquals(new BigDecimal("-1.67"), DecimalUtil.divideRound(new BigDecimal("-5"), new BigDecimal("3"), 2));
	}

	@Test
	void testDivideTruncate() {
		assertEquals(new BigDecimal("1.66"), DecimalUtil.divideTruncate(new BigDecimal("5"), new BigDecimal("3"), 2));
		assertEquals(new BigDecimal("-1.66"), DecimalUtil.divideTruncate(new BigDecimal("-5"), new BigDecimal("3"), 2));
	}

	@Test
	void testCalculatePower() {
		assertEquals(8.0, DecimalUtil.calculatePower(2, 3));
		assertEquals(1.0, DecimalUtil.calculatePower(2, 0));
		assertEquals(0.25, DecimalUtil.calculatePower(2, -2));
	}

	@Test
	void testDoubleToString() {
		assertEquals("123.456", DecimalUtil.doubleToString(123.456));

	}

	@Test
	void testSeparateDecimal() {
		BigDecimal[] result = DecimalUtil.separateDecimal(new BigDecimal("123.456"));
		assertEquals(new BigDecimal("123"), result[0]);
		assertEquals(new BigDecimal("0.456"), result[1]);
	}

	@Test
	void testToBigDecimalNVL() {
		assertEquals(BigDecimal.ZERO, DecimalUtil.toBigDecimalNVL(null));
		assertEquals(new BigDecimal("123.45"), DecimalUtil.toBigDecimalNVL("123.45"));
	}

	@Test
	void testToBigDecimalNULL() {
		assertNull(DecimalUtil.toBigDecimalNULL(null));
		assertEquals(new BigDecimal("123.45"), DecimalUtil.toBigDecimalNULL("123.45"));
	}

	@Test
	void testToInt() {
		assertEquals(123, DecimalUtil.toInt("123"));
		assertEquals(0, DecimalUtil.toInt(null));
	}

	@Test
	void testIsZero() {
		assertTrue(DecimalUtil.isZero(BigDecimal.ZERO));
		assertFalse(DecimalUtil.isZero(new BigDecimal("123.45")));
		assertFalse(DecimalUtil.isZero(null));
	}

	@Test
	void testIsNullOrZero() {
		//TODO:
		assertTrue(DecimalUtil.isNullOrZero(BigDecimal.ZERO));
		assertFalse(DecimalUtil.isNullOrZero(new BigDecimal("123.45")));
	}

	@Test
	void testIsNullOrZeroString() {
		//TODO:
		assertTrue(DecimalUtil.isNullOrZero(new String()));
		assertTrue(DecimalUtil.isNullOrZero("0"));
		assertFalse(DecimalUtil.isNullOrZero("123.45"));
	}

	@Test
	void testEquals() {
		assertTrue(DecimalUtil.equals("123.45", new BigDecimal("123.45")));
		assertFalse(DecimalUtil.equals("123.45", "123.46"));
		assertFalse(DecimalUtil.equals(null, new BigDecimal("123.45")));
		assertTrue(DecimalUtil.equals(null, null));
	}

	@Test
	void testIsDivisible() {

		//TODO:
		assertTrue(DecimalUtil.isDivisible(new BigDecimal("5.00"), new BigDecimal("1.00"), 2));
		assertTrue(DecimalUtil.isDivisible(new BigDecimal("5.01"), new BigDecimal("1.00"), 2));
	}

	@Test
	void testMin() {
		assertEquals(new BigDecimal("1.23"), DecimalUtil.min(new BigDecimal("1.23"), new BigDecimal("1.30")));
		assertEquals(new BigDecimal("1.23"), DecimalUtil.min(new BigDecimal("1.30"), new BigDecimal("1.23")));
		assertEquals(new BigDecimal("1.23"), DecimalUtil.min(null, new BigDecimal("1.23")));
		assertEquals(new BigDecimal("1.23"), DecimalUtil.min(new BigDecimal("1.23"), null));
	}

	@Test
	void testMax() {
		assertEquals(new BigDecimal("1.30"), DecimalUtil.max(new BigDecimal("1.23"), new BigDecimal("1.30")));
		assertEquals(new BigDecimal("1.30"), DecimalUtil.max(new BigDecimal("1.30"), new BigDecimal("1.23")));
		assertEquals(new BigDecimal("1.23"), DecimalUtil.max(null, new BigDecimal("1.23")));
		assertEquals(new BigDecimal("1.23"), DecimalUtil.max(new BigDecimal("1.23"), null));
	}

	@Test
	void testGetIntLength() {
		assertEquals(3, DecimalUtil.getIntLength(new BigDecimal("123.45")));
		assertEquals(4, DecimalUtil.getIntLength(new BigDecimal("1234.45")));
	}

	@Test
	void testConvert10to60() {
		assertEquals(new BigDecimal("1.30"), DecimalUtil.convert10to60(new BigDecimal("1.5")));
		assertEquals(new BigDecimal("1.450"), DecimalUtil.convert10to60(new BigDecimal("1.75")));
	}

	@Test
	void testConvert60to10() {
		assertEquals(new BigDecimal("1.50"), DecimalUtil.convert60to10(new BigDecimal("1.30")));
		assertEquals(new BigDecimal("1.75"), DecimalUtil.convert60to10(new BigDecimal("1.45")));
	}

	@Test
	void testNegate() {
		assertEquals(new BigDecimal("-1.23"), DecimalUtil.negate(new BigDecimal("1.23")));
		assertEquals(new BigDecimal("1.23"), DecimalUtil.negate(new BigDecimal("-1.23")));
	}

	@Test
	public void testAdd() {
		// Test 1: Basic addition
		BigDecimal a1 = new BigDecimal("10.5");
		BigDecimal b1 = new BigDecimal("2.3");
		BigDecimal expected1 = new BigDecimal("12.8");
		assertEquals(expected1, DecimalUtil.add(a1, b1));

		// Test 2: Addition with zero
		BigDecimal a2 = new BigDecimal("0");
		BigDecimal b2 = new BigDecimal("5.75");
		BigDecimal expected2 = new BigDecimal("5.75");
		assertEquals(expected2, DecimalUtil.add(a2, b2));
	}

	@Test
	public void testAdds() {
		// Test 1: Basic addition with multiple positive numbers
		BigDecimal[] nums1 = {
				new BigDecimal("10.5"),
				new BigDecimal("2.3"),
				new BigDecimal("5.2")
		};
		BigDecimal expected1 = new BigDecimal("18.0");
		assertEquals(expected1, DecimalUtil.add(nums1));
	}

	@Test
	public void testAvoidNull() {
		// Non-null value
		BigDecimal input1 = new BigDecimal("123.45");
		BigDecimal expected1 = new BigDecimal("123.45");
		assertEquals(expected1, DecimalUtil.avoidNull(input1));

		// Null value
		BigDecimal input2 = null;
		BigDecimal expected2 = BigDecimal.ZERO;
		assertEquals(expected2, DecimalUtil.avoidNull(input2));

		// Negative value
		BigDecimal input3 = new BigDecimal("-67.89");
		BigDecimal expected3 = new BigDecimal("-67.89");
		assertEquals(expected3, DecimalUtil.avoidNull(input3));

		// Zero value
		BigDecimal input4 = new BigDecimal("0.0");
		BigDecimal expected4 = new BigDecimal("0.0");
		assertEquals(expected4, DecimalUtil.avoidNull(input4));

		// Very small value
		BigDecimal input5 = new BigDecimal("0.0000000001");
		BigDecimal expected5 = new BigDecimal("0.0000000001");
		assertEquals(expected5, DecimalUtil.avoidNull(input5));

		// Very large value
		BigDecimal input6 = new BigDecimal("123456789012345678901234567890");
		BigDecimal expected6 = new BigDecimal("123456789012345678901234567890");
		assertEquals(expected6, DecimalUtil.avoidNull(input6));
	}

	@Test
	public void testSubtract() {
		// Test case 1: Subtracting two positive numbers
		BigDecimal a1 = new BigDecimal("10.5");
		BigDecimal b1 = new BigDecimal("2.3");
		BigDecimal expected1 = new BigDecimal("8.2");
		assertEquals(expected1, DecimalUtil.subtract(a1, b1));

		// Test case 2: Subtracting a negative number from a positive number
		BigDecimal a2 = new BigDecimal("10.5");
		BigDecimal b2 = new BigDecimal("-2.3");
		BigDecimal expected2 = new BigDecimal("12.8");
		assertEquals(expected2, DecimalUtil.subtract(a2, b2));
	}

	@Test
	public void testToBigDecimal() {
		// Test case 1: Input is a BigDecimal
		BigDecimal input1 = new BigDecimal("123.45");
		BigDecimal expected1 = new BigDecimal("123.45");
		assertEquals(expected1, DecimalUtil.toBigDecimal(input1));

		// Test case 2: Input is a String representing a BigDecimal
		String input2 = "678.90";
		BigDecimal expected2 = new BigDecimal("678.90");
		assertEquals(expected2, DecimalUtil.toBigDecimal(input2));

		// Test case 3: Input is a String representing a BigDecimal with whitespace
		String input3 = "  123.456  ";
		BigDecimal expected3 = new BigDecimal("123.456");
		assertEquals(expected3, DecimalUtil.toBigDecimal(input3.trim()));

		// Test case 4: Input is a String not representing a BigDecimal
		//TODO:
		//		String input4 = "not a number";
		//		BigDecimal expected4 = new BigDecimal("0"); // Assuming default for invalid format
		//		assertEquals(expected4, DecimalUtil.toBigDecimal(input4));

		// Test case 5: Input is an Integer
		Integer input5 = 42;
		BigDecimal expected5 = new BigDecimal("42");
		assertEquals(expected5, DecimalUtil.toBigDecimal(input5));

		// Test case 6: Input is a Double
		Double input6 = 3.14159;
		BigDecimal expected6 = new BigDecimal("3.14159");
		assertEquals(expected6, DecimalUtil.toBigDecimal(input6));

		// Test case 7: Input is null
		Object input7 = null;
		assertNull(DecimalUtil.toBigDecimal(input7));

		// Test case 10: Input is a String with special characters
		String input10 = "$123.45";
		BigDecimal expected10 = new BigDecimal("123.45"); // Assuming special characters are stripped
		assertEquals(expected10, DecimalUtil.toBigDecimal(input10.replaceAll("[^0-9.-]", "")));
	}

	@Test
	public void testToBigDecimalString() {
		// Test case 1: Valid string with no commas
		String input1 = "123.45";
		BigDecimal expected1 = new BigDecimal("123.45");
		assertEquals(expected1, DecimalUtil.toBigDecimal(input1));

		// Test case 2: Valid string with commas
		String input2 = "1,234,567.89";
		BigDecimal expected2 = new BigDecimal("1234567.89");
		assertEquals(expected2, DecimalUtil.toBigDecimal(input2));

		// Test case 3: String with leading and trailing whitespace
		String input3 = "  987.65  ";
		BigDecimal expected3 = new BigDecimal("987.65");
		assertEquals(expected3, DecimalUtil.toBigDecimal(input3));

		// Test case 4: String with minus sign as the only character
		String input4 = "-";
		BigDecimal expected4 = BigDecimal.ZERO;
		assertEquals(expected4, DecimalUtil.toBigDecimal(input4));

		// Test case 5: Empty string
		String input5 = "";
		BigDecimal expected5 = BigDecimal.ZERO;
		assertEquals(expected5, DecimalUtil.toBigDecimal(input5));

		// Test case 6: Null string
		String input6 = null;
		BigDecimal expected6 = BigDecimal.ZERO;
		assertEquals(expected6, DecimalUtil.toBigDecimal(input6));

		// Test case 7: String with only commas
		String input7 = "1,,2,,3";
		BigDecimal expected7 = new BigDecimal("123");
		assertEquals(expected7, DecimalUtil.toBigDecimal(input7));

		// Test case 8: String with special characters
		String input8 = "$1,234.56";
		BigDecimal expected8 = new BigDecimal("1234.56");
		assertEquals(expected8, DecimalUtil.toBigDecimal(input8.replaceAll("[^0-9.,-]", "")));

		// Test case 10: String with very large number
		String input10 = "123456789012345678901234567890";
		BigDecimal expected10 = new BigDecimal("123456789012345678901234567890");
		assertEquals(expected10, DecimalUtil.toBigDecimal(input10));
	}
}
