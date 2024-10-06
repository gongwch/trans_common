package jp.co.ais.trans2.common.db;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * 
 */
public class SQLUtil {

	/** 1:Varchar2% */
	public static final int CHAR_FRONT = 1;

	/** 1:%Varchar2% */
	public static final int CHAR_AMBI = 4;

	/** 2:nVarchar2% */
	public static final int NCHAR_FRONT = 2;

	/** 3:%nVarchar2% */
	public static final int NCHAR_AMBI = 3;

	/** 5:%Varchar2 */
	public static final int CHAR_BACK = 5;

	/** 6:%nVarchar2 */
	public static final int NCHAR_BACK = 6;

	/**
	 * Refに関連する曖昧検索は、「%」を検索文字の一部として認識させる
	 * 
	 * @param str1 検索文字
	 * @param type 0: Code検索% 1:%Name検索% 2:Name検索%
	 * @return 検索SQL
	 */
	public static String getLikeStatement(String str1, int type) {
		String result = "";
		if (Util.isNullOrEmpty(str1)) {
			return result;
		}

		String str = StringUtil.convertPrm(str1);

		// TODO LIKE文は設定値が存在しなくても条件に追加されるべきか。
		try {

			if (!DBUtil.isUseMySQL) {

				switch (type) {

					case CHAR_FRONT:
						result = " LIKE '" + str + "%'" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case CHAR_AMBI:
						result = " LIKE '%" + str + "%'" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case CHAR_BACK:
						result = " LIKE '%" + str + "'" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case NCHAR_FRONT:
						result = " LIKEC '" + str + "%'" + " ESCAPE N'" + Util.ESCAPE_STRING + "'";
						break;

					case NCHAR_AMBI:
						result = " LIKEC '%" + str + "%'" + " ESCAPE N'" + Util.ESCAPE_STRING + "'";
						break;

					case NCHAR_BACK:
						result = " LIKEC '%" + str + "'" + " ESCAPE N'" + Util.ESCAPE_STRING + "'";
						break;

					default:
						throw new TRuntimeException("column type not found.");
				}

			} else {
				// MySQL
				switch (type) {

					case CHAR_FRONT:
						result = " LIKE '" + str + "%'" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case CHAR_AMBI:
						result = " LIKE '%" + str + "%'" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case CHAR_BACK:
						result = " LIKE '%" + str + "'" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case NCHAR_FRONT:
						result = " LIKE '" + str + "%'" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case NCHAR_AMBI:
						result = " LIKE '%" + str + "%'" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case NCHAR_BACK:
						result = " LIKE '%" + str + "'" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					default:
						throw new TRuntimeException("column type not found.");
				}
			}

			return result;

		} catch (Exception ex) {
			throw new TRuntimeException(ex, "E00009");
		}

	}

	/**
	 * SQLパラメータ生成
	 * 
	 * @param param
	 * @return パラメータ
	 */
	public static String getParam(String param) {
		if (param == null) {
			return "NULL";
		}
		return "'" + param.replace("'", "''") + "'";
	}

	/**
	 * SQLパラメータ生成
	 * 
	 * @param param
	 * @return パラメータ
	 */
	public static String getParam(BigDecimal param) {
		if (param == null) {
			return "NULL";
		}
		return param.toString();
	}

	/**
	 * SQLパラメータ生成
	 * 
	 * @param param
	 * @return パラメータ
	 */
	public static String getYYYYMMDDParam(String param) {
		if (param == null) {
			return "NULL";
		}
		return getParam(param);
	}

	/**
	 * SQLパラメータ生成
	 * 
	 * @param param
	 * @return パラメータ
	 */
	public static String getYYYYMMDDParam(Date param) {
		if (param == null) {
			return "NULL";
		}
		return getParam(DateUtil.toYMDString(param));
	}

	/**
	 * SQLパラメータ生成
	 * 
	 * @param param
	 * @return パラメータ
	 */
	public static String getYMDParam(Date param) {
		if (param == null) {
			return "NULL";
		}
		return " TO_DATE(" + getParam(DateUtil.toYMDString(param)) + ",'YYYY/MM/DD') ";
	}

	/**
	 * SQLパラメータ生成
	 * 
	 * @param param
	 * @return パラメータ
	 */
	public static String getYMDHMSParam(Date param) {
		if (param == null) {
			return "NULL";
		}
		return " TO_DATE(" + getParam(DateUtil.toYMDHMSString(param)) + ",'YYYY/MM/DD HH24:MI:SS') ";
	}

	/**
	 * SQLパラメータ生成
	 * 
	 * @param param
	 * @return パラメータ
	 */
	public static String getYMDHMSParam(String param) {
		if (param == null) {
			return "NULL";
		}
		return " TO_DATE(" + getParam(param) + ",'YYYY/MM/DD HH24:MI:SS') ";
	}

	/**
	 * SQLパラメータ生成
	 * 
	 * @param param
	 * @return パラメータ
	 */
	public static String getYMDHMSSSSParam(Date param) {
		if (param == null) {
			return "NULL";
		}
		return " TO_TIMESTAMP(" + getParam(DateUtil.toYMDHMSSSSString(param)) + ",'YYYY/MM/DD HH24:MI:SSxFF3') ";
	}

	/**
	 * SQLパラメータ生成
	 * 
	 * @param param
	 * @return パラメータ
	 */
	public static String getYMDHMSSSSParam(String param) {
		if (param == null) {
			return "NULL";
		}
		return " TO_TIMESTAMP(" + getParam(param) + ",'YYYY/MM/DD HH24:MI:SSxFF3') ";
	}

	/**
	 * StringのListをSQLのIN句にして返す。
	 * 
	 * @param param Stringリスト
	 * @return StringのListをSQLのIN句にした文字列
	 */
	public static String getInStatement(List<String> param) {

		String sql = "(";

		for (int i = 0; i < param.size(); i++) {
			if (i != 0) {
				sql = sql + ", ";
			}
			sql = sql + getParam(param.get(i));
		}

		sql = sql + ")";

		return sql;
	}

	/**
	 * StringのリストをSQLのIN句にして返す。
	 * 
	 * @param param Stringリスト
	 * @return StringのリストをSQLのIN句にした文字列
	 */
	public static String getInStatement(Set<String> param) {
		String sql = "";

		for (String str : param) {
			if (sql.length() != 0) {
				sql += ", ";
			}
			sql += getParam(str);
		}

		return "(" + sql + ")";
	}

	/**
	 * Stringの配列をSQLのIN句にして返す。
	 * 
	 * @param param String配列
	 * @return Stringの配列をSQLのIN句にした文字列
	 */
	public static String getInStatement(String[] param) {

		String sql = "(";

		for (int i = 0; i < param.length; i++) {
			if (i != 0) {
				sql = sql + ", ";
			}
			sql = sql + getParam(param[i]);
		}

		sql = sql + ")";

		return sql;
	}

	/**
	 * intのListをSQLのIN句にして返す。
	 * 
	 * @param param intのリスト
	 * @return intのListをSQLのIN句にした文字列
	 */
	public static String getIntInStatement(List<Integer> param) {

		String sql = "(";

		for (int i = 0; i < param.size(); i++) {
			if (i != 0) {
				sql = sql + ", ";
			}
			sql = sql + Integer.toString(param.get(i));
		}

		sql = sql + ")";

		return sql;
	}

	/**
	 * intの配列をSQLのIN句にして返す。
	 * 
	 * @param param int配列
	 * @return intの配列をSQLのIN句にした文字列
	 */
	public static String getIntInStatement(int[] param) {

		String sql = "(";

		for (int i = 0; i < param.length; i++) {
			if (i != 0) {
				sql = sql + ", ";
			}
			sql = sql + Integer.toString(param[i]);
		}

		sql = sql + ")";

		return sql;
	}

	/**
	 * Refに関連する曖昧検索時、「%」を検索文字の一部として認識させる（小文字を大文字に変換する）
	 * 
	 * @param str1 検索文字
	 * @param type 0: Code検索% 1:%Name検索% 2:Name検索%
	 * @return 検索SQL
	 */
	public static String getLikeUpperStatement(String str1, int type) {
		String result = "";
		if (Util.isNullOrEmpty(str1)) {
			return result;
		}

		String str = StringUtil.convertPrm(str1);

		// TODO LIKE文は設定値が存在しなくても条件に追加されるべきか。
		try {

			if (!DBUtil.isUseMySQL) {

				switch (type) {

					case CHAR_FRONT:
						result = " LIKE UPPER('" + str + "%')" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case CHAR_AMBI:
						result = " LIKE UPPER('%" + str + "%')" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case CHAR_BACK:
						result = " LIKE UPPER('%" + str + "')" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case NCHAR_FRONT:
						result = " LIKEC UPPER('" + str + "%')" + " ESCAPE N'" + Util.ESCAPE_STRING + "'";
						break;

					case NCHAR_AMBI:
						result = " LIKEC UPPER('%" + str + "%')" + " ESCAPE N'" + Util.ESCAPE_STRING + "'";
						break;

					case NCHAR_BACK:
						result = " LIKEC UPPER('%" + str + "')" + " ESCAPE N'" + Util.ESCAPE_STRING + "'";
						break;

					default:
						throw new TRuntimeException("column type not found.");
				}

			} else {
				// MySQL
				switch (type) {

					case CHAR_FRONT:
						result = " LIKE UPPER('" + str + "%')" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case CHAR_AMBI:
						result = " LIKE UPPER('%" + str + "%')" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case CHAR_BACK:
						result = " LIKE UPPER('%" + str + "')" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case NCHAR_FRONT:
						result = " LIKE UPPER('" + str + "%')" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case NCHAR_AMBI:
						result = " LIKE UPPER('%" + str + "%')" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					case NCHAR_BACK:
						result = " LIKE UPPER('%" + str + "')" + " ESCAPE '" + Util.ESCAPE_STRING + "'";
						break;

					default:
						throw new TRuntimeException("column type not found.");
				}
			}

			return result;

		} catch (Exception ex) {
			throw new TRuntimeException(ex, "E00009");
		}

	}

	/**
	 * SQLパラメータ生成(小文字→大文字変換)
	 * 
	 * @param param
	 * @return パラメータ
	 */
	public static String getUpperParam(String param) {
		if (param == null) {
			return "NULL";
		}
		return "UPPER('" + param.replace("'", "''") + "')";
	}

}
