package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.util.*;

/**
 * チャートのヘッダ表現
 */
public class TChartHeader extends TChart {

	/**  */
	public FontMetrics fm = null;

	/**
	 * コンストラクター
	 * 
	 * @param cell
	 */
	public TChartHeader(TChart cell) {
		super(cell.baseDt, cell.maxDays, cell.unit);

		// 現在日付
		setCurrentDate(cell.getCurrentDate());
		// 計算用幅
		setCalcWidth(cell.getCalcWidth());

		// true:透明
		setTransparent(cell.isTransparent());
		// 背景色
		setBackground(cell.getBackground());
		// 文字色
		setForeground(cell.getForeground());
	}

	@Override
	protected TChartPainter createPainter() {
		return new TChartHeaderPainter();
	}

	/**
	 * フォント変更処理
	 */
	@Override
	public void setFont(Font font) {
		super.setFont(font);

		fm = getFontMetrics(getFont());
	}

	/**
	 * 表示文字の取得
	 * 
	 * @param day
	 * @return 表示文字
	 */
	public Date getHeaderDate(int day) {
		Date dt = DateUtil.addDay(baseDt, day);
		return dt;
	}

	/**
	 * 表示文字の取得
	 * 
	 * @param dt
	 * @return 表示文字
	 */
	public String[] getTitle1(Date dt) {
		String[] title = new String[] { toTitle1(dt), toTitle2(dt) };
		return title;
	}

	/**
	 * 表示文字の取得
	 * 
	 * @param dt
	 * @return 表示文字
	 */
	public String getTitle2(Date dt) {
		return toTitle2(dt);
	}

	/**
	 * タイトル上段の日付フォーマット
	 * 
	 * @param dt 日付
	 * @return タイトル上段
	 */
	public String toTitle1(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (title1DateFormat) {
			return title1DateFormat.format(dt);
		}
	}

	/**
	 * タイトル下段の日付フォーマット
	 * 
	 * @param dt 日付
	 * @return タイトル下段
	 */
	public String toTitle2(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (title2DateFormat) {
			return title2DateFormat.format(dt);
		}
	}

	/**
	 * 指定日付の座標の取得
	 * 
	 * @param day 差異
	 * @return 座標
	 */
	public int getHeaderPosition(double day) {
		int pos = (int) (day / maxDays * getDrawWidth());

		if (pos == 0) {
			return getDrawX();
		}

		return pos;
	}

	/**
	 * @return 描画Y
	 */
	@Override
	public int getDrawY() {
		return 2;
	}

	/**
	 * ヘッダー、フッターを描画する
	 * 
	 * @return true ヘッダー、フッターを描画する
	 */
	@Override
	public boolean isDrawHeaderAndFooter() {
		return false;
	}
}
