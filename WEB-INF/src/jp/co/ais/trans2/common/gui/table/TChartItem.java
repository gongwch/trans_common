package jp.co.ais.trans2.common.gui.table;

import java.awt.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.dt.*;
import jp.co.ais.trans.common.except.*;

/**
 * チャートデータ
 */
public class TChartItem extends TransferBase implements Cloneable {

	/** 表示項目リスト */
	protected List<TChartDetailItem> itemList = null;

	/** ツールチップ */
	protected String toolTipText = null;

	/** 選択中座標X */
	protected int selectedX = -1;

	/** 選択中座標Y */
	protected int selectedY = -1;

	/** 摘要明細各行データリスト */
	protected List<TChartDetailItem> summaryDetailItemList = null;

	/** 行高さ個別指定 */
	protected int summaryRowHeight = 0;

	/** DETAILモード＋高さ */
	protected int detailModeRowHeight = 0;

	/**
	 * クローン
	 * 
	 * @see java.lang.Object#clone()
	 */
	@Override
	public TChartItem clone() {
		try {
			TChartItem clone = (TChartItem) super.clone();
			if (itemList != null) clone.itemList = new ArrayList<TChartDetailItem>(itemList);
			return clone;

		} catch (CloneNotSupportedException ex) {
			throw new TRuntimeException(ex);
		}
	}

	/**
	 * コンストラクター
	 */
	public TChartItem() {
		this.itemList = new ArrayList<TChartDetailItem>();
	}

	/**
	 * @return データ件数
	 */
	public int getCount() {
		return itemList.size();
	}

	/**
	 * @return true:空リスト
	 */
	public boolean isEmpty() {
		return itemList.isEmpty();
	}

	/**
	 * @param i
	 * @return 明細項目
	 */
	public TChartDetailItem get(int i) {
		return itemList.get(i);
	}

	/**
	 * @return TChartDetailItem
	 */
	public TChartDetailItem createItem() {
		return new TChartDetailItem();
	}

	/**
	 * @param from
	 * @param to
	 * @param fillColor
	 * @param label
	 * @param shortLabel
	 * @param footerLabel
	 * @param foreColor
	 * @param obj
	 * @return TChartDetailItem
	 */
	public TChartDetailItem createItem(Date from, Date to, Color fillColor, String label, String shortLabel,
		String footerLabel, Color foreColor, Object obj) {
		return new TChartDetailItem(from, to, fillColor, label, shortLabel, footerLabel, foreColor, obj);
	}

	/**
	 * 項目追加
	 * 
	 * @param from 開始日
	 * @param to 終了日
	 * @param fillColor 表示色
	 * @param label 表示文字
	 * @param shortLabel 表示文字略語
	 * @param footerLabel フッターラベル
	 * @param foreColor 表示文字色
	 * @param obj 持つデータ
	 */
	public void addItem(Date from, Date to, Color fillColor, String label, String shortLabel, String footerLabel,
		Color foreColor, Object obj) {
		TChartDetailItem item = createItem(from, to, fillColor, label, shortLabel, footerLabel, foreColor, obj);
		itemList.add(item);
	}

	/**
	 * ソート
	 */
	public void sort() {
		if (itemList == null) {
			return;
		}

		// from-to並び順でソート
		TChartDetailItemComparator comparator = new TChartDetailItemComparator();
		Collections.sort(itemList, comparator);
	}

	/**
	 * 項目クリア
	 */
	public void clear() {
		itemList.clear();
	}

	/**
	 * 表示項目リストの取得
	 * 
	 * @return itemList 表示項目リスト
	 */
	public List<TChartDetailItem> getItemList() {
		return itemList;
	}

	/**
	 * 表示項目リストの設定
	 * 
	 * @param itemList 表示項目リスト
	 */
	public void setItemList(List<TChartDetailItem> itemList) {
		this.itemList = itemList;
	}

	/**
	 * ツールチップの取得
	 * 
	 * @return toolTipText ツールチップ
	 */
	public String getToolTipText() {
		return toolTipText;
	}

	/**
	 * ツールチップの設定
	 * 
	 * @param toolTipText ツールチップ
	 */
	public void setToolTipText(String toolTipText) {
		this.toolTipText = toolTipText;
	}

	/**
	 * 選択中座標Xの取得
	 * 
	 * @return selectedX 選択中座標X
	 */
	public int getSelectedX() {
		return selectedX;
	}

	/**
	 * 選択中座標Xの設定
	 * 
	 * @param selectedX 選択中座標X
	 */
	public void setSelectedX(int selectedX) {
		this.selectedX = selectedX;
	}

	/**
	 * 選択中座標Yの取得
	 * 
	 * @return selectedY 選択中座標Y
	 */
	public int getSelectedY() {
		return selectedY;
	}

	/**
	 * 選択中座標Yの設定
	 * 
	 * @param selectedY 選択中座標Y
	 */
	public void setSelectedY(int selectedY) {
		this.selectedY = selectedY;
	}

	/**
	 * 摘要明細各行データリストの取得
	 * 
	 * @return summaryDetailItemList 摘要明細各行データリスト
	 */
	public List<TChartDetailItem> getSummaryDetailItemList() {
		return summaryDetailItemList;
	}

	/**
	 * 摘要明細各行データリストの設定
	 * 
	 * @param summaryDetailItemList 摘要明細各行データリスト
	 */
	public void setSummaryDetailItemList(List<TChartDetailItem> summaryDetailItemList) {
		this.summaryDetailItemList = summaryDetailItemList;
	}

	/**
	 * 行高さ個別指定の取得
	 * 
	 * @return summaryRowHeight 行高さ個別指定
	 */
	public int getSummaryRowHeight() {
		return summaryRowHeight;
	}

	/**
	 * 行高さ個別指定の設定
	 * 
	 * @param summaryRowHeight 行高さ個別指定
	 */
	public void setSummaryRowHeight(int summaryRowHeight) {
		this.summaryRowHeight = summaryRowHeight;
	}

	/**
	 * DETAILモード＋高さの取得
	 * 
	 * @return detailModeRowHeight DETAILモード＋高さ
	 */
	public int getDetailModeRowHeight() {
		return detailModeRowHeight;
	}

	/**
	 * DETAILモード＋高さの設定
	 * 
	 * @param detailModeRowHeight DETAILモード＋高さ
	 */
	public void setDetailModeRowHeight(int detailModeRowHeight) {
		this.detailModeRowHeight = detailModeRowHeight;
	}

}
