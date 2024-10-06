package jp.co.ais.trans.common.report;

import java.awt.*;
import java.awt.print.*;
import java.io.*;
import java.net.*;
import java.util.*;
import java.util.List;

import com.klg.jclass.page.*;
import com.klg.jclass.page.adobe.pdf.*;
import com.klg.jclass.page.awt.*;
import com.klg.jclass.page.awt.JCAWTPrinter.PrinterJobCancelledException;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;

/**
 * PDF���[���C�A�E�g��ʃN���X.
 */
public class ReportLayout extends LayoutBase {

	/** ���������u���b�N�p�_�~�[ */
	protected static String syncDummy = "";

	/** A4�� ���[ */
	public static final int A4_LANDSCAPE = 1;

	/** A4�c ���[ */
	public static final int A4_PORTRAIT = 2;

	/** A3�� ���[ */
	public static final int A3_LANDSCAPE = 3;

	/** �e���v���[�g��` A3�� */
	protected static final String A3LANDSCAPE_TEMPLATE;

	/** �e���v���[�g��` A4�� */
	protected static final String A4LANDSCAPE_TEMPLATE;

	/** �e���v���[�g��` A4�c */
	protected static final String A4PORTRAIT_TEMPLATE;

	/** �t�H���g�t�@�C����ǂݍ��񂾂��ǂ����̔��f���X�g(�����) */
	protected static Map<String, String[]> fontMap = new TreeMap<String, String[]>();

	static {
		try {
			// A4���p�e���v���[�gXML
			StringBuilder buff = new StringBuilder();

			buff.append("<?xml version=\"1.0\"?>");
			buff.append("<!DOCTYPE JCPAGETEMPLATE SYSTEM \"JCPageTemplate.dtd\">\n");
			buff.append("<JCPAGETEMPLATE TITLE=\"a4side\">\n");
			buff.append("    <PAGE NAME=\"a4side\" UNIT=\"cm\">");
			buff.append("        <LOCATION X=\"0\" Y=\"0\"/>");
			buff.append("        <SIZE WIDTH=\"29.7\"  HEIGHT=\"21\"/>");
			buff.append("        <FRAME NAME=\"detail\" UNIT=\"cm\">");
			buff.append("            <LOCATION X=\"1\" Y=\"2\"/>");
			buff.append("            <SIZE WIDTH=\"27.7\"  HEIGHT=\"19\"/>");
			buff.append("        </FRAME>");
			buff.append("        <FLOWFRAME NAME=\"detail\"/>");
			buff.append("        <FLOWPAGE NAME=\"a4side\"/>");
			buff.append("        <FLOWSECTION NAME=\"a4side\"/>");
			buff.append("    </PAGE>");
			buff.append("</JCPAGETEMPLATE>\n");

			A4LANDSCAPE_TEMPLATE = buff.toString();

			// A4�c�p�e���v���[�gXML
			buff = new StringBuilder();

			buff.append("<?xml version=\"1.0\"?>");
			buff.append("<!DOCTYPE JCPAGETEMPLATE SYSTEM \"JCPageTemplate.dtd\">\n");
			buff.append("<JCPAGETEMPLATE TITLE=\"a4port\">\n");
			buff.append("    <PAGE NAME=\"a4port\" UNIT=\"cm\">");
			buff.append("        <LOCATION X=\"0\" Y=\"0\"/>");
			buff.append("        <SIZE WIDTH=\"21\"  HEIGHT=\"29.7\"/>");
			buff.append("        <FRAME NAME=\"detail\" UNIT=\"cm\">");
			buff.append("            <LOCATION X=\"1\" Y=\"2\"/>");
			buff.append("            <SIZE WIDTH=\"19\"  HEIGHT=\"25.7\"/>");
			buff.append("        </FRAME>");
			buff.append("        <FLOWFRAME NAME=\"detail\"/>");
			buff.append("        <FLOWPAGE NAME=\"a4port\"/>");
			buff.append("        <FLOWSECTION NAME=\"a4port\"/>");
			buff.append("    </PAGE>");
			buff.append("</JCPAGETEMPLATE>\n");

			A4PORTRAIT_TEMPLATE = buff.toString();

			// A3���p�e���v���[�gXML
			buff = new StringBuilder();

			buff.append("<?xml version=\"1.0\"?>");
			buff.append("<!DOCTYPE JCPAGETEMPLATE SYSTEM \"JCPageTemplate.dtd\">\n");
			buff.append("<JCPAGETEMPLATE TITLE=\"a3side\">\n");
			buff.append("    <PAGE NAME=\"a3side\" UNIT=\"cm\">");
			buff.append("        <LOCATION X=\"0\" Y=\"0\"/>");
			buff.append("        <SIZE WIDTH=\"42\"  HEIGHT=\"29.7\"/>");
			buff.append("        <FRAME NAME=\"detail\" UNIT=\"cm\">");
			buff.append("            <LOCATION X=\"1\" Y=\"2\"/>");
			buff.append("            <SIZE WIDTH=\"40\"  HEIGHT=\"27.7\"/>");
			buff.append("        </FRAME>");
			buff.append("        <FLOWFRAME NAME=\"detail\"/>");
			buff.append("        <FLOWPAGE NAME=\"a3side\"/>");
			buff.append("        <FLOWSECTION NAME=\"a3side\"/>");
			buff.append("    </PAGE>");
			buff.append("</JCPAGETEMPLATE>\n");

			A3LANDSCAPE_TEMPLATE = buff.toString();

		} catch (Exception ex) {
			ServerLogger.error("static error", ex);

			throw new TEnvironmentException(ex, "S00001");
		}
	}

	/** �v�����^��� */
	protected JCPrinter printer;

	/** ����ݒ肷��h�L�������g */
	protected JCDocument document;

	/** Direct��PDF�� */
	protected boolean isDirect;

	/** �e���v���[�g�^�C�v */
	protected int type;

	/**
	 * �R���X�g���N�^. <br>
	 * PDF���[�h�ō쐬�B
	 * 
	 * @param lang ����R�[�h
	 * @throws TException
	 */
	public ReportLayout(String lang) throws TException {
		this(lang, false);
	}

	/**
	 * �R���X�g���N�^. <br>
	 * �w�肳�ꂽ���[�h(true:�_�C���N�g���[�h�Afalse:Viewer���[�h)�ō쐬.
	 * 
	 * @param lang ����R�[�h
	 * @param isDirect �_�C���N�g���
	 * @throws TException �v�����^�ݒ�G���[
	 */
	public ReportLayout(String lang, boolean isDirect) throws TException {
		this(lang, isDirect, A4_LANDSCAPE);
	}

	/**
	 * �R���X�g���N�^. <br>
	 * �w�肳�ꂽ���[�h(true:�_�C���N�g���[�h�Afalse:Viewer���[�h)�ō쐬.
	 * 
	 * @param lang ����R�[�h
	 * @param type �p������(�c:A4_PORTRAIT�A��:A4_LANDSCAPE)
	 * @throws TException �v�����^�ݒ�G���[
	 */
	public ReportLayout(String lang, int type) throws TException {
		this(lang, false, type);
	}

	/**
	 * �R���X�g���N�^. <br>
	 * �w�肳�ꂽ���[�h(true:�_�C���N�g���[�h�Afalse:Viewer���[�h)�ō쐬.
	 * 
	 * @param lang ����R�[�h
	 * @param isDirect �_�C���N�g���
	 * @param type �p������(�c:A4_PORTRAIT�A��:A4_LANDSCAPE)
	 * @throws TException �v�����^�ݒ�G���[
	 */
	public ReportLayout(String lang, boolean isDirect, int type) throws TException {
		super(lang);

		this.init(isDirect, type);
	}

	/**
	 * ����������
	 * 
	 * @param isDirect_ �_�C���N�g���
	 * @param type_ �t�H�[�}�b�g�^�C�v
	 * @throws TException �v�����^�ݒ�G���[
	 */
	protected void init(boolean isDirect_, int type_) throws TException {
		this.isDirect = isDirect_;
		this.type = type_;

		try {

			List template = getTemplate();
			if (isDirect) {
				printer = new JCAWTPrinter(template, false);
			} else {
				readFontLibrary(getLanguageCode());
				printer = new JCPDFPrinter(new ByteArrayOutputStream());
			}

			document = new JCDocument(printer, template);
			document.setFlushPolicy(JCDocument.FLUSH_POLICY_ON_OUTPUT);
			document.setOutputPolicy(JCDocument.OUTPUT_POLICY_IMMEDIATE);

		} catch (PrinterJobCancelledException e) {
			throw new TException(e, "W00045");

		} catch (PrinterException e) {
			throw new TException(e, "W00045");

		} catch (IOException e) {
			throw new TEnvironmentException(e, "E00020", "font");

		} finally {
			ServerLogger.debug("type=" + type + ", printer=" + printer);
		}
	}

	/**
	 * �e���v���[�g���擾����.<br>
	 * ���ɂȂ��e���v���[�g��p����ꍇ�́A���̃��\�b�h��Orverride����
	 * 
	 * @return �e���v���[�gList
	 */
	protected List getTemplate() {
		try {
			String str;
			switch (type) {
				case A3_LANDSCAPE:
					str = A3LANDSCAPE_TEMPLATE;
					break;

				case A4_LANDSCAPE:
					str = A4LANDSCAPE_TEMPLATE;
					break;

				case A4_PORTRAIT:
					str = A4PORTRAIT_TEMPLATE;
					break;

				default:
					throw new IllegalArgumentException("type is not found.");
			}

			return JCPageTemplate.loadTemplates(new StringReader(str));

		} catch (Exception ex) {
			throw new TEnvironmentException(ex);
		}
	}

	/**
	 * �t�H���g�t�@�C���̓ǂݍ���
	 * 
	 * @param lang_ ����R�[�h
	 * @throws IOException
	 */
	protected void readFontLibrary(String lang_) throws IOException {
		if (!fontMap.containsKey(lang_)) {

			{
				// PDF�p�t�H���g�ݒ�
				URL newFontMap = getClass().getResource("/report/font/font.properties");
				ServerLogger.info("newFontMap=" + newFontMap);

				URL newFont = getClass().getResource(ReportConfig.getPDFFontFileName(lang_));
				ServerLogger.info("newFont=" + newFont);

				FontLibrary.addFont(newFont, FontLibrary.TTF);
				FontLibrary.addFontNameMap(newFontMap);

				TrueTypeFontProperties fp = FontLibrary.getTrueTypeFontProperties(ReportConfig.getPDFFontName(lang_),
					FontLibrary.PDF);
				fp.setEmbeddingRules(TrueTypeFontProperties.EMBED_NEEDED);

				// �_�C���N�g�t�H���g�͕���
				// String directFont = ReportConfig.getDirectPrintFontName(lang); //
				// �_�C���N�g����p�t�H���g(ClientSide)
				String directFont = "";
				String pdfFont = ReportConfig.getPDFFontName(lang_); // PDF�쐬�p�t�H���g(ServerSide)

				fontMap.put(lang_, new String[] { directFont, pdfFont });

				ServerLogger.info("lang:" + lang_ + ", direct=" + directFont + ", pdf=" + pdfFont);
			}

			for (int i = 1; i <= 10; i++) {
				// �ő�P�O��Addon�t�H���g�ݒ�\
				String addonFontId = ReportConfig.getPDFAddonFontId(i);
				if (fontMap.containsKey(addonFontId)) {
					continue;
				}
				if (Util.isNullOrEmpty(addonFontId)) {
					break;
				}

				String addonFontName = ReportConfig.getPDFAddonFontName(i);
				if (Util.isNullOrEmpty(addonFontName)) {
					break;
				}

				String addonFontFileName = ReportConfig.getPDFAddonFontFileName(i);
				if (Util.isNullOrEmpty(addonFontFileName)) {
					break;
				}

				// PDF�p�t�H���g�ݒ�
				URL newFont = getClass().getResource(addonFontFileName);
				ServerLogger.info("newFont=" + newFont);

				FontLibrary.addFont(newFont, FontLibrary.TTF);

				TrueTypeFontProperties fp = FontLibrary.getTrueTypeFontProperties(addonFontId, FontLibrary.PDF);
				fp.setEmbeddingRules(TrueTypeFontProperties.EMBED_NEEDED);

				fontMap.put(addonFontId, new String[] { "", addonFontName });
				ServerLogger.info("lang:" + lang_ + ", font id=" + addonFontId + ", font name=" + addonFontName);

			}
		}
	}

	/**
	 * PDF���[�p�t�H���g
	 * 
	 * @return �t�H���g��
	 */
	@Override
	protected String getFont() {
		// �_�C���N�g��� or PDF���[
		return fontMap.get(getLanguageCode())[isDirect ? 0 : 1];
	}

	/**
	 * PDF���[�paddon�t�H���g
	 * 
	 * @param fontId
	 * @return addon�t�H���g��
	 */
	protected String getFontName(String fontId) {
		// �_�C���N�g��� or PDF���[
		if (fontMap.containsKey(fontId)) {
			return fontMap.get(fontId)[isDirect ? 0 : 1];
		} else {
			return fontId;
		}
	}

	/**
	 * document�ϐ��𗘗p<br>
	 * <s>�h�L�������g�쐬</s>
	 * 
	 * @deprecated document�ϐ��𗘗p
	 * @return �h�L�������g
	 */
	protected JCDocument createDocument() {
		return document;
	}

	/**
	 * PDF�o�C�i���f�[�^�̎擾
	 * 
	 * @return byte[]
	 */
	public byte[] getBinary() {
		ServerLogger.debug("document" + document);

		synchronized (syncDummy) {
			// PDF����ByteArrayOutputStream�ɏ�������
			document.print();

			// ByteArrayOutputStream�ɏ������܂ꂽ����byte�z��ŕԂ�
			return ((ByteArrayOutputStream) printer.getOutputStream()).toByteArray();
		}
	}

	/**
	 * �h�L�������g�̎擾
	 * 
	 * @return �h�L�������g
	 */
	public JCDocument getDocument() {
		return document;
	}

	/**
	 * �e�[�u���Z���̃X�^�C��
	 * 
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �X�^�C��
	 */
	protected JCTextStyle createCellStyle(int fontSize) {
		JCTextStyle style = (JCTextStyle) JCTextStyle.NORMAL.clone();
		style.setFontFamily(getFont());
		style.setPointSize(fontSize);

		return style;
	}

	/**
	 * �S�̂̃X�^�C��
	 * 
	 * @return �X�^�C��
	 */
	protected JCTextStyle createCurrentStyle() {
		return createCurrentStyle(16);
	}

	/**
	 * �S�̂̃X�^�C��
	 * 
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �X�^�C��
	 */
	protected JCTextStyle createCurrentStyle(int fontSize) {
		JCTextStyle style = createCellStyle(fontSize);

		if (this.isDirect) {
			style.setFontStyle(Font.BOLD);
		}

		style.setAlignment(JCTextStyle.ALIGNMENT_CENTER);

		return style;
	}

	/**
	 * �����̃X�^�C��
	 * 
	 * @return �X�^�C��
	 */
	protected JCTextStyle createCenterStyle() {
		return createCenterStyle(8);
	}

	/**
	 * �����̃X�^�C��
	 * 
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �X�^�C��
	 */
	protected JCTextStyle createCenterStyle(int fontSize) {
		JCTextStyle style = createCellStyle(fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_CENTER);

		return style;
	}

	/**
	 * ���񂹂̃X�^�C��
	 * 
	 * @return �X�^�C��
	 */
	protected JCTextStyle createLeftStyle() {
		return createLeftStyle(8);
	}

	/**
	 * ���񂹂̃X�^�C��
	 * 
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �X�^�C��
	 */
	protected JCTextStyle createLeftStyle(int fontSize) {
		JCTextStyle style = createCellStyle(fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_LEFT);

		return style;
	}

	/**
	 * �E�񂹂̃X�^�C��
	 * 
	 * @return �X�^�C��
	 */
	protected JCTextStyle createRightStyle() {
		return createRightStyle(8);
	}

	/**
	 * �E�񂹂̃X�^�C��
	 * 
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �X�^�C��
	 */
	protected JCTextStyle createRightStyle(int fontSize) {
		JCTextStyle style = createCellStyle(fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_RIGHT);

		return style;
	}

	/**
	 * �e�[�u���J�����̃X�^�C��
	 * 
	 * @return �X�^�C��
	 */
	protected JCTextStyle createColumnStyle() {
		return createColumnStyle(8);
	}

	/**
	 * �e�[�u���J�����̃X�^�C��
	 * 
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �X�^�C��
	 */
	protected JCTextStyle createColumnStyle(int fontSize) {
		JCTextStyle style = createCellStyle(fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_CENTER);

		return style;
	}

	/**
	 * �e�[�u���Z���̃X�^�C��
	 * 
	 * @return �X�^�C��
	 */
	protected JCTextStyle createCellStyle() {
		return createCellStyle(8);
	}

	/**
	 * �e�[�u���Z���̃X�^�C��
	 * 
	 * @param fontId
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �X�^�C��
	 */
	protected JCTextStyle createCellStyle(String fontId, int fontSize) {
		JCTextStyle style = (JCTextStyle) JCTextStyle.NORMAL.clone();
		style.setFontFamily(getFontName(fontId));
		style.setPointSize(fontSize);

		return style;
	}

	/**
	 * �S�̂̃X�^�C��
	 * 
	 * @param fontId
	 * @return �X�^�C��
	 */
	protected JCTextStyle createCurrentStyle(String fontId) {
		return createCurrentStyle(fontId, 16);
	}

	/**
	 * �S�̂̃X�^�C��
	 * 
	 * @param fontId
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �X�^�C��
	 */
	protected JCTextStyle createCurrentStyle(String fontId, int fontSize) {
		JCTextStyle style = createCellStyle(fontId, fontSize);

		if (this.isDirect) {
			style.setFontStyle(Font.BOLD);
		}

		style.setAlignment(JCTextStyle.ALIGNMENT_CENTER);

		return style;
	}

	/**
	 * �����̃X�^�C��
	 * 
	 * @param fontId
	 * @return �X�^�C��
	 */
	protected JCTextStyle createCenterStyle(String fontId) {
		return createCenterStyle(fontId, 8);
	}

	/**
	 * �����̃X�^�C��
	 * 
	 * @param fontId
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �X�^�C��
	 */
	protected JCTextStyle createCenterStyle(String fontId, int fontSize) {
		JCTextStyle style = createCellStyle(fontId, fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_CENTER);

		return style;
	}

	/**
	 * ���񂹂̃X�^�C��
	 * 
	 * @param fontId
	 * @return �X�^�C��
	 */
	protected JCTextStyle createLeftStyle(String fontId) {
		return createLeftStyle(fontId, 8);
	}

	/**
	 * ���񂹂̃X�^�C��
	 * 
	 * @param fontId
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �X�^�C��
	 */
	protected JCTextStyle createLeftStyle(String fontId, int fontSize) {
		JCTextStyle style = createCellStyle(fontId, fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_LEFT);

		return style;
	}

	/**
	 * �E�񂹂̃X�^�C��
	 * 
	 * @param fontId
	 * @return �X�^�C��
	 */
	protected JCTextStyle createRightStyle(String fontId) {
		return createRightStyle(fontId, 8);
	}

	/**
	 * �E�񂹂̃X�^�C��
	 * 
	 * @param fontId
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �X�^�C��
	 */
	protected JCTextStyle createRightStyle(String fontId, int fontSize) {
		JCTextStyle style = createCellStyle(fontId, fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_RIGHT);

		return style;
	}

	/**
	 * �e�[�u���J�����̃X�^�C��
	 * 
	 * @param fontId
	 * @return �X�^�C��
	 */
	protected JCTextStyle createColumnStyle(String fontId) {
		return createColumnStyle(fontId, 8);
	}

	/**
	 * �e�[�u���J�����̃X�^�C��
	 * 
	 * @param fontId
	 * @param fontSize �t�H���g�T�C�Y
	 * @return �X�^�C��
	 */
	protected JCTextStyle createColumnStyle(String fontId, int fontSize) {
		JCTextStyle style = createCellStyle(fontId, fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_CENTER);

		return style;
	}

	/**
	 * �e�[�u���Z���̃X�^�C��
	 * 
	 * @param fontId
	 * @return �X�^�C��
	 */
	protected JCTextStyle createCellStyle(String fontId) {
		return createCellStyle(fontId, 8);
	}

	/**
	 * �r���̃X�^�C��(����)
	 * 
	 * @return Draw�X�^�C��
	 */
	protected JCDrawStyle createNonLineStyle() {
		return (JCDrawStyle) JCDrawStyle.BLANK.clone();
	}

	/**
	 * �r���̃X�^�C��(����)
	 * 
	 * @return Draw�X�^�C��
	 */
	protected JCDrawStyle createBoldLineStyle() {
		JCDrawStyle drawStyle = new JCDrawStyle();
		drawStyle.setLineType(JCDrawStyle.LINE_TYPE_PLAIN);
		drawStyle.setLineWidth(new JCUnit.Measure(JCUnit.CM, 0.03));
		return drawStyle;
	}

	/**
	 * �r���̃X�^�C��(�W����)
	 * 
	 * @return Draw�X�^�C��
	 */
	protected JCDrawStyle createNomalLineStyle() {
		return (JCDrawStyle) JCDrawStyle.LINE.clone();
	}

	/**
	 * �r���̃X�^�C��(�j��)<br>
	 * �j���Ԋu�́A0.001
	 * 
	 * @return Draw�X�^�C��
	 */
	protected JCDrawStyle createBrokenLineStyle() {
		return createBrokenLineStyle(0.001d);
	}

	/**
	 * �r���̃X�^�C��(�j��)
	 * 
	 * @param dashLength �j���̊Ԋu
	 * @return Draw�X�^�C��
	 */
	protected JCDrawStyle createBrokenLineStyle(double dashLength) {
		JCDrawStyle drawStyle = new JCDrawStyle();
		drawStyle.setLineType(JCDrawStyle.LINE_TYPE_BROKEN);
		drawStyle.setDashLength(new JCUnit.Measure(JCUnit.INTERNAL, dashLength));

		return drawStyle;
	}

	/**
	 * �r���̃X�^�C��(��d��)
	 * 
	 * @return Draw�X�^�C��
	 */
	protected JCDrawStyle createDoubleLineStyle() {
		return (JCDrawStyle) JCDrawStyle.LINE_DOUBLE.clone();
	}

	/**
	 * �r���̃X�^�C��(�f����)
	 * 
	 * @return Draw�X�^�C��
	 */
	protected JCDrawStyle createDashedLineStyle() {
		return (JCDrawStyle) JCDrawStyle.LINE_DASHED.clone();
	}

	/**
	 * ���̑���(�c��)��ݒ肷��
	 * 
	 * @param style �ΏۃX�^�C��
	 * @param width ��
	 */
	protected void setLineWidth(JCDrawStyle style, double width) {
		style.setLineWidth(new JCUnit.Measure(JCUnit.CM, width));
	}
}
