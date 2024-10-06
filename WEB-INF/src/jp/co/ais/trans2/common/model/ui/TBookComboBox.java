package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.define.AccountBook;

/**
 * 帳簿選択コンボボックス
 */
public class TBookComboBox extends TLabelComboBox {

	/** コントローラ */
	protected TBookComboBoxController controller;

	/** 台帳通貨選択コンポーネント */
	public TBookCurrencyComboBox cmbBookCurrency;

	/**
	 * コンストラクタ
	 */
	public TBookComboBox() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

		controller = new TBookComboBoxController(this);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param cmbBookCurrency 表示通貨選択コンボボックス
	 */
	public TBookComboBox(TBookCurrencyComboBox cmbBookCurrency) {
		this();

		this.cmbBookCurrency = cmbBookCurrency;
		controller.setCurrencySelector();
	}

	/**
	 * コンポーネント初期化
	 */
	protected void initComponents() {
		//
	}

	/**
	 * コンポーネントの配置
	 */
	protected void allocateComponents() {

		// 帳簿区分
		label.setLangMessageID("C10961");
		setLabelSize(60);
		setComboSize(150);
		setSize(245, 20);
	}

	/**
	 * 選択されている台帳を返す
	 * 
	 * @return 選択されている台帳
	 */
	public AccountBook getAccountBook() {
		return controller.getAccountBook();
	}

	/**
	 * 指定の台帳を選択する
	 * 
	 * @param book
	 */
	public void selectAccountBook(AccountBook book) {
		controller.selectAccountBook(book);
	}

}
