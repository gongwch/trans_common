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
 * ���[���C�A�E�g���N���X
 * 
 * @author AIS
 */
public abstract class TReportLayout extends ReportLayout {

	/** flow */
	protected JCFlow flow = null;

	/** book */
	protected LedgerBook book;

	/** ���[�`�� */
	protected LedgerDrawer drawer;

	/** ���[�f�[�^��ۑ����邩 */
	protected static boolean isSaveBook = ServerConfig.isFlagOn("trans.system.ledger.saveBook");

	/** ���[�敪���\�����邩 */
	protected static boolean isNotOutputAccountBook = ServerConfig.isFlagOn("trans.report.not.output.accountbook");

	/** �t���[���̊J�n�ʒu�̐������W */
	protected double frameLoacationX = 0.5;

	/** �t���[���̏I���ʒu�̐������W */
	protected double frameLoacationY = 1.0;

	/** �t���[���̕� */
	protected double frameWidth = 28.7;

	/** �t���[���̍��� */
	protected double frameHeight = 21.0;

	/** ���݂̃y�[�W�ԍ� */
	protected int currentPageNo = 1;

	/** �Z���̃X�^�C��(�^�C�g��) */
	protected JCTextStyle titleStyle;

	/** �Z���̃X�^�C��(��) */
	protected JCTextStyle leftStyle;

	/** �Z���̃X�^�C��(����) */
	protected JCTextStyle centerStyle;

	/** �Z���̃X�^�C��(�E) */
	protected JCTextStyle rightStyle;

	/** �r���̃X�^�C��(�^�C�g��) */
	protected JCDrawStyle titleLineStyle;

	/** �r���̃X�^�C��(��) */
	protected JCDrawStyle brokenLineStyle;

	/** �r���̃X�^�C��(��) */
	protected JCDrawStyle middleLineStyle;

	/** �r���̃X�^�C��(����) */
	protected JCDrawStyle thinLineStyle;

	/**
	 * �R���X�g���N�^
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
	 * ������
	 */
	protected void init() {
		initFrameSize();
		initStyle();
		initWord();
	}

	/**
	 * �t���[���̃T�C�Y�ݒ�
	 */
	protected void initFrameSize() {
		// for Override
	}

	/**
	 * �����ݒ�
	 */
	protected void initWord() {
		// for Override
	}

	/**
	 * ����Book���o�͂���ꍇ�Abook���ς�������ɌĂяo����鏉�������\�b�h
	 */
	protected void initWhenBookBreak() {
		// for Override
	}

	/**
	 * Style������
	 */
	protected void initStyle() {

		// ���̃X�^�C��������
		titleLineStyle = createBoldLineStyle();
		titleLineStyle.setLineWidth(new JCUnit.Measure(JCUnit.CM, 0.040));
		brokenLineStyle = createBrokenLineStyle(0.015);
		middleLineStyle = createNomalLineStyle();
		middleLineStyle.setLineWidth(new JCUnit.Measure(JCUnit.CM, 0.015));
		thinLineStyle = createNomalLineStyle();
		thinLineStyle.setLineWidth(new JCUnit.Measure(JCUnit.CM, 0.010));

		// �����X�^�C��������
		titleStyle = createCurrentStyle();
		titleStyle.setUnderlining(2);
		leftStyle = createLeftStyle(8);
		centerStyle = createCenterStyle(8);
		rightStyle = createRightStyle(8);

	}

	/**
	 * ���[��Ԃ��B ��1��Book��1��PDF�t�@�C���ŕԂ�
	 * 
	 * @param _book
	 * @return ���[
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

		// ������
		initWhenBookBreak();

		List<LedgerSheet> allSheet = _book.getSheet();

		// �V�[�g�̐������y�[�W�쐬����
		for (int i = 0; i < allSheet.size(); i++) {

			currentPageNo = i + 1;

			// �w�b�_�[�`��
			LedgerSheet sheet = allSheet.get(i);

			refleshFrame();

			drawHeader(sheet.getHeader());

			// 2.���ו`��
			drawDetail(sheet.getDetail());

			// 3.�t�b�^�[�`��
			if (sheet.getFooter() != null) {
				drawFooter(sheet.getFooter());
			}

			// 4.���y�[�W
			if (i != allSheet.size() - 1) {
				flow.newPage();
			}

		}

		return getBinary();

	}

	/**
	 * ���[��Ԃ��B <br>
	 * ��1��Book��1��PDF�t�@�C���ŕԂ��B<BR>
	 * 1�y�[�W��sheetCount����LedgerSheet���󎚂���B
	 * 
	 * @param _book LedgerBook
	 * @param maxBlocks ���[1�y�[�W�ɂ�����LedgerSheet��\�����邩
	 * @return ���[
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

		// ������
		initWhenBookBreak();

		List<LedgerSheet> allSheet = _book.getSheet();

		int blockCounter = 0;
		refleshFrame();

		// �V�[�g�̐������y�[�W�쐬����
		for (int i = 0; i < allSheet.size(); i++) {

			currentPageNo = i + 1;

			LedgerSheet sheet = allSheet.get(i);

			// �w�b�_�[�`��
			refleshSheetBlock(blockCounter);

			drawHeader(sheet.getHeader());

			// ���ו`��
			drawDetail(sheet.getDetail());

			// �t�b�^�[�`��
			if (sheet.getFooter() != null) {
				drawFooter(sheet.getFooter());
			}

			// ���y�[�W
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
	 * JCFrame��refresh
	 */
	protected void refleshFrame() {
		JCFrame currentFrame = flow.getCurrentFrame();
		currentFrame.setLocation(new JCUnit.Point(JCUnit.CM, frameLoacationX, frameLoacationY));
		currentFrame.setSize(new JCUnit.Dimension(JCUnit.CM, frameWidth, frameHeight));
		drawer.reflesh();
	}

	/**
	 * ���[��Ԃ��B <br>
	 * ��������Book��1��PDF�t�@�C���ŕԂ��B���C�A�E�g�͂��ꂼ���Book�ŋ��ʂƂ���B
	 * 
	 * @param books
	 * @return ���[
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

			// ������
			initWhenBookBreak();

			List<LedgerSheet> allSheet = _book.getSheet();

			// �V�[�g�̐������y�[�W�쐬����
			for (int i = 0; i < allSheet.size(); i++) {

				currentPageNo = i + 1;

				// �w�b�_�[�`��
				LedgerSheet sheet = allSheet.get(i);

				refleshFrame();

				drawHeader(sheet.getHeader());

				// 2.���ו`��
				drawDetail(sheet.getDetail());

				// 3.�t�b�^�[�`��
				if (sheet.getFooter() != null) {
					drawFooter(sheet.getFooter());
				}

				// 4.���y�[�W
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
	 * ���[��Ԃ� ��������Book��1��PDF�t�@�C���ŕԂ��B���C�A�E�g��Book���Ɉ���Ă悢�B
	 * 
	 * @param bookAndLayouts
	 * @return ���[
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

			// ���[���o��
			LedgerBook _book = bookAndLayouts.get(bookCount).getBook();
			// ���C�A�E�g���o��
			TReportLayout layout = bookAndLayouts.get(bookCount).getLayout();
			layout.flow = flow;
			layout.document = document;
			layout.drawer = drawer;
			layout.book = _book;
			layout.init();

			// ������
			layout.initWhenBookBreak();

			List<LedgerSheet> allSheet = _book.getSheet();

			// �V�[�g�̐������y�[�W�쐬����
			for (int i = 0; i < allSheet.size(); i++) {

				layout.currentPageNo = i + 1;

				// �w�b�_�[�`��
				LedgerSheet sheet = allSheet.get(i);

				JCFrame currentFrame = flow.getCurrentFrame();
				currentFrame.setLocation(new JCUnit.Point(JCUnit.CM, layout.frameLoacationX, layout.frameLoacationY));
				currentFrame.setSize(new JCUnit.Dimension(JCUnit.CM, layout.frameWidth, layout.frameHeight));
				drawer.reflesh();

				layout.drawHeader(sheet.getHeader());

				// 2.���ו`��
				layout.drawDetail(sheet.getDetail());

				// 3.�t�b�^�[�`��
				if (sheet.getFooter() != null) {
					layout.drawFooter(sheet.getFooter());
				}

				// 4.���y�[�W
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
	 * book��ۑ�����
	 * 
	 * @param _book
	 */
	protected void saveBook(LedgerBook _book) {
		FileUtil.saveObject(_book, _book.getClass().getSimpleName());
	}

	/**
	 * book��ۑ�����
	 * 
	 * @param books
	 */
	protected void saveBook(List<? extends LedgerBook> books) {
		FileUtil.saveObject(books, books.get(0).getClass().getSimpleName());
	}

	/**
	 * �w�b�_�[�`��
	 * 
	 * @param header
	 * @throws TException
	 */
	protected abstract void drawHeader(LedgerSheetHeader header) throws TException;

	/**
	 * ���ו`��
	 * 
	 * @param detail
	 * @throws TException
	 */
	protected abstract void drawDetail(List<LedgerSheetDetail> detail) throws TException;

	/**
	 * �t�b�^�[�`��
	 * 
	 * @param footer
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	protected void drawFooter(@SuppressWarnings("unused") LedgerSheetFooter footer) throws TException {
		// for override
	}

	/**
	 * 1�y�[�W�ɕ���Sheet��`�悷��ꍇ�ASheet���ς�������_�ł̏����������Override���ĉ������B
	 * 
	 * @param sheetCount ���݂̃V�[�g�BPageBreak�����0�B
	 * @throws TException
	 */
	@SuppressWarnings("unused")
	protected void refleshSheetBlock(@SuppressWarnings("unused") int sheetCount) throws TException {
		// for override
	}

	/**
	 * �y�[�W��\������
	 * 
	 * @param _drawer
	 * @param point
	 * @throws TException
	 */
	protected void drawPageNo(LedgerDrawer _drawer, JCUnit.Point point) throws TException {

		LedgerCellTable cellTable = new LedgerCellTable();
		// ��
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C01585") + ":")); // ��
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, getPageNoChar()));
		_drawer.drawText(cellTable, point);

	}

	/**
	 * �y�[�W�ԍ��\���������Ԃ�
	 * 
	 * @return �y�[�W�ԍ��\��������
	 */
	protected String getPageNoChar() {
		return Integer.toString(currentPageNo) + "/" + Integer.toString(book.getSheetCount());
	}

	/**
	 * ��������\������
	 * 
	 * @param _drawer
	 * @param point
	 * @throws TException
	 */
	protected void drawOutputDate(LedgerDrawer _drawer, JCUnit.Point point) throws TException {

		LedgerCellTable cellTable = new LedgerCellTable();
		// ������
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C00902") + ":"));
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, book.getCurrentDate(getLanguageCode())));
		_drawer.drawText(cellTable, point);

	}

	/**
	 * ���Ԃ�\������
	 * 
	 * @param _drawer
	 * @param point
	 * @throws TException
	 */
	protected void drawTimeStamp(LedgerDrawer _drawer, JCUnit.Point point) throws TException {

		LedgerCellTable cellTable = new LedgerCellTable();
		// ����
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C00903") + ":"));
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, book.getCurrentTime(getLanguageCode())));
		_drawer.drawText(cellTable, point);

	}

	/**
	 * ����敪��\������
	 * 
	 * @param _drawer
	 * @param point
	 * @param accountBook
	 * @throws TException
	 */
	protected void drawBookName(LedgerDrawer _drawer, JCUnit.Point point, AccountBook accountBook) throws TException {

		if (isNotOutputAccountBook) {
			// ����敪��\��
			return;
		}

		LedgerCellTable cellTable = new LedgerCellTable();
		// ����敪
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C10961") + ":"));
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, StringUtil.leftBX(
			getWord(AccountBook.getAccountBookName(accountBook)), 12)));
		_drawer.drawText(cellTable, point);

	}

	/**
	 * �\���ʉ݂�\������
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
		// �\���ʉ�
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C10549") + ":"));
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, getShortWord(CurrencyType.getCurrencyTypeName(currencyType))
			+ " " + currencyCode));
		_drawer.drawText(cellTable, point);

	}

	/**
	 * �o�͏���\������
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
	 * �o�͏���\������
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
	 * �o�͏���\������
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
		// �ʉ�
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C00371") + ":"));
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, currency.getCode()));
		_drawer.drawText(cellTable, new JCUnit.Point(JCUnit.CM, x, y + 1.2));

	}

	/**
	 * �o�͏���\������
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
		// �ʉ�
		cellTable.addCell(new LedgerCell(rightStyle, LedgerCell.WIDTH_REST, getWord("C00371") + ":"));
		cellTable.addCell(new LedgerCell(rightStyle, 2.0, currencyCode));
		_drawer.drawText(cellTable, new JCUnit.Point(JCUnit.CM, x, y + 1.2));

	}

	/**
	 * �o�͏��(����A�\���ʉ݂��܂�)��\������
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
	 * �o�͏��(����A�\���ʉ݂��܂�)��\������
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
	 * ���`����Ԃ�
	 * 
	 * @param dateFrom
	 * @param dateTo
	 * @return �� yyyy/mm/dd �`�� yyyy/mm/dd
	 */
	protected String getDateRangeStatement(Date dateFrom, Date dateTo) {
		// �� //��
		String rt = "(" + getWord("C00898") + " " + DateUtil.toYMDString(dateFrom) + " �` " + getWord("C00899") + " "
			+ DateUtil.toYMDString(dateTo) + ")";
		return rt;
	}

	/**
	 * �z��������܂�/�܂܂Ȃ� �𕶎���ŕԂ�
	 * 
	 * @param isIncludeUnderDepartment �z��������܂ނ�
	 * @return �z��������܂�/�܂܂Ȃ�
	 */
	protected String getIncludeUnderDepartmentStatement(boolean isIncludeUnderDepartment) {
		// �܂�
		String rt = getWord("C00461");
		if (!isIncludeUnderDepartment) {
			// �܂܂Ȃ�
			rt = getWord("C00460");
		}
		return rt;
	}

	/**
	 * ���Z�i�K�𕶎���ŕԂ�
	 * 
	 * @param settlementStage
	 * @return ���Z�i�K
	 */
	protected String getSettlementStageStagement(int settlementStage) {
		return FormatUtil.settlementLevelFormat(settlementStage, lang);
	}

	/**
	 * ���Z�i�K�𕶎���ŕԂ�
	 * 
	 * @param settlementStage
	 * @param normalBlank true�̏ꍇ<br>
	 *            �ʏ�̏ꍇ�̓u�����N<br>
	 *            �ȊO�Fn�����Z
	 * @return ���Z�i�K
	 */
	protected String getSettlementStageStagement(int settlementStage, boolean normalBlank) {
		return FormatUtil.settlementLevelFormat(settlementStage, lang, normalBlank);
	}

	/**
	 * �X�V�敪�𕶎���ŕԂ�
	 * 
	 * @param isEntry
	 * @param isApprove
	 * @param isSettle
	 * @return �X�V�敪
	 */
	protected String getSlipStateStagement(boolean isEntry, boolean isApprove, boolean isSettle) {
		String rt = "";
		if (isEntry) {
			// �o�^
			rt = getWord("C01258");
		}
		if (isApprove) {
			if (rt.length() != 0) {
				rt = rt + " ";
			}
			// ���F
			rt = rt + getWord("C01168");
		}
		if (isSettle) {
			if (rt.length() != 0) {
				rt = rt + " ";
			}
			// �X�V
			rt = rt + getWord("C00169");
		}

		return rt;

	}

	/**
	 * �X�V�敪�𕶎���ŕԂ�
	 * 
	 * @param states
	 * @return �X�V�敪����
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
	 * @return �t���[���̊J�n�ʒu�̐������W
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
	 * @return �t���[���̏I���ʒu�̐������W
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
	 * @return �t���[���̍���
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
	 * @return �t���[���̕�
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
