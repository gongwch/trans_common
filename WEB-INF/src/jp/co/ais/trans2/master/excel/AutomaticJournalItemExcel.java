package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.item.*;

/**
 * �Ȗڐ���敪�ꗗ�G�N�Z��
 */
public class AutomaticJournalItemExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public AutomaticJournalItemExcel(String lang) {
		super(lang);
	}

	/**
	 * �Ȗڐ���敪�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param list �Ȗڐ���敪�ꗗ
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<AutomaticJournalItem> list) throws TException {

		try {
			createReport(list);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param list
	 */
	public void createReport(List<AutomaticJournalItem> list) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C00911"));

		// �J�����ݒ�
		sheet.addColumn(getWord("C01008"), 3200);
		sheet.addColumn(getWord("C11885"), 6400);
		sheet.addColumn(getWord("C00571"), 4200);
		sheet.addColumn(getWord("C10389"), 6400);
		sheet.addColumn(getWord("C00572"), 4200);
		sheet.addColumn(getWord("C00700"), 6400);
		sheet.addColumn(getWord("C00602"), 4200);
		sheet.addColumn(getWord("C00701"), 6400);
		sheet.addColumn(getWord("C00603"), 4200);
		sheet.addColumn(getWord("C00702"), 6400);

		// ���ו`��
		for (AutomaticJournalItem bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getKMK_CNT());
			newRow.addCell(bean.getKMK_CNT_NAME());
			newRow.addCell(bean.getDEP_CODE());
			newRow.addCell(bean.getDEP_NAME());
			newRow.addCell(bean.getKMK_CODE());
			newRow.addCell(bean.getKMK_NAME());
			newRow.addCell(bean.getHKM_CODE());
			newRow.addCell(bean.getHKM_NAME());
			newRow.addCell(bean.getUKM_CODE());
			newRow.addCell(bean.getUKM_NAME());
		}

	}
}