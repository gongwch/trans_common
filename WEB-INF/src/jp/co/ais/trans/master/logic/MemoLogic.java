package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

/**
 * 摘要マスタビジネスロジック
 * 
 * @author roh
 */
public interface MemoLogic extends CommonLogic {

	/**
	 * 摘要コードで詳細を取得
	 * 
	 * @param keys
	 * @return 摘要情報
	 */
	public abstract List getREFItems(Map keys);

	/**
	 * パラメータ条件で摘要情報リスト習得
	 * 
	 * @param kaiCode 会社コード
	 * @param tekCode 摘要コード
	 * @param sName 略称
	 * @param kName 索引略称
	 * @param memoType 行摘要タイプ
	 * @param slipType 伝票摘要タイプ
	 * @param termBasisDate 有効期間
	 * @return 摘要リスト
	 */
	public abstract List<Object> refDailog(String kaiCode, String tekCode, String sName, String kName, int memoType,
		String slipType, Timestamp termBasisDate);

}
