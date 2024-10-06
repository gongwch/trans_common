package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * ���ێ��x�����R���|�[�l���g
 * 
 * @author AIS
 */
public class TBalanceAccountsReference extends TReference {

	/** �R���g���[�� */
	protected TBalanceAccountsReferenceController controller;

	/**
	 * �R���X�g���N�^
	 */
	public TBalanceAccountsReference() {
		this.controller = new TBalanceAccountsReferenceController(this);
		this.resize();
	}

	/**
	 * �R�[�h�̒�����Ԃ��B�f�t�H���g���ƈقȂ錟���t�B�[���h�� ���Y���\�b�h��Override����B
	 * 
	 * @return �R�[�h��
	 */
	@Override
	protected int getCodeLength() {
		return 4;
	}

	/**
	 * ����������Ԃ�
	 * 
	 * @return ��������
	 */
	public RemittanceSearchCondition getSearchCondition() {
		return controller.getCondition();
	}

	/**
	 * �I�����ꂽEntity��Ԃ�
	 * 
	 * @return �I�����ꂽEntity
	 */
	public Remittance getEntity() {
		return controller.getEntity();
	}

	@Override
	protected TReferenceController getController() {
		return controller;
	}

	/**
	 * Entity�Z�b�g
	 * 
	 * @param remittance Entity
	 */
	public void setEntity(Remittance remittance) {
		controller.setEntity(remittance);
	}
}
