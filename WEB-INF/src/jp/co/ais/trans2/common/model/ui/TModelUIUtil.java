package jp.co.ais.trans2.common.model.ui;

import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * ���iUI�p���[�e�B���e�B
 */
public class TModelUIUtil {

	/**
	 * ���b�Z�[�W�擾
	 * 
	 * @param messageId ���b�Z�[�WID
	 * @return �ϊ���̃��b�Z�[�W
	 */
	public static String getWord(String messageId) {
		return MessageUtil.getWord(TLoginInfo.getUser().getLanguage(), messageId);
	}

	/**
	 * ���b�Z�[�W(����)�擾
	 * 
	 * @param messageId ���b�Z�[�WID
	 * @return �ϊ���̃��b�Z�[�W
	 */
	public static String getShortWord(String messageId) {
		return MessageUtil.getShortWord(TLoginInfo.getUser().getLanguage(), messageId);
	}

}
