package jp.co.ais.trans2.common.gui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;

/**
 * 任意選択(個別指定)コンポーネント
 * 
 * @author AIS
 */
public abstract class TOptionalSelector extends TPanel {

	/** 個別選択ボタン */
	public TButton btn;

	/** 選択一覧 */
	public TComboBox cbo;

	/** 会社出力単位 */
	protected TCompanyOrganizationUnit companyOrgUnit = null;

	/**
	 * TTableのカラム定義
	 * 
	 * @author AIS
	 */
	public enum SC {
		/** コード */
		code,

		/** 名称 */
		names
	}

	/**
	 * コンストラクタ.
	 */
	public TOptionalSelector() {

		initComponents();

		allocateComponents();
	}

	/**
	 * 初期化
	 */
	public void initComponents() {
		btn = new TButton();
		cbo = new TComboBox();
	}

	/**
	 * 配置
	 */
	public void allocateComponents() {

		setSize(333, 20);
		setLayout(null);
		setOpaque(false);

		// 個別指定ボタン
		btn.setSize(20, 80);
		btn.setOpaque(false);
		btn.setLocation(0, 0);
		btn.setLangMessageID("C11088"); // 任意指定
		add(btn);

		// 選択一欄
		cbo.setSize(249, 20);
		cbo.setLocation(80, 0);
		cbo.setOpaque(false);
		add(cbo);
	}

	/**
	 * コントローラのgetter
	 * 
	 * @return コントローラ
	 */
	public abstract TOptionalSelectorController getController();

	/**
	 * タブ順設定
	 * 
	 * @param index タブ順
	 */
	public void setTabControlNo(int index) {
		btn.setTabControlNo(index);
		cbo.setTabControlNo(index);
	}

	/**
	 * テーブル一覧のリフレッシュ
	 */
	public void refresh() {
		if (getController() != null && getController().getDialog() != null) {
			getController().refresh();
			getController().saveListData();
		}
	}

	/**
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enabled) {
		btn.setEnabled(enabled);
		cbo.setEnabled(enabled);
	}

	/**
	 * 選択されたコードリストを返す
	 * 
	 * @return 選択されたコードリスト
	 */
	public List<String> getCodeList() {
		return getController().getCodeList();
	}

	/**
	 * クリアする
	 */
	public void clear() {
		getController().clear();
	}

	/**
	 * 検索条件のコードリストをセット（初期値）
	 * 
	 * @param codeList
	 */
	public void setCodeList(List<String> codeList) {
		getController().setCodeList(codeList);
	}

	/**
	 * 会社出力単位の取得
	 * 
	 * @return companyOrgUnit 会社出力単位
	 */
	public TCompanyOrganizationUnit getCompanyOrgUnit() {
		return companyOrgUnit;
	}

	/**
	 * 会社出力単位の設定
	 * 
	 * @param companyOrgUnit 会社出力単位
	 */
	public void setCompanyOrgUnit(TCompanyOrganizationUnit companyOrgUnit) {
		this.companyOrgUnit = companyOrgUnit;
	}

}
