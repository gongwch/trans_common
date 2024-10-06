package jp.co.ais.trans2.master.ui;

import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;

/**
 * MR0030-ReceivePolicyMaster - 入金方針マスタ - ダイアログ画面
 * 
 * @author AIS
 */
public class MR0030ReceivePolicyMasterPanel extends TMainPanel {

	/** 縦幅固定値 */
	protected final int BUTTON_HEIGHT = 25;

	/** 横幅固定値 */
	protected final int BUTTON_WIDTH = 110;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 取消ボタン */
	public TButton btnCancel;

	/** 入金取込ファイル情報パネル */
	public TPanel pnlPayCapFileInfo;

	/** レコード長 */
	public TLabelNumericField ctrlRecordLength;

	/** CR/LF付 */
	public TCheckBox ctrlCRLF;

	/** 振込入金通知タイプパネル */
	public TPanel pnlPayNoteType;

	/** 振込入金通知タイプ ButtonGroup */
	public ButtonGroup bgPayNoteType;

	/** フォーマットＡ */
	public TRadioButton rdoFormatA;

	/** フォーマットＢ */
	public TRadioButton rdoFormatB;

	/** 振込手数料パネル */
	public TPanel pnlTransferFee;

	/** 差額許容範囲ラベル */
	public TLabel lblDifferenceTolerance;

	/** 差額許容範囲 開始金額 */
	public TNumericField ctrlDifferenceToleranceStr;

	/** 差額許容範囲 終了金額 */
	public TNumericField ctrlDifferenceToleranceEnd;

	/** "円"ラベル */
	public TLabel lblen;

	/** 科目グループ */
	public TItemGroup ctrlItemGroup;

	/** 計上部門 */
	public TDepartmentReference ctrlDepartment;

	/** 消費税コード */
	public TTaxReference ctrlConsumptionTax;

	/** 請求書番号入力フラグ */
	public TCheckBox chkBillNoInputFlg;

	/** 請求書作成フラグ */
	public TCheckBox chkBillCreateFlg;

	/** 請求書番号 ON：自動設定 OFF：手入力 */
	public TCheckBox chkInvoiceNo;

	/** 非会計明細区分設定 */
	public TPanel pnlInvoiceNo;

	/** 請求書番号 設定項目1 */
	public TLabelComboBox cboInvoiceNo1;

	/** 請求書番号 設定項目1 名称 */
	public TTextField ctrlInvoiceNo1Name;

	/** 請求書番号 設定項目2 */
	public TLabelComboBox cboInvoiceNo2;

	/** 請求書番号 設定項目2 名称 */
	public TTextField ctrlInvoiceNo2Name;

	/** 請求書番号 設定項目3 */
	public TLabelComboBox cboInvoiceNo3;

	/** 請求書番号 設定項目3 名称 */
	public TTextField ctrlInvoiceNo3Name;

	/** 請求書番号連番桁数 */
	public TLabelNumericField ctrlInvoiceNoDigit;

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnCancel = new TButton();
		pnlPayCapFileInfo = new TPanel();
		ctrlRecordLength = new TLabelNumericField();
		ctrlCRLF = new TCheckBox();
		pnlPayNoteType = new TPanel();
		bgPayNoteType = new ButtonGroup();
		rdoFormatA = new TRadioButton();
		rdoFormatB = new TRadioButton();
		pnlTransferFee = new TPanel();
		ctrlDifferenceToleranceStr = new TNumericField();
		ctrlDifferenceToleranceEnd = new TNumericField();
		ctrlItemGroup = new TItemGroup();
		ctrlDepartment = new TDepartmentReference();
		ctrlConsumptionTax = new TTaxReference();
		chkBillNoInputFlg = new TCheckBox();
		chkBillCreateFlg = new TCheckBox();

		chkInvoiceNo = new TCheckBox();
		pnlInvoiceNo = new TPanel();
		cboInvoiceNo1 = new TLabelComboBox();
		ctrlInvoiceNo1Name = new TTextField();
		cboInvoiceNo2 = new TLabelComboBox();
		ctrlInvoiceNo2Name = new TTextField();
		cboInvoiceNo3 = new TLabelComboBox();
		ctrlInvoiceNo3Name = new TTextField();
		ctrlInvoiceNoDigit = new TLabelNumericField();
	}

	@Override
	public void allocateComponents() {

		setSize(600, 470);

		// Header初期化
		pnlHeader.setLayout(null);

		// 確定ボタン
		btnSettle.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setLocation(getWidth() - 245, HEADER_Y);
		pnlHeader.add(btnSettle);

		// 取消ボタン
		btnCancel.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnCancel.setLangMessageID("C01747");
		btnCancel.setShortcutKey(KeyEvent.VK_F12);
		btnCancel.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnCancel);

		// Body初期化
		pnlBody.setLayout(null);

		// 入金取込ファイル情報パネル
		pnlPayCapFileInfo.setLayout(null);
		pnlPayCapFileInfo.setLangMessageID("C10106");
		pnlPayCapFileInfo.setSize(450, 115);
		pnlPayCapFileInfo.setLocation(100, 10);
		pnlBody.add(pnlPayCapFileInfo);

		// レコード長
		ctrlRecordLength.setLabelSize(110);
		ctrlRecordLength.setFieldSize(50);
		ctrlRecordLength.setSize(215, 20);
		ctrlRecordLength.setLocation(10, 20);
		ctrlRecordLength.setLabelText(getWord("C10020"));
		ctrlRecordLength.setMaxLength(3);
		pnlPayCapFileInfo.add(ctrlRecordLength);

		// CR/LF付
		ctrlCRLF.setLangMessageID("C10003");
		ctrlCRLF.setSize(100, 20);
		ctrlCRLF.setLocation(300, 20);
		ctrlCRLF.setSelected(true);
		ctrlCRLF.setIndex(0);
		pnlPayCapFileInfo.add(ctrlCRLF);

		// 振込入金通知タイプパネル
		pnlPayNoteType.setLayout(null);
		pnlPayNoteType.setLangMessageID("C03858");
		pnlPayNoteType.setSize(350, 50);
		pnlPayNoteType.setLocation(60, 55);
		pnlPayCapFileInfo.add(pnlPayNoteType);

		int x = 50;
		int y = 15;

		// フォーマットＡ
		rdoFormatA.setLangMessageID("C03859");
		rdoFormatA.setSize(110, 30);
		rdoFormatA.setLocation(x, y);
		rdoFormatA.setSelected(true);
		rdoFormatA.setIndex(0);
		pnlPayNoteType.add(rdoFormatA);

		x += rdoFormatA.getWidth();

		// フォーマットＢ
		rdoFormatB.setLangMessageID("C03860");
		rdoFormatB.setSize(110, 30);
		rdoFormatB.setLocation(x + 30, y);
		rdoFormatB.setIndex(1);
		pnlPayNoteType.add(rdoFormatB);

		// ButtonGroup
		bgPayNoteType = new ButtonGroup();
		bgPayNoteType.add(rdoFormatA);
		bgPayNoteType.add(rdoFormatB);

		// 振込手数料パネル
		pnlTransferFee.setLayout(null);
		pnlTransferFee.setLangMessageID("C01183");
		pnlTransferFee.setSize(450, 170);
		pnlTransferFee.setLocation(100, 130);
		pnlBody.add(pnlTransferFee);

		// 差額許容範囲ラベル
		lblDifferenceTolerance = new TLabel();
		lblDifferenceTolerance.setSize(100, 20);
		lblDifferenceTolerance.setLocation(50, 20);
		lblDifferenceTolerance.setText(getWord("C10022"));
		pnlTransferFee.add(lblDifferenceTolerance);

		// 差額許容範囲 開始金額
		ctrlDifferenceToleranceStr.setSize(80, 20);
		ctrlDifferenceToleranceStr.setLocation(130, 20);
		ctrlDifferenceToleranceStr.setMaxLength(5);
		pnlTransferFee.add(ctrlDifferenceToleranceStr);

		// "円～"ラベル
		lblDifferenceTolerance = new TLabel();
		lblDifferenceTolerance.setSize(50, 20);
		lblDifferenceTolerance.setLocation(220, 20);
		lblDifferenceTolerance.setText(getWord("C00036") + getWord("C01333"));
		pnlTransferFee.add(lblDifferenceTolerance);

		// 差額許容範囲 終了金額
		ctrlDifferenceToleranceEnd.setSize(80, 20);
		ctrlDifferenceToleranceEnd.setLocation(260, 20);
		ctrlDifferenceToleranceEnd.setMaxLength(5);
		pnlTransferFee.add(ctrlDifferenceToleranceEnd);

		// "円"ラベル
		lblDifferenceTolerance = new TLabel();
		lblDifferenceTolerance.setSize(100, 20);
		lblDifferenceTolerance.setLocation(350, 20);
		lblDifferenceTolerance.setText(getWord("C00036"));
		pnlTransferFee.add(lblDifferenceTolerance);

		// 科目、補助、内訳
		ctrlItemGroup.setLocation(50, 50);
		TGuiUtil.setComponentSize(ctrlItemGroup, 400, 60);
		pnlTransferFee.add(ctrlItemGroup);

		// 計上部門
		ctrlDepartment.setLocation(50, 110);
		ctrlDepartment.btn.setLangMessageID("C00863");
		pnlTransferFee.add(ctrlDepartment);

		// 消費税コード
		ctrlConsumptionTax.setLocation(50, 130);
		ctrlConsumptionTax.btn.setLangMessageID("C00573");
		ctrlConsumptionTax.setCodeSize(80);
		pnlTransferFee.add(ctrlConsumptionTax);

		// 請求書番号入力フラグ
		chkBillNoInputFlg.setLangMessageID("C01204");
		chkBillNoInputFlg.setSize(200, 20);
		chkBillNoInputFlg.setLocation(100, 300);
		chkBillNoInputFlg.setSelected(true);
		chkBillNoInputFlg.setIndex(0);
		pnlBody.add(chkBillNoInputFlg);

		// 請求書作成フラグ
		chkBillCreateFlg.setLangMessageID("C10099");
		chkBillCreateFlg.setSize(150, 20);
		chkBillCreateFlg.setLocation(300, 300);
		chkBillCreateFlg.setSelected(true);
		chkBillCreateFlg.setIndex(0);
		pnlBody.add(chkBillCreateFlg);

		// 請求書番号 採番管理
		x = chkBillNoInputFlg.getX();
		y = chkBillNoInputFlg.getY() + chkBillNoInputFlg.getHeight() + 20;

		pnlInvoiceNo.setLayout(null);
		pnlInvoiceNo.setBorder(TBorderFactory.createTitledBorder(getWord("C12190")));
		pnlInvoiceNo.setSize(450, 150);
		pnlInvoiceNo.setLocation(x, y);
		pnlBody.add(pnlInvoiceNo);

		// 請求書番号 ON：自動設定 OFF：手入力
		chkInvoiceNo.setSize(150, 20);
		chkInvoiceNo.setLocation(20, 20);
		chkInvoiceNo.setLangMessageID("C12191");
		pnlInvoiceNo.add(chkInvoiceNo);

		x = chkInvoiceNo.getX();
		y = chkInvoiceNo.getY() + chkInvoiceNo.getHeight() + 5;

		// 請求書番号 設定項目1
		cboInvoiceNo1.setLabelSize(100);
		cboInvoiceNo1.setComboSize(110);
		cboInvoiceNo1.setSize(215, 20);
		cboInvoiceNo1.setLocation(x, y);
		cboInvoiceNo1.setLangMessageID("C00713");
		initInvoiceComboBox(cboInvoiceNo1.getComboBox());
		pnlInvoiceNo.add(cboInvoiceNo1);

		x += cboInvoiceNo1.getWidth();

		// 請求書番号 設定項目1 名称
		ctrlInvoiceNo1Name.setSize(120, 20);
		ctrlInvoiceNo1Name.setLocation(x, y);
		ctrlInvoiceNo1Name.setMaxLength(10);
		ctrlInvoiceNo1Name.setEnabled(false); // 初期は使用不可
		pnlInvoiceNo.add(ctrlInvoiceNo1Name);

		x = cboInvoiceNo1.getX();
		y = cboInvoiceNo1.getY() + cboInvoiceNo1.getHeight();

		// 請求書番号 設定項目2
		cboInvoiceNo2.setLabelSize(100);
		cboInvoiceNo2.setComboSize(110);
		cboInvoiceNo2.setSize(215, 20);
		cboInvoiceNo2.setLocation(x, y);
		cboInvoiceNo2.setLangMessageID("C00714");
		initInvoiceComboBox(cboInvoiceNo2.getComboBox());
		pnlInvoiceNo.add(cboInvoiceNo2);

		x += cboInvoiceNo2.getWidth();

		// 請求書番号 設定項目2 名称
		ctrlInvoiceNo2Name.setSize(120, 20);
		ctrlInvoiceNo2Name.setLocation(x, y);
		ctrlInvoiceNo2Name.setMaxLength(10);
		ctrlInvoiceNo2Name.setEnabled(false); // 初期は使用不可
		pnlInvoiceNo.add(ctrlInvoiceNo2Name);

		x = cboInvoiceNo2.getX();
		y = cboInvoiceNo2.getY() + cboInvoiceNo2.getHeight();

		// 請求書番号 設定項目3
		cboInvoiceNo3.setLabelSize(100);
		cboInvoiceNo3.setComboSize(110);
		cboInvoiceNo3.setSize(215, 20);
		cboInvoiceNo3.setLocation(x, y);
		cboInvoiceNo3.setLangMessageID("C00715");
		initInvoiceComboBox(cboInvoiceNo3.getComboBox());
		pnlInvoiceNo.add(cboInvoiceNo3);

		x += cboInvoiceNo3.getWidth();

		// 請求書番号 設定項目3 名称
		ctrlInvoiceNo3Name.setSize(120, 20);
		ctrlInvoiceNo3Name.setLocation(x, y);
		ctrlInvoiceNo3Name.setMaxLength(10);
		ctrlInvoiceNo3Name.setEnabled(false); // 初期は使用不可
		pnlInvoiceNo.add(ctrlInvoiceNo3Name);

		x = cboInvoiceNo3.getX() - 1;
		y = cboInvoiceNo3.getY() + cboInvoiceNo3.getHeight() + 5;

		// 自動採番部桁数
		ctrlInvoiceNoDigit.setLabelSize(85);
		ctrlInvoiceNoDigit.setFieldSize(20);
		ctrlInvoiceNoDigit.setSize(145, 20);
		ctrlInvoiceNoDigit.setLocation(x, y);
		ctrlInvoiceNoDigit.setPositiveOnly(true);
		ctrlInvoiceNoDigit.setLabelText(TModelUIUtil.getShortWord(("C00224")));
		ctrlInvoiceNoDigit.setMinValue(DecimalUtil.toBigDecimal("3"));
		ctrlInvoiceNoDigit.setNumber(3);
		ctrlInvoiceNoDigit.setMaxLength(1);
		pnlInvoiceNo.add(ctrlInvoiceNoDigit);

	}

	/**
	 * 請求書番号 自動設定項目コンボボックスの初期化
	 * 
	 * @param comboBox 初期化対象のコンボボックス
	 */
	protected void initInvoiceComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(InvoiceNoAdopt.NONE,
			getWord(InvoiceNoAdopt.getInvoiceNoAdoptName(InvoiceNoAdopt.NONE)));
		comboBox.addTextValueItem(InvoiceNoAdopt.FIXED_CHAR,
			getWord(InvoiceNoAdopt.getInvoiceNoAdoptName(InvoiceNoAdopt.FIXED_CHAR)));
		comboBox.addTextValueItem(InvoiceNoAdopt.YY_DATE,
			getWord(InvoiceNoAdopt.getInvoiceNoAdoptName(InvoiceNoAdopt.YY_DATE)));
		comboBox.addTextValueItem(InvoiceNoAdopt.YYYY_DATE,
			getWord(InvoiceNoAdopt.getInvoiceNoAdoptName(InvoiceNoAdopt.YYYY_DATE)));
		comboBox.addTextValueItem(InvoiceNoAdopt.MM_DATE,
			getWord(InvoiceNoAdopt.getInvoiceNoAdoptName(InvoiceNoAdopt.MM_DATE)));
		comboBox.addTextValueItem(InvoiceNoAdopt.YYMM_DATE,
			getWord(InvoiceNoAdopt.getInvoiceNoAdoptName(InvoiceNoAdopt.YYMM_DATE)));
		comboBox.addTextValueItem(InvoiceNoAdopt.YYYYMM_DATE,
			getWord(InvoiceNoAdopt.getInvoiceNoAdoptName(InvoiceNoAdopt.YYYYMM_DATE)));
		comboBox.addTextValueItem(InvoiceNoAdopt.DEPARTMENT,
			getWord(InvoiceNoAdopt.getInvoiceNoAdoptName(InvoiceNoAdopt.DEPARTMENT)));
		comboBox.addTextValueItem(InvoiceNoAdopt.USER,
			getWord(InvoiceNoAdopt.getInvoiceNoAdoptName(InvoiceNoAdopt.USER)));
		comboBox.addTextValueItem(InvoiceNoAdopt.CODE,
			getWord(InvoiceNoAdopt.getInvoiceNoAdoptName(InvoiceNoAdopt.CODE)));
	}

	@Override
	/** Tab順定義 */
	public void setTabIndex() {
		int i = 1;

		ctrlRecordLength.setTabControlNo(i++);
		ctrlCRLF.setTabControlNo(i++);
		rdoFormatA.setTabControlNo(i++);
		rdoFormatB.setTabControlNo(i++);
		ctrlDifferenceToleranceStr.setTabControlNo(i++);
		ctrlDifferenceToleranceEnd.setTabControlNo(i++);
		ctrlItemGroup.setTabControlNo(i++);
		ctrlDepartment.setTabControlNo(i++);
		ctrlConsumptionTax.setTabControlNo(i++);
		chkBillNoInputFlg.setTabControlNo(i++);
		chkBillCreateFlg.setTabControlNo(i++);

		chkInvoiceNo.setTabControlNo(i++);
		cboInvoiceNo1.setTabControlNo(i++);
		ctrlInvoiceNo1Name.setTabControlNo(i++);
		cboInvoiceNo2.setTabControlNo(i++);
		ctrlInvoiceNo2Name.setTabControlNo(i++);
		cboInvoiceNo3.setTabControlNo(i++);
		ctrlInvoiceNo3Name.setTabControlNo(i++);
		ctrlInvoiceNoDigit.setTabControlNo(i++);

		btnSettle.setTabControlNo(i++);
		btnCancel.setTabControlNo(i++);
	}
}