package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * 部品UI用ユーティリティ
 */
public class TModelUIUtil {

	/**
	 * メッセージ取得
	 * 
	 * @param messageId メッセージID
	 * @return 変換後のメッセージ
	 */
	public static String getWord(String messageId) {
		return MessageUtil.getWord(TLoginInfo.getUser().getLanguage(), messageId);
	}

	/**
	 * メッセージ(略称)取得
	 * 
	 * @param messageId メッセージID
	 * @return 変換後のメッセージ
	 */
	public static String getShortWord(String messageId) {
		return MessageUtil.getShortWord(TLoginInfo.getUser().getLanguage(), messageId);
	}

}
