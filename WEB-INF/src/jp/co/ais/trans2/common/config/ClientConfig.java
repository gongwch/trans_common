package jp.co.ais.trans2.common.config;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * クライアント側の設定管理クラス。<br>
 * クライアント側の設定情報は当該クラスを通じて取得すること。
 * 
 * @author AIS
 */
public class ClientConfig {

	/** JNLPプロパティ先頭 */
	public static String jnlpHeader = "jnlp.p.";

	/** クライアント設定情報 */
	protected static ResourceBundle bundle = ResourceBundle.getBundle("client");

	/**
	 * Web経由でアクセスする場合true。<br>
	 * 開発時はfalseに設定することでAPServerを起動すること無く開発できる。<br>
	 */
	protected static boolean web = true;

	/** メイン画面の高さ */
	protected static int mainViewHeight;

	/** メイン画面の幅 */
	protected static int mainViewWidth;

	/** バージョン */
	protected static String systemVersion;

	/** 任意指定のプロパティー */
	protected static Map<String, String> properties = null;

	static {

		// 初期化
		properties = new HashMap<String, String>();

		for (Map.Entry entry : System.getProperties().entrySet()) {
			String key = Util.avoidNull(entry.getKey());

			// jnlp先頭判定処理追加
			if (key.startsWith(jnlpHeader)) {
				key = key.replace(jnlpHeader, "");
			}
			setProperty(key, Util.avoidNull(entry.getValue()));
		}

		// Web経由でアクセスするか
		web = Boolean.parseBoolean(getProperty("system.type.web"));

		// メイン画面のサイズ
		mainViewWidth = 800;
		String width = getProperty("applet.width");
		if (!Util.isNullOrEmpty(width)) {
			mainViewWidth = Integer.parseInt(width);
		}

		mainViewHeight = 600;
		String height = getProperty("applet.height");
		if (!Util.isNullOrEmpty(height)) {
			mainViewHeight = Integer.parseInt(height);
		}

		// バージョン
		try {
			systemVersion = getProperty("trans.version");
		} catch (Exception e) {
			//
		}
	}

	/**
	 * @param key
	 * @param value
	 */
	public static void initConfigOld(String key, String value) {
		try {

			boolean isOldKeyValue = true;

			if ("client.font".equals(key)) {
				jp.co.ais.trans.common.config.ClientConfig.clientFont = value;

			} else if ("client.font.size".equals(key)) {
				// フォント
				jp.co.ais.trans.common.config.ClientConfig.clientFontSize = Float.parseFloat(value);

			} else if ("login.comp.code".equals(key)) {
				// 仮ログイン状態
				jp.co.ais.trans.common.config.ClientConfig.kariComp = value;

			} else if ("login.user.code".equals(key)) {
				// 仮ログイン状態
				jp.co.ais.trans.common.config.ClientConfig.kariUser = value;

			} else if ("show.prg.code".equals(key)) {
				// PRGコード表示
				jp.co.ais.trans.common.config.ClientConfig.isShowPrgCode = Boolean.valueOf(value);

			} else if ("applet.widht".equals(key)) {
				// プログラムコード表示
				jp.co.ais.trans.common.config.ClientConfig.appletWidht = Integer.parseInt(value);

			} else if ("applet.height".equals(key)) {
				// プログラムコード表示
				jp.co.ais.trans.common.config.ClientConfig.appletHeight = Integer.parseInt(value);

			} else if ("base.address".equals(key)) {
				// 接続先設定
				jp.co.ais.trans.common.config.ClientConfig.baseAddress = value;

			} else if ("http.protocol".equals(key)) {
				// 接続先設定
				jp.co.ais.trans.common.config.ClientConfig.protocol = value;

			} else if ("http.host".equals(key)) {
				// 接続先設定
				jp.co.ais.trans.common.config.ClientConfig.host = value;

			} else if ("http.port".equals(key)) {
				// 接続先設定
				jp.co.ais.trans.common.config.ClientConfig.port = value;

			} else if ("http.project".equals(key)) {
				// 接続先設定
				jp.co.ais.trans.common.config.ClientConfig.project = value;

			} else if ("root.url.directory".equals(key)) {
				// ルートディレクトリ
				jp.co.ais.trans.common.config.ClientConfig.rootDirectory = value;

			} else if ("trans.system.name".equals(key)) {
				// システム名称
				jp.co.ais.trans.common.config.ClientConfig.systemName = value;

				// システム名称が開発コード名称と異なる場合、画像末尾文字を変更する.
				if (!jp.co.ais.trans.common.config.ClientConfig.DEV_CODE.equals(value)) {
					jp.co.ais.trans.common.config.ClientConfig.imageSuffix = "_" + value;
				}

			} else if ("trans.system.multilogin".equals(key)) {
				// ルートディレクトリ
				String multilogin = value;
				if (Util.isNumber(multilogin)) {
					jp.co.ais.trans.common.config.ClientConfig.maxWindowCount = Integer.parseInt(multilogin);

				} else if (BooleanUtil.toBoolean(multilogin)) {
					// trueの場合は、1画面
					jp.co.ais.trans.common.config.ClientConfig.maxWindowCount = 1;
				}

			} else if ("menu.split".equals(key)) {
				// メニューサイズ可変
				jp.co.ais.trans.common.config.ClientConfig.isMenuSplit = Boolean.valueOf(value);

			} else if ("table.label.backcolor".equals(key)) {
				// スプレッドカラーコード
				jp.co.ais.trans.common.config.ClientConfig.labelback = value.split(",");

			} else if ("table.label.fontcolor".equals(key)) {
				// スプレッドカラーコード
				jp.co.ais.trans.common.config.ClientConfig.labelfont = value.split(",");

			} else if ("table.cell.backcolor1".equals(key)) {
				// スプレッドカラーコード
				jp.co.ais.trans.common.config.ClientConfig.cellback1 = value.split(",");

			} else if ("table.cell.backcolor2".equals(key)) {
				// スプレッドカラーコード
				jp.co.ais.trans.common.config.ClientConfig.cellback2 = value.split(",");

			} else if ("trans.header.backcolor".equals(key)) {
				// ヘッダー背景色
				jp.co.ais.trans.common.config.ClientConfig.headerBackColor = value;

			} else {
				isOldKeyValue = false;
			}

			if (isOldKeyValue) {
				ClientLogger.debug("is old key and value. [key=" + key + ", value=" + value);
			}

		} catch (Exception e) {
			ClientLogger.error("is error key and value. [key=" + key + ", value=" + value);
		}
	}

	/**
	 * Clientプロパティから値(複数)を取得<br>
	 * カンマ区切りで設定していあることが前提
	 * 
	 * @param key キー
	 * @return 設定値リスト
	 */
	public static String[] getProperties(String key) {

		String[] values = StringUtil.split(getProperty(key), ",");

		for (int i = 0; i < values.length; i++) {
			values[i] = values[i].trim();
		}

		return values;
	}

	/**
	 * Clientプロパティから値を取得する。
	 * 
	 * @param key キー
	 * @return 設定値
	 */
	public static String getProperty(String key) {

		if (properties.containsKey(key)) {
			return properties.get(key);
		} else {
			String value = Util.avoidNull(bundle.getString(key));
			properties.put(key, value);
			return value;
		}

	}

	/**
	 * プロパティーの取得
	 * 
	 * @return プロパティーマップ
	 */
	public static Map<String, String> getProperties() {
		return properties;
	}

	/**
	 * 任意プロパティの設定
	 * 
	 * @param key
	 * @param value
	 */
	public static void setProperty(String key, String value) {
		properties.put(key, value);

		// 旧Configをサポートする
		initConfigOld(key, value);
	}

	/**
	 * @return Web経由でアクセスするかを返す。
	 */
	public static boolean isWeb() {
		return web;
	}

	/**
	 * @param flag true:WEBモード
	 */
	public static void setWeb(boolean flag) {
		web = flag;
	}

	/**
	 * @return メイン画面の高さを返す。
	 */
	public static int getMainViewHeight() {
		return mainViewHeight;
	}

	/**
	 * @return メイン画面の幅を返す。
	 */
	public static int getMainViewWidth() {
		return mainViewWidth;
	}

	/**
	 * タイトル
	 * 
	 * @return タイトル
	 */
	public static String getTitle() {

		String title;
		try {
			title = getProperty("system.name");

		} catch (MissingResourceException e) {
			title = "TRANS";
		}

		if (!Util.isNullOrEmpty(systemVersion)) {
			title += " Ver" + systemVersion;
		}

		return title;
	}

	/**
	 * システムスタイル
	 * 
	 * @return システムスタイル
	 */
	public static String getSystemStyle() {
		try {
			return getProperty("system.style");

		} catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * フラグON/OFF
	 * 
	 * @param key キー
	 * @return true:ON、false:OFF
	 */
	public static boolean isFlagOn(String key) {

		try {
			String prop = getProperty(key);
			return BooleanUtil.toBoolean(prop);

		} catch (MissingResourceException e) {
			return false;
		}
	}

	/**
	 * 設定プロパティから色を取得する
	 * 
	 * @param key
	 * @return 色
	 */
	public static Color getColor(String key) {

		try {
			String prop = getProperty(key);
			String[] arr = prop.split(",");

			int color0 = Integer.parseInt(arr[0]);
			int color1 = Integer.parseInt(arr[1]);
			int color2 = Integer.parseInt(arr[2]);

			return new Color(color0, color1, color2);

		} catch (Throwable e) {
			return null;
		}
	}
}
