package jp.co.ais.trans2.model.department;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * ����̌�������
 * 
 * @author AIS
 */
public class DepartmentSearchCondition extends TransferBase implements Cloneable, FilterableCondition, OPLoginCondition {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �g�D�R�[�h */
	protected String organizationCode = null;

	/** ��ʕ��� */
	protected String superiorDepartmentCode = null;

	/** �W�v������܂߂邩 */
	protected boolean sumDepartment = false;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** ����like */
	protected String namesLike = null;

	/** ��������like */
	protected String namekLike = null;

	/**
	 * �K�w���x���B�}�C�i�X�l�̏ꍇ�͎w�薳���B
	 */
	protected int level = -1;

	/** �L������ */
	protected Date validTerm = null;

	/** �ŏI�X�V���� */
	protected Date lastUpdateDate = null;

	/** ���݌��� */
	protected int nowCount = 0;

	/** �R�[�h(����) */
	protected List<String> departmentCodeList = new LinkedList<String>();

	/** �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p) */
	protected List<String> companyCodeList = null;

	/** �A�g1���g�p���邩 */
	protected boolean isUseIf1 = false;

	/** �A�g2���g�p���邩 */
	protected boolean isUseIf2 = false;

	/** �A�g3���g�p���邩 */
	protected boolean isUseIf3 = false;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public DepartmentSearchCondition clone() {
		try {
			return (DepartmentSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * @return codeFrom��߂��܂��B
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * @param codeFrom codeFrom��ݒ肵�܂��B
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * @return codeTo��߂��܂��B
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * @param codeTo codeTo��ݒ肵�܂��B
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * @return companyCode��߂��܂��B
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * @param companyCode companyCode��ݒ肵�܂��B
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * @return validTerm��߂��܂��B
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * @param validTerm validTerm��ݒ肵�܂��B
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
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
	 * @return String
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * @param organizationCode
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * @return boolean
	 */
	public boolean isSumDepartment() {
		return sumDepartment;
	}

	/**
	 * @param sumDepartment
	 */
	public void setSumDepartment(boolean sumDepartment) {
		this.sumDepartment = sumDepartment;
	}

	/**
	 * @return int
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * @param level
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * @param level
	 */
	public void setLevel(IndicationLevel level) {
		this.setLevel(level.value);
	}

	/**
	 * @param level
	 */
	public void setDownLevel(IndicationLevel level) {
		this.setLevel(level.value - 1);
	}

	/**
	 * @return String
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * @return String
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * @return String
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * @param namesLike
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * @return String
	 */
	public String getSuperiorDepartmentCode() {
		return superiorDepartmentCode;
	}

	/**
	 * @param superiorDepartmentCode
	 */
	public void setSuperiorDepartmentCode(String superiorDepartmentCode) {
		this.superiorDepartmentCode = superiorDepartmentCode;
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
	 * ����R�[�h(����)�̎擾
	 * 
	 * @return ����R�[�h(����)
	 */
	public List<String> getCodeList() {
		return this.departmentCodeList;
	}

	/**
	 * ����R�[�h(����)�̎擾
	 * 
	 * @return ����R�[�h(����)
	 */
	public String[] getDepartmentCodeList() {
		if (departmentCodeList.isEmpty()) {
			return null;
		}

		return departmentCodeList.toArray(new String[departmentCodeList.size()]);
	}

	/**
	 * ����R�[�h(����)�̐ݒ�
	 * 
	 * @param departmentCodeList ����R�[�h(����)
	 */
	public void setDepartmentCodeList(String[] departmentCodeList) {
		addDepartmentCode(departmentCodeList);
	}

	/**
	 * ����R�[�h(����)�̐ݒ�
	 * 
	 * @param list ����R�[�h(����)
	 */
	public void addDepartmentCode(String... list) {
		for (String departmentCode : list) {
			this.departmentCodeList.add(departmentCode);
		}
	}

	/**
	 * ����R�[�h(����)�̃N���A
	 */
	public void clearDepartmentCodeList() {
		departmentCodeList.clear();
	}

	/**
	 * �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p)�̎擾
	 * 
	 * @return companyCodeList �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p)
	 */
	public List<String> getCompanyCodeList() {
		return companyCodeList;
	}

	/**
	 * �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p)�̐ݒ�
	 * 
	 * @param companyCodeList �Ώۉ�Ѓ��X�g(getRef�̏ꍇ�̂ݎg�p)
	 */
	public void setCompanyCodeList(List<String> companyCodeList) {
		this.companyCodeList = companyCodeList;
	}

	/**
	 * @return boolean
	 */
	public boolean isUseIf1() {
		return isUseIf1;
	}

	/**
	 * @param isUseIf1
	 */
	public void setUseIf1(boolean isUseIf1) {
		this.isUseIf1 = isUseIf1;
	}

	/**
	 * @return boolean
	 */
	public boolean isUseIf2() {
		return isUseIf2;
	}

	/**
	 * @param isUseIf2
	 */
	public void setUseIf2(boolean isUseIf2) {
		this.isUseIf2 = isUseIf2;
	}

	/**
	 * @return boolean
	 */
	public boolean isUseIf3() {
		return isUseIf3;
	}

	/**
	 * @param isUseIf3
	 */
	public void setUseIf3(boolean isUseIf3) {
		this.isUseIf3 = isUseIf3;
	}
}
