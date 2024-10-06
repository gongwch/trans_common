package jp.co.ais.trans.common.file;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * CSVファイル読込のためのクラス <br>
 * BufferedReaderに、Fileから直にインスタンスを作るコンストラクタTCsvReader(File)と 読込method readLineToColumnList()を追加したクラスです。 <br>
 * 古いバージョン<br>
 * 新しいバージョン:{@link jp.co.ais.trans2.common.file.TCsvFileReader}
 */
public class TCsvReader extends BufferedReader {

	static final String DELIMITER = ",";

	/**
	 * constructor.
	 * 
	 * @param in ファイルリーダ
	 */
	public TCsvReader(Reader in) {
		super(in);
	}

	/**
	 * constructor. <br>
	 * 通常、このコンストラクタを使う。
	 * 
	 * @param fi CSVファイル
	 * @throws FileNotFoundException
	 */
	public TCsvReader(File fi) throws FileNotFoundException {

		super(new FileReader(fi));
	}

	/**
	 * 読込んだ行をカンマ(,)区切りのカラムと判断し、リストにして返す
	 * 
	 * @return 行リスト
	 * @throws IOException
	 */
	public List<String> readLineToColumnList() throws IOException {

		List<String> list = null;

		// １行読込
		String lin = this.readLine();

		if (lin == null) {
			return null;
		}

		// リスト生成
		list = new LinkedList<String>();

		Enumeration column = new CSVTokenizer(lin);

		while (column.hasMoreElements()) {
			list.add((String) column.nextElement());
		}

		return list;
	}

	/**
	 * TCsvReaderをclose()する
	 * 
	 * @param in TCsvReader
	 */
	public static void close(TCsvReader in) {
		try {
			if (in != null) {
				in.close();
			}
		} catch (IOException ex) {
			SystemErrorHandler.handledException(ex);
		}
	}
}
