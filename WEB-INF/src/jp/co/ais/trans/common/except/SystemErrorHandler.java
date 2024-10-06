package jp.co.ais.trans.common.except;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.util.*;

/**
 * �V�X�e���G���[�n���h���[<br>
 * �N���C�A���g�T�C�h�������T�[�o�T�C�h�������s���ȏꍇ�ɗ��p.<br>
 * ���V�X�e�����ʈȊO�͗��p���Ȃ�����.
 */
public class SystemErrorHandler {

	/**
	 * �G���[�n���h�����O
	 * 
	 * @param e �ΏۃG���[
	 */
	public static void handledException(Throwable e) {

		if (Util.isNullOrEmpty(TClientLoginInfo.getSessionID())) {
			ServerErrorHandler.handledException(e);
		} else {
			ClientErrorHandler.handledException(e);
		}
	}
}