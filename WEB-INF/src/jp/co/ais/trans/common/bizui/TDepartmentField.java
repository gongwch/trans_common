package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;
import java.awt.event.*;
import java.util.*;

import javax.swing.*;

/**
 * �v�㕔��p�t�B�[���h <br>
 * 
 * @author roh
 */
public class TDepartmentField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = -762992297227133680L;

	/** �g�D���x���ݒ� �O */
	public static final int LVL_0 = 1;

	/** �g�D���x���ݒ� �P */
	public static final int LVL_1 = 2;

	/** �g�D���x���ݒ� �Q */
	public static final int LVL_2 = 3;

	/** �g�D���x���ݒ� �R */
	public static final int LVL_3 = 4;

	/** �g�D���x���ݒ� �S */
	public static final int LVL_4 = 5;

	/** �g�D���x���ݒ� �T */
	public static final int LVL_5 = 6;

	/** �g�D���x���ݒ� �U */
	public static final int LVL_6 = 7;

	/** �g�D���x���ݒ� �V */
	public static final int LVL_7 = 8;

	/** �g�D���x���ݒ� �W */
	public static final int LVL_8 = 9;

	/** �g�D���x���ݒ� �X */
	public static final int LVL_9 = 10;

	/** �g�D���x���ݒ� �Ȃ� */
	public static final int NOT_SET = 11;

	/** �R���g���[�� */
	private TDepartmentFieldCtrl ctrl;

	/** ��ЃR�[�h */
	private String companyCode;

	/** �L������ */
	private String termBasisDate;

	/** �W�v�敪 */
	private boolean sumDepartment = true;

	/** ���͋敪 */
	private boolean inputDepartment = true;

	/** �g�D�R�[�h�� */
	private String organization;

	/** �K�w���x�� */
	private int departmentLevel;

	/** ��ʕ��� */
	private String upperDepartment;

	/** �J�n�R�[�h */
	private String beginCode;

	/** �I���R�[�h */
	private String endCode;
	
	/** ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N */
	private boolean chekcMode = true;

	/** Call�N���X */
	protected List<CallBackListener> callCtrlList = new LinkedList<CallBackListener>();

	/**
	 * �R���X�g���N�^
	 */
	public TDepartmentField() {
		super();
		ctrl = new TDepartmentFieldCtrl(this);
		initComponents();
	}

	/**
	 * ��ʍ\�z
	 */
	private void initComponents() {

		// ���X�g�t�H�[�J�X���C�x���g
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				if (!getField().isEditable()) {
					return true;
				}
				if (!isValueChanged()) {
					return true;
				}

				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				boolean sts = ctrl.setDepartNotice();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}

				return sts;
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
		this.setLangMessageID("C00467");
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
	 * �K�w���x���K��
	 * 
	 * @return departmentLevel �K�w���x��
	 */
	public int getDepartmentLevel() {
		return departmentLevel;
	}

	/**
	 * �K�w���x���ݒ�
	 * 
	 * @param departmentLevel �K�w���x��<br>
	 *            �g�D��̏�ʃ��x���w��<br>
	 *            <br>
	 *            TDepartmentField�N���X��LVL_1 - LVL9�܂Œ萔���g���B<br>
	 *            ���ڐ�������͂���ꍇ�w�背�x����+�P�l���p�����[�^�œn���B
	 */
	public void setDepartmentLevel(int departmentLevel) {
		this.departmentLevel = departmentLevel;
	}

	/**
	 * ���͋敪�K��
	 * 
	 * @return inputDepartment
	 */
	public boolean isInputDepartment() {
		return inputDepartment;
	}

	/**
	 * ���͋敪�ݒ�
	 * 
	 * @param inputDepartment ���͕���t���O <br>
	 *            false�ɐݒ肷��ƏW�v���傾����\������B
	 */
	public void setInputDepartment(boolean inputDepartment) {
		this.inputDepartment = inputDepartment;
	}

	/**
	 * �g�D�R�[�h�K��
	 * 
	 * @return organization
	 */
	public String getOrganization() {
		return organization;
	}

	/**
	 * �g�D�R�[�h�ݒ�
	 * 
	 * @param organization �g�D���������i�g�D�R�[�h�j<br>
	 *            �g�D�R�[�h��ݒ肷���,�ݒ肢���g�D�̕��傾�����\�������B
	 */
	public void setOrganization(String organization) {
		this.organization = organization;
	}

	/**
	 * �W�v�敪�K��
	 * 
	 * @return sumDepartment
	 */
	public boolean isSumDepartment() {
		return sumDepartment;
	}

	/**
	 * �W�v�敪�ݒ�
	 * 
	 * @param sumDepartment �W�v����t���O<br>
	 *            false�ɐݒ肷��Ɠ��͕��傾����\������B
	 */
	public void setSumDepartment(boolean sumDepartment) {
		this.sumDepartment = sumDepartment;
	}

	/**
	 * �L�����Ԑݒ�
	 * 
	 * @return �L������
	 */
	public String getTermBasisDate() {
		return termBasisDate;
	}

	/**
	 * �L�����ԏK��
	 * 
	 * @param termBasisDate �L������
	 */
	public void setTermBasisDate(String termBasisDate) {
		this.termBasisDate = termBasisDate;
	}

	/**
	 * ��ʕ���R�[�h�K��
	 * 
	 * @return upperDepartment
	 */
	public String getUpperDepartment() {
		return upperDepartment;
	}

	/**
	 * �ݒ�
	 * 
	 * @param upperDepartment ��ʕ���R�[�h<br>
	 *            ��ʃ��x�����ݒ肳�ꂽ�ꍇ�A��ʕ����ݒ肷��B<br>
	 *            ��ʕ���R�[�h�ɓ����镔�傾����\������B
	 */
	public void setUpperDepartment(String upperDepartment) {
		this.upperDepartment = upperDepartment;
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
	 * ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N���s�����ǂ���
	 * 
	 * @return true: �`�F�b�N����
	 */
	public boolean isChekcMode() {
		return chekcMode;
	}

	/**
	 * ���X�g�t�H�[�J�X���̃R�[�h���݃`�F�b�N���s�����ǂ���
	 * 
	 * @param chekcMode true: �`�F�b�N����
	 */
	public void setChekcMode(boolean chekcMode) {
		this.chekcMode = chekcMode;
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

		ctrl.setDepartNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}
	}
}
