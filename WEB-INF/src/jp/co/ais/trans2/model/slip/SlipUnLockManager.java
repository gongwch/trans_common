package jp.co.ais.trans2.model.slip;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * “`•[”r‘¼§Œä‚ğ§Œä‚·‚éƒNƒ‰ƒX
 */
public interface SlipUnLockManager {

	/**
	 * “`•[‚ğŒŸõ‚·‚é.
	 * 
	 * @return List<SlipUnLock>
	 * @throws TException
	 */
	public List<SlipUnLock> get() throws TException;

	/**
	 * “`•[‚ğXV‚·‚é.<br>
	 * 
	 * @param list “`•[ƒNƒ‰ƒX
	 * @throws TException
	 */
	public void update(List<SlipUnLock> list) throws TException;

}
