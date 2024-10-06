package jp.co.ais.trans2.common.model.ui;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * 通貨別外貨出力選択コンポーネント
 * 
 * @author AIS
 */
public class TCurrencySelector extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -3116302500758346513L;

	/** コントローラ */
	protected TCurrencySelectorController controller;

	/** する */
	public TRadioButton rdoTrue;

	/** しない */
	public TRadioButton rdoFalse;

	/** 通貨フィールド */
	public TCurrencyReference currencyReference;

	/**
	 * 
	 *
	 */
	public TCurrencySelector() {

		initComponents();

		allocateComponents();

		// コントローラ生成
		controller = new TCurrencySelectorController(this);

	}

	/**
	 * コンポーネントの初期化
	 */
	public void initComponents() {
		rdoTrue = new TRadioButton();
		rdoFalse = new TRadioButton();
		currencyReference = new TCurrencyReference();
	}

	/**
	 * コンポーネントの配置
	 */
	public void allocateComponents() {

		setLangMessageID("C11086"); // 通貨別外貨表示
		setSize(175, 75);

		// する
		rdoTrue.setLangMessageID("C02100"); // する
		rdoTrue.setSize(50, 20);
		rdoTrue.setLocation(15, 5);
		add(rdoTrue);

		// 通貨選択
		currencyReference.btn.setMargin(new Insets(0, 0, 0, 0));
		currencyReference.btn.setLangMessageID("C00371"); // 通貨
		currencyReference.btn.setMaximumSize(new Dimension(50, 20));
		currencyReference.btn.setMinimumSize(new Dimension(50, 20));
		currencyReference.btn.setPreferredSize(new Dimension(50, 20));
		currencyReference.name.setVisible(false);
		currencyReference.resize();
		currencyReference.setLocation(70, 7);
		add(currencyReference);

		// しない
		rdoFalse.setLangMessageID("C02099"); // しない
		rdoFalse.setSize(80, 20);
		rdoFalse.setLocation(15, 30);
		add(rdoFalse);

		ButtonGroup bg = new ButtonGroup();
		bg.add(rdoTrue);
		bg.add(rdoFalse);

	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		rdoTrue.setTabControlNo(tabControlNo);
		currencyReference.setTabControlNo(tabControlNo);
		rdoFalse.setTabControlNo(tabControlNo);
	}

}
