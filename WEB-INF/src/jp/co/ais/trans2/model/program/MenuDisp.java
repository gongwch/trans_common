package jp.co.ais.trans2.model.program;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans2.define.*;

/**
 * ���j���[�\��Entity
 * 
 * @author AIS
 */
public class MenuDisp extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �O���[�v��� */
	protected SystemClassification systemClassification = null;

	/** �e�v���O�����R�[�h */
	protected String parentCode = null;

	/** �R�[�h */
	protected String code = null;

	/** ���� */
	protected String name = null;

	/** ���j���[�敪 */
	protected MenuDivision menuDivision = null;

	/** �\������ */
	protected int dispIndex = 0;

	/** �v���O�����O���[�v�ɑ�����v���O�����Q */
	protected Program program = null;

	/**
	 * �m�[�h�ɕ\�����閼�̂��擾����
	 * 
	 * @return �m�[�h�ɕ\�����閼��
	 */
	public String getViewName() {
		return this.toString();
	}

	/**
	 * �I�u�W�F�N�g�̃I���W�i�������\����Ԃ�.
	 * 
	 * @return ����
	 */
	@Override
	public String toString() {
		return this.name;
	}

	/**
	 * �G���e�B�e�B�̔�r�Ɏg�p����B
	 * 
	 * @param obj ��r
	 * @return boolean
	 */
	public boolean equals(MenuDisp obj) {
		if (super.equals(obj)) {
			return true;
		} else {
			return this.code.equals(obj.getCode());
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
	 * �O���[�v�����擾����B
	 * 
	 * @return �O���[�v���
	 */
	public SystemClassification getSystemClassification() {
		return systemClassification;
	}

	/**
	 * �O���[�v����ݒ肷��B
	 * 
	 * @param systemClassification
	 */
	public void setSystemClassification(SystemClassification systemClassification) {
		this.systemClassification = systemClassification;
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
	 * ���j���[���ǂ����𔻒肷��
	 * 
	 * @return true:���j���[�Afalse:�m�[�h
	 */
	public boolean isMenu() {
		return MenuDivision.MENU.equals(menuDivision);
	}

	/**
	 * �\���������擾����B
	 * 
	 * @return �\������
	 */
	public int getDispIndex() {
		return dispIndex;
	}

	/**
	 * �\��������ݒ肷��B
	 * 
	 * @param dispIndex
	 */
	public void setDispIndex(int dispIndex) {
		this.dispIndex = dispIndex;
	}

	/**
	 * �v���O�����O���[�v�����擾����B
	 * 
	 * @return �v���O�����O���[�v
	 */
	public Program getProgram() {
		return program;
	}

	/**
	 * �v���O�����O���[�v��ݒ肷��B
	 * 
	 * @param program
	 */
	public void setProgram(Program program) {
		this.program = program;
	}

}
