package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.company.*;
import jp.co.ais.trans2.model.item.*;

/**
 * ����Ȗڈꗗ�G�N�Z��
 */
public class DetailItemExcel extends TExcel {

	/** �������t���O�g�����ǂ��� */
	protected static boolean isUseOccurDate = ServerConfig.isFlagOn("trans.MG0080.use.occurdate");

	/** ��Џ�� */
	protected Company company = null;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param lang ����R�[�h
	 * @param company
	 */
	public DetailItemExcel(String lang, Company company) {
		super(lang);
		this.company = company;
	}

	/**
	 * ����Ȗڈꗗ���G�N�Z���ŕԂ�
	 * 
	 * @param ItemList �Ȗڈꗗ
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<Item> ItemList) throws TException {

		try {
			createReport(ItemList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * �G�N�Z�����o�͂���
	 * 
	 * @param itemList
	 */
	public void createReport(List<Item> itemList) {

		// �V�[�g�ǉ�
		TExcelSheet sheet = addSheet(getWord("C02320"));

		// �J�����ݒ�
		sheet.addColumn(getWord("C00572"), 3500);
		sheet.addColumn(getWord("C00700"), 10000);
		sheet.addColumn(getWord("C00890"), 3500);
		sheet.addColumn(getWord("C00934"), 8400);
		sheet.addColumn(getWord("C00876"), 3500);
		sheet.addColumn(getWord("C00877"), 8400);
		sheet.addColumn(getWord("C00878"), 6600);
		sheet.addColumn(getWord("C00879"), 6400);
		sheet.addColumn(getWord("C00573"), 4200);
		sheet.addColumn(getWord("C01272"), 7000);
		sheet.addColumn(getWord("C01155"), 7000);
		sheet.addColumn(getWord("C01188"), 7000);
		sheet.addColumn(getWord("C01049"), 7000);
		sheet.addColumn(getWord("C01083"), 7000);
		sheet.addColumn(getWord("C01079"), 7000);
		sheet.addColumn(getWord("C01081"), 7000);
		sheet.addColumn(getWord("C01102"), 7000);
		sheet.addColumn(getWord("C01094"), 7000);
		sheet.addColumn(getWord("C01134"), 5000);
		sheet.addColumn(getWord("C01120"), 5500);
		if (company.getAccountConfig().getManagement1Name() != null) {
			sheet.addColumn(getWord(company.getAccountConfig().getManagement1Name() + getWord("C02386")), 5000);
		} else {
			sheet.addColumn(getWord("C01026"), 5000);
		}
		if (company.getAccountConfig().getManagement2Name() != null) {
			sheet.addColumn(getWord(company.getAccountConfig().getManagement2Name() + getWord("C02386")), 5000);
		} else {
			sheet.addColumn(getWord("C01028"), 5000);
		}
		if (company.getAccountConfig().getManagement3Name() != null) {
			sheet.addColumn(getWord(company.getAccountConfig().getManagement3Name() + getWord("C02386")), 5000);
		} else {
			sheet.addColumn(getWord("C01030"), 5000);
		}
		if (company.getAccountConfig().getManagement4Name() != null) {
			sheet.addColumn(getWord(company.getAccountConfig().getManagement4Name() + getWord("C02386")), 5000);
		} else {
			sheet.addColumn(getWord("C01032"), 5000);
		}
		if (company.getAccountConfig().getManagement5Name() != null) {
			sheet.addColumn(getWord(company.getAccountConfig().getManagement5Name() + getWord("C02386")), 5000);
		} else {
			sheet.addColumn(getWord("C01034"), 5000);
		}
		if (company.getAccountConfig().getManagement6Name() != null) {
			sheet.addColumn(getWord(company.getAccountConfig().getManagement6Name() + getWord("C02386")), 5000);
		} else {
			sheet.addColumn(getWord("C01036"), 5000);
		}
		if (company.getAccountConfig().getNonAccounting1Name() != null) {
			sheet.addColumn(getWord(company.getAccountConfig().getNonAccounting1Name() + getWord("C02386")), 5000);
		} else {
			sheet.addColumn(getWord("C01288"), 5000);
		}
		if (company.getAccountConfig().getNonAccounting2Name() != null) {
			sheet.addColumn(getWord(company.getAccountConfig().getNonAccounting2Name() + getWord("C02386")), 5000);
		} else {
			sheet.addColumn(getWord("C01289"), 5000);
		}
		if (company.getAccountConfig().getNonAccounting3Name() != null) {
			sheet.addColumn(getWord(company.getAccountConfig().getNonAccounting3Name() + getWord("C02386")), 5000);
		} else {
			sheet.addColumn(getWord("C01290"), 5000);
		}
		sheet.addColumn(getWord("C01282"), 6000);
		sheet.addColumn(getWord("C01088"), 6000);
		sheet.addColumn(getWord("C01223"), 5000);
		sheet.addColumn(getWord("C01301"), 5000);
		if (isUseOccurDate) {
			sheet.addColumn(getWord("C11619"), 5000); // �������t���O
		}
		sheet.addColumn(getWord("C00055"), 4200);
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (Item bean : itemList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getSubItem().getCode());
			newRow.addCell(bean.getSubItem().getName());
			newRow.addCell(bean.getDetailItem().getCode());
			newRow.addCell(bean.getDetailItem().getName());
			newRow.addCell(bean.getDetailItem().getNames());
			newRow.addCell(bean.getDetailItem().getNamek());
			newRow.addCell(bean.getDetailItem().getConsumptionTax().getCode());
			newRow.addCell(getBoo(bean.getDetailItem().isUseInputCashFlowSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseOutputCashFlowSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseTransferSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseExpenseSettlementSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUsePaymentAppropriateSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseReceivableAppropriateSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseReceivableAppropriateSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseAssetsEntrySlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUsePaymentRequestSlip()), SwingConstants.CENTER);
			newRow.addCell(getWord(getName(bean.getDetailItem().getClientType())), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseEmployee()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseManagement1()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseManagement2()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseManagement3()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseManagement4()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseManagement5()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseManagement6()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseNonAccount1()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseNonAccount2()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseNonAccount3()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseSalesTaxation()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUsePurchaseTaxation()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getDetailItem().isUseForeignCurrency()), SwingConstants.CENTER);
			newRow.addCell(getBoo2(bean.getDetailItem().isDoesCurrencyEvaluate()), SwingConstants.CENTER);
			if (isUseOccurDate) {
				newRow.addCell(getWord(getBoo(bean.getDetailItem().isUseOccurDate())), SwingConstants.CENTER);
			}
			newRow.addCell(DateUtil.toYMDString(bean.getDetailItem().getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(bean.getDetailItem().getDateTo()), SwingConstants.CENTER);

		}

	}

	/**
	 * BOOLEAN��string�ł�Ԃ�
	 * 
	 * @param castString
	 * @return string
	 */
	public String getBoo(boolean castString) {

		if (castString) {
			return getWord("C01276");// ���͉�
		} else {
			return getWord("C01279");// ���͕s��

		}
	}

	/**
	 * BOOLEAN��String�ł�Ԃ��B�Ǘ��Ȗڕ\���p�B
	 * 
	 * @param castStringKnr
	 * @return string
	 */
	public String getBoo1(boolean castStringKnr) {

		if (castStringKnr) {
			return getWord("C02371");// ���͕K�{
		} else {
			return getWord("C01279");// ���͕s��

		}
	}

	/**
	 * BOOLEAN��String�ł�Ԃ��B�]���֕\���p�B
	 * 
	 * @param castStringExc
	 * @return string
	 */
	public String getBoo2(boolean castStringExc) {

		if (castStringExc) {
			return getWord("C02100");// ����
		} else {
			return getWord("C02099");// ���Ȃ�

		}
	}

	/**
	 * @param customerType
	 * @return ���Ӑ�敪����
	 */
	public static String getName(CustomerType customerType) {

		if (customerType == null) {
			return "";
		}

		switch (customerType) {
			case NONE:
				return "C01279";// ���͕s��
			case CUSTOMER:
				return "C00401";// ���Ӑ�
			case VENDOR:
				return "C00203";// �d����
			case BOTH:
				return "C02122";// ���Ӑ恕�d����
			default:
				return "";
		}

	}
}