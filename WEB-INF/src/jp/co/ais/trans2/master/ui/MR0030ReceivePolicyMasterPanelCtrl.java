package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.bill.*;

/**
 * MR0030ReceivePolicyMaster - �������j�}�X�^ - Main Controller
 * 
 * @author AIS
 */
public class MR0030ReceivePolicyMasterPanelCtrl extends TController {

	/** �w����� */
	protected MR0030ReceivePolicyMasterPanel mainView = null;

	/** ���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p���� */
	protected Mode mode = null;

	/** �������j */
	protected ReceivePolicy receivePolicy = null;

	/** AR�F�������ԍ������̔Ԃ��g�p���� */
	protected static boolean isUseARInvAutoNumbering = ClientConfig.isFlagOn("trans.use.ar.inv.auto.numbering");

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
		mainView = new MR0030ReceivePolicyMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`
	 */
	protected void addMainViewEvent() {

		// [�m��]�{�^������
		mainView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [���]�{�^������
		mainView.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCancel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// �����̔Ԃ��g�p����`�F�b�N��OFF�`�F�b�N
		mainView.chkInvoiceNo.addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {
				setInvoiceNoEditable(false);
			}
		});
		// �����ݒ荀��1
		mainView.cboInvoiceNo1.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {

				InvoiceNoAdopt adopt = (InvoiceNoAdopt) mainView.cboInvoiceNo1.getSelectedItemValue();
				if (InvoiceNoAdopt.FIXED_CHAR != adopt) {
					mainView.cboInvoiceNo1.setEditable(false);
					mainView.ctrlInvoiceNo1Name.setText(null);
					mainView.ctrlInvoiceNo1Name.setEnabled(false);
				} else {
					mainView.ctrlInvoiceNo1Name.setEnabled(true);
				}
			}
		});
		// �����ݒ荀��2
		mainView.cboInvoiceNo2.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {

				InvoiceNoAdopt adopt = (InvoiceNoAdopt) mainView.cboInvoiceNo2.getSelectedItemValue();
				if (InvoiceNoAdopt.FIXED_CHAR != adopt) {
					mainView.cboInvoiceNo2.setEditable(false);
					mainView.ctrlInvoiceNo2Name.setText(null);
					mainView.ctrlInvoiceNo2Name.setEnabled(false);
				} else {
					mainView.ctrlInvoiceNo2Name.setEnabled(true);
				}
			}
		});
		// �����ݒ荀��3
		mainView.cboInvoiceNo3.getComboBox().addItemListener(new ItemListener() {

			public void itemStateChanged(@SuppressWarnings("unused") ItemEvent e) {

				InvoiceNoAdopt adopt = (InvoiceNoAdopt) mainView.cboInvoiceNo3.getSelectedItemValue();
				if (InvoiceNoAdopt.FIXED_CHAR != adopt) {
					mainView.cboInvoiceNo3.setEditable(false);
					mainView.ctrlInvoiceNo3Name.setText(null);
					mainView.ctrlInvoiceNo3Name.setEnabled(false);
				} else {
					mainView.ctrlInvoiceNo3Name.setEnabled(true);
				}
			}
		});

	}

	/**
	 * �����̔Ԃ��g�p���� �`�F�b�N�{�b�N�X�ύX����
	 * 
	 * @param isStart true:�����N��
	 */
	protected void setInvoiceNoEditable(boolean isStart) {
		// �S�Ă̍��ڂ��g�p��
		if (mainView.chkInvoiceNo.isSelected()) {

			mainView.cboInvoiceNo1.setEnabled(true);
			mainView.cboInvoiceNo2.setEnabled(true);
			mainView.cboInvoiceNo3.setEnabled(true);
			mainView.ctrlInvoiceNoDigit.setEnabled(true);
			if (!isStart) {
				mainView.ctrlInvoiceNoDigit.setNumber(3);
			}
		} else {

			mainView.cboInvoiceNo1.setEnabled(false);
			mainView.cboInvoiceNo2.setEnabled(false);
			mainView.cboInvoiceNo3.setEnabled(false);

			mainView.ctrlInvoiceNo1Name.clear();
			mainView.ctrlInvoiceNo2Name.clear();
			mainView.ctrlInvoiceNo3Name.clear();

			mainView.ctrlInvoiceNo1Name.setEnabled(false);
			mainView.ctrlInvoiceNo2Name.setEnabled(false);
			mainView.ctrlInvoiceNo3Name.setEnabled(false);

			mainView.ctrlInvoiceNoDigit.clear();
			mainView.ctrlInvoiceNoDigit.setEnabled(false);

		}
		if (!isStart) {
			// �����l�F�Ȃ� �͋���
			mainView.cboInvoiceNo1.setSelectedItemValue(InvoiceNoAdopt.NONE);
			mainView.cboInvoiceNo2.setSelectedItemValue(InvoiceNoAdopt.NONE);
			mainView.cboInvoiceNo3.setSelectedItemValue(InvoiceNoAdopt.NONE);
		}
	}

	/**
	 * �w�����[�m��]�{�^������
	 */
	protected void btnSettle_Click() {

		try {
			// ���̓`�F�b�N���s���B
			if (!isInputCheck()) {
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00004")) {
				return;
			}

			ReceivePolicy bean = getInputedReceivePolicy();
			request(getModelClass(), "modify", bean);

			// �������b�Z�[�W
			showMessage(mainView, "I00008");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w�����[���]�{�^������
	 */
	protected void btnCancel_Click() {

		// �m�F���b�Z�[�W
		if (!showConfirmMessage(mainView, "Q00002")) {
			return;
		}
		initMainView();
	}

	/**
	 * �w����ʏ�����
	 */
	protected void initMainView() {

		ReceivePolicy bean = null;

		try {

			bean = (ReceivePolicy) request(getModelClass(), "get");

		} catch (TException e) {

			errorHandler(e);
		}

		if (bean == null) {
			// ���������ɊY������f�[�^�����݂��Ȃ��ꍇ�A�����l��\��
			mainView.ctrlRecordLength.setNumber(200);
			mainView.ctrlCRLF.setSelected(false);
			mainView.rdoFormatA.setSelected(true);
			mainView.ctrlDifferenceToleranceStr.setValue(0);
			mainView.ctrlDifferenceToleranceEnd.setValue(0);
			mainView.chkBillNoInputFlg.setSelected(false);
			mainView.chkBillCreateFlg.setSelected(false);
		} else {
			// �Y���f�[�^�����݂���ꍇ�A�擾�l��\��
			mainView.ctrlRecordLength.setNumber(bean.getLength());
			// CR/LF�t
			if (bean.getLineType().equals("1")) {
				mainView.ctrlCRLF.setSelected(true);
			} else {
				mainView.ctrlCRLF.setSelected(false);
			}
			// �t�H�[�}�b�g
			if (bean.getFormat().equals("1")) {
				mainView.rdoFormatB.setSelected(true);
			} else {
				mainView.rdoFormatA.setSelected(true);
			}

			mainView.ctrlDifferenceToleranceStr.setNumber(bean.getFeeFrom().intValue());
			mainView.ctrlDifferenceToleranceEnd.setNumber(bean.getFeeTo().intValue());

			// �ȖڃR�[�h
			mainView.ctrlItemGroup.ctrlItemReference.setCode(bean.getItemCode());
			// �⏕�R�[�h
			mainView.ctrlItemGroup.ctrlSubItemReference.setCode(bean.getSubItemCode());
			// ����R�[�h
			mainView.ctrlItemGroup.ctrlDetailItemReference.setCode(bean.getDetailCode());
			// �ȖځA�⏕�A����̗���
			mainView.ctrlItemGroup.refleshGroupEntity();

			// �v�㕔��
			mainView.ctrlDepartment.setCode(bean.getDepartmentCode());
			mainView.ctrlDepartment.refleshEntity();

			// �����
			mainView.ctrlConsumptionTax.setCode(bean.getTaxCode());
			mainView.ctrlConsumptionTax.refleshEntity();

			mainView.chkBillNoInputFlg.setSelected(bean.isCompulsoryInputBillNo());
			mainView.chkBillCreateFlg.setSelected(bean.isCheckBillMake());

			// �����ԍ� �̔ԊǗ�
			mainView.chkInvoiceNo.setSelected(bean.isInvNumbering());
			mainView.cboInvoiceNo1.setSelectedItemValue(bean.getInvoiceNoAdopt1());
			mainView.cboInvoiceNo2.setSelectedItemValue(bean.getInvoiceNoAdopt2());
			mainView.cboInvoiceNo3.setSelectedItemValue(bean.getInvoiceNoAdopt3());
			mainView.ctrlInvoiceNo1Name.setText(bean.getInvNo1Name());
			mainView.ctrlInvoiceNo2Name.setText(bean.getInvNo2Name());
			mainView.ctrlInvoiceNo3Name.setText(bean.getInvNo3Name());
			mainView.ctrlInvoiceNoDigit.setNumber(bean.getInvNoDigit());

		}

		if (isUseARInvAutoNumbering) {
			// �����̔Ԃ��g�p���鏈��
			setInvoiceNoEditable(true);
		} else {
			// ��ʔ�\��
			mainView.pnlInvoiceNo.setVisible(false);
			mainView.chkInvoiceNo.setVisible(false);
			mainView.cboInvoiceNo1.setVisible(false);
			mainView.cboInvoiceNo2.setVisible(false);
			mainView.cboInvoiceNo3.setVisible(false);
			mainView.ctrlInvoiceNo1Name.setVisible(false);
			mainView.ctrlInvoiceNo2Name.setVisible(false);
			mainView.ctrlInvoiceNo3Name.setVisible(false);
			mainView.ctrlInvoiceNoDigit.setVisible(false);
		}
	}

	/**
	 * ��ʂœ��͂��ꂽ�f�[�^��Ԃ�
	 * 
	 * @return ��ʂœ��͂��ꂽ�f�[�^
	 */
	protected ReceivePolicy getInputedReceivePolicy() {

		ReceivePolicy bean = new ReceivePolicy();

		bean.setCompanyCode(getCompanyCode());
		bean.setLength(mainView.ctrlRecordLength.getIntValue());

		if (mainView.ctrlCRLF.isSelected() == true) {
			bean.setLineType("1");
		} else {
			bean.setLineType("0");
		}

		if (mainView.rdoFormatA.isSelected() == true) {
			bean.setFormat("0");
		} else {
			bean.setFormat("1");
		}

		bean.setFeeFrom(mainView.ctrlDifferenceToleranceStr.getBigDecimal());
		bean.setFeeTo(mainView.ctrlDifferenceToleranceEnd.getBigDecimal());

		bean.setItemCode(mainView.ctrlItemGroup.ctrlItemReference.getCode());
		bean.setSubItemCode(mainView.ctrlItemGroup.ctrlSubItemReference.getCode());
		bean.setDetailCode(mainView.ctrlItemGroup.ctrlDetailItemReference.getCode());

		bean.setDepartmentCode(mainView.ctrlDepartment.getCode());
		bean.setTaxCode(mainView.ctrlConsumptionTax.getCode());

		bean.setCompulsoryInputBillNo(mainView.chkBillNoInputFlg.isSelected());
		bean.setCheckBillMake(mainView.chkBillCreateFlg.isSelected());

		// �����ԍ� �̔ԊǗ�
		bean.setInvNumbering(mainView.chkInvoiceNo.isSelected());
		bean.setInvoiceNoAdopt1((InvoiceNoAdopt) mainView.cboInvoiceNo1.getSelectedItemValue());
		bean.setInvoiceNoAdopt2((InvoiceNoAdopt) mainView.cboInvoiceNo2.getSelectedItemValue());
		bean.setInvoiceNoAdopt3((InvoiceNoAdopt) mainView.cboInvoiceNo3.getSelectedItemValue());
		bean.setInvNo1Name(mainView.ctrlInvoiceNo1Name.getText());
		bean.setInvNo2Name(mainView.ctrlInvoiceNo2Name.getText());
		bean.setInvNo3Name(mainView.ctrlInvoiceNo3Name.getText());
		bean.setInvNoDigit(mainView.ctrlInvoiceNoDigit.getIntValue());

		return bean;
	}

	/**
	 * @return ���f��
	 */
	protected Class getModelClass() {
		return ReceivePolicyManager.class;
	}

	/**
	 * �ҏW��ʓ��͒l�̑Ó������`�F�b�N *
	 * 
	 * @return bool - true:run, false:stop
	 */
	protected boolean isInputCheck() {

		// ���R�[�h���������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(mainView.ctrlRecordLength.getValue())) {
			showMessage(mainView, "I00037", "C10020");
			mainView.ctrlRecordLength.requestTextFocus();
			return false;
		}

		// ���e���z�͈� �J�n���z�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(mainView.ctrlDifferenceToleranceStr.getValue())) {
			showMessage(mainView, "I00037", "C10156");
			mainView.ctrlDifferenceToleranceStr.requestFocus();
			return false;
		}

		// ���e���z�͈� �I�����z�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(mainView.ctrlDifferenceToleranceEnd.getValue())) {
			showMessage(mainView, "I00037", "C10197");
			mainView.ctrlDifferenceToleranceEnd.requestFocus();
			return false;
		}

		// �J�n���z > �I�����z�̏ꍇ�G���[
		if (mainView.ctrlDifferenceToleranceStr.getInt() > mainView.ctrlDifferenceToleranceEnd.getInt()) {
			showMessage(mainView, "W00066");
			mainView.ctrlDifferenceToleranceStr.requestFocus();
			return false;
		}

		// �ȖڃR�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(mainView.ctrlItemGroup.ctrlItemReference.getCode())) {
			showMessage(mainView, "I00037", "C00077");
			mainView.ctrlItemGroup.ctrlItemReference.requestTextFocus();
			return false;
		}

		// �⏕�ȖڃR�[�h�������͂̏ꍇ�G���[
		if (mainView.ctrlItemGroup.ctrlSubItemReference.code.isEditable()) {
			if (Util.isNullOrEmpty(mainView.ctrlItemGroup.ctrlSubItemReference.getCode())) {
				showMessage(mainView, "I00037", "C00488");
				mainView.ctrlItemGroup.ctrlSubItemReference.requestTextFocus();
				return false;
			}
		}

		// ����ȖڃR�[�h�������͂̏ꍇ�G���[
		if (mainView.ctrlItemGroup.ctrlDetailItemReference.code.isEditable()) {
			if (Util.isNullOrEmpty(mainView.ctrlItemGroup.ctrlDetailItemReference.getCode())) {
				showMessage(mainView, "I00037", "C00025");
				mainView.ctrlItemGroup.ctrlDetailItemReference.requestTextFocus();
				return false;
			}
		}

		// �v�㕔��R�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(mainView.ctrlDepartment.getCode())) {
			showMessage(mainView, "I00037", "C00571");
			mainView.ctrlDepartment.requestTextFocus();
			return false;
		}

		// ����ŃR�[�h�������͂̏ꍇ�G���[
		if (Util.isNullOrEmpty(mainView.ctrlConsumptionTax.getCode())) {
			showMessage(mainView, "I00037", "C00573");
			mainView.ctrlConsumptionTax.requestTextFocus();
			return false;
		}

		// �����̔Ԃ��g�p����ꍇ�̂݃`�F�b�N
		if (isUseARInvAutoNumbering && mainView.chkInvoiceNo.isSelected()) {
			// �����ݒ荀��1
			InvoiceNoAdopt adopt = (InvoiceNoAdopt) mainView.cboInvoiceNo1.getSelectedItemValue();
			if (InvoiceNoAdopt.FIXED_CHAR == adopt && //
				Util.isNullOrEmpty(mainView.ctrlInvoiceNo1Name.getValue())) {
				showMessage(mainView, "I00037", "C00713");
				mainView.ctrlInvoiceNo1Name.requestFocus();
				return false;
			}
			// �����ݒ荀��2
			adopt = (InvoiceNoAdopt) mainView.cboInvoiceNo2.getSelectedItemValue();
			if (InvoiceNoAdopt.FIXED_CHAR == adopt && //
				Util.isNullOrEmpty(mainView.ctrlInvoiceNo2Name.getValue())) {
				showMessage(mainView, "I00037", "C00714");
				mainView.ctrlInvoiceNo2Name.requestFocus();
				return false;
			}
			// �����ݒ荀��3
			adopt = (InvoiceNoAdopt) mainView.cboInvoiceNo3.getSelectedItemValue();
			if (InvoiceNoAdopt.FIXED_CHAR == adopt && //
				Util.isNullOrEmpty(mainView.ctrlInvoiceNo3Name.getValue())) {
				showMessage(mainView, "I00037", "C00715");
				mainView.ctrlInvoiceNo3Name.requestFocus();
				return false;
			}
			// �����̔ԕ�����
			if (Util.isNullOrEmpty(mainView.ctrlInvoiceNoDigit.getValue())) {
				showMessage(mainView, "I00037", "C00224");
				mainView.ctrlInvoiceNoDigit.requestFocus();
				return false;
			}

		}

		return true;
	}
}