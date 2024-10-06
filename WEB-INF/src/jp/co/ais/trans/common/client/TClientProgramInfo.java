package jp.co.ais.trans.common.client;

import jp.co.ais.trans.common.util.*;

/**
 * 現在選択されているプログラムの情報を保持するクラス.
 */
public class TClientProgramInfo implements Cloneable {

	/** インスタンス */
	private static TClientProgramInfo instance = new TClientProgramInfo();

	/** プログラムコード */
	private String prgCode = "";

	/** プログラム名称 */
	private String prgName = "";

	/** プログラム略称 */
	private String prgNameS = "";

	/** 権限レベル */
	private int ken;

	/**
	 * common、test以外で直接使わないこと.<br>
	 * インスタンスの取得
	 * 
	 * @return インスタンス
	 * @deprecated 直接使わないこと
	 */
	public static TClientProgramInfo getInstance() {
		return instance;
	}

	/**
	 * プログラムコード取得
	 * 
	 * @return プログラムコード
	 */
	public String getProgramCode() {
		return this.prgCode;
	}

	/**
	 * プログラムコード取得setter
	 * 
	 * @param prgCode プログラムコード
	 */
	public void setProgramCode(String prgCode) {
		this.prgCode = prgCode;
	}

	/**
	 * プログラム名称取得
	 * 
	 * @return プログラム名称
	 */
	public String getProgramName() {
		return this.prgName;
	}

	/**
	 * プログラム名称setter
	 * 
	 * @param prgName プログラム名称
	 */
	public void setProgramName(String prgName) {
		this.prgName = prgName;
	}

	/**
	 * プログラム略称取得
	 * 
	 * @return プログラム略称
	 */
	public String getProgramShortName() {
		return this.prgNameS;
	}

	/**
	 * プログラム略称setter
	 * 
	 * @param prgNameS プログラム略称
	 */
	public void setProgramShortName(String prgNameS) {
		this.prgNameS = prgNameS;
	}

	/**
	 * 権限レベル取得
	 * 
	 * @return 権限レベル
	 */
	public int getProcessLevel() {
		return this.ken;
	}

	/**
	 * 権限レベルsetter
	 * 
	 * @param ken 権限レベル
	 */
	public void setProcessLevel(int ken) {
		this.ken = ken;
	}

	/**
	 * クローンインスタンスを生成
	 * 
	 * @return インスタンス
	 * @see java.lang.Object#clone()
	 */
	public TClientProgramInfo clone() {
		TClientProgramInfo clone = new TClientProgramInfo();
		clone.prgCode = this.prgCode;
		clone.prgName = this.prgName;
		clone.prgNameS = this.prgNameS;
		clone.ken = this.ken;

		return clone;
	}

	/**
	 * 文字列変換
	 */
	public String toString() {
		StringBuilder buff = new StringBuilder("[");
		buff.append(Util.safeNull(prgCode)).append("/");
		buff.append(Util.safeNull(prgName)).append("/");
		buff.append(Util.safeNull(prgNameS)).append("/");
		buff.append(Util.safeNull(String.valueOf(ken))).append("/");
		buff.append("]");

		return buff.toString();
	}
}
