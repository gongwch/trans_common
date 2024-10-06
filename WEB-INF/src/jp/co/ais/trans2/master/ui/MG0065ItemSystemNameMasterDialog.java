package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;

/**
 * MG0065-ItemSystemMaster - 科目体系名称マスタ - ダイアログ画面
 * 
 * @author AIS
 */
public class MG0065ItemSystemNameMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 科目体系コード */
	public TLabelField ctrlCode;

	/** 科目体系名称 */
	public TLabelField ctrlName;

	/** 科目体系略称 */
	public TLabelField ctrlNameS;

	/** 科目体系検索名称 */
	public TLabelField ctrlNameK;

	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 * @param mordal
	 */
	public MG0065ItemSystemNameMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlCode = new TLabelField();
		ctrlName = new TLabelField();
		ctrlNameS = new TLabelField();
		ctrlNameK = new TLabelField();
	}

	@Override
	public void allocateComponents() {

		setSize(600, 230);

		// Header初期化
		pnlHeader.setLayout(null);

		// 確定ボタン
		btnSettle.setSize(25, 110);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setLocation(getWidth() - 245, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		btnClose.setSize(25, 110);
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		// Body初期化
		pnlBody.setLayout(null);

		// 科目体系コード
		ctrlCode.setFieldSize(40);
		ctrlCode.setLangMessageID("C00617");
		ctrlCode.setMaxLength(2);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		ctrlCode.setLocation(20, 10);
		pnlBody.add(ctrlCode);

		// 科目体系名称
		ctrlName.setFieldSize(400);
		ctrlName.setLangMessageID("C00618");
		ctrlName.setMaxLength(40);
		ctrlName.setLocation(20, 40);
		pnlBody.add(ctrlName);

		// 科目体系略称
		ctrlNameS.setFieldSize(200);
		ctrlNameS.setLangMessageID("C00619");
		ctrlNameS.setMaxLength(20);
		ctrlNameS.setLocation(20, 70);
		pnlBody.add(ctrlNameS);

		// 科目体系検索名称
		ctrlNameK.setFieldSize(400);
		ctrlNameK.setLabelSize(120);
		ctrlNameK.setLangMessageID("C00620");
		ctrlNameK.setMaxLength(40);
		ctrlNameK.setLocation(0, 100);
		pnlBody.add(ctrlNameK);

	}

	@Override
	/** Tab順定義 */
	public void setTabIndex() {
		int i = 1;
		ctrlCode.setTabControlNo(i++);
		ctrlName.setTabControlNo(i++);
		ctrlNameS.setTabControlNo(i++);
		ctrlNameK.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}