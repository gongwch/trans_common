package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * �x�����@�}�X�^�����p�����[�^
 */
public class ApHohMstParameter extends TransferBase {

	/** �x���Ώۋ敪 �Ј��x�� */
	public static final String SIH_KBN_EMP = "0";

	/** �x���Ώۋ敪 �ЊO�x�� */
	public static final String SIH_KBN_CMP = "1";

	/** �x�������R�[�h �Ј����� */
	public static final String NAI_CODE_EMP_DEFERRED = "10";

	/** �x�������R�[�h �����U���i�Ј��j */
	public static final String NAI_CODE_EMP_UNPAID = "03";

	/** ��ЃR�[�h */
	private String kaiCode;

	/** �Ј��R�[�h */
	private String empCode;

	/** �x���Ώۋ敪 */
	private String hohSihKbn;

	/** �x�������R�[�h (NOT����) */
	private String notHohNaiCode;

	/** �x�������R�[�h */
	private String[] hohNaiCode;

	/** �x�����@�R�[�h */
	private String hohCode;

	/** �x�����@�R�[�h�i�����܂������j */
	private String likeHohCode;

	/** �x�����@���́i�����܂������j */
	private String likeHohName;

	/** �x�����@�������́i�����܂������j */
	private String likeHohNameK;

	/** �L�����ԓ��t */
	private Date termBasisDate;

	/** �x�����@�R�[�h */
	private List<String> hohCodes;

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
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getKaiCode() {
		return kaiCode;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @param kaiCode
	 */
	public void setKaiCode(String kaiCode) {
		this.kaiCode = kaiCode;
	}

	/**
	 * �x�������R�[�h (NOT����)
	 * 
	 * @return �x�������R�[�h (NOT����)
	 */
	public String getNotHohNaiCode() {
		return notHohNaiCode;
	}

	/**
	 * �x�������R�[�h (NOT����)
	 * 
	 * @param notHohNaiCode
	 */
	public void setNotHohNaiCode(String notHohNaiCode) {
		this.notHohNaiCode = notHohNaiCode;
	}

	/**
	 * �x���Ώۋ敪���擾����
	 * 
	 * @return �x���Ώۋ敪
	 */
	public String getHohCode() {
		return hohCode;
	}

	/**
	 * �x���Ώۋ敪��ݒ肷��
	 * 
	 * @param hohCode
	 */
	public void setHohCode(String hohCode) {
		this.hohCode = hohCode;
	}

	/**
	 * �x�������R�[�h���擾����
	 * 
	 * @return �x�������R�[�h
	 */
	public String[] getHohNaiCode() {
		return hohNaiCode;
	}

	/**
	 * �x�������R�[�h��ݒ肷��
	 * 
	 * @param hohNaiCode
	 */
	public void setHohNaiCode(String[] hohNaiCode) {
		this.hohNaiCode = hohNaiCode;
	}

	/**
	 * �x���Ώۋ敪���擾����
	 * 
	 * @return �x���Ώۋ敪
	 */
	public String getHohSihKbn() {
		return hohSihKbn;
	}

	/**
	 * �x���Ώۋ敪��ݒ肷��
	 * 
	 * @param hohSihKbn
	 */
	public void setHohSihKbn(String hohSihKbn) {
		this.hohSihKbn = hohSihKbn;
	}

	/**
	 * �x�����@�R�[�h�i�����܂������j��ݒ肷��
	 * 
	 * @return �x�����@�R�[�h�i�����܂������j
	 */
	public String getLikeHohCode() {

		if (Util.isNullOrEmpty(likeHohCode)) {
			return "";
		}

		return likeHohCode;
	}

	/**
	 * �x�����@�R�[�h�i�����܂������j���擾����
	 * 
	 * @param likeHohCode
	 */
	public void setLikeHohCode(String likeHohCode) {
		this.likeHohCode = likeHohCode;
	}

	/**
	 * �x�����@���́i�����܂������j���擾����
	 * 
	 * @return �x�����@���́i�����܂������j
	 */
	public String getLikeHohName() {

		if (Util.isNullOrEmpty(likeHohName)) {
			return "";
		}

		return likeHohName;

	}

	/**
	 * �x�����@���́i�����܂������j��ݒ肷��
	 * 
	 * @param likeHohName
	 */
	public void setLikeHohName(String likeHohName) {
		this.likeHohName = likeHohName;
	}

	/**
	 * �x�����@�������́i�����܂������j���擾����
	 * 
	 * @return �x�����@�������́i�����܂������j
	 */
	public String getLikeHohNameK() {

		if (Util.isNullOrEmpty(likeHohNameK)) {
			return "";
		}

		return likeHohNameK;
	}

	/**
	 * �x�����@�������́i�����܂������j��ݒ肷��
	 * 
	 * @param likeHohNameK
	 */
	public void setLikeHohNameK(String likeHohNameK) {
		this.likeHohNameK = likeHohNameK;
	}

	/**
	 * �Ј��R�[�h���擾����
	 * 
	 * @return empCode
	 */
	public String getEmpCode() {
		return empCode;
	}

	/**
	 * �Ј��R�[�h��ݒ肷��
	 * 
	 * @param empCode
	 */
	public void setEmpCode(String empCode) {
		this.empCode = empCode;
	}

	/**
	 * �x�����@�R�[�h���X�g���擾����
	 * 
	 * @return hohCodes
	 */
	public List<String> getHohCodes() {
		return hohCodes;
	}

	/**
	 * �x�����@�R�[�h���X�g��ݒ肷��
	 * 
	 * @param hohCodes
	 */
	public void setHohCodes(List<String> hohCodes) {
		this.hohCodes = hohCodes;
	}

}
