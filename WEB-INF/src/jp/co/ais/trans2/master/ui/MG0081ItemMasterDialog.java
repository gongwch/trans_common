package jp.co.ais.trans2.master.ui;

import java.awt.*;

import jp.co.ais.trans2.master.model.ui.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 科目マスタの編集画面<br>
 * 航海収支計算フラグ追加版
 */
public class MG0081ItemMasterDialog extends MG0080ItemMasterDialog {

	/**
	 * コンストラクター
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0081ItemMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	/**
	 * コンポーネントの初期化
	 */
	@Override
	public void initComponents() {
		super.initComponents();

		chk = new TItemStatusVoyageUnit(company);
	}

	/**
	 * コンポーネントの配置
	 */
	@Override
	public void allocateComponents() {
		super.allocateComponents();

		setSize(790, 630);
	}

}
