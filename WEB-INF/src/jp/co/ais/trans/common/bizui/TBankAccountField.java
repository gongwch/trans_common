package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * ��s�����t�B�[���h
 * 
 * @author roh
 */
public class TBankAccountField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = 2496968603353850946L;

	/** �R���g���[�� */
	protected TBankAccountFieldCtrl ctrl;

	/** ��ЃR�[�h */
	private String companyCode;

	/** �ЊO�敪 */
	private boolean outKbn;

	/** �Ј��x�����敪 */
	private boolean empKbn;

	/** �L������ */
	private String termBasisDate;

	/** Call�N���X */
	protected List<CallBackListener> callCtrlList;

	/**
	 * �R���X�g���N�^
	 */
	public TBankAccountField() {
		super();
		initComponents();
		ctrl = new TBankAccountFieldCtrl(this);
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

}
