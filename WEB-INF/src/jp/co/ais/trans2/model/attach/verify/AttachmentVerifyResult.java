package jp.co.ais.trans2.model.attach.verify;

import jp.co.ais.trans.common.dt.*;

/**
 * �Y�t�t�@�C�����،���
 */
public class AttachmentVerifyResult extends TransferBase {

	/** ��ЃR�[�h */
	protected String KAI_CODE;

	/** ���ؑΏێ�� */
	protected VerifyResultType TYPE;

	/** �L�[���1 �d��F�`�[�ԍ� OP�FOP_ATT_UID */
	protected String KEY1;

	/** �L�[���2 �d��F�`�[���t OP�F(VOY:VOY_UID TCC:TCC_UID VCC:VCC_UID) */
	protected String KEY2;

	/** �L�[���3 �d��F�`�[��ʖ� OP�F(VOY:vessel + voy TCC:TC_CTRT_NO VCC:VCC_CTRT_NO) */
	protected String KEY3;

	/** �L�[���4 �d��F�Ȃ� OP�F(VOY:�Ȃ� TCC:vessel VCC:vessel + voy) */
	protected String KEY4;

	/** �t�@�C���� */
	protected String FILE_NAME;

	/** �T�[�o�[�t�@�C���� */
	protected String SRV_FILE_NAME;

	/** ���b�Z�[�W */
	protected String MESSAGE;

	/** ���ؑΏێ�� */
	public enum VerifyResultType {
		/** �d�� */
		SWK_ATTACH,
		/** �D�� */
		SC_ATTACH,
		/** OP */
		OP_ATTACH,
		/** BL */
		BL_ATTACH;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKAI_CODE() {
		return KAI_CODE;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param kAI_CODE
	 */
	public void setKAI_CODE(String kAI_CODE) {
		KAI_CODE = kAI_CODE;
	}

	/**
	 * ���ؑΏێ��
	 * 
	 * @return ���ؑΏێ��
	 */
	public VerifyResultType getTYPE() {
		return TYPE;
	}

	/**
	 * ���ؑΏێ��
	 *
	 * @param tYPE
	 */
	public void setTYPE(VerifyResultType tYPE) {
		TYPE = tYPE;
	}

	/**
	 * �L�[���1 �d��F�`�[�ԍ� OP�FOP_ATT_UID
	 * 
	 * @return �L�[���1 �d��F�`�[�ԍ� OP�FOP_ATT_UID
	 */
	public String getKEY1() {
		return KEY1;
	}

	/**
	 * �L�[���1 �d��F�`�[�ԍ� OP�FOP_ATT_UID
	 * 
	 * @param kEY1
	 */
	public void setKEY1(String kEY1) {
		KEY1 = kEY1;
	}

	/**
	 * �L�[���2 �d��F�`�[���t OP�F(VOY:VOY_UID TCC:TCC_UID VCC:VCC_UID)
	 *
	 * @return �L�[���2 �d��F�`�[���t OP�F(VOY:VOY_UID TCC:TCC_UID VCC:VCC_UID)
	 */
	public String getKEY2() {
		return KEY2;
	}

	/**
	 * �L�[���2 �d��F�`�[���t OP�F(VOY:VOY_UID TCC:TCC_UID VCC:VCC_UID)
	 * 
	 * @param kEY2
	 */
	public void setKEY2(String kEY2) {
		KEY2 = kEY2;
	}

	/**
	 * �L�[���3 �d��F�`�[��ʖ� OP�F(VOY:vessel + voy TCC:TC_CTRT_NO VCC:VCC_CTRT_NO)
	 * 
	 * @return �L�[���3 �d��F�`�[��ʖ� OP�F(VOY:vessel + voy TCC:TC_CTRT_NO VCC:VCC_CTRT_NO)
	 */
	public String getKEY3() {
		return KEY3;
	}

	/**
	 * �L�[���3 �d��F�`�[��ʖ� OP�F(VOY:vessel + voy TCC:TC_CTRT_NO VCC:VCC_CTRT_NO)
	 * 
	 * @param kEY3
	 */
	public void setKEY3(String kEY3) {
		KEY3 = kEY3;
	}

	/**
	 * �L�[���4 �d��F�Ȃ� OP�F(VOY:�Ȃ� TCC:vessel VCC:vessel + voy)
	 * 
	 * @return �L�[���4 �d��F�Ȃ� OP�F(VOY:�Ȃ� TCC:vessel VCC:vessel + voy)
	 */
	public String getKEY4() {
		return KEY4;
	}

	/**
	 * �L�[���4 �d��F�Ȃ� OP�F(VOY:�Ȃ� TCC:vessel VCC:vessel + voy)
	 * 
	 * @param kEY4
	 */
	public void setKEY4(String kEY4) {
		KEY4 = kEY4;
	}

	/**
	 * �t�@�C����
	 * 
	 * @return �t�@�C����
	 */
	public String getFILE_NAME() {
		return FILE_NAME;
	}

	/**
	 * �t�@�C����
	 * 
	 * @param fILE_NAME
	 */
	public void setFILE_NAME(String fILE_NAME) {
		FILE_NAME = fILE_NAME;
	}

	/**
	 * �T�[�o�[�t�@�C����
	 *
	 * @return �T�[�o�[�t�@�C����
	 */
	public String getSRV_FILE_NAME() {
		return SRV_FILE_NAME;
	}

	/**
	 * �T�[�o�[�t�@�C����
	 * 
	 * @param sRV_FILE_NAME
	 */
	public void setSRV_FILE_NAME(String sRV_FILE_NAME) {
		SRV_FILE_NAME = sRV_FILE_NAME;
	}

	/**
	 * ���b�Z�[�W
	 * 
	 * @return ���b�Z�[�W
	 */
	public String getMESSAGE() {
		return MESSAGE;
	}

	/**
	 * ���b�Z�[�W
	 *
	 * @param mESSAGE
	 */
	public void setMESSAGE(String mESSAGE) {
		MESSAGE = mESSAGE;
	}

}
