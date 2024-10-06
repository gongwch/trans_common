package jp.co.ais.trans.common.server.servlet;

/**
 * ���O�C���ؖ�
 */
public final class LoginCertification {

	/** UID */
	private static final long serialVersionUID = 1L;

	/** �F�؏�� true:�F�� false:�F�؂���Ă��Ȃ� */
	private boolean certify = false;

	/** ��ЃR�[�h */
	private String companyCode;

	/** ���[�U�R�[�h */
	private String userCode;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param compCode ��Ѓ}�X�^
	 * @param userCode ���[�U�}�X�^
	 */
	public LoginCertification(String compCode, String userCode) {
		this.certify = true;

		this.companyCode = compCode;
		this.userCode = userCode;
	}

	/**
	 * �F�؏�ԎQ��.
	 * 
	 * @return true:���F���
	 */
	public boolean isCertified() {
		return this.certify;
	}

	/**
	 * ��ЃR�[�h
	 * 
	 * @return ��ЃR�[�h
	 */
	public String getCompanyCode() {
		return companyCode;
	}

	/**
	 * ���[�U�R�[�h
	 * 
	 * @return ���[�U�R�[�h
	 */
	public String getUserCode() {
		return userCode;
	}
}
