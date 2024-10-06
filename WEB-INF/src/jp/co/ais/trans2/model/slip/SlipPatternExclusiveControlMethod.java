package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.security.*;

/**
 * 伝票パターンの排他制御
 */
public class SlipPatternExclusiveControlMethod extends TController implements TExclusiveControlMethod {

	/** 伝票種別リスト */
	protected List<String> slipTypeList = new ArrayList<String>();

	/**
	 * コンストラクタ.
	 * 
	 * @param prgInfo
	 * @param slipType
	 */
	public SlipPatternExclusiveControlMethod(TClientProgramInfo prgInfo, String... slipType) {
		for (int i = 0; i < slipType.length; i++) {
			this.slipTypeList.add(slipType[i]);
		}

		this.setProgramInfo(prgInfo);

	}

	/**
	 * @see jp.co.ais.trans2.model.security.TExclusiveControlMethod#exclude()
	 */
	public void exclude() throws TException {
		request(SlipExclusiveManager.class, "unLockPatternAll", slipTypeList);
	}

	/**
	 * @see jp.co.ais.trans2.model.security.TExclusiveControlMethod#cancelExclude()
	 */
	public void cancelExclude() throws TException {
		request(SlipExclusiveManager.class, "unLockPatternAll", slipTypeList);
	}
}
