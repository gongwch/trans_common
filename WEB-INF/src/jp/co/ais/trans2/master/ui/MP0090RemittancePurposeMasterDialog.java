package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;

/**
 * MP0090-RemittancePurposeMaster - 送金目的マスタ - ダイアログ画面
 * 
 * @author AIS
 */
public class MP0090RemittancePurposeMasterDialog extends TDialog {

	/** 縦幅固定値 */
	protected final int BUTTON_HEIGHT = 25;

	/** 横幅固定値 */
	protected final int BUTTON_WIDTH = 110;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 送金目的コード */
	public TLabelField ctrlRemittancePurposeCode;

	/** 送金目的名カナ */
	public TLabelField ctrlRemittancePurpose;

	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 * @param mordal
	 */
	public MP0090RemittancePurposeMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlRemittancePurposeCode = new TLabelField();
		ctrlRemittancePurpose = new TLabelField();
	}

	@Override
	public void allocateComponents() {

		setSize(600, 160);

		// Header初期化
		pnlHeader.setLayout(null);

		// 確定ボタン
		btnSettle.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setLocation(getWidth() - 245, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		btnClose.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		// Body初期化
		pnlBody.setLayout(null);

		// 送金目的コード
		ctrlRemittancePurposeCode.setLabelSize(110);
		ctrlRemittancePurposeCode.setFieldSize(35);
		ctrlRemittancePurposeCode.setSize(215, 20);
		ctrlRemittancePurposeCode.setLocation(19, 20);
		ctrlRemittancePurposeCode.setLabelText(getWord("C00793"));
		ctrlRemittancePurposeCode.setMaxLength(4);
		ctrlRemittancePurposeCode.setImeMode(false);
		ctrlRemittancePurposeCode.setAllowedSpace(false);
		pnlBody.add(ctrlRemittancePurposeCode);

		// 送金目的
		ctrlRemittancePurpose.setLabelSize(110);
		ctrlRemittancePurpose.setFieldSize(380);
		ctrlRemittancePurpose.setSize(495, 20);
		ctrlRemittancePurpose.setLocation(50, 50);
		ctrlRemittancePurpose.setLabelText(getWord("C00947"));
		ctrlRemittancePurpose.setImeMode(true);
		ctrlRemittancePurpose.setMaxLength(22);
		pnlBody.add(ctrlRemittancePurpose);
	}

	@Override
	/** Tab順定義 */
	public void setTabIndex() {
		int i = 1;

		ctrlRemittancePurposeCode.setTabControlNo(i++);
		ctrlRemittancePurpose.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}