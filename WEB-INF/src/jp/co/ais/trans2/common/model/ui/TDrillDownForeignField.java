package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;

/**
 * ドリルダウンの外貨フィールド
 */
public class TDrillDownForeignField {

	/** 外貨借方計 */
	public TLabelNumericField txDebitInSum;

	/** 外貨貸方計 */
	public TNumericField txCreditInSum;

	/** 外貨 */
	public TTextField txCur;

	/**
	 * コンストラクター
	 */
	public TDrillDownForeignField() {
		txDebitInSum = new TLabelNumericField();
		txCreditInSum = new TNumericField();
		txCur = new TTextField();
	}

	/**
	 * 表示/非表示設定
	 * 
	 * @param flag
	 */
	public void setVisible(boolean flag) {
		txDebitInSum.setVisible(flag);
		txCreditInSum.setVisible(flag);
		txCur.setVisible(flag);
	}

	/**
	 * @return 表示/非表示
	 */
	public boolean isVisible() {
		return txDebitInSum.isVisible();
	}
}
