package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.dt.TransferBase;

/**
 * �p�X���[�h�|���V�[
 * @author AIS
 *
 */
public class PasswordPolicy extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** �p�X���[�h���b�N�A�E�g���B�܂ł̎��s���e�񐔁B 0�̏ꍇ�A���b�N�A�E�g���Ȃ��B */
	protected int lockOutAllowedCount = 0; 

	/** ���b�N�A�E�g��A���b�N�����܂ł̎���(��)�B  0�̏ꍇ�A���b�N�������Ȃ��B */
	protected int lockOutReleaseTime = 0;

	/** �Œ�p�X���[�h�� */
	protected int passwordMinLength = 0;

	/** �p�X���[�h�̗L������������Ŏw��B 0�̏ꍇ�A�L�������Ȃ��B */
	protected int passwordTerm = 0;

	/**
	 * �L���͈́F1�`4�B
	 * �p�X���[�h�ɉp�啶���A�p�������A�����A�L���̕�����̂����A����ނ��܂܂Ȃ���΂����Ȃ������w�肷��B
	 */
	protected int complicateLevel = 0;

	/**
	 * �p�X���[�h�����}�X�^�ɕێ�����p�X���[�h���𐔂��w�肷��B
	 * �ێ�����Ă���p�X���[�h�͐V�K�ݒ�ɗ��p�s�Ƃ���B
	 */
	protected int historyMaxCount = 0;

	/**�p�X���[�h�L�������̉����O�Ɋ����؂ꃁ�b�Z�[�W�ʒm���s�����B0�̏ꍇ�A�ʒm���Ȃ� */
	protected int daysNoticePasswordTermComeThrough = 0;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getComplicateLevel() {
		return complicateLevel;
	}

	public void setComplicateLevel(int complicateLevel) {
		this.complicateLevel = complicateLevel;
	}

	public int getDaysNoticePasswordTermComeThrough() {
		return daysNoticePasswordTermComeThrough;
	}

	public void setDaysNoticePasswordTermComeThrough(
			int daysNoticePasswordTermComeThrough) {
		this.daysNoticePasswordTermComeThrough = daysNoticePasswordTermComeThrough;
	}

	public int getHistoryMaxCount() {
		return historyMaxCount;
	}

	public void setHistoryMaxCount(int historyMaxCount) {
		this.historyMaxCount = historyMaxCount;
	}

	public int getLockOutAllowedCount() {
		return lockOutAllowedCount;
	}

	public void setLockOutAllowedCount(int lockOutAllowedCount) {
		this.lockOutAllowedCount = lockOutAllowedCount;
	}

	public int getLockOutReleaseTime() {
		return lockOutReleaseTime;
	}

	public void setLockOutReleaseTime(int lockOutReleaseTime) {
		this.lockOutReleaseTime = lockOutReleaseTime;
	}

	public int getPasswordMinLength() {
		return passwordMinLength;
	}

	public void setPasswordMinLength(int passwordMinLength) {
		this.passwordMinLength = passwordMinLength;
	}

	public int getPasswordTerm() {
		return passwordTerm;
	}

	public void setPasswordTerm(int passwordTerm) {
		this.passwordTerm = passwordTerm;
	}

}
