package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans2.common.client.TController;
import jp.co.ais.trans2.model.security.*;

/**
 * “`•[‚Ì”r‘¼§Œä
 * 
 * @author AIS
 */
public class SlipExclusiveControlMethod extends TController implements TExclusiveControlMethod {

	/** “`•[í•ÊƒŠƒXƒg */
	protected List<String> slipTypeList = new ArrayList<String>();

	/**
	 * ƒRƒ“ƒXƒgƒ‰ƒNƒ^.
	 * 
	 * @param prgInfo
	 * @param slipType
	 */
	public SlipExclusiveControlMethod(TClientProgramInfo prgInfo, String... slipType) {
		for (int i = 0; i < slipType.length; i++) {
			this.slipTypeList.add(slipType[i]);
		}

		this.setProgramInfo(prgInfo);

	}

	public void exclude() throws TException {
		request(SlipExclusiveManager.class, "unLockAll", slipTypeList);
	}

	public void cancelExclude() throws TException {
		request(SlipExclusiveManager.class, "unLockAll", slipTypeList);
	}

}
