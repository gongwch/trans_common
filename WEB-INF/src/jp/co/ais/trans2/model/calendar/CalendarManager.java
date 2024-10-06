package jp.co.ais.trans2.model.calendar;

import java.io.*;
import java.util.*;

import jp.co.ais.trans.common.except.*;

/**
 * カレンダー管理インターフェース。
 * 
 * @author AIS
 */
public interface CalendarManager {

	/**
	 * 指定条件に該当するカレンダー情報を返す。
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するカレンダー情報
	 * @throws TException
	 */
	public Map<String, Boolean> get(CalendarSearchCondition condition) throws TException;

	/**
	 * 指定条件に該当するカレンダー情報を返す。
	 * 
	 * @param condition 検索条件
	 * @return 指定条件に該当するカレンダー情報
	 * @throws TException
	 */
	public List<CalendarEntity> getCalendar(CalendarSearchCondition condition) throws TException;

	/**
	 * カレンダーを登録する。
	 * 
	 * @param list カレンダー
	 * @param condition
	 * @throws TException
	 */
	public void entry(List<CalendarEntity> list, CalendarSearchCondition condition) throws TException;

	/**
	 * エクセル取込の処理
	 * 
	 * @param file
	 * @throws TException
	 */
	public void importExcel(File file) throws TException;

	/**
	 * エクセル出力の処理
	 * 
	 * @param condition
	 * @return エクセル
	 * @throws TException
	 */
	public byte[] exportExcel(CalendarSearchCondition condition) throws TException;
}
