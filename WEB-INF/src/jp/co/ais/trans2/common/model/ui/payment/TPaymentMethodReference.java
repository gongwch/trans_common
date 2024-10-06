package jp.co.ais.trans2.common.model.ui.payment;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.model.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * �x�����@�����R���|�[�l���g
 * 
 * @author AIS
 */
public class TPaymentMethodReference extends TReference {

	/** �R���g���[�� */
	protected TPaymentMethodReferenceController controller;

	/** ��s�����t�B�[���h�i�x�����@�����R�[�h �A���p�j */
	protected TBankAccountReference ctrlBankAccount = null;

	/** �G���[�x�����@�����R�[�h */
	protected PaymentKind[] errPaymentKinds = null;

	/**
	 * 
	 *
	 */
	public TPaymentMethodReference() {
		createController();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �^�C�v
	 */
	public TPaymentMethodReference(TYPE type) {
		super(type);
		createController();
	}

	/**
	 * �R���g���[���̍쐬
	 */
	protected void createController() {
		controller = new TPaymentMethodReferenceController(this);
	}

	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public PaymentMethodSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public PaymentMethod getEntity() {
		return controller.getEntity();
	}

	/**
	 * Entity���Z�b�g����
	 * 
	 * @param PaymentMethod �x�����@
	 */
	public void setEntity(PaymentMethod PaymentMethod) {
		controller.setEntity(PaymentMethod);
		controller.checkPaymentKind();
	}

	/**
	 * ��s�����̓��͂��K�v�Ȏx�����@���ǂ���
	 * 
	 * @return ��s�����̓��͂��K�v�Ȏx�����@���ǂ���
	 */
	public boolean isNeedInputAccount() {
		return controller.isNeedInputAccount();
	}

	/**
	 * ����������
	 */
	@Override
	public void clear() {
		super.clear();
		controller.checkPaymentKind();
	}

	/**
	 * ��s�����t�B�[���h��ݒ肷��B
	 * 
	 * @param ctrlBankAccount
	 */
	public void setBankAccountReference(TBankAccountReference ctrlBankAccount) {
		this.ctrlBankAccount = ctrlBankAccount;
	}

	/**
	 * �G���[�x�����@�����R�[�h��ݒ肷��B<br>
	 * ���͒l���w��̎x�����@�����R�[�h�̂����ꂩ�ƈ�v����ꍇ�A�A�g�����s�����t�B�[���h����͕s�ɂ���B
	 * 
	 * @param paymentKinds
	 */
	public void setErrPaymentKinds(PaymentKind... paymentKinds) {
		this.errPaymentKinds = paymentKinds;
	}

	/**
	 * �V���������ōČ������s���A�K���l�łȂ��ꍇ�̓N���A����.
	 */
	public void refleshEntity() {
		controller.refleshEntity();
	}

}
