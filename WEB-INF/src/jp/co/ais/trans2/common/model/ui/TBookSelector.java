package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 台帳選択コンポーネント
 * 
 * @author AIS
 */
public class TBookSelector extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 3687486371935677293L;

	/** 自国会計帳簿 */
	public TRadioButton rdoOwn;

	/** IFRS会計帳簿 */
	public TRadioButton rdoIfrs;

	/** コントローラ */
	protected TBookSelectorController controller;

	/** 台帳通貨選択コンポーネント */
	public TBookCurrencySelector tBookCurrencySelector;

	/**
	 * コンポーネント
	 */
	public TBookSelector() {
		this(true);
	}

	/**
	 * コンポーネント
	 * 
	 * @param title タイトル表示かどうか
	 */
	public TBookSelector(boolean title) {
		initComponents();
		allocateComponents(title);

		controller = new TBookSelectorController(this);
	}

	/**
	 * コンポーネント
	 * 
	 * @param tBookCurrencySelector 台帳通貨選択コンポーネント
	 */
	public TBookSelector(TBookCurrencySelector tBookCurrencySelector) {
		this(tBookCurrencySelector, true);
	}

	/**
	 * コンポーネント
	 * 
	 * @param title タイトル表示かどうか
	 * @param tBookCurrencySelector 台帳通貨選択コンポーネント
	 */
	public TBookSelector(TBookCurrencySelector tBookCurrencySelector, boolean title) {
		this(title);

		this.tBookCurrencySelector = tBookCurrencySelector;
		controller.setCurrencySelector();
	}

	/**
	 * コンポーネントの初期化
	 */
	public void initComponents() {
		rdoOwn = new TRadioButton();
		rdoIfrs = new TRadioButton();
	}

	/**
	 * コンポーネントの配置
	 * 
	 * @param title タイトル表示かどうか
	 */
	public void allocateComponents(boolean title) {

		setSize(135, 75);

		if (title) {
			setLangMessageID("C10961"); // 帳簿区分

		} else {
			setBorder(TBorderFactory.createTitledBorder(TModelUIUtil.getWord("C10961"))); // 帳簿区分
			this.titlePanel.setVisible(false);
			mainPanel.setOpaque(false);
		}

		// 自国会計帳簿
		rdoOwn.setLangMessageID("C10982"); // 自国会計帳簿
		rdoOwn.setSize(120, 20);
		rdoOwn.setLocation(15, title ? 5 : 0);
		add(rdoOwn);

		// IFRS会計帳簿
		rdoIfrs.setLangMessageID("C10983"); // IFRS会計帳簿
		rdoIfrs.setSize(120, 20);
		rdoIfrs.setLocation(15, title ? 30 : 25);
		add(rdoIfrs);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdoOwn);
		bg.add(rdoIfrs);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		rdoOwn.setTabControlNo(tabControlNo);
		rdoIfrs.setTabControlNo(tabControlNo);
	}

	/**
	 * 選択された帳簿を返す
	 * 
	 * @return 選択された帳簿
	 */
	public AccountBook getAccountBook() {
		return controller.getAccountBook();
	}

	/**
	 * 帳簿を設定
	 * 
	 * @param accountBook 選択された帳簿
	 */
	public void setAccountBook(AccountBook accountBook) {
		controller.setAccountBook(accountBook);
	}

	/**
	 * 台帳通貨選択コンポーネント
	 * 
	 * @return 台帳通貨選択コンポーネント
	 */
	public TBookCurrencySelector getTBookCurrencySelector() {
		return tBookCurrencySelector;
	}

	/**
	 * 台帳通貨選択コンポーネント
	 * 
	 * @param bookCurrencySelector 台帳通貨選択コンポーネント
	 */
	public void setTBookCurrencySelector(TBookCurrencySelector bookCurrencySelector) {
		tBookCurrencySelector = bookCurrencySelector;
	}

}
