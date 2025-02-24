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
 * MP0040-PaymentMethodMaster - x₯ϋ@}X^ - _CAOζΚ
 * 
 * @author AIS
 */
public class MP0040PaymentMethodMasterDialog extends TDialog {

	/** cΕθl */
	protected final int BUTTON_HEIGHT = 25;

	/** ‘Εθl */
	protected final int BUTTON_WIDTH = 110;

	/** mθ{^ */
	public TImageButton btnSettle;

	/** ίι{^ */
	public TButton btnClose;

	/** x₯ϋ@R[h */
	public TLabelField ctrlPayCode;

	/** x₯ϋ@Ό */
	public TLabelField ctrlPayName;

	/** x₯ϋ@υΌ */
	public TLabelField ctrlPayNameK;

	/** ΝΝυR|[lg */
	public TDetailItemRange ctrlSerch;

	/** ΘΪO[v */
	public TItemGroup ctrlItemGroup;

	/** vγε */
	public TDepartmentReference ctrlDepartment;

	/** x₯ΞΫζͺpl */
	public TPanel pnlPaymentDivision;

	/** x₯ΞΫζͺ ButtonGroup */
	public ButtonGroup bgPaymentDivision;

	/** Πυx₯ */
	public TRadioButton rdoEmployeePayment;

	/** ΠOx₯ */
	public TRadioButton rdoExternalPayment;

	/** x₯ΰR[h:R{{bNX */
	public TLabelComboBox ctrlPaymentInternalCode;

	/** JnNϊ */
	public TLabelPopupCalendar ctrlStrDate;

	/** IΉNϊ */
	public TLabelPopupCalendar ctrlEndDate;

	/**
	 * RXgN^
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

		// Headerϊ»
		pnlHeader.setLayout(null);

		// mθ{^
		btnSettle.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setLocation(getWidth() - 245, HEADER_Y);
		pnlHeader.add(btnSettle);

		// ίι{^
		btnClose.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		// Bodyϊ»
		pnlBody.setLayout(null);

		// x₯ϋ@R[h
		ctrlPayCode.setFieldSize(80);
		ctrlPayCode.setLocation(30, 10);
		ctrlPayCode.setLabelText(getWord("C00864"));
		ctrlPayCode.setMaxLength(3);
		ctrlPayCode.setImeMode(false);
		ctrlPayCode.setAllowedSpace(false);
		pnlBody.add(ctrlPayCode);

		// x₯ϋ@Ό
		ctrlPayName.setFieldSize(300);
		ctrlPayName.setLocation(30, 35);
		ctrlPayName.setLabelText(getWord("C00865"));
		ctrlPayName.setMaxLength(20);
		pnlBody.add(ctrlPayName);

		// x₯ϋ@υΌ
		ctrlPayNameK.setFieldSize(300);
		ctrlPayNameK.setLocation(30, 60);
		ctrlPayNameK.setLabelText(getWord("C00866"));
		ctrlPayNameK.setMaxLength(40);
		pnlBody.add(ctrlPayNameK);

		// ΘΪAβAΰσ
		ctrlItemGroup.setLocation(55,85);
		TGuiUtil.setComponentSize(ctrlItemGroup, 500, 60);
		pnlBody.add(ctrlItemGroup);

		// vγε
		ctrlDepartment.setLocation(55, 155);
		ctrlDepartment.btn.setLangMessageID("C00863");
		pnlBody.add(ctrlDepartment);

		// x₯ΞΫζͺpl
		pnlPaymentDivision = new TPanel();
		pnlPaymentDivision.setLangMessageID("C01096");
		pnlPaymentDivision.setSize(250, 60);
		pnlPaymentDivision.setLocation(135, 180);
		pnlBody.add(pnlPaymentDivision);

		// Πυx₯
		rdoEmployeePayment.setLangMessageID("C01119");
		rdoEmployeePayment.setSize(120, 30);
		rdoEmployeePayment.setLocation(150,200);
		rdoEmployeePayment.setSelected(true);
		rdoEmployeePayment.setIndex(0);
		pnlBody.add(rdoEmployeePayment);

		// ΠOx₯
		rdoExternalPayment.setLangMessageID("C01124");
		rdoExternalPayment.setSize(120, 30);
		rdoExternalPayment.setLocation(250, 200);
		rdoExternalPayment.setIndex(1);
		pnlBody.add(rdoExternalPayment);

		// ButtonGroup
		bgPaymentDivision = new ButtonGroup();
		bgPaymentDivision.add(rdoEmployeePayment);
		bgPaymentDivision.add(rdoExternalPayment);

		// x₯ΰR[h
		ctrlPaymentInternalCode.setComboSize(220);
		ctrlPaymentInternalCode.setLabelSize(140);
		ctrlPaymentInternalCode.setLocation(-10, 250);
		ctrlPaymentInternalCode.setLangMessageID("C01097");
		setPaymentInternalCode(ctrlPaymentInternalCode.getComboBox());
		pnlBody.add(ctrlPaymentInternalCode);

		// JnNϊ
		ctrlStrDate.setLabelSize(110);
		ctrlStrDate.setSize(110 + ctrlStrDate.getCalendarSize() + 5, 20);
		ctrlStrDate.setLangMessageID("COP706");
		ctrlStrDate.setLocation(20, 275);
		pnlBody.add(ctrlStrDate);

		// IΉNϊ
		ctrlEndDate.setLabelSize(110);
		ctrlEndDate.setSize(110 + ctrlEndDate.getCalendarSize() + 5, 20);
		ctrlEndDate.setLangMessageID("COP707");
		ctrlEndDate.setLocation(20, 300);
		pnlBody.add(ctrlEndDate);
	}

	/**
	 * x₯ΰR[hR{{bNXΜlέθ
	 * 
	 * @param comboBox
	 */
	protected void setPaymentInternalCode(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(PaymentKind.EMP_PAY_CASH,
			getWord(PaymentKind.getPaymentKindName(PaymentKind.EMP_PAY_CASH))); // »ΰ(Πυ)
		comboBox.addTextValueItem(PaymentKind.EMP_PAY_UNPAID, getWord("C02136")); // ’₯U(Πυ)
		comboBox.addTextValueItem(PaymentKind.EMP_PAY_CREDIT, getWord("C02137")); // NWbg(Πυ)
		comboBox.addTextValueItem(PaymentKind.EMP_PAY_ACCRUED, getWord("C02138")); // Πυ’ϋ(Πυ)
		comboBox.addTextValueItem(PaymentKind.EX_PAY_CASH, getWord("C00154")); // »ΰ
		comboBox.addTextValueItem(PaymentKind.EX_PAY_BANK, getWord("C02139")); // U(βsϋ)
		comboBox.addTextValueItem(PaymentKind.EX_PAY_FB, getWord("C02140")); // U(FBμ¬)
		comboBox.addTextValueItem(PaymentKind.EX_PAY_CHECK, getWord("C02141")); // ¬Ψθ
		comboBox.addTextValueItem(PaymentKind.EX_PAY_BILL, getWord("C00230")); // x₯θ`
		comboBox.addTextValueItem(PaymentKind.EX_PAY_ERASE, getWord("C02142")); // Α
		comboBox.addTextValueItem(PaymentKind.EX_PAY_OFFSET, getWord("C00331")); // E
		comboBox.addTextValueItem(PaymentKind.EX_PAY_REMITTANCE_ABROAD, getWord("C02143")); // Oΰ
		comboBox.addTextValueItem(PaymentKind.EX_PAY_BANK_ABROAD, getWord("C02144")); // U(Opϋ)
		comboBox.addTextValueItem(PaymentKind.OTHER, getWord("C00338")); // »ΜΌ

	}

	@Override
	/** Tabθ` */
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