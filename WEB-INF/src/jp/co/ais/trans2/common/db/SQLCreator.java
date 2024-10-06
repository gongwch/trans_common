package jp.co.ais.trans2.common.db;

import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * SQL構築
 */
public class SQLCreator {

	/** */
	protected StringBuilder sql = new StringBuilder();

	/** CR LF */
	protected String crlf = "\n";

	/**
	 * コンストラクター
	 */
	public SQLCreator() {
		// 処理なし
	}

	/**
	 * 追加
	 * 
	 * @param value 追加文
	 * @return SQLCreator
	 */
	public SQLCreator add(SQLCreator value) {
		sql.append(value.toSQL());
		return this;
	}

	/**
	 * 追加
	 * 
	 * @param value 追加文
	 * @return SQLCreator
	 */
	public SQLCreator add(String value) {
		sql.append(value).append(crlf);
		return this;
	}

	/**
	 * 追加
	 * 
	 * @param value 追加文
	 * @param arg バインド値
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
	 * 追加
	 * 
	 * @param value 追加文
	 * @param arg バインド値
	 * @return SQLCreator
	 */
	public SQLCreator add(String value, int arg) {
		sql.append(value.replace("?", String.valueOf(arg))).append(crlf);
		return this;
	}

	/**
	 * 追加
	 * 
	 * @param value 追加文
	 * @param arg バインド値
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
	 * 追加(YYYY/MM/DD)
	 * 
	 * @param value 追加文
	 * @param date バインド値
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
	 * 追加
	 * 
	 * @param value 追加文
	 * @param date バインド値
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
	 * 追加(YYYY/MM)
	 * 
	 * @param value 追加文
	 * @param date バインド値
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
	 * 追加(YYYY/MM/DD HH24:MI:SS)
	 * 
	 * @param value 追加文
	 * @param date バインド値
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
	 * YMDHMSまで日付追加
	 * 
	 * @param value
	 * @param date
	 * @return SQLCreator
	 */
	public SQLCreator adt(String value, Date date) {
		return addYMDHMS(value, date);
	}

	/**
	 * 追加(YYYY/MM/DD HH24:MI:SS.SSS)
	 * 
	 * @param value 追加文
	 * @param date バインド値
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
	 * 追加
	 * 
	 * @param value 追加文
	 * @param list バインド値
	 * @return SQLCreator
	 */
	public SQLCreator add(String value, List<String> list) {
		sql.append(value.replace("?", SQLUtil.getInStatement(list))).append(crlf);
		return this;
	}

	/**
	 * 追加
	 * 
	 * @param value 追加文
	 * @param list バインド値
	 * @return SQLCreator
	 */
	public SQLCreator adi(String value, List<Integer> list) {
		sql.append(value.replace("?", SQLUtil.getIntInStatement(list))).append(crlf);
		return this;
	}

	/**
	 * 追加
	 * 
	 * @param value 追加文
	 * @param list バインド値
	 * @return SQLCreator
	 */
	public SQLCreator add(String value, String[] list) {
		sql.append(value.replace("?", SQLUtil.getInStatement(list))).append(crlf);
		return this;
	}

	/**
	 * 追加
	 * 
	 * @param value 追加文
	 * @param arg バインド値
	 * @return SQLCreator
	 */
	public SQLCreator addLikeFront(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeStatement(arg, SQLUtil.CHAR_FRONT))).append(crlf);
		return this;
	}

	/**
	 * 追加
	 * 
	 * @param value 追加文
	 * @param arg バインド値
	 * @return SQLCreator
	 */
	public SQLCreator addNLikeFront(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeStatement(arg, SQLUtil.NCHAR_FRONT))).append(crlf);
		return this;
	}

	/**
	 * 追加 後方一致LIKE文
	 * 
	 * @param value 追加文
	 * @param arg バインド値
	 * @return SQLCreator
	 */
	public SQLCreator addLikeBack(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeStatement(arg, SQLUtil.CHAR_BACK))).append(crlf);
		return this;
	}

	/**
	 * 追加 後方一致LIKE文(NVARCHAR2)
	 * 
	 * @param value 追加文
	 * @param arg バインド値
	 * @return SQLCreator
	 */
	public SQLCreator addNLikeBack(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeStatement(arg, SQLUtil.NCHAR_BACK))).append(crlf);
		return this;
	}

	/**
	 * 追加
	 * 
	 * @param value 追加文
	 * @param arg バインド値
	 * @return SQLCreator
	 */
	public SQLCreator addLikeAmbi(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeStatement(arg, SQLUtil.CHAR_AMBI))).append(crlf);
		return this;
	}

	/**
	 * 追加
	 * 
	 * @param value 追加文
	 * @param arg バインド値
	 * @return SQLCreator
	 */
	public SQLCreator addNLikeAmbi(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeStatement(arg, SQLUtil.NCHAR_AMBI))).append(crlf);
		return this;
	}

	/**
	 * StringのリストをSQLのIN句にして返す。
	 * 
	 * @param param Stringリスト
	 * @return StringのリストをSQLのIN句にした文字列
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
	 * Stringの配列をSQLのIN句にして返す。
	 * 
	 * @param param String配列
	 * @return Stringの配列をSQLのIN句にした文字列
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
	 * intのListをSQLのIN句にして返す。
	 * 
	 * @param param intのリスト
	 * @return intのListをSQLのIN句にした文字列
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
	 * intの配列をSQLのIN句にして返す。
	 * 
	 * @param param int配列
	 * @return intの配列をSQLのIN句にした文字列
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
	 * 追加(小文字→大文字)
	 * 
	 * @param value 追加文
	 * @param arg バインド値
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
	 * 追加
	 * 
	 * @param value 追加文
	 * @param arg バインド値
	 * @return SQLCreator
	 */
	public SQLCreator addLikeUpperFront(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeUpperStatement(arg, SQLUtil.CHAR_FRONT))).append(crlf);
		return this;
	}

	/**
	 * 追加
	 * 
	 * @param value 追加文
	 * @param arg バインド値
	 * @return SQLCreator
	 */
	public SQLCreator addNLikeUpperFront(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeUpperStatement(arg, SQLUtil.NCHAR_FRONT))).append(crlf);
		return this;
	}

	/**
	 * 追加 後方一致LIKE文(NVARCHAR2)(小文字→大文字)
	 * 
	 * @param value 追加文
	 * @param arg バインド値
	 * @return SQLCreator
	 */
	public SQLCreator addLikeUpperBack(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeUpperStatement(arg, SQLUtil.CHAR_BACK))).append(crlf);
		return this;
	}

	/**
	 * 追加 後方一致LIKE文(NVARCHAR2)(小文字→大文字)
	 * 
	 * @param value 追加文
	 * @param arg バインド値
	 * @return SQLCreator
	 */
	public SQLCreator addNLikeUpperBack(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeUpperStatement(arg, SQLUtil.NCHAR_BACK))).append(crlf);
		return this;
	}

	/**
	 * 追加
	 * 
	 * @param value 追加文
	 * @param arg バインド値
	 * @return SQLCreator
	 */
	public SQLCreator addLikeUpperAmbi(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeUpperStatement(arg, SQLUtil.CHAR_AMBI))).append(crlf);
		return this;
	}

	/**
	 * 追加
	 * 
	 * @param value 追加文
	 * @param arg バインド値
	 * @return SQLCreator
	 */
	public SQLCreator addNLikeUpperAmbi(String value, String arg) {
		sql.append(value.replace("?", SQLUtil.getLikeUpperStatement(arg, SQLUtil.NCHAR_AMBI))).append(crlf);
		return this;
	}

	/**
	 * SQL変換
	 * 
	 * @return SQL
	 */
	public String toSQL() {
		return sql.toString();
	}

	/**
	 * SQLクリア
	 */
	public void clear() {
		sql = new StringBuilder();
	}

	/**
	 * SQL表現で返す
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return toSQL();
	}
}
