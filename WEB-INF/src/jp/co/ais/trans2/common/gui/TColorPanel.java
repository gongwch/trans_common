package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.geom.*;

import jp.co.ais.trans.common.gui.*;

/**
 * �O���f�[�V�����J���[�t���p�l��
 * 
 * @author AIS
 */
public class TColorPanel extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 4008758734836840294L;

	/** �O���f�[�V�����n�_�F */
	protected Color startColor = new Color(45, 120, 255);

	/** �O���f�[�V�����I�_�F */
	protected Color endColor = Color.white;

	@Override
	public void paintComponent(Graphics g) {

		Graphics2D g2 = (Graphics2D) g;

		GradientPaint gradient = new GradientPaint(300, 0, endColor, getWidth(), getHeight(), startColor);
		g2.setPaint(gradient);

		g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));

	}

	/**
	 * @return endColor��߂��܂��B
	 */
	public Color getEndColor() {
		return endColor;
	}

	/**
	 * @param endColor endColor��ݒ肵�܂��B
	 */
	public void setEndColor(Color endColor) {
		this.endColor = endColor;
	}

	/**
	 * @return startColor��߂��܂��B
	 */
	public Color getStartColor() {
		return startColor;
	}

	/**
	 * @param startColor startColor��ݒ肵�܂��B
	 */
	public void setStartColor(Color startColor) {
		this.startColor = startColor;
	}

}
