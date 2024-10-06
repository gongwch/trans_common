package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * �V�X�e���敪�}�X�^
 */
public class SYS_MST extends MasterBase {

	/** �V�X�e���敪Bean */
	public static final String TABLE = "SYS_MST";

	/** �� */
	public static final String AP = "AP";

	/** ���d�� */
	public static final int AP_HDR = 1;

	/** ���d��p�^�� */
	public static final int AP_PTN = 2;

	/** ��{��v */
	public static final String GL = "GL";

	/** ��{��v�d�� */
	public static final int GL_HDR = 3;

	/** ��{��v�d��p�^�� */
	public static final int GL_PTN = 4;

	/** �� */
	public static final String AR = "AR";

	/** ���d�� */
	public static final int AR_HDR = 5;

	/** ���d��p�^�� */
	public static final int AR_PTN = 6;

	/** �ؓ��� */
	public static final String LM = "LM";

	/** �ؓ����w�b�_ */
	public static final int LM_HDR = 7;

	/** ��ЃR�[�h */
	protected String kAI_CODE = null;

	/** �V�X�e���敪 */
	protected String sYS_KBN = null;

	/** �V�X�e���敪���� */
	protected String sYS_KBN_NAME = null;

	/** �V�X�e���敪���� */
	protected String sYS_KBN_NAME_S = null;

	/** �V�X�e���敪�������� */
	protected String sYS_KBN_NAME_K = null;

	/** �O���V�X�e���敪 */
	protected String oSY_KBN = null;

	/** �v���O�����h�c */
	protected String pRG_ID = null;

	/** �J���[ */
	protected String sYS_COLOR = null;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return kAI_CODE ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return kAI_CODE;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param kAI_CODE ��ЃR�[�h
	 */
	public void setKAI_CODE(String kAI_CODE) {
		this.kAI_CODE = kAI_CODE;
	}

	/**
	 * �V�X�e���敪�̎擾
	 * 
	 * @return sYS_KBN �V�X�e���敪
	 */
	public String getSYS_KBN() {
		return sYS_KBN;
	}

	/**
	 * �V�X�e���敪�̐ݒ�
	 * 
	 * @param sYS_KBN �V�X�e���敪
	 */
	public void setSYS_KBN(String sYS_KBN) {
		this.sYS_KBN = sYS_KBN;
	}

	/**
	 * �V�X�e���敪���̂̎擾
	 * 
	 * @return sYS_KBN_NAME �V�X�e���敪����
	 */
	public String getSYS_KBN_NAME() {
		return sYS_KBN_NAME;
	}

	/**
	 * �V�X�e���敪���̂̐ݒ�
	 * 
	 * @param sYS_KBN_NAME �V�X�e���敪����
	 */
	public void setSYS_KBN_NAME(String sYS_KBN_NAME) {
		this.sYS_KBN_NAME = sYS_KBN_NAME;
	}

	/**
	 * �V�X�e���敪���̂̎擾
	 * 
	 * @return sYS_KBN_NAME_S �V�X�e���敪����
	 */
	public String getSYS_KBN_NAME_S() {
		return sYS_KBN_NAME_S;
	}

	/**
	 * �V�X�e���敪���̂̐ݒ�
	 * 
	 * @param sYS_KBN_NAME_S �V�X�e���敪����
	 */
	public void setSYS_KBN_NAME_S(String sYS_KBN_NAME_S) {
		this.sYS_KBN_NAME_S = sYS_KBN_NAME_S;
	}

	/**
	 * �V�X�e���敪�������̂̎擾
	 * 
	 * @return sYS_KBN_NAME_K �V�X�e���敪��������
	 */
	public String getSYS_KBN_NAME_K() {
		return sYS_KBN_NAME_K;
	}

	/**
	 * �V�X�e���敪�������̂̐ݒ�
	 * 
	 * @param sYS_KBN_NAME_K �V�X�e���敪��������
	 */
	public void setSYS_KBN_NAME_K(String sYS_KBN_NAME_K) {
		this.sYS_KBN_NAME_K = sYS_KBN_NAME_K;
	}

	/**
	 * �O���V�X�e���敪�̎擾
	 * 
	 * @return oSY_KBN �O���V�X�e���敪
	 */
	public String getOSY_KBN() {
		return oSY_KBN;
	}

	/**
	 * �O���V�X�e���敪�̐ݒ�
	 * 
	 * @param oSY_KBN �O���V�X�e���敪
	 */
	public void setOSY_KBN(String oSY_KBN) {
		this.oSY_KBN = oSY_KBN;
	}

	/**
	 * �v���O�����h�c�̎擾
	 * 
	 * @return pRG_ID �v���O�����h�c
	 */
	public String getPRG_ID() {
		return pRG_ID;
	}

	/**
	 * �v���O�����h�c�̐ݒ�
	 * 
	 * @param pRG_ID �v���O�����h�c
	 */
	public void setPRG_ID(String pRG_ID) {
		this.pRG_ID = pRG_ID;
	}

	/**
	 * �J���[�̎擾
	 * 
	 * @return sYS_COLOR �J���[
	 */
	public String getSYS_COLOR() {
		return sYS_COLOR;
	}

	/**
	 * �J���[�̐ݒ�
	 * 
	 * @param sYS_COLOR �J���[
	 */
	public void setSYS_COLOR(String sYS_COLOR) {
		this.sYS_COLOR = sYS_COLOR;
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer("[");
		buff.append("/kAI_CODE=").append(kAI_CODE);
		buff.append("/sYS_KBN=").append(sYS_KBN);
		buff.append("/sYS_KBN_NAME=").append(sYS_KBN_NAME);
		buff.append("/sYS_KBN_NAME_S=").append(sYS_KBN_NAME_S);
		buff.append("/sYS_KBN_NAME_K=").append(sYS_KBN_NAME_K);
		buff.append("/oSY_KBN=").append(oSY_KBN);
		buff.append("/iNP_DATE=").append(iNP_DATE);
		buff.append("/uPD_DATE=").append(uPD_DATE);
		buff.append("/pRG_ID=").append(pRG_ID);
		buff.append("/uSR_ID=").append(uSR_ID);
		buff.append("/sYS_COLOR=").append(sYS_COLOR);
		buff.append("]");
		return buff.toString();
	}

	@Override
	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kAI_CODE);
		list.add(sYS_KBN);
		list.add(sYS_KBN_NAME);
		list.add(sYS_KBN_NAME_S);
		list.add(sYS_KBN_NAME_K);
		list.add(oSY_KBN);
		list.add(iNP_DATE);
		list.add(uPD_DATE);
		list.add(pRG_ID);
		list.add(uSR_ID);
		list.add(sYS_COLOR);

		return list;
	}
}
