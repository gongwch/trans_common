package jp.co.ais.trans.common.util;

import org.junit.jupiter.api.Test;

class UTF8EncryptUtilTest {

	@Test
	public void testDecrypt() {
		String originalString = "testString";
		String encryptedString = UTF8EncryptUtil.encrypt(originalString);
		String decryptedString = UTF8EncryptUtil.decrypt(encryptedString);

		assertEquals(originalString, decryptedString, "Decrypted string should match the original string");
	}

	@Test
	public void testEncrypt() {
		String originalString = "testString";
		String encryptedString = UTF8EncryptUtil.encrypt(originalString);
		assertNotNull("Encrypted string should not be null", encryptedString);
	}

	@Test
	public void testGetArray() {
		String[] originalArray = { "item1", "item2" };
		String token = UTF8EncryptUtil.getToken(originalArray);

		// Convert the token back to an array and verify the contents
		String[] resultArray = UTF8EncryptUtil.getArray(token);
		assertArrayEquals(originalArray, resultArray);
	}

	@Test
	public void testGetToken() {
		String[] inputArray = { "value1", "value2" };
		String token = UTF8EncryptUtil.getToken(inputArray);
		assertNotNull("Token should not be null", token);

		// Decrypt the token and verify it matches the original array
		String[] decryptedArray = UTF8EncryptUtil.getArray(token);
		assertEquals(inputArray.length, decryptedArray.length);
		for (int i = 0; i < inputArray.length; i++) {
			assertEquals(inputArray[i], decryptedArray[i], "Array element should match");
		}
	}
}
