package jp.co.ais.trans2.model.port;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * Port�̌�������
 * 
 * @author AIS
 */
public class PortSearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** Port�R�[�h */
	protected String code = null;

	/** Port�R�[�h�O����v */
	protected String codeLike = null;

	/** Port�R�[�hFrom */
	protected String codeFrom = null;

	/** Port�R�[�hTo */
	protected String codeTo = null;

	/** ����like */
	protected String namesLike = null;

	/** ��������like */
	protected String namekLike = null;

	/** �L������ */
	protected Date validTerm = null;

	/** UNLOCODE */
	protected String UNLOCODE = null;

	/** COU_CODE */
	protected String COU_CODE = null;

	/** Port Name */
	protected String nameLike = null;

	/** COU_NAME */
	protected String couName = null;

	/** (S)ECA�t���O */
	protected int S_ECA_FLG = -1;

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/** �R�[�h���X�g */
	protected List<String> codeList;

	/** ���݌��� */
	protected int nowCount = 0;

	/** �N(�T�}�[�^�C���p) */
	protected int YEAR = -1;

	/** REGION�R�[�h */
	protected String REGION_CODE = null;

	/** SUMMER_TIME�����(LCT) */
	protected Date summerTimeBaseDate = null;

	/** true:���݂��Ă���SUMMER_TIME�f�[�^�̂� */
	protected boolean onlySummerData = false;

	/** true:���q */
	protected boolean local = false;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public PortSearchCondition clone() {
		try {
			return (PortSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return code��߂��܂��B
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code code��ݒ肵�܂��B
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ��ЃR�[�h���擾���܂��B
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h��ݒ肵�܂��B
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * Port�R�[�hFrom���擾���܂��B
	 * 
	 * @return Port�R�[�hFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * Port�R�[�hFrom��ݒ肵�܂��B
	 * 
	 * @param codeFrom Port�R�[�hFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * Port�R�[�hTo���擾���܂��B
	 * 
	 * @return Port�R�[�hTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * Port�R�[�hTo��ݒ肵�܂��B
	 * 
	 * @param codeTo Port�R�[�hTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return namesLike��߂��܂��B
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * @param namesLike namesLike��ݒ肵�܂��B
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * @return ��ЃR�[�h�O����v��߂��܂��B
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike ��ЃR�[�h�O����v��ݒ肵�܂��B
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * ��������like���擾���܂��B
	 * 
	 * @return ��������like
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * ��������like��ݒ肵�܂��B
	 * 
	 * @param namekLike ��������like
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * �L���������擾���܂��B
	 * 
	 * @return validTerm �L��������߂��܂��B
	 */
	public Date getValidTerm() {
		return this.validTerm;
	}

	/**
	 * �L��������ݒ肵�܂��B
	 * 
	 * @param validTerm
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * @see jp.co.ais.trans2.model.FilterableCondition#getCodeList()
	 */
	public List<String> getCodeList() {
		return codeList;
	}

	/**
	 * @param codeList
	 */
	public void setCodeList(List<String> codeList) {
		this.codeList = codeList;
	}

	/**
	 * @return UNLOCODE
	 */
	public String getUNLOCODE() {
		return UNLOCODE;
	}

	/**
	 * @param UNLOCODE
	 */
	public void setUNLOCODE(String UNLOCODE) {
		this.UNLOCODE = UNLOCODE;
	}

	/**
	 * @return COU_CODE
	 */
	public String getCOU_CODE() {
		return COU_CODE;
	}

	/**
	 * @param COU_CODE
	 */
	public void setCOU_CODE(String COU_CODE) {
		this.COU_CODE = COU_CODE;
	}

	/**
	 * @return Port Name
	 */
	public String getNameLike() {
		return nameLike;
	}

	/**
	 * @param nameLike
	 */
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	/**
	 * @return couName
	 */
	public String getCouName() {
		return couName;
	}

	/**
	 * @param couName
	 */
	public void setCouName(String couName) {
		this.couName = couName;
	}

	/**
	 * (S)ECA�t���O�̎擾
	 * 
	 * @return S_ECA_FLG (S)ECA�t���O
	 */
	public int getS_ECA_FLG() {
		return S_ECA_FLG;
	}

	/**
	 * (S)ECA�t���O�̐ݒ�
	 * 
	 * @param S_ECA_FLG (S)ECA�t���O
	 */
	public void setS_ECA_FLG(int S_ECA_FLG) {
		this.S_ECA_FLG = S_ECA_FLG;
	}

	/**
	 * �ŏI�X�V�����̎擾
	 * 
	 * @return lastUpdateDate �ŏI�X�V����
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * �ŏI�X�V�����̐ݒ�
	 * 
	 * @param lastUpdateDate �ŏI�X�V����
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * ���݌����̎擾
	 * 
	 * @return nowCount ���݌���
	 */
	public int getNowCount() {
		return nowCount;
	}

	/**
	 * ���݌����̐ݒ�
	 * 
	 * @param nowCount ���݌���
	 */
	public void setNowCount(int nowCount) {
		this.nowCount = nowCount;
	}

	/**
	 * �N(�T�}�[�^�C���p)�̎擾
	 * 
	 * @return YEAR �N(�T�}�[�^�C���p)
	 */
	public int getYEAR() {
		return YEAR;
	}

	/**
	 * �N(�T�}�[�^�C���p)�̐ݒ�
	 * 
	 * @param YEAR �N(�T�}�[�^�C���p)
	 */
	public void setYEAR(int YEAR) {
		this.YEAR = YEAR;
	}

	/**
	 * REGION�R�[�h�̎擾
	 * 
	 * @return REGION_CODE REGION�R�[�h
	 */
	public String getREGION_CODE() {
		return REGION_CODE;
	}

	/**
	 * REGION�R�[�h�̐ݒ�
	 * 
	 * @param REGION_CODE REGION�R�[�h
	 */
	public void setREGION_CODE(String REGION_CODE) {
		this.REGION_CODE = REGION_CODE;
	}

	/**
	 * SUMMER_TIME�����(LCT)�̎擾
	 * 
	 * @return summerTimeBaseDate SUMMER_TIME�����(LCT)
	 */
	public Date getSummerTimeBaseDate() {
		return summerTimeBaseDate;
	}

	/**
	 * SUMMER_TIME�����(LCT)�̐ݒ�
	 * 
	 * @param summerTimeBaseDate SUMMER_TIME�����(LCT)
	 */
	public void setSummerTimeBaseDate(Date summerTimeBaseDate) {
		this.summerTimeBaseDate = summerTimeBaseDate;
	}

	/**
	 * true:���݂��Ă���SUMMER_TIME�f�[�^�݂̂̎擾
	 * 
	 * @return onlySummerData true:���݂��Ă���SUMMER_TIME�f�[�^�̂�
	 */
	public boolean isOnlySummerData() {
		return onlySummerData;
	}

	/**
	 * true:���݂��Ă���SUMMER_TIME�f�[�^�݂̂̐ݒ�
	 * 
	 * @param onlySummerData true:���݂��Ă���SUMMER_TIME�f�[�^�̂�
	 */
	public void setOnlySummerData(boolean onlySummerData) {
		this.onlySummerData = onlySummerData;
	}

	/**
	 * true:���q�̎擾
	 * 
	 * @return local true:���q
	 */
	public boolean isLocal() {
		return local;
	}

	/**
	 * true:���q�̐ݒ�
	 * 
	 * @param local true:���q
	 */
	public void setLocal(boolean local) {
		this.local = local;
	}

}
