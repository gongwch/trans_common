package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �����t�B�[���h
 * 
 * @author roh
 */
public class TCustomerField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = 945316597939119806L;

	/** �R���g���[�� */
	protected TCustomerFieldCtrl ctrl;

	/** ��ЃR�[�h */
	private String companyCode;

	/** �L������ */
	private String termBasisDate;

	/** �d����t���O */
	private boolean siire;

	/** ���Ӑ�t���O */
	private boolean tokui;

	/** �J�n�R�[�h */
	private String beginCode;

	/** �I���R�[�h */
	private String endCode;

	/** �ؓ���\���t���O */
	private boolean isLoan;
	
	/** ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N */
	private boolean checkMode = true;

	/** Call�N���X */
	protected List<CallBackListener> callCtrlList = new LinkedList<CallBackListener>();

	/**
	 * �R���X�g���N�^
	 */
	public TCustomerField() {
		super();
		ctrl = new TCustomerFieldCtrl(this);
		initComponents();
	}

	/**
	 * �\�z
	 */
	private void initComponents() {

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

				return customerLostFocus();
			}
		});

		// ����{�^���������C�x���g
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

		// �{�^���T�C�Y�����l
		this.setButtonSize(85);
		// �t�B�[���h�T�C�Y�����l
		this.setFieldSize(75);
		// ���͌��������l
		this.setMaxLength(10);
		// IME����̐ݒ�
		this.setImeMode(false);
		// ���b�Z�[�WID�̐ݒ�
		this.setLangMessageID("C00408");
	}

	/**
	 * ���[�X�g�t�H�[�J�X
	 * 
	 * @return boolean
	 */
	protected boolean customerLostFocus() {
		for (CallBackListener callCtrl : callCtrlList) {
			callCtrl.before();
		}

		boolean sts = ctrl.setCustomerNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}

		return sts;
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
	 * �d����t���O�K��
	 * 
	 * @return �d����t���O
	 */
	public boolean isSiire() {
		return siire;
	}

	/**
	 * �d����t���O�ݒ�
	 * 
	 * @param siire �d����t���O<br>
	 *            true�ɐݒ莞�A�d���悾����\������
	 */
	public void setSiire(boolean siire) {
		this.siire = siire;
	}

	/**
	 * ���Ӑ�t���O�K��
	 * 
	 * @return ���Ӑ�t���O
	 */
	public boolean isTokui() {
		return tokui;
	}

	/**
	 * ���Ӑ�t���O�ݒ�
	 * 
	 * @param tokui ���Ӑ�t���O<br>
	 *            true�ɐݒ莞�A ���Ӑ悾����\������B
	 */
	public void setTokui(boolean tokui) {
		this.tokui = tokui;
	}

	/**
	 * �J�n�R�[�h�̎擾
	 * 
	 * @return beginCode �J�n�R�[�h
	 */
	public String getBeginCode() {
		return beginCode;
	}

	/**
	 * �J�n�R�[�h�̐ݒ�
	 * 
	 * @param beginCode
	 */
	public void setBeginCode(String beginCode) {
		this.beginCode = beginCode;
	}

	/**
	 * �I���R�[�h�̎擾
	 * 
	 * @return endCode �I���R�[�h
	 */
	public String getEndCode() {
		return endCode;
	}

	/**
	 * �I���R�[�h�̐ݒ�
	 * 
	 * @param endCode
	 */
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}

	/**
	 * CallBackListener��ǉ�����
	 * 
	 * @param callCtrl CallBackListener
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}
	
	/**
	 * ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N���s�����ǂ���
	 * 
	 * @return true: �`�F�b�N����
	 */
	public boolean isCheckMode() {
		return checkMode;
	}

	/**
	 * ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N���s�����ǂ���
	 * 
	 * @param chekcMode true: �`�F�b�N����
	 */
	public void setCheckMode(boolean chekcMode) {
		this.checkMode = chekcMode;
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
		boolean sts = ctrl.setCustomerNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
			listener.after(sts);
		}
	}

	/**
	 * �ؓ���t���O�ݒ�<br>
	 * TRUE�ݒ莞�A�_�C�A���O�Ɏؓ������\�������B
	 * 
	 * @param isLoan
	 */
	public void setLoanCustomer(boolean isLoan) {
		this.isLoan = isLoan;
	}

	/**
	 * �ؓ���t���O�擾
	 * 
	 * @return �ؓ���t���O
	 */
	public boolean isLoanCustomer() {
		return isLoan;
	}

}
