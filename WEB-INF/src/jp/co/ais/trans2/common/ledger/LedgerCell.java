package jp.co.ais.trans2.common.ledger;

import java.math.BigDecimal;

import jp.co.ais.trans.common.util.NumberFormatUtil;

import com.klg.jclass.page.JCTextStyle;

/**
 * ���[�̃Z���N���X
 * @author AIS
 *
 */
public class LedgerCell {

	public final static double WIDTH_REST = -256.0;

	/** �Z���X�^�C�� */
	protected JCTextStyle style;

	/** �� */
	protected double width;

	/** �l */
	protected String value;

	/**
	 * �X�^�C���A���A�l���w�肵�ăZ���𐶐�����
	 * @param style
	 * @param width
	 * @param value
	 */
	public LedgerCell(JCTextStyle style, double width, String value) {
		this.style = style;
		this.width = width;
		this.value = value;
	}

	/**
	 * �X�^�C���A���A�l�A�����_�ȉ��������w�肵�ăZ���𐶐�����
	 * @param style
	 * @param width
	 * @param value
	 * @param decimalPoint
	 */
	public LedgerCell(
			JCTextStyle style,
			double width,
			BigDecimal value,
			int decimalPoint) {

		this.style = style;
		this.width = width;
		if (value == null) {
			this.value = "";
		} else {
			this.value = NumberFormatUtil.formatNumber(value, decimalPoint);
		}
	}

	/**
	 * @return style ��߂��܂��B
	 */
	public JCTextStyle getStyle() {
		return style;
	}

	/**
	 * @param style �ݒ肷�� style�B
	 */
	public void setStyle(JCTextStyle style) {
		this.style = style;
	}

	/**
	 * @return width ��߂��܂��B
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @param width �ݒ肷�� width�B
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * @return value ��߂��܂��B
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value �ݒ肷�� value�B
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
