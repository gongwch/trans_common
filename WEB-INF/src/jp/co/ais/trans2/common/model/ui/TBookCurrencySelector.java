package jp.co.ais.trans2.common.model.ui;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 台帳通貨選択コンポーネント
 * 
 * @author AIS
 */
public class TBookCurrencySelector extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -8446725749481416463L;

	/** 自国通貨 */
	public TRadioButton rdoKey;

	/** 機能通貨 */
	public TRadioButton rdoFunctional;

	/** コントローラ */
	protected TBookCurrencySelectorController controller;

	/**
	 * コンストラクタ
	 */
	public TBookCurrencySelector() {
		this(true);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param title タイトル表示かどうか
	 */
	public TBookCurrencySelector(boolean title) {
		initComponents();
		allocateComponents(title);

		controller = new TBookCurrencySelectorController(this);
	}

	/**
	 * コンポーネントの初期化
	 */
	public void initComponents() {
		rdoKey = new TRadioButton();
		rdoFunctional = new TRadioButton();
	}

	/**
	 * コンポーネントの配置
	 * 
	 * @param title タイトル表示かどうか
	 */
	public void allocateComponents(boolean title) {

		if (title) {
			setLangMessageID("C10549"); // 表示通貨

		} else {
			setBorder(TBorderFactory.createTitledBorder(TModelUIUtil.getWord("C10549"))); // 表示通貨
			this.titlePanel.setVisible(false);
			mainPanel.setOpaque(false);
		}

		setSize(110, 75);
		TGuiUtil.setComponentSize(this, this.getSize());

		// 自国通貨
		rdoKey.setLangMessageID("C11083"); // 自国通貨
		rdoKey.setSize(80, 20);
		rdoKey.setLocation(15, title ? 5 : 0);
		add(rdoKey);

		// 機能通貨
		rdoFunctional.setLangMessageID("C11084"); // 機能通貨
		rdoFunctional.setSize(80, 20);
		rdoFunctional.setLocation(15, title ? 30 : 25);
		add(rdoFunctional);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdoKey);
		bg.add(rdoFunctional);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		rdoKey.setTabControlNo(tabControlNo);
		rdoFunctional.setTabControlNo(tabControlNo);
	}

	/**
	 * 表示通貨を返す
	 * 
	 * @return 表示通貨
	 */
	public CurrencyType getCurrencyType() {
		return controller.getCurrencyType();
	}

	/**
	 * 表示通貨の設定
	 * 
	 * @param currencyType 表示通貨
	 */
	public void setCurrencyType(CurrencyType currencyType) {
		controller.setCurrencyType(currencyType);
	}

}
