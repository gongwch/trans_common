package jp.co.ais.trans2.common.model.ui;

import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;

/**
 * 通貨別外貨出力選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TCurrencySelectorController extends TController {

	/** フィールド */
	protected TCurrencySelector field;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TCurrencySelectorController(TCurrencySelector field) {
		this.field = field;
		init();
	}

	@Override
	public void start() {
		//
	}

	/**
	 * 初期化
	 */
	protected void init() {
		addEvent();
		field.rdoTrue.setSelected(true);
	}

	/**
	 * イベント定義
	 */
	protected void addEvent() {
		field.rdoTrue.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					rdoTrue_Click();
				}
			}

		});

		field.rdoFalse.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					rdoFalse_Click();
				}
			}

		});

	}

	@Override
	public TPanelBusiness getPanel() {
		return null;
	}

	/**
	 * [する]押下
	 */
	public void rdoTrue_Click() {
		field.currencyReference.setEditable(true);
	}

	/**
	 * [しない]押下
	 */
	public void rdoFalse_Click() {
		field.currencyReference.clear();
		field.currencyReference.setEditable(false);
	}

}
