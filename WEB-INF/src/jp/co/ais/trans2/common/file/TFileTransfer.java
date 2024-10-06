package jp.co.ais.trans2.common.file;

import java.io.*;

/**
 * �t�@�C������MIF
 */
public interface TFileTransfer {

	/**
	 * �t�@�C�����擾
	 * 
	 * @return file
	 */
	public File getFile();

	/**
	 * �t�@�C����ݒ�
	 * 
	 * @param file
	 */
	public void setFile(File file);

	/**
	 * �t�@�C���o�C�i�����擾
	 * 
	 * @return binary
	 */
	public Object getFileData();

	/**
	 * �t�@�C���o�C�i����ݒ�
	 * 
	 * @param binary
	 */
	public void setFileData(Object binary);

	/**
	 * �t�@�C���C���f�b�N�X�̎擾
	 * 
	 * @return fileIndex �t�@�C���C���f�b�N�X
	 */
	public int getFileIndex();

	/**
	 * �t�@�C���C���f�b�N�X�̐ݒ�
	 * 
	 * @param fileIndex �t�@�C���C���f�b�N�X
	 */
	public void setFileIndex(int fileIndex);

	/**
	 * �T�[�o�T�C�g�t�@�C�����̎擾
	 * 
	 * @return serverFileName �T�[�o�T�C�g�t�@�C����
	 */
	public String getServerFileName();

	/**
	 * �T�[�o�T�C�g�t�@�C�����̐ݒ�
	 * 
	 * @param serverFileName �T�[�o�T�C�g�t�@�C����
	 */
	public void setServerFileName(String serverFileName);

}
