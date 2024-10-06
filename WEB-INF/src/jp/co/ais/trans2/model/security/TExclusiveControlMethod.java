package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;

/**
 * �r��������@
 * @author AIS
 *
 */
public interface TExclusiveControlMethod {

	/**
	 * �r������
	 * @throws TException
	 */
	public void exclude() throws TException;

	/**
	 * �r������������
	 * @throws TException
	 */
	public void cancelExclude() throws TException;

}
