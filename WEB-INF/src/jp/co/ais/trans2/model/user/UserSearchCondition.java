package jp.co.ais.trans2.model.user;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * ���[�U�[�̌�������
 * 
 * @author AIS
 */
public class UserSearchCondition extends TransferBase implements Cloneable {

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

	/** �p�X���[�h */
	protected String password = null;

	/** �Ј��R�[�h */
	protected String employeeCode = null;

	/** �V�X�e���敪���X�g */
	protected List<String> sysKbnList = null;

	/** ����R�[�h */
	protected String lang = "";

	/** �A�N�Z�X�����t���O�P */
	protected int AccessPermissionFlag1 = -1;

	/** �A�N�Z�X�����t���O�Q */
	protected int AccessPermissionFlag2 = -1;

	/** �A�N�Z�X�����t���O�R */
	protected int AccessPermissionFlag3 = -1;

	/** �A�N�Z�X�����t���O�S */
	protected int AccessPermissionFlag4 = -1;

	/** �A�N�Z�X�����t���O�T */
	protected int AccessPermissionFlag5 = -1;

	/** �A�N�Z�X�����t���O�U */
	protected int AccessPermissionFlag6 = -1;

	/** �A�N�Z�X�����t���O�V */
	protected int AccessPermissionFlag7 = -1;

	/** �A�N�Z�X�����t���O�W */
	protected int AccessPermissionFlag8 = -1;

	/** �A�N�Z�X�����t���O�X */
	protected int AccessPermissionFlag9 = -1;

	/** �A�N�Z�X�����t���O�P�O */
	protected int AccessPermissionFlag10 = -1;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public UserSearchCondition clone() {
		try {
			return (UserSearchCondition) super.clone();

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
	 * @return password��߂��܂��B
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password password��ݒ肵�܂��B
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * codeLike���擾����B
	 * 
	 * @return String codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * codeLike��ݒ肷��B
	 * 
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * namekLike���擾����B
	 * 
	 * @return String namekLike
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * namekLike��ݒ肷��B
	 * 
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * namesLike���擾����B
	 * 
	 * @return String namesLike
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * namesLike��ݒ肷��B
	 * 
	 * @param namesLike
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * �Ј��R�[�h
	 * 
	 * @return �Ј��R�[�h
	 */
	public String getEmployeeCode() {
		return employeeCode;
	}

	/**
	 * �Ј��R�[�h
	 * 
	 * @param employeeCode �Ј��R�[�h
	 */
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}

	/**
	 * ����R�[�h���擾����B
	 * 
	 * @return ����R�[�h
	 */
	public String getLang() {
		return lang;
	}

	/**
	 * ����R�[�h��ݒ肷��B
	 * 
	 * @param lang
	 */
	public void setLang(String lang) {
		this.lang = lang;
	}

	/**
	 * �A�N�Z�X�����t���O�P�̎擾
	 * 
	 * @return AccessPermissionFlag1 �A�N�Z�X�����t���O�P
	 */
	public int getAccessPermissionFlag1() {
		return AccessPermissionFlag1;
	}

	/**
	 * �A�N�Z�X�����t���O�P�̐ݒ�
	 * 
	 * @param AccessPermissionFlag1 �A�N�Z�X�����t���O�P
	 */
	public void setAccessPermissionFlag1(int AccessPermissionFlag1) {
		this.AccessPermissionFlag1 = AccessPermissionFlag1;
	}

	/**
	 * �A�N�Z�X�����t���O�Q�̎擾
	 * 
	 * @return AccessPermissionFlag2 �A�N�Z�X�����t���O�Q
	 */
	public int getAccessPermissionFlag2() {
		return AccessPermissionFlag2;
	}

	/**
	 * �A�N�Z�X�����t���O�Q�̐ݒ�
	 * 
	 * @param AccessPermissionFlag2 �A�N�Z�X�����t���O�Q
	 */
	public void setAccessPermissionFlag2(int AccessPermissionFlag2) {
		this.AccessPermissionFlag2 = AccessPermissionFlag2;
	}

	/**
	 * �A�N�Z�X�����t���O�R�̎擾
	 * 
	 * @return AccessPermissionFlag3 �A�N�Z�X�����t���O�R
	 */
	public int getAccessPermissionFlag3() {
		return AccessPermissionFlag3;
	}

	/**
	 * �A�N�Z�X�����t���O�R�̐ݒ�
	 * 
	 * @param AccessPermissionFlag3 �A�N�Z�X�����t���O�R
	 */
	public void setAccessPermissionFlag3(int AccessPermissionFlag3) {
		this.AccessPermissionFlag3 = AccessPermissionFlag3;
	}

	/**
	 * �A�N�Z�X�����t���O�S�̎擾
	 * 
	 * @return AccessPermissionFlag4 �A�N�Z�X�����t���O�S
	 */
	public int getAccessPermissionFlag4() {
		return AccessPermissionFlag4;
	}

	/**
	 * �A�N�Z�X�����t���O�S�̐ݒ�
	 * 
	 * @param AccessPermissionFlag4 �A�N�Z�X�����t���O�S
	 */
	public void setAccessPermissionFlag4(int AccessPermissionFlag4) {
		this.AccessPermissionFlag4 = AccessPermissionFlag4;
	}

	/**
	 * �A�N�Z�X�����t���O�T�̎擾
	 * 
	 * @return AccessPermissionFlag5 �A�N�Z�X�����t���O�T
	 */
	public int getAccessPermissionFlag5() {
		return AccessPermissionFlag5;
	}

	/**
	 * �A�N�Z�X�����t���O�T�̐ݒ�
	 * 
	 * @param AccessPermissionFlag5 �A�N�Z�X�����t���O�T
	 */
	public void setAccessPermissionFlag5(int AccessPermissionFlag5) {
		this.AccessPermissionFlag5 = AccessPermissionFlag5;
	}

	/**
	 * �A�N�Z�X�����t���O�U�̎擾
	 * 
	 * @return AccessPermissionFlag6 �A�N�Z�X�����t���O�U
	 */
	public int getAccessPermissionFlag6() {
		return AccessPermissionFlag6;
	}

	/**
	 * �A�N�Z�X�����t���O�U�̐ݒ�
	 * 
	 * @param AccessPermissionFlag6 �A�N�Z�X�����t���O�U
	 */
	public void setAccessPermissionFlag6(int AccessPermissionFlag6) {
		this.AccessPermissionFlag6 = AccessPermissionFlag6;
	}

	/**
	 * �A�N�Z�X�����t���O�V�̎擾
	 * 
	 * @return AccessPermissionFlag7 �A�N�Z�X�����t���O�V
	 */
	public int getAccessPermissionFlag7() {
		return AccessPermissionFlag7;
	}

	/**
	 * �A�N�Z�X�����t���O�V�̐ݒ�
	 * 
	 * @param AccessPermissionFlag7 �A�N�Z�X�����t���O�V
	 */
	public void setAccessPermissionFlag7(int AccessPermissionFlag7) {
		this.AccessPermissionFlag7 = AccessPermissionFlag7;
	}

	/**
	 * �A�N�Z�X�����t���O�W�̎擾
	 * 
	 * @return AccessPermissionFlag8 �A�N�Z�X�����t���O�W
	 */
	public int getAccessPermissionFlag8() {
		return AccessPermissionFlag8;
	}

	/**
	 * �A�N�Z�X�����t���O�W�̐ݒ�
	 * 
	 * @param AccessPermissionFlag8 �A�N�Z�X�����t���O�W
	 */
	public void setAccessPermissionFlag8(int AccessPermissionFlag8) {
		this.AccessPermissionFlag8 = AccessPermissionFlag8;
	}

	/**
	 * �A�N�Z�X�����t���O�X�̎擾
	 * 
	 * @return AccessPermissionFlag9 �A�N�Z�X�����t���O�X
	 */
	public int getAccessPermissionFlag9() {
		return AccessPermissionFlag9;
	}

	/**
	 * �A�N�Z�X�����t���O�X�̐ݒ�
	 * 
	 * @param AccessPermissionFlag9 �A�N�Z�X�����t���O�X
	 */
	public void setAccessPermissionFlag9(int AccessPermissionFlag9) {
		this.AccessPermissionFlag9 = AccessPermissionFlag9;
	}

	/**
	 * �A�N�Z�X�����t���O�P�O�̎擾
	 * 
	 * @return AccessPermissionFlag10 �A�N�Z�X�����t���O�P�O
	 */
	public int getAccessPermissionFlag10() {
		return AccessPermissionFlag10;
	}

	/**
	 * �A�N�Z�X�����t���O�P�O�̐ݒ�
	 * 
	 * @param AccessPermissionFlag10 �A�N�Z�X�����t���O�P�O
	 */
	public void setAccessPermissionFlag10(int AccessPermissionFlag10) {
		this.AccessPermissionFlag10 = AccessPermissionFlag10;
	}

	/**
	 * �V�X�e���敪���X�g�̎擾
	 * 
	 * @return sysKbnList �V�X�e���敪���X�g
	 */
	public List<String> getSysKbnList() {
		return sysKbnList;
	}

	/**
	 * �V�X�e���敪���X�g�̐ݒ�
	 * 
	 * @param sysKbnList �V�X�e���敪���X�g
	 */
	public void setSysKbnList(List<String> sysKbnList) {
		this.sysKbnList = sysKbnList;
	}

}
