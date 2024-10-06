package jp.co.ais.trans.master.logic;

import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 銀行マスタビジネスロジック
 * 
 * @author roh
 */
public interface BankLogic extends CommonLogic {

	/**
	 * 銀行口座情報取得 PK検索（支店コード除外）
	 * 
	 * @param param
	 * @return 銀行情報
	 */
	BNK_MST getBankName(BnkMstParameter param);

	/**
	 * 銀行口座情報リスト取得 （支店コード除外）
	 * 
	 * @param param
	 * @return 銀行情報リスト
	 */
	List<BNK_MST> getBankList(BnkMstParameter param);

	/**
	 * 銀行支店情報取得 PK検索
	 * 
	 * @param param
	 * @return 銀行支店情報リスト
	 */
	BNK_MST getStnName(BnkMstParameter param);

	/**
	 * 銀行支店情報リスト取得
	 * 
	 * @param param
	 * @return 銀行支店情報リスト
	 */
	List<BNK_MST> getStnList(BnkMstParameter param);

}
