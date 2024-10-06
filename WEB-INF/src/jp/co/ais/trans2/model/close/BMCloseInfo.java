package jp.co.ais.trans2.model.close;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �����X�V<br>
 * �R�����ߏ��Bean
 */
public class BMCloseInfo extends TransferBase {

	/** �D�R�[�h */
	protected String VESSEL_CODE;

	/** �D���� */
	protected String VESSEL_NAME;

	/** ����敪 */
	protected String OIL_TYPE_KBN;

	/** ����敪���� */
	protected String OIL_TYPE_NAME;

	/** �`�[���t */
	protected Date DEN_DATE;

	/**
	 * �D�R�[�h�擾
	 * 
	 * @return �D�R�[�h
	 */
	public String getVESSEL_CODE() {
		return this.VESSEL_CODE;
	}

	/**
	 * �D�R�[�h�ݒ�
	 * 
	 * @param vESSEL_CODE
	 */
	public void setVESSEL_CODE(String vESSEL_CODE) {
		VESSEL_CODE = vESSEL_CODE;
	}

	/**
	 * �D���̎擾
	 * 
	 * @return �D����
	 */
	public String getVESSEL_NAME() {
		return this.VESSEL_NAME;
	}

	/**
	 * �D���̐ݒ�
	 * 
	 * @param vESSEL_NAME
	 */
	public void setVESSEL_NAME(String vESSEL_NAME) {
		VESSEL_NAME = vESSEL_NAME;
	}


	/**
	 * ����敪�擾
	 * 
	 * @return ����敪
	 */
	public String getOIL_TYPE_KBN() {
		return this.OIL_TYPE_KBN;
	}

	/**
	 * ����敪�ݒ�
	 * 
	 * @param oIL_TYPE_KBN
	 */
	public void setOIL_TYPE_KBN(String oIL_TYPE_KBN) {
		OIL_TYPE_KBN = oIL_TYPE_KBN;
	}
/**
	 * ����敪���̎擾
	 * 
	 * @return ����敪����
	 */
	public String getOIL_TYPE_NAME() {
		return this.OIL_TYPE_NAME;
	}

	/**
	 * ����敪���̐ݒ�
	 * 
	 * @param oIL_TYPE_NAME
	 */
	public void setOIL_TYPE_NAME(String oIL_TYPE_NAME) {
		OIL_TYPE_NAME = oIL_TYPE_NAME;
	}

	/**
	 * �`�[���t�擾
	 * 
	 * @return �`�[���t
	 */
	public Date getDEN_DATE() {
		return this.DEN_DATE;
	}

	/**
	 * �`�[���t�ݒ�
	 * 
	 * @param dEN_DATE
	 */
	public void setDEN_DATE(Date dEN_DATE) {
		DEN_DATE = dEN_DATE;
	}
}
