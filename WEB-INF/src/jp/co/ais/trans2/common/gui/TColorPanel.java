package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.geom.*;

import jp.co.ais.trans.common.gui.*;

/**
 * グラデーションカラー付きパネル
 * 
 * @author AIS
 */
public class TColorPanel extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 4008758734836840294L;

	/** グラデーション始点色 */
	protected Color startColor = new Color(45, 120, 255);

	/** グラデーション終点色 */
	protected Color endColor = Color.white;

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		GradientPaint gradient = new GradientPaint(300, 0, endColor, getWidth(), getHeight(), startColor);
		g2.setPaint(gradient);

		g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));

	}

	/**
	 * @return endColorを戻します。
	 */
	public Color getEndColor() {
		return endColor;
	}

	/**
	 * @param endColor endColorを設定します。
	 */
	public void setEndColor(Color endColor) {
		this.endColor = endColor;
	}

	/**
	 * @return startColorを戻します。
	 */
	public Color getStartColor() {
		return startColor;
	}

	/**
	 * @param startColor startColorを設定します。
	 */
	public void setStartColor(Color startColor) {
		this.startColor = startColor;
	}

}
