package jp.co.ais.trans.common.message;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * XMLから指定された言語のメッセージを読み取って、メッセージリストを構築して返す。
 */
public class MessageBuilder {

	/** 改行文字 */
	protected static final String LINE_SEP = System.getProperty("line.separator");

	/**
	 * メッセージリストを構築
	 * 
	 * @param lang 言語コード(ja:日本語など)
	 * @param fileName XMLファイル名
	 * @return メッセージリスト
	 * @throws IOException
	 */
	public static Map buildMessageList(String lang, String fileName) throws IOException {

		try {
			InputStream atream;
			if (fileName.contains(":")) {
				atream = new FileInputStream(fileName);

			} else {
				atream = ResourceUtil.getResourceAsStream(MessageBuilder.class, fileName);
			}

			XMLHandler handler = new XMLHandler(lang);

			// SAXパーサーファクトリを生成
			SAXParserFactory spfactory = SAXParserFactory.newInstance();

			// SAXパーサーを生成
			SAXParser parser = spfactory.newSAXParser();

			// XMLファイルを指定されたデフォルトハンドラーで処理
			parser.parse(atream, handler);

			return handler.getData();

		} catch (SAXException e) {
			ServerErrorHandler.handledException(e);

			throw new IOException(e.getMessage());

		} catch (ParserConfigurationException e) {
			ServerErrorHandler.handledException(e);

			throw new IOException(e.getMessage());
		}
	}

	/**
	 * XMLハンドラ
	 */
	protected static class XMLHandler extends DefaultHandler {

		/** 戻り値 */
		protected Map tm;

		/** 言語コード */
		protected String lang;

		/** True:対象言語に発生したイベントかどうかの判定 */
		protected boolean bln = false;

		/** キー */
		protected String key = "";

		/** 値 */
		protected String value = "";

		/**
		 * コンストラクタ.
		 * 
		 * @param lang 言語コード
		 */
		public XMLHandler(String lang) {
			this.lang = lang;
			this.tm = new TreeMap();
		}

		/**
		 * 要素の開始タグ読み込み時
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) {

			// タグ名が<lang>
			if (qName.equals("lang")) {
				// langの属性codeが指定された言語コードか
				bln = attributes.getValue("code").equals(lang);
			}

			// word属性<word>
			if (bln && qName.equals("word")) {
				key = attributes.getValue("id");
				value = "";
			}
		}

		/**
		 * テキストデータ読み込み時
		 */
		@Override
		public void characters(char[] ch, int offset, int length) {
			if (bln) {
				value += new String(ch, offset, length);
			}
		}

		/**
		 * 要素の終了タグ
		 */
		@Override
		public void endElement(String uri, String localName, String qName) {

			if (bln && qName.equals("word")) {
				if (!(value.trim().equals(""))) {
					// tmに追加
					tm.put(key, value.trim().replaceAll("\\\\n", LINE_SEP));
				}
			}
		}

		/**
		 * 貯めたデータを返す
		 * 
		 * @return データ
		 */
		public Map getData() {
			return tm;
		}
	}
}
