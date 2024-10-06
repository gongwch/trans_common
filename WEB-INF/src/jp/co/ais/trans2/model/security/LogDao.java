package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;

/**
 * ÉçÉOÇ…ä÷Ç∑ÇÈDao
 * @author AIS
 *
 */
public interface LogDao {

	/**
	 * ÉçÉOÇìoò^Ç∑ÇÈ
	 * @param log
	 * @throws TException
	 */
	public void entry(Log log) throws TException;

}
