package jp.co.ais.trans2.common.gui;

import java.awt.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * 範囲検索フィールド(三つ)
 * 
 * @author AIS
 */
public abstract class TReferenceTripleRange extends TPanel {

	/**
	 * コンストラクタ
	 */
	public TReferenceTripleRange() {
		init();
		initComponents();
		allocateComponents();
	}

	/**
	 * 初期化
	 */
	protected void init() {
		// for override
	}

	/**
	 * 初期化
	 */
	public abstract void initComponents();

	/**
	 * コンポーネント配置
	 */
	public void allocateComponents() {
		setLayout(null);
		setOpaque(false);

		setSize(500, 100);

		getFieldUp().setSize(450, 20);
		getFieldUp().btn.setLangMessageID("C00609");
		getFieldUp().code.setMaximumSize(new Dimension(50, 20));
		getFieldUp().code.setMinimumSize(new Dimension(50, 20));
		getFieldUp().code.setPreferredSize(new Dimension(50, 20));
		getFieldUp().name.setMaximumSize(new Dimension(300, 20));
		getFieldUp().name.setMinimumSize(new Dimension(300, 20));
		getFieldUp().name.setPreferredSize(new Dimension(300, 20));
		getFieldUp().setLocation(0, 0);
		add(getFieldUp());

		getFieldFrom().setSize(450, 20);
		getFieldFrom().btn.setLangMessageID("C01012");
		getFieldFrom().code.setMaximumSize(new Dimension(100, 20));
		getFieldFrom().code.setMinimumSize(new Dimension(100, 20));
		getFieldFrom().code.setPreferredSize(new Dimension(100, 20));
		getFieldFrom().name.setMaximumSize(new Dimension(250, 20));
		getFieldFrom().name.setMinimumSize(new Dimension(250, 20));
		getFieldFrom().name.setPreferredSize(new Dimension(250, 20));
		getFieldFrom().setLocation(0, 25);
		add(getFieldFrom());

		getFieldTo().setSize(450, 20);
		getFieldTo().btn.setLangMessageID("C01143");
		getFieldTo().code.setMaximumSize(new Dimension(100, 20));
		getFieldTo().code.setMinimumSize(new Dimension(100, 20));
		getFieldTo().code.setPreferredSize(new Dimension(100, 20));
		getFieldTo().name.setMaximumSize(new Dimension(250, 20));
		getFieldTo().name.setMinimumSize(new Dimension(250, 20));
		getFieldTo().name.setPreferredSize(new Dimension(250, 20));
		getFieldTo().setLocation(0, 50);
		add(getFieldTo());

		getFieldUp().setCheckExist(false);
		getFieldFrom().setCheckExist(false);
		getFieldTo().setCheckExist(false);
	}

	/**
	 * 上位条件フィールドのgetter
	 * 
	 * @return 上位条件フィールド
	 */
	public abstract TReference getFieldUp();

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
	 * クリア
	 */
	public void clear() {
		getFieldUp().clear();
		getFieldFrom().clear();
		getFieldTo().clear();
	}

	/**
	 * Editableの設定
	 * 
	 * @param editable
	 */
	public void setEditable(boolean editable) {
		getFieldUp().setEditable(editable);
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
	 * コードの存在チェックをするか設定
	 * 
	 * @param checkExist true:チェックする
	 */
	public void setCheckExist(boolean checkExist) {
		getFieldUp().setCheckExist(checkExist);
		getFieldFrom().setCheckExist(checkExist);
		getFieldTo().setCheckExist(checkExist);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		getFieldUp().setTabControlNo(tabControlNo);
		getFieldFrom().setTabControlNo(tabControlNo);
		getFieldTo().setTabControlNo(tabControlNo);
	}

	/**
	 * 入力された上位条件コードを返す
	 * 
	 * @return 入力された開始コード
	 */
	public String getCodeUp() {
		return getFieldUp().code.getText();
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
	 * 上位条件コードのセット
	 * 
	 * @param txt 上位条件コード
	 */
	public void setCodeUp(String txt) {
		getFieldUp().code.setText(txt);
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
}