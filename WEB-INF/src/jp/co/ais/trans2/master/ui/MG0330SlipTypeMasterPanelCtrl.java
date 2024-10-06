package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �`�[��ʃ}�X�^ �R���g���[���[
 */
public class MG0330SlipTypeMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0330SlipTypeMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0330SlipTypeMasterDialog editView = null;

	/** �C���{�C�X���x�`�F�b�N���邩�ǂ��� true:�\������ */
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

	/**
	 * invoice�g�p���邩�ǂ���
	 */
	protected void initInvoiceFlg() {

		isInvoice = getCompany().isCMP_INV_CHK_FLG();
	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0330SlipTypeMasterPanel();
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
			SlipTypeSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				// �x�����b�Z�[�W�\��
				showMessage(mainView, "I00068");
				mainView.TSlipTypeReferenceRange.ctrlSlipTypeReferenceFrom.code.requestFocus();
				return;
			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// �`�[��ʏ����擾
			List<SlipType> list = getSlipType(condition);

			// ���������ɊY������`�[��ʂ����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// �`�[��ʏ����ꗗ�ɕ\������
			for (SlipType sliptype : list) {
				mainView.tbl.addRow(getRowData(sliptype));
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

			// �ҏW�Ώۂ̓`�[��ʂ��擾����
			SlipType sliptype = getSelectedSlipType();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ̓`�[��ʏ����Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, sliptype);

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

			// ���ʑΏۂ̓`�[��ʂ��擾����
			SlipType sliptype = getSelectedSlipType();

			// ���ʉ�ʂ𐶐����A���ʑΏۂ̓`�[��ʏ����Z�b�g����
			createEditView();
			initEditView(Mode.COPY, sliptype);

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
			if (!showConfirmMessage(mainView, FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̓`�[��ʂ��擾����
			SlipType sliptype = getSelectedSlipType();

			// �폜����
			deleteSlipType(sliptype);

			// �폜�����`�[��ʂ��ꗗ����폜
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

			SlipTypeSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			// �`�[��ʃ}�X�^
			printer.preview(data, getWord("C00912") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new MG0330SlipTypeMasterDialog(mainView.getParentFrame(), true);

		// �R���{�{�b�N�X�̒��g
		initDataDivComboBox(editView.cboDataDiv.getComboBox());
		// ���Ȃ�
		editView.cboDenNoFlg.getComboBox().addTextValueItem(false, getWord("C02099"));
		// "����"
		editView.cboDenNoFlg.getComboBox().addTextValueItem(true, getWord("C02100"));
		// ���V�X����ΏۊO
		editView.cboAnoSysKbn.getComboBox().addTextValueItem(false, getWord("C02097"));
		// ���V�X����Ώ�
		editView.cboAnoSysKbn.getComboBox().addTextValueItem(true, getWord("C02098"));
		initAcceptUnitComboBox(editView.cboAcceptUnit.getComboBox());
		initTaxCulDivComboBox(editView.cboTaxCulDiv.getComboBox());
		initJnlIfComboBox(editView.cboJnlIfDiv.getComboBox());

		// ���Ȃ�
		editView.cboInvoiceCheck.getComboBox().addTextValueItem(false, getWord("C02099"));
		// ����
		editView.cboInvoiceCheck.getComboBox().addTextValueItem(true, getWord("C02100"));

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();

	}

	/**
	 * �f�[�^�敪�R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox
	 */
	protected void initDataDivComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(DataDivision.INPUT, getWord(DataDivision.getDataDivisionName(DataDivision.INPUT)));
		comboBox.addTextValueItem(DataDivision.OUTPUT, getWord(DataDivision.getDataDivisionName(DataDivision.OUTPUT)));
		comboBox.addTextValueItem(DataDivision.TRANSFER,
			getWord(DataDivision.getDataDivisionName(DataDivision.TRANSFER)));
		comboBox.addTextValueItem(DataDivision.ESTIMATE,
			getWord(DataDivision.getDataDivisionName(DataDivision.ESTIMATE)));
		comboBox.addTextValueItem(DataDivision.ESTIMATE_DEL,
			getWord(DataDivision.getDataDivisionName(DataDivision.ESTIMATE_DEL)));
		comboBox.addTextValueItem(DataDivision.LEDGER_IFRS,
			getWord(DataDivision.getDataDivisionName(DataDivision.LEDGER_IFRS)));
		comboBox.addTextValueItem(DataDivision.LEDGER_SELF,
			getWord(DataDivision.getDataDivisionName(DataDivision.LEDGER_SELF)));
		comboBox.addTextValueItem(DataDivision.EMP_KARI,
			getWord(DataDivision.getDataDivisionName(DataDivision.EMP_KARI)));
		comboBox
			.addTextValueItem(DataDivision.EXPENCE, getWord(DataDivision.getDataDivisionName(DataDivision.EXPENCE)));
		comboBox
			.addTextValueItem(DataDivision.ACCOUNT, getWord(DataDivision.getDataDivisionName(DataDivision.ACCOUNT)));
		comboBox.addTextValueItem(DataDivision.PAYMENT_EMP,
			getWord(DataDivision.getDataDivisionName(DataDivision.PAYMENT_EMP)));
		comboBox.addTextValueItem(DataDivision.ABROAD, getWord(DataDivision.getDataDivisionName(DataDivision.ABROAD)));
		comboBox.addTextValueItem(DataDivision.PAYMENT_COM,
			getWord(DataDivision.getDataDivisionName(DataDivision.PAYMENT_COM)));
		comboBox.addTextValueItem(DataDivision.CREDIT_SLIP,
			getWord(DataDivision.getDataDivisionName(DataDivision.CREDIT_SLIP)));
		comboBox.addTextValueItem(DataDivision.CREDIT_DEL,
			getWord(DataDivision.getDataDivisionName(DataDivision.CREDIT_DEL)));
	}

	/**
	 * ����P�ʃR���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox
	 */
	protected void initAcceptUnitComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(AcceptUnit.SLIPTYPE, getWord(AcceptUnit.getAcceptUnitName(AcceptUnit.SLIPTYPE)));
		comboBox.addTextValueItem(AcceptUnit.SLIPNO, getWord(AcceptUnit.getAcceptUnitName(AcceptUnit.SLIPNO)));
	}

	/**
	 * ����Ōv�Z�敪�R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox
	 */
	protected void initTaxCulDivComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(TaxCalcType.OUT, getWord(TaxCalcType.getTaxCalcTypeName(TaxCalcType.OUT)));
		comboBox.addTextValueItem(TaxCalcType.IN, getWord(TaxCalcType.getTaxCalcTypeName(TaxCalcType.IN)));
	}

	/**
	 * �d��C���^�[�t�F�[�X�敪�R���{�{�b�N�X�̏�����
	 * 
	 * @param comboBox
	 */
	protected void initJnlIfComboBox(TComboBox comboBox) {
		comboBox.addTextValueItem(SlipState.ENTRY, getWord(SlipState.getSlipStateName(SlipState.ENTRY)));
		comboBox.addTextValueItem(SlipState.APPROVE, getWord(SlipState.getSlipStateName(SlipState.APPROVE)));
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

	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param mode_ ���샂�[�h�B
	 * @param sliptype �B�C���A���ʂ̏ꍇ�͓��Y����ҏW��ʂɃZ�b�g����B
	 */
	protected void initEditView(Mode mode_, SlipType sliptype) {

		this.mode = mode_;

		// �V�K�̏ꍇ
		if (Mode.NEW == mode_) {
			// �V�K���
			editView.setTitle(getWord("C01698"));

			if (!isInvoice) {
				editView.cboInvoiceCheck.setVisible(false);
			}

			return;
		}

		// �ҏW
		if (Mode.MODIFY == mode_) {
			// �ҏW���
			editView.setTitle(getWord("C00977"));
			editView.ctrlSlipTypeCode.setEditable(false);
			editView.setEditMode();
		}
		// ����
		else {
			// ���ʉ��
			editView.setTitle(getWord("C01738"));
		}

		// dialog�̏����l
		editView.ctrlSlipTypeCode.setValue(sliptype.getCode());
		editView.ctrlSysKbn.setEntity(sliptype.getSystemDivision());
		editView.ctrlSlipTypeName.setValue(sliptype.getName());
		editView.ctrlSlipTypeNames.setValue(sliptype.getNames());
		editView.ctrlSlipTitle.setValue(sliptype.getNamek());
		if (DataDivision.getDataDivision(sliptype.getDataType()) != null) {
			editView.cboDataDiv.setSelectedIndex(DataDivision.getDataDivision(sliptype.getDataType()).value);
		}
		editView.cboAnoSysKbn.setSelectedItemValue(sliptype.isAnotherSystemDivision());
		editView.cboDenNoFlg.setSelectedItemValue(sliptype.isTakeNewSlipNo());
		editView.cboAcceptUnit.setSelectedItemValue(sliptype.isAcceptUnit());
		editView.cboTaxCulDiv
			.setSelectedIndex(TaxCalcType.getTaxCulKbn(sliptype.isInnerConsumptionTaxCalculation()).value);
		editView.cboJnlIfDiv.setSelectedItemValue(sliptype.getJounalIfDivision());
		editView.ctrlRevSlipTypeReference.setEntity(sliptype.getReversingSlipType());

		if (isInvoice) {
			editView.cboInvoiceCheck.setSelectedItemValue(sliptype.isINV_SYS_FLG() ? true : false);
		} else {
			editView.cboInvoiceCheck.setVisible(false);
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

			// ���͂��ꂽ�`�[��ʏ����擾
			SlipType sliptype = getInputedSlipType();

			// �V�K�o�^�̏ꍇ
			if (Mode.NEW == mode || Mode.COPY == mode) {

				// �V�K�o�^
				request(getModelClass(), "entry", sliptype);

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(sliptype));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

				// �C���̏ꍇ
			} else if (Mode.MODIFY == mode) {

				// �C��
				request(getModelClass(), "modify", sliptype);

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(sliptype));

			}

			// �ҏW��ʂ����
			btnClose_Click();

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
	 * �w����ʂœ��͂��ꂽ�`�[��ʂ̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected SlipTypeSearchCondition getSearchCondition() {

		SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCodeFrom(mainView.TSlipTypeReferenceRange.getCodeFrom());
		condition.setCodeTo(mainView.TSlipTypeReferenceRange.getCodeTo());
		condition.setInvoiceFlg(isInvoice);

		return condition;

	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return CustomerManager
	 */
	protected Class getModelClass() {
		return SlipTypeManager.class;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽ����Ԃ� ���`�[�ԍ��̔��׸ނ�SlipTypeManagerImpl�Ŏ擾
	 * 
	 * @return �ҏW��ʂœ��͂��ꂽ�`�[���
	 */
	protected SlipType getInputedSlipType() {

		SlipType sliptype = createSlipType();
		sliptype.setCompanyCode(getCompany().getCode());

		sliptype.setCode(editView.ctrlSlipTypeCode.getValue());
		sliptype.setSystemDiv(editView.ctrlSysKbn.getCode());
		sliptype.setName(editView.ctrlSlipTypeName.getValue());
		sliptype.setNames(editView.ctrlSlipTypeNames.getValue());
		sliptype.setNamek(editView.ctrlSlipTitle.getValue());
		sliptype
			.setDataType(DataDivision.getDataDivisionCode((DataDivision) editView.cboDataDiv.getSelectedItemValue()));
		sliptype.setAnotherSystemDivision(BooleanUtil.toBoolean(editView.cboAnoSysKbn.getSelectedIndex()));
		if (editView.cboTaxCulDiv.getSelectedIndex() == 0) {
			sliptype.setInnerConsumptionTaxCalculation(false);
		} else {
			sliptype.setInnerConsumptionTaxCalculation(true);
		}
		if (editView.cboDenNoFlg.getSelectedIndex() == 0) {
			sliptype.setTakeNewSlipNo(false);
		} else {
			sliptype.setTakeNewSlipNo(true);
		}
		sliptype.setAcceptUnit((AcceptUnit) editView.cboAcceptUnit.getSelectedItemValue());
		sliptype.setJounalIfDivision((SlipState) editView.cboJnlIfDiv.getSelectedItemValue());
		sliptype.setReversingSlipType(editView.ctrlRevSlipTypeReference.getEntity());

		if (isInvoice) {
			sliptype.setINV_SYS_FLG(BooleanUtil.toBoolean(editView.cboInvoiceCheck.getSelectedIndex()));
		}

		return sliptype;

	}

	/**
	 * �`�[��ʃt�@�N�g��
	 * 
	 * @return SlipType
	 */
	protected SlipType createSlipType() {
		return new SlipType();
	}

	/**
	 * �`�[��ʏ����ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param slipType �`�[���
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�`�[���
	 */
	protected List<String> getRowData(SlipType slipType) {

		List<String> list = new ArrayList<String>();

		String slipTypeCode = null;
		String slipTypeName = null;

		list.add(slipType.getCode());
		list.add(slipType.getSystemDiv());
		list.add(slipType.getName());
		list.add(slipType.getNames());
		list.add(slipType.getNamek());
		list.add(getWord(DataDivision.getDataDivisionName(DataDivision.getDataDivision(slipType.getDataType()))));
		list.add(getWord(slipType.getAnotherSystemDivisionName()));
		list.add(getWord(slipType.getSlipIndexDivisionName()));
		list.add(getWord(AcceptUnit.getAcceptUnitName(slipType.isAcceptUnit())));
		list.add(getWord(TaxCalcType.getTaxCalcTypeName(TaxCalcType.getTaxCulKbn(slipType
			.isInnerConsumptionTaxCalculation()))));
		list.add(getWord(SlipState.getSlipStateName(slipType.getJounalIfDivision())));

		if (slipType.getReversingSlipType() != null) {
			slipTypeCode = slipType.getReversingSlipType().getCode();
			slipTypeName = slipType.getReversingSlipType().getName();
		}
		list.add(slipTypeCode);
		list.add(slipTypeName);

		list.add(slipType.isINV_SYS_FLG() ? getWord("C02100") : getWord("C02099"));

		return list;
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
	 * �ꗗ�őI������Ă���`�[��ʂ�Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���`�[���
	 * @throws Exception
	 */
	protected SlipType getSelectedSlipType() throws Exception {

		SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode((String) mainView.tbl.getSelectedRowValueAt(MG0330SlipTypeMasterPanel.SC.denSyuCode));

		List<SlipType> list = getSlipType(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			throw new TException(getMessage("I00193"));
		}
		return list.get(0);
	}

	/**
	 * ���������ɊY������`�[��ʂ̃��X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY������`�[��ʂ̃��X�g
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	protected List<SlipType> getSlipType(SlipTypeSearchCondition condition) throws Exception {

		List<SlipType> list = (List<SlipType>) request(getModelClass(), "get", condition);

		return list;
	}

	/**
	 * �w��̓`�[��ʂ��폜����
	 * 
	 * @param sliptype
	 * @throws Exception
	 */
	protected void deleteSlipType(SlipType sliptype) throws Exception {
		request(getModelClass(), "delete", sliptype);
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputCorrect() throws Exception {

		// �`�[��ʃR�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlSlipTypeCode.getValue())) {
			// �`�[��ʃR�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C00837");
			editView.ctrlSlipTypeCode.requestTextFocus();
			return false;
		}

		// �V�X�e���敪�������͂̏ꍇ�G���[ if
		if (Util.isNullOrEmpty(editView.ctrlSysKbn.code.getValue())) {
			// "�V�X�e���敪�R�[�h����͂��Ă��������B
			showMessage(editView, "I00037", "C11223");
			editView.ctrlSysKbn.requestTextFocus();
			return false;
		}

		// �`�[��ʖ��̂������͂̏ꍇ�G���[ if
		if (Util.isNullOrEmpty(editView.ctrlSlipTypeName.getValue())) {
			// �`�[��ʖ��̂���͂��Ă��������B
			showMessage(editView, "I00037", "C00838");
			editView.ctrlSlipTypeName.requestTextFocus();
			return false;
		}

		// �`�[��ʗ��̂������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlSlipTypeNames.getValue())) {
			// �`�[��ʗ��̂���͂��Ă��������B"
			showMessage(editView, "I00037", "C00839");
			editView.ctrlSlipTypeNames.requestTextFocus();
			return false;
		}

		// ���[�^�C�g���������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(editView.ctrlSlipTitle.getValue())) {
			// ���[�^�C�g������͂��Ă��������B
			showMessage(editView, "I00037", "C02757");
			editView.ctrlSlipTitle.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓���`�[��ʂ����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			SlipTypeSearchCondition condition = new SlipTypeSearchCondition();
			condition.setCode(editView.ctrlSlipTypeCode.getValue());
			condition.setCompanyCode(getCompanyCode());
			List<SlipType> list = getSlipType(condition);
			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00261");
				editView.ctrlSlipTypeCode.requestTextFocus();
				return false;
			}
		}

		return true;
	}

}
