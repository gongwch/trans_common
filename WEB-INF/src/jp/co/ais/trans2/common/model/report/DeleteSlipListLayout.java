package jp.co.ais.trans2.common.model.report;

import java.util.*;

import com.klg.jclass.page.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.report.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.common.report.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �폜�`�[���X�g���C�A�E�g
 * 
 * @author AIS
 */
public class DeleteSlipListLayout extends TReportLayout {

	/** ���t */
	protected String date;

	/** �폜�`�[NO */
	protected static double NO_WIDTH = 4.5d;

	/** �폜�� */
	protected static double DATE_WIDTH = 3.9d;

	/** �폜���[�U�[�\���̏ꍇ�A�폜�`�[NO */
	protected static double NO_USR_WIDTH = 2.5d;

	/** �폜���[�U�[�\���̏ꍇ�A�폜�� */
	protected static double DATE_USR_WIDTH = 2.0d;

	/** �폜���[�U�[ */
	protected static double USR_WIDTH = 3.9d;

	/**
	 * �폜�`�[���X�g�f�[�^���C�A�E�g
	 * 
	 * @param lang ����R�[�h
	 * @throws TException
	 */
	public DeleteSlipListLayout(String lang) throws TException {
		super(lang, ReportLayout.A4_PORTRAIT);
	}

	@Override
	protected void initFrameSize() {
		setFrameSize(17.0, 27.7);
		setFrameLocation(2.0, 1.0);
	}

	@Override
	protected void initWord() {

		SlipCondition condition = getBook().getCondition();
		// �`�[���t
		date = "( " + getWord("C00599") + "�F " + DateUtil.toYMDString(condition.getSlipDateFrom()) + " �` "
			+ DateUtil.toYMDString(condition.getSlipDateTo()) + " )";
	}

	protected DeleteSlipListBook getBook() {
		return (DeleteSlipListBook) book;
	}

	/**
	 * �w�b�_�[�̕`��
	 * 
	 * @param header �w�b�_�[
	 * @throws TException
	 */
	@Override
	protected void drawHeader(@SuppressWarnings("unused") LedgerSheetHeader header) throws TException {

		SlipCondition condition = getBook().getCondition();

		boolean showUserInfo = getBook().isShowUserInfo();
		// �^�C�g��
		// �폜�`�[���X�g
		drawer.drawText(titleStyle, getWord("C01610"), 0.0, 0.0);

		// ��Ж�
		drawer.drawText(leftStyle, condition.getCompany().getName(), 0.0, 0.0);

		// �Ώ۔N��
		drawer.drawText(centerStyle, date, 0.0, 1.0);

		// �o�͏��
		drawOutputInformation(drawer, 0.0, 0.40);
		// ���ׂ̃w�b�_�[�^�C�g��
		LedgerCellTable cellTable = new LedgerCellTable();

		if (!showUserInfo) {
			// �폜�`�[�ԍ�
			cellTable.addCell(leftStyle, NO_WIDTH, getWord("C11097"));
			// �폜��
			cellTable.addCell(leftStyle, DATE_WIDTH, getWord("C01613"));
			// �폜�`�[�ԍ�
			cellTable.addCell(leftStyle, NO_WIDTH, getWord("C11097"));
			// �폜��
			cellTable.addCell(leftStyle, DATE_WIDTH, getWord("C01613"));

		} else {
			// �폜�`�[�ԍ�
			cellTable.addCell(leftStyle, NO_USR_WIDTH, getWord("C11097"));
			// �폜��
			cellTable.addCell(leftStyle, DATE_USR_WIDTH, getWord("C01613"));
			// �폜���[�U�[
			cellTable.addCell(leftStyle, USR_WIDTH, getWord("C11924"));
			// �폜�`�[�ԍ�
			cellTable.addCell(leftStyle, NO_USR_WIDTH, getWord("C11097"));
			// �폜��
			cellTable.addCell(leftStyle, DATE_USR_WIDTH, getWord("C01613"));
			// �폜���[�U�[
			cellTable.addCell(leftStyle, USR_WIDTH, getWord("C11924"));

		}

		drawer.drawText(cellTable, new JCUnit.Point(JCUnit.CM, 0.0, 2.00));
		drawer.drawLine(titleLineStyle, 0.0, 2.40, drawer.getLedgerDoubleWidth(), 2.40);

	}

	/**
	 * ���ׂ̕`��
	 * 
	 * @param list
	 */
	@Override
	protected void drawDetail(List<LedgerSheetDetail> list) throws TException {

		double y = 2.40;

		int maxRowNo = getBook().getMaxRowCount() / 2;

		boolean showUserInfo = getBook().isShowUserInfo();

		if (!showUserInfo) {
			for (int i = 0; i < list.size(); i++) {

				DeleteSlipListDetail detail = (DeleteSlipListDetail) list.get(i);

				LedgerCellTable cellTable = new LedgerCellTable();

				if (i < maxRowNo) {
					cellTable.addCell(leftStyle, NO_WIDTH, detail.getDelSlipNo());
					cellTable.addCell(leftStyle, DATE_WIDTH, DateUtil.toYMDString(detail.getDelDate()));
					cellTable.addCell(leftStyle, NO_WIDTH, "");
					cellTable.addCell(leftStyle, DATE_WIDTH, "");

					drawer.drawLine(brokenLineStyle, 0.0, y + 0.5, drawer.getLedgerDoubleWidth(), y + 0.5);

				} else {
					cellTable.addCell(leftStyle, NO_WIDTH, "");
					cellTable.addCell(leftStyle, DATE_WIDTH, "");
					cellTable.addCell(leftStyle, NO_WIDTH, detail.getDelSlipNo());
					cellTable.addCell(leftStyle, DATE_WIDTH, DateUtil.toYMDString(detail.getDelDate()));
				}

				drawer.drawText(cellTable, 0.0, y + 0.10);

				y = y + 0.5;

				if (i == maxRowNo - 1) {
					y = 2.40;
				}

			}

		} else {
			for (int i = 0; i < list.size(); i++) {

				DeleteSlipListDetail detail = (DeleteSlipListDetail) list.get(i);

				LedgerCellTable cellTable = new LedgerCellTable();

				if (i < maxRowNo) {
					cellTable.addCell(leftStyle, NO_USR_WIDTH, detail.getDelSlipNo());
					cellTable.addCell(leftStyle, DATE_USR_WIDTH, DateUtil.toYMDString(detail.getDelDate()));
					cellTable.addCell(leftStyle, USR_WIDTH,
						StringUtil.leftBX(detail.getUsrID() + " " + detail.getUsrName(), 20));
					cellTable.addCell(leftStyle, NO_USR_WIDTH, "");
					cellTable.addCell(leftStyle, DATE_USR_WIDTH, "");
					cellTable.addCell(leftStyle, USR_WIDTH, "");

					drawer.drawLine(brokenLineStyle, 0.0, y + 0.5, drawer.getLedgerDoubleWidth(), y + 0.5);

				} else {
					cellTable.addCell(leftStyle, NO_USR_WIDTH, "");
					cellTable.addCell(leftStyle, DATE_USR_WIDTH, "");
					cellTable.addCell(leftStyle, USR_WIDTH, "");
					cellTable.addCell(leftStyle, NO_USR_WIDTH, detail.getDelSlipNo());
					cellTable.addCell(leftStyle, DATE_USR_WIDTH, DateUtil.toYMDString(detail.getDelDate()));
					cellTable.addCell(leftStyle, USR_WIDTH,
						StringUtil.leftBX(detail.getUsrID() + " " + detail.getUsrName(), 20));
				}

				drawer.drawText(cellTable, 0.0, y + 0.10);

				y = y + 0.5;

				if (i == maxRowNo - 1) {
					y = 2.40;
				}

			}
		}

	}

}