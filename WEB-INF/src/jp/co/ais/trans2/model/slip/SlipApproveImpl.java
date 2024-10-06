package jp.co.ais.trans2.model.slip;

import java.sql.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.employee.*;
import jp.co.ais.trans2.model.history.*;
import jp.co.ais.trans2.model.history.ApproveHistory.SYO_FLG;

/**
 * 伝票承認の実装クラス
 * 
 * @author AIS
 */
public abstract class SlipApproveImpl extends TModel implements SlipApprove {

	/** true:承認履歴機能有効 */
	public static boolean isUseApproveHistory = ServerConfig.isFlagOn("trans.slip.use.approve.history");

	public void approveSlip(SlipDen dtl, Employee employee) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// Entity更新
			dtl.setSWK_BEFORE_UPD_KBN(dtl.getSWK_UPD_KBN());
			dtl.setSWK_UPD_KBN(SlipState.APPROVE.value);
			dtl.setSWK_SYO_EMP_CODE(employee.getCode());
			dtl.setSWK_SYO_EMP_NAME(employee.getName());
			dtl.setSWK_SYO_EMP_NAME_S(employee.getNames());
			dtl.setSWK_SYO_DATE(getNow());

			// ヘッダー更新
			updateHeader(conn, dtl);
			// 明細更新
			updateDetail(conn, dtl);
	
			if (isUseApproveHistory) {
				// 履歴登録
				ApproveHistoryManager appr = (ApproveHistoryManager) getComponent(ApproveHistoryManager.class);
				appr.entry(appr.getApproveHistory(dtl, employee, SYO_FLG.APPROVE));
			}

		} catch (TException ex) {
			throw ex;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void denySlip(SlipDen dtl) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// Entity更新
			dtl.setSWK_BEFORE_UPD_KBN(dtl.getSWK_UPD_KBN());
			dtl.setSWK_UPD_KBN(SlipState.DENY.value);
			dtl.setSWK_SYO_EMP_CODE(null);
			dtl.setSWK_SYO_EMP_NAME(null);
			dtl.setSWK_SYO_EMP_NAME_S(null);
			dtl.setSWK_SYO_DATE(null);

			// ヘッダー更新
			updateHeader(conn, dtl);
			// 明細更新
			updateDetail(conn, dtl);
	
			if (isUseApproveHistory) {
				// 履歴登録
				ApproveHistoryManager appr = (ApproveHistoryManager) getComponent(ApproveHistoryManager.class);
				appr.entry(appr.getApproveHistory(dtl, getUser().getEmployee(), SYO_FLG.DENY));
			}

		} catch (TException ex) {
			throw ex;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void cancelApproveSlip(SlipDen dtl, boolean isUseFieldApprove) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			// Entity更新
			dtl.setSWK_BEFORE_UPD_KBN(dtl.getSWK_UPD_KBN());
	
			// 現場承認を使用する場合は現場承認に戻す
			if (isUseFieldApprove) {
	
				dtl.setSWK_UPD_KBN(SlipState.FIELD_APPROVE.value);
	
			// 現場承認を使用しない場合は登録に戻す
			} else {
	
				dtl.setSWK_UPD_KBN(SlipState.ENTRY.value);
				dtl.setSWK_SYO_EMP_CODE(null);
				dtl.setSWK_SYO_EMP_NAME(null);
				dtl.setSWK_SYO_EMP_NAME_S(null);
				dtl.setSWK_SYO_DATE(null);

			}
	
			// ヘッダー更新
			updateHeader(conn, dtl);
			// 明細更新
			updateDetail(conn, dtl);
	
			if (isUseApproveHistory) {
				// 履歴登録
				ApproveHistoryManager appr = (ApproveHistoryManager) getComponent(ApproveHistoryManager.class);
				appr.entry(appr.getApproveHistory(dtl, getUser().getEmployee(), SYO_FLG.CANCEL));
			}

		} catch (TException ex) {
			throw ex;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	public void approveSlipForFieldState(SlipDen dtl, Employee employee) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// Entity更新
			dtl.setSWK_BEFORE_UPD_KBN(dtl.getSWK_UPD_KBN());
			dtl.setSWK_UPD_KBN(SlipState.FIELD_APPROVE.value);
			dtl.setSWK_SYO_EMP_CODE(employee.getCode());
			dtl.setSWK_SYO_EMP_NAME(employee.getName());
			dtl.setSWK_SYO_EMP_NAME_S(employee.getNames());
			dtl.setSWK_SYO_DATE(getNow());

			// ヘッダー更新
			updateHeader(conn, dtl);
			// 明細更新
			updateDetail(conn, dtl);
	
			if (isUseApproveHistory) {
				// 履歴登録
				ApproveHistoryManager appr = (ApproveHistoryManager) getComponent(ApproveHistoryManager.class);
				appr.entry(appr.getApproveHistory(dtl, employee, SYO_FLG.APPROVE));
			}

		} catch (TException ex) {
			throw ex;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	public void denySlipForFieldState(SlipDen dtl) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// Entity更新
			dtl.setSWK_BEFORE_UPD_KBN(dtl.getSWK_UPD_KBN());
			dtl.setSWK_UPD_KBN(SlipState.FIELD_DENY.value);
			dtl.setSWK_SYO_EMP_CODE(null);
			dtl.setSWK_SYO_EMP_NAME(null);
			dtl.setSWK_SYO_EMP_NAME_S(null);
			dtl.setSWK_SYO_DATE(null);

			// ヘッダー更新
			updateHeader(conn, dtl);
			// 明細更新
			updateDetail(conn, dtl);
	
			if (isUseApproveHistory) {
				// 履歴登録
				ApproveHistoryManager appr = (ApproveHistoryManager) getComponent(ApproveHistoryManager.class);
				appr.entry(appr.getApproveHistory(dtl, getUser().getEmployee(), SYO_FLG.DENY));
			}

		} catch (TException ex) {
			throw ex;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	public void cancelApproveSlipForFieldState(SlipDen dtl, boolean isUseFieldApprove) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			// Entity更新
			dtl.setSWK_BEFORE_UPD_KBN(dtl.getSWK_UPD_KBN());
			dtl.setSWK_UPD_KBN(SlipState.ENTRY.value);
			dtl.setSWK_SYO_EMP_CODE(null);
			dtl.setSWK_SYO_EMP_NAME(null);
			dtl.setSWK_SYO_EMP_NAME_S(null);
			dtl.setSWK_SYO_DATE(null);

			// ヘッダー更新
			updateHeader(conn, dtl);
			// 明細更新
			updateDetail(conn, dtl);
	
			if (isUseApproveHistory) {
				// 履歴登録
				ApproveHistoryManager appr = (ApproveHistoryManager) getComponent(ApproveHistoryManager.class);
				appr.entry(appr.getApproveHistory(dtl, getUser().getEmployee(), SYO_FLG.CANCEL));
			}

		} catch (TException ex) {
			throw ex;
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * ヘッダーの更新
	 * 
	 * @param conn
	 * @param dtl
	 * @throws TException
	 */
	public abstract void updateHeader(Connection conn, SlipDen dtl) throws TException;

	/**
	 * 明細の更新
	 * 
	 * @param conn
	 * @param dtl
	 * @throws TException
	 */
	public void updateDetail(Connection conn, SlipDen dtl) throws TException {

		String sql = " UPDATE SWK_DTL " + //
			" SET " + //
			" SWK_UPD_KBN = " + Integer.toString(dtl.getSWK_UPD_KBN()) + ", " + //
			" UPD_DATE = " + SQLUtil.getYMDHMSParam(getNow()) + ", " + //
			" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " + //
			" USR_ID = " + SQLUtil.getParam(getUserCode()) + //
			" WHERE KAI_CODE = " + SQLUtil.getParam(dtl.getKAI_CODE());

		if (Util.isNullOrEmpty(dtl.getSWK_BEFORE_DEN_NO())) {
			sql += " AND	SWK_DEN_NO = " + SQLUtil.getParam(dtl.getSWK_DEN_NO());

			// 振戻伝票があれば一緒に承認する
		} else {
			sql += " AND	SWK_DEN_NO IN (" + //
				SQLUtil.getParam(dtl.getSWK_DEN_NO()) + ", " + //
				SQLUtil.getParam(dtl.getSWK_BEFORE_DEN_NO()) + ") ";
		}

		sql += " AND	SWK_UPD_KBN IN (1, 2, 3, 11, 12) ";

		DBUtil.execute(conn, sql);
	}

}
