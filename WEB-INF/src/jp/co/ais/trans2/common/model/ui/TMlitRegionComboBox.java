package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;

/**
 * 輸送実績地域コンボボックス
 */
public class TMlitRegionComboBox extends TLabelComboBox {

	/** コントローラ */
	protected TMlitRegionComboBoxController controller;

	/** 輸送実績国コンボボックス */
	protected TMlitCountryComboBox couCombo;

	/** true:空白選択肢がある */
	protected boolean hasBlank = false;

	/**
	 * 
	 */
	public TMlitRegionComboBox() {
		this(false);
	}

	/**
	 * @param hasBlank
	 */
	public TMlitRegionComboBox(boolean hasBlank) {

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
	protected TMlitRegionComboBoxController createController() {
		return new TMlitRegionComboBoxController(this);
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

		label.setLangMessageID("CBL480"); // MLIT Region
		setLabelSize(75);
		setComboSize(180);
		setSize(260, 20);
	}

	/**
	 * @return コントローラ
	 */
	public TMlitRegionComboBoxController getController() {
		return controller;
	}

	/**
	 * 選択された地域コードを返す
	 * 
	 * @return 選択された地域コード
	 */
	public String getSelectedRegionCode() {
		return controller.getSelectedRegionCode();
	}

	/**
	 * 地域コードを設定する
	 * 
	 * @param code 地域コード
	 */
	public void setSelectedRegionCode(String code) {
		controller.setSelectedRegionCode(code);
	}

	/**
	 * 国コンボボックスを設定
	 * 
	 * @param combo
	 */
	public void setCountryComboBox(TMlitCountryComboBox combo) {
		this.couCombo = combo;
	}
}
