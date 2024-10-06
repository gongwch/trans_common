package jp.co.ais.trans2.common.db;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * SQL�\�z
 */
public class SQLCreator {

	/** */
	protected StringBuilder sql = new StringBuilder();

	/** CR LF */
	protected String crlf = "\n";

	/**
	 * �R���X�g���N�^�[
	 */
	public SQLCreator() {
		// �����Ȃ�
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @return SQLCreator
	 */
	public SQLCreator add(SQLCreator value) {
		sql.append(value.toSQL());
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @return SQLCreator
	 */
	public SQLCreator add(String value) {
		sql.append(value).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator add(String value, String arg) {
		if (Util.isNullOrEmpty(arg)) {
			sql.append(value.replace("?", "NULL")).append(crlf);
			return this;
		}

		sql.append(value.replace("?", SQLUtil.getParam(arg))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator add(String value, int arg) {
		sql.append(value.replace("?", String.valueOf(arg))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator add(String value, BigDecimal arg) {
		if (arg == null) {
			sql.append(value.replace("?", "NULL")).append(crlf);
			return this;
		}

		sql.append(value.replace("?", arg.toPlainString())).append(crlf);
		return this;
	}

	/**
	 * �ǉ�(YYYY/MM/DD)
	 * 
	 * @param value �ǉ���
	 * @param date �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator add(String value, Date date) {
		if (date == null) {
			sql.append(value.replace("?", "NULL")).append(crlf);
			return this;
		}

		sql.append(value.replace("?", SQLUtil.getParam(DateUtil.toYMDString(date)))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param date �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addToDateParam(String value, Date date) {
		if (date == null) {
			sql.append(value.replace("?", "NULL")).append(crlf);
			return this;
		}

		sql.append(value.replace("?", SQLUtil.getYMDParam(date))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�(YYYY/MM)
	 * 
	 * @param value �ǉ���
	 * @param date �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addYM(String value, Date date) {
		if (date == null) {
			sql.append(value.replace("?", "NULL")).append(crlf);
			return this;
		}

		sql.append(value.replace("?", SQLUtil.getParam(DateUtil.toYMString(date)))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�(YYYY/MM/DD HH24:MI:SS)
	 * 
	 * @param value �ǉ���
	 * @param date �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addYMDHMS(String value, Date date) {
		if (date == null) {
			sql.append(value.replace("?", "NULL")).append(crlf);
			return this;
		}

		sql.append(value.replace("?", SQLUtil.getYMDHMSParam(date))).append(crlf);
		return this;
	}

	/**
	 * YMDHMS�܂œ��t�ǉ�
	 * 
	 * @param value
	 * @param date
	 * @return SQLCreator
	 */
	public SQLCreator adt(String value, Date date) {
		return addYMDHMS(value, date);
	}

	/**
	 * �ǉ�(YYYY/MM/DD HH24:MI:SS.SSS)
	 * 
	 * @param value �ǉ���
	 * @param date �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addYMDHMSSSS(String value, Date date) {
		if (date == null) {
			sql.append(value.replace("?", "NULL")).append(crlf);
			return this;
		}

		sql.append(value.replace("?", SQLUtil.getYMDHMSSSSParam(date))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param list �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator add(String value, List<String> list) {
		sql.append(value.replace("?", SQLUtil.getInStatement(list))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param list �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator adi(String value, List<Integer> list) {
		sql.append(value.replace("?", SQLUtil.getIntInStatement(list))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param list �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator add(String value, String[] list) {
		sql.append(value.replace("?", SQLUtil.getInStatement(list))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addLikeFront(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeStatement(arg, SQLUtil.CHAR_FRONT))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addNLikeFront(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeStatement(arg, SQLUtil.NCHAR_FRONT))).append(crlf);
		return this;
	}

	/**
	 * �ǉ� �����vLIKE��
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addLikeBack(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeStatement(arg, SQLUtil.CHAR_BACK))).append(crlf);
		return this;
	}

	/**
	 * �ǉ� �����vLIKE��(NVARCHAR2)
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addNLikeBack(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeStatement(arg, SQLUtil.NCHAR_BACK))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addLikeAmbi(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeStatement(arg, SQLUtil.CHAR_AMBI))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addNLikeAmbi(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeStatement(arg, SQLUtil.NCHAR_AMBI))).append(crlf);
		return this;
	}

	/**
	 * String�̃��X�g��SQL��IN��ɂ��ĕԂ��B
	 * 
	 * @param param String���X�g
	 * @return String�̃��X�g��SQL��IN��ɂ���������
	 */
	public SQLCreator addInStatement(List<String> param) {

		sql.append("(");

		for (int i = 0; i < param.size(); i++) {
			if (i != 0) {
				sql.append(", ");
			}
			sql.append("'");
			sql.append(param.get(i));
			sql.append("'");
		}

		sql.append(")");

		return this;

	}

	/**
	 * String�̔z���SQL��IN��ɂ��ĕԂ��B
	 * 
	 * @param param String�z��
	 * @return String�̔z���SQL��IN��ɂ���������
	 */
	public SQLCreator addInStatement(String[] param) {

		sql.append("(");

		for (int i = 0; i < param.length; i++) {
			if (i != 0) {
				sql.append(", ");
			}
			sql.append("'");
			sql.append(param[i]);
			sql.append("'");
		}

		sql.append(")");

		return this;

	}

	/**
	 * int��List��SQL��IN��ɂ��ĕԂ��B
	 * 
	 * @param param int�̃��X�g
	 * @return int��List��SQL��IN��ɂ���������
	 */
	public SQLCreator addIntInStatement(List<Integer> param) {

		sql.append("(");

		for (int i = 0; i < param.size(); i++) {
			if (i != 0) {
				sql.append(", ");
			}
			sql.append(Integer.toString(param.get(i)));
		}

		sql.append(")");

		return this;
	}

	/**
	 * int�̔z���SQL��IN��ɂ��ĕԂ��B
	 * 
	 * @param param int�z��
	 * @return int�̔z���SQL��IN��ɂ���������
	 */
	public SQLCreator addIntInStatement(int[] param) {

		sql.append("(");

		for (int i = 0; i < param.length; i++) {
			if (i != 0) {
				sql.append(", ");
			}
			sql.append(Integer.toString(param[i]));
		}

		sql.append(")");

		return this;
	}

	/**
	 * �ǉ�(���������啶��)
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addUpper(String value, String arg) {
		if (Util.isNullOrEmpty(arg)) {
			sql.append(value.replace("?", "NULL")).append(crlf);
			return this;
		}

		sql.append(value.replace("?", SQLUtil.getUpperParam(arg))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addLikeUpperFront(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeUpperStatement(arg, SQLUtil.CHAR_FRONT))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addNLikeUpperFront(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeUpperStatement(arg, SQLUtil.NCHAR_FRONT))).append(crlf);
		return this;
	}

	/**
	 * �ǉ� �����vLIKE��(NVARCHAR2)(���������啶��)
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addLikeUpperBack(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeUpperStatement(arg, SQLUtil.CHAR_BACK))).append(crlf);
		return this;
	}

	/**
	 * �ǉ� �����vLIKE��(NVARCHAR2)(���������啶��)
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addNLikeUpperBack(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeUpperStatement(arg, SQLUtil.NCHAR_BACK))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addLikeUpperAmbi(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeUpperStatement(arg, SQLUtil.CHAR_AMBI))).append(crlf);
		return this;
	}

	/**
	 * �ǉ�
	 * 
	 * @param value �ǉ���
	 * @param arg �o�C���h�l
	 * @return SQLCreator
	 */
	public SQLCreator addNLikeUpperAmbi(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeUpperStatement(arg, SQLUtil.NCHAR_AMBI))).append(crlf);
		return this;
	}

	/**
	 * SQL�ϊ�
	 * 
	 * @return SQL
	 */
	public String toSQL() {
		return sql.toString();
	}

	/**
	 * SQL�N���A
	 */
	public void clear() {
		sql = new StringBuilder();
	}

	/**
	 * SQL�\���ŕԂ�
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toSQL();
	}
}
