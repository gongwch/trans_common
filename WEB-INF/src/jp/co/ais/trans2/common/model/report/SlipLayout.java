package jp.co.ais.trans2.common.model.report;

import java.awt.*;
import java.util.List;

import com.klg.jclass.page.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.common.model.format.*;
import jp.co.ais.trans2.common.report.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票のレイアウト基底
 * 
 * @author AIS
 */
public abstract class SlipLayout extends TReportLayout {

	/** 帳票区分を非表示するか */
	protected static boolean isNotOutputSlipAccountBook = ServerConfig.isFlagOn("trans.slip.not.output.accountbook");

	/** true:IFRS区分より発生日は科目の発生日フラグにより制御機能有効＜Server＞ */
	public static boolean allowOccurDateBlank = ServerConfig
		.isFlagOn("trans.slip.detail.allow.occurdate.blank.when.noifrs");

	/** true:BS勘定消込機能有効<Server> */
	public static boolean isUseBS = ServerConfig.isFlagOn("trans.slip.use.bs");

	/** true:BS勘定消込・呼出どちらでもマークを記載<Server> */
	public static boolean isShowAllBS = ServerConfig.isFlagOn("trans.slip.use.bs.sousai.mark");

	/** true:AP勘定消込機能無効<Server> */
	public static boolean isNotUseAP = ServerConfig.isFlagOn("trans.slip.disable.ap.zan");

	/** true:AR勘定消込機能無効<Server> */
	public static boolean isNotUseAR = ServerConfig.isFlagOn("trans.slip.disable.ar.zan");

	/** true:勘定科目は略称を出す有効<Server> */
	public static boolean isUseItemShortName = ServerConfig.isFlagOn("trans.slip.pdf.use.item.shortname");

	/** true:取引先は略称を出す有効<Server> */
	public static boolean isUseCustomerShortName = ServerConfig.isFlagOn("trans.slip.pdf.use.customer.shortname");

	/** true:付替仕訳生成時に相手会社の勘定科目を表示する */
	public static boolean isUseTransferItemName = ServerConfig.isFlagOn("trans.slip.use.transfer.item.name");

	/** ヘッダー単語定義 */

	/** ヘッダー：伝票日付 */
	protected String HEADER_SLIP_DATE_WORD = "C00599";

	/** ヘッダー：入力部門 */
	protected String HEADER_DEPARTMENT_NAME_WORD = "C01280";

	/** ヘッダー：入力者 */
	protected String HEADER_EMPLOYEE_NAME_WORD = "C01278";

	/** ヘッダー：伝票番号 */
	protected String HEADER_SLIP_NO_WORD = "C00605";

	/** ヘッダー：修正 */
	protected String HEADER_UPDATE_WORD = "C01760";

	/** ヘッダー：回 */
	protected String HEADER_UPDATE_COUNT_WORD = "C01761";

	/** ヘッダー：証憑番号 */
	protected String HEADER_VOUCHER_NO_WORD = "C01178";

	/** ヘッダー：伝票摘要 */
	protected String HEADER_SLIP_REMARK_WORD = "C00569";

	/** ヘッダー：基軸通貨 */
	protected String HEADER_KEY_CURRENCY_WORD = "C00907";

	/** ヘッダー：帳簿区分 */
	protected String HEADER_ACCOUNT_BOOK_NAME_WORD = "C10961";

	/** ヘッダー：承認印1 */
	protected String HEADER_ACCEPT_STAMP_1_WORD = "C01762";

	/** ヘッダー：承認印2 */
	protected String HEADER_ACCEPT_STAMP_2_WORD = "C11436";

	/** ヘッダー：承認印3 */
	protected String HEADER_ACCEPT_STAMP_3_WORD = "C11437";

	/** 明細単語定義 */

	/** 明細：借方金額 */
	protected String DETAIL_DR_AMOUNT_WORD = "C01557";

	/** 明細：貸方金額 */
	protected String DETAIL_CR_AMOUNT_WORD = "C01559";

	/** 明細：行摘要 */
	protected String DETAIL_ROW_REMARK_WORD = "C00119";

	/** 明細：計上部門/取引先/社員 */
	protected String DETAIL_CUSTOMER_WORD = "C11071";

	/** 明細：通貨 */
	protected String DETAIL_CURRENCY_WORD = "C00371";

	/** 明細：発生日/税 */
	protected String DETAIL_TAX_WORD = "C11435";

	/** 明細：管理1/2/3 */
	protected String DETAIL_MANAGEMENT1TO3_WORD = "C11072";

	/** 明細：管理4/5/6 */
	protected String DETAIL_MANAGEMENT4TO6_WORD = "C11073";

	/** 明細：非会計明細1/2/3 */
	protected String DETAIL_NON_ACCOUNT_WORD = "C01763";

	/** 明細：合計 */
	protected String DETAIL_SUM_WORD = "C00165";

	/** 明細：外貨計1 */
	protected String DETAIL_FOREIGN_1_WORD = "C01764";

	/** 明細：外貨計2 */
	protected String DETAIL_FOREIGN_2_WORD = "C01765";

	/** 明細：外貨計3 */
	protected String DETAIL_FOREIGN_3_WORD = "C11608";

	/** 明細：外貨計4 */
	protected String DETAIL_FOREIGN_4_WORD = "C11609";

	/** 列幅定義 */

	/** 行高さ */
	protected double ROW_HEIGHT = 0.50d;

	/** ヘッダータイトル幅 */
	protected double HEADER_TITLE_WIDTH = 2.5d;

	/** ヘッダー部門幅 */
	protected double HEADER_DEPARTMENT_WIDTH = 9.0d;

	/** 空行幅 */
	protected double REST_WIDTH = 5.85d;

	/** 行番号幅 */
	protected double ROW_NO_WIDTH = 0.7d;

	/** 科目幅 */
	protected double ITEM_WIDTH = 4.6d;

	/** 金額幅 */
	protected double AMOUNT_WIDTH = 3.10d;

	/** 通貨幅 */
	protected double CURRENCY_WIDTH = 1.8d;

	/** 摘要幅 */
	protected double REMARK_WIDTH = 4.5d;

	/** 取引先幅 */
	protected double CUSTOMER_WIDTH = 3.6d;

	/** 管理1～3幅 */
	protected double MANAGEMENT1TO3_WIDTH = 2.6d;

	/** 管理4～6幅 */
	protected double MANAGEMENT4TO6_WIDTH = 2.6d;

	/** 非会計明細幅 */
	protected double NON_ACCOUNT_WIDTH = 2.25d;

	/** 桁数定義 */

	/** 帳簿区分桁数 */
	protected int ACCOUNT_BOOK_NAME_LENGTH = 12;

	/** 修正回数桁数 */
	protected int UPDATE_COUNT_LENGTH = 3;

	/** 承認印1桁数 */
	protected int ACCEPT_STAMP_1_LENGTH = 6;

	/** 承認印2桁数 */
	protected int ACCEPT_STAMP_2_LENGTH = 6;

	/** 承認印3桁数 */
	protected int ACCEPT_STAMP_3_LENGTH = 6;

	/** 科目桁数 */
	protected int ITEM_LENGTH = 28;

	/** 消費税桁数 */
	protected int TAX_LENGTH = 10;

	/** 摘要桁数 */
	protected int REMARK_LENGTH = 27;

	/** 取引先桁数 */
	protected int CUSTOMER_LENGTH = 21;

	/** 管理1～3桁数 */
	protected int MANAGEMENT1TO3_LENGTH = 15;

	/** 管理4～6桁数 */
	protected int MANAGEMENT4TO6_LENGTH = 15;

	/** 非会計明細桁数 */
	protected int NON_ACCOUNT_LENGTH = 13;

	/** ヘッダー：入力部門桁数 */
	protected int HEADER_DEPARTMENT_NAME_LENGTH = 55;

	/** スタイル定義 */

	/** 枠スタイル */
	protected JCDrawStyle rectanglesStyle;

	/** 左スタイル(9) */
	protected JCTextStyle mLeftStyle;

	/** 中央スタイル(9) */
	protected JCTextStyle mCenterStyle;

	/** 右スタイル(9) */
	protected JCTextStyle mRightStyle;

	/** 左スタイル(10) */
	protected JCTextStyle lLeftStyle;

	/** 中央スタイル(10) */
	protected JCTextStyle lCenterStyle;

	/** 非会計明細1 */
	protected JCTextStyle nonAccount1Style;

	/** 非会計明細2 */
	protected JCTextStyle nonAccount2Style;

	/** 非会計明細3 */
	protected JCTextStyle nonAccount3Style;

	/** 枠スタイル色 */
	protected Color rectanglesStyleColor = new Color(190, 190, 190);

	/** 科目のキャプション */
	protected String itemCaption = null;

	/** 定義持つ */
	protected SlipLayoutDefine layoutDefine = null;

	/**
	 * @param langMessage
	 * @param type
	 * @throws TException
	 */
	public SlipLayout(String langMessage, int type) throws TException {
		super(langMessage, type);
	}

	/**
	 * @param langMessage
	 * @throws TException
	 */
	public SlipLayout(String langMessage) throws TException {
		super(langMessage);
	}

	/**
	 * ブックの取得
	 * 
	 * @return ブック
	 */
	protected SlipBook getBook() {
		return (SlipBook) book;
	}

	@Override
	protected void initFrameSize() {
		setFrameSize(28.9, 21.0);
		setFrameLocation(0.4, 1.5);
	}

	@Override
	protected void initWhenBookBreak() {

		Company company = getBook().getCompany();
		AccountConfig ac = company.getAccountConfig();
		itemCaption = ac.getItemName() + "/" + ac.getSubItemName();
		if (ac.isUseDetailItem()) {
			itemCaption = itemCaption + "/" + ac.getDetailItemName();
		}
		nonAccount1Style = getNonAccountingStyle(company.getAccountConfig().getNonAccounting1());
		nonAccount2Style = getNonAccountingStyle(company.getAccountConfig().getNonAccounting2());
		nonAccount3Style = getNonAccountingStyle(company.getAccountConfig().getNonAccounting3());

	}

	@Override
	protected void initStyle() {
		super.initStyle();
		rectanglesStyle = new JCDrawStyle();
		rectanglesStyle.setFillForegroundColor(rectanglesStyleColor);
		mLeftStyle = super.createLeftStyle(9);
		mCenterStyle = super.createCenterStyle(9);
		mRightStyle = super.createRightStyle(9);
		lLeftStyle = super.createLeftStyle(10);
		lCenterStyle = super.createCenterStyle(10);
	}

	/**
	 * 非会計明細のスタイルを返す
	 * 
	 * @param nonAccounting
	 * @return 非会計明細のスタイル
	 */
	protected JCTextStyle getNonAccountingStyle(NonAccountingDivision nonAccounting) {

		if (NonAccountingDivision.NUMBER == nonAccounting) {
			return super.createRightStyle(7);
		} else if (NonAccountingDivision.YMD_DATE == nonAccounting || NonAccountingDivision.YMDHM_DATE == nonAccounting) {
			return super.createCenterStyle(7);
		}
		return super.createLeftStyle(7);

	}

	@Override
	protected void drawHeader(LedgerSheetHeader header) throws TException {

		Company company = getBook().getCompany();

		SlipBookHeader h = (SlipBookHeader) header;

		// タイトル
		drawer.drawText(titleStyle, h.getTitle(), 0.0, 0.0);

		// 会社名称
		drawer.drawText(lLeftStyle, company.getName(), 0.0, 0.0);

		// 伝票日付
		drawer.drawText(lCenterStyle, getWord(HEADER_SLIP_DATE_WORD) + "：" + DateUtil.toYMDString(h.getSlipDate()),
			0.0, 1.4);

		// 入力部門、入力者
		double startY = 2.55;
		double rowHeight = ROW_HEIGHT;
		double endY = startY + rowHeight + rowHeight + rowHeight;
		LedgerCellTable cellTable = new LedgerCellTable();
		// 入力部門
		cellTable.addCell(mCenterStyle, HEADER_TITLE_WIDTH, getShortWord(HEADER_DEPARTMENT_NAME_WORD));
		cellTable.addCell(mLeftStyle, HEADER_DEPARTMENT_WIDTH,
			StringUtil.leftBX(getHeaderDepartment(h), HEADER_DEPARTMENT_NAME_LENGTH));
		// 入力者
		cellTable.addCell(mCenterStyle, HEADER_TITLE_WIDTH, getWord(HEADER_EMPLOYEE_NAME_WORD));
		cellTable.addCell(mLeftStyle, HEADER_DEPARTMENT_WIDTH,
			StringUtil.leftBX(getIraiEmployeeInfo(h), HEADER_DEPARTMENT_NAME_LENGTH));
		cellTable.addCell(mCenterStyle, REST_WIDTH, "");
		drawer.drawText(cellTable, 0.0, startY);

		// 伝票番号
		cellTable = new LedgerCellTable();
		cellTable.addCell(mCenterStyle, HEADER_TITLE_WIDTH, getWord(HEADER_SLIP_NO_WORD));
		cellTable.addCell(mLeftStyle, HEADER_DEPARTMENT_WIDTH - 5.5d, Util.avoidNull(h.getSlipNo()));
		cellTable.addCell(mRightStyle, 3.5d, getSettlementStageStagement(h.getSettlementStage(), true), 21); // 決算段階
		// 修正 回
		cellTable.addCell(
			mRightStyle,
			1.5d,
			getShortWord(HEADER_UPDATE_WORD) + h.getUpdateCount()
				+ StringUtil.leftBX(getWord(HEADER_UPDATE_COUNT_WORD), UPDATE_COUNT_LENGTH));
		cellTable.addCell(mRightStyle, 0.5d, ""); // 右余白

		// 証憑番号
		cellTable.addCell(mCenterStyle, HEADER_TITLE_WIDTH, getWord(HEADER_VOUCHER_NO_WORD));
		cellTable.addCell(mLeftStyle, HEADER_DEPARTMENT_WIDTH, Util.avoidNull(h.getVoucherNo()));
		cellTable.addCell(mCenterStyle, REST_WIDTH, "");
		drawer.drawText(cellTable, 0.0, startY + rowHeight);

		// 伝票摘要
		cellTable = new LedgerCellTable();
		cellTable.addCell(mCenterStyle, HEADER_TITLE_WIDTH, getWord(HEADER_SLIP_REMARK_WORD));
		cellTable.addCell(mLeftStyle, HEADER_DEPARTMENT_WIDTH + HEADER_TITLE_WIDTH + HEADER_DEPARTMENT_WIDTH,
			getSlipRemark(h));
		cellTable.addCell(mCenterStyle, REST_WIDTH, "");
		drawer.drawText(cellTable, 0.0, startY + rowHeight + rowHeight);

		// 塗りつぶし
		double startX = 0.0;
		double endX = HEADER_TITLE_WIDTH;

		drawer.drawRectangles(rectanglesStyle, startX, startY, endX, endY);

		startX = HEADER_TITLE_WIDTH + HEADER_DEPARTMENT_WIDTH;
		endX = startX + HEADER_TITLE_WIDTH;
		drawer.drawRectangles(rectanglesStyle, startX, startY, endX, startY + rowHeight + rowHeight);

		// 枠線
		startX = 0.0;
		endX = HEADER_TITLE_WIDTH + HEADER_DEPARTMENT_WIDTH + HEADER_TITLE_WIDTH + HEADER_DEPARTMENT_WIDTH;
		drawHeaderFrameBorder();
		drawer.drawLine(thinLineStyle, startX, startY + rowHeight, endX, startY + rowHeight);
		drawer.drawLine(thinLineStyle, startX, startY + rowHeight + rowHeight, endX, startY + rowHeight + rowHeight);
		startX = HEADER_TITLE_WIDTH;
		drawer.drawLine(thinLineStyle, startX, startY, startX, endY);
		startX = startX + HEADER_DEPARTMENT_WIDTH;
		drawer.drawLine(thinLineStyle, startX, startY, startX, startY + rowHeight + rowHeight);
		startX = startX + HEADER_TITLE_WIDTH;
		drawer.drawLine(thinLineStyle, startX, startY, startX, startY + rowHeight + rowHeight);

		// 出力情報
		drawOutputInformation(drawer, new JCUnit.Point(JCUnit.CM, 0, 0));
		cellTable = new LedgerCellTable();
		// 基軸通貨
		cellTable.addCell(rightStyle, LedgerCell.WIDTH_REST, getWord(HEADER_KEY_CURRENCY_WORD) + ":");
		cellTable.addCell(rightStyle, 2.0, company.getAccountConfig().getKeyCurrency().getCode());
		drawer.drawText(cellTable, 0.0, 1.2);

		if (!isNotOutputSlipAccountBook) {
			// 帳簿区分表示

			cellTable = new LedgerCellTable();
			// 帳簿区分
			cellTable.addCell(rightStyle, LedgerCell.WIDTH_REST, getWord(HEADER_ACCOUNT_BOOK_NAME_WORD) + ":");
			cellTable.addCell(rightStyle, 2.0, StringUtil.leftBX(
				getWord(AccountBook.getAccountBookName(h.getAccountBook())), ACCOUNT_BOOK_NAME_LENGTH));
			drawer.drawText(cellTable, 0.0, 1.6);
		}

		// 承認印
		double approveMark_Y = 2.10;
		endY = 4.05;
		drawer.drawFrameBorder(middleLineStyle, drawer.getLedgerDoubleWidth() - 4.8, approveMark_Y,
			drawer.getLedgerDoubleWidth(), endY);
		drawer.drawLine(thinLineStyle, drawer.getLedgerDoubleWidth() - 4.8, approveMark_Y + rowHeight,
			drawer.getLedgerDoubleWidth(), approveMark_Y + rowHeight);
		drawer.drawLine(thinLineStyle, drawer.getLedgerDoubleWidth() - 3.2, approveMark_Y,
			drawer.getLedgerDoubleWidth() - 3.2, endY);
		drawer.drawLine(thinLineStyle, drawer.getLedgerDoubleWidth() - 1.6, approveMark_Y,
			drawer.getLedgerDoubleWidth() - 1.6, endY);

		cellTable = new LedgerCellTable();
		cellTable.addCell(mCenterStyle, LedgerCell.WIDTH_REST, "");
		// 承認印1
		cellTable.addCell(mLeftStyle, 1.6,
			StringUtil.leftBX(getShortWord(HEADER_ACCEPT_STAMP_1_WORD), ACCEPT_STAMP_1_LENGTH));
		// 承認印2
		cellTable.addCell(mLeftStyle, 1.6,
			StringUtil.leftBX(getShortWord(HEADER_ACCEPT_STAMP_2_WORD), ACCEPT_STAMP_2_LENGTH));
		// 承認印3
		cellTable.addCell(mLeftStyle, 1.4,
			StringUtil.leftBX(getShortWord(HEADER_ACCEPT_STAMP_3_WORD), ACCEPT_STAMP_3_LENGTH));
		drawer.drawText(cellTable, 0.0, approveMark_Y);

	}

	/**
	 * 明細ヘッダー描画
	 * 
	 * @throws TException
	 */
	protected void drawDetailTitle() throws TException {

		double rowHeight = ROW_HEIGHT;

		// 明細ヘッダーと枠線
		double startX = 0.0;
		double endX = HEADER_TITLE_WIDTH + HEADER_DEPARTMENT_WIDTH + HEADER_TITLE_WIDTH + HEADER_DEPARTMENT_WIDTH
			+ REST_WIDTH;
		final double detailTopY = getDetailTopY();
		double endY = detailTopY + 0.80 + rowHeight * 3 * (getBook().getMaxRowCount() + 1);
		drawer.drawRectangles(rectanglesStyle, startX, detailTopY, endX, detailTopY + 0.80);
		drawer.drawRectangles(rectanglesStyle, startX, detailTopY, ROW_NO_WIDTH, endY);
		drawer.drawFrameBorder(middleLineStyle, startX, detailTopY, endX, endY - rowHeight * 3);
		drawer.drawFrameBorder(middleLineStyle, startX, endY - rowHeight * 3, ROW_NO_WIDTH + ITEM_WIDTH + AMOUNT_WIDTH
			+ AMOUNT_WIDTH + CURRENCY_WIDTH, endY);

		drawer.drawLine(middleLineStyle, startX, detailTopY + 0.80, endX, detailTopY + 0.80);
		startX = startX + ROW_NO_WIDTH;
		drawer.drawLine(middleLineStyle, startX, detailTopY, startX, endY);
		startX = startX + ITEM_WIDTH;
		drawer.drawLine(thinLineStyle, startX, detailTopY, startX, endY);
		startX = startX + AMOUNT_WIDTH;
		drawer.drawLine(thinLineStyle, startX, detailTopY, startX, endY);
		startX = startX + AMOUNT_WIDTH;
		drawer.drawLine(middleLineStyle, startX, detailTopY, startX, endY);
		startX = startX + CURRENCY_WIDTH;
		drawer.drawLine(middleLineStyle, startX, detailTopY, startX, endY);
		startX = startX + REMARK_WIDTH;
		drawer.drawLine(thinLineStyle, startX, detailTopY, startX, endY - rowHeight * 3);
		startX = startX + CUSTOMER_WIDTH;
		drawer.drawLine(thinLineStyle, startX, detailTopY, startX, endY - rowHeight * 3);
		if (MANAGEMENT1TO3_WIDTH != 0) {
			startX = startX + MANAGEMENT1TO3_WIDTH;
			drawer.drawLine(thinLineStyle, startX, detailTopY, startX, endY - rowHeight * 3);
		}
		if (MANAGEMENT4TO6_WIDTH != 0) {
			startX = startX + MANAGEMENT4TO6_WIDTH;
			drawer.drawLine(thinLineStyle, startX, detailTopY, startX, endY - rowHeight * 3);
		}

		// 横線
		startX = 0.0;
		double horizonLineY = detailTopY + 0.80;
		for (int i = 0; i < getBook().getMaxRowCount() - 1; i++) {
			horizonLineY = horizonLineY + rowHeight * 3;
			drawer.drawLine(thinLineStyle, startX, horizonLineY, endX, horizonLineY);
		}

		horizonLineY = horizonLineY + rowHeight * 4;
		endX = ROW_NO_WIDTH + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH;
		drawer.drawLine(thinLineStyle, startX, horizonLineY, endX, horizonLineY);

		horizonLineY = horizonLineY + rowHeight;
		drawer.drawLine(thinLineStyle, startX, horizonLineY, endX, horizonLineY);

		// 明細ヘッダー
		LedgerCellTable cellTable = new LedgerCellTable();
		cellTable.addCell(mCenterStyle, ROW_NO_WIDTH, "");
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, itemCaption);
		// 借方金額
		cellTable.addCell(mCenterStyle, AMOUNT_WIDTH, getWord(DETAIL_DR_AMOUNT_WORD));
		// 貸方金額
		cellTable.addCell(mCenterStyle, AMOUNT_WIDTH, getWord(DETAIL_CR_AMOUNT_WORD));
		cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, "");
		// 行摘要
		cellTable.addCell(mCenterStyle, REMARK_WIDTH, getWord(DETAIL_ROW_REMARK_WORD));
		// 計上部門/取引先/社員
		cellTable.addCell(mCenterStyle, CUSTOMER_WIDTH,
			StringUtil.leftBX(getWord(DETAIL_CUSTOMER_WORD), CUSTOMER_LENGTH));
		// 管理1/2/3
		cellTable.addCell(mCenterStyle, MANAGEMENT1TO3_WIDTH,
			StringUtil.leftBX(getWord(DETAIL_MANAGEMENT1TO3_WORD), MANAGEMENT1TO3_LENGTH));
		// 管理4/5/6
		cellTable.addCell(mCenterStyle, MANAGEMENT4TO6_WIDTH,
			StringUtil.leftBX(getWord(DETAIL_MANAGEMENT4TO6_WORD), MANAGEMENT4TO6_LENGTH));
		// 非会計明細
		cellTable.addCell(mCenterStyle, NON_ACCOUNT_WIDTH,
			StringUtil.leftBX(getShortWord(DETAIL_NON_ACCOUNT_WORD), NON_ACCOUNT_LENGTH));

		double detaiLineY = getDetailTopY() + 0.175d;
		drawer.drawText(cellTable, 0.0, detaiLineY);

		cellTable = new LedgerCellTable();
		cellTable.addCell(mCenterStyle, ROW_NO_WIDTH + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH, "");
		// 通貨
		cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, getWord(DETAIL_CURRENCY_WORD));
		cellTable.addCell(mCenterStyle, REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH
			+ NON_ACCOUNT_WIDTH, "");

		detaiLineY = getDetailTopY();
		drawer.drawText(cellTable, 0.0, detaiLineY);

		cellTable = new LedgerCellTable();
		cellTable.addCell(mCenterStyle, ROW_NO_WIDTH + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH, "");
		// 発生日/税
		cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, StringUtil.leftBX(getWord(DETAIL_TAX_WORD), TAX_LENGTH));
		cellTable.addCell(mCenterStyle, REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH
			+ NON_ACCOUNT_WIDTH, "");

		detaiLineY = getDetailTopY() + 0.35d;
		drawer.drawText(cellTable, 0.0, detaiLineY);

		// 合計
		cellTable = new LedgerCellTable();
		cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, getWord(DETAIL_SUM_WORD));
		cellTable.addCell(mCenterStyle, AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH + REMARK_WIDTH + CUSTOMER_WIDTH
			+ MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH, "");
		detaiLineY = getDetailTopY() + 11.30D;
		drawer.drawText(cellTable, 0.0, detaiLineY);

		// 外貨計1
		cellTable = new LedgerCellTable();
		cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, getWord(DETAIL_FOREIGN_1_WORD));
		cellTable.addCell(mCenterStyle, AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH + REMARK_WIDTH + CUSTOMER_WIDTH
			+ MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH, "");

		detaiLineY = detaiLineY + ROW_HEIGHT;
		drawer.drawText(cellTable, 0.0, detaiLineY);

		// 外貨計2
		cellTable = new LedgerCellTable();
		cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, getWord(DETAIL_FOREIGN_2_WORD));
		cellTable.addCell(mCenterStyle, AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH + REMARK_WIDTH + CUSTOMER_WIDTH
			+ MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH, "");

		detaiLineY = detaiLineY + ROW_HEIGHT;
		drawer.drawText(cellTable, 0.0, detaiLineY);

		SlipBookFooter f = (SlipBookFooter) getBook().getLastSheet().getFooter();
		if (f != null && f.getMaxForeignCurrencyCount() > 2) {
			// 外貨出力件数＞2の場合、出力

			// 明細ヘッダーと枠線
			startX = ROW_NO_WIDTH + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH;
			endX = startX + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH;
			endY = detailTopY + 0.80 + rowHeight * 3 * (getBook().getMaxRowCount() + 1);
			drawer.drawFrameBorder(middleLineStyle, startX, endY - rowHeight * 2, endX, endY);

			// 縦線
			double x = startX + ITEM_WIDTH;
			drawer.drawLine(thinLineStyle, x, endY, x, endY - rowHeight * 2);
			x += AMOUNT_WIDTH;
			drawer.drawLine(thinLineStyle, x, endY, x, endY - rowHeight * 2);
			x += AMOUNT_WIDTH;
			drawer.drawLine(thinLineStyle, x, endY, x, endY - rowHeight * 2);

			double y = endY - ROW_HEIGHT;
			drawer.drawLine(thinLineStyle, startX, y, endX, y);

			// y初期化
			y = endY - ROW_HEIGHT * 2;

			for (int index = 2; index < f.getMaxForeignCurrencyCount(); index++) {

				String word = null;
				if (index == 2) {
					word = getWord(DETAIL_FOREIGN_3_WORD);
				} else {
					word = getWord(DETAIL_FOREIGN_4_WORD);
				}

				cellTable = new LedgerCellTable();
				// 行番号
				cellTable.addCell(mLeftStyle, ROW_NO_WIDTH + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH,
					"");
				// 単語
				cellTable.addCell(mCenterStyle, ITEM_WIDTH, word);
				// 余白
				cellTable.addCell(mCenterStyle, REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH
					+ MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH - ITEM_WIDTH, "");
				drawer.drawText(cellTable, 0.0, y);

				y += ROW_HEIGHT;
			}
		}
	}

	@Override
	protected void drawDetail(List<LedgerSheetDetail> detail) throws TException {

		// 1.明細ヘッダー描画
		drawDetailTitle();

		Company company = getBook().getCompany();

		double y = getDetailTopY() + 0.80d;
		for (int i = 0; i < detail.size(); i++) {

			SlipBookDetail row = (SlipBookDetail) detail.get(i);
			SWK_DTL slipDetail = row.getSlipDetail();
			List<String> remark = StringUtil.getParagraphString(slipDetail.getSWK_GYO_TEK(), REMARK_LENGTH, 3);

			LedgerCellTable cellTable = new LedgerCellTable();

			// 1行目
			// 行番号
			cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, Integer.toString(row.getRowNo()));
			// 科目
			cellTable.addCell(mLeftStyle, ITEM_WIDTH, StringUtil.leftBX(getItem(slipDetail), ITEM_LENGTH));

			// 外貨
			String amount = FormatUtil.foreingAmountFormat(company.getAccountConfig().getKeyCurrency().getCode(),
				slipDetail.getSWK_CUR_CODE(), slipDetail.getSWK_IN_KIN(), slipDetail.getCUR_DEC_KETA());

			if (slipDetail.isDR()) {
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, amount);
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			} else {
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, amount);
			}

			// 基軸通貨も出力プロパティ対応
			String currencyCode = slipDetail.getSWK_CUR_CODE();
			if (!isShowDetailKeyCurrencyCode()) {
				currencyCode = FormatUtil.currencyFormat(company.getAccountConfig().getKeyCurrency().getCode(),
					currencyCode);
			}
			// 通貨
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, currencyCode);
			// 行摘要
			cellTable.addCell(mLeftStyle, REMARK_WIDTH, remark.get(0));

			// 計上部門
			cellTable
				.addCell(mLeftStyle, CUSTOMER_WIDTH, StringUtil.leftBX(getDepartment(slipDetail), CUSTOMER_LENGTH));
			// 管理1
			cellTable.addCell(mLeftStyle, MANAGEMENT1TO3_WIDTH,
				StringUtil.leftBX(getManagement1(slipDetail), MANAGEMENT1TO3_LENGTH));
			// 管理4
			cellTable.addCell(mLeftStyle, MANAGEMENT4TO6_WIDTH,
				StringUtil.leftBX(getManagement4(slipDetail), MANAGEMENT4TO6_LENGTH));
			// 非会計明細1
			cellTable.addCell(nonAccount1Style, NON_ACCOUNT_WIDTH,
				StringUtil.leftBX(Util.avoidNull(slipDetail.getSWK_HM_1()), NON_ACCOUNT_LENGTH));

			drawer.drawText(cellTable, 0.0, y);

			y = y + ROW_HEIGHT;

			// 2行目
			cellTable = new LedgerCellTable();
			cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
			// 補助科目
			cellTable.addCell(mLeftStyle, ITEM_WIDTH, StringUtil.leftBX(getSubItem(slipDetail), ITEM_LENGTH));
			// レート
			String rate = FormatUtil.rateFormat(company.getAccountConfig().getKeyCurrency().getCode(),
				slipDetail.getSWK_CUR_CODE(), slipDetail.getSWK_CUR_RATE());

			if (slipDetail.isDR()) {
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, rate);
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			} else {
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, rate);
			}
			// 発生日
			String occurDate = DateUtil.toYMDString(slipDetail.getHAS_DATE());
			if (isAllowOccurDateBlank()) {

				if (!slipDetail.isUseItemOccurDate()) {
					// 科目が発生日未使用の場合、発生日はブランクにする
					occurDate = "";
				}
			}
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, occurDate);

			// 行摘要
			cellTable.addCell(mLeftStyle, REMARK_WIDTH, remark.get(1));
			// 取引先
			cellTable.addCell(mLeftStyle, CUSTOMER_WIDTH, StringUtil.leftBX(getCustomer(slipDetail), CUSTOMER_LENGTH));
			// 管理2
			cellTable.addCell(mLeftStyle, MANAGEMENT1TO3_WIDTH,
				StringUtil.leftBX(getManagement2(slipDetail), MANAGEMENT1TO3_LENGTH));
			// 管理5
			cellTable.addCell(mLeftStyle, MANAGEMENT4TO6_WIDTH,
				StringUtil.leftBX(getManagement5(slipDetail), MANAGEMENT4TO6_LENGTH));
			// 非会計明細2
			cellTable.addCell(nonAccount2Style, NON_ACCOUNT_WIDTH,
				StringUtil.leftBX(Util.avoidNull(slipDetail.getSWK_HM_2()), NON_ACCOUNT_LENGTH));

			drawer.drawText(cellTable, 0.0, y);

			y = y + ROW_HEIGHT;

			// 3行目
			cellTable = new LedgerCellTable();
			int aparKbn = slipDetail.getSWK_APAR_KESI_KBN();

			// BS勘定マーク
			String mark = "";
			if (isUseBS) {
				if (isShowAllBS) {
					// 消込区分に応じてすべて記載
					if (slipDetail.getSWK_KESI_KBN() == SWK_DTL.KESI_KBN.BASE) {
						mark = getWord("C03191"); // ＊ 元
					} else if (slipDetail.getSWK_KESI_KBN() == SWK_DTL.KESI_KBN.FORWARD) {
						mark = getWord("C11766"); // ※
					}
				} else {
					// 従来の処理 行番号記載のもののみ記載
					if (slipDetail.getSWK_SOUSAI_GYO_NO() != null) {
						if (slipDetail.getSWK_KESI_KBN() == SWK_DTL.KESI_KBN.BASE) {
							mark = getWord("C03191"); // ＊ 元
						} else {
							mark = getWord("C11766"); // ※
						}
					}
				}
			}
			if (!isNotUseAP
				&& (aparKbn == SWK_DTL.APAR_KESI_KBN.AP_BASE || aparKbn == SWK_DTL.APAR_KESI_KBN.AP_FORWARD)) {

				if (aparKbn == SWK_DTL.APAR_KESI_KBN.AP_BASE) {
					mark += getWord("C03191"); // ＊ 元
				} else {
					mark += getWord("C11766"); // ※
				}
			}
			if (!isNotUseAR
				&& (aparKbn == SWK_DTL.APAR_KESI_KBN.AR_BASE || aparKbn == SWK_DTL.APAR_KESI_KBN.AR_FORWARD)) {

				if (aparKbn == SWK_DTL.APAR_KESI_KBN.AR_BASE) {
					mark += getWord("C03191"); // ＊ 元
				} else {
					mark += getWord("C11766"); // ※
				}
			}
			cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, mark);

			// 内訳科目
			cellTable.addCell(mLeftStyle, ITEM_WIDTH, StringUtil.leftBX(getDetailItem(slipDetail), ITEM_LENGTH));
			// 邦貨
			amount = NumberFormatUtil.formatNumber(slipDetail.getSWK_KIN(), company.getAccountConfig().getKeyCurrency()
				.getDecimalPoint());
			if (slipDetail.isDR()) {
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, amount);
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			} else {
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, amount);
			}
			// 税
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, StringUtil.leftBX(getTax(slipDetail), TAX_LENGTH));
			// 行摘要
			cellTable.addCell(mLeftStyle, REMARK_WIDTH, remark.get(2));
			// 社員
			cellTable.addCell(mLeftStyle, CUSTOMER_WIDTH, StringUtil.leftBX(getEmployee(slipDetail), CUSTOMER_LENGTH));
			// 管理3
			cellTable.addCell(mLeftStyle, MANAGEMENT1TO3_WIDTH,
				StringUtil.leftBX(getManagement3(slipDetail), MANAGEMENT1TO3_LENGTH));
			// 管理6
			cellTable.addCell(mLeftStyle, MANAGEMENT4TO6_WIDTH,
				StringUtil.leftBX(getManagement6(slipDetail), MANAGEMENT4TO6_LENGTH));
			// 非会計明細3
			cellTable.addCell(nonAccount3Style, NON_ACCOUNT_WIDTH,
				StringUtil.leftBX(Util.avoidNull(slipDetail.getSWK_HM_3()), NON_ACCOUNT_LENGTH));

			drawer.drawText(cellTable, 0.0, y);

			y = y + ROW_HEIGHT;
		}

	}

	/**
	 * @return true:発生日ブランク可能
	 */
	protected boolean isAllowOccurDateBlank() {
		Company company = getBook().getCompany();
		return allowOccurDateBlank && !company.getAccountConfig().isUseIfrs();
	}

	/**
	 * @param h
	 * @return 伝票摘要
	 */
	protected String getSlipRemark(SlipBookHeader h) {
		if (layoutDefine != null && layoutDefine.isNotShowHeaderSlipRemarkCode()) {
			return Util.avoidNull(h.getRemark());
		}

		return Util.avoidNull(h.getRemarkCode()) + " " + Util.avoidNull(h.getRemark());
	}

	/**
	 * @param h
	 * @return ヘッダー入力部門
	 */
	protected String getHeaderDepartment(SlipBookHeader h) {
		if (layoutDefine != null && layoutDefine.isNotShowHeaderDepartmentCode()) {
			return Util.avoidNull(h.getDepartmentName());
		}

		return Util.avoidNull(h.getDepartmentCode()) + " " + Util.avoidNull(h.getDepartmentName());
	}

	/**
	 * 入力者情報の取得
	 * 
	 * @param h ヘッダー
	 * @return ヘッダー入力者情報
	 */
	protected String getIraiEmployeeInfo(SlipBookHeader h) {
		if (layoutDefine != null && layoutDefine.isNotShowHeaderEmployeeCode()) {
			return Util.avoidNull(h.getEmployeeName());
		}

		return Util.avoidNull(h.getEmployeeCode()) + " " + Util.avoidNull(h.getEmployeeName());
	}

	/**
	 * @param slipDetail
	 * @return 計上部門
	 */
	protected String getDepartment(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowDepartmentCode()) {
			return Util.avoidNull(slipDetail.getSWK_DEP_NAME());
		}

		return Util.avoidNull(slipDetail.getSWK_DEP_CODE()) + " " + Util.avoidNull(slipDetail.getSWK_DEP_NAME());
	}

	/**
	 * @param slipDetail
	 * @return 科目
	 */
	protected String getItem(SWK_DTL slipDetail) {
		String name = "";
		if (!isUseItemShortName) {
			name = Util.avoidNull(slipDetail.getSWK_KMK_NAME());
		} else {
			name = Util.avoidNull(slipDetail.getSWK_KMK_NAME_S());
		}

		if (layoutDefine != null && layoutDefine.isNotShowItemCode()) {
			return name;
		}

		return Util.avoidNull(slipDetail.getSWK_KMK_CODE()) + " " + name;
	}

	/**
	 * @param slipDetail
	 * @return 補助科目
	 */
	protected String getSubItem(SWK_DTL slipDetail) {
		String name = "";
		if (!isUseItemShortName) {
			name = Util.avoidNull(slipDetail.getSWK_HKM_NAME());
		} else {
			name = Util.avoidNull(slipDetail.getSWK_HKM_NAME_S());
		}

		if (layoutDefine != null && layoutDefine.isNotShowSubItemCode()) {
			return name;
		}

		return Util.avoidNull(slipDetail.getSWK_HKM_CODE()) + " " + name;
	}

	/**
	 * @param slipDetail
	 * @return 内訳科目
	 */
	protected String getDetailItem(SWK_DTL slipDetail) {
		String name = "";
		// コードNULL＆名称NOT NULLは相手勘定をセットした明細の為、名称を返す
		if (isUseTransferItemName && Util.isNullOrEmpty(slipDetail.getSWK_UKM_CODE())
			&& !Util.isNullOrEmpty(slipDetail.getSWK_UKM_NAME())) {
			return Util.avoidNull(slipDetail.getSWK_UKM_NAME());
		}
		if (!isUseItemShortName) {
			name = Util.avoidNull(slipDetail.getSWK_UKM_NAME());
		} else {
			name = Util.avoidNull(slipDetail.getSWK_UKM_NAME_S());
		}

		if (layoutDefine != null && layoutDefine.isNotShowDetailItemCode()) {
			return name;
		}

		return Util.avoidNull(slipDetail.getSWK_UKM_CODE()) + " " + name;
	}

	/**
	 * @param slipDetail
	 * @return 取引先
	 */
	protected String getCustomer(SWK_DTL slipDetail) {
		String name = "";
		if (!isUseCustomerShortName) {
			name = Util.avoidNull(slipDetail.getSWK_TRI_NAME());
		} else {
			name = Util.avoidNull(slipDetail.getSWK_TRI_NAME_S());
		}

		if (layoutDefine != null && layoutDefine.isNotShowCustomerCode()) {
			return name;
		}

		return Util.avoidNull(slipDetail.getSWK_TRI_CODE()) + " " + name;
	}

	/**
	 * @param slipDetail
	 * @return 社員
	 */
	protected String getEmployee(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowEmployeeCode()) {
			return Util.avoidNull(slipDetail.getSWK_EMP_NAME());
		}

		return Util.avoidNull(slipDetail.getSWK_EMP_CODE()) + " " + Util.avoidNull(slipDetail.getSWK_EMP_NAME());
	}

	/**
	 * @param slipDetail
	 * @return 消費税
	 */
	protected String getTax(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowTaxCode()) {
			return Util.avoidNull(slipDetail.getSWK_ZEI_NAME());
		}

		return Util.avoidNull(slipDetail.getSWK_ZEI_CODE()) + " " + Util.avoidNull(slipDetail.getSWK_ZEI_NAME());
	}

	/**
	 * @param slipDetail
	 * @return 管理1出力情報
	 */
	protected String getManagement1(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowManagementCode1()) {
			return Util.avoidNull(slipDetail.getSWK_KNR_NAME_1());
		}

		return Util.avoidNull(slipDetail.getSWK_KNR_CODE_1()) + " " + Util.avoidNull(slipDetail.getSWK_KNR_NAME_1());
	}

	/**
	 * @param slipDetail
	 * @return 管理2出力情報
	 */
	protected String getManagement2(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowManagementCode2()) {
			return Util.avoidNull(slipDetail.getSWK_KNR_NAME_2());
		}

		return Util.avoidNull(slipDetail.getSWK_KNR_CODE_2()) + " " + Util.avoidNull(slipDetail.getSWK_KNR_NAME_2());
	}

	/**
	 * @param slipDetail
	 * @return 管理3出力情報
	 */
	protected String getManagement3(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowManagementCode3()) {
			return Util.avoidNull(slipDetail.getSWK_KNR_NAME_3());
		}

		return Util.avoidNull(slipDetail.getSWK_KNR_CODE_3()) + " " + Util.avoidNull(slipDetail.getSWK_KNR_NAME_3());
	}

	/**
	 * @param slipDetail
	 * @return 管理4出力情報
	 */
	protected String getManagement4(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowManagementCode4()) {
			return Util.avoidNull(slipDetail.getSWK_KNR_NAME_4());
		}

		return Util.avoidNull(slipDetail.getSWK_KNR_CODE_4()) + " " + Util.avoidNull(slipDetail.getSWK_KNR_NAME_4());
	}

	/**
	 * @param slipDetail
	 * @return 管理5出力情報
	 */
	protected String getManagement5(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowManagementCode5()) {
			return Util.avoidNull(slipDetail.getSWK_KNR_NAME_5());
		}

		return Util.avoidNull(slipDetail.getSWK_KNR_CODE_5()) + " " + Util.avoidNull(slipDetail.getSWK_KNR_NAME_5());
	}

	/**
	 * @param slipDetail
	 * @return 管理6出力情報
	 */
	protected String getManagement6(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowManagementCode6()) {
			return Util.avoidNull(slipDetail.getSWK_KNR_NAME_6());
		}

		return Util.avoidNull(slipDetail.getSWK_KNR_CODE_6()) + " " + Util.avoidNull(slipDetail.getSWK_KNR_NAME_6());
	}

	/**
	 * フッターの描画
	 * 
	 * @param footer
	 */
	@Override
	protected void drawFooter(LedgerSheetFooter footer) throws TException {

		SlipBookFooter f = (SlipBookFooter) footer;
		Company company = getBook().getCompany();

		LedgerCellTable cellTable = new LedgerCellTable();

		double y = getDetailTopY() + 11.30d;

		// 1行目
		// 行番号
		cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
		// 科目
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, "");
		// 借方金額
		cellTable.addCell(
			mRightStyle,
			AMOUNT_WIDTH,
			NumberFormatUtil.formatNumber(f.getDebitAmount(), company.getAccountConfig().getKeyCurrency()
				.getDecimalPoint()));
		// 貸方金額
		cellTable.addCell(
			mRightStyle,
			AMOUNT_WIDTH,
			NumberFormatUtil.formatNumber(f.getCreditAmount(), company.getAccountConfig().getKeyCurrency()
				.getDecimalPoint()));
		// 通貨
		cellTable.addCell(mCenterStyle, CURRENCY_WIDTH + REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH
			+ MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH, "");

		drawer.drawText(cellTable, 0.0, y);

		y = y + ROW_HEIGHT;

		// 2行目
		cellTable = new LedgerCellTable();
		// 行番号
		cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
		// 科目
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, "");
		if (Util.isNullOrEmpty(f.getForeignCurrencyCode1())) {
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH + REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH
				+ MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH, "");
		} else {
			// 借方金額
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH,
				NumberFormatUtil.formatNumber(f.getForeignDebitAmount1(), f.getForeignDecimalPoint1()));
			// 貸方金額
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH,
				NumberFormatUtil.formatNumber(f.getForeignCreditAmount1(), f.getForeignDecimalPoint1()));
			// 通貨
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, f.getForeignCurrencyCode1());
			cellTable.addCell(mCenterStyle, REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH
				+ NON_ACCOUNT_WIDTH, "");
		}

		drawer.drawText(cellTable, 0.0, y);

		y = y + ROW_HEIGHT;

		// 3行目
		cellTable = new LedgerCellTable();
		// 行番号
		cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
		// 科目
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, "");
		if (Util.isNullOrEmpty(f.getForeignCurrencyCode2())) {
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH + REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH
				+ MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH, "");
		} else {
			// 借方金額
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH,
				NumberFormatUtil.formatNumber(f.getForeignDebitAmount2(), f.getForeignDecimalPoint2()));
			// 貸方金額
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH,
				NumberFormatUtil.formatNumber(f.getForeignCreditAmount2(), f.getForeignDecimalPoint2()));
			// 通貨
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, f.getForeignCurrencyCode2());
			cellTable.addCell(mCenterStyle, REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH
				+ NON_ACCOUNT_WIDTH, "");
		}

		drawer.drawText(cellTable, 0.0, y);

		if (f.getMaxForeignCurrencyCount() > 2) {
			// 外貨出力件数＞2の場合、出力

			// 線
			double rowHeight = ROW_HEIGHT;

			// 明細ヘッダーと枠線
			double startX = ROW_NO_WIDTH + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH;

			double detailTopY = getDetailTopY();
			double endX = startX + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH;
			double endY = detailTopY + 0.80 + rowHeight * 3 * (getBook().getMaxRowCount() + 1);
			drawer.drawFrameBorder(middleLineStyle, startX, endY - rowHeight * 2, endX, endY);

			// 縦線
			double x = startX + ITEM_WIDTH;
			drawer.drawLine(thinLineStyle, x, endY, x, endY - rowHeight * 2);
			x += AMOUNT_WIDTH;
			drawer.drawLine(thinLineStyle, x, endY, x, endY - rowHeight * 2);
			x += AMOUNT_WIDTH;
			drawer.drawLine(thinLineStyle, x, endY, x, endY - rowHeight * 2);

			y = endY - ROW_HEIGHT;
			drawer.drawLine(thinLineStyle, startX, y, endX, y);

			// y初期化
			y = endY - ROW_HEIGHT * 2;

			for (int index = 2; index < f.getMaxForeignCurrencyCount(); index++) {

				cellTable = new LedgerCellTable();
				// 行番号
				cellTable.addCell(mLeftStyle, ROW_NO_WIDTH + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH,
					"");
				cellTable.addCell(mCenterStyle, ITEM_WIDTH, "");
				if (Util.isNullOrEmpty(f.getForeignCurrencyCode(index))) {
					cellTable.addCell(mCenterStyle, REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH
						+ MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH - ITEM_WIDTH, "");
				} else {

					String debit = NumberFormatUtil.formatNumber(f.getForeignDebitAmount(index),
						f.getForeignDecimalPoint(index));
					String credit = NumberFormatUtil.formatNumber(f.getForeignCreditAmount(index),
						f.getForeignDecimalPoint(index));

					// 借方金額
					cellTable.addCell(mRightStyle, AMOUNT_WIDTH, debit);
					// 貸方金額
					cellTable.addCell(mRightStyle, AMOUNT_WIDTH, credit);
					// 通貨
					cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, f.getForeignCurrencyCode(index));
					// 余白
					cellTable.addCell(mCenterStyle, (REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH
						+ MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH)
						- (ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH), "");
				}

				drawer.drawText(cellTable, 0.0, y);

				y += ROW_HEIGHT;
			}

		}

	}

	/**
	 * 明細の開始Y座標を返す
	 * 
	 * @return 明細の開始Y座標
	 */
	protected double getDetailTopY() {
		return 4.35d;
	}

	/**
	 * ヘッダーの枠線描画
	 */
	protected void drawHeaderFrameBorder() {

		double startX = 0.0;
		double endX = HEADER_TITLE_WIDTH + HEADER_DEPARTMENT_WIDTH + HEADER_TITLE_WIDTH + HEADER_DEPARTMENT_WIDTH;
		double startY = 2.55d;
		double endY = 4.05d;

		drawer.drawFrameBorder(middleLineStyle, startX, startY, endX, endY);
	}

	/**
	 * 伝票レイアウト定義の設定
	 * 
	 * @param define 伝票レイアウト定義
	 */
	public void setDefine(SlipLayoutDefine define) {

		if (define == null) {
			return;
		}

		this.layoutDefine = define;

		// ヘッダー単語
		this.HEADER_SLIP_DATE_WORD = define.getHeaderSlipDateWord(); // ヘッダー：伝票日付
		this.HEADER_DEPARTMENT_NAME_WORD = define.getHeaderDepartmentNameWord(); // ヘッダー：入力部門
		this.HEADER_EMPLOYEE_NAME_WORD = define.getHeaderEmployeeNameWord(); // ヘッダー：入力者
		this.HEADER_SLIP_NO_WORD = define.getHeaderSlipNoWord(); // ヘッダー：伝票番号
		this.HEADER_UPDATE_WORD = define.getHeaderUpdateWord(); // ヘッダー：修正
		this.HEADER_UPDATE_COUNT_WORD = define.getHeaderUpdateCountWord(); // ヘッダー：回
		this.HEADER_VOUCHER_NO_WORD = define.getHeaderVoucherNoWord(); // ヘッダー：証憑番号
		this.HEADER_SLIP_REMARK_WORD = define.getHeaderSlipRemarkWord(); // ヘッダー：伝票摘要
		this.HEADER_KEY_CURRENCY_WORD = define.getHeaderKeyCurrencyWord(); // ヘッダー：基軸通貨
		this.HEADER_ACCOUNT_BOOK_NAME_WORD = define.getHeaderAccountBookNameWord(); // ヘッダー：帳簿区分
		this.HEADER_ACCEPT_STAMP_1_WORD = define.getHeaderAcceptStamp1Word(); // ヘッダー：承認印1
		this.HEADER_ACCEPT_STAMP_2_WORD = define.getHeaderAcceptStamp2Word(); // ヘッダー：承認印2
		this.HEADER_ACCEPT_STAMP_3_WORD = define.getHeaderAcceptStamp3Word(); // ヘッダー：承認印3

		// 明細タイトル単語
		this.DETAIL_DR_AMOUNT_WORD = define.getDetailDrAmountWord(); // 明細：借方金額
		this.DETAIL_CR_AMOUNT_WORD = define.getDetailCrAmountWord(); // 明細：貸方金額
		this.DETAIL_ROW_REMARK_WORD = define.getDetailRowRemarkWord(); // 明細：行摘要
		this.DETAIL_CUSTOMER_WORD = define.getDetailCustomerWord(); // 明細：計上部門/取引先/社員
		this.DETAIL_CURRENCY_WORD = define.getDetailCurrencyWord(); // 明細：通貨
		this.DETAIL_TAX_WORD = define.getDetailTaxWord(); // 明細：発生日/税
		this.DETAIL_MANAGEMENT1TO3_WORD = define.getDetailManagement1To3Word(); // 明細：管理1/2/3
		this.DETAIL_MANAGEMENT4TO6_WORD = define.getDetailManagement4To6Word(); // 明細：管理4/5/6
		this.DETAIL_NON_ACCOUNT_WORD = define.getDetailNonAccountWord(); // 明細：非会計明細1/2/3
		this.DETAIL_SUM_WORD = define.getDetailSumWord(); // 明細：合計
		this.DETAIL_FOREIGN_1_WORD = define.getDetailForeign1Word(); // 明細：外貨計1
		this.DETAIL_FOREIGN_2_WORD = define.getDetailForeign2Word(); // 明細：外貨計2
		this.DETAIL_FOREIGN_3_WORD = define.getDetailForeign3Word(); // 明細：外貨計3
		this.DETAIL_FOREIGN_4_WORD = define.getDetailForeign4Word(); // 明細：外貨計4

		// 幅
		this.ROW_HEIGHT = define.getRowHeight(); // 行高さ
		this.HEADER_TITLE_WIDTH = define.getHeaderTitleWidth(); // ヘッダータイトル幅
		this.HEADER_DEPARTMENT_WIDTH = define.getHeaderDepartmentWidth(); // ヘッダー部門幅
		this.REST_WIDTH = define.getDetailRestWidth(); // 空行幅
		this.ROW_NO_WIDTH = define.getDetailRowNoWidth(); // 行番号幅
		this.ITEM_WIDTH = define.getDetailItemWidth(); // 科目幅
		this.AMOUNT_WIDTH = define.getDetailAmountWidth(); // 金額幅
		this.CURRENCY_WIDTH = define.getDetailCurrencyWidth(); // 通貨幅
		this.REMARK_WIDTH = define.getDetailRemarkWidth(); // 摘要幅
		this.CUSTOMER_WIDTH = define.getDetailCustomerWidth(); // 取引先幅
		this.MANAGEMENT1TO3_WIDTH = define.getDetailManagement1To3Width(); // 管理1～3幅
		this.MANAGEMENT4TO6_WIDTH = define.getDetailManagement4To6Width(); // 管理4～6幅
		this.NON_ACCOUNT_WIDTH = define.getDetailNonAccountWidth(); // 非会計明細幅

		// 桁数
		this.ACCOUNT_BOOK_NAME_LENGTH = define.getHeaderAccountBookNameLength(); // 帳簿区分桁数
		this.UPDATE_COUNT_LENGTH = define.getHeaderUpdateCountLength(); // 修正回数桁数
		this.ACCEPT_STAMP_1_LENGTH = define.getHeaderAcceptStamp1Length(); // 承認印1桁数
		this.ACCEPT_STAMP_2_LENGTH = define.getHeaderAcceptStamp2Length(); // 承認印2桁数
		this.ACCEPT_STAMP_3_LENGTH = define.getHeaderAcceptStamp3Length(); // 承認印3桁数
		this.ITEM_LENGTH = define.getDetailItemLength(); // 科目桁数
		this.TAX_LENGTH = define.getDetailTaxLength(); // 消費税桁数
		this.REMARK_LENGTH = define.getDetailRemarkLength(); // 摘要桁数
		this.CUSTOMER_LENGTH = define.getDetailCustomerLength(); // 取引先桁数
		this.MANAGEMENT1TO3_LENGTH = define.getDetailManagement1To3Length(); // 管理1～3桁数
		this.MANAGEMENT4TO6_LENGTH = define.getDetailManagement4To6Length(); // 管理4～6桁数
		this.NON_ACCOUNT_LENGTH = define.getDetailNonAccountLength(); // 非会計明細桁数

		this.HEADER_DEPARTMENT_NAME_LENGTH = define.getHeaderDepartmentNameLength(); // 入力部門桁数

	}

	/**
	 * ページ番号表示文字列を返す
	 * 
	 * @return ページ番号表示文字列
	 */
	@Override
	protected String getPageNoChar() {

		int startPageNo = 0;
		int sheetCount = book.getSheetCount();

		if (book instanceof SlipBook) {
			SlipBook slipBook = (SlipBook) book;

			// 開始ページ番号の設定
			startPageNo = slipBook.getStartPageNo();

			// 指定総ページ数があれば、当該総ページ数を出力する
			if (slipBook.getTotalPageCount() > 0) {
				sheetCount = slipBook.getTotalPageCount();
			}
		}

		return Integer.toString(startPageNo + currentPageNo) + "/" + Integer.toString(sheetCount);
	}

	/**
	 * @return true:ヘッダー基軸通貨も表示
	 */
	protected boolean isShowHeaderKeyCurrencyCode() {
		return ServerConfig.isFlagOn("trans.slip.layout.header.key.currency.visable");
	}

	/**
	 * @return true:ヘッダー基軸通貨も表示
	 */
	protected boolean isShowDetailKeyCurrencyCode() {
		return ServerConfig.isFlagOn("trans.slip.layout.detail.key.currency.visable");
	}

}
