package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * “`•[”r‘¼‚Ì”r‘¼ŠÇ—
 * 
 * @author AIS
 */
public interface SlipExclusiveManager {

	/**
	 * ”r‘¼‰ğœ‚·‚é
	 * 
	 * @param slipTypeList “`•[í•Ê
	 * @throws TException
	 */
	public void unLockAll(List<String> slipTypeList) throws TException;

	/**
	 * ƒpƒ^[ƒ“”r‘¼‰ğœ‚·‚é
	 * 
	 * @param slipTypeList “`•[í•Ê
	 * @throws TException
	 */
	public void unLockPatternAll(List<String> slipTypeList) throws TException;
}
