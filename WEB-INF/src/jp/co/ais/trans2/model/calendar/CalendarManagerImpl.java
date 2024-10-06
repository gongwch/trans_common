package jp.co.ais.trans2.model.calendar;

import java.io.*;
import java.math.*;
import java.sql.*;
import java.util.*;
import java.util.Date;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.db.*;
import jp.co.ais.trans2.common.db.DBUtil;
import jp.co.ais.trans2.common.excel.*;
import jp.co.ais.trans2.common.exception.*;
import jp.co.ais.trans2.master.excel.*;
import jp.co.ais.trans2.model.*;

/**
 * カレンダー管理の実装クラスです。
 * 
 * @author AIS
 */
public class CalendarManagerImpl extends TModel implements CalendarManager {

	/**
	 * 指定条件に該当するカレンダー情報を返す。
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するカレンダー情報<日付、（可能:true、不可:false、存在しない:null）>
	 * @throws TException
	 */
	public Map<String, Boolean> get(CalendarSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		Map<String, Boolean> map = new TreeMap<String, Boolean>();

		try {

			String column = condition.getDivisionColumn();
			if (Util.isNullOrEmpty(column)) {
				column = CalendarSearchCondition.BANK;
			}

			// カレンダーマスタを抽出
			SQLCreator sql = new SQLCreator();

			sql.add("");
			sql.add(" SELECT cal.CAL_DATE, ");
			sql.add("        cal." + column + " AS KBN ");
			sql.add(" FROM   AP_CAL_MST cal ");
			sql.add(" WHERE  cal.KAI_CODE = ? ", condition.getCompanyCode()); // 会社コード

			// 開始日付
			if (!Util.isNullOrEmpty(condition.getDateFrom())) {
				sql.add(" AND CAL_DATE >= ? ", condition.getDateFrom());
			}

			// 終了日付
			if (!Util.isNullOrEmpty(condition.getDateTo())) {
				sql.add(" AND CAL_DATE <= ? ", condition.getDateTo());
			}

			sql.add(" ORDER  BY cal.CAL_DATE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				Date date = rs.getDate("CAL_DATE");
				String kbn = rs.getString("KBN");

				String dt = DateUtil.toYMDString(date);

				if (Util.isNullOrEmpty(kbn)) {
					map.put(dt, null); // 未設定あり
				} else {
					if (CalendarSearchCondition.BANK.equals(column)) {
						map.put(dt, "0".equals(kbn)); // 0:営業日
					} else {
						map.put(dt, "1".equals(kbn)); // 1:可能日（臨時支払日、社員支払日）
					}
				}
			}

			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}

		return map;
	}

	/**
	 * @param condition
	 * @return カレンダーマスタ
	 * @throws TException
	 */
	public List<CalendarEntity> getCalendar(CalendarSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		List<CalendarEntity> list = new ArrayList<CalendarEntity>();

		try {
			// カレンダーマスタを抽出
			SQLCreator sql = new SQLCreator();
			sql.add(" SELECT ");
			sql.add("  cal.KAI_CODE ");
			sql.add(" ,cal.CAL_DATE ");
			sql.add(" ,cal.CAL_HARAI ");
			sql.add(" ,cal.CAL_BNK_KBN ");
			sql.add(" ,cal.CAL_SHA ");
			sql.add(" ,cal.CAL_INP_DATE ");
			sql.add(" ,cal.UPD_DATE ");
			sql.add(" ,cal.PRG_ID ");
			sql.add(" ,cal.USR_ID ");
			sql.add(" FROM AP_CAL_MST cal ");
			sql.add(" WHERE 1 = 1 ");

			// 会社コード
			if (!Util.isNullOrEmpty(condition.getCompanyCode())) {
				sql.add("AND cal.KAI_CODE = ? ", condition.getCompanyCode());
			}

			// 開始日付
			if (!Util.isNullOrEmpty(condition.getDateFrom())) {
				sql.add(" AND cal.CAL_DATE >= ? ", condition.getDateFrom());
			}

			// 終了日付
			if (!Util.isNullOrEmpty(condition.getDateTo())) {
				sql.add(" AND cal.CAL_DATE <= ? ", condition.getDateTo());
			}
			sql.add(" ORDER BY cal.CAL_DATE ");

			Statement statement = DBUtil.getStatement(conn);
			ResultSet rs = DBUtil.select(statement, sql);

			while (rs.next()) {
				list.add(mapping(rs));
			}
			DBUtil.close(rs);
			DBUtil.close(statement);

		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
		return list;
	}

	/**
	 * O/Rマッピング
	 * 
	 * @param rs
	 * @return エンティティ
	 * @throws Exception
	 */
	protected CalendarEntity mapping(ResultSet rs) throws Exception {

		CalendarEntity bean = new CalendarEntity();

		// 基本情報マッピング
		bean.setCompanyCode(rs.getString("KAI_CODE"));
		bean.setCalDate(rs.getDate("CAL_DATE"));
		bean.setCalSha(rs.getString("CAL_HARAI"));
		bean.setCalBank(rs.getString("CAL_BNK_KBN"));
		bean.setCalRinji(rs.getString("CAL_SHA"));
		return bean;
	}

	/**
	 * カレンダーマスタに登録
	 * 
	 * @param list
	 * @throws TException
	 */
	public void entry(List<CalendarEntity> list, CalendarSearchCondition condition) throws TException {

		Connection conn = DBUtil.getConnection();

		try {
			delete(conn, condition);

			for (CalendarEntity bean : list) {
				SQLCreator sql = new SQLCreator();
				sql.add(" INSERT INTO AP_CAL_MST ");
				sql.add(" ( ");
				sql.add(" KAI_CODE ");
				sql.add(" ,CAL_DATE ");
				sql.add(" ,CAL_HARAI ");
				sql.add(" ,CAL_BNK_KBN ");
				sql.add(" ,CAL_SHA ");
				sql.add(" ,CAL_INP_DATE ");
				sql.add(" ,PRG_ID ");
				sql.add(" ,USR_ID ");
				sql.add(" ) VALUES ( ");
				sql.add("  ?", bean.getCompanyCode());
				sql.add(", ?", bean.getCalDate());
				sql.add(", ?", bean.getCalSha());
				sql.add(", ?", bean.getCalBank());
				sql.add(", ?", bean.getCalRinji());
				sql.adt(", ?", getNow());
				sql.add(", ?", getProgramCode());
				sql.add(", ?", getUserCode());
				sql.add(" ) ");

				DBUtil.execute(conn, sql);
			}
		} catch (Exception e) {
			throw new TException(e);
		} finally {
			DBUtil.close(conn);
		}
	}

	/**
	 * カレンダーマスタから削除
	 * 
	 * @param conn
	 * @param condition
	 * @throws TException
	 */
	public void delete(Connection conn, CalendarSearchCondition condition) throws TException {

		SQLCreator sql = new SQLCreator();
		sql.add(" DELETE FROM AP_CAL_MST ");
		sql.add("WHERE KAI_CODE = ? ", condition.getCompanyCode());
		sql.add("AND CAL_DATE >= ? ", condition.getDateFrom());
		sql.add("AND CAL_DATE <= ? ", condition.getDateTo());

		DBUtil.execute(conn, sql);
	}

	/**
	 * エクスセル取込の処理
	 * 
	 * @param file
	 * @throws TException
	 */
	public void importExcel(File file) throws TException {

		// ファイルからI/Fデータの抽出
		List<CalendarEntity> list = convertExcel(file);

		CalendarSearchCondition condition = new CalendarSearchCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setDateFrom(list.get(0).getCalDate());
		condition.setDateTo(list.get(list.size() - 1).getCalDate());

		entry(list, condition);
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
	 * @param sheet
	 * @return true:問題なし
	 */
	protected boolean isExistsRow(TExcelSheet sheet) {

		if (sheet.getRowCount() == 1) {
			return false;
		}
		return true;
	}

	/**
	 * テンプレートの整合性チェック
	 * 
	 * @param file
	 * @return テンプレートの整合性
	 * @throws TException
	 */
	protected List<CalendarEntity> convertExcel(File file) throws TException {

		List<CalendarEntity> list = new ArrayList<CalendarEntity>();

		try {
			// excelファイルを読み取る
			TExcel excel = new TExcel(file);
			// シート存在チェック
			if (!isExistsSheet(excel)) {
				// 取込対象外のファイルです。システムから出力したファイルを利用してください。
				throw new TWarningException(getMessage("I00775"));
			}
			TExcelSheet sheet = excel.getSheet(0);
			// 行存在チェック
			if (!isExistsRow(sheet)) {
				// ファイルに有効な行がありません。
				throw new TWarningException(getMessage("I00296"));
			}
			// ヘッダチェック(カラム変更がないかテンプレートの整合性をとる)
			if (sheet.sheet.getRow(0).getLastCellNum() != 5) {
				// 取込対象外のファイルです。システムから出力したファイルを利用してください。
				throw new TWarningException(getMessage("I00775"));
			}

			Date preDate = null;

			for (int row = 1; row < sheet.getRowCount(); row++) {
				String rowMsg = (row + 1) + getWord("C04288") + " ： ";

				String tgtDate = sheet.getString(row, 0);

				// 必須チェック
				if (Util.isNullOrEmpty(tgtDate)) {
					throw new TWarningException(rowMsg + getMessage("I00037", "C00446"));
				}

				// 日付チェック
				try {
					TExcel.getExcelDate(new BigDecimal(tgtDate));
				} catch (Exception e) {
					// 日付形式ではありません。正しい形式で入力を行ってください。
					throw new TWarningException(rowMsg + getMessage("I00803"));
				}

				CalendarEntity bean = mapping(sheet, row);

				if (preDate != null && !DateUtil.equals(DateUtil.addDay(preDate, 1), bean.getCalDate())) {
					throw new TWarningException(rowMsg + getMessage("I01061", DateUtil.toYMDString(bean.getCalDate())));
				}
				list.add(bean);
				preDate = bean.getCalDate();
			}
		} catch (TWarningException e) {
			throw e;
		} catch (TException e) {
			throw e;
		} catch (Exception e) {
			ServerLogger.error("error", e);
			// ファイルの読み込みに失敗しました。
			throw new TException("E00021");
		}
		return list;
	}

	/**
	 * マッピング
	 * 
	 * @param sheet
	 * @param row
	 * @return CalendarEntity
	 */
	public CalendarEntity mapping(TExcelSheet sheet, int row) {

		CalendarEntity bean = new CalendarEntity();
		bean.setCompanyCode(getCompanyCode());
		bean.setCalDate(TExcel.getExcelDate(new BigDecimal((sheet.getString(row, 0)))));
		bean.setCalSha(geteCheckFlg(sheet.getString(row, 2)));
		bean.setCalBank(geteCheckFlg(sheet.getString(row, 3)));
		bean.setCalRinji(geteCheckFlg(sheet.getString(row, 4)));

		return bean;
	}

	/**
	 * @param data
	 * @return flg
	 */
	public String geteCheckFlg(String data) {
		return Util.isNullOrEmpty(data) ? "0" : "1";
	}

	/**
	 * 用途一覧をエクセル形式で返す
	 * 
	 * @param condition 検索条件
	 * @return エクセル形式の用途一覧
	 * @throws TException
	 */
	public byte[] exportExcel(CalendarSearchCondition condition) throws TException {

		List<CalendarEntity> list = getCalendar(condition);

		if (list == null || list.isEmpty()) {
			return null;
		}

		CalenderExcel excel = new CalenderExcel(getUser().getLanguage());
		byte[] data = excel.getExcel(list);

		return data;
	}
}
