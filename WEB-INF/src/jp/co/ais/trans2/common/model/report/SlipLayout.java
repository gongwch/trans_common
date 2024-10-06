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
 * �`�[�̃��C�A�E�g���
 * 
 * @author AIS
 */
public abstract class SlipLayout extends TReportLayout {

	/** ���[�敪���\�����邩 */
	protected static boolean isNotOutputSlipAccountBook = ServerConfig.isFlagOn("trans.slip.not.output.accountbook");

	/** true:IFRS�敪��蔭�����͉Ȗڂ̔������t���O�ɂ�萧��@�\�L����Server�� */
	public static boolean allowOccurDateBlank = ServerConfig
		.isFlagOn("trans.slip.detail.allow.occurdate.blank.when.noifrs");

	/** true:BS��������@�\�L��<Server> */
	public static boolean isUseBS = ServerConfig.isFlagOn("trans.slip.use.bs");

	/** true:BS��������E�ďo�ǂ���ł��}�[�N���L��<Server> */
	public static boolean isShowAllBS = ServerConfig.isFlagOn("trans.slip.use.bs.sousai.mark");

	/** true:AP��������@�\����<Server> */
	public static boolean isNotUseAP = ServerConfig.isFlagOn("trans.slip.disable.ap.zan");

	/** true:AR��������@�\����<Server> */
	public static boolean isNotUseAR = ServerConfig.isFlagOn("trans.slip.disable.ar.zan");

	/** true:����Ȗڂ͗��̂��o���L��<Server> */
	public static boolean isUseItemShortName = ServerConfig.isFlagOn("trans.slip.pdf.use.item.shortname");

	/** true:�����͗��̂��o���L��<Server> */
	public static boolean isUseCustomerShortName = ServerConfig.isFlagOn("trans.slip.pdf.use.customer.shortname");

	/** true:�t�֎d�󐶐����ɑ����Ђ̊���Ȗڂ�\������ */
	public static boolean isUseTransferItemName = ServerConfig.isFlagOn("trans.slip.use.transfer.item.name");

	/** �w�b�_�[�P���` */

	/** �w�b�_�[�F�`�[���t */
	protected String HEADER_SLIP_DATE_WORD = "C00599";

	/** �w�b�_�[�F���͕��� */
	protected String HEADER_DEPARTMENT_NAME_WORD = "C01280";

	/** �w�b�_�[�F���͎� */
	protected String HEADER_EMPLOYEE_NAME_WORD = "C01278";

	/** �w�b�_�[�F�`�[�ԍ� */
	protected String HEADER_SLIP_NO_WORD = "C00605";

	/** �w�b�_�[�F�C�� */
	protected String HEADER_UPDATE_WORD = "C01760";

	/** �w�b�_�[�F�� */
	protected String HEADER_UPDATE_COUNT_WORD = "C01761";

	/** �w�b�_�[�F�؜ߔԍ� */
	protected String HEADER_VOUCHER_NO_WORD = "C01178";

	/** �w�b�_�[�F�`�[�E�v */
	protected String HEADER_SLIP_REMARK_WORD = "C00569";

	/** �w�b�_�[�F��ʉ� */
	protected String HEADER_KEY_CURRENCY_WORD = "C00907";

	/** �w�b�_�[�F����敪 */
	protected String HEADER_ACCOUNT_BOOK_NAME_WORD = "C10961";

	/** �w�b�_�[�F���F��1 */
	protected String HEADER_ACCEPT_STAMP_1_WORD = "C01762";

	/** �w�b�_�[�F���F��2 */
	protected String HEADER_ACCEPT_STAMP_2_WORD = "C11436";

	/** �w�b�_�[�F���F��3 */
	protected String HEADER_ACCEPT_STAMP_3_WORD = "C11437";

	/** ���גP���` */

	/** ���ׁF�ؕ����z */
	protected String DETAIL_DR_AMOUNT_WORD = "C01557";

	/** ���ׁF�ݕ����z */
	protected String DETAIL_CR_AMOUNT_WORD = "C01559";

	/** ���ׁF�s�E�v */
	protected String DETAIL_ROW_REMARK_WORD = "C00119";

	/** ���ׁF�v�㕔��/�����/�Ј� */
	protected String DETAIL_CUSTOMER_WORD = "C11071";

	/** ���ׁF�ʉ� */
	protected String DETAIL_CURRENCY_WORD = "C00371";

	/** ���ׁF������/�� */
	protected String DETAIL_TAX_WORD = "C11435";

	/** ���ׁF�Ǘ�1/2/3 */
	protected String DETAIL_MANAGEMENT1TO3_WORD = "C11072";

	/** ���ׁF�Ǘ�4/5/6 */
	protected String DETAIL_MANAGEMENT4TO6_WORD = "C11073";

	/** ���ׁF���v����1/2/3 */
	protected String DETAIL_NON_ACCOUNT_WORD = "C01763";

	/** ���ׁF���v */
	protected String DETAIL_SUM_WORD = "C00165";

	/** ���ׁF�O�݌v1 */
	protected String DETAIL_FOREIGN_1_WORD = "C01764";

	/** ���ׁF�O�݌v2 */
	protected String DETAIL_FOREIGN_2_WORD = "C01765";

	/** ���ׁF�O�݌v3 */
	protected String DETAIL_FOREIGN_3_WORD = "C11608";

	/** ���ׁF�O�݌v4 */
	protected String DETAIL_FOREIGN_4_WORD = "C11609";

	/** �񕝒�` */

	/** �s���� */
	protected double ROW_HEIGHT = 0.50d;

	/** �w�b�_�[�^�C�g���� */
	protected double HEADER_TITLE_WIDTH = 2.5d;

	/** �w�b�_�[���啝 */
	protected double HEADER_DEPARTMENT_WIDTH = 9.0d;

	/** ��s�� */
	protected double REST_WIDTH = 5.85d;

	/** �s�ԍ��� */
	protected double ROW_NO_WIDTH = 0.7d;

	/** �Ȗڕ� */
	protected double ITEM_WIDTH = 4.6d;

	/** ���z�� */
	protected double AMOUNT_WIDTH = 3.10d;

	/** �ʉݕ� */
	protected double CURRENCY_WIDTH = 1.8d;

	/** �E�v�� */
	protected double REMARK_WIDTH = 4.5d;

	/** ����敝 */
	protected double CUSTOMER_WIDTH = 3.6d;

	/** �Ǘ�1�`3�� */
	protected double MANAGEMENT1TO3_WIDTH = 2.6d;

	/** �Ǘ�4�`6�� */
	protected double MANAGEMENT4TO6_WIDTH = 2.6d;

	/** ���v���ו� */
	protected double NON_ACCOUNT_WIDTH = 2.25d;

	/** ������` */

	/** ����敪���� */
	protected int ACCOUNT_BOOK_NAME_LENGTH = 12;

	/** �C���񐔌��� */
	protected int UPDATE_COUNT_LENGTH = 3;

	/** ���F��1���� */
	protected int ACCEPT_STAMP_1_LENGTH = 6;

	/** ���F��2���� */
	protected int ACCEPT_STAMP_2_LENGTH = 6;

	/** ���F��3���� */
	protected int ACCEPT_STAMP_3_LENGTH = 6;

	/** �Ȗڌ��� */
	protected int ITEM_LENGTH = 28;

	/** ����Ō��� */
	protected int TAX_LENGTH = 10;

	/** �E�v���� */
	protected int REMARK_LENGTH = 27;

	/** ����挅�� */
	protected int CUSTOMER_LENGTH = 21;

	/** �Ǘ�1�`3���� */
	protected int MANAGEMENT1TO3_LENGTH = 15;

	/** �Ǘ�4�`6���� */
	protected int MANAGEMENT4TO6_LENGTH = 15;

	/** ���v���׌��� */
	protected int NON_ACCOUNT_LENGTH = 13;

	/** �w�b�_�[�F���͕��包�� */
	protected int HEADER_DEPARTMENT_NAME_LENGTH = 55;

	/** �X�^�C����` */

	/** �g�X�^�C�� */
	protected JCDrawStyle rectanglesStyle;

	/** ���X�^�C��(9) */
	protected JCTextStyle mLeftStyle;

	/** �����X�^�C��(9) */
	protected JCTextStyle mCenterStyle;

	/** �E�X�^�C��(9) */
	protected JCTextStyle mRightStyle;

	/** ���X�^�C��(10) */
	protected JCTextStyle lLeftStyle;

	/** �����X�^�C��(10) */
	protected JCTextStyle lCenterStyle;

	/** ���v����1 */
	protected JCTextStyle nonAccount1Style;

	/** ���v����2 */
	protected JCTextStyle nonAccount2Style;

	/** ���v����3 */
	protected JCTextStyle nonAccount3Style;

	/** �g�X�^�C���F */
	protected Color rectanglesStyleColor = new Color(190, 190, 190);

	/** �Ȗڂ̃L���v�V���� */
	protected String itemCaption = null;

	/** ��`���� */
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
	 * �u�b�N�̎擾
	 * 
	 * @return �u�b�N
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
	 * ���v���ׂ̃X�^�C����Ԃ�
	 * 
	 * @param nonAccounting
	 * @return ���v���ׂ̃X�^�C��
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

		// �^�C�g��
		drawer.drawText(titleStyle, h.getTitle(), 0.0, 0.0);

		// ��Ж���
		drawer.drawText(lLeftStyle, company.getName(), 0.0, 0.0);

		// �`�[���t
		drawer.drawText(lCenterStyle, getWord(HEADER_SLIP_DATE_WORD) + "�F" + DateUtil.toYMDString(h.getSlipDate()),
			0.0, 1.4);

		// ���͕���A���͎�
		double startY = 2.55;
		double rowHeight = ROW_HEIGHT;
		double endY = startY + rowHeight + rowHeight + rowHeight;
		LedgerCellTable cellTable = new LedgerCellTable();
		// ���͕���
		cellTable.addCell(mCenterStyle, HEADER_TITLE_WIDTH, getShortWord(HEADER_DEPARTMENT_NAME_WORD));
		cellTable.addCell(mLeftStyle, HEADER_DEPARTMENT_WIDTH,
			StringUtil.leftBX(getHeaderDepartment(h), HEADER_DEPARTMENT_NAME_LENGTH));
		// ���͎�
		cellTable.addCell(mCenterStyle, HEADER_TITLE_WIDTH, getWord(HEADER_EMPLOYEE_NAME_WORD));
		cellTable.addCell(mLeftStyle, HEADER_DEPARTMENT_WIDTH,
			StringUtil.leftBX(getIraiEmployeeInfo(h), HEADER_DEPARTMENT_NAME_LENGTH));
		cellTable.addCell(mCenterStyle, REST_WIDTH, "");
		drawer.drawText(cellTable, 0.0, startY);

		// �`�[�ԍ�
		cellTable = new LedgerCellTable();
		cellTable.addCell(mCenterStyle, HEADER_TITLE_WIDTH, getWord(HEADER_SLIP_NO_WORD));
		cellTable.addCell(mLeftStyle, HEADER_DEPARTMENT_WIDTH - 5.5d, Util.avoidNull(h.getSlipNo()));
		cellTable.addCell(mRightStyle, 3.5d, getSettlementStageStagement(h.getSettlementStage(), true), 21); // ���Z�i�K
		// �C�� ��
		cellTable.addCell(
			mRightStyle,
			1.5d,
			getShortWord(HEADER_UPDATE_WORD) + h.getUpdateCount()
				+ StringUtil.leftBX(getWord(HEADER_UPDATE_COUNT_WORD), UPDATE_COUNT_LENGTH));
		cellTable.addCell(mRightStyle, 0.5d, ""); // �E�]��

		// �؜ߔԍ�
		cellTable.addCell(mCenterStyle, HEADER_TITLE_WIDTH, getWord(HEADER_VOUCHER_NO_WORD));
		cellTable.addCell(mLeftStyle, HEADER_DEPARTMENT_WIDTH, Util.avoidNull(h.getVoucherNo()));
		cellTable.addCell(mCenterStyle, REST_WIDTH, "");
		drawer.drawText(cellTable, 0.0, startY + rowHeight);

		// �`�[�E�v
		cellTable = new LedgerCellTable();
		cellTable.addCell(mCenterStyle, HEADER_TITLE_WIDTH, getWord(HEADER_SLIP_REMARK_WORD));
		cellTable.addCell(mLeftStyle, HEADER_DEPARTMENT_WIDTH + HEADER_TITLE_WIDTH + HEADER_DEPARTMENT_WIDTH,
			getSlipRemark(h));
		cellTable.addCell(mCenterStyle, REST_WIDTH, "");
		drawer.drawText(cellTable, 0.0, startY + rowHeight + rowHeight);

		// �h��Ԃ�
		double startX = 0.0;
		double endX = HEADER_TITLE_WIDTH;

		drawer.drawRectangles(rectanglesStyle, startX, startY, endX, endY);

		startX = HEADER_TITLE_WIDTH + HEADER_DEPARTMENT_WIDTH;
		endX = startX + HEADER_TITLE_WIDTH;
		drawer.drawRectangles(rectanglesStyle, startX, startY, endX, startY + rowHeight + rowHeight);

		// �g��
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

		// �o�͏��
		drawOutputInformation(drawer, new JCUnit.Point(JCUnit.CM, 0, 0));
		cellTable = new LedgerCellTable();
		// ��ʉ�
		cellTable.addCell(rightStyle, LedgerCell.WIDTH_REST, getWord(HEADER_KEY_CURRENCY_WORD) + ":");
		cellTable.addCell(rightStyle, 2.0, company.getAccountConfig().getKeyCurrency().getCode());
		drawer.drawText(cellTable, 0.0, 1.2);

		if (!isNotOutputSlipAccountBook) {
			// ����敪�\��

			cellTable = new LedgerCellTable();
			// ����敪
			cellTable.addCell(rightStyle, LedgerCell.WIDTH_REST, getWord(HEADER_ACCOUNT_BOOK_NAME_WORD) + ":");
			cellTable.addCell(rightStyle, 2.0, StringUtil.leftBX(
				getWord(AccountBook.getAccountBookName(h.getAccountBook())), ACCOUNT_BOOK_NAME_LENGTH));
			drawer.drawText(cellTable, 0.0, 1.6);
		}

		// ���F��
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
		// ���F��1
		cellTable.addCell(mLeftStyle, 1.6,
			StringUtil.leftBX(getShortWord(HEADER_ACCEPT_STAMP_1_WORD), ACCEPT_STAMP_1_LENGTH));
		// ���F��2
		cellTable.addCell(mLeftStyle, 1.6,
			StringUtil.leftBX(getShortWord(HEADER_ACCEPT_STAMP_2_WORD), ACCEPT_STAMP_2_LENGTH));
		// ���F��3
		cellTable.addCell(mLeftStyle, 1.4,
			StringUtil.leftBX(getShortWord(HEADER_ACCEPT_STAMP_3_WORD), ACCEPT_STAMP_3_LENGTH));
		drawer.drawText(cellTable, 0.0, approveMark_Y);

	}

	/**
	 * ���׃w�b�_�[�`��
	 * 
	 * @throws TException
	 */
	protected void drawDetailTitle() throws TException {

		double rowHeight = ROW_HEIGHT;

		// ���׃w�b�_�[�Ƙg��
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

		// ����
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

		// ���׃w�b�_�[
		LedgerCellTable cellTable = new LedgerCellTable();
		cellTable.addCell(mCenterStyle, ROW_NO_WIDTH, "");
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, itemCaption);
		// �ؕ����z
		cellTable.addCell(mCenterStyle, AMOUNT_WIDTH, getWord(DETAIL_DR_AMOUNT_WORD));
		// �ݕ����z
		cellTable.addCell(mCenterStyle, AMOUNT_WIDTH, getWord(DETAIL_CR_AMOUNT_WORD));
		cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, "");
		// �s�E�v
		cellTable.addCell(mCenterStyle, REMARK_WIDTH, getWord(DETAIL_ROW_REMARK_WORD));
		// �v�㕔��/�����/�Ј�
		cellTable.addCell(mCenterStyle, CUSTOMER_WIDTH,
			StringUtil.leftBX(getWord(DETAIL_CUSTOMER_WORD), CUSTOMER_LENGTH));
		// �Ǘ�1/2/3
		cellTable.addCell(mCenterStyle, MANAGEMENT1TO3_WIDTH,
			StringUtil.leftBX(getWord(DETAIL_MANAGEMENT1TO3_WORD), MANAGEMENT1TO3_LENGTH));
		// �Ǘ�4/5/6
		cellTable.addCell(mCenterStyle, MANAGEMENT4TO6_WIDTH,
			StringUtil.leftBX(getWord(DETAIL_MANAGEMENT4TO6_WORD), MANAGEMENT4TO6_LENGTH));
		// ���v����
		cellTable.addCell(mCenterStyle, NON_ACCOUNT_WIDTH,
			StringUtil.leftBX(getShortWord(DETAIL_NON_ACCOUNT_WORD), NON_ACCOUNT_LENGTH));

		double detaiLineY = getDetailTopY() + 0.175d;
		drawer.drawText(cellTable, 0.0, detaiLineY);

		cellTable = new LedgerCellTable();
		cellTable.addCell(mCenterStyle, ROW_NO_WIDTH + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH, "");
		// �ʉ�
		cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, getWord(DETAIL_CURRENCY_WORD));
		cellTable.addCell(mCenterStyle, REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH
			+ NON_ACCOUNT_WIDTH, "");

		detaiLineY = getDetailTopY();
		drawer.drawText(cellTable, 0.0, detaiLineY);

		cellTable = new LedgerCellTable();
		cellTable.addCell(mCenterStyle, ROW_NO_WIDTH + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH, "");
		// ������/��
		cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, StringUtil.leftBX(getWord(DETAIL_TAX_WORD), TAX_LENGTH));
		cellTable.addCell(mCenterStyle, REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH
			+ NON_ACCOUNT_WIDTH, "");

		detaiLineY = getDetailTopY() + 0.35d;
		drawer.drawText(cellTable, 0.0, detaiLineY);

		// ���v
		cellTable = new LedgerCellTable();
		cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, getWord(DETAIL_SUM_WORD));
		cellTable.addCell(mCenterStyle, AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH + REMARK_WIDTH + CUSTOMER_WIDTH
			+ MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH, "");
		detaiLineY = getDetailTopY() + 11.30D;
		drawer.drawText(cellTable, 0.0, detaiLineY);

		// �O�݌v1
		cellTable = new LedgerCellTable();
		cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, getWord(DETAIL_FOREIGN_1_WORD));
		cellTable.addCell(mCenterStyle, AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH + REMARK_WIDTH + CUSTOMER_WIDTH
			+ MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH, "");

		detaiLineY = detaiLineY + ROW_HEIGHT;
		drawer.drawText(cellTable, 0.0, detaiLineY);

		// �O�݌v2
		cellTable = new LedgerCellTable();
		cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, getWord(DETAIL_FOREIGN_2_WORD));
		cellTable.addCell(mCenterStyle, AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH + REMARK_WIDTH + CUSTOMER_WIDTH
			+ MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH, "");

		detaiLineY = detaiLineY + ROW_HEIGHT;
		drawer.drawText(cellTable, 0.0, detaiLineY);

		SlipBookFooter f = (SlipBookFooter) getBook().getLastSheet().getFooter();
		if (f != null && f.getMaxForeignCurrencyCount() > 2) {
			// �O�ݏo�͌�����2�̏ꍇ�A�o��

			// ���׃w�b�_�[�Ƙg��
			startX = ROW_NO_WIDTH + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH;
			endX = startX + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH;
			endY = detailTopY + 0.80 + rowHeight * 3 * (getBook().getMaxRowCount() + 1);
			drawer.drawFrameBorder(middleLineStyle, startX, endY - rowHeight * 2, endX, endY);

			// �c��
			double x = startX + ITEM_WIDTH;
			drawer.drawLine(thinLineStyle, x, endY, x, endY - rowHeight * 2);
			x += AMOUNT_WIDTH;
			drawer.drawLine(thinLineStyle, x, endY, x, endY - rowHeight * 2);
			x += AMOUNT_WIDTH;
			drawer.drawLine(thinLineStyle, x, endY, x, endY - rowHeight * 2);

			double y = endY - ROW_HEIGHT;
			drawer.drawLine(thinLineStyle, startX, y, endX, y);

			// y������
			y = endY - ROW_HEIGHT * 2;

			for (int index = 2; index < f.getMaxForeignCurrencyCount(); index++) {

				String word = null;
				if (index == 2) {
					word = getWord(DETAIL_FOREIGN_3_WORD);
				} else {
					word = getWord(DETAIL_FOREIGN_4_WORD);
				}

				cellTable = new LedgerCellTable();
				// �s�ԍ�
				cellTable.addCell(mLeftStyle, ROW_NO_WIDTH + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH,
					"");
				// �P��
				cellTable.addCell(mCenterStyle, ITEM_WIDTH, word);
				// �]��
				cellTable.addCell(mCenterStyle, REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH
					+ MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH - ITEM_WIDTH, "");
				drawer.drawText(cellTable, 0.0, y);

				y += ROW_HEIGHT;
			}
		}
	}

	@Override
	protected void drawDetail(List<LedgerSheetDetail> detail) throws TException {

		// 1.���׃w�b�_�[�`��
		drawDetailTitle();

		Company company = getBook().getCompany();

		double y = getDetailTopY() + 0.80d;
		for (int i = 0; i < detail.size(); i++) {

			SlipBookDetail row = (SlipBookDetail) detail.get(i);
			SWK_DTL slipDetail = row.getSlipDetail();
			List<String> remark = StringUtil.getParagraphString(slipDetail.getSWK_GYO_TEK(), REMARK_LENGTH, 3);

			LedgerCellTable cellTable = new LedgerCellTable();

			// 1�s��
			// �s�ԍ�
			cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, Integer.toString(row.getRowNo()));
			// �Ȗ�
			cellTable.addCell(mLeftStyle, ITEM_WIDTH, StringUtil.leftBX(getItem(slipDetail), ITEM_LENGTH));

			// �O��
			String amount = FormatUtil.foreingAmountFormat(company.getAccountConfig().getKeyCurrency().getCode(),
				slipDetail.getSWK_CUR_CODE(), slipDetail.getSWK_IN_KIN(), slipDetail.getCUR_DEC_KETA());

			if (slipDetail.isDR()) {
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, amount);
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			} else {
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, amount);
			}

			// ��ʉ݂��o�̓v���p�e�B�Ή�
			String currencyCode = slipDetail.getSWK_CUR_CODE();
			if (!isShowDetailKeyCurrencyCode()) {
				currencyCode = FormatUtil.currencyFormat(company.getAccountConfig().getKeyCurrency().getCode(),
					currencyCode);
			}
			// �ʉ�
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, currencyCode);
			// �s�E�v
			cellTable.addCell(mLeftStyle, REMARK_WIDTH, remark.get(0));

			// �v�㕔��
			cellTable
				.addCell(mLeftStyle, CUSTOMER_WIDTH, StringUtil.leftBX(getDepartment(slipDetail), CUSTOMER_LENGTH));
			// �Ǘ�1
			cellTable.addCell(mLeftStyle, MANAGEMENT1TO3_WIDTH,
				StringUtil.leftBX(getManagement1(slipDetail), MANAGEMENT1TO3_LENGTH));
			// �Ǘ�4
			cellTable.addCell(mLeftStyle, MANAGEMENT4TO6_WIDTH,
				StringUtil.leftBX(getManagement4(slipDetail), MANAGEMENT4TO6_LENGTH));
			// ���v����1
			cellTable.addCell(nonAccount1Style, NON_ACCOUNT_WIDTH,
				StringUtil.leftBX(Util.avoidNull(slipDetail.getSWK_HM_1()), NON_ACCOUNT_LENGTH));

			drawer.drawText(cellTable, 0.0, y);

			y = y + ROW_HEIGHT;

			// 2�s��
			cellTable = new LedgerCellTable();
			cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
			// �⏕�Ȗ�
			cellTable.addCell(mLeftStyle, ITEM_WIDTH, StringUtil.leftBX(getSubItem(slipDetail), ITEM_LENGTH));
			// ���[�g
			String rate = FormatUtil.rateFormat(company.getAccountConfig().getKeyCurrency().getCode(),
				slipDetail.getSWK_CUR_CODE(), slipDetail.getSWK_CUR_RATE());

			if (slipDetail.isDR()) {
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, rate);
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			} else {
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, rate);
			}
			// ������
			String occurDate = DateUtil.toYMDString(slipDetail.getHAS_DATE());
			if (isAllowOccurDateBlank()) {

				if (!slipDetail.isUseItemOccurDate()) {
					// �Ȗڂ����������g�p�̏ꍇ�A�������̓u�����N�ɂ���
					occurDate = "";
				}
			}
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, occurDate);

			// �s�E�v
			cellTable.addCell(mLeftStyle, REMARK_WIDTH, remark.get(1));
			// �����
			cellTable.addCell(mLeftStyle, CUSTOMER_WIDTH, StringUtil.leftBX(getCustomer(slipDetail), CUSTOMER_LENGTH));
			// �Ǘ�2
			cellTable.addCell(mLeftStyle, MANAGEMENT1TO3_WIDTH,
				StringUtil.leftBX(getManagement2(slipDetail), MANAGEMENT1TO3_LENGTH));
			// �Ǘ�5
			cellTable.addCell(mLeftStyle, MANAGEMENT4TO6_WIDTH,
				StringUtil.leftBX(getManagement5(slipDetail), MANAGEMENT4TO6_LENGTH));
			// ���v����2
			cellTable.addCell(nonAccount2Style, NON_ACCOUNT_WIDTH,
				StringUtil.leftBX(Util.avoidNull(slipDetail.getSWK_HM_2()), NON_ACCOUNT_LENGTH));

			drawer.drawText(cellTable, 0.0, y);

			y = y + ROW_HEIGHT;

			// 3�s��
			cellTable = new LedgerCellTable();
			int aparKbn = slipDetail.getSWK_APAR_KESI_KBN();

			// BS����}�[�N
			String mark = "";
			if (isUseBS) {
				if (isShowAllBS) {
					// �����敪�ɉ����Ă��ׂċL��
					if (slipDetail.getSWK_KESI_KBN() == SWK_DTL.KESI_KBN.BASE) {
						mark = getWord("C03191"); // �� ��
					} else if (slipDetail.getSWK_KESI_KBN() == SWK_DTL.KESI_KBN.FORWARD) {
						mark = getWord("C11766"); // ��
					}
				} else {
					// �]���̏��� �s�ԍ��L�ڂ̂��̂̂݋L��
					if (slipDetail.getSWK_SOUSAI_GYO_NO() != null) {
						if (slipDetail.getSWK_KESI_KBN() == SWK_DTL.KESI_KBN.BASE) {
							mark = getWord("C03191"); // �� ��
						} else {
							mark = getWord("C11766"); // ��
						}
					}
				}
			}
			if (!isNotUseAP
				&& (aparKbn == SWK_DTL.APAR_KESI_KBN.AP_BASE || aparKbn == SWK_DTL.APAR_KESI_KBN.AP_FORWARD)) {

				if (aparKbn == SWK_DTL.APAR_KESI_KBN.AP_BASE) {
					mark += getWord("C03191"); // �� ��
				} else {
					mark += getWord("C11766"); // ��
				}
			}
			if (!isNotUseAR
				&& (aparKbn == SWK_DTL.APAR_KESI_KBN.AR_BASE || aparKbn == SWK_DTL.APAR_KESI_KBN.AR_FORWARD)) {

				if (aparKbn == SWK_DTL.APAR_KESI_KBN.AR_BASE) {
					mark += getWord("C03191"); // �� ��
				} else {
					mark += getWord("C11766"); // ��
				}
			}
			cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, mark);

			// ����Ȗ�
			cellTable.addCell(mLeftStyle, ITEM_WIDTH, StringUtil.leftBX(getDetailItem(slipDetail), ITEM_LENGTH));
			// �M��
			amount = NumberFormatUtil.formatNumber(slipDetail.getSWK_KIN(), company.getAccountConfig().getKeyCurrency()
				.getDecimalPoint());
			if (slipDetail.isDR()) {
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, amount);
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			} else {
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
				cellTable.addCell(mRightStyle, AMOUNT_WIDTH, amount);
			}
			// ��
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, StringUtil.leftBX(getTax(slipDetail), TAX_LENGTH));
			// �s�E�v
			cellTable.addCell(mLeftStyle, REMARK_WIDTH, remark.get(2));
			// �Ј�
			cellTable.addCell(mLeftStyle, CUSTOMER_WIDTH, StringUtil.leftBX(getEmployee(slipDetail), CUSTOMER_LENGTH));
			// �Ǘ�3
			cellTable.addCell(mLeftStyle, MANAGEMENT1TO3_WIDTH,
				StringUtil.leftBX(getManagement3(slipDetail), MANAGEMENT1TO3_LENGTH));
			// �Ǘ�6
			cellTable.addCell(mLeftStyle, MANAGEMENT4TO6_WIDTH,
				StringUtil.leftBX(getManagement6(slipDetail), MANAGEMENT4TO6_LENGTH));
			// ���v����3
			cellTable.addCell(nonAccount3Style, NON_ACCOUNT_WIDTH,
				StringUtil.leftBX(Util.avoidNull(slipDetail.getSWK_HM_3()), NON_ACCOUNT_LENGTH));

			drawer.drawText(cellTable, 0.0, y);

			y = y + ROW_HEIGHT;
		}

	}

	/**
	 * @return true:�������u�����N�\
	 */
	protected boolean isAllowOccurDateBlank() {
		Company company = getBook().getCompany();
		return allowOccurDateBlank && !company.getAccountConfig().isUseIfrs();
	}

	/**
	 * @param h
	 * @return �`�[�E�v
	 */
	protected String getSlipRemark(SlipBookHeader h) {
		if (layoutDefine != null && layoutDefine.isNotShowHeaderSlipRemarkCode()) {
			return Util.avoidNull(h.getRemark());
		}

		return Util.avoidNull(h.getRemarkCode()) + " " + Util.avoidNull(h.getRemark());
	}

	/**
	 * @param h
	 * @return �w�b�_�[���͕���
	 */
	protected String getHeaderDepartment(SlipBookHeader h) {
		if (layoutDefine != null && layoutDefine.isNotShowHeaderDepartmentCode()) {
			return Util.avoidNull(h.getDepartmentName());
		}

		return Util.avoidNull(h.getDepartmentCode()) + " " + Util.avoidNull(h.getDepartmentName());
	}

	/**
	 * ���͎ҏ��̎擾
	 * 
	 * @param h �w�b�_�[
	 * @return �w�b�_�[���͎ҏ��
	 */
	protected String getIraiEmployeeInfo(SlipBookHeader h) {
		if (layoutDefine != null && layoutDefine.isNotShowHeaderEmployeeCode()) {
			return Util.avoidNull(h.getEmployeeName());
		}

		return Util.avoidNull(h.getEmployeeCode()) + " " + Util.avoidNull(h.getEmployeeName());
	}

	/**
	 * @param slipDetail
	 * @return �v�㕔��
	 */
	protected String getDepartment(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowDepartmentCode()) {
			return Util.avoidNull(slipDetail.getSWK_DEP_NAME());
		}

		return Util.avoidNull(slipDetail.getSWK_DEP_CODE()) + " " + Util.avoidNull(slipDetail.getSWK_DEP_NAME());
	}

	/**
	 * @param slipDetail
	 * @return �Ȗ�
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
	 * @return �⏕�Ȗ�
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
	 * @return ����Ȗ�
	 */
	protected String getDetailItem(SWK_DTL slipDetail) {
		String name = "";
		// �R�[�hNULL������NOT NULL�͑��芨����Z�b�g�������ׁׂ̈A���̂�Ԃ�
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
	 * @return �����
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
	 * @return �Ј�
	 */
	protected String getEmployee(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowEmployeeCode()) {
			return Util.avoidNull(slipDetail.getSWK_EMP_NAME());
		}

		return Util.avoidNull(slipDetail.getSWK_EMP_CODE()) + " " + Util.avoidNull(slipDetail.getSWK_EMP_NAME());
	}

	/**
	 * @param slipDetail
	 * @return �����
	 */
	protected String getTax(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowTaxCode()) {
			return Util.avoidNull(slipDetail.getSWK_ZEI_NAME());
		}

		return Util.avoidNull(slipDetail.getSWK_ZEI_CODE()) + " " + Util.avoidNull(slipDetail.getSWK_ZEI_NAME());
	}

	/**
	 * @param slipDetail
	 * @return �Ǘ�1�o�͏��
	 */
	protected String getManagement1(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowManagementCode1()) {
			return Util.avoidNull(slipDetail.getSWK_KNR_NAME_1());
		}

		return Util.avoidNull(slipDetail.getSWK_KNR_CODE_1()) + " " + Util.avoidNull(slipDetail.getSWK_KNR_NAME_1());
	}

	/**
	 * @param slipDetail
	 * @return �Ǘ�2�o�͏��
	 */
	protected String getManagement2(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowManagementCode2()) {
			return Util.avoidNull(slipDetail.getSWK_KNR_NAME_2());
		}

		return Util.avoidNull(slipDetail.getSWK_KNR_CODE_2()) + " " + Util.avoidNull(slipDetail.getSWK_KNR_NAME_2());
	}

	/**
	 * @param slipDetail
	 * @return �Ǘ�3�o�͏��
	 */
	protected String getManagement3(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowManagementCode3()) {
			return Util.avoidNull(slipDetail.getSWK_KNR_NAME_3());
		}

		return Util.avoidNull(slipDetail.getSWK_KNR_CODE_3()) + " " + Util.avoidNull(slipDetail.getSWK_KNR_NAME_3());
	}

	/**
	 * @param slipDetail
	 * @return �Ǘ�4�o�͏��
	 */
	protected String getManagement4(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowManagementCode4()) {
			return Util.avoidNull(slipDetail.getSWK_KNR_NAME_4());
		}

		return Util.avoidNull(slipDetail.getSWK_KNR_CODE_4()) + " " + Util.avoidNull(slipDetail.getSWK_KNR_NAME_4());
	}

	/**
	 * @param slipDetail
	 * @return �Ǘ�5�o�͏��
	 */
	protected String getManagement5(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowManagementCode5()) {
			return Util.avoidNull(slipDetail.getSWK_KNR_NAME_5());
		}

		return Util.avoidNull(slipDetail.getSWK_KNR_CODE_5()) + " " + Util.avoidNull(slipDetail.getSWK_KNR_NAME_5());
	}

	/**
	 * @param slipDetail
	 * @return �Ǘ�6�o�͏��
	 */
	protected String getManagement6(SWK_DTL slipDetail) {
		if (layoutDefine != null && layoutDefine.isNotShowManagementCode6()) {
			return Util.avoidNull(slipDetail.getSWK_KNR_NAME_6());
		}

		return Util.avoidNull(slipDetail.getSWK_KNR_CODE_6()) + " " + Util.avoidNull(slipDetail.getSWK_KNR_NAME_6());
	}

	/**
	 * �t�b�^�[�̕`��
	 * 
	 * @param footer
	 */
	@Override
	protected void drawFooter(LedgerSheetFooter footer) throws TException {

		SlipBookFooter f = (SlipBookFooter) footer;
		Company company = getBook().getCompany();

		LedgerCellTable cellTable = new LedgerCellTable();

		double y = getDetailTopY() + 11.30d;

		// 1�s��
		// �s�ԍ�
		cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
		// �Ȗ�
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, "");
		// �ؕ����z
		cellTable.addCell(
			mRightStyle,
			AMOUNT_WIDTH,
			NumberFormatUtil.formatNumber(f.getDebitAmount(), company.getAccountConfig().getKeyCurrency()
				.getDecimalPoint()));
		// �ݕ����z
		cellTable.addCell(
			mRightStyle,
			AMOUNT_WIDTH,
			NumberFormatUtil.formatNumber(f.getCreditAmount(), company.getAccountConfig().getKeyCurrency()
				.getDecimalPoint()));
		// �ʉ�
		cellTable.addCell(mCenterStyle, CURRENCY_WIDTH + REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH
			+ MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH, "");

		drawer.drawText(cellTable, 0.0, y);

		y = y + ROW_HEIGHT;

		// 2�s��
		cellTable = new LedgerCellTable();
		// �s�ԍ�
		cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
		// �Ȗ�
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, "");
		if (Util.isNullOrEmpty(f.getForeignCurrencyCode1())) {
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH + REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH
				+ MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH, "");
		} else {
			// �ؕ����z
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH,
				NumberFormatUtil.formatNumber(f.getForeignDebitAmount1(), f.getForeignDecimalPoint1()));
			// �ݕ����z
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH,
				NumberFormatUtil.formatNumber(f.getForeignCreditAmount1(), f.getForeignDecimalPoint1()));
			// �ʉ�
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, f.getForeignCurrencyCode1());
			cellTable.addCell(mCenterStyle, REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH
				+ NON_ACCOUNT_WIDTH, "");
		}

		drawer.drawText(cellTable, 0.0, y);

		y = y + ROW_HEIGHT;

		// 3�s��
		cellTable = new LedgerCellTable();
		// �s�ԍ�
		cellTable.addCell(mLeftStyle, ROW_NO_WIDTH, "");
		// �Ȗ�
		cellTable.addCell(mCenterStyle, ITEM_WIDTH, "");
		if (Util.isNullOrEmpty(f.getForeignCurrencyCode2())) {
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH, "");
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH + REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH
				+ MANAGEMENT4TO6_WIDTH + NON_ACCOUNT_WIDTH, "");
		} else {
			// �ؕ����z
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH,
				NumberFormatUtil.formatNumber(f.getForeignDebitAmount2(), f.getForeignDecimalPoint2()));
			// �ݕ����z
			cellTable.addCell(mRightStyle, AMOUNT_WIDTH,
				NumberFormatUtil.formatNumber(f.getForeignCreditAmount2(), f.getForeignDecimalPoint2()));
			// �ʉ�
			cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, f.getForeignCurrencyCode2());
			cellTable.addCell(mCenterStyle, REMARK_WIDTH + CUSTOMER_WIDTH + MANAGEMENT1TO3_WIDTH + MANAGEMENT4TO6_WIDTH
				+ NON_ACCOUNT_WIDTH, "");
		}

		drawer.drawText(cellTable, 0.0, y);

		if (f.getMaxForeignCurrencyCount() > 2) {
			// �O�ݏo�͌�����2�̏ꍇ�A�o��

			// ��
			double rowHeight = ROW_HEIGHT;

			// ���׃w�b�_�[�Ƙg��
			double startX = ROW_NO_WIDTH + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH;

			double detailTopY = getDetailTopY();
			double endX = startX + ITEM_WIDTH + AMOUNT_WIDTH + AMOUNT_WIDTH + CURRENCY_WIDTH;
			double endY = detailTopY + 0.80 + rowHeight * 3 * (getBook().getMaxRowCount() + 1);
			drawer.drawFrameBorder(middleLineStyle, startX, endY - rowHeight * 2, endX, endY);

			// �c��
			double x = startX + ITEM_WIDTH;
			drawer.drawLine(thinLineStyle, x, endY, x, endY - rowHeight * 2);
			x += AMOUNT_WIDTH;
			drawer.drawLine(thinLineStyle, x, endY, x, endY - rowHeight * 2);
			x += AMOUNT_WIDTH;
			drawer.drawLine(thinLineStyle, x, endY, x, endY - rowHeight * 2);

			y = endY - ROW_HEIGHT;
			drawer.drawLine(thinLineStyle, startX, y, endX, y);

			// y������
			y = endY - ROW_HEIGHT * 2;

			for (int index = 2; index < f.getMaxForeignCurrencyCount(); index++) {

				cellTable = new LedgerCellTable();
				// �s�ԍ�
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

					// �ؕ����z
					cellTable.addCell(mRightStyle, AMOUNT_WIDTH, debit);
					// �ݕ����z
					cellTable.addCell(mRightStyle, AMOUNT_WIDTH, credit);
					// �ʉ�
					cellTable.addCell(mCenterStyle, CURRENCY_WIDTH, f.getForeignCurrencyCode(index));
					// �]��
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
	 * ���ׂ̊J�nY���W��Ԃ�
	 * 
	 * @return ���ׂ̊J�nY���W
	 */
	protected double getDetailTopY() {
		return 4.35d;
	}

	/**
	 * �w�b�_�[�̘g���`��
	 */
	protected void drawHeaderFrameBorder() {

		double startX = 0.0;
		double endX = HEADER_TITLE_WIDTH + HEADER_DEPARTMENT_WIDTH + HEADER_TITLE_WIDTH + HEADER_DEPARTMENT_WIDTH;
		double startY = 2.55d;
		double endY = 4.05d;

		drawer.drawFrameBorder(middleLineStyle, startX, startY, endX, endY);
	}

	/**
	 * �`�[���C�A�E�g��`�̐ݒ�
	 * 
	 * @param define �`�[���C�A�E�g��`
	 */
	public void setDefine(SlipLayoutDefine define) {

		if (define == null) {
			return;
		}

		this.layoutDefine = define;

		// �w�b�_�[�P��
		this.HEADER_SLIP_DATE_WORD = define.getHeaderSlipDateWord(); // �w�b�_�[�F�`�[���t
		this.HEADER_DEPARTMENT_NAME_WORD = define.getHeaderDepartmentNameWord(); // �w�b�_�[�F���͕���
		this.HEADER_EMPLOYEE_NAME_WORD = define.getHeaderEmployeeNameWord(); // �w�b�_�[�F���͎�
		this.HEADER_SLIP_NO_WORD = define.getHeaderSlipNoWord(); // �w�b�_�[�F�`�[�ԍ�
		this.HEADER_UPDATE_WORD = define.getHeaderUpdateWord(); // �w�b�_�[�F�C��
		this.HEADER_UPDATE_COUNT_WORD = define.getHeaderUpdateCountWord(); // �w�b�_�[�F��
		this.HEADER_VOUCHER_NO_WORD = define.getHeaderVoucherNoWord(); // �w�b�_�[�F�؜ߔԍ�
		this.HEADER_SLIP_REMARK_WORD = define.getHeaderSlipRemarkWord(); // �w�b�_�[�F�`�[�E�v
		this.HEADER_KEY_CURRENCY_WORD = define.getHeaderKeyCurrencyWord(); // �w�b�_�[�F��ʉ�
		this.HEADER_ACCOUNT_BOOK_NAME_WORD = define.getHeaderAccountBookNameWord(); // �w�b�_�[�F����敪
		this.HEADER_ACCEPT_STAMP_1_WORD = define.getHeaderAcceptStamp1Word(); // �w�b�_�[�F���F��1
		this.HEADER_ACCEPT_STAMP_2_WORD = define.getHeaderAcceptStamp2Word(); // �w�b�_�[�F���F��2
		this.HEADER_ACCEPT_STAMP_3_WORD = define.getHeaderAcceptStamp3Word(); // �w�b�_�[�F���F��3

		// ���׃^�C�g���P��
		this.DETAIL_DR_AMOUNT_WORD = define.getDetailDrAmountWord(); // ���ׁF�ؕ����z
		this.DETAIL_CR_AMOUNT_WORD = define.getDetailCrAmountWord(); // ���ׁF�ݕ����z
		this.DETAIL_ROW_REMARK_WORD = define.getDetailRowRemarkWord(); // ���ׁF�s�E�v
		this.DETAIL_CUSTOMER_WORD = define.getDetailCustomerWord(); // ���ׁF�v�㕔��/�����/�Ј�
		this.DETAIL_CURRENCY_WORD = define.getDetailCurrencyWord(); // ���ׁF�ʉ�
		this.DETAIL_TAX_WORD = define.getDetailTaxWord(); // ���ׁF������/��
		this.DETAIL_MANAGEMENT1TO3_WORD = define.getDetailManagement1To3Word(); // ���ׁF�Ǘ�1/2/3
		this.DETAIL_MANAGEMENT4TO6_WORD = define.getDetailManagement4To6Word(); // ���ׁF�Ǘ�4/5/6
		this.DETAIL_NON_ACCOUNT_WORD = define.getDetailNonAccountWord(); // ���ׁF���v����1/2/3
		this.DETAIL_SUM_WORD = define.getDetailSumWord(); // ���ׁF���v
		this.DETAIL_FOREIGN_1_WORD = define.getDetailForeign1Word(); // ���ׁF�O�݌v1
		this.DETAIL_FOREIGN_2_WORD = define.getDetailForeign2Word(); // ���ׁF�O�݌v2
		this.DETAIL_FOREIGN_3_WORD = define.getDetailForeign3Word(); // ���ׁF�O�݌v3
		this.DETAIL_FOREIGN_4_WORD = define.getDetailForeign4Word(); // ���ׁF�O�݌v4

		// ��
		this.ROW_HEIGHT = define.getRowHeight(); // �s����
		this.HEADER_TITLE_WIDTH = define.getHeaderTitleWidth(); // �w�b�_�[�^�C�g����
		this.HEADER_DEPARTMENT_WIDTH = define.getHeaderDepartmentWidth(); // �w�b�_�[���啝
		this.REST_WIDTH = define.getDetailRestWidth(); // ��s��
		this.ROW_NO_WIDTH = define.getDetailRowNoWidth(); // �s�ԍ���
		this.ITEM_WIDTH = define.getDetailItemWidth(); // �Ȗڕ�
		this.AMOUNT_WIDTH = define.getDetailAmountWidth(); // ���z��
		this.CURRENCY_WIDTH = define.getDetailCurrencyWidth(); // �ʉݕ�
		this.REMARK_WIDTH = define.getDetailRemarkWidth(); // �E�v��
		this.CUSTOMER_WIDTH = define.getDetailCustomerWidth(); // ����敝
		this.MANAGEMENT1TO3_WIDTH = define.getDetailManagement1To3Width(); // �Ǘ�1�`3��
		this.MANAGEMENT4TO6_WIDTH = define.getDetailManagement4To6Width(); // �Ǘ�4�`6��
		this.NON_ACCOUNT_WIDTH = define.getDetailNonAccountWidth(); // ���v���ו�

		// ����
		this.ACCOUNT_BOOK_NAME_LENGTH = define.getHeaderAccountBookNameLength(); // ����敪����
		this.UPDATE_COUNT_LENGTH = define.getHeaderUpdateCountLength(); // �C���񐔌���
		this.ACCEPT_STAMP_1_LENGTH = define.getHeaderAcceptStamp1Length(); // ���F��1����
		this.ACCEPT_STAMP_2_LENGTH = define.getHeaderAcceptStamp2Length(); // ���F��2����
		this.ACCEPT_STAMP_3_LENGTH = define.getHeaderAcceptStamp3Length(); // ���F��3����
		this.ITEM_LENGTH = define.getDetailItemLength(); // �Ȗڌ���
		this.TAX_LENGTH = define.getDetailTaxLength(); // ����Ō���
		this.REMARK_LENGTH = define.getDetailRemarkLength(); // �E�v����
		this.CUSTOMER_LENGTH = define.getDetailCustomerLength(); // ����挅��
		this.MANAGEMENT1TO3_LENGTH = define.getDetailManagement1To3Length(); // �Ǘ�1�`3����
		this.MANAGEMENT4TO6_LENGTH = define.getDetailManagement4To6Length(); // �Ǘ�4�`6����
		this.NON_ACCOUNT_LENGTH = define.getDetailNonAccountLength(); // ���v���׌���

		this.HEADER_DEPARTMENT_NAME_LENGTH = define.getHeaderDepartmentNameLength(); // ���͕��包��

	}

	/**
	 * �y�[�W�ԍ��\���������Ԃ�
	 * 
	 * @return �y�[�W�ԍ��\��������
	 */
	@Override
	protected String getPageNoChar() {

		int startPageNo = 0;
		int sheetCount = book.getSheetCount();

		if (book instanceof SlipBook) {
			SlipBook slipBook = (SlipBook) book;

			// �J�n�y�[�W�ԍ��̐ݒ�
			startPageNo = slipBook.getStartPageNo();

			// �w�葍�y�[�W��������΁A���Y���y�[�W�����o�͂���
			if (slipBook.getTotalPageCount() > 0) {
				sheetCount = slipBook.getTotalPageCount();
			}
		}

		return Integer.toString(startPageNo + currentPageNo) + "/" + Integer.toString(sheetCount);
	}

	/**
	 * @return true:�w�b�_�[��ʉ݂��\��
	 */
	protected boolean isShowHeaderKeyCurrencyCode() {
		return ServerConfig.isFlagOn("trans.slip.layout.header.key.currency.visable");
	}

	/**
	 * @return true:�w�b�_�[��ʉ݂��\��
	 */
	protected boolean isShowDetailKeyCurrencyCode() {
		return ServerConfig.isFlagOn("trans.slip.layout.detail.key.currency.visable");
	}

}
