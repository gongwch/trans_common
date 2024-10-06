package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.*;

/**
 * �v���O�����P�ʂ̔r���Ǘ�
 * 
 * @author AIS
 */
public interface ProgramExclusiveManager {

	/**
	 * �r������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @param programCode �v���O�����R�[�h
	 * @throws TException
	 */
	public void exclude(String companyCode, String userCode, String programCode) throws TException;

	/**
	 * �r������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @param programCode �v���O�����R�[�h
	 * @param processID ����ID
	 * @throws TException
	 */
	public void exclude(String companyCode, String userCode, String programCode, String processID) throws TException;

	/**
	 * �r����������
	 * 
	 * @throws TException
	 */
	public void cancelExclude() throws TException;

	/**
	 * �r����������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @param processID ����ID
	 * @throws TException
	 */
	public void cancelExclude(String companyCode, String userCode, String processID) throws TException;

}
