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
 * �����}�X�^�̕ҏW���
 * 
 * @author AIS
 */
public class MG0150CustomerMasterDialog extends TDialog {

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �����R�[�h */
	public TLabelField ctrlTriCode;

	/** ����於�� */
	public TLabelField ctrlTriName;

	/** ���Ə����� */
	public TLabelField ctrlOfficeName;

	/** ����旪�� */
	public TLabelField ctrlTriNames;

	/** ����挟������ */
	public TLabelField ctrlTriNamek;

	/** �X�֔ԍ� */
	public TLabelField ctrlPostNum;

	/** �d�b�ԍ� */
	public TLabelField ctrlTelNum;

	/** FAX�ԍ� */
	public TLabelField ctrlFaxNum;

	/** �� */
	public TCountryReference ctrlCountry;

	/** �Z��1 */
	public TLabelField ctrlAddressNum1;

	/** �Z��2 */
	public TLabelField ctrlAddressNum2;

	/** �Z���J�i */
	public TLabelField ctrlAddressNumKana;

	/** EMail Address */
	public TLabelField ctrlEMailAddress;

	/** ���Ӑ��� */
	public TPanel pnlTokui;

	/** ������s���� */
	public TBankAccountReference ctrlBankAccount;

	/** �U���˗��l��(�J�i) */
	public TLabelField ctrlClientName;

	/** �x��/���������\���R���| */
	public TCustomerClosedDayNumSetting ctrlCustomerClosedDaySetting;

	/** ���Е��S */
	public TRadioButton rdoTosha;

	/** ������S */
	public TRadioButton rdoSenpo;

	/** �����萔�� ButtonGroup */
	public ButtonGroup bgNyukin;

	/** �����萔�� */
	public TPanel pnlNyukin;

	/** ��d���� */
	public TRadioButton rdoNotSiire;

	/** �d���� */
	public TRadioButton rdoSiire;

	/** �d����敪 ButtonGroup */
	public ButtonGroup bgSiire;

	/** �d����敪 */
	public TPanel pnlSiire;

	/** �񓾈Ӑ� */
	public TRadioButton rdoNotTokui;

	/** ���Ӑ� */
	public TRadioButton rdoTokui;

	/** ���Ӑ�敪 ButtonGroup */
	public ButtonGroup bgTokui;

	/** ���Ӑ�敪 */
	public TPanel pnlTokuiKbn;

	/** �W�v����� */
	public TCustomerReference TCustomerReference;

	/** �J�n�N���� */
	public TLabelPopupCalendar dtBeginDate;

	/** �I���N���� */
	public TLabelPopupCalendar dtEndDate;

	/** �����敪 */
	public TPanel pnlTri;

	/** CHARTERER */
	public TCheckBox chkChtr;

	/** OWNER */
	public TCheckBox chkOnr;

	/** PORT AGENT */
	public TCheckBox chkAgnt;

	/** SUPPLIER */
	public TCheckBox chkSplr;

	/** BROKER */
	public TCheckBox chkBrkr;

	/** BANK */
	protected TCheckBox chkBank;

	/** OTHER */
	public TCheckBox chkOzr;

	/** Shipper */
	public TCheckBox chkShpp;

	/** Consignee */
	public TCheckBox chkCons;

	/** Notify party */
	public TCheckBox chkNtpt;

	/** Fowarder */
	public TCheckBox chkFwd;

	/** Bunker Trader */
	public TCheckBox chkBktr;

	/** Bunker Supplier */
	public TCheckBox chkBksp;

	/** �O���[�v��� */
	public TCheckBox chkGrp;

	/** AR�������S���� */
	public TPanel pnlCusTitle;

	/** �����h�� */
	public TLabelComboBox cboCusTitle;

	/** �S���������� */
	public TLabelField ctrlDepInCharge;

	/** �S���Җ��� */
	public TLabelField ctrlChargeName;

	/** �S���Ҍh�� */
	public TLabelComboBox cboChargeTitle;

	/** ��s��������\�����Ȃ� */
	public TCheckBox chkHideBankNo;

	/** �`�F�b�N�{�b�N�X��K�i���������s���Ǝ� */
	public TCheckBox chkNoInvReg;

	/** ����Ń��t�@�����X */
	public TTaxReference ctrlTaxReference;

	/** �K�i���������s���ƎҔԍ� */
	public TLabelField ctrlInvRegNo;

	/**
	 * @param parent
	 * @param mordal
	 */
	public MG0150CustomerMasterDialog(Frame parent, boolean mordal) {
		super(parent, mordal);
	}

	@Override
	public void initComponents() {
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlTriCode = new TLabelField();
		ctrlTriName = new TLabelField();
		ctrlTriNames = new TLabelField();
		ctrlTriNamek = new TLabelField();
		ctrlOfficeName = new TLabelField();
		ctrlPostNum = new TLabelField();
		ctrlTelNum = new TLabelField();
		ctrlFaxNum = new TLabelField();
		ctrlCountry = new TCountryReference();
		ctrlAddressNum1 = new TLabelField();
		ctrlAddressNum2 = new TLabelField();
		ctrlAddressNumKana = new TLabelField();
		ctrlEMailAddress = new TLabelField();
		pnlTokui = new TPanel();
		ctrlBankAccount = new TBankAccountReference();
		ctrlClientName = new TLabelField();
		ctrlCustomerClosedDaySetting = new TCustomerClosedDayNumSetting(false); // false:����
		pnlNyukin = new TPanel();
		rdoTosha = new TRadioButton();
		rdoSenpo = new TRadioButton();
		pnlSiire = new TPanel();
		rdoNotSiire = new TRadioButton();
		rdoSiire = new TRadioButton();
		pnlTokuiKbn = new TPanel();
		rdoNotTokui = new TRadioButton();
		rdoTokui = new TRadioButton();
		TCustomerReference = new TCustomerReference();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		pnlTri = new TPanel();
		chkChtr = new TCheckBox();
		chkOnr = new TCheckBox();
		chkAgnt = new TCheckBox();
		chkSplr = new TCheckBox();
		chkBrkr = new TCheckBox();
		chkBank = new TCheckBox();
		chkOzr = new TCheckBox();
		chkShpp = new TCheckBox();
		chkCons = new TCheckBox();
		chkNtpt = new TCheckBox();
		chkFwd = new TCheckBox();
		chkBktr = new TCheckBox();
		chkBksp = new TCheckBox();
		chkGrp = new TCheckBox();
		chkHideBankNo = new TCheckBox();
		chkNoInvReg = new TCheckBox();
		ctrlTaxReference = new TTaxReference();
		ctrlInvRegNo = new TLabelField();

		pnlCusTitle = new TPanel();
		cboCusTitle = new TLabelComboBox();
		ctrlDepInCharge = new TLabelField();
		ctrlChargeName = new TLabelField();
		cboChargeTitle = new TLabelComboBox();
	}

	@Override
	public void allocateComponents() {

		int x = ctrlTriName.getX() + ctrlTriName.getWidth() + 5;
		int addX = 0;
		if (MG0150CustomerMasterPanelCtrl.isInvoice && !MG0150CustomerMasterPanelCtrl.isUseCustomerManagementSet) {
			addX = 200;
		} else if (MG0150CustomerMasterPanelCtrl.isUseCustomerManagementSet) {
			addX = 300;
		}
		setSize(730 + addX, 730);
		int y = HEADER_Y;

		// �m��{�^��
		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F8);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(getWidth() - 240 - HEADER_MARGIN_X, y);
		pnlHeader.add(btnSettle);

		// �߂�{�^��
		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(getWidth() - 130, y);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);
		y = 10;

		// �����R�[�h
		ctrlTriCode.setFieldSize(80);
		ctrlTriCode.setLocation(35, y);
		ctrlTriCode.setMaxLength(10);
		ctrlTriCode.setImeMode(false);
		ctrlTriCode.setAllowedSpace(false);
		ctrlTriCode.setLangMessageID("C00786");
		pnlBody.add(ctrlTriCode);

		y += 30;

		// ����於��
		ctrlTriName.setFieldSize(450);
		ctrlTriName.setLocation(35, y);
		ctrlTriName.setLangMessageID("C00830");
		ctrlTriName.setMaxLength(40);
		pnlBody.add(ctrlTriName);

		y += 30;

		// ���Ə�����
		ctrlOfficeName.setFieldSize(450);
		ctrlOfficeName.setLocation(35, y);
		ctrlOfficeName.setLangMessageID("C00581");
		ctrlOfficeName.setMaxLength(40);
		pnlBody.add(ctrlOfficeName);

		y += 30;

		// ����旪��
		ctrlTriNames.setFieldSize(450);
		ctrlTriNames.setLocation(35, y);
		ctrlTriNames.setLangMessageID("C00787");
		ctrlTriNames.setMaxLength(40);
		pnlBody.add(ctrlTriNames);

		y += 30;

		// ����挟������
		ctrlTriNamek.setFieldSize(450);
		ctrlTriNamek.setLocation(35, y);
		ctrlTriNamek.setLangMessageID("C00836");
		ctrlTriNamek.setMaxLength(80);
		pnlBody.add(ctrlTriNamek);

		y += 30;

		// �X�֔ԍ�
		ctrlPostNum.setFieldSize(80);
		ctrlPostNum.setLocation(35, y);
		ctrlPostNum.setImeMode(false);
		ctrlPostNum.setLangMessageID("C00527");
		ctrlPostNum.setMaxLength(10);
		pnlBody.add(ctrlPostNum);

		// �d�b�ԍ�
		ctrlTelNum.setFieldSize(100);
		ctrlTelNum.setLocation(200, y);
		ctrlTelNum.setImeMode(false);
		ctrlTelNum.setLangMessageID("C00393");
		ctrlTelNum.setMaxLength(12);
		pnlBody.add(ctrlTelNum);

		// FAX�ԍ�
		ctrlFaxNum.setFieldSize(100);
		ctrlFaxNum.setLocation(385, y);
		ctrlFaxNum.setImeMode(false);
		ctrlFaxNum.setLangMessageID("C00690");
		ctrlFaxNum.setMaxLength(12);
		pnlBody.add(ctrlFaxNum);

		y += 30;

		// ��
		ctrlCountry.setLocation(40, y);
		pnlBody.add(ctrlCountry);

		y += 30;

		// �Z��1
		ctrlAddressNum1.setFieldSize(450);
		ctrlAddressNum1.setLocation(35, y);
		ctrlAddressNum1.setLangMessageID("C01150");
		ctrlAddressNum1.setMaxLength(50);
		pnlBody.add(ctrlAddressNum1);

		y += 30;

		// �Z��2
		ctrlAddressNum2.setFieldSize(450);
		ctrlAddressNum2.setLocation(35, y);
		ctrlAddressNum2.setLangMessageID("C01151");
		ctrlAddressNum2.setMaxLength(50);
		pnlBody.add(ctrlAddressNum2);

		y += 30;

		// �Z���J�i
		ctrlAddressNumKana.setFieldSize(450);
		ctrlAddressNumKana.setLocation(35, y);
		ctrlAddressNumKana.setLangMessageID("C01152");
		ctrlAddressNumKana.setMaxLength(80);
		pnlBody.add(ctrlAddressNumKana);

		y += 30;

		// EMail Address
		ctrlEMailAddress.setFieldSize(450);
		ctrlEMailAddress.setLocation(35, y);
		ctrlEMailAddress.setLangMessageID("CBL401");
		ctrlEMailAddress.setMaxLength(200);
		pnlBody.add(ctrlEMailAddress);

		y = 370;

		// ������s����
		ctrlBankAccount.setLocation(130, y);
		ctrlBankAccount.setEditable(false);
		pnlBody.add(ctrlBankAccount);

		y += 30;

		// �U���˗��l��(�J�i)
		ctrlClientName.setLabelSize(120);
		ctrlClientName.setFieldSize(230);
		ctrlClientName.setLocation(85, y);
		ctrlClientName.setLangMessageID("C00859");
		ctrlClientName.setMaxLength(48);
		ctrlClientName.setEditable(false);
		pnlBody.add(ctrlClientName);

		y += 30;

		// �莞��������:�u�������v�Œ�
		ctrlCustomerClosedDaySetting.lblCashDay.setLangMessageID("C00448");
		ctrlCustomerClosedDaySetting.setLocation(78, y);
		ctrlCustomerClosedDaySetting.setSize(40, 20);
		ctrlCustomerClosedDaySetting.resize();
		ctrlCustomerClosedDaySetting.setEditable(false);
		pnlBody.add(ctrlCustomerClosedDaySetting);

		y += 53;

		// ���Е��S
		rdoTosha.setLangMessageID("C00399");
		rdoTosha.setSize(80, 15);
		rdoTosha.setSelected(true);
		rdoTosha.setLocation(140, y);
		rdoTosha.setIndex(0);
		rdoTosha.setEnabled(false);
		pnlBody.add(rdoTosha);

		y += 20;

		// ������S
		rdoSenpo.setLangMessageID("C00327");
		rdoSenpo.setSize(80, 15);
		rdoSenpo.setLocation(140, y);
		rdoSenpo.setIndex(1);
		rdoSenpo.setEnabled(false);
		pnlBody.add(rdoSenpo);

		bgNyukin = new ButtonGroup();
		bgNyukin.add(rdoTosha);
		bgNyukin.add(rdoSenpo);

		y = 365;

		// ��d����
		rdoNotSiire.setLangMessageID("C01295");
		rdoNotSiire.setSize(80, 15);
		rdoNotSiire.setSelected(true);
		rdoNotSiire.setLocation(495, y);
		rdoNotSiire.setIndex(0);
		pnlBody.add(rdoNotSiire);

		y += 20;

		// �d����
		rdoSiire.setLangMessageID("C00203");
		rdoSiire.setSize(80, 15);
		rdoSiire.setLocation(495, y);
		rdoSiire.setIndex(1);
		pnlBody.add(rdoSiire);

		bgSiire = new ButtonGroup();
		bgSiire.add(rdoNotSiire);
		bgSiire.add(rdoSiire);

		y += 50;

		// �񓾈Ӑ�
		rdoNotTokui.setLangMessageID("C01296");
		rdoNotTokui.setSize(90, 15);
		rdoNotTokui.setSelected(true);
		rdoNotTokui.setLocation(495, y);
		rdoNotTokui.setIndex(0);
		pnlBody.add(rdoNotTokui);

		y += 20;

		// ���Ӑ�
		rdoTokui.setLangMessageID("C00401");
		rdoTokui.setSize(90, 15);
		rdoTokui.setLocation(495, y);
		rdoTokui.setIndex(1);
		pnlBody.add(rdoTokui);

		bgTokui = new ButtonGroup();
		bgTokui.add(rdoNotTokui);
		bgTokui.add(rdoTokui);

		y = ctrlCustomerClosedDaySetting.getY() + ctrlCustomerClosedDaySetting.getHeight() + 10;

		// �����萔��
		pnlNyukin.setLocation(120, y);
		pnlNyukin.setSize(140, 70);
		pnlNyukin.setBorder(TBorderFactory.createTitledBorder(getWord("C01269")));
		pnlBody.add(pnlNyukin);

		y = ctrlEMailAddress.getY() + ctrlEMailAddress.getHeight() + 10;

		// ���Ӑ���
		pnlTokui.setLocation(80, y);
		pnlTokui.setSize(400, 210);
		pnlTokui.setBorder(TBorderFactory.createTitledBorder(getWord("C01262")));
		pnlBody.add(pnlTokui);

		// �d����敪
		pnlSiire.setLocation(490, y);
		pnlSiire.setSize(100, 70);
		pnlSiire.setBorder(TBorderFactory.createTitledBorder(getWord("C01089")));
		pnlBody.add(pnlSiire);

		y = pnlSiire.getY() + pnlSiire.getHeight();

		// ���Ӑ�敪
		pnlTokuiKbn.setLocation(490, y);
		pnlTokuiKbn.setSize(100, 70);
		pnlTokuiKbn.setBorder(TBorderFactory.createTitledBorder(getWord("C01261")));
		pnlBody.add(pnlTokuiKbn);

		y = pnlTokui.getY() + pnlTokui.getHeight() + 10;

		// �W�v�����R�[�h
		TCustomerReference.setLocation(75, y);
		TCustomerReference.btn.setLangMessageID("C11093");
		TCustomerReference.btn.setMaximumSize(new Dimension(140, 20));
		TCustomerReference.btn.setMinimumSize(new Dimension(140, 20));
		TCustomerReference.btn.setPreferredSize(new Dimension(140, 20));
		TCustomerReference.name.setMaximumSize(new Dimension(180, 20));
		TCustomerReference.name.setMinimumSize(new Dimension(180, 20));
		TCustomerReference.name.setPreferredSize(new Dimension(180, 20));
		TCustomerReference.setSize(new Dimension(410, 20));
		pnlBody.add(TCustomerReference);

		y += TCustomerReference.getHeight() + 10;

		// �J�n�N����
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.setLabelSize(70);
		dtBeginDate.setLocation(70, y);
		pnlBody.add(dtBeginDate);

		// �I���N����
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.setLabelSize(70);
		dtEndDate.setLocation(240, y);
		pnlBody.add(dtEndDate);

		y = pnlTokuiKbn.getY() + pnlTokuiKbn.getHeight();

		// �����敪
		pnlTri.setLocation(490, y);
		pnlTri.setSize(210, 160);
		pnlTri.setBorder(TBorderFactory.createTitledBorder(getWord("C03344")));
		pnlBody.add(pnlTri);

		y += 17;

		// CHARTERER
		chkChtr.setLangMessageID("CTC0058");
		chkChtr.setSize(100, 15);
		chkChtr.setLocation(495, y);
		pnlBody.add(chkChtr);

		y += 20;

		// OWNER
		chkOnr.setLangMessageID("CTC0059");
		chkOnr.setSize(100, 15);
		chkOnr.setLocation(495, y);
		pnlBody.add(chkOnr);

		y += 20;

		// PORT AGENT
		chkAgnt.setLangMessageID("CTC0115");
		chkAgnt.setSize(100, 15);
		chkAgnt.setLocation(495, y);
		pnlBody.add(chkAgnt);

		y += 20;

		// SUPPLIER
		chkSplr.setLangMessageID("CTC0116");
		chkSplr.setSize(100, 15);
		chkSplr.setLocation(495, y);
		pnlBody.add(chkSplr);

		y += 20;

		// BROKER
		chkBrkr.setLangMessageID("CTC0078");
		chkBrkr.setSize(100, 15);
		chkBrkr.setLocation(495, y);
		pnlBody.add(chkBrkr);

		y += 20;

		// BANK
		chkBank.setLangMessageID("COP532"); // BANK
		chkBank.setSize(100, 15);
		chkBank.setLocation(495, y);
		pnlBody.add(chkBank);

		y += 20;

		// OTHER
		chkOzr.setLangMessageID("CTC0117");
		chkOzr.setSize(100, 15);
		chkOzr.setLocation(495, y);
		pnlBody.add(chkOzr);

		y = chkChtr.getY();

		// Shipper
		chkShpp.setLangMessageID("CBL206");
		chkShpp.setSize(130, 15);
		chkShpp.setLocation(590, y);
		pnlBody.add(chkShpp);

		y = chkOnr.getY();

		// Consignee
		chkCons.setLangMessageID("CBL209");
		chkCons.setSize(130, 15);
		chkCons.setLocation(590, y);
		pnlBody.add(chkCons);

		y = chkAgnt.getY();

		// Notify party
		chkNtpt.setLangMessageID("CBL320");
		chkNtpt.setSize(130, 15);
		chkNtpt.setLocation(590, y);
		pnlBody.add(chkNtpt);

		y = chkSplr.getY();

		// Fowarder
		chkFwd.setLangMessageID("CBL833");
		chkFwd.setSize(130, 15);
		chkFwd.setLocation(590, y);
		pnlBody.add(chkFwd);

		y = chkBrkr.getY();

		// Bunker Trader
		chkBktr.setLangMessageID("CBL834");
		chkBktr.setSize(130, 15);
		chkBktr.setLocation(590, y);
		pnlBody.add(chkBktr);

		y = chkBank.getY();

		// Bunker Supplier
		chkBksp.setLangMessageID("CBL835");
		chkBksp.setSize(130, 15);
		chkBksp.setLocation(590, y);
		pnlBody.add(chkBksp);

		y = chkOzr.getY();

		// �O���[�v���
		chkGrp.setLangMessageID("C10583");
		chkGrp.setSize(130, 15);
		chkGrp.setLocation(590, y);
		pnlBody.add(chkGrp);

		x = ctrlAddressNum2.getX() + ctrlAddressNum2.getWidth() + 20;
		y = ctrlAddressNum2.getY() + 15;

		// ����S���Ҙg
		pnlCusTitle.setLocation(x, y);
		pnlCusTitle.setSize(370, 100);
		pnlCusTitle.setBorder(TBorderFactory.createTitledBorder(getWord("C12188")));
		pnlBody.add(pnlCusTitle);

		y += 20;

		// �����h��
		cboCusTitle.setLabelSize(60);
		cboCusTitle.setComboSize(65);
		cboCusTitle.setLocation(x + 20, y);
		cboCusTitle.setLangMessageID("C12184");
		initHonorTitleComboBox(cboCusTitle.getComboBox(), true);
		pnlBody.add(cboCusTitle);

		y += cboCusTitle.getHeight() + 3;

		// �S����������
		ctrlDepInCharge.setLabelSize(80);
		ctrlDepInCharge.setFieldSize(250);
		ctrlDepInCharge.setLocation(x, y);
		ctrlDepInCharge.setLangMessageID("C12185");
		ctrlDepInCharge.setMaxLength(40);
		pnlBody.add(ctrlDepInCharge);

		y += ctrlDepInCharge.getHeight() + 3;

		// �S���Җ���
		ctrlChargeName.setLabelSize(80);
		ctrlChargeName.setFieldSize(250);
		ctrlChargeName.setLocation(x, y);
		ctrlChargeName.setLangMessageID("C12186");
		ctrlChargeName.setMaxLength(40);
		pnlBody.add(ctrlChargeName);

		x = cboCusTitle.getX() + cboCusTitle.getWidth() + 15;
		y = cboCusTitle.getY();

		// �S���Ҍh��
		cboChargeTitle.setLabelSize(60);
		cboChargeTitle.setComboSize(65);
		cboChargeTitle.setLocation(x, y);
		cboChargeTitle.setLangMessageID("C12187");
		initHonorTitleComboBox(cboChargeTitle.getComboBox(), false);
		pnlBody.add(cboChargeTitle);

		x = ctrlAddressNum2.getX() + ctrlAddressNum2.getWidth() + 20;
		y = ctrlChargeName.getY() + 50;

		// 
		chkHideBankNo.setLangMessageID("�x��������\�����Ȃ�");
		chkHideBankNo.setSize(160, 15);
		chkHideBankNo.setLocation(x, y);
		pnlBody.add(chkHideBankNo);

		y += 20;

		// ��K�i���������s���Ǝ�
		chkNoInvReg.setLangMessageID("C12197");
		chkNoInvReg.setSize(160, 15);
		chkNoInvReg.setLocation(x, y);
		pnlBody.add(chkNoInvReg);

		y = chkNoInvReg.getY() + 30;

		// ����Ń��t�@�����X
		ctrlTaxReference.setLocation(x, y);
		pnlBody.add(ctrlTaxReference);

		y = ctrlTaxReference.getY() + 30;

		// �K�i���������s���Ǝғo�^�ԍ�
		ctrlInvRegNo.setLabelSize(170);
		ctrlInvRegNo.setFieldSize(100);
		ctrlInvRegNo.setLocation(x, y);
		ctrlInvRegNo.setImeMode(false);
		ctrlInvRegNo.setAllowedSpace(false);
		ctrlInvRegNo.setLangMessageID("C12171");
		ctrlInvRegNo.setMaxLength(14);
		pnlBody.add(ctrlInvRegNo);

	}

	/**
	 * �����h�̃��x���R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox �������Ώۂ̃R���{�{�b�N�X
	 * @param isCus
	 */
	protected void initHonorTitleComboBox(TComboBox comboBox, boolean isCus) {
		comboBox.addTextValueItem(HonorType.NONE, getWord(HonorType.getName(HonorType.NONE)));
		comboBox.addTextValueItem(HonorType.MR_JP, getWord(HonorType.getName(HonorType.MR_JP)));
		comboBox.addTextValueItem(HonorType.YOUR_JP, getWord(HonorType.getName(HonorType.YOUR_JP)));
		comboBox.addTextValueItem(HonorType.DEAR_JP, getWord(HonorType.getName(HonorType.DEAR_JP)));
		comboBox.addTextValueItem(HonorType.PER_JP, getWord(HonorType.getName(HonorType.PER_JP)));
		comboBox.addTextValueItem(HonorType.AD1_JP, getWord(HonorType.getName(HonorType.AD1_JP)));
		comboBox.addTextValueItem(HonorType.AD2_JP, getWord(HonorType.getName(HonorType.AD2_JP)));
		comboBox.addTextValueItem(HonorType.TCH_JP, getWord(HonorType.getName(HonorType.TCH_JP)));
		comboBox.addTextValueItem(HonorType.MR_EN, getWord(HonorType.getName(HonorType.MR_EN)));
		comboBox.addTextValueItem(HonorType.MS_EN, getWord(HonorType.getName(HonorType.MS_EN)));
		comboBox.addTextValueItem(HonorType.MRS_EN, getWord(HonorType.getName(HonorType.MRS_EN)));
		if (isCus) {
			comboBox.addTextValueItem(HonorType.MERS_EN, getWord(HonorType.getName(HonorType.MERS_EN)));
			comboBox.addTextValueItem(HonorType.TO_EN, getWord(HonorType.getName(HonorType.TO_EN)));
		}
	}

	@Override
	public void setTabIndex() {
		int i = 0;
		ctrlTriCode.setTabControlNo(i++);
		ctrlTriName.setTabControlNo(i++);
		ctrlOfficeName.setTabControlNo(i++);
		ctrlTriNames.setTabControlNo(i++);
		ctrlTriNamek.setTabControlNo(i++);
		ctrlPostNum.setTabControlNo(i++);
		ctrlTelNum.setTabControlNo(i++);
		ctrlFaxNum.setTabControlNo(i++);
		ctrlCountry.setTabControlNo(i++);
		ctrlAddressNum1.setTabControlNo(i++);
		ctrlAddressNum2.setTabControlNo(i++);
		ctrlAddressNumKana.setTabControlNo(i++);
		ctrlEMailAddress.setTabControlNo(i++);

		cboCusTitle.setTabControlNo(i++);
		cboChargeTitle.setTabControlNo(i++);
		ctrlDepInCharge.setTabControlNo(i++);
		ctrlChargeName.setTabControlNo(i++);

		chkHideBankNo.setTabControlNo(i++);
		chkNoInvReg.setTabControlNo(i++);
		ctrlTaxReference.setTabControlNo(i++);
		ctrlInvRegNo.setTabControlNo(i++);

		ctrlBankAccount.setTabControlNo(i++);
		ctrlClientName.setTabControlNo(i++);
		ctrlCustomerClosedDaySetting.closeDay.setTabControlNo(i++);
		ctrlCustomerClosedDaySetting.nextMonth.setTabControlNo(i++);
		ctrlCustomerClosedDaySetting.cashDay.setTabControlNo(i++);
		rdoTosha.setTabControlNo(i++);
		rdoSenpo.setTabControlNo(i++);
		rdoNotSiire.setTabControlNo(i++);
		rdoSiire.setTabControlNo(i++);
		rdoNotTokui.setTabControlNo(i++);
		rdoTokui.setTabControlNo(i++);
		TCustomerReference.setTabControlNo(i++);
		chkChtr.setTabControlNo(i++);
		chkOnr.setTabControlNo(i++);
		chkAgnt.setTabControlNo(i++);
		chkSplr.setTabControlNo(i++);
		chkBrkr.setTabControlNo(i++);
		chkBank.setTabControlNo(i++);
		chkOzr.setTabControlNo(i++);
		chkShpp.setTabControlNo(i++);
		chkCons.setTabControlNo(i++);
		chkNtpt.setTabControlNo(i++);
		chkFwd.setTabControlNo(i++);
		chkBktr.setTabControlNo(i++);
		chkBksp.setTabControlNo(i++);
		chkGrp.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);
	}

}
