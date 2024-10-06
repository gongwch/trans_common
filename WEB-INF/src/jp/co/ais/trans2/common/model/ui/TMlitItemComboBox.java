package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;

/**
 * 輸送実績大品目コンボボックス
 */
public class TMlitItemComboBox extends TLabelComboBox {

	/** コントローラ */
	protected TMlitItemComboBoxController controller;

	/** 輸送実績サブ品目コンボボックス */
	protected TMlitSubItemComboBox subCombo;

	/** true:空白選択肢がある */
	protected boolean hasBlank = false;

	/**
	 * 
	 */
	public TMlitItemComboBox() {
		this(false);
	}

	/**
	 * @param hasBlank
	 */
	public TMlitItemComboBox(boolean hasBlank) {

		this.hasBlank = hasBlank;

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		// コントローラを配置する
		controller = createController();
	}

	/**
	 * @return コントローラ
	 */
	protected TMlitItemComboBoxController createController() {
		return new TMlitItemComboBoxController(this);
	}

	/**
	 * 
	 */
	protected void initComponents() {
		//
	}

	/**
	 * 
	 */
	protected void allocateComponents() {

		label.setLangMessageID("COP1232"); // MLIT ITEM
		setLabelSize(75);
		setComboSize(180);
		setSize(260, 20);
	}

	/**
	 * @return コントローラ
	 */
	public TMlitItemComboBoxController getController() {
		return controller;
	}

	/**
	 * 選択された品目コードを返す
	 * 
	 * @return 選択された品目コード
	 */
	public String getSelectedCode() {
		return controller.getSelectedCode();
	}

	/**
	 * 品目コードを設定する
	 * 
	 * @param code 品目コード
	 */
	public void setSelectedCode(String code) {
		controller.setSelectedCode(code);
	}

	/**
	 * 選択された品目名称を返す
	 * 
	 * @return 品目名称
	 */
	public String getSelectedName() {
		return controller.getSelectedName();
	}

	/**
	 * サブ品目コンボボックスを設定
	 * 
	 * @param combo
	 */
	public void setSubComboBox(TMlitSubItemComboBox combo) {
		this.subCombo = combo;
	}
}
