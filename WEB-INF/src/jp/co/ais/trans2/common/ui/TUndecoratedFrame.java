package jp.co.ais.trans2.common.ui;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.lang.reflect.*;
import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;

/**
 * 可変型画面
 * 
 * @author AIS
 */
public class TUndecoratedFrame extends TFrame {

	/** 画像 */
	protected BufferedImage backGroundImage;

	/** 座標 */
	protected Point origin;

	/**
	 * コンストラクター
	 */
	public TUndecoratedFrame() {
		try {
			initBackgroundImage();
			addEvents();
		} catch (Throwable ex) {
			// エラー
			ex.printStackTrace();
		}
	}

	/**
	 * コンポーネントを初期化する
	 */
	public void initBackgroundImage() {

		backGroundImage = getImage();
		origin = this.getLocation();

		MediaTracker mt = new MediaTracker(this);
		mt.addImage(backGroundImage, 0);

		try {
			mt.waitForAll();
		} catch (Exception e) {
			e.printStackTrace();
		}

		setUndecorated(true);
		setSize(new Dimension(backGroundImage.getWidth(null), backGroundImage.getHeight(null)));

		// ウィンドウ初期化
		initWindow();

		this.setResizable(false);
		this.setLocationRelativeTo(null);
		this.repaint();

	}

	/**
	 * ウィンドウ初期化<br>
	 * 低いバージョンのエラー対応
	 */
	protected void initWindow() {

		if (!ClientConfig.isFlagOn("login.use.undecorated.window")) {
			return;
		}

		try {
			Class awtUtilitiesClass = Class.forName("com.sun.awt.AWTUtilities");

			// AWTUtilities.setWindowShape(this, getImageShape(backGroundImage)); // 形
			Method mSetWindowShape = awtUtilitiesClass.getMethod("setWindowShape", Window.class, Shape.class);
			mSetWindowShape.invoke(null, this, getImageShape(backGroundImage)); // 形

			// AWTUtilities.setWindowOpacity(this, 1f); // 透明度
			Method mSetWindowOpacity = awtUtilitiesClass.getMethod("setWindowOpacity", Window.class, float.class);
			mSetWindowOpacity.invoke(null, this, 1f); // 透明度
		} catch (Throwable ex) {
			// エラーなし
			System.out.println("no AWTUtilities class found.");
		}
	}

	/**
	 * イベント追加
	 */
	protected void addEvents() {

		addMouseListener(new MouseAdapter() {

			@Override
			public void mousePressed(MouseEvent e) {
				origin.x = e.getX();
				origin.y = e.getY();
			}

			@Override
			public void mouseClicked(MouseEvent e) {
				if (e.getButton() == MouseEvent.BUTTON3) System.exit(0);
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				super.mouseReleased(e);
			}

			@Override
			public void mouseEntered(MouseEvent e) {
				repaint();
			}
		});

		addMouseMotionListener(new MouseMotionAdapter() {

			@Override
			public void mouseDragged(MouseEvent e) {
				Point p = getLocation();
				setLocation(p.x + e.getX() - origin.x, p.y + e.getY() - origin.y);
			}
		});

	}

	/**
	 * 背景画像を取得する
	 * 
	 * @return 背景画像
	 */
	protected BufferedImage getImage() {
		BufferedImage img = ResourceUtil.getImage(getImagePath());
		return img;
	}

	/**
	 * @return 背景画像パス
	 */
	protected String getImagePath() {
		return "images/login_un_t.png";
	}

	/**
	 * Shape
	 * 
	 * @param img
	 * @return Shape
	 * @author Hexen
	 */
	public Shape getImageShape(Image img) {
		ArrayList<Integer> x = new ArrayList<Integer>();
		ArrayList<Integer> y = new ArrayList<Integer>();
		int width = img.getWidth(null);
		int height = img.getHeight(null);

		PixelGrabber pgr = new PixelGrabber(img, 0, 0, -1, -1, true);
		try {
			pgr.grabPixels();
		} catch (InterruptedException ex) {
			ex.getStackTrace();
		}
		int pixels[] = (int[]) pgr.getPixels();

		for (int i = 0; i < pixels.length; i++) {
			int alpha = getAlpha(pixels[i]);
			if (alpha == 0) {
				continue;
			} else {
				x.add(i % width > 0 ? i % width - 1 : 0);
				y.add(i % width == 0 ? (i == 0 ? 0 : i / width - 1) : i / width);
			}
		}

		int[][] matrix = new int[height][width];
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				matrix[i][j] = 0;
			}
		}

		for (int c = 0; c < x.size(); c++) {
			matrix[y.get(c)][x.get(c)] = 1;
		}

		Area rec = new Area();
		int temp = 0;

		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				if (matrix[i][j] == 1) {
					if (temp == 0) temp = j;
					else if (j == width) {
						if (temp == 0) {
							Rectangle rectemp = new Rectangle(j, i, 1, 1);
							rec.add(new Area(rectemp));
						} else {
							Rectangle rectemp = new Rectangle(temp, i, j - temp, 1);
							rec.add(new Area(rectemp));
							temp = 0;
						}
					}
				} else {
					if (temp != 0) {
						Rectangle rectemp = new Rectangle(temp, i, j - temp, 1);
						rec.add(new Area(rectemp));
						temp = 0;
					}
				}
			}
			temp = 0;
		}
		return rec;
	}

	/**
	 * Alpha
	 * 
	 * @param pixel
	 * @return Alpha
	 */
	protected int getAlpha(int pixel) {
		return (pixel >> 24) & 0xff;
	}

}
