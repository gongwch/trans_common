package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;

/**
 * MG0320-SystemDivision - システム区分マスタ - ダイアログ画面
 * 
 * @author AIS
 */
public class MG0320SystemDivisionMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** システム区分 */
	public TLabelField ctrlKbn;

	/** システム区分名称 */
	public TLabelField ctrlKbnName;

	/** システム区分略称 */
	public TLabelField ctrlKbnNameS;

	/** システム区分検索名称 */
	public TLabelField ctrlKbnNameK;

	/** 外部システム区分コンボボックス */
	public TLabelComboBox ctrlExternalKbn;

	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 * @param mordal
	 */
	public MG0320SystemDivisionMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlKbn = new TLabelField();
		ctrlKbnName = new TLabelField();
		ctrlKbnNameS = new TLabelField();
		ctrlKbnNameK = new TLabelField();
		ctrlExternalKbn = new TLabelComboBox();

	}

	@Override
	public void allocateComponents() {

		setSize(500, 240);

		// Header初期化
		pnlHeader.setLayout(null);

		// 確定ボタン
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(getWidth() - 240 - HEADER_MARGIN_X, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		// Body初期化
		pnlBody.setLayout(null);

		int X = 10;
		// システム区分
		ctrlKbn.setFieldSize(35);
		ctrlKbn.setLangMessageID("C00980");
		ctrlKbn.setMaxLength(2);
		ctrlKbn.setImeMode(false);
		ctrlKbn.setAllowedSpace(false);
		ctrlKbn.setLocation(X + 30, 10);
		pnlBody.add(ctrlKbn);

		// システム区分名称
		ctrlKbnName.setFieldSize(310);
		ctrlKbnName.setLabelSize(130);
		ctrlKbnName.setLangMessageID("C00832");
		ctrlKbnName.setMaxLength(40);
		ctrlKbnName.setLocation(X, 40);
		pnlBody.add(ctrlKbnName);

		// システム区分略称
		ctrlKbnNameS.setFieldSize(200);
		ctrlKbnNameS.setLabelSize(130);
		ctrlKbnNameS.setLangMessageID("C00833");
		ctrlKbnNameS.setMaxLength(20);
		ctrlKbnNameS.setLocation(X, 70);
		pnlBody.add(ctrlKbnNameS);

		// システム区分検索名称
		ctrlKbnNameK.setFieldSize(200);
		ctrlKbnNameK.setLabelSize(130);
		ctrlKbnNameK.setLangMessageID("C00834");
		ctrlKbnNameK.setMaxLength(20);
		ctrlKbnNameK.setLocation(X, 100);
		pnlBody.add(ctrlKbnNameK);

		// 外部システム区分コンボボックス
		ctrlExternalKbn.setLabelSize(130);
		ctrlExternalKbn.setComboSize(140);
		ctrlExternalKbn.setLangMessageID("C01018");
		ctrlExternalKbn.setLocation(X, 130);
		initComboBox(ctrlExternalKbn.getComboBox());
		pnlBody.add(ctrlExternalKbn);

	}

	@Override
	/** Tab順定義 */
	public void setTabIndex() {
		int i = 1;
		ctrlKbn.setTabControlNo(i++);
		ctrlKbnName.setTabControlNo(i++);
		ctrlKbnNameS.setTabControlNo(i++);
		ctrlKbnNameK.setTabControlNo(i++);
		ctrlExternalKbn.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

	/**
	 * コンボボックス定義
	 * 
	 * @param comboBox
	 */
	protected void initComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(OuterSystemType.PACKAGE_USE,
			getWord(OuterSystemType.getName(OuterSystemType.PACKAGE_USE)));
		comboBox.addTextValueItem(OuterSystemType.PACKAGE_UNUSE,
			getWord(OuterSystemType.getName(OuterSystemType.PACKAGE_UNUSE)));
		comboBox.addTextValueItem(OuterSystemType.OUTER_SYSTEM,
			getWord(OuterSystemType.getName(OuterSystemType.OUTER_SYSTEM)));

	}
}