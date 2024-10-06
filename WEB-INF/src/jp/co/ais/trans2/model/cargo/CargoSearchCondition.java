package jp.co.ais.trans2.model.cargo;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.*;

/**
 * Cargo�}�X�^��������
 */
public class CargoSearchCondition extends TransferBase implements FilterableCondition, OPLoginCondition, Cloneable {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** Cargo�R�[�h */
	protected String code = null;

	/** Cargo�R�[�h�O����v */
	protected String codeLike = null;

	/** Cargo�R�[�hFrom */
	protected String codeFrom = null;

	/** Cargo�R�[�hTo */
	protected String codeTo = null;
	
	/**Category */
	protected String category = null;

	/** �ݕ���like */
	protected String nameLike = null;

	/** �O���[�v��like */
	protected String groupNameLike = null;

	/** �L������ */
	protected Date validTerm = null;

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/** ���݌��� */
	protected int nowCount = 0;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public CargoSearchCondition clone() {
		try {
			return (CargoSearchCondition) super.clone();

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
	 * Cargo�R�[�hFrom���擾���܂��B
	 * 
	 * @return Cargo�R�[�hFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * Cargo�R�[�hFrom��ݒ肵�܂��B
	 * 
	 * @param codeFrom Cargo�R�[�hFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * Cargo�R�[�hTo���擾���܂��B
	 * 
	 * @return Cargo�R�[�hTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * Cargo�R�[�hTo��ݒ肵�܂��B
	 * 
	 * @param codeTo Cargo�R�[�hTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return namesLike��߂��܂��B
	 */
	public String getNamesLike() {
		return nameLike;
	}

	/**
	 * @param namesLike namesLike��ݒ肵�܂��B
	 */
	public void setNamesLike(String namesLike) {
		this.nameLike = namesLike;
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
		return nameLike;
	}

	/**
	 * ��������like��ݒ肵�܂��B
	 * 
	 * @param namekLike ��������like
	 */
	public void setNamekLike(String namekLike) {
		this.nameLike = namekLike;
	}
	
	/**
	 * Category���擾���܂��B
	 * 
	 * @return ��������like
	 */
	public String getCategory() {
		return category;
	}

	/**
	 * Category��ݒ肵�܂��B
	 * 
	 * @param category ��������like
	 */
	public void setCategory(String category) {
		this.category = category;
	}

	/**
	 * �L���������擾���܂��B
	 * 
	 * @return validTerm �L��������߂��܂��B
	 */
	public Date getValidTerm() {
		return validTerm;
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
		return null;
	}

	/**
	 * ��������like���擾���܂��B
	 * 
	 * @return ��������like
	 */
	public String getNameLike() {
		return nameLike;
	}

	/**
	 * ��������like��ݒ肵�܂��B
	 * 
	 * @param nameLike ��������like
	 */
	public void setNameLike(String nameLike) {
		this.nameLike = nameLike;
	}

	/**
	 * Gets the group name like.
	 * 
	 * @return the search group name
	 */
	public String getGroupNameLike() {
		return groupNameLike;
	}

	/**
	 * Sets the search group name like.
	 * 
	 * @param groupNameLike
	 */
	public void setGroupNameLike(String groupNameLike) {
		this.groupNameLike = groupNameLike;
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

}
