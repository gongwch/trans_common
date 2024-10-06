package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.define.CurrencyType;

/**
 * 表示通貨選択コンボボックス
 */
public class TBookCurrencyComboBox extends TLabelComboBox {

	/** コントローラ */
	protected TBookCurrencyComboBoxController controller;

	/**
	 * 
	 */
	public TBookCurrencyComboBox() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		controller = new TBookCurrencyComboBoxController(this);

	}

	/**
	 * コンポーネント初期化
	 */
	protected void initComponents() {
		// 
	}

	/**
	 * コンポーネントの配置
	 */
	protected void allocateComponents() {

		// 表示通貨
		label.setLangMessageID("C10549");
		setLabelSize(50);
		setComboSize(150);
		setSize(245, 20);

	}

	/**
	 * @return
	 */
	public TBookCurrencyComboBoxController getController() {
		return controller;
	}

	/**
	 * 選択されている表示通貨を返す
	 * 
	 * @return 選択されている表示通貨
	 */
	public CurrencyType getCurrencyType() {
		return controller.getCurrencyType();
	}

}
