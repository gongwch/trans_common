package jp.co.ais.trans2.model.exclusive;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * �r�����׈ꗗ�C���^�[�t�F�[�X����
 * 
 * @author AIS
 */
public abstract class ExclusiveDetailManagerImpl extends TModel implements ExclusiveDetailManager {

	public List<ExclusiveDetail> get() throws TException {

		Connection conn = DBUtil.getConnection();
		List<ExclusiveDetail> list = new ArrayList<ExclusiveDetail>();

		try {

			SQLCreator sql = new SQLCreator();

			sql.add("  SELECT        ");
			sql.add("  hai.KAI_CODE  ");
			sql.add(" ,hai.SHORI_KBN ");
			sql.add(" ,hai.TRI_CODE  "); // �r���L�[
			sql.add(" ,hai.GYO_NO    ");
			sql.add(" ,hai.INP_DATE  ");
			sql.add(" ,hai.PRG_ID    ");
			sql.add(" ,prg.PRG_NAME  ");
			sql.add(" ,hai.USR_ID    ");
			sql.add(" ,usr.USR_NAME  ");
			sql.add("  FROM          ");
			sql.add("  HAITA_CTL hai ");
			sql.add("  LEFT OUTER JOIN PRG_MST prg    ");
			sql.add("  ON hai.KAI_CODE = prg.KAI_CODE ");
			sql.add("  AND hai.PRG_ID = prg.PRG_CODE  ");
			sql.add("  LEFT OUTER JOIN USR_MST usr    ");
			sql.add("  ON hai.KAI_CODE = usr.KAI_CODE ");
			sql.add("  AND hai.USR_ID = usr.USR_CODE  ");
			sql.add("  WHERE 1 = 1   ");
			sql.add("  AND hai.KAI_CODE = ?", getCompanyCode());

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
	 * O/R�}�b�s���O
	 * 
	 * @param rs
	 * @return �G���e�B�e�B
	 * @throws Exception
	 */
	protected ExclusiveDetail mapping(ResultSet rs) throws Exception {

		ExclusiveDetail bean = new ExclusiveDetail();

		bean.setKAI_CODE(rs.getString("KAI_CODE"));
		bean.setSHORI_KBN(rs.getString("SHORI_KBN"));
		bean.setHAITA_KEY(rs.getString("TRI_CODE"));
		bean.setGYO_NO(rs.getString("GYO_NO"));
		bean.setINP_DATE(rs.getTimestamp("INP_DATE"));
		bean.setPRG_ID(rs.getString("PRG_ID"));
		bean.setPRG_NAME(rs.getString("PRG_NAME"));
		bean.setUSR_ID(rs.getString("USR_ID"));
		bean.setUSR_NAME(rs.getString("USR_NAME"));

		return bean;

	}

	/**
	 * �G�N�Z���擾
	 * 
	 * @return byte ��������
	 * @throws TException
	 */
	public byte[] getExcel() throws TException {

		try {

			List<ExclusiveDetail> list = get();

			if (list == null || list.isEmpty()) {
				return null;
			}

			ExclusiveDetailExcel exl = new ExclusiveDetailExcel(getUser().getLanguage());

			return exl.getExcel(list);

		} catch (Exception e) {
			throw new TException(e);
		}
	}

}
