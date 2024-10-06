package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * MG0130ConsumptionTaxMaster - ����Ń}�X�^ - Main Controller
 * 
 * @author AIS
 */
public class MG0130ConsumptionTaxMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0130ConsumptionTaxMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0130ConsumptionTaxMasterDialog editView = null;

	/** ���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p���� */
	protected Mode mode = null;

	/** �ҏW�O�̔�K�i���������s���Ǝ҂̃t���O�ێ� */
	protected boolean isBeforeNoInvRegFlg = false;

	/** ��K�i���������s���Ǝ҂̐ݒ��\�����邩�ǂ��� true:�\������ */
	protected static boolean isInvoiceTaxProperty = ClientConfig.isFlagOn("trans.slip.use.invoice.tax");

	/** true: 2023INVOICE���x�Ή����g�p����(��Џ��܂�) */
	public static boolean isInvoice = false;

	/** ���샂�[�h */
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

			// �w����ʏ�����
			initMainView();

			// �w����ʕ\��
			mainView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʐ���, �C�x���g��`
	 */
	protected void createMainView() {
		mainView = new MG0130ConsumptionTaxMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʏ�����
	 */
	protected void initMainView() {
		setMainButtonEnabled(false);
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param bln Boolean
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * invoice�g�p���邩�ǂ���
	 */
	protected void initInvoiceFlg() {

		isInvoice = getCompany().isCMP_INV_CHK_FLG();
	}

	/**
	 * �w����ʂ̃C�x���g��`
	 */
	protected void addMainViewEvent() {

		// [�V�K]�{�^��
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [����]�{�^��
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [�ҏW]�{�^��
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [����]�{�^��
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [�폜]�{�^��
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [�G�N�Z��]�{�^��
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �w�����[�V�K]�{�^������
	 */
	protected void btnNew_Click() {
		try {

			// �ҏW��ʐ���
			createEditView();

			// �ҏW��ʏ�����
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

			// ���������擾
			ConsumptionTaxSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlTaxRefRan.requestFocus();
				return;
			}

			// �ꗗ���N���A
			mainView.tbList.removeRow();

			// ���C���{�^������
			setMainButtonEnabled(false);

			// �f�[�^�擾 //???List<String> dataTypeList
			List<ConsumptionTax> list = getList(condition);

			// ���������ɊY������f�[�^�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ���������f�[�^���ꗗ�ɕ\������
			for (ConsumptionTax cst : list) {
				mainView.tbList.addRow(getRowData(cst));
			}

			// ���C���{�^������
			setMainButtonEnabled(true);

			mainView.tbList.setRowSelectionInterval(0, 0);

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

			// �ҏW�Ώۂ̃f�[�^�擾
			ConsumptionTax bean = getSelected();

			if (bean == null) {
				return;
			}

			isBeforeNoInvRegFlg = bean.isNO_INV_REG_FLG();

			// �ҏW��ʐ���
			createEditView();

			// �ҏW��ʂɑI���f�[�^���Z�b�g
			initEditView(Mode.MODIFY, bean);

			// �ҏW��ʕ\��
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

			// ���ʑΏۂ̃f�[�^�擾
			ConsumptionTax bean = getSelected();

			if (bean == null) {
				return;
			}

			// ���ʉ�ʐ���
			createEditView();

			// �ҏW��ʂɑI���f�[�^���Z�b�g
			initEditView(Mode.COPY, bean);

			// ���ʉ�ʕ\��
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

			// �m�F���b�Z�[�W�\��
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �폜�Ώۂ̃f�[�^�擾
			ConsumptionTax bean = getSelected();

			if (bean == null) {
				return;
			}

			// �폜���s
			doDelete(bean);

			// �폜�����s���ꗗ����폜
			mainView.tbList.removeSelectedRow();

			// �폜������A�ꗗ�̃��R�[�h��0���̏ꍇ�A���C���{�^������
			if (mainView.tbList.getRowCount() == 0) {
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

		if (mainView.tbList.getSelectedRow() == -1) {
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

			// ���������擾
			ConsumptionTaxSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N
			if (Util.isSmallerThen(condition.getCodeFrom(), condition.getCodeTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlTaxRefRan.requestFocus();
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �G�N�Z���f�[�^�̎擾
			byte[] data = (byte[]) request(getModelClass(), "getExcel", condition);

			// �f�[�^�����������ꍇ�A�G���[���b�Z�[�W�o��
			if (Util.isNullOrEmpty(data)) {
				showMessage(mainView, "I00022");
				return;
			}

			// �G�N�Z���^�C�g���Z�b�g
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02324") + ".xls");
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʍ쐬
	 */
	protected void createEditView() {
		editView = new MG0130ConsumptionTaxMasterDialog(mainView.getParentFrame(), true);
		addEditViewEvent();
		addSubViewEvent();
	}

	/**
	 * �ҏW��ʂ̃C�x���g��`
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

		// ����d���敪�ύX����
		editView.ctrlcboUsKbn.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(ItemEvent e) {
				if (ItemEvent.SELECTED == e.getStateChange()) {
					changedUseKbn();
				}
			}
		});

		// ��K�i
		editView.ctrlNoInvReg.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				changedNoInvReg();
			}
		});

	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {
		try {

			// ���̓f�[�^�`�F�b�N
			if (!isInputCorrect()) {
				return;
			}

			// ���̓f�[�^�擾
			ConsumptionTax bean = getInputtedConsumptionTax();

			// �V�K�o�^�̏ꍇ
			switch (mode) {

				case NEW:
				case COPY:

					// �V�K�o�^ - ���ʓo�^
					request(getModelClass(), "entry", bean);

					// �ǉ������ꗗ�ɔ��f
					mainView.tbList.addRow(getRowData(bean));

					// ���C���{�^������
					setMainButtonEnabled(true);

					break;

				case MODIFY:

					boolean isTriMstUpdate = false;

					// �m�F���b�Z�[�W�\��
					if (isInvoice && chkTriMstNoInvRegTaxCode()) {

						if (!showConfirmMessage(FocusButton.NO, "I01108")) {
							return;
						}
						isTriMstUpdate = true;
					}

					// �C��
					request(getModelClass(), "modify", bean, isTriMstUpdate);

					// �C�������ꗗ�ɔ��f
					mainView.tbList.modifySelectedRow(getRowData(bean));

					break;
			}

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
	 * �ҏW��ʕ\���敪���W�I�{�^���������̃C�x���g
	 */
	protected void addSubViewEvent() {

		// [����Ōv�Z���g�p���Ȃ�]�{�^������
		editView.rdoDisUse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				if (editView.rdoDisUse.isSelected()) {
					btnDU_Click();
				}
			}

		});

		// [����Ōv�Z���g�p����]�{�^������
		editView.rdoUse.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnU_Click();
			}
		});

	}

	/**
	 * �ҏW��ʏ�����
	 * 
	 * @param editMode
	 * @param bean
	 */
	protected void initEditView(Mode editMode, ConsumptionTax bean) {

		this.mode = editMode;

		switch (editMode) {

			case NEW:
				editView.setTitle(getWord("C01698"));
				editView.ctrlStrDate.setValue(editView.ctrlStrDate.getCalendar().getMinimumDate());
				editView.ctrlEndDate.setValue(editView.ctrlEndDate.getCalendar().getMaximumDate());
				btnDU_Click();

				if (!isInvoice) {
					editView.ctrlNoInvReg.setVisible(false);
					editView.ctrlTransitMeasure.setVisible(false);
					editView.lblTransitMeasure.setVisible(false);
				}
				changedNoInvReg();
				changedUseKbn();
				break;

			case MODIFY:
			case COPY:

				if (Mode.MODIFY == editMode) {

					editView.setTitle(getWord("C00977"));
					editView.ctrlTaxCode.setEditable(false);
					editView.setEditMode();

				} else {

					editView.setTitle(getWord("C01738"));

				}

				editView.ctrlTaxCode.setValue(bean.getCode());
				editView.ctrlTaxName.setValue(bean.getName());
				editView.ctrlTaxNameS.setValue(bean.getNames());
				editView.ctrlTaxNameK.setValue(bean.getNamek());
				// ���X�g�{�b�N�X�i����d���敪�j
				editView.ctrlcboUsKbn.setSelectedItemValue(bean.getTaxType());
				// ���W�I�{�^��(����Ōv�Z��)
				editView.numOutputOrder.setValue(bean.getOdr());

				if (bean.isTaxConsumption() == false) {
					editView.rdoDisUse.setSelected(true);
				} else if (bean.isTaxConsumption() == true) {
					editView.rdoUse.setSelected(true);
				}

				if (editView.rdoDisUse.isSelected()) {
					btnDU_Click();
				}
				if (editView.rdoUse.isSelected()) {
					btnU_Click();
					editView.numOutputOrder.setValue(bean.getOdr());
				}

				editView.numConsumptionTaxRate.setNumber(bean.getRate());
				editView.ctrlStrDate.setValue(bean.getDateFrom());
				editView.ctrlEndDate.setValue(bean.getDateTo());

				if (isInvoice) {
					editView.ctrlNoInvReg.setSelected(bean.isNO_INV_REG_FLG());
					editView.ctrlTransitMeasure.setNumber(bean.getKEKA_SOTI_RATE());
				} else {
					editView.ctrlNoInvReg.setVisible(false);
					editView.ctrlTransitMeasure.setVisible(false);
					editView.lblTransitMeasure.setVisible(false);
				}
				changedNoInvReg();
				changedUseKbn();
				break;
		}
	}

	/**
	 * �ҏW���[�g�p���Ȃ�]�{�^������
	 */
	protected void btnDU_Click() {
		editView.numOutputOrder.setEditable(false);
		editView.numOutputOrder.clear();
	}

	/**
	 * �ҏW���[�g�p����]�{�^������
	 */
	protected void btnU_Click() {
		editView.numOutputOrder.setEditable(true);

	}

	/**
	 * �ҏW��ʂ̓��͒l�擾
	 * 
	 * @return Summary
	 */
	protected ConsumptionTax getInputtedConsumptionTax() {

		ConsumptionTax bean = new ConsumptionTax();

		bean.setCompanyCode(getCompanyCode());
		bean.setCode(editView.ctrlTaxCode.getValue());
		bean.setName(editView.ctrlTaxName.getValue());
		bean.setNames(editView.ctrlTaxNameS.getValue());
		bean.setNamek(editView.ctrlTaxNameK.getValue());
		// ���X�g�{�b�N�X�i����d���敪�j
		bean.setTaxType((TaxType) editView.ctrlcboUsKbn.getSelectedItemValue());
		// ���W�I�{�^��(����Ōv�Z��)
		if (editView.rdoDisUse.isSelected()) {
			bean.setTaxConsumption(false);
		} else if (editView.rdoUse.isSelected()) {
			bean.setTaxConsumption(true);
			bean.setOdr(editView.numOutputOrder.getValue());
		}
		bean.setRate(editView.numConsumptionTaxRate.getBigDecimal());// ����ŗ�
		bean.setDateFrom(editView.ctrlStrDate.getValue());
		bean.setDateTo(editView.ctrlEndDate.getValue());

		if (isInvoice) {
			bean.setNO_INV_REG_FLG(editView.ctrlNoInvReg.isSelected());
			bean.setKEKA_SOTI_RATE(editView.ctrlTransitMeasure.getBigDecimal()); // �o�ߑ[�u�T���\��
		}

		return bean;
	}

	/**
	 * ���������擾
	 * 
	 * @return SummarySearchCondition ��������
	 */
	protected ConsumptionTaxSearchCondition getSearchCondition() {

		ConsumptionTaxSearchCondition condition = new ConsumptionTaxSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setCodeFrom(mainView.ctrlTaxRefRan.getCodeFrom());
		condition.setCodeTo(mainView.ctrlTaxRefRan.getCodeTo());
		condition.setInvoiceFlg(isInvoice);
		if (!mainView.chkOutputTermEnd.isSelected()) {
			condition.setValidTerm(Util.getCurrentDate());
		}

		return condition;
	}

	/**
	 * �ꗗ�ɕ\������f�[�^���Z�b�g
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(ConsumptionTax bean) {

		List<Object> list = new ArrayList<Object>();

		list.add(bean.getCode()); // ����ŃR�[�h
		list.add(bean.getName()); // ����Ŗ���
		list.add(bean.getNames()); // ����ŗ���
		list.add(bean.getNamek()); // ����Ō�������
		list.add(getWord(TaxType.getName(bean.getTaxType()))); // ����d���敪
		list.add(bean.getRate()); // ����ŗ�
		list.add(bean.isTaxConsumption() ? bean.getOdr() : getWord("CLM0291")); // ����Ōv�Z�o�͂��i�{�^���j
		list.add(bean.isNO_INV_REG_FLG() ? getWord("C12198") : ""); // ��K�i���������s���Ǝ�
		list.add(DecimalUtil.isNullOrZero(bean.getKEKA_SOTI_RATE()) ? ""
			: NumberFormatUtil.formatNumber(bean.getKEKA_SOTI_RATE(), 0) + "%");
		list.add(DateUtil.toYMDString(bean.getDateFrom()));
		list.add(DateUtil.toYMDString(bean.getDateTo()));
		list.add(bean);

		return list;
	}

	/**
	 * @return ��������
	 */
	protected ConsumptionTaxSearchCondition createCondition() {
		return new ConsumptionTaxSearchCondition();
	}

	/**
	 * �ꗗ�őI�������f�[�^���擾
	 * 
	 * @return bean
	 * @exception Exception
	 */
	protected ConsumptionTax getSelected() throws Exception {

		ConsumptionTax bean = (ConsumptionTax) mainView.tbList
			.getSelectedRowValueAt(MG0130ConsumptionTaxMasterPanel.SC.ENTITY);

		ConsumptionTaxSearchCondition condition = new ConsumptionTaxSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setCode(bean.getCode());

		List<ConsumptionTax> list = getList(condition);

		// ���b�Z�[�W�ǉ��K�v ##########
		if (list == null || list.isEmpty()) {
			showMessage("I00193");// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			return null;
		}
		return list.get(0);
	}

	/**
	 * ���������ɊY������f�[�^��Ԃ�
	 * 
	 * @param condition
	 * @return List
	 * @throws Exception
	 */
	protected List<ConsumptionTax> getList(ConsumptionTaxSearchCondition condition) throws Exception {
		List<ConsumptionTax> list = (List<ConsumptionTax>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * �w��s�f�[�^���폜����
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(ConsumptionTax bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * @return ���f��
	 */
	protected Class getModelClass() {
		return ConsumptionTaxManager.class;
	}

	/**
	 * incoice�p�����}�X�^�ɓo�^����Ă����K�i���������s���Ǝ҂̏���ŃR�[�h���N���A���邩�`�F�b�N
	 * 
	 * @return boolean
	 */
	protected boolean chkTriMstNoInvRegTaxCode() {

		if (isBeforeNoInvRegFlg && !editView.ctrlNoInvReg.isSelected()) {
			return true;
		}

		return false;
	}

	/**
	 * ����d���敪�ύX����
	 */
	protected void changedUseKbn() {
		TaxType type = (TaxType) editView.ctrlcboUsKbn.getSelectedItemValue();

		if (TaxType.PURCHAESE == type) {
			editView.ctrlNoInvReg.setEnabled(true);
			editView.ctrlTransitMeasure.setEditable(editView.ctrlNoInvReg.isSelected());
		} else {
			editView.ctrlNoInvReg.setSelected(false);
			editView.ctrlNoInvReg.setEnabled(false);
			editView.ctrlTransitMeasure.clear();
			editView.ctrlTransitMeasure.setEditable(false);
		}
	}

	/**
	 * ��K�i�̏ꍇ�ɍT���\�����͉\
	 */
	protected void changedNoInvReg() {
		if (editView.ctrlNoInvReg.isSelected()) {
			editView.ctrlTransitMeasure.setEditable(true);

			if (Util.isNullOrEmpty(editView.ctrlTransitMeasure.getValue())) {
				editView.ctrlTransitMeasure.setNumber(100);
			}
		} else {
			editView.ctrlTransitMeasure.clear();
			editView.ctrlTransitMeasure.setEditable(false);
		}
	}

	/**
	 * �ҏW��ʓ��͒l�̑Ó������`�F�b�N
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */

	protected boolean isInputCorrect() throws Exception {

		if (Util.isNullOrEmpty(editView.ctrlTaxCode.getValue())) {
			showMessage(editView, "I00037", "C00573"); // ����ŃR�[�h
			editView.ctrlTaxCode.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlTaxName.getValue())) {
			showMessage(editView, "I00037", "C00774"); // ����Ŗ���
			editView.ctrlTaxName.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlTaxNameS.getValue())) {
			showMessage(editView, "I00037", "C00775"); // ����ŗ���
			editView.ctrlTaxNameS.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlTaxNameK.getValue())) {
			showMessage(editView, "I00037", "C00828"); // ����Ō�������
			editView.ctrlTaxNameK.requestTextFocus();
			return false;
		}

		if (editView.rdoUse.isSelected()) {

			if (Util.isNullOrEmpty(editView.numOutputOrder.getValue())) {
				showMessage(editView, "I00037", "C00776"); // ����Ōv�Z�o�͏���
				editView.numOutputOrder.requestTextFocus();
				return false;
			}
		}

		if (!editView.rdoDisUse.isSelected()) {
			String value = editView.numOutputOrder.getValue();
			if (!Util.isNullOrEmpty(value)) {
				if (Integer.parseInt(value) < 1 || 99 < Integer.parseInt(value)) {
					showMessage(editView, "W00065", 1, 99);
					editView.numOutputOrder.requestFocus();
					return false;
				}
			}
		}

		if (Util.isNullOrEmpty(editView.numConsumptionTaxRate.getValue())) {
			showMessage(editView, "I00037", "C00777"); // ����ŗ�
			editView.numConsumptionTaxRate.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlStrDate.getValue())) {
			showMessage(editView, "I00037", "COP706"); // �J�n�N����
			editView.ctrlStrDate.requestTextFocus();
			return false;
		}

		if (Util.isNullOrEmpty(editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00037", "COP707"); // �I���N����
			editView.ctrlEndDate.requestTextFocus();
			return false;
		}

		// �J�n�N����,�I���N�����`�F�b�N
		if ((!Util.isNullOrEmpty(editView.ctrlStrDate.getValue())
			&& !Util.isNullOrEmpty(editView.ctrlEndDate.getValue()))
			&& !Util.isSmallerThenByYMD(editView.ctrlStrDate.getValue(), editView.ctrlEndDate.getValue())) {
			showMessage(editView, "I00067");
			editView.ctrlStrDate.requestFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓����ЃR�[�h&&����Ј��R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			ConsumptionTaxSearchCondition condition = new ConsumptionTaxSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setCode(editView.ctrlTaxCode.getValue());

			List<ConsumptionTax> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00084", "C00697");
				editView.ctrlTaxCode.requestTextFocus();
				return false;
			}
		}

		if (isInvoice) {
			if (editView.ctrlTransitMeasure.isEditable()
				&& Util.isNullOrEmpty(editView.ctrlTransitMeasure.getValue())) {
				showMessage(editView, "I00037", "C12228"); // �o�ߑ[�u�\�T����
				editView.ctrlTransitMeasure.requestTextFocus();
				return false;
			}
		}
		return true;
	}
}