package jp.co.ais.trans2.model.slip;

import jp.co.ais.trans.common.dt.*;

/**
 * テンポラリーテーブル名設定クラス
 */
public interface TempTableNameGetterSetting {

	/**
	 * ワークテーブル名の取得
	 * 
	 * @param bean
	 * @return T_BALANCE_WORKテーブル名
	 */
	public String getBalanceWorkTableName(TransferBase bean);

	/**
	 * 一時テーブル名の取得
	 * 
	 * @param bean
	 * @param defaultTableName デフォルト一時テーブルID
	 * @return 各プログラムで使う一時テーブル名
	 */
	public String getTemporaryWorkTableName(TransferBase bean, String defaultTableName);

}
