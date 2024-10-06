package jp.co.ais.trans2.common.ui;

import java.io.*;
import java.util.*;

/**
 * OP LOGIN情報
 */
public class OPLoginData implements Serializable {

	/** 最終更新日時 */
	protected Date lastUpdateDate = null;

	/** データ区分 */
	protected int dataTypeValue = 0;

	/** 保存データのリスト */
	protected List list = null;

	/**
	 * 最終更新日時の取得
	 * 
	 * @return lastUpdateDate 最終更新日時
	 */
	public Date getLastUpdateDate() {
		return lastUpdateDate;
	}

	/**
	 * 最終更新日時の設定
	 * 
	 * @param lastUpdateDate 最終更新日時
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
		this.lastUpdateDate = lastUpdateDate;
	}

	/**
	 * データ区分の取得
	 * 
	 * @return dataTypeValue データ区分
	 */
	public int getDataTypeValue() {
		return dataTypeValue;
	}

	/**
	 * データ区分の設定
	 * 
	 * @param dataTypeValue データ区分
	 */
	public void setDataTypeValue(int dataTypeValue) {
		this.dataTypeValue = dataTypeValue;
	}

	/**
	 * 保存データのリストの取得
	 * 
	 * @return list 保存データのリスト
	 */
	public List getList() {
		return list;
	}

	/**
	 * 保存データのリストの設定
	 * 
	 * @param list 保存データのリスト
	 */
	public void setList(List list) {
		this.list = list;
	}

}
