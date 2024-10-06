package jp.co.ais.trans.common.message;

import java.io.*;
import java.util.*;

import javax.xml.parsers.*;

import org.xml.sax.*;
import org.xml.sax.helpers.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;

/**
 * XML����w�肳�ꂽ����̃��b�Z�[�W��ǂݎ���āA���b�Z�[�W���X�g���\�z���ĕԂ��B
 */
public class MessageBuilder {

	/** ���s���� */
	protected static final String LINE_SEP = System.getProperty("line.separator");

	/**
	 * ���b�Z�[�W���X�g���\�z
	 * 
	 * @param lang ����R�[�h(ja:���{��Ȃ�)
	 * @param fileName XML�t�@�C����
	 * @return ���b�Z�[�W���X�g
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

			// SAX�p�[�T�[�t�@�N�g���𐶐�
			SAXParserFactory spfactory = SAXParserFactory.newInstance();

			// SAX�p�[�T�[�𐶐�
			SAXParser parser = spfactory.newSAXParser();

			// XML�t�@�C�����w�肳�ꂽ�f�t�H���g�n���h���[�ŏ���
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
	 * XML�n���h��
	 */
	protected static class XMLHandler extends DefaultHandler {

		/** �߂�l */
		protected Map tm;

		/** ����R�[�h */
		protected String lang;

		/** True:�Ώی���ɔ��������C�x���g���ǂ����̔��� */
		protected boolean bln = false;

		/** �L�[ */
		protected String key = "";

		/** �l */
		protected String value = "";

		/**
		 * �R���X�g���N�^.
		 * 
		 * @param lang ����R�[�h
		 */
		public XMLHandler(String lang) {
			this.lang = lang;
			this.tm = new TreeMap();
		}

		/**
		 * �v�f�̊J�n�^�O�ǂݍ��ݎ�
		 */
		@Override
		public void startElement(String uri, String localName, String qName, Attributes attributes) {

			// �^�O����<lang>
			if (qName.equals("lang")) {
				// lang�̑���code���w�肳�ꂽ����R�[�h��
				bln = attributes.getValue("code").equals(lang);
			}

			// word����<word>
			if (bln && qName.equals("word")) {
				key = attributes.getValue("id");
				value = "";
			}
		}

		/**
		 * �e�L�X�g�f�[�^�ǂݍ��ݎ�
		 */
		@Override
		public void characters(char[] ch, int offset, int length) {
			if (bln) {
				value += new String(ch, offset, length);
			}
		}

		/**
		 * �v�f�̏I���^�O
		 */
		@Override
		public void endElement(String uri, String localName, String qName) {

			if (bln && qName.equals("word")) {
				if (!(value.trim().equals(""))) {
					// tm�ɒǉ�
					tm.put(key, value.trim().replaceAll("\\\\n", LINE_SEP));
				}
			}
		}

		/**
		 * ���߂��f�[�^��Ԃ�
		 * 
		 * @return �f�[�^
		 */
		public Map getData() {
			return tm;
		}
	}
}
