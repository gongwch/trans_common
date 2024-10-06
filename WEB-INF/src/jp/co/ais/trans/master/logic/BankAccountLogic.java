package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

/**
 * 銀行口座マスタビジネスロジック
 * 
 * @author roh
 */
public interface BankAccountLogic extends CommonLogic {

	/**
	 * 口座コードで銀行口座情報取得
	 * 
	 * @param keys
	 * @return 銀行口座情報
	 */
	public abstract List getREFItems(Map keys);

	/**
	 * 結果を検索する
	 * 
	 * @param conditions
	 */
	public abstract List findREF(Map conditions);

	/**
	 * 銀行口座検索
	 * 
	 * @param kaiCode 会社コード
	 * @param code 銀行コード
	 * @param nameS 略称
	 * @param nameK 索引略称
	 * @param outkbn 社外区分
	 * @param empkbn 社員区分
	 * @param termBasisDate 有効期間
	 * @return 銀行口座リスト
	 */
	public abstract List<Object> searchReservationBankAccount(String kaiCode, String code, String nameS, String nameK,
		boolean outkbn, boolean empkbn, Timestamp termBasisDate);

	/**
	 * デフォルトの入金銀行口座を取得
	 * 
	 * @param kaiCode
	 * @return Map<String, Object>
	 */
	public abstract Map<String, Object> getDefaultReceivedAccount(String kaiCode);

}
