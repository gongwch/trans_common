package jp.co.ais.trans.common.bizui;

import java.awt.event.*;
import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.bizui.ctrl.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �Ј������t�B�[���h
 * 
 * @author roh
 */
public class TEmployeeField extends TButtonField {

	/** UID */
	private static final long serialVersionUID = 8690144683548660582L;

	/** �R���g���[�� */
	protected TEmployeeFieldCtrl ctrl;

	/** �L������ */
	private String termBasisDate;

	/** ��ЃR�[�h */
	private String companyCode;

	/** ���[�U�o�^�t���O */
	private boolean user;

	/** ����R�[�h */
	private String departmentCode;

	/** Call�N���X */
	private List<CallBackListener> callCtrlList;

	/** �J�n�R�[�h */
	private String beginCode;

	/** �I���R�[�h */
	private String endCode;

	/**
	 * �R���X�g���N�^
	 */
	public TEmployeeField() {
		super();
		ctrl = new TEmployeeFieldCtrl(this);
		callCtrlList = Collections.synchronizedList(new LinkedList<CallBackListener>());
		initComponents();
	}

	/**
	 * �\�z
	 */
	private void initComponents() {

		// ���X�g�t�H�[�J�X���C�x���g
		setInputVerifier(new TInputVerifier() {

			public boolean verifyCommit(JComponent comp) {
				// ���͕s�\�̏ꍇ�A�t�H�[�J�X�C�x���g�͔������Ȃ�
				if (!getField().isEditable()) {
					return true;
				}
				// �t�B�[���h�l��ύX���Ȃ��܂܂��ƃt�H�[�J�X�C�x���g�͔������Ȃ�
				if (!isValueChanged()) {
					return true;
				}

				for (CallBackListener callCtrl : callCtrlList) {
					callCtrl.before();
				}

				ctrl.termBasisDateFlag = true;
				// �ڍ׃t�B�[���h�Ɏw��̓��e���Z�b�g����B
				// FALSE���Ԃ��ꂽ�ꍇverify���s��Ȃ��B
				boolean sts = ctrl.setEmployeeNotice();

				for (CallBackListener listener : callCtrlList) {
					listener.after();
				}
				return sts;
			}
		});

		// �{�^�����X�i�[������
		addButtonActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent evt) {
				// �����_�C�A���O��\������B
				ctrl.showSearchDialog();
			}
		});

		// �T�C�Y�����l
		this.setButtonSize(85);
		this.setFieldSize(75);

		// ���͌��������l
		this.setMaxLength(10);
		this.setImeMode(false);
		this.setLangMessageID("C00246");
	}

	/**
	 * Call�N���X���Z�b�g����B
	 * 
	 * @param callCtrl CallControl�N���X
	 */
	public void addCallControl(CallBackListener callCtrl) {
		this.callCtrlList.add(callCtrl);
	}

	/**
	 * ����R�[�h�K��
	 * 
	 * @return DepartmentCode ����R�[�h
	 */
	public String getDepartmentCode() {
		return departmentCode;
	}

	/**
	 * ����R�[�h�ݒ�
	 * 
	 * @param departmentCode ����R�[�h<br>
	 */
	public void setDepartmentCode(String departmentCode) {
		this.departmentCode = departmentCode;
	}

	/**
	 * ���[�U�o�^�t���O�K��
	 * 
	 * @return user ���[�U�o�^�t���O
	 */
	public boolean isUser() {
		return user;
	}

	/**
	 * ���[�U�o�^�t���O�ݒ�
	 * 
	 * @param user ���[�U�o�^�t���O<br>
	 *            true�ɐݒ莞�A���[�U�o�^�Ј������\��
	 */
	public void setUser(boolean user) {
		this.user = user;
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

		ctrl.termBasisDateFlag = false;
		ctrl.setEmployeeNotice();

		for (CallBackListener listener : callCtrlList) {
			listener.after();
		}
	}

	/**
	 * �Ј��J�n�擾
	 * 
	 * @return �Ј��J�n
	 */
	public String getBeginCode() {
		return beginCode;
	}

	/**
	 * �Ј��J�n�ݒ�
	 * 
	 * @param beginCode �Ј��J�n
	 */
	public void setBeginCode(String beginCode) {
		this.beginCode = beginCode;
	}

	/**
	 * �Ј��I���擾
	 * 
	 * @return �Ј��I��
	 */
	public String getEndCode() {
		return endCode;
	}

	/**
	 * �Ј��I���ݒ�
	 * 
	 * @param endCode �Ј��I��
	 */
	public void setEndCode(String endCode) {
		this.endCode = endCode;
	}
}
