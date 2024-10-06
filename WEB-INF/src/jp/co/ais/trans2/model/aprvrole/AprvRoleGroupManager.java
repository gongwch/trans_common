package jp.co.ais.trans2.model.aprvrole;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ���F�������[���C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface AprvRoleGroupManager {

	/**
	 * �w������ɊY���������Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������������
	 * @throws TException
	 */
	public List<AprvRoleGroup> get(AprvRoleGroupSearchCondition condition) throws TException;

	/**
	 * ����o�^����B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(AprvRoleGroup bean) throws TException;

	/**
	 * �����C������B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modify(AprvRoleGroup bean) throws TException;

	/**
	 * �����폜����B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(AprvRoleGroup bean) throws TException;

	/**
	 * �G�N�Z�����o�͂���B
	 * 
	 * @param condition
	 * @return �G�N�Z���o�C�i��
	 * @throws TException
	 */
	public byte[] getExcel(AprvRoleGroupSearchCondition condition) throws TException;

}
