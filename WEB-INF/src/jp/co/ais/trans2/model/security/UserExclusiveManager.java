package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.*;

/**
 * ���O�C�����[�U����(PCI_CHK_CTL�e�[�u��)�}�l�[�W�� �C���^�[�t�F�[�X
 */

public interface UserExclusiveManager {

	/**
	 * ���O�C���\���ǂ���
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @return true:�\
	 */
	public boolean canEntry(String companyCode, String userCode);

	/**
	 * �r������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 */
	public void exclude(String companyCode, String userCode);

	/**
	 * �r����������
	 */
	public void cancelExclude();

	/**
	 * �r����������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 */
	public void cancelExclude(String companyCode, String userCode);

	/**
	 * ���O�C���\���ǂ���
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param password �p�X���[�h
	 * @return true:�\
	 * @throws TException
	 */
	public UserExclusive canEntry(String companyCode, String userCode, String password) throws TException;

}
