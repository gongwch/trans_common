package jp.co.ais.trans.master.logic;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.master.entity.*;

/**
 * 取引先マスタビジネスロジック
 * 
 * @author roh
 */
public interface CustomerLogic extends CommonLogic {

	/**
	 * 取引先のボタンフィールドの条件文に対応するように設定。
	 * 
	 * @param kaiCode
	 * @param triCode
	 * @param sName
	 * @param kName
	 * @param termBasisDate
	 * @param siire
	 * @param tokui
	 * @param beginCode
	 * @param endCode
	 * @return 取引先情報リスト
	 */
	List<Object> refSearchCustomer(String kaiCode, String triCode, String sName, String kName, Timestamp termBasisDate,
		boolean siire, boolean tokui, String beginCode, String endCode);

	/**
	 * 取引先データ検索
	 * 
	 * @param customerCode 取引先コード
	 * @param beginCode 開始コード
	 * @param endCode 終了コード
	 * @return 取引先データ
	 */
	TRI_MST findCustomer(String customerCode, String beginCode, String endCode);

	/**
	 * 会社コードの設定
	 * 
	 * @param companyCode 会社コード
	 */
	void setCompanyCode(String companyCode);

	/**
	 * 依頼名称更新
	 * 
	 * @param dto
	 */
	public void updateIraiName(TRI_MST dto);
}
