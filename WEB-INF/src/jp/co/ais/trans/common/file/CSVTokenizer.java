package jp.co.ais.trans.common.file;

/*
 * このクラスは下記の安藤さんの作成されたものです ========================================== 安藤友晴 稚内北星学園短期大学図書館 tomoharu@wakhok.ac.jp
 * http://www.wakhok.ac.jp/~tomoharu/ ==========================================
 */

import java.util.Enumeration;
import java.util.NoSuchElementException;

/**
 * 1行のCSV形式のデータを解析し、それぞれの項目に分解するクラス。 CSV形式に対応した java.util.StringTokenizer のようなもの。
 * 
 * @version 1.0.1 (1999.4.6)
 * @author TAMURA Kent <kent@muraoka.info.waseda.ac.jp>
 * @author ANDOH Tomoharu <tomoharu@wakhok.ac.jp>
 */

public class CSVTokenizer implements Enumeration {

	/** COPYRIGHT */
	public static final String copyright = "Copyright 1997 TAMURA Kent" + "\n" + "Copyright 1999 ANDOH Tomoharu";

	private String source; // 対象となる文字列

	private int currentPosition; // 次の読み出し位置

	private int maxPosition;

	/**
	 * CSV 形式の line を解析する CSVTokenizer のインスタンスを 作成する。
	 * 
	 * @param line CSV形式の文字列 改行コードを含まない。
	 */
	public CSVTokenizer(String line) {
		source = line;
		currentPosition = 0;
		maxPosition = line.length();
	}

	/**
	 * 次のカンマがある位置を返す。 カンマが残っていない場合は nextComma() == maxPosition となる。 また最後の項目が空の場合も nextComma() == maxPosition となる。
	 * 
	 * @param ind 検索を開始する位置
	 * @return 次のカンマがある位置。カンマがない場合は、文字列の 長さの値となる。
	 */
	private int nextComma(int ind) {
		boolean inquote = false;
		while (ind < maxPosition) {
			char ch = source.charAt(ind);
			if (!inquote && ch == ',') {
				break;
			} else if ('"' == ch) {
				inquote = !inquote; // ""の処理もこれでOK
			}
			ind++;
		}
		return ind;
	}

	/**
	 * 含まれている項目の数を返す。
	 * 
	 * @return 含まれている項目の数
	 */
	public int countTokens() {
		int i = 0;
		int ret = 1;
		while ((i = nextComma(i)) < maxPosition) {
			i++;
			ret++;
		}
		return ret;
	}

	/**
	 * 次の項目の文字列を返す。
	 * 
	 * @return 次の項目
	 * @exception NoSuchElementException 項目が残っていないとき
	 */
	public String nextToken() {
		// ">=" では末尾の項目を正しく処理できない。
		// 末尾の項目が空（カンマで1行が終わる）場合、例外が発生して
		// しまうので。
		if (currentPosition > maxPosition) throw new NoSuchElementException(toString() + "#nextToken");

		int st = currentPosition;
		currentPosition = nextComma(currentPosition);

		StringBuffer strb = new StringBuffer();
		while (st < currentPosition) {
			char ch = source.charAt(st++);
			if (ch == '"') {
				// "が単独で現れたときは何もしない
				if ((st < currentPosition) && (source.charAt(st) == '"')) {
					strb.append(ch);
					st++;
				}
			} else {
				strb.append(ch);
			}
		}
		currentPosition++;
		return new String(strb);
	}

	/**
	 * <code>nextToken</code>メソッドと同じで、 次の項目の文字列を返す。<br>
	 * ただし返値は、String型ではなく、Object型である。<br>
	 * java.util.Enumerationを実装しているため、このメソッドが ある。
	 * 
	 * @return 次の項目
	 * @exception NoSuchElementException 項目が残っていないとき
	 * @see java.util.Enumeration
	 */
	public Object nextElement() {
		return nextToken();
	}

	/**
	 * まだ項目が残っているかどうか調べる。
	 * 
	 * @return まだ項目がのこっているならtrue
	 */
	public boolean hasMoreTokens() {
		// "<=" でなく、"<" だと末尾の項目を正しく処理できない。
		return (nextComma(currentPosition) <= maxPosition);
	}

	/**
	 * <code>hasMoreTokens</code>メソッドと同じで、 まだ項目が残っているかどうか調べる。<br>
	 * java.util.Enumerationを実装しているため、このメソッドが ある。
	 * 
	 * @return まだ項目がのこっているならtrue
	 * @see java.util.Enumeration
	 */
	public boolean hasMoreElements() {
		return hasMoreTokens();
	}

	/**
	 * インスタンスの文字列表現を返す。
	 * 
	 * @return インスタンスの文字列表現。
	 */
	public String toString() {
		return "CSVTokenizer(\"" + source + "\")";
	}
}
