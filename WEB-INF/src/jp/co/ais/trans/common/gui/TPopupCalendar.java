package jp.co.ais.trans.common.gui;

import javax.swing.table.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * カレンダーフィールドコンポーネント
 */
public class TPopupCalendar extends TCalendar implements TTableComponent {

	/** シリアルUID */
	private static final long serialVersionUID = 3560600449562807948L;

	/** @deprecated TYPE_YMを使用. */
	public static final int YEAR_MONTH = TCalendar.TYPE_YM;

	/** @deprecated TYPE_YMDを使用 */
	public static final int YEAR_MONTH_DATE = TCalendar.TYPE_YMD;

	/** 年 */
	@SuppressWarnings("hiding")
	public static final int TYPE_Y = TCalendar.TYPE_Y;

	/** 年月 */
	@SuppressWarnings("hiding")
	public static final int TYPE_YM = TCalendar.TYPE_YM;

	/** 年月日 */
	@SuppressWarnings("hiding")
	public static final int TYPE_YMD = TCalendar.TYPE_YMD;

	/** 年月日時分 */
	@SuppressWarnings("hiding")
	public static final int TYPE_YMDHM = TCalendar.TYPE_YMDHM;

	/** 年月日時分秒 */
	@SuppressWarnings("hiding")
	public static final int TYPE_YMDHMS = TCalendar.TYPE_YMDHMS;

	/**
	 * コンストラクタ<br>
	 * カレンダータイプはTYPE_YMD
	 */
	public TPopupCalendar() {
		super();
	}

	/**
	 * コンストラクタ.<br>
	 * 指定したタイプに従ってカレンダーを構築する
	 * <ol>
	 * <li>TYPE_Y : 年
	 * <li>TYPE_YM : 年月
	 * <li>TYPE_YMD : 年月日
	 * <li>TYPE_TIMER : 年月日 分日時
	 * </ol>
	 * 
	 * @param calType カレンダータイプ(TYPE_X)
	 */
	public TPopupCalendar(int calType) {
		super(calType);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	@Override
	public TableCellEditor getCellEditor(TTable tbl) {

		tableCellEditor = true;
		return new TDateEditor(this, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	@Override
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new TDateRenderer(new TPopupCalendar(this.calendarType), tbl);
	}
}
