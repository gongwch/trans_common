package jp.co.ais.trans2.model.xml;

import java.io.*;

import javax.xml.parsers.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * XMLパーサー
 */
public class XMLParser {

	/** ハンドラ */
	protected XMLHandler handler;

	/**
	 * コンストラクタ.
	 */
	public XMLParser() {
		super();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param handler ハンドラ
	 */
	public XMLParser(XMLHandler handler) {
		this.handler = handler;
	}

	/**
	 * ハンドラ
	 * 
	 * @param handler ハンドラ
	 */
	public void setHandler(XMLHandler handler) {
		this.handler = handler;
	}

	/**
	 * 変換
	 * 
	 * @param fileName XMLファイル名
	 * @return 値
	 * @throws TException
	 */
	public Object parse(String fileName) throws TException {

		InputStream stream = null;

		try {

			if (fileName.contains(":")) {
				stream = new FileInputStream(fileName);

			} else {
				stream = ResourceUtil.getResourceAsStream(fileName);
			}

			return parse(stream);

		} catch (FileNotFoundException e) {
			throw new TException(e);
		} finally {
			ResourceUtil.closeInputStream(stream);
		}
	}

	/**
	 * 変換
	 * 
	 * @param stream ストリーム
	 * @return 値
	 * @throws TException
	 */
	public Object parse(InputStream stream) throws TException {
		try {
			// SAXパーサー
			SAXParserFactory spfactory = SAXParserFactory.newInstance();
			SAXParser parser = spfactory.newSAXParser();

			// XMLファイルを指定されたデフォルトハンドラーで処理
			parser.parse(stream, handler);
			return handler.get();

		} catch (Exception e) {
			throw new TException(e);
		}
	}
}
