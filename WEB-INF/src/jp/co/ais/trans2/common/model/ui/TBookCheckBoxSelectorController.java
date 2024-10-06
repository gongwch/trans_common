package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.define.*;

/**
 * 台帳選択コンポーネント(チェックボックス版)のコントローラ
 * 
 * @author AIS
 */
public class TBookCheckBoxSelectorController extends TController {

	/** 台帳選択コンポーネント */
	protected TBookCheckBoxSelector field;

	/**
	 * コンストラクタ
	 * 
	 * @param field フィールド
	 */
	public TBookCheckBoxSelectorController(TBookCheckBoxSelector field) {
		this.field = field;
		init();
	}

	/**
	 * 初期化
	 */
	public void init() {
		field.chkOwn.setSelected(true);
		field.chkIfrs.setSelected(true);
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
		if (field.chkOwn.isSelected() && !field.chkIfrs.isSelected()) {
			return AccountBook.OWN;
		} else if (!field.chkOwn.isSelected() && field.chkIfrs.isSelected()) {
			return AccountBook.IFRS;
		} else if (field.chkOwn.isSelected() && field.chkIfrs.isSelected()) {
			return AccountBook.BOTH;
		}
		return null;
	}

	/**
	 * 台帳が選択されているかどうか
	 * 
	 * @return false：一つも選択されていない
	 */
	public boolean isSelected() {

		if (!field.chkOwn.isSelected() && !field.chkIfrs.isSelected()) {
			return false;
		}
		return true;
	}

}
