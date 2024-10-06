package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.common.client.TController;

/**
 * 排他制御テーブル単位の排他制御
 * 
 * @author AIS
 */
public class TCodeExclusiveControlMethod extends TController implements TExclusiveControlMethod {

	/**
	 * コンストラクタ.
	 * 
	 * @param prgInfo
	 */
	public TCodeExclusiveControlMethod(TClientProgramInfo prgInfo) {
		this.setProgramInfo(prgInfo);
	}

	public void exclude() throws TException {
		request(CodeExclusiveManager.class, "cancelExclude");
	}

	public void cancelExclude() throws TException {
		request(CodeExclusiveManager.class, "cancelExclude");
	}

}
