package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.bizui.ctrl.*;

/**
 * �Ǘ��}�X�^�����t�B�[���h�g�� �Ј��E����挟���ǉ�
 * 
 * @author ookawara
 */
public class TManagementEnhancingField extends TManagementField {

	/** �V���A��UID */
	protected static final long serialVersionUID = 1L;

	/** �����R�[�h */
	public static final int TYPE_CUSTOMER = 7;

	/** �Ј��R�[�h */
	public static final int TYPE_EMP = 8;

	protected boolean loanCustomer;

	/**
	 * �R���X�g���N�^
	 */
	public TManagementEnhancingField() {
		super(TYPE_MANAGEMENT1);

		super.ctrl = new TManagementEnhancingFieldCtrl(this);
	}

	/**
	 * �ؓ���\���t���O�擾
	 * 
	 * @return �ؓ���\���t���O
	 */
	public boolean isLoanCustomer() {
		return loanCustomer;
	}

	/**
	 * �ؓ���\���t���O�ݒ�
	 * 
	 * @param loanCustomer �ؓ���\���t���O
	 */
	public void setLoanCustomer(boolean loanCustomer) {
		this.loanCustomer = loanCustomer;
	}
}
