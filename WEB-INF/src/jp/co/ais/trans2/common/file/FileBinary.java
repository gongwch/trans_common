package jp.co.ais.trans2.common.file;

import jp.co.ais.trans.common.dt.*;

/**
 * �t�@�C���o�C�i��
 */
public class FileBinary extends TransferBase {

	/** �t�@�C���� */
	protected String fileName = null;

	/** �o�C�i�� */
	protected byte[] binary = null;

	/**
	 * �t�@�C�������擾����
	 * 
	 * @return �t�@�C����
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * �t�@�C������ݒ肷��
	 * 
	 * @param fbDataFileName
	 */
	public void setFileName(String fbDataFileName) {
		this.fileName = fbDataFileName;
	}

	/**
	 * �o�C�i���f�[�^���擾����
	 * 
	 * @return �o�C�i���f�[�^
	 */
	public byte[] getBinary() {
		return binary;
	}

	/**
	 * �o�C�i���f�[�^��ݒ肷��
	 * 
	 * @param binary �o�C�i���f�[�^
	 */
	public void setBinary(byte[] binary) {
		this.binary = binary;
	}

}