package jp.co.ais.trans2.model.check;

import jp.co.ais.trans.common.except.*;

/**
 * �}�X�^�g�p�ς݃`�F�b�N
 */
public interface CheckMasterUseDao {

	/**
	 * �}�X�^�g�p�ς݃`�F�b�N
	 * 
	 * @param condition �`�F�b�N����
	 * @throws TException
	 */
	public void check(CheckCondition condition) throws TException;
}
