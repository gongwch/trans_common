package jp.co.ais.trans.common.server;

import java.io.*;

import jp.co.ais.trans.common.config.*;

/**
 * �T�[�o�萔
 */
public enum TServerEnv {

	/** HttpSession�ɕۑ������V�X�e�����ʐ�pkey����prefix. */
	SYS_PREFIX("trans.common."),

	/** ���[�g�f�B���N�g�� */
	TOP_DIR("/" + ClientConfig.getRootDirectory() + "/"),

	/** JSP�t�@�C���p�X */
	JSP_PATH(TServerEnv.TOP_DIR.toString()),

	/** �G���[��JSP�t�@�C����. */
	ERR_JSP("error.jsp"),

	/** �A�b�v���[�h���e���|�����t�@�C���z�u�f�B���N�g��. */
	TMP_DIR("/"),

	/** ���s���O�t�@�C���z�u�f�B���N�g��. */
	USER_LOG_DIR(ServerConfig.getRootPath() + File.separator + "log" + File.separator);

	/** �l */
	private String value;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param value �l
	 */
	private TServerEnv(String value) {
		this.value = value;
	}

	/**
	 * ������\��
	 */
	@Override
	public String toString() {
		return value;
	}
}
