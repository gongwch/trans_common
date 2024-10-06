package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * ��s�}�X�^�����p�����[�^
 */
public class BnkMstParameter extends TransferBase {

	/** ��s�R�[�h */
	private String bnkCode = "";

	/** �x�X�R�[�h */
	private String bnkStnCode = "";

	/** ��s�R�[�h�i�����܂��j */
	private String likeBnkCode = "";

	/** �x�X�R�[�h�i�����܂��j */
	private String likeBnkStnCode = "";

	/** ��s�� */
	private String likeBnkName = "";

	/** ��s�������� */
	private String likeBnkNameK = "";

	/** �x�X�� */
	private String likeBnkStnName = "";

	/** �x�X�������� */
	private String likeBnkStnNameK = "";

	/** ��s�J�n�R�[�h */
	private String bnkCodeBegin = "";

	/** ��s�I���R�[�h */
	private String bnkCodeEnd = "";

	/** �x�X�J�n�R�[�h */
	private String bnkStnCodeBegin = "";

	/** �x�X�I���R�[�h */
	private String bnkStnCodeEnd = "";

	/** �L�����ԓ��t */
	private Date termBasisDate = null;

	/**
	 * �L�����ԓ��t���擾����
	 * 
	 * @return �L�����ԓ��t
	 */
	public Date getTermBasisDate() {
		return termBasisDate;
	}

	/**
	 * �L�����ԓ��t��ݒ肷��
	 * 
	 * @param termBasisDate
	 */
	public void setTermBasisDate(Date termBasisDate) {
		this.termBasisDate = termBasisDate;
	}

	/**
	 * ��s�R�[�h�擾
	 * 
	 * @return ��s�R�[�h
	 */
	public String getBnkCode() {
		return bnkCode;
	}

	/**
	 * ��s�R�[�h�ݒ�
	 * 
	 * @param bnkCode ��s�R�[�h
	 */
	public void setBnkCode(String bnkCode) {
		this.bnkCode = bnkCode;
	}

	/**
	 * bnkCodeBegin�擾
	 * 
	 * @return bnkCodeBegin
	 */
	public String getBnkCodeBegin() {
		return bnkCodeBegin;
	}

	/**
	 * bnkCodeBegin�ݒ�
	 * 
	 * @param bnkCodeBegin
	 */
	public void setBnkCodeBegin(String bnkCodeBegin) {
		this.bnkCodeBegin = bnkCodeBegin;
	}

	/**
	 * bnkCodeEnd�擾
	 * 
	 * @return bnkCodeEnd
	 */
	public String getBnkCodeEnd() {
		return bnkCodeEnd;
	}

	/**
	 * bnkCodeEnd�ݒ�
	 * 
	 * @param bnkCodeEnd
	 */
	public void setBnkCodeEnd(String bnkCodeEnd) {
		this.bnkCodeEnd = bnkCodeEnd;
	}

	/**
	 * �x�X�R�[�h�擾
	 * 
	 * @return �x�X�R�[�h
	 */
	public String getBnkStnCode() {
		return bnkStnCode;
	}

	/**
	 * �x�X�R�[�h�ݒ�
	 * 
	 * @param bnkStnCode �x�X�R�[�h
	 */
	public void setBnkStnCode(String bnkStnCode) {
		this.bnkStnCode = bnkStnCode;
	}

	/**
	 * bnkStnCodeBegin�擾
	 * 
	 * @return bnkStnCodeBegin
	 */
	public String getBnkStnCodeBegin() {
		return bnkStnCodeBegin;
	}

	/**
	 * bnkStnCodeBegin�ݒ�
	 * 
	 * @param bnkStnCodeBegin
	 */
	public void setBnkStnCodeBegin(String bnkStnCodeBegin) {
		this.bnkStnCodeBegin = bnkStnCodeBegin;
	}

	/**
	 * �x�X�I���R�[�h�擾
	 * 
	 * @return �x�X�I���R�[�h
	 */
	public String getBnkStnCodeEnd() {
		return bnkStnCodeEnd;
	}

	/**
	 * �x�X�I���R�[�h�ݒ�
	 * 
	 * @param bnkStnCodeEnd �x�X�I���R�[�h
	 */
	public void setBnkStnCodeEnd(String bnkStnCodeEnd) {
		this.bnkStnCodeEnd = bnkStnCodeEnd;
	}

	/**
	 * likeBnkCode�擾
	 * 
	 * @return likeBnkCode
	 */
	public String getLikeBnkCode() {
		return likeBnkCode;
	}

	/**
	 * likeBnkCode�ݒ�
	 * 
	 * @param likeBnkCode
	 */
	public void setLikeBnkCode(String likeBnkCode) {
		this.likeBnkCode = likeBnkCode;
	}

	/**
	 * likeBnkName�擾
	 * 
	 * @return likeBnkName
	 */
	public String getLikeBnkName() {
		return likeBnkName;
	}

	/**
	 * likeBnkName�ݒ�
	 * 
	 * @param likeBnkName
	 */
	public void setLikeBnkName(String likeBnkName) {
		this.likeBnkName = likeBnkName;
	}

	/**
	 * likeBnkNameK�擾
	 * 
	 * @return likeBnkNameK
	 */
	public String getLikeBnkNameK() {
		return likeBnkNameK;
	}

	/**
	 * likeBnkNameK�ݒ�
	 * 
	 * @param likeBnkNameK
	 */
	public void setLikeBnkNameK(String likeBnkNameK) {
		this.likeBnkNameK = likeBnkNameK;
	}

	/**
	 * likeBnkStnCode�擾
	 * 
	 * @return likeBnkStnCode
	 */
	public String getLikeBnkStnCode() {
		return likeBnkStnCode;
	}

	/**
	 * likeBnkStnCode�ݒ�
	 * 
	 * @param likeBnkStnCode
	 */
	public void setLikeBnkStnCode(String likeBnkStnCode) {
		this.likeBnkStnCode = likeBnkStnCode;
	}

	/**
	 * likeBnkStnName�擾
	 * 
	 * @return likeBnkStnName
	 */
	public String getLikeBnkStnName() {
		return likeBnkStnName;
	}

	/**
	 * likeBnkStnName�ݒ�
	 * 
	 * @param likeBnkStnName
	 */
	public void setLikeBnkStnName(String likeBnkStnName) {
		this.likeBnkStnName = likeBnkStnName;
	}

	/**
	 * likeBnkStnNameK�擾
	 * 
	 * @return likeBnkStnNameK
	 */
	public String getLikeBnkStnNameK() {
		return likeBnkStnNameK;
	}

	/**
	 * likeBnkStnNameK�ݒ�
	 * 
	 * @param likeBnkStnNameK
	 */
	public void setLikeBnkStnNameK(String likeBnkStnNameK) {
		this.likeBnkStnNameK = likeBnkStnNameK;
	}

}
