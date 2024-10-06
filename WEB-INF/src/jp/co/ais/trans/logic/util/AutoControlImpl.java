package jp.co.ais.trans.logic.util;

import java.sql.*;
import java.util.Date;

import org.seasar.framework.container.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.master.dao.*;
import jp.co.ais.trans.master.entity.*;
import jp.co.ais.trans2.common.db.DBUtil;

/**
 * 自動採番コントロールクラス実装
 * 
 * @author nagahashi
 */
public class AutoControlImpl implements AutoControl {

	/** コンテナ */
	private S2Container container;

	/** 計上部門コード */
	protected String depCode;

	/**
	 * コンストラクタ
	 * 
	 * @param container コンテナ
	 */
	public AutoControlImpl(S2Container container) {
		super();
		this.container = container;
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
		AUTO_CTLDao autodao = (AUTO_CTLDao) container.getComponent(AUTO_CTLDao.class);
		AUTO_CTL auto = autodao.getAUTO_CTLByKaicodePrifix(companyCode, prifix);
		int lastno = increase;
		AUTO_CTL autoCTL = (AUTO_CTL) container.getComponent(AUTO_CTL.class);

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
	 * 自動設定項目の取得(伝票種別追加)
	 * 
	 * @param division 自動設定の区分
	 * @param companyCode 会社コード
	 * @param userCode ユーザーコード
	 * @param loginDepCode 入力部門
	 * @param systemDivision システム区分
	 * @param slipDate 伝票日付(yyyy/mm/dd形式)
	 * @param slipType 伝票種別
	 * @param kisyu
	 * @return 自動設定項目
	 */
	public String getAutoSetting(String division, String companyCode, String userCode, String loginDepCode,
		String systemDivision, String slipDate, String slipType, Integer kisyu) {

		if (division.equals("1")) {
			if (Integer.parseInt(slipDate.substring(5, 7)) < kisyu) {
				String resNendo = String.valueOf(Integer.parseInt(slipDate.substring(2, 4)) - 1);
				if (1 == resNendo.length()) {
					resNendo = "0" + resNendo;
				}
				return resNendo;
			} else {
				return slipDate.substring(2, 4);
			}
		} else if (division.equals("2")) {
			if (Integer.parseInt(slipDate.substring(5, 7)) < kisyu) {
				return String.valueOf(Integer.parseInt(slipDate.substring(0, 4)) - 1);
			} else {
				return slipDate.substring(0, 4);
			}
		} else if (division.equals("3")) {
			return slipDate.substring(2, 4) + slipDate.substring(5, 7);
		} else if (division.equals("4")) {
			return slipDate.substring(0, 4) + slipDate.substring(5, 7);
		} else if (division.equals("5")) {
			return userCode;
		} else if (division.equals("6")) {
			return Util.isNullOrEmpty(this.depCode) ? loginDepCode : this.depCode;
		} else if (division.equals("7")) {
			return systemDivision;
		} else if (division.equals("8")) {
			return companyCode;
		} else if (division.equals("9")) {
			return slipType;
		}

		return "";
	}

	/**
	 * 計上部門コードを設定する。
	 * 
	 * @param depCode
	 */
	public void setDepCode(String depCode) {
		this.depCode = depCode;
	}

	/**
	 * プレフィックスの取得<br>
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
	 * @return プレフィックス
	 */
	public String getPrefix(String division, String companyCode, String userCode, String departmentCode,
		String systemDivision, String slipDate, String slipType, Integer kisyu) {
		return "";
	}

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
		String systemDivision, String slipDate, String slipType, Integer kisyu) {
		return "";
	}

}
