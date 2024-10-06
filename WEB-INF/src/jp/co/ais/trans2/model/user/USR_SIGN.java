package jp.co.ais.trans2.model.user;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.file.*;

/**
 * ���[�U�[ SIGN
 */
public class USR_SIGN extends TransferBase implements TFileTransfer {

	/** �e�[�u���� */
	public static final String TABLE = "USR_SIGN_ATTACH";

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** �S���҃R�[�h */
	protected String USR_CODE = null;

	/** �t�@�C���� */
	protected String FILE_NAME = null;

	/** �t�@�C���f�[�^ */
	protected Object FILE_DATA = null;

	/** �t�@�C�� */
	protected File file = null;

	/** �T�[�o�T�C�g�t�@�C���� */
	protected String serverFileName = null;

	/** �o�^���t */
	protected Date INP_DATE = null;

	/** �v���O����ID */
	protected String PRG_ID = null;

	/** ���[�UID */
	protected String USR_ID = null;

	/** �t�@�C���C���f�b�N�X */
	protected int fileIndex = -1;

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
	 * /** �S���҃R�[�h�̎擾
	 * 
	 * @return USER_CODE �S���҃R�[�h
	 */
	public String getUSR_CODE() {
		return USR_CODE;
	}

	/**
	 * �S���҃R�[�h�̐ݒ�
	 * 
	 * @param USR_CODE �S���҃R�[�h
	 */
	public void setUSR_CODE(String USR_CODE) {
		this.USR_CODE = USR_CODE;
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
	 * �t�@�C���f�[�^�̎擾
	 * 
	 * @return FILE_DATA �t�@�C���f�[�^
	 */
	public Object getFILE_DATA() {
		return FILE_DATA;
	}

	/**
	 * �t�@�C���f�[�^�̐ݒ�
	 * 
	 * @param FILE_DATA �t�@�C���f�[�^
	 */
	public void setFILE_DATA(Object FILE_DATA) {
		this.FILE_DATA = FILE_DATA;
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
	 * �v���O����ID�̎擾
	 * 
	 * @return PRG_ID �v���O����ID
	 */
	public String getPRG_ID() {
		return PRG_ID;
	}

	/**
	 * �v���O����ID�̐ݒ�
	 * 
	 * @param PRG_ID �v���O����ID
	 */
	public void setPRG_ID(String PRG_ID) {
		this.PRG_ID = PRG_ID;
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
	public Object getFileData() {
		return getFILE_DATA();
	}

	/**
	 * �t�@�C���o�C�i����ݒ�
	 * 
	 * @param binary
	 */
	public void setFileData(Object binary) {
		setFILE_DATA(binary);
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
