package jp.co.ais.trans.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import jp.co.ais.trans.common.except.TException;

class EncryptUtilTest {

	private static final String TEST_KEY = "testkey123";
	private static final String TEST_STRING = "Hello, World!";
	private static final String TEST_FILE = "test.txt";
	private static final String ENCRYPTED_FILE = "encrypted_test.txt";

	@Test
	public void testEncryptDecryptString() {
		String encrypted = EncryptUtil.encrypt(TEST_STRING);
		String decrypted = EncryptUtil.decrypt(encrypted);

		assertNotEquals(TEST_STRING, encrypted, "Encrypted string should not match the original");
		assertEquals(TEST_STRING, decrypted, "Decrypted string should match the original");
	}

	@Test
	public void testEncryptDecryptFileNullKey() throws IOException, TException {
		// Write the test string to a file
		try (FileOutputStream fos = new FileOutputStream(TEST_FILE)) {
			fos.write(TEST_STRING.getBytes());
		}

		// Encrypt the file with null key
		assertThrows(TException.class, () -> EncryptUtil.encryptProperty(TEST_FILE, ENCRYPTED_FILE, null));

	}

	@Test
	public void testEncryptDecryptEmptyString() {
		String encrypted = EncryptUtil.encrypt(TEST_KEY, "");
		String decrypted = EncryptUtil.decrypt(TEST_KEY, encrypted);
		assertEquals("", decrypted, "Decrypted empty string does not match the original.");
	}

	@Test
	//TODO:
	public void testEncryptDecryptSpecialCharacters() {
		String specialString = "Hello$";
		String encrypted = EncryptUtil.encrypt(TEST_KEY, specialString);
		String decrypted = EncryptUtil.decrypt(TEST_KEY, encrypted);
		assertEquals(specialString, decrypted, "Decrypted string with special characters does not match the original.");
	}

	@Test
	public void testEncryptDecryptFileNonExistent() {
		assertThrows(FileNotFoundException.class, () -> {
			try (FileInputStream fis = new FileInputStream("non_existent_file.txt")) {
				EncryptUtil.decryptProperty(fis);
			}
		});
	}

	@Test
	public void testEncryptDecryptInvalidKey() {
		String encrypted = EncryptUtil.encrypt("wrong_key", TEST_STRING);
		assertThrows(Exception.class, () -> EncryptUtil.decrypt("testkey123", encrypted));
	}

}
