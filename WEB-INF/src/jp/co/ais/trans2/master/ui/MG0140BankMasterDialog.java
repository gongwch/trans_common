package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * 銀行マスタの編集画面
 */
public class MG0140BankMasterDialog extends TDialog {

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 銀行コード */
	public TLabelField ctrlCode;

	/** 銀行支店コード */
	public TLabelField ctrlBranchCode;

	/** 銀行名称 */
	public TLabelField ctrlName;

	/** 銀行カナ名称 */
	public TLabelField ctrlKana;

	/** 銀行検索名称 */
	public TLabelField ctrlNamek;

	/** 銀行支店名称 */
	public TLabelField ctrlBranchName;

	/** 銀行支店カナ名称 */
	public TLabelField ctrlBranchKana;

	/** 銀行支店検索名称 */
	public TLabelField ctrlBranchNamek;

	/** 開始年月日 */
	public TLabelPopupCalendar dateFrom;

	/** 終了年月日 */
	public TLabelPopupCalendar dateTo;

	/**
	 * コンストラクタ.
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MG0140BankMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {

		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlCode = new TLabelField();
		ctrlBranchCode = new TLabelField();
		ctrlName = new TLabelField();
		ctrlKana = new TLabelField();
		ctrlNamek = new TLabelField();
		ctrlBranchName = new TLabelField();
		ctrlBranchKana = new TLabelField();
		ctrlBranchNamek = new TLabelField();
		dateFrom = new TLabelPopupCalendar();
		dateTo = new TLabelPopupCalendar();
	}

	@Override
	public void allocateComponents() {

		setSize(480, 430);

		// 確定ボタン
		int x = 355 - 110 - HEADER_MARGIN_X;
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 戻るボタン
		x = 355;
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		// 銀行コード
		ctrlCode.setLangMessageID("C00779");
		ctrlCode.setLabelSize(90);
		ctrlCode.setFieldSize(70);
		ctrlCode.setMaxLength(4);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		ctrlCode.setLocation(40, 20);
		pnlBody.add(ctrlCode);

		// 支店コード
		ctrlBranchCode.setLangMessageID("C00780");
		ctrlBranchCode.setLabelSize(120);
		ctrlBranchCode.setFieldSize(60);
		ctrlBranchCode.setMaxLength(3);
		ctrlBranchCode.setImeMode(false);
		ctrlBranchCode.setAllowedSpace(false);
		ctrlBranchCode.setLocation(10, 50);
		pnlBody.add(ctrlBranchCode);

		// 銀行名称
		ctrlName.setLangMessageID("C00781");
		ctrlName.setLabelSize(95);
		ctrlName.setFieldSize(300);
		ctrlName.setMaxLength(30);
		ctrlName.setLocation(35, 80);
		pnlBody.add(ctrlName);

		// 銀行カナ名称
		ctrlKana.setLangMessageID("C00782");
		ctrlKana.setLabelSize(120);
		ctrlKana.setFieldSize(150);
		ctrlKana.setMaxLength(30);
		ctrlKana.setLocation(10, 110);
		pnlBody.add(ctrlKana);

		// 銀行検索名称
		ctrlNamek.setLangMessageID("C00829");
		ctrlNamek.setLabelSize(120);
		ctrlNamek.setFieldSize(300);
		ctrlNamek.setMaxLength(30);
		ctrlNamek.setLocation(10, 140);
		pnlBody.add(ctrlNamek);

		// 銀行支店名称
		ctrlBranchName.setLangMessageID("C00783");
		ctrlBranchName.setLabelSize(120);
		ctrlBranchName.setFieldSize(300);
		ctrlBranchName.setMaxLength(30);
		ctrlBranchName.setLocation(10, 170);
		pnlBody.add(ctrlBranchName);

		// 銀行支店カナ名称
		ctrlBranchKana.setLangMessageID("C00784");
		ctrlBranchKana.setLabelSize(120);
		ctrlBranchKana.setFieldSize(150);
		ctrlBranchKana.setMaxLength(30);
		ctrlBranchKana.setLocation(10, 200);
		pnlBody.add(ctrlBranchKana);

		// 銀行支店検索名称
		ctrlBranchNamek.setLangMessageID("C00785");
		ctrlBranchNamek.setLabelSize(125);
		ctrlBranchNamek.setFieldSize(300);
		ctrlBranchNamek.setMaxLength(30);
		ctrlBranchNamek.setLocation(5, 230);
		pnlBody.add(ctrlBranchNamek);

		// 開始年月日
		dateFrom.setLabelSize(110);
		dateFrom.setSize(110 + dateFrom.getCalendarSize() + 5, 20);
		dateFrom.setLocation(20, 260);
		dateFrom.setLangMessageID("C00055");
		pnlBody.add(dateFrom);

		// 終了年月日
		dateTo.setLabelSize(110);
		dateTo.setSize(110 + dateFrom.getCalendarSize() + 5, 20);
		dateTo.setLocation(20, 290);
		dateTo.setLangMessageID("C00261");
		pnlBody.add(dateTo);
	}

	@Override
	public void setTabIndex() {

		int i = 0;
		ctrlCode.setTabControlNo(i++);
		ctrlBranchCode.setTabControlNo(i++);
		ctrlName.setTabControlNo(i++);
		ctrlKana.setTabControlNo(i++);
		ctrlNamek.setTabControlNo(i++);
		ctrlBranchName.setTabControlNo(i++);
		ctrlBranchKana.setTabControlNo(i++);
		ctrlBranchNamek.setTabControlNo(i++);
		dateFrom.setTabControlNo(i++);
		dateTo.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}