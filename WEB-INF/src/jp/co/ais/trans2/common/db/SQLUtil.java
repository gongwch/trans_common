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
	 * Ref�Ɋ֘A����B�������́A�u%�v�����������̈ꕔ�Ƃ��ĔF��������
	 * 
	 * @param str1 ��������
	 * @param type 0: Code����% 1:%Name����% 2:Name����%
	 * @return ����SQL
	 */
	public static String getLikeStatement(String str1, int type) {
		String result = "";
		if (Util.isNullOrEmpty(str1)) {
			return result;
		}

		String str = StringUtil.convertPrm(str1);

		// TODO LIKE���͐ݒ�l�����݂��Ȃ��Ă������ɒǉ������ׂ����B
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
	 * SQL�p�����[�^����
	 * 
	 * @param param
	 * @return �p�����[�^
	 */
	public static String getParam(String param) {
		if (param == null) {
			return "NULL";
		}
		return "'" + param.replace("'", "''") + "'";
	}

	/**
	 * SQL�p�����[�^����
	 * 
	 * @param param
	 * @return �p�����[�^
	 */
	public static String getParam(BigDecimal param) {
		if (param == null) {
			return "NULL";
		}
		return param.toString();
	}

	/**
	 * SQL�p�����[�^����
	 * 
	 * @param param
	 * @return �p�����[�^
	 */
	public static String getYYYYMMDDParam(String param) {
		if (param == null) {
			return "NULL";
		}
		return getParam(param);
	}

	/**
	 * SQL�p�����[�^����
	 * 
	 * @param param
	 * @return �p�����[�^
	 */
	public static String getYYYYMMDDParam(Date param) {
		if (param == null) {
			return "NULL";
		}
		return getParam(DateUtil.toYMDString(param));
	}

	/**
	 * SQL�p�����[�^����
	 * 
	 * @param param
	 * @return �p�����[�^
	 */
	public static String getYMDParam(Date param) {
		if (param == null) {
			return "NULL";
		}
		return " TO_DATE(" + getParam(DateUtil.toYMDString(param)) + ",'YYYY/MM/DD') ";
	}

	/**
	 * SQL�p�����[�^����
	 * 
	 * @param param
	 * @return �p�����[�^
	 */
	public static String getYMDHMSParam(Date param) {
		if (param == null) {
			return "NULL";
		}
		return " TO_DATE(" + getParam(DateUtil.toYMDHMSString(param)) + ",'YYYY/MM/DD HH24:MI:SS') ";
	}

	/**
	 * SQL�p�����[�^����
	 * 
	 * @param param
	 * @return �p�����[�^
	 */
	public static String getYMDHMSParam(String param) {
		if (param == null) {
			return "NULL";
		}
		return " TO_DATE(" + getParam(param) + ",'YYYY/MM/DD HH24:MI:SS') ";
	}

	/**
	 * SQL�p�����[�^����
	 * 
	 * @param param
	 * @return �p�����[�^
	 */
	public static String getYMDHMSSSSParam(Date param) {
		if (param == null) {
			return "NULL";
		}
		return " TO_TIMESTAMP(" + getParam(DateUtil.toYMDHMSSSSString(param)) + ",'YYYY/MM/DD HH24:MI:SSxFF3') ";
	}

	/**
	 * SQL�p�����[�^����
	 * 
	 * @param param
	 * @return �p�����[�^
	 */
	public static String getYMDHMSSSSParam(String param) {
		if (param == null) {
			return "NULL";
		}
		return " TO_TIMESTAMP(" + getParam(param) + ",'YYYY/MM/DD HH24:MI:SSxFF3') ";
	}

	/**
	 * String��List��SQL��IN��ɂ��ĕԂ��B
	 * 
	 * @param param String���X�g
	 * @return String��List��SQL��IN��ɂ���������
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
	 * String�̃��X�g��SQL��IN��ɂ��ĕԂ��B
	 * 
	 * @param param String���X�g
	 * @return String�̃��X�g��SQL��IN��ɂ���������
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
	 * String�̔z���SQL��IN��ɂ��ĕԂ��B
	 * 
	 * @param param String�z��
	 * @return String�̔z���SQL��IN��ɂ���������
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
	 * int��List��SQL��IN��ɂ��ĕԂ��B
	 * 
	 * @param param int�̃��X�g
	 * @return int��List��SQL��IN��ɂ���������
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
	 * int�̔z���SQL��IN��ɂ��ĕԂ��B
	 * 
	 * @param param int�z��
	 * @return int�̔z���SQL��IN��ɂ���������
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
	 * Ref�Ɋ֘A����B���������A�u%�v�����������̈ꕔ�Ƃ��ĔF��������i��������啶���ɕϊ�����j
	 * 
	 * @param str1 ��������
	 * @param type 0: Code����% 1:%Name����% 2:Name����%
	 * @return ����SQL
	 */
	public static String getLikeUpperStatement(String str1, int type) {
		String result = "";
		if (Util.isNullOrEmpty(str1)) {
			return result;
		}

		String str = StringUtil.convertPrm(str1);

		// TODO LIKE���͐ݒ�l�����݂��Ȃ��Ă������ɒǉ������ׂ����B
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
	 * SQL�p�����[�^����(���������啶���ϊ�)
	 * 
	 * @param param
	 * @return �p�����[�^
	 */
	public static String getUpperParam(String param) {
		if (param == null) {
			return "NULL";
		}
		return "UPPER('" + param.replace("'", "''") + "')";
	}

}
