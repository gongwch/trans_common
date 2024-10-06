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
 * 科目集計マスタの複写画面
 */
public class MG0071ItemSummaryMasterCopyDialog extends TDialog {

	/** 縦幅固定値 */
	protected final int BUTTON_HEIGHT = 25;

	/** 横幅固定値 */
	protected final int BUTTON_WIDTH = 110;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 科目体系 */
	public TItemOrganizationReference ctrlItemOrganizationReference;

	/** 有効期限切れチェックボックス */
	public TCheckBox chkOutputTermEnd;

	/**
	 * コンストラクタ
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0071ItemSummaryMasterCopyDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlItemOrganizationReference = new TItemOrganizationReference();
		chkOutputTermEnd = new TCheckBox();
	}

	@Override
	public void allocateComponents() {

		setSize(500, 170);

		pnlHeader.setLayout(null);

		// 確定ボタン
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnSettle.setLocation(getWidth() - 245, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		// Body初期化
		pnlBody.setLayout(null);

		// 科目体系
		ctrlItemOrganizationReference.setLocation(10, 5);
		TGuiUtil.setComponentSize(ctrlItemOrganizationReference, 300, 50);
		pnlBody.add(ctrlItemOrganizationReference);

		// 有効期間切れ表示(隠し)
		chkOutputTermEnd.setLangMessageID("C11089");
		chkOutputTermEnd.setLocation(0, 0);
		chkOutputTermEnd.setSize(0, 0);
		pnlBody.add(chkOutputTermEnd);

	}

	@Override
	/**
	 * Tab順定義
	 */
	public void setTabIndex() {
		int i = 1;
		ctrlItemOrganizationReference.setTabControlNo(i++);
		chkOutputTermEnd.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}