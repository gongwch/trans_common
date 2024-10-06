package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.currency.Currency;

/**
 * �ʉ݈ꗗ�G�N�Z��
 */
public class CurrencyExcel extends TExcel {

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 */
	public CurrencyExcel(String lang) {
		super(lang);
	}

	/**
	 * �ʉ݈ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param CurrencyList �ʉ݈ꗗ
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<Currency> CurrencyList) throws TException {

		try {
			createReport(CurrencyList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param currencyList
	 */
	public void createReport(List<Currency> currencyList) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C01985"));

		// �J�����ݒ�
		sheet.addColumn(getWord("C00665"), 3200);// �ʉ݃R�[�h
		sheet.addColumn(getWord("C00880"), 9900);// �ʉݖ���
		sheet.addColumn(getWord("C00881"), 6400);// �ʉݗ���
		sheet.addColumn(getWord("C00882"), 6400);// �ʉ݌�������
		sheet.addColumn(getWord("C00896"), 4200);// ���[�g�W��
		sheet.addColumn(getWord("C00884"), 4200);// �����_�ȉ�����
		sheet.addColumn(getWord("C00055"), 4200);// �J�n�N����
		sheet.addColumn(getWord("C00261"), 4200);// �I���N����

		// ���ו`��
		for (Currency currency : currencyList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(currency.getCode(), SwingConstants.CENTER);
			newRow.addCell(currency.getName());
			newRow.addCell(currency.getNames());
			newRow.addCell(currency.getNamek());
			newRow.addCell(currency.getRatePow());
			newRow.addCell(currency.getDecimalPoint());
			newRow.addCell(DateUtil.toYMDString(currency.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(currency.getDateTo()), SwingConstants.CENTER);
		}

	}
}