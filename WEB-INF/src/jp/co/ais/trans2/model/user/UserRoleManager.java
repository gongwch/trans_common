package jp.co.ais.trans2.model.user;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ���[�U�[���[���}�l�[�W��
 * 
 * @author AIS
 */
public interface UserRoleManager {

	/**
	 * �w������ɊY������f�[�^��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �Ώۃf�[�^���X�g
	 * @throws TException
	 */
	public List<UserRole> get(UserSearchCondition condition) throws TException;

	/**
	 * �o�^����
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(UserRole bean) throws TException;

	/**
	 * �C������
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modify(UserRole bean) throws TException;

	/**
	 * �폜����
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(UserRole bean) throws TException;

	/**
	 * ���[�U�[���[��(�G�N�Z��)��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �Ώۃf�[�^���X�g
	 * @throws TException
	 */
	public byte[] getExcel(UserSearchCondition condition) throws TException;

}
