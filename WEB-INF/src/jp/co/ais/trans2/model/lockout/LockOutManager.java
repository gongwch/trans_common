package jp.co.ais.trans2.model.lockout;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * MG0027 - ���b�N�A�E�g�Ǘ�
 * 
 * @author AIS
 */
public interface LockOutManager {

	/**
	 * ��񌟍� (SELECT)
	 * 
	 * @return ���b�N�A�E�g�Ǘ��}�X�^���
	 * @throws TException
	 */
	public List<LockOut> get() throws TException;

	/**
	 * ���폜 (DELETE)
	 * 
	 * @param lockoutList �I�����
	 * @throws TException
	 */
	public void delete(List<LockOut> lockoutList) throws TException;

}