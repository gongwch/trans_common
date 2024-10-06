package jp.co.ais.trans2.model.code;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * �R�[�h�}�X�^��������
 */
public class CodeSearchCondition extends TransferBase implements OPLoginCondition {

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** �R�[�h�敪 */
	protected String CODE_DIV = null;

	/** �R�[�h */
	protected String CODE = null;

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/** ���݌��� */
	protected int nowCount = 0;

	/** true:���q���[�h�Afalse:�O�q���[�h */
	protected Boolean local = null;

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
	 * �R�[�h�敪�̎擾
	 * 
	 * @return CODE_DIV �R�[�h�敪
	 */
	public String getCODE_DIV() {
		return CODE_DIV;
	}

	/**
	 * �R�[�h�敪�̐ݒ�
	 * 
	 * @param CODE_DIV �R�[�h�敪
	 */
	public void setCODE_DIV(String CODE_DIV) {
		this.CODE_DIV = CODE_DIV;
	}

	/**
	 * �R�[�h�̎擾
	 * 
	 * @return CODE �R�[�h
	 */
	public String getCODE() {
		return CODE;
	}

	/**
	 * �R�[�h�̐ݒ�
	 * 
	 * @param CODE �R�[�h
	 */
	public void setCODE(String CODE) {
		this.CODE = CODE;
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

	/**
	 * true:���q���[�h�Afalse:�O�q���[�h�̎擾
	 * 
	 * @return local true:���q���[�h�Afalse:�O�q���[�h
	 */
	public Boolean getLocal() {
		return local;
	}

	/**
	 * true:���q���[�h�Afalse:�O�q���[�h�̎擾
	 * 
	 * @return local true:���q���[�h�Afalse:�O�q���[�h
	 */
	public Boolean isLocal() {
		return local;
	}

	/**
	 * true:���q���[�h�Afalse:�O�q���[�h�̐ݒ�
	 * 
	 * @param local true:���q���[�h�Afalse:�O�q���[�h
	 */
	public void setLocal(Boolean local) {
		this.local = local;
	}

}
