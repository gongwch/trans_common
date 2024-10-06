package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.TNumericField;

/**
 * 外貨金額フィールド
 * 
 * @author AIS
 */
public class TForeignAmount extends TNumericField {

	/**
	 * コンストラクタ.
	 */
	public TForeignAmount() {

		initComponents();

		allocateComponents();

	}

	/**
	 * 
	 */
	public void initComponents() {
		setMaxLength(13, 4);
		setChangeRedOfMinus(true);
	}

	/**
	 * 
	 */
	public void allocateComponents() {
		//
	}

}
