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
 * �Ȗڈꗗ�G�N�Z��
 */
public class ItemExcel extends TExcel {

	/** �q�C���x�v�Z�t���O�g�����ǂ��� */
	protected static boolean isUseVoyageCalcution = ServerConfig.isFlagOn("trans.MG0080.use.voyage.calculation");

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
	public ItemExcel(String lang, Company company) {
		super(lang);
		this.company = company;

	}

	/**
	 * �Ȗڈꗗ���G�N�Z���ŕԂ�
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
		TExcelSheet sheet = addSheet(getWord("C02342"));

		// �J�����ݒ�
		sheet.addColumn(getWord("C00572"), 3200);
		sheet.addColumn(getWord("C00700"), 10000);
		sheet.addColumn(getWord("C00730"), 8400);
		sheet.addColumn(getWord("C00731"), 8400);
		sheet.addColumn(getWord("C01148"), 3400);
		sheet.addColumn(getWord("C01007"), 4200);
		sheet.addColumn(getWord("C01226"), 4200);
		sheet.addColumn(getWord("C01314"), 3800);
		sheet.addColumn(getWord("C02066"), 5000);
		sheet.addColumn(getWord("C00573"), 3800);
		sheet.addColumn(getWord("C00968"), 5000);
		sheet.addColumn(getWord("C00959"), 5000);
		sheet.addColumn(getWord("C00960"), 5000);
		sheet.addColumn(getWord("C00962"), 5000);
		sheet.addColumn(getWord("C01134"), 5000);
		sheet.addColumn(getWord("C01217"), 5000);
		sheet.addColumn(getWord("C02078"), 5000);
		sheet.addColumn(getWord("C00453"), 5000);
		sheet.addColumn(getWord("C02067"), 5500);
		sheet.addColumn(getWord("C02068"), 5500);
		sheet.addColumn(getWord("C02321"), 5500);
		sheet.addColumn(getWord("C01049"), 5500);
		sheet.addColumn(getWord("C01083"), 5500);
		sheet.addColumn(getWord("C02071"), 5500);
		sheet.addColumn(getWord("C02072"), 5500);
		sheet.addColumn(getWord("C02073"), 5500);
		sheet.addColumn(getWord("C02074"), 5500);
		sheet.addColumn(getWord("C01223"), 5000);
		sheet.addColumn(getWord("C01120"), 5000);

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

		sheet.addColumn(getWord("C01282"), 5000);
		sheet.addColumn(getWord("C01088"), 5000);
		if (isUseVoyageCalcution) {
			sheet.addColumn(getWord("C11602"), 5000); // �q�C���x�v�Z�t���O
		}
		if (isUseOccurDate) {
			sheet.addColumn(getWord("C11619"), 5000); // �������t���O
		}
		sheet.addColumn(getWord("C00055"), 4200);
		sheet.addColumn(getWord("C00261"), 4200);

		// ���ו`��
		for (Item item : itemList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(item.getCode());
			newRow.addCell(item.getName());
			newRow.addCell(item.getNames());
			newRow.addCell(item.getNamek());
			newRow.addCell(getWord(ItemSumType.getName(item.getItemSumType())), SwingConstants.CENTER);
			newRow.addCell(getWord(ItemType.getName(item.getItemType())), SwingConstants.CENTER);
			newRow.addCell(getWord(Dc.getName(item.getDc())), SwingConstants.CENTER);

			if (item.getItemSumType() == ItemSumType.INPUT) {
				newRow.addCell(item.hasSubItem() ? getWord("C00006") : getWord("C00412"), SwingConstants.CENTER);
			} else {
				newRow.addCell("");
			}

			newRow.addCell(item.getFixedDepartmentCode());
			newRow.addCell(item.getConsumptionTax().getCode());

			if (item.getItemSumType() == ItemSumType.INPUT) {

				newRow.addCell(getWord(GLType.getName(item.getGlType())), SwingConstants.CENTER);
				newRow.addCell(getWord(APType.getName(item.getApType())), SwingConstants.CENTER);
				newRow.addCell(getWord(ARType.getName(item.getArType())), SwingConstants.CENTER);
				newRow.addCell(getWord(BGType.getName(item.getBgType())), SwingConstants.CENTER);
				newRow.addCell(getWord(CustomerType.getName(item.getClientType())), SwingConstants.CENTER);
				newRow.addCell(getWord(getDivisionName(item.isDoesOffsetItem())), SwingConstants.CENTER);
				newRow.addCell(getWord(getDivisionName(item.isDoesBsOffset())), SwingConstants.CENTER);
				newRow.addCell(getWord(EvaluationMethod.getName(item.getEvaluationMethod())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUseInputCashFlowSlip())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUseOutputCashFlowSlip())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUseTransferSlip())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUseExpenseSettlementSlip())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUsePaymentAppropriateSlip())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUseReceivableAppropriateSlip())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUseReceivableErasingSlip())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUseAssetsEntrySlip())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUsePaymentRequestSlip())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUseForeignCurrency())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo1(item.isUseEmployee())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo1(item.isUseManagement1())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo1(item.isUseManagement2())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo1(item.isUseManagement3())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo1(item.isUseManagement4())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo1(item.isUseManagement5())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo1(item.isUseManagement6())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUseNonAccount1())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUseNonAccount2())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUseNonAccount3())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUseSalesTaxation())), SwingConstants.CENTER);
				newRow.addCell(getWord(getBoo(item.isUsePurchaseTaxation())), SwingConstants.CENTER);

				// �q�C���x�v�Z�t���O
				if (isUseVoyageCalcution) {
					newRow.addCell(getWord(getDivisionName(item.isUseVoyageCalculation())), SwingConstants.CENTER);
				}
				// �������t���O
				if (isUseOccurDate) {
					newRow.addCell(getWord(getBoo(item.isUseOccurDate())), SwingConstants.CENTER);
				}

			} else {

				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");
				newRow.addCell("");

				// �q�C���x�v�Z�t���O
				if (isUseVoyageCalcution) {
					newRow.addCell("");
				}
				// �������t���O
				if (isUseOccurDate) {
					newRow.addCell("");
				}

			}

			newRow.addCell(DateUtil.toYMDString(item.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(item.getDateTo()), SwingConstants.CENTER);

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
			return "C01276"; // ���͉�
		} else {
			return "C01279"; // ���͕s��
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
			return "C02371"; // ���͕K�{
		} else {
			return "C01279"; // ���͕s��
		}
	}

	/**
	 * ���̂�Ԃ�
	 * 
	 * @param bool
	 * @return String
	 */
	public static String getDivisionName(boolean bool) {

		if (bool) {
			return "C02100"; // ����
		} else {
			return "C02099"; // ���Ȃ�
		}
	}
}