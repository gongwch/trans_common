package jp.co.ais.trans.master.logic;

import jp.co.ais.trans.master.entity.*;

/**
 * ユーザ認証ビジネスロジック
 * 
 * @author roh
 */
public interface UserAuthLogic {

	/**
	 * データ検索.<br>
	 * 該当データが存在しない場合は、nullが返る.
	 * 
	 * @param kaiCode 会社コード
	 * @return データ.無い場合はnull
	 */
	public abstract USR_AUTH_MST find(String kaiCode);

	/**
	 * データを更新する.<br>
	 * 既にデータが存在する場合は、更新日時を入れて更新.<br>
	 * データが存在しない場合は、作成日時を入れて新規追加.
	 * 
	 * @param authDto 対象データ
	 */
	public abstract void update(USR_AUTH_MST authDto);
}
