package jp.co.ais.trans2.common.ui;

import java.util.*;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.ui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.security.*;

/**
 * TAppletUI
 */
public class TAppletUI extends TApplet implements Runnable {

	/** メインTAppletUI */
	public static TAppletUI instance = null;

	/** 画面横サイズ */
	protected int width;

	/** 画面縦サイズ */
	protected int height;

	/** ログインCTRL */
	protected TAppletLoginCtrl loginCtrl = null;

	/** 指定プログラム */
	protected TController controller = null;

	/** 指定プログラムの画面 */
	protected TPanelBusiness view = null;

	/**
	 * 初期設定とLook & Feel設定
	 */
	@Override
	public void init() {

		try {
			System.gc();

			// 画面構築
			TUIManager.setUI(this);

			String url = getParameter("trans_url");
			ClientConfig.setBaseAddress(url);
			jp.co.ais.trans2.common.config.ClientConfig.setWeb(true);

			TMainCtrl main = TMainCtrl.getInstance();
			if (main == null) {
				main = new TMainCtrl();
				main.selectedProgram = new TreeMap<String, TAppletClientBase>();
				main.frameProgram = new TreeMap<String, TPanelBusiness>();
				main.frames = new TreeMap<String, TFrame>();
				TMainCtrl.instance = main;
			}

			String token = getParameter("token");
			String[] arr = UTF8EncryptUtil.getArray(token);

			if (arr != null && arr.length > 2) {

				// ログイン処理
				String companyCode = arr[0];
				String userCode = arr[1];
				String pw = arr[2];

				// 会社情報、ユーザ情報作成
				loginCtrl = new TAppletLoginCtrl();
				loginCtrl.login(companyCode, userCode, pw);

				// プログラム取得＆起動
				String ldName = getParameter("trans_program_load_name");
				Class clazz = Class.forName(ldName);

				if (clazz != null) {

					String prgCode = getParameter("trans_program_code");
					String prgName = getParameter("trans_program_name");
					loginCtrl.setProgramInfo(prgCode, prgName);

					controller = (TController) clazz.newInstance();

					// 排他制御
					if (controller instanceof TExclusive) {
						TExclusiveControlMethod ec = ((TExclusive) controller).getExclusiveControlMethod();
						try {
							ec.exclude();

						} catch (Exception e) {
							controller.errorHandler(view, e);
							return;
						}
					}

					controller.setProgramInfo(loginCtrl.getProgramInfo());
					controller.start();

					view = controller.getPanel();

					this.width = view.getWidth();
					this.height = view.getHeight();

					add(view);

					loginCtrl.log("IN");
				}

				showStatus("");

				instance = this;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * Appletを閉じる際に呼ばれる.
	 * 
	 * @see java.applet.Applet#stop()
	 */
	@Override
	public void destroy() {
		super.destroy();

		ClientLogger.debug("Applet destroy");

		if (this.controller != null && this.controller instanceof TExclusive) {
			try {
				((TExclusive) this.controller).getExclusiveControlMethod().cancelExclude();
			} catch (TException e) {
				controller.errorHandler(view, e);
			}

			if (loginCtrl != null) {
				loginCtrl.log("OUT");
			}
		}

		System.gc();
	}

	/**
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// 無し
	}
}