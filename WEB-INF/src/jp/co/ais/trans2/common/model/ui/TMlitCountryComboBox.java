package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.mlit.region.*;

/**
 * 輸送実績地域コンボボックス
 */
public class TMlitCountryComboBox extends TLabelComboBox {

	/** コントローラ */
	protected TMlitCountryComboBoxController controller;
	
	/** true:空白選択肢がある */
	protected boolean hasBlank = false;

	/**
	 * 
	 */
	public TMlitCountryComboBox() {
		this(false);
	}

	/**
	 * @param hasBlank
	 */
	public TMlitCountryComboBox(boolean hasBlank) {

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
	protected TMlitCountryComboBoxController createController() {
		return new TMlitCountryComboBoxController(this);
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

		label.setLangMessageID("CBL481"); // MLIT Country
		setLabelSize(75);
		setComboSize(180);
		setSize(260, 20);
	}

	/**
	 * @return コントローラ
	 */
	public TMlitCountryComboBoxController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public YJRegionSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * コンボボックスを現在の状況に合わせリフレッシュ
	 */
	public void reflesh() {
		controller.refleshCombo();
	}
	
	/**
	 * 選択された国コードを返す
	 * 
	 * @return 選択された国コード
	 */
	public String getSelectedCountryCode() {
		return controller.getSelectedCountryCode();
	}

	/**
	 * 国コードを設定する
	 * 
	 * @param code 国コード
	 */
	public void setSelectedCountryCode(String code) {
		controller.setSelectedCountryCode(code);
	}
}
