package jp.co.ais.trans.common.config;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * クライアントサイド設定情報
 */
public final class ClientConfig {

	/** 固定開発コード(名称) */
	public static final String DEV_CODE = "TRANS";

	/** デフォルトフォントサイズ */
	public static final int DEFAULT_FONT_SIZE = 12;

	/** クライアントサイド設定 */
	public static ResourceBundle bundle = ResourceBundle.getBundle("client");

	/** 仮ログイン会社名(開発用) */
	public static String kariComp;

	/** 仮ログインユーザー名(開発用) */
	public static String kariUser;

	/** AppletViewer幅設定(開発用) */
	public static int appletWidht;

	/** 接続プロトコル(開発用) */
	public static String protocol;

	/** 接続先(開発用) */
	public static String host;

	/** 接続ポート(開発用) */
	public static String port;

	/** プロジェクト名(開発用) */
	public static String project;

	/** AppletViewer高さ設定(開発用) */
	public static int appletHeight;

	/** プログラムコード表示有無(開発用) */
	public static boolean isShowPrgCode = false;

	/** URLルートディレクトリ */
	public static String rootDirectory;

	/** システム名称 */
	public static String systemName;

	/** 画像ファイルサフィックス */
	public static String imageSuffix = "";

	/** クライアント画面フォント */
	public static String clientFont;

	/** クライアント画面フォントサイズ */
	public static float clientFontSize;

	/** TTableカラー設定 */
	public static String[] labelback;

	/** TTableカラー設定 */
	public static String[] labelfont;

	/** TTableカラー設定 */
	public static String[] cellback1;

	/** TTableカラー設定 */
	public static String[] cellback2;

	/** メインパネル：ヘッダー部カラー設定 */
	public static String headerBackColor;

	/** マルチウィンドウ起動上限数 */
	public static int maxWindowCount = 0;

	/** メニューサイズを可変にするかどうか */
	public static boolean isMenuSplit;

	/** 接続先アドレス */
	public static String baseAddress;

	static {

		// 開発用設定 ---

		try {
			// 仮ログイン状態
			kariComp = bundle.getString("login.comp.code");
			kariUser = bundle.getString("login.user.code");
		} catch (MissingResourceException e) {
			//
		}

		try {
			// プログラムコード表示
			isShowPrgCode = Boolean.valueOf(bundle.getString("show.prg.code"));
		} catch (MissingResourceException e) {
			//
		}

		try {
			// プログラムコード表示
			appletWidht = Integer.parseInt(bundle.getString("applet.widht"));
			appletHeight = Integer.parseInt(bundle.getString("applet.height"));

		} catch (Exception e) {
			appletWidht = 1010;
			appletHeight = 644;
		}

		// 接続先設定 ---

		try {
			// プロトコル設定
			protocol = bundle.getString("http.protocol");

		} catch (MissingResourceException e) {
			// プロトコル設定が無い場合、httpがデフォルト
			protocol = "http";
		}

		try {
			host = bundle.getString("http.host");
			port = bundle.getString("http.port");
			project = bundle.getString("http.project");

		} catch (MissingResourceException e) {
			// Applet用
		}

		// ルートディレクトリ
		rootDirectory = bundle.getString("root.url.directory");

		// システム関連設定 ---

		try {
			// システム名称
			systemName = bundle.getString("trans.system.name");

		} catch (MissingResourceException e) {
			systemName = DEV_CODE;
		}

		// システム名称が開発コード名称と異なる場合、画像末尾文字を変更する.
		if (!DEV_CODE.equals(systemName)) {
			imageSuffix = "_" + systemName;
		}

		try {
			// マルチプログラムウインドウ
			String multilogin = bundle.getString("trans.system.multilogin");

			if (Util.isNumber(multilogin)) {
				maxWindowCount = Integer.parseInt(multilogin);

			} else if (BooleanUtil.toBoolean(multilogin)) {
				// trueの場合は、1画面
				maxWindowCount = 1;
			}

		} catch (MissingResourceException e) {
			maxWindowCount = 0;
		}

		try {
			// メニューサイズ可変
			isMenuSplit = BooleanUtil.toBoolean(ClientConfig.getProperty("menu.split"));

		} catch (Exception ex) {
			isMenuSplit = false;
		}

		// フォント
		clientFont = bundle.containsKey("client.font") ? bundle.getString("client.font") : "Dialog";
		clientFontSize = bundle.containsKey("client.font.size") ? Float
			.parseFloat(bundle.getString("client.font.size")) : DEFAULT_FONT_SIZE;

		// スプレッドカラーコード
		labelback = bundle.getString("table.label.backcolor").split(",");
		labelfont = bundle.getString("table.label.fontcolor").split(",");
		cellback1 = bundle.getString("table.cell.backcolor1").split(",");
		cellback2 = bundle.getString("table.cell.backcolor2").split(",");

		try {
			// ヘッダー背景色
			headerBackColor = bundle.getString("trans.header.backcolor");

		} catch (MissingResourceException e) {
			headerBackColor = "";
		}
	}

	/**
	 * Clientプロパティから値を取得
	 * 
	 * @param key キー
	 * @return 設定値
	 */
	public static String getProperty(String key) {
		return jp.co.ais.trans2.common.config.ClientConfig.getProperty(key);
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
	 * 色設定の取得
	 * 
	 * @param key
	 * @return 色設定
	 */
	public static Color getColor(String key) {
		try {
			String[] rgb = getProperties(key);
			return Util.toColor(rgb);
		} catch (Throwable ex) {
			return null;
		}
	}

	/**
	 * 色設定の取得<br>
	 * nullの場合、デフォルト値を返す
	 * 
	 * @param key
	 * @param defaultValue デフォルト値
	 * @return 色設定
	 */
	public static Color getColor(String key, Color defaultValue) {
		Color color = getColor(key);
		if (color == null) {
			return defaultValue;
		} else {
			return color;
		}
	}

	/**
	 * 仮ログイン会社名
	 * 
	 * @return kariComp
	 */
	public static String getDummyComp() {

		return kariComp;
	}

	/**
	 * 仮ログインユーザー名
	 * 
	 * @return kariUser
	 */
	public static String getDummyUser() {

		return kariUser;
	}

	/**
	 * プログラムコード表示有無(開発用)
	 * 
	 * @return 表示する場合true
	 */
	public static boolean isShowPrgramCode() {
		return isShowPrgCode;
	}

	/**
	 * AppletViewer幅取得(開発用)
	 * 
	 * @return 幅サイズ
	 */
	public static int getAppletHeight() {
		return appletHeight;
	}

	/**
	 * AppletViewer高さ設定(開発用)
	 * 
	 * @return 高さサイズ
	 */
	public static int getAppletWidht() {
		return appletWidht;
	}

	/**
	 * プロトコル
	 * 
	 * @return プロトコル
	 */
	public static String getProtocol() {
		return protocol;
	}

	/**
	 * ホスト名
	 * 
	 * @return host
	 */
	public static String getHost() {
		return host;
	}

	/**
	 * ポート
	 * 
	 * @return port
	 */
	public static String getPort() {
		return port;
	}

	/**
	 * プロジェクト
	 * 
	 * @return project
	 */
	public static String getProject() {
		return project;
	}

	/**
	 * 基本URLアドレス
	 * 
	 * @param url アドレス
	 */
	public static void setBaseAddress(String url) {
		baseAddress = url;
	}

	/**
	 * 基本URLアドレス
	 * 
	 * @return アドレス
	 */
	public static String getBaseAddress() {
		if (!Util.isNullOrEmpty(baseAddress)) {
			return baseAddress;
		}

		return protocol + "://" + host + ":" + port + "/" + project + "/";
	}

	/**
	 * URLルートディレクトリ
	 * 
	 * @return ルートディレクトリ
	 */
	public static String getRootDirectory() {
		return rootDirectory;
	}

	/**
	 * システム名称の取得
	 * 
	 * @return システム名称
	 */
	public static String getSystemName() {
		return systemName;
	}

	/**
	 * 画面フォント指定
	 * 
	 * @return フォント
	 */
	public static String getClientFont() {
		return clientFont;
	}

	/**
	 * 画面フォントサイズ指定
	 * 
	 * @return フォントサイズ
	 */
	public static float getClientFontSize() {
		return clientFontSize;
	}

	/**
	 * 画像ファイルサフィックス（後方文字）の取得
	 * 
	 * @return 画像の後方文字
	 */
	public static String getImageSuffix() {
		return imageSuffix;
	}

	/**
	 * マルチウィンドウ起動上限数
	 * 
	 * @return 上限数
	 */
	public static int getMaxWindowCount() {
		return maxWindowCount;
	}

	/**
	 * ヘッダーカラーを取得
	 * 
	 * @return 設定値
	 */
	public static String getHeaderBackColor() {
		return headerBackColor;
	}

	/**
	 * コンポーネント幅補正値を取得する
	 * 
	 * @param type コンポーネントタイプ
	 * @return 補正値
	 */
	public static double getDeffWidth(String type) {
		try {
			String deff = getProperty("deff.width." + type);
			return Double.valueOf(deff);

		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * コンポーネント高さ補正値を取得する
	 * 
	 * @param type コンポーネントタイプ
	 * @return 補正値
	 */
	public static double getDeffHeight(String type) {
		try {
			String deff = getProperty("deff.height." + type);
			return Double.valueOf(deff);

		} catch (Exception ex) {
			return 0;
		}
	}

	/**
	 * メッセージダイアログのOKボタン文字
	 * 
	 * @return OKボタン文字
	 */
	public static String getOkButtonText() {
		try {
			return getProperty("dialog.ok.text");

		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * 確認ダイアログのYes/Noボタン文字
	 * 
	 * @return Yes/Noボタン文字
	 */
	public static String[] getYesNoButtonWords() {
		try {
			return StringUtil.toArrayByComma(getProperty("dialog.yesno.text"));

		} catch (Exception ex) {
			return null;
		}
	}

	/**
	 * メニューサイズを可変にするかどうか
	 * 
	 * @return true:可変
	 */
	public static boolean isMenuSplit() {
		return isMenuSplit;
	}

	/**
	 * メニュータブの表示順序(システム区分)
	 * 
	 * @return メニュータブの表示順序
	 */
	public static String[] getMenuTabOrder() {
		try {

			return StringUtil.toArrayByComma(getProperty("menu.systab.order"));

		} catch (Exception ex) {
			return new String[] { "AG", "OW", "AC", "KT" };
		}
	}

	/**
	 * スプレッドシートのEnterキー押下時の遷移先を指定する
	 * 
	 * @return タイプ
	 */
	public static String getTableEnterTraverse() {
		try {
			return getProperty("table.enter.traverse");

		} catch (Exception ex) {
			return "TRAVERSE_DOWN";
		}
	}

	/**
	 * TTableカラー設定
	 * 
	 * @return ラベル色
	 */
	public static Color getTableLabelColor() {

		return toColor(labelback);
	}

	/**
	 * TTableカラー設定
	 * 
	 * @return ラベルフォント色
	 */
	public static Color getTableLabelFontColor() {

		return toColor(labelfont);
	}

	/**
	 * TTableカラー設定
	 * 
	 * @return デフォルト色
	 */
	public static Color getTableCellBackColor1() {

		return toColor(cellback1);
	}

	/**
	 * TTableカラー設定
	 * 
	 * @return 繰り返し選択色
	 */
	public static Color getTableCellBackColor2() {

		return toColor(cellback2);
	}

	/**
	 * TTableカラー設定
	 * 
	 * @return 選択時カラー
	 */
	public static Color getTableSelectColor() {
		try {
			return toColor(StringUtil.split(getProperty("table.cell.selectcolor"), ","));

		} catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * TTableカラー設定
	 * 
	 * @return 選択時カラー
	 */
	public static Color getTableSelectCellFontColor() {
		try {
			return toColor(StringUtil.split(getProperty("table.cell.selectfontcolor"), ","));

		} catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * TTableカラー設定
	 * 
	 * @return 選択時カラー
	 */
	public static Color getTableCellFontColor() {
		try {
			return toColor(StringUtil.split(getProperty("table.cell.fontcolor"), ","));

		} catch (MissingResourceException e) {
			return null;
		}
	}

	/**
	 * TTable高さ設定
	 * 
	 * @return 高さ
	 */
	public static int getTableCellHeight() {
		try {
			return Integer.parseInt(getProperty("table.cell.height"));

		} catch (MissingResourceException e) {
			return 0;
		}
	}

	/**
	 * 色変換
	 * 
	 * @param abg RBG
	 * @return 色
	 */
	public static Color toColor(String[] abg) {
		int color0 = Integer.parseInt(abg[0]);
		int color1 = Integer.parseInt(abg[1]);
		int color2 = Integer.parseInt(abg[2]);

		return new Color(color0, color1, color2);
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
}
