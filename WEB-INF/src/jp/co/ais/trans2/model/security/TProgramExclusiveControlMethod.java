package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.common.client.TController;

/**
 * �v���O�����P�ʂ̔r������
 * 
 * @author AIS
 */
public class TProgramExclusiveControlMethod extends TController implements TExclusiveControlMethod {

	/** ��ЃR�[�h */
	protected String companyCode = null;

	/** BAT_ID */
	protected String processID = null;

	/** �v���O�����R�[�h */
	protected String programCode = null;

	/** ���[�U�[�R�[�h */
	protected String userCode = null;

	/**
	 * @param companyCode ��ЃR�[�h
	 * @param userCode ���[�U�[�R�[�h
	 * @param programCode �v���O�����R�[�h
	 */
	public TProgramExclusiveControlMethod(String companyCode, String userCode, String programCode) {
		this.companyCode = companyCode;
		this.userCode = userCode;
		this.programCode = programCode;
		this.processID = programCode;
	}

	/**
	 * @param companyCode ��ЃR�[�h
	 * @param processID ����ID
	 * @param userCode ���[�U�[�R�[�h
	 * @param programCode �v���O�����R�[�h
	 */
	public TProgramExclusiveControlMethod(String companyCode, String userCode, String programCode, String processID) {
		this.companyCode = companyCode;
		this.processID = processID;
		this.userCode = userCode;
		this.programCode = programCode;
	}

	public void exclude() throws TException {
		request(ProgramExclusiveManager.class, "exclude", companyCode, userCode, programCode, processID);
	}

	public void cancelExclude() throws TException {
		request(ProgramExclusiveManager.class, "cancelExclude", companyCode, userCode, processID);
	}

}
