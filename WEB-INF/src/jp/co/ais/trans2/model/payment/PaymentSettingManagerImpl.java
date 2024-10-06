package jp.co.ais.trans2.model.payment;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.check.*;
import jp.co.ais.trans2.model.country.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.remittance.*;

/**
 * 支払条件マネージャの実装クラスです。
 * 
 * @author AIS
 */
public class PaymentSettingManagerImpl extends TModel implements PaymentSettingManager {

	/** 新送金目的マスタを使うかどうか、true:使う */
	protected static final boolean isUseNewRemittance = ServerConfig.isFlagOn("trans.new.mp0100.use");

	/**
	 * 指定条件に該当する支払方法情報を返す
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当する支払方法情報
	 * @throws TException
	 */
	public List<PaymentSetting> get(PaymentSettingSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<PaymentSetting> list = new ArrayList<PaymentSetting>();

		try {

			VCreator sql = new VCreator();

			sql.add("SELECT");
			sql.add("   sj.KAI_CODE, "); // 会社コード
			sql.add("   sj.TRI_CODE, "); // 取引先コード
			sql.add("   sj.TJK_CODE, "); // 取引先条件コード
			sql.add("   sj.FURI_TESU_KBN, "); // 振込手数料区分
			sql.add("   sj.SJC_DATE, "); // 支払条件締め日
			sql.add("   sj.SJR_MON, "); // 支払条件締め後月
			sql.add("   sj.SJP_DATE, "); // 支払条件支払日
			sql.add("   sj.SIHA_KBN, "); // 支払区分
			sql.add("   sj.SIHA_HOU_CODE, "); // 支払方法
			sql.add("   sj.FURI_CBK_CODE, "); // 振込振出銀行口座ｺｰﾄﾞ
			sql.add("   sj.BNK_CODE, "); // 銀行コード
			sql.add("   sj.BNK_STN_CODE, "); // 支店コード
			sql.add("   sj.YKN_KBN, "); // 預金種目
			sql.add("   sj.YKN_NO, "); // 口座番号
			sql.add("   sj.YKN_NAME, "); // 口座名義
			sql.add("   sj.YKN_KANA, "); // 口座名義カナ
			sql.add("   sj.GS_MKT_CODE, "); // 送金目的名
			sql.add("   sj.GS_STN_NAME, "); // 被仕向支店名称
			sql.add("   sj.GS_BNK_NAME, "); // 被仕向銀行名称
			sql.add("   sj.GS_TESU_KBN, "); // 手数料区分
			sql.add("   sj.SIHA_BNK_NAME, "); // 支払銀行名称
			sql.add("   sj.SIHA_STN_NAME, "); // 支払支店名称
			sql.add("   sj.SIHA_BNK_ADR, "); // 支払銀行住所
			sql.add("   sj.KEIYU_BNK_NAME, "); // 経由銀行名称
			sql.add("   sj.KEIYU_STN_NAME, "); // 経由支店名称
			sql.add("   sj.KEIYU_BNK_ADR, "); // 経由銀行住所
			sql.add("   sj.UKE_ADR, "); // 受取人住所
			sql.add("   sj.STR_DATE, "); // 開始年月日
			sql.add("   sj.END_DATE, "); // 終了年月日
			sql.add(" 	sj.COU_CODE,");
			sql.add(" 	sj.GS_BNK_COU_CODE,");
			sql.add(" 	sj.GS_BNK_SWIFT_CODE,");
			sql.add(" 	sj.SIHA_BNK_COU_CODE,");
			sql.add(" 	sj.SIHA_BNK_SWIFT_CODE,");
			sql.add(" 	sj.KEIYU_BNK_COU_CODE,");
			sql.add(" 	sj.KEIYU_BNK_SWIFT_CODE,");
			sql.add(" 	sj.BNK_CHG_KBN,");

			// 支払方法
			sql.add("   hoh.HOH_HOH_CODE, "); // 支払方法コード
			sql.add("   hoh.HOH_HOH_NAME, "); // 支払方法名
			sql.add("   hoh.HOH_HOH_NAME_K, "); // 支払方法検索名称
			sql.add("   hoh.HOH_KMK_CODE, "); // 科目コード
			sql.add("   hoh.HOH_HKM_CODE, "); // 補助科目コード
			sql.add("   hoh.HOH_UKM_CODE, "); // 内訳科目コード
			sql.add("   hoh.HOH_DEP_CODE, "); // 計上部門コード
			sql.add("   hoh.HOH_SIH_KBN, "); // 支払対象区分
			sql.add("   hoh.HOH_NAI_CODE, "); // 支払内部コード

			// 銀行口座
			sql.add("   cbk.CBK_CBK_CODE, "); // 銀行口座コード
			sql.add("   cbk.CBK_NAME, "); // 銀行口座名称
			sql.add("   cbk.CUR_CODE, "); // 通貨コード
			sql.add("   cbk.CBK_BNK_CODE, "); // 銀行コード
			sql.add("   cbk.CBK_STN_CODE, "); // 支店コード
			sql.add("   cbk.CBK_IRAI_EMP_CODE, "); // 振込依頼人コード
			sql.add("   cbk.CBK_IRAI_NAME, "); // 振込依頼人名
			sql.add("   cbk.CBK_IRAI_NAME_J, "); // 振込依頼人名（漢字）
			sql.add("   cbk.CBK_IRAI_NAME_E, "); // 振込依頼人名（英字）
			sql.add("   cbk.CBK_YKN_KBN, "); // 預金種目
			sql.add("   cbk.CBK_YKN_NO, "); // 口座番号
			sql.add("   cbk.CBK_EMP_FB_KBN, "); // 社員ＦＢ区分
			sql.add("   cbk.CBK_OUT_FB_KBN, "); // 社外ＦＢ区分
			sql.add("   cbk.CBK_DEP_CODE, "); // 計上部門コード
			sql.add("   cbk.CBK_KMK_CODE, "); // 科目コード
			sql.add("   cbk.CBK_HKM_CODE, "); // 補助科目コード
			sql.add("   cbk.CBK_UKM_CODE, "); // 内訳科目コード

			// 銀行＆支店名1
			sql.add(" CASE                                   ");
			sql.add(" WHEN bnk1.BNK_NAME_S IS NULL THEN      ");
			sql.add("  (SELECT MIN(BNK_NAME_S) FROM BNK_MST WHERE BNK_CODE = sj.BNK_CODE) ");
			sql.add(" ELSE ");
			sql.add("  bnk1.BNK_NAME_S                       ");
			sql.add(" END BNK_NAME,                          ");// 銀行名1
			sql.add("   bnk1.BNK_STN_NAME_S  BNK_STN_NAME, "); // 支店名1

			// 銀行＆支店名2
			sql.add("   bnk2.BNK_NAME_S  CBK_BNK_NAME, "); // 銀行名2
			sql.add("   bnk2.BNK_STN_NAME_S  CBK_BNK_STN_NAME, "); // 支店名2

			// 取引先
			sql.add("   tri.TRI_NAME, "); // 取引先名称
			sql.add("   tri.TRI_NAME_S, "); // 取引先略称
			sql.add("   tri.TRI_TYPE_PSN_FLG, "); // 口座を隠す

			// 送金目的
			if (isUseNewRemittance) {
				sql.add(" 	rmt.MKT_CODE,");
				sql.add(" 	rmt.RMT_NAME GS_MKT_NAME,");
			} else {
				sql.add("    NULL MKT_CODE,");
				sql.add(" 	mkt.MKT_NAME GS_MKT_NAME,");
			}

			// 国
			sql.add(" 	cou1.COU_NAME COU_NAME,"); // 受取人国
			sql.add(" 	cou2.COU_NAME GS_BNK_COU_NAME,"); // 被仕向銀行国
			sql.add(" 	cou3.COU_NAME SIHA_BNK_COU_NAME,"); // 支払銀行国
			sql.add(" 	cou4.COU_NAME KEIYU_BNK_COU_NAME"); // 経由銀行国

			sql.add("FROM TRI_SJ_MST sj");
			sql.add(" LEFT OUTER JOIN  AP_HOH_MST hoh  ON hoh.KAI_CODE = ? ", getCompanyCode());
			sql.add("                                 AND hoh.HOH_HOH_CODE = sj.SIHA_HOU_CODE ");
			sql.add(" LEFT OUTER JOIN  AP_CBK_MST cbk  ON cbk.KAI_CODE = ? ", getCompanyCode());
			sql.add("                                 AND cbk.CBK_CBK_CODE = sj.FURI_CBK_CODE ");
			sql.add(" LEFT OUTER JOIN  BNK_MST bnk1    ON sj.BNK_CODE      = bnk1.BNK_CODE");
			sql.add("                                 AND sj.BNK_STN_CODE  = bnk1.BNK_STN_CODE");
			sql.add(" LEFT OUTER JOIN  BNK_MST bnk2    ON cbk.CBK_BNK_CODE = bnk2.BNK_CODE");
			sql.add("                                 AND cbk.CBK_STN_CODE = bnk2.BNK_STN_CODE");
			sql.add(" LEFT OUTER JOIN  TRI_MST tri     ON tri.KAI_CODE = ? ", getCompanyCode());
			sql.add("                                 AND tri.TRI_CODE = sj.TRI_CODE");
			if (isUseNewRemittance) {
				sql.add(" LEFT OUTER JOIN  AP_RMT_MST rmt  ON rmt.KAI_CODE = ? ", getCompanyCode());
				sql.add("                                 AND rmt.RMT_CODE = sj.GS_MKT_CODE ");
			} else {
				sql.add(" LEFT OUTER JOIN  AP_MKT_MST mkt  ON sj.GS_MKT_CODE = mkt.MKT_CODE");
			}
			sql.add(" LEFT OUTER JOIN COUNTRY_MST cou1 ON sj.COU_CODE = cou1.COU_CODE");
			sql.add(" LEFT OUTER JOIN COUNTRY_MST cou2 ON sj.GS_BNK_COU_CODE = cou2.COU_CODE");
			sql.add(" LEFT OUTER JOIN COUNTRY_MST cou3 ON sj.SIHA_BNK_COU_CODE = cou3.COU_CODE");
			sql.add(" LEFT OUTER JOIN COUNTRY_MST cou4 ON sj.KEIYU_BNK_COU_CODE = cou4.COU_CODE");

			sql.add("WHERE 1 = 1 ");

			// 会社コード
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("   AND sj.KAI_CODE = ? ", condition.getCompanyCode());
			}

			// 取引先コード
			if (!Util.isNullOrEmpty(condition.getCustomerCode())) {
				sql.add("   AND sj.TRI_CODE = ? ", condition.getCustomerCode());
			}

			// 取引先 開始コード
			if (!Util.isNullOrEmpty(condition.getCustomerCodeFrom())) {
				sql.add("   AND sj.TRI_CODE >= ? ", condition.getCustomerCodeFrom());
			}

			// 取引先 終了コード
			if (!Util.isNullOrEmpty(condition.getCustomerCodeTo())) {
				sql.add("   AND sj.TRI_CODE <= ? ", condition.getCustomerCodeTo());
			}

			// コード
			if (!Util.isNullOrEmpty(condition.getCode())) {
				sql.add("   AND sj.TJK_CODE = ? ", condition.getCode());
			}

			// 開始コード
			if (!Util.isNullOrEmpty(condition.getCodeFrom())) {
				sql.add("   AND sj.TJK_CODE >= ? ", condition.getCodeFrom());
			}

			// 終了コード
			if (!Util.isNullOrEmpty(condition.getCodeTo())) {
				sql.add("   AND sj.TJK_CODE <= ? ", condition.getCodeTo());
			}

			// コード前方一致
			if (!Util.isNullOrEmpty(condition.getCodeLike())) {
				sql.add("   AND sj.TJK_CODE " + SQLUtil.getLikeStatement(condition.getCodeLike(), SQLUtil.CHAR_FRONT));
			}

			// 名称あいまい
			if (!Util.isNullOrEmpty(condition.getNameLike())) {
				sql.add("   AND CONCAT(NVL(bnk1.BNK_NAME_S,''), CONCAT(' ', NVL(bnk1.BNK_STN_NAME_S,''))) "
					+ SQLUtil.getLikeStatement(condition.getNameLike(), SQLUtil.NCHAR_AMBI));
			}

			// 有効期間
			if (!Util.isNullOrEmpty(condition.getValidTerm())) {
				sql.add("   AND sj.STR_DATE <= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm()));
				sql.add("   AND sj.END_DATE >= " + SQLUtil.getYYYYMMDDParam(condition.getValidTerm()));
			}

			// 支払条件口座番号
			if (!Util.isNullOrEmpty(condition.getYknNo())) {
				sql.add("   AND sj.YKN_NO = ? ", condition.getYknNo());
			}

			// 最終更新日時
			if (condition.getLastUpdateDate() != null) {
				sql.adt(" AND  (sj.INP_DATE > ? ", condition.getLastUpdateDate());
				sql.adt("    OR sj.UPD_DATE > ?)", condition.getLastUpdateDate());
			}

			sql.add(" ORDER BY sj.KAI_CODE, sj.TRI_CODE, sj.TJK_CODE");

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
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected PaymentSetting mapping(ResultSet rs) throws Exception {

		PaymentSetting bean = createPaymentSetting();
		bean.setCompanyCode(rs.getString("KAI_CODE"));// 会社コード
		bean.setCode(rs.getString("TJK_CODE"));// 取引先条件コード
		bean.setCashInFeeType(CashInFeeType.getCashInFeeType(rs.getInt("FURI_TESU_KBN")));// 振込手数料区分
		bean.setCloseDay(rs.getString("SJC_DATE"));// 支払条件締め日
		bean.setNextMonth(rs.getString("SJR_MON"));// 支払条件締め後月
		bean.setCashDay(rs.getString("SJP_DATE"));// 支払条件支払日
		bean.setPaymentDateType(PaymentDateType.getPaymentDateType(rs.getString("SIHA_KBN"))); // 支払区分
		bean.setBankCode(rs.getString("BNK_CODE"));// 銀行コード1
		bean.setBankName(rs.getString("BNK_NAME"));// 銀行名
		bean.setBranchCode(rs.getString("BNK_STN_CODE"));// 支店コード
		bean.setBranchName(rs.getString("BNK_STN_NAME"));// 支店名
		bean.setDepositKind(DepositKind.getDepositKind(rs.getInt("YKN_KBN")));// 預金種目
		bean.setDepositKindName(getWord(DepositKind.getDepositKindName(bean.getDepositKind())));// 預金種目名称
		bean.setAccountNo(rs.getString("YKN_NO"));// 口座番号
		bean.setAccountName(rs.getString("YKN_NAME")); // 口座名義
		bean.setAccountNameKana(rs.getString("YKN_KANA"));// 口座名義カナ
		bean.setSendBranchName(rs.getString("GS_STN_NAME"));// 被仕向支店名称
		bean.setSendBankName(rs.getString("GS_BNK_NAME"));// 被仕向銀行名称
		bean.setCommissionPaymentType(CommissionPaymentType.get(rs.getInt("GS_TESU_KBN")));// 手数料区分
		bean.setPayBankName(rs.getString("SIHA_BNK_NAME"));// 支払銀行名称
		bean.setPayBranchName(rs.getString("SIHA_STN_NAME"));// 支払支店名称
		bean.setPayBankAddress(rs.getString("SIHA_BNK_ADR"));// 支払銀行住所
		bean.setViaBankName(rs.getString("KEIYU_BNK_NAME"));// 経由銀行名称
		bean.setViaBranchName(rs.getString("KEIYU_STN_NAME"));// 経由支店名称
		bean.setViaBankAddress(rs.getString("KEIYU_BNK_ADR"));// 経由銀行住所
		bean.setReceiverAddress(rs.getString("UKE_ADR"));// 受取人住所
		bean.setDateFrom(rs.getDate("STR_DATE"));// 有効期間開始
		bean.setDateTo(rs.getDate("END_DATE"));// 有効期間終了
		bean.setBankSwiftCode(rs.getString("GS_BNK_SWIFT_CODE")); // 被仕向銀行SWIFTコード
		bean.setPaymentBankSwiftCode(rs.getString("SIHA_BNK_SWIFT_CODE")); // 支払銀行SWIFTコード
		bean.setViaBankSwiftCode(rs.getString("KEIYU_BNK_SWIFT_CODE")); // 経由銀行SWIFTコード
		bean.setBankChargeType(BankChargeType.get(rs.getInt("BNK_CHG_KBN"))); // バンクチャージ区分

		// 支払方法マスタ
		if (!Util.isNullOrEmpty(rs.getString("HOH_HOH_CODE"))) {
			PaymentMethod pm = createPaymentMethod();
			pm.setCompanyCode(rs.getString("KAI_CODE"));// 会社コード
			pm.setCode(rs.getString("HOH_HOH_CODE"));// コード
			pm.setName(rs.getString("HOH_HOH_NAME"));// 名称
			pm.setNamek(rs.getString("HOH_HOH_NAME_K"));// 検索名称
			pm.setItemCode(rs.getString("HOH_KMK_CODE"));// 科目コード
			pm.setSubItemCode(rs.getString("HOH_HKM_CODE"));// 補助科目コード
			pm.setDetailItemCode(rs.getString("HOH_UKM_CODE"));// 内訳科目コード
			pm.setDepartmentCode(rs.getString("HOH_DEP_CODE"));// 計上部門コード
			pm.setPaymentDivision(rs.getInt("HOH_SIH_KBN"));// 支払対象区分
			pm.setPaymentKind(PaymentKind.getPaymentKind(rs.getString("HOH_NAI_CODE")));// 支払内部コード
			bean.setPaymentMethod(pm);
		}

		// 銀行口座マスタ
		if (!Util.isNullOrEmpty(rs.getString("CBK_CBK_CODE"))) {
			BankAccount bankAccount = createBankAccount();
			bankAccount.setCompanyCode(rs.getString("KAI_CODE"));// 会社コード
			bankAccount.setCode(rs.getString("CBK_CBK_CODE"));// コード
			bankAccount.setName(rs.getString("CBK_NAME"));// 銀行口座名称
			bankAccount.setCurrencyCode(rs.getString("CUR_CODE")); // 通貨コード
			bankAccount.setBankCode(rs.getString("CBK_BNK_CODE"));// 銀行コード
			bankAccount.setBankName(Util.avoidNull(rs.getString("CBK_BNK_NAME")));// 銀行名称
			bankAccount.setBranchCode(rs.getString("CBK_STN_CODE"));// 支店コード
			bankAccount.setBranchName(Util.avoidNull(rs.getString("CBK_BNK_STN_NAME")));// 支店名称
			bankAccount.setClientCode(rs.getString("CBK_IRAI_EMP_CODE"));// 振込依頼人コード
			bankAccount.setClientName(rs.getString("CBK_IRAI_NAME"));// 振込依頼人名
			bankAccount.setClientNameJ(rs.getString("CBK_IRAI_NAME_J"));// 振込依頼人名（漢字）
			bankAccount.setClientNameE(rs.getString("CBK_IRAI_NAME_E"));// 振込依頼人名（英字）
			bankAccount.setDepositKind(DepositKind.getDepositKind(rs.getInt("CBK_YKN_KBN")));// 預金種目
			bankAccount.setAccountNo(rs.getString("CBK_YKN_NO"));// 口座番号
			bankAccount.setDepartmentCode(rs.getString("CBK_DEP_CODE"));// 計上部門コード
			bankAccount.setItemCode(rs.getString("CBK_KMK_CODE"));// 科目コード
			bankAccount.setSubItemCode(rs.getString("CBK_HKM_CODE"));// 補助科目コード
			bankAccount.setDetailItemCode(rs.getString("CBK_UKM_CODE"));// 内訳科目コード
			bankAccount.setUseEmployeePayment(rs.getInt("CBK_EMP_FB_KBN") != 0);// 社員ＦＢで使用するか
			bankAccount.setUseExPayment(rs.getInt("CBK_OUT_FB_KBN") != 0);// 社外ＦＢで使用するか
			bean.setBankAccount(bankAccount);
		}

		// 取引先
		Customer customer = createCustomer();
		customer.setCode(rs.getString("TRI_CODE"));
		customer.setName(Util.avoidNull(rs.getString("TRI_NAME")));
		customer.setNames(Util.avoidNull(rs.getString("TRI_NAME_S")));
		customer.setPersonalCustomer(BooleanUtil.toBoolean(rs.getInt("TRI_TYPE_PSN_FLG")));
		bean.setCustomer(customer);

		// 送金目的マスタ
		if (!Util.isNullOrEmpty(rs.getString("GS_MKT_CODE"))) {
			Remittance rmt = createRemittance();
			rmt.setCode(rs.getString("GS_MKT_CODE"));
			rmt.setBalanceCode(rs.getString("MKT_CODE")); // 国際収支コード
			rmt.setName(Util.avoidNull(rs.getString("GS_MKT_NAME")));
			bean.setRemittancePurpose(rmt);
		}

		// 国マスタ
		if (!Util.isNullOrEmpty(rs.getString("COU_NAME"))) {
			Country cou = createCountry();
			cou.setCode(rs.getString("COU_CODE"));
			cou.setName(rs.getString("COU_NAME"));
			bean.setRecipientCountry(cou); // 受取人国
		}

		if (!Util.isNullOrEmpty(rs.getString("GS_BNK_COU_NAME"))) {
			Country cou = createCountry();
			cou.setCode(rs.getString("GS_BNK_COU_CODE"));
			cou.setName(rs.getString("GS_BNK_COU_NAME"));
			bean.setBankCountry(cou); // 被仕向銀行国
		}

		if (!Util.isNullOrEmpty(rs.getString("SIHA_BNK_COU_NAME"))) {
			Country cou = createCountry();
			cou.setCode(rs.getString("SIHA_BNK_COU_CODE"));
			cou.setName(rs.getString("SIHA_BNK_COU_NAME"));
			bean.setPaymentBankCountry(cou); // 支払銀行国
		}

		if (!Util.isNullOrEmpty(rs.getString("KEIYU_BNK_COU_NAME"))) {
			Country cou = createCountry();
			cou.setCode(rs.getString("KEIYU_BNK_COU_CODE"));
			cou.setName(rs.getString("KEIYU_BNK_COU_NAME"));
			bean.setViaBankCountry(cou); // 経由銀行国
		}

		return bean;
	}

	/**
	 * @return 国
	 */
	protected Country createCountry() {
		return new Country();
	}

	/**
	 * @return 送金目的
	 */
	protected Remittance createRemittance() {
		return new Remittance();
	}

	/**
	 * @return 取引先
	 */
	protected Customer createCustomer() {
		return new Customer();
	}

	/**
	 * @return 銀行口座
	 */
	protected BankAccount createBankAccount() {
		return new BankAccount();
	}

	/**
	 * @return 支払方法
	 */
	protected PaymentMethod createPaymentMethod() {
		return new PaymentMethod();
	}

	/**
	 * @return 支払条件
	 */
	protected PaymentSetting createPaymentSetting() {
		return new PaymentSetting();
	}

	/**
	 * 支払方法を登録する。
	 * 
	 * @param bean 支払方法
	 * @throws TException
	 */
	public void entry(PaymentSetting bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();
			s.add("INSERT INTO TRI_SJ_MST( ");
			s.add("   KAI_CODE, ");
			s.add("   TRI_CODE, ");
			s.add("   TJK_CODE, ");
			s.add("   FURI_TESU_KBN, ");
			s.add("   SJC_DATE, ");
			s.add("   SJR_MON, ");
			s.add("   SJP_DATE, ");
			s.add("   SIHA_KBN, ");
			s.add("   SIHA_HOU_CODE, ");
			s.add("   FURI_CBK_CODE, ");
			s.add("   BNK_CODE, ");
			s.add("   BNK_STN_CODE, ");
			s.add("   YKN_KBN, ");
			s.add("   YKN_NO, ");
			s.add("   YKN_NAME, ");
			s.add("   YKN_KANA, ");
			s.add("   GS_MKT_CODE, ");
			s.add("   GS_STN_NAME, ");
			s.add("   GS_BNK_NAME, ");
			s.add("   GS_TESU_KBN, ");
			s.add("   SIHA_BNK_NAME, ");
			s.add("   SIHA_STN_NAME, ");
			s.add("   SIHA_BNK_ADR, ");
			s.add("   KEIYU_BNK_NAME, ");
			s.add("   KEIYU_STN_NAME, ");
			s.add("   KEIYU_BNK_ADR, ");
			s.add("   UKE_ADR, ");
			s.add("   STR_DATE, ");
			s.add("   END_DATE, ");
			s.add("   INP_DATE, ");
			s.add("   PRG_ID, ");
			s.add("   USR_ID, ");
			s.add("   COU_CODE, ");
			s.add("   GS_BNK_COU_CODE, ");
			s.add("   GS_BNK_SWIFT_CODE, ");
			s.add("   SIHA_BNK_COU_CODE, ");
			s.add("   SIHA_BNK_SWIFT_CODE, ");
			s.add("   KEIYU_BNK_COU_CODE, ");
			s.add("   KEIYU_BNK_SWIFT_CODE, ");
			s.add("   BNK_CHG_KBN ");
			s.add(") VALUES (");
			s.add("   ?,", bean.getCompanyCode());
			s.add("   ?,", bean.getCustomer().getCode());
			s.add("   ?,", bean.getCode());
			s.add("   ?,", bean.getCashInFeeType() != null ? bean.getCashInFeeType().value : 0);
			s.add("   ?,", bean.getCloseDay());
			s.add("   ?,", bean.getNextMonth());
			s.add("   ?,", bean.getCashDay());
			s.add("   ?,", bean.getPaymentDateType() != null ? bean.getPaymentDateType().value : "");
			s.add("   ?,", bean.getPaymentMethod().getCode());
			s.add("   ?,", bean.getBankAccount() != null ? bean.getBankAccount().getCode() : null);
			s.add("   ?,", bean.getBankCode());
			s.add("   ?,", bean.getBranchCode());
			s.add("   ?,", bean.getDepositKind() != null ? bean.getDepositKind().value : 0);
			s.add("   ?,", bean.getAccountNo());
			s.add("   ?,", bean.getAccountName());
			s.add("   ?,", bean.getAccountNameKana());
			s.add("   ?,", bean.getRemittancePurpose() != null ? bean.getRemittancePurpose().getCode() : "");
			s.add("   ?,", bean.getSendBranchName());
			s.add("   ?,", bean.getSendBankName());
			s.add("   ?,", bean.getCommissionPaymentType() != null ? bean.getCommissionPaymentType().value : 0);
			s.add("   ?,", bean.getPayBankName());
			s.add("   ?,", bean.getPayBranchName());
			s.add("   ?,", bean.getPayBankAddress());
			s.add("   ?,", bean.getViaBankName());
			s.add("   ?,", bean.getViaBranchName());
			s.add("   ?,", bean.getViaBankAddress());
			s.add("   ?,", bean.getReceiverAddress());
			s.add("   ?,", bean.getDateFrom());
			s.add("   ?,", bean.getDateTo());
			s.adt("   ?,", getNow());
			s.add("   ?,", getProgramCode());
			s.add("   ?,", getUserCode());
			s.add("   ?,", bean.getRecipientCountry() != null ? bean.getRecipientCountry().getCode() : ""); // 受取人国コード
			s.add("   ?,", bean.getBankCountry() != null ? bean.getBankCountry().getCode() : ""); // 被仕向銀行国コード
			s.add("   ?,", bean.getBankSwiftCode()); // 被仕向銀行SWIFTコード
			s.add("   ?,", bean.getPaymentBankCountry() != null ? bean.getPaymentBankCountry().getCode() : ""); // 支払銀行国コード
			s.add("   ?,", bean.getPaymentBankSwiftCode()); // 支払銀行SWIFTコード
			s.add("   ?,", bean.getViaBankCountry() != null ? bean.getViaBankCountry().getCode() : ""); // 経由銀行国コード
			s.add("   ?,", bean.getViaBankSwiftCode()); // 経由銀行SWIFTコード
			s.add("   ? ", bean.getBankChargeType() != null ? bean.getBankChargeType().value : 0); // バンクチャージ区分
			s.add(") ");

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 支払方法を修正する。
	 * 
	 * @param bean 支払方法
	 * @throws TException
	 */
	public void modify(PaymentSetting bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator s = new SQLCreator();
			s.add("UPDATE TRI_SJ_MST ");
			s.add(" SET ");
			s.add("   TRI_CODE = ?,", bean.getCustomer().getCode());
			s.add("   TJK_CODE = ?,", bean.getCode());

			if (bean.getCashInFeeType() != null) {
				s.add("   FURI_TESU_KBN = ?,", bean.getCashInFeeType().value);
			} else {
				s.add("   FURI_TESU_KBN = NULL,");
			}

			s.add("   SJC_DATE = ?,", bean.getCloseDay());
			s.add("   SJR_MON = ?,", bean.getNextMonth());
			s.add("   SJP_DATE = ?,", bean.getCashDay());

			if (bean.getPaymentDateType() != null) {
				s.add("   SIHA_KBN = ?,", bean.getPaymentDateType().value);
			} else {
				s.add("   SIHA_KBN = NULL,");
			}

			s.add("   SIHA_HOU_CODE = ?,", bean.getPaymentMethod().getCode());
			s.add("   FURI_CBK_CODE = ?,", bean.getBankAccount() != null ? bean.getBankAccount().getCode() : null);
			s.add("   BNK_CODE = ?,", bean.getBankCode());
			s.add("   BNK_STN_CODE = ?,", bean.getBranchCode());

			if (bean.getDepositKind() != null) {
				s.add("   YKN_KBN = ?,", bean.getDepositKind().value);
			} else {
				s.add("   YKN_KBN = NULL,");
			}

			s.add("   YKN_NO = ?,", bean.getAccountNo());
			s.add("   YKN_NAME = ?,", bean.getAccountName());
			s.add("   YKN_KANA = ?,", bean.getAccountNameKana());

			if (bean.getRemittancePurpose() != null) {
				s.add("   GS_MKT_CODE = ?,", bean.getRemittancePurpose().getCode());
			} else {
				s.add("   GS_MKT_CODE = NULL,");
			}

			s.add("   GS_STN_NAME = ?,", bean.getSendBranchName());
			s.add("   GS_BNK_NAME = ?,", bean.getSendBankName());

			if (bean.getCommissionPaymentType() != null) {
				s.add("   GS_TESU_KBN = ?,", bean.getCommissionPaymentType().value);
			} else {
				s.add("   GS_TESU_KBN = NULL,");
			}

			s.add("   SIHA_BNK_NAME = ?,", bean.getPayBankName());
			s.add("   SIHA_STN_NAME = ?,", bean.getPayBranchName());
			s.add("   SIHA_BNK_ADR = ?,", bean.getPayBankAddress());
			s.add("   KEIYU_BNK_NAME = ?,", bean.getViaBankName());
			s.add("   KEIYU_STN_NAME = ?,", bean.getViaBranchName());
			s.add("   KEIYU_BNK_ADR = ?,", bean.getViaBankAddress());
			s.add("   UKE_ADR = ?,", bean.getReceiverAddress());
			s.add("   STR_DATE = ?,", bean.getDateFrom());
			s.add("   END_DATE = ?,", bean.getDateTo());
			s.adt("   UPD_DATE = ?,", getNow());
			s.add("   PRG_ID = ?,", getProgramCode());
			s.add("   USR_ID = ?,", getUserCode());

			if (bean.getRecipientCountry() != null) {
				s.add("   COU_CODE = ?,", bean.getRecipientCountry().getCode()); // 受取人国コード
			} else {
				s.add("   COU_CODE = NULL,");
			}

			if (bean.getBankCountry() != null) {
				s.add("   GS_BNK_COU_CODE = ?,", bean.getBankCountry().getCode()); // 被仕向銀行国コード
			} else {
				s.add("   GS_BNK_COU_CODE = NULL,");
			}

			s.add("   GS_BNK_SWIFT_CODE = ?,", bean.getBankSwiftCode()); // 被仕向銀行SWIFTコード

			if (bean.getPaymentBankCountry() != null) {
				s.add("   SIHA_BNK_COU_CODE = ?,", bean.getPaymentBankCountry().getCode()); // 支払銀行国コード
			} else {
				s.add("   SIHA_BNK_COU_CODE = NULL,");
			}

			s.add("   SIHA_BNK_SWIFT_CODE = ?,", bean.getPaymentBankSwiftCode()); // 支払銀行SWIFTコード

			if (bean.getViaBankCountry() != null) {
				s.add("   KEIYU_BNK_COU_CODE = ?,", bean.getViaBankCountry().getCode()); // 経由銀行国コード
			} else {
				s.add("   KEIYU_BNK_COU_CODE = NULL,");
			}

			s.add("   KEIYU_BNK_SWIFT_CODE = ?,", bean.getViaBankSwiftCode()); // 経由銀行SWIFTコード

			if (bean.getBankChargeType() != null) {
				s.add("   BNK_CHG_KBN = ? ", bean.getBankChargeType().value); // バンクチャージ区分
			} else {
				s.add("   BNK_CHG_KBN = ? ", 0);
			}

			s.add("WHERE KAI_CODE = ? ", bean.getCompanyCode());
			s.add("  AND TRI_CODE = ? ", bean.getCustomer().getCode());
			s.add("  AND TJK_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 支払方法を削除する。
	 * 
	 * @param bean 支払方法
	 * @throws TException
	 */
	public void delete(PaymentSetting bean) throws TException {

		CheckCondition condition = new CheckCondition(CheckCondition.CHECK_TYPE.PAYMENT_SETTING);
		condition.setCompanyCode(bean.getCompanyCode());
		condition.setCustomerCode(bean.getCustomer().getCode());
		condition.setPaymentSettingCode(bean.getCode());

		// 使用済みチェック
		CheckMasterUseDao cd = (CheckMasterUseDao) getComponent(CheckMasterUseDao.class);
		cd.check(condition);

		Connection conn = DBUtil.getConnection();

		try {
			SQLCreator s = new SQLCreator();
			s.add("DELETE ");
			s.add("FROM TRI_SJ_MST ");
			s.add("WHERE KAI_CODE = ? ", bean.getCompanyCode());
			s.add("  AND TRI_CODE = ? ", bean.getCustomer().getCode());
			s.add("  AND TJK_CODE = ? ", bean.getCode());

			DBUtil.execute(conn, s);

		} catch (Exception e) {
			throw new TException(e);

		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 支払情報一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の一覧
	 * @throws TException
	 */
	public byte[] getExcel(PaymentSettingSearchCondition condition) throws TException {

		try {
			// 会社データの抽出
			List<PaymentSetting> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			PaymentSettingExcel layout = new PaymentSettingExcel(getUser().getLanguage());
			return layout.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * 支払情報一覧をエクセル形式で返す(海外用)
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の一覧
	 * @throws TException
	 */
	public byte[] getExcelForGlobal(PaymentSettingSearchCondition condition) throws TException {

		try {
			// 会社データの抽出
			List<PaymentSetting> list = get(condition);

			if (list == null || list.isEmpty()) {
				return null;
			}

			PaymentSettingGlobalExcel layout = new PaymentSettingGlobalExcel(getUser().getLanguage());
			return layout.getExcel(list);

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
