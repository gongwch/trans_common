package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans2.common.ledger.*;

/**
 * �폜�`�[���X�g
 * 
 * @author AIS
 */
public class DeleteSlipListBook extends LedgerBook {

	/** �������� */
	protected SlipCondition condition = null;
	
	/**
	 * �^�C�g���ݒ�
	 */
	public DeleteSlipListBook() {
		setTitle("C01610"); // �폜�`�[���X�g
	}

	/**
	 * �폜�`�[���X�g�̒��o����
	 * 
	 * @return SlipCondition
	 */
	public SlipCondition getCondition() {
		return condition;
	}

	/**
	 * �폜�`�[���X�g�̐ݒ肷��
	 * 
	 * @param condition
	 */
	public void setCondition(SlipCondition condition) {
		this.condition = condition;
	}
	
	/**
	 * �폜���[�U�[�̕\���敪���擾����B
	 * 
	 * @return �\���敪
	 */

	public boolean isShowUserInfo() {
		if (condition != null && condition instanceof DeleteSlipListCondition) {
			return ((DeleteSlipListCondition) condition).isShowUserInfo();
		} else {
		    return false;
		}
	}
	
}
