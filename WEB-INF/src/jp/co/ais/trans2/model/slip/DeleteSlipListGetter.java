package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans.common.except.*;

/**
 * �폜�`�[���X�g���o�C���^�[�t�F�[�X
 * 
 * @author AIS
 */
public interface DeleteSlipListGetter {

	/**
	 * �폜�`�[���X�g��ݒ肷��
	 * 
	 * @param condition
	 * @return SlipCondition
	 * @throws TException
	 */
	public DeleteSlipListBook get(SlipCondition condition) throws TException;

}
