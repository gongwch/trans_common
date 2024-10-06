package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;

/**
 * 伝票のステータス選択コンポーネント
 * 
 * @author AIS
 */
public class TSlipStateSelector extends TTitlePanel {

	/** serialVersionUID */
	private static final long serialVersionUID = 5229926607981299214L;

	/** コントローラ */
	protected TSlipStateSelectorController controller;

	/** 更新区分[登録] */
	public TCheckBox chkEntry;

	/** 更新区分[承認] */
	public TCheckBox chkApprove;

	/**
	 * 
	 *
	 */
	public TSlipStateSelector() {

		initComponents();

		allocateComponents();

		// コントローラ生成
		controller = new TSlipStateSelectorController(this);

	}

	/**
	 * コンポーネントの初期化
	 */
	public void initComponents() {
		chkEntry = new TCheckBox();
		chkApprove = new TCheckBox();
	}

	/**
	 * コンポーネントの配置
	 */
	public void allocateComponents() {

		setLangMessageID("C01069"); // 更新区分
		setSize(90, 75);

		// 登録
		chkEntry.setLangMessageID("C01258"); // 登録
		chkEntry.setSize(50, 20);
		chkEntry.setLocation(15, 5);
		add(chkEntry);

		// 承認
		chkApprove.setLangMessageID("C01168"); // 承認
		chkApprove.setSize(50, 20);
		chkApprove.setLocation(15, 30);
		add(chkApprove);

	}

	/**
	 * 登録を含むか
	 * 
	 * @return 登録を含むか
	 */
	public boolean isEntry() {
		return controller.isEntry();
	}

	/**
	 * 登録を含むか設定
	 * 
	 * @param isEntry 登録を含むか
	 */
	public void setEntry(boolean isEntry) {
		controller.setEntry(isEntry);
	}

	/**
	 * 承認を含むか
	 * 
	 * @return 承認を含むか
	 */
	public boolean isApprove() {
		return controller.isApprove();
	}

	/**
	 * 承認を含むか設定
	 * 
	 * @param isApprove 承認を含むか
	 */
	public void setApprove(boolean isApprove) {
		controller.setApprove(isApprove);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		chkEntry.setTabControlNo(tabControlNo);
		chkApprove.setTabControlNo(tabControlNo);
	}

}
