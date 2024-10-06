package jp.co.ais.trans2.common.ui;

import java.util.*;

/**
 * 各種マスタリフレッシュ処理用の絞込み条件I/F
 */
public interface OPLoginCondition {

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode 会社コード
	 */
	public void setCompanyCode(String companyCode);

	/**
	 * 最終更新日時の設定
	 * 
	 * @param lastUpdateDate 最終更新日時
	 */
	public void setLastUpdateDate(Date lastUpdateDate);

	/**
	 * 最終更新日時の取得
	 * 
	 * @return lastUpdateDate 最終更新日時
	 */
	public Date getLastUpdateDate();

	/**
	 * 現在件数の設定
	 * 
	 * @param nowCount 現在件数
	 */
	public void setNowCount(int nowCount);

	/**
	 * 現在件数の取得
	 * 
	 * @return nowCount 現在件数
	 */
	public int getNowCount();
}
