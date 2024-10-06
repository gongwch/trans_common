package jp.co.ais.trans2.master.excel;

import java.util.List;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.TExcel;
import jp.co.ais.trans2.common.excel.TExcelRow;
import jp.co.ais.trans2.common.excel.TExcelSheet;
import jp.co.ais.trans2.model.company.*;

/**
 * 会社間付替マスタエクセル
 * 
 * @author AIS
 */
public class InterCompanyTransferExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public InterCompanyTransferExcel(String lang) {
		super(lang);
	}

	/**
	 * 一覧をエクセルで返す
	 * 
	 * @param list
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] getExcel(Company company, List<InterCompanyTransfer> list) throws TException {

		try {
			createReport(company, list);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param list
	 */
	public void createReport(Company company, List<InterCompanyTransfer> list) {

		// シート追加
		// 会社間付替マスタ
		TExcelSheet sheet = addSheet(getWord("C02341"));

		AccountConfig ac = company.getAccountConfig();

		// カラム設定
		// 付替会社コード
		sheet.addColumn(getWord("C02050"), 6000);
		sheet.addColumn(getWord("C11150"), 8400);
		// 付替計上部門コード
		sheet.addColumn(getWord("C02051"), 6000);
		// 付替計上部門略称
		sheet.addColumn(getWord("C11151"), 8400);
		// 付替 ｺｰﾄﾞ
		sheet.addColumn(getWord("C00375") + ac.getItemName() + getWord("C00174"), 6000);
		// 付替 略称
		sheet.addColumn(getWord("C00375") + ac.getItemName() + getWord("C00548"), 8400);
		// 付替 コード
		sheet.addColumn(getWord("C00375") + ac.getSubItemName() + getWord("C00174"), 6000);
		// 付替 略称
		sheet.addColumn(getWord("C00375") + ac.getSubItemName() + getWord("C00548"), 8400);
		if (ac.isUseDetailItem()) {
			// 付替 コード
			sheet.addColumn(getWord("C00375") + ac.getDetailItemName() + getWord("C00174"), 6000);
			// 付替 略称
			sheet.addColumn(getWord("C00375") + ac.getDetailItemName() + getWord("C00548"), 8400);
		}
		// 取引先コード
		sheet.addColumn(getWord("C00786"), 6000);
		// 取引先略称
		sheet.addColumn(getWord("C00787"), 8400);

		// 明細描画
		for (InterCompanyTransfer bean : list) {
			TExcelRow newRow = sheet.addRow();
			newRow.addCell(bean.getTransferCompany().getCode());
			newRow.addCell(bean.getTransferCompany().getNames());
			newRow.addCell(bean.getDepartment().getCode());
			newRow.addCell(bean.getDepartment().getNames());
			newRow.addCell(bean.getItem().getCode());
			newRow.addCell(bean.getItem().getNames());
			newRow.addCell(bean.getItem().getSubItemCode());
			newRow.addCell(bean.getItem().getSubItemNames());
			if (ac.isUseDetailItem()) {
				newRow.addCell(bean.getItem().getDetailItemCode());
				newRow.addCell(bean.getItem().getDetailItemNames());
			}
			newRow.addCell(bean.getCustomer().getCode());
			newRow.addCell(bean.getCustomer().getNames());
		}

	}

}
