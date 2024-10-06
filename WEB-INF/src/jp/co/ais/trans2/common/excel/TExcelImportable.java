package jp.co.ais.trans2.common.excel;

import java.io.*;
import java.math.*;
import java.util.*;

import org.apache.poi.ss.usermodel.*;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans.common.util.DateUtil;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.model.*;

/**
 * 取込可能エクセル生成クラス
 * 
 * @param <EntityType> 取込時に生成するエンティティの型
 * @param <ColumnType> 出力・取込に利用するエクセル項目定義
 */
public abstract class TExcelImportable<EntityType extends TransferBase, ColumnType extends ExcelImportableColumn>
	extends TExcel {

	/** 編集中シート */
	protected TExcelSheet sheet;

	/** 取込時 エラー一覧 */
	protected List<Message> errorList;

	/**
	 * コンストラクタ.
	 * 
	 * @param lang
	 */
	public TExcelImportable(String lang) {
		super(lang);
		errorList = new ArrayList();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param file
	 * @throws TException
	 */
	public TExcelImportable(File file) throws TException {
		super(file);
		errorList = new ArrayList();
	}

	/**
	 * コンストラクタ.
	 * 
	 * @param lang 言語コード
	 * @param type
	 */
	public TExcelImportable(String lang, ExcelType type) {
		super(lang, type);
		errorList = new ArrayList();
	}

	/**
	 * エラーリストを取得する
	 * 
	 * @return エラーリスト
	 */
	public List<Message> getErrorList() {
		return errorList;
	}

	/**
	 * エクセルを出力する
	 * 
	 * @param entityList
	 * @return エクセルバイナリ
	 * @throws TException
	 */
	public byte[] getExcel(List<EntityType> entityList) throws TException {
		try {
			createReport(entityList);
			return getBinary();
		} catch (Exception e) {
			throw new TException(e);
		}

	}

	/**
	 * エクセルを作成する
	 * 
	 * @param entityList
	 */
	protected void createReport(List<EntityType> entityList) {
		// シート追加
		sheet = addSheet(getSheetName());

		// 列定義を初期化
		initHeaderColumn();

		// 明細を記述
		drawDetail(entityList);

	}

	/**
	 * 出力時のシート名称を取得
	 * 
	 * @return シート名称
	 */
	protected abstract String getSheetName();

	/**
	 * 列定義を初期化する
	 */
	protected void initHeaderColumn() {
		for (ColumnType col : getAllColumns()) {
			if (isOutput(col)) {
				sheet.addColumn(getColumnName(col), col.getWidth());
			}
		}
	}

	/**
	 * 明細を記載する
	 * 
	 * @param entityList
	 */
	protected void drawDetail(List<EntityType> entityList) {
		for (EntityType bean : entityList) {
			TExcelRow newRow = sheet.addRow();
			for (ColumnType col : getAllColumns()) {
				if (!isOutput(col)) {
					continue;
				}
				addRowCellValue(col, bean, newRow);
			}
		}
	}

	/**
	 * 行のセルにEntityの値を追加
	 * 
	 * @param col
	 * @param bean
	 * @param newRow
	 */
	protected abstract void addRowCellValue(ColumnType col, EntityType bean, TExcelRow newRow);

	/**
	 * 定義されたExcelカラムをすべて返却
	 * 
	 * @return 定義されたカラム
	 */
	protected abstract ColumnType[] getAllColumns();

	/**
	 * 伝票エクセルを読み込み仕訳明細行へと変換
	 * 
	 * @param file
	 * @return 仕訳明細行リスト
	 * @throws TException
	 */
	public List<EntityType> convertToEntityList(File file) throws TException {
		try {
			// excelファイルを読み取る
			TExcel excel = new TExcel(file);
			// シート存在チェック
			if (!isExistsSheet(excel)) {
				// 取込対象外のファイルです。システムから出力したファイルを利用してください。
				throw new TWarningException(getMessage("I00775"));
			}
			sheet = excel.getSheet(0);
			// 行存在チェック
			if (!isExistsRow()) {
				// ファイルに有効な行がありません。
				throw new TWarningException(getMessage("I00296"));
			}
			// ヘッダチェック(カラム変更がないかテンプレートの整合性をとる)
			if (!isRightTemplate()) {
				// 取込対象外のファイルです。システムから出力したファイルを利用してください。
				throw new TWarningException(getMessage("I00775"));
			}

			// 値マッピング
			List<EntityType> entityList = new ArrayList();
			for (int row = 1; row < sheet.getRowCount(); row++) {
				EntityType bean = createEntity();
				for (ColumnType col : getAllColumns()) {
					if (!isImportColumn(col)) {
						continue;
					}
					if (verifyCellValue(row, col)) {
						// セル値が整合性を保つ場合値をセット
						setColumnValue(row, col, bean);
					}
				}
				if (verifyRowValue(row)) {
					// 行が整合性を保つ場合beanを追加
					entityList.add(bean);
				}
			}

			return entityList;

		} catch (TWarningException e) {
			throw e;
		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			// ファイルの読み込みに失敗しました。
			throw new TException("E00021");
		}

	}

	/**
	 * エクセルファイルのエラーチェックを実施<br>
	 * エラー非存在の場合空のメッセージリストを返却
	 * 
	 * @param file
	 * @return エクセルエラー一覧
	 * @throws TException
	 */
	public List<Message> verifyImportExcel(File file) throws TException {
		// excelファイルを読み取る
		TExcel excel = new TExcel(file);
		return verifyImportExcel(excel);
	}

	/**
	 * エクセルファイルのエラーチェックを実施<br>
	 * エラー非存在の場合空のメッセージリストを返却
	 * 
	 * @param excel
	 * @return エクセルエラー一覧
	 */
	public List<Message> verifyImportExcel(TExcel excel) {
		errorList = new ArrayList();
		// シート存在チェック
		if (!isExistsSheet(excel)) {
			// 取込対象外のファイルです。システムから出力したファイルを利用してください。
			errorList.add(new Message("I00775"));
			return errorList;
		}
		sheet = excel.getSheet(0);
		// 行存在チェック
		if (!isExistsRow()) {
			// ファイルに有効な行がありません。
			errorList.add(new Message("I00296"));
			return errorList;
		}
		// ヘッダチェック(カラム変更がないかテンプレートの整合性をとる)
		if (!isRightTemplate()) {
			// 取込対象外のファイルです。システムから出力したファイルを利用してください。
			errorList.add(new Message("I00775"));
			return errorList;
		}
		// シート内各値の検証
		verifySheetValues();
		return errorList;
	}

	/**
	 * シート内の各値を検証
	 */
	protected void verifySheetValues() {
		for (int row = 1; row < sheet.getRowCount(); row++) {
			for (ColumnType col : getAllColumns()) {
				if (!isImportColumn(col)) {
					continue;
				}
				verifyCellValue(row, col);
			}
			verifyRowValue(row);
		}
	}

	/**
	 * シート存在チェック
	 * 
	 * @param excel
	 * @return true: 問題なし
	 */
	protected boolean isExistsSheet(TExcel excel) {
		if (excel.getSheetCount() == 0) {
			return false;
		}
		return true;
	}

	/**
	 * シート行存在チェック
	 * 
	 * @return true:問題なし
	 */
	protected boolean isExistsRow() {
		if (sheet.getRowCount() == 1) {
			return false;
		}
		return true;
	}

	/**
	 * 取込対象のカラムか
	 * 
	 * @param col
	 * @return true:取込対象
	 */
	protected boolean isImportColumn(ColumnType col) {
		if (!isOutput(col)) {
			// 出力されない場合取込非対象
			return false;
		}
		return col.isImportColumn();
	}

	/**
	 * Beanを生成する
	 * 
	 * @return Bean
	 */
	protected abstract EntityType createEntity();

	/**
	 * セル単位ではなく、行単位での値の検証を行う<br>
	 * 特定の値の場合のみ必須や選択肢が絞られるものなど
	 * 
	 * @param row
	 * @return true:問題なし
	 */
	@SuppressWarnings("unused")
	protected boolean verifyRowValue(int row) {
		// 処理なし Override用
		return true;
	}

	/**
	 * 各セルの値を検証する<br>
	 * 型としての有効性、最大桁数のチェック、必須チェック
	 * 
	 * @param row
	 * @param col
	 * @return true:問題なし
	 */
	protected boolean verifyCellValue(int row, ColumnType col) {
		boolean isOK = true;
		if (!col.isImportColumn()) {
			// 取込対象カラムではない場合終了
			return true;
		}
		// 必須チェック
		if (col.isMandatory()) {
			isOK = isOK && verifyCellMandatory(row, col);
		}
		switch (col.getColumnType()) {
			case DATE:
				// データ型チェック 日付
				isOK = isOK && verifyCellIsDate(row, col);
				break;
			case ALPHANUMERIC:
				// 半角英数チェック
				isOK = isOK && verifyCellAlphanumeric(row, col, false);
				// 桁数チェック
				isOK = isOK && verifyCellCharacterLength(row, col);
				break;
			case ALPHANUMERIC_SYMBOLS:
				// 半角英数チェック
				isOK = isOK && verifyCellAlphanumeric(row, col, true);
				// 桁数チェック
				isOK = isOK && verifyCellCharacterLength(row, col);
				break;
			case STRING:
				// 桁数チェック
				isOK = isOK && verifyCellCharacterLength(row, col);
				break;
			case STRING_KANA:
				isOK = isOK && verifyCellIsKana(row, col);
				// 桁数チェック
				isOK = isOK && verifyCellCharacterLength(row, col);
				break;
			case INTEGER:
				// 整数チェック
				isOK = isOK && verifyCellInteger(row, col);
				break;
			case DECIMAL:
				// 小数チェック
				isOK = isOK && verifyCellDecimal(row, col);
				break;
		}
		return isOK;

	}

	/**
	 * セルの型チェック<br>
	 * 半角ｶﾅのみが入力されているか
	 * 
	 * @param row
	 * @param col
	 * @return true:問題なし
	 */
	protected boolean verifyCellIsKana(int row, ColumnType col) {
		String value = sheet.getString(row, getColumnIndex(col));
		if (!StringUtil.isHalfKana(value)) {
			// {0}行目 {1}カラム目:{2}に半角ｶﾅ以外の文字が含まれています。{3}
			Message err = new Message("I00776", row, getColumnIndex(col), getColumnName(col), value);
			errorList.add(err);
			return false;
		}
		return true;
	}

	/**
	 * セルの型チェック<br>
	 * 整数が入力されているか<br>
	 * 最大桁数も設定されている場合チェックを行う
	 * 
	 * @param row
	 * @param col
	 * @return true:問題なし
	 */
	protected boolean verifyCellInteger(int row, ColumnType col) {
		// {0}行目 {1}カラム目:{2}が{3}に変換できません。{4}
		String notNumber = "I00762";
		// 整数
		String intName = "C11888";
		// {0}行目 {1}カラム目:{2}の値が最大桁数を超えています。入力：{3}、最大：{4}
		String numOver = "I00759";
		try {
			int intVal = sheet.getInt(row, getColumnIndex(col));
			if (col.getMaxLength() <= 0) {
				return true;
			}
			if (Math.pow(10, col.getMaxLength()) < intVal) {
				// 桁数オーバー
				String len = Util.avoidNull(intVal);
				Message msg = new Message(numOver, row, getColumnIndex(col), getColumnName(col), len.length(),
					col.getMaxLength());
				errorList.add(msg);
				return false;
			}
		} catch (Exception e) {
			// int変換失敗時（小数点を含む場合など）
			// 型変換のミス想定
			Message msg = new Message(notNumber, row, getColumnIndex(col), getColumnName(col), intName);
			errorList.add(msg);
			return false;
		}
		return true;
	}

	/**
	 * セルの型チェック<br>
	 * Decimalが入力されているか
	 * 
	 * @param row
	 * @param col
	 * @return true:問題なし
	 */
	protected boolean verifyCellDecimal(int row, ColumnType col) {
		// {0}行目 {1}カラム目:{2}が{3}に変換できません。{4}
		String notNumber = "I00762";
		// 数値
		String decimalItem = "C02160";
		// {0}行目 {1}カラム目:{2}の値が最大桁数を超えています。入力：{3}、最大：{4}
		String numOver = "I00759";

		int maxLength = col.getMaxLength();
		int decimalPoint = col.getDecimalPoint();
		try {
			BigDecimal value = getDecimal(row, getColumnIndex(col));
			value = DecimalUtil.avoidNull(value);
			value.stripTrailingZeros();
			String limit = "" + col.getMaxLength() + "," + col.getDecimalPoint();
			if (DecimalUtil.getIntLength(value) > maxLength - decimalPoint) {
				// 整数部桁数が指定の桁数を超える場合
				Message msg = new Message(numOver, row, getColumnIndex(col), getColumnName(col),
					DecimalUtil.getIntLength(value), limit);
				errorList.add(msg);
				return false;
			}
			if (value.scale() > decimalPoint) {
				// 小数点以下桁数が指定の桁数を超える場合
				Message msg = new Message(numOver, row, getColumnIndex(col), getColumnName(col), value.scale(), limit);
				errorList.add(msg);
				return false;
			}
		} catch (Exception e) {
			// Decimal変換失敗時
			// 型変換のミス想定
			Message msg = new Message(notNumber, row, getColumnIndex(col), getColumnName(col), decimalItem);
			errorList.add(msg);
			return false;
		}
		return true;
	}

	/**
	 * セルの値チェック 必須項目が入力されているかを判定
	 * 
	 * @param row
	 * @param col
	 * @return true:問題なし
	 */
	protected boolean verifyCellMandatory(int row, ColumnType col) {
		if (!col.isMandatory()) {
			return true;
		}
		String value = Util.avoidNull(sheet.getString(row, getColumnIndex(col)));
		// {0}行目 {1}カラム目:必須項目に値がありません。{2}
		String mandatory = "I00758";
		if (Util.isNullOrEmpty(value)) {
			Message msg = new Message(mandatory, row, getColumnIndex(col), getColumnName(col));
			errorList.add(msg);
			return false;
		}
		return true;
	}

	/**
	 * セル値が桁数を超えていないかをチェック
	 * 
	 * @param row
	 * @param col
	 * @return true:問題なし
	 */
	protected boolean verifyCellCharacterLength(int row, ColumnType col) {
		if (col.getMaxLength() <= 0) {
			// 最大文字数未設定の場合、処理未実施
			return true;
		}
		if (col.getColumnType() == ExcelColumnType.DATE) {
			// 日付は型チェックで問題なし
			return true;
		}
		// {0}行目 {1}カラム目:{2}の値が最大桁数を超えています。入力：{3}、最大：{4}
		String lengthOver = "I00759";
		String value = Util.avoidNull(sheet.getString(row, getColumnIndex(col)));

		// セルのタイプが半角英数or半角英数記号の場合は数値型の可能性があるので少数低下削除処理
		if (col.getColumnType() == ExcelColumnType.ALPHANUMERIC
			|| col.getColumnType() == ExcelColumnType.ALPHANUMERIC_SYMBOLS) {
			value = numeric4IntegerString(value);
		}

		if (value.length() > col.getMaxLength()) {
			Message msg = new Message(lengthOver, row, getColumnIndex(col), getColumnName(col), value,
				col.getMaxLength());
			errorList.add(msg);
			return false;
		}
		return true;
	}

	/**
	 * 数値書式チェック
	 * 
	 * @param row 行
	 * @param col カラム
	 * @param allowsSymbols
	 * @return true:問題なし
	 */
	protected boolean verifyCellAlphanumeric(int row, ColumnType col, boolean allowsSymbols) {
		String value = numeric4IntegerString(Util.avoidNull(sheet.getString(row, getColumnIndex(col))));

		if (!Util.isNullOrEmpty(value)) {
			String regex = allowsSymbols ? "[^A-Za-z0-9 ./#&-_]" : "[^A-Za-z0-9]";

			BigDecimal bd = new BigDecimal(String.valueOf(Double.MIN_VALUE));
			try {
				bd = new BigDecimal(value);
			} catch (NumberFormatException ne) {
				// 例外発生時は数値ではないので何もしない
			}
			if (!bd.equals(new BigDecimal(String.valueOf(Double.MIN_VALUE)))) {
				// セルが数値型の場合小数点以下が文字列として入るので除去
				bd = bd.setScale(0, RoundingMode.DOWN);
				value = bd.toString();
			}

			String filtered = value.replaceAll(regex, "");
			if (!filtered.equals(value)) {
				// 半角英数以外の文字を含んでいます。
				String errorMessage;
				if (allowsSymbols) {
					// {0}行目 {1}カラム目:{2}に半角英数、符号以外の文字が含まれています。{3}
					errorMessage = "I00760";
				} else {
					// {0}行目 {1}カラム目:{2}に半角英数以外の文字が含まれています。{3}
					errorMessage = "I00761";
				}
				Message msg = new Message(errorMessage, row, col, getColumnName(col), value);
				errorList.add(msg);
				return false;
			}
		}
		return true;
	}

	/**
	 * 数値カラムに入力されたコード値を小数点以下を除いた数値型文字列として返す
	 * 
	 * @param value
	 * @return 数値だった場合に小数点以下を除いた文字列
	 */
	protected String numeric4IntegerString(String value) {

		BigDecimal bd = new BigDecimal(String.valueOf(Double.MIN_VALUE));
		String str = value;

		try {
			bd = new BigDecimal(value);

		} catch (NumberFormatException ne) {
			// 例外発生時は数値ではないので何もしない
		}

		if (bd.compareTo(new BigDecimal(String.valueOf(Double.MIN_VALUE))) != 0) {
			// セルが数値型の場合小数点以下が文字列として入るので除去O
			value = DecimalUtil.toBigDecimal(value).setScale(0, RoundingMode.DOWN).toPlainString();

			if (!value.equals("0") && "0".equals(String.valueOf(str.charAt(0)))) {
				int len = str.getBytes().length;
				value = StringUtil.fillLeft(value, len, '0');
			}
		}
		return value;
	}

	/**
	 * セルの値が日付としてパースできるか確認
	 * 
	 * @param row
	 * @param col
	 * @return true;日付
	 */
	protected boolean verifyCellIsDate(int row, ColumnType col) {
		if (col.getColumnType() != ExcelColumnType.DATE) {
			// 日付以外は対象外
			return true;
		}
		if (!col.isMandatory() && Util.isNullOrEmpty(sheet.getString(row, getColumnIndex(col)))) {
			// 必須項目以外で空の場合
			return true;
		}
		// {0}行目 {1}カラム目:{2}が{3}に変換できません。{4}
		String invalidDate = "I00762";
		// 日付
		String dateItemName = "C00446";
		String value = Util.avoidNull(sheet.getString(row, getColumnIndex(col)));
		Date date;
		try {
			// DECIMAL値で取得した値をParseできるか確認
			date = DateUtil.toDateNE(sheet.getDecimal(row, getColumnIndex(col)));
			if (date == null) {
				date = sheet.getDate(row, getColumnIndex(col));
			}
		} catch (Exception ex) {
			// 失敗時 Stringで再挑戦
			try {
				date = DateUtil.toDateNE(value);
				if (date == null) {
					Message msg = new Message(invalidDate, row, getColumnIndex(col), getColumnName(col), dateItemName);
					errorList.add(msg);
					return false;
				}
			} catch (Exception e) {
				// 行:{0} 列:{1} 項目名:{2} 値:{3} 有効な日付ではありません。
				Message msg = new Message(invalidDate, row, getColumnIndex(col), getColumnName(col), dateItemName);
				errorList.add(msg);
				return false;
			}
		}
		return true;
	}

	/**
	 * 指定位置セルの日付値を取得する
	 * 
	 * @param row
	 * @param col
	 * @return 指定位置セルの日付値
	 */
	protected Date getDate(int row, ColumnType col) {
		Date date = null;
		try {
			// DECIMAL値で取得した値をParseできるか確認
			date = DateUtil.toDateNE(sheet.getDecimal(row, getColumnIndex(col)));
			if (date == null) {
				date = sheet.getDate(row, getColumnIndex(col));
			}
		} catch (Exception ex) {
			// 失敗時 Stringで再挑戦
			try {
				String value = Util.avoidNull(sheet.getString(row, getColumnIndex(col)));
				date = DateUtil.toDateNE(value);
			} catch (Exception e) {
				// エラー時処理はなし
			}
		}
		return date;
	}

	/**
	 * 指定位置セルのDecimal値を取得する<br>
	 * 数値として評価できない場合、エラーをthrow
	 * 
	 * @param row
	 * @param col
	 * @return 指定セルのDecimal値
	 */
	protected BigDecimal getDecimal(int row, int col) {
		return DecimalUtil.toBigDecimal(getCellValue(row, col));
	}

	/**
	 * Excel入力値をエンティティに割り当てていく
	 * 
	 * @param row
	 * @param col
	 * @param bean
	 */
	protected abstract void setColumnValue(int row, ColumnType col, EntityType bean);

	/**
	 * テンプレートの整合性チェック
	 * 
	 * @return テンプレートの整合性
	 */
	protected boolean isRightTemplate() {
		try {
			int column = 0;
			while (true) {
				ColumnType defColumn = getOutputColumn(column);
				String fileCol = sheet.getString(0, column);
				if (Util.isNullOrEmpty(fileCol) && defColumn == null) {
					// ちょうど同じ位置で列が終了の場合
					break;
				}
				if (!Util.equals(fileCol, getColumnName(defColumn))) {
					return false;
				}
				column++;
			}
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	/**
	 * 列位置から出力するカラムを取得
	 * 
	 * @param index
	 * @return 出力するカラム定義
	 */
	protected ColumnType getOutputColumn(int index) {
		int i = 0;
		for (ColumnType col : getAllColumns()) {
			if (!isOutput(col)) {
				continue;
			}
			if (i == index) {
				return col;
			}
			i++;
		}
		return null;
	}

	/**
	 * int値からカラムを取得
	 * 
	 * @param column
	 * @return カラム
	 */
	protected ColumnType getColumn(int column) {
		ColumnType[] defines = getAllColumns();
		return defines[column];
	}

	/**
	 * カラムは出力対象か<br>
	 * 基本的には定義されたカラムはすべて出力する
	 * 
	 * @param col
	 * @return true:出力対象のカラム
	 */
	protected boolean isOutput(@SuppressWarnings("unused") ColumnType col) {
		return true;
	}

	/**
	 * Enum値からカラム位置取得
	 * 
	 * @param col
	 * @return カラム位置
	 */
	protected int getColumnIndex(ColumnType col) {
		int i = 0;
		for (ColumnType item : getAllColumns()) {
			if (col == item) {
				return i;
			}
			if (!isOutput(item)) {
				// 出力されないカラム分は位置を加算しない
				continue;
			}
			i++;
		}
		return i;
	}

	/**
	 * 列の名称を取得する
	 * 
	 * @param col
	 * @return 列の名称
	 */
	protected String getColumnName(ColumnType col) {
		return col.getName();
	}

	/**
	 * @param row
	 * @param col
	 * @return 値
	 */
	protected Object getCellValue(int row, int col) {
		Row row_ = sheet.sheet.getRow(row);

		if (row_ == null) {
			return null;
		}

		Cell cell_ = row_.getCell(col);

		if (cell_ == null) {
			return null;
		}

		switch (cell_.getCellType()) {
			case Cell.CELL_TYPE_STRING:
				return cell_.getRichStringCellValue().getString();

			case Cell.CELL_TYPE_BOOLEAN:
				return cell_.getBooleanCellValue();

			case Cell.CELL_TYPE_NUMERIC:
				return cell_.getNumericCellValue();

			case Cell.CELL_TYPE_FORMULA:
				// 計算式の場合、キャッシュ値を使う

				switch (cell_.getCachedFormulaResultType()) {
					case Cell.CELL_TYPE_STRING:
						return cell_.getRichStringCellValue().getString();

					case Cell.CELL_TYPE_BOOLEAN:
						return cell_.getBooleanCellValue();

					case Cell.CELL_TYPE_NUMERIC:
						return cell_.getNumericCellValue();

					case Cell.CELL_TYPE_ERROR:
						return cell_.getErrorCellValue();
					default:
						return null;
				}

			case Cell.CELL_TYPE_ERROR:
				return cell_.getErrorCellValue();

			case Cell.CELL_TYPE_BLANK:
			default:
				return null;
		}
	}

	/**
	 * 0埋め処理
	 * @param str
	 * @return String
	 */

	protected String addZero(String str) {

		// ブランクはそのまま
		if (Util.isNullOrEmpty(str)) {
			return str;
		}
		// 数値じゃなかったらそのまま
		try {
			new BigDecimal(str);

		} catch (NumberFormatException ne) {
			return str;
		}

		int z = 0;

		for (int j = 0; j < str.length(); j++) {
			if (!("0").equals(String.valueOf(str.charAt(j)))) {
				break;
			}
			z++;
		}
		str = DecimalUtil.toBigDecimal(str).setScale(0, RoundingMode.DOWN).toPlainString();
		if (str.equals("0")) {
			for (int j = 0; j < z - 1; j++) {
				str = "0" + str;
			}
		} else {
			for (int j = 0; j < z; j++) {
				str = "0" + str;
			}
		}
		return str;

	}
}
