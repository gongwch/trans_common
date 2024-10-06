package jp.co.ais.trans.common.except;

import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.message.*;
import jp.co.ais.trans.common.util.*;

/**
 * �T�[�o�[�T�C�h�G���[�n���h���[
 */
public class ServerErrorHandler {

	/**
	 * �V�X�e���G���[���̏���
	 * 
	 * @param e �ΏۃG���[
	 */
	public static void handledException(Throwable e) {

		String lang = null;
		try {
			lang = ServerConfig.getSystemLanguageCode();
		} catch (Exception e1) {
			//
		}

		if (Util.isNullOrEmpty(lang)) {
			lang = Locale.getDefault().getLanguage();
		}

		if (e instanceof TException) {
			TException ex = (TException) e;
			ServerLogger.error(MessageUtil.convertMessage(lang, ex.getMessage(), ex.getMessageArgs()), ex);
		} else if (e instanceof TRuntimeException) {
			TRuntimeException ex = (TRuntimeException) e;
			ServerLogger.error(MessageUtil.convertMessage(lang, ex.getMessage(), ex.getMessageArgs()), ex);
		} else {
			ServerLogger.error("ServerError", e);
		}

	}
}
