package jp.co.ais.trans2.model.aprvrole;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ���F�������[���C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface AprvRoleManager {

	/**
	 * �w������ɊY���������Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������������
	 * @throws TException
	 */
	public List<AprvRole> get(AprvRoleSearchCondition condition) throws TException;

	/**
	 * ����o�^����B
	 * 
	 * @param usrList
	 * @throws TException
	 */
	public void entry(List<AprvRole> usrList) throws TException;

	/**
	 * �����C������B
	 * 
	 * @param usrList
	 * @throws TException
	 */
	public void modify(List<AprvRole> usrList) throws TException;

	/**
	 * �����폜����B
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(AprvRole bean) throws TException;

	/**
	 * �G�N�Z�����o�͂���B
	 * 
	 * @param condition
	 * @return �G�N�Z���o�C�i��
	 * @throws TException
	 */
	public byte[] getExcel(AprvRoleSearchCondition condition) throws TException;

}
