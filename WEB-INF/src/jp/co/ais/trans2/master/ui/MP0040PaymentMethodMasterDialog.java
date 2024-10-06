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
 * MP0040-PaymentMethodMaster - 支払方法マスタ - ダイアログ画面
 * 
 * @author AIS
 */
public class MP0040PaymentMethodMasterDialog extends TDialog {

	/** 縦幅固定値 */
	protected final int BUTTON_HEIGHT = 25;

	/** 横幅固定値 */
	protected final int BUTTON_WIDTH = 110;

	/** 確定ボタン */
	public TImageButton btnSettle;

	/** 戻るボタン */
	public TButton btnClose;

	/** 支払方法コード */
	public TLabelField ctrlPayCode;

	/** 支払方法名 */
	public TLabelField ctrlPayName;

	/** 支払方法検索名 */
	public TLabelField ctrlPayNameK;

	/** 範囲検索コンポーネント */
	public TDetailItemRange ctrlSerch;

	/** 科目グループ */
	public TItemGroup ctrlItemGroup;

	/** 計上部門 */
	public TDepartmentReference ctrlDepartment;

	/** 支払対象区分パネル */
	public TPanel pnlPaymentDivision;

	/** 支払対象区分 ButtonGroup */
	public ButtonGroup bgPaymentDivision;

	/** 社員支払 */
	public TRadioButton rdoEmployeePayment;

	/** 社外支払 */
	public TRadioButton rdoExternalPayment;

	/** 支払内部コード:コンボボックス */
	public TLabelComboBox ctrlPaymentInternalCode;

	/** 開始年月日 */
	public TLabelPopupCalendar ctrlStrDate;

	/** 終了年月日 */
	public TLabelPopupCalendar ctrlEndDate;

	/**
	 * コンストラクタ
	 * 
	 * @param parent
	 * @param mordal
	 */
	public MP0040PaymentMethodMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlPayCode = new TLabelField();
		ctrlPayName = new TLabelField();
		ctrlPayNameK = new TLabelField();
		ctrlItemGroup = new TItemGroup();
		ctrlDepartment = new TDepartmentReference();
		pnlPaymentDivision = new TPanel();
		bgPaymentDivision = new ButtonGroup();
		rdoEmployeePayment = new TRadioButton();
		rdoExternalPayment = new TRadioButton();
		ctrlPaymentInternalCode = new TLabelComboBox();
		ctrlStrDate = new TLabelPopupCalendar();
		ctrlEndDate = new TLabelPopupCalendar();
	}

	@Override
	public void allocateComponents() {

		setSize(500, 430);

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

		// 支払方法コード
		ctrlPayCode.setFieldSize(80);
		ctrlPayCode.setLocation(30, 10);
		ctrlPayCode.setLabelText(getWord("C00864"));
		ctrlPayCode.setMaxLength(3);
		ctrlPayCode.setImeMode(false);
		ctrlPayCode.setAllowedSpace(false);
		pnlBody.add(ctrlPayCode);

		// 支払方法名
		ctrlPayName.setFieldSize(300);
		ctrlPayName.setLocation(30, 35);
		ctrlPayName.setLabelText(getWord("C00865"));
		ctrlPayName.setMaxLength(20);
		pnlBody.add(ctrlPayName);

		// 支払方法検索名
		ctrlPayNameK.setFieldSize(300);
		ctrlPayNameK.setLocation(30, 60);
		ctrlPayNameK.setLabelText(getWord("C00866"));
		ctrlPayNameK.setMaxLength(40);
		pnlBody.add(ctrlPayNameK);

		// 科目、補助、内訳
		ctrlItemGroup.setLocation(55,85);
		TGuiUtil.setComponentSize(ctrlItemGroup, 500, 60);
		pnlBody.add(ctrlItemGroup);

		// 計上部門
		ctrlDepartment.setLocation(55, 155);
		ctrlDepartment.btn.setLangMessageID("C00863");
		pnlBody.add(ctrlDepartment);

		// 支払対象区分パネル
		pnlPaymentDivision = new TPanel();
		pnlPaymentDivision.setLangMessageID("C01096");
		pnlPaymentDivision.setSize(250, 60);
		pnlPaymentDivision.setLocation(135, 180);
		pnlBody.add(pnlPaymentDivision);

		// 社員支払
		rdoEmployeePayment.setLangMessageID("C01119");
		rdoEmployeePayment.setSize(120, 30);
		rdoEmployeePayment.setLocation(150,200);
		rdoEmployeePayment.setSelected(true);
		rdoEmployeePayment.setIndex(0);
		pnlBody.add(rdoEmployeePayment);

		// 社外支払
		rdoExternalPayment.setLangMessageID("C01124");
		rdoExternalPayment.setSize(120, 30);
		rdoExternalPayment.setLocation(250, 200);
		rdoExternalPayment.setIndex(1);
		pnlBody.add(rdoExternalPayment);

		// ButtonGroup
		bgPaymentDivision = new ButtonGroup();
		bgPaymentDivision.add(rdoEmployeePayment);
		bgPaymentDivision.add(rdoExternalPayment);

		// 支払内部コード
		ctrlPaymentInternalCode.setComboSize(220);
		ctrlPaymentInternalCode.setLabelSize(140);
		ctrlPaymentInternalCode.setLocation(-10, 250);
		ctrlPaymentInternalCode.setLangMessageID("C01097");
		setPaymentInternalCode(ctrlPaymentInternalCode.getComboBox());
		pnlBody.add(ctrlPaymentInternalCode);

		// 開始年月日
		ctrlStrDate.setLabelSize(110);
		ctrlStrDate.setSize(110 + ctrlStrDate.getCalendarSize() + 5, 20);
		ctrlStrDate.setLangMessageID("COP706");
		ctrlStrDate.setLocation(20, 275);
		pnlBody.add(ctrlStrDate);

		// 終了年月日
		ctrlEndDate.setLabelSize(110);
		ctrlEndDate.setSize(110 + ctrlEndDate.getCalendarSize() + 5, 20);
		ctrlEndDate.setLangMessageID("COP707");
		ctrlEndDate.setLocation(20, 300);
		pnlBody.add(ctrlEndDate);
	}

	/**
	 * 支払内部コードコンボボックスの値設定
	 * 
	 * @param comboBox
	 */
	protected void setPaymentInternalCode(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(PaymentKind.EMP_PAY_CASH,
			getWord(PaymentKind.getPaymentKindName(PaymentKind.EMP_PAY_CASH))); // 現金(社員)
		comboBox.addTextValueItem(PaymentKind.EMP_PAY_UNPAID, getWord("C02136")); // 未払振込(社員)
		comboBox.addTextValueItem(PaymentKind.EMP_PAY_CREDIT, getWord("C02137")); // クレジット(社員)
		comboBox.addTextValueItem(PaymentKind.EMP_PAY_ACCRUED, getWord("C02138")); // 社員未収(社員)
		comboBox.addTextValueItem(PaymentKind.EX_PAY_CASH, getWord("C00154")); // 現金
		comboBox.addTextValueItem(PaymentKind.EX_PAY_BANK, getWord("C02139")); // 振込(銀行窓口)
		comboBox.addTextValueItem(PaymentKind.EX_PAY_FB, getWord("C02140")); // 振込(FB作成)
		comboBox.addTextValueItem(PaymentKind.EX_PAY_CHECK, getWord("C02141")); // 小切手
		comboBox.addTextValueItem(PaymentKind.EX_PAY_BILL, getWord("C00230")); // 支払手形
		comboBox.addTextValueItem(PaymentKind.EX_PAY_ERASE, getWord("C02142")); // 消込
		comboBox.addTextValueItem(PaymentKind.EX_PAY_OFFSET, getWord("C00331")); // 相殺
		comboBox.addTextValueItem(PaymentKind.EX_PAY_REMITTANCE_ABROAD, getWord("C02143")); // 外国送金
		comboBox.addTextValueItem(PaymentKind.EX_PAY_BANK_ABROAD, getWord("C02144")); // 振込(国外用窓口)
		comboBox.addTextValueItem(PaymentKind.OTHER, getWord("C00338")); // その他

	}

	@Override
	/** Tab順定義 */
	public void setTabIndex() {
		int i = 1;

		ctrlPayCode.setTabControlNo(i++);
		ctrlPayName.setTabControlNo(i++);
		ctrlPayNameK.setTabControlNo(i++);
		ctrlItemGroup.setTabControlNo(i++);
		ctrlDepartment.setTabControlNo(i++);
		rdoEmployeePayment.setTabControlNo(i++);
		rdoExternalPayment.setTabControlNo(i++);
		ctrlPaymentInternalCode.setTabControlNo(i++);
		ctrlStrDate.setTabControlNo(i++);
		ctrlEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}
}