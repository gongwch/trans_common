package jp.co.ais.trans2.common.report;

import java.util.*;

import com.klg.jclass.page.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.report.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.common.model.format.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * 帳票レイアウト基底クラス
 * 
 * @author AIS
 */
public abstract class TReportLayout extends ReportLayout {

	/** flow */
	protected JCFlow flow = null;

	/** book */
	protected LedgerBook book;

	/** 帳票描画 */
	protected LedgerDrawer drawer;

	/** 帳票データを保存するか */
	protected static boolean isSaveBook = ServerConfig.isFlagOn("trans.system.ledger.saveBook");

	/** 帳票区分を非表示するか */
	protected static boolean isNotOutputAccountBook = ServerConfig.isFlagOn("trans.report.not.output.accountbook");

	/** フレームの開始位置の水平座標 */
	protected double frameLoacationX = 0.5;

	/** フレームの終了位置の垂直座標 */
	protected double frameLoacationY = 1.0;

	/** フレームの幅 */
	protected double frameWidth = 28.7;

	/** フレームの高さ */
	protected double frameHeight = 21.0;

	/** 現在のページ番号 */
	protected int currentPageNo = 1;

	/** セルのスタイル(タイトル) */
	protected JCTextStyle titleStyle;

	/** セルのスタイル(左) */
	protected JCTextStyle leftStyle;

	/** セルのスタイル(中央) */
	protected JCTextStyle centerStyle;

	/** セルのスタイル(右) */
	protected JCTextStyle rightStyle;

	/** 罫線のスタイル(タイトル) */
	protected JCDrawStyle titleLineStyle;

	/** 罫線のスタイル(壊し) */
	protected JCDrawStyle brokenLineStyle;

	/** 罫線のスタイル(中) */
	protected JCDrawStyle middleLineStyle;

	/** 罫線のスタイル(狭い) */
	protected JCDrawStyle thinLineStyle;

	/**
	 * コンストラクタ
	 * 
	 * @param langMessage
	 * @param type
	 * @throws TException
	 */
	public TReportLayout(String langMessage, int type) throws TException {
		super(langMessage, false, type);
	}

	/**
	 * @param langMessage
	 * @throws TException
	 */
	public TReportLayout(String langMessage) throws TException {
		super(langMessage, false);
	}

	/**
	 * 初期化
	 */
	protected void init() {
		initFrameSize();
		initStyle();
		initWord();
	}

	/**
	 * フレームのサイズ設定
	 */
	protected void initFrameSize() {
		// for Override
	}

	/**
	 * 文言設定
	 */
	protected void initWord() {
		// for Override
	}

	/**
	 * 複数Bookを出力する場合、bookが変わった時に呼び出される初期化メソッド
	 */
	protected void initWhenBookBreak() {
		// for Override
	}

	/**
	 * Style初期化
	 */
	protected void initStyle() {

		// 線のスタイル初期化
		titleLineStyle = createBoldLineStyle();
		titleLineStyle.setLineWidth(new JCUnit.Measure(JCUnit.CM, 0.040));
		brokenLineStyle = createBrokenLineStyle(0.015);
		middleLineStyle = createNomalLineStyle();
		middleLineStyle.setLineWidth(new JCUnit.Measure(JCUnit.CM, 0.015));
		thinLineStyle = createNomalLineStyle();
		thinLineStyle.setLineWidth(new JCUnit.Measure(JCUnit.CM, 0.010));

		// 文字スタイル初期化
		titleStyle = createCurrentStyle();
		titleStyle.setUnderlining(2);
		leftStyle = createLeftStyle(8);
		centerStyle = createCenterStyle(8);
		rightStyle = createRightStyle(8);

	}

	/**
	 * 帳票を返す。 ※1つのBookを1つのPDFファイルで返す
	 * 
	 * @param _book
	 * @return 帳票
	 * @throws TException
	 */
	public byte[] getReport(LedgerBook _book) throws TException {

		this.book = _book;

		if (isSaveBook) {
			saveBook(_book);
		}

		init();

		document.setFlushPolicy(JCDocument.FLUSH_POLICY_ON_OUTPUT);
		document.setOutputPolicy(JCDocument.OUTPUT_POLICY_IMMEDIATE);

		flow = new JCFlow(document);
		drawer = new LedgerDrawer(document);

		// 初期化
		initWhenBookBreak();

		List<LedgerSheet> allSheet = _book.getSheet();

		// シートの数だけページ作成する
		for (int i = 0; i < allSheet.size(); i++) {

			currentPageNo = i + 1;

			// ヘッダー描画
			LedgerSheet sheet = allSheet.get(i);

			refleshFrame();

			drawHeader(sheet.getHeader());

			// 2.明細描画
			drawDetail(sheet.getDetail());

			// 3.フッター描画
			if (sheet.getFooter() != null) {
				drawFooter(sheet.getFooter());
			}

			// 4.改ページ
			if (i != allSheet.size() - 1) {
				flow.newPage();
			}

		}

		return getBinary();

	}

	/**
	 * 帳票を返す。 <br>
	 * ※1つのBookを1つのPDFファイルで返す。<BR>
	 * 1ページにsheetCount分のLedgerSheetを印字する。
	 * 
	 * @param _book LedgerBook
	 * @param maxBlocks 帳票1ページにいくつのLedgerSheetを表示するか
	 * @return 帳票
	 * @throws TException
	 */
	public byte[] getReport(LedgerBook _book, int maxBlocks) throws TException {

		this.book = _book;

		if (isSaveBook) {
			saveBook(_book);
		}

		init();

		document.setFlushPolicy(JCDocument.FLUSH_POLICY_ON_OUTPUT);
		document.setOutputPolicy(JCDocument.OUTPUT_POLICY_IMMEDIATE);

		flow = new JCFlow(document);
		drawer = new LedgerDrawer(document);

		// 初期化
		initWhenBookBreak();

		List<LedgerSheet> allSheet = _book.getSheet();

		int blockCounter = 0;
		refleshFrame();

		// シートの数だけページ作成する
		for (int i = 0; i < allSheet.size(); i++) {

			currentPageNo = i + 1;

			LedgerSheet sheet = allSheet.get(i);

			// ヘッダー描画
			refleshSheetBlock(blockCounter);

			drawHeader(sheet.getHeader());

			// 明細描画
			drawDetail(sheet.getDetail());

			// フッター描画
			if (sheet.getFooter() != null) {
				drawFooter(sheet.getFooter());
			}

			// 改ページ
			if (blockCounter == maxBlocks - 1 && i != allSheet.size() - 1) {
				flow.newPage();
				refleshFrame();
				blockCounter = 0;
			} else {
				blockCounter++;
			}

		}

		return getBinary();

	}

	/**
	 * JCFrameのrefresh
	 */
	protected void refleshFrame() {
		JCFrame currentFrame = flow.getCurrentFrame();
		currentFrame.setLocation(new JCUnit.Point(JCUnit.CM, frameLoacationX, frameLoacationY));
		currentFrame.setSize(new JCUnit.Dimension(JCUnit.CM, frameWidth, frameHeight));
		drawer.reflesh();
	}

	/**
	 * 帳票を返す。 <br>
	 * ※複数のBookを1つのPDFファイルで返す。レイアウトはそれぞれのBookで共通とする。
	 * 
	 * @param books
	 * @return 帳票
	 * @throws TException
	 */
	public byte[] getReport(List<? extends LedgerBook> books) throws TException {

		if (isSaveBook) {
			saveBook(books);
		}

		this.book = books.get(0);

		init();

		document.setFlushPolicy(JCDocument.FLUSH_POLICY_ON_OUTPUT);
		document.setOutputPolicy(JCDocument.OUTPUT_POLICY_IMMEDIATE);

		flow = new JCFlow(document);
		drawer = new LedgerDrawer(document);

		for (int bookCount = 0; bookCount < books.size(); bookCount++) {

			LedgerBook _book = books.get(bookCount);

			this.book = _book;

			// 初期化
			initWhenBookBreak();

			List<LedgerSheet> allSheet = _book.getSheet();

			// シートの数だけページ作成する
			for (int i = 0; i < allSheet.size(); i++) {

				currentPageNo = i + 1;

				// ヘッダー描画
				LedgerSheet sheet = allSheet.get(i);

				refleshFrame();

				drawHeader(sheet.getHeader());

				// 2.明細描画
				drawDetail(sheet.getDetail());

				// 3.フッター描画
				if (sheet.getFooter() != null) {
					drawFooter(sheet.getFooter());
				}

				// 4.改ページ
				if (i != allSheet.size() - 1) {
					flow.newPage();
				}

			}

			if (bookCount != books.size() - 1) {
				flow.newPage();
			}

		}

		return getBinary();

	}

	/**
	 * 帳票を返す ※複数のBookを1つのPDFファイルで返す。レイアウトはBook毎に違ってよい。
	 * 
	 * @param bookAndLayouts
	 * @return 帳票
	 * @throws TException
	 */
	public byte[] getMultiReport(List<LedgerBookAndLayout> bookAndLayouts) throws TException {

		this.book = bookAndLayouts.get(0).getBook();

		init();

		document.setFlushPolicy(JCDocument.FLUSH_POLICY_ON_OUTPUT);
		document.setOutputPolicy(JCDocument.OUTPUT_POLICY_IMMEDIATE);

		flow = new JCFlow(document);
		drawer = new LedgerDrawer(document);

		for (int bookCount = 0; bookCount < bookAndLayouts.size(); bookCount++) {

			// 帳票取り出し
			LedgerBook _book = bookAndLayouts.get(bookCount).getBook();
			// レイアウト取り出し
			TReportLayout layout = bookAndLayouts.get(bookCount).getLayout();
			layout.flow = flow;
			layout.document = document;
			layout.drawer = drawer;
			layout.book = _book;
			layout.init();

			// 初期化
			layout.initWhenBookBreak();

			List<LedgerSheet> allSheet = _book.getSheet();

			// シートの数だけページ作成する
			for (int i = 0; i < allSheet.size(); i++) {

				layout.currentPageNo = i + 1;

				// ヘッダー描画
				LedgerSheet sheet = allSheet.get(i);

				JCFrame currentFrame = flow.getCurrentFrame();
				currentFrame.setLocation(new JCUnit.Point(JCUnit.CM, layout.frameLoacationX, layout.frameLoacationY));
				currentFrame.setSize(new JCUnit.Dimension(JCUnit.CM, layout.frameWidth, layout.frameHeight));
				drawer.reflesh();

				layout.drawHeader(sheet.getHeader());

				// 2.明細描画
				layout.drawDetail(sheet.getDetail());

				// 3.フッター描画
				if (sheet.getFooter() != null) {
					layout.drawFooter(sheet.getFooter());
				}

				// 4.改ページ
				if (i != allSheet.size() - 1) {
					flow.newPage();
				}

			}

			if (bookCount != bookAndLayouts.size() - 1) {
				flow.newPage();
			}

		}

		return getBinary();

	}

	/**
	 * bookを保存する
	 * 
	 * @param _book
	 */
	protected void saveBook(LedgerBook _book) {
		FileUtil.saveObject(_book, _book.getClass().getSimpleName());
	}

	/**
	 * bookを保存する
	 * 
	 * @param books
	 */
	protected void saveBook(List<? extends LedgerBook> books) {
		FileUtil.saveObject(books, books.get(0).getClass().getSimpleName());
	}

	/**
	 * ヘッダー描画
	 * 
	 * @param header
	 * @throws TException
	 */
	protected abstract void drawHeader(LedgerSheetHeader header) throws TException;

	/**
	 * 明細描画
	 * 
	 * @param detail
	 * @throws TException
	 */
	protected abstract void drawDetail(List<LedgerSheetDetail> detail) throws TException;

	/**
	 * フッター描画
	 * 
	 * @param footer
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	protected void drawFooter(@SuppressWarnings("unused") LedgerSheetFooter footer) throws TException {
		// for override
	}

	/**
	 * 1ページに複数Sheetを描画する場合、Sheetが変わった時点での処理があればOverrideして下さい。
	 * 
	 * @param sheetCount 現在のシート。PageBreak直後は0。
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	protected void refleshSheetBlock(@SuppressWarnings("unused") int sheetCount) throws TException {
		// for override
	}

	/**
	 * ページを表示する
	 * 
	 * @param _drawer
	 * @param point
	 * @throws TException
	 */
	protected void drawPageNo(LedgerDrawer _drawer, JCUnit.Point point) throws TException {

		LedgerCellTable cellTable = new LedgerCellTable();
		// 頁
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C01585") + ":")); // 頁
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, getPageNoChar()));
		_drawer.drawText(cellTable, point);

	}

	/**
	 * ページ番号表示文字列を返す
	 * 
	 * @return ページ番号表示文字列
	 */
	protected String getPageNoChar() {
		return Integer.toString(currentPageNo) + "/" + Integer.toString(book.getSheetCount());
	}

	/**
	 * 処理日を表示する
	 * 
	 * @param _drawer
	 * @param point
	 * @throws TException
	 */
	protected void drawOutputDate(LedgerDrawer _drawer, JCUnit.Point point) throws TException {

		LedgerCellTable cellTable = new LedgerCellTable();
		// 処理日
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C00902") + ":"));
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, book.getCurrentDate(getLanguageCode())));
		_drawer.drawText(cellTable, point);

	}

	/**
	 * 時間を表示する
	 * 
	 * @param _drawer
	 * @param point
	 * @throws TException
	 */
	protected void drawTimeStamp(LedgerDrawer _drawer, JCUnit.Point point) throws TException {

		LedgerCellTable cellTable = new LedgerCellTable();
		// 時間
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C00903") + ":"));
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, book.getCurrentTime(getLanguageCode())));
		_drawer.drawText(cellTable, point);

	}

	/**
	 * 帳簿区分を表示する
	 * 
	 * @param _drawer
	 * @param point
	 * @param accountBook
	 * @throws TException
	 */
	protected void drawBookName(LedgerDrawer _drawer, JCUnit.Point point, AccountBook accountBook) throws TException {

		if (isNotOutputAccountBook) {
			// 帳簿区分非表示
			return;
		}

		LedgerCellTable cellTable = new LedgerCellTable();
		// 帳簿区分
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C10961") + ":"));
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, StringUtil.leftBX(
			getWord(AccountBook.getAccountBookName(accountBook)), 12)));
		_drawer.drawText(cellTable, point);

	}

	/**
	 * 表示通貨を表示する
	 * 
	 * @param _drawer
	 * @param point
	 * @param currencyType
	 * @param company
	 * @throws TException
	 */
	protected void drawCurrencyType(LedgerDrawer _drawer, JCUnit.Point point, CurrencyType currencyType, Company company)
		throws TException {

		String currencyCode = company.getAccountConfig().getKeyCurrency().getCode();
		if (CurrencyType.FUNCTIONAL == currencyType) {
			currencyCode = company.getAccountConfig().getFunctionalCurrency().getCode();
		}

		LedgerCellTable cellTable = new LedgerCellTable();
		// 表示通貨
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C10549") + ":"));
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, getShortWord(CurrencyType.getCurrencyTypeName(currencyType))
			+ " " + currencyCode));
		_drawer.drawText(cellTable, point);

	}

	/**
	 * 出力情報を表示する
	 * 
	 * @param _drawer
	 * @param point
	 * @throws TException
	 */
	protected void drawOutputInformation(LedgerDrawer _drawer, JCUnit.Point point) throws TException {

		drawPageNo(_drawer, point);
		drawOutputDate(_drawer, new JCUnit.Point(JCUnit.CM, point.x, point.y + 0.4));
		drawTimeStamp(_drawer, new JCUnit.Point(JCUnit.CM, point.x, point.y + 0.8));

	}

	/**
	 * 出力情報を表示する
	 * 
	 * @param _drawer
	 * @param x
	 * @param y
	 * @throws TException
	 */
	protected void drawOutputInformation(LedgerDrawer _drawer, double x, double y) throws TException {
		drawOutputInformation(_drawer, new JCUnit.Point(JCUnit.CM, x, y));
	}

	/**
	 * 出力情報を表示する
	 * 
	 * @param _drawer
	 * @param x
	 * @param y
	 * @param currency
	 * @throws TException
	 */
	protected void drawOutputInformation(LedgerDrawer _drawer, double x, double y, Currency currency) throws TException {

		drawOutputInformation(_drawer, new JCUnit.Point(JCUnit.CM, x, y));
		LedgerCellTable cellTable = new LedgerCellTable();
		// 通貨
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C00371") + ":"));
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, currency.getCode()));
		_drawer.drawText(cellTable, new JCUnit.Point(JCUnit.CM, x, y + 1.2));

	}

	/**
	 * 出力情報を表示する
	 * 
	 * @param _drawer
	 * @param x
	 * @param y
	 * @param currencyCode
	 * @throws TException
	 */
	protected void drawOutputInformation(LedgerDrawer _drawer, double x, double y, String currencyCode)
		throws TException {

		drawOutputInformation(_drawer, new JCUnit.Point(JCUnit.CM, x, y));
		LedgerCellTable cellTable = new LedgerCellTable();
		// 通貨
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C00371") + ":"));
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, currencyCode));
		_drawer.drawText(cellTable, new JCUnit.Point(JCUnit.CM, x, y + 1.2));

	}

	/**
	 * 出力情報(帳簿、表示通貨を含む)を表示する
	 * 
	 * @param _drawer
	 * @param point
	 * @param accountBook
	 * @param currencyType
	 * @param company
	 * @throws TException
	 */
	protected void drawOutputInformation(LedgerDrawer _drawer, JCUnit.Point point, AccountBook accountBook,
		CurrencyType currencyType, Company company) throws TException {

		drawOutputInformation(_drawer, point);
		drawBookName(_drawer, new JCUnit.Point(JCUnit.CM, point.x, point.y + 1.2), accountBook);
		drawCurrencyType(_drawer, new JCUnit.Point(JCUnit.CM, point.x, point.y + 1.6), currencyType, company);

	}

	/**
	 * 出力情報(帳簿、表示通貨を含む)を表示する
	 * 
	 * @param accountBook
	 * @param currencyType
	 * @param company
	 * @param x
	 * @param y
	 * @throws TException
	 */
	protected void drawOutputInformation(AccountBook accountBook, CurrencyType currencyType, Company company, double x,
		double y) throws TException {

		drawOutputInformation(drawer, new JCUnit.Point(JCUnit.CM, x, y), accountBook, currencyType, company);

	}

	/**
	 * 自～至を返す
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @return 自 yyyy/mm/dd ～至 yyyy/mm/dd
	 */
	protected String getDateRangeStatement(Date dateFrom, Date dateTo) {
		// 自 //至
		String rt = "(" + getWord("C00898") + " " + DateUtil.toYMDString(dateFrom) + " ～ " + getWord("C00899") + " "
			+ DateUtil.toYMDString(dateTo) + ")";
		return rt;
	}

	/**
	 * 配下部門を含む/含まない を文字列で返す
	 * 
	 * @param isIncludeUnderDepartment 配下部門を含むか
	 * @return 配下部門を含む/含まない
	 */
	protected String getIncludeUnderDepartmentStatement(boolean isIncludeUnderDepartment) {
		// 含む
		String rt = getWord("C00461");
		if (!isIncludeUnderDepartment) {
			// 含まない
			rt = getWord("C00460");
		}
		return rt;
	}

	/**
	 * 決算段階を文字列で返す
	 * 
	 * @param settlementStage
	 * @return 決算段階
	 */
	protected String getSettlementStageStagement(int settlementStage) {
		return FormatUtil.settlementLevelFormat(settlementStage, lang);
	}

	/**
	 * 決算段階を文字列で返す
	 * 
	 * @param settlementStage
	 * @param normalBlank trueの場合<br>
	 *            通常の場合はブランク<br>
	 *            以外：n次決算
	 * @return 決算段階
	 */
	protected String getSettlementStageStagement(int settlementStage, boolean normalBlank) {
		return FormatUtil.settlementLevelFormat(settlementStage, lang, normalBlank);
	}

	/**
	 * 更新区分を文字列で返す
	 * 
	 * @param isEntry
	 * @param isApprove
	 * @param isSettle
	 * @return 更新区分
	 */
	protected String getSlipStateStagement(boolean isEntry, boolean isApprove, boolean isSettle) {
		String rt = "";
		if (isEntry) {
			// 登録
			rt = getWord("C01258");
		}
		if (isApprove) {
			if (rt.length() != 0) {
				rt = rt + " ";
			}
			// 承認
			rt = rt + getWord("C01168");
		}
		if (isSettle) {
			if (rt.length() != 0) {
				rt = rt + " ";
			}
			// 更新
			rt = rt + getWord("C00169");
		}

		return rt;

	}

	/**
	 * 更新区分を文字列で返す
	 * 
	 * @param states
	 * @return 更新区分名称
	 */
	protected String getSlipStateStagement(List<SlipState> states) {
		if (states == null) {
			return "";
		}
		String rt = "";
		for (SlipState state : states) {
			rt = rt + getWord(SlipState.getSlipStateName(state)) + " ";
		}
		return rt;
	}

	/**
	 * @return フレームの開始位置の水平座標
	 */
	public double getFrameLoacationX() {
		return frameLoacationX;
	}

	/**
	 * @param frameLoacationX
	 */
	public void setFrameLoacationX(double frameLoacationX) {
		this.frameLoacationX = frameLoacationX;
	}

	/**
	 * @return フレームの終了位置の垂直座標
	 */
	public double getFrameLoacationY() {
		return frameLoacationY;
	}

	/**
	 * @param x
	 * @param y
	 */
	public void setFrameLocation(double x, double y) {
		setFrameLoacationX(x);
		setFrameLoacationY(y);
	}

	/**
	 * @param frameLoacationY
	 */
	public void setFrameLoacationY(double frameLoacationY) {
		this.frameLoacationY = frameLoacationY;
	}

	/**
	 * @return フレームの高さ
	 */
	public double getFrameHeight() {
		return frameHeight;
	}

	/**
	 * @param frameHeight
	 */
	public void setFrameHeight(double frameHeight) {
		this.frameHeight = frameHeight;
	}

	/**
	 * @return フレームの幅
	 */
	public double getFrameWidth() {
		return frameWidth;
	}

	/**
	 * @param frameWidth
	 */
	public void setFrameWidth(double frameWidth) {
		this.frameWidth = frameWidth;
	}

	/**
	 * @param width
	 * @param height
	 */
	public void setFrameSize(double width, double height) {
		setFrameWidth(width);
		setFrameHeight(height);
	}

}
