package jp.co.ais.trans.logic.system;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * ���s���O�ꗗ�r�W�l�X���W�b�N
 * 
 * @author roh
 */
public interface ExecutedLogLogic {

	/**
	 * ��ЃR�[�h�ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	void setCompanyCode(String companyCode);

	/**
	 * �J�n���t�ݒ�
	 * 
	 * @param startDate �J�n���t
	 */
	void setStartDate(Date startDate);

	/**
	 * �I�����t�ݒ�
	 * 
	 * @param endDate �I�����t
	 */
	void setEndDate(Date endDate);

	/**
	 * �J�n�v���O�����R�[�h�ݒ�
	 * 
	 * @param startPrg �J�n�v���O�����R�[�h
	 */
	void setStartProgramCode(String startPrg);

	/**
	 * �I���v���O�����R�[�h�ݒ�
	 * 
	 * @param endPrg �I���v���O�����R�[�h
	 */
	void setEndProgramCode(String endPrg);

	/**
	 * �J�n���[�U�R�[�h�ݒ�
	 * 
	 * @param userCode �J�n���[�U�R�[�h
	 */
	void setStartUserCode(String userCode);

	/**
	 * �I�����[�U�R�[�h�ݒ�
	 * 
	 * @param endUser �I�����[�U�R�[�h
	 */
	void setEndUserCode(String endUser);

	/**
	 * ���O�C���A�E�g�Ώېݒ�
	 */
	void onLogin();

	/**
	 * ���O�C���A�E�g��Ώېݒ�
	 */
	void offLogin();

	/**
	 * ���s���O�ꗗ���擾
	 * 
	 * @return List ���s���O�ꗗ
	 */
	List<ExecutedLog> getExecutedLogList();

	/**
	 * ���t�Ń\�[�g
	 * 
	 * @param list �Ώۃ��X�g
	 */
	void sortByDate(List<ExecutedLog> list);

	/**
	 * ���[�U�R�[�h�Ń\�[�g
	 * 
	 * @param list �Ώۃ��X�g
	 */
	void sortByUserCode(List<ExecutedLog> list);

	/**
	 * �v���O�����R�[�h�Ń\�[�g
	 * 
	 * @param list �Ώۃ��X�g
	 */
	void sortByProgramCode(List<ExecutedLog> list);

	/**
	 * ����R�[�h�ݒ�
	 * 
	 * @param langCode
	 */
	void setLangCode(String langCode);

	/**
	 * sort�ݒ�
	 * 
	 * @param sort
	 */
	void setSort(int sort);

	/**
	 * ���O���s
	 * 
	 * @param userCode ���[�U�R�[�h
	 * @param userName ���[�U����
	 * @param ipAddress IP�A�h���X
	 * @param prgCode �v���O����ID
	 * @param state ���
	 */
	public void log(String userCode, String userName, String ipAddress, String prgCode, String state);

	/**
	 * ���O�C�����O���s
	 * 
	 * @param userCode ���[�U�R�[�h
	 * @param userName ���[�U����
	 * @param ipAddress IP�A�h���X
	 */
	public void logLogin(String userCode, String userName, String ipAddress);

	/**
	 * ���O�A�E�g���O���s
	 * 
	 * @param userCode ���[�U�R�[�h
	 * @param userName ���[�U����
	 * @param ipAddress IP�A�h���X
	 */
	public void logLogout(String userCode, String userName, String ipAddress);
}
