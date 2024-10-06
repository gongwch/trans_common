package jp.co.ais.trans.common.bizui;

import jp.co.ais.trans.common.bizui.ctrl.*;

/**
 * ��s�����t�B�[���h�g�� �f�t�H���g�̓��������Z�b�g�@�\��ǉ�
 * 
 * @author ookawara
 */
public class TBankAccountEnhancingField extends TBankAccountField {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	/** �ʉ݃R�[�h */
	private String curCode;

	/** ���۰كN���X */
	private TBankAccountEnhancingFieldCtrl fieldCtrl;

	/**
	 * �R���X�g���N�^
	 */
	public TBankAccountEnhancingField() {
		super();
		this.fieldCtrl = new TBankAccountEnhancingFieldCtrl(this);
		super.ctrl = this.fieldCtrl;
	}

	/**
	 * �ʉ݃R�[�h��ݒ�
	 * 
	 * @param curCode
	 */
	public void setCurrencyCode(String curCode) {
		this.curCode = curCode;
	}

	/**
	 * �ʉ݃R�[�h��Ԃ�
	 * 
	 * @return curCode �ʉ݃R�[�h
	 */
	public String getCurrencyCode() {
		return this.curCode;
	}

	/**
	 * �f�t�H���g�̎x�����������擾���A�R�[�h�E���̂ɃZ�b�g
	 */
	public void setDefaultReceivedAccount() {
		this.fieldCtrl.setDefaultReceivedAccount();
	}
}
