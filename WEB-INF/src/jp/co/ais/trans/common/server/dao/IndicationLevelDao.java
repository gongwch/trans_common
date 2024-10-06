package jp.co.ais.trans.common.server.dao;

/**
 * 開示レベル情報Dao
 */
public interface IndicationLevelDao {

	/** 対象Entity */
	public Class BEAN = IndicationLevel.class;

	/** 開示レベル情報取得 */
	public String getIndicationLevel_ARGS = "kaiCode, usrId, kmtCode, dpkSsk";

	/**
	 * 開示レベル情報取得
	 * 
	 * @param kaiCode 会社コード
	 * @param usrId ユーザコード
	 * @param kmtCode 科目体系コード
	 * @param dpkSsk 組織コード
	 * @return 開示レベル
	 */
	public IndicationLevel getIndicationLevel(String kaiCode, String usrId, String kmtCode, String dpkSsk);

}