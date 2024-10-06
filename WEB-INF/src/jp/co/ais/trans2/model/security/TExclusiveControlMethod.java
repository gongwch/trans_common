package jp.co.ais.trans2.model.security;

import jp.co.ais.trans.common.except.TException;

/**
 * îrëºêßå‰ï˚ñ@
 * @author AIS
 *
 */
public interface TExclusiveControlMethod {

	/**
	 * îrëºÇ∑ÇÈ
	 * @throws TException
	 */
	public void exclude() throws TException;

	/**
	 * îrëºÇâèúÇ∑ÇÈ
	 * @throws TException
	 */
	public void cancelExclude() throws TException;

}
