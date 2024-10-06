package jp.co.ais.trans2.model.xml;

import java.io.*;

import javax.xml.parsers.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * XML�p�[�T�[
 */
public class XMLParser {

	/** �n���h�� */
	protected XMLHandler handler;

	/**
	 * �R���X�g���N�^.
	 */
	public XMLParser() {
		super();
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param handler �n���h��
	 */
	public XMLParser(XMLHandler handler) {
		this.handler = handler;
	}

	/**
	 * �n���h��
	 * 
	 * @param handler �n���h��
	 */
	public void setHandler(XMLHandler handler) {
		this.handler = handler;
	}

	/**
	 * �ϊ�
	 * 
	 * @param fileName XML�t�@�C����
	 * @return �l
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
	 * �ϊ�
	 * 
	 * @param stream �X�g���[��
	 * @return �l
	 * @throws TException
	 */
	public Object parse(InputStream stream) throws TException {
		try {
			// SAX�p�[�T�[
			SAXParserFactory spfactory = SAXParserFactory.newInstance();
			SAXParser parser = spfactory.newSAXParser();

			// XML�t�@�C�����w�肳�ꂽ�f�t�H���g�n���h���[�ŏ���
			parser.parse(stream, handler);
			return handler.get();

		} catch (Exception e) {
			throw new TException(e);
		}
	}
}
