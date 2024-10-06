package jp.co.ais.trans2.model.xml;

import java.io.*;

import javax.xml.parsers.*;
import javax.xml.transform.*;
import javax.xml.transform.dom.*;
import javax.xml.transform.stream.*;

import org.w3c.dom.*;

import com.sun.org.apache.xml.internal.serializer.*;

import jp.co.ais.trans.common.util.*;

/**
 * XML作成
 */
public class XMLBuilder {

	/** XMLドキュメント */
	public Document document = null;

	/** true:XML宣言出力する、false:XML宣言出力しない */
	public boolean xmlTitle = false;

	/** 要素のインデント桁数 */
	public int indent = 2;

	/**
	 * コンストラクター
	 */
	public XMLBuilder() {
		// なし
	}

	/**
	 * コンストラクター
	 * 
	 * @param rootTag ルートタグ名
	 * @throws Exception
	 */
	public XMLBuilder(String rootTag) throws Exception {
		// 初期化
		initDocument(rootTag);
	}

	/**
	 * 初期化
	 * 
	 * @param rootTag ルートタグ名
	 * @throws Exception
	 */
	public void initDocument(String rootTag) throws Exception {
		initDocument("", rootTag);
	}

	/**
	 * 初期化
	 * 
	 * @param xmlns xmlns定義
	 * @param rootTag ルートタグ名
	 * @throws Exception
	 */
	public void initDocument(String xmlns, String rootTag) throws Exception {

		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = factory.newDocumentBuilder();
		DOMImplementation domImpl = builder.getDOMImplementation();

		document = domImpl.createDocument(xmlns, rootTag, null);
		document.setXmlStandalone(true);

	}

	/**
	 * ルート要素の取得
	 * 
	 * @return ルート要素
	 */
	public Element getDocumentElement() {
		return document.getDocumentElement();
	}

	/**
	 * 指定親要素の子要素の作成
	 * 
	 * @param parent 親要素
	 * @param name 要素名
	 * @return 文書内の要素
	 */
	public Element appendElement(Element parent, String name) {
		Element node = document.createElement(name);
		parent.appendChild(node);
		return node;
	}

	/**
	 * 指定親要素の子要素の作成
	 * 
	 * @param parent 親要素
	 * @param name 要素名
	 * @param text テキスト
	 * @return 文書内の要素
	 */
	public Element appendElement(Element parent, String name, String text) {
		Element node = document.createElement(name);
		node.appendChild(document.createTextNode(Util.avoidNullNT(text)));
		parent.appendChild(node);
		return node;
	}

	/**
	 * ルート要素(親=<b>root</b>)の子要素(タグ=<b>name</b>)-[テキスト=(<b>text</b>)]の作成
	 * 
	 * @param name 要素名
	 * @param text テキスト
	 * @return 文書内の要素
	 */
	public Element addText(String name, String text) {
		Element node = document.createElement(name);
		node.appendChild(document.createTextNode(Util.avoidNullNT(text)));
		getDocumentElement().appendChild(node);
		return node;
	}

	/**
	 * 文書内の要素のテキスト追加
	 * 
	 * @param node 文書内の要素
	 * @param text テキスト
	 */
	public void appendTextNode(Element node, String text) {
		node.appendChild(document.createTextNode(Util.avoidNullNT(text)));
	}

	/**
	 * 文書内の要素のテキスト追加
	 * 
	 * @param node 文書内の要素
	 * @param text テキスト
	 */
	public void setText(Element node, String text) {
		node.setTextContent(Util.avoidNullNT(text));
	}

	/**
	 * 出力
	 * 
	 * @param fileName ファイル名
	 * @throws Exception
	 */
	public void output(String fileName) throws Exception {

		FileOutputStream os = null;
		try {
			TransformerFactory transFactory = TransformerFactory.newInstance();

			Transformer transformer = transFactory.newTransformer();
			transformer.setOutputProperty(OutputKeys.INDENT, "yes");
			transformer.setOutputProperty(OutputPropertiesFactory.S_KEY_INDENT_AMOUNT, String.valueOf(indent));
			transformer.setOutputProperty(OutputKeys.METHOD, "xml");
			transformer.setOutputProperty(OutputKeys.OMIT_XML_DECLARATION, xmlTitle ? "no" : "yes");

			DOMSource source = new DOMSource(document);
			File newXML = new File(fileName);

			os = new FileOutputStream(newXML);
			StreamResult result = new StreamResult(os);
			transformer.transform(source, result);

		} catch (TransformerFactoryConfigurationError e) {
			throw e;
		} finally {
			ResourceUtil.closeOutputStream(os);
		}
	}

	/**
	 * XML宣言出力するかどうかの取得
	 * 
	 * @return xmlTitle XML宣言出力するかどうか
	 */
	public boolean isXmlTitle() {
		return xmlTitle;
	}

	/**
	 * XML宣言出力するかどうかの設定
	 * 
	 * @param xmlTitle XML宣言出力するかどうか
	 */
	public void setXmlTitle(boolean xmlTitle) {
		this.xmlTitle = xmlTitle;
	}

	/**
	 * 要素のインデント桁数の取得
	 * 
	 * @return indent 要素のインデント桁数
	 */
	public int getIndent() {
		return indent;
	}

	/**
	 * 要素のインデント桁数の設定
	 * 
	 * @param indent 要素のインデント桁数
	 */
	public void setIndent(int indent) {
		this.indent = indent;
	}
}
