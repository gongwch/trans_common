package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * MG0016-EmployeeMaster - 社員マスタ - ダイアログ画面
 * 
 * @author AIS
 */
public class MG0160EmployeeMasterDialog extends TDialog {

	/** 縦幅固定値 */
	protected final int BUTTON_HEIGHT = 25;

	/** 横幅固定値 */
	protected final int BUTTON_WIDTH = 110;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 社員コード */
	public TLabelField ctrlCode;

	/** 社員名称 */
	public TLabelField ctrlName;

	/** 社員略称 */
	public TLabelField ctrlNameS;

	/** 社員検索名称 */
	public TLabelField ctrlNameK;

	/** 振込口座番号 */
	public TLabelField ctrlYknNo;

	/** 口座名義カナ */
	public TLabelField ctrlYknKana;

	/** 振出銀行口座 */
	public TBankAccountReference ctrlCbkCode;

	/** 振込銀行コード */
	public TBankReference ctrlBnkCode;

	/** 振込支店コード */
	public TBranchReference ctrlStnCode;

	/** 振込口座現預金種別:枠 */
	public TPanel pnlKozaKbn;

	/** 振込口座現預金種別:Button Group */
	public ButtonGroup bgKozaKbn;

	/** 振込口座現預金種別:1:普通預金 */
	public TRadioButton ctrlKozaKbnOrd;

	/** 振込口座現預金種別:2:当座預金 */
	public TRadioButton ctrlKozaKbnCur;

	/** 開始年月日 */
	public TLabelPopupCalendar ctrlDateFrom;

	/** 終了年月日 */
	public TLabelPopupCalendar ctrlDateTo;

	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 * @param mordal
	 */
	public MG0160EmployeeMasterDialog(Frame parent, boolean mordal) {
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
		ctrlCbkCode = new TBankAccountReference();
		ctrlBnkCode = new TBankReference();
		ctrlStnCode = new TBranchReference();
		pnlKozaKbn = new TPanel();
		bgKozaKbn = new ButtonGroup();
		ctrlKozaKbnOrd = new TRadioButton();
		ctrlKozaKbnCur = new TRadioButton();
		ctrlYknNo = new TLabelField();
		ctrlYknKana = new TLabelField();
		ctrlDateFrom = new TLabelPopupCalendar();
		ctrlDateTo = new TLabelPopupCalendar();
	}

	@Override
	public void allocateComponents() {

		setSize(600, 400);

		// Header初期化
		pnlHeader.setLayout(null);

		// 確定ボタン
		btnSettle.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
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

		// 社員コード
		ctrlCode.setFieldSize(150);
		ctrlCode.setLangMessageID("C00697");
		ctrlCode.setMaxLength(10);
		ctrlCode.setImeMode(false);
		ctrlCode.setAllowedSpace(false);
		ctrlCode.setLocation(20, 10);
		pnlBody.add(ctrlCode);

		// 社員名称
		ctrlName.setFieldSize(400);
		ctrlName.setLangMessageID("C00807");
		ctrlName.setMaxLength(40);
		ctrlName.setLocation(20, 40);
		pnlBody.add(ctrlName);

		// 社員略称
		ctrlNameS.setFieldSize(400);
		ctrlNameS.setLangMessageID("C00808");
		ctrlNameS.setMaxLength(20);
		ctrlNameS.setLocation(20, 70);
		pnlBody.add(ctrlNameS);

		// 社員検索名称
		ctrlNameK.setFieldSize(400);
		ctrlNameK.setLabelSize(120);
		ctrlNameK.setLangMessageID("C00809");
		ctrlNameK.setMaxLength(40);
		ctrlNameK.setLocation(0, 100);
		pnlBody.add(ctrlNameK);

		// 振出銀行口座
		ctrlCbkCode.setLocation(45, 130);
		pnlBody.add(ctrlCbkCode);

		// 振込銀行
		ctrlBnkCode.setLocation(45, 160);
		pnlBody.add(ctrlBnkCode);

		// 振込銀行支店
		ctrlStnCode.setLocation(45, 190);
		pnlBody.add(ctrlStnCode);

		// 振込口座預金種別
		pnlKozaKbn.setLayout(null);
		pnlKozaKbn.setSize(150, 70);
		pnlKozaKbn.setLangMessageID(getShortWord("C00471"));
		pnlKozaKbn.setLocation(400, 130);
		pnlBody.add(pnlKozaKbn);

		// 振込口座預金種別:普通
		ctrlKozaKbnOrd.setSize(100, 20);
		ctrlKozaKbnOrd.setLangMessageID("C00463");
		ctrlKozaKbnOrd.setIndex(1);
		ctrlKozaKbnOrd.setLocation(20, 20);
		pnlKozaKbn.add(ctrlKozaKbnOrd);

		// 振込口座預金種別:当座
		ctrlKozaKbnCur.setSize(100, 20);
		ctrlKozaKbnCur.setLangMessageID("C00397");
		ctrlKozaKbnCur.setIndex(2);
		ctrlKozaKbnCur.setLocation(20, 40);
		pnlKozaKbn.add(ctrlKozaKbnCur);

		// Radio Button Group 化
		bgKozaKbn.add(ctrlKozaKbnOrd);
		bgKozaKbn.add(ctrlKozaKbnCur);

		// 振込口座番号
		ctrlYknNo.setFieldSize(80);
		ctrlYknNo.setLangMessageID("C00813");
		ctrlYknNo.setMaxLength(7);
		ctrlYknNo.setImeMode(false);
		ctrlYknNo.setLocation(20, 220);
		pnlBody.add(ctrlYknNo);

		// 口座名義カナ
		ctrlYknKana.setFieldSize(400);
		ctrlYknKana.setLangMessageID("C00168");
		ctrlYknKana.setMaxLength(30);
		ctrlYknKana.setLocation(20, 250);
		pnlBody.add(ctrlYknKana);

		// 開始年月日
		ctrlDateFrom.setLabelSize(110);
		ctrlDateFrom.setSize(110 + ctrlDateFrom.getCalendarSize() + 5, 20);
		ctrlDateFrom.setLangMessageID("C00055");
		ctrlDateFrom.setLocation(10, 280);
		pnlBody.add(ctrlDateFrom);

		// 終了年月日
		ctrlDateTo.setLabelSize(110);
		ctrlDateTo.setSize(110 + ctrlDateTo.getCalendarSize() + 5, 20);
		ctrlDateTo.setLangMessageID("C00261");
		ctrlDateTo.setLocation(200, 280);
		pnlBody.add(ctrlDateTo);
	}

	@Override
	/** Tab順定義 */
	public void setTabIndex() {
		int i = 1;
		ctrlCode.setTabControlNo(i++);
		ctrlName.setTabControlNo(i++);
		ctrlNameS.setTabControlNo(i++);
		ctrlNameK.setTabControlNo(i++);
		ctrlCbkCode.setTabControlNo(i++);
		ctrlBnkCode.setTabControlNo(i++);
		ctrlStnCode.setTabControlNo(i++);
		ctrlKozaKbnOrd.setTabControlNo(i++);
		ctrlKozaKbnCur.setTabControlNo(i++);
		ctrlYknNo.setTabControlNo(i++);
		ctrlYknKana.setTabControlNo(i++);
		ctrlDateFrom.setTabControlNo(i++);
		ctrlDateTo.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}