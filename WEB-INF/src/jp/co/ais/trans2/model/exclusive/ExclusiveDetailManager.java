package jp.co.ais.trans2.model.exclusive;

import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * ”r‘¼–¾×ŒŸõ‚ğ§Œä‚·‚éƒNƒ‰ƒX
 */
public interface ExclusiveDetailManager {

	/**
	 * ”r‘¼–¾×‚ğŒŸõ‚·‚é.
	 * 
	 * @return ”r‘¼–¾×ˆê——
	 * @throws TException
	 */
	public List<ExclusiveDetail> get() throws TException;

	/**
	 * ”r‘¼–¾×ˆê——(ƒGƒNƒZƒ‹)‚ğ•Ô‚·
	 * 
	 * @return ”r‘¼–¾×ˆê——
	 * @throws TException
	 */
	public byte[] getExcel() throws TException;

}
