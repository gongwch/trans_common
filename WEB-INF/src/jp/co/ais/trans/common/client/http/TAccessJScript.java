package jp.co.ais.trans.common.client.http;

import java.applet.*;

import jp.co.ais.trans.common.log.*;
import netscape.javascript.*;

/**
 * �u���E�U�̃R���e���cDOM�A�N�Z�X��JavaScript���s�N���X.
 */
public class TAccessJScript extends Thread {

	/**  */
	private String funcName = null;

	/**  */
	private Object[] funcArgs = null;

	/**  */
	private Applet app = null;

	/**
	 * constractor.
	 */
	public TAccessJScript() {
		super();
	}

	/**
	 * constractor.
	 * 
	 * @param app ����A�v���b�g
	 */
	public TAccessJScript(Applet app) {
		super();
		this.app = app;
	}

	/**
	 * constractor.
	 * 
	 * @param app ����A�v���b�g
	 * @param funcName JScript function��
	 * @param funcArgs JScript function����
	 */
	public TAccessJScript(Applet app, String funcName, Object[] funcArgs) {
		super();
		this.app = app;
		this.funcName = funcName;
		this.funcArgs = funcArgs;
	}

	/**
	 * �A�v���b�g�̐ݒ�.
	 * 
	 * @param app ����A�v���b�g
	 */
	public void setApplet(Applet app) {
		this.app = app;
	}

	/**
	 * �u���E�U�̃R���e���c�̒l���擾.
	 * 
	 * @param name Document�p�X (document.�ȍ~��'.'�q���œn��)
	 * @return �R���e���c�̒l
	 * @throws Throwable
	 */
	public Object getBrowserDOM(String name) throws Throwable {

		if (!(this.app.getAppletContext() instanceof JSObject)) {
			return null;
		}

		JSObject win = (JSObject) this.app.getAppletContext();
		if (win == null) {
			return null;
		}

		JSObject doc = (JSObject) win.getMember("document");
		if (doc == null) {
			return null;
		}

		String[] arr = name.split("\\.");

		for (int cnt = 0; cnt < arr.length - 1; cnt++) {
			doc = (JSObject) doc.getMember(arr[cnt]);
		}

		Object obj = doc.getMember(arr[arr.length - 1]);

		return obj;
	}

	/**
	 * �u���E�U�̃R���e���c�̒l��ݒ�.
	 * 
	 * @param name Document�p�X (document.�ȍ~��'.'�q���œn��)
	 * @param value �R���e���c�̒l
	 * @throws Throwable
	 */
	public void setBrowserDOM(String name, String value) throws Throwable {

		if (!(this.app.getAppletContext() instanceof JSObject)) {
			return;
		}

		JSObject win = (JSObject) this.app.getAppletContext();
		if (win == null) {
			return;
		}

		JSObject doc = (JSObject) win.getMember("document");
		if (doc == null) {
			return;
		}

		String[] arr = name.split("\\.");

		for (int cnt = 0; cnt < arr.length - 1; cnt++) {
			doc = (JSObject) doc.getMember(arr[cnt]);
		}

		doc.setMember(arr[arr.length - 1], value);
	}

	/**
	 * Thread��JScript�����s.
	 * 
	 * @param funcName_ JScript function��
	 * @param funcArgs_ JScript function����
	 */
	public void executeJScriptFunctionOnThread(String funcName_, String[] funcArgs_) {
		this.funcName = funcName_;
		this.funcArgs = funcArgs_;

		try {
			this.run();
		} catch (Exception ex) {
			ClientLogger.error("executeJScript:" + ex.getMessage(), ex);

		} catch (Throwable t) {
			ClientLogger.error("executeJScript not exception:" + t.getMessage(), t);
		}
	}

	/**
	 * Thread��JScript�����s.
	 * 
	 * @param app_ ����A�v���b�g
	 * @param funcName_ JScript function��
	 * @param funcArgs_ JScript function����
	 */
	public void executeJScriptFunctionOnThread(Applet app_, String funcName_, String[] funcArgs_) {
		this.app = app_;
		this.funcName = funcName_;
		this.funcArgs = funcArgs_;

		try {
			this.run();

		} catch (Exception ex) {
			ClientLogger.error("executeJScript:" + ex.getMessage(), ex);
		} catch (Throwable t) {
			ClientLogger.error("executeJScript not exception:" + t.getMessage(), t);
		}
	}

	/**
	 * JScript�����s.(single thread)
	 * 
	 * @param funcName_ JScript function��
	 * @param funcArgs_ JScript function����
	 * @return JScript return value.
	 */
	public String executeJScriptFunction(String funcName_, String[] funcArgs_) {
		if (this.app == null || funcName_ == null) {
			return null;
		}
		this.funcName = funcName_;
		this.funcArgs = funcArgs_;

		/**
		 * Applet�̑��݂���window��DOM���擾����.
		 */
		if (!(this.app.getAppletContext() instanceof JSObject)) {
			return null;
		}

		JSObject win = (JSObject) this.app.getAppletContext();
		if (win == null) {
			return null;
		}

		/**
		 * Call JScript function in HTML page
		 */
		Object ret = win.call(this.funcName, this.funcArgs);

		if (ret == null) {
			return null;
		}

		return ret.toString();
	}

	/**
	 * Thread��JScript�����s.(call from thread)
	 */
	public void executeJScriptFunction() {
		if (this.app == null || this.funcName == null) {
			return;
		}

		/**
		 * Applet�̑��݂���window��DOM���擾����.
		 */
		if (!(this.app.getAppletContext() instanceof JSObject)) {
			return;
		}

		JSObject win = (JSObject) this.app.getAppletContext();
		if (win == null) {
			return;
		}

		/**
		 * Call JScript function in HTML page
		 */
		win.call(this.funcName, this.funcArgs);
	}

	/**
	 * JScript��thread�Ŏ��s.
	 */
	@Override
	public void run() {

		this.executeJScriptFunction();
	}
}
