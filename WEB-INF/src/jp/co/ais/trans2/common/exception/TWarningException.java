package jp.co.ais.trans2.common.exception;

import jp.co.ais.trans.common.except.TException;

/**
 * åxçêException
 * 
 * @author AIS
 */
public class TWarningException extends TException {

	/**
	 * 
	 */
	public TWarningException() {
		//
	}

	/**
	 * @param message
	 */
	public TWarningException(String message) {
		super(message);
	}

	/**
	 * @param messageId
	 * @param message
	 */
	public TWarningException(String messageId, String message) {
		super(messageId, message);
	}

}
