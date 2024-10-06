package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;

/**
 * 会社組織コンボボックス
 */
public class TCompanyOrganizationComboBox extends TLabelComboBox {

	/** コントローラ */
	protected TCompanyOrganizationComboBoxController controller;

	/** true:空白選択肢がある */
	protected boolean hasBlank = false;

	/**
	 * 
	 */
	public TCompanyOrganizationComboBox() {
		this(false);
	}

	/**
	 * @param hasBlank
	 */
	public TCompanyOrganizationComboBox(boolean hasBlank) {

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
	protected TCompanyOrganizationComboBoxController createController() {
		return new TCompanyOrganizationComboBoxController(this);
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

		label.setLangMessageID("C00335");
		setLabelSize(60);
		setComboSize(100);
		setSize(165, 20);

	}

	/**
	 * @return コントローラ
	 */
	public TCompanyOrganizationComboBoxController getController() {
		return controller;
	}

	/**
	 * 選択された組織コードを返す
	 * 
	 * @return 選択された組織コード
	 */
	public String getSelectedOrganizationCode() {
		return controller.getSelectedOrganizationCode();
	}

	/**
	 * 組織コードを設定する
	 * 
	 * @param code 組織コード
	 */
	public void setSelectedOrganizationCode(String code) {
		controller.setSelectedOrganizationCode(code);
	}

}
