package jp.co.ais.trans2.model.slip;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.common.file.*;

/**
 * �`�[�Y�t
 */
public class SWK_ATTACH extends TransferBase implements TFileTransfer {

	/** �e�[�u���� */
	public static final String TABLE = "SWK_ATTACH";

	/** ��ЃR�[�h */
	protected String KAI_CODE = null;

	/** �`�[�ԍ� */
	protected String SWK_DEN_NO = null;

	/** �A�� */
	protected int SEQ = 0;

	/** �S���҃R�[�h */
	protected String EMP_CODE = null;

	/** �S���Җ��� */
	protected String EMP_NAME = null;

	/** �S���җ��� */
	protected String EMP_NAME_S = null;

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
	 * �`�[�ԍ��̎擾
	 * 
	 * @return SWK_DEN_NO �`�[�ԍ�
	 */
	public String getSWK_DEN_NO() {
		return SWK_DEN_NO;
	}

	/**
	 * �`�[�ԍ��̐ݒ�
	 * 
	 * @param SWK_DEN_NO �`�[�ԍ�
	 */
	public void setSWK_DEN_NO(String SWK_DEN_NO) {
		this.SWK_DEN_NO = SWK_DEN_NO;
	}

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
	 * �S���҃R�[�h�̎擾
	 * 
	 * @return EMP_CODE �S���҃R�[�h
	 */
	public String getEMP_CODE() {
		return EMP_CODE;
	}

	/**
	 * �S���҃R�[�h�̐ݒ�
	 * 
	 * @param EMP_CODE �S���҃R�[�h
	 */
	public void setEMP_CODE(String EMP_CODE) {
		this.EMP_CODE = EMP_CODE;
	}

	/**
	 * �S���Җ��̂̎擾
	 * 
	 * @return EMP_NAME �S���Җ���
	 */
	public String getEMP_NAME() {
		return EMP_NAME;
	}

	/**
	 * �S���Җ��̂̐ݒ�
	 * 
	 * @param EMP_NAME �S���Җ���
	 */
	public void setEMP_NAME(String EMP_NAME) {
		this.EMP_NAME = EMP_NAME;
	}

	/**
	 * �S���җ��̂̎擾
	 * 
	 * @return EMP_NAME_S �S���җ���
	 */
	public String getEMP_NAME_S() {
		return EMP_NAME_S;
	}

	/**
	 * �S���җ��̂̐ݒ�
	 * 
	 * @param EMP_NAME_S �S���җ���
	 */
	public void setEMP_NAME_S(String EMP_NAME_S) {
		this.EMP_NAME_S = EMP_NAME_S;
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
