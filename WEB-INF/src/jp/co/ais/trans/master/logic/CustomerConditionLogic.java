package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.text.*;
import java.util.*;

/**
 * 支払い条件マスタビジネスロジック
 * 
 * @author roh
 */
public interface CustomerConditionLogic extends CommonLogic {

	/**
	 * ダイアログに表示される取引先リスト取得
	 * 
	 * @param kaiCode 会社コード
	 * @param triCode 取引先コード
	 * @param tjkCode 取引先条件コード
	 * @param termBasisDate 有効期間
	 * @return 取引先リスト
	 */
	public abstract List<Object> refDailog(String kaiCode, String triCode, String tjkCode, Timestamp termBasisDate);

	/**
	 * 取引先条件コードコードによる、取引先情報取得
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param tjkCode
	 * @return 取引先情報
	 */
	public abstract List<Object> searchName(String kaiCode, String triCode, String tjkCode);

	/**
	 * デフォルトの検索条件取得
	 * 
	 * @param kaiCode 会社コード
	 * @param triCode 取引先コード
	 * @param curCode 通貨コード
	 * @return map 取引先条件コード・支払銀行名
	 */

	public abstract Map<String, Object> getDefaultPaymentCondition(String kaiCode, String triCode, String curCode);

	/**
	 * 支払先条件入力時に 支払先統合情報を取得
	 * 
	 * @param param
	 * @return map 取引先統合情報
	 * @throws ParseException
	 */
	public abstract Map<String, Object> getPaymentConditionInfo(Map<String, String> param) throws ParseException;

	/**
	 * 取引条件を取得
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param triSjCode
	 * @return TRI_SJ_MST
	 */
	public abstract Object findOneInfo(String kaiCode, String triCode, String triSjCode);
}
