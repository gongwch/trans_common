package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.util.*;

/**
 * チャートのバー表現I/F
 */
public interface TChartBarInterface {

	/**
	 * 開始日の取得
	 * 
	 * @return from 開始日
	 */
	public Date getFrom();

	/**
	 * 終了日の取得
	 * 
	 * @return to 終了日
	 */
	public Date getTo();

	/**
	 * 表示色の取得
	 * 
	 * @return fillColor 表示色
	 */
	public Color getFillColor();

	/**
	 * 表示文字の取得
	 * 
	 * @return label 表示文字
	 */
	public String getLabel();

	/**
	 * 表示文字略語の取得
	 * 
	 * @return shortLabel 表示文字略語
	 */
	public String getShortLabel();

	/**
	 * フッター文字の取得
	 * 
	 * @return footerLabel フッター文字
	 */
	public String getFooterLabel();

	/**
	 * 表示文字色の取得
	 * 
	 * @return foreColor 表示文字色
	 */
	public Color getForeColor();

	/**
	 * 対象情報の取得
	 * 
	 * @return obj 対象情報
	 */
	public Object getObj();

	/**
	 * 描画■の取得
	 * 
	 * @return rectangle 描画■
	 */
	public Rectangle getRectangle();

	/**
	 * 描画■の設定
	 * 
	 * @param rectangle 描画■
	 */
	public void setRectangle(Rectangle rectangle);
}
