package jp.co.ais.trans2.model.bill;

import java.sql.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 入金方針マスタマネージャの実装クラスです。
 * 
 * @author AIS
 */
public class ReceivePolicyManagerImpl extends TModel implements ReceivePolicyManager {

	/** AR：請求書番号自動採番を使用する */
	protected static boolean isUseARInvAutoNumbering = ServerConfig.isFlagOn("trans.use.ar.inv.auto.numbering");

	/**
	 * ログイン会社コードの入金方針マスタ情報を返す
	 * 
	 * @return 入金方針マスタ情報
	 * @throws TException
	 */
	public ReceivePolicy get() throws TException {
		return get(getCompanyCode());
	}

	/**
	 * 指定条件に該当する入金方針情報を返す
	 * 
	 * @return 指定条件に該当する入金方針情報
	 * @throws TException
	 */
	public ReceivePolicy get(String companyCode) throws TException {
		Connection conn = DBUtil.getConnection();
		ReceivePolicy bean = null;

		try {

			VCreator sql = new VCreator();
			sql.add(" SELECT nhs.*");
			sql.add(" ,zei.ZEI_CODE");
			sql.add(" ,zei.ZEI_NAME");
			sql.add(" ,zei.ZEI_NAME_S");
			sql.add(" ,zei.ZEI_NAME_K");
			sql.add(" ,zei.US_KBN");
			sql.add(" ,zei.ZEI_RATE");
			sql.add(" ,zei.SZEI_KEI_KBN");

			sql.add(" FROM AR_NHS_MST nhs");

			sql.add(" LEFT JOIN SZEI_MST zei ON nhs.KAI_CODE = zei.KAI_CODE ");
			sql.add(" AND nhs.NHS_TESU_ZEI_CODE = zei.ZEI_CODE ");

			sql.add(" WHERE 1 = 1 ");

			// 会社コード
			if (!Util.isNullOrEmpty(getCompanyCode())) {
				sql.add("   AND nhs.KAI_CODE = ?", getCompanyCode());
			}

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				bean = mapping(rs);
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return bean;

	}

	/**
	 * 入金方針マスタを登録する。
	 * 
	 * @param bean 入金方針マスタ
	 * @throws TException
	 */
	public void entry(ReceivePolicy bean) throws TException {

		// TODO
	}

	/**
	 * 入金方針マスタを修正する。
	 * 
	 * @param bean 入金方針マスタ
	 * @throws TException
	 */
	public void modify(ReceivePolicy bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			VCreator sql = new VCreator();
			sql.add("UPDATE");
			sql.add("    AR_NHS_MST");
			sql.add("SET");
			sql.add("     NHS_REC_LENGTH = ?", bean.getLength());
			sql.add("    ,NHS_CRLF_KBN = ?", bean.getLineType());
			sql.add("    ,NHS_FORMAT_TYPE = ?", bean.getFormat());
			sql.add("    ,NHS_TESU_STR_KIN = ?", bean.getFeeFrom());
			sql.add("    ,NHS_TESU_END_KIN = ?", bean.getFeeTo());
			sql.add("    ,NHS_TESU_KMK_CODE = ?", bean.getItemCode());
			sql.add("    ,NHS_TESU_HKM_CODE = ?", bean.getSubItemCode());
			sql.add("    ,NHS_TESU_UKM_CODE = ?", bean.getDetailCode());
			sql.add("    ,NHS_TESU_DEP_CODE = ?", bean.getDepartmentCode());
			sql.add("    ,NHS_TESU_ZEI_CODE = ?", bean.getTaxCode());
			sql.add("    ,NHS_SEI_NO_INP_KBN = ?", bean.isCompulsoryInputBillNo() ? 1 : 0);
			sql.add("    ,NHS_SEI_KBN = ?", bean.isCheckBillMake() ? 1 : 0);
			if (isUseARInvAutoNumbering) {
				sql.add("    ,NHS_JID_USE_KBN = ?", bean.isInvNumbering() ? 1 : 0);
				InvoiceNoAdopt ad = bean.getInvoiceNoAdopt1();
				sql.add("    ,NHS_JID_KBN_1 = ?", ad != null ? ad.getValue() : 0);
				ad = bean.getInvoiceNoAdopt2();
				sql.add("    ,NHS_JID_KBN_2 = ?", ad != null ? ad.getValue() : 0);
				ad = bean.getInvoiceNoAdopt3();
				sql.add("    ,NHS_JID_KBN_3 = ?", ad != null ? ad.getValue() : 0);
				sql.add("    ,NHS_JID_NAME_1 = ?", bean.getInvNo1Name());
				sql.add("    ,NHS_JID_NAME_2 = ?", bean.getInvNo2Name());
				sql.add("    ,NHS_JID_NAME_3 = ?", bean.getInvNo3Name());
				sql.add("    ,AUTO_NO_KETA = ?", bean.getInvNoDigit());
			}
			sql.adt("    ,UPD_DATE = ?", getNow());
			sql.add("    ,PRG_ID = ?", getProgramCode());
			sql.add("    ,USR_ID = ?", getUserCode());
			sql.add("WHERE");
			sql.add("    KAI_CODE = ?", getCompanyCode());
			int count = DBUtil.execute(conn, sql); // 上記のSQLでupdateしたデータの数をint couontに入れる。

			if (count == 0) { // updateした数が0だった場合true
				sql = new VCreator();
				sql.add("INSERT INTO");
				sql.add("     AR_NHS_MST (");
				sql.add("     KAI_CODE ");
				sql.add("    ,NHS_REC_LENGTH ");
				sql.add("    ,NHS_CRLF_KBN ");
				sql.add("    ,NHS_FORMAT_TYPE ");
				sql.add("    ,NHS_TESU_STR_KIN ");
				sql.add("    ,NHS_TESU_END_KIN ");
				sql.add("    ,NHS_TESU_KMK_CODE ");
				sql.add("    ,NHS_TESU_HKM_CODE ");
				sql.add("    ,NHS_TESU_UKM_CODE ");
				sql.add("    ,NHS_TESU_DEP_CODE ");
				sql.add("    ,NHS_TESU_ZEI_CODE ");
				sql.add("    ,NHS_SEI_NO_INP_KBN ");
				sql.add("    ,NHS_SEI_KBN ");
				if (isUseARInvAutoNumbering) {
					sql.add("    ,NHS_JID_USE_KBN ");
					sql.add("    ,NHS_JID_KBN_1 ");
					sql.add("    ,NHS_JID_KBN_2 ");
					sql.add("    ,NHS_JID_KBN_3 ");
					sql.add("    ,NHS_JID_NAME_1 ");
					sql.add("    ,NHS_JID_NAME_2 ");
					sql.add("    ,NHS_JID_NAME_3 ");
					sql.add("    ,AUTO_NO_KETA ");
				}
				sql.add("    ,INP_DATE ");
				sql.add("    ,PRG_ID ");
				sql.add("    ,USR_ID ");
				sql.add(" ) VALUES (");
				sql.add("     ?", bean.getCompanyCode());
				sql.add("    ,?", bean.getLength());
				sql.add("    ,?", bean.getLineType());
				sql.add("    ,?", bean.getFormat());
				sql.add("    ,?", bean.getFeeFrom());
				sql.add("    ,?", bean.getFeeTo());
				sql.add("    ,?", bean.getItemCode());
				sql.add("    ,?", bean.getSubItemCode());
				sql.add("    ,?", bean.getDetailCode());
				sql.add("    ,?", bean.getDepartmentCode());
				sql.add("    ,?", bean.getTaxCode());
				sql.add("    ,?", bean.isCompulsoryInputBillNo() ? 1 : 0);
				sql.add("    ,?", bean.isCheckBillMake() ? 1 : 0);
				if (isUseARInvAutoNumbering) {
					sql.add("    ,? ", bean.isInvNumbering() ? 1 : 0);
					InvoiceNoAdopt ad = bean.getInvoiceNoAdopt1();
					sql.add("    ,? ", ad != null ? ad.getValue() : 0);
					ad = bean.getInvoiceNoAdopt2();
					sql.add("    ,? ", ad != null ? ad.getValue() : 0);
					ad = bean.getInvoiceNoAdopt3();
					sql.add("    ,? ", ad != null ? ad.getValue() : 0);
					sql.add("    ,? ", bean.getInvNo1Name());
					sql.add("    ,? ", bean.getInvNo2Name());
					sql.add("    ,? ", bean.getInvNo3Name());
					sql.add("    ,? ", bean.getInvNoDigit());
				}
				sql.adt("    ,?", getNow());
				sql.add("    ,?", getProgramCode());
				sql.add("    ,?", getUserCode());
				sql.add(")");
				DBUtil.execute(conn, sql);
			}

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 入金方針マスタを削除する。
	 * 
	 * @param bean 入金方針マスタ
	 * @throws TException
	 */
	public void delete(ReceivePolicy bean) throws TException {

		// TODO

	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected ReceivePolicy mapping(ResultSet rs) throws Exception {

		ReceivePolicy bean = new ReceivePolicy();
		bean.setCompanyCode(rs.getString("KAI_CODE")); // 会社コード
		bean.setInFolder(rs.getString("NHS_IN_FOLDER"));
		bean.setOutFolder(rs.getString("NHS_BAK_FOLDER"));
		bean.setLength(rs.getInt("NHS_REC_LENGTH")); // レコード長
		bean.setLineType(rs.getString("NHS_CRLF_KBN")); // CR/LF付
		bean.setFeeFrom(rs.getBigDecimal("NHS_TESU_STR_KIN")); // 差額許容範囲 開始金額
		bean.setFeeTo(rs.getBigDecimal("NHS_TESU_END_KIN")); // 差額許容範囲 終了金額
		bean.setItemCode(rs.getString("NHS_TESU_KMK_CODE")); // 科目
		bean.setSubItemCode(rs.getString("NHS_TESU_HKM_CODE")); // 補助科目
		bean.setDetailCode(rs.getString("NHS_TESU_UKM_CODE")); // 内訳科目
		bean.setDepartmentCode(rs.getString("NHS_TESU_DEP_CODE")); // 計上部門
		bean.setTaxCode(rs.getString("NHS_TESU_ZEI_CODE")); // 消費税
		bean.setCompulsoryInputBillNo(rs.getString("NHS_SEI_NO_INP_KBN").equals("1")); // 請求書番号入力フラグ
		bean.setCheckBillMake(rs.getString("NHS_SEI_KBN").equals("1")); // 請求書作成フラグ
		bean.setFormat(rs.getString("NHS_FORMAT_TYPE")); // 振込入金通知タイプ

		if (isUseARInvAutoNumbering) {
			bean.setInvNumbering(rs.getInt("NHS_JID_USE_KBN") == 1);
			bean.setInvoiceNoAdopt1(InvoiceNoAdopt.get(rs.getInt("NHS_JID_KBN_1")));
			bean.setInvoiceNoAdopt2(InvoiceNoAdopt.get(rs.getInt("NHS_JID_KBN_2")));
			bean.setInvoiceNoAdopt3(InvoiceNoAdopt.get(rs.getInt("NHS_JID_KBN_3")));
			bean.setInvNo1Name(rs.getString("NHS_JID_NAME_1"));
			bean.setInvNo2Name(rs.getString("NHS_JID_NAME_2"));
			bean.setInvNo3Name(rs.getString("NHS_JID_NAME_3"));
			bean.setInvNoDigit(rs.getInt("AUTO_NO_KETA"));
		}

		ConsumptionTax tax = new ConsumptionTax();
		tax.setCode(rs.getString("ZEI_CODE"));
		tax.setName(rs.getString("ZEI_NAME"));
		tax.setNames(rs.getString("ZEI_NAME_S"));
		tax.setNamek(rs.getString("ZEI_NAME_K"));
		tax.setTaxType(TaxType.get(rs.getInt("US_KBN")));
		tax.setRate(rs.getBigDecimal("ZEI_RATE"));
		bean.setTax(tax);

		return bean;

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
