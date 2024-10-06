package jp.co.ais.trans2.master.excel;

import java.util.List;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.TExcel;
import jp.co.ais.trans2.common.excel.TExcelRow;
import jp.co.ais.trans2.common.excel.TExcelSheet;
import jp.co.ais.trans2.common.model.format.RateFormat;
import jp.co.ais.trans2.define.RateType;
import jp.co.ais.trans2.model.currency.*;

/**
 * �ʉ݃��[�g�}�X�^�G�N�Z��
 * 
 * @author AIS
 */
public class RateExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public RateExcel(String lang) {
		super(lang);
	}

	/**
	 * �ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param list
	 * @return �G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(List<Rate> list) throws TException {

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
	public void createReport(List<Rate> list) {

		// �V�[�g�ǉ�
		// �ʉ݃��[�g�}�X�^
		TExcelSheet sheet = addSheet(getWord("C11158"));

		// �J�����ݒ�
		// ���[�g�敪
		sheet.addColumn(getWord("C00883"), 8400);
		// �ʉ݃R�[�h
		sheet.addColumn(getWord("C00665"), 4200);
		// �K�p�J�n��
		sheet.addColumn(getWord("C03741"), 6300);
		// ���[�g
		sheet.addColumn(getWord("C00556"), 6300);

		// ���ו`��
		for (Rate rate : list) {
			TExcelRow newRow = sheet.addRow();
			newRow.addCell(getWord(RateType.getName(rate.getRateType())));
			newRow.addCell(rate.getCurrency().getCode());
			newRow.addCell(rate.getTermFrom());
			newRow.addCell(rate.getCurrencyRate(), RateFormat.getRateDecimalPoint());
		}
	}
}