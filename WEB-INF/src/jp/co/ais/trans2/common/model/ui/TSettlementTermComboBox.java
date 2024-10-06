package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.define.SettlementTerm;

/**
 * 決算期間選択コンボボックス
 */
public class TSettlementTermComboBox extends TLabelComboBox {

	/** コントローラ */
	protected TSettlementTermComboBoxController controller;

	/**
	 * 
	 */
	public TSettlementTermComboBox() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		controller = new TSettlementTermComboBoxController(this);

	}

	protected void initComponents() {
		// 
	}

	protected void allocateComponents() {

		// 決算期間
		label.setLangMessageID("C11133");
		setLabelSize(60);
		setComboSize(100);
		setSize(165, 20);

	}

	/**
	 * @return
	 */
	public TSettlementTermComboBoxController getController() {
		return controller;
	}

	/**
	 * 選択された決算期間を返す
	 * 
	 * @return 選択された決算期間
	 */
	public SettlementTerm getSelectedSettlementTerm() {
		return controller.getSelectedSettlementTerm();
	}

	/**
	 * 指定の決算期間を選択する
	 */
	public void setSelectedSettlementTerm(SettlementTerm term) {
		controller.setSelectedSettlementTerm(term);
	}

}
