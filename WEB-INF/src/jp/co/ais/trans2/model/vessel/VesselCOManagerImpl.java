package jp.co.ais.trans2.model.vessel;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * 内航外航区分マネージャの実装クラス
 * 
 * @author AIS
 */
public class VesselCOManagerImpl extends TModel implements VesselCOManager {

	/**
	 * 指定条件に該当するデータを返す
	 * 
	 * @return 対象データリスト
	 * @throws TException
	 */
	public List<VesselCO> get(boolean isExcel) throws TException {

		Connection conn = DBUtil.getConnection();
		List<VesselCO> list = new ArrayList<VesselCO>();
		try {

			SQLCreator sql = new SQLCreator();

			sql.add(" SELECT ");
			sql.add("  cvm.KAI_CODE ");
			sql.add(" ,cvm.VESSEL_CODE ");
			sql.add(" ,cvm.VESSEL_NAME_S ");
			sql.add(" ,cvc.CO_KBN ");
			sql.add(" FROM CM_VESSEL_MST cvm ");
			sql.add("     LEFT JOIN CM_VESSEL_CO_MST cvc ");
			sql.add("     ON  cvc.KAI_CODE = cvm.KAI_CODE ");
			sql.add("     AND cvc.VESSEL_CODE = cvm.VESSEL_CODE ");
			sql.add(" WHERE cvm.KAI_CODE = ?", getCompanyCode());
			// エクセルの場合、NULLを除外
			if (isExcel) {
				sql.add("     AND cvc.CO_KBN IS NOT NULL ");
			}
			sql.add(" ORDER BY cvc.CO_KBN ,cvm.VESSEL_CODE ");

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
	 * 登録する
	 * 
	 * @param bean
	 * @throws TException
	 */
	public void entry(List<VesselCO> bean) throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			for (VesselCO VesselCO : bean) {

				SQLCreator sql = new SQLCreator();

				sql.add("INSERT INTO CM_VESSEL_CO_MST ( ");
				sql.add("  KAI_CODE ");
				sql.add(" ,VESSEL_CODE ");
				sql.add(" ,CO_KBN ");
				sql.add(" ,INP_DATE ");
				sql.add(" ,PRG_ID ");
				sql.add(" ,USR_ID ");
				sql.add(") VALUES ( ");
				sql.add("  ?", getCompanyCode());
				sql.add(" ,?", VesselCO.getVesselCode());
				sql.add(" ,?", VesselCO.getCOKbn());
				sql.adt(" ,?", getNow());
				sql.add(" ,?", getProgramCode());
				sql.add(" ,?", getUserCode());
				sql.add(") ");

				DBUtil.execute(conn, sql);

			}
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

	}

	/**
	 * 削除する
	 * 
	 * @throws TException
	 */
	public void delete() throws TException {

		Connection conn = DBUtil.getConnection();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("DELETE FROM CM_VESSEL_CO_MST ");
			sql.add("WHERE KAI_CODE = ? ", getCompanyCode());

			DBUtil.execute(conn, sql);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * 内航外航区分(エクセル)を返す
	 * 
	 * @return 対象データリスト
	 * @throws TException
	 */
	public byte[] getExcel() throws TException {

		try {
			List<VesselCO> list = get(true);

			if (list == null || list.isEmpty()) {
				return null;
			}

			VesselCOExcel exl = new VesselCOExcel(getUser().getLanguage());

			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return bean
	 * @throws Exception
	 */
	protected VesselCO mapping(ResultSet rs) throws Exception {

		VesselCO bean = new VesselCO();
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setVesselCode(rs.getString("VESSEL_CODE"));
		bean.setVesselNames(rs.getString("VESSEL_NAME_S"));
		bean.setCOKbn(Util.avoidNull(rs.getString("CO_KBN")));

		return bean;

	}
}
