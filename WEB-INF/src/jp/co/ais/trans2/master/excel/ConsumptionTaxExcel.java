package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * ����ňꗗ�G�N�Z��
 */
public class ConsumptionTaxExcel extends TExcel {

	/** Invoice���x true:�g�p���� */
	protected boolean isInvoice = false;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 * @param condition
	 */
	public ConsumptionTaxExcel(String lang, ConsumptionTaxSearchCondition condition) {
		super(lang);
		isInvoice = condition.isInvoiceFlg();
	}

	/**
	 * ����Ń}�X�^�ꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param list ����Ń��X�g
	 * @return ����ňꗗ�G�N�Z��
	 * @throws TException
	 */
	public byte[] getExcel(List<ConsumptionTax> list) throws TException {

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
	public void createReport(List<ConsumptionTax> list) {
		// �V�[�g�ǉ� �}�X�^
		TExcelSheet sheet = addSheet(getWord("C02324"));

		// �J�����ݒ�
		// ����ŃR�[�h
		sheet.addColumn(getWord("C00573"), 4000);
		// ����Ŗ���
		sheet.addColumn(getWord("C00774"), 10000);
		// ����ŗ���
		sheet.addColumn(getWord("C00775"), 10000);
		// ����Ō�������
		sheet.addColumn(getWord("C00828"), 10000);
		// ����d���敪
		sheet.addColumn(getWord("C01283"), 4000);
		// ����ŗ�
		sheet.addColumn(getWord("C00777"), 4000);
		// ����Ōv�Z���o�͏���
		sheet.addColumn(getWord("C02045"), 5000);

		if (isInvoice) {
			// ��K�i���������s���Ǝ�
			sheet.addColumn(getWord("C12197"), 6000);
			// �o�ߑ[�u�T���\��
			sheet.addColumn(getWord("C12228"), 4500);
		}

		// �J�n�N����
		sheet.addColumn(getWord("C00055"), 4200);
		// �I���N����
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (ConsumptionTax ct : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(ct.getCode());
			newRow.addCell(ct.getName());
			newRow.addCell(ct.getNames());
			newRow.addCell(ct.getNamek());
			newRow.addCell(getWord(TaxType.getName(ct.getTaxType())), SwingConstants.CENTER);
			newRow.addCell(ct.getRate(), 1);
			newRow.addCell(ct.isTaxConsumption() ? ct.getOdr() : getWord("C00282"), SwingConstants.RIGHT);

			if (isInvoice) {
				// ��K�i���������s���Ǝ�
				newRow.addCell(ct.isNO_INV_REG_FLG() ? getWord("C12198") : "", SwingConstants.CENTER);
				// �o�ߑ[�u�T���\��
				String rate = "";
				if (!DecimalUtil.isNullOrZero(ct.getKEKA_SOTI_RATE())) {
					rate = NumberFormatUtil.formatNumber(ct.getKEKA_SOTI_RATE(), 0) + "%";
				}
				newRow.addCell(rate, SwingConstants.RIGHT);
			}

			newRow.addCell(DateUtil.toYMDString(ct.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(ct.getDateTo()), SwingConstants.CENTER);
		}
	}
}
