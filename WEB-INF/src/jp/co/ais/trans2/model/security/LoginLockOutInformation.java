package jp.co.ais.trans2.model.security;

import java.util.Date;
import jp.co.ais.trans.common.dt.TransferBase;

/**
 * ���b�N�A�E�g���
 * @author AIS
 *
 */
public class LoginLockOutInformation extends TransferBase {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** ���[�U�[�R�[�h */
	protected String userCode = null;

	/** ���O�C�����s�� */
	protected int failedCount = 0;

	/** �ŏI���O�C�����s�^�C���X�^���v */
	protected Date failedTimestamp = null;

	public String getCompanyCode() {
		return companyCode;
	}

	public void setCompanyCode(String companyCode) {
		this.companyCode = companyCode;
	}

	public int getFailedCount() {
		return failedCount;
	}

	public void setFailedCount(int failedCount) {
		this.failedCount = failedCount;
	}

	public Date getFailedTimestamp() {
		return failedTimestamp;
	}

	public void setFailedTimestamp(Date failedTimestamp) {
		this.failedTimestamp = failedTimestamp;
	}

	public String getUserCode() {
		return userCode;
	}

	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

}
