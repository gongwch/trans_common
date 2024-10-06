package jp.co.ais.trans2.model.currency;

import java.io.*;
import java.math.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.define.*;

/**
 * レート取込データ
 */
public class RateConverterImpl implements RateConverter {

	/** 初期化 */
	protected List<Rate> list = new ArrayList<Rate>();

	/** 取込日時 */
	protected Date impDate = null;

	/** ファイル名 */
	protected String fileName = null;

	/** Excel */
	protected TExcel excel;

	/** エラー */
	protected TErroneousRateDataException err = new TErroneousRateDataException();

	/**
	 * Rateデータ取得
	 * 
	 * @param file
	 * @param companyCode
	 * @return レート取込データ
	 * @throws TErroneousRateDataException
	 */
	public List<Rate> toRateData(File file, String companyCode) throws TErroneousRateDataException {

		try {

			// 取込ファイル名
			fileName = file.getName();

			// 取込日時
			impDate = Util.getCurrentDate();

			// 取込ファイル形式エラーチェック
			if (!ExtensionType.XLS.value.equals(ResourceUtil.getExtension(file))
				&& !ExtensionType.XLSX.value.equals(ResourceUtil.getExtension(file))) {

				// エラー
				err.setError(err.new ErrorContent(TErroneousRateDataException.RateError.FILE, null, 0));

				throw err;
			}

			// ファイルを読み取る

			excel = new TExcel(file);

			TExcelSheet sheet = excel.getSheet(0);

			// シートの内容をチェック
			excelCheck(sheet);

			for (int i = 2; i < sheet.getRowCount() + 1; i++) {
				Rate bean = new Rate();

				// 会社コード
				bean.setCompanyCode(companyCode);

				String kbn = sheet.getString(i - 1, 0);

				if (kbn.equals("M")) {
					bean.setRateType(RateType.COMPANY);
				} else if (kbn.equals("Y")) {
					bean.setRateType(RateType.SETTLEMENT);
				} else {
					bean.setRateType(null);
				}

				bean.setTermFrom(sheet.getDate(i - 1, 1));

				Currency cur = new Currency();
				cur.setCode(sheet.getString(i - 1, 2));

				bean.setCurrency(cur);
				bean.setCurrencyRate(DecimalUtil.toBigDecimal(sheet.getDecimal(i - 1, 3)));

				list.add(bean);

			}
		} catch (Exception e) {
			throw err;
		}

		return list;

	}

	/**
	 * シートの不正チェック
	 * 
	 * @param sheet
	 * @throws TErroneousRateDataException
	 */
	protected void excelCheck(TExcelSheet sheet) throws TErroneousRateDataException {

		err = new TErroneousRateDataException();

		for (int i = 2; i < sheet.getRowCount() + 1; i++) {

			// 区分の値チェック
			try {
				String kbn = sheet.getString(i - 1, 0);
				if (!kbn.equals("M") && !kbn.equals("Y")) {
					err = error(TErroneousRateDataException.RateError.VALUE,
						TErroneousRateDataException.RateError.DIVISION, i);
					throw err;
				}

				// 日付の値チェック
				sheet.getString(i - 1, 1);
				sheet.getDate(0, 1);

				if (sheet.getDate(i - 1, 1) == null) {
					err = error(TErroneousRateDataException.RateError.VALUE,
						TErroneousRateDataException.RateError.TERM_FROM, i);
					throw err;
				}

				// 通貨の値チェック
				if (Util.isNullOrEmpty(sheet.getString(i - 1, 2))) {
					err = error(TErroneousRateDataException.RateError.NULL, null, i);
					throw err;

				}

			} catch (Exception e) {
				throw err;
			}

			// レートの値チェック
			try {
				if (Util.isNullOrEmpty(sheet.getString(i - 1, 3)) || sheet.getDecimal(i - 1, 3) == BigDecimal.ZERO) {
					err = error(TErroneousRateDataException.RateError.VALUE,
						TErroneousRateDataException.RateError.RATE, i);
					throw err;
				}
			} catch (Exception e) {
				if (Util.isNullOrEmpty(err.getError())) {
					err = error(TErroneousRateDataException.RateError.NULL, null, i);
				}
				// 型が違った場合
				throw err;
			}

			// 項目の過不足チェック
			if (!Util.isNullOrEmpty(sheet.getString(i - 1, 4))) {
				err = error(TErroneousRateDataException.RateError.NULL, null, i);
				throw err;
			}

		}

		// 摘要開始日
		Date beginDate = null;

		// 通貨
		String code = "";

		// ファイル内に同一のデータが存在するかチェック
		for (int i = 2; i < sheet.getRowCount() + 1; i++) {

			if (i == 2) {
				beginDate = sheet.getDate(i - 1, 1);
				code = sheet.getString(i - 1, 2);
			}

			for (int j = 2; j < sheet.getRowCount() + 1; j++) {
				if (i != j) {
					if (beginDate.compareTo(sheet.getDate(j - 1, 1)) == 0 && code.equals(sheet.getString(j - 1, 2))) {
						err = error(TErroneousRateDataException.RateError.EXISTENCE_FILE, null, i);
						throw err;
					}

				}
			}

			if (i != sheet.getRowCount()) {
				beginDate = sheet.getDate(i, 1);
				code = sheet.getString(i, 2);
			}

		}

	}

	/**
	 * 表示するエラーを返す
	 * 
	 * @param errorType
	 * @param detaType
	 * @param rows
	 * @return err
	 */
	protected TErroneousRateDataException error(TErroneousRateDataException.RateError errorType,
		TErroneousRateDataException.RateError detaType, int rows) {
		err.setError(err.new ErrorContent(errorType, detaType, rows));
		return err;
	}

}