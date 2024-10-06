package jp.co.ais.trans.common.util;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.io.*;

import javax.imageio.*;

import jp.co.ais.trans.common.except.*;

/**
 * 画像←→バイト処理Util
 */
public class ImageUtil {

	/** 画像の幅 */
	protected static final double IMAGE_WIDTH = 150;

	/** 画像の高さ */
	protected static final double IMAGE_HEIGHT = 150;

	/** 画像の容量(BYTE) */
	protected static final double IMAGE_SIZE = 10240;

	/**
	 * バイトから画像の取得
	 * 
	 * @param imageData
	 * @return 画像
	 */
	public static BufferedImage toImage(byte[] imageData) {

		try {
			if (imageData == null) {
				return null;
			}

			ByteArrayInputStream bais = new ByteArrayInputStream(imageData);
			return ImageIO.read(bais);

		} catch (IOException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 画像のバイト取得
	 * 
	 * @param image 画像
	 * @return バイト
	 */
	public static byte[] toBinary(BufferedImage image) {
		try {

			if (image == null) {
				return null;
			}

			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			ImageIO.write(image, "png", baos);

			return baos.toByteArray();

		} catch (IOException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * 縮小した画像の取得
	 * 
	 * @param bufImage 画像
	 * @return バイト
	 */
	public static BufferedImage getTrimImage(BufferedImage bufImage) {
		byte[] image = toBinary(bufImage);
		byte[] imageData = getTrimImage(image);
		return toImage(imageData);
	}

	/**
	 * 縮小した画像のバイト取得
	 * 
	 * @param image 画像
	 * @return バイト
	 */
	public static byte[] getTrimImage(byte[] image) {
		byte[] imageData = null;

		// 画像ファイルを圧縮
		try {
			if (image.length > IMAGE_SIZE) {
				double scale = ImageUtil.getScale(image, IMAGE_WIDTH, IMAGE_HEIGHT);

				imageData = ImageUtil.reCreateImage(image, scale);
			} else {
				imageData = image;
			}
		} catch (Exception e) {
			imageData = image;
		}
		return imageData;
	}

	/**
	 * 元画像と出力画像の倍率を取得 200X200の画像を100X100に変換しようとする場合、 倍率は「0.5」。
	 * 
	 * @param byteData
	 * @param width
	 * @param height
	 * @return 変換前と変換後の倍率
	 */
	public static double getScale(byte[] byteData, double width, double height) {
		double doubleValueWidth = 0;
		double doubleValueHeight = 0;

		ByteArrayInputStream inputStream = new ByteArrayInputStream(byteData);
		try {
			BufferedImage image = ImageIO.read(inputStream);

			doubleValueWidth = width / image.getWidth();
			if (doubleValueWidth > 1) doubleValueWidth = 1;

			doubleValueHeight = height / image.getHeight();
			if (doubleValueHeight > 1) doubleValueHeight = 1;
		} catch (IOException e) {
			e.printStackTrace();
		}

		if (doubleValueHeight < doubleValueWidth) return doubleValueHeight;
		else return doubleValueWidth;
	}

	/**
	 * 倍率にそって、画像を変換する
	 * 
	 * @param byteData
	 * @param scale
	 * @return 変換後の画像
	 */
	public static byte[] reCreateImage(byte[] byteData, double scale) {
		ByteArrayInputStream inputStream = new ByteArrayInputStream(byteData);
		try {
			BufferedImage image = ImageIO.read(inputStream);

			AffineTransformOp atOp = new AffineTransformOp(AffineTransform.getScaleInstance(scale, scale), null);

			BufferedImage imageNew = new BufferedImage((int) (image.getWidth() * scale),
				(int) (image.getHeight() * scale), image.getType());

			atOp.filter(image, imageNew);

			ByteArrayOutputStream byteOutput = new ByteArrayOutputStream();

			try {
				ImageIO.write(imageNew, "png", byteOutput);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				try {
					byteOutput.flush();
					byteOutput.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}

			if (byteOutput.size() > 0) byteData = byteOutput.toByteArray();
		} catch (IOException e) {
			e.printStackTrace();
			byteData = null;
		}

		return byteData;
	}

	/**
	 * @param component
	 * @return キャプチャー画像
	 */
	public static byte[] getImageBinaryFromComponent(Component component) {
		return toBinary(getImageFromComponent(component));
	}

	/**
	 * @param component
	 * @return キャプチャー画像
	 */
	public static BufferedImage getImageFromComponent(Component component) {
		return getImageFromComponent(component, null, 0, 0);
	}

	/**
	 * @param component
	 * @param addonText
	 * @param drawX
	 * @param drawY
	 * @return キャプチャー画像
	 */
	public static BufferedImage getImageFromComponent(Component component, String addonText, int drawX, int drawY) {
		int width = component.getWidth();
		int height = component.getHeight();

		BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
		Graphics2D g2d = (Graphics2D) img.getGraphics();

		// 背景
		g2d.setColor(component.getBackground());
		g2d.fillRect(0, 0, width, height);

		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

		// 全て描画
		component.paintAll(g2d);

		if (!Util.isNullOrEmpty(addonText)) {
			g2d.setColor(Color.blue);
			g2d.setFont(new Font("MS Gothic", Font.PLAIN, 30));
			g2d.drawString(addonText, drawX, drawY);
		}

		g2d.dispose();
		return img;
	}
}
