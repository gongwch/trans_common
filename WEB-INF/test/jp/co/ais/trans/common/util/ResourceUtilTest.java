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
		// �e�X�g�Ώۂ̃N���X���w��
		Class<?> clazz = ResourceUtilTest.class;

		// �w�肵���N���X�̃��\�[�X�p�X���擾
		String resourcePath = ResourceUtil.getResourcePath(clazz);

		// ���\�[�X�p�X�� null �łȂ����Ƃ��m�F
		assertNotNull(resourcePath, "���\�[�X�p�X�� null �ł����Ă͂����܂���B");

		// ���\�[�X�p�X���N���X�����܂ނ��Ƃ��m�F
		assertTrue(resourcePath.contains(clazz.getName().replace('.', '/') + ".class"),
				"���\�[�X�p�X�̓N���X�����܂ނׂ��ł��B");
	}

	@Test
	void testConvertPath() {

		// �e�X�g�N���X���w��
		Class<?> clazz = ResourceUtilTest.class;

		// ���\�[�X�����݂���p�X�̃e�X�g
		String existingPath = ResourceUtil.getResourcePath(clazz);
		String result = ResourceUtil.convertPath(clazz, existingPath);
		assertEquals(existingPath, result, "���\�[�X�����݂���ꍇ�A���̃p�X�����̂܂ܕԂ��ׂ��ł��B");

	}

	@Test
	void testGetResourceAsFileClassString() {
		// �e�X�g�Ώۂ̃N���X���w��
		Class<?> clazz = ResourceUtilTest.class;

		// �w�肵���N���X�̃��\�[�X�p�X���擾
		String resourcePath = ResourceUtil.getResourcePath(clazz);
		// Test valid path
		File validFile = ResourceUtil.getResourceAsFile(UtilTest.class, resourcePath);
		assertNotNull(validFile, "File should not be null for valid path");
		assertTrue(validFile.exists(), "File should exist for valid path");

	}

	@Test
	void testGetResourceAsFileString() throws IOException {
		//TODO:TEST CASE NOT ENOUGH
		// �e�X�g�Ώۂ̃N���X���w��
		Class<?> clazz = ResourceUtilTest.class;

		// �w�肵���N���X�̃��\�[�X�p�X���擾
		String resourcePath = ResourceUtil.getResourcePath(clazz);
		// Test valid path
		File validFile = ResourceUtil.getResourceAsFile(resourcePath);
		assertNotNull(validFile, "File should not be null for valid path");
		assertTrue(validFile.exists(), "File should exist for valid path");

	}

	@Test
	void testGetImageClassString() throws IOException {
		// �e�X�g�Ώۂ̃N���X���w��
		Class<?> clazz = ResourceUtilTest.class;

		// �w�肵���N���X�̃��\�[�X�p�X���擾
		String resourcePath = ResourceUtil.getResourcePath(clazz).replaceFirst("/[^/]+$", "") + "/" + "version.png";
		// Test valid path
		ImageIcon validImageIcon = ResourceUtil.getImage(UtilTest.class, resourcePath);
		assertNotNull(validImageIcon, "ImageIcon should not be null for valid path");
		Image image = validImageIcon.getImage();
		assertNotNull(image, "Image should not be null for valid ImageIcon");

	}

	@Test
	void testGetImageString() {

		// �e�X�g�Ώۂ̃N���X���w��
		Class<?> clazz = ResourceUtilTest.class;

		// �w�肵���N���X�̃��\�[�X�p�X���擾
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
		// �e�X�g�Ώۂ̃N���X���w��
		Class<?> clazz = ResourceUtilTest.class;

		// �w�肵���N���X�̃��\�[�X�p�X���擾
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
		// �e�X�g�Ώۂ̃N���X���w��
		Class<?> clazz = ResourceUtilTest.class;

		// �w�肵���N���X�̃��\�[�X�p�X���擾
		String resourcePath = ResourceUtil.getResourcePath(clazz).replaceFirst("/[^/]+$", "") + "/" + "version.png";
		// Valid Path Test
		URL url = ResourceUtil.getResource(resourcePath);
		assertNotNull(url, "URL should not be null for a valid path");
	}

	@Test
	void testGetResourceClassString() {
		// �e�X�g�Ώۂ̃N���X���w��
		Class<?> clazz = ResourceUtilTest.class;

		// �w�肵���N���X�̃��\�[�X�p�X���擾
		String resourcePath = ResourceUtil.getResourcePath(clazz).replaceFirst("/[^/]+$", "") + "/" + "version.png";
		// Valid Path Test
		URL url = ResourceUtil.getResource(clazz, resourcePath);
		assertNotNull(url, "URL should not be null for a valid path");
	}

	@Test
	void testGetResourceAsStreamString() {
		//�e�X�g�Ώۂ̃N���X���w��
		Class<?> clazz = ResourceUtilTest.class;
		// �w�肵���N���X�̃��\�[�X�p�X���擾
		String resourcePath = ResourceUtil.getResourcePath(clazz).replaceFirst("/[^/]+$", "") + "/" + "version.png";
		// Valid Path Test
		InputStream imputStream = ResourceUtil.getResourceAsStream(clazz, resourcePath);
		assertNotNull(imputStream, "URL should not be null for a valid path");

	}

	@Test
	void testGetResourceAsStreamClassString() {
		//�e�X�g�Ώۂ̃N���X���w��
		Class<?> clazz = ResourceUtilTest.class;
		// �w�肵���N���X�̃��\�[�X�p�X���擾
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
		// �P�[�X1: �t�@�C�������݂��Ȃ�
		String tempFileName = "tempFileNonExistent.txt";
		assertTrue(ResourceUtil.canWrite(tempFileName), "�V�K�t�@�C���͏������݉\�ł���ׂ��ł��B");

		// �N���[���A�b�v
		File tempFile = new File(tempFileName);
		if (tempFile.exists()) {
			tempFile.delete();
		}

		// �P�[�X2: �t�@�C�������݂��A�������݉\
		tempFileName = "tempFileWritable.txt";
		try (FileOutputStream fos = new FileOutputStream(tempFileName)) {
			fos.write("Test".getBytes());
		}

		assertTrue(ResourceUtil.canWrite(tempFileName), "�����̃t�@�C���͏������݉\�ł���ׂ��ł��B");

		// �N���[���A�b�v
		tempFile = new File(tempFileName);
		if (tempFile.exists()) {
			tempFile.delete();
		}

		// �P�[�X3: �t�@�C�������݂��A�������ݕs�\�i�ǂݎ���p�j
		tempFileName = "tempFileReadOnly.txt";
		try (FileOutputStream fos = new FileOutputStream(tempFileName)) {
			fos.write("Test".getBytes());
		}

		// �t�@�C����ǂݎ���p�ɐݒ�
		File readOnlyFile = new File(tempFileName);
		readOnlyFile.setWritable(false);

		assertFalse(ResourceUtil.canWrite(tempFileName), "�ǂݎ���p�̃t�@�C���͏������ݕs�\�ł���ׂ��ł��B");

		// �N���[���A�b�v
		readOnlyFile.setWritable(true); // �������݌����𕜌�
		if (readOnlyFile.exists()) {
			readOnlyFile.delete();
		}
	}

	@Test
	void testWriteBinary() throws IOException {
		// �e�X�g�p�̃t�@�C�����ƃf�[�^
		String fileName = "testWriteBinary.dat";
		byte[] data = "test data".getBytes();

		// �o�C�i���f�[�^����������
		ResourceUtil.writeBinary(fileName, data);

		// �t�@�C���̑��݊m�F
		File file = new File(fileName);
		assertTrue(file.exists(), "�t�@�C�����쐬����Ă���ׂ��ł��B");

		// �t�@�C������f�[�^��ǂݍ��݁A���e���m�F
		byte[] readData = Files.readAllBytes(file.toPath());
		assertArrayEquals(data, readData, "�t�@�C���ɏ������܂ꂽ�f�[�^���������ׂ��ł��B");

		// �N���[���A�b�v
		if (file.exists()) {
			file.delete();
		}
	}

	@Test
	void testCloseInputStream() {
		// Test normal InputStream
		try (InputStream normalStream = new ByteArrayInputStream(new byte[0])) {
			// ���̃��\�b�h������ɃN���[�Y�ł��邱�Ƃ��m�F
			ResourceUtil.closeInputStream(normalStream);
		} catch (Exception ex) {
			fail("�����InputStream���N���[�Y����ۂɗ�O���������܂����B");
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
		// �W���o�͂��L���v�`�����邽�߂�ByteArrayOutputStream���쐬
		try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
				java.io.PrintStream originalOut = System.out;
				java.io.PrintStream testOut = new java.io.PrintStream(baos)) {

			//System.setOut(testOut); // �W���o�͂��L���v�`��
			ResourceUtil.closeInputStream(faultyStream);

			// �W���o�͂���G���[���b�Z�[�W���擾���Ċm�F
			String output = baos.toString();
			assertTrue(output.contains("Close error"), "�G���[���b�Z�[�W���W���o�͂Ɋ܂܂�Ă���ׂ��ł��B");

			System.setOut(originalOut); // �W���o�͂����ɖ߂�
		} catch (IOException ex) {
			fail("��O�������ɃG���[���������܂����B");
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
				// TODO �����������ꂽ���\�b�h�E�X�^�u

			}
		};
		//TODO:
		// �W���o�͂��L���v�`�����邽�߂�ByteArrayOutputStream���쐬
		try (java.io.ByteArrayOutputStream baos = new java.io.ByteArrayOutputStream();
				java.io.PrintStream originalOut = System.out;
				java.io.PrintStream testOut = new java.io.PrintStream(baos)) {
			System.setOut(testOut);
			ResourceUtil.closeOutputStream(faultyStream);
			// �W���o�͂���G���[���b�Z�[�W���擾���Ċm�F
			String output = baos.toString();
			assertTrue(output.contains("OutputStream Close Error"), "�G���[���b�Z�[�W���W���o�͂Ɋ܂܂�Ă���ׂ��ł��B");

			//System.setOut(originalOut); // �W���o�͂����ɖ߂�
		} catch (IOException ex) {
			fail("��O�������ɃG���[���������܂����B");
		}
	}

	@Test
	void testClosePrintWriter() throws IOException {
		// Test normal PrintWriter
		try (PrintWriter normalWriter = new PrintWriter(new ByteArrayOutputStream())) {
			// ���̃��\�b�h������ɃN���[�Y�ł��邱�Ƃ��m�F
			ResourceUtil.closePrintWriter(normalWriter);
		} catch (Exception ex) {
			fail("�����PrintWriter���N���[�Y����ۂɗ�O���������܂����B");
		}

		// Test PrintWriter that throws an IOException on close
		PrintWriter faultyWriter = new PrintWriter(new ByteArrayOutputStream()) {
			@Override
			public void close() {
				throw new RuntimeException("PrintWriter Close error");
			}
		};

		// �W���o�͂��L���v�`�����邽�߂�ByteArrayOutputStream���쐬
		try (ByteArrayOutputStream baos = new ByteArrayOutputStream();
				java.io.PrintStream originalOut = System.out;
				java.io.PrintStream testOut = new java.io.PrintStream(baos)) {

			System.setOut(testOut); // �W���o�͂��L���v�`��
			ResourceUtil.closePrintWriter(faultyWriter); //"PrintWriter Close error"

		} catch (RuntimeException ex) {
			// �W���o�͂���G���[���b�Z�[�W���擾���Ċm�F
			String output = ex.toString();
			assertTrue(output.contains("Close error"), "�G���[���b�Z�[�W���W���o�͂Ɋ܂܂�Ă���ׂ��ł��B");

		}
	}

	@Test
	void testZipBinary() throws IOException {
		int BUFFER_SIZE = 1024;
		// �e�X�g�p�̃o�C�i���f�[�^
		byte[] inputBytes = "This is a test.".getBytes();
		String fileName = "test.txt";

		// ���\�b�h�Ăяo��
		byte[] zipBytes = ResourceUtil.zipBinary(fileName, inputBytes);

		// ZIP�t�@�C���̓��e���m�F
		try (ByteArrayInputStream bais = new ByteArrayInputStream(zipBytes);
				ZipInputStream zis = new ZipInputStream(bais)) {

			ZipEntry entry = zis.getNextEntry();
			assertNotNull(entry, "ZIP�G���g�������݂���ׂ��ł��B");
			assertEquals(fileName, entry.getName(), "ZIP�G���g���̖��O����v���܂���B");

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] buffer = new byte[BUFFER_SIZE];
			int len;

			while ((len = zis.read(buffer)) > 0) {
				baos.write(buffer, 0, len);
			}

			byte[] outputBytes = baos.toByteArray();
			assertArrayEquals(inputBytes, outputBytes, "���k���ꂽ�f�[�^�����̃f�[�^�ƈ�v���܂���B");

			zis.closeEntry();
		}
	}

	@Test
	void testWriteBinaryInZip() throws IOException {
		// �e�X�g�p�̃o�C�i���f�[�^�ƃt�@�C����
		byte[] inputBytes = "This is a test.".getBytes();
		String fileName = "test.txt";

		// ZIP�t�@�C�����쐬
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (ZipOutputStream zos = new ZipOutputStream(bos)) {
			ZipEntry entry = new ZipEntry(fileName);
			zos.putNextEntry(entry);
			zos.write(inputBytes);
			zos.closeEntry();
		}

		byte[] zipBytes = bos.toByteArray();

		// ZIP�t�@�C������t�@�C�����𓀂��ď�������
		File outputFile = new File("test.txt");
		try {
			ResourceUtil.writeBinaryInZip(fileName, zipBytes);

			// �t�@�C���̓��e���m�F
			byte[] outputBytes = Files.readAllBytes(outputFile.toPath());
			assertArrayEquals(inputBytes, outputBytes, "���k���ꂽ�f�[�^�����̃f�[�^�ƈ�v���܂���B");
		} finally {
			// ��Еt��
			if (outputFile.exists()) {
				outputFile.delete();
			}
		}
	}

	@Test
	void testToBinaryInZip() throws IOException {
		// �e�X�g�p�̃o�C�i���f�[�^�ƃt�@�C����
		byte[] inputBytes = "This is a test.".getBytes();
		String fileName = "test.txt";

		// ZIP�t�@�C�����쐬
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		try (ZipOutputStream zos = new ZipOutputStream(bos)) {
			ZipEntry entry = new ZipEntry(fileName);
			zos.putNextEntry(entry);
			zos.write(inputBytes);
			zos.closeEntry();
		}

		byte[] zipBytes = bos.toByteArray();

		// ZIP���𓀂��ăo�C�i���f�[�^���擾
		byte[] resultBytes = ResourceUtil.toBinaryInZip(zipBytes);

		// ���ʂ����Ғʂ肩�m�F
		assertNotNull(resultBytes, "The result should not be null");
		assertArrayEquals(inputBytes, resultBytes, "The extracted bytes should match the input bytes");
	}

	@Test
	void testIsFilePathExists() throws IOException {
		String TEST_FILE_NAME = "testFile.txt";
		String TEST_DIR_NAME = "testDir";
		// �e�X�g�p�̃t�@�C���ƃf�B���N�g�����쐬
		File testFile = new File(TEST_FILE_NAME);
		File testDir = new File(TEST_DIR_NAME);
		boolean fileCreated = testFile.createNewFile();
		boolean dirCreated = testDir.mkdir();

		try {
			// �t�@�C�������݂���ꍇ
			assertTrue(fileCreated, "Test file should be created");
			assertTrue(ResourceUtil.isFilePathExists(TEST_FILE_NAME), "The file should exist");

			// ���݂��Ȃ��t�@�C���̃`�F�b�N
			assertFalse(ResourceUtil.isFilePathExists("nonExistentFile.txt"), "The file should not exist");

			// �f�B���N�g�������݂���ꍇ
			assertTrue(dirCreated, "Test directory should be created");
			assertTrue(ResourceUtil.isFilePathExists(TEST_DIR_NAME), "The directory should exist");

			// ���݂��Ȃ��f�B���N�g���̃`�F�b�N
			assertFalse(ResourceUtil.isFilePathExists("nonExistentDir"), "The directory should not exist");

		} finally {
			// �e�X�g��Ƀt�@�C���ƃf�B���N�g�����폜
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
		// �e�X�g�p�t�@�C�����쐬�i�t�@�C���̑��݂͎��ۂɂ͕K�v�Ȃ��j
		File fileWithExtension = new File("example.txt");
		File fileWithoutExtension = new File("example");
		File fileWithMultipleDots = new File("archive.tar.gz");
		File fileWithHiddenExtension = new File(".hiddenfile.ext");
		File fileWithNoExtension = new File(".hiddenfile");

		// �g���q�̎擾�Ɗm�F
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
		// �e�X�g�p�̃t�@�C�����쐬
		String testFileName = "testfile.bin";
		byte[] testData = "This is a test.".getBytes();
		try (FileOutputStream fos = new FileOutputStream(testFileName)) {
			fos.write(testData);
		}

		// ���\�b�h���Ăяo���Č��ʂ��擾
		byte[] result = ResourceUtil.readBinarry(testFileName);

		// ���ʂ��m�F
		assertArrayEquals(testData, result, "The binary data read from the file should match the written data");

		// �e�X�g�p�t�@�C�����폜
		Files.deleteIfExists(new File(testFileName).toPath());
	}

	@Test
	void testMakeTextFile() throws IOException {
		// �e�X�g�p�̃t�@�C�����Ɠ��e
		String testFileName = "testfile.txt";
		String testContent = "This is a test content.";

		// �e�X�g�p�t�@�C�����쐬
		ResourceUtil.makeTextFile(testFileName, testContent);

		// �t�@�C�������݂��邱�Ƃ��m�F
		Path filePath = Paths.get(testFileName);
		assertTrue(Files.exists(filePath), "The file should exist");

		// �t�@�C���̓��e��ǂݎ���Č���
		List<String> content = Files.readAllLines(filePath);
		assertEquals(testContent, content.get(0), "The content of the file should match the expected content");

		// �e�X�g�p�t�@�C�����폜
		Files.deleteIfExists(filePath);
	}

	@Test
	void testToObject() throws IOException {
		// �e�X�g�p�I�u�W�F�N�g�̍쐬
		Map<String, String> testObject = new HashMap<>();
		testObject.put("key1", "value1");
		testObject.put("key2", "value2");

		// �I�u�W�F�N�g���o�C�i���`���ɕϊ�
		byte[] serializedObject = serializeObject(testObject);

		// �o�C�i������I�u�W�F�N�g�ɕϊ�
		Object deserializedObject = ResourceUtil.toObject(serializedObject);

		// ���ʂ�����
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
		// �e�X�g�p�I�u�W�F�N�g�̍쐬
		Map<String, String> testObject = new HashMap<>();
		testObject.put("key1", "value1");
		testObject.put("key2", "value2");

		// �I�u�W�F�N�g���o�C�i���`���ɕϊ�
		byte[] binaryData = ResourceUtil.toBinarry(testObject);

		// �o�C�i������I�u�W�F�N�g�ɕϊ�
		Object deserializedObject = deserializeObject(binaryData);

		// ���ʂ�����
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
		// �e�X�g�Ώۂ̃t�@�C�����Ɗ��҂����g���q
		String fileName = "example.txt";
		String expectedExtension = "txt";

		// ���\�b�h�Ăяo��
		String actualExtension = ResourceUtil.getExtension(fileName);

		// ���ʂ̊m�F
		assertEquals(expectedExtension, actualExtension, "�g���q���������擾�ł���ׂ��ł��B");
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
