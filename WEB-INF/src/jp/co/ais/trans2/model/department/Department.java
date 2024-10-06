package jp.co.ais.trans2.model.department;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.ac.*;
import jp.co.ais.trans2.model.*;

/**
 * ������
 * 
 * @author AIS
 */
public class Department extends TransferBase implements TReferable, AutoCompletable, FilterableEntity {

	/**
	 * @return �C���N�������g�T�[�`�\���l
	 */
	public String getDisplayText() {
		StringBuilder sb = new StringBuilder();
		sb.append(getCode());
		sb.append(" / ");
		sb.append(Util.avoidNull(getName()));
		sb.append(" / ");
		sb.append(Util.avoidNull(getNames()));

		return sb.toString();
	}

	@Override
	public String toString() {
		return getCode() + " " + getName();
	}

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String name = null;

	/** ���� */
	protected String names = null;

	/** �������� */
	protected String namek = null;

	/** �L�����ԊJ�n */
	protected Date dateFrom = null;

	/** �L�����ԏI�� */
	protected Date dateTo = null;

	/** �W�v���傩 */
	protected boolean sumDepartment = false;

	/** �g�D�R�[�h */
	protected String organizationCode = null;

	/** �g�D���� */
	protected String organizationName = null;

	/** ���僌�x�� */
	protected int level = -1;

	/** ��ʕ���R�[�h */
	protected String superiorDepartmentCode = null;

	/** �l��1 */
	protected int personnel1 = 0;

	/** �l��2 */
	protected int personnel2 = 0;

	/** ���ʐ� */
	protected BigDecimal floorSpace = null;

	/** �A�g1���g�p���邩 */
	protected boolean isUseIf1 = false;

	/** �A�g2���g�p���邩 */
	protected boolean isUseIf2 = false;

	/** �A�g3���g�p���邩 */
	protected boolean isUseIf3 = false;

	/** �A�g�R�[�h1 */
	protected String ifCode1 = null;

	/** �A�g����1 */
	protected String ifName1 = null;

	/** �A�g�R�[�h2 */
	protected String ifCode2 = null;

	/** �A�g����2 */
	protected String ifName2 = null;

	/** �A�g�R�[�h3 */
	protected String ifCode3 = null;

	/** �A�g����3 */
	protected String ifName3 = null;

	/**
	 * ��ʕ���R�[�h
	 * 
	 * @return ��ʕ���R�[�h
	 */
	public String getSuperiorDepartmentCode() {
		return superiorDepartmentCode;
	}

	/**
	 * ��ʕ���R�[�h
	 * 
	 * @param superiorDepartmentCode ��ʕ���R�[�h
	 */
	public void setSuperiorDepartmentCode(String superiorDepartmentCode) {
		this.superiorDepartmentCode = superiorDepartmentCode;
	}

	/**
	 * �W�v���傩
	 * 
	 * @return true:�W�v����
	 */
	public boolean isSumDepartment() {
		return sumDepartment;
	}

	/**
	 * �W�v���傩
	 * 
	 * @param sumDepartment true:�W�v����
	 */
	public void setSumDepartment(boolean sumDepartment) {
		this.sumDepartment = sumDepartment;
	}

	/**
	 * @return dateFrom��߂��܂��B
	 */
	public Date getDateFrom() {
		return dateFrom;
	}

	/**
	 * @param dateFrom dateFrom��ݒ肵�܂��B
	 */
	public void setDateFrom(Date dateFrom) {
		this.dateFrom = dateFrom;
	}

	/**
	 * @return dateTo��߂��܂��B
	 */
	public Date getDateTo() {
		return dateTo;
	}

	/**
	 * @param dateTo dateTo��ݒ肵�܂��B
	 */
	public void setDateTo(Date dateTo) {
		this.dateTo = dateTo;
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
	 * @return name��߂��܂��B
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name name��ݒ肵�܂��B
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return namek��߂��܂��B
	 */
	public String getNamek() {
		return namek;
	}

	/**
	 * @param namek namek��ݒ肵�܂��B
	 */
	public void setNamek(String namek) {
		this.namek = namek;
	}

	/**
	 * @return names��߂��܂��B
	 */
	public String getNames() {
		return names;
	}

	/**
	 * @param names names��ݒ肵�܂��B
	 */
	public void setNames(String names) {
		this.names = names;
	}

	/**
	 * ���僌�x��
	 * 
	 * @return ���僌�x����߂��܂��B
	 */
	public int getLevel() {
		return level;
	}

	/**
	 * ���僌�x��
	 * 
	 * @param level ���僌�x����ݒ肵�܂��B
	 */
	public void setLevel(int level) {
		this.level = level;
	}

	/**
	 * �g�D�R�[�h
	 * 
	 * @return �g�D�R�[�h��߂��܂��B
	 */
	public String getOrganizationCode() {
		return organizationCode;
	}

	/**
	 * �g�D�R�[�h
	 * 
	 * @param organizationCode �g�D�R�[�h��ݒ肵�܂��B
	 */
	public void setOrganizationCode(String organizationCode) {
		this.organizationCode = organizationCode;
	}

	/**
	 * �g�D����
	 * 
	 * @return �g�D���̂�߂��܂��B
	 */
	public String getOrganizationName() {
		return organizationName;
	}

	/**
	 * �g�D����
	 * 
	 * @param organizationName �g�D���̂�ݒ肵�܂��B
	 */
	public void setOrganizationName(String organizationName) {
		this.organizationName = organizationName;
	}

	/**
	 * @return ���ʐς�߂��܂��B
	 */
	public BigDecimal getFloorSpace() {
		return floorSpace;
	}

	/**
	 * @param floorSpace ���ʐς�ݒ肵�܂��B
	 */
	public void setFloorSpace(BigDecimal floorSpace) {
		this.floorSpace = floorSpace;
	}

	/**
	 * @return �l��1��߂��܂��B
	 */
	public int getPersonnel1() {
		return personnel1;
	}

	/**
	 * @param personnel1 �l��1��ݒ肵�܂��B
	 */
	public void setPersonnel1(int personnel1) {
		this.personnel1 = personnel1;
	}

	/**
	 * @return �l��2��߂��܂��B
	 */
	public int getPersonnel2() {
		return personnel2;
	}

	/**
	 * @param personnel2 �l��2��ݒ肵�܂��B
	 */
	public void setPersonnel2(int personnel2) {
		this.personnel2 = personnel2;
	}

	/**
	 * @return ifCode1��߂��܂��B
	 */
	public String getIfCode1() {
		return ifCode1;
	}

	/**
	 * @param ifCode1 ifCode1��ݒ肵�܂��B
	 */
	public void setIfCode1(String ifCode1) {
		this.ifCode1 = ifCode1;
	}

	/**
	 * @return ifName1��߂��܂��B
	 */
	public String getIfName1() {
		return ifName1;
	}

	/**
	 * @param ifName1 ifName1��ݒ肵�܂��B
	 */
	public void setIfName1(String ifName1) {
		this.ifName1 = ifName1;
	}

	/**
	 * @return ifCode2��߂��܂��B
	 */
	public String getIfCode2() {
		return ifCode2;
	}

	/**
	 * @param ifCode2 ifCode2��ݒ肵�܂��B
	 */
	public void setIfCode2(String ifCode2) {
		this.ifCode2 = ifCode2;
	}

	/**
	 * @return ifName2��߂��܂��B
	 */
	public String getIfName2() {
		return ifName2;
	}

	/**
	 * @param ifName2 ifName2��ݒ肵�܂��B
	 */
	public void setIfName2(String ifName2) {
		this.ifName2 = ifName2;
	}

	/**
	 * @return ifCode3��߂��܂��B
	 */
	public String getIfCode3() {
		return ifCode3;
	}

	/**
	 * @param ifCode3 ifCode3��ݒ肵�܂��B
	 */
	public void setIfCode3(String ifCode3) {
		this.ifCode3 = ifCode3;
	}

	/**
	 * @return ifName3��߂��܂��B
	 */
	public String getIfName3() {
		return ifName3;
	}

	/**
	 * @param ifName3 ifName3��ݒ肵�܂��B
	 */
	public void setIfName3(String ifName3) {
		this.ifName3 = ifName3;
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
