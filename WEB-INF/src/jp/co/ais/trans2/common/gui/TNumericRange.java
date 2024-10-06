package jp.co.ais.trans2.common.gui;

import java.math.*;

import jp.co.ais.trans.common.gui.*;

/**
 * 数値範囲フィールド
 * 
 * @author AIS
 */
public class TNumericRange extends TTextRange {

	/**
	 * コンポーネントを初期化する<BR>
	 */
	@Override
	protected void initComponents() {
		fieldFrom = new TLabelNumericField();
		fieldTo = new TLabelNumericField();
	}

	/**
	 * コンポーネント（開始）を取得する
	 * 
	 * @return コンポーネント（開始）
	 */
	@Override
	public TLabelNumericField getFieldFrom() {
		return (TLabelNumericField) fieldFrom;
	}

	/**
	 * コンポーネント（終了）を取得する
	 * 
	 * @return コンポーネント（終了）
	 */
	@Override
	public TLabelNumericField getFieldTo() {
		return (TLabelNumericField) fieldTo;
	}

	/**
	 * コンポーネント（開始）の値を取得する
	 * 
	 * @return コンポーネント（開始）の値
	 */
	public BigDecimal getBigDecimalFrom() {
		return getFieldFrom().getBigDecimal();
	}

	/**
	 * コンポーネント（終了）の値を取得する
	 * 
	 * @return コンポーネント（終了）の値
	 */
	public BigDecimal getBigDecimalTo() {
		return getFieldTo().getBigDecimal();
	}

	/**
	 * 値を設定する
	 * 
	 * @param valueFrom コンポーネント（開始） 設定値
	 * @param valueTo コンポーネント（終了） 設定値
	 */
	public void setValue(Number valueFrom, Number valueTo) {
		getFieldFrom().setNumber(valueFrom);
		getFieldTo().setNumber(valueTo);
	}

	/**
	 * 「FROM <= TO」を比較する.
	 * 
	 * @return 「FROM <= TO」または、どちらかがブランクならばtrue.
	 */
	@Override
	public boolean isSmallerFrom() {
		return getBigDecimalFrom().compareTo(getBigDecimalTo()) != 1;
	}
}
