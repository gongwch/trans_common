package jp.co.ais.trans2.model.security;

import java.util.List;
import jp.co.ais.trans.common.except.TException;

/**
 * �v���O�������[���}�l�[�W��
 * 
 * @author AIS
 */
public interface ProgramRoleManager {

	/**
	 * �w������ɊY������f�[�^��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �Ώۃf�[�^���X�g
	 * @throws TException
	 */
	public List<ProgramRole> get(ProgramRoleSearchCondition condition) throws TException;

	/**
	 * �o�^����
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(ProgramRole bean) throws TException;

	/**
	 * �C������
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void modify(ProgramRole bean) throws TException;

	/**
	 * �폜����
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void delete(ProgramRole bean) throws TException;

	/**
	 * �v���O�������[��(�G�N�Z��)��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �Ώۃf�[�^���X�g
	 * @throws TException
	 */
	public byte[] getExcel(ProgramRoleSearchCondition condition) throws TException;

}
