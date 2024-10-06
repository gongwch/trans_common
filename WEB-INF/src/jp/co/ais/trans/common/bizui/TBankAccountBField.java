package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.master.entity.*;

/**
 * ��s�����t�B�[���h(��s���ɋ�s�}�X�^.��s��+��s�}�X�^.�x�X����\������)
 * 
 * @author roh
 */
public class TBankAccountBField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = 2496968603353850225L;

	/** �R���g���[�� */
	protected TBankAccountBFieldCtrl ctrl;

	/** ��ЃR�[�h */
	private String companyCode;

	/** �ЊO�敪 */
	private boolean outKbn;

	/** �Ј��x���敪 */
	private boolean empKbn;

	/** �L������ */
	private String termBasisDate;

	/** REF�^�C�g�� */
	private String refTitle;

	/** ��s�����}�X�^ */
	private AP_CBK_MST bean;

	/** ��s�������X�g */
	private List<String> cbkCodes;

	/** Call�N���X */
	protected List<CallBackListener> callCtrlList;

	/**
	 * �R���X�g���N�^
	 */
	public TBankAccountBField() {
		super();
		initComponents();
		ctrl = new TBankAccountBFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
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
		this.setLangMessageID("C00857");

		// ���X�g�t�H�[�J�X
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!getField().isEditable()) {
					return true;
				}

				if (!isValueChanged()) {
					return true;
				}
				return setBankAccountFocus();
			}
		});

		// ����{�^��������
		addButtonActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				ctrl.showSearchDialog();
			}
		});
	}

	/**
	 * ���[�X�g�t�H�[�J�X��
	 * 
	 * @return boolean
	 */
	protected boolean setBankAccountFocus() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean sts = ctrl.setBankAccountNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}

		return sts;
	}

	/**
	 * Call�N���X���Z�b�g����B<BR>
	 * 
	 * @param callCtrl CallControl�N���X
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * ��ЃR�[�h�擾
	 * 
	 * @return ��ЃR�[�h
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
	 * �Ј��x���t���O�擾
	 * 
	 * @return empKbn �Ј��x���t���O
	 */
	public boolean isEmpKbn() {
		return empKbn;
	}

	/**
	 * �Ј��x���t���O�ݒ�
	 * 
	 * @param empKbn �Ј��x���t���O<br>
	 *            true�ݒ莞�A�Ј��x��������\������
	 */
	public void setEmpKbn(boolean empKbn) {
		this.empKbn = empKbn;
	}

	/**
	 * �ЊO�x���敪�擾
	 * 
	 * @return outKbn �ЊO�x��
	 */
	public boolean isOutKbn() {
		return outKbn;
	}

	/**
	 * �ЊO�x���敪�擾
	 * 
	 * @param outKbn �ЊO�x��<br>
	 *            true�ɐݒ莞�A�ЊO�x��������\������B
	 */
	public void setOutKbn(boolean outKbn) {
		this.outKbn = outKbn;
	}

	/**
	 * �L�����Ԏ擾
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
		ctrl.setBankAccountNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}
	}

	/**
	 * ��s�����}�X�^���擾����
	 * 
	 * @return ��s�����}�X�^
	 */
	public AP_CBK_MST getBean() {
		return bean;
	}

	/**
	 * ��s�����}�X�^��ݒ肷��
	 * 
	 * @param bean
	 */
	public void setBean(AP_CBK_MST bean) {
		this.bean = bean;
	}

	/**
	 * REF�^�C�g�����擾����
	 * 
	 * @return REF�^�C�g��
	 */
	public String getRefTitle() {
		return refTitle;
	}

	/**
	 * REF�^�C�g����ݒ肷��
	 * 
	 * @param refTitle
	 */
	public void setRefTitle(String refTitle) {
		this.refTitle = refTitle;
	}

	/**
	 * ��s�������X�g�擾
	 * 
	 * @return cbkCodes
	 */
	public List<String> getCbkCodes() {
		return cbkCodes;
	}

	/**
	 * ��s�������X�g�ݒ�
	 * 
	 * @param cbkCodes
	 */
	public void setCbkCodes(List<String> cbkCodes) {
		this.cbkCodes = cbkCodes;
	}

}
