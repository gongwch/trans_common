package jp.co.ais.trans2.model.program;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * �v���O�����̌�������
 * 
 * @author AIS
 */
public class ProgramSearchCondition extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** ����like */
	protected String namesLike = null;

	/** ��������like */
	protected String namekLike = null;

	/** �L������ */
	protected Date validTerm = null;

	/** �V�X�e���敪 */
	protected String systemCode = null;

	/** ���s�\�ȃv���O���� */
	protected boolean executable = true;

	/** ���j���[�p�v���O���� */
	protected boolean notExecutable = true;

	/** �v���O�������[�� */
	protected String programRoleCode = null;

	/** ����R�[�h */
	protected String lang = "";

	/** ���j���[�\���ȊO�̂� */
	protected boolean withoutMenu = false;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public ProgramSearchCondition clone() {
		try {
			return (ProgramSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * ����R�[�h�̎擾����
	 * 
	 * @return String
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * ����R�[�h�̐ݒ肷��
	 * 
	 * @param lang
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * ���j���[�p�v���O�����̎擾
	 * 
	 * @return true:���j���[�p�v���O����
	 */
	public boolean isNotExecutable() {
		return notExecutable;
	}

	/**
	 * ���j���[�p�v���O�����̐ݒ�
	 * 
	 * @param notExecutable true:���j���[�p�v���O����
	 */
	public void setNotExecutable(boolean notExecutable) {
		this.notExecutable = notExecutable;
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
	 * �R�[�h�O����v�̎擾
	 * 
	 * @return codeLike �R�[�h�O����v
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * �R�[�h�O����v�̐ݒ�
	 * 
	 * @param codeLike �R�[�h�O����v
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * ����like�̎擾
	 * 
	 * @return namesLike ����like
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * ����like�̐ݒ�
	 * 
	 * @param namesLike ����like
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * ��������like�̎擾
	 * 
	 * @return namekLike ��������like
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * ��������like�̐ݒ�
	 * 
	 * @param namekLike ��������like
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * �V�X�e���敪�̎擾
	 * 
	 * @return systemCode �V�X�e���敪
	 */
	public String getSystemCode() {
		return systemCode;
	}

	/**
	 * �V�X�e���敪�̐ݒ�
	 * 
	 * @param systemCode �V�X�e���敪
	 */
	public void setSystemCode(String systemCode) {
		this.systemCode = systemCode;
	}

	/**
	 * ���s�\�ȃv���O�����̎擾
	 * 
	 * @return executable ���s�\�ȃv���O����
	 */
	public boolean isExecutable() {
		return executable;
	}

	/**
	 * ���s�\�ȃv���O�����̐ݒ�
	 * 
	 * @param executable ���s�\�ȃv���O����
	 */
	public void setExecutable(boolean executable) {
		this.executable = executable;
	}

	/**
	 * �v���O�������[���̎擾
	 * 
	 * @return programRoleCode �v���O�������[��
	 */
	public String getProgramRoleCode() {
		return programRoleCode;
	}

	/**
	 * �v���O�������[���̐ݒ�
	 * 
	 * @param programRoleCode �v���O�������[��
	 */
	public void setProgramRoleCode(String programRoleCode) {
		this.programRoleCode = programRoleCode;
	}

	/**
	 * ���j���[�\���ȊO�݂̂̎擾
	 * 
	 * @return withoutMenu ���j���[�\���ȊO�̂�
	 */
	public boolean isWithoutMenu() {
		return withoutMenu;
	}

	/**
	 * ���j���[�\���ȊO�݂̂̐ݒ�
	 * 
	 * @param withoutMenu ���j���[�\���ȊO�̂�
	 */
	public void setWithoutMenu(boolean withoutMenu) {
		this.withoutMenu = withoutMenu;
	}

}
