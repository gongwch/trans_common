package jp.co.ais.trans.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import javax.swing.SwingConstants;

import org.junit.jupiter.api.Test;

class StringUtilTest {
	@Test
	public void testSplit() {
		// Test 1: Normal Split
		String s1 = "apple,banana,cherry";
		String delimiter1 = ",";
		String[] expected1 = { "apple", "banana", "cherry" };
		assertArrayEquals(expected1, StringUtil.split(s1, delimiter1));

		// Test 2: Empty String Input
		String s2 = "";
		String delimiter2 = ",";
		String[] expected2 = {};
		assertArrayEquals(expected2, StringUtil.split(s2, delimiter2));

		// Test 3: Empty Delimiter
		String s3 = "applebananacherry";
		String delimiter3 = "";
		String[] expected3 = { "applebananacherry" };
		assertArrayEquals(expected3, StringUtil.split(s3, delimiter3));

		// Test 4: Empty String and Empty Delimiter
		String s4 = "";
		String delimiter4 = "";
		String[] expected4 = {};
		assertArrayEquals(expected4, StringUtil.split(s4, delimiter4));

		// Test 5: Delimiter Not Found in String
		String s5 = "applebananacherry";
		String delimiter5 = ",";
		String[] expected5 = { "applebananacherry" };
		assertArrayEquals(expected5, StringUtil.split(s5, delimiter5));

		// Test 6: Delimiter at Start and End
		String s6 = ",apple,banana,cherry,";
		String delimiter6 = ",";
		String[] expected6 = { "", "apple", "banana", "cherry", "" };
		assertArrayEquals(expected6, StringUtil.split(s6, delimiter6));

		// Test 7: Delimiter Longer than String
		String s7 = "apple";
		String delimiter7 = "longdelim";
		String[] expected7 = { "apple" };
		assertArrayEquals(expected7, StringUtil.split(s7, delimiter7));

		// Test 8: Consecutive Delimiters
		String s8 = "apple,,banana,cherry";
		String delimiter8 = ",";
		String[] expected8 = { "apple", "", "banana", "cherry" };
		assertArrayEquals(expected8, StringUtil.split(s8, delimiter8));

		// Test 9: Single Character Delimiter
		String s9 = "a-b-c-d";
		String delimiter9 = "-";
		String[] expected9 = { "a", "b", "c", "d" };
		assertArrayEquals(expected9, StringUtil.split(s9, delimiter9));
	}

	@Test
	public void testSplitWithLimit() {
		// Test 1: Normal Split with Limit
		String s1 = "apple,banana,cherry,date";
		String delimiter1 = ",";
		int num1 = 3;
		String[] expected1 = { "apple", "banana", "cherry,date" };
		assertArrayEquals(expected1, StringUtil.split(s1, delimiter1, num1));

		// Test 2: Empty String Input
		String s2 = "";
		String delimiter2 = ",";
		int num2 = 3;
		String[] expected2 = {};
		assertArrayEquals(expected2, StringUtil.split(s2, delimiter2, num2));

		// Test 3: Empty Delimiter
		String s3 = "applebananacherry";
		String delimiter3 = "";
		int num3 = 3;
		String[] expected3 = { "applebananacherry" };
		assertArrayEquals(expected3, StringUtil.split(s3, delimiter3, num3));

		// Test 4: Empty String and Empty Delimiter
		String s4 = "";
		String delimiter4 = "";
		int num4 = 3;
		String[] expected4 = {};
		assertArrayEquals(expected4, StringUtil.split(s4, delimiter4, num4));

		// Test 5: Delimiter Not Found in String
		String s5 = "applebananacherry";
		String delimiter5 = ",";
		int num5 = 3;
		String[] expected5 = { "applebananacherry" };
		assertArrayEquals(expected5, StringUtil.split(s5, delimiter5, num5));

		// Test 6: Delimiter at Start and End with Limit
		String s6 = ",apple,banana,cherry,";
		String delimiter6 = ",";
		int num6 = 3;
		String[] expected6 = { "", "apple", "banana,cherry," };
		assertArrayEquals(expected6, StringUtil.split(s6, delimiter6, num6));

		// Test 7: Delimiter Longer than String
		String s7 = "apple";
		String delimiter7 = "longdelim";
		int num7 = 3;
		String[] expected7 = { "apple" };
		assertArrayEquals(expected7, StringUtil.split(s7, delimiter7, num7));

		// Test 8: Consecutive Delimiters with Limit
		String s8 = "apple,,banana,cherry";
		String delimiter8 = ",";
		int num8 = 3;
		String[] expected8 = { "apple", "", "banana,cherry" };
		assertArrayEquals(expected8, StringUtil.split(s8, delimiter8, num8));

		// Test 9: Single Character Delimiter with Limit
		String s9 = "a-b-c-d";
		String delimiter9 = "-";
		int num9 = 3;
		String[] expected9 = { "a", "b", "c-d" };
		assertArrayEquals(expected9, StringUtil.split(s9, delimiter9, num9));

		// Test 10: Limit Less than or Equal to Zero
		String s10 = "apple,banana,cherry,date";
		String delimiter10 = ",";
		int num10 = 0; // Invalid limit
		String[] expected10 = {};
		assertArrayEquals(expected10, StringUtil.split(s10, delimiter10, num10));

		// Test 11: Limit Greater than Number of Delimiters
		String s11 = "apple,banana,cherry";
		String delimiter11 = ",";
		int num11 = 5; // Greater than number of splits possible
		String[] expected11 = { "apple", "banana", "cherry" };
		assertArrayEquals(expected11, StringUtil.split(s11, delimiter11, num11));
	}

	@Test
	// TODO: The test case is generated by the chatgpt, which requires extract confirmation
	public void testEscapeHtml() {
		// Test 1: Basic HTML characters
		String input1 = "<div>Hello & Welcome</div>";
		//TODO:
		// Expected HTML escape: &lt;div&gt;Hello &amp; Welcome&lt;/div&gt;
		// Expected Java escape: \u003Cdiv\u003EHello \u0026 Welcome\u003C/div\u003E
		String expected1 = "&lt;div&gt;Hello &amp; Welcome&lt;/div&gt;";
		assertEquals(expected1, StringUtil.escapeHtml(input1));

		// Test 2: Input with Java special characters
		String input2 = "Hello \"world\" \n \r \\";
		// Expected HTML escape: Hello &quot;world&quot; \n \r \\
		// Expected Java escape: Hello \\\"world\\\" \\n \\r \\\\
		String expected2 = "Hello &quot;world&quot; \\n \\r \\\\";
		assertEquals(expected2, StringUtil.escapeHtml(input2));

		// Test 3: Input with both HTML and Java special characters
		String input3 = "<script>alert('Hello')</script>";
		// Expected HTML escape: &lt;script&gt;alert(&#x27;Hello&#x27;)&lt;/script&gt;
		// Expected Java escape: \u003Cscript\u003Ealert\u0028\u0027Hello\u0027\u0029\u003C/script\u003E
		String expected3 = "&lt;script&gt;alert('Hello')&lt;/script&gt;";
		assertEquals(expected3, StringUtil.escapeHtml(input3));

		// Test 4: Empty String
		String input4 = "";
		String expected4 = "";
		assertEquals(expected4, StringUtil.escapeHtml(input4));

		// Test 6: String with Unicode characters
		String input6 = "‚±‚ñ‚É‚¿‚Í";
		String expected6 = "\\u3053\\u3093\\u306B\\u3061\\u306F";
		assertEquals(expected6, StringUtil.escapeHtml(input6));

		// Test 7: Null Input
		String input7 = null;
		String expected7 = "";
		assertEquals(expected7, StringUtil.escapeHtml(input7));
	}

	@Test
	public void testUnescapeHtml() {
		// TODO: The test case is generated by the chatgpt, which requires extract confirmation
		// Test 1: Basic HTML characters
		String input1 = "\\u003Cdiv\\u003EHello \\u0026 Welcome\\u003C/div\\u003E";
		// Expected Java unescape: <div>Hello & Welcome</div>
		// Expected HTML unescape: <div>Hello & Welcome</div>
		String expected1 = "<div>Hello & Welcome</div>";
		assertEquals(expected1, StringUtil.unescapeHtml(input1));

		// Test 2: Input with Java special characters
		String input2 = "Hello \\\"world\\\" \\n \\r \\\\";
		// Expected Java unescape: Hello "world" \n \r \\
		// Expected HTML unescape: Hello "world" \n \r \\
		String expected2 = "Hello \"world\" \n \r \\";
		assertEquals(expected2, StringUtil.unescapeHtml(input2));

		// Test 3: Input with both HTML and Java special characters
		String input3 = "\\u003Cscript\\u003Ealert\\u0028\\u0027Hello\\u0027\\u0029\\u003C/script\\u003E";
		// Expected Java unescape: <script>alert('Hello')</script>
		// Expected HTML unescape: <script>alert('Hello')</script>
		String expected3 = "<script>alert('Hello')</script>";
		assertEquals(expected3, StringUtil.unescapeHtml(input3));

		// Test 4: Empty String
		String input4 = "";
		String expected4 = "";
		assertEquals(expected4, StringUtil.unescapeHtml(input4));

		// Test 5: String with only spaces
		String input5 = "   ";
		String expected5 = "";
		assertEquals(expected5, StringUtil.unescapeHtml(input5));

		// Test 6: String with Unicode characters
		String input6 = "‚±‚ñ‚É‚¿‚Í";
		String expected6 = "‚±‚ñ‚É‚¿‚Í";
		assertEquals(expected6, StringUtil.unescapeHtml(input6));

		// Test 7: Null Input
		String input7 = null;
		String expected7 = "";
		assertEquals(expected7, StringUtil.unescapeHtml(input7));
	}

	@Test
	public void testAddSpace() {
		String result = StringUtil.addSpace("example");
		assertEquals("e x a m p l e", result);
	}

	@Test
	public void testAddCharacter() {
		String result = StringUtil.addCharacter("example", "-");
		assertEquals("e-x-a-m-p-l-e", result);
	}

	@Test
	public void testIsUpperCase() {
		assertTrue(StringUtil.isUpperCase("HELLO WORLD"));
		assertFalse(StringUtil.isUpperCase("Hello World"));
	}

	@Test
	public void testIsLowerCase() {
		assertTrue(StringUtil.isLowerCase("hello world"));
		assertFalse(StringUtil.isLowerCase("Hello World"));
	}

	@Test
	public void testIsHalfKana() {
		assertTrue(StringUtil.isHalfKana("ÊÝ¶¸¶Å"));
		assertFalse(StringUtil.isHalfKana("ƒnƒ“ƒOƒ‹"));
	}

	@Test
	public void testToUpperCase() {
		assertEquals("TO UPPER CASE", StringUtil.toUpperCase("toUpperCase"));
	}

	@Test
	public void testToLowerCase() {
		assertEquals("toLowerCase", StringUtil.toLowerCase("TO LOWER CASE"));
	}

	@Test
	public void testAppendLineSeparator() {
		//TODO:
		String input = "This is a test string that needs formatting.";
		int maxLength = 10;
		String expected = "This is a \ntest string \nthat needs \nformatting.";
		String result = StringUtil.appendLineSeparator(maxLength, input);
		assertEquals(expected, result);
	}

	@Test
	public void testToCommaStringArray() {
		String result = StringUtil.toCommaString(new String[] { "a", "b", "c" });
		assertEquals("a,b,c", result);
	}

	@Test
	public void testToCommaStringBooleanArray() {
		String result = StringUtil.toCommaString(new boolean[] { true, false, true });
		assertEquals("true,false,true", result);
	}

	@Test
	public void testToCommaStringIntArray() {
		String result = StringUtil.toCommaString(new int[] { 1, 2, 3 });
		assertEquals("1,2,3", result);
	}

	@Test
	public void testToCommaStringList() {
		String result = StringUtil.toCommaString(Arrays.asList("a", "b", "c"));
		assertEquals("a,b,c", result);
	}

	@Test
	public void testToDQuoCommaStringList() {
		String result = StringUtil.toDQuoCommaString(Arrays.asList("a", "b", "c"));
		assertEquals("\"a\",\"b\",\"c\"", result);
	}

	@Test
	public void testToArrayByComma() {
		String[] result = StringUtil.toArrayByComma("a,b,c");
		assertArrayEquals(new String[] { "a", "b", "c" }, result);
	}

	@Test
	public void testToIntArrayByComma() {
		int[] result = StringUtil.toIntArrayByComma("1,2,3");
		assertArrayEquals(new int[] { 1, 2, 3 }, result);
	}

	@Test
	void testToDelimitStringStringArray() {
		String[] input = { "one", "two", "three" };
		String expected = "one<>two<>three"; // Adjust this based on your DELIMITER
		String result = StringUtil.toDelimitString(input);
		assertEquals(expected, result);
	}

	@Test
	void testToArrayFromDelimitString() {
		String input = "one<>two<>three"; // Adjust this based on DELIMITER
		String[] expected = { "one", "two", "three" };
		String[] result = StringUtil.toArrayFromDelimitString(input);
		assertArrayEquals(expected, result);
	}

	@Test
	void testToDelimitStringBooleanArray() {
		boolean[] input = { true, false, true };
		String expected = "1<>0<>1";//based on DELIMITER(<>)
		String result = StringUtil.toDelimitString(input);
		assertEquals(expected, result);
	}

	@Test
	void testToBooleanArrayFromDelimitString() {
		String input = "true<>false<>true";
		boolean[] expected = { true, false, true };
		boolean[] result = StringUtil.toBooleanArrayFromDelimitString(input);
		assertArrayEquals(expected, result);
	}

	@Test
	void testToDelimitStringIntArray() {
		int[] input = { 1, 2, 3 };
		String expected = "1<>2<>3";
		String result = StringUtil.toDelimitString(input);
		assertEquals(expected, result);
	}

	@Test
	void testToIntArrayFromDelimitString() {
		String input = "1<>2<>3";
		int[] expected = { 1, 2, 3 };
		int[] result = StringUtil.toIntArrayFromDelimitString(input);
		assertArrayEquals(expected, result);
	}

	@Test
	void testRightTrim() {
		String input = "hello   ";
		String expected = "hello";
		String result = StringUtil.rightTrim(input);
		assertEquals(expected, result);
	}

	@Test
	void testLeftTrim() {
		String input = "   hello";
		String expected = "hello";
		String result = StringUtil.leftTrim(input);
		assertEquals(expected, result);
	}

	@Test
	void testLeftBX() {
		String input = "‚ ‚¢‚¤‚¦‚¨";
		String expected = "‚ ‚¢‚¤";
		String result = StringUtil.leftBX(input, 6); // Assuming 6 bytes, 1 hira takes 2 bytes.
		assertEquals(expected, result);
	}

	@Test
	void testRightBX() {
		String input = "‚ ‚¢‚¤‚¦‚¨";
		String expected = "‚¤‚¦‚¨";
		String result = StringUtil.rightBX(input, 6); // Assuming 6 bytes, 1 hira takes 2 bytes.
		assertEquals(expected, result);
	}

	@Test
	void testAfterBX() {
		String input = "‚ ‚¢‚¤‚¦‚¨";
		String expected = "‚¦‚¨";
		String result = StringUtil.afterBX(input, 6); // Assuming 6 bytes, 1 hira takes 2 bytes.
		assertEquals(expected, result);
	}

	@Test
	void testCutBX() {
		String input = "‚ ‚¢‚¤‚¦‚¨";
		String expected = "‚¢";
		String result = StringUtil.cutBX(input, 3, 5); // Start: 3 bytes, End: 5 bytes
		assertEquals(expected, result);
	}

	@Test
	void testToHTMLEscapeStringList() {
		List<String> input = Arrays.asList("a", "b", "c");
		String expected = "a<>b<>c";
		String result = StringUtil.toHTMLEscapeString(input);
		assertEquals(expected, result);
	}

	@Test
	void testToListFromHTMLEscapeString() {
		String input = "a<>b<>c";
		List<String> expected = Arrays.asList("a", "b", "c");
		List<String> result = StringUtil.toListFromHTMLEscapeString(input);
		assertEquals(expected, result);
	}

	@Test
	//TODO:
	void testConvertPrm() {
		String input = "a'b_c%d";
		String expected = "a''b/_c/%d";
		String result = StringUtil.convertPrm(input);
		assertEquals(expected, result);
	}

	@Test
	//TODO:
	void testToURLEncodeString() throws UnsupportedEncodingException {
		byte[] input = "test".getBytes("ISO-8859-1");
		String expected = "test";
		String result = StringUtil.toURLEncodeString(input);
		assertEquals(expected, result);
	}

	@Test
	void testToURLDecodeBytes() throws UnsupportedEncodingException {
		String input = "test";
		byte[] expected = "test".getBytes("ISO-8859-1");
		byte[] result = StringUtil.toURLDecodeBytes(input);
		assertArrayEquals(expected, result);
	}

	@Test
	void testToJoinStringStringArray() {
		String[] input = { "one", "two", "three" };
		String expected = "onetwothree";
		String result = StringUtil.toJoinString(input);
		assertEquals(expected, result);
	}

	@Test
	void testToJoinStringList() {
		List<String> input = Arrays.asList("one", "two", "three");
		String expected = "onetwothree";
		String result = StringUtil.toJoinString(input);
		assertEquals(expected, result);
	}

	@Test
	void testJoin() {
		List<String> input = Arrays.asList("one", "two", "three");
		String split = ",";
		String expected = "one,two,three";
		String result = StringUtil.join(input, split);
		assertEquals(expected, result);
	}

	@Test
	void testGetParagraphString() {
		String input = "‚ ‚¢‚¤‚¦‚¨‚©‚«";
		List<String> expected = Arrays.asList("‚ ‚¢‚¤", "‚¦‚¨‚©", "‚«", "");
		List<String> result = StringUtil.getParagraphString(input, 6, 4);
		assertEquals(expected, result);
	}

	@Test
	void testSpaceFill() {
		String input = "test";
		String expected = "test    "; // Adjust length if needed
		String result = StringUtil.spaceFill(input, 8);
		assertEquals(expected, result);
	}

	@Test
	// TODO:
	void testFillHtmlSpace() {
		assertEquals("ABC&nbsp;&nbsp;&nbsp;&nbsp;", StringUtil.fillHtmlSpace("ABC", 7));
		assertEquals("ABCDE", StringUtil.fillHtmlSpace("ABCDE", 5));
		assertEquals("ABC&nbsp;&nbsp;", StringUtil.fillHtmlSpace("ABC", 5));
	}

	@Test
	void testFill() {
		assertEquals("ABC###", StringUtil.fill("ABC", 6, '#'));
		assertEquals("ABCDE", StringUtil.fill("ABCDE", 5, '#'));
		assertEquals("ABC###", StringUtil.fill("ABC", 6, '#'));
	}

	@Test
	void testFillLeft() {
		assertEquals("###ABC", StringUtil.fillLeft("ABC", 6, '#'));
		assertEquals("ABCDE", StringUtil.fillLeft("ABCDE", 5, '#'));
		assertEquals("##ABC", StringUtil.fillLeft("ABC", 5, '#'));
	}

	@Test
	void testTrimLeft() {
		assertEquals("ABC", StringUtil.trimLeft("###ABC", '#'));
		assertEquals("ABC", StringUtil.trimLeft("ABC", '#'));
		assertEquals("ABC", StringUtil.trimLeft("***ABC", '*'));
	}

	@Test
	void testContainsFullSize() {
		assertTrue(StringUtil.containsFullSize("ABC‚ "));
		assertFalse(StringUtil.containsFullSize("ABC"));
		assertFalse(StringUtil.containsFullSize("123"));
		assertFalse(StringUtil.containsFullSize(""));
		assertFalse(StringUtil.containsFullSize(null));
	}

	@Test
	void testIsHalfCharOrNumber() {
		assertTrue(StringUtil.isHalfCharOrNumber("ABC123"));
		assertFalse(StringUtil.isHalfCharOrNumber("ABC123‚ "));
		assertTrue(StringUtil.isHalfCharOrNumber("123"));
		assertTrue(StringUtil.isHalfCharOrNumber(""));
		assertTrue(StringUtil.isHalfCharOrNumber(null));
	}

	@Test
	void testAddDQuo() {
		assertEquals("\"ABC\"", StringUtil.addDQuo("ABC"));
		assertEquals("\"\"", StringUtil.addDQuo(""));
		assertEquals("\"\"", StringUtil.addDQuo(null));
	}

	@Test
	void testIsOver() {
		assertTrue(StringUtil.isOver("ABCDE", 4));
		assertFalse(StringUtil.isOver("ABCDE", 5));
		assertFalse(StringUtil.isOver("ABCDE", 6));
	}

	@Test
	void testIsBytesOver() {
		assertTrue(StringUtil.isBytesOver("ABCDE", 4));
		assertFalse(StringUtil.isBytesOver("ABCDE", 5));
		assertFalse(StringUtil.isBytesOver("ABCDE", 6));
	}

	@Test
	void testReplaceToUnderscore() {
		assertEquals("ABC_123", StringUtil.replaceToUnderscore("ABC@123"));
		assertEquals("ABC_123_", StringUtil.replaceToUnderscore("ABC@123#"));
		assertEquals("ABC123", StringUtil.replaceToUnderscore("ABC123"));
		assertEquals("", StringUtil.replaceToUnderscore(""));
	}

	@Test
	void testCreateUID() {
		String uid = StringUtil.createUID();
		assertNotNull(uid);
		assertEquals(36, uid.length());
		assertTrue(uid.matches("[a-f0-9\\-]{36}"));
	}

	@Test
	public void testArraycopy() {
		byte[] src = { 1, 2, 3, 4, 5, 6, 7, 8, 9, 10 };
		byte[] dst = new byte[10];

		// Test 1: Basic Copy
		StringUtil.arraycopy(src, 2, dst, 4, 4);
		assertArrayEquals(new byte[] { 0, 0, 0, 0, 3, 4, 5, 6, 0, 0 }, dst);

		// Test 2: Copy to the beginning of the destination array
		byte[] dst2 = new byte[10];
		StringUtil.arraycopy(src, 0, dst2, 0, 5);
		assertArrayEquals(new byte[] { 1, 2, 3, 4, 5, 0, 0, 0, 0, 0 }, dst2);

		// Test 3: Copy from the middle of the source array
		byte[] dst3 = new byte[10];
		StringUtil.arraycopy(src, 4, dst3, 3, 4);
		assertArrayEquals(new byte[] { 0, 0, 0, 5, 6, 7, 8, 0, 0, 0 }, dst3);

		// Test 4: Copy with boundary conditions (end of the source and destination arrays)
		byte[] dst4 = new byte[10];
		StringUtil.arraycopy(src, 5, dst4, 7, 3);
		assertArrayEquals(new byte[] { 0, 0, 0, 0, 0, 0, 0, 6, 7, 8 }, dst4);

		// Test 5: Copy with overlapping source and destination (same array)
		StringUtil.arraycopy(src, 2, src, 4, 4);
		assertArrayEquals(new byte[] { 1, 2, 3, 4, 3, 4, 5, 6, 9, 10 }, src);

		// Test 6: Source index out of bounds
		assertThrows(IllegalArgumentException.class, () -> {
			StringUtil.arraycopy(src, -1, dst, 0, 5);
		});

		// Test 7: Source position + length exceeds source array size
		assertThrows(IllegalArgumentException.class, () -> {
			StringUtil.arraycopy(src, 7, dst, 0, 5);
		});

		// Test 8: Destination index out of bounds
		assertThrows(IllegalArgumentException.class, () -> {
			StringUtil.arraycopy(src, 0, dst, -1, 5);
		});

		// Test 9: Destination position + length exceeds destination array size
		assertThrows(IllegalArgumentException.class, () -> {
			StringUtil.arraycopy(src, 0, dst, 6, 5);
		});
	}

	@Test
	void testSplitStringStringInt() {

		// Test 1: Basic split with limit
		String str1 = "one,two,three,four";
		String delimiter1 = ",";
		int limit1 = 3;
		String[] expected1 = { "one", "two", "three,four" };
		assertArrayEquals(expected1, StringUtil.splitLimit(str1, delimiter1, limit1));

		// Test 2: Limit is less than number of splits
		String str2 = "apple;banana;cherry";
		String delimiter2 = ";";
		int limit2 = 2;
		String[] expected2 = { "apple", "banana;cherry" };
		assertArrayEquals(expected2, StringUtil.splitLimit(str2, delimiter2, limit2));

		// Test 3: Limit is zero
		String str3 = "data";
		String delimiter3 = ",";
		int limit3 = 0;
		String[] expected3 = { "data" };
		assertArrayEquals(expected3, StringUtil.splitLimit(str3, delimiter3, limit3));

		// Test 4: Empty string with limit
		String str4 = "";
		String delimiter4 = ",";
		int limit4 = 3;
		String[] expected4 = { null, null, null };
		assertArrayEquals(expected4, StringUtil.splitLimit(str4, delimiter4, limit4));

		// Test 5: Null string with limit
		String str5 = null;
		String delimiter5 = ",";
		int limit5 = 3;
		String[] expected5 = { null, null, null };
		assertArrayEquals(expected5, StringUtil.splitLimit(str5, delimiter5, limit5));

		// Test 6: Null delimiter with limit
		String str6 = "one two three";
		String delimiter6 = null;
		int limit6 = 2;
		String[] expected6 = { "one two three", null };
		assertArrayEquals(expected6, StringUtil.splitLimit(str6, delimiter6, limit6));

		// Test 7: Limit greater than the number of delimiters
		String str7 = "a,b,c";
		String delimiter7 = ",";
		int limit7 = 5;
		String[] expected7 = { "a", "b", "c", null, null };
		assertArrayEquals(expected7, StringUtil.splitLimit(str7, delimiter7, limit7));

		// Test 8: Limit exactly matches the number of splits
		String str8 = "one|two|three";
		String delimiter8 = "|";
		int limit8 = 3;
		String[] expected8 = { "one", "two", "three" };
		assertArrayEquals(expected8, StringUtil.splitLimit(str8, delimiter8, limit8));
	}

	@Test
	public void testGetHtmlString() {
		// Test data
		Object[] data = { "Line 1", "Line 2", "Line 3" };

		// Test 1: Left alignment (default)
		int alignLeft = SwingConstants.LEFT;
		//TODO:
		String expectedLeft = "<html><div style=\"text-align:left;white-space:nowrap; \">Line 1<br>Line 2<br>Line 3<br></div></html>";
		assertEquals(expectedLeft, StringUtil.getHtmlString(alignLeft, data));

	}

	@Test
	public void testToArrayFromHTMLEscapeString() {

		// Test 1: HTML escape with null string representation
		String htmlStr2 = "Some&nbsp;Text<>NULL_STRING1<>Another&nbsp;Text";
		String[] expected2 = { "Some Text", null, "Another Text" };
		String[] actual = StringUtil.toArrayFromHTMLEscapeString(htmlStr2);
		for (int i = 0; i < htmlStr2.length(); i++) {
			System.out.println(expected2[i]);
			System.out.println(actual[i]);
			assertTrue(expected2[i].equals(actual[i]));
		}

		// Test 2: Basic HTML escape and split
		// TODO:Discrepancy
		String htmlStr1 = "Hello&nbsp;World<>Foo&nbsp;Bar<>Null&nbsp;String";
		String[] expected1 = { "Hello World", "Foo Bar", "Null String" };
		assertArrayEquals(expected1, StringUtil.toArrayFromHTMLEscapeString(htmlStr1));

		// Test 3: Empty HTML string
		String htmlStr3 = "";
		String[] expected3 = { "" };
		assertArrayEquals(expected3, StringUtil.toArrayFromHTMLEscapeString(htmlStr3));

		// Test 4: Null HTML string
		String htmlStr4 = null;
		String[] expected4 = { null };
		assertArrayEquals(expected4, StringUtil.toArrayFromHTMLEscapeString(htmlStr4));

		// Test 5: HTML string with extra spaces and new lines
		String htmlStr5 = " Line&nbsp;One <> Line&nbsp;Two <>  ";
		String[] expected5 = { "Line One", "Line Two", "" };
		assertArrayEquals(expected5, StringUtil.toArrayFromHTMLEscapeString(htmlStr5));

		// Test 6: HTML string with multiple delimiters
		String htmlStr6 = "Part1<>Part2<><>Part4";
		String[] expected6 = { "Part1", "Part2", "", "Part4" };
		assertArrayEquals(expected6, StringUtil.toArrayFromHTMLEscapeString(htmlStr6));

		// Test 7: HTML string with special characters
		String htmlStr7 = "Special&nbsp;chars&nbsp;&lt;&gt;&amp;<>New&nbsp;Line";
		String[] expected7 = { "Special chars <>&", "New Line" };
		assertArrayEquals(expected7, StringUtil.toArrayFromHTMLEscapeString(htmlStr7));
	}

	@Test
	public void testToHTMLEscapeString() {
		// Test 1: Basic input
		String[] input1 = { "Hello", "World" };
		String expected1 = "Hello<>World"; // Assuming escapeHtml doesn't change these strings
		assertEquals(expected1, StringUtil.toHTMLEscapeString(input1));
	}
}
