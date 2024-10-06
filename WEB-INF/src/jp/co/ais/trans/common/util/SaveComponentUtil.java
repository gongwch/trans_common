package jp.co.ais.trans.common.util;

import java.awt.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;
import javax.swing.*;

import jp.co.ais.trans2.common.print.*;

/**
 * 画面キャプチャー
 */
public class SaveComponentUtil {

	/**
	 * Save component as a jpeg file
	 * 
	 * @param outputFile jpeg output file
	 * @param component swing component
	 * @throws java.io.IOException exception while writing output
	 */
	public static void writeAsJPEG(File outputFile, Component component) throws IOException {
		writeAsImage(outputFile, "jpg", component);
	}

	/**
	 * Save component as a png file
	 * 
	 * @param outputFile png output file
	 * @param component swing component
	 * @throws java.io.IOException exception while writing output
	 */
	public static void writeAsPNG(File outputFile, Component component) throws IOException {
		writeAsImage(outputFile, "png", component);
	}

	/**
	 * Save component as a gif file
	 * 
	 * @param outputFile gif output file
	 * @param component swing component
	 * @throws java.io.IOException exception while writing output
	 */
	public static void writeAsGIF(File outputFile, Component component) throws IOException {
		writeAsImage(outputFile, "gif", component);
	}

	/**
	 * Save component as a image file
	 * 
	 * @param outputFile image output file
	 * @param imageFormat image format
	 * @param component swing component
	 * @throws java.io.IOException exception while writing output
	 */
	public static void writeAsImage(File outputFile, String imageFormat, Component component) throws IOException {
		BufferedImage bufferedImage = new BufferedImage(component.getWidth(), component.getHeight(),
			BufferedImage.TYPE_INT_RGB);

		Graphics2D g2 = bufferedImage.createGraphics();

		component.paint(g2);

		ImageIO.write(bufferedImage, imageFormat, outputFile);
	}

	/**
	 * @param component
	 * @return filename
	 */
	public static String parseToFile(Component component) {
		String filename = null;

		try {
			TPrinter printer = new TPrinter();
			String ts = DateUtil.toYMDHMSSSSPlainString(Util.getCurrentDate());
			filename = printer.createResultFile("capture" + ts + ".png", new byte[0]);
			File pngOutputFile = new File(filename);
			writeAsPNG(pngOutputFile, component);
			SystemUtil.executeFile(filename);

			System.out.println("save capture to file:" + filename);

		} catch (Exception e) {
			e.printStackTrace();
		}

		return filename;
	}

	/**
	 * @param component
	 */
	public static void capture(Component component) {

		try {
			TPrinter printer = new TPrinter();
			String ts = DateUtil.toYMDHMSSSSPlainString(Util.getCurrentDate());
			String filename = printer.createResultFile("capture" + ts + ".png", new byte[0]);
			File pngOutputFile = new File(filename);
			writeAsPNG(pngOutputFile, component);

			System.out.println("save capture to file:" + filename);

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * TEST
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		JFrame frame = new JFrame();
		frame.setSize(new Dimension(200, 200));
		JPanel panel = new JPanel(new BorderLayout());
		panel.add(new JLabel("Test test test"), BorderLayout.CENTER);
		frame.setContentPane(panel);
		frame.setVisible(true);

		try {

			TPrinter printer = new TPrinter();

			// svae as jpeg

			String filename = printer.createResultFile("test.jpg", new byte[0]);
			File jpegOutputFile = new File(filename);
			writeAsJPEG(jpegOutputFile, panel);

			// save as png
			filename = printer.createResultFile("test.png", new byte[0]);
			File pngOutputFile = new File(filename);
			writeAsPNG(pngOutputFile, panel);
			SystemUtil.executeFile(filename);

			// save as gif
			filename = printer.createResultFile("test.gif", new byte[0]);
			File gifOutputFile = new File(filename);
			writeAsGIF(gifOutputFile, panel);

		} catch (Exception ioe) {
			ioe.printStackTrace();
		}
	}
}