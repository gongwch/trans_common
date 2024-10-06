package jp.co.ais.trans2.model.aprvrole;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * ���F�������[���O���[�v�G���e�B�e�B
 */
public class AprvRoleGroup extends TransferBase {

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** ���[���O���[�v�R�[�h */
	protected String APRV_ROLE_GRP_CODE = null;

	/** ���[���O���[�v���� */
	protected String APRV_ROLE_GRP_NAME = null;

	/** ���[���O���[�v���� */
	protected String APRV_ROLE_GRP_NAME_S = null;

	/** ���[���O���[�v�������� */
	protected String APRV_ROLE_GRP_NAME_K = null;

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

	/** ���׃��X�g */
	protected List<AprvRoleGroupDetail> detailList = null;

	/**
	 * ��ЃR�[�h���擾����
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * ��ЃR�[�h��ݒ肷��
	 * 
	 * @param KAI_CODE ��ЃR�[�h
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * ���[���O���[�v�R�[�h���擾����
	 * 
	 * @return ���[���O���[�v�R�[�h
	 */
	public String getAPRV_ROLE_GRP_CODE() {
		return APRV_ROLE_GRP_CODE;
	}

	/**
	 * ���[���O���[�v�R�[�h��ݒ肷��
	 * 
	 * @param APRV_ROLE_GRP_CODE ���[���O���[�v�R�[�h
	 */
	public void setAPRV_ROLE_GRP_CODE(String APRV_ROLE_GRP_CODE) {
		this.APRV_ROLE_GRP_CODE = APRV_ROLE_GRP_CODE;
	}

	/**
	 * ���[���O���[�v���̂��擾����
	 * 
	 * @return ���[���O���[�v����
	 */
	public String getAPRV_ROLE_GRP_NAME() {
		return APRV_ROLE_GRP_NAME;
	}

	/**
	 * ���[���O���[�v���̂�ݒ肷��
	 * 
	 * @param APRV_ROLE_GRP_NAME ���[���O���[�v����
	 */
	public void setAPRV_ROLE_GRP_NAME(String APRV_ROLE_GRP_NAME) {
		this.APRV_ROLE_GRP_NAME = APRV_ROLE_GRP_NAME;
	}

	/**
	 * ���[���O���[�v���̂��擾����
	 * 
	 * @return ���[���O���[�v����
	 */
	public String getAPRV_ROLE_GRP_NAME_S() {
		return APRV_ROLE_GRP_NAME_S;
	}

	/**
	 * ���[���O���[�v���̂�ݒ肷��
	 * 
	 * @param APRV_ROLE_GRP_NAME_S ���[���O���[�v����
	 */
	public void setAPRV_ROLE_GRP_NAME_S(String APRV_ROLE_GRP_NAME_S) {
		this.APRV_ROLE_GRP_NAME_S = APRV_ROLE_GRP_NAME_S;
	}

	/**
	 * ���[���O���[�v�������̂��擾����
	 * 
	 * @return ���[���O���[�v��������
	 */
	public String getAPRV_ROLE_GRP_NAME_K() {
		return APRV_ROLE_GRP_NAME_K;
	}

	/**
	 * ���[���O���[�v�������̂�ݒ肷��
	 * 
	 * @param APRV_ROLE_GRP_NAME_K ���[���O���[�v��������
	 */
	public void setAPRV_ROLE_GRP_NAME_K(String APRV_ROLE_GRP_NAME_K) {
		this.APRV_ROLE_GRP_NAME_K = APRV_ROLE_GRP_NAME_K;
	}

	/**
	 * �J�n�N�������擾����
	 * 
	 * @return �J�n�N����
	 */
	public Date getSTR_DATE() {
		return STR_DATE;
	}

	/**
	 * �J�n�N������ݒ肷��
	 * 
	 * @param STR_DATE �J�n�N����
	 */
	public void setSTR_DATE(Date STR_DATE) {
		this.STR_DATE = STR_DATE;
	}

	/**
	 * �I���N�������擾����
	 * 
	 * @return �I���N����
	 */
	public Date getEND_DATE() {
		return END_DATE;
	}

	/**
	 * �I���N������ݒ肷��
	 * 
	 * @param END_DATE �I���N����
	 */
	public void setEND_DATE(Date END_DATE) {
		this.END_DATE = END_DATE;
	}

	/**
	 * �o�^���t���擾����
	 * 
	 * @return �o�^���t
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * �o�^���t��ݒ肷��
	 * 
	 * @param INP_DATE �o�^���t
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * �X�V���t���擾����
	 * 
	 * @return �X�V���t
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * �X�V���t��ݒ肷��
	 * 
	 * @param UPD_DATE �X�V���t
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;
	}

	/**
	 * �v���O�����h�c���擾����
	 * 
	 * @return �v���O�����h�c
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * �v���O�����h�c��ݒ肷��
	 * 
	 * @param PRG_ID �v���O�����h�c
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
	}

	/**
	 * ���[�U�[�h�c���擾����
	 * 
	 * @return ���[�U�[�h�c
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ���[�U�[�h�c��ݒ肷��
	 * 
	 * @param USR_ID ���[�U�[�h�c
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * ���׃��X�g���擾
	 * 
	 * @return detailList
	 */
	public List<AprvRoleGroupDetail> getDetailList() {
		return detailList;
	}

	/**
	 * ���׃��X�g���Z�b�g����
	 * 
	 * @param detailList detailList
	 */
	public void setDetailList(List<AprvRoleGroupDetail> detailList) {
		this.detailList = detailList;
	}

	/**
	 * ���׃��X�g�ɖ��ׂ�ǉ�
	 * 
	 * @param detail
	 */
	public void addDetailList(AprvRoleGroupDetail detail) {
		if (detailList == null) {
			detailList = new ArrayList();
		}
		detailList.add(detail);
	}

	/**
	 * �擪�̏��F���[���R�[�h�擾
	 * 
	 * @return �擪�̏��F���[���R�[�h�擾
	 */
	public String getFirstRoleCode() {
		if (detailList == null || detailList.isEmpty()) {
			return null;
		}
		String code = null;
		int minLevel = 9;
		for (AprvRoleGroupDetail d : detailList) {
			if (code == null) {
				code = d.getAPRV_ROLE_CODE();
				minLevel = d.getAPRV_LEVEL();
			}
			if (minLevel > d.getAPRV_LEVEL()) {
				code = d.getAPRV_ROLE_CODE();
				minLevel = d.getAPRV_LEVEL();
			}
		}
		return code;
	}

	/**
	 * �ŏI�̏��F���[���R�[�h�擾
	 * 
	 * @return �ŏI�̏��F���[���R�[�h�擾
	 */
	public String getLastRoleCode() {
		if (detailList == null || detailList.isEmpty()) {
			return null;
		}
		String code = null;
		int maxLevel = 0;
		for (AprvRoleGroupDetail d : detailList) {
			if (code == null) {
				code = d.getAPRV_ROLE_CODE();
				maxLevel = d.getAPRV_LEVEL();
			}
			if (maxLevel < d.getAPRV_LEVEL()) {
				code = d.getAPRV_ROLE_CODE();
				maxLevel = d.getAPRV_LEVEL();
			}
		}
		return code;
	}
}
