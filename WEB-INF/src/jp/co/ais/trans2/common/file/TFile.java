package jp.co.ais.trans2.common.file;

import java.io.File;
import java.util.Date;

import jp.co.ais.trans.common.dt.TransferBase;

/**
 * �t�@�C��
 * 
 * @author AIS
 */
public class TFile extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �L�[�R�[�h */
	protected String key = null;

	/** �t�@�C������ */
	protected String fileName = null;

	/** ���͎��� */
	protected Date inputDate = null;

	/** �v���O�����R�[�h */
	protected String programCode = null;

	/** ���[�U�[�R�[�h */
	protected String userCode = null;

	/** �t�@�C�� */
	protected File file = null;

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �L�[�R�[�h�̎擾
	 * 
	 * @return key �L�[�R�[�h
	 */
	public String getKey() {
		return key;
	}

	/**
	 * �L�[�R�[�h�̐ݒ�
	 * 
	 * @param key �L�[�R�[�h
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * �t�@�C�����̂̎擾
	 * 
	 * @return fileName �t�@�C������
	 */
	public String getFileName() {
		return fileName;
	}

	/**
	 * �t�@�C�����̂̐ݒ�
	 * 
	 * @param fileName �t�@�C������
	 */
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	/**
	 * ���͎����̎擾
	 * 
	 * @return inputDate ���͎���
	 */
	public Date getInputDate() {
		return inputDate;
	}

	/**
	 * ���͎����̐ݒ�
	 * 
	 * @param inputDate ���͎���
	 */
	public void setInputDate(Date inputDate) {
		this.inputDate = inputDate;
	}

	/**
	 * �v���O�����R�[�h�̎擾
	 * 
	 * @return programCode �v���O�����R�[�h
	 */
	public String getProgramCode() {
		return programCode;
	}

	/**
	 * �v���O�����R�[�h�̐ݒ�
	 * 
	 * @param programCode �v���O�����R�[�h
	 */
	public void setProgramCode(String programCode) {
		this.programCode = programCode;
	}

	/**
	 * ���[�U�[�R�[�h�̎擾
	 * 
	 * @return userCode ���[�U�[�R�[�h
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * ���[�U�[�R�[�h�̐ݒ�
	 * 
	 * @param userCode ���[�U�[�R�[�h
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
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

}
