package jp.co.ais.trans2.master.excel;

import java.util.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;
import jp.co.ais.trans2.model.slip.*;

/**
 * 伝票種別マスタ エクセル
 */
public class SlipTypeExcel extends TExcel {

	/** インボイス制度チェックするかどうか true:表示する */
	protected boolean isInvoice = false;

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 * @param condition
	 */
	public SlipTypeExcel(String lang, SlipTypeSearchCondition condition) {
		super(lang);
		isInvoice = condition.isInvoiceFlg();
	}

	/**
	 * 伝票種別一覧をエクセルで返す
	 * 
	 * @param sliptypeList 取引先一覧
	 * @return getBinary
	 * @throws TException
	 */
	public byte[] getExcel(List<SlipType> sliptypeList) throws TException {

		try {
			createReport(sliptypeList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param sliptypeList
	 */
	public void createReport(List<SlipType> sliptypeList) {

		// シート追加
		TExcelSheet sheet = addSheet(getWord("C00912"));// 伝票種別マスタ

		// カラム設定
		sheet.addColumn(getWord("C00837"), 4200);// 伝票種別コード
		sheet.addColumn(getWord("C00980"), 4200);// システム区分
		sheet.addColumn(getWord("C00838"), 6300);// 伝票種別名称
		sheet.addColumn(getWord("C00839"), 6300);// 伝票種別略称
		sheet.addColumn(getWord("C02757"), 8400);// 帳票タイトル
		sheet.addColumn(getWord("C02047"), 6300);// データ区分
		sheet.addColumn(getWord("C01221"), 6300);// 他システム区分
		sheet.addColumn(getWord("C00392"), 8400);// 伝票番号採番フラグ
		sheet.addColumn(getWord("C11169"), 8400);// 入力単位
		sheet.addColumn(getWord("C00287"), 4200);// 消費税計算区分
		sheet.addColumn(getWord("C00299"), 8400);// 仕訳インターフェース区分
		sheet.addColumn(getWord("C11280"), 6200);// 振戻伝票種別コード
		sheet.addColumn(getWord("C11281"), 6200);// 振戻伝票種別名称

		if (isInvoice) {
			sheet.addColumn(getWord("C12199"), 4500);// インボイス制度チェック
		}

		// 明細描画
		for (SlipType slipType : sliptypeList) {

			TExcelRow newRow = sheet.addRow();

			newRow.addCell(slipType.getCode());
			newRow.addCell(slipType.getSystemDiv());
			newRow.addCell(slipType.getName());
			newRow.addCell(slipType.getNames());
			newRow.addCell(slipType.getNamek());
			newRow
				.addCell(getWord(DataDivision.getDataDivisionName(DataDivision.getDataDivision(slipType.getDataType()))));
			newRow.addCell(getWord(slipType.getAnotherSystemDivisionName()));
			newRow.addCell(getWord(slipType.getSlipIndexDivisionName()));
			newRow.addCell(getWord(AcceptUnit.getAcceptUnitName(slipType.isAcceptUnit())));
			newRow.addCell(getWord(TaxCalcType.getTaxCalcTypeName(TaxCalcType.getTaxCulKbn(slipType
				.isInnerConsumptionTaxCalculation()))));
			newRow.addCell(getWord(SlipState.getSlipStateName(slipType.getSlipState())));

			if (slipType.getReversingSlipType() != null) {
				newRow.addCell(slipType.getReversingSlipType().getCode());
				newRow.addCell(slipType.getReversingSlipType().getName());
			} else {
				newRow.addEmptyCell();
				newRow.addEmptyCell();
			}

			if (isInvoice) {
				newRow.addCell(slipType.isINV_SYS_FLG() ? getWord("C02100") : getWord("C02099"));
			}

		}

	}
}
