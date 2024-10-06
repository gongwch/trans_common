package jp.co.ais.trans.common.config;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * サーバサイド設定情報
 */
public class ServerConfig {

	/** システムコード(サブシステム制御) */
	private static String[] sysCodes = null;

	/** 規定ログイン数 */
	private static int regulatedNumber = -1;

	/** サーバサイド設定 */
	private static ResourceBundle bundle = ResourceBundle.getBundle("server");

	/** システム言語コード */
	private static String lang;

	/** システムスキーマ */
	private static String schemaName;

	/** 表領域名 */
	private static String tablespaceName;

	/** セッション認証モード */
	private static boolean isSessionMode;

	/** ログイン管理モード */
	private static boolean isLoginManagedMode;

	/** 実行ログファイルをDBに登録するかどうか */
	private static boolean isExeLogDBMode;

	/** WEBルートディレクトリパス */
	private static String rootPath = "";

	/** 伝票の有効期限をチェックするか */
	private static boolean isSlipTermCheck;

	/** 規定サブシステム利用ユーザ数をチェックするか */
	private static boolean isSystemRegulatedNumberCheck;

	/** 規定サブシステム利用ユーザ数 */
	private static Map<String, Integer> regulatedNumberMap = null;

	/** 規定利用ユーザの会社グループ<会社コード, 所属グループ> */
	private static Map<String, Integer> regulatedGroupMap = null;

	/** 規定利用ユーザの会社グループ<index, 会社コードリスト> */
	private static Map<Integer, List<String>> regulatedGroupListMap = null;

	static {
		// 暗号化された規定数＆システムコード
		FileInputStream fis = null;

		try {
			fis = new FileInputStream(ResourceUtil.getResourceAsFile("keyfile/TSCode.dll"));

			// 復号したファイルをロードしたプロパティの取得
			Properties properties = EncryptUtil.decryptProperty(fis);

			String sysCodesStr = properties.getProperty("system.code");
			String regulNumStr = properties.getProperty("login.regulate.number");

			// 設定無しはシステムエラー
			if (Util.isNullOrEmpty(sysCodesStr)) {
				throw new TEnvironmentException("S00001", "system.code not found.");
			}

			if (Util.isNullOrEmpty(regulNumStr)) {
				throw new TEnvironmentException("S00001", "login.regulate.number not found.");
			}

			// 使用サブシステム：取得したsysCodeをカンマ区切りでString[]に設定
			sysCodes = StringUtil.split(sysCodesStr, ",");

			// 規定ログイン数
			regulatedNumber = Integer.parseInt(regulNumStr);

			// サブシステム毎にチェックを行うかどうか
			try {
				String prop = properties.getProperty("system.use.regulate.check");
				isSystemRegulatedNumberCheck = BooleanUtil.toBoolean(prop);

			} catch (MissingResourceException e) {
				isSystemRegulatedNumberCheck = false;
			}

			regulatedNumberMap = new LinkedHashMap<String, Integer>();
			regulatedGroupMap = new LinkedHashMap<String, Integer>();
			regulatedGroupListMap = new LinkedHashMap<Integer, List<String>>();

			// サブシステム毎に既定ログイン数を設定
			if (isSystemRegulatedNumberCheck) {
				for (String sysCode : sysCodes) {
					if (Util.isNullOrEmpty(sysCode)) {
						continue;
					}

					// サブシステム単位
					try {
						String key = "system.regulate.number." + sysCode;
						String numStr = properties.getProperty(key);
						int num = regulatedNumber;

						if (!Util.isNullOrEmpty(numStr) && Util.isNumber(numStr)) {
							num = Integer.parseInt(numStr);
						}

						regulatedNumberMap.put(sysCode, num);

					} catch (Throwable ex) {
						// エラーなし

						regulatedNumberMap.put(sysCode, regulatedNumber);
					}

					// グループIndex単位
					{
						int num = 0;

						{
							String numStr = properties.getProperty("system.regulate.group.count");

							if (!Util.isNullOrEmpty(numStr) && Util.isNumber(numStr)) {
								num = Integer.parseInt(numStr);
							}
						}

						if (num > 0) {
							// グループ設定あり
							for (int i = 1; i <= num; i++) {
								String key = "system.regulate.group." + i;
								String[] values = StringUtil.split(properties.getProperty(key), ",");
								List<String> companyCodeList = new ArrayList<String>();

								for (String str : values) {
									companyCodeList.add(str);
									regulatedGroupMap.put(str, i);
								}

								regulatedGroupListMap.put(i, companyCodeList);
							}
						}

						if (!regulatedGroupListMap.isEmpty()) {
							// グループあり
							for (Integer i : regulatedGroupListMap.keySet()) {
								String key = "system.regulate.number." + i + "." + sysCode;
								String numStr = properties.getProperty(key);
								int n = regulatedNumber;

								if (!Util.isNullOrEmpty(numStr) && Util.isNumber(numStr)) {
									n = Integer.parseInt(numStr);
								}

								regulatedNumberMap.put(sysCode + i, n);
							}
						}
					}
				}

			}

		} catch (FileNotFoundException e) {
			ServerErrorHandler.handledException(e);
			throw new TEnvironmentException(e, "TSCode file not found.");

		} catch (TEnvironmentException e) {
			ServerErrorHandler.handledException(e);
			throw e;

		} catch (TException e) {
			ServerErrorHandler.handledException(e);
			throw new TEnvironmentException(e, "SystemProperty decrypt failed.");

		} finally {
			ResourceUtil.closeInputStream(fis);
		}

		// systemプロパティ

		try {
			// サーバサイドデフォルト言語
			lang = getProperty("trans.system.lang");

		} catch (MissingResourceException e) {
			lang = Locale.getDefault().getLanguage();
		}

		try {
			// スキーマ名
			schemaName = getProperty("trans.system.schema") + ".";

		} catch (MissingResourceException e) {
			schemaName = "";
		}

		try {
			// Function名
			tablespaceName = getProperty("trans.system.tablespace") + ".";

		} catch (MissingResourceException e) {
			tablespaceName = "";
		}

		try {
			// セッション管理無効フラグ（開発用）
			String noSessionMode = getProperty("trans.no.session.mode");

			isSessionMode = !Boolean.valueOf(noSessionMode);

		} catch (MissingResourceException e) {
			isSessionMode = true;
		}

		try {
			// ログイン管理無効フラグ（開発用）
			String noLoginManage = getProperty("trans.no.loginmanaged.mode");

			isLoginManagedMode = !Boolean.valueOf(noLoginManage);

		} catch (MissingResourceException e) {
			isLoginManagedMode = true;
		}

		try {
			// サーバプロパティ取得
			String strDBLogMode = ServerConfig.getProperty("trans.exelog.isdbmode");

			isExeLogDBMode = Boolean.valueOf(strDBLogMode);

		} catch (MissingResourceException e) {
			isExeLogDBMode = false;
		}

		try {
			// 伝票有効期限チェック
			isSlipTermCheck = BooleanUtil.toBoolean(ServerConfig.getProperty("trans.slip.term.check"));

		} catch (MissingResourceException e) {
			isSlipTermCheck = false;
		}
	}

	/**
	 * 利用システムコードの取得
	 * 
	 * @return String[] sysCode
	 */
	public static String[] getSysCodes() {

		return sysCodes;
	}

	/**
	 * 規定ログイン数の取得
	 * 
	 * @return int 規定ログイン数
	 */
	public static int getRegulatedNumber() {

		return regulatedNumber;
	}

	/**
	 * 規定サブシステム利用ユーザ数の取得
	 * 
	 * @param sysCode サブシステムコード
	 * @param companyCode 会社コード指定
	 * @return int 規定サブシステム利用ユーザ数
	 */
	public static int getSystemRegulatedNumber(String sysCode, String companyCode) {
		int groupIndex = getSystemRegulatedGroupIndex(companyCode);
		return getSystemRegulatedNumber(sysCode, groupIndex);
	}

	/**
	 * 規定サブシステム利用ユーザ数の取得
	 * 
	 * @param sysCode サブシステムコード
	 * @param groupIndex
	 * @return int 規定サブシステム利用ユーザ数
	 */
	public static int getSystemRegulatedNumber(String sysCode, int groupIndex) {
		if (regulatedNumberMap.containsKey(sysCode + groupIndex)) {
			// グループ指定あり、グループライセンスを返す
			return regulatedNumberMap.get(sysCode + groupIndex);

		} else if (regulatedNumberMap.containsKey(sysCode)) {
			// デフォルトサブシステム単位のライセンスを返す
			return regulatedNumberMap.get(sysCode);
		}

		// デフォルトシステム単位のライセンスを返す
		return regulatedNumber;
	}

	/**
	 * 指定会社コードのグループIndexを返す
	 * 
	 * @param companyCode
	 * @return 指定会社コードのグループIndex
	 */
	public static int getSystemRegulatedGroupIndex(String companyCode) {
		if (!Util.isNullOrEmpty(companyCode) && regulatedGroupMap.containsKey(companyCode)) {
			return Util.avoidNullAsInt(regulatedGroupMap.get(companyCode));
		}

		return -1;
	}

	/**
	 * 指定会社コードの同一グループIndexの会社コードリストを返す
	 * 
	 * @param companyCode
	 * @return 指定会社コードの同一グループIndexの会社コードリスト
	 */
	public static List<String> getSystemRegulatedGroupList(String companyCode) {
		int groupIndex = getSystemRegulatedGroupIndex(companyCode);
		List<String> list = getSystemRegulatedGroupList(groupIndex, companyCode);
		return list;
	}

	/**
	 * 指定グループIndexの会社コードリストを返す
	 * 
	 * @param groupIndex
	 * @param companyCode
	 * @return 指定グループIndexの会社コードリスト
	 */
	public static List<String> getSystemRegulatedGroupList(int groupIndex, String companyCode) {
		List<String> list = null;

		if (groupIndex != -1) {
			list = regulatedGroupListMap.get(groupIndex);
		}

		if (list == null) {
			list = new ArrayList<String>();
		}

		if (list.isEmpty()) {
			list.add(companyCode);
		}

		return list;
	}

	/**
	 * システム言語コード
	 * 
	 * @return 言語コード
	 */
	public static String getSystemLanguageCode() {
		return lang;
	}

	/**
	 * システムスキーマ名.<br>
	 * 設定が無い場合は、空文字を返す.
	 * 
	 * @return スキーマ名
	 */
	public static String getSchemaName() {
		return schemaName;
	}

	/**
	 * 表領域名.<br>
	 * 設定が無い場合は、空文字を返す.
	 * 
	 * @return 表領域名
	 */
	public static String getTableSpaceName() {
		return tablespaceName;
	}

	/**
	 * セッション認証モード
	 * 
	 * @return 認証する場合はtrue
	 */
	public static boolean isSessionMode() {
		return isSessionMode;
	}

	/**
	 * ログイン管理モード
	 * 
	 * @return 管理する場合はtrue
	 */
	public static boolean isLoginManagedMode() {
		return isLoginManagedMode;
	}

	/**
	 * ログイン管理モードの指定
	 * 
	 * @param isLoginManagedMode
	 */
	public static void setLoginManagedMode(boolean isLoginManagedMode) {
		ServerConfig.isLoginManagedMode = isLoginManagedMode;
	}

	/**
	 * 実行ログファイルをDBに登録するかどうか
	 * 
	 * @return DBに登録する場合はtrue
	 */
	public static boolean isExeLogDBMode() {
		return isExeLogDBMode;
	}

	/**
	 * 伝票の有効期限をチェックするか
	 * 
	 * @return true:チェックする
	 */
	public static boolean isSlipTermCheck() {
		return isSlipTermCheck;
	}

	/**
	 * 規定サブシステム利用ユーザ数をチェックするか
	 * 
	 * @return true:チェックする
	 */
	public static boolean isSystemRegulatedNumberCheck() {
		return isSystemRegulatedNumberCheck;
	}

	/**
	 * Serverプロパティから値を取得
	 * 
	 * @param key キー
	 * @return 設定値
	 */
	public static String getProperty(String key) {

		return Util.avoidNull(bundle.getString(key));
	}

	/**
	 * Serverプロパティから値(複数)を取得<br>
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
	 * サーバサイドルートディレクトリを設定
	 * 
	 * @param dir サーバサイドルートディレクトリ
	 */
	public static void setRootPath(String dir) {
		rootPath = dir;
	}

	/**
	 * サーバサイドルートディレクトリを取得
	 * 
	 * @return サーバサイドルートディレクトリ
	 */
	public static String getRootPath() {
		if (Util.isNullOrEmpty(rootPath)) {
			return System.getProperty("user.dir");
		}

		return rootPath;
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
	 * 指定設定があるかどうか
	 * 
	 * @param key キー
	 * @return true:存在
	 */
	public static boolean contains(String key) {
		return bundle.containsKey(key);
	}
}
