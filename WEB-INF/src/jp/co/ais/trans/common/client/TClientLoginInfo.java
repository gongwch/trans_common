package jp.co.ais.trans.common.client;

import javax.print.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.info.*;
import jp.co.ais.trans.common.util.*;

/**
 * Appletで共有するログイン情報取得.
 */
public final class TClientLoginInfo {

	/** server session ID */
	private static String sessionID = "";

	/** ユーザ情報 */
	private static TUserInfo userInfo;

	/** ユーザ情報アクセス */
	private static TRequestUserInfo reqUserInfo;

	/** 画面キャプチャ印刷利用プリンタ */
	private static PrintService printService;

	static {
		userInfo = new TUserInfo();
		reqUserInfo = new TRequestUserInfo();

		// 仮ログインユーザ(開発用)
		userInfo.setCompanyCode(Util.avoidNull(ClientConfig.getDummyComp()));
		userInfo.setUserCode(Util.avoidNull(ClientConfig.getDummyUser()));
		userInfo.setUserLanguage(System.getProperty("user.language"));
	}

	/**
	 * ユーザ情報の取得
	 * 
	 * @return ユーザ情報
	 */
	public static TUserInfo getInstance() {

		return userInfo;
	}

	/**
	 * 会社情報の取得
	 * 
	 * @return 会社情報
	 */
	public static TCompanyInfo getCompanyInfo() {
		return userInfo.getCompanyInfo();
	}

	/**
	 * セッションID取得
	 * 
	 * @return セッションID
	 */
	public static String getSessionID() {
		return TClientLoginInfo.sessionID;
	}

	/**
	 * セッションID取得setter
	 * 
	 * @param sessionID セッションID
	 */
	public static void setSessionID(String sessionID) {
		TClientLoginInfo.sessionID = sessionID;
	}

	/**
	 * ログイン情報再取得
	 * 
	 * @throws TException
	 */
	public static void reflesh() throws TException {

		String compCode = userInfo.getCompanyCode();
		String userCode = userInfo.getUserCode();

		reqUserInfo.request(compCode, userCode);
	}

	/**
	 * PrintServiceを取得する
	 * 
	 * @return PrintService
	 */
	public static PrintService getPrintService() {
		return printService;
	}

	/**
	 * PrintServiceを設定する.
	 * 
	 * @param service PrintService
	 */
	public static void setPrintService(PrintService service) {
		printService = service;
	}
}
