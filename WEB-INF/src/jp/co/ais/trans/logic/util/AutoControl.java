package jp.co.ais.trans.logic.util;

/**
 * 自動採番コントロールクラス
 * 
 * @author nagahashi
 */
public interface AutoControl {

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

	/**
	 * 自動設定項目の取得(伝票種別追加)
	 * 
	 * @param division 自動設定の区分
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param depCode 入力部門
	 * @param systemDivision システム区分
	 * @param slipDate 伝票日付(yyyy/mm/dd形式)
	 * @param slipType 伝票種別
	 * @param kisyu
	 * @return 自動設定項目
	 */
	public String getAutoSetting(String division, String companyCode, String userCode, String depCode,
		String systemDivision, String slipDate, String slipType, Integer kisyu);

	/**
	 * プレフィックスの取得
	 * 
	 * @param division 自動設定の区分
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param depCode 入力部門
	 * @param systemDivision システム区分
	 * @param slipDate 伝票日付(yyyy/mm/dd形式)
	 * @param slipType 伝票種別
	 * @param kisyu
	 * @return プレフィックス
	 */
	public String getPrefix(String division, String companyCode, String userCode, String depCode,
		String systemDivision, String slipDate, String slipType, Integer kisyu);

	/**
	 * サフィックスの取得<br>
	 * カスタマイズ用
	 * 
	 * @param division 自動設定の区分
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param departmentCode 入力部門
	 * @param systemDivision システム区分
	 * @param slipDate 伝票日付(yyyy/mm/dd形式)
	 * @param slipType 伝票種別
	 * @param kisyu
	 * @return サフィックス
	 */
	public String getSuffix(String division, String companyCode, String userCode, String departmentCode,
		String systemDivision, String slipDate, String slipType, Integer kisyu);

}
