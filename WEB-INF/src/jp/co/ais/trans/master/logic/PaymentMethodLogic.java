package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 支払い方法ビジネスロジック
 * 
 * @author roh
 */
public interface PaymentMethodLogic extends CommonLogic {

	/**
	 * 支払い方法コードで支払い情報取得
	 * 
	 * @param keys
	 * @return 支払い方法情報
	 */
	public abstract AP_HOH_MST getREFItems(Map keys);

	/**
	 * 支払い方法リスト取得
	 * 
	 * @param kaiCode 会社コード
	 * @param hohCode 支払い方法コード
	 * @param sName 略称
	 * @param kName 索引略称
	 * @param termBasisDate 有効期間
	 * @return 支払い方法リスト
	 */
	public abstract List<Object> refDailog(String kaiCode, String hohCode, String sName, String kName,
		Timestamp termBasisDate);

}
