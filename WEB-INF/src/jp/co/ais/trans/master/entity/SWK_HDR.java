package jp.co.ais.trans.master.entity;

import java.util.*;

/**
 * 共通伝票ヘッダ
 */
// TODO そのうち共通項目を移動してInterfaceをやめる
public interface SWK_HDR {

	/**
	 * 会社コード
	 * 
	 * @return 会社コード
	 */
	public String getKAI_CODE();

	/**
	 * 伝票日付
	 * 
	 * @return 伝票日付
	 */
	public Date getSWK_DEN_DATE();

	/**
	 * 伝票番号
	 * 
	 * @return 伝票番号
	 */
	public String getSWK_DEN_NO();
}
