package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �`�[�r�������}�X�^
 */
public class SlipUnLock extends TransferBase {

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** �V�X�e���敪 */
	protected String SWK_SYS_KBN = null;

	/** �V�X�e���敪���� */
	protected String SYS_NAME = null;

	/** �`�[�ԍ� */
	protected String SWK_DEN_NO = null;

	/** �`�[��� */
	protected String SWK_DEN_SYU = null;

	/** �`�[��ʖ��� */
	protected String DEN_SYU_NAME = null;

	/** �`�[���t */
	protected Date SWK_DEN_DATE = null;

	/** �`�[�E�v */
	protected String SWK_TEK = null;

	/** �r�����[�U */
	protected String USR_ID = null;

	/** �r�����[�U���� */
	protected String USR_NAME = null;

	/** �r�����s���� */
	protected Date UPD_DATE = null;

	/** �r�����s���� */
	protected int flag = -1;

	/**
	 * @return ��ЃR�[�h��߂��܂��B
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * @param KAI_CODE ��ЃR�[�h��ݒ肵�܂��B
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * @return �V�X�e���敪��߂��܂��B
	 */
	public String getSWK_SYS_KBN() {
		return SWK_SYS_KBN;
	}

	/**
	 * @param SWK_SYS_KBN �V�X�e���敪��ݒ肵�܂��B
	 */
	public void setSWK_SYS_KBN(String SWK_SYS_KBN) {
		this.SWK_SYS_KBN = SWK_SYS_KBN;
	}

	/**
	 * @return �V�X�e���敪���̂�߂��܂��B
	 */
	public String getSYS_NAME() {
		return SYS_NAME;
	}

	/**
	 * @param SYS_NAME �V�X�e���敪���̂�ݒ肵�܂��B
	 */
	public void setSYS_NAME(String SYS_NAME) {
		this.SYS_NAME = SYS_NAME;
	}

	/**
	 * @return �`�[�ԍ���߂��܂��B
	 */
	public String getSWK_DEN_NO() {
		return SWK_DEN_NO;
	}

	/**
	 * @param SWK_DEN_NO �`�[�ԍ���ݒ肵�܂��B
	 */
	public void setSWK_DEN_NO(String SWK_DEN_NO) {
		this.SWK_DEN_NO = SWK_DEN_NO;
	}

	/**
	 * @return �`�[��ʂ�߂��܂��B
	 */
	public String getSWK_DEN_SYU() {
		return SWK_DEN_SYU;
	}

	/**
	 * @param SWK_DEN_SYU �`�[��ʂ�ݒ肵�܂��B
	 */
	public void setSWK_DEN_SYU(String SWK_DEN_SYU) {
		this.SWK_DEN_SYU = SWK_DEN_SYU;
	}

	/**
	 * @return �`�[��ʖ��̂�߂��܂��B
	 */
	public String getDEN_SYU_NAME() {
		return DEN_SYU_NAME;
	}

	/**
	 * @param DEN_SYU_NAME �`�[��ʖ��̂�ݒ肵�܂��B
	 */
	public void setDEN_SYU_NAME(String DEN_SYU_NAME) {
		this.DEN_SYU_NAME = DEN_SYU_NAME;
	}

	/**
	 * @return �`�[���t��߂��܂��B
	 */
	public Date getSWK_DEN_DATE() {
		return SWK_DEN_DATE;
	}

	/**
	 * @param SWK_DEN_DATE �`�[���t��ݒ肵�܂��B
	 */
	public void setSWK_DEN_DATE(Date SWK_DEN_DATE) {
		this.SWK_DEN_DATE = SWK_DEN_DATE;
	}

	/**
	 * @return �d��E�v��߂��܂��B
	 */
	public String getSWK_TEK() {
		return SWK_TEK;
	}

	/**
	 * @param SWK_TEK �d��E�v��ݒ肵�܂��B
	 */
	public void setSWK_TEK(String SWK_TEK) {
		this.SWK_TEK = SWK_TEK;
	}

	/**
	 * @return �r�����[�U��߂��܂��B
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * @param USR_ID �r�����[�U��ݒ肵�܂��B
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * @return �r�����[�U���̂�߂��܂��B
	 */
	public String getUSR_NAME() {
		return USR_NAME;
	}

	/**
	 * @param USR_NAME �r�����[�U���̂�ݒ肵�܂��B
	 */
	public void setUSR_NAME(String USR_NAME) {
		this.USR_NAME = USR_NAME;
	}

	/**
	 * �r�����s���t
	 * 
	 * @return �r�����s���t
	 */
	public Date getUPD_DATE() {
		return UPD_DATE;
	}

	/**
	 * �r�����s���t
	 * 
	 * @param UPD_DATE �r�����s���t��ݒ肵�܂��B
	 */
	public void setUPD_DATE(Date UPD_DATE) {
		this.UPD_DATE = UPD_DATE;

	}

	/**
	 * �t���O
	 * 
	 * @return flag
	 */
	public int getFlag() {
		return flag;
	}

	/**
	 * �t���O
	 * 
	 * @param flag ��ݒ肵�܂��B
	 */
	public void setFlag(int flag) {
		this.flag = flag;
	}
}
