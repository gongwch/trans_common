package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.CurrencyType;

/**
 * 表示通貨選択コンボボックスのコントローラ
 * 
 * @author AIS
 */
public class TBookCurrencyComboBoxController extends TController {

	/** コンボボックス */
	protected TBookCurrencyComboBox comboBox;

	/**
	 * @param comboBox フィールド
	 */
	public TBookCurrencyComboBoxController(TBookCurrencyComboBox comboBox) {

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
	 * 表示通貨生成
	 *
	 */
	protected void getList() {

		comboBox.getComboBox().addTextValueItem(
				CurrencyType.KEY, getWord(CurrencyType.getCurrencyTypeName(CurrencyType.KEY)));
		comboBox.getComboBox().addTextValueItem(
				CurrencyType.FUNCTIONAL, getWord(CurrencyType.getCurrencyTypeName(CurrencyType.FUNCTIONAL)));

	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 選択されている表示通貨を返す
	 * @return 選択されている表示通貨
	 */
	public CurrencyType getCurrencyType() {
		return (CurrencyType)comboBox.getSelectedItemValue();
	}

}
