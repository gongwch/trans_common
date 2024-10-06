package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.mlit.item.*;

/**
 * 輸送実績サブ品目コンボボックス
 */
public class TMlitSubItemComboBox extends TLabelComboBox {

	/** コントローラ */
	protected TMlitSubItemComboBoxController controller;

	/** true:空白選択肢がある */
	protected boolean hasBlank = false;

	/**
	 * 
	 */
	public TMlitSubItemComboBox() {
		this(false);
	}

	/**
	 * @param hasBlank
	 */
	public TMlitSubItemComboBox(boolean hasBlank) {

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
	protected TMlitSubItemComboBoxController createController() {
		return new TMlitSubItemComboBoxController(this);
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

		label.setLangMessageID("COP1233"); // MLIT SUB ITEM
		setLabelSize(75);
		setComboSize(180);
		setSize(260, 20);
	}

	/**
	 * @return コントローラ
	 */
	public TMlitSubItemComboBoxController getController() {
		return controller;
	}

	/**
	 * 検索条件を返す
	 * 
	 * @return 検索条件
	 */
	public YJItemSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * コンボボックスを現在の状況に合わせリフレッシュ
	 */
	public void reflesh() {
		controller.refleshCombo();
	}

	/**
	 * 選択されたサブ品目コードを返す
	 * 
	 * @return 選択されたサブ品目コード
	 */
	public String getSelectedCode() {
		return controller.getSelectedCode();
	}

	/**
	 * サブ品目コードを設定する
	 * 
	 * @param code サブ品目コード
	 */
	public void setSelectedCode(String code) {
		controller.setSelectedCode(code);
	}

	/**
	 * 選択されたサブ品目名称を返す
	 * 
	 * @return サブ品目名称
	 */
	public String getSelectedName() {
		return controller.getSelectedName();
	}

}
