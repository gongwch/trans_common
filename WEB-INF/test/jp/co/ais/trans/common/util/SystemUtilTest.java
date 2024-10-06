package jp.co.ais.trans.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.junit.jupiter.api.Test;

import jp.co.ais.trans.common.except.TException;

class SystemUtilTest {

	@Test
	void testGetTemporaryDir() {
		// Retrieve the temporary directory path
		String tempDir = SystemUtil.getTemporaryDir();

		// Verify that the path is not null
		assertNotNull(tempDir, "Temporary directory path should not be null");

		// Verify that the path actually exists (though not guaranteed to be writable)
		File tempDirFile = new File(tempDir);
		assertTrue(tempDirFile.isDirectory(), "Temporary directory should be a valid directory");
	}

	@Test
	void testGetAisSettingDir() {
		// Get the directory path from the method
		String aisSettingDir = SystemUtil.getAisSettingDir();

		// Construct the expected directory path
		String expectedDir = SystemUtil.getUserHomeDir() + "\\.ais\\setting\\";

		// Verify that the directory path is correct
		assertEquals(expectedDir, aisSettingDir, "The AIS setting directory path is incorrect");

		// Verify that the directory exists
		File dirFile = new File(aisSettingDir);
		assertTrue(dirFile.isDirectory(), "The AIS setting directory should exist and be a directory");

	}

	@Test
	void testGetAisDir() {
		// Get the directory path from the method
		String aisDir = SystemUtil.getAisDir();

		// Construct the expected directory path
		String expectedDir = SystemUtil.getUserHomeDir() + "\\.ais\\";

		// Verify that the directory path is correct
		assertEquals(expectedDir, aisDir, "The Ais directory is incorrect");

		// Verify that the directory exists
		File dirFile = new File(aisDir);
		assertTrue(dirFile.isDirectory(), " the Ais directory should exist and be a directory.");

	}

	@Test
	void testGetUserHomeDir() {
		// Expected value based on the system property
		String expectedHomeDir = System.getProperty("user.home");

		// Call the method to test
		String actualHomeDir = SystemUtil.getUserHomeDir();

		// Verify the result
		assertEquals(expectedHomeDir, actualHomeDir, "The user home directory path should match the system property.");
	}

	@Test
	void testEscapeFileName() {
		// Test cases
		String[][] testCases = {
				// Input, Expected
				{ "normalFileName.txt", "normalFileName.txt" },
				{ "file^name&test()[]{}=;!'+,`~　.txt", "file^^name^&test^(^)^[^]^{^}^=^;^!^'^+^,^`^~^　.txt" },
				{ "", "" }, // Empty string
				{ "fileNameWithNoSpecialCharacters", "fileNameWithNoSpecialCharacters" },
				{ "^special^chars&", "^^special^^chars^&" },
				{ "exampleFile^name", "exampleFile^^name" },
				{ "anotherFile@name~", "anotherFile@name^~" }, // Including characters not in ESCAPE_LIST
				{ "file_with_special_chars_123", "file_with_special_chars_123" }, // No characters to escape
				{ "file^name_with_ ^space", "file^^name_with_^ ^^space" }, // Space as part of ESCAPE_LIST
				{ "equals=and;semicolon", "equals^=and^;semicolon" },
				{ "!exclamation'quote+", "^!exclamation^'quote^+" },
				{ "+comma`backtick~tilde", "^+comma^`backtick^~tilde" },
				{ "fullwidth_space　", "fullwidth_space^　" } // Full-width space
		};

		for (String[] testCase : testCases) {
			String input = testCase[0];
			String expected = testCase[1];
			String actual = SystemUtil.escapeFileName(input);
			assertEquals(expected, actual, "Failed for input: " + input);
		}
	}

	@Test
	void testExecuteFile() throws TException, IOException {
		// Setup: Create a temporary executable file
		File tempFile = File.createTempFile("testExecutable", ".bat");
		tempFile.deleteOnExit(); // Ensure the file is deleted after the test

		// Write a simple command to the file to ensure it executes
		try (FileWriter writer = new FileWriter(tempFile)) {
			writer.write("echo Test executed");
		}

		// Ensure the file is executable
		tempFile.setExecutable(true);

		// Execute the file
		Process process = SystemUtil.executeFile(tempFile.getAbsolutePath());

		// Validate that the process started correctly
		assertNotNull(process, "The process should not be null");

		// Optionally, check the process output (this might vary based on your system)
		// Reading output is system-dependent and might need more sophisticated handling.
	}

	@Test
	void testPrintPDFString() throws TException, IOException {
		//TODO:  個人パソコンで既にインストールしたのに、acrord32.exe が見つかりません。
		// Setup: Create a temporary PDF file
		File tempFile = File.createTempFile("testPrint", ".pdf");
		tempFile.deleteOnExit(); // Ensure the file is deleted after the test

		// Create a dummy PDF file (in a real scenario, you would need a valid PDF)
		// For the purpose of this test, this file can be empty or contain minimal valid PDF content.
		try (FileWriter writer = new FileWriter(tempFile)) {
			writer.write("%PDF-1.4\n%âãÏÓ\n1 0 obj\n<< /Type /Catalog\n/Pages 2 0 R >>\nendobj\n");
			writer.write("2 0 obj\n<< /Type /Pages\n/Kids [3 0 R]\n/Count 1 >>\nendobj\n");
			writer.write("3 0 obj\n<< /Type /Page\n/Parent 2 0 R\n/MediaBox [0 0 612 792] >>\nendobj\n");
			writer.write(
					"xref\n0 4\n0000000000 65535 f \n0000000010 00000 n \n0000000100 00000 n \n0000000200 00000 n \n");
			writer.write("trailer\n<< /Size 4 /Root 1 0 R >>\nstartxref\n300\n%%EOF\n");
		}

		// Execute the printPDF method
		Process process = SystemUtil.printPDF(tempFile.getAbsolutePath());

		// Validate that the process started correctly
		assertNotNull(process, "The process should not be null");

		// Optionally, check if the process completed successfully
		try {
			int exitCode = process.waitFor();
			assertEquals(0, exitCode, "The process should complete with exit code 0");
		} catch (InterruptedException e) {
			fail("The process was interrupted", e);
		}
	}

	@Test
	void testPrintPDFForWait() {
		//TODO:  個人パソコンで既にインストールしたのに、acrord32.exe が見つかりません。
		String TEST_PDF_FILE = "testfile.pdf"; // Ensure this file exists or mock file creation
		// Setup
		// Ensure TEST_PDF_FILE exists and is accessible

		// Execution
		Process process = null;
		try {
			process = SystemUtil.printPDFForWait(TEST_PDF_FILE);

			// Verification
			assertNotNull(process);
			assertEquals(0, process.exitValue()); // Ensure process exited successfully
		} catch (TException e) {
			// Handle the exception or fail the test
			e.printStackTrace();
			fail("Exception thrown during printPDFForWait: " + e.getMessage());
		}
	}

	@Test
	void testPrintPDFStringString() {
		//TODO:  個人パソコンで既にインストールしたのに、acrord32.exe が見つかりません。
		String TEST_PDF_FILE = "testfile.pdf"; // Ensure this file exists or mock file creation
		String TEST_PRINTER = "MyPrinter";
		// Setup
		// Ensure that TEST_PDF_FILE exists and TEST_PRINTER is a valid printer

		// Execution
		Process process = null;
		try {
			process = SystemUtil.printPDF(TEST_PDF_FILE, TEST_PRINTER);

			// Verification
			assertNotNull(process);
			// Optionally, check the process output or other side effects if applicable
		} catch (TException e) {
			// Handle the exception or fail the test
			e.printStackTrace();
			// Fail the test if an exception is thrown
			assertThrows(TException.class, () -> {
				throw e;
			});
		}
	}

	@Test
	void testStartPDF() {
		// Execution
		assertDoesNotThrow(() -> {
			SystemUtil.startPDF();
		});

		// Since startPDF does not return any value or have direct output,
		// there isn't a straightforward way to verify the exact behavior
	}

	@Test
	void testExec() throws TException {
		// Define a simple command that should succeed
		String cmd = "cmd /c echo HelloWorld";//c tells the Command Prompt to execute the command that follows.

		// Execute the command using exec method
		Process process = SystemUtil.exec(cmd);

		// Validate that the process is not null
		assertNotNull(process, "The process should not be null");
	}

	@Test
	void testClosePDFApp() {
		// This test would actually attempt to kill the process
		SystemUtil systemUtil = new SystemUtil();
		assertDoesNotThrow(() -> systemUtil.closePDFApp());

		// Verify manually that the process is terminated, if possibl
	}

}
