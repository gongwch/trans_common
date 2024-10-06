package jp.co.ais.trans2.model.port;

import java.io.*;
import java.math.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.common.util.DateUtil;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.model.*;
import jp.co.ais.trans2.model.port.TErroneousSummerTimeException.ErrorContent;

/**
 * サマータイムマスタ変換実装
 */
public class SummerTimeConverterImpl extends TModel implements SummerTimeConvert {

	/** エラーリスト */
	public TErroneousSummerTimeException errorContents = null;

	/** 受取日時 */
	public Date takenInDate = null;

	/** Excel */
	public TExcel excel;

	/** 明細の開始行インデックス(Excelの行１=0,…) */
	public static int START_ROW_INDEX = 1;

	/**
	 * 変換処理
	 * 
	 * @param file
	 * @param condition
	 * @return list
	 * @throws TErroneousSummerTimeException
	 * @throws TException
	 */
	public List<OP_SMR_TIME_MST> convert(File file, PortSearchCondition condition)
		throws TErroneousSummerTimeException, TException {

		errorContents = new TErroneousSummerTimeException();
		excel = new TExcel(file);
		TExcelSheet sheet = excel.getSheet(0);

		// Check excel file was empty
		int count = sheet.getRowCount();
		if (count <= 1) {
			return null;
		}

		// 結果リスト
		List<OP_SMR_TIME_MST> list = new ArrayList<OP_SMR_TIME_MST>();

		// mapRegion use to check duplicate region in excel file
		Map<String, Integer> mapRegion = new HashMap<String, Integer>();

		for (int row = START_ROW_INDEX; row < count; row++) {

			boolean hasError = false;

			// REGION CODE
			int colNo = 1;
			String regionCode = sheet.getString(row, colNo);

			if (Util.isNullOrEmpty(regionCode)) {
				// Not input region
				String error = getMessage("I00037", "COP1285"); // REGION CODE
				addError(row, colNo, error);
				hasError = true;

			} else if (mapRegion.containsKey(regionCode)) {
				// I00471">{0}の{1}行目の{2}が重複しています。
				// COP1285">REGION CODE
				String error = getMessage("I00471", mapRegion.get(regionCode) + 1, row + 1, "COP1285");
				addError(row, colNo, error);
				hasError = true;
			}

			if (!mapRegion.containsKey(regionCode)) {
				mapRegion.put(regionCode, row);
			}

			// TIME DIFFERENCE
			colNo = 3;

			String timeDiff = sheet.getString(row, colNo);
			BigDecimal tz = null;
			boolean hasNumberTz = false;

			{
				Row row_ = sheet.sheet.getRow(row);
				Cell cell = row_.getCell(colNo);

				if (cell != null && cell.getCellType() == Cell.CELL_TYPE_NUMERIC) {
					tz = BigDecimal.valueOf(cell.getNumericCellValue()).multiply(new BigDecimal(24));
					hasNumberTz = true;

					// 小数点以下チェック
					if (tz != null) {
						tz = DecimalUtil.roundNum(tz, 5);

						String str = NumberFormatUtil.formatNumber(tz, 2);
						String[] arr = str.split("\\.");

						if (arr.length > 1) {
							String hh = arr[0];
							String mm = arr[1];

							BigDecimal minutes = null;

							if (mm.equals("00")) {
								// 0.0
								minutes = BigDecimal.ZERO;

							} else if (mm.equals("25")) {
								// 0.25
								minutes = new BigDecimal("0.25");

							} else if (mm.equals("50")) {
								// 0.5
								minutes = new BigDecimal("0.5");

							} else if (mm.equals("75")) {
								// 0.75
								minutes = new BigDecimal("0.75");

							} else {
								// I00099">{0}が不正です。
								String error = getMessage("I00099", "COP1287"); // TIME DIFFERENCE
								addError(row, colNo, error);
								hasError = true;

							}

							// 正しい時差
							if (minutes != null) {

								BigDecimal hours = new BigDecimal(hh);
								if (hours.signum() == -1) {
									tz = hours.subtract(minutes);
								} else {
									tz = hours.add(minutes);
								}
							}
						}
					}
				}
			}

			if (!hasNumberTz) {
				if (timeDiff != null) {
					timeDiff = timeDiff.replaceAll("[ ,\\.]", "");
				}

				// TIME DIFFERENCE
				if (Util.isNullOrEmpty(timeDiff)) {
					// 処理なし

					// } else if (timeDiff.contains("")) {
					// String error = getMessage("I00259", "COP1287", timeDiff); // TIME DIFFERENCE
					// addError(row, colNo, error);
					// hasError = true;

				} else {

					String[] arr = timeDiff.split(":");
					if (arr.length < 2) {
						String error = getMessage("I00259", "COP1287", timeDiff); // TIME DIFFERENCE
						addError(row, colNo, error);
						hasError = true;

					} else {

						String hh = arr[0];
						String mm = arr[1];

						if (!Util.isNumber(hh) || !Util.isNumber(mm)) {
							String error = getMessage("I00259", "COP1287", timeDiff); // TIME DIFFERENCE
							addError(row, colNo, error);
							hasError = true;

						} else {

							BigDecimal minutes = null;
							int m = Integer.parseInt(mm);

							if (m == 0) {
								// 0.0
								minutes = BigDecimal.ZERO;

							} else if (m == 15) {
								// 0.25
								minutes = new BigDecimal("0.25");

							} else if (m == 30) {
								// 0.5
								minutes = new BigDecimal("0.5");

							} else if (m == 45) {
								// 0.75
								minutes = new BigDecimal("0.75");

							} else {
								// I00268">{0}が不正です。({1})
								String error = getMessage("I00268", "COP1287", timeDiff); // TIME DIFFERENCE
								addError(row, colNo, error);
								hasError = true;

							}

							// 正しい時差
							if (minutes != null) {

								BigDecimal hours = new BigDecimal(hh);
								if (hours.signum() == -1) {
									tz = hours.subtract(minutes);
								} else {
									tz = hours.add(minutes);
								}
							}
						}
					}
				}
			}

			Date from = null;
			Date to = null;

			int fromColNo = 4;
			int toColNo = 5;

			boolean correctFrom = true;
			boolean correctTo = true;

			// FROM DATE(LCT)
			colNo = fromColNo;
			Date chkFrom = sheet.getDate(row, colNo);
			String strFrom = null;
			if (chkFrom == null) {
				strFrom = sheet.getString(row, colNo);

				if (Util.isNullOrEmpty(strFrom)) {
					// 処理なし

				} else if (DateUtil.toDateNE(strFrom) == null) {
					String error = getMessage("I00260", "COP1288", strFrom);
					addError(row, colNo, error);
					hasError = true;
					correctFrom = false;

				} else {
					from = DateUtil.toDateNE(strFrom);
				}
			} else {
				from = chkFrom;
			}

			// TO DATE(LCT)
			colNo = toColNo;
			Date chkTo = sheet.getDate(row, colNo);
			String strTo = null;
			if (chkTo == null) {
				strTo = sheet.getString(row, colNo);

				if (Util.isNullOrEmpty(strTo)) {
					// 処理なし

				} else if (DateUtil.toDateNE(strTo) == null) {
					String error = getMessage("I00260", "COP1288", strTo);
					addError(row, colNo, error);
					hasError = true;
					correctTo = false;

				} else {
					to = DateUtil.toDateNE(strTo);
				}
			} else {
				to = chkTo;
			}

			if (from != null && to != null) {
				// FROMとTOも正しい書式

				if (!Util.isSmallerThenByYMDHMS(from, to)) {
					String error = getMessage("I00090", "COP1288", "COP1289");
					addError(row, fromColNo, error);
					hasError = true;
				}

			} else if (correctFrom && correctTo) {
				// エラー発生してない場合のみチェック

				if (from == null && to != null) {
					// FROMが必須
					String error = getMessage("I00037", "COP1288");
					addError(row, fromColNo, error);
					hasError = true;

				} else if (from != null && to == null) {
					// TOが必須
					String error = getMessage("I00037", "COP1289");
					addError(row, toColNo, error);
					hasError = true;
				}
			}

			// REMARKS
			colNo = 6;
			String remarks = sheet.getString(row, colNo);

			if (hasError) {
				continue;
			}

			// 対象データ判定
			if (from != null && to != null && !DecimalUtil.isNullOrZero(tz)) {
				// REMARKS以外、FROM/TO/TZ設定ありの場合、取込対象

				// 変換
				OP_SMR_TIME_MST bean = createOP_SMR_TIME_MST();

				bean.setKAI_CODE(condition.getCompanyCode());
				bean.setYEAR(condition.getYEAR());
				bean.setREGION_CODE(regionCode);
				bean.setTIME_DIFFERENCE(tz);
				bean.setFROM_DATE(from);
				bean.setTO_DATE(to);
				bean.setREMARKS(remarks);

				// 結果リストへまとめる
				list.add(bean);
			}

		}

		if (errorContents.getErrorList() != null && !errorContents.getErrorList().isEmpty()) {
			throw errorContents;
		}

		// 対象となるデータが見つかりません。
		if (list.isEmpty()) {
			return null;
		}

		return list;

	}

	/**
	 * @return エンティティ
	 */
	protected OP_SMR_TIME_MST createOP_SMR_TIME_MST() {
		return new OP_SMR_TIME_MST();
	}

	/**
	 * エラー記録
	 * 
	 * @param row
	 * @param col
	 * @param msg
	 */
	protected void addError(int row, int col, String msg) {
		String cellID = TExcelSheet.toExcelNo(row, col);
		addError(null, row + 1, cellID, msg);
	}

	/**
	 * エラー記録
	 * 
	 * @param bean
	 * @param row
	 * @param cellID
	 * @param msg
	 */
	protected void addError(OP_SMR_TIME_MST bean, int row, String cellID, String msg) {
		ErrorContent ec = errorContents.new ErrorContent(bean, takenInDate, msg);
		ec.setRowNo(row);
		ec.setCellID(cellID);
		errorContents.addErrorContent(ec);
	}

}
