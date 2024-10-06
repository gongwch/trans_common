package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * �V�X�e���}�l�[�W��
 */
public interface SystemManager {

	/**
	 * ���[�U�G���g��(���O�C��)
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @throws TException
	 */
	public void entryUser(String companyCode, String userCode) throws TException;

	/**
	 * �w�肳���v���O�������J���邩�ǂ���(���j���[�N���b�N�^�C�~���O)
	 * 
	 * @param log ���O���
	 * @return true:�J����
	 * @throws TException
	 */
	public boolean canOpenProgram(Log log) throws TException;

	/**
	 * �w�肳���v���O�������J��(���j���[�N���b�N�^�C�~���O)
	 * 
	 * @param log ���O���
	 * @throws TException
	 */
	public void openProgram(Log log) throws TException;

	/**
	 * �w�肳��郆�[�U���J���Ă�v���O���������(�^�u����A�~����)
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @param programCode �v���O�����R�[�h
	 * @throws TException
	 */
	public void closeProgram(String companyCode, String userCode, String programCode) throws TException;

	/**
	 * �w�肳��郆�[�U���J���Ă�S�ăv���O���������
	 * 
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�R�[�h
	 * @throws TException
	 */
	public void closeAllProgram(String companyCode, String userCode) throws TException;

	/**
	 * OP�L���b�V�����̎擾
	 * 
	 * @param param
	 * @return OP�L���b�V�����
	 * @throws TException
	 */
	public OPLoginInfo getOPCache(OPLoginInfo param) throws TException;

}
