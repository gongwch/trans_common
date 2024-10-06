package jp.co.ais.trans2.model.language;

import java.sql.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.db.*;

/**
 * 言語マネージャの実装クラスです。
 * @author AIS
 *
 */
public class LanguageManagerImpl implements LanguageManager {

	public List<Language> getLanguage(LanguageSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();
		List<Language> list = new ArrayList<Language>();

		try {
			
			String sql = 
				" SELECT " +
					" LNG_CODE, " + 
					" LNG_NAME " + 
				" FROM LNG_MST " +
				" WHERE KAI_CODE = " + SQLUtil.getParam(condition.getCompanyCode()) +
				" ORDER BY " +
					" LNG_CODE ";

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
	 * @param rs
	 * @return bean
	 * @throws Exception
	 */
	protected Language mapping(ResultSet rs) throws Exception {

		Language bean = new Language();
		bean.setCode(rs.getString("LNG_CODE"));
		bean.setName(rs.getString("LNG_NAME"));

		return bean;

	}
 
}
