package jp.co.ais.trans.common.server.di;

import jp.co.ais.trans.master.entity.*;

/**
 * マスタ参照ロジック
 */
public interface TReferenceAuthorityLogic {

	/**
	 * ログインユーザーの所属部門略称取得<BR>
	 * 
	 * @param kaiCode 会社コード
	 * @param curCode テキストフィールドに文字列
	 * @return BMN_MST 部門マスタ
	 */
	public BMN_MST getBMN_MSTByKaicodeDepcode(String kaiCode, String curCode);

	/**
	 * ログインユーザーの社員コードを取得<BR>
	 * 
	 * @param kaiCode 会社コード
	 * @param curCode テキストフィールドに文字列
	 * @return USR_MST ユーザーマスタ
	 */
	public USR_MST getUSR_MSTByKaicodeUsercode(String kaiCode, String curCode);

	/**
	 * ログインユーザーの社員略称を取得<BR>
	 * 
	 * @param kaiCode 会社コード
	 * @param empCode テキストフィールドに文字列
	 * @param depCode 所属部門コード
	 * @return EMP_MST ユーザーマスタ
	 */
	public EMP_MST getEMP_MSTByKaiCodeEmpCode(String kaiCode, String empCode, String depCode);
}
