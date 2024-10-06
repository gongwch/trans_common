package jp.co.ais.trans.common.bizui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �x�������t�B�[���h
 * 
 * @author roh
 */
public class TPaymentConditionField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = -6991199356860005469L;

	/** �R���g���[�� */
	protected TPaymentConditionFieldCtrl ctrl;

	/** �L������ */
	private String termBasisDate;

	/** ��ЃR�[�h */
	private String companyCode;

	/** �����R�[�h */
	private String customerCode;

	/** Call�N���X */
	protected List<CallBackListener> callCtrlList;

	/**
	 * �R���X�g���N�^
	 */
	public TPaymentConditionField() {
		super();
		ctrl = new TPaymentConditionFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
		initComponents();
	}

	/**
	 * ��ЃR�[�h�K��
	 * 
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �L�����ԏK��
	 * 
	 * @return �L������
	 */
	public String getTermBasisDate() {
		return termBasisDate;
	}

	/**
	 * �L�����Ԑݒ�
	 * 
	 * @param termBasisDate �L������
	 */
	public void setTermBasisDate(String termBasisDate) {
		this.termBasisDate = termBasisDate;
	}

	/**
	 * �\�z
	 */
	private void initComponents() {
		// �{�^���T�C�Y�����l
		this.setButtonSize(85);
		// �t�B�[���h�T�C�Y�����l
		this.setFieldSize(75);
		// ���͌��������l
		this.setMaxLength(10);
		// IME����̐ݒ�
		this.setImeMode(false);
		// ���b�Z�[�WID�̐ݒ�
		this.setLangMessageID("C00238");

		// ���X�g�t�H�[�J�X���C�x���g
		setInputVerifier(new TInputVerifier() {

			@Override
			public boolean verifyCommit(JComponent comp) {
				if (!getField().isEditable()) {
					return true;
				}
				if (!isValueChanged()) {
					return true;
				}

				setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
				return setPayConditionFocus();
			}
		});

		// �x���������{�^���������C�x���g
		addButtonActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {

				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}
				ctrl.showSearchDialog();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}

			}
		});
	}

	/**
	 * ���[�X�g�t�H�[�J�X��
	 * 
	 * @return boolean
	 */
	protected boolean setPayConditionFocus() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean sts = ctrl.setPaymentConditionNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}
		return sts;
	}

	/**
	 * �����ݒ�
	 * 
	 * @return customerCode �����R�[�h
	 */
	public String getCustomerCode() {
		return customerCode;
	}

	/**
	 * �����ݒ�
	 * 
	 * @param customerCode �����
	 */
	public void setCustomerCode(String customerCode) {
		this.customerCode = customerCode;
	}

	/**
	 * Call�N���X���Z�b�g����
	 * 
	 * @param callCtrl CallControl�N���X
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * field �e�L�X�g�t�B�[���h�ɕ������ݒ肷��
	 * 
	 * @param value �ݒ蕶����
	 */
	@Override
	public void setValue(String value) {
		super.setValue(value);
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}
		boolean sts = ctrl.setPaymentConditionNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}
	}
}
