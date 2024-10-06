package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.language.*;

/**
 * 言語コードコンボボックス
 */
public class TLanguageComboBox extends TLabelComboBox {

	/** コントローラ */
	protected TLanguageComboBoxController controller;

	/**
	 * 
	 */
	public TLanguageComboBox() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		controller = new TLanguageComboBoxController(this);

	}

	protected void initComponents() {
		// 
	}

	protected void allocateComponents() {

		// 言語コード
		label.setLangMessageID("C00699");
		setLabelSize(60);
		setComboSize(100);
		setSize(165, 20);

	}

	/**
	 * @return controller
	 */
	public TLanguageComboBoxController getController() {
		return controller;
	}

	/**
	 * 選択されている値を取得
	 * 
	 * @return 値
	 */
	@Override
	public String getSelectedItemValue() {
		return (String) this.combo.getSelectedItemValue();
	}

}
