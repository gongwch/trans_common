package jp.co.ais.trans2.common.ledger;

import java.math.BigDecimal;

import jp.co.ais.trans.common.util.NumberFormatUtil;

import com.klg.jclass.page.JCTextStyle;

/**
 * 帳票のセルクラス
 * @author AIS
 *
 */
public class LedgerCell {

	public final static double WIDTH_REST = -256.0;

	/** セルスタイル */
	protected JCTextStyle style;

	/** 幅 */
	protected double width;

	/** 値 */
	protected String value;

	/**
	 * スタイル、幅、値を指定してセルを生成する
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
	 * スタイル、幅、値、小数点以下桁数を指定してセルを生成する
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
	 * @return style を戻します。
	 */
	public JCTextStyle getStyle() {
		return style;
	}

	/**
	 * @param style 設定する style。
	 */
	public void setStyle(JCTextStyle style) {
		this.style = style;
	}

	/**
	 * @return width を戻します。
	 */
	public double getWidth() {
		return width;
	}

	/**
	 * @param width 設定する width。
	 */
	public void setWidth(double width) {
		this.width = width;
	}

	/**
	 * @return value を戻します。
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param value 設定する value。
	 */
	public void setValue(String value) {
		this.value = value;
	}

}
