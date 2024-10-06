package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 伝票種類マスタビジネスロジック
 * 
 * @author roh
 */
public interface SlipTypeLogic extends CommonLogic {

	/**
	 * 伝票種別略称取得<BR>
	 * 
	 * @param kaiCode 会社コード
	 * @param isIncludeSystemEls true:他システムから取り込んだ伝票種別を対象とする
	 * @return 伝票種別略称リスト
	 */
	public List<DEN_SYU_MST> getDenSyuNames(String kaiCode, boolean isIncludeSystemEls);
}
