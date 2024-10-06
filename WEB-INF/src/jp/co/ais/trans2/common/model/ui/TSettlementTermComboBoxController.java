package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.SettlementTerm;

/**
 * 決算期間選択コンボボックスのコントローラ
 * 
 * @author AIS
 */
public class TSettlementTermComboBoxController extends TController {

	/** コンボボックス */
	protected TSettlementTermComboBox comboBox;

	/**
	 * @param comboBox フィールド
	 */
	public TSettlementTermComboBoxController(TSettlementTermComboBox comboBox) {

		this.comboBox = comboBox;

		init();

	}

	/**
	 * 初期化
	 */
	protected void init() {

		// コンボボックス生成
		getList();

	}

	/**
	 * コンボボックスのリストを返す
	 * 
	 */
	protected void getList() {

		comboBox.getComboBox().addItem(
			new TComboBox.TTextValue(getWord(SettlementTerm.getSettlementTermName(SettlementTerm.YEAR)),
				SettlementTerm.YEAR));
		comboBox.getComboBox().addItem(
			new TComboBox.TTextValue(getWord(SettlementTerm.getSettlementTermName(SettlementTerm.HALF)),
				SettlementTerm.HALF));
		comboBox.getComboBox().addItem(
			new TComboBox.TTextValue(getWord(SettlementTerm.getSettlementTermName(SettlementTerm.QUARTER)),
				SettlementTerm.QUARTER));
		comboBox.getComboBox().addItem(
			new TComboBox.TTextValue(getWord(SettlementTerm.getSettlementTermName(SettlementTerm.MONTH)),
				SettlementTerm.MONTH));

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 選択された組織コードを返す
	 * 
	 * @return 選択された組織コード
	 */
	public SettlementTerm getSelectedSettlementTerm() {
		return (SettlementTerm) comboBox.getComboBox().getSelectedItemValue();
	}

	/**
	 * 指定の決算期間を選択する
	 * @param term 
	 */
	public void setSelectedSettlementTerm(SettlementTerm term) {
		comboBox.getComboBox().setSelectedItemValue(term);
	}

}
