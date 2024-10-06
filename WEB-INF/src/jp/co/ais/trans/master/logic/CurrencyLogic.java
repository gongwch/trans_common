package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

/**
 * 通貨マスタビジネスロジック
 * 
 * @author roh
 */
public interface CurrencyLogic extends CommonLogic {

	/**
	 * 検索条件による通貨リスト取得
	 * 
	 * @param kaiCode 会社コード
	 * @param curCode 通貨コード
	 * @param sName 略称
	 * @param kName 索引略称
	 * @param termBasisDate 有効期間
	 * @param beginCode
	 * @param endCode
	 * @return 通貨リスト
	 */
	public abstract List<Object> refDailogCurrency(String kaiCode, String curCode, String sName, String kName,
		Timestamp termBasisDate, String beginCode, String endCode);

	/**
	 * 通貨コードによる、通貨情報取得
	 * 
	 * @param keys
	 * @return 通貨情報
	 */
	public abstract List getREFItems(Map keys);

}
