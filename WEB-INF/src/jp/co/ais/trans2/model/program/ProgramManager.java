package jp.co.ais.trans2.model.program;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * �v���O�����}�l�[�W��
 * 
 * @author AIS
 */
public interface ProgramManager {

	/**
	 * �w������ɊY������v���O�����̌n��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������v���O�����̌n
	 * @throws TException
	 */
	public List<SystemizedProgram> getSystemizedProgram(ProgramSearchCondition condition) throws TException;

	/**
	 * �w������ɊY������v���O������Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������v���O����
	 * @throws TException
	 */
	public List<Program> get(ProgramSearchCondition condition) throws TException;

	/**
	 * �w������ɊY������V�X�e����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY������V�X�e��
	 * @throws TException
	 */
	public List<SystemClassification> getSystem(SystemClassificationSearchCondition condition) throws TException;

	/**
	 * �v���O������o�^����B
	 * 
	 * @param program �v���O����
	 * @throws TException
	 */
	public void entry(Program program) throws TException;

	/**
	 * �v���O�������C������B
	 * 
	 * @param program �v���O����
	 * @throws TException
	 */
	public void modify(Program program) throws TException;

	/**
	 * �v���O�������폜����B
	 * 
	 * @param program �v���O����
	 * @throws TException
	 */
	public void delete(Program program) throws TException;

	/**
	 * �v���O����(�G�N�Z��)��Ԃ�
	 * 
	 * @param condition ��������
	 * @return �v���O����
	 * @throws TException
	 */
	public byte[] getExcel(ProgramSearchCondition condition) throws TException;

}
