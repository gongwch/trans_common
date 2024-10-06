package jp.co.ais.trans2.master.excel;

import java.util.*;

import javax.swing.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.vessel.*;

/**
 * 内航外航区分エクセル
 * 
 * @author AIS
 */
public class VesselCOExcel extends TExcel {

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 */
	public VesselCOExcel(String lang) {
		super(lang);
	}

	/**
	 * 帳票を返す
	 * 
	 * @param list
	 * @return 帳票
	 * @throws TException
	 */
	public byte[] getExcel(List<VesselCO> list) throws TException {

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
	public void createReport(List<VesselCO> list) {

		// シート追加
		// 内航外航区分マスタ
		TExcelSheet sheet = addSheet(getWord("C11985"));

		// カラム設定
		// 船コード
		sheet.addColumn(getWord("C11758"), 5000);
		// 船略称
		sheet.addColumn(getWord("C11759"), 8000);
		// 内航/外航区分
		sheet.addColumn(getWord("C11986"), 5000);

		// 明細描画
		for (VesselCO bean : list) {
			TExcelRow newRow = sheet.addRow();
			if (bean.getCOKbn().equals("1")) {
				newRow.addCell(bean.getVesselCode());
				newRow.addCell(bean.getVesselNames());
				newRow.addCell(getWord("COP1418"), SwingConstants.CENTER);

			} else if (bean.getCOKbn().equals("2")) {
				newRow.addCell(bean.getVesselCode());
				newRow.addCell(bean.getVesselNames());
				newRow.addCell(getWord("COP1419"), SwingConstants.CENTER);

			}

		}

	}
}
