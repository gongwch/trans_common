package jp.co.ais.trans2.model.user;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ���[�U�[�C���^�[�t�F�[�X�B
 * 
 * @author AIS
 */
public interface UserManager {

	/**
	 * �w������ɊY�����郆�[�U�[����Ԃ�
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����郆�[�U�[���
	 * @throws TException
	 */
	public List<User> get(UserSearchCondition condition) throws TException;

	/**
	 * �w��̉�ЃR�[�h�A���[�U�[�R�[�h�ɕR�t�����[�U�[����Ԃ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @return �w��̉�ЃR�[�h�A���[�U�[�R�[�h�ɕR�t�����[�U�[
	 * @throws TException
	 */
	public User get(String companyCode, String userCode) throws TException;

	/**
	 * �w��̉�ЃR�[�h�A���[�U�[�R�[�h�A�p�X���[�h�ɕR�t�����[�U�[����Ԃ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param password �p�X���[�h
	 * @return �w��̉�ЃR�[�h�A���[�U�[�R�[�h�A�p�X���[�h�ɕR�t�����[�U�[
	 * @throws TException
	 */
	public User get(String companyCode, String userCode, String password) throws TException;

	/**
	 * �v�����^���̂�Ԃ�(�v�����^�ݒ肪����ꍇ)
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @return �v�����^����
	 * @throws TException
	 */
	public String getPrinterName(String companyCode, String userCode) throws TException;

	/**
	 * ���[�U�[��o�^����B
	 * 
	 * @param user ���[�U�[
	 * @throws TException
	 */
	public void entry(User user) throws TException;

	/**
	 * ���[�U�[���C������B
	 * 
	 * @param user ���[�U�[
	 * @throws TException
	 */
	public void modify(User user) throws TException;

	/**
	 * ���[�U�[���폜����B
	 * 
	 * @param user ���[�U�[
	 * @throws TException
	 */
	public void delete(User user) throws TException;

	/**
	 * ���[�U�[��L&F��o�^����B
	 * 
	 * @param user ���[�U�[
	 * @throws TException
	 */
	public void entryLookandFeel(User user) throws TException;

	/**
	 * ���[�U�[�}�X�^�ꗗ���G�N�Z���`���ŕԂ�
	 * 
	 * @param condition ��������
	 * @return �G�N�Z���`���̃��[�U�[�}�X�^�ꗗ
	 * @throws TException
	 */
	public byte[] getExcel(UserSearchCondition condition) throws TException;

	/**
	 * �N���C�A���g�v�����^���̂��X�V����B
	 * 
	 * @param user �v�����^����
	 * @throws TException
	 */
	public void updatePrinter(User user) throws TException;

	/**
	 * �_�b�V���{�[�h�����R���g���[���ꗗ�擾
	 * 
	 * @param condition ��������
	 * @return �w������ɊY�����郆�[�U�[���
	 * @throws TException
	 */
	public List<USR_DASH_CTL> getDashBoardSysKbn(UserSearchCondition condition) throws TException;

}
