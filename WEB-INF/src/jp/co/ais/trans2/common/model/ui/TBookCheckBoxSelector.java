package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.define.*;

/**
 * 台帳選択コンポーネント(チェックボックス版)
 * 
 * @author AIS
 */
public class TBookCheckBoxSelector extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 3687486371935677293L;

	/** 自国会計帳簿 */
	public TCheckBox chkOwn;

	/** IFRS会計帳簿 */
	public TCheckBox chkIfrs;

	/** コントローラ */
	protected TBookCheckBoxSelectorController controller;

	/**
	 * コンポーネント
	 */
	public TBookCheckBoxSelector() {

		initComponents();

		allocateComponents();

		controller = new TBookCheckBoxSelectorController(this);

	}

	/**
	 * コンポーネントの初期化
	 */
	public void initComponents() {
		chkOwn = new TCheckBox();
		chkIfrs = new TCheckBox();
	}

	/**
	 * コンポーネントの配置
	 */
	public void allocateComponents() {

		setLayout(null);
		// 帳簿区分
		setLangMessageID("C10961");
		setSize(135, 75);

		// 自国会計帳簿
		chkOwn.setLangMessageID("C10982");
		chkOwn.setSize(100, 20);
		chkOwn.setLocation(15, 18);
		add(chkOwn);

		// IFRS会計帳簿
		chkIfrs.setLangMessageID("C10983");
		chkIfrs.setSize(100, 20);
		chkIfrs.setLocation(15, 43);
		add(chkIfrs);

	}

	/**
	 * フォーカス処理（自国会計帳簿にフォーカス）
	 * 
	 * @see javax.swing.JComponent#requestFocus()
	 */
	@Override
	public void requestFocus() {
		chkOwn.requestFocus();
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		chkOwn.setTabControlNo(tabControlNo);
		chkIfrs.setTabControlNo(tabControlNo);
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
	 * 台帳が選択されているかどうか
	 * 
	 * @return false：一つも選択されていない
	 */
	public boolean isSelected() {
		return controller.isSelected();
	}

}
