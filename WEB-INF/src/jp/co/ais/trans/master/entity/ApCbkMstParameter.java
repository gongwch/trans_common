package jp.co.ais.trans.master.entity;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;

/**
 * ��s�����}�X�^�����p�����[�^
 */
public class ApCbkMstParameter extends TransferBase {

	/** ��ЃR�[�h */
	private String kaiCode;

	/** ��s�����R�[�h */
	private String cbkCode;

	/** ��s�����R�[�h�i�����܂������j */
	private String likeCbkCode;

	/** ��s���i�����܂������j */
	private String likeNameS;

	/** �����ԍ��i�����܂������j */
	private String likeCbkYknNo;

	/** �L�����ԓ��t */
	private Date termBasisDate;

	/** �Ј�FB�敪 */
	private boolean empFbKbn;

	/** �ЊOFB�敪 */
	private boolean outFbKbn;

	/** ��s�����R�[�h */
	private List<String> cbkCodes;

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
	 * ��s�����R�[�h�i�����܂������j��ݒ肷��
	 * 
	 * @return ��s�����R�[�h�i�����܂������j
	 */
	public String getLikeCbkCode() {

		if (Util.isNullOrEmpty(likeCbkCode)) {
			return "";
		}

		return likeCbkCode;
	}

	/**
	 * ��s�����R�[�h�i�����܂������j���擾����
	 * 
	 * @param likeCbkCode
	 */
	public void setLikeCbkCode(String likeCbkCode) {
		this.likeCbkCode = likeCbkCode;
	}

	/**
	 * ��s���i�����܂������j���擾����
	 * 
	 * @return ��s���i�����܂������j
	 */
	public String getLikeNameS() {

		if (Util.isNullOrEmpty(likeNameS)) {
			return "";
		}

		return likeNameS;

	}

	/**
	 * ��s���i�����܂������j��ݒ肷��
	 * 
	 * @param likeNameS
	 */
	public void setLikeNameS(String likeNameS) {
		this.likeNameS = likeNameS;
	}

	/**
	 * �����ԍ��i�����܂������j���擾����
	 * 
	 * @return �����ԍ��i�����܂������j
	 */
	public String getLikeCbkYknNo() {

		if (Util.isNullOrEmpty(likeCbkYknNo)) {
			return "";
		}

		return likeCbkYknNo;
	}

	/**
	 * �����ԍ��i�����܂������j��ݒ肷��
	 * 
	 * @param likeCbkYknNo
	 */
	public void setLikeCbkYknNo(String likeCbkYknNo) {
		this.likeCbkYknNo = likeCbkYknNo;
	}

	/**
	 * �Ј�FB�敪���擾����
	 * 
	 * @return �Ј�FB�敪
	 */
	public boolean getEmpFbKbn() {
		return empFbKbn;
	}

	/**
	 * �Ј�FB�敪��ݒ肷��
	 * 
	 * @param empFbKbn
	 */
	public void setEmpFbKbn(boolean empFbKbn) {
		this.empFbKbn = empFbKbn;
	}

	/**
	 * �ЊOFB�敪���擾����
	 * 
	 * @return �ЊOFB�敪
	 */
	public boolean getOutFbKbn() {
		return outFbKbn;
	}

	/**
	 * �ЊOFB�敪��ݒ肷��
	 * 
	 * @param outFbKbn
	 */
	public void setOutFbKbn(boolean outFbKbn) {
		this.outFbKbn = outFbKbn;
	}

	/**
	 * ��s�����R�[�h���擾����
	 * 
	 * @return ��s�����R�[�h
	 */
	public String getCbkCode() {
		return cbkCode;
	}

	/**
	 * ��s�����R�[�h���w�肷��
	 * 
	 * @param cbkCode
	 */
	public void setCbkCode(String cbkCode) {
		this.cbkCode = cbkCode;
	}

	/**
	 * ��s�����R�[�h�擾
	 * 
	 * @return cbkCodes
	 */
	public List<String> getCbkCodes() {
		return cbkCodes;
	}

	/**
	 * ��s�����R�[�h�ݒ�
	 * 
	 * @param cbkCodes
	 */
	public void setCbkCodes(List<String> cbkCodes) {
		this.cbkCodes = cbkCodes;
	}
}
