package jp.co.ais.trans2.common.gui;

import java.awt.*;

import javax.swing.text.DefaultHighlighter.DefaultHighlightPainter;

import jp.co.ais.trans.common.gui.*;

/**
 * �n�C���C�g
 */
public class THighlightPainter extends DefaultHighlightPainter {

	/**
	 * �R���X�g���N�^�[
	 */
	public THighlightPainter() {
		super(Color.yellow);
	}

	@Override
	public Color getColor() {
		// return TTextBGColor.ERROR.brighter();
		// return TTextBGColor.getHighLightFocusInColor().darker();
		return TTextBGColor.getHighLightFocusInColor();
	}

}
