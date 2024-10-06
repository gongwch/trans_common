package jp.co.ais.trans.common.except;

import javax.swing.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.message.*;

/**
 * �N���C�A���g�T�C�h�G���[�n���h���[
 */
public class ClientErrorHandler implements Thread.UncaughtExceptionHandler {

	/**
	 * �G���[�̃n���h�����O
	 * 
	 * @param t �����X���b�h
	 * @param e �G���[
	 */
	public void uncaughtException(Thread t, Throwable e) {

		if (e instanceof OutOfMemoryError) {
			// ���[�U���̎擾.
			String error = MessageUtil.convertMessage(TClientLoginInfo.getInstance().getUserLanguage(), "S00001");
			JOptionPane.showMessageDialog(null, error + e.toString());
		}
	}

	/**
	 * �G���[�n���h�����O
	 * 
	 * @param e �ΏۃG���[
	 */
	public static void handledException(Throwable e) {

		for (int i = 0; e != null && i < 5; i++) {
			ClientLogger.error("", e);
			e = e.getCause();
		}
	}
}