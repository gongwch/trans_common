package jp.co.ais.trans2.master.model.ui;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 科目マスタチェックボックスのコンポーネント<br>
 * 航海収支計算フラグ追加版
 */
public class TItemStatusVoyageUnit extends TItemStatusUnit {

	/** 航海収支計算フラグ */
	public TCheckBox chkVoyage;

	/**
	 * コンストラクタ
	 * 
	 * @param company
	 */
	public TItemStatusVoyageUnit(Company company) {
		super(company);
	}

	/**
	 * コンポーネントの初期化。主にインスタンスの生成を行います。
	 */
	@Override
	public void initComponents() {
		super.initComponents();

		chkVoyage = new TCheckBox();
	}

	/**
	 * コンポーネントの配置を行います。
	 */
	@Override
	public void allocateComponents() {
		super.allocateComponents();

		setLayout(null);
		setSize(700, 540);

		// 航海収支計算フラグ
		chkVoyage.setLangMessageID("C11602"); // 航海収支計算フラグ
		chkVoyage.setSize(180, 20);
		if (chkOccurDate.isVisible()) {
			chkVoyage.setLocation(0, chkOccurDate.getY() + 20);
		} else {
			chkVoyage.setLocation(0, chksirzeiflg.getY() + 20);
		}
		add(chkVoyage);
	}

	/**
	 * コンポーネントのタブ順の設定を行います。
	 * 
	 * @param tabControlNo
	 */
	@Override
	public void setTabControlNo(int tabControlNo) {
		super.setTabControlNo(tabControlNo);

		chkVoyage.setTabControlNo(tabControlNo);
	}
}
