package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * âsûÀêGNZ
 */
public class BankAccountExcel extends TExcel {

	/**
	 * RXgN^.
	 * 
	 * @param lang ¾êR[h
	 */
	public BankAccountExcel(String lang) {
		super(lang);
	}

	/**
	 * âsûÀêðGNZÅÔ·
	 * 
	 * @param bankAccountList âsûÀê
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<BankAccount> bankAccountList) throws TException {

		try {
			createReport(bankAccountList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * GNZðoÍ·é
	 * 
	 * @param bankAccountList
	 */
	public void createReport(List<BankAccount> bankAccountList) {

		// V[gÇÁ
		// âsûÀ}X^
		TExcelSheet sheet = addSheet(getWord("C02322"));

		// JÝè
		// âsûÀR[h
		sheet.addColumn(getWord("C01879"), 4200);
		// âsûÀ¼Ì
		sheet.addColumn(getWord("C02145"), 8400);
		// ÊÝR[h
		sheet.addColumn(getWord("C00665"), 3000);
		// âsR[h
		sheet.addColumn(getWord("C00779"), 4200);
		// âs¼Ì
		sheet.addColumn(getWord("C00781"), 8400);
		// âs¼Ìipj
		sheet.addColumn(getWord("C11891"), 8400);
		// xXR[h
		sheet.addColumn(getWord("C02055"), 4200);
		// xX¼Ì
		sheet.addColumn(getWord("C02060"), 8400);
		// xX¼Ìipj
		sheet.addColumn(getWord("C11892"), 8400);
		// UËlR[h
		sheet.addColumn(getWord("C00858"), 4800);
		// UËl¼i¶Åj"
		sheet.addColumn(getWord("C00859"), 8400);
		// UËl¼i¿j
		sheet.addColumn(getWord("C00860"), 8400);
		// UËl¼ipj
		sheet.addColumn(getWord("C00861"), 8400);
		// aàíÚ
		sheet.addColumn(getWord("C01326"), 2500);
		// ûÀÔ
		sheet.addColumn(getWord("C00794"), 4200);
		// ÐõFBæª
		sheet.addColumn(getWord("C01117"), 4000);
		// ÐOFBæª
		sheet.addColumn(getWord("C01122"), 4000);
		// vãåR[h
		sheet.addColumn(getWord("C00571"), 4200);
		// vãåªÌ
		sheet.addColumn(getWord("C00863") + getWord("C00548"), 8400);
		// ÈÚR[h
		sheet.addColumn(getWord("C00572"), 4200);
		// ÈÚªÌ
		sheet.addColumn(getWord("C00730"), 8400);
		// âÈÚR[h
		sheet.addColumn(getWord("C00602"), 4200);
		// âÈÚªÌ
		sheet.addColumn(getWord("C00739"), 8400);
		// àóÈÚR[h
		sheet.addColumn(getWord("C00603"), 4200);
		// àóÈÚªÌ
		sheet.addColumn(getWord("C01594"), 8400);
		// SWIFTR[h
		sheet.addColumn(getWord("C10414"), 4200);
		// BANK COUNTRY
		sheet.addColumn(getWord("C11425"), 4200);
		// Z1
		sheet.addColumn(getWord("C00687"), 8400);
		// Z1(p)
		sheet.addColumn(getWord("C11893"), 8400);
		// Z2
		sheet.addColumn(getWord("C00688"), 8400);
		// Z2(p)
		sheet.addColumn(getWord("C11894"), 8400);
		// JnNú
		sheet.addColumn(getWord("C00055"), 4200);
		// I¹Nú
		sheet.addColumn(getWord("C00261"), 4200);

		// ¾×`æ
		for (BankAccount bankAccoount : bankAccountList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bankAccoount.getCode());
			newRow.addCell(bankAccoount.getName());
			newRow.addCell(bankAccoount.getCurrencyCode(), SwingConstants.CENTER);
			newRow.addCell(bankAccoount.getBankCode());
			newRow.addCell(bankAccoount.getBankName());
			newRow.addCell(bankAccoount.getBankNameE());
			newRow.addCell(bankAccoount.getBranchCode());
			newRow.addCell(bankAccoount.getBranchName());
			newRow.addCell(bankAccoount.getBranchNameE());
			newRow.addCell(bankAccoount.getClientCode());
			newRow.addCell(bankAccoount.getClientName());
			newRow.addCell(bankAccoount.getClientNameJ());
			newRow.addCell(bankAccoount.getClientNameE());
			newRow.addCell(getWord(DepositKind.getDepositKindName(bankAccoount.getDepositKind())),
				SwingConstants.CENTER);
			newRow.addCell(bankAccoount.getAccountNo());
			newRow.addCell(bankAccoount.isUseEmployeePayment() ? getWord("C02149") : getWord("C02148"),
				SwingConstants.CENTER);
			newRow
				.addCell(bankAccoount.isUseExPayment() ? getWord("C02151") : getWord("C02150"), SwingConstants.CENTER);
			newRow.addCell(bankAccoount.getDepartmentCode());
			newRow.addCell(bankAccoount.getDepartmentNames());
			newRow.addCell(bankAccoount.getItemCode());
			newRow.addCell(bankAccoount.getItemNames());
			newRow.addCell(bankAccoount.getSubItemCode());
			newRow.addCell(bankAccoount.getSubItemNames());
			newRow.addCell(bankAccoount.getDetailItemCode());
			newRow.addCell(bankAccoount.getDetailItemNames());
			newRow.addCell(bankAccoount.getSwift());
			newRow.addCell(bankAccoount.getBankCountry()); // Bank Country
			newRow.addCell(bankAccoount.getBnkAdr1());
			newRow.addCell(bankAccoount.getBnkAdr1E());
			newRow.addCell(bankAccoount.getBnkAdr2());
			newRow.addCell(bankAccoount.getBnkAdr2E());
			newRow.addCell(DateUtil.toYMDString(bankAccoount.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(bankAccoount.getDateTo()), SwingConstants.CENTER);
		}

	}
}
