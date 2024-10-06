package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 新送金目的マスタの編集画面
 */
public class MP0100RemittancePurposeMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 送金目的コード */
	public TLabelField ctrlPurposeCode;

	/** 国際収支コード(リファレンス) */
	public TBalanceAccountsReference ctrlBalanceCode;

	/** 送金目的名称 */
	public TLabelHalfAngleTextField ctrlPurposeName;

	/**
	 * コンストラクタ
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MP0100RemittancePurposeMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {

		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlPurposeCode = new TLabelField();
		ctrlBalanceCode = new TBalanceAccountsReference();
		ctrlPurposeName = new TLabelHalfAngleTextField();
	}

	@Override
	public void allocateComponents() {
		setSize(500, 200);

		// 確定ボタン
		int x = 255 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x + 90, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 255;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x + 90, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		x = 10;
		int y = 10;

		// 送金目的コード
		ctrlPurposeCode.setLabelSize(110);
		ctrlPurposeCode.setFieldSize(80);
		ctrlPurposeCode.setSize(195, 20);
		ctrlPurposeCode.setLocation(x, y);
		ctrlPurposeCode.setLangMessageID("C00793"); // 送金目的コード
		ctrlPurposeCode.setMaxLength(10);
		ctrlPurposeCode.setImeMode(false);
		ctrlPurposeCode.setAllowedSpace(false);
		pnlBody.add(ctrlPurposeCode);

		x += 35;
		y += ctrlPurposeCode.getHeight() + 5;

		// 国際収支コード
		ctrlBalanceCode.setLocation(x, y);
		ctrlBalanceCode.btn.setLangMessageID("C11838"); // 国際収支
		pnlBody.add(ctrlBalanceCode);

		x = ctrlPurposeCode.getX();
		y += ctrlPurposeCode.getHeight() + 5;

		// 送金目的名称
		ctrlPurposeName.setLangMessageID("C11840"); // 送金目的名称
		ctrlPurposeName.setSize(265, 20);
		ctrlPurposeName.setLabelSize(110);
		ctrlPurposeName.setFieldSize(150);
		ctrlPurposeName.setLocation(x, y);
		ctrlPurposeName.setMaxLength(22);
		pnlBody.add(ctrlPurposeName);
	}

	@Override
	public void setTabIndex() {

		int i = 0;
		ctrlPurposeCode.setTabControlNo(i++);
		ctrlBalanceCode.setTabControlNo(i++);
		ctrlPurposeName.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}