package message;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans2.common.client.*;

/**
 * 
 */
public class MessageTestUtil extends TController {

	/**  */
	public static final String EN = "en";

	/**  */
	public static final String JA = "ja";

	/**  */
	private static TController ctrl;

	/**
	 * @param messageID
	 * @param bindIds
	 */
	public static void printlnMessages(String messageID, Object... bindIds) {
		TClientLoginInfo.getInstance().setUserLanguage(EN);
		System.out.println(getMessages(EN, messageID, bindIds));
		TClientLoginInfo.getInstance().setUserLanguage(JA);
		System.out.println(getMessages(JA, messageID, bindIds));
	}
	
	public static String  getMessages(String lang, String messageID, Object... bindIds) {
		ctrl = new MessageTestUtil();
		TClientLoginInfo.getInstance().setUserLanguage(lang);
		return ctrl.getMessage(messageID, bindIds);
	}


	/**
	 * @param messageID
	 */
	protected static void printlnWord(String messageID) {
		ctrl = new MessageTestUtil();
		TClientLoginInfo.getInstance().setUserLanguage(EN);
		System.out.println(ctrl.getWord(messageID));
		TClientLoginInfo.getInstance().setUserLanguage(JA);
		System.out.println(ctrl.getWord(messageID));
	}
}
