package jp.co.ais.trans2.common.db;

import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.department.*;

/**
 * Transの業務SQLユーティリティ
 * 
 * @author AIS
 */
public class TransSQLUtil {

	/**
	 * 更新区分のIN句を返す
	 * 
	 * @param isEntry 登録を含むか
	 * @param isApprove 承認を含むか
	 * @param isSettle 確定を含むか
	 * @return 更新区分のIN句
	 */
	public static String getSlipStateInStatement(boolean isEntry, boolean isApprove, boolean isSettle) {

		List<Integer> list = new ArrayList<Integer>();

		if (isEntry) {
			list.add(SlipState.ENTRY.value);
			list.add(SlipState.FIELD_DENY.value);
			list.add(SlipState.DENY.value);
		}

		if (isApprove) {
			list.add(SlipState.FIELD_APPROVE.value);
			list.add(SlipState.APPROVE.value);
		}

		if (isSettle) {
			list.add(SlipState.UPDATE.value);
		}

		String sql = SQLUtil.getIntInStatement(list);

		return sql;

	}

	/**
	 * 部門出力条件に対する条件文を返す。
	 * 
	 * @param depc 部門出力条件
	 * @param tableName 部門階層マスタのテーブル名
	 * @return 部門出力条件に対する条件文
	 */
	public static String getDepartmentOutputConditionStatement(DepartmentOutputCondition depc, String tableName) {
		return getOutputConditionStatement(depc, tableName);
	}

	/**
	 * 部門出力条件に対する条件文を返す。部門階層マスタのテーブル名は短縮でdpkとする。
	 * 
	 * @param depc 部門出力条件
	 * @return 部門出力条件に対する条件文
	 */
	public static String getDepartmentOutputConditionStatement(DepartmentOutputCondition depc) {
		return getDepartmentOutputConditionStatement(depc, "dpk");
	}

	/**
	 * 会社出力条件に対する条件文を返す。
	 * 
	 * @param depc 会社出力条件
	 * @param tableName 会社階層マスタのテーブル名
	 * @return 会社出力条件に対する条件文
	 */
	public static String getCompanyOutputConditionStatement(CompanyOutputCondition depc, String tableName) {
		return getOutputConditionStatement(depc, tableName);
	}

	/**
	 * 会社出力条件に対する条件文を返す。会社階層マスタのテーブル名は短縮でdpkとする。
	 * 
	 * @param depc 会社出力条件
	 * @return 会社出力条件に対する条件文
	 */
	public static String getCompanyOutputConditionStatement(CompanyOutputCondition depc) {
		return getCompanyOutputConditionStatement(depc, "dpk");
	}

	/**
	 * 出力条件に対する条件文を返す。階層マスタのテーブル名は短縮でdpkとする。
	 * 
	 * @param depc 出力条件
	 * @return 出力条件に対する条件文
	 */
	public static String getOutputConditionStatement(BasicOutputCondition depc) {
		return getOutputConditionStatement(depc, "dpk");
	}

	/**
	 * 出力条件に対する条件文を返す。
	 * 
	 * @param depc 出力条件
	 * @param tableName 階層マスタのテーブル名
	 * @return 出力条件に対する条件文
	 */
	public static String getOutputConditionStatement(BasicOutputCondition depc, String tableName) {

		String sql = " AND " + tableName + ".DPK_SSK = " + SQLUtil.getParam(depc.getOrganizationCode());

		// 配下を含むか
		if (depc.isIncludeUnder()) {
			sql = sql + " AND " + tableName + ".DPK_LVL >= " + Integer.toString(depc.getLevel());
		} else {
			sql = sql + " AND " + tableName + ".DPK_LVL = " + Integer.toString(depc.getLevel());
		}

		// 上位
		if (!Util.isNullOrEmpty(depc.getSuperior())) {
			sql = sql + " AND " + tableName + ".DPK_LVL_" + Integer.toString(depc.getSuperiorLevel()) + " = "
				+ SQLUtil.getParam(depc.getSuperior().getCode());
		}

		// 開始
		if (!Util.isNullOrEmpty(depc.getFrom())) {
			sql = sql + " AND " + tableName + ".DPK_LVL_" + Integer.toString(depc.getLevel()) + " >= "
				+ SQLUtil.getParam(depc.getFrom().getCode());
		}

		// 終了
		if (!Util.isNullOrEmpty(depc.getTo())) {
			sql = sql + " AND " + tableName + ".DPK_LVL_" + Integer.toString(depc.getLevel()) + " <= "
				+ SQLUtil.getParam(depc.getTo().getCode());
		}

		// 個別選択
		List<String> codeList = depc.getOptionalEntitiesCode();
		if (codeList != null && !codeList.isEmpty()) {
			sql = sql + " AND " + tableName + ".DPK_LVL_" + Integer.toString(depc.getLevel()) + " IN "
				+ SQLUtil.getInStatement(codeList);
		}

		return sql;

	}

}
