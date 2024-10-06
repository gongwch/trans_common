package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.payment.*;

/**
 * 支払方法エクセル
 * 
 * @author AIS
 */
public class PaymentMethodExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public PaymentMethodExcel(String lang) {
		super(lang);
	}

	/**
	 * 帳票を返す
	 * 
	 * @param list
	 * @return 帳票
	 * @throws TException
	 */
	public byte[] getExcel(List<PaymentMethod> list) throws TException {

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
	public void createReport(List<PaymentMethod> list) {

		TExcelSheet sheet = addSheet(getWord("C02350")); // 支払方法マスタ

		// 支払方法コード
		sheet.addColumn(getWord("C00864"), 4000);
		// 支払方法名称
		sheet.addColumn(getWord("C00865"), 8000);
		// 支払方法件検索名称
		sheet.addColumn(getWord("C00866"), 8000);
		// 科目
		sheet.addColumn(getWord("C00077"), 4000);
		// 補助
		sheet.addColumn(getWord("C00488"), 4000);
		// 内訳
		sheet.addColumn(getWord("C00025"), 4000);
		// 計上部門コード
		sheet.addColumn(getWord("C00571"), 4000);
		// 支払対象区分
		sheet.addColumn(getWord("C01096"), 5000);
		// 支払内部コード
		sheet.addColumn(getWord("C01097"), 5000);
		// 開始年月日
		sheet.addColumn(getWord("C00055"), 5000);
		// 終了年月日
		sheet.addColumn(getWord("C00261"), 5000);
		// フォーマット

		// 明細描画
		for (PaymentMethod bean : list) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(bean.getCode());
			newRow.addCell(bean.getName());
			newRow.addCell(bean.getNamek());

			newRow.addCell(bean.getItemCode());
			newRow.addCell(bean.getSubItemCode());
			newRow.addCell(bean.getDetailItemCode());
			newRow.addCell(bean.getDepartmentCode());

			newRow.addCell(bean.isUseEmployeePayment() ? getWord("C01119") : getWord("C01124"), SwingConstants.CENTER);
			newRow.addCell(getWord(PaymentKind.getPaymentKindName(bean.getPaymentKind())), SwingConstants.CENTER);

			newRow.addCell(DateUtil.toYMDString(bean.getDateFrom()), SwingConstants.CENTER);
			newRow.addCell(DateUtil.toYMDString(bean.getDateTo()), SwingConstants.CENTER);

		}

	}
}
