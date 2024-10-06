package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;

/**
 * レートフィールド
 * 
 * @author AIS
 */
public class TRate extends TNumericField {

	/**
	 * コンストラクタ.
	 */
	public TRate() {

		initComponents();

		allocateComponents();

	}

	/**
	 * 
	 */
	public void initComponents() {
		setMaxLength(13, 4);
	}

	/**
	 * 
	 */
	public void allocateComponents() {
		//
	}

}
