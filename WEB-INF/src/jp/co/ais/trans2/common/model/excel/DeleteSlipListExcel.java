package jp.co.ais.trans2.common.model.excel;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.ledger.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * �폜�`�[���X�g�G�N�Z��
 * 
 * @author AIS
 */
public class DeleteSlipListExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public DeleteSlipListExcel(String lang) {
		super(lang);
	}

	/**
	 * �G�N�Z����Ԃ�
	 * 
	 * @param book
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(LedgerBook book) throws TException {
		try {
			createReport(book);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param book
	 */
	public void createReport(LedgerBook book) {

		DeleteSlipListBook listBook = (DeleteSlipListBook) book;

		// �\���敪
		boolean showUserInfo = listBook.isShowUserInfo();

		// �V�[�g�擾
		TExcelSheet sheet = createSheet(listBook);

		for (LedgerSheet bookSheet : book.getSheet()) {

			for (LedgerSheetDetail detailRow : bookSheet.getDetail()) {

				DeleteSlipListDetail bean = (DeleteSlipListDetail) detailRow;

				TExcelRow newrow = sheet.addRow();
				// �폜�`�[No
				newrow.addCell(bean.getDelSlipNo());
				// �폜��
				newrow.addCell(DateUtil.toYMDDate(bean.getDelDate()));

				if (showUserInfo) {

					// ���[�U�[ID
					newrow.addCell(bean.getUsrID());

					// ���[�U�[��
					newrow.addCell(bean.getUsrName());
				}
			}
		}

	}

	/**
	 * �V�[�g���쐬����B
	 * 
	 * @param listBook
	 * @return �V�[�g
	 */
	private TExcelSheet createSheet(DeleteSlipListBook listBook) {

		// �V�K���[�N�V�[�g���쐬����
		// �폜�`�[���X�g
		TExcelSheet sheetExcel = addSheet(getWord("C01610"));
		// �폜�`�[�ԍ�
		sheetExcel.addColumn(getWord("C11097"), 9000);
		// �폜��
		sheetExcel.addColumn(getWord("C01613"), 3600);

		if (listBook.isShowUserInfo()) {
			// ���[�U�[ID
			sheetExcel.addColumn(getWord("C10995"), 4000);

			// ���[�U�[��
			sheetExcel.addColumn(getWord("C11923"), 6000);
		}

		return sheetExcel;

	}

}
