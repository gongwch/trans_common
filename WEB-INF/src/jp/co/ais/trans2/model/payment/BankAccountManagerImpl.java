package jp.co.ais.trans2.model.payment;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * 銀行口座マネージャの実装クラスです。
 * 
 * @author AIS
 */
public class BankAccountManagerImpl extends TModel implements BankAccountManager {

	/**
	 * 指定条件に該当する銀行口座情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する銀行口座情報
	 * @throws TException
	 */
	public List<BankAccount> get(BankAccountSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<BankAccount> list = new ArrayList<BankAccount>();

		try {

			VCreator sql = getSQL(condition);

			sql.add(" ORDER BY cbk.CBK_CBK_CODE ");

			// 銀行口座コード最小値取得区分(MySQL用)
			if (condition.isMinimumOnly() && DBUtil.isUseMySQL) {
				sql.add(" LIMIT 1 ");
			}

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;

	}

	/**
	 * 指定条件に該当する銀行口座情報を返す<br>
	 * 全会社出力：REF画面用
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する銀行口座情報
	 * @throws TException
	 */
	public List<BankAccount> getRef(BankAccountSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<BankAccount> list = new ArrayList<BankAccount>();

		try {

			VCreator sql = getSQL(condition);

			sql.add(" ORDER BY  ");
			sql.add("   cbk.CBK_CBK_CODE ");
			sql.add("  ,CASE WHEN cbk.KAI_CODE = ? THEN 0 ELSE 1 END ", getCompanyCode()); // 同じコードの場合にログイン会社優先
			sql.add("  ,cbk.KAI_CODE ");

			// 銀行口座コード最小値取得区分(MySQL用)
			if (condition.isMinimumOnly() && DBUtil.isUseMySQL) {
				sql.add(" LIMIT 1 ");
			}

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			String currentCode = null;

			while (rs.next()) {

				BankAccount bean = mapping(rs);

				if (Util.isNullOrEmpty(currentCode)) {
					// 初期化
					currentCode = bean.getCode();
				} else if (Util.equals(currentCode, bean.getCode())) {
					// コードが同じ場合に処理不要
					continue;
				}

				// コード退避
				currentCode = bean.getCode();

				// 戻り値リスト持つ
				list.add(bean);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return list;

	}

	/**
	 * @param condition
	 * @return SQL
	 */
	protected VCreator getSQL(BankAccountSearchCondition condition) {
		VCreator sql = new VCreator();

		sql.add("SELECT");
		sql.add("	cbk.KAI_CODE,");
		sql.add("	cbk.CBK_CBK_CODE,");
		sql.add("	cbk.CBK_NAME,");
		sql.add("	cbk.CUR_CODE,");
		sql.add("	cur.CUR_NAME,");
		sql.add("	cur.CUR_NAME_S,");
		sql.add("	cur.CUR_NAME_K,");
		sql.add("	cur.DEC_KETA,");
		sql.add("	cbk.CBK_BNK_CODE,");
		sql.add("	cbk.CBK_STN_CODE,");
		sql.add("	cbk.CBK_IRAI_EMP_CODE,");
		sql.add("	cbk.CBK_IRAI_NAME,");
		sql.add("   cbk.CBK_IRAI_NAME_J,");
		sql.add("	cbk.CBK_IRAI_NAME_E,");
		sql.add("	cbk.CBK_YKN_KBN,");
		sql.add("	cbk.CBK_YKN_NO,");
		sql.add("	cbk.CBK_OUT_FB_KBN,");
		sql.add("	cbk.CBK_EMP_FB_KBN,");
		sql.add("	cbk.CBK_DEP_CODE,");
		sql.add("	dep.DEP_NAME,"); // 部門名称
		sql.add("	dep.DEP_NAME_S,"); // 部門略称
		sql.add("	dep.DEP_NAME_K,"); // 部門検索名称
		sql.add("	cbk.CBK_KMK_CODE,");
		sql.add("	kmk.KMK_NAME,"); // 科目名称
		sql.add("	kmk.KMK_NAME_S,"); // 科目略称
		sql.add("	kmk.KMK_NAME_K,"); // 科目検索名称
		sql.add("	cbk.CBK_HKM_CODE,");
		sql.add("	hkm.HKM_NAME,"); // 補助科目名称
		sql.add("	hkm.HKM_NAME_S,"); // 補助科目略称
		sql.add("	hkm.HKM_NAME_K,"); // 補助科目検索名称
		sql.add("	cbk.CBK_UKM_CODE,");
		sql.add("	ukm.UKM_NAME,"); // 内訳科目名称
		sql.add("	ukm.UKM_NAME_S,"); // 内訳科目略称
		sql.add("	ukm.UKM_NAME_K,"); // 内訳科目検索名称
		sql.add("	cbk.IBAN_NO,"); // IBANコード
		sql.add("	cbk.BANK_IDF,"); // 銀行識別子
		sql.add("	cbk.ACCOUNT_IDF,"); // 口座識別子
		sql.add("	cbk.SWIFT_CODE,"); // SWIFTコード
		sql.add("	cbk.CBK_COU_CODE,"); // BANK COUNTRY
		sql.add("	cbk.BNK_ADR_1,"); // 住所1
		sql.add("	cbk.BNK_ADR_2,"); // 住所2
		sql.add("	cbk.STR_DATE,");
		sql.add("	cbk.END_DATE,");
		sql.add("	bnk.BNK_NAME_S,");
		sql.add("	bnk.BNK_KANA, ");
		sql.add("	bnk.BNK_NAME_K, ");
		sql.add("	bnk.BNK_STN_NAME_S, ");
		sql.add("	bnk.BNK_STN_KANA, ");
		sql.add("	bnk.BNK_STN_NAME_K, ");
		sql.add("	cbk.CBK_BANK_NAME_E,"); // 銀行名（英字）
		sql.add("	cbk.CBK_STN_NAME_E,"); // 支店名（英字）
		sql.add("	cbk.BNK_ADR_1_E,"); // 住所1（英字）
		sql.add("	cbk.BNK_ADR_2_E "); // 住所2（英字）
		sql.add(" FROM AP_CBK_MST cbk ");
		sql.add("LEFT OUTER JOIN BNK_MST bnk ON cbk.CBK_BNK_CODE = bnk.BNK_CODE ");
		sql.add("	                     AND    cbk.CBK_STN_CODE = bnk.BNK_STN_CODE ");
		sql.add("LEFT OUTER JOIN BMN_MST dep ON dep.KAI_CODE = cbk.KAI_CODE ");
		sql.add("	                     AND    dep.DEP_CODE = cbk.CBK_DEP_CODE");
		sql.add("LEFT OUTER JOIN KMK_MST kmk ON kmk.KAI_CODE = cbk.KAI_CODE ");
		sql.add("	                     AND    kmk.KMK_CODE = cbk.CBK_KMK_CODE");
		sql.add("LEFT OUTER JOIN HKM_MST hkm ON hkm.KAI_CODE = cbk.KAI_CODE ");
		sql.add("	                     AND    hkm.KMK_CODE = cbk.CBK_KMK_CODE");
		sql.add("	                     AND    hkm.HKM_CODE = cbk.CBK_HKM_CODE");
		sql.add("LEFT OUTER JOIN UKM_MST ukm ON ukm.KAI_CODE = cbk.KAI_CODE ");
		sql.add("	                     AND    ukm.KMK_CODE = cbk.CBK_KMK_CODE");
		sql.add("	                     AND    ukm.HKM_CODE = cbk.CBK_HKM_CODE");
		sql.add("	                     AND    ukm.UKM_CODE = cbk.CBK_UKM_CODE");
		sql.add("LEFT OUTER JOIN CUR_MST cur ON cur.KAI_CODE = cbk.KAI_CODE ");
		sql.add("	                     AND    cur.CUR_CODE = cbk.CUR_CODE");
		sql.add(" WHERE 1 = 1 ");

		// 会社コード
		if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
			sql.add("   AND cbk.KAI_CODE = ?", condition.getCompanyCode());
		}

		// コード
		if (!Util.isNullOrEmpty(condition.getCode())) {
			sql.add("   AND cbk.CBK_CBK_CODE = ?", condition.getCode());
		}

		// 開始コード
		if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
			sql.add("   AND cbk.CBK_CBK_CODE >= ?", condition.getCodeFrom());
		}

		// 終了コード
		if (!Util.isNullOrEmpty(condition.getCodeTo())) {
			sql.add("   AND cbk.CBK_CBK_CODE <= ?", condition.getCodeTo());
		}

		// コード前方一致
		if (!Util.isNullOrEmpty(condition.getCodeLike())) {
			sql.addLikeFront("   AND cbk.CBK_CBK_CODE ?", condition.getCodeLike());
		}

		// 銀行名（銀行名 + 支店名）あいまい
		if (!Util.isNullOrEmpty(condition.getNameLike())) {
			sql.addNLikeAmbi("   AND CONCAT( NVL(bnk.BNK_NAME_S,''), CONCAT(' ', NVL(bnk.BNK_STN_NAME_S,''))) ?",
				condition.getNameLike());
		}

		// 口座番号あいまい
		if (!Util.isNullOrEmpty(condition.getAccountNokLike())) {
			sql.addLikeAmbi("   AND cbk.CBK_YKN_NO ?", condition.getAccountNokLike());
		}

		// 口座番号
		if (!Util.isNullOrEmpty(condition.getAccountNo())) {
			sql.add("   AND cbk.CBK_YKN_NO = ?", condition.getAccountNo());
		}

		// 社員ＦＢ区分
		if (condition.isUseEmployeePayment()) {
			sql.add("   AND cbk.CBK_EMP_FB_KBN = 1 ");
		}

		// 社外ＦＢ区分
		if (condition.isUseExPayment()) {
			sql.add("   AND cbk.CBK_OUT_FB_KBN = 1 ");
		}

		// 有効期間
		if (!Util.isNullOrEmpty(condition.getValidTerm())) {
			sql.add("   AND cbk.STR_DATE <= ?", condition.getValidTerm());
			sql.add("   AND cbk.END_DATE >= ?", condition.getValidTerm());
		}

		// 銀行コード
		if (!Util.isNullOrEmpty(condition.getBankCode())) {
			sql.add("   AND cbk.CBK_BNK_CODE = ?", condition.getBankCode());
		}

		// 支店コード
		if (!Util.isNullOrEmpty(condition.getBranchCode())) {
			sql.add("   AND cbk.CBK_STN_CODE = ?", condition.getBranchCode());
		}

		// 預金種目
		if (condition.getDepositKind() != null) {
			sql.add("   AND cbk.CBK_YKN_KBN = ?", condition.getDepositKind().value);
		}

		// 口座番号(10桁0埋め)
		if (!Util.isNullOrEmpty(condition.getAccountNoFillZero())) {
			sql.add(
				"   AND SUBSTR(CONCAT('0000000000', NVL(cbk.CBK_YKN_NO,'')), LENGTH(NVL(cbk.CBK_YKN_NO,'')) + 1, 10) = ?",
				StringUtil.rightBX("0000000000" + condition.getAccountNoFillZero(), 10));
		}

		// 通貨コード
		if (!Util.isNullOrEmpty(condition.getCurrencyCode())) {
			sql.add("   AND cbk.CUR_CODE = ?", condition.getCurrencyCode());
		}

		// 銀行口座コード最小値取得区分(Oracle用)
		if (condition.isMinimumOnly() && !DBUtil.isUseMySQL) {
			sql.add("   AND ROWNUM = 1 ");
		}

		// 受付番号
		if (!Util.isNullOrEmpty(condition.getAcceptNo())) {
			sql.add(" AND EXISTS (");
			sql.add("   SELECT 1 FROM AP_DEN_DAT den");
			sql.add("   WHERE den.KAI_CODE = cbk.KAI_CODE ");
			sql.add("   AND   den.DEN_CBK_CODE = cbk.CBK_CBK_CODE ");
			sql.add("   AND   den.DEN_UTK_NO = ?) ", condition.getAcceptNo());
		}

		// 最終更新日時
		if (condition.getLastUpdateDate() != null) {
			sql.adt(" AND  (cbk.CBK_INP_DATE > ? ", condition.getLastUpdateDate());
			sql.adt("    OR cbk.UPD_DATE > ?)", condition.getLastUpdateDate());
		}

		// 計上部門
		if (!Util.isNullOrEmpty(condition.getDeptCode())) {
			sql.add("   AND   cbk.CBK_DEP_CODE = ? ", condition.getDeptCode());
		}

		// 銀行口座コード
		if (!condition.getCodeList().isEmpty()) {
			sql.add(" AND	cbk.CBK_CBK_CODE IN " + SQLUtil.getInStatement(condition.getCodeList()));
		}

		return sql;
	}

	/**
	 * 削除データが存在しているかどうか
	 * 
	 * @param condition
	 * @return true:削除データが存在している
	 * @throws TException
	 */
	public boolean hasDelete(BankAccountSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		boolean hasDelete = false;
		try {

			VCreator sql = new VCreator();
			sql.add("");
			sql.add(" SELECT COUNT(1) ");
			sql.add(" FROM   AP_CBK_MST cbk ");
			sql.add(" WHERE  cbk.KAI_CODE = ? ", condition.getCompanyCode()); // 会社コード

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (cbk.CBK_INP_DATE <= ? ", condition.getLastUpdateDate());
				sql.adt("    OR cbk.UPD_DATE <= ? ", condition.getLastUpdateDate());
				sql.add("    OR cbk.CBK_INP_DATE IS NULL AND cbk.UPD_DATE IS NULL) ");
			}

			// 削除あり＝現在持っている件数はDBの過去の件数と不一致
			hasDelete = DBUtil.selectOneInt(conn, sql.toSQL()) != condition.getNowCount();

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return hasDelete;
	}

	/**
	 * 指定条件に該当する銀行口座コードを返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する銀行口座コード
	 * @throws TException
	 */
	public BankAccount getBankAccount(BankAccountSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		BankAccount bankAccount = null;

		try {

			VCreator sql = new VCreator();

			sql.add("SELECT");
			sql.add("	cbk.KAI_CODE,");
			sql.add("	cbk.CBK_CBK_CODE,");
			sql.add("	cbk.CBK_NAME,");
			sql.add("	cbk.CUR_CODE,");
			sql.add("	cur.CUR_NAME,");
			sql.add("	cur.CUR_NAME_S,");
			sql.add("	cur.CUR_NAME_K,");
			sql.add("	cur.DEC_KETA,");
			sql.add("	cbk.CBK_BNK_CODE,");
			sql.add("	cbk.CBK_STN_CODE,");
			sql.add("	cbk.CBK_IRAI_EMP_CODE,");
			sql.add("	cbk.CBK_IRAI_NAME,");
			sql.add("   cbk.CBK_IRAI_NAME_J,");
			sql.add("	cbk.CBK_IRAI_NAME_E,");
			sql.add("	cbk.CBK_YKN_KBN,");
			sql.add("	cbk.CBK_YKN_NO,");
			sql.add("	cbk.CBK_OUT_FB_KBN,");
			sql.add("	cbk.CBK_EMP_FB_KBN,");
			sql.add("	cbk.CBK_DEP_CODE,");
			sql.add("	dep.DEP_NAME,"); // 部門名称
			sql.add("	dep.DEP_NAME_S,"); // 部門略称
			sql.add("	dep.DEP_NAME_K,"); // 部門検索名称
			sql.add("	cbk.CBK_KMK_CODE,");
			sql.add("	kmk.KMK_NAME,"); // 科目名称
			sql.add("	kmk.KMK_NAME_S,"); // 科目略称
			sql.add("	kmk.KMK_NAME_K,"); // 科目検索名称
			sql.add("	cbk.CBK_HKM_CODE,");
			sql.add("	hkm.HKM_NAME,"); // 補助科目名称
			sql.add("	hkm.HKM_NAME_S,"); // 補助科目略称
			sql.add("	hkm.HKM_NAME_K,"); // 補助科目検索名称
			sql.add("	cbk.CBK_UKM_CODE,");
			sql.add("	ukm.UKM_NAME,"); // 内訳科目名称
			sql.add("	ukm.UKM_NAME_S,"); // 内訳科目略称
			sql.add("	ukm.UKM_NAME_K,"); // 内訳科目検索名称
			sql.add("	cbk.IBAN_NO,"); // IBANコード
			sql.add("	cbk.BANK_IDF,"); // 銀行識別子
			sql.add("	cbk.ACCOUNT_IDF,"); // 口座識別子
			sql.add("	cbk.SWIFT_CODE,"); // SWIFTコード
			sql.add("	cbk.CBK_COU_CODE,"); // BANK COUNTRY
			sql.add("	cbk.BNK_ADR_1,"); // 住所1
			sql.add("	cbk.BNK_ADR_2,"); // 住所2
			sql.add("	cbk.STR_DATE,");
			sql.add("	cbk.END_DATE,");
			sql.add("	bnk.BNK_NAME_S,");
			sql.add("	bnk.BNK_KANA, ");
			sql.add("	bnk.BNK_NAME_K, ");
			sql.add("	bnk.BNK_STN_NAME_S, ");
			sql.add("	bnk.BNK_STN_KANA, ");
			sql.add("	bnk.BNK_STN_NAME_K, ");
			sql.add("	cbk.CBK_BANK_NAME_E,"); // 銀行名（英字）
			sql.add("	cbk.CBK_STN_NAME_E,"); // 支店名（英字）
			sql.add("	cbk.BNK_ADR_1_E,"); // 住所1（英字）
			sql.add("	cbk.BNK_ADR_2_E "); // 住所2（英字）
			sql.add(" FROM AP_CBK_MST cbk");

			sql.add("LEFT OUTER JOIN BNK_MST bnk ON cbk.CBK_BNK_CODE = bnk.BNK_CODE ");
			sql.add("	                     AND    cbk.CBK_STN_CODE = bnk.BNK_STN_CODE ");
			sql.add("LEFT OUTER JOIN BMN_MST dep ON dep.KAI_CODE = cbk.KAI_CODE ");
			sql.add("	                     AND    dep.DEP_CODE = cbk.CBK_DEP_CODE");
			sql.add("LEFT OUTER JOIN KMK_MST kmk ON kmk.KAI_CODE = cbk.KAI_CODE ");
			sql.add("	                     AND    kmk.KMK_CODE = cbk.CBK_KMK_CODE");
			sql.add("LEFT OUTER JOIN HKM_MST hkm ON hkm.KAI_CODE = cbk.KAI_CODE ");
			sql.add("	                     AND    hkm.KMK_CODE = cbk.CBK_KMK_CODE");
			sql.add("	                     AND    hkm.HKM_CODE = cbk.CBK_HKM_CODE");
			sql.add("LEFT OUTER JOIN UKM_MST ukm ON ukm.KAI_CODE = cbk.KAI_CODE ");
			sql.add("	                     AND    ukm.KMK_CODE = cbk.CBK_KMK_CODE");
			sql.add("	                     AND    ukm.HKM_CODE = cbk.CBK_HKM_CODE");
			sql.add("	                     AND    ukm.UKM_CODE = cbk.CBK_UKM_CODE");
			sql.add("LEFT OUTER JOIN CUR_MST cur ON cur.KAI_CODE = cbk.KAI_CODE ");
			sql.add("	                     AND    cur.CUR_CODE = cbk.CUR_CODE");

			sql.add(" WHERE 1 = 1 ");

			// 会社コード
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("   AND cbk.KAI_CODE = ?", condition.getCompanyCode());
			}

			// コード
			if (!Util.isNullOrEmpty(condition.getIBanCode())) {
				sql.add("AND (cbk.IBAN_NO = ? ", condition.getIBanCode());
				sql.add(" OR CONCAT(NVL(cbk.BANK_IDF,''), NVL(cbk.ACCOUNT_IDF,'')) = ?", condition.getIBanCode()
					.replaceAll("/", ""));
				sql.add("    ) ");
			}

			// 科目コード
			if (!Util.isNullOrEmpty(condition.getItemCode())) {
				sql.add("   AND cbk.CBK_KMK_CODE = ?", condition.getItemCode());
			}

			// 補助科目コード
			if (!Util.isNullOrEmpty(condition.getSubItemCode())) {
				sql.add("   AND cbk.CBK_HKM_CODE = ?", condition.getSubItemCode());
			}

			// 内訳科目コード
			if (!Util.isNullOrEmpty(condition.getDetailItemCode())) {
				sql.add("   AND cbk.CBK_UKM_CODE = ?", condition.getDetailItemCode());
			}

			// 預金番号
			if (!Util.isNullOrEmpty(condition.getAccountNo())) {
				sql.add("   AND cbk.CBK_YKN_NO = ?", condition.getAccountNo());
			}

			sql.add(" ORDER BY ");
			sql.add("	cbk.KAI_CODE,");
			sql.add("	cbk.CBK_CBK_CODE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				bankAccount = mapping(rs);
				break;
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return bankAccount;
	}

	/**
	 * 銀行口座を登録する。
	 * 
	 * @param bean 銀行口座
	 * @throws TException
	 */
	public void entry(BankAccount bean) throws TException {

		Connection conn = DBUtil.getConnection();
		try {
			VCreator s = new VCreator();
			s.add("INSERT INTO AP_CBK_MST (");
			s.add("	KAI_CODE,");
			s.add("	CBK_CBK_CODE,");
			s.add("	CBK_NAME,");
			s.add("	CUR_CODE,");
			s.add("	CBK_BNK_CODE,");
			s.add("	CBK_STN_CODE,");
			s.add("	CBK_IRAI_EMP_CODE,");
			s.add("	CBK_IRAI_NAME,");
			s.add("	CBK_IRAI_NAME_J,");
			s.add("	CBK_IRAI_NAME_E,");
			s.add("	CBK_YKN_KBN,");
			s.add("	CBK_YKN_NO,");
			s.add("	CBK_EMP_FB_KBN,");
			s.add("	CBK_OUT_FB_KBN,");
			s.add("	CBK_DEP_CODE,");
			s.add("	CBK_KMK_CODE,");
			s.add("	CBK_HKM_CODE,");
			s.add("	CBK_UKM_CODE,");
			s.add("	IBAN_NO,"); // IBANコード
			s.add("	BANK_IDF,"); // 銀行識別子
			s.add("	ACCOUNT_IDF,"); // 口座識別子
			s.add("	SWIFT_CODE,"); // SWIFTコード
			s.add("	CBK_COU_CODE,"); // BANK COUNTRY
			s.add("	BNK_ADR_1,"); // 住所1
			s.add("	BNK_ADR_2,"); // 住所2
			s.add("	CBK_BANK_NAME_E,"); // 銀行名（英字）
			s.add("	CBK_STN_NAME_E,"); // 支店名（英字）
			s.add("	BNK_ADR_1_E,"); // 住所1（英字）
			s.add("	BNK_ADR_2_E,"); // 住所2（英字）
			s.add("	STR_DATE,");
			s.add("	END_DATE,");
			s.add("	CBK_INP_DATE,");
			s.add("	PRG_ID,");
			s.add("	USR_ID");
			s.add(") VALUES (");
			s.add("	?,", bean.getCompanyCode());
			s.add("	?,", bean.getCode());
			s.add("	?,", bean.getName());
			s.add("	?,", bean.getCurrencyCode());
			s.add("	?,", bean.getBankCode());
			s.add("	?,", bean.getBranchCode());
			s.add("	?,", bean.getClientCode());
			s.add("	?,", bean.getClientName());
			s.add("	?,", bean.getClientNameJ());
			s.add("	?,", bean.getClientNameE());
			s.add("	?,", bean.getDepositKind().value);
			s.add("	?,", bean.getAccountNo());
			s.add("	?,", bean.isUseEmployeePayment() ? 1 : 0);
			s.add("	?,", bean.isUseExPayment() ? 1 : 0);
			s.add("	?,", bean.getDepartmentCode());
			s.add("	?,", bean.getItemCode());
			s.add("	?,", bean.getSubItemCode());
			s.add("	?,", bean.getDetailItemCode());
			s.add("	?,", bean.getIBan()); // IBANコード
			s.add("	?,", bean.getBankIndentify()); // 銀行識別子
			s.add("	?,", bean.getBankAccountIndentify()); // 口座識別子
			s.add("	?,", bean.getSwift()); // SWIFTコード
			s.add("	?,", bean.getBankCountry()); // BANK COUNTRY
			s.add("	?,", bean.getBnkAdr1()); // 住所1
			s.add("	?,", bean.getBnkAdr2()); // 住所2
			s.add("	?,", bean.getBankNameE()); // 銀行名（英字）
			s.add("	?,", bean.getBranchNameE()); // 支店名（英字）
			s.add("	?,", bean.getBnkAdr1E()); // 住所1
			s.add("	?,", bean.getBnkAdr2E()); // 住所2
			s.add("	?,", bean.getDateFrom());
			s.add("	?,", bean.getDateTo());
			s.adt("	?,", getNow());
			s.add("	?,", getProgramCode());
			s.add("	?", getUserCode());
			s.add(")");

			DBUtil.execute(conn, s);

			DBUtil.close(conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 銀行口座を修正する。
	 * 
	 * @param bean 銀行口座
	 * @throws TException
	 */
	public void modify(BankAccount bean) throws TException {

		Connection conn = DBUtil.getConnection();
		try {
			VCreator s = new VCreator();
			s.add("UPDATE AP_CBK_MST SET");
			s.add("	CBK_NAME = ?,", bean.getName());
			s.add("	CUR_CODE = ?,", bean.getCurrencyCode());
			s.add("	CBK_BNK_CODE = ?,", bean.getBankCode());
			s.add("	CBK_STN_CODE = ?,", bean.getBranchCode());
			s.add("	CBK_IRAI_EMP_CODE = ?,", bean.getClientCode());
			s.add("	CBK_IRAI_NAME = ?,", bean.getClientName());
			s.add("	CBK_IRAI_NAME_J = ?,", bean.getClientNameJ());
			s.add("	CBK_IRAI_NAME_E = ?,", bean.getClientNameE());
			s.add("	CBK_YKN_KBN = ?,", bean.getDepositKind().value);
			s.add("	CBK_YKN_NO = ?,", bean.getAccountNo());
			s.add("	CBK_EMP_FB_KBN = ?,", bean.isUseEmployeePayment() ? 1 : 0);
			s.add("	CBK_OUT_FB_KBN = ?,", bean.isUseExPayment() ? 1 : 0);
			s.add("	CBK_DEP_CODE = ?,", bean.getDepartmentCode());
			s.add("	CBK_KMK_CODE = ?,", bean.getItemCode());
			s.add("	CBK_HKM_CODE = ?,", bean.getSubItemCode());
			s.add("	CBK_UKM_CODE = ?,", bean.getDetailItemCode());
			s.add("	IBAN_NO = ? ,", bean.getIBan()); // IBANコード
			s.add("	BANK_IDF = ? ,", bean.getBankIndentify()); // 銀行識別子
			s.add("	ACCOUNT_IDF = ? ,", bean.getBankAccountIndentify()); // 口座識別子
			s.add("	SWIFT_CODE = ? ,", bean.getSwift()); // SWIFTコード
			s.add("	CBK_COU_CODE = ? ,", bean.getBankCountry()); // BANK COUNTRY
			s.add("	BNK_ADR_1 = ? ,", bean.getBnkAdr1()); // 住所1
			s.add("	BNK_ADR_2 = ? ,", bean.getBnkAdr2()); // 住所2
			s.add(" CBK_BANK_NAME_E = ? ,", bean.getBankNameE()); // 銀行名（英字）
			s.add("	CBK_STN_NAME_E = ? ,", bean.getBranchNameE()); // 支店名（英字）
			s.add("	BNK_ADR_1_E = ? ,", bean.getBnkAdr1E()); // 住所1（英字）
			s.add("	BNK_ADR_2_E = ? ,", bean.getBnkAdr2E()); // 住所2（英字）
			s.add("	STR_DATE = ?,", bean.getDateFrom());
			s.add("	END_DATE = ?,", bean.getDateTo());
			s.adt("	UPD_DATE = ?,", getNow());
			s.add("	PRG_ID = ?,", getProgramCode());
			s.add("	USR_ID = ?", getUserCode());

			s.add("WHERE KAI_CODE = ?", bean.getCompanyCode());
			s.add("AND CBK_CBK_CODE = ?", bean.getCode());

			DBUtil.execute(conn, s);

			DBUtil.close(conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 銀行口座を削除する。
	 * 
	 * @param bean 銀行口座
	 * @throws TException
	 */
	public void delete(BankAccount bean) throws TException {

		Connection conn = DBUtil.getConnection();
		try {
			VCreator s = new VCreator();
			s.add("DELETE FROM AP_CBK_MST");
			s.add("WHERE KAI_CODE = ?", bean.getCompanyCode());
			s.add("AND CBK_CBK_CODE = ?", bean.getCode());

			DBUtil.execute(conn, s);

			DBUtil.close(conn);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected BankAccount mapping(ResultSet rs) throws Exception {

		BankAccount bean = createBankAccount();

		// 会社コード
		bean.setCompanyCode(rs.getString("KAI_CODE"));

		// コード
		bean.setCode(rs.getString("CBK_CBK_CODE"));

		// 銀行口座名称
		bean.setName(rs.getString("CBK_NAME"));

		// 通貨コード
		bean.setCurrencyCode(rs.getString("CUR_CODE"));

		// 通貨情報
		Currency currency = createCurrency();
		currency.setCompanyCode(rs.getString("KAI_CODE"));
		currency.setCode(rs.getString("CUR_CODE"));
		currency.setName(rs.getString("CUR_NAME"));
		currency.setNames(rs.getString("CUR_NAME_S"));
		currency.setNamek(rs.getString("CUR_NAME_K"));
		currency.setDecimalPoint(rs.getInt("DEC_KETA"));
		bean.setCurrency(currency);

		// 銀行情報
		bean.setBankCode(rs.getString("CBK_BNK_CODE")); // 銀行コード
		bean.setBankName(Util.avoidNull(rs.getString("BNK_NAME_S"))); // 銀行名称
		bean.setBankKana(Util.avoidNull(rs.getString("BNK_KANA"))); // 銀行カナ
		bean.setBankNamek(Util.avoidNull(rs.getString("BNK_NAME_K"))); // 銀行検索名称
		bean.setBranchCode(rs.getString("CBK_STN_CODE")); // 支店コード
		bean.setBranchName(Util.avoidNull(rs.getString("BNK_STN_NAME_S"))); // 支店名称
		bean.setBranchKana(Util.avoidNull(rs.getString("BNK_STN_KANA"))); // 支店カナ
		bean.setBranchNamek(Util.avoidNull(rs.getString("BNK_STN_NAME_K"))); // 支店検索名称

		// 銀行名（英字）
		bean.setBankNameE(rs.getString("CBK_BANK_NAME_E"));

		// 支店名（英字）
		bean.setBranchNameE(rs.getString("CBK_STN_NAME_E"));

		// 振込依頼人コード
		bean.setClientCode(rs.getString("CBK_IRAI_EMP_CODE"));

		// 振込依頼人名
		bean.setClientName(rs.getString("CBK_IRAI_NAME"));

		// 振込依頼人名（漢字）
		bean.setClientNameJ(rs.getString("CBK_IRAI_NAME_J"));

		// 振込依頼人名（英字）
		bean.setClientNameE(rs.getString("CBK_IRAI_NAME_E"));

		// 預金種目
		bean.setDepositKind(DepositKind.getDepositKind(rs.getInt("CBK_YKN_KBN")));

		// 口座番号
		bean.setAccountNo(rs.getString("CBK_YKN_NO"));

		// 計上部門コード
		bean.setDepartmentCode(rs.getString("CBK_DEP_CODE"));
		bean.setDepartmentName(rs.getString("DEP_NAME"));
		bean.setDepartmentNames(rs.getString("DEP_NAME_S"));
		bean.setDepartmentNamek(rs.getString("DEP_NAME_K"));

		// 科目コード
		bean.setItemCode(rs.getString("CBK_KMK_CODE"));
		bean.setItemName(rs.getString("KMK_NAME"));
		bean.setItemNames(rs.getString("KMK_NAME_S"));
		bean.setItemNamek(rs.getString("KMK_NAME_K"));

		// 補助科目コード
		bean.setSubItemCode(rs.getString("CBK_HKM_CODE"));
		bean.setSubItemName(rs.getString("HKM_NAME"));
		bean.setSubItemNames(rs.getString("HKM_NAME_S"));
		bean.setSubItemNamek(rs.getString("HKM_NAME_K"));

		// 内訳科目コード
		bean.setDetailItemCode(rs.getString("CBK_UKM_CODE"));
		bean.setDetailItemName(rs.getString("UKM_NAME"));
		bean.setDetailItemNames(rs.getString("UKM_NAME_S"));
		bean.setDetailItemNamek(rs.getString("UKM_NAME_K"));

		// IBANコード
		bean.setIBan(rs.getString("IBAN_NO"));

		// 銀行識別子
		bean.setBankIndentify(rs.getString("BANK_IDF"));

		// 口座識別子
		bean.setBankAccountIndentify(rs.getString("ACCOUNT_IDF"));

		// SWIFTコード
		bean.setSwift(rs.getString("SWIFT_CODE"));

		// BANK COUNTRY
		bean.setBankCountry(rs.getString("CBK_COU_CODE"));

		// 住所1
		bean.setBnkAdr1(rs.getString("BNK_ADR_1"));

		// 住所2
		bean.setBnkAdr2(rs.getString("BNK_ADR_2"));

		// 住所1（英字）
		bean.setBnkAdr1E(rs.getString("BNK_ADR_1_E"));

		// 住所2（英字）
		bean.setBnkAdr2E(rs.getString("BNK_ADR_2_E"));

		// 有効期間開始
		bean.setDateFrom(rs.getDate("STR_DATE"));

		// 有効期間終了
		bean.setDateTo(rs.getDate("END_DATE"));

		// 社員ＦＢで使用するか
		bean.setUseEmployeePayment(rs.getInt("CBK_EMP_FB_KBN") != 0);

		// 社外ＦＢで使用するか
		bean.setUseExPayment(rs.getInt("CBK_OUT_FB_KBN") != 0);

		return bean;

	}

	/**
	 * @return 通貨
	 */
	protected Currency createCurrency() {
		return new Currency();
	}

	/**
	 * @return 銀行口座
	 */
	protected BankAccount createBankAccount() {
		return new BankAccount();
	}

	/**
	 * エクセルを返す
	 * 
	 * @param condition
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] getExcel(BankAccountSearchCondition condition) throws TException {

		try {

			// データを抽出
			List<BankAccount> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			BankAccountExcel exl = new BankAccountExcel(getUser().getLanguage());
			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * SQL creator
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
