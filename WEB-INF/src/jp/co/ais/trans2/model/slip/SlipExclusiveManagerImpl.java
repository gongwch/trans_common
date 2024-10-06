package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.model.TModel;
import jp.co.ais.trans2.model.security.*;

/**
 * “`•[”r‘¼‚ÌÀ‘•
 * 
 * @author AIS
 */
public class SlipExclusiveManagerImpl extends TModel implements SlipExclusiveManager {

	/**
	 * ”r‘¼‰ğœ‚·‚é
	 * 
	 * @param slipTypeList “`•[í•Ê
	 * @throws TException
	 */
	public void unLockAll(List<String> slipTypeList) throws TException {

		for (String slipType : slipTypeList) {
			// “`•[”r‘¼‰ğœ
			SlipManager slipManager = (SlipManager) getComponent(SlipManager.class);
			slipManager.unlockAll(slipType);
		}

		// c‚s‚Ì”r‘¼‚ğ‰ğœ
		CodeExclusiveManager exclusiveManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);
		exclusiveManager.cancelExclude();
	}

	/**
	 * ƒpƒ^[ƒ“”r‘¼‰ğœ‚·‚é
	 * 
	 * @param slipTypeList “`•[í•Ê
	 */
	public void unLockPatternAll(List<String> slipTypeList) throws TException {

		for (String slipType : slipTypeList) {
			// “`•[ƒpƒ^[ƒ“”r‘¼‰ğœ
			SlipManager slipManager = (SlipManager) getComponent(SlipManager.class);
			slipManager.unlockPatternAll(slipType);
		}
	}

}
