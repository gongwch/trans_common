package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.util.*;

import jp.co.ais.trans.common.dt.*;

/**
 * チャートデータ明細
 */
public class TChartDetailItem extends TransferBase implements Cloneable, TChartBarInterface {

	/** 開始日 */
	protected Date from = null;

	/** 終了日 */
	protected Date to = null;

	/** 表示色 */
	protected Color fillColor = null;

	/** 表示文字 */
	protected String label = null;

	/** 表示文字略語 */
	protected String shortLabel = null;

	/** フッター文字 */
	protected String footerLabel = null;

	/** 表示文字色 */
	protected Color foreColor = null;

	/** 対象情報 */
	protected Object obj = null;

	/** 描画■ */
	protected Rectangle rectangle = null;

	/**
	 * コンストラクター
	 */
	public TChartDetailItem() {
		//
	}

	/**
	 * コンストラクター
	 * 
	 * @param from
	 * @param to
	 * @param fillColor
	 * @param label
	 * @param shortLabel
	 * @param footerLabel
	 * @param foreColor
	 * @param obj
	 */
	public TChartDetailItem(Date from, Date to, Color fillColor, String label, String shortLabel, String footerLabel,
		Color foreColor, Object obj) {
		this.from = from;
		this.to = to;
		this.fillColor = fillColor;
		this.label = label;
		this.shortLabel = shortLabel;
		this.footerLabel = footerLabel;
		this.foreColor = foreColor;
		this.obj = obj;
	}

	/**
	 * 開始日の取得
	 * 
	 * @return from 開始日
	 */
	public Date getFrom() {
		return from;
	}

	/**
	 * 開始日の設定
	 * 
	 * @param from 開始日
	 */
	public void setFrom(Date from) {
		this.from = from;
	}

	/**
	 * 終了日の取得
	 * 
	 * @return to 終了日
	 */
	public Date getTo() {
		return to;
	}

	/**
	 * 終了日の設定
	 * 
	 * @param to 終了日
	 */
	public void setTo(Date to) {
		this.to = to;
	}

	/**
	 * 表示色の取得
	 * 
	 * @return fillColor 表示色
	 */
	public Color getFillColor() {
		return fillColor;
	}

	/**
	 * 表示色の設定
	 * 
	 * @param fillColor 表示色
	 */
	public void setFillColor(Color fillColor) {
		this.fillColor = fillColor;
	}

	/**
	 * 表示文字の取得
	 * 
	 * @return label 表示文字
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * 表示文字の設定
	 * 
	 * @param label 表示文字
	 */
	public void setLabel(String label) {
		this.label = label;
	}

	/**
	 * 表示文字略語の取得
	 * 
	 * @return shortLabel 表示文字略語
	 */
	public String getShortLabel() {
		return shortLabel;
	}

	/**
	 * 表示文字略語の設定
	 * 
	 * @param shortLabel 表示文字略語
	 */
	public void setShortLabel(String shortLabel) {
		this.shortLabel = shortLabel;
	}

	/**
	 * フッター文字の取得
	 * 
	 * @return footerLabel フッター文字
	 */
	public String getFooterLabel() {
		return footerLabel;
	}

	/**
	 * フッター文字の設定
	 * 
	 * @param footerLabel フッター文字
	 */
	public void setFooterLabel(String footerLabel) {
		this.footerLabel = footerLabel;
	}

	/**
	 * 表示文字色の取得
	 * 
	 * @return foreColor 表示文字色
	 */
	public Color getForeColor() {
		return foreColor;
	}

	/**
	 * 表示文字色の設定
	 * 
	 * @param foreColor 表示文字色
	 */
	public void setForeColor(Color foreColor) {
		this.foreColor = foreColor;
	}

	/**
	 * 対象情報の取得
	 * 
	 * @return obj 対象情報
	 */
	public Object getObj() {
		return obj;
	}

	/**
	 * 対象情報の設定
	 * 
	 * @param obj 対象情報
	 */
	public void setObj(Object obj) {
		this.obj = obj;
	}

	/**
	 * 描画■の取得
	 * 
	 * @return rectangle 描画■
	 */
	public Rectangle getRectangle() {
		return rectangle;
	}

	/**
	 * 描画■の設定
	 * 
	 * @param rectangle 描画■
	 */
	public void setRectangle(Rectangle rectangle) {
		this.rectangle = rectangle;
	}

}
