package jp.co.ais.trans.common.server.dao;

/**
 * 現場承認FLAG取得用Dao(旧バージョン).<br>
 * KTの方を利用すること。
 */
public interface CMP_MSTDao {

	/** 対象Bean */
	public Class BEAN = CMP_MST.class;

	/** ARGS */
	public static final String getCMP_MSTByKaicode_ARGS = "kaiCode";

	/**
	 * データ取得（指定）
	 * 
	 * @param kaiCode 会社コード
	 * @return 0 or 1 (CMP_G_SHONIN_FLG)
	 */
	public int getCMP_MSTByKaicode(String kaiCode);

	/**
	 * データ取得
	 * 
	 * @param kaiCode 会社コード
	 * @return CMP_MST
	 */
	public CMP_MST getCMP_MSTData(String kaiCode);

}
