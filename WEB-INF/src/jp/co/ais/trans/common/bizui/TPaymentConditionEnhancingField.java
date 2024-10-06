package jp.co.ais.trans.common.bizui;

import java.util.*;

import jp.co.ais.trans.common.bizui.ctrl.*;

/**
 * �x�������t�B�[���h�g�� �f�t�H���g�̎x�������\���@�\��ǉ�
 * 
 * @author ookawara
 */

public class TPaymentConditionEnhancingField extends TPaymentConditionField {

	/** �V���A��UID */
	private static final long serialVersionUID = 1L;

	/** �ʉ݃R�[�h */
	private String curCode;

	/** �`�[���t */
	private Date slipDate;

	/** ���ʗpBEAN�N���X */
	private PaymentInformationParameter param;
	
	/** �L�������`�F�b�N�t���O */
	private boolean isTermBasisDate = true;

	/**
	 * �R���X�g���N�^
	 */
	public TPaymentConditionEnhancingField() {
		super();
		this.ctrl = new TPaymentConditionEnhancingFieldCtrl(this);
		super.ctrl = this.ctrl;
		param = new PaymentInformationParameter();

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
	 * �`�[���t��Ԃ�
	 * 
	 * @return slipDate �`�[���t
	 */
	public Date getSlipDate() {
		return slipDate;
	}

	/**
	 * �`�[���t��ݒ�
	 * 
	 * @param slipDate �`�[���t
	 */
	public void setSlipDate(Date slipDate) {
		this.slipDate = slipDate;
	}

	/**
	 * �f�t�H���g�̎x�����������擾���A�R�[�h�E���̂ɃZ�b�g
	 */
	public void setDefaultPaymentCondition() {
		((TPaymentConditionEnhancingFieldCtrl) this.ctrl).setDefaultPaymentCondition();
	}

	/**
	 * �����pBEAN�N���X���Z�b�g����B<BR>
	 * 
	 * @param param PaymentInformationParameter�N���X
	 */
	public void setParameter(PaymentInformationParameter param) {
		this.param = param;
	}

	/**
	 * �����pBEAN�N���X���擾����B<BR>
	 * 
	 * @return param PaymentInformationParameter�N���X
	 */
	public PaymentInformationParameter getParameter() {
		return this.param;
	}
	
	/**
	 * �L�������t���O��ݒ肷��B<BR>
	 * 
	 * @param isTermBasisDate ����t���O true:�L�������� false:�L�������O
	 * 
	 */
	public void setIsTermBasisDate(boolean isTermBasisDate) {
		this.isTermBasisDate = isTermBasisDate;
	}

	/**
	 * �L�������t���O��Ԃ��B<BR>
	 * 
	 * @return isTermBasisDate ����t���O true:�L�������� false:�L�������O
	 * 
	 */
	public boolean IsTermBasisDate() {
		return isTermBasisDate;
	}
}
