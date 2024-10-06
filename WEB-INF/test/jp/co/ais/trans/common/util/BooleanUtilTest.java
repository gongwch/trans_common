package jp.co.ais.trans.common.util;

import org.junit.jupiter.api.Test;

class BooleanUtilTest {

	@Test
	void testToStringBoolean() {
		assertEquals("1", BooleanUtil.toString(Boolean.TRUE));
		assertEquals("0", BooleanUtil.toString(Boolean.FALSE));

	}

	@Test
	void testToInt() {
		assertEquals(0, BooleanUtil.toInt(Boolean.FALSE));
		assertEquals(1, BooleanUtil.toInt(Boolean.TRUE));

	}

	@Test
	void testToBooleanInt() {
		assertEquals(Boolean.FALSE, BooleanUtil.toBoolean(0));
		assertEquals(Boolean.TRUE, BooleanUtil.toBoolean(1));
	}

	@Test
	void testToBooleanString() {
		assertEquals("1", BooleanUtil.toString(Boolean.TRUE));
		assertEquals("0", BooleanUtil.toString(Boolean.FALSE));

	}

}
