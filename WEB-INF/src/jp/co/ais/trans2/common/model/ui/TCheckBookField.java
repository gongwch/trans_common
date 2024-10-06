package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 帳簿選択フィールド
 * 
 * @author AIS
 */
public class TCheckBookField extends TTitlePanel {

	/** 自国帳簿 */
	public TCheckBox chkNationalBook;

	/** IFRS帳簿 */
	public TCheckBox chkIFRSBook;

	/** 高さ */
	public static final int height = 20;

	/**
	 * コンストラクタ.
	 */
	public TCheckBookField() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

	}

	/**
	 * コンポーネント
	 */
	protected void initComponents() {
		chkNationalBook = new TCheckBox();
		chkIFRSBook = new TCheckBox();
	}

	/**
	 * コンポーネントを配置する
	 */
	protected void allocateComponents() {

		setSize(130, 75);

		// 帳簿選択
		setLangMessageID("C11103");

		chkNationalBook.setSelected(true);
		chkNationalBook.setSize(100, height);
		// 自国帳簿
		chkNationalBook.setLangMessageID("C11104");
		chkNationalBook.setLocation(15, 5);
		add(chkNationalBook);

		chkIFRSBook.setSelected(true);
		chkIFRSBook.setSize(100, height);
		// IFRS帳簿
		chkIFRSBook.setLangMessageID("C11105");
		chkIFRSBook.setLocation(15, 30);
		add(chkIFRSBook);

	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		chkNationalBook.setTabControlNo(tabControlNo);
		chkIFRSBook.setTabControlNo(tabControlNo);
	}

	/**
	 * @return
	 */
	public TCheckBox getChkNationalBook() {
		return chkNationalBook;
	}

	/**
	 * @return
	 */
	public TCheckBox getChkIFRSBook() {
		return chkIFRSBook;
	}

	/**
	 * 編集状態を設定する
	 * 
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		chkNationalBook.setEnabled(isEditable);
		chkIFRSBook.setEnabled(isEditable);
	}

	/**
	 * クリア
	 */
	public void clear() {
		chkNationalBook.setSelected(false);
		chkIFRSBook.setSelected(false);
	}

	/**
	 * いずれかの帳簿が選択されているかを返す
	 * 
	 * @return いずれかの帳簿が選択されているか
	 */
	public boolean isSelected() {
		return chkNationalBook.isSelected() || chkIFRSBook.isSelected();
	}

	/**
	 * 選択状態の帳簿を返す
	 * 
	 * @return
	 */
	public AccountBook getAccountBook() {
		if (chkIFRSBook.isSelected() && chkNationalBook.isSelected()) {
			return AccountBook.BOTH;
		} else if (chkNationalBook.isSelected()) {
			return AccountBook.OWN;
		} else if (chkIFRSBook.isSelected()) {
			return AccountBook.IFRS;
		} else {
			return null;
		}

	}
}
