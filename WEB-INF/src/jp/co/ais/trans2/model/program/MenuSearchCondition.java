package jp.co.ais.trans2.model.program;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.define.*;

/**
 * �v���O�����̌�������
 * 
 * @author AIS
 */
public class MenuSearchCondition extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �O���[�v�R�[�h */
	protected String grpCode = null;

	/** �e�v���O�����R�[�h */
	protected String parentCode = null;

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String name = null;

	/** ���j���[�敪 */
	protected MenuDivision menuDivision = null;

	/** �v���O�������[�� */
	protected String programRoleCode = null;

	/** �v���O�������[����������s�v�Atrue:����s�v */
	protected boolean withoutProgramRole = false;

	/** �q�m�[�h���Ȃ��^�u�擾�Atrue:�擾 */
	protected boolean withBlank = false;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public MenuSearchCondition clone() {
		try {
			return (MenuSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * ��ЃR�[�h���擾����B
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h��ݒ肷��B
	 * 
	 * @param companyCode
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	/**
	 * �O���[�v�R�[�h���擾����B
	 * 
	 * @return �O���[�v�R�[�h
	 */
	public String getGrpCode() {
		return grpCode;
	}

	/**
	 * �O���[�v�R�[�h��ݒ肷��B
	 * 
	 * @param grpCode
	 */
	public void setGrpCode(String grpCode) {
		this.grpCode = grpCode;
	}

	/**
	 * �e�v���O�����R�[�h���擾����B
	 * 
	 * @return �e�v���O�����R�[�h
	 */
	public String getParentCode() {
		return parentCode;
	}

	/**
	 * �e�v���O�����R�[�h��ݒ肷��B
	 * 
	 * @param parentCode
	 */
	public void setParentCode(String parentCode) {
		this.parentCode = parentCode;
	}

	/**
	 * ���j���[�R�[�h���擾����B
	 * 
	 * @return ���j���[�R�[�h
	 */
	public String getCode() {
		return code;
	}

	/**
	 * ���j���[�R�[�h��ݒ肷��B
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * ���j���[���̂��擾����B
	 * 
	 * @return ���j���[����
	 */
	public String getName() {
		return name;
	}

	/**
	 * ���j���[���̂�ݒ肷��B
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * ���j���[�敪���擾����B
	 * 
	 * @return ���j���[�敪
	 */
	public MenuDivision getMenuDivision() {
		return menuDivision;
	}

	/**
	 * ���j���[�敪��ݒ肷��B
	 * 
	 * @param menuDivision
	 */
	public void setMenuDivision(MenuDivision menuDivision) {
		this.menuDivision = menuDivision;
	}

	/**
	 * �v���O�������[�����擾����B
	 * 
	 * @return �v���O�������[��
	 */
	public String getProgramRoleCode() {
		return programRoleCode;
	}

	/**
	 * �v���O�������[����ݒ肷��B
	 * 
	 * @param programRoleCode
	 */
	public void setProgramRoleCode(String programRoleCode) {
		this.programRoleCode = programRoleCode;
	}

	/**
	 * �v���O�������[����������s�v�̎擾
	 * 
	 * @return withoutProgramRole �v���O�������[����������s�v�Atrue:����s�v
	 */
	public boolean isWithoutProgramRole() {
		return withoutProgramRole;
	}

	/**
	 * �v���O�������[����������s�v�̐ݒ�
	 * 
	 * @param withoutProgramRole �v���O�������[����������s�v�Atrue:����s�v
	 */
	public void setWithoutProgramRole(boolean withoutProgramRole) {
		this.withoutProgramRole = withoutProgramRole;
	}

	/**
	 * �q�m�[�h���Ȃ��^�u�擾�Atrue:�擾�̎擾
	 * 
	 * @return withBlank �q�m�[�h���Ȃ��^�u�擾�Atrue:�擾
	 */
	public boolean isWithBlank() {
		return withBlank;
	}

	/**
	 * �q�m�[�h���Ȃ��^�u�擾�Atrue:�擾�̐ݒ�
	 * 
	 * @param withBlank �q�m�[�h���Ȃ��^�u�擾�Atrue:�擾
	 */
	public void setWithBlank(boolean withBlank) {
		this.withBlank = withBlank;
	}

}
