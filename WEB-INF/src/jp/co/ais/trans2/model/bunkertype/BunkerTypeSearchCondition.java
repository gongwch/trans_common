package jp.co.ais.trans2.model.bunkertype;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * ����}�X�^��������
 */
public class BunkerTypeSearchCondition extends TransferBase implements OPLoginCondition {

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** BNKR_TYPE_CODE */
	protected String BNKR_TYPE_CODE = null;

	/** BNKR_TYPE_ID */
	protected String BNKR_TYPE_ID = null;

	/** DISP_ODR */
	protected int DISP_ODR = 0;

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/** ���݌��� */
	protected int nowCount = 0;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return KAI_CODE ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param KAI_CODE ��ЃR�[�h
	 */
	public void setKAI_CODE(String KAI_CODE) {
		this.KAI_CODE = KAI_CODE;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		setKAI_CODE(companyCode);
	}

	/**
	 * BNKR_TYPE_CODE �̎擾
	 * 
	 * @return BNKR_TYPE_CODE
	 */
	public String getBNKR_TYPE_CODE() {
		return BNKR_TYPE_CODE;
	}

	/**
	 * BNKR_TYPE_CODE �̐ݒ�
	 * 
	 * @param BNKR_TYPE_CODE
	 */
	public void setBNKR_TYPE_CODE(String BNKR_TYPE_CODE) {
		this.BNKR_TYPE_CODE = BNKR_TYPE_CODE;
	}

	/**
	 * BNKR_TYPE_ID �̎擾
	 * 
	 * @return BNKR_TYPE_ID
	 */
	public String getBNKR_TYPE_ID() {
		return BNKR_TYPE_ID;
	}

	/**
	 * BNKR_TYPE_ID �̐ݒ�
	 * 
	 * @param BNKR_TYPE_ID
	 */
	public void setBNKR_TYPE_ID(String BNKR_TYPE_ID) {
		this.BNKR_TYPE_ID = BNKR_TYPE_ID;
	}

	/**
	 * DISP_ODR �̎擾
	 * 
	 * @return DISP_ODR
	 */
	public int getDISP_ODR() {
		return DISP_ODR;
	}

	/**
	 * DISP_ODR �̐ݒ�
	 * 
	 * @param DISP_ODR
	 */
	public void setDISP_ODR(int DISP_ODR) {
		this.DISP_ODR = DISP_ODR;
	}

	/**
	 * �ŏI�X�V�����̎擾
	 * 
	 * @return lastUpdateDate �ŏI�X�V����
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * �ŏI�X�V�����̐ݒ�
	 * 
	 * @param lastUpdateDate �ŏI�X�V����
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * ���݌����̎擾
	 * 
	 * @return nowCount ���݌���
	 */
	public int getNowCount() {
		return nowCount;
	}

	/**
	 * ���݌����̐ݒ�
	 * 
	 * @param nowCount ���݌���
	 */
	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

}
