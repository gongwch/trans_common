package jp.co.ais.trans2.common.exception;

import jp.co.ais.trans.common.except.TException;

/**
 * èÓïÒException
 * 
 * @author AIS
 */
public class TInformationException extends TException {

	/**
	 * 
	 */
	public TInformationException() {
		//
	}

	/**
	 * @param message
	 */
	public TInformationException(String message) {
		super(message);
	}

	/**
	 * @param messageId
	 * @param message
	 */
	public TInformationException(String messageId, String message) {
		super(messageId, message);
	}

}
