package jp.co.ais.trans2.common.gui;

import java.awt.*;

/**
 * �`��J�n�A�I���A�w�i�F
 */
public class TChartPainterRect {

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("from=").append(from);
		sb.append(", to=").append(to);
		sb.append(", color=").append(color);
		return sb.toString();
	}

	/** �J�n */
	public int from = 0;

	/** �I�� */
	public int to = 0;

	/** �w�i�F */
	public Color color = null;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param from �J�n
	 * @param to �I��
	 * @param color �w�i�F
	 */
	public TChartPainterRect(int from, int to, Color color) {
		this.from = from;
		this.to = to;
		this.color = color;
	}

}
