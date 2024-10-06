package jp.co.ais.trans.common.server.dao;

import java.util.*;

/**
 * 開示レベル情報
 */
public class IndicationLevel {

	/** 階層レベル */
	private int kJL_LVL;

	/** 上位部門コード */
	private String kJL_UP_DEP_CODE;

	/** 上位部門略称 */
	private String kJL_UP_DEP_NAME_S;

	/** 部門コード */
	private String kJL_DEP_CODE;

	/** 部門略称 */
	private String kJL_DEP_NAME_S;

	/**
	 * 階層レベル
	 * 
	 * @param kJL_LVL 階層レベル
	 */
	public void setKJL_LVL(int kJL_LVL) {
		this.kJL_LVL = kJL_LVL;
	}

	/**
	 * 上位部門コード
	 * 
	 * @param kJL_UP_DEP_CODE 上位部門コード
	 */
	public void setKJL_UP_DEP_CODE(String kJL_UP_DEP_CODE) {
		this.kJL_UP_DEP_CODE = kJL_UP_DEP_CODE;
	}

	/**
	 * 上位部門略称
	 * 
	 * @param kJL_UP_DEP_NAME_S 上位部門略称
	 */
	public void setKJL_UP_DEP_NAME_S(String kJL_UP_DEP_NAME_S) {
		this.kJL_UP_DEP_NAME_S = kJL_UP_DEP_NAME_S;
	}

	/**
	 * 部門コード
	 * 
	 * @param kJL_DEP_CODE 部門コード
	 */
	public void setKJL_DEP_CODE(String kJL_DEP_CODE) {
		this.kJL_DEP_CODE = kJL_DEP_CODE;
	}

	/**
	 * 部門略称します。
	 * 
	 * @param kJL_DEP_NAME_S 部門略称
	 */
	public void setKJL_DEP_NAME_S(String kJL_DEP_NAME_S) {
		this.kJL_DEP_NAME_S = kJL_DEP_NAME_S;
	}

	/**
	 * 階層レベルを取得します。
	 * 
	 * @return 階層レベル
	 */
	public int getKJL_LVL() {
		return this.kJL_LVL;

	}

	/**
	 * 上位部門コードを取得します。
	 * 
	 * @return 上位部門コード
	 */
	public String getKJL_UP_DEP_CODE() {
		return this.kJL_UP_DEP_CODE;

	}

	/**
	 * 上位部門略称
	 * 
	 * @return 上位部門略称
	 */
	public String getKJL_UP_DEP_NAME_S() {
		return kJL_UP_DEP_NAME_S;
	}

	/**
	 * 部門コードを取得します。
	 * 
	 * @return 部門コード
	 */
	public String getKJL_DEP_CODE() {
		return this.kJL_DEP_CODE;

	}

	/**
	 * 部門略称を取得します。
	 * 
	 * @return 部門略称
	 */
	public String getKJL_DEP_NAME_S() {
		return this.kJL_DEP_NAME_S;

	}

	/**
	 * ToStringメソッドを作成する。
	 * 
	 * @return ストリング
	 */
	public String toString() {
		StringBuffer strBuff = new StringBuffer("");
		strBuff.append(kJL_LVL).append("/");
		strBuff.append(kJL_UP_DEP_CODE).append("/");
		strBuff.append(kJL_UP_DEP_NAME_S).append("/");
		strBuff.append(kJL_DEP_CODE).append("/");
		strBuff.append(kJL_DEP_NAME_S).append("/");

		return strBuff.toString();
	}

	/**
	 * 配列変換
	 * 
	 * @return 配列
	 */
	public List<Object> toObjectArray() {
		List<Object> list = new ArrayList<Object>();

		list.add(kJL_LVL);
		list.add(kJL_UP_DEP_CODE);
		list.add(kJL_UP_DEP_NAME_S);
		list.add(kJL_DEP_CODE);
		list.add(kJL_DEP_NAME_S);

		return list;
	}

	/**
	 * マップ変換
	 * 
	 * @return マップ
	 */
	public Map<String, Object> toObjectMap() {

		Map<String, Object> map = new TreeMap<String, Object>();
		map.put("KJL_LVL", kJL_LVL); // 階層レベル
		map.put("KJL_UP_DEP_CODE", kJL_UP_DEP_CODE); // 上位部門コード
		map.put("KJL_UP_DEP_NAME_S", kJL_UP_DEP_NAME_S); // 上位部門コード
		map.put("KJL_DEP_CODE", kJL_DEP_CODE); // 部門コード
		map.put("KJL_DEP_NAME_S", kJL_DEP_NAME_S); // 部門略称

		return map;
	}
}
