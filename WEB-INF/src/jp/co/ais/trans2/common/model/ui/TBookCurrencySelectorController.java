package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;

/**
 * 台帳通貨選択コンポーネントのコントローラ
 * 
 * @author AIS
 */
public class TBookCurrencySelectorController extends TController {

	/** 台帳通貨選択コンポーネント */
	protected TBookCurrencySelector field;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TBookCurrencySelectorController(TBookCurrencySelector field) {
		this.field = field;
		init();
	}

	/**
	 * 初期化
	 */
	public void init() {
		field.rdoKey.setSelected(true);
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
	 * 表示通貨を返す
	 * 
	 * @return 表示通貨
	 */
	public CurrencyType getCurrencyType() {
		if (field.rdoKey.isSelected()) {
			return CurrencyType.KEY;
		} else if (field.rdoFunctional.isSelected()) {
			return CurrencyType.FUNCTIONAL;
		}
		return null;
	}

	/**
	 * 表示通貨の設定
	 * 
	 * @param currencyType 表示通貨
	 */
	public void setCurrencyType(CurrencyType currencyType) {
		if (CurrencyType.FUNCTIONAL == currencyType) {
			field.rdoFunctional.setSelected(true);
		} else {
			field.rdoKey.setSelected(true);
		}
	}

}
