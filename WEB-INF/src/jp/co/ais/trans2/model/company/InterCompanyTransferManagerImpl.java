package jp.co.ais.trans2.model.company;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.*;

import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.db.SQLUtil;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.customer.*;
import jp.co.ais.trans2.model.department.*;
import jp.co.ais.trans2.model.item.*;
import jp.co.ais.trans.common.except.TException;
import jp.co.ais.trans.common.util.*;

/**
 * 会社間付替マネージャの実装クラスです。
 * 
 * @author AIS
 */
public class InterCompanyTransferManagerImpl extends TModel implements InterCompanyTransferManager {

	public List<InterCompanyTransfer> get(InterCompanyTransferSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<InterCompanyTransfer> list = new ArrayList<InterCompanyTransfer>();

		try {

			String sql =
				" SELECT " +
					" ktk.KAI_CODE, " +
					" ktk.KTK_KAI_CODE, " +
					" env.KAI_NAME, " +
					" env.KAI_NAME_S, " +
					" ktk.KTK_DEP_CODE, " +
					" dep.DEP_NAME, " +
					" dep.DEP_NAME_S, " +
					" ktk.KTK_KMK_CODE, " +
					" kmk.KMK_NAME, " +
					" kmk.KMK_NAME_S, " +
					" kmk.HKM_KBN, " +
					" kmk.TRI_CODE_FLG AS KMK_TRI_CODE_FLG, " +
					" ktk.KTK_HKM_CODE, " +
					" hkm.HKM_NAME, " +
					" hkm.HKM_NAME_S, " +
					" hkm.UKM_KBN, " +
					" hkm.TRI_CODE_FLG AS HKM_TRI_CODE_FLG, " +
					" ktk.KTK_UKM_CODE, " +
					" ukm.UKM_NAME, " +
					" ukm.UKM_NAME_S, " +
					" ukm.TRI_CODE_FLG AS UKM_TRI_CODE_FLG, " +
					" ktk.KTK_TRI_CODE, " +
					" tri.TRI_NAME, " +
					" tri.TRI_NAME_S " +
				" FROM " +
					" KTK_MST ktk " +
					" LEFT OUTER JOIN ENV_MST env " +
					" ON	ktk.KTK_KAI_CODE = env.KAI_CODE " +
					" LEFT OUTER JOIN BMN_MST dep " +
					" ON	ktk.KAI_CODE = dep.KAI_CODE " +
					" AND	ktk.KTK_DEP_CODE = dep.DEP_CODE " +
					" LEFT OUTER JOIN KMK_MST kmk " +
					" ON	ktk.KAI_CODE = kmk.KAI_CODE " +
					" AND	ktk.KTK_KMK_CODE = kmk.KMK_CODE " +
					" LEFT OUTER JOIN HKM_MST hkm " +
					" ON	ktk.KAI_CODE = hkm.KAI_CODE " +
					" AND	ktk.KTK_KMK_CODE = hkm.KMK_CODE " +
					" AND	ktk.KTK_HKM_CODE = hkm.HKM_CODE " +
					" LEFT OUTER JOIN UKM_MST ukm " +
					" ON	ktk.KAI_CODE = ukm.KAI_CODE " +
					" AND	ktk.KTK_KMK_CODE = ukm.KMK_CODE " +
					" AND	ktk.KTK_HKM_CODE = ukm.HKM_CODE " +
					" AND	ktk.KTK_UKM_CODE = ukm.UKM_CODE " +
					" LEFT OUTER JOIN TRI_MST tri " +
					" ON	ktk.KAI_CODE = tri.KAI_CODE " +
					" AND	ktk.KTK_TRI_CODE = tri.TRI_CODE " +
				" WHERE 1 = 1 ";

			// 会社コード
			if (!Util.isNullOrEmpty(condition.getCompany().getCode())) {
				sql += " AND	ktk.KAI_CODE = " + SQLUtil.getParam(condition.getCompany().getCode());
			}

			// 付替会社コード
			if (!Util.isNullOrEmpty(condition.getTransferCompanyCode())) {
				sql += " AND	ktk.KTK_KAI_CODE = " + SQLUtil.getParam(condition.getTransferCompanyCode());
			}

			// 付替会社開始コード
			if (!Util.isNullOrEmpty(condition.getTransferCompanyFrom())) {
				sql += " AND	ktk.KTK_KAI_CODE >= " + SQLUtil.getParam(condition.getTransferCompanyFrom());
			}

			// 付替会社終了コード
			if (!Util.isNullOrEmpty(condition.getTransferCompanyTo())) {
				sql += " AND	ktk.KTK_KAI_CODE <= " + SQLUtil.getParam(condition.getTransferCompanyTo());
			}

			sql += " ORDER BY " + " ktk.KTK_KAI_CODE ";

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

	public void entry(InterCompanyTransfer interCompanyTransfer) throws TException {

		String sql =
			" INSERT INTO KTK_MST ( " +
				" KAI_CODE, " +
				" KTK_KAI_CODE, " +
				" KTK_DEP_CODE, " +
				" KTK_KMK_CODE, " +
				" KTK_HKM_CODE, " +
				" KTK_UKM_CODE, " +
				" KTK_TRI_CODE, " +
				" INP_DATE, " +
				" PRG_ID, " +
				" USR_ID " +
			" ) VALUES (" +
				SQLUtil.getParam(interCompanyTransfer.getCompanyCode()) + ", " +
				SQLUtil.getParam(interCompanyTransfer.getTransferCompany().getCode()) + ", " +
				SQLUtil.getParam(interCompanyTransfer.getDepartment().getCode()) + ", " +
				SQLUtil.getParam(interCompanyTransfer.getItem().getCode()) + ", " +
				SQLUtil.getParam(interCompanyTransfer.getItem().getSubItemCode()) + ", " +
				SQLUtil.getParam(interCompanyTransfer.getItem().getDetailItemCode()) + ", " +
				SQLUtil.getParam(interCompanyTransfer.getCustomer().getCode()) + ", " +
				SQLUtil.getYMDHMSParam(getNow()) + ", " +
				SQLUtil.getParam(getProgramCode()) + ", " +
				SQLUtil.getParam(getUserCode()) +
			" ) ";

		Connection conn = DBUtil.getConnection();
		DBUtil.execute(conn, sql);

	}

	public void modify(InterCompanyTransfer interCompanyTransfer) throws TException {

		String sql =
			" UPDATE KTK_MST " +
			" SET " +
				" KTK_DEP_CODE = " + SQLUtil.getParam(interCompanyTransfer.getDepartment().getCode()) + ", " +
				" KTK_KMK_CODE = " + SQLUtil.getParam(interCompanyTransfer.getItem().getCode()) + ", " +
				" KTK_HKM_CODE = " + SQLUtil.getParam(interCompanyTransfer.getItem().getSubItemCode()) + ", " +
				" KTK_UKM_CODE = " + SQLUtil.getParam(interCompanyTransfer.getItem().getDetailItemCode()) + ", " +
				" KTK_TRI_CODE = " + SQLUtil.getParam(interCompanyTransfer.getCustomer().getCode()) + ", " +
				" UPD_DATE = " + SQLUtil.getYMDHMSParam(getNow()) + ", " +
				" PRG_ID = " + SQLUtil.getParam(getProgramCode()) + ", " +
				" USR_ID = " + SQLUtil.getParam(getUserCode()) +
			" WHERE KAI_CODE = " + SQLUtil.getParam(interCompanyTransfer.getCompanyCode()) +
			" AND 	KTK_KAI_CODE = " + SQLUtil.getParam(interCompanyTransfer.getTransferCompany().getCode());

		Connection conn = DBUtil.getConnection();

		DBUtil.execute(conn, sql);

	}

	public void delete(InterCompanyTransfer interCompanyTransfer) throws TException {

		String sql =
			" DELETE " +
			" FROM	KTK_MST " +
			" WHERE KAI_CODE = " + SQLUtil.getParam(interCompanyTransfer.getCompanyCode()) +
			" AND KTK_KAI_CODE = " + SQLUtil.getParam(interCompanyTransfer.getTransferCompany().getCode());

		Connection conn = DBUtil.getConnection();

		DBUtil.execute(conn, sql);

	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return bean
	 * @throws Exception
	 */
	protected InterCompanyTransfer mapping(ResultSet rs) throws Exception {

		InterCompanyTransfer bean = new InterCompanyTransfer();
		// 会社コード
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		// 付替会社
		Company company = new Company();
		company.setCode(rs.getString("KTK_KAI_CODE"));
		company.setName(rs.getString("KAI_NAME"));
		company.setNames(rs.getString("KAI_NAME_S"));
		bean.setTransferCompany(company);
		// 付替部門
		Department department = new Department();
		department.setCode(rs.getString("KTK_DEP_CODE"));
		department.setName(rs.getString("DEP_NAME"));
		department.setNames(rs.getString("DEP_NAME_S"));
		bean.setDepartment(department);
		// 科目
		Item item = new Item();
		item.setCode(rs.getString("KTK_KMK_CODE"));
		item.setName(rs.getString("KMK_NAME"));
		item.setNames(rs.getString("KMK_NAME_S"));
		item.setSubItem(BooleanUtil.toBoolean(rs.getInt("HKM_KBN")));
		item.setClientType(CustomerType.get(rs.getInt("KMK_TRI_CODE_FLG")));

		// 補助科目
		if (!Util.isNullOrEmpty(rs.getString("KTK_HKM_CODE"))) {
			SubItem subItem = new SubItem();
			subItem.setCode(rs.getString("KTK_HKM_CODE"));
			subItem.setName(rs.getString("HKM_NAME"));
			subItem.setNames(rs.getString("HKM_NAME_S"));
			subItem.setDetailItem(BooleanUtil.toBoolean(rs.getInt("UKM_KBN")));
			subItem.setClientType(CustomerType.get(rs.getInt("HKM_TRI_CODE_FLG")));
			item.setSubItem(subItem);
			// 内訳科目
			if (!Util.isNullOrEmpty(rs.getString("KTK_UKM_CODE"))) {
				DetailItem detailItem = new DetailItem();
				detailItem.setCode(rs.getString("KTK_UKM_CODE"));
				detailItem.setName(rs.getString("UKM_NAME"));
				detailItem.setNames(rs.getString("UKM_NAME_S"));
				detailItem.setClientType(CustomerType.get(rs.getInt("UKM_TRI_CODE_FLG")));
				subItem.setDetailItem(detailItem);
			}
		}
		bean.setItem(item);
		// 取引先
		Customer customer = new Customer();
		customer.setCode(rs.getString("KTK_TRI_CODE"));
		customer.setName(rs.getString("TRI_NAME"));
		customer.setNames(rs.getString("TRI_NAME_S"));
		bean.setCustomer(customer);

		return bean;

	}

	/**
	 * 会社間付替マスタのデータを取得し、エンティティを返す。
	 */
	public InterCompanyTransfer getOne(String companyCode, String transferCompanyCode) throws TException {
		InterCompanyTransferSearchCondition c = new InterCompanyTransferSearchCondition();
		Company company = new Company();
		company.setCode(companyCode);
		c.setCompany(company);
		c.setTransferCompanyCode(transferCompanyCode);
		List<InterCompanyTransfer> list = get(c);
		if (list == null || list.isEmpty()) {
			return null;
		}
		return list.get(0);
	}

	/**
	 * エクセルを返す
	 * 
	 * @param condition
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] getExcel(InterCompanyTransferSearchCondition condition) throws TException {

		try {

			// 検索条件を取得する
			List<InterCompanyTransfer> list = get(condition);

			// 検索条件に該当するデータが存在しない場合、メッセージを表示して終了
			if (list == null || list.isEmpty()) {
				return null;
			}

			InterCompanyTransferExcel exl = new InterCompanyTransferExcel(getUser().getLanguage());
			return exl.getExcel(condition.getCompany(), list);
		} catch (Exception e) {
			throw new TException(e);
		}
	}

}
