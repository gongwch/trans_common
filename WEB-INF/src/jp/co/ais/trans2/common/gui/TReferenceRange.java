package jp.co.ais.trans2.common.gui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.Util;

/**
 * 範囲検索フィールド
 * 
 * @author AIS
 */
public abstract class TReferenceRange extends TPanel {

	/**
	 * 
	 *
	 */
	public TReferenceRange() {
		initComponents();
		allocateComponents();
		init();
	}

	/**
	 * 初期化
	 */
	protected void init() {
		// for override
	}

	/**
	 * 開始フィールドのgetter
	 * 
	 * @return 開始フィールド
	 */
	public abstract TReference getFieldFrom();

	/**
	 * 終了フィールドのgetter
	 * 
	 * @return 終了フィールド
	 */
	public abstract TReference getFieldTo();

	/**
	 * 初期化
	 */
	public abstract void initComponents();

	/**
	 * コンポーネントの配置を行う。
	 */
	public void allocateComponents() {

		setLayout(null);
		setOpaque(false);

		getFieldFrom().setLocation(0, 0);
		// 開　　始
		getFieldFrom().btn.setLangMessageID("C01012");
		add(getFieldFrom());
		getFieldTo().setLocation(0, getFieldTo().getHeight());
		// 終　　了
		getFieldTo().btn.setLangMessageID("C01143");
		add(getFieldTo());

		reSize();

		getFieldFrom().setCheckExist(false);
		getFieldTo().setCheckExist(false);
	}

	/**
	 * サイズの再反映
	 */
	public void reSize() {
		setSize(getFieldFrom().getWidth(), getFieldFrom().getHeight() + getFieldTo().getHeight());

	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		getFieldFrom().setTabControlNo(tabControlNo);
		getFieldTo().setTabControlNo(tabControlNo);
	}

	/**
	 * 入力された開始コードを返す
	 * 
	 * @return 入力された開始コード
	 */
	public String getCodeFrom() {
		return getFieldFrom().code.getText();
	}

	/**
	 * 入力された終了コードを返す
	 * 
	 * @return 入力された終了コード
	 */
	public String getCodeTo() {
		return getFieldTo().code.getText();
	}

	/**
	 * 開始コードのセット
	 * 
	 * @param txt 開始コード
	 */
	public void setCodeFrom(String txt) {
		getFieldFrom().code.setText(txt);
	}

	/**
	 * 終了コードのセット
	 * 
	 * @param txt 終了コード
	 */
	public void setCodeTo(String txt) {
		getFieldTo().code.setText(txt);
	}

	/**
	 * 開始コンポのentity初期化
	 */
	public void refleshEntityFrom() {
		getFieldFrom().refleshAndShowEntity();
	}

	/**
	 * 終了コンポのentity初期化
	 */
	public void refleshEntityTo() {
		getFieldTo().refleshAndShowEntity();
	}

	/**
	 * クリア
	 */
	public void clear() {
		getFieldFrom().clear();
		getFieldTo().clear();
	}

	/**
	 * Editableの設定
	 * 
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		getFieldFrom().setEditable(editable);
		getFieldTo().setEditable(editable);
	}

	/**
	 * 大小チェック
	 * 
	 * @return true(問題無し) / false(エラーあり)
	 */
	public boolean isSmallerFrom() {
		return (Util.isSmallerThen(getFieldFrom().getCode(), getFieldTo().getCode()));
	}

	/**
	 * コードの存在チェックをするか設定します
	 * 
	 * @param checkExist true:チェックする
	 */
	public void setCheckExist(boolean checkExist) {
		getFieldFrom().setCheckExist(checkExist);
		getFieldTo().setCheckExist(checkExist);
	}
}
