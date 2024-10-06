package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;

/**
 * 台帳選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TBookSelectorController extends TController {

	/** 台帳選択コンポーネント */
	protected TBookSelector field;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TBookSelectorController(TBookSelector field) {
		this.field = field;
		init();
		initEvent();
	}

	/**
	 * 初期化
	 */
	public void init() {
		field.rdoOwn.setSelected(true);
	}

	@Override
	public void start() {
		//
	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * 選択された帳簿を返す
	 * 
	 * @return 選択された帳簿
	 */
	public AccountBook getAccountBook() {
		if (field.rdoOwn.isSelected()) {
			return AccountBook.OWN;
		} else if (field.rdoIfrs.isSelected()) {
			return AccountBook.IFRS;
		}
		return null;
	}

	/**
	 * 帳簿を設定
	 * 
	 * @param accountBook 選択された帳簿
	 */
	public void setAccountBook(AccountBook accountBook) {
		if (AccountBook.IFRS == accountBook) {
			field.rdoIfrs.setSelected(true);
		} else {
			field.rdoOwn.setSelected(true);
		}
	}

	/**
	 * イベント構成
	 */
	public void initEvent() {
		field.rdoOwn.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					setCurrencySelector();
				}
			}
		});

		field.rdoIfrs.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					setCurrencySelector();
				}
			}

		});
	}

	/**
	 * 台帳通貨選択を設定する。
	 */
	public void setCurrencySelector() {

		TBookCurrencySelector tBookCurrencySelector = field.tBookCurrencySelector;

		if (tBookCurrencySelector != null) {
			// 自国会計帳簿
			if (field.rdoOwn.isSelected()) {
				tBookCurrencySelector.rdoKey.setSelected(true);// 自国通貨
				tBookCurrencySelector.rdoKey.setEnabled(false);// 自国通貨
				tBookCurrencySelector.rdoFunctional.setEnabled(false);// 機能通貨
			} else {
				tBookCurrencySelector.rdoKey.setEnabled(true);// 自国通貨
				tBookCurrencySelector.rdoFunctional.setEnabled(true);// 機能通貨
			}
		}
	}

}
