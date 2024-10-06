package jp.co.ais.trans2.common.gui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;

/**
 * 日付範囲フィールド
 * 
 * @author AIS
 */
public class TDateRange extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -5671715054170282481L;

	/** 日付（開始） */
	public TLabelPopupCalendar dateFrom;

	/** 日付（終了） */
	public TLabelPopupCalendar dateTo;

	/** 年 */
	public static final int TYPE_Y = TCalendar.TYPE_Y;

	/** 年月 */
	public static final int TYPE_YM = TCalendar.TYPE_YM;

	/** 年月日 */
	public static final int TYPE_YMD = TCalendar.TYPE_YMD;

	/** 高さ */
	public static final int height = 20;

	/**
	 * コンストラクタ.
	 */
	public TDateRange() {

		this(TYPE_YMD);

	}

	/**
	 * コンストラクタ.
	 * 
	 * @param calType 日付表示形式
	 */
	public TDateRange(int calType) {

		// コンポーネントを初期化する
		initComponents(calType);

		// コンポーネントを配置する
		allocateComponents();
	}

	/**
	 * コンポーネントを初期化する<BR>
	 * 
	 * @param calType 日付表示形式
	 */
	protected void initComponents(int calType) {
		dateFrom = new TLabelPopupCalendar(calType);
		dateTo = new TLabelPopupCalendar(calType);
	}

	/**
	 * コンポーネントを配置する
	 */
	protected void allocateComponents() {

		setLayout(null);

		// 日付（開始）
		dateFrom.setLocation(0, 0);
		dateFrom.setSize(dateFrom.getCalendarSize() + dateFrom.getLabelSize() + 5, height);
		add(dateFrom);

		// 日付（終了）
		dateTo.setLabelSize(20);
		dateTo.setSize(dateTo.getCalendarSize() + dateTo.getLabelSize() + 5, height);
		dateTo.setLangMessageID("C01333");
		dateTo.setLocation(dateFrom.getWidth(), 0);
		add(dateTo);

		// 全体のサイズを設定
		setSize();

	}

	/**
	 * 全体のサイズ設定
	 */
	public void setSize() {
		setSize(dateFrom.getWidth() + dateTo.getWidth() + 5, 20);
	}

	/**
	 * FROM、TO、全体のサイズを再設定
	 */
	public void resize() {
		dateFrom.setSize(dateFrom.getCalendarSize() + dateFrom.getLabelSize() + 5, height);
		dateTo.setSize(dateTo.getCalendarSize() + dateTo.getLabelSize() + 5, height);
		dateTo.setLocation(dateFrom.getWidth(), 0);
		setSize(dateFrom.getWidth() + dateTo.getWidth() + 5, 20);
	}

	/**
	 * Tab順の設定
	 * 
	 * @param tabControlNo タブ順
	 */
	public void setTabControlNo(int tabControlNo) {
		dateFrom.setTabControlNo(tabControlNo);
		dateTo.setTabControlNo(tabControlNo);
	}

	/**
	 * 入力日付の「FROM <= TO」を比較する.
	 * 
	 * @return 「FROM <= TO」または、どちらかがブランクならばtrue.
	 */
	public boolean isSmallerFrom() {
		Date fromDate = dateFrom.getValue();
		Date toDate = dateTo.getValue();

		if (fromDate == null || toDate == null) {
			return true;
		}

		return fromDate.compareTo(toDate) <= 0;
	}

	/**
	 * 日付（開始）を取得する
	 * 
	 * @return 日付（開始）
	 */
	public TLabelPopupCalendar getDateFrom() {
		return dateFrom;
	}

	/**
	 * 日付（終了）を取得する
	 * 
	 * @return 日付（終了）
	 */
	public TLabelPopupCalendar getDateTo() {
		return dateTo;
	}

	/**
	 * 日付（開始）値を取得する
	 * 
	 * @return 日付（開始）値
	 */
	public Date getValueFrom() {
		return dateFrom.getValue();
	}

	/**
	 * 日付（終了）値を取得する
	 * 
	 * @return 日付（終了）値
	 */
	public Date getValueTo() {
		return dateTo.getValue();
	}

	/**
	 * 編集状態を設定する
	 * 
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		dateFrom.setEditable(isEditable);
		dateTo.setEditable(isEditable);
	}

	/**
	 * 値を設定する
	 * 
	 * @param valueFrom 開始日付の値
	 * @param valueTo 終了日付の値
	 */
	public void setValue(Date valueFrom, Date valueTo) {
		dateFrom.setValue(valueFrom);
		dateTo.setValue(valueTo);
	}

	/**
	 * ラベルを設定する
	 * 
	 * @param ID メッセージID
	 * @param Width ラベルサイズ
	 */
	public void setLabelFrom(String ID, int Width) {
		dateFrom.setLangMessageID(ID);
		dateFrom.setLabelSize(Width);
		resize();
	}

	/**
	 * クリア
	 */
	public void clear() {
		dateFrom.clear();
		dateTo.clear();
	}

}
