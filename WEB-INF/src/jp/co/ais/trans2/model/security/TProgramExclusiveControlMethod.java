package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.common.client.TController;

/**
 * プログラム単位の排他制御
 * 
 * @author AIS
 */
public class TProgramExclusiveControlMethod extends TController implements TExclusiveControlMethod {

	/** 会社コード */
	protected String companyCode = null;

	/** BAT_ID */
	protected String processID = null;

	/** プログラムコード */
	protected String programCode = null;

	/** ユーザーコード */
	protected String userCode = null;

	/**
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param programCode プログラムコード
	 */
	public TProgramExclusiveControlMethod(String companyCode, String userCode, String programCode) {
		this.companyCode = companyCode;
		this.userCode = userCode;
		this.programCode = programCode;
		this.processID = programCode;
	}

	/**
	 * @param companyCode 会社コード
	 * @param processID 処理ID
	 * @param userCode ユーザーコード
	 * @param programCode プログラムコード
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
