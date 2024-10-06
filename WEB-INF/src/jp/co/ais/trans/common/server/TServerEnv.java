package jp.co.ais.trans.common.server;

import java.io.*;

import jp.co.ais.trans.common.config.*;

/**
 * サーバ定数
 */
public enum TServerEnv {

	/** HttpSessionに保存されるシステム共通専用key名のprefix. */
	SYS_PREFIX("trans.common."),

	/** ルートディレクトリ */
	TOP_DIR("/" + ClientConfig.getRootDirectory() + "/"),

	/** JSPファイルパス */
	JSP_PATH(TServerEnv.TOP_DIR.toString()),

	/** エラーのJSPファイル名. */
	ERR_JSP("error.jsp"),

	/** アップロード時テンポラリファイル配置ディレクトリ. */
	TMP_DIR("/"),

	/** 実行ログファイル配置ディレクトリ. */
	USER_LOG_DIR(ServerConfig.getRootPath() + File.separator + "log" + File.separator);

	/** 値 */
	private String value;

	/**
	 * コンストラクタ
	 * 
	 * @param value 値
	 */
	private TServerEnv(String value) {
		this.value = value;
	}

	/**
	 * 文字列表現
	 */
	@Override
	public String toString() {
		return value;
	}
}
