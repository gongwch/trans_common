package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;

/**
 * 台帳選択コンボボックスのコントローラ
 * 
 * @author AIS
 */
public class TBookComboBoxController extends TController {

	/** コンボボックス */
	protected TBookComboBox comboBox;

	/**
	 * @param comboBox フィールド
	 */
	public TBookComboBoxController(TBookComboBox comboBox) {

		this.comboBox = comboBox;

		init();
		initEvent();
	}

	/**
	 * 初期化
	 */
	protected void init() {

		// コンボボックス生成
		getList();
	}

	/**
	 * 台帳生成
	 */
	protected void getList() {
		comboBox.getComboBox().addTextValueItem(AccountBook.OWN, getWord(AccountBook.getAccountBookName(AccountBook.OWN)));
		comboBox.getComboBox().addTextValueItem(AccountBook.IFRS, getWord(AccountBook.getAccountBookName(AccountBook.IFRS)));
	}

	/**
	 * イベント構成
	 */
	public void initEvent() {
		comboBox.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					setCurrencySelector();
				}
			}
		});
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 選択されている台帳を返す
	 * 
	 * @return 選択されている台帳
	 */
	public AccountBook getAccountBook() {
		return (AccountBook) comboBox.getSelectedItemValue();
	}

	/**
	 * 指定の台帳を選択する
	 * 
	 * @param book
	 */
	public void selectAccountBook(AccountBook book) {
		comboBox.setSelectedItemValue(book);
	}

	/**
	 * 台帳通貨選択を設定する。
	 */
	public void setCurrencySelector() {

		TBookCurrencyComboBox cmbBookCurrency = comboBox.cmbBookCurrency;

		if (cmbBookCurrency != null) {

			switch (getAccountBook()) {
				case OWN: // 自国会計帳簿
					cmbBookCurrency.setSelectedItemValue(CurrencyType.KEY); // 自国通貨ONLY
					cmbBookCurrency.setEnabled(false);
					break;

				default:
					cmbBookCurrency.setEnabled(true);
					break;
			}
		}
	}

}
