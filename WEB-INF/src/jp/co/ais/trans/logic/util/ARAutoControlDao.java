package jp.co.ais.trans.logic.util;

import jp.co.ais.trans2.define.*;

/**
 * AR請求書番号用自動採番Daoインターフェース
 * 
 * @author AIS
 */
public interface ARAutoControlDao {

	/**
	 * プレフィックスの取得
	 * 
	 * @param division 自動設定の区分
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param depCode 入力部門
	 * @param slipDate 伝票日付(yyyy/mm/dd形式)
	 * @return プレフィックス
	 */
	public String getPrefix(String division, String companyCode, String userCode, String depCode, String slipDate);

	/**
	 * 自動設定項目の取得
	 * 
	 * @param jid 自動設定の区分
	 * @param jidName 自動設定の固有文言
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param depCode 入力部門
	 * @param slipDate 伝票日付(yyyy/mm/dd形式)
	 * @return 自動設定項目
	 */
	public String getAutoSetting(InvoiceNoAdopt jid, String jidName, String companyCode, String userCode,
		String depCode, String slipDate);

	/**
	 * サフィックスの取得<br>
	 * カスタマイズ用
	 * 
	 * @param division 自動設定の区分
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param departmentCode 入力部門
	 * @param slipDate 伝票日付(yyyy/mm/dd形式)
	 * @return サフィックス
	 */
	public String getSuffix(String division, String companyCode, String userCode, String departmentCode, String slipDate);

	/**
	 * 自動採番された番号を取得<br>
	 * 自動採番コントロールの更新も行う
	 * 
	 * @param companyCode
	 * @param userCode
	 * @param prifix
	 * @param increase
	 * @return 自動採番番号
	 */
	public int getAutoNumber(String companyCode, String userCode, String prifix, int increase);

}