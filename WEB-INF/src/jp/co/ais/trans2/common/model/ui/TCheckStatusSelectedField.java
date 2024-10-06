package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * 新規/修正選択フィールド
 * 
 * @author AIS
 */
public class TCheckStatusSelectedField extends TTitlePanel {

	/** 新規 */
	public TCheckBox chkNew;

	/** 修正 */
	public TCheckBox chkMod;

	/** 高さ */
	public static final int height = 20;

	/**
	 * コンストラクタ.
	 */
	public TCheckStatusSelectedField() {

		// コンポーネントを初期化する
		initComponents();

		// コンポーネントを配置する
		allocateComponents();

	}

	/**
	 * コンポーネント
	 */
	protected void initComponents() {
		chkNew = new TCheckBox();
		chkMod = new TCheckBox();
	}

	/**
	 * コンポーネントを配置する
	 */
	protected void allocateComponents() {

		setSize(100, 75);

		// 選択
		setLangMessageID("C00324");

		chkNew.setSelected(true);
		chkNew.setSize(70, height);
		// 新規
		chkNew.setLangMessageID("C00303");
		chkNew.setLocation(15, 5);
		add(chkNew);

		chkMod.setSelected(true);
		chkMod.setSize(70, height);
		// 修正
		chkMod.setLangMessageID("C01760");
		chkMod.setLocation(15, 30);
		add(chkMod);

	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		chkNew.setTabControlNo(tabControlNo);
		chkMod.setTabControlNo(tabControlNo);
	}

	/**
	 * @return chkNew
	 */
	public TCheckBox getChkNew() {
		return chkNew;
	}

	/**
	 * @return chkMod
	 */
	public TCheckBox getChkMod() {
		return chkMod;
	}

	/**
	 * 編集状態を設定する
	 * 
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		chkNew.setEnabled(isEditable);
		chkMod.setEnabled(isEditable);
	}

	/**
	 * クリア
	 */
	public void clear() {
		chkNew.setSelected(false);
		chkMod.setSelected(false);
	}

	/**
	 * いずれかが選択されているかを返す
	 * 
	 * @return いずれかが選択されているか
	 */
	public boolean isSelected() {
		return chkNew.isSelected() || chkMod.isSelected();
	}

}
