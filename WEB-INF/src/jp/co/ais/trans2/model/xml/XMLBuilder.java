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
 * XML�쐬
 */
public class XMLBuilder {

	/** XML�h�L�������g */
	public Document document = null;

	/** true:XML�錾�o�͂���Afalse:XML�錾�o�͂��Ȃ� */
	public boolean xmlTitle = false;

	/** �v�f�̃C���f���g���� */
	public int indent = 2;

	/**
	 * �R���X�g���N�^�[
	 */
	public XMLBuilder() {
		// �Ȃ�
	}

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param rootTag ���[�g�^�O��
	 * @throws Exception
	 */
	public XMLBuilder(String rootTag) throws Exception {
		// ������
		initDocument(rootTag);
	}

	/**
	 * ������
	 * 
	 * @param rootTag ���[�g�^�O��
	 * @throws Exception
	 */
	public void initDocument(String rootTag) throws Exception {
		initDocument("", rootTag);
	}

	/**
	 * ������
	 * 
	 * @param xmlns xmlns��`
	 * @param rootTag ���[�g�^�O��
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
	 * ���[�g�v�f�̎擾
	 * 
	 * @return ���[�g�v�f
	 */
	public Element getDocumentElement() {
		return document.getDocumentElement();
	}

	/**
	 * �w��e�v�f�̎q�v�f�̍쐬
	 * 
	 * @param parent �e�v�f
	 * @param name �v�f��
	 * @return �������̗v�f
	 */
	public Element appendElement(Element parent, String name) {
		Element node = document.createElement(name);
		parent.appendChild(node);
		return node;
	}

	/**
	 * �w��e�v�f�̎q�v�f�̍쐬
	 * 
	 * @param parent �e�v�f
	 * @param name �v�f��
	 * @param text �e�L�X�g
	 * @return �������̗v�f
	 */
	public Element appendElement(Element parent, String name, String text) {
		Element node = document.createElement(name);
		node.appendChild(document.createTextNode(Util.avoidNullNT(text)));
		parent.appendChild(node);
		return node;
	}

	/**
	 * ���[�g�v�f(�e=<b>root</b>)�̎q�v�f(�^�O=<b>name</b>)-[�e�L�X�g=(<b>text</b>)]�̍쐬
	 * 
	 * @param name �v�f��
	 * @param text �e�L�X�g
	 * @return �������̗v�f
	 */
	public Element addText(String name, String text) {
		Element node = document.createElement(name);
		node.appendChild(document.createTextNode(Util.avoidNullNT(text)));
		getDocumentElement().appendChild(node);
		return node;
	}

	/**
	 * �������̗v�f�̃e�L�X�g�ǉ�
	 * 
	 * @param node �������̗v�f
	 * @param text �e�L�X�g
	 */
	public void appendTextNode(Element node, String text) {
		node.appendChild(document.createTextNode(Util.avoidNullNT(text)));
	}

	/**
	 * �������̗v�f�̃e�L�X�g�ǉ�
	 * 
	 * @param node �������̗v�f
	 * @param text �e�L�X�g
	 */
	public void setText(Element node, String text) {
		node.setTextContent(Util.avoidNullNT(text));
	}

	/**
	 * �o��
	 * 
	 * @param fileName �t�@�C����
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
	 * XML�錾�o�͂��邩�ǂ����̎擾
	 * 
	 * @return xmlTitle XML�錾�o�͂��邩�ǂ���
	 */
	public boolean isXmlTitle() {
		return xmlTitle;
	}

	/**
	 * XML�錾�o�͂��邩�ǂ����̐ݒ�
	 * 
	 * @param xmlTitle XML�錾�o�͂��邩�ǂ���
	 */
	public void setXmlTitle(boolean xmlTitle) {
		this.xmlTitle = xmlTitle;
	}

	/**
	 * �v�f�̃C���f���g�����̎擾
	 * 
	 * @return indent �v�f�̃C���f���g����
	 */
	public int getIndent() {
		return indent;
	}

	/**
	 * �v�f�̃C���f���g�����̐ݒ�
	 * 
	 * @param indent �v�f�̃C���f���g����
	 */
	public void setIndent(int indent) {
		this.indent = indent;
	}
}
