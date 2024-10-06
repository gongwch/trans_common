package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * システム区分マスタビジネスロジック
 */
public interface SystemDivisionLogic {

	/**
	 * システム区分リスト取得
	 * 
	 * @param kaiCode 会社コード
	 * @return システム区分リスト
	 */
	List<SYS_MST> getSystemList(String kaiCode);

	/**
	 * システム区分取得
	 * 
	 * @param kaiCode
	 * @param syskbn
	 * @return システム区分
	 */
	SYS_MST getSystemKbn(String kaiCode, String syskbn);
}
