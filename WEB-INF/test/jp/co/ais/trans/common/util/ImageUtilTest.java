package jp.co.ais.trans.common.util;

import static org.assertj.core.api.Assertions.*;

import java.awt.Panel;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

import org.junit.jupiter.api.Test;

class ImageUtilTest {

	@Test
	void testToImage() throws IOException {
		BufferedImage originalImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "png", baos);
		byte[] imageData = baos.toByteArray();

		BufferedImage result = ImageUtil.toImage(imageData);

		assertThat(result).isNotNull();
		assertThat(result.getWidth()).isEqualTo(100);
		assertThat(result.getHeight()).isEqualTo(100);
	}

	@Test
	void testToBinary() throws IOException {
		BufferedImage originalImage = new BufferedImage(100, 100, BufferedImage.TYPE_INT_ARGB);

		byte[] imageData = ImageUtil.toBinary(originalImage);

		assertThat(imageData).isNotNull();
		assertThat(imageData.length).isGreaterThan(0);
	}

	@Test
	void testGetTrimImageBufferedImage() throws IOException {
		BufferedImage originalImage = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "png", baos);

		BufferedImage trimmedImage = ImageUtil.getTrimImage(originalImage);

		assertThat(trimmedImage).isNotNull();

	}

	@Test
	void testGetScale() throws IOException {
		BufferedImage originalImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "png", baos);
		byte[] imageData = baos.toByteArray();

		double scale = ImageUtil.getScale(imageData, 100, 100);

		assertThat(scale).isEqualTo(0.5);
	}

	@Test
	void testReCreateImage() throws IOException {
		BufferedImage originalImage = new BufferedImage(200, 200, BufferedImage.TYPE_INT_ARGB);
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ImageIO.write(originalImage, "png", baos);
		byte[] imageData = baos.toByteArray();

		byte[] scaledImageData = ImageUtil.reCreateImage(imageData, 0.5);
		BufferedImage scaledImage = ImageUtil.toImage(scaledImageData);

		assertThat(scaledImage).isNotNull();
		assertThat(scaledImage.getWidth()).isEqualTo(100);
		assertThat(scaledImage.getHeight()).isEqualTo(100);
	}

	@Test
	void testGetImageBinaryFromComponent() {
		Panel panel = new Panel();
		panel.setSize(200, 200);

		byte[] imageData = ImageUtil.getImageBinaryFromComponent(panel);

		assertThat(imageData).isNotNull();
		assertThat(imageData.length).isGreaterThan(0);
	}

	@Test
	void testGetImageFromComponent() {
		Panel panel = new Panel();
		panel.setSize(200, 200);

		BufferedImage image = ImageUtil.getImageFromComponent(panel);

		assertThat(image).isNotNull();
		assertThat(image.getWidth()).isEqualTo(200);
		assertThat(image.getHeight()).isEqualTo(200);
	}

	@Test
	void testGetImageFromComponentWithText() {
		Panel panel = new Panel();
		panel.setSize(200, 200);

		BufferedImage image = ImageUtil.getImageFromComponent(panel, "Test", 50, 50);

		assertThat(image).isNotNull();
		assertThat(image.getWidth()).isEqualTo(200);
		assertThat(image.getHeight()).isEqualTo(200);
		// Check if the image contains the text "Test" (this is complex to test directly)
		// You might need to visually verify this or use OCR libraries for more thorough tests
	}

	@Test
	void testgGetTrimImageByte() {
		byte[] imageData = ImageUtil.getTrimImage(new byte[9]);
		assertThat(imageData).isNotNull();
	}

	@Test
	void testNullImageConversion() {
		BufferedImage image = ImageUtil.toImage(null);
		assertThat(image).isNull();

		byte[] binaryData = ImageUtil.toBinary(null);
		assertThat(binaryData).isNull();
	}

}