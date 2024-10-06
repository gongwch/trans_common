package jp.co.ais.trans.common.util;

import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

import org.junit.jupiter.api.Test;

class SaveComponentUtilTest {

	@Test
	void testWriteAsJPEG() throws IOException {
		File testFile = new File("testImg.jpg");
		testFile.deleteOnExit();

		// Create a sample component to render
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(100, 100));
		panel.setBackground(Color.RED);
		panel.setBounds(0, 0, 100, 100);
		// Perform the method call
		SaveComponentUtil.writeAsJPEG(testFile, panel);

		// Validate the output
		assertTrue(testFile.exists(), "JPEG file should be created.");
		assertTrue(testFile.length() > 0, "JPEG file should not be empty.");

		// Verify that the file is a valid JPEG
		BufferedImage image = ImageIO.read(testFile);
		assertNotNull(image, "The file should be a valid image.");
		assertEquals("jpg", ResourceUtil.getExtension(testFile.getName()), "The file extension should be jpg.");

	}

	@Test
	void testWriteAsPNG() throws IOException {
		File testFile = new File("testImg.png");
		testFile.deleteOnExit();

		// Create a sample component to render
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(100, 100));
		panel.setBackground(Color.RED);
		panel.setBounds(0, 0, 100, 100);
		// Perform the method call
		SaveComponentUtil.writeAsPNG(testFile, panel);

		// Validate the output
		assertTrue(testFile.exists(), "PNG file should be created.");
		assertTrue(testFile.length() > 0, "PNG file should not be empty.");

		// Verify that the file is a valid png
		BufferedImage image = ImageIO.read(testFile);
		assertNotNull(image, "The file should be a valid image.");
		assertEquals("png", ResourceUtil.getExtension(testFile.getName()), "The file extension should be png.");
	}

	@Test
	void testWriteAsGIF() throws IOException {
		File testFile = new File("testImg.gif");
		testFile.deleteOnExit();

		// Create a sample component to render
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(100, 100));
		panel.setBackground(Color.RED);
		panel.setBounds(0, 0, 100, 100);
		// Perform the method call
		SaveComponentUtil.writeAsGIF(testFile, panel);

		// Validate the output
		assertTrue(testFile.exists(), "Gif file should be created.");
		assertTrue(testFile.length() > 0, "gif file should not be empty.");

		// Verify that the file is a valid gif
		BufferedImage image = ImageIO.read(testFile);
		assertNotNull(image, "The file should be a valid image.");
		assertEquals("gif", ResourceUtil.getExtension(testFile.getName()), "The file extension should be gif.");
	}

	@Test
	void testWriteAsImage() throws IOException {
		File testFile = new File("testImg.png");
		testFile.deleteOnExit();

		// Create a sample component to render
		JPanel panel = new JPanel();
		panel.setPreferredSize(new Dimension(100, 100));
		panel.setBackground(Color.RED);
		panel.setBounds(0, 0, 100, 100);
		// Perform the method call
		SaveComponentUtil.writeAsImage(testFile, "png", panel);

		// Validate the output
		assertTrue(testFile.exists(), "PNG file should be created.");
		assertTrue(testFile.length() > 0, "PNG file should not be empty.");

		// Verify that the file is a valid png
		BufferedImage image = ImageIO.read(testFile);
		assertNotNull(image, "The file should be a valid image.");
		assertEquals("png", ResourceUtil.getExtension(testFile.getName()), "The file extension should be png.");
	}

	@Test
	void testParseToFile() {
		String filename = null;

		try {
			// Create a sample component to test
			JPanel panel = new JPanel();
			panel.setPreferredSize(new Dimension(100, 100));
			panel.setBackground(Color.RED);
			panel.setBounds(0, 0, 100, 100);

			// Perform the method call
			filename = SaveComponentUtil.parseToFile(panel);

			// Validate the output
			assertNotNull(filename, "Filename should not be null.");

			File file = new File(filename);
			assertTrue(file.exists(), "Image file should be created.");
			assertTrue(file.length() > 0, "Image file should not be empty.");
			assertEquals("png", ResourceUtil.getExtension(filename), "The file extension should be png.");

		} catch (Exception e) {
			fail("An exception occurred: " + e.getMessage());
		} finally {
			// Clean up
			if (filename != null) {
				File file = new File(filename);
				if (file.exists()) {
					if (!file.delete()) {
						System.err.println("Failed to delete the test file: " + filename);
					}
				}
			}
		}
	}

	@Test
	void testCapture() {
		String filename = null;
		try {

			// Create a sample component to test
			JPanel panel = new JPanel();
			panel.setPreferredSize(new Dimension(100, 100));
			panel.setBackground(Color.RED);
			panel.setBounds(0, 0, 100, 100);

			// Call the method
			SaveComponentUtil.capture(panel);

			// Generate the filename based on the current date and time
			String ts = DateUtil.toYMDHMSSSSPlainString(Util.getCurrentDate());
			filename = "capture" + ts + ".png";

			// Validate the output
			File file = new File(filename);
			// TODO: It is challenging to create a file with the same name that contains the time in the format (YMDHMSSSS).
			// However, you can test this by checking if the file is created in the directory "C:\Users\xxx\AppData\Local\Temp\".
			assertTrue(file.exists(), "Image file should be created.");
			assertTrue(file.length() > 0, "Image file should not be empty.");
			assertEquals("png", ResourceUtil.getExtension(filename), "The file extension should be png.");

		} catch (Exception e) {
			fail("An exception occurred: " + e.getMessage());
		} finally {
			// Clean up
			if (filename != null) {
				File file = new File(filename);
				if (file.exists()) {
					if (!file.delete()) {
						System.err.println("Failed to delete the test file: " + filename);
					}
				}
			}
		}
	}

}
