package jp.co.ais.trans.master.entity;

import jp.co.ais.trans.common.dt.*;

/**
 * �����[�X�t�@�C���p�I�u�W�F�N�g
 * 
 * @author roh
 */
public class ReleasedFileObject extends TransferBase {

	/** �t�H���_�� */
	private String pATH_NAME;

	/** �t�@�C���� */
	private String fILE_NAME;

	/** �X�V���t */
	private String uPDATE_DATE;

	/** �t�@�C���̒��� */
	private long lENGTH;

	/**
	 * �t�@�C���̒����擾
	 * 
	 * @return �t�@�C���̒���
	 */

	public long getLENGTH() {
		return lENGTH;
	}

	/**
	 * �t�@�C���̒����ݒ�
	 * 
	 * @param length
	 */
	public void setLENGTH(long length) {
		lENGTH = length;
	}

	/**
	 * �t�@�C�����擾
	 * 
	 * @return �t�@�C����
	 */
	public String getFILE_NAME() {
		return fILE_NAME;
	}

	/**
	 * �t�@�C�����ݒ�
	 * 
	 * @param file_name
	 */
	public void setFILE_NAME(String file_name) {
		fILE_NAME = file_name;
	}

	/**
	 * �t�H���_�� �擾
	 * 
	 * @return �t�H���_��
	 */
	public String getPATH_NAME() {
		return pATH_NAME;
	}

	/**
	 * �t�H���_�� �ݒ�
	 * 
	 * @param path_name
	 */
	public void setPATH_NAME(String path_name) {
		pATH_NAME = path_name;
	}

	/**
	 * �X�V���t�擾
	 * 
	 * @return �X�V���t
	 */
	public String getUPDATE_DATE() {
		return uPDATE_DATE;
	}

	/**
	 * �X�V���t�ݒ�
	 * 
	 * @param update_date
	 */
	public void setUPDATE_DATE(String update_date) {
		uPDATE_DATE = update_date;
	}

}
