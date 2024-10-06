package jp.co.ais.trans.master.dao;

import java.util.List;
import jp.co.ais.trans.master.entity.AP_CBK_MST2;

/**
 * 銀行口座マスタDao (一覧画面用)
 */
public interface AP_CBK_MSTDao2 {

	/** Bean */
	public Class BEAN = AP_CBK_MST2.class;

	/** Query */
	public String query_ARGS = "kaiCode,cbkCbkCodeFrom,cbkCbkCodeTo";

	/**
	 * @param kaiCode
	 * @param cbkCbkCodeFrom
	 * @param cbkCbkCodeTo
	 * @return List
	 */
	public List query(String kaiCode, String cbkCbkCodeFrom, String cbkCbkCodeTo);
}
