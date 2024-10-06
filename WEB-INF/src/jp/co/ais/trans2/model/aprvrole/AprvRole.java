package jp.co.ais.trans2.model.aprvrole;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.model.user.*;

/**
 * ���F�������[���}�X�^
 */
public class AprvRole extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** ���F�������[���R�[�h */
	protected String APRV_ROLE_CODE = null;

	/** ���F�������[������ */
	protected String APRV_ROLE_NAME = null;

	/** ���F�������[������ */
	protected String APRV_ROLE_NAME_S = null;

	/** ���F�������[���������� */
	protected String APRV_ROLE_NAME_K = null;

	/** ���[�U�[�R�[�h */
	protected String USR_CODE = null;

	/** ���[�U�[���� */
	protected String USR_NAME_S = null;

	/** ���嗪�� */
	protected String DEP_NAME_S = null;

	/** �J�n�N���� */
	protected Date STR_DATE = null;

	/** �I���N���� */
	protected Date END_DATE = null;

	/** �o�^���t */
	protected Date INP_DATE = null;

	/** �X�V���t */
	protected Date UPD_DATE = null;

	/** �v���O�����h�c */
	protected String PRG_ID = null;

	/** ���[�U�[�h�c */
	protected String USR_ID = null;

	/** �����ێ����[�U�[���X�g */
	protected List<User> userList;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * ���F�������[���R�[�h�̎擾
	 * 
	 * @return APRV_ROLE_CODE ���F�������[���R�[�h
	 */
	public String getAPRV_ROLE_CODE() {
		return APRV_ROLE_CODE;
	}

	/**
	 * ���F�������[���R�[�h�̐ݒ�
	 * 
	 * @param APRV_ROLE_CODE ���F�������[���R�[�h
	 */
	public void setAPRV_ROLE_CODE(String APRV_ROLE_CODE) {
		this.APRV_ROLE_CODE = APRV_ROLE_CODE;
	}

	/**
	 * ���F�������[�����̂̎擾
	 * 
	 * @return APRV_ROLE_NAME ���F�������[������
	 */
	public String getAPRV_ROLE_NAME() {
		return APRV_ROLE_NAME;
	}

	/**
	 * ���F�������[�����̂̐ݒ�
	 * 
	 * @param APRV_ROLE_NAME ���F�������[������
	 */
	public void setAPRV_ROLE_NAME(String APRV_ROLE_NAME) {
		this.APRV_ROLE_NAME = APRV_ROLE_NAME;
	}

	/**
	 * ���F�������[�����̂̎擾
	 * 
	 * @return APRV_ROLE_NAME_S ���F�������[������
	 */
	public String getAPRV_ROLE_NAME_S() {
		return APRV_ROLE_NAME_S;
	}

	/**
	 * ���F�������[�����̂̐ݒ�
	 * 
	 * @param APRV_ROLE_NAME_S ���F�������[������
	 */
	public void setAPRV_ROLE_NAME_S(String APRV_ROLE_NAME_S) {
		this.APRV_ROLE_NAME_S = APRV_ROLE_NAME_S;
	}

	/**
	 * ���F�������[���������̂̎擾
	 * 
	 * @return APRV_ROLE_NAME_K ���F�������[����������
	 */
	public String getAPRV_ROLE_NAME_K() {
		return APRV_ROLE_NAME_K;
	}

	/**
	 * ���F�������[���������̂̐ݒ�
	 * 
	 * @param APRV_ROLE_NAME_K ���F�������[����������
	 */
	public void setAPRV_ROLE_NAME_K(String APRV_ROLE_NAME_K) {
		this.APRV_ROLE_NAME_K = APRV_ROLE_NAME_K;
	}

	/**
	 * ���[�U�[�R�[�h�̎擾
	 * 
	 * @return USR_CODE ���[�U�[�R�[�h
	 */
	public String getUSR_CODE() {
		return USR_CODE;
	}

	/**
	 * ���[�U�[�R�[�h�̐ݒ�
	 * 
	 * @param USR_CODE ���[�U�[�R�[�h
	 */
	public void setUSR_CODE(String USR_CODE) {
		this.USR_CODE = USR_CODE;
	}

	/**
	 * ���[�U�[���̂̎擾
	 * 
	 * @return USR_NAME_S ���[�U�[����
	 */
	public String getUSR_NAME_S() {
		return USR_NAME_S;
	}

	/**
	 * ���[�U�[���̂̐ݒ�
	 * 
	 * @param USR_NAME_S ���[�U�[����
	 */
	public void setUSR_NAME_S(String USR_NAME_S) {
		this.USR_NAME_S = USR_NAME_S;
	}

	/**
	 * ���嗪�̂̎擾
	 * 
	 * @return DEP_NAME_S ���嗪��
	 */
	public String getDEP_NAME_S() {
		return DEP_NAME_S;
	}

	/**
	 * ���嗪�̂̐ݒ�
	 * 
	 * @param DEP_NAME_S ���嗪��
	 */
	public void setDEP_NAME_S(String DEP_NAME_S) {
		this.DEP_NAME_S = DEP_NAME_S;
	}

	/**
	 * �J�n�N�����̎擾
	 * 
	 * @return STR_DATE �J�n�N����
	 */
	public Date getSTR_DATE() {
		return STR_DATE;
	}

	/**
	 * �J�n�N�����̐ݒ�
	 * 
	 * @param STR_DATE �J�n�N����
	 */
	public void setSTR_DATE(Date STR_DATE) {
		this.STR_DATE = STR_DATE;
	}

	/**
	 * �I���N�����̎擾
	 * 
	 * @return END_DATE �I���N����
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}

	/**
	 * �I���N�����̐ݒ�
	 * 
	 * @param END_DATE �I���N����
	 */
	public void setEND_DATE(Date END_DATE) {
		this.END_DATE = END_DATE;
	}

	/**
	 * �o�^���t�̎擾
	 * 
	 * @return INP_DATE �o�^���t
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * �o�^���t�̐ݒ�
	 * 
	 * @param INP_DATE �o�^���t
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * �X�V���t�̎擾
	 * 
	 * @return UPD_DATE �X�V���t
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * �X�V���t�̐ݒ�
	 * 
	 * @param UPD_DATE �X�V���t
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * �v���O�����h�c�̎擾
	 * 
	 * @return PRG_ID �v���O�����h�c
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * �v���O�����h�c�̐ݒ�
	 * 
	 * @param PRG_ID �v���O�����h�c
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * ���[�U�[�h�c�̎擾
	 * 
	 * @return USR_ID ���[�U�[�h�c
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ���[�U�[�h�c�̐ݒ�
	 * 
	 * @param USR_ID ���[�U�[�h�c
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * �����ێ����[�U�[���X�g���擾
	 * 
	 * @return userList
	 */
	public List<User> getUserList() {
		return userList;
	}

	/**
	 * �����ێ����[�U�[���X�g���Z�b�g����
	 * 
	 * @param userList userList
	 */
	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

	/**
	 * �����ێ����[�U�[���X�g�ɒǉ�
	 * 
	 * @param usr
	 */
	public void addUser(User usr) {
		if (this.userList == null) {
			userList = new ArrayList();
		}
		this.userList.add(usr);
	}

}
