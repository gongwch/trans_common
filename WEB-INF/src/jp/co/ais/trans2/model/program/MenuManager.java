package jp.co.ais.trans2.model.program;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ���j���[�\���}�l�[�W��
 * 
 * @author AIS
 */
public interface MenuManager {

	/**
	 * �w������ɊY������v���O�����̌n��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������v���O�����̌n
	 * @throws TException
	 */
	public List<SystemizedProgram> getSystemizedProgram(MenuSearchCondition condition) throws TException;

	/**
	 * �w������ɊY������v���O�������̂�Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������v���O��������
	 * @throws TException
	 */
	public String getProgramName(MenuSearchCondition condition) throws TException;

	/**
	 * ���j���[�\����o�^����B
	 * 
	 * @param systemList ���j���[�\��
	 * @throws TException
	 */
	public void entry(List<SystemizedProgram> systemList) throws TException;

}
