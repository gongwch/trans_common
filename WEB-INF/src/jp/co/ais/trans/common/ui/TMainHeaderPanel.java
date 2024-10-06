package jp.co.ais.trans.common.ui;

import java.awt.*;
import java.awt.geom.*;

import jp.co.ais.plaf.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * TRANS2�O���f�[�V�����p�w�b�_�[�p�l��
 */
public class TMainHeaderPanel extends TPanel {

	/** �O���f�[�V�����n�_�F */
	protected Color startColor = Color.white;

	/** �O���f�[�V�����I�_�F */
	protected Color endColor = new Color(210, 210, 202);

	@Override
	public void paintComponent(Graphics g) {

		if (TGradientPaint.isFlat) {
			endColor = ColorHelper.brighter(getBackground(), 25);
		}

		Graphics2D g2 = (Graphics2D) g;

		TGradientPaint gradient = new TGradientPaint(getWidth() / 2, 0.0f, startColor, getWidth() / 2, getHeight(),
			endColor);
		g2.setPaint(gradient);

		g2.fill(new Rectangle2D.Double(0, 0, getWidth(), getHeight()));
	}

}