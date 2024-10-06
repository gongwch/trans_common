package jp.co.ais.trans.logic.util;

import java.sql.*;
import java.text.*;
import java.util.Date;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;

/**
 * AR請求書番号用自動採番Dao実装
 */
public class ARAutoControlDaoImpl extends TModel implements ARAutoControlDao {

	/**
	 * プレフィックスの取得<br>
	 * カスタマイズ用
	 * 
	 * @param division 自動設定の区分
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param departmentCode 入力部門
	 * @param slipDate 伝票日付(yyyy/mm/dd形式)
	 * @return プレフィックス
	 */
	public String getPrefix(String division, String companyCode, String userCode, String departmentCode, String slipDate) {
		return "";
	}

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
		String depCode, String slipDate) {
		try {

			switch (jid) {
				case NONE: // なし
					return "";

				case FIXED_CHAR: // 固有文字
					return jidName;

				case YY_DATE: // 年度(YY)
					String yy = Util.avoidNull(BizUtil.getFiscalYear(slipDate, companyCode));
					return StringUtil.rightBX(yy, 2);

				case YYYY_DATE:// 年度(YYYY)
					return Util.avoidNull(BizUtil.getFiscalYear(slipDate, companyCode));

				case MM_DATE: // 月度
					String mm = Util.avoidNull(BizUtil.getFiscalMonth(slipDate, companyCode));
					return StringUtil.rightBX(("00" + mm), 2);

				case YYMM_DATE: // 年月（YYMM）
					return slipDate.replaceAll("/", "").substring(3, 6);

				case YYYYMM_DATE: // 年月（YYYYMM）
					return StringUtil.leftBX(slipDate.replaceAll("/", ""), 6);

				case DEPARTMENT: // 部門
					return depCode;

				case USER: // ユーザー
					return userCode;

				case CODE: // 会社
					return companyCode;

				default:
					return "";
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return "";
	}

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
	public int getAutoNumber(String companyCode, String userCode, String prifix, int increase) {

		Connection conn = null;
		try {
			conn = DBUtil.getConnection();
			// 排他コントロールマスタをロック
			try {
				DBUtil.execute(conn, "LOCK TABLE AUTO_CTL IN SHARE ROW EXCLUSIVE MODE NOWAIT");
			} catch (TException e) {
				throw new TRuntimeException("W01133");// 只今混み合っております。しばらくお待ちください。
			}
		} catch (TException ex) {
			throw new TRuntimeException(ex);
		} finally {
			DBUtil.close(conn);
		}

		// 自動採番コントロールより取得(最終番号)
		AUTO_CTLDao autodao = (AUTO_CTLDao) getComponent(AUTO_CTLDao.class);
		AUTO_CTL auto = autodao.getAUTO_CTLByKaicodePrifix(companyCode, prifix);

		int lastno = increase;
		AUTO_CTL autoCTL = (AUTO_CTL) getComponent(AUTO_CTL.class);

		Date sysDate = Util.getCurrentDate();

		autoCTL.setKAI_CODE(companyCode);
		autoCTL.setPRIFIX(prifix);
		autoCTL.setUPD_DATE(sysDate);
		autoCTL.setUSR_ID(userCode);

		if (auto != null) {
			// 最終番号取得
			lastno = auto.getLAST_NO() + increase;
			autoCTL.setLAST_NO(lastno);
			// 更新処理
			autodao.update(autoCTL);
		} else {
			// 新規登録
			autoCTL.setLAST_NO(lastno);
			autoCTL.setINP_DATE(sysDate);
			autodao.insert(autoCTL);
		}

		return lastno;
	}

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
	public String getSuffix(String division, String companyCode, String userCode, String departmentCode, String slipDate) {
		return "";
	}

}