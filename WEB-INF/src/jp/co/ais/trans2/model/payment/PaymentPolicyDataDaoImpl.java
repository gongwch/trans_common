package jp.co.ais.trans2.model.payment;

import java.sql.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.model.*;

/**
 * 支払方針データ抽出Dao実装クラス
 */
public class PaymentPolicyDataDaoImpl extends TModel implements PaymentPolicyDataDao {

	/**
	 * 支払方針データ検索
	 * 
	 * @throws TException
	 */
	public PaymentPolicy getFolderPass() throws TException {

		Connection conn = DBUtil.getConnection();

		PaymentPolicy bean = createPaymentPolicy();

		try {

			SQLCreator s = new SQLCreator();

			// カラムが存在した場合

			s.add("SELECT ");
			s.add("  KAI_CODE,");
			s.add("  SHH_FB_PATH,");
			s.add("  SHH_REM_PATH,");
			s.add("  SHH_EMP_FB_PATH");
			s.add("FROM AP_SHH_MST");
			s.add("WHERE  KAI_CODE = ?", getCompanyCode());

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, s);

			while (rs.next()) {
				bean = mappingPaymentMethod(rs);
			}

		} catch (Exception e) {

			bean.setCompanyCode(getCompanyCode());

		} finally {
			DBUtil.close(conn);
		}

		return bean;
	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected PaymentPolicy mappingPaymentMethod(ResultSet rs) throws Exception {

		PaymentPolicy bean = createPaymentPolicy();

		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setFbOutputPath(rs.getString("SHH_FB_PATH"));
		bean.setRemmitanceOutputPath(rs.getString("SHH_REM_PATH"));
		bean.setEmployeeFbOutputPath(rs.getString("SHH_EMP_FB_PATH"));

		return bean;
	}

	/**
	 * 支払方針データ検索
	 * 
	 * @throws TException
	 */
	public PaymentPolicy get() throws TException {

		Connection conn = DBUtil.getConnection();

		PaymentPolicy bean = createPaymentPolicy();

		try {

			SQLCreator s = new SQLCreator();

			// カラムが存在した場合

			s.add("SELECT *");
			s.add("FROM AP_SHH_MST");
			s.add("WHERE  KAI_CODE = ?", getCompanyCode());

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, s);

			while (rs.next()) {
				bean = mapping(rs);
			}

		} catch (Exception e) {

			bean.setCompanyCode(getCompanyCode());

		} finally {
			DBUtil.close(conn);
		}

		return bean;
	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected PaymentPolicy mapping(ResultSet rs) throws Exception {

		PaymentPolicy bean = createPaymentPolicy();

		bean.setCompanyCode(rs.getString("KAI_CODE"));

		bean.setSHH_SIHA_1(rs.getInt("SHH_SIHA_1")); // 定時支払（１日）
		bean.setSHH_SIHA_5(rs.getInt("SHH_SIHA_5")); // 定時支払（５日）
		bean.setSHH_SIHA_10(rs.getInt("SHH_SIHA_10")); // 定時支払（10日）
		bean.setSHH_SIHA_15(rs.getInt("SHH_SIHA_15")); // 定時支払（15日）
		bean.setSHH_SIHA_20(rs.getInt("SHH_SIHA_20")); // 定時支払（20日）
		bean.setSHH_SIHA_25(rs.getInt("SHH_SIHA_25")); // 定時支払（25日）
		bean.setSHH_SIHA_30(rs.getInt("SHH_SIHA_30")); // 定時支払（末日）
		bean.setSHH_BNK_KBN_1(rs.getInt("SHH_BNK_KBN_1")); // 銀行休日区分（1日）
		bean.setSHH_BNK_KBN_5(rs.getInt("SHH_BNK_KBN_5")); // 銀行休日区分（5日）
		bean.setSHH_BNK_KBN_10(rs.getInt("SHH_BNK_KBN_10")); // 銀行休日区分（10日）
		bean.setSHH_BNK_KBN_15(rs.getInt("SHH_BNK_KBN_15")); // 銀行休日区分（15日）
		bean.setSHH_BNK_KBN_20(rs.getInt("SHH_BNK_KBN_20")); // 銀行休日区分（20日）
		bean.setSHH_BNK_KBN_25(rs.getInt("SHH_BNK_KBN_25")); // 銀行休日区分（25日）
		bean.setSHH_BNK_KBN_30(rs.getInt("SHH_BNK_KBN_30")); // 銀行休日区分（末日）
		bean.setSHH_SIHA_MIN(rs.getBigDecimal("SHH_SIHA_MIN")); // 支払下限額
		bean.setSHH_FURI_MIN(rs.getBigDecimal("SHH_FURI_MIN")); // 振込手数料下限額
		bean.setSHH_TESU_KMK_CODE(rs.getString("SHH_TESU_KMK_CODE")); // 手数料科目コード
		bean.setSHH_TESU_HKM_CODE(rs.getString("SHH_TESU_HKM_CODE")); // 手数料補助科目コード
		bean.setSHH_TESU_UKM_CODE(rs.getString("SHH_TESU_UKM_CODE")); // 手数料内訳科目コード
		bean.setSHH_TESU_DEP_CODE(rs.getString("SHH_TESU_DEP_CODE")); // 手数料計上部門コード
		bean.setSHH_TESU_ZEI_CODE(rs.getString("SHH_TESU_ZEI_CODE")); // 手数料消費税コード
		bean.setSHH_GS_PRINT_KBN(rs.getString("SHH_GS_PRINT_KBN")); // 外国送金作成フラグ
		bean.setSHH_SEI_NO_KBN(rs.getString("SHH_SEI_NO_KBN")); // 請求書番号フラグ

		bean.setFbOutputPath(rs.getString("SHH_FB_PATH"));
		bean.setRemmitanceOutputPath(rs.getString("SHH_REM_PATH"));
		bean.setEmployeeFbOutputPath(rs.getString("SHH_EMP_FB_PATH"));

		return bean;
	}

	/**
	 * @return エンティティ
	 */
	protected PaymentPolicy createPaymentPolicy() {
		return new PaymentPolicy();
	}

}