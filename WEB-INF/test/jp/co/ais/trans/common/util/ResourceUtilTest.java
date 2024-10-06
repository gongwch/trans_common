package jp.co.ais.trans.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;

import org.junit.jupiter.api.Test;

class ResourceUtilTest {

	@Test
	void testGetResourcePath() {
		// テスト対象のクラスを指定
		Class<?> clazz = ResourceUtilTest.class;

		// 指定したクラスのリソースパスを取得
		String resourcePath = ResourceUtil.getResourcePath(clazz);

		// リソースパスが null でないことを確認
		assertNotNull(resourcePath, "リソースパスは null であってはいけません。");

		// リソースパスがクラス名を含むことを確認
		assertTrue(resourcePath.contains(clazz.getName().replace('.', '/') + ".class"),
				"リソースパスはクラス名を含むべきです。");
	}

	@Test
	void testConvertPath() {

		// テストクラスを指定
		Class<?> clazz = ResourceUtilTest.class;

		// リソースが存在するパスのテスト
		String existingPath = ResourceUtil.getResourcePath(clazz);
		String result = ResourceUtil.convertPath(clazz, existingPath);
		assertEquals(existingPath, result, "リソースが存在する場合、そのパスをそのまま返すべきです。");

	}

	@Test
	void testGetResourceAsFileClassString() {
		// テスト対象のクラスを指定
		Class<?> clazz = ResourceUtilTest.class;

		// 指定したクラスのリソースパスを取得
		String resourcePath = ResourceUtil.getResourcePath(clazz);
		// Test valid path
		File validFile = ResourceUtil.getResourceAsFile(UtilTest.class, resourcePath);
		assertNotNull(validFile, "File should not be null for valid path");
		assertTrue(validFile.exists(), "File should exist for valid path");

	}

	@Test
	void testGetResourceAsFileString() throws IOException {
		//TODO:TEST CASE NOT ENOUGH
		// テスト対象のクラスを指定
		Class<?> clazz = ResourceUtilTest.class;

		// 指定したクラスのリソースパスを取得
		String resourcePath = ResourceUtil.getResourcePath(clazz);
		// Test valid path
		File validFile = ResourceUtil.getResourceAsFile(resourcePath);
		assertNotNull(validFile, "File should not be null for valid path");
		assertTrue(validFile.exists(), "File should exist for valid path");

	}

	@Test
	void testGetImageClassString() throws IOException {
		// テスト対象のクラスを指定
		Class<?> clazz = ResourceUtilTest.class;

		// 指定したクラスのリソースパスを取得
		String resourcePath = ResourceUtil.getResourcePath(clazz).replaceFirst("/[^/]+$", "") + "/" + "version.png";
		// Test valid path
		ImageIcon validImageIcon = ResourceUtil.getImage(UtilTest.class, resourcePath);
		assertNotNull(validImageIcon, "ImageIcon should not be null for valid path");
		Image image = validImageIcon.getImage();
		assertNotNull(image, "Image should not be null for valid ImageIcon");

	}

	@Test
	void testGetImageString() {

		// テスト対象のクラスを指定
		Class<?> clazz = ResourceUtilTest.class;

		// 指定したクラスのリソースパスを取得
		String resourcePath = ResourceUtil.getResourcePath(clazz).replaceFirst("/[^/]+$", "") + "/" + "version.png";
		// Test valid path
		BufferedImage validImage = ResourceUtil.getImage(resourcePath);
		assertNotNull(validImage, "ImageIcon should not be null for valid path");
		assertTrue(validImage.getWidth() > 0 && validImage.getHeight() > 0,
				"Image dimensions should be greater than zero for valid image");

		// Test invaild path
		BufferedImage invalidImage = ResourceUtil.getImage("");
		assertNull(invalidImage, "ImageIcon should not be null for valid path");

	}

	@Test
	void testGetIcon() {
		// テスト対象のクラスを指定
		Class<?> clazz = ResourceUtilTest.class;

		// 指定したクラスのリソースパスを取得
		String resourcePath = ResourceUtil.getResourcePath(clazz).replaceFirst("/[^/]+$", "") + "/" + "version.png";
		// Valid Path Test
		Icon icon = ResourceUtil.getIcon(resourcePath);
		assertNotNull(icon, "Icon should not be null for valid path");
		assertTrue(icon instanceof ImageIcon, "Icon should be an instance of ImageIcon for valid path");
		ImageIcon imageIcon = (ImageIcon) icon;
		assertNotNull(imageIcon.getImage(), "Image within the Icon should not be null for valid image");
		assertTrue(imageIcon.getImage().getWidth(null) > 0 && imageIcon.getImage().getHeight(null) > 0,
				"Image dimensions should be greater than zero for valid image");

	}

	@Test
	void testGetResourceString() {
		// テスト対象のクラスを指定
		Class<?> clazz = ResourceUtilTest.class;

		// 指定したクラスのリソースパスを取得
		String resourcePath = ResourceUtil.getResourcePath(clazz).replaceFirst("/[^/]+$", "") + "/" + "version.png";
		// Valid Path Test
		URL url = ResourceUtil.getResource(resourcePath);
		assertNotNull(url, "URL should not be null for a valid path");
	}

	@Test
	void testGetResourceClassString() {
		// テスト対象のクラスを指定
		Class<?> clazz = ResourceUtilTest.class;

		// 指定したクラスのリソースパスを取得
		String resourcePath = ResourceUtil.getResourcePath(clazz).replaceFirst("/[^/]+$", "") + "/" + "version.png";
		// Valid Path Test
		URL url = ResourceUtil.getResource(clazz, resourcePath);
		assertNotNull(url, "URL should not be null for a valid path");
	}

	@Test
	void testGetResourceAsStreamString() {
		//テスト対象のクラスを指定
		Class<?> clazz = ResourceUtilTest.class;
		// 指定したクラスのリソースパスを取得
		String resourcePath = ResourceUtil.getResourcePath(clazz).replaceFirst("/[^/]+$", "") + "/" + "version.png";
		// Valid Path Test
		InputStream imputStream = ResourceUtil.getResourceAsStream(clazz, resourcePath);
		assertNotNull(imputStream, "URL should not be null for a valid path");

	}

	@Test
	void testGetResourceAsStreamClassString() {
		//テスト対象のクラスを指定
		Class<?> clazz = ResourceUtilTest.class;
		// 指定したクラスのリソースパスを取得
		String resourcePath = ResourceUtil.getResourcePath(clazz).replaceFirst("/[^/]+$", "") + "/" + "version.png";
		// Valid Path Test
		InputStream imputStream = ResourceUtil.getResourceAsStream(resourcePath);
		assertNotNull(imputStream, "URL should not be null for a valid path");

	}

	@Test
	void testToImage() {
		//Case 1: Valid Image Data
		try {
			BufferedImage img = new BufferedImage(1, 1, BufferedImage.TYPE_INT_RGB);
			img.setRGB(0, 0, 0xff0000); // Set the pixel to red

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(img, "png", baos);
			byte[] imageBytes = baos.toByteArray();

			BufferedImage result = ResourceUtil.toImage(imageBytes);
			assertNotNull(result, "Image should not be null");
			assertEquals(1, result.getWidth(), "Width should be 1");
			assertEquals(1, result.getHeight(), "Height should be 1");
			assertTrue(areArraysEqual(intToRGB(0xff0000), intToRGB(result.getRGB(0, 0))),
					"Pixel color should be red");//TODO:
		} catch (IOException e) {
			fail("Failed to create valid image data");
		}

		// Case 2: Empty Byte Array
		BufferedImage result = ResourceUtil.toImage(new byte[0]);
		assertNull(result, "Result should be null for an empty byte array");
	}

	@Test
	void testCanWrite() throws FileNotFoundException, IOException {
		// ケース1: ファイルが存在しない
		String tempFileName = "tempFileNonExistent.txt";
		assertTrue(ResourceUtil.canWrite(tempFileName), "新規ファイルは書き込み可能であるべきです。");

		// クリーンアップ
		File tempFile = new File(tempFileName);
		if (tempFile.exists()) {
			tempFile.delete();
		}

		// ケース2: ファイルが存在し、書き込み可能
		tempFileName = "tempFileWritable.txt";
		try (FileOutputStream fos = new FileOutputStream(tempFileName)) {
			fos.write("Test".getBytes());
		}

		assertTrue(ResourceUtil.canWrite(tempFileName), "既存のファイルは書き込み可能であるべきです。");

		// クリーンアップ
		tempFile = new File(tempFileName);
		if (tempFile.exists()) {
			tempFile.delete();
		}

		// ケース3: ファイルが存在し、書き込み不可能（読み取り専用）
		tempFileName = "tempFileReadOnly.txt";
		try (FileOutputStream fos = new FileOutputStream(tempFileName)) {
			fos.write("Test".getBytes());
		}

		// ファイルを読み取り専用に設定
		File readOnlyFile = new File(tempFileName);
		readOnlyFile.setWritable(false);

		assertFalse(ResourceUtil.canWrite(tempFileName), "読み取り専用のファイルは書き込み不可能であるべきです。");

		// クリーンアップ
		readOnlyFile.setWritable(true); // 書き込み権限を復元
		if (readOnlyFile.exists()) {
			readOnlyFile.delete();
		}
	}

	@Test
	void testWriteBinary() throws IOException {
		// テスト用のファイル名とデータ
		String fileName = "testWriteBinary.dat";
		byte[] data = "test data".getBytes();

		// バイナリデータを書き込む
		ResourceUtil.writeBinary(fileName, data);

		// ファイルの存在確認
		File file = new File(fileName);
		assertTrue(file.exists(), "ファイルが作成されているべきです。");

		// ファイルからデータを読み込み、内容を確認
		byte[] readData = Files.readAllBytes(file.toPath());
		assertArrayEquals(data, readData, "ファイルに書き込まれたデータが正しいべきです。");

		// クリーンアップ
		if (file.exists()) {
			file.delete();
		}
	}

	@Test
	void testCloseInputStream() {
		// Test normal InputStream
		try (InputStream normalStream = new ByteArrayInputStream(new byte[0])) {
			// このメソッドが正常にクローズできることを確認
			ResourceUtil.closeInputStream(normalStream);
		} catch (Exception ex) {
			fail("正常なInputStreamをクローズする際に例外が発生しました。");
		}

		// Test InputStream that throws an IOException on close
		InputStream faultyStream = new InputStream() {
			@Override
			public int read() throws IOException {
				return -1;
			}

			@Override
			public void close() throws IOException {
				throw new IOException("Close error");
			}
		};
		//TODO:
		// 標準出力をキャプチャするためのByteArrayOutputStreamを作成
		try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
				java.io.PrintStream originalOut = System.out;
				java.io.PrintStream testOut = new java.io.PrintStream(baos)) {

			//System.setOut(testOut); // 標準出力をキャプチャ
			ResourceUtil.closeInputStream(faultyStream);

			// 標準出力からエラーメッセージを取得して確認
			String output = baos.toString();
			assertTrue(output.contains("Close error"), "エラーメッセージが標準出力に含まれているべきです。");

			System.setOut(originalOut); // 標準出力を元に戻す
		} catch (IOException ex) {
			fail("例外処理中にエラーが発生しました。");
		}
	}

	@Test
	void testCloseOutputStream() throws IOException {
		// Test normal OutputStream
		try (OutputStream outStream = new ByteArrayOutputStream()) {
			ResourceUtil.closeOutputStream(outStream);
		}

		// Test OutputStream that throws an IOException on close
		OutputStream faultyStream = new OutputStream() {

			@Override
			public void close() throws IOException {
				throw new IOException("OutputStream Close Error");
			}

			@Override
			public void write(int b) throws IOException {
				// TODO 自動生成されたメソッド・スタブ

			}
		};
		//TODO:
		// 標準出力をキャプチャするためのByteArrayOutputStreamを作成
		try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
				java.io.PrintStream originalOut = System.out;
				java.io.PrintStream testOut = new java.io.PrintStream(baos)) {
			System.setOut(testOut);
			ResourceUtil.closeOutputStream(faultyStream);
			// 標準出力からエラーメッセージを取得して確認
			String output = baos.toString();
			assertTrue(output.contains("OutputStream Close Error"), "エラーメッセージが標準出力に含まれているべきです。");

			//System.setOut(originalOut); // 標準出力を元に戻す
		} catch (IOException ex) {
			fail("例外処理中にエラーが発生しました。");
		}
	}

	@Test
	void testClosePrintWriter() throws IOException {
		// Test normal PrintWriter
		try (PrintWriter normalWriter = new PrintWriter(new ByteArrayOutputStream())) {
			// このメソッドが正常にクローズできることを確認
			ResourceUtil.closePrintWriter(normalWriter);
		} catch (Exception ex) {
			fail("正常なPrintWriterをクローズする際に例外が発生しました。");
		}

		// Test PrintWriter that throws an IOException on close
		PrintWriter faultyWriter = new PrintWriter(new ByteArrayOutputStream()) {
			@Override
			public void close() {
				throw new RuntimeException("PrintWriter Close error");
			}
		};

		// 標準出力をキャプチャするためのByteArrayOutputStreamを作成
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				java.io.PrintStream originalOut = System.out;
				java.io.PrintStream testOut = new java.io.PrintStream(baos)) {

			System.setOut(testOut); // 標準出力をキャプチャ
			ResourceUtil.closePrintWriter(faultyWriter); //"PrintWriter Close error"

		} catch (RuntimeException ex) {
			// 標準出力からエラーメッセージを取得して確認
			String output = ex.toString();
			assertTrue(output.contains("Close error"), "エラーメッセージが標準出力に含まれているべきです。");

		}
	}

	@Test
	void testZipBinary() throws IOException {
		int BUFFER_SIZE = 1024;
		// テスト用のバイナリデータ
		byte[] inputBytes = "This is a test.".getBytes();
		String fileName = "test.txt";

		// メソッド呼び出し
		byte[] zipBytes = ResourceUtil.zipBinary(fileName, inputBytes);

		// ZIPファイルの内容を確認
		try (ByteArrayInputStream bais = new ByteArrayInputStream(zipBytes);
				ZipInputStream zis = new ZipInputStream(bais)) {

			ZipEntry entry = zis.getNextEntry();
			assertNotNull(entry, "ZIPエントリが存在するべきです。");
			assertEquals(fileName, entry.getName(), "ZIPエントリの名前が一致しません。");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int len;

			while ((len = zis.read(buffer)) > 0) {
				baos.write(buffer, 0, len);
			}

			byte[] outputBytes = baos.toByteArray();
			assertArrayEquals(inputBytes, outputBytes, "圧縮されたデータが元のデータと一致しません。");

			zis.closeEntry();
		}
	}

	@Test
	void testWriteBinaryInZip() throws IOException {
		// テスト用のバイナリデータとファイル名
		byte[] inputBytes = "This is a test.".getBytes();
		String fileName = "test.txt";

		// ZIPファイルを作成
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (ZipOutputStream zos = new ZipOutputStream(bos)) {
			ZipEntry entry = new ZipEntry(fileName);
			zos.putNextEntry(entry);
			zos.write(inputBytes);
			zos.closeEntry();
		}

		byte[] zipBytes = bos.toByteArray();

		// ZIPファイルからファイルを解凍して書き込み
		File outputFile = new File("test.txt");
		try {
			ResourceUtil.writeBinaryInZip(fileName, zipBytes);

			// ファイルの内容を確認
			byte[] outputBytes = Files.readAllBytes(outputFile.toPath());
			assertArrayEquals(inputBytes, outputBytes, "圧縮されたデータが元のデータと一致しません。");
		} finally {
			// 後片付け
			if (outputFile.exists()) {
				outputFile.delete();
			}
		}
	}

	@Test
	void testToBinaryInZip() throws IOException {
		// テスト用のバイナリデータとファイル名
		byte[] inputBytes = "This is a test.".getBytes();
		String fileName = "test.txt";

		// ZIPファイルを作成
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (ZipOutputStream zos = new ZipOutputStream(bos)) {
			ZipEntry entry = new ZipEntry(fileName);
			zos.putNextEntry(entry);
			zos.write(inputBytes);
			zos.closeEntry();
		}

		byte[] zipBytes = bos.toByteArray();

		// ZIPを解凍してバイナリデータを取得
		byte[] resultBytes = ResourceUtil.toBinaryInZip(zipBytes);

		// 結果が期待通りか確認
		assertNotNull(resultBytes, "The result should not be null");
		assertArrayEquals(inputBytes, resultBytes, "The extracted bytes should match the input bytes");
	}

	@Test
	void testIsFilePathExists() throws IOException {
		String TEST_FILE_NAME = "testFile.txt";
		String TEST_DIR_NAME = "testDir";
		// テスト用のファイルとディレクトリを作成
		File testFile = new File(TEST_FILE_NAME);
		File testDir = new File(TEST_DIR_NAME);
		boolean fileCreated = testFile.createNewFile();
		boolean dirCreated = testDir.mkdir();

		try {
			// ファイルが存在する場合
			assertTrue(fileCreated, "Test file should be created");
			assertTrue(ResourceUtil.isFilePathExists(TEST_FILE_NAME), "The file should exist");

			// 存在しないファイルのチェック
			assertFalse(ResourceUtil.isFilePathExists("nonExistentFile.txt"), "The file should not exist");

			// ディレクトリが存在する場合
			assertTrue(dirCreated, "Test directory should be created");
			assertTrue(ResourceUtil.isFilePathExists(TEST_DIR_NAME), "The directory should exist");

			// 存在しないディレクトリのチェック
			assertFalse(ResourceUtil.isFilePathExists("nonExistentDir"), "The directory should not exist");

		} finally {
			// テスト後にファイルとディレクトリを削除
			if (testFile.exists()) {
				testFile.delete();
			}
			if (testDir.exists()) {
				testDir.delete();
			}
		}
	}

	@Test
	void testGetExtensionFile() {
		// テスト用ファイルを作成（ファイルの存在は実際には必要ない）
		File fileWithExtension = new File("example.txt");
		File fileWithoutExtension = new File("example");
		File fileWithMultipleDots = new File("archive.tar.gz");
		File fileWithHiddenExtension = new File(".hiddenfile.ext");
		File fileWithNoExtension = new File(".hiddenfile");

		// 拡張子の取得と確認
		assertEquals("txt", ResourceUtil.getExtension(fileWithExtension), "The extension should be 'txt'");
		assertEquals("", ResourceUtil.getExtension(fileWithoutExtension),
				"The file without extension should return an empty string");
		assertEquals("gz", ResourceUtil.getExtension(fileWithMultipleDots),
				"The extension should be 'gz' (last one in the name)");
		assertEquals("ext", ResourceUtil.getExtension(fileWithHiddenExtension), "The extension should be 'ext'");
		assertEquals("", ResourceUtil.getExtension(fileWithNoExtension),
				"The file with no extension should return an empty string");
	}

	@Test
	void testReadBinarry() throws FileNotFoundException, IOException {
		// テスト用のファイルを作成
		String testFileName = "testfile.bin";
		byte[] testData = "This is a test.".getBytes();
		try (FileOutputStream fos = new FileOutputStream(testFileName)) {
			fos.write(testData);
		}

		// メソッドを呼び出して結果を取得
		byte[] result = ResourceUtil.readBinarry(testFileName);

		// 結果を確認
		assertArrayEquals(testData, result, "The binary data read from the file should match the written data");

		// テスト用ファイルを削除
		Files.deleteIfExists(new File(testFileName).toPath());
	}

	@Test
	void testMakeTextFile() throws IOException {
		// テスト用のファイル名と内容
		String testFileName = "testfile.txt";
		String testContent = "This is a test content.";

		// テスト用ファイルを作成
		ResourceUtil.makeTextFile(testFileName, testContent);

		// ファイルが存在することを確認
		Path filePath = Paths.get(testFileName);
		assertTrue(Files.exists(filePath), "The file should exist");

		// ファイルの内容を読み取って検証
		List<String> content = Files.readAllLines(filePath);
		assertEquals(testContent, content.get(0), "The content of the file should match the expected content");

		// テスト用ファイルを削除
		Files.deleteIfExists(filePath);
	}

	@Test
	void testToObject() throws IOException {
		// テスト用オブジェクトの作成
		Map<String, String> testObject = new HashMap<>();
		testObject.put("key1", "value1");
		testObject.put("key2", "value2");

		// オブジェクトをバイナリ形式に変換
		byte[] serializedObject = serializeObject(testObject);

		// バイナリからオブジェクトに変換
		Object deserializedObject = ResourceUtil.toObject(serializedObject);

		// 結果を検証
		assertTrue(deserializedObject instanceof Map, "The deserialized object should be an instance of Map");
		@SuppressWarnings("unchecked")
		Map<String, String> resultMap = (Map<String, String>) deserializedObject;
		assertEquals(testObject, resultMap, "The deserialized object should match the original object");
	}

	private byte[] serializeObject(Object obj) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (ObjectOutputStream oos = new ObjectOutputStream(bos)) {
			oos.writeObject(obj);
		}
		return bos.toByteArray();
	}

	@Test
	public void testToBinarry() throws IOException, ClassNotFoundException {
		// テスト用オブジェクトの作成
		Map<String, String> testObject = new HashMap<>();
		testObject.put("key1", "value1");
		testObject.put("key2", "value2");

		// オブジェクトをバイナリ形式に変換
		byte[] binaryData = ResourceUtil.toBinarry(testObject);

		// バイナリからオブジェクトに変換
		Object deserializedObject = deserializeObject(binaryData);

		// 結果を検証
		assertTrue(deserializedObject instanceof Map, "The deserialized object should be an instance of Map");
		@SuppressWarnings("unchecked")
		Map<String, String> resultMap = (Map<String, String>) deserializedObject;
		assertEquals(testObject, resultMap, "The deserialized object should match the original object");
	}

	private Object deserializeObject(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		try (ObjectInputStream ois = new ObjectInputStream(bis)) {
			return ois.readObject();
		}
	}

	@Test
	void testGetExtensionString() {
		// テスト対象のファイル名と期待される拡張子
		String fileName = "example.txt";
		String expectedExtension = "txt";

		// メソッド呼び出し
		String actualExtension = ResourceUtil.getExtension(fileName);

		// 結果の確認
		assertEquals(expectedExtension, actualExtension, "拡張子が正しく取得できるべきです。");
	}

	protected static int[] intToRGB(int color) {
		// Extract the red, green, and blue components
		int red = (color >> 16) & 0xFF;
		int green = (color >> 8) & 0xFF;
		int blue = color & 0xFF;

		// Return the RGB components as an array
		return new int[] { red, green, blue };
	}

	public static boolean areArraysEqual(int[] array1, int[] array2) {
		// Check if both arrays are the same instance
		if (array1 == array2) {
			return true;
		}

		// Check if either array is null
		if (array1 == null || array2 == null) {
			return false;
		}

		// Check if the arrays have the same length
		if (array1.length != array2.length) {
			return false;
		}

		// Compare each element of the arrays
		for (int i = 0; i < array1.length; i++) {
			if (array1[i] != array2[i]) {
				return false;
			}
		}

		// Arrays are equal
		return true;
	}
}
