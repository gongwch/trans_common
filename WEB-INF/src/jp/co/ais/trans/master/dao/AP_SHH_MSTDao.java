package jp.co.ais.trans.master.dao;

import java.util.List;

import jp.co.ais.trans.master.entity.AP_SHH_MST;

/**
 * 支払方針マスタDao
 */
public interface AP_SHH_MSTDao {

	/** */
	public Class BEAN = AP_SHH_MST.class;

	/**
	 * 全体習得
	 * 
	 * @return List
	 */
	public List getAllAP_SHH_MST();

	/** パラメータ */
	public String getAP_SHH_MST_QUERY = "KAI_CODE = ?";

	/**
	 * 支払方針取得
	 * 
	 * @param KAI_CODE
	 * @return 支払方針Bean
	 */
	public AP_SHH_MST getAP_SHH_MST(String KAI_CODE);

	/** パラメータ */
	public String getAP_SHH_MSTByIds_ARGS = "KAI_CODEs";

	/** パラメータ */
	public String getAP_SHH_MSTByIds_QUERY = "KAI_CODE in /*kAI_CODEs*/(1)";

	/**
	 * 会社コードリストで支払方針リスト取得
	 * 
	 * @param kAI_CODEs
	 * @return List
	 */
	public List getAP_SHH_MSTByIds(List kAI_CODEs);

	/**
	 * 登録
	 * 
	 * @param dto
	 */
	public void insert(AP_SHH_MST dto);

	/**
	 * 更新
	 * 
	 * @param dto
	 */
	public void update(AP_SHH_MST dto);

	/**
	 * 削除
	 * 
	 * @param dto
	 */
	public void delete(AP_SHH_MST dto);

	// 下記は ISFnet China 追加分

	/**
	 * @param kaiCode
	 * @return List
	 */
	public List getAllAP_SHH_MST2(String kaiCode);

}
