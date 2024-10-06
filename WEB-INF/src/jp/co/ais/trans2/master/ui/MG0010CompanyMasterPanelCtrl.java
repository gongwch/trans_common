package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.close.*;
import jp.co.ais.trans2.model.company.*;

/**
 * ��ЃR���g���[���}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0010CompanyMasterPanelCtrl extends TController {

	/** SPC�^�u�\���� */
	protected static boolean isNotShowSpc = ClientConfig.isFlagOn("trans.company.master.not.show.spc");

	/** INVOICE(��Њ�b���p���)�^�u�\���� */
	protected static boolean isShowCompanyEng = ClientConfig.isFlagOn("trans.company.master.show.company.eng");

	/** AR�F�p��������SIGNER�\���� */
	protected static boolean isShowARSignerEng = ClientConfig.isFlagOn("trans.company.master.show.ar.signer.eng");

	/** true: 2023INVOICE���x�Ή����g�p���� */
	public static boolean isInvoice = ClientConfig.isFlagOn("trans.slip.use.invoice.tax");

	/** �w����� */
	protected MG0010CompanyMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0010CompanyMasterDialog editView = null;

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
		mainView = new MG0010CompanyMasterPanel();
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

	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {

		// ���C���{�^���������s�\�ɂ���
		setMainButtonEnabled(false);

		// �Č���
		btnSearch_Click();

		// ��ЃR�[�h�J�n�Ƀt�H�[�J�X
		mainView.ctrlCompanyRange.ctrlCompanyReferenceFrom.code.requestFocus();

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

			// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
			if (Util.isSmallerThen(mainView.ctrlCompanyRange.getCodeFrom(), mainView.ctrlCompanyRange.getCodeTo()) == false) {
				showMessage(editView, "I00068");
				mainView.ctrlCompanyRange.getFieldFrom().requestFocus();
				return;

			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// ��Џ����擾
			CompanySearchCondition condition = getSearchCondition();
			List<Company> list = getCompany(condition);

			// ���������ɊY�������Ђ����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ��Џ����ꗗ�ɕ\������
			for (Company company : list) {
				mainView.tbl.addRow(getRowData(company));
			}

			// ���C���{�^���������\�ɂ��A1�s�ڂ�I������
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[�ҏW]�{�^������
	 */
	protected void btnModify_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// �ҏW�Ώۂ̉�Ђ��擾����
			Company company = getSelectedCompany();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̉�Џ����Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, company);

			// �ҏW��ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnCopy_Click() {

		try {

			if (!checkMainView()) {
				return;
			}

			// ���ʑΏۂ̉�Ђ��擾����
			Company company = getSelectedCompany();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̉�Џ����Z�b�g����
			createEditView();
			initEditView(Mode.COPY, company);

			// ���ʉ�ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[�폜]�{�^������
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

			// �폜�Ώۂ̉�Ђ��擾����
			Company company = getSelectedCompany();

			// �폜����
			deleteCompany(company);

			// �폜������Ђ��ꗗ����폜
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
	 * �w�����[�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �f�[�^���o
			CompanySearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C00910") + ".xls");// ��ЃR���g���[���}�X�^

		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0010CompanyMasterDialog(getCompany(), mainView.getParentFrame(), true);
		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

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

		// ����Ǘ��`�F�b�N
		editView.chkDetailItem.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkDetailItem.isSelected()) {
					editView.ctrlDetailItemName.setEditable(true);
				} else {
					editView.ctrlDetailItemName.setEditable(false);
					editView.ctrlDetailItemName.setText(null);
				}
			}
		});

		// �Ǘ�1�`�F�b�N
		editView.chkManagement1.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkManagement1.isSelected()) {
					editView.ctrlManagement1Name.setEditable(true);
				} else {
					editView.ctrlManagement1Name.setEditable(false);
					editView.ctrlManagement1Name.setText(null);
				}
			}
		});

		// �Ǘ�2�`�F�b�N
		editView.chkManagement2.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkManagement2.isSelected()) {
					editView.ctrlManagement2Name.setEditable(true);
				} else {
					editView.ctrlManagement2Name.setEditable(false);
					editView.ctrlManagement2Name.setText(null);
				}
			}
		});

		// �Ǘ�3�`�F�b�N
		editView.chkManagement3.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkManagement3.isSelected()) {
					editView.ctrlManagement3Name.setEditable(true);
				} else {
					editView.ctrlManagement3Name.setEditable(false);
					editView.ctrlManagement3Name.setText(null);
				}
			}
		});

		// �Ǘ�4�`�F�b�N
		editView.chkManagement4.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkManagement4.isSelected()) {
					editView.ctrlManagement4Name.setEditable(true);
				} else {
					editView.ctrlManagement4Name.setEditable(false);
					editView.ctrlManagement4Name.setText(null);
				}
			}
		});

		// �Ǘ�5�`�F�b�N
		editView.chkManagement5.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkManagement5.isSelected()) {
					editView.ctrlManagement5Name.setEditable(true);
				} else {
					editView.ctrlManagement5Name.setEditable(false);
					editView.ctrlManagement5Name.setText(null);
				}
			}
		});

		// �Ǘ�6�`�F�b�N
		editView.chkManagement6.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.chkManagement6.isSelected()) {
					editView.ctrlManagement6Name.setEditable(true);
				} else {
					editView.ctrlManagement6Name.setEditable(false);
					editView.ctrlManagement6Name.setText(null);
				}
			}
		});

		// ���v���׋敪1
		editView.cboNotAccount1.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.cboNotAccount1.getSelectedIndex() == 0) {
					editView.ctrlNotAccount1Name.setEditable(false);
					editView.ctrlNotAccount1Name.setText(null);
				} else {
					editView.ctrlNotAccount1Name.setEditable(true);
				}
			}
		});
		// ���v���׋敪2
		editView.cboNotAccount2.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.cboNotAccount2.getSelectedIndex() == 0) {
					editView.ctrlNotAccount2Name.setEditable(false);
					editView.ctrlNotAccount2Name.setText(null);
				} else {
					editView.ctrlNotAccount2Name.setEditable(true);
				}
			}
		});

		// ���v���׋敪3
		editView.cboNotAccount3.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.cboNotAccount3.getSelectedIndex() == 0) {
					editView.ctrlNotAccount3Name.setEditable(false);
					editView.ctrlNotAccount3Name.setText(null);
				} else {
					editView.ctrlNotAccount3Name.setEditable(true);
				}
			}
		});

		// ���Z���s���^�s��Ȃ�
		editView.cboSettlement.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				if (editView.cboSettlement.getSelectedIndex() == 0) {
					editView.ctrlSettlementStage.setEditable(true);
				} else {
					editView.ctrlSettlementStage.setEditable(false);
					editView.ctrlSettlementStage.setText(null);
				}
			}
		});
		// ���[�N�t���[���F
		editView.cboWorkFlowApprove.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				afterWorkflowChanged();
			}
		});

	}

	/**
	 * ���[�N�t���[���F�`�F�b�N�{�b�N�X�ύX��
	 */
	protected void afterWorkflowChanged() {
		boolean isWorkflow = editView.cboWorkFlowApprove.getSelectedItemValue() == ApproveType.WORKFLOW;
		if (isWorkflow) {
			// ���[�N�t���[���F���p���͌��� / �o�����F�����p�K�{
			editView.chkFieldApprove.setSelected(true);
			editView.chkAccountantApprove.setSelected(true);
		} else {
			// ���[�N�t���[���F�񗘗p���͑I�������Z�b�g
			editView.cboDenyAction.setSelectedItemValue(DenyAction.BACK_ONE);
		}
		switchApproveCheckEnable();
	}

	/**
	 * ���F�n�`�F�b�N�{�b�N�X�ҏW�ۂ�ؑ�
	 */
	protected void switchApproveCheckEnable() {
		boolean isWorkflow = editView.cboWorkFlowApprove.getSelectedItemValue() == ApproveType.WORKFLOW;
		editView.chkFieldApprove.setEnabled(!isWorkflow);
		editView.chkAccountantApprove.setEnabled(!isWorkflow);
		editView.cboDenyAction.setEnabled(isWorkflow);
	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode ���샂�[�h�B
	 * @param bean ��ЁB�C���A���ʂ̏ꍇ�͓��Y��Џ���ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(@SuppressWarnings("hiding") Mode mode, Company bean) {

		this.mode = mode;

		editView.ctrlDetailItemName.setEditable(false);
		editView.ctrlManagement1Name.setEditable(false);
		editView.ctrlManagement2Name.setEditable(false);
		editView.ctrlManagement3Name.setEditable(false);
		editView.ctrlManagement4Name.setEditable(false);
		editView.ctrlManagement5Name.setEditable(false);
		editView.ctrlManagement6Name.setEditable(false);
		editView.ctrlNotAccount1Name.setEditable(false);
		editView.ctrlNotAccount2Name.setEditable(false);
		editView.ctrlNotAccount3Name.setEditable(false);
		// �V�K�̏ꍇ
		if (Mode.NEW == mode) {

			editView.setTitle(getWord("C01698"));// �V�K���

			editView.dtBeginDate.setValue(editView.dtBeginDate.getCalendar().getMinimumDate());
			editView.dtEndDate.setValue(editView.dtEndDate.getCalendar().getMaximumDate());
			switchApproveCheckEnable();
			return;

			// �ҏW�A���ʂ̏ꍇ
		} else if (Mode.MODIFY == mode || Mode.COPY == mode) {

			// �ҏW
			if (Mode.MODIFY == mode) {
				editView.setTitle(getWord("C00977"));// �ҏW���
				editView.ctrlCode.setEditable(false);
				editView.setEditMode();
				// ����
			} else {
				editView.setTitle(getWord("C01738"));// ���ʉ��
			}

			FiscalPeriod fp = bean.getFiscalPeriod();

			// ��Џ��
			editView.ctrlCode.setValue(bean.getCode());
			editView.ctrlName.setValue(bean.getName());
			editView.ctrlNames.setValue(bean.getNames());
			editView.ctrlAddress1.setValue(bean.getAddress1());
			editView.ctrlAddress2.setValue(bean.getAddress2());
			editView.ctrlKana.setValue(bean.getAddressKana());
			editView.ctrlZipCode.setValue(bean.getZip());
			editView.ctrlPhoneNo.setValue(bean.getPhoneNo());
			editView.ctrlFaxNo.setValue(bean.getFaxNo());
			editView.ctrlInvRegNo.setValue(bean.getInvRegNo());
			editView.dtBeginDate.setValue(bean.getDateFrom());
			editView.dtEndDate.setValue(bean.getDateTo());

			// �ȖڂƊǗ�
			AccountConfig ac = bean.getAccountConfig();
			editView.ctrlItemName.setValue(ac.getItemName());
			editView.ctrlSubItemName.setValue(ac.getSubItemName());
			editView.chkDetailItem.setSelected(ac.isUseDetailItem());
			editView.ctrlDetailItemName.setValue(ac.getDetailItemName());
			editView.chkManagement1.setSelected(ac.isUseManagement1());
			editView.chkManagement2.setSelected(ac.isUseManagement2());
			editView.chkManagement3.setSelected(ac.isUseManagement3());
			editView.chkManagement4.setSelected(ac.isUseManagement4());
			editView.chkManagement5.setSelected(ac.isUseManagement5());
			editView.chkManagement6.setSelected(ac.isUseManagement6());
			editView.ctrlManagement1Name.setValue(ac.getManagement1Name());
			editView.ctrlManagement2Name.setValue(ac.getManagement2Name());
			editView.ctrlManagement3Name.setValue(ac.getManagement3Name());
			editView.ctrlManagement4Name.setValue(ac.getManagement4Name());
			editView.ctrlManagement5Name.setValue(ac.getManagement5Name());
			editView.ctrlManagement6Name.setValue(ac.getManagement6Name());
			editView.cboNotAccount1.setSelectedItemValue(ac.getNonAccounting1());
			editView.ctrlNotAccount1Name.setValue(ac.getNonAccounting1Name());
			editView.cboNotAccount2.setSelectedItemValue(ac.getNonAccounting2());
			editView.ctrlNotAccount2Name.setValue(ac.getNonAccounting2Name());
			editView.cboNotAccount3.setSelectedItemValue(ac.getNonAccounting3());
			editView.ctrlNotAccount3Name.setValue(ac.getNonAccounting3Name());

			// �`�[
			editView.ctrlSlipNoDigit.setNumber(ac.getSlipNoDigit());
			editView.cboSlipNoAdopt1.setSelectedItemValue(ac.getSlipNoAdopt1());
			editView.cboSlipNoAdopt2.setSelectedItemValue(ac.getSlipNoAdopt2());
			editView.cboSlipNoAdopt3.setSelectedItemValue(ac.getSlipNoAdopt3());
			editView.cboExchangeFraction.setSelectedItemValue(ac.getExchangeFraction());
			editView.cboTemporaryGetTaxFraction.setSelectedItemValue(ac.getReceivingFunction());
			editView.cboTemporaryPaymentTaxFraction.setSelectedItemValue(ac.getPaymentFunction());
			editView.chkSlipPrint.setSelected(ac.isSlipPrint());
			editView.chkSlipPrintInitial.setSelected(ac.getSlipPrintDefault());
			editView.cboConvertType.setSelectedItemValue(ac.getConvertType());

			// ���Z
			if (fp.getMaxSettlementStage() > 0) {
				editView.cboSettlement.setSelectedIndex(0);
				editView.ctrlSettlementStage.setValue(fp.getMaxSettlementStage());
			} else {
				editView.cboSettlement.setSelectedIndex(1);
			}

			editView.cboSettlementPer.setSelectedItemValue(fp.getSettlementTerm());

			editView.cboForeignCurrencyExchangeRate.setSelectedItemValue(ac.getEvaluationRateDate());

			if (ac.isCarryJournalOfMidtermClosingForward()) {
				editView.cboCarryJournalOfMidtermClosingForward.setSelectedIndex(0);
			} else {
				editView.cboCarryJournalOfMidtermClosingForward.setSelectedIndex(1);
			}

			// ���̑�
			editView.ctrlInitialMonth.setNumber(fp.getMonthBeginningOfPeriod());
			editView.cboWorkFlowApprove.setSelectedItemValue(ac.isUseWorkflowApprove() ? ApproveType.WORKFLOW
				: ApproveType.REGULAR);
			editView.cboDenyAction.setSelectedItemValue(ac.isBackFirstWhenWorkflowDeny() ? DenyAction.BACK_FIRST
				: DenyAction.BACK_ONE);
			editView.chkFieldApprove.setSelected(ac.isUseFieldApprove());
			editView.chkAccountantApprove.setSelected(ac.isUseApprove());
			editView.ctrlKeyCurrency.setEntity(ac.getKeyCurrency());
			editView.ctrlFunctionCurrency.setEntity(ac.getFunctionalCurrency());
			editView.chkGrpKbn.setSelected(ac.isUseGroupAccount());
			editView.chkIfrsKbn.setSelected(ac.isUseIfrs());
			editView.chkHasDateKbn.setSelected(ac.isUseHasDateCheck());
			editView.ctrlEnSignerText.setText(bean.getAR_SIGN_NAME());

			if (isInvoice) {
				editView.ctrlInvoiceChk.setSelected(bean.isCMP_INV_CHK_FLG());
			} else {
				editView.ctrlInvoiceChk.setVisible(false);
			}

			// ��
			editView.ctrlCompanyColor.setColor(bean.getColor());

			// INVOICE�g�p(��Њ�b���p���)--�ǉ�
			editView.ctrlEngName.setValue(bean.getKAI_NAME_ENG());
			editView.ctrlEngNames.setValue(bean.getKAI_NAME_S_ENG());
			editView.ctrlEngAddress1.setValue(bean.getJYU_1_ENG());
			editView.ctrlEngAddress2.setValue(bean.getJYU_2_ENG());

			// SPC��Џ��--�ǉ�
			editView.ctrlConnCompanyCode.setValue(bean.getConnCompanyCode());
			editView.ctrlSignerPosition.setValue(bean.getSignerPosition());
			editView.ctrlRemitterName.setValue(bean.getRemitterName());
			editView.ctrlConnPhoneNo.setValue(bean.getConnPhoneNo());
			editView.ctrlDebitNoteAddress1.setValue(bean.getDebitNoteAddress1());
			editView.ctrlDebitNoteAddress2.setValue(bean.getDebitNoteAddress2());
			editView.ctrlDebitNoteAddress3.setValue(bean.getDebitNoteAddress3());
			editView.ctrlDebitNoteInfo.setText(bean.getDebitNoteInfo());

			// ���F�`�F�b�N�{�b�N�X�ύX �r���ŋ@�\�ǉ���z�肵switch�ł͂Ȃ�after����
			afterWorkflowChanged();

		}

	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {

			// ���̓`�F�b�N���s���B
			if (!isInputRight()) {
				return;
			}

			// ���͂��ꂽ��Џ����擾
			Company company = getInputedCompany();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", company);

				// �ҏW��ʂ����
				btnClose_Click();

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(company));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", company);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(company));

				// �ҏW��ʂ����
				btnClose_Click();

			}

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
	 * �w����ʂœ��͂��ꂽ��Ђ̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected CompanySearchCondition getSearchCondition() {

		CompanySearchCondition condition = createCompanySearchCondition();
		condition.setCodeFrom(mainView.ctrlCompanyRange.getCodeFrom());
		condition.setCodeTo(mainView.ctrlCompanyRange.getCodeTo());
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}
		condition.setNotShowSpc(isNotShowSpc); // SPC��\���t���O����
		condition.setShowInvoice(isShowCompanyEng);// INVOICE(��Њ�b���p���)
		condition.setShowARSignerEng(isShowARSignerEng); // AR�F�p��������SIGNER�\����
		condition.setInvoiceFlg(isInvoice); // �C���{�C�X�g�p

		return condition;

	}

	/**
	 * ���f���C���^�[�t�F�[�X��Ԃ�
	 * 
	 * @return ���f���C���^�[�t�F�[�X
	 */
	protected Class getModelClass() {
		return CompanyManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ��Ђ�Ԃ�
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ���
	 */
	protected Company getInputedCompany() {

		// ��Џ��
		Company company = createCompany();
		company.setCode(editView.ctrlCode.getValue());
		company.setName(editView.ctrlName.getValue());
		company.setNames(editView.ctrlNames.getValue());
		company.setAddress1(editView.ctrlAddress1.getValue());
		company.setAddress2(editView.ctrlAddress2.getValue());
		company.setAddressKana(editView.ctrlKana.getValue());
		company.setZip(editView.ctrlZipCode.getValue());
		company.setPhoneNo(editView.ctrlPhoneNo.getValue());
		company.setFaxNo(editView.ctrlFaxNo.getValue());
		company.setInvRegNo(editView.ctrlInvRegNo.getValue());
		company.setDateFrom(editView.dtBeginDate.getValue());
		company.setDateTo(editView.dtEndDate.getValue());

		// �ȖڂƊǗ�
		AccountConfig ac = createAccountConfig();
		ac.setItemName(editView.ctrlItemName.getValue());
		ac.setSubItemName(editView.ctrlSubItemName.getValue());
		ac.setUseDetailItem(editView.chkDetailItem.isSelected());
		ac.setDetailItemName(editView.ctrlDetailItemName.getText());
		ac.setUseManagement1(editView.chkManagement1.isSelected());
		ac.setUseManagement2(editView.chkManagement2.isSelected());
		ac.setUseManagement3(editView.chkManagement3.isSelected());
		ac.setUseManagement4(editView.chkManagement4.isSelected());
		ac.setUseManagement5(editView.chkManagement5.isSelected());
		ac.setUseManagement6(editView.chkManagement6.isSelected());
		ac.setManagement1Name(editView.ctrlManagement1Name.getText());
		ac.setManagement2Name(editView.ctrlManagement2Name.getText());
		ac.setManagement3Name(editView.ctrlManagement3Name.getText());
		ac.setManagement4Name(editView.ctrlManagement4Name.getText());
		ac.setManagement5Name(editView.ctrlManagement5Name.getText());
		ac.setManagement6Name(editView.ctrlManagement6Name.getText());
		ac.setNonAccounting1(((NonAccountingDivision) editView.cboNotAccount1.getSelectedItemValue()).getValue());
		ac.setNonAccounting1Name(editView.ctrlNotAccount1Name.getText());
		ac.setNonAccounting2(((NonAccountingDivision) editView.cboNotAccount2.getSelectedItemValue()).getValue());
		ac.setNonAccounting2Name(editView.ctrlNotAccount2Name.getText());
		ac.setNonAccounting3(((NonAccountingDivision) editView.cboNotAccount3.getSelectedItemValue()).getValue());
		ac.setNonAccounting3Name(editView.ctrlNotAccount3Name.getText());

		// �`�[
		ac.setSlipNoDigit(editView.ctrlSlipNoDigit.getIntValue());
		ac.setSlipNoAdopt1((SlipNoAdopt) editView.cboSlipNoAdopt1.getSelectedItemValue());
		ac.setSlipNoAdopt2((SlipNoAdopt) editView.cboSlipNoAdopt2.getSelectedItemValue());
		ac.setSlipNoAdopt3((SlipNoAdopt) editView.cboSlipNoAdopt3.getSelectedItemValue());
		ac.setExchangeFraction((ExchangeFraction) editView.cboExchangeFraction.getSelectedItemValue());
		ac.setReceivingFunction((ExchangeFraction) editView.cboTemporaryGetTaxFraction.getSelectedItemValue());
		ac.setPaymentFunction((ExchangeFraction) editView.cboTemporaryPaymentTaxFraction.getSelectedItemValue());
		ac.setSlipPrint(editView.chkSlipPrint.isSelected());
		ac.setSlipPrintDefault(editView.chkSlipPrintInitial.isSelected());
		ac.setConvertType((ConvertType) editView.cboConvertType.getSelectedItemValue());

		// ���Z
		FiscalPeriod fp = createFiscalPeriod();
		if (editView.cboSettlement.getComboBox().getSelectedIndex() == 0) {
			fp.setMaxSettlementStage(editView.ctrlSettlementStage.getInt());
		} else if (editView.cboSettlement.getComboBox().getSelectedIndex() == 1) {
			fp.setMaxSettlementStage(0);
		}
		fp.setSettlementTerm((SettlementTerm) editView.cboSettlementPer.getSelectedItemValue());

		ac.setEvaluationRateDate((EvaluationRateDate) editView.cboForeignCurrencyExchangeRate.getSelectedItemValue());

		ac.setCarryJournalOfMidtermClosingForward(editView.cboCarryJournalOfMidtermClosingForward.getSelectedIndex() == 0);

		// ���̑�
		fp.setMonthBeginningOfPeriod(editView.ctrlInitialMonth.getIntValue());
		ac.setUseWorkflowApprove(editView.cboWorkFlowApprove.getSelectedItemValue() == ApproveType.WORKFLOW);
		ac.setBackFirstWhenWorkflowDeny(editView.cboDenyAction.getSelectedItemValue() == DenyAction.BACK_FIRST);
		ac.setUseFieldApprove(editView.chkFieldApprove.isSelected());
		ac.setUseApprove(editView.chkAccountantApprove.isSelected());
		ac.setKeyCurrency(editView.ctrlKeyCurrency.getEntity());
		ac.setFunctionalCurrency(editView.ctrlFunctionCurrency.getEntity());
		ac.setUseGroupAccount(editView.chkGrpKbn.isSelected());
		ac.setUseIfrs(editView.chkIfrsKbn.isSelected());
		ac.setUseHasDateCheck(editView.chkHasDateKbn.isSelected());
		company.setCMP_INV_CHK_FLG(editView.ctrlInvoiceChk.isSelected());
		company.setAR_SIGN_NAME(editView.ctrlEnSignerText.getText());

		company.setAccountConfig(ac);
		company.setFiscalPeriod(fp);

		// ��
		company.setColor(editView.ctrlCompanyColor.getColor());

		// INVOICE�g�p(��Њ�b���p���)
		company.setKAI_NAME_ENG(editView.ctrlEngName.getValue());
		company.setKAI_NAME_S_ENG(editView.ctrlEngNames.getValue());
		company.setJYU_1_ENG(editView.ctrlEngAddress1.getValue());
		company.setJYU_2_ENG(editView.ctrlEngAddress2.getValue());

		// SPC��Џ��
		company.setConnCompanyCode(editView.ctrlConnCompanyCode.getValue());
		company.setSignerPosition(editView.ctrlSignerPosition.getValue());
		company.setRemitterName(editView.ctrlRemitterName.getValue());
		company.setConnPhoneNo(editView.ctrlConnPhoneNo.getValue());
		company.setDebitNoteAddress1(editView.ctrlDebitNoteAddress1.getValue());
		company.setDebitNoteAddress2(editView.ctrlDebitNoteAddress2.getValue());
		company.setDebitNoteAddress3(editView.ctrlDebitNoteAddress3.getValue());
		company.setDebitNoteInfo(editView.ctrlDebitNoteInfo.getText());

		return company;

	}

	/**
	 * @return ��Ђ̌�������
	 */
	protected CompanySearchCondition createCompanySearchCondition() {
		return new CompanySearchCondition();
	}

	/**
	 * @return ��v����
	 */
	protected FiscalPeriod createFiscalPeriod() {
		return new FiscalPeriod();
	}

	/**
	 * @return ��v�̐ݒ���
	 */
	protected AccountConfig createAccountConfig() {
		return new AccountConfig();
	}

	/**
	 * @return ���
	 */
	protected Company createCompany() {
		return new Company();
	}

	/**
	 * ��Џ����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param company ���
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ���
	 */
	protected String[] getRowData(Company company) {
		return new String[] { company.getCode(), company.getName(), company.getNames(),
				DateUtil.toYMDString(company.getDateFrom()), DateUtil.toYMDString(company.getDateTo()) };
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
	}

	/**
	 * ���������ɊY�������Ђ̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�������Ђ̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<Company> getCompany(CompanySearchCondition condition) throws Exception {

		List<Company> list = (List<Company>) request(getModelClass(), "get", condition);

		return list;

	}

	/**
	 * �ꗗ�őI������Ă����Ђ�Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă�����
	 * @throws Exception
	 */
	protected Company getSelectedCompany() throws Exception {

		CompanySearchCondition condition = createCompanySearchCondition();
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0010CompanyMasterPanel.SC.code));
		condition.setShowInvoice(isShowCompanyEng);// INVOICE(��Њ�b���p���)
		List<Company> list = getCompany(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂���
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * �w��̉�Ђ��폜����
	 * 
	 * @param company ���
	 * @throws Exception
	 */
	protected void deleteCompany(Company company) throws Exception {
		request(getModelClass(), "delete", company);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {
		int tabIndex = 0;
		// -- ��Џ��^�u --
		// ��ЃR�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "C00596");// ��ЃR�[�h
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓����Ђ����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			CompanySearchCondition condition = createCompanySearchCondition();
			condition.setCode(editView.ctrlCode.getValue());
			condition.setShowInvoice(isShowCompanyEng);// INVOICE(��Њ�b���p���)

			List<Company> list = getCompany(condition);

			if (list != null && !list.isEmpty()) {
				// �w��̉�Ђ͊��ɑ��݂��܂�
				showMessage(editView, "I00148", "C00053");
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}

		// ��Ж��̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlName.getValue())) {
			showMessage(editView, "I00037", "C00685");// ��Ж���
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlName.requestTextFocus();
			return false;
		}

		// ��З��̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlNames.getValue())) {
			showMessage(editView, "I00037", "C00686");// ��З���
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlNames.requestTextFocus();
			return false;
		}

		// �J�n�N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtBeginDate.getValue())) {
			showMessage(editView, "I00037", "C00055");// �J�n�N����
			editView.tab.setSelectedIndex(tabIndex);
			editView.dtBeginDate.requestTextFocus();
			return false;
		}

		// �I���N�����������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.dtEndDate.getValue())) {
			showMessage(editView, "I00037", "C00261");// �I���N����
			editView.tab.setSelectedIndex(tabIndex);
			editView.dtEndDate.requestTextFocus();
			return false;
		}

		// �J�n�N����,�I���N�����`�F�b�N
		if (!Util.isSmallerThenByYMD(editView.dtBeginDate.getValue(), editView.dtEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.dtBeginDate.requestFocus();
			return false;
		}

		// -- �ȖڂƊǗ��^�u --
		tabIndex++;
		// �Ȗږ���
		if (Util.isNullOrEmpty(editView.ctrlItemName.getValue())) {
			showMessage(editView, "I00037", "C00700");// �Ȗږ���
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlItemName.requestFocus();
			return false;
		}

		// �⏕�Ȗږ���
		if (Util.isNullOrEmpty(editView.ctrlSubItemName.getValue())) {
			showMessage(editView, "I00037", "C00701");// �⏕�Ȗږ���
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlSubItemName.requestFocus();
			return false;
		}

		// ����Ȗږ���
		if (editView.ctrlDetailItemName.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlDetailItemName.getValue())) {
				showMessage(editView, "I00037", "C00702");// ����Ȗږ���
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlDetailItemName.requestFocus();
				return false;
			}
		}

		// �Ǘ��敪1����
		if (editView.ctrlManagement1Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlManagement1Name.getValue())) {
				editView.tab.setSelectedIndex(1);
				showMessage(editView, "I00037", "C11204");// �Ǘ��敪1�̖���
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlManagement1Name.requestFocus();
				return false;
			}
		}

		// �Ǘ��敪2����
		if (editView.ctrlManagement2Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlManagement2Name.getValue())) {
				showMessage(editView, "I00037", "C11205");// �Ǘ��敪2�̖���
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlManagement2Name.requestFocus();
				return false;
			}
		}

		// �Ǘ��敪3����
		if (editView.ctrlManagement3Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlManagement3Name.getValue())) {
				showMessage(editView, "I00037", "C11206");// �Ǘ��敪3�̖���
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlManagement3Name.requestFocus();
				return false;
			}
		}

		// �Ǘ��敪4����
		if (editView.ctrlManagement4Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlManagement4Name.getValue())) {
				showMessage(editView, "I00037", "C11207");// �Ǘ��敪4�̖���
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlManagement4Name.requestFocus();
				return false;
			}
		}

		// �Ǘ��敪5����
		if (editView.ctrlManagement5Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlManagement5Name.getValue())) {
				showMessage(editView, "I00037", "C11208");// �Ǘ��敪5�̖���
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlManagement5Name.requestFocus();
				return false;
			}
		}

		// �Ǘ��敪6����
		if (editView.ctrlManagement6Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlManagement6Name.getValue())) {
				showMessage(editView, "I00037", "C11209");// �Ǘ��敪6�̖���
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlManagement6Name.requestFocus();
				return false;
			}
		}

		// ���v���׋敪����1
		if (editView.ctrlNotAccount1Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlNotAccount1Name.getValue())) {
				showMessage(editView, "I00037", "C11210");// ���v���׋敪1�̖���
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlNotAccount1Name.requestFocus();
				return false;
			}
		}

		// ���v���׋敪����2
		if (editView.ctrlNotAccount2Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlNotAccount2Name.getValue())) {
				showMessage(editView, "I00037", "C11211");// ���v���׋敪2�̖���
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlNotAccount2Name.requestFocus();
				return false;
			}
		}

		// ���v���׋敪����3
		if (editView.ctrlNotAccount3Name.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlNotAccount3Name.getValue())) {
				showMessage(editView, "I00037", "C11212");// ���v���׋敪3�̖���
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlNotAccount3Name.requestFocus();
				return false;
			}
		}

		// -- �`�[�^�u --
		tabIndex++;
		// �����̔ԕ�
		if (Util.isNullOrEmpty(editView.ctrlSlipNoDigit.getValue())) {
			showMessage(editView, "I00037", "C00224");// �����̔ԕ�����
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlSlipNoDigit.requestFocus();
			return false;
		}

		// -- ���Z�^�u --
		tabIndex++;
		// ���Z�i�K��
		if (editView.ctrlSettlementStage.isEditable()) {
			if (Util.isNullOrEmpty(editView.ctrlSettlementStage.getValue())) {
				showMessage(editView, "I00037", "C11189");// ���Z�i�K��
				editView.tab.setSelectedIndex(tabIndex);
				editView.ctrlSettlementStage.requestFocus();
				return false;
			}
		}

		if (editView.ctrlSettlementStage.isEditable() && editView.ctrlSettlementStage.getInt() == 0) {
			// ���Z�i�K����1�`9�Ɏw�肵�Ă��������B
			showMessage(editView, "I00226");
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlSettlementStage.requestFocus();
			return false;
		}

		// �O���[�v��v���g�p����ꍇ�A���Z�i�K�����킹��
		if (editView.chkGrpKbn.isSelected()) {
			int settlementStage = 0;
			if (editView.ctrlSettlementStage.isEditable()) {
				settlementStage = editView.ctrlSettlementStage.getInt();
			}
			List<Company> list = getDifferentSettlementStageCompany(settlementStage);
			if (list != null && !list.isEmpty()) {
				// �O���[�v��v���g�p����ꍇ�A�O���[�v��Ђƌ��Z�i�K�����킹�ĉ�����
				showMessage(editView, "I00227");
				editView.tab.setSelectedIndex(tabIndex);
				editView.chkGrpKbn.requestFocus();
				return false;
			}
		}

		// -- ���̑��^�u --
		tabIndex++;
		// ��v���� ����
		if (Util.isNullOrEmpty(editView.ctrlInitialMonth.getValue())) {
			showMessage(editView, "I00037", "C00105");// ����
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlInitialMonth.requestFocus();
			return false;
		}

		if (Util.avoidNullAsInt(editView.ctrlInitialMonth.getValue()) > 12
			|| Util.avoidNullAsInt(editView.ctrlInitialMonth.getValue()) == 0) {
			// ���񌎂�1�`12�Ɏw�肵�Ă�������
			showMessage(editView, "I00228");
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlInitialMonth.requestFocus();
			return false;
		}

		// ��ʉ�
		if (editView.ctrlKeyCurrency.isEmpty()) {
			showMessage(editView, "I00037", "C00907");// ��ʉ�
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlKeyCurrency.requestTextFocus();
			return false;
		}

		// �@�\�ʉ�
		if (editView.ctrlFunctionCurrency.isEmpty()) {
			showMessage(editView, "I00037", "C11084");// �@�\�ʉ�
			editView.tab.setSelectedIndex(tabIndex);
			editView.ctrlFunctionCurrency.requestTextFocus();
			return false;
		}

		// -- ���^�u --
		// �Ȃ�
		return true;

	}

	/**
	 * �w�茈�Z�i�K�ȊO�̃O���[�v��Ђ��g�p�����Ђ�Ԃ�
	 * 
	 * @param settlementStage ���Z�i�K
	 * @return List
	 * @throws Exception
	 */
	protected List<Company> getDifferentSettlementStageCompany(int settlementStage) throws Exception {

		CompanySearchCondition condition = createCompanySearchCondition();
		condition.setGroupAccountDivision(1);
		condition.setSettlementStageOtherThan(settlementStage);
		condition.setShowInvoice(isShowCompanyEng);// INVOICE(��Њ�b���p���)

		List<Company> list = getCompany(condition);

		return list;

	}
}
