package jp.co.ais.trans2.model.userunlock;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ���O�C���������C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface UserUnLockManager {

	/**
	 * �w������ɊY�����郍�O�C����������Ԃ�
	 * 
	 * @return �w������ɊY�����郍�O�C���������
	 * @throws TException
	 */
	public List<UserUnLock> get() throws TException;

	/**
	 * ���O�C�����������폜����B�i�����Łj
	 * 
	 * @param list
	 * @throws TException
	 */
	public void delete(List<UserUnLock> list) throws TException;

	/**
	 * ���O�C�����������폜����B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(UserUnLock bean) throws TException;
}
