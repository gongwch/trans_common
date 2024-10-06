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
 * MP0040-PaymentMethodMaster - �x�����@�}�X�^ - �_�C�A���O���
 * 
 * @author AIS
 */
public class MP0040PaymentMethodMasterDialog extends TDialog {

	/** �c���Œ�l */
	protected final int BUTTON_HEIGHT = 25;

	/** �����Œ�l */
	protected final int BUTTON_WIDTH = 110;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �x�����@�R�[�h */
	public TLabelField ctrlPayCode;

	/** �x�����@�� */
	public TLabelField ctrlPayName;

	/** �x�����@������ */
	public TLabelField ctrlPayNameK;

	/** �͈͌����R���|�[�l���g */
	public TDetailItemRange ctrlSerch;

	/** �ȖڃO���[�v */
	public TItemGroup ctrlItemGroup;

	/** �v�㕔�� */
	public TDepartmentReference ctrlDepartment;

	/** �x���Ώۋ敪�p�l�� */
	public TPanel pnlPaymentDivision;

	/** �x���Ώۋ敪 ButtonGroup */
	public ButtonGroup bgPaymentDivision;

	/** �Ј��x�� */
	public TRadioButton rdoEmployeePayment;

	/** �ЊO�x�� */
	public TRadioButton rdoExternalPayment;

	/** �x�������R�[�h:�R���{�{�b�N�X */
	public TLabelComboBox ctrlPaymentInternalCode;

	/** �J�n�N���� */
	public TLabelPopupCalendar ctrlStrDate;

	/** �I���N���� */
	public TLabelPopupCalendar ctrlEndDate;

	/**
	 * �R���X�g���N�^
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

		// Header������
		pnlHeader.setLayout(null);

		// �m��{�^��
		btnSettle.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setLocation(getWidth() - 245, HEADER_Y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		btnClose.setSize(BUTTON_HEIGHT, BUTTON_WIDTH);
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setLocation(getWidth() - 130, HEADER_Y);
		pnlHeader.add(btnClose);

		// Body������
		pnlBody.setLayout(null);

		// �x�����@�R�[�h
		ctrlPayCode.setFieldSize(80);
		ctrlPayCode.setLocation(30, 10);
		ctrlPayCode.setLabelText(getWord("C00864"));
		ctrlPayCode.setMaxLength(3);
		ctrlPayCode.setImeMode(false);
		ctrlPayCode.setAllowedSpace(false);
		pnlBody.add(ctrlPayCode);

		// �x�����@��
		ctrlPayName.setFieldSize(300);
		ctrlPayName.setLocation(30, 35);
		ctrlPayName.setLabelText(getWord("C00865"));
		ctrlPayName.setMaxLength(20);
		pnlBody.add(ctrlPayName);

		// �x�����@������
		ctrlPayNameK.setFieldSize(300);
		ctrlPayNameK.setLocation(30, 60);
		ctrlPayNameK.setLabelText(getWord("C00866"));
		ctrlPayNameK.setMaxLength(40);
		pnlBody.add(ctrlPayNameK);

		// �ȖځA�⏕�A����
		ctrlItemGroup.setLocation(55,85);
		TGuiUtil.setComponentSize(ctrlItemGroup, 500, 60);
		pnlBody.add(ctrlItemGroup);

		// �v�㕔��
		ctrlDepartment.setLocation(55, 155);
		ctrlDepartment.btn.setLangMessageID("C00863");
		pnlBody.add(ctrlDepartment);

		// �x���Ώۋ敪�p�l��
		pnlPaymentDivision = new TPanel();
		pnlPaymentDivision.setLangMessageID("C01096");
		pnlPaymentDivision.setSize(250, 60);
		pnlPaymentDivision.setLocation(135, 180);
		pnlBody.add(pnlPaymentDivision);

		// �Ј��x��
		rdoEmployeePayment.setLangMessageID("C01119");
		rdoEmployeePayment.setSize(120, 30);
		rdoEmployeePayment.setLocation(150,200);
		rdoEmployeePayment.setSelected(true);
		rdoEmployeePayment.setIndex(0);
		pnlBody.add(rdoEmployeePayment);

		// �ЊO�x��
		rdoExternalPayment.setLangMessageID("C01124");
		rdoExternalPayment.setSize(120, 30);
		rdoExternalPayment.setLocation(250, 200);
		rdoExternalPayment.setIndex(1);
		pnlBody.add(rdoExternalPayment);

		// ButtonGroup
		bgPaymentDivision = new ButtonGroup();
		bgPaymentDivision.add(rdoEmployeePayment);
		bgPaymentDivision.add(rdoExternalPayment);

		// �x�������R�[�h
		ctrlPaymentInternalCode.setComboSize(220);
		ctrlPaymentInternalCode.setLabelSize(140);
		ctrlPaymentInternalCode.setLocation(-10, 250);
		ctrlPaymentInternalCode.setLangMessageID("C01097");
		setPaymentInternalCode(ctrlPaymentInternalCode.getComboBox());
		pnlBody.add(ctrlPaymentInternalCode);

		// �J�n�N����
		ctrlStrDate.setLabelSize(110);
		ctrlStrDate.setSize(110 + ctrlStrDate.getCalendarSize() + 5, 20);
		ctrlStrDate.setLangMessageID("COP706");
		ctrlStrDate.setLocation(20, 275);
		pnlBody.add(ctrlStrDate);

		// �I���N����
		ctrlEndDate.setLabelSize(110);
		ctrlEndDate.setSize(110 + ctrlEndDate.getCalendarSize() + 5, 20);
		ctrlEndDate.setLangMessageID("COP707");
		ctrlEndDate.setLocation(20, 300);
		pnlBody.add(ctrlEndDate);
	}

	/**
	 * �x�������R�[�h�R���{�{�b�N�X�̒l�ݒ�
	 * 
	 * @param comboBox
	 */
	protected void setPaymentInternalCode(TComboBox comboBox) {
		comboBox.addTextValueItem("", null);
		comboBox.addTextValueItem(PaymentKind.EMP_PAY_CASH,
			getWord(PaymentKind.getPaymentKindName(PaymentKind.EMP_PAY_CASH))); // ����(�Ј�)
		comboBox.addTextValueItem(PaymentKind.EMP_PAY_UNPAID, getWord("C02136")); // �����U��(�Ј�)
		comboBox.addTextValueItem(PaymentKind.EMP_PAY_CREDIT, getWord("C02137")); // �N���W�b�g(�Ј�)
		comboBox.addTextValueItem(PaymentKind.EMP_PAY_ACCRUED, getWord("C02138")); // �Ј�����(�Ј�)
		comboBox.addTextValueItem(PaymentKind.EX_PAY_CASH, getWord("C00154")); // ����
		comboBox.addTextValueItem(PaymentKind.EX_PAY_BANK, getWord("C02139")); // �U��(��s����)
		comboBox.addTextValueItem(PaymentKind.EX_PAY_FB, getWord("C02140")); // �U��(FB�쐬)
		comboBox.addTextValueItem(PaymentKind.EX_PAY_CHECK, getWord("C02141")); // ���؎�
		comboBox.addTextValueItem(PaymentKind.EX_PAY_BILL, getWord("C00230")); // �x����`
		comboBox.addTextValueItem(PaymentKind.EX_PAY_ERASE, getWord("C02142")); // ����
		comboBox.addTextValueItem(PaymentKind.EX_PAY_OFFSET, getWord("C00331")); // ���E
		comboBox.addTextValueItem(PaymentKind.EX_PAY_REMITTANCE_ABROAD, getWord("C02143")); // �O������
		comboBox.addTextValueItem(PaymentKind.EX_PAY_BANK_ABROAD, getWord("C02144")); // �U��(���O�p����)
		comboBox.addTextValueItem(PaymentKind.OTHER, getWord("C00338")); // ���̑�

	}

	@Override
	/** Tab����` */
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