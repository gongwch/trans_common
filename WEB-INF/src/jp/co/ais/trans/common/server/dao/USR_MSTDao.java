package jp.co.ais.trans.common.server.dao;

/**
 * ユーザーシステム区分取得用 Dao
 */
public interface USR_MSTDao {

	/** Entity */
	public Class BEAN = USR_MST.class;

	/** getUSR_MSTByKaicodeUsrcode ARGS */
	public String getUSR_MSTByKaicodeUsrcode_ARGS = "KAI_CODE,USR_CODE";

	/**
	 * 指定されたデータの取得(ユーザ名)
	 * 
	 * @param kaiCode 会社コード
	 * @param usrCode ユーザコード
	 * @return ユーザ
	 */
	public USR_MST getUSR_MSTByKaicodeUsrcode(String kaiCode, String usrCode);

}