package jp.co.ais.trans2.model.aprvrole;

import java.util.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * ���F�����}�X�^��������
 */
public class AprvRoleSearchCondition extends TransferBase implements Cloneable {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �R�[�h */
	protected String code = null;

	/** �R�[�h�O����v */
	protected String codeLike = null;

	/** ����like */
	protected String namesLike = null;

	/** ��������like */
	protected String namekLike = null;

	/** �J�n�R�[�h */
	protected String codeFrom = null;

	/** �I���R�[�h */
	protected String codeTo = null;

	/** �L������ */
	protected Date validTerm = null;

	/** ���[�U�[�R�[�h */
	protected String userCode = null;

	/** ���F�����O���[�v */
	private List<AprvRoleGroup> aprvGrpList;

	/**
	 * �N���[��
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public AprvRoleSearchCondition clone() {
		try {
			return (AprvRoleSearchCondition) super.clone();

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * ��ЃR�[�h�̎擾
	 * 
	 * @return companyCode ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ��ЃR�[�h�̐ݒ�
	 * 
	 * @param companyCode ��ЃR�[�h
	 */
	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
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
	 * �R�[�h�O����v��߂�
	 * 
	 * @return codeLike
	 */
	public String getCodeLike() {
		return codeLike;
	}

	/**
	 * �R�[�h�O����v��ݒ肷��
	 * 
	 * @param codeLike
	 */
	public void setCodeLike(String codeLike) {
		this.codeLike = codeLike;
	}

	/**
	 * ����like��߂�
	 * 
	 * @return namesLike
	 */
	public String getNamesLike() {
		return namesLike;
	}

	/**
	 * ����like��ݒ肷��
	 * 
	 * @param namesLike
	 */
	public void setNamesLike(String namesLike) {
		this.namesLike = namesLike;
	}

	/**
	 * ��������like��߂�
	 * 
	 * @return namekLike
	 */
	public String getNamekLike() {
		return namekLike;
	}

	/**
	 * ��������like��ݒ肷��
	 * 
	 * @param namekLike
	 */
	public void setNamekLike(String namekLike) {
		this.namekLike = namekLike;
	}

	/**
	 * �J�n�R�[�h��߂�
	 * 
	 * @return codeFrom
	 */
	public String getCodeFrom() {
		return codeFrom;
	}

	/**
	 * �J�n�R�[�h��ݒ肷��
	 * 
	 * @param codeFrom
	 */
	public void setCodeFrom(String codeFrom) {
		this.codeFrom = codeFrom;
	}

	/**
	 * �I���R�[�h��߂�
	 * 
	 * @return codeTo
	 */
	public String getCodeTo() {
		return codeTo;
	}

	/**
	 * �I���R�[�h��ݒ肷��
	 * 
	 * @param codeTo
	 */
	public void setCodeTo(String codeTo) {
		this.codeTo = codeTo;
	}

	/**
	 * �L�����Ԃ�߂�
	 * 
	 * @return validTerm
	 */
	public Date getValidTerm() {
		return validTerm;
	}

	/**
	 * �L�����Ԃ�ݒ肷��
	 * 
	 * @param validTerm
	 */
	public void setValidTerm(Date validTerm) {
		this.validTerm = validTerm;
	}

	/**
	 * ���[�U�[�R�[�h
	 * 
	 * @return ���[�U�[�R�[�h
	 */
	public String getUserCode() {
		return this.userCode;
	}

	/**
	 * ���[�U�[�R�[�h���w�肷��
	 * 
	 * @param userCode
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	public List<AprvRoleGroup> getAprvGrpList() {
		return aprvGrpList;
	}

	/**
	 * ���F�O���[�v���X�g���w��
	 * 
	 * @param aprvGrpList
	 */
	public void setAprvRoleGroupList(List<AprvRoleGroup> aprvGrpList) {
		this.aprvGrpList = aprvGrpList;
	}

}
