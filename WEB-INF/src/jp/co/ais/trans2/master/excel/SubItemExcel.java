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
 * 補助科目一覧エクセル
 */
public class SubItemExcel extends TExcel {

	/** 発生日フラグ使うかどうか */
	protected static boolean isUseOccurDate = ServerConfig.isFlagOn("trans.MG0080.use.occurdate");

	/** 補助 固定部門 表示可否 */
	protected static boolean isUseKoteiDep = ServerConfig.isFlagOn("trans.use.sub.item.kotei.dep");

	/** 会社情報 */
	protected Company company = null;

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 * @param company
	 */
	public SubItemExcel(String lang, Company company) {
		super(lang);
		this.company = company;
	}

	/**
	 * 補助科目一覧をエクセルで返す
	 * 
	 * @param ItemList 科目一覧
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
	 * エクセルを出力する
	 * 
	 * @param itemList
	 */
	public void createReport(List<Item> itemList) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C02353"));

		// カラム設定
		sheet.addColumn(getWord("C00572"), 3500);
		sheet.addColumn(getWord("C00700"), 10000);
		sheet.addColumn(getWord("C00890"), 3500);
		sheet.addColumn(getWord("C00934"), 8400);
		sheet.addColumn(getWord("C00933"), 6600);
		sheet.addColumn(getWord("C00935"), 6400);
		sheet.addColumn(getWord("C01264"), 4200);
		if (isUseKoteiDep) {
			sheet.addColumn(getWord("C02066"), 5000);
		}
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
			sheet.addColumn(getWord("C11619"), 5000); // 発生日フラグ
		}
		sheet.addColumn(getWord("C00055"), 4200);
		sheet.addColumn(getWord("C00261"), 4200);

		// 明細描画
		for (Item bean : itemList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getSubItem().getCode());
			newRow.addCell(bean.getSubItem().getName());
			newRow.addCell(bean.getSubItem().getNames());
			newRow.addCell(bean.getSubItem().getNamek());
			newRow.addCell(getUtiName(bean.getSubItem().hasDetailItem()), SwingConstants.CENTER);
			if (isUseKoteiDep) {
				newRow.addCell(bean.getSubItem().getFixedDepartmentCode());
			}
			newRow.addCell(bean.getSubItem().getConsumptionTax().getCode());
			newRow.addCell(getBoo(bean.getSubItem().isUseInputCashFlowSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseOutputCashFlowSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseTransferSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseExpenseSettlementSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUsePaymentAppropriateSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseReceivableAppropriateSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseReceivableAppropriateSlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseAssetsEntrySlip()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUsePaymentRequestSlip()), SwingConstants.CENTER);
			newRow.addCell(getWord(getName(bean.getSubItem().getClientType())), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseEmployee()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseManagement1()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseManagement2()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseManagement3()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseManagement4()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseManagement5()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseManagement6()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseNonAccount1()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseNonAccount2()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseNonAccount3()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseSalesTaxation()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUsePurchaseTaxation()), SwingConstants.CENTER);
			newRow.addCell(getBoo(bean.getSubItem().isUseForeignCurrency()), SwingConstants.CENTER);
			newRow.addCell(getBoo2(bean.getSubItem().isDoesCurrencyEvaluate()), SwingConstants.CENTER);
			if (isUseOccurDate) {
				newRow.addCell(getWord(getBoo(bean.getSubItem().isUseOccurDate())), SwingConstants.CENTER);
			}
			newRow.addCell(DateUtil.toYMDString(bean.getSubItem().getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(bean.getSubItem().getDateTo()), SwingConstants.CENTER);

		}

	}

	/**
	 * BOOLEANをstringでを返す
	 * 
	 * @param castString
	 * @return string
	 */
	public String getBoo(boolean castString) {

		if (castString) {
			return getWord("C01276");// 入力可
		} else {
			return getWord("C01279");// 入力不可

		}
	}

	/**
	 * BOOLEANをStringでを返す。管理科目表示用。
	 * 
	 * @param castStringKnr
	 * @return string
	 */
	public String getBoo1(boolean castStringKnr) {

		if (castStringKnr) {
			return getWord("C02371");// 入力必須
		} else {
			return getWord("C01279");// 入力不可

		}
	}

	/**
	 * BOOLEANをStringでを返す。評価替表示用。
	 * 
	 * @param castStringExc
	 * @return string
	 */
	public String getBoo2(boolean castStringExc) {

		if (castStringExc) {
			return getWord("C02100");// する
		} else {
			return getWord("C02099");// しない

		}
	}

	/**
	 * @param hasDetailItem
	 * @return 内訳
	 */
	public String getUtiName(boolean hasDetailItem) {

		if (hasDetailItem) {
			return getWord("C02156");// 有
		} else {
			return getWord("C02155");// 無
		}
	}

	/**
	 * @param customerType
	 * @return 得意先区分名称
	 */
	public static String getName(CustomerType customerType) {

		if (customerType == null) {
			return "";
		}

		switch (customerType) {
			case NONE:
				return "C01279";// 入力不可
			case CUSTOMER:
				return "C00401";// 得意先
			case VENDOR:
				return "C00203";// 仕入先
			case BOTH:
				return "C02122";// 得意先＆仕入先
			default:
				return "";
		}

	}
}