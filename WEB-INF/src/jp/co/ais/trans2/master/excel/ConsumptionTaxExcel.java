package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.tax.*;

/**
 * 消費税一覧エクセル
 */
public class ConsumptionTaxExcel extends TExcel {

	/** Invoice制度 true:使用する */
	protected boolean isInvoice = false;

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 * @param condition
	 */
	public ConsumptionTaxExcel(String lang, ConsumptionTaxSearchCondition condition) {
		super(lang);
		isInvoice = condition.isInvoiceFlg();
	}

	/**
	 * 消費税マスタ一覧をエクセルで返す
	 * 
	 * @param list 消費税リスト
	 * @return 消費税一覧エクセル
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
	 * エクセルを出力する
	 * 
	 * @param list
	 */
	public void createReport(List<ConsumptionTax> list) {
		// シート追加 マスタ
		TExcelSheet sheet = addSheet(getWord("C02324"));

		// カラム設定
		// 消費税コード
		sheet.addColumn(getWord("C00573"), 4000);
		// 消費税名称
		sheet.addColumn(getWord("C00774"), 10000);
		// 消費税略称
		sheet.addColumn(getWord("C00775"), 10000);
		// 消費税検索名称
		sheet.addColumn(getWord("C00828"), 10000);
		// 売上仕入区分
		sheet.addColumn(getWord("C01283"), 4000);
		// 消費税率
		sheet.addColumn(getWord("C00777"), 4000);
		// 消費税計算書出力順序
		sheet.addColumn(getWord("C02045"), 5000);

		if (isInvoice) {
			// 非適格請求書発行事業者
			sheet.addColumn(getWord("C12197"), 6000);
			// 経過措置控除可能率
			sheet.addColumn(getWord("C12228"), 4500);
		}

		// 開始年月日
		sheet.addColumn(getWord("C00055"), 4200);
		// 終了年月日
		sheet.addColumn(getWord("C00261"), 4200);

		// 明細描画
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
				// 非適格請求書発行事業者
				newRow.addCell(ct.isNO_INV_REG_FLG() ? getWord("C12198") : "", SwingConstants.CENTER);
				// 経過措置控除可能率
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
