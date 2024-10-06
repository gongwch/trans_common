package jp.co.ais.trans2.model.balance;

import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.calc.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.*;
import jp.co.ais.trans2.model.security.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 残高マネージャ実装
 */
public class BalanceManagerImpl extends TModel implements BalanceManager {

	/** true:CM_FUND_DTL登録を行うか */
	public static boolean isUseCmFund = ServerConfig.isFlagOn("trans.use.cm.fund.entry");

	/** 計算ロジック */
	public TCalculator calculator = TCalculatorFactory.getCalculator();

	/**
	 * AP/AR残高更新
	 * 
	 * @param slip 伝票
	 */
	public void updateBalance(Slip slip) {

		// 更新の場合、既存をクリア
		BalanceCondition condition = new BalanceCondition();
		condition.setCompanyCode(slip.getCompanyCode()); // 会社コード
		condition.setEraseSlipNo(slip.getSlipNo()); // 伝票番号
		condition.setProgramCode(getProgramCode()); // プログラムID
		condition.setUserCode(getUserCode()); // ユーザーコード
		condition.setUpdateDate(getNow()); // 更新日付

		// 債務残高の更新(支払済みを取り消し)
		clearApBalance(condition);

		// 債権残高の削除(会社コード,伝票番号)
		clearArBalance(condition);

		// 残高更新
		List<AP_ZAN> apList = new ArrayList<AP_ZAN>();
		List<AR_ZAN> arList = new ArrayList<AR_ZAN>();

		String dataKind = slip.getHeader().getSWK_DATA_KBN();

		for (SWK_DTL dtl : slip.getDetails()) {
			if (dtl.isFunctionalCurrency()) {
				continue;
			}

			AP_ZAN apZan = dtl.getAP_ZAN();
			if (apZan != null) {
				// apZan.setZAN_DEN_DATE(); // 債務残高伝票日付
				// apZan.setZAN_DEN_NO(); // 債務残高伝票番号
				// apZan.setZAN_GYO_NO(); // 債務残高行番号
				apZan.setZAN_JSK_DATE(dtl.getSWK_DEN_DATE()); // 伝票日付
				apZan.setZAN_SIHA_DEN_NO(dtl.getSWK_DEN_NO()); // 伝票番号
				apZan.setZAN_SIHA_GYO_NO(dtl.getSWK_GYO_NO()); // 行番号

				apZan.setUPD_DATE(getNow()); // 更新日付
				apZan.setPRG_ID(getProgramCode()); // プログラムＩＤ
				apZan.setUSR_ID(getUserCode()); // ユーザーＩＤ
				apList.add(apZan);
			}

			AR_ZAN arZan = dtl.getAR_ZAN();
			if (arZan != null) {
				arZan.setZAN_DATA_KBN(dataKind); // データ区分
				arZan.setZAN_SOUSAI_FLG(0); // 相殺フラグ
				arZan.setZAN_KESI_DEN_DATE(dtl.getSWK_DEN_DATE()); // 消込日付
				arZan.setZAN_KESI_DEN_NO(dtl.getSWK_DEN_NO()); // 消込伝票番号
				arZan.setZAN_KESI_GYO_NO(dtl.getSWK_GYO_NO()); // 消込伝票行番号
				arZan.setZAN_KESI_IN_KIN(dtl.getSWK_IN_KIN()); // 消込金額(外貨)
				arZan.setZAN_KESI_KIN(dtl.getSWK_KIN()); // 消込金額
				arZan.setZAN_SEI_IN_KIN(null); // 請求金額(外貨) クリア
				arZan.setZAN_SEI_KIN(null); // 請求金額 クリア
				arZan.setUPD_DATE(getNow()); // 更新日付
				arZan.setPRG_ID(getProgramCode()); // プログラムＩＤ
				arZan.setUSR_ID(getUserCode()); // ユーザーＩＤ

				arList.add(arZan);
			}
		}

		// 債務残高
		if (!apList.isEmpty()) {
			updateApBalance(apList);
		}

		// 債権残高
		if (!arList.isEmpty()) {
			insertArBalance(arList);
		}

		if (isUseCmFund) {
			try {
				// 削除 && 伝票番号リストを取得する
				List<String> apSlipNoList = deleteCmFundInfoAPAR(condition, true);
				List<String> arSlipNoList = deleteCmFundInfoAPAR(condition, false);

				if (apSlipNoList != null && apSlipNoList.size() > 0) {
					updateCmFundDtl(slip.getCompanyCode(), apSlipNoList, true);
				}
				if (arSlipNoList != null && arSlipNoList.size() > 0) {
					updateCmFundDtl(slip.getCompanyCode(), arSlipNoList, false);
				}
			} catch (TException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * AP残高にロックをかける(行単位)
	 * 
	 * @param list 対象残高(条件)リスト
	 * @throws TException 排他失敗
	 */
	public void lockApBalance(List<BalanceCondition> list) throws TException {

		if (isOtherApErased(list)) {
			throw new TException(getMessage("I00115"));// 既に他ユーザによって消し込まれたデータが存在します。再度検索を行ってください。
		}

		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);

		for (BalanceCondition condition : list) {
			String slipNo = condition.getSlipNo();
			String lineNo = String.valueOf(condition.getSlipLineNo());

			// 2：債務残高消込
			codeExManager.exclude("2", slipNo, lineNo);
		}
	}

	/**
	 * 他ユーザに先に消し込まれていないかチェック(AP)
	 * 
	 * @param list 対象残高(条件)リスト
	 * @return true:消し込まれている
	 */
	protected boolean isOtherApErased(List<BalanceCondition> list) {

		// チェック用リストにコピー
		List<BalanceCondition> checkList = new ArrayList<BalanceCondition>(list.size());
		checkList.addAll(list);

		// 伝票番号を纏めて再検索
		BalanceCondition condition = new BalanceCondition();
		condition.setCompanyCode(getCompanyCode());

		for (BalanceCondition con : list) {
			condition.setNotIncledEraseSlipNo(con.getNotIncledEraseSlipNo());
			condition.addSlipNo(con.getSlipNo());
		}

		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);
		List<AP_ZAN> resList = apBalanceDao.findForExists(condition);

		for (AP_ZAN zan : resList) {
			String slipNo = zan.getZAN_DEN_NO();
			int slipLineNo = zan.getZAN_GYO_NO();

			for (BalanceCondition con : checkList) {
				if (slipNo.equals(con.getSlipNo()) && slipLineNo == con.getSlipLineNo()) {
					checkList.remove(con);
					break;
				}
			}
		}

		return !checkList.isEmpty();
	}

	/**
	 * AR残高にロックをかける(行単位)
	 * 
	 * @param list 対象残高(条件)リスト
	 * @throws TException 排他失敗
	 */
	public void lockArBalance(List<BalanceCondition> list) throws TException {

		if (isOtherArErased(list)) {
			throw new TException(getMessage("I00115"));// 既に他ユーザによって消し込まれたデータが存在します。再度検索を行ってください。
		}

		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);

		for (BalanceCondition condition : list) {
			String slipNo = condition.getSlipNo();
			String lineNo = String.valueOf(condition.getSlipLineNo());

			// 1：債権残高消込
			codeExManager.exclude("1", slipNo, lineNo);
		}
	}

	/**
	 * 他ユーザに先に消し込まれていないかチェック(AR)
	 * 
	 * @param list 対象残高(条件)リスト
	 * @return true:消し込まれている
	 */
	protected boolean isOtherArErased(List<BalanceCondition> list) {

		// チェック用リストにコピー
		List<BalanceCondition> checkList = new ArrayList<BalanceCondition>(list.size());
		checkList.addAll(list);

		// 伝票番号を纏めて再検索
		BalanceCondition condition = new BalanceCondition();
		condition.setCompanyCode(getCompanyCode());

		for (BalanceCondition con : list) {
			condition.setNotIncledEraseSlipNo(con.getNotIncledEraseSlipNo());
			condition.addSlipNo(con.getSlipNo());
		}

		AR_ZANDao arBalanceDao = (AR_ZANDao) getComponent(AR_ZANDao.class);
		List<AR_ZAN> resList = arBalanceDao.findForExists(condition);

		for (AR_ZAN zan : resList) {
			String slipNo = zan.getZAN_SEI_DEN_NO();
			int slipLineNo = zan.getZAN_SEI_GYO_NO();
			BigDecimal kin = zan.getNON_KESI_KIN(); // 消込対象金額

			for (BalanceCondition con : checkList) {
				if (slipNo.equals(con.getSlipNo()) && slipLineNo == con.getSlipLineNo()) {
					// 消込対象金額
					if (kin.compareTo(con.getKin()) != 0) {
						return true;
					}

					checkList.remove(con);
					break;
				}
			}
		}

		return !checkList.isEmpty();
	}

	/**
	 * AP残高のロック解除
	 * 
	 * @param condition 対象残高(条件)
	 * @throws TException 排他失敗
	 */
	public void unlockApBalance(BalanceCondition condition) throws TException {
		String slipNo = condition.getSlipNo();
		String lineNo = String.valueOf(condition.getSlipLineNo());

		// 2：債務残高消込
		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);
		codeExManager.cancelExclude("2", slipNo, lineNo);
	}

	/**
	 * AP残高のロック解除
	 * 
	 * @param condition 対象残高(条件)
	 * @throws TException 排他失敗
	 */
	public void unlockApBalanceList(BalanceCondition condition) throws TException {

		// 2：債務残高消込
		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);
		codeExManager.cancelExclude("2", condition.getSlipNoList(), condition.getRowNoList());
	}

	/**
	 * AR残高のロック解除(行単位)
	 * 
	 * @param condition 対象残高(条件)
	 * @throws TException 排他失敗
	 */
	public void unlockArBalance(BalanceCondition condition) throws TException {
		String slipNo = condition.getSlipNo();
		String lineNo = String.valueOf(condition.getSlipLineNo());

		// 1：債権残高消込
		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);
		codeExManager.cancelExclude("1", slipNo, lineNo);
	}

	/**
	 * AR残高のロック解除(行単位)
	 * 
	 * @param condition 対象残高(条件)
	 * @throws TException 排他失敗
	 */
	public void unlockArBalanceList(BalanceCondition condition) throws TException {

		// 1：債権残高消込
		CodeExclusiveManager codeExManager = (CodeExclusiveManager) getComponent(CodeExclusiveManager.class);
		codeExManager.cancelExclude("1", condition.getSlipNoList(), condition.getRowNoList());
	}

	/**
	 * 債務残高データ取得
	 * 
	 * @param conditon 条件
	 * @return 残高データ
	 */
	public List<AP_ZAN> getApBalance(BalanceCondition conditon) {
		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);
		return apBalanceDao.findByCondition(conditon.toSQL());
	}

	/**
	 * 債務残高削除(消し込まれていない状態に戻す)
	 * 
	 * @param condition 条件
	 */
	public void clearApBalance(BalanceCondition condition) {
		try {
			List<String> slipNoList = null;
			if (isUseCmFund) {
				// 先に削除
				slipNoList = deleteCmFundInfoAPAR(condition, true);
			}

			AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);
			// 支払済みを取り消し
			apBalanceDao.updateCancelBalance(condition);

			if (isUseCmFund) {
				if (slipNoList != null && slipNoList.size() > 0) {
					updateCmFundDtl(condition.getCompanyCode(), slipNoList, false);
				}
			}
		} catch (TException e) {
			throw new TRuntimeException(e);
		}
	}

	/**
	 * 債務残高更新(支払済みの状態にする)
	 * 
	 * @param list 残高リスト
	 */
	public void updateApBalance(List<AP_ZAN> list) {

		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);

		BalanceCondition condition = new BalanceCondition();

		// 支払済みの状態に更新
		for (AP_ZAN zan : list) {
			condition.setCompanyCode(zan.getKAI_CODE()); // 会社コード
			condition.setSlipDate(zan.getZAN_DEN_DATE()); // 債務残高 伝票日付
			condition.setSlipNo(zan.getZAN_DEN_NO()); // 債務残高 伝票番号
			condition.setSlipLineNo(zan.getZAN_GYO_NO()); // 債務残高 行番号
			condition.setEraseSlipDate(zan.getZAN_JSK_DATE()); // 伝票日付(実績日)
			condition.setEraseSlipNo(zan.getZAN_SIHA_DEN_NO()); // 伝票番号(支払伝票番号)
			condition.setEraseSlipLineNo(zan.getZAN_SIHA_GYO_NO()); // 行番号(支払行番号)
			condition.setProgramCode(getProgramCode()); // プログラムID
			condition.setUserCode(getUserCode()); // ユーザーコード
			condition.setUpdateDate(getNow()); // 更新日

			// 債務残高の更新()
			apBalanceDao.updateBalance(condition);
		}
	}

	/**
	 * 債権残高データ取得(サマリ版)
	 * 
	 * @param conditon 条件
	 * @return List データ
	 */
	public List<AR_ZAN> getSummaryArBalance(BalanceCondition conditon) {
		AR_ZANDao arBalanceDao = (AR_ZANDao) getComponent(AR_ZANDao.class);
		return arBalanceDao.findSummry(conditon.toSQL());
	}

	/**
	 * 債務残高データ取得
	 * 
	 * @param conditon 条件
	 * @return 残高データ
	 */
	public List<AR_ZAN> getArBalance(BalanceCondition conditon) {
		AR_ZANDao arBalanceDao = (AR_ZANDao) getComponent(AR_ZANDao.class);
		return arBalanceDao.findByCondition(conditon.toSQL());
	}

	/**
	 * 債務残高削除(消し込みデータの削除)
	 * 
	 * @param condition 条件
	 */
	public void clearArBalance(BalanceCondition condition) {

		try {
			List<String> slipNoList = null;
			if (isUseCmFund) {
				// 先に削除
				slipNoList = deleteCmFundInfoAPAR(condition, false);
			}
			AR_ZANDao arBalanceDao = (AR_ZANDao) getComponent(AR_ZANDao.class);
			arBalanceDao.deleteBySlipNo(condition.getCompanyCode(), condition.getEraseSlipNo());

			if (isUseCmFund) {
				if (slipNoList != null && slipNoList.size() > 0) {
					updateCmFundDtl(condition.getCompanyCode(), slipNoList, false);
				}
			}
		} catch (TException e) {
			throw new TRuntimeException(e);
		}
	}

	/**
	 * 債権残高更新(消し込みデータの追加)<br>
	 * swkDtlをBeansで渡し、残高情報更新
	 * 
	 * @param list 残高リスト
	 */
	public void insertArBalance(List<AR_ZAN> list) {
		AR_ZANDao arBalanceDao = (AR_ZANDao) getComponent(AR_ZANDao.class);

		for (AR_ZAN zan : list) {
			arBalanceDao.insert(zan);
		}
	}

	/**
	 * 債務残高更新(消し込みデータの追加)<br>
	 * swkDtlをBeansで渡し、残高情報更新
	 * 
	 * @param list 残高リスト
	 */
	public void insertApBalance(List<AP_ZAN> list) {
		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);

		for (AP_ZAN zan : list) {
			apBalanceDao.insert(zan);
		}
	}

	/**
	 * 登録
	 * 
	 * @param bean エンティティ
	 */
	public void insertByUpdateDate(AP_ZAN bean) {
		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);

		apBalanceDao.insertByUpdateDate(bean);
	}

	/**
	 * 更新
	 * 
	 * @param bean 残高リスト
	 */
	public int updateByUpdateDate(AP_ZAN bean) {
		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);

		return apBalanceDao.updateByUpdateDate(bean, getNow());
	}

	/**
	 * 削除
	 * 
	 * @param bean 残高リスト
	 * @return int
	 */
	public int deleteByUpdateDate(AP_ZAN bean) {
		AP_ZANDao apBalanceDao = (AP_ZANDao) getComponent(AP_ZANDao.class);

		return apBalanceDao.deleteByUpdateDate(bean);
	}

	/**
	 * 債務残高 1件検索(今はキーのみ)
	 * 
	 * @param condition 条件
	 * @return 債務残高
	 */
	public AP_ZAN getAP(BalanceCondition condition) {
		AP_ZANDao dao = (AP_ZANDao) getComponent(AP_ZANDao.class);

		String compCode = condition.getCompanyCode();
		String slipNo = condition.getSlipNo();
		int slipLineNo = condition.getSlipLineNo();

		return dao.findByPrimaryKey(compCode, slipNo, slipLineNo);
	}

	/**
	 * 債権残高残高 1件検索(今はキーのみ)
	 * 
	 * @param condition 条件
	 * @return 債務残高
	 */
	public AR_ZAN getAR(BalanceCondition condition) {
		AR_ZANDao dao = (AR_ZANDao) getComponent(AR_ZANDao.class);

		String compCode = condition.getCompanyCode();
		String slipNo = condition.getSlipNo();
		int slipLineNo = condition.getSlipLineNo();

		return dao.findByPrimaryKey(compCode, slipNo, slipLineNo);
	}

	/**
	 * CM_FUND_DTL情報の削除
	 * 
	 * @param condition
	 * @param isAP
	 * @return List<String>
	 * @throws TException
	 */
	public List<String> deleteCmFundInfoAPAR(BalanceCondition condition, boolean isAP) throws TException {
		Connection conn = DBUtil.getConnection();

		List<String> slipNoList = new ArrayList<String>();

		try {
			// 残高取得
			VCreator sql = new VCreator();
			sql.add(" SELECT ");
			sql.add("  KAI_CODE, ");
			if (isAP) {
				sql.add("  ZAN_DEN_DATE, ");
				sql.add("  ZAN_DEN_NO ");
				sql.add(" FROM AP_ZAN ");
				sql.add(" WHERE 1 = 1");
				if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
					sql.add(" AND KAI_CODE = ? ", condition.getCompanyCode());
				}
				sql.add(" AND   ZAN_SIHA_DEN_NO = ? ", condition.getEraseSlipNo());
			} else {
				sql.add("  ZAN_SEI_DEN_DATE, ");
				sql.add("  ZAN_SEI_DEN_NO, ");
				sql.add("  ZAN_SEI_GYO_NO, ");
				sql.add("  ZAN_SEI_NO, ");
				sql.add("  ZAN_DEP_CODE, ");
				sql.add("  ZAN_TRI_CODE ");
				sql.add(" FROM AR_ZAN ");
				sql.add(" WHERE 1 = 1");
				if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
					sql.add(" AND KAI_CODE = ? ", condition.getCompanyCode());
				}
				sql.add(" AND   ZAN_KESI_DEN_NO = ? ", condition.getEraseSlipNo());
			}

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String kaiCode = null;
			String slipNo = null;
			Date slipDate = null;

			String seiNo = null;
			int gyoNo = -1;
			String depCode = null;
			String triCode = null;

			while (rs.next()) {

				kaiCode = rs.getString("KAI_CODE");
				if (isAP) {
					slipNo = rs.getString("ZAN_DEN_NO");
					slipDate = rs.getDate("ZAN_DEN_DATE");
				} else {
					slipNo = rs.getString("ZAN_SEI_DEN_NO");
					slipDate = rs.getDate("ZAN_SEI_DEN_DATE");

					seiNo = rs.getString("ZAN_SEI_NO");
					gyoNo = rs.getInt("ZAN_SEI_GYO_NO");
					depCode = rs.getString("ZAN_DEP_CODE");
					triCode = rs.getString("ZAN_TRI_CODE");
				}
				// 削除共通
				sql = new VCreator();
				sql.add(" DELETE FROM CM_FUND_DTL ");
				sql.add(" WHERE KAI_CODE     = ? ", kaiCode);
				sql.add(" AND   KEY_DEN_DATE = ? ", slipDate);
				sql.add(" AND   KEY_DEN_NO   = ? ", slipNo);
				if (seiNo != null) {
					sql.add(" AND   KEY_SEI_NO   = ? ", seiNo);
				}
				if (gyoNo != -1) {
					sql.add(" AND   KEY_GYO_NO   = ? ", gyoNo);
				}
				if (depCode != null) {
					sql.add(" AND   KEY_DEP_CODE   = ? ", depCode);
				}
				if (triCode != null) {
					sql.add(" AND   KEY_TRI_CODE   = ? ", triCode);
				}
				sql.add(" AND   DATA_TYPE    = 2 ");

				DBUtil.execute(sql);

				slipNoList.add(slipNo);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

			return slipNoList;

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * CM_FUND_DTL情報の更新
	 * 
	 * @param kaiCode
	 * @param slipNoList
	 * @param isAP true:AP false:AR
	 * @throws TException
	 */
	public void updateCmFundDtl(String kaiCode, List<String> slipNoList, boolean isAP) throws TException {
		Connection conn = DBUtil.getConnection();
		VCreator sql = null;
		try {
			// 共通部構築
			VCreator sqlIns = new VCreator();
			sqlIns.add(" INSERT INTO CM_FUND_DTL ( ");
			sqlIns.add("      KAI_CODE ");
			sqlIns.add("     ,DEN_DATE ");
			sqlIns.add("     ,DEN_NO ");
			sqlIns.add("     ,TRI_CODE ");
			sqlIns.add("     ,TRI_NAME ");
			sqlIns.add("     ,KNR_CODE ");
			sqlIns.add("     ,KNR_NAME ");
			sqlIns.add("     ,TEK ");
			sqlIns.add("     ,DEN_SYU_CODE ");
			sqlIns.add("     ,DEN_SYU_NAME ");
			sqlIns.add("     ,CUR_CODE ");
			sqlIns.add("     ,ZAN_KIN ");
			sqlIns.add("     ,ZAN_IN_KIN ");
			sqlIns.add("     ,CBK_CODE ");
			sqlIns.add("     ,DATA_KBN ");
			sqlIns.add("     ,SYS_KBN ");
			sqlIns.add("     ,DATA_TYPE ");
			sqlIns.add("     ,KEY_DEN_DATE ");
			sqlIns.add("     ,KEY_DEN_NO ");

			sqlIns.add("     ,KEY_GYO_NO ");
			sqlIns.add("     ,KEY_SEI_NO ");
			sqlIns.add("     ,KEY_DEP_CODE ");
			sqlIns.add("     ,KEY_TRI_CODE ");

			sqlIns.add("     ,INP_DATE ");
			sqlIns.add("     ,UPD_DATE ");
			sqlIns.add("     ,PRG_ID ");
			sqlIns.add("     ,USR_ID ");
			sqlIns.add(" ) VALUES ( ");

			String curCode = null;
			BigDecimal inKin = BigDecimal.ZERO;
			BigDecimal kin = BigDecimal.ZERO;
			String keyCurCode = getCompany().getAccountConfig().getKeyCurrency().getCode();
			RateManager rateMn = get(RateManager.class);

			if (isAP) {
				// 債務残高生成
				sql = new VCreator();
				sql.add(" SELECT ");
				sql.add("  zan.KAI_CODE, ");
				sql.add("  zan.ZAN_SIHA_DATE, ");
				sql.add("  zan.ZAN_DEN_DATE, ");
				sql.add("  zan.ZAN_DEN_NO, ");
				sql.add("  zan.ZAN_DEP_CODE, ");
				sql.add("  dep.DEP_NAME, ");
				sql.add("  zan.ZAN_TEK, ");
				sql.add("  zan.ZAN_SIHA_CODE, ");
				sql.add("  tri.TRI_NAME, ");
				sql.add("  zan.ZAN_KIN, ");
				sql.add("  zan.ZAN_IN_SIHA_KIN, ");
				sql.add("  zan.ZAN_CUR_CODE, ");
				sql.add("  cbk.CUR_CODE, ");
				sql.add("  cur.DEC_KETA, ");
				sql.add("  cur.RATE_POW, ");
				sql.add("  zan.ZAN_SYS_KBN, ");
				sql.add("  zan.ZAN_DEN_SYU, ");
				sql.add("  syu.DEN_SYU_NAME, ");
				sql.add("  zan.ZAN_FURI_CBK_CODE, ");

				sql.add("  zan.ZAN_INP_DATE, ");
				sql.add("  zan.UPD_DATE, ");
				sql.add("  zan.PRG_ID, ");
				sql.add("  zan.USR_ID ");

				sql.add(" FROM AP_ZAN zan ");

				sql.add(" LEFT OUTER JOIN TRI_MST tri ");
				sql.add(" ON  zan.KAI_CODE      = tri.KAI_CODE ");
				sql.add(" AND zan.ZAN_SIHA_CODE = tri.TRI_CODE ");

				sql.add(" LEFT OUTER JOIN BMN_MST dep ");
				sql.add(" ON  zan.KAI_CODE     = dep.KAI_CODE ");
				sql.add(" AND zan.ZAN_DEP_CODE = dep.DEP_CODE ");

				sql.add(" LEFT OUTER JOIN DEN_SYU_MST syu ");
				sql.add(" ON  zan.KAI_CODE    = syu.KAI_CODE ");
				sql.add(" AND zan.ZAN_DEN_SYU = syu.DEN_SYU_CODE ");

				sql.add(" INNER JOIN AP_CBK_MST cbk ");
				sql.add(" ON  zan.KAI_CODE          = cbk.KAI_CODE ");
				sql.add(" AND zan.ZAN_FURI_CBK_CODE = cbk.CBK_CBK_CODE ");

				sql.add(" INNER JOIN CUR_MST cur ");
				sql.add(" ON  cbk.KAI_CODE = cur.KAI_CODE ");
				sql.add(" AND cbk.CUR_CODE = cur.CUR_CODE ");

				sql.add(" WHERE 1 = 1 ");
				if (!Util.isNullOrEmpty(kaiCode)) {
					sql.add(" AND zan.KAI_CODE = ?", kaiCode);
				}
				sql.add(" AND   zan.ZAN_DEN_NO IN ");
				sql.addInStatement(slipNoList);
				sql.add(" AND zan.ZAN_SIHA_DEN_NO IS NULL ");
				sql.add(" AND zan.ZAN_FURI_CBK_CODE IS NOT NULL ");

				sql.add(" ORDER BY zan.KAI_CODE , zan.ZAN_SIHA_DATE , zan.ZAN_DEN_NO ");

				Statement statement = DBUtil.getStatement(conn);
				ResultSet rs = DBUtil.select(statement, sql);

				while (rs.next()) {
					// 銀行口座通貨
					curCode = rs.getString("CUR_CODE");
					// 金額セット
					inKin = rs.getBigDecimal("ZAN_IN_SIHA_KIN");
					kin = rs.getBigDecimal("ZAN_KIN");

					if (Util.equals(curCode, rs.getString("ZAN_CUR_CODE"))) {
						// 口座通貨と取引通貨が同一 はそのまま

					} else if (Util.equals(curCode, keyCurCode)) {
						// 取引通貨と基軸通貨が同一
						inKin = kin;
					} else {
						// 以外は計算より求める
						BigDecimal rate = rateMn.getRate(curCode, rs.getDate("ZAN_SIHA_DATE"));
						inKin = convertToForeign(kin, rate, rs.getInt("RATE_POW"), rs.getInt("DEC_KETA"), getCompany());
					}

					// 登録共通
					sql = new VCreator();
					sql.add(sqlIns);
					sql.add("     ? ", rs.getString("KAI_CODE"));
					sql.add("    ,? ", rs.getDate("ZAN_SIHA_DATE"));
					sql.add("    ,? ", rs.getString("ZAN_DEN_NO"));
					sql.add("    ,? ", rs.getString("ZAN_SIHA_CODE"));
					sql.add("    ,? ", rs.getString("TRI_NAME"));
					sql.add("    ,? ", rs.getString("ZAN_DEP_CODE"));
					sql.add("    ,? ", rs.getString("DEP_NAME"));
					sql.add("    ,? ", rs.getString("ZAN_TEK"));
					sql.add("    ,? ", rs.getString("ZAN_DEN_SYU"));
					sql.add("    ,? ", rs.getString("DEN_SYU_NAME"));
					sql.add("    ,? ", curCode);
					sql.add("    ,? ", kin.negate()); // 符号反転
					sql.add("    ,? ", inKin.negate()); // 符号反転
					sql.add("    ,? ", rs.getString("ZAN_FURI_CBK_CODE"));
					sql.add("    ,0 "); // DATA_KBN
					sql.add("    ,? ", rs.getString("ZAN_SYS_KBN"));
					sql.add("    ,2 "); // DATA_TYPE
					sql.add("    ,? ", rs.getDate("ZAN_DEN_DATE"));
					sql.add("    ,? ", rs.getString("ZAN_DEN_NO"));
					sql.add("    ,NULL ");
					sql.add("    ,NULL ");
					sql.add("    ,NULL ");
					sql.add("    ,NULL ");
					sql.adt("    ,? ", rs.getTimestamp("ZAN_INP_DATE"));
					sql.adt("    ,? ", rs.getTimestamp("UPD_DATE"));
					sql.add("    ,? ", rs.getString("PRG_ID"));
					sql.add("    ,? ", rs.getString("USR_ID"));
					sql.add(" ) ");

					DBUtil.execute(sql);
				}

				DBUtil.close(rs);
				DBUtil.close(statement);
			} else {
				// 債権残高生成
				sql = new VCreator();
				sql.add(" SELECT ");
				sql.add("  zan.KAI_CODE, ");
				sql.add("  zan.ZAN_AR_DATE, ");
				sql.add("  zan.ZAN_SEI_DEN_DATE, ");
				sql.add("  zan.ZAN_SEI_DEN_NO, ");
				sql.add("  zan.ZAN_DEP_CODE, ");
				sql.add("  dep.DEP_NAME, ");
				sql.add("  swk.SWK_TEK, ");
				sql.add("  zan.ZAN_TRI_CODE, ");
				sql.add("  tri.TRI_NAME, ");
				sql.add("  SUM(NVL(zan.ZAN_SEI_KIN,0)) ZAN_SEI_KIN, ");
				sql.add("  SUM(NVL(zan.ZAN_SEI_IN_KIN,0)) ZAN_SEI_IN_KIN, ");
				sql.add("  zan.ZAN_CUR_CODE, ");
				sql.add("  cbk.CUR_CODE, ");
				sql.add("  cur.DEC_KETA, ");
				sql.add("  cur.RATE_POW, ");
				sql.add("  swk.SWK_SYS_KBN ZAN_SYS_KBN, ");
				sql.add("  swk.SWK_DEN_SYU, ");
				sql.add("  syu.DEN_SYU_NAME, ");
				sql.add("  org.ZAN_CBK_CODE, ");
				sql.add("  zan.ZAN_SEI_NO,");
				sql.add("  zan.ZAN_SEI_GYO_NO, ");
				sql.add("  org.INP_DATE, ");
				sql.add("  org.UPD_DATE, ");
				sql.add("  org.PRG_ID, ");
				sql.add("  org.USR_ID ");

				sql.add(" FROM ( ");
				sql.add("     SELECT ");
				sql.add("         sei.KAI_CODE,");
				sql.add("         sei.ZAN_AR_DATE,");
				sql.add("         sei.ZAN_SEI_DEN_NO,");
				sql.add("         sei.ZAN_TRI_CODE,");
				sql.add("         sei.ZAN_SEI_DEN_DATE,");
				sql.add("         sei.ZAN_CUR_CODE, ");
				sql.add("         sei.ZAN_DEP_CODE,");
				sql.add("         sei.ZAN_SEI_NO,");
				sql.add("         sei.ZAN_SEI_GYO_NO, ");
				sql.add("         SUM(NVL(sei.ZAN_SEI_KIN,0)) ZAN_SEI_KIN,");
				sql.add("         SUM(NVL(sei.ZAN_SEI_IN_KIN,0)) ZAN_SEI_IN_KIN ");
				sql.add("     FROM AR_ZAN sei ");
				sql.add(" WHERE 1 = 1 ");
				if (!Util.isNullOrEmpty(kaiCode)) {
					sql.add(" AND sei.KAI_CODE = ?", kaiCode);
				}
				sql.add("     AND   sei.ZAN_SEI_DEN_NO IN ");
				sql.addInStatement(slipNoList);
				sql.add("     GROUP BY ");
				sql.add("         sei.KAI_CODE,");
				sql.add("         sei.ZAN_AR_DATE,");
				sql.add("         sei.ZAN_SEI_DEN_NO,");
				sql.add("         sei.ZAN_TRI_CODE,");
				sql.add("         sei.ZAN_SEI_DEN_DATE,");
				sql.add("         sei.ZAN_CUR_CODE, ");
				sql.add("         sei.ZAN_DEP_CODE,");
				sql.add("         sei.ZAN_SEI_NO,");
				sql.add("         sei.ZAN_SEI_GYO_NO ");
				sql.add("     UNION ALL ");
				sql.add("     SELECT ");
				sql.add("         sei.KAI_CODE,");
				sql.add("         sei.ZAN_AR_DATE,");
				sql.add("         sei.ZAN_SEI_DEN_NO,");
				sql.add("         sei.ZAN_TRI_CODE,");
				sql.add("         sei.ZAN_SEI_DEN_DATE,");
				sql.add("         sei.ZAN_CUR_CODE, ");
				sql.add("         sei.ZAN_DEP_CODE,");
				sql.add("         sei.ZAN_SEI_NO,");
				sql.add("         sei.ZAN_SEI_GYO_NO, ");
				sql.add("         SUM(NVL(sei.ZAN_KESI_KIN,0)) * -1 ZAN_SEI_KIN,");
				sql.add("         SUM(NVL(sei.ZAN_KESI_IN_KIN,0))  * -1 ZAN_SEI_IN_KIN ");
				sql.add("     FROM AR_ZAN sei ");
				sql.add(" WHERE 1 = 1 ");
				if (!Util.isNullOrEmpty(kaiCode)) {
					sql.add(" AND sei.KAI_CODE = ?", kaiCode);
				}
				sql.add("     AND   sei.ZAN_SEI_DEN_NO IN ");
				sql.addInStatement(slipNoList);
				sql.add("     GROUP BY ");
				sql.add("         sei.KAI_CODE,");
				sql.add("         sei.ZAN_AR_DATE,");
				sql.add("         sei.ZAN_SEI_DEN_NO,");
				sql.add("         sei.ZAN_TRI_CODE,");
				sql.add("         sei.ZAN_SEI_DEN_DATE,");
				sql.add("         sei.ZAN_CUR_CODE, ");
				sql.add("         sei.ZAN_DEP_CODE,");
				sql.add("         sei.ZAN_SEI_NO,");
				sql.add("         sei.ZAN_SEI_GYO_NO ");
				sql.add(" ) zan ");

				sql.add(" INNER JOIN AR_ZAN org ");
				sql.add(" ON  zan.KAI_CODE = org.KAI_CODE ");
				sql.add(" AND zan.ZAN_DEP_CODE = org.ZAN_DEP_CODE ");
				sql.add(" AND zan.ZAN_TRI_CODE = org.ZAN_TRI_CODE ");
				sql.add(" AND NVL(zan.ZAN_SEI_NO, ' ') = NVL(org.ZAN_SEI_NO, ' ') ");
				sql.add(" AND zan.ZAN_SEI_DEN_DATE = org.ZAN_SEI_DEN_DATE ");
				sql.add(" AND zan.ZAN_SEI_DEN_NO = org.ZAN_SEI_DEN_NO  ");
				sql.add(" AND zan.ZAN_SEI_GYO_NO = org.ZAN_SEI_GYO_NO ");
				sql.add(" AND org.ZAN_DATA_KBN = '31' ");
				sql.add(" AND org.ZAN_KESI_DEN_NO IS NULL ");

				sql.add(" INNER JOIN AR_SWK_HDR swk ");
				sql.add(" ON  zan.KAI_CODE         = swk.KAI_CODE ");
				sql.add(" AND zan.ZAN_SEI_DEN_DATE = swk.SWK_DEN_DATE ");
				sql.add(" AND zan.ZAN_SEI_DEN_NO   = swk.SWK_DEN_NO ");

				sql.add(" LEFT OUTER JOIN TRI_MST tri ");
				sql.add(" ON  zan.KAI_CODE     = tri.KAI_CODE ");
				sql.add(" AND zan.ZAN_TRI_CODE = tri.TRI_CODE ");

				sql.add(" LEFT OUTER JOIN BMN_MST dep ");
				sql.add(" ON  zan.KAI_CODE     = dep.KAI_CODE ");
				sql.add(" AND zan.ZAN_DEP_CODE = dep.DEP_CODE ");

				sql.add(" LEFT OUTER JOIN DEN_SYU_MST syu ");
				sql.add(" ON  swk.KAI_CODE    = syu.KAI_CODE ");
				sql.add(" AND swk.SWK_DEN_SYU = syu.DEN_SYU_CODE ");

				sql.add(" INNER JOIN AP_CBK_MST cbk ");
				sql.add(" ON  org.KAI_CODE     = cbk.KAI_CODE ");
				sql.add(" AND org.ZAN_CBK_CODE = cbk.CBK_CBK_CODE ");

				sql.add(" INNER JOIN CUR_MST cur ");
				sql.add(" ON  cbk.KAI_CODE = cur.KAI_CODE ");
				sql.add(" AND cbk.CUR_CODE = cur.CUR_CODE ");

				sql.add(" GROUP BY ");
				sql.add("  zan.KAI_CODE, ");
				sql.add("  zan.ZAN_AR_DATE, ");
				sql.add("  zan.ZAN_SEI_DEN_DATE, ");
				sql.add("  zan.ZAN_SEI_DEN_NO, ");
				sql.add("  zan.ZAN_DEP_CODE, ");
				sql.add("  dep.DEP_NAME, ");
				sql.add("  swk.SWK_TEK, ");
				sql.add("  zan.ZAN_TRI_CODE, ");
				sql.add("  tri.TRI_NAME, ");
				sql.add("  zan.ZAN_CUR_CODE, ");
				sql.add("  cbk.CUR_CODE, ");
				sql.add("  cur.DEC_KETA, ");
				sql.add("  cur.RATE_POW, ");
				sql.add("  swk.SWK_SYS_KBN, ");
				sql.add("  swk.SWK_DEN_SYU, ");
				sql.add("  syu.DEN_SYU_NAME, ");
				sql.add("  org.ZAN_CBK_CODE, ");
				sql.add("  zan.ZAN_SEI_NO,");
				sql.add("  zan.ZAN_SEI_GYO_NO, ");
				sql.add("  org.INP_DATE, ");
				sql.add("  org.UPD_DATE, ");
				sql.add("  org.PRG_ID, ");
				sql.add("  org.USR_ID ");
				sql.add("  HAVING SUM(NVL(zan.ZAN_SEI_KIN,0)) <> 0 OR SUM(NVL(zan.ZAN_SEI_IN_KIN,0)) <> 0 ");

				sql.add(" ORDER BY zan.KAI_CODE , zan.ZAN_AR_DATE , zan.ZAN_SEI_DEN_NO ");

				Statement statement = DBUtil.getStatement(conn);
				ResultSet rs = DBUtil.select(statement, sql);

				while (rs.next()) {
					// 銀行口座通貨
					curCode = rs.getString("CUR_CODE");
					// 金額セット
					inKin = rs.getBigDecimal("ZAN_SEI_IN_KIN");
					kin = rs.getBigDecimal("ZAN_SEI_KIN");

					if (Util.equals(curCode, rs.getString("ZAN_CUR_CODE"))) {
						// 口座通貨と取引通貨が同一 はそのまま

					} else if (Util.equals(curCode, keyCurCode)) {
						// 取引通貨と基軸通貨が同一
						inKin = kin;
					} else {
						// 以外は計算より求める
						BigDecimal rate = rateMn.getRate(curCode, rs.getDate("ZAN_AR_DATE"));
						inKin = convertToForeign(kin, rate, rs.getInt("RATE_POW"), rs.getInt("DEC_KETA"), getCompany());
					}

					// 登録共通
					sql = new VCreator();
					sql.add(sqlIns);
					sql.add("     ? ", rs.getString("KAI_CODE"));
					sql.add("    ,? ", rs.getDate("ZAN_AR_DATE"));
					sql.add("    ,? ", rs.getString("ZAN_SEI_DEN_NO"));
					sql.add("    ,? ", rs.getString("ZAN_TRI_CODE"));
					sql.add("    ,? ", rs.getString("TRI_NAME"));
					sql.add("    ,? ", rs.getString("ZAN_DEP_CODE"));
					sql.add("    ,? ", rs.getString("DEP_NAME"));
					sql.add("    ,? ", rs.getString("SWK_TEK"));
					sql.add("    ,? ", rs.getString("SWK_DEN_SYU"));
					sql.add("    ,? ", rs.getString("DEN_SYU_NAME"));
					sql.add("    ,? ", curCode);
					sql.add("    ,? ", kin); // 符号そのまま
					sql.add("    ,? ", inKin); // 符号そのまま
					sql.add("    ,? ", rs.getString("ZAN_CBK_CODE"));
					sql.add("    ,0 "); // DATA_KBN
					sql.add("    ,? ", rs.getString("ZAN_SYS_KBN"));
					sql.add("    ,2 "); // DATA_TYPE
					sql.add("    ,? ", rs.getDate("ZAN_SEI_DEN_DATE"));
					sql.add("    ,? ", rs.getString("ZAN_SEI_DEN_NO"));
					sql.add("    ,? ", rs.getInt("ZAN_SEI_GYO_NO"));
					sql.add("    ,? ", rs.getString("ZAN_SEI_NO"));
					sql.add("    ,? ", rs.getString("ZAN_DEP_CODE"));
					sql.add("    ,? ", rs.getString("ZAN_TRI_CODE"));
					sql.adt("    ,? ", rs.getTimestamp("INP_DATE"));
					sql.adt("    ,? ", rs.getTimestamp("UPD_DATE"));
					sql.add("    ,? ", rs.getString("PRG_ID"));
					sql.add("    ,? ", rs.getString("USR_ID"));
					sql.add(" ) ");

					DBUtil.execute(sql);
				}

				DBUtil.close(rs);
				DBUtil.close(statement);

			}

		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 基軸通貨→入力金額
	 * 
	 * @param keyAmount 基軸通貨金額
	 * @param rate レート
	 * @param ratePow 外貨通貨レート係数
	 * @param decimalPoints 外貨通貨小数点以下桁数
	 * @param company
	 * @return 入力金額
	 */
	public BigDecimal convertToForeign(BigDecimal keyAmount, BigDecimal rate, int ratePow, int decimalPoints,
		Company company) {

		if (rate == null) {
			return null;
		}

		if (keyAmount == null) {
			return null;
		}

		if (DecimalUtil.isNullOrZero(rate)) {
			return BigDecimal.ZERO;
		}

		if (DecimalUtil.isNullOrZero(keyAmount)) {
			return BigDecimal.ZERO;
		}

		AccountConfig conf = company.getAccountConfig();
		ExchangeFraction frac = conf.getExchangeFraction();

		// 換算
		TExchangeAmount param = TCalculatorFactory.createExchangeAmount();
		param.setExchangeFraction(frac);
		param.setConvertType(conf.getConvertType());
		param.setDigit(decimalPoints);
		param.setKeyAmount(keyAmount);
		param.setRate(rate);
		param.setRatePow(ratePow);

		return calculator.exchangeForeignAmount(param);
	}

	/**
	 * SQL用
	 */
	protected class VCreator extends SQLCreator {

		/**
		 * コンストラクター
		 */
		public VCreator() {
			crlf = " ";
		}
	}
}