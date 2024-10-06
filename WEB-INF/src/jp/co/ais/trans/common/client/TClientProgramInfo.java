package jp.co.ais.trans.common.client;

import jp.co.ais.trans.common.util.*;

/**
 * ���ݑI������Ă���v���O�����̏���ێ�����N���X.
 */
public class TClientProgramInfo implements Cloneable {

	/** �C���X�^���X */
	private static TClientProgramInfo instance = new TClientProgramInfo();

	/** �v���O�����R�[�h */
	private String prgCode = "";

	/** �v���O�������� */
	private String prgName = "";

	/** �v���O�������� */
	private String prgNameS = "";

	/** �������x�� */
	private int ken;

	/**
	 * common�Atest�ȊO�Œ��ڎg��Ȃ�����.<br>
	 * �C���X�^���X�̎擾
	 * 
	 * @return �C���X�^���X
	 * @deprecated ���ڎg��Ȃ�����
	 */
	public static TClientProgramInfo getInstance() {
		return instance;
	}

	/**
	 * �v���O�����R�[�h�擾
	 * 
	 * @return �v���O�����R�[�h
	 */
	public String getProgramCode() {
		return this.prgCode;
	}

	/**
	 * �v���O�����R�[�h�擾setter
	 * 
	 * @param prgCode �v���O�����R�[�h
	 */
	public void setProgramCode(String prgCode) {
		this.prgCode = prgCode;
	}

	/**
	 * �v���O�������̎擾
	 * 
	 * @return �v���O��������
	 */
	public String getProgramName() {
		return this.prgName;
	}

	/**
	 * �v���O��������setter
	 * 
	 * @param prgName �v���O��������
	 */
	public void setProgramName(String prgName) {
		this.prgName = prgName;
	}

	/**
	 * �v���O�������̎擾
	 * 
	 * @return �v���O��������
	 */
	public String getProgramShortName() {
		return this.prgNameS;
	}

	/**
	 * �v���O��������setter
	 * 
	 * @param prgNameS �v���O��������
	 */
	public void setProgramShortName(String prgNameS) {
		this.prgNameS = prgNameS;
	}

	/**
	 * �������x���擾
	 * 
	 * @return �������x��
	 */
	public int getProcessLevel() {
		return this.ken;
	}

	/**
	 * �������x��setter
	 * 
	 * @param ken �������x��
	 */
	public void setProcessLevel(int ken) {
		this.ken = ken;
	}

	/**
	 * �N���[���C���X�^���X�𐶐�
	 * 
	 * @return �C���X�^���X
	 * @see java.lang.Object#clone()
	 */
	public TClientProgramInfo clone() {
		TClientProgramInfo clone = new TClientProgramInfo();
		clone.prgCode = this.prgCode;
		clone.prgName = this.prgName;
		clone.prgNameS = this.prgNameS;
		clone.ken = this.ken;

		return clone;
	}

	/**
	 * ������ϊ�
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder("[");
		buff.append(Util.safeNull(prgCode)).append("/");
		buff.append(Util.safeNull(prgName)).append("/");
		buff.append(Util.safeNull(prgNameS)).append("/");
		buff.append(Util.safeNull(String.valueOf(ken))).append("/");
		buff.append("]");

		return buff.toString();
	}
}
