package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import java.util.regex.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.ui.MG0150CustomerUsrMasterDialog.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.program.*;

/**
 * �����}�X�^ �R���g���[���[
 */
public class MG0150CustomerMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0150CustomerMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0150CustomerMasterDialog editView = null;

	/** �S���Ґݒ��� */
	protected MG0150CustomerUsrMasterDialog usrView = null;

	/** ���R�[�h�K�{�`�F�b�N���邩�ǂ��� true�F���� */
	protected boolean isUseCountryCheck = ClientConfig.isFlagOn("trans.MG0150.use.country.required.check");

	/** true:�O���[�v��Ћ敪�L�� */
	public static boolean isUseTRI_TYPE_GRP_FLG = ClientConfig.isFlagOn("trans.kt.MG0150.group.comp.flag");

	/** �����敪��\�����Ȃ����ǂ��� true:�\�����Ȃ� */
	protected static boolean isNoVisibleTriDivison = ClientConfig.isFlagOn("trans.MG0150.no.visible.tri.division");

	/** ���������`�F�b�N���邩�ǂ��� true:�`�F�b�N���� */
	protected static boolean isRequiredCustomerAccountNo = ClientConfig
		.isFlagOn("trans.MG0150.required.customer.account.no");

	/** �����S���Ґݒ��\�����邩�ǂ��� true:�\������ */
	protected static boolean isVisibleTriUsrSetting = ClientConfig.isFlagOn("trans.MG0150.visible.tri.usr.setting");

	/** �����̌h��/�S������/�S���҂Ȃǂ̐ݒ��\�����邩�ǂ��� true:�\������ */
	protected static boolean isUseCustomerManagementSet = ClientConfig
		.isFlagOn("trans.usr.customer.managementi.setting");
	
	/** ����悪���l���ۂ���\�����邩�ǂ��� true:�\������ */
	protected static boolean isUseCustomerIsPerson = ClientConfig.isFlagOn("trans.kt.MG0150.tri.person.flag");

	/** ��K�i���������s���Ǝ҂̐ݒ��\�����邩�ǂ��� true:�\������ */
	protected static boolean isInvoiceTaxProperty = ClientConfig.isFlagOn("trans.slip.use.invoice.tax");

	/** true: 2023INVOICE���x�Ή����g�p����(��Џ��܂�) */
	public static boolean isInvoice = false;

	/**
	 * ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B
	 */
	protected Mode mode = null;

	/**
	 * ���샂�[�h�B
	 * 
	 * @author AIS
	 */
	protected enum Mode {
		/** �V�K */
		NEW,
		/** �C�� */
		MODIFY,
		/** ���� */
		COPY
	}

	@Override
	public void start() {

		try {

			// �C���{�C�X�g�p���邩�ǂ���
			if (isInvoiceTaxProperty) {
				initInvoiceFlg();
			}

			// �w����ʐ���
			createMainView();

			// �w����ʂ�����������
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0150CustomerMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		// [�V�K]�{�^������
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�ҏW]�{�^������
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [����]�{�^������
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�폜]�{�^������
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�G�N�Z��]�{�^������
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�S���Ґݒ�]�{�^������
		mainView.btnUsr.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnUsr_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�S���҈ꗗ�o��]�{�^������
		mainView.btnUsrOut.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnUsrOut_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {

		// ���C���{�^���������s�\�ɂ���
		setMainButtonEnabled(false);

	}

	/**
	 * invoice�g�p���邩�ǂ���
	 */
	protected void initInvoiceFlg() {

		isInvoice = getCompany().isCMP_INV_CHK_FLG();
	}

	/**
	 * �w�����[�V�K]�{�^������
	 */
	protected void btnNew_Click() {

		try {

			// �ҏW��ʂ𐶐�
			createEditView();

			// �ҏW��ʂ�����������
			initEditView(Mode.NEW, null);

			// �ҏW��ʂ�\��
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnSearch_Click() {

		try {

			// ��������
			CustomerSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				// �x�����b�Z�[�W�\��
				showMessage(mainView, "I00068");
				mainView.TCustomerReferenceRange.ctrlCustomerReferenceFrom.code.requestFocus();
				return;
			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// ���������擾
			List<Customer> list = getCustomer(condition);

			// ���������ɊY���������悪���݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ���������ꗗ�ɕ\������
			for (Customer customer : list) {
				mainView.tbl.addRow(getRowData(customer));
			}

			// ���C���{�^���������\�ɂ��A1�s�ڂ�I������
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [�ҏW]�{�^������
	 */
	protected void btnModify_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �ҏW�Ώۂ̎������擾����
			Customer customer = getSelectedCustomer();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̎��������Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, customer);

			// �ҏW��ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [����]�{�^������
	 */
	protected void btnCopy_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// ���ʑΏۂ̎������擾����
			Customer customer = getSelectedCustomer();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̎��������Z�b�g����
			createEditView();
			initEditView(Mode.COPY, customer);

			// ���ʉ�ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [�폜]�{�^������
	 */
	protected void btnDelete_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̎������擾����
			Customer customer = getSelectedCustomer();

			// �폜����
			deleteCustomer(customer);

			// �폜�����������ꗗ����폜
			mainView.tbl.removeSelectedRow();

			// �폜��������0���ɂȂ����烁�C���{�^���������s�\�ɂ���
			if (mainView.tbl.getRowCount() == 0) {
				setMainButtonEnabled(false);
			}

			// �������b�Z�[�W
			showMessage(mainView.getParentFrame(), "I00013");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �I���s�̃`�F�b�N
	 * 
	 * @return true:�G���[�Ȃ�
	 */
	protected boolean checkMainView() {

		if (mainView.tbl.getSelectedRow() == -1) {
			// �ꗗ����f�[�^��I�����Ă��������B
			showMessage("I00043");
			return false;
		}

		return true;
	}

	/**
	 * [�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			CustomerSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02326") + ".xls");// �����}�X�^

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [�S���Ґݒ�]�{�^������
	 */
	protected void btnUsr_Click() {

		try {

			// �S���Ґݒ�̎������擾����
			Customer customer = getSelectedCustomer();

			// �����S���Ґݒ�����擾
			List<CustomerUser> list = getTRI_USR_MST(customer.getCode());

			// �S���Ґݒ��ʂ𐶐����A�ҏW�Ώۂ̎��������Z�b�g����
			createUsrView();
			initUsrView(customer);

			// �����S���Ґݒ�𖢓o�^�̏ꍇ
			if (list.isEmpty()) {
				SystemClassificationSearchCondition condition = new SystemClassificationSearchCondition();
				condition.setCompanyCode(getCompanyCode());
				List<SystemClassification> listSYS = getSYS_KBN(condition);
				// �����S���Ґݒ�����ꗗ�ɕ\������
				for (SystemClassification sys : listSYS) {
					usrView.tbl.addRow(getRowDataSYS(sys));
				}
			} else {
				// �����S���Ґݒ�����ꗗ�ɕ\������
				for (CustomerUser customerUsr : list) {
					usrView.tbl.addRow(getRowDataUsr(customerUsr));
				}
			}

			// �S���Ґݒ��ʂ�\������
			usrView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [�S���҈ꗗ�o��]�{�^������
	 */
	protected void btnUsrOut_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			CustomerSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getUsrExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00363") + getWord("C00010") + ".xls");// �S���҈ꗗ

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0150CustomerMasterDialog(mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

	}

	/**
	 * �S���Ґݒ��ʂ̃t�@�N�g���B�V�K�ɒS���Ґݒ��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createUsrView() {

		// �S���Ґݒ��ʂ𐶐�
		usrView = new MG0150CustomerUsrMasterDialog(mainView.getParentFrame(), true);

		// �S���Ґݒ��ʂ̃C�x���g��`
		addUsrViewEvent();

	}

	/**
	 * �ҏW��ʂ̃C�x���g��`�B
	 */
	protected void addEditViewEvent() {

		// [�m��]�{�^������
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�񓾈Ӑ�]�I��
		editView.rdoNotTokui.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				changedCustomer(e.getStateChange() != ItemEvent.SELECTED);
			}
		});

		// [��K�i���������s���Ǝ�]�I��
		editView.chkNoInvReg.addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				chkNoInvReg(editView.chkNoInvReg.isSelected());
			}
		});

		// �d����敪��d����I��
		editView.rdoNotSiire.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (editView.rdoNotSiire.isSelected() && isInvoice) {
					btnSiire_Click(false);
				}
			}

		});

		// �d����敪�d����I��
		editView.rdoSiire.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				if (isInvoice) {
					btnSiire_Click(true);
				}
			}
		});
	}

	/**
	 * �S���Ґݒ��ʂ̃C�x���g��`�B
	 */
	protected void addUsrViewEvent() {

		// [�m��]�{�^������
		usrView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				usrView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettleUsr_Click();
				usrView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		usrView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				usrView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCloseUsr_Click();
				usrView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �񓾈Ӑ�/���Ӑ�ؑ�
	 * 
	 * @param isCustomer
	 */
	protected void changedCustomer(boolean isCustomer) {
		editView.ctrlBankAccount.setEditable(isCustomer);
		editView.ctrlClientName.setEditable(isCustomer);
		editView.ctrlCustomerClosedDaySetting.setEditable(isCustomer);
		editView.rdoTosha.setEnabled(isCustomer);
		editView.rdoSenpo.setEnabled(isCustomer);

		if (!isCustomer) {
			editView.ctrlBankAccount.clear();
			editView.ctrlClientName.clear();
			editView.ctrlCustomerClosedDaySetting.clear();
		}

	}

	/**
	 * ��K�i���������s���Ǝ҃`�F�b�N�{�b�N�X����
	 * 
	 * @param flg
	 */
	protected void chkNoInvReg(boolean flg) {
		editView.ctrlInvRegNo.clear();
		editView.ctrlInvRegNo.setEditable(!flg);
		editView.ctrlTaxReference.clear();
		editView.ctrlTaxReference.setEditable(flg);

	}

	/**
	 * �ҏW���[�d����敪]�{�^������ �C���{�C�X����Ő���
	 * 
	 * @param flg ��d��:false
	 */
	protected void btnSiire_Click(boolean flg) {
		editView.chkNoInvReg.setEnabled(flg);
		if (!flg) {
			editView.chkNoInvReg.setSelected(flg);
			editView.ctrlInvRegNo.clear();
			editView.ctrlInvRegNo.setEditable(flg);
			editView.ctrlTaxReference.clear();
			editView.ctrlTaxReference.setEditable(flg);
		} else {
			chkNoInvReg(false);
		}
	}

	/**
	 * �S���Ґݒ��ʂ�����������
	 * 
	 * @param customer �B�C���A���ʂ̏ꍇ�͓��Y����ҏW��ʂɃZ�b�g����B
	 */
	protected void initUsrView(Customer customer) {
		usrView.setTitle(getWord("C00363") + getWord("C00319") + getWord("C00075"));
		usrView.ctrlTriCode.setValue(customer.getCode());
		usrView.ctrlTriName.setValue(customer.getName());

	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ ���샂�[�h�B
	 * @param customer �B�C���A���ʂ̏ꍇ�͓��Y����ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode mode_, Customer customer) {

		this.mode = mode_;

		// ����Ń��t�@�����X�F�C���{�C�X��K�i���������s���Ǝ҃t���O�ݒ�
		editView.ctrlTaxReference.getSearchCondition().setNoInvRegFlg(true);
		// �d���̂ݕ\��
		editView.ctrlTaxReference.getSearchCondition().setPurcharseOnliy(true);

		// �V�K�̏ꍇ
		if (Mode.NEW == mode_) {
			editView.setTitle(getWord("C01698"));
			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
			editView.ctrlTaxReference.setEditable(false);

			if (!isUseTRI_TYPE_GRP_FLG) {
				editView.chkGrp.setVisible(false);
			}

			// �v���p�e�B�ɂ���\���ݒ�
			if (isNoVisibleTriDivison) {

				editView.pnlTri.setVisible(false);

				editView.chkChtr.setVisible(false);
				editView.chkOnr.setVisible(false);
				editView.chkAgnt.setVisible(false);
				editView.chkSplr.setVisible(false);
				editView.chkBrkr.setVisible(false);
				editView.chkBank.setVisible(false);
				editView.chkOzr.setVisible(false);

				editView.chkShpp.setVisible(false);
				editView.chkCons.setVisible(false);
				editView.chkNtpt.setVisible(false);
				editView.chkFwd.setVisible(false);
				editView.chkBktr.setVisible(false);
				editView.chkBksp.setVisible(false);
			}

			if (!isUseCustomerManagementSet) {
				editView.pnlCusTitle.setVisible(false);
				editView.cboCusTitle.setVisible(false);
				editView.cboChargeTitle.setVisible(false);
				editView.ctrlDepInCharge.setVisible(false);
				editView.ctrlChargeName.setVisible(false);
			}
			if (!isUseCustomerIsPerson) {
				editView.chkHideBankNo.setVisible(false);
			}
			if (isInvoice) {
				btnSiire_Click(false);
			} else {
				editView.chkNoInvReg.setVisible(false);
				editView.ctrlTaxReference.setVisible(false);
				editView.ctrlInvRegNo.setVisible(false);
			}

			return;
		}

		// �ҏW
		if (Mode.MODIFY == mode_) {
			editView.setTitle(getWord("C00977"));
			editView.ctrlTriCode.setEditable(false);
			editView.setEditMode();
		}
		// ����
		else {
			editView.setTitle(getWord("C01738"));
		}

		editView.ctrlTriCode.setValue(customer.getCode());
		editView.ctrlTriName.setValue(customer.getName());
		editView.ctrlTriNames.setValue(customer.getNames());
		editView.ctrlTriNamek.setValue(customer.getNamek());
		editView.ctrlPostNum.setValue(customer.getZipCode());
		editView.ctrlCountry.setEntity(customer.getCountry());
		editView.ctrlOfficeName.setValue(customer.getOfficeName());
		editView.ctrlTelNum.setValue(customer.getTel());
		editView.ctrlFaxNum.setValue(customer.getFax());
		editView.ctrlAddressNum1.setValue(customer.getAddress());
		editView.ctrlAddressNum2.setValue(customer.getAddress2());
		editView.ctrlAddressNumKana.setValue(customer.getAddressKana());
		editView.ctrlEMailAddress.setValue(customer.getEmailAddress());

		if (CustomerType.CUSTOMER == customer.getCustomerType() || CustomerType.BOTH == customer.getCustomerType()) {
			editView.ctrlBankAccount.setEditable(true);
			editView.ctrlClientName.setEditable(true);
			editView.ctrlCustomerClosedDaySetting.setEditable(true);
			editView.rdoTosha.setEnabled(true);
			editView.rdoSenpo.setEnabled(true);

			editView.ctrlBankAccount.setEntity(customer.getBankAccount());
			editView.ctrlClientName.setValue(customer.getClientName());
			editView.ctrlCustomerClosedDaySetting.setValue(customer);
		}

		if (CashInFeeType.Other == customer.getCashInFeeType()) {
			editView.rdoSenpo.setSelected(true);
		} else {
			editView.rdoTosha.setSelected(true);
		}

		boolean isSiire = false;
		boolean isTokui = false;

		switch (customer.getCustomerType()) {
			case BOTH:
				isSiire = true;
				isTokui = true;
				break;

			case VENDOR:
				isSiire = true;
				break;

			case CUSTOMER:
				isTokui = true;
				break;

			case NONE:
			default:
				break;
		}

		editView.rdoSiire.setSelected(isSiire);
		editView.rdoTokui.setSelected(isTokui);

		editView.TCustomerReference.setCode(customer.getSumCode());
		editView.TCustomerReference.setNames(customer.getSumName());

		editView.dtBeginDate.setValue(customer.getDateFrom());
		editView.dtEndDate.setValue(customer.getDateTo());

		// ��K�i���������s���Ǝ� on��off��
		chkNoInvReg(editView.chkNoInvReg.isSelected());

		// �v���p�e�B�ɂ���\���ݒ�
		if (isNoVisibleTriDivison) {

			editView.pnlTri.setVisible(false);

			editView.chkChtr.setVisible(false);
			editView.chkOnr.setVisible(false);
			editView.chkAgnt.setVisible(false);
			editView.chkSplr.setVisible(false);
			editView.chkBrkr.setVisible(false);
			editView.chkBank.setVisible(false);
			editView.chkOzr.setVisible(false);

			editView.chkShpp.setVisible(false);
			editView.chkCons.setVisible(false);
			editView.chkNtpt.setVisible(false);
			editView.chkFwd.setVisible(false);
			editView.chkBktr.setVisible(false);
			editView.chkBksp.setVisible(false);
		} else {
			editView.chkChtr.setSelected(customer.isCharterer());
			editView.chkOnr.setSelected(customer.isOwner());
			editView.chkAgnt.setSelected(customer.isPortAgent());
			editView.chkSplr.setSelected(customer.isSupplier());
			editView.chkBrkr.setSelected(customer.isBroker());
			editView.chkBank.setSelected(customer.isBank());
			editView.chkOzr.setSelected(customer.isOther());

			editView.chkShpp.setSelected(customer.isShipper());
			editView.chkCons.setSelected(customer.isConsignee());
			editView.chkNtpt.setSelected(customer.isNotifyParty());
			editView.chkFwd.setSelected(customer.isFowarder());
			editView.chkBktr.setSelected(customer.isBunkerTrader());
			editView.chkBksp.setSelected(customer.isBunkerSupplier());
		}
		editView.chkHideBankNo.setSelected(customer.isPersonalCustomer());

		if (isUseTRI_TYPE_GRP_FLG) {
			editView.chkGrp.setSelected(customer.isGroupCompany());
		} else {
			editView.chkGrp.setVisible(false);
		}

		if (isUseCustomerManagementSet) {
			editView.cboCusTitle.setSelectedItemValue(customer.getTRI_TITLE_TYPE());
			editView.cboChargeTitle.setSelectedItemValue(customer.getEMP_TITLE_TYPE());
			editView.ctrlDepInCharge.setValue(customer.getCHARGE_DEP_NAME());
			editView.ctrlChargeName.setValue(customer.getCHARGE_EMP_NAME());
		} else {
			editView.pnlCusTitle.setVisible(false);
			editView.cboCusTitle.setVisible(false);
			editView.cboChargeTitle.setVisible(false);
			editView.ctrlDepInCharge.setVisible(false);
			editView.ctrlChargeName.setVisible(false);
		}

		if (isInvoice) {
			if (!customer.isPurchase()) {
				btnSiire_Click(false);
			} else {
				editView.chkNoInvReg.setSelected(customer.isNO_INV_REG_FLG());
				editView.ctrlInvRegNo.setValue(customer.getINV_REG_NO());
				editView.ctrlTaxReference.setCode(Util.avoidNull(customer.getNO_INV_REG_ZEI_CODE()));
				editView.ctrlTaxReference.refleshAndShowEntity();
			}
		} else {
			editView.chkNoInvReg.setVisible(false);
			editView.ctrlInvRegNo.setVisible(false);
			editView.ctrlTaxReference.setVisible(false);
		}
	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// ���̓`�F�b�N���s���B
			if (!isInputCorrect()) {
				return;
			}

			// ���͂��ꂽ���������擾
			Customer customer = getInputedCustomer();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", customer);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(customer));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", customer);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(customer));

			}

			// �ҏW��ʂ����
			btnClose_Click();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �S���Ґݒ���[�m��]�{�^������
	 */
	protected void btnSettleUsr_Click() {

		try {

			// ���͂��ꂽ�����S���Ґݒ�����擾
			List<CustomerUser> list = getInputedCustomerUsr();

			// �V�K�o�^
			request(getModelClass(), "entryTRI_USR_MST", list);

			// ���C���{�^���������\�ɂ���
			setMainButtonEnabled(true);

			// �S���Ґݒ��ʂ����
			btnCloseUsr_Click();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW���[�߂�]�{�^������
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * �S���Ґݒ���[�߂�]�{�^������
	 */
	protected void btnCloseUsr_Click() {
		usrView.setVisible(false);
	}

	/**
	 * �w����ʂœ��͂��ꂽ�����̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected CustomerSearchCondition getSearchCondition() {

		CustomerSearchCondition condition = new CustomerSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCodeFrom(mainView.TCustomerReferenceRange.getCodeFrom());
		condition.setCodeTo(mainView.TCustomerReferenceRange.getCodeTo());
		condition.setInvoiceFlg(isInvoice);
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;

	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return CustomerManager
	 */
	protected Class getModelClass() {
		return CustomerManager.class;
	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return CustomerManager
	 */
	protected Class getModelClassSYS() {
		return ProgramManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ����Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�����
	 */
	protected Customer getInputedCustomer() {

		Customer customer = createCustomer();
		customer.setCompanyCode(getCompany().getCode());
		customer.setCode(editView.ctrlTriCode.getValue());
		customer.setName(editView.ctrlTriName.getValue());
		customer.setNames(editView.ctrlTriNames.getValue());
		customer.setNamek(editView.ctrlTriNamek.getValue());
		customer.setOfficeName(editView.ctrlOfficeName.getValue());
		customer.setZipCode(editView.ctrlPostNum.getValue());
		customer.setTel(editView.ctrlTelNum.getValue());
		customer.setFax(editView.ctrlFaxNum.getValue());
		customer.setCountry(editView.ctrlCountry.getEntity()); // ���G���e�B�e�B
		customer.setCountryCode(editView.ctrlCountry.getCode()); // ���R�[�h
		customer.setAddress(editView.ctrlAddressNum1.getValue());
		customer.setAddress2(editView.ctrlAddressNum2.getValue());
		customer.setAddressKana(editView.ctrlAddressNumKana.getValue());
		customer.setEmailAddress(editView.ctrlEMailAddress.getValue());
		customer.setBankAccount(editView.ctrlBankAccount.getEntity());
		customer.setBankAccountCode(editView.ctrlBankAccount.getCode());
		customer.setClientName(editView.ctrlClientName.getValue());

		if (editView.rdoTokui.isSelected()) {
			customer.setCloseDay(editView.ctrlCustomerClosedDaySetting.getCloseDay());
			customer.setNextMonth(editView.ctrlCustomerClosedDaySetting.getNextMonth());
			customer.setCashDay(editView.ctrlCustomerClosedDaySetting.getCashDay());
		}

		if (editView.rdoTosha.isSelected()) {
			customer.setCashInFeeType(CashInFeeType.Our);
		} else {
			customer.setCashInFeeType(CashInFeeType.Other);
		}

		customer.setPurchase(editView.rdoSiire.isSelected());
		customer.setClient(editView.rdoTokui.isSelected());
		customer.setSumCode(editView.TCustomerReference.getCode());
		customer.setSumName(editView.TCustomerReference.getNames());

		if (editView.rdoSiire.isSelected() && editView.rdoTokui.isSelected()) {
			customer.setCustomerType(CustomerType.BOTH);
		} else if (!editView.rdoSiire.isSelected() && editView.rdoTokui.isSelected()) {
			customer.setCustomerType(CustomerType.CUSTOMER);
		} else if (editView.rdoSiire.isSelected() && !editView.rdoTokui.isSelected()) {
			customer.setCustomerType(CustomerType.VENDOR);
		} else {
			customer.setCustomerType(CustomerType.NONE);
		}

		customer.setDateFrom(editView.dtBeginDate.getValue());
		customer.setDateTo(editView.dtEndDate.getValue());

		customer.setCharterer(editView.chkChtr.isSelected());
		customer.setOwner(editView.chkOnr.isSelected());
		customer.setPortAgent(editView.chkAgnt.isSelected());
		customer.setSupplier(editView.chkSplr.isSelected());
		customer.setBroker(editView.chkBrkr.isSelected());
		customer.setBank(editView.chkBank.isSelected());
		customer.setOther(editView.chkOzr.isSelected());

		customer.setShipper(editView.chkShpp.isSelected());
		customer.setConsignee(editView.chkCons.isSelected());
		customer.setNotifyParty(editView.chkNtpt.isSelected());
		customer.setFowarder(editView.chkFwd.isSelected());
		customer.setBunkerTrader(editView.chkBktr.isSelected());
		customer.setBunkerSupplier(editView.chkBksp.isSelected());
		customer.setPersonalCustomer(editView.chkHideBankNo.isSelected());

		if (isUseTRI_TYPE_GRP_FLG) {
			customer.setGroupCompany(editView.chkGrp.isSelected());
		}
		if (isUseCustomerManagementSet) {
			customer.setTRI_TITLE_TYPE((HonorType) editView.cboCusTitle.getSelectedItemValue());
			customer.setEMP_TITLE_TYPE((HonorType) editView.cboChargeTitle.getSelectedItemValue());
			customer.setCHARGE_DEP_NAME(editView.ctrlDepInCharge.getValue());
			customer.setCHARGE_EMP_NAME(editView.ctrlChargeName.getValue());
		}

		if (isInvoice) {
			customer.setNO_INV_REG_FLG(editView.chkNoInvReg.isSelected());
			customer.setINV_REG_NO(editView.ctrlInvRegNo.getValue());
			customer.setNO_INV_REG_ZEI_CODE(Util.isNullOrEmpty(editView.ctrlTaxReference.getCode()) ? null
				: editView.ctrlTaxReference.getCode());
			customer.setNO_INV_REG_ZEI_NAME(Util.isNullOrEmpty(editView.ctrlTaxReference.getNames()) ? null
				: editView.ctrlTaxReference.getNames());
		}

		return customer;

	}

	/**
	 * �S���Ґݒ��ʂœ��͂��ꂽ����Ԃ�
	 * 
	 * @return �S���Ґݒ��ʂœ��͂��ꂽ�����
	 */
	protected List<CustomerUser> getInputedCustomerUsr() {

		List<CustomerUser> list = new ArrayList<CustomerUser>();
		for (int row = 0; row < usrView.tbl.getRowCount(); row++) {
			CustomerUser bean = new CustomerUser();
			bean.setKAI_CODE(getCompanyCode());
			bean.setTRI_CODE(usrView.ctrlTriCode.getValue());
			bean.setSYS_KBN(Util.avoidNull(usrView.tbl.getRowValueAt(row, TriColum.SYS_KBN)));
			bean.setUSR_NAME(Util.avoidNull(usrView.tbl.getRowValueAt(row, TriColum.USR_NAME)));
			bean.setDEP_NAME(Util.avoidNull(usrView.tbl.getRowValueAt(row, TriColum.DEP_NAME)));
			bean.setPOSITION(Util.avoidNull(usrView.tbl.getRowValueAt(row, TriColum.POSITION)));
			list.add(bean);
		}

		return list;

	}

	/**
	 * �����t�@�N�g��
	 * 
	 * @return Customer
	 */
	protected Customer createCustomer() {
		return new Customer();
	}

	/**
	 * ���������ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param customer �����
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�����
	 */
	protected Object[] getRowData(Customer customer) {

		return new Object[] {
				customer.getCode(), //
				customer.getName(), //
				customer.getNames(), //
				customer.getNamek(), //
				getTriType(customer),
				getWord(customer.isGroupCompany() ? "C10583" : ""), // �O���[�v��Ћ敪
				customer.getOfficeName(), //
				customer.getZipCode(), //
				customer.getCountryCode(), (customer.getCountry() == null) ? null : customer.getCountry().getName(),
				customer.getAddressKana(), //
				customer.getAddress(), //
				customer.getAddress2(), //
				customer.getEmailAddress(), //
				getWord(HonorType.getName(customer.getTRI_TITLE_TYPE())), //
				getWord(HonorType.getName(customer.getEMP_TITLE_TYPE())), //
				customer.getCHARGE_DEP_NAME(), //
				customer.getCHARGE_EMP_NAME(), //
				customer.getTel(), //
				customer.getFax(), //
				customer.getSumCode(), //
				customer.getSumName(), //
				getWord(CustomerType.getVenderName(customer.getCustomerType())), //
				getWord(CustomerType.getCustomerName(customer.getCustomerType())), //
				customer.getCloseDay(), //
				customer.getNextMonth(), //
				customer.getCashDay(), //
				customer.getBankAccountCode(), //
				(customer.getBankAccount() == null) ? null : customer.getBankAccount().getName(), //
				customer.getClientName(), //
				getWord(CashInFeeType.getCashInFeeTypeName(customer.getCashInFeeType())), //
				customer.isNO_INV_REG_FLG() ? getWord("C12198") : "", //
				customer.getNO_INV_REG_ZEI_NAME(), //
				customer.getINV_REG_NO(), //
				DateUtil.toYMDString(customer.getDateFrom()), //
				DateUtil.toYMDString(customer.getDateTo()) //
		};
	}

	/**
	 * �����S���Ґݒ�����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param sys �V�X�e�����
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�����
	 */
	protected Object[] getRowDataSYS(SystemClassification sys) {

		return new Object[] { sys, sys.getCode(), null, null, null };
	}

	/**
	 * �����S���Ґݒ�����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param customerUsr �V�X�e�����
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�����
	 */
	protected Object[] getRowDataUsr(CustomerUser customerUsr) {

		return new Object[] { customerUsr, customerUsr.getSYS_KBN(), customerUsr.getUSR_NAME(),
				customerUsr.getDEP_NAME(), customerUsr.getPOSITION() };
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
		mainView.btnUsr.setEnabled(bln);
	}

	/**
	 * ���������ɊY����������̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY����������̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Customer> getCustomer(CustomerSearchCondition condition) throws Exception {

		List<Customer> list = (List<Customer>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * ���������ɊY����������̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY����������̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<SystemClassification> getSYS_KBN(SystemClassificationSearchCondition condition) throws Exception {

		List<SystemClassification> list = (List<SystemClassification>) request(getModelClassSYS(), "getSystem",
			condition);

		return list;
	}

	/**
	 * ���������ɊY����������̃��X�g��Ԃ�
	 * 
	 * @param triCode �����R�[�h
	 * @return ���������ɊY����������̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<CustomerUser> getTRI_USR_MST(String triCode) throws Exception {

		List<CustomerUser> list = (List<CustomerUser>) request(getModelClass(), "getTRI_USR_MST", triCode);

		return list;
	}

	/**
	 * �ꗗ�őI������Ă��������Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă�������
	 * @throws Exception
	 */
	protected Customer getSelectedCustomer() throws Exception {

		CustomerSearchCondition condition = new CustomerSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0150CustomerMasterPanel.SC.triCode));

		List<Customer> list = getCustomer(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂���
			throw new TException("I00193");
		}
		return list.get(0);
	}

	/**
	 * �w��̎������폜����
	 * 
	 * @param customer
	 * @throws Exception
	 */
	protected void deleteCustomer(Customer customer) throws Exception {
		request(getModelClass(), "delete", customer);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// �����R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlTriCode.getValue())) {
			// �����R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C00786");
			editView.ctrlTriCode.requestTextFocus();
			return false;
		}

		// ����於�̂������͂̏ꍇ�G���[ if
		if (Util.isNullOrEmpty(editView.ctrlTriName.getValue())) {
			// ����於�̂���͂��Ă��������B
			showMessage(editView, "I00037", "C00830");
			editView.ctrlTriName.requestTextFocus();
			return false;
		}

		// ����旪�̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlTriNames.getValue())) {
			// ����旪�̂���͂��Ă��������B
			showMessage(editView, "I00037", "C00787");
			editView.ctrlTriNames.requestTextFocus();
			return false;
		}

		// ����挟�����̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlTriNamek.getValue())) {
			// ����挟�����̂���͂��Ă��������B
			showMessage(editView, "I00037", "C00836");
			editView.ctrlTriNamek.requestTextFocus();
			return false;
		}

		// ���������͂̏ꍇ�G���[
		if (isUseCountryCheck && Util.isNullOrEmpty(editView.ctrlCountry.getCode())) {
			// ������͂��Ă��������B
			showMessage(editView, "I00037", "C11415");
			editView.ctrlCountry.requestTextFocus();
			return false;

		}

		// EMail Address�����[���A�h���X�`��(@.*)�ȊO�̏ꍇ�G���[
		if (!Util.isNullOrEmpty(editView.ctrlEMailAddress.getValue())) {
			String email = editView.ctrlEMailAddress.getValue();
			if (!checkMailAddressByRegularExpression(email)) {
				// ���������[���A�h���X����͂��Ă��������B
				showMessage(editView, "I00789");
				editView.ctrlEMailAddress.requestTextFocus();
				return false;
			}

		}

		// ���Ӑ�̏ꍇ
		if (isRequiredCustomerAccountNo && editView.rdoTokui.isSelected()) {

			// ��s�����������͂̏ꍇ�G���[
			if (Util.isNullOrEmpty(editView.ctrlBankAccount.getCode())) {
				// ������s��������͂��Ă��������B
				showMessage(editView, "I00037", "C01268");
				editView.ctrlBankAccount.requestTextFocus();
				return false;

			}

			// ��������(����)�������͂̏ꍇ�G���[
			if (editView.rdoTokui.isSelected()
				&& Util.isNullOrEmpty(editView.ctrlCustomerClosedDaySetting.closeDay.getValue())) {
				// ��������(������)����͂��Ă��������B
				showMessage(editView, "I00037", "C11092");
				editView.ctrlCustomerClosedDaySetting.closeDay.requestFocus();
				return false;
			}

			// ��������(������)�������͂̏ꍇ�G���[
			if (editView.rdoTokui.isSelected()
				&& Util.isNullOrEmpty(editView.ctrlCustomerClosedDaySetting.nextMonth.getValue())) {
				// ��������(������)����͂��Ă��������B
				showMessage(editView, "I00037", "C11090");
				editView.ctrlCustomerClosedDaySetting.nextMonth.requestFocus();
				return false;
			}

			// ��������(���ߓ�)�������͂̏ꍇ�G���[
			if (editView.rdoTokui.isSelected()
				&& Util.isNullOrEmpty(editView.ctrlCustomerClosedDaySetting.cashDay.getValue())) {
				// ��������(������)����͂��Ă��������B
				showMessage(editView, "I00037", "C11091");
				editView.ctrlCustomerClosedDaySetting.cashDay.requestFocus();
				return false;
			}

			int close = editView.ctrlCustomerClosedDaySetting.getCloseDay();
			if (close < 1 || (31 < close && close != 99)) {
				// �����ɂ́A1�`31�A�܂��́A99(����)����͂��Ă��������B
				showMessage(editView, "I00209", "C03552");
				editView.ctrlCustomerClosedDaySetting.closeDay.requestFocus();
				return false;
			}

			int cash = editView.ctrlCustomerClosedDaySetting.getCashDay();

			if (cash < 1 || (31 < cash && cash != 99)) {
				// �x�����ɂ́A1�`31�A�܂��́A99(����)����͂��Ă��������B
				showMessage(editView, "I00209", "C03552");
				editView.ctrlCustomerClosedDaySetting.cashDay.requestFocus();
				return false;
			}

		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			// �J�n�N��������͂��Ă�������
			showMessage(editView, "I00037", "C00055");
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			// �I���N��������͂��Ă�������
			showMessage(editView, "I00037", "C00261");
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// �J�n�N����,�I���N�����`�F�b�N
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓������悪���ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			CustomerSearchCondition condition = new CustomerSearchCondition();
			condition.setCode(editView.ctrlTriCode.getValue());
			condition.setCompanyCode(getCompanyCode());
			List<Customer> list = getCustomer(condition);
			if (list != null && !list.isEmpty()) {
				// �w��̎����͊��ɑ��݂��܂��B
				showMessage(editView, "I00148", "C00408");
				editView.ctrlTriCode.requestTextFocus();
				return false;
			}
		}

		return true;
	}

	/**
	 * �I������Ă�������敪��A��
	 * 
	 * @param customer
	 * @return �I�����ꂽ�����敪
	 */
	protected String getTriType(Customer customer) {

		// �����敪
		StringBuilder sb = new StringBuilder();
		if (customer.isCharterer()) {
			sb.append(getWord("CTC0058"));
		}
		if (customer.isOwner()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0059"));
		}
		if (customer.isPortAgent()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0115"));
		}
		if (customer.isSupplier()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0116"));
		}
		if (customer.isBroker()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0078"));
		}
		// BANK
		if (customer.isBank()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("COP532"));
		}
		if (customer.isOther()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CTC0117"));
		}
		if (customer.isShipper()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL206"));
		}
		if (customer.isConsignee()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL209"));
		}
		if (customer.isNotifyParty()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL320"));
		}
		if (customer.isFowarder()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL833"));
		}
		if (customer.isBunkerTrader()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL834"));
		}
		if (customer.isBunkerSupplier()) {
			if (!Util.isNullOrEmpty(sb)) {
				sb.append(", ");
			}
			sb.append(getWord("CBL835"));
		}

		return sb.toString();
	}

	/**
	 * ���[���A�h���X�`�F�b�N
	 * 
	 * @param address �`�F�b�N�Ώۂ̃A�h���X
	 * @return ���������[���A�h���X��true *@*.*�`���ȊO��false
	 */
	public static boolean checkMailAddressByRegularExpression(String address) {

		String aText = "[\\w!#%&'/=~`\\*\\+\\?\\{\\}\\^\\$\\-\\|]";
		String dotAtom = aText + "+" + "(\\." + aText + "+)*";
		String regularExpression = "^" + dotAtom + "@" + dotAtom + "$";
		Pattern pattern = Pattern.compile(regularExpression);
		Matcher matcher = pattern.matcher(address);
		if (matcher.find()) {
			return true;
		}
		return false;
	}

}
