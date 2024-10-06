package jp.co.ais.trans2.common.gui;

import java.awt.*;

/**
 * 描画開始、終了、背景色
 */
public class TChartPainterRect {

	/**
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("from=").append(from);
		sb.append(", to=").append(to);
		sb.append(", color=").append(color);
		return sb.toString();
	}

	/** 開始 */
	public int from = 0;

	/** 終了 */
	public int to = 0;

	/** 背景色 */
	public Color color = null;

	/**
	 * コンストラクター
	 * 
	 * @param from 開始
	 * @param to 終了
	 * @param color 背景色
	 */
	public TChartPainterRect(int from, int to, Color color) {
		this.from = from;
		this.to = to;
		this.color = color;
	}

}
