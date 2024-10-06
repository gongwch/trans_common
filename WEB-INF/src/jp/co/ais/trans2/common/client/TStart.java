package jp.co.ais.trans2.common.client;

import java.io.*;
import java.lang.reflect.*;
import java.util.*;

import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.config.*;
import jp.co.ais.trans2.common.ui.*;

/**
 * プログラム起動クラス。<br>
 * アプリケーションは当該クラスのmainメソッドを起点とする。
 * 
 * @author AIS
 */
public class TStart {

	/** デフォルトローカルタイムゾーン */
	public static TimeZone localTimeZone;

	/** DEBUGモード */
	public static boolean isDebugMode = false;

	static {

		try {
			Class clazz = Class.forName("javax.jnlp.ServiceManager");
			Method method = clazz.getDeclaredMethod("lookup", String.class);
			Object objBS = method.invoke(clazz, "javax.jnlp.BasicService");

			Method methodBS = objBS.getClass().getDeclaredMethod("getCodeBase");
			String codebase = Util.avoidNull(methodBS.invoke(objBS));

			// URL設定
			// BasicService bs = (BasicService) clazz.newInstance().lookup("javax.jnlp.BasicService");
			// String codebase = bs.getCodeBase().toString();
			jp.co.ais.trans.common.config.ClientConfig.setBaseAddress(codebase);
			System.out.println("#codebase=" + codebase);

		} catch (RuntimeException ex) {
			// エラーなし
			ClientLogger.info("local mode 1");
		} catch (Exception e) {
			// エラーなし
			ClientLogger.info("local mode 2");
		}

		// 各クライアントパラメータの初期化
		showInitialParamaters();

		localTimeZone = TimeZone.getDefault();

		try {

			// Java 9 からエラーのため、コメントアウトする
			// // フォーカス
			// AppContext.getAppContext().put(KeyboardFocusManager.class, new TKeyboardFocusManager());

			// タイムゾーン設定
			String timeZone = ClientConfig.getProperty("trans.time.zone");
			TimeZone.setDefault(TimeZone.getTimeZone(timeZone));

			ClientLogger.info("#set timezone=" + timeZone);

		} catch (MissingResourceException e) {
			// 処理なし
		}
	}

	/**
	 * プログラムエントリポイント<BR>
	 * アプリケーションを起動する。
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		println("new TStart.");

		// アプリ引数によってプロパティ初期化
		initArgumentProperties(args);

		// ログインコントローラ起動
		TLoginCtrl ctrl = new TLoginCtrl();

		// ログイン開始
		ctrl.start();
	}

	/**
	 * 初期パラメータの初期化
	 */
	public static void showInitialParamaters() {

		// ログ出力かどうか最優先
		try {
			String key = "debug.mode";
			String value = Util.avoidNull(System.getProperty(ClientConfig.jnlpHeader + key));
			ClientConfig.setProperty(key, value);

			isDebugMode = Boolean.valueOf(ClientConfig.getProperty(key));

			if (isDebugMode) {
				for (Map.Entry<String, String> entry : ClientConfig.getProperties().entrySet())
					ClientLogger.info(entry.getKey() + "=" + entry.getValue());
			}
		} catch (Exception e) {
			//
		}
	}

	/**
	 * アプリ引数によってプロパティ初期化
	 * 
	 * @param args
	 */
	public static void initArgumentProperties(String[] args) {
		try {
			for (int i = 0; i < args.length - 1; i += 2) {
				String key = args[i];
				if (i + 1 >= args.length) {
					break;
				}
				String value = args[i + 1];

				// ファイルであれば、ファイルを読み込み
				if (key.equalsIgnoreCase("-config")) {

					System.out.println("has config file.");

					if (ClientConfig.isWeb()) {
						// 初期化のため、一回アクセスする
					}

					//
					FileReader fr = null;
					BufferedReader br = null;

					try {

						fr = new FileReader(value);
						br = new BufferedReader(fr);
						String line = null;

						println("#config to properties init. [" + value + "]");

						for (int lin = 1; (line = br.readLine()) != null; lin++) {
							String[] arr = line.split("=");
							if (arr.length > 1) {
								String subKey = arr[0];
								String subValue = arr[1];

								// jnlp先頭判定処理追加
								if (subKey.startsWith(ClientConfig.jnlpHeader)) {
									subKey = subKey.replace(ClientConfig.jnlpHeader, "");
								}

								println("#config key=[" + subKey + "], value=[" + subValue + "] at line:" + lin);
								System.setProperty(subKey, subValue);
								ClientConfig.setProperty(subKey, subValue);
							}
						}

					} catch (Throwable ex) {
						println("#config to properties error! [" + value + "]");
						println(ex);
					} finally {
						if (br != null) {
							br.close();
						}
						if (fr != null) {
							fr.close();
						}
					}
				} else {
					// 通常設定

					// jnlp先頭判定処理追加
					if (key.startsWith(ClientConfig.jnlpHeader)) {
						key = key.replace(ClientConfig.jnlpHeader, "");
					}
					ClientConfig.setProperty(key, value);
				}
			}
		} catch (Exception e) {
			println("#args to properties error.");
		}
	}

	/**
	 * DEBUG
	 * 
	 * @param str
	 */
	protected static void println(String str) {
		ClientLogger.info(str);
	}

	/**
	 * DEBUG
	 * 
	 * @param ex
	 */
	protected static void println(Throwable ex) {
		ex.printStackTrace(System.out);
	}

}
