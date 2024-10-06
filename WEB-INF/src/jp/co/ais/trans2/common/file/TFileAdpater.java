package jp.co.ais.trans2.common.file;

import java.io.*;

import jp.co.ais.trans.common.dt.*;

/**
 * �t�@�C��Adapter
 */
public class TFileAdpater extends TransferBase implements TFileTransfer {

	/** �t�@�C�� */
	protected File file = null;

	/** �t�@�C���o�C�i�� */
	protected Object fileData = null;

	/** �t�@�C���C���f�b�N�X */
	protected int fileIndex = -1;

	/** �T�[�o�T�C�g�t�@�C���� */
	protected String serverFileName = null;

	/**
	 * �t�@�C���̎擾
	 * 
	 * @return file �t�@�C��
	 */
	public File getFile() {
		return file;
	}

	/**
	 * �t�@�C���̐ݒ�
	 * 
	 * @param file �t�@�C��
	 */
	public void setFile(File file) {
		this.file = file;
	}

	/**
	 * �t�@�C���o�C�i���̎擾
	 * 
	 * @return fileData �t�@�C���o�C�i��
	 */
	public Object getFileData() {
		return fileData;
	}

	/**
	 * �t�@�C���o�C�i���̐ݒ�
	 * 
	 * @param fileData �t�@�C���o�C�i��
	 */
	public void setFileData(Object fileData) {
		this.fileData = fileData;
	}

	/**
	 * �t�@�C���C���f�b�N�X�̎擾
	 * 
	 * @return fileIndex �t�@�C���C���f�b�N�X
	 */
	public int getFileIndex() {
		return fileIndex;
	}

	/**
	 * �t�@�C���C���f�b�N�X�̐ݒ�
	 * 
	 * @param fileIndex �t�@�C���C���f�b�N�X
	 */
	public void setFileIndex(int fileIndex) {
		this.fileIndex = fileIndex;
	}

	/**
	 * �T�[�o�T�C�g�t�@�C�����̎擾
	 * 
	 * @return serverFileName �T�[�o�T�C�g�t�@�C����
	 */
	public String getServerFileName() {
		return serverFileName;
	}

	/**
	 * �T�[�o�T�C�g�t�@�C�����̐ݒ�
	 * 
	 * @param serverFileName �T�[�o�T�C�g�t�@�C����
	 */
	public void setServerFileName(String serverFileName) {
		this.serverFileName = serverFileName;
	}

}
