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
 * PDF帳票レイアウト上位クラス.
 */
public class ReportLayout extends LayoutBase {

	/** 同時処理ブロック用ダミー */
	protected static String syncDummy = "";

	/** A4横 帳票 */
	public static final int A4_LANDSCAPE = 1;

	/** A4縦 帳票 */
	public static final int A4_PORTRAIT = 2;

	/** A3横 帳票 */
	public static final int A3_LANDSCAPE = 3;

	/** テンプレート定義 A3横 */
	protected static final String A3LANDSCAPE_TEMPLATE;

	/** テンプレート定義 A4横 */
	protected static final String A4LANDSCAPE_TEMPLATE;

	/** テンプレート定義 A4縦 */
	protected static final String A4PORTRAIT_TEMPLATE;

	/** フォントファイルを読み込んだかどうかの判断リスト(言語別) */
	protected static Map<String, String[]> fontMap = new TreeMap<String, String[]>();

	static {
		try {
			// A4横用テンプレートXML
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

			// A4縦用テンプレートXML
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

			// A3横用テンプレートXML
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

	/** プリンタ種類 */
	protected JCPrinter printer;

	/** 情報を設定するドキュメント */
	protected JCDocument document;

	/** DirectかPDFか */
	protected boolean isDirect;

	/** テンプレートタイプ */
	protected int type;

	/**
	 * コンストラクタ. <br>
	 * PDFモードで作成。
	 * 
	 * @param lang 言語コード
	 * @throws TException
	 */
	public ReportLayout(String lang) throws TException {
		this(lang, false);
	}

	/**
	 * コンストラクタ. <br>
	 * 指定されたモード(true:ダイレクトモード、false:Viewerモード)で作成.
	 * 
	 * @param lang 言語コード
	 * @param isDirect ダイレクト印刷
	 * @throws TException プリンタ設定エラー
	 */
	public ReportLayout(String lang, boolean isDirect) throws TException {
		this(lang, isDirect, A4_LANDSCAPE);
	}

	/**
	 * コンストラクタ. <br>
	 * 指定されたモード(true:ダイレクトモード、false:Viewerモード)で作成.
	 * 
	 * @param lang 言語コード
	 * @param type 用紙向き(縦:A4_PORTRAIT、横:A4_LANDSCAPE)
	 * @throws TException プリンタ設定エラー
	 */
	public ReportLayout(String lang, int type) throws TException {
		this(lang, false, type);
	}

	/**
	 * コンストラクタ. <br>
	 * 指定されたモード(true:ダイレクトモード、false:Viewerモード)で作成.
	 * 
	 * @param lang 言語コード
	 * @param isDirect ダイレクト印刷
	 * @param type 用紙向き(縦:A4_PORTRAIT、横:A4_LANDSCAPE)
	 * @throws TException プリンタ設定エラー
	 */
	public ReportLayout(String lang, boolean isDirect, int type) throws TException {
		super(lang);

		this.init(isDirect, type);
	}

	/**
	 * 初期化処理
	 * 
	 * @param isDirect_ ダイレクト印刷
	 * @param type_ フォーマットタイプ
	 * @throws TException プリンタ設定エラー
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
	 * テンプレートを取得する.<br>
	 * 基底にないテンプレートを用いる場合は、このメソッドをOrverrideする
	 * 
	 * @return テンプレートList
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
	 * フォントファイルの読み込み
	 * 
	 * @param lang_ 言語コード
	 * @throws IOException
	 */
	protected void readFontLibrary(String lang_) throws IOException {
		if (!fontMap.containsKey(lang_)) {

			{
				// PDF用フォント設定
				URL newFontMap = getClass().getResource("/report/font/font.properties");
				ServerLogger.info("newFontMap=" + newFontMap);

				URL newFont = getClass().getResource(ReportConfig.getPDFFontFileName(lang_));
				ServerLogger.info("newFont=" + newFont);

				FontLibrary.addFont(newFont, FontLibrary.TTF);
				FontLibrary.addFontNameMap(newFontMap);

				TrueTypeFontProperties fp = FontLibrary.getTrueTypeFontProperties(ReportConfig.getPDFFontName(lang_),
					FontLibrary.PDF);
				fp.setEmbeddingRules(TrueTypeFontProperties.EMBED_NEEDED);

				// ダイレクトフォントは封印
				// String directFont = ReportConfig.getDirectPrintFontName(lang); //
				// ダイレクト印刷用フォント(ClientSide)
				String directFont = "";
				String pdfFont = ReportConfig.getPDFFontName(lang_); // PDF作成用フォント(ServerSide)

				fontMap.put(lang_, new String[] { directFont, pdfFont });

				ServerLogger.info("lang:" + lang_ + ", direct=" + directFont + ", pdf=" + pdfFont);
			}

			for (int i = 1; i <= 10; i++) {
				// 最大１０個Addonフォント設定可能
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

				// PDF用フォント設定
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
	 * PDF帳票用フォント
	 * 
	 * @return フォント名
	 */
	@Override
	protected String getFont() {
		// ダイレクト印刷 or PDF帳票
		return fontMap.get(getLanguageCode())[isDirect ? 0 : 1];
	}

	/**
	 * PDF帳票用addonフォント
	 * 
	 * @param fontId
	 * @return addonフォント名
	 */
	protected String getFontName(String fontId) {
		// ダイレクト印刷 or PDF帳票
		if (fontMap.containsKey(fontId)) {
			return fontMap.get(fontId)[isDirect ? 0 : 1];
		} else {
			return fontId;
		}
	}

	/**
	 * document変数を利用<br>
	 * <s>ドキュメント作成</s>
	 * 
	 * @deprecated document変数を利用
	 * @return ドキュメント
	 */
	protected JCDocument createDocument() {
		return document;
	}

	/**
	 * PDFバイナリデータの取得
	 * 
	 * @return byte[]
	 */
	public byte[] getBinary() {
		ServerLogger.debug("document" + document);

		synchronized (syncDummy) {
			// PDF情報をByteArrayOutputStreamに書き込む
			document.print();

			// ByteArrayOutputStreamに書き込まれた情報をbyte配列で返す
			return ((ByteArrayOutputStream) printer.getOutputStream()).toByteArray();
		}
	}

	/**
	 * ドキュメントの取得
	 * 
	 * @return ドキュメント
	 */
	public JCDocument getDocument() {
		return document;
	}

	/**
	 * テーブルセルのスタイル
	 * 
	 * @param fontSize フォントサイズ
	 * @return スタイル
	 */
	protected JCTextStyle createCellStyle(int fontSize) {
		JCTextStyle style = (JCTextStyle) JCTextStyle.NORMAL.clone();
		style.setFontFamily(getFont());
		style.setPointSize(fontSize);

		return style;
	}

	/**
	 * 全体のスタイル
	 * 
	 * @return スタイル
	 */
	protected JCTextStyle createCurrentStyle() {
		return createCurrentStyle(16);
	}

	/**
	 * 全体のスタイル
	 * 
	 * @param fontSize フォントサイズ
	 * @return スタイル
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
	 * 中央のスタイル
	 * 
	 * @return スタイル
	 */
	protected JCTextStyle createCenterStyle() {
		return createCenterStyle(8);
	}

	/**
	 * 中央のスタイル
	 * 
	 * @param fontSize フォントサイズ
	 * @return スタイル
	 */
	protected JCTextStyle createCenterStyle(int fontSize) {
		JCTextStyle style = createCellStyle(fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_CENTER);

		return style;
	}

	/**
	 * 左寄せのスタイル
	 * 
	 * @return スタイル
	 */
	protected JCTextStyle createLeftStyle() {
		return createLeftStyle(8);
	}

	/**
	 * 左寄せのスタイル
	 * 
	 * @param fontSize フォントサイズ
	 * @return スタイル
	 */
	protected JCTextStyle createLeftStyle(int fontSize) {
		JCTextStyle style = createCellStyle(fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_LEFT);

		return style;
	}

	/**
	 * 右寄せのスタイル
	 * 
	 * @return スタイル
	 */
	protected JCTextStyle createRightStyle() {
		return createRightStyle(8);
	}

	/**
	 * 右寄せのスタイル
	 * 
	 * @param fontSize フォントサイズ
	 * @return スタイル
	 */
	protected JCTextStyle createRightStyle(int fontSize) {
		JCTextStyle style = createCellStyle(fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_RIGHT);

		return style;
	}

	/**
	 * テーブルカラムのスタイル
	 * 
	 * @return スタイル
	 */
	protected JCTextStyle createColumnStyle() {
		return createColumnStyle(8);
	}

	/**
	 * テーブルカラムのスタイル
	 * 
	 * @param fontSize フォントサイズ
	 * @return スタイル
	 */
	protected JCTextStyle createColumnStyle(int fontSize) {
		JCTextStyle style = createCellStyle(fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_CENTER);

		return style;
	}

	/**
	 * テーブルセルのスタイル
	 * 
	 * @return スタイル
	 */
	protected JCTextStyle createCellStyle() {
		return createCellStyle(8);
	}

	/**
	 * テーブルセルのスタイル
	 * 
	 * @param fontId
	 * @param fontSize フォントサイズ
	 * @return スタイル
	 */
	protected JCTextStyle createCellStyle(String fontId, int fontSize) {
		JCTextStyle style = (JCTextStyle) JCTextStyle.NORMAL.clone();
		style.setFontFamily(getFontName(fontId));
		style.setPointSize(fontSize);

		return style;
	}

	/**
	 * 全体のスタイル
	 * 
	 * @param fontId
	 * @return スタイル
	 */
	protected JCTextStyle createCurrentStyle(String fontId) {
		return createCurrentStyle(fontId, 16);
	}

	/**
	 * 全体のスタイル
	 * 
	 * @param fontId
	 * @param fontSize フォントサイズ
	 * @return スタイル
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
	 * 中央のスタイル
	 * 
	 * @param fontId
	 * @return スタイル
	 */
	protected JCTextStyle createCenterStyle(String fontId) {
		return createCenterStyle(fontId, 8);
	}

	/**
	 * 中央のスタイル
	 * 
	 * @param fontId
	 * @param fontSize フォントサイズ
	 * @return スタイル
	 */
	protected JCTextStyle createCenterStyle(String fontId, int fontSize) {
		JCTextStyle style = createCellStyle(fontId, fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_CENTER);

		return style;
	}

	/**
	 * 左寄せのスタイル
	 * 
	 * @param fontId
	 * @return スタイル
	 */
	protected JCTextStyle createLeftStyle(String fontId) {
		return createLeftStyle(fontId, 8);
	}

	/**
	 * 左寄せのスタイル
	 * 
	 * @param fontId
	 * @param fontSize フォントサイズ
	 * @return スタイル
	 */
	protected JCTextStyle createLeftStyle(String fontId, int fontSize) {
		JCTextStyle style = createCellStyle(fontId, fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_LEFT);

		return style;
	}

	/**
	 * 右寄せのスタイル
	 * 
	 * @param fontId
	 * @return スタイル
	 */
	protected JCTextStyle createRightStyle(String fontId) {
		return createRightStyle(fontId, 8);
	}

	/**
	 * 右寄せのスタイル
	 * 
	 * @param fontId
	 * @param fontSize フォントサイズ
	 * @return スタイル
	 */
	protected JCTextStyle createRightStyle(String fontId, int fontSize) {
		JCTextStyle style = createCellStyle(fontId, fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_RIGHT);

		return style;
	}

	/**
	 * テーブルカラムのスタイル
	 * 
	 * @param fontId
	 * @return スタイル
	 */
	protected JCTextStyle createColumnStyle(String fontId) {
		return createColumnStyle(fontId, 8);
	}

	/**
	 * テーブルカラムのスタイル
	 * 
	 * @param fontId
	 * @param fontSize フォントサイズ
	 * @return スタイル
	 */
	protected JCTextStyle createColumnStyle(String fontId, int fontSize) {
		JCTextStyle style = createCellStyle(fontId, fontSize);
		style.setAlignment(JCTextStyle.ALIGNMENT_CENTER);

		return style;
	}

	/**
	 * テーブルセルのスタイル
	 * 
	 * @param fontId
	 * @return スタイル
	 */
	protected JCTextStyle createCellStyle(String fontId) {
		return createCellStyle(fontId, 8);
	}

	/**
	 * 罫線のスタイル(線無)
	 * 
	 * @return Drawスタイル
	 */
	protected JCDrawStyle createNonLineStyle() {
		return (JCDrawStyle) JCDrawStyle.BLANK.clone();
	}

	/**
	 * 罫線のスタイル(太線)
	 * 
	 * @return Drawスタイル
	 */
	protected JCDrawStyle createBoldLineStyle() {
		JCDrawStyle drawStyle = new JCDrawStyle();
		drawStyle.setLineType(JCDrawStyle.LINE_TYPE_PLAIN);
		drawStyle.setLineWidth(new JCUnit.Measure(JCUnit.CM, 0.03));
		return drawStyle;
	}

	/**
	 * 罫線のスタイル(標準線)
	 * 
	 * @return Drawスタイル
	 */
	protected JCDrawStyle createNomalLineStyle() {
		return (JCDrawStyle) JCDrawStyle.LINE.clone();
	}

	/**
	 * 罫線のスタイル(破線)<br>
	 * 破線間隔は、0.001
	 * 
	 * @return Drawスタイル
	 */
	protected JCDrawStyle createBrokenLineStyle() {
		return createBrokenLineStyle(0.001d);
	}

	/**
	 * 罫線のスタイル(破線)
	 * 
	 * @param dashLength 破線の間隔
	 * @return Drawスタイル
	 */
	protected JCDrawStyle createBrokenLineStyle(double dashLength) {
		JCDrawStyle drawStyle = new JCDrawStyle();
		drawStyle.setLineType(JCDrawStyle.LINE_TYPE_BROKEN);
		drawStyle.setDashLength(new JCUnit.Measure(JCUnit.INTERNAL, dashLength));

		return drawStyle;
	}

	/**
	 * 罫線のスタイル(二重線)
	 * 
	 * @return Drawスタイル
	 */
	protected JCDrawStyle createDoubleLineStyle() {
		return (JCDrawStyle) JCDrawStyle.LINE_DOUBLE.clone();
	}

	/**
	 * 罫線のスタイル(断続線)
	 * 
	 * @return Drawスタイル
	 */
	protected JCDrawStyle createDashedLineStyle() {
		return (JCDrawStyle) JCDrawStyle.LINE_DASHED.clone();
	}

	/**
	 * 線の太さ(縦幅)を設定する
	 * 
	 * @param style 対象スタイル
	 * @param width 幅
	 */
	protected void setLineWidth(JCDrawStyle style, double width) {
		style.setLineWidth(new JCUnit.Measure(JCUnit.CM, width));
	}
}
