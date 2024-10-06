package jp.co.ais.trans2.model.save;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.file.*;

/**
 * �}�j���A���Y�t
 */
public class MANUAL_ATTACH extends TransferBase implements TFileTransfer {

	/** �A�� */
	protected int SEQ = 0;

	/** �t�@�C���� */
	protected String FILE_NAME = null;

	/** �t�@�C�� */
	protected File file = null;

	/** �T�[�o�[�t�@�C���� */
	protected String SRV_FILE_NAME = null;

	/** �o�^���t */
	protected Date INP_DATE = null;

	/** ���[�UID */
	protected String USR_ID = null;

	/** �t�@�C���C���f�b�N�X */
	protected int fileIndex = -1;

	/**
	 * �A�Ԃ̎擾
	 * 
	 * @return SEQ �A��
	 */
	public int getSEQ() {
		return SEQ;
	}

	/**
	 * �A�Ԃ̐ݒ�
	 * 
	 * @param SEQ �A��
	 */
	public void setSEQ(int SEQ) {
		this.SEQ = SEQ;
	}

	/**
	 * �t�@�C�����̎擾
	 * 
	 * @return FILE_NAME �t�@�C����
	 */
	public String getFILE_NAME() {
		return FILE_NAME;
	}

	/**
	 * �t�@�C�����̐ݒ�
	 * 
	 * @param FILE_NAME �t�@�C����
	 */
	public void setFILE_NAME(String FILE_NAME) {
		this.FILE_NAME = FILE_NAME;
	}

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
	 * �T�[�o�[�t�@�C�����̎擾
	 * 
	 * @return SRV_FILE_NAME �T�[�o�[�t�@�C����
	 */
	public String getSRV_FILE_NAME() {
		return SRV_FILE_NAME;
	}

	/**
	 * �T�[�o�[�t�@�C�����̐ݒ�
	 * 
	 * @param SRV_FILE_NAME �T�[�o�[�t�@�C����
	 */
	public void setSRV_FILE_NAME(String SRV_FILE_NAME) {
		this.SRV_FILE_NAME = SRV_FILE_NAME;
	}

	/**
	 * �T�[�o�T�C�g�t�@�C�����̎擾
	 * 
	 * @return serverFileName �T�[�o�T�C�g�t�@�C����
	 */
	public String getServerFileName() {
		return SRV_FILE_NAME;
	}

	/**
	 * �T�[�o�T�C�g�t�@�C�����̐ݒ�
	 * 
	 * @param serverFileName �T�[�o�T�C�g�t�@�C����
	 */
	public void setServerFileName(String serverFileName) {
		this.SRV_FILE_NAME = serverFileName;
	}

	/**
	 * �o�^���t�̎擾
	 * 
	 * @return INP_DATE �o�^���t
	 */
	public Date getINP_DATE() {
		return INP_DATE;
	}

	/**
	 * �o�^���t�̐ݒ�
	 * 
	 * @param INP_DATE �o�^���t
	 */
	public void setINP_DATE(Date INP_DATE) {
		this.INP_DATE = INP_DATE;
	}

	/**
	 * ���[�UID�̎擾
	 * 
	 * @return USR_ID ���[�UID
	 */
	public String getUSR_ID() {
		return USR_ID;
	}

	/**
	 * ���[�UID�̐ݒ�
	 * 
	 * @param USR_ID ���[�UID
	 */
	public void setUSR_ID(String USR_ID) {
		this.USR_ID = USR_ID;
	}

	/**
	 * �t�@�C���o�C�i�����擾
	 * 
	 * @return binary
	 */
	@Deprecated
	public Object getFileData() {
		return null;
	}

	/**
	 * �t�@�C���o�C�i����ݒ�
	 * 
	 * @param binary
	 */
	@Deprecated
	public void setFileData(Object binary) {
		//
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

}
