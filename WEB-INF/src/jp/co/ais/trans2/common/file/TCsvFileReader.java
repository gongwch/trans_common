package jp.co.ais.trans2.common.file;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * CSVファイル読込のためのクラス <br>
 * BufferedReaderに、Fileから直にインスタンスを作るコンストラクタTCsvReader(File)と 読込method readLineToColumnList()を追加したクラスです。
 */

public class TCsvFileReader extends BufferedReader {

	/** DELIMITER */
	static final String DELIMITER = ",";

	/**
	 * constructor.
	 * 
	 * @param in ファイルリーダ
	 */
	public TCsvFileReader(Reader in) {
		super(in);
	}

	/**
	 * constructor. <br>
	 * 通常、このコンストラクタを使う。
	 * 
	 * @param fi CSVファイル
	 * @throws IOException 
	 */
	public TCsvFileReader(File fi) throws IOException {
		super(new InputStreamReader(new FileInputStream(fi), FileCharsetDetector.getCharset(fi)));
	}

	/**
	 * 読込んだ行をカンマ(,)区切りのカラムと判断し、リストにして返す
	 * 
	 * @return 行リスト
	 * @throws IOException
	 */
	public List<String> readLineToColumnList() throws IOException {

		// １行読込
		String lin = this.readLine();

		if (lin == null) {
			return null;
		}

		List<String> list = TCsvTokenizer.readLine(lin);

		return list;
	}

	/**
	 * TCsvReaderをclose()する
	 * 
	 * @param in TCsvReader
	 */
	public static void close(TCsvFileReader in) {
		try {
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			SystemErrorHandler.handledException(ex);
		}
	}
}
