package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TDialog;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ��s�����}�X�^�ҏW���
 * 
 * @author AIS
 */
public class MP0030BankAccountMasterDialog extends TDialog {

	/** �߂�{�^�� */
	public TButton btnClose;

	/** �m��{�^�� */
	public TImageButton btnSettle;

	/** ��s�����R�[�h */
	public TLabelField ctrlBankAccount;

	/** ��s�������� */
	public TTextField txtBankAccountName;

	/** �ʉ� */
	public TCurrencyReference ctrlCurrency;

	/** ��s */
	public TBankReference ctrlBank;

	/** �x�X */
	public TBranchReference ctrlBranch;

	/** �a����� */
	public TDepositKindComboBox ctrlDepositType;

	/** �U���˗��l�R�[�h */
	public TLabelField ctrlTransferRequesterCode;

	/** �U���˗��l���i�Łj */
	public TLabelHarfSizeCharConvertField ctrlTransferRequesterKanaName;

	/** �U���˗��l���i�����j */
	public TLabelField ctrlTransferRequesterKanjiName;

	/** �U���˗��l���i�p���j */
	public TLabelField ctrlTransferRequesterEnglishName;

	/** �Ј�FB�敪 */
	public TTitlePanel pnlEmployeeFBDivision;

	/** �ЊOFB�敪 */
	public TTitlePanel pnlExternalFBDivision;

	/** �Ј�FB�ȊO */
	public TRadioButton rdoEmployeeFBDivisionException;

	/** �Ј�FB�p */
	public TRadioButton rdoEmployeeFBUse;

	/** �ЊOFB�ȊO */
	public TRadioButton rdoExternalFBException;

	/** �ЊOFB�p */
	public TRadioButton rdoExternalFBUse;

	/** �Ј�FB�敪�{�^���O���[�v */
	public ButtonGroup btngrpEmployeeFBDivision;

	/** �ЊOFB�敪�{�^���O���[�v */
	public ButtonGroup btngrpExternalFBDivision;

	/** �����ԍ� */
	public TLabelField ctrlAccountNumber;

	/** �v�㕔�� */
	public TDepartmentReference ctrlAppropriateDepartment;

	/** �Ȗ� */
	public TItemGroup ctrlItemUnit;

	/** �J�n�N���� */
	public TLabelPopupCalendar dtBeginDate;

	/** �I���N���� */
	public TLabelPopupCalendar dtEndDate;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param company
	 * @param parent
	 * @param mordal
	 */
	public MP0030BankAccountMasterDialog(Company company, Frame parent, boolean mordal) {
		super(company, parent, mordal);
	}

	@Override
	public void initComponents() {
		btngrpEmployeeFBDivision = new ButtonGroup();
		btngrpExternalFBDivision = new ButtonGroup();
		btnSettle = new TImageButton(IconType.SETTLE);
		btnClose = new TButton();
		ctrlBankAccount = new TLabelField();
		txtBankAccountName = new TTextField();
		ctrlCurrency = new TCurrencyReference();
		ctrlBranch = new TBranchReference();
		ctrlBank = new TBankReference();
		ctrlTransferRequesterKanaName = new TLabelHarfSizeCharConvertField();
		ctrlTransferRequesterKanjiName = new TLabelField();
		ctrlTransferRequesterEnglishName = new TLabelField();
		ctrlAccountNumber = new TLabelField();
		ctrlTransferRequesterCode = new TLabelField();
		ctrlDepositType = new TDepositKindComboBox();
		pnlEmployeeFBDivision = new TTitlePanel();
		rdoEmployeeFBDivisionException = new TRadioButton();
		rdoEmployeeFBUse = new TRadioButton();
		pnlExternalFBDivision = new TTitlePanel();
		rdoExternalFBException = new TRadioButton();
		rdoExternalFBUse = new TRadioButton();
		ctrlAppropriateDepartment = new TDepartmentReference();
		dtBeginDate = new TLabelPopupCalendar();
		dtEndDate = new TLabelPopupCalendar();
		ctrlItemUnit = new TItemGroup();

	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#allocateComponents()
	 */
	@Override
	public void allocateComponents() {

		setSize(700, 550);

		int x = 450;
		// �c�����X���W
		int baseX = 10;

		btnSettle.setLangMessageID("C01019");
		btnSettle.setShortcutKey(KeyEvent.VK_F9);
		btnSettle.setSize(25, 110);
		btnSettle.setLocation(x, HEADER_Y);

		pnlHeader.add(btnSettle);

		x = x + btnSettle.getWidth() + HEADER_MARGIN_X;

		btnClose.setLangMessageID("C01747");
		btnClose.setShortcutKey(KeyEvent.VK_F12);
		btnClose.setSize(25, 110);
		btnClose.setLocation(x, HEADER_Y);
		btnClose.setForClose(true);
		pnlHeader.add(btnClose);

		pnlBody.setLayout(null);

		ctrlBankAccount.setLabelSize(90);
		ctrlBankAccount.setFieldSize(120);
		ctrlBankAccount.setLangMessageID("C01879");
		ctrlBankAccount.getField().setAllowedSpace(false);
		ctrlBankAccount.setMaxLength(10);
		ctrlBankAccount.setImeMode(false);
		ctrlBankAccount.setLocation(baseX + 40, COMPONENT_MARGIN_Y);

		pnlBody.add(ctrlBankAccount);

		txtBankAccountName.setSize(380, 20);
		txtBankAccountName.setMaxLength(40);

		txtBankAccountName.setLocation(ctrlBankAccount.getX() + ctrlBankAccount.getWidth() + 2, COMPONENT_MARGIN_Y);
		pnlBody.add(txtBankAccountName);

		ctrlCurrency.name.setVisible(false);
		ctrlCurrency.setLocation(baseX - 20, ctrlBankAccount.getY() + ctrlBankAccount.getHeight() + COMPONENT_MARGIN_Y);
		pnlBody.add(ctrlCurrency);

		ctrlBank.setLocation(baseX + 55, ctrlCurrency.getY() + ctrlCurrency.getHeight() + COMPONENT_MARGIN_Y);
		pnlBody.add(ctrlBank);

		ctrlBranch.setLocation(baseX + 55, ctrlBank.getY() + ctrlBank.getHeight() + COMPONENT_MARGIN_Y);
		pnlBody.add(ctrlBranch);

		ctrlTransferRequesterCode.setFieldSize(120);
		ctrlTransferRequesterCode.setLangMessageID("C00858");
		ctrlTransferRequesterCode.getField().setAllowedSpace(false);
		ctrlTransferRequesterCode.setMaxLength(10);
		ctrlTransferRequesterCode.setImeMode(false);
		ctrlTransferRequesterCode.setLabelSize(130);

		ctrlTransferRequesterCode.setLocation(baseX, ctrlBranch.getY() + ctrlBranch.getHeight() + COMPONENT_MARGIN_Y);
		pnlBody.add(ctrlTransferRequesterCode);

		ctrlTransferRequesterKanaName.setFieldSize(450);
		ctrlTransferRequesterKanaName.setLangMessageID("C00859");// �U���˗��l���i�Łj
		ctrlTransferRequesterKanaName.setMaxLength(40);
		ctrlTransferRequesterKanaName.setLabelSize(130);

		ctrlTransferRequesterKanaName.setLocation(baseX,
			ctrlTransferRequesterCode.getY() + ctrlTransferRequesterCode.getHeight() + COMPONENT_MARGIN_Y);
		pnlBody.add(ctrlTransferRequesterKanaName);

		ctrlTransferRequesterKanjiName.setFieldSize(450);
		ctrlTransferRequesterKanjiName.setLangMessageID("C00860");
		ctrlTransferRequesterKanjiName.setMaxLength(40);
		ctrlTransferRequesterKanjiName.setLabelSize(130);

		ctrlTransferRequesterKanjiName.setLocation(baseX, ctrlTransferRequesterKanaName.getY()
			+ ctrlTransferRequesterKanaName.getHeight() + COMPONENT_MARGIN_Y);
		pnlBody.add(ctrlTransferRequesterKanjiName);

		ctrlTransferRequesterEnglishName.setFieldSize(450);
		ctrlTransferRequesterEnglishName.setLangMessageID("C00861");
		ctrlTransferRequesterEnglishName.setMaxLength(140);
		ctrlTransferRequesterEnglishName.setLabelSize(130);

		ctrlTransferRequesterEnglishName.setLocation(baseX, ctrlTransferRequesterKanjiName.getY()
			+ ctrlTransferRequesterKanjiName.getHeight() + COMPONENT_MARGIN_Y);
		pnlBody.add(ctrlTransferRequesterEnglishName);

		ctrlAccountNumber.setFieldSize(120);
		ctrlAccountNumber.setLangMessageID("C00794");
		ctrlAccountNumber.getField().setAllowedSpace(false);
		ctrlAccountNumber.setMaxLength(10);
		ctrlAccountNumber.setImeMode(false);
		ctrlAccountNumber.setLabelSize(130);

		ctrlAccountNumber
			.setLocation(baseX, ctrlTransferRequesterEnglishName.getY() + ctrlTransferRequesterEnglishName.getHeight()
				+ COMPONENT_MARGIN_Y);
		pnlBody.add(ctrlAccountNumber);

		ctrlDepositType.setComboSize(95);
		ctrlDepositType.setLabelSize(100);
		ctrlDepositType.setLangMessageID("C01326");
		ctrlDepositType.setTabControlNo(11);
		ctrlDepositType.setLabelSize(130);

		ctrlDepositType.setLocation(baseX, ctrlAccountNumber.getY() + ctrlAccountNumber.getHeight()
			+ COMPONENT_MARGIN_Y);
		pnlBody.add(ctrlDepositType);

		// �Ј�FB�敪
		pnlEmployeeFBDivision.setLangMessageID("C01117");
		pnlEmployeeFBDivision.setSize(120, 70);

		btngrpEmployeeFBDivision.add(rdoEmployeeFBDivisionException);
		rdoEmployeeFBDivisionException.setSelected(true);
		rdoEmployeeFBDivisionException.setLangMessageID("C01116");
		rdoEmployeeFBDivisionException.setSize(100, 20);
		rdoEmployeeFBDivisionException.setLocation(5, 5);
		pnlEmployeeFBDivision.add(rdoEmployeeFBDivisionException);

		btngrpEmployeeFBDivision.add(rdoEmployeeFBUse);
		rdoEmployeeFBUse.setLangMessageID("C01118");
		rdoEmployeeFBUse.setSize(100, 20);
		rdoEmployeeFBUse.setLocation(5, 25);
		pnlEmployeeFBDivision.add(rdoEmployeeFBUse);

		pnlEmployeeFBDivision.setLocation(baseX + 250, ctrlDepositType.getY() + ctrlDepositType.getHeight()
			+ COMPONENT_MARGIN_Y);
		pnlBody.add(pnlEmployeeFBDivision);

		// �ЊOFB�敪
		pnlExternalFBDivision.setLangMessageID("C01122");
		pnlExternalFBDivision.setSize(120, 70);

		btngrpExternalFBDivision.add(rdoExternalFBException);
		rdoExternalFBException.setSelected(true);
		rdoExternalFBException.setLangMessageID("C01121");
		rdoExternalFBException.setSize(100, 20);
		rdoExternalFBException.setLocation(5, 5);
		pnlExternalFBDivision.add(rdoExternalFBException);

		btngrpExternalFBDivision.add(rdoExternalFBUse);
		rdoExternalFBUse.setLangMessageID("C01123");
		rdoExternalFBUse.setSize(100, 20);
		rdoExternalFBUse.setLocation(5, 25);
		pnlExternalFBDivision.add(rdoExternalFBUse);

		pnlExternalFBDivision.setLocation(pnlEmployeeFBDivision.getX() + pnlEmployeeFBDivision.getWidth() + 10,
			pnlEmployeeFBDivision.getY());
		pnlBody.add(pnlExternalFBDivision);

		ctrlAppropriateDepartment.setLocation(baseX + 55,
			pnlEmployeeFBDivision.getY() + pnlEmployeeFBDivision.getHeight() + COMPONENT_MARGIN_Y);
		pnlBody.add(ctrlAppropriateDepartment);

		ctrlItemUnit.setSize(ctrlItemUnit.ctrlItemReference.getWidth(), 70);
		ctrlItemUnit.ctrlSubItemReference.setLocation(0, 25);
		ctrlItemUnit.ctrlDetailItemReference.setLocation(0, 50);
		ctrlItemUnit.getItemSearchCondition().setSumItem(false);

		ctrlItemUnit.setLocation(baseX + 55, ctrlAppropriateDepartment.getY() + ctrlAppropriateDepartment.getHeight()
			+ COMPONENT_MARGIN_Y);
		pnlBody.add(ctrlItemUnit);

		dtBeginDate.setLabelSize(65);
		dtBeginDate.setLangMessageID("C00055");
		dtBeginDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtBeginDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));

		dtBeginDate.setLocation(baseX + 65, ctrlItemUnit.getY() + ctrlItemUnit.getHeight() + COMPONENT_MARGIN_Y);
		pnlBody.add(dtBeginDate);

		dtEndDate.setLabelSize(65);
		dtEndDate.setLangMessageID("C00261");
		dtEndDate.getCalendar().setMinimumDate(DateUtil.getDate(1900, 01, 01));
		dtEndDate.getCalendar().setMaximumDate(DateUtil.getDate(2099, 12, 31));

		dtEndDate.setLocation(dtBeginDate.getX() + dtBeginDate.getWidth() + 70, dtBeginDate.getY());
		pnlBody.add(dtEndDate);

	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TDialog#setTabIndex()
	 */
	@Override
	public void setTabIndex() {
		int i = 1;
		ctrlBankAccount.setTabControlNo(i++);
		txtBankAccountName.setTabControlNo(i++);
		ctrlCurrency.setTabControlNo(i++);
		ctrlBank.setTabControlNo(i++);
		ctrlBranch.setTabControlNo(i++);
		ctrlTransferRequesterCode.setTabControlNo(i++);
		ctrlTransferRequesterKanaName.setTabControlNo(i++);
		ctrlTransferRequesterKanjiName.setTabControlNo(i++);
		ctrlTransferRequesterEnglishName.setTabControlNo(i++);
		ctrlAccountNumber.setTabControlNo(i++);
		ctrlDepositType.setTabControlNo(i++);
		rdoEmployeeFBDivisionException.setTabControlNo(i++);
		rdoEmployeeFBUse.setTabControlNo(i++);
		rdoExternalFBException.setTabControlNo(i++);
		rdoExternalFBUse.setTabControlNo(i++);
		ctrlAppropriateDepartment.setTabControlNo(i++);
		ctrlItemUnit.setTabControlNo(i++);
		dtBeginDate.setTabControlNo(i++);
		dtEndDate.setTabControlNo(i++);
		btnSettle.setTabControlNo(i++);
		btnClose.setTabControlNo(i++);

	}

}
