package jp.co.ais.trans.common.client.http;

import java.applet.*;

import jp.co.ais.trans.common.log.*;
import netscape.javascript.*;

/**
 * ブラウザのコンテンツDOMアクセスとJavaScript実行クラス.
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
	 * @param app 動作アプレット
	 */
	public TAccessJScript(Applet app) {
		super();
		this.app = app;
	}

	/**
	 * constractor.
	 * 
	 * @param app 動作アプレット
	 * @param funcName JScript function名
	 * @param funcArgs JScript function引数
	 */
	public TAccessJScript(Applet app, String funcName, Object[] funcArgs) {
		super();
		this.app = app;
		this.funcName = funcName;
		this.funcArgs = funcArgs;
	}

	/**
	 * アプレットの設定.
	 * 
	 * @param app 動作アプレット
	 */
	public void setApplet(Applet app) {
		this.app = app;
	}

	/**
	 * ブラウザのコンテンツの値を取得.
	 * 
	 * @param name Documentパス (document.以降を'.'繋ぎで渡す)
	 * @return コンテンツの値
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
	 * ブラウザのコンテンツの値を設定.
	 * 
	 * @param name Documentパス (document.以降を'.'繋ぎで渡す)
	 * @param value コンテンツの値
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
	 * ThreadでJScriptを実行.
	 * 
	 * @param funcName_ JScript function名
	 * @param funcArgs_ JScript function引数
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
	 * ThreadでJScriptを実行.
	 * 
	 * @param app_ 動作アプレット
	 * @param funcName_ JScript function名
	 * @param funcArgs_ JScript function引数
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
	 * JScriptを実行.(single thread)
	 * 
	 * @param funcName_ JScript function名
	 * @param funcArgs_ JScript function引数
	 * @return JScript return value.
	 */
	public String executeJScriptFunction(String funcName_, String[] funcArgs_) {
		if (this.app == null || funcName_ == null) {
			return null;
		}
		this.funcName = funcName_;
		this.funcArgs = funcArgs_;

		/**
		 * Appletの存在するwindowのDOMを取得する.
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
	 * ThreadでJScriptを実行.(call from thread)
	 */
	public void executeJScriptFunction() {
		if (this.app == null || this.funcName == null) {
			return;
		}

		/**
		 * Appletの存在するwindowのDOMを取得する.
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
	 * JScriptをthreadで実行.
	 */
	@Override
	public void run() {

		this.executeJScriptFunction();
	}
}
