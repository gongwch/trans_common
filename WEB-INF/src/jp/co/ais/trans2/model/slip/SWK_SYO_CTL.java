package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �d�󏳔F�󋵃e�[�u��
 */
public class SWK_SYO_CTL extends TransferBase {

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** �`�[���t */
	protected Date SWK_DEN_DATE = null;

	/** �`�[�ԍ� */
	protected String SWK_DEN_NO = null;

	/** SEQ */
	protected int SEQ = -1;

	/** �X�V�敪 */
	protected int UPD_KBN = -1;

	/** �O��X�V�敪 */
	protected int PREV_UPD_KBN = -1;

	/** ���F���[���O���[�v�R�[�h */
	protected String APRV_ROLE_GRP_CODE = null;

	/** ���F���[���O���[�v���� */
	protected String aprvRoleGroupName = null;

	/** ���F���[���R�[�h */
	protected String APRV_ROLE_CODE = null;

	/** ���F���[���� */
	protected String aprvRoleName = null;

	/** ���F���[�����X�L�b�v�\�׸ނ� */
	protected boolean isAprvRoleSkippable = false;

	/** ���񏳔F���[���R�[�h */
	protected String NEXT_APRV_ROLE_CODE = null;

	/** ���񏳔F���[������ */
	protected String nextAprvRoleName = null;

	/** ���񏳔F���[�����X�L�b�v�\�׸ނ� */
	protected boolean isNextAprvRoleSkippable = false;

	/** �o�^���t */
	protected Date INP_DATE = null;

	/** �v���O�����h�c */
	protected String PRG_ID = null;

	/** ���[�U�[�h�c */
	protected String USR_ID = null;

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
	 * �`�[���t���擾����
	 * 
	 * @return �`�[���t
	 */
	public Date getSWK_DEN_DATE() {
		return SWK_DEN_DATE;
	}

	/**
	 * �`�[���t��ݒ肷��
	 * 
	 * @param SWK_DEN_DATE �`�[���t
	 */
	public void setSWK_DEN_DATE(Date SWK_DEN_DATE) {
		this.SWK_DEN_DATE = SWK_DEN_DATE;
	}

	/**
	 * �`�[�ԍ����擾����
	 * 
	 * @return �`�[�ԍ�
	 */
	public String getSWK_DEN_NO() {
		return SWK_DEN_NO;
	}

	/**
	 * �`�[�ԍ���ݒ肷��
	 * 
	 * @param SWK_DEN_NO �`�[�ԍ�
	 */
	public void setSWK_DEN_NO(String SWK_DEN_NO) {
		this.SWK_DEN_NO = SWK_DEN_NO;
	}

	/**
	 * SEQ���擾����
	 * 
	 * @return SEQ
	 */
	public int getSEQ() {
		return SEQ;
	}

	/**
	 * SEQ��ݒ肷��
	 * 
	 * @param SEQ SEQ
	 */
	public void setSEQ(int SEQ) {
		this.SEQ = SEQ;
	}

	/**
	 * �X�V�敪���擾����
	 * 
	 * @return �X�V�敪
	 */
	public int getUPD_KBN() {
		return UPD_KBN;
	}

	/**
	 * �X�V�敪��ݒ肷��
	 * 
	 * @param UPD_KBN �X�V�敪
	 */
	public void setUPD_KBN(int UPD_KBN) {
		this.UPD_KBN = UPD_KBN;
	}

	/**
	 * �O��X�V�敪���擾����
	 * 
	 * @return �O��X�V�敪
	 */
	public int getPREV_UPD_KBN() {
		return PREV_UPD_KBN;
	}

	/**
	 * �O��X�V�敪��ݒ肷��
	 * 
	 * @param PREV_UPD_KBN �O��X�V�敪
	 */
	public void setPREV_UPD_KBN(int PREV_UPD_KBN) {
		this.PREV_UPD_KBN = PREV_UPD_KBN;
	}

	/**
	 * ���F���[���O���[�v�R�[�h���擾
	 * 
	 * @return aPRV_ROLE_GRP_CODE
	 */
	public String getAPRV_ROLE_GRP_CODE() {
		return APRV_ROLE_GRP_CODE;
	}

	/**
	 * ���F���[���O���[�v�R�[�h���Z�b�g����
	 * 
	 * @param aPRV_ROLE_GRP_CODE aPRV_ROLE_GRP_CODE
	 */
	public void setAPRV_ROLE_GRP_CODE(String aPRV_ROLE_GRP_CODE) {
		APRV_ROLE_GRP_CODE = aPRV_ROLE_GRP_CODE;
	}

	/**
	 * ���F���[���R�[�h���擾����
	 * 
	 * @return ���F���[���R�[�h
	 */
	public String getAPRV_ROLE_CODE() {
		return APRV_ROLE_CODE;
	}

	/**
	 * ���F���[���R�[�h��ݒ肷��
	 * 
	 * @param APRV_ROLE_CODE ���F���[���R�[�h
	 */
	public void setAPRV_ROLE_CODE(String APRV_ROLE_CODE) {
		this.APRV_ROLE_CODE = APRV_ROLE_CODE;
	}

	/**
	 * ���񏳔F���[���R�[�h���擾����
	 * 
	 * @return ���񏳔F���[���R�[�h
	 */
	public String getNEXT_APRV_ROLE_CODE() {
		return NEXT_APRV_ROLE_CODE;
	}

	/**
	 * ���񏳔F���[���R�[�h��ݒ肷��
	 * 
	 * @param NEXT_APRV_ROLE_CODE ���񏳔F���[���R�[�h
	 */
	public void setNEXT_APRV_ROLE_CODE(String NEXT_APRV_ROLE_CODE) {
		this.NEXT_APRV_ROLE_CODE = NEXT_APRV_ROLE_CODE;
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
	 * ���F���[���O���[�v���̂��擾
	 * 
	 * @return aprvRoleGroupName
	 */
	public String getAprvRoleGroupName() {
		return aprvRoleGroupName;
	}

	/**
	 * ���F���[���O���[�v���̂��Z�b�g����
	 * 
	 * @param aprvRoleGroupName aprvRoleGroupName
	 */
	public void setAprvRoleGroupName(String aprvRoleGroupName) {
		this.aprvRoleGroupName = aprvRoleGroupName;
	}

	/**
	 * ���F���[�������擾
	 * 
	 * @return aprvRoleName
	 */
	public String getAprvRoleName() {
		return aprvRoleName;
	}

	/**
	 * ���F���[�������Z�b�g����
	 * 
	 * @param aprvRoleName aprvRoleName
	 */
	public void setAprvRoleName(String aprvRoleName) {
		this.aprvRoleName = aprvRoleName;
	}

	/**
	 * ���񏳔F���[�����̂��擾
	 * 
	 * @return nextAprvRoleName
	 */
	public String getNextAprvRoleName() {
		return nextAprvRoleName;
	}

	/**
	 * ���񏳔F���[�����̂��Z�b�g����
	 * 
	 * @param nextAprvRoleName nextAprvRoleName
	 */
	public void setNextAprvRoleName(String nextAprvRoleName) {
		this.nextAprvRoleName = nextAprvRoleName;
	}

	/**
	 * ���[�����X�L�b�v�\�׸ނ����擾
	 * 
	 * @return isAprvRoleSkippable
	 */
	public boolean isAprvRoleSkippable() {
		return isAprvRoleSkippable;
	}

	/**
	 * ���[�����X�L�b�v�\�׸ނ����Z�b�g����
	 * 
	 * @param isAprvRoleSkippable isAprvRoleSkippable
	 */
	public void setAprvRoleSkippable(boolean isAprvRoleSkippable) {
		this.isAprvRoleSkippable = isAprvRoleSkippable;
	}

	/**
	 * ���񏳔F���[�����X�L�b�v�\�׸ނ����擾
	 * 
	 * @return isNextAprvRoleSkippable
	 */
	public boolean isNextAprvRoleSkippable() {
		return isNextAprvRoleSkippable;
	}

	/**
	 * ���񏳔F���[�����X�L�b�v�\�׸ނ����Z�b�g����
	 * 
	 * @param isNextAprvRoleSkippable isNextAprvRoleSkippable
	 */
	public void setNextAprvRoleSkippable(boolean isNextAprvRoleSkippable) {
		this.isNextAprvRoleSkippable = isNextAprvRoleSkippable;
	}

}
