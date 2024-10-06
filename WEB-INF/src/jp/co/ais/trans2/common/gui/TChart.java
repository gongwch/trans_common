package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.text.*;
import java.util.*;
import java.util.List;

import javax.swing.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * チャート
 */
public class TChart extends JComponent implements TTableComponent, TTableColumnHeader {

	/** 汎用INDEXキー */
	public int index;

	/** TableCellEditor利用か */
	public boolean tableCellEditor = false;

	/** 基準日 */
	public Date baseDt = null;

	/** 最大日数 */
	public int maxDays = 0;

	/** 目盛り単位 */
	public int unit = 1;

	/** true:LD/DC描画する */
	public boolean drawLDDC = false;

	/** 対象データ */
	public TChartItem ds = null;

	/** ヘッダーコンポ */
	public TChartHeader headerComponent = null;

	/** タイトル上段の日付フォーマット */
	public DateFormat title1DateFormat = DateUtil.DATE_FORMAT_Y;

	/** タイトル下段の日付フォーマット */
	public DateFormat title2DateFormat = DateUtil.DATE_FORMAT_MD;

	/** アイテムのヘッダータイトルDF */
	public DateFormat itemHeaderDateFormat = new SimpleDateFormat("d");

	/** 薄いステップ */
	public double brighter = 30;

	/** 厚いステップ */
	public double darker = 30;

	/** ステップ */
	public int steps = 60;

	/** 左側線 */
	public boolean drawLeftLine = false;

	/** 左側線 */
	public boolean drawRightLine = false;

	/** 左側線 */
	public boolean drawTopLine = false;

	/** 左側線 */
	public boolean drawBottomLine = false;

	/** 透明 */
	public boolean transparent = false;

	/** ヘッダー＆フッター描画 */
	public boolean drawHeaderAndFooter = true;

	/** 現在日付 */
	public Date currentDate = null;

	/** 計算用幅 */
	public int calcWidth = 1000;

	/** true:土日背景色を描画する */
	protected boolean drawHolidayBackColor = false;

	/** 土曜日背景色 */
	protected Color satBackColor = Color.blue.brighter();

	/** 日曜日背景色 */
	protected Color sunBackColor = Color.red.brighter();

	/** 土日描画RECT */
	protected List<TChartPainterRect> holidayRectangleList = null;

	/** 行選択中 */
	protected boolean tableRowSelected = false;

	/** 描画者作成 */
	public TChartPainter painter = createPainter();

	/** 親スプレッド */
	protected TTable tbl = null;

	/** 摘要明細表示の行数 */
	protected int summaryDetailCount = 0;

	/** 摘要明細行タイトルの文字幅 */
	protected int summaryDetailNameWidth = 30;

	/** DETAIL表現モード */
	protected boolean drawDetailMode = false;

	/**
	 * 初期化
	 * 
	 * @param baseDt 基準日
	 * @param maxDays 最大日数
	 * @param unit 目盛り単位
	 */
	public TChart(Date baseDt, int maxDays, int unit) {
		this.baseDt = baseDt;
		this.maxDays = maxDays;
		this.unit = unit;

		// 初期化
		initComponents();
	}

	/**
	 * 初期化
	 */
	public void initComponents() {
		// 透明
		this.setOpaque(false);
	}

	/**
	 * 描画
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		painter.paintChart(this, g);
	}

	/**
	 * @return 描画用全部■
	 */
	public List<Rectangle> getRectangleList() {
		return getRectangleList(getDrawHeight());
	}

	/**
	 * @param height
	 * @return 描画用全部■
	 */
	public List<Rectangle> getRectangleList(int height) {
		List<Rectangle> rectangles = new ArrayList<Rectangle>();

		if (ds == null || baseDt == null || maxDays == 0 || ds.getItemList() == null || ds.isEmpty()) {
			return null;
		}

		if (height == -1) {
			height = getDrawHeight();
		}

		for (int i = 0; i < ds.getCount(); i++) {
			TChartDetailItem bean = ds.get(i);
			Date from = bean.getFrom();
			Date to = bean.getTo();

			int posFrom = getPosition(from);
			int posTo = getPosition(to);

			Rectangle r = new Rectangle(posFrom, getDrawY(), posTo - posFrom, height);
			rectangles.add(r);
		}

		return rectangles;
	}

	/**
	 * @return バー描画用全部■
	 */
	public List<TChartBarInterface> getBarList() {
		List<TChartBarInterface> list = new ArrayList<TChartBarInterface>();

		if (ds == null || baseDt == null || maxDays == 0 || !isDrawDetailMode() || ds.getItemList() == null
			|| ds.isEmpty()) {
			return null;
		}

		for (int i = 0; i < ds.getCount(); i++) {
			TChartDetailItem bean = ds.get(i);

			if (bean.getObj() == null) {
				continue;
			}

			if (!(bean.getObj() instanceof TChartBarListInterface)) {
				continue;
			}

			List barDetailList = ((TChartBarListInterface) bean.getObj()).getBarDetailList();

			if (barDetailList == null || barDetailList.isEmpty()) {
				continue;
			}

			for (Object obj : barDetailList) {
				if (obj instanceof TChartBarInterface) {
					TChartBarInterface bar = (TChartBarInterface) obj;

					Date from = bar.getFrom();
					Date to = bar.getTo();

					int posFrom = getPosition(from);
					int posTo = getPosition(to);

					Rectangle r = new Rectangle(posFrom, getDrawY(), posTo - posFrom, getDrawHeight());
					bar.setRectangle(r);

					list.add(bar);
				}
			}
		}

		return list;
	}

	/**
	 * @return 描画用■SummaryDetail行タイトル
	 */
	public Rectangle getSummaryDetailTitleRect() {
		if (ds == null || baseDt == null || maxDays == 0 || ds == null || ds.getSummaryDetailItemList() == null
			|| ds.getSummaryDetailItemList().isEmpty()) {
			return null;
		}

		int x = 0;

		Rectangle r = new Rectangle(x, getSummaryDetailStartY(), getSummaryDetailNameWidth(),
			getSummaryDetailDrawHeight());
		return r;
	}

	/**
	 * @return 描画用全部■SummaryDetail
	 */
	public List<Rectangle> getSummaryDetailRectList() {
		List<Rectangle> rectangles = new ArrayList<Rectangle>();

		if (ds == null || baseDt == null || maxDays == 0 || ds == null || ds.getSummaryDetailItemList() == null
			|| ds.getSummaryDetailItemList().isEmpty()) {
			return null;
		}

		int x = 0;
		int startY = getSummaryDetailStartY() - 3;

		for (TChartDetailItem bean : ds.getSummaryDetailItemList()) {
			Date to = bean.getTo();

			int posFrom = getSummaryDetailNameWidth() + x;
			int posTo = getPosition(to) + x;

			Rectangle r = new Rectangle(posFrom, startY, posTo - posFrom, getSummaryDetailDrawRowHeight());
			rectangles.add(r);

			startY += getSummaryDetailDrawRowHeight();
		}

		return rectangles;
	}

	/**
	 * @return SummaryDetail描画時の開始Y
	 */
	protected int getSummaryDetailStartY() {

		int y = 14;

		if (isDrawHeaderAndFooter()) {
			y = 14;
		} else {
			y = 2;
		}

		return y;
	}

	/**
	 * 摘要明細行タイトルの文字幅の取得
	 * 
	 * @return summaryDetailNameWidth 摘要明細行タイトルの文字幅
	 */
	public int getSummaryDetailNameWidth() {
		return summaryDetailNameWidth;
	}

	/**
	 * 摘要明細行タイトルの文字幅の設定
	 * 
	 * @param summaryDetailNameWidth 摘要明細行タイトルの文字幅
	 */
	public void setSummaryDetailNameWidth(int summaryDetailNameWidth) {
		this.summaryDetailNameWidth = summaryDetailNameWidth;
	}

	/**
	 * @return SummaryDetail描画時の高さ
	 */
	public int getSummaryDetailDrawRowHeight() {
		return 14;
	}

	/**
	 * @return 摘要明細描画高さ
	 */
	public int getSummaryDetailDrawHeight() {
		int summaryHeight = 0;
		if (ds != null && ds.getSummaryDetailItemList() != null) {
			summaryHeight = ds.getSummaryDetailItemList().size() * getSummaryDetailDrawRowHeight();
		}
		return summaryHeight;
	}

	/**
	 * @return 描画用全部■LD/DC
	 */
	public List<TChartBarInterface> getLDDCList() {
		List<TChartBarInterface> list = new ArrayList<TChartBarInterface>();

		if (ds == null || baseDt == null || maxDays == 0 || !isDrawLDDC() || ds.getItemList() == null || ds.isEmpty()) {
			return null;
		}

		for (int i = 0; i < ds.getCount(); i++) {
			TChartDetailItem bean = ds.get(i);

			if (bean.getObj() == null) {
				continue;
			}

			if (!(bean.getObj() instanceof TChartBarListInterface)) {
				continue;
			}

			List detailList = ((TChartBarListInterface) bean.getObj()).getDetailList();

			if (detailList == null || detailList.isEmpty()) {
				continue;
			}

			for (Object obj : detailList) {
				if (obj instanceof TChartBarInterface) {
					TChartBarInterface bar = (TChartBarInterface) obj;

					Date from = bar.getFrom();
					Date to = bar.getTo();

					int posFrom = getPosition(from);
					int posTo = getPosition(to);

					Rectangle r = new Rectangle(posFrom, getLDDCStartY(), posTo - posFrom, getLDDCHeight());
					bar.setRectangle(r);

					list.add(bar);
				}
			}
		}

		return list;
	}

	/**
	 * @return 描画用全部■VCC
	 */
	public List<TChartBarInterface> getVCCList() {
		List<TChartBarInterface> list = new ArrayList<TChartBarInterface>();

		if (ds == null || baseDt == null || maxDays == 0 || !isDrawDetailMode() || ds.getItemList() == null
			|| ds.isEmpty()) {
			return null;
		}

		for (int i = 0; i < ds.getCount(); i++) {
			TChartDetailItem bean = ds.get(i);

			if (bean.getObj() == null) {
				continue;
			}

			if (!(bean.getObj() instanceof TChartBarListInterface)) {
				continue;
			}

			List vccDetailList = ((TChartBarListInterface) bean.getObj()).getVCCDetailList();

			if (vccDetailList == null || vccDetailList.isEmpty()) {
				continue;
			}

			for (Object obj : vccDetailList) {
				if (obj instanceof TChartBarInterface) {
					TChartBarInterface bar = (TChartBarInterface) obj;

					Date from = bar.getFrom();
					Date to = bar.getTo();

					int posFrom = getPosition(from);
					int posTo = getPosition(to);

					Rectangle r = new Rectangle(posFrom, getLDDCStartY(), posTo - posFrom, getLDDCHeight());
					bar.setRectangle(r);

					list.add(bar);
				}
			}
		}

		return list;
	}

	/**
	 * @return LDDC描画時の開始Y
	 */
	protected int getLDDCStartY() {

		int startY = 33;

		// TODO: 摘要明細描画モード対応
		startY += getSummaryDetailDrawHeight();

		return startY;
	}

	/**
	 * @return LDDC描画時の高さ
	 */
	protected int getLDDCHeight() {
		return 28;
	}

	/**
	 * @param bean
	 * @param kbn 区分
	 * @return チャートバーリストデータ
	 */
	protected List getChartBarList(TChartDetailItem bean, String kbn) {

		if (bean == null || bean.getObj() == null) {
			return null;
		}

		if (bean.getObj() instanceof TChartBarListInterface) {
			List detailList = ((TChartBarListInterface) bean.getObj()).getDetailList();

			if (detailList != null && !detailList.isEmpty()) {
				return detailList;
			}
		}

		if (bean.getObj() instanceof Map) {
			Map map = (Map) bean.getObj();

			if (!map.containsKey(kbn)) {
				return null;
			} else {
				Object obj = map.get(kbn);
				if (obj != null && (obj instanceof List)) {
					return (List) obj;
				}
			}

		}

		return null;
	}

	/**
	 * クリックされた値の取得
	 * 
	 * @param x
	 * @return クリックされた値
	 */
	public Object getValue(int x) {
		if (ds == null || baseDt == null || maxDays == 0 || ds.getItemList() == null || ds.isEmpty()) {
			return null;
		}

		for (int i = 0; i < ds.getCount(); i++) {
			TChartDetailItem bean = ds.get(i);
			Date from = bean.getFrom();
			Date to = bean.getTo();

			int posFrom = getPosition(from);
			int posTo = getPosition(to);

			if (posFrom <= x && x <= posTo) {
				return bean.getObj();
			}
		}

		return null;
	}

	/**
	 * クリックされた値の取得
	 * 
	 * @param x
	 * @return クリックされた値
	 */
	public Object getPerferredValue(int x) {
		if (ds == null || baseDt == null || maxDays == 0 || ds.getItemList() == null || ds.isEmpty()) {
			return null;
		}

		TChartDetailItem last = null;

		for (int i = 0; i < ds.getCount(); i++) {
			TChartDetailItem bean = ds.get(i);
			Date from = bean.getFrom();
			Date to = bean.getTo();

			int posFrom = getPosition(from);
			int posTo = getPosition(to);

			if (posFrom <= x) {
				last = bean;
			}

			if (posFrom <= x && x <= posTo) {
				return bean.getObj();
			}
		}

		if (last != null) {
			// 直近適当のデータを返す
			return last.getObj();
		}

		return null;
	}

	/**
	 * 指定日付の座標の取得
	 * 
	 * @param dt 日付
	 * @return 座標
	 */
	public int getPosition(Date dt) {
		double days = DateUtil.getDayCount(baseDt, dt, 1).doubleValue();

		int pos = (int) (days / maxDays * getDrawWidth());

		if (pos > getDrawWidth()) {
			return getDrawWidth();
		}

		return pos;
	}

	/**
	 * 指定基準日より日数の座標の取得
	 * 
	 * @param days 日数
	 * @return 座標
	 */
	public int getPosition(double days) {
		// 基準日より日数

		int pos = (int) (days / maxDays * getDrawWidth());

		if (pos > getDrawWidth()) {
			return getDrawWidth();
		}

		return pos;
	}

	/**
	 * @param x
	 * @return 座標Xに対する日付
	 */
	public Date getDate(int x) {

		int days = x * maxDays / getDrawWidth();
		Date dt = DateUtil.addDay(baseDt, days);

		return dt;
	}

	/**
	 * @return 描画X
	 */
	public int getDrawX() {
		return 2;
	}

	/**
	 * @return 描画Y
	 */
	public int getDrawY() {

		int y = 2;

		if (isDrawHeaderAndFooter()) {
			y = getDrawHeaderHeight();
		} else {
			y = 2;
		}

		// TODO: 摘要明細描画モード対応
		y += getSummaryDetailDrawHeight();

		return y;
	}

	/**
	 * @return ヘッダー文字描画時の高さ
	 */
	public int getDrawHeaderHeight() {
		return 14;
	}

	/**
	 * @return 描画Y1
	 */
	public int getDrawTopY() {

		int topY = 0;

		// TODO: 摘要明細描画モード対応
		// topY += getSummaryDetailDrawHeight();

		return topY;
	}

	/**
	 * @return 描画幅
	 */
	public int getDrawWidth() {
		return getCalcWidth() - getDrawX() * 2;
	}

	/**
	 * @return 描画高さ
	 */
	public int getDrawHeight() {

		int height = getHeight() - getDrawY() * 2;

		// TODO: 摘要明細描画モード対応
		height += getSummaryDetailDrawHeight();

		return height;
	}

	/**
	 * @return 描画Y2
	 */
	public int getDrawBottomY() {
		if (isDrawHeaderAndFooter()) {
			return getHeight();
		} else {
			return getDrawHeight();
		}
	}

	/**
	 * 目盛り単位の取得
	 * 
	 * @return unit 目盛り単位
	 */
	public int getUnit() {
		return unit;
	}

	/**
	 * 目盛り単位の設定
	 * 
	 * @param unit 目盛り単位
	 */
	public void setUnit(int unit) {
		this.unit = unit;

		if (headerComponent != null) {
			headerComponent.setUnit(unit);
		}
	}

	/**
	 * 基準日の取得
	 * 
	 * @return baseDt 基準日
	 */
	public Date getBaseDt() {
		return baseDt;
	}

	/**
	 * 基準日の設定
	 * 
	 * @param baseDt 基準日
	 */
	public void setBaseDt(Date baseDt) {
		this.baseDt = baseDt;

		if (headerComponent != null) {
			headerComponent.setBaseDt(baseDt);
		}
	}

	/**
	 * 最大日数の取得
	 * 
	 * @return maxDays 最大日数
	 */
	public int getMaxDays() {
		return maxDays;
	}

	/**
	 * 最大日数の設定
	 * 
	 * @param maxDays 最大日数
	 */
	public void setMaxDays(int maxDays) {
		this.maxDays = maxDays;

		if (headerComponent != null) {
			headerComponent.setMaxDays(maxDays);
		}
	}

	/**
	 * 対象データの取得
	 * 
	 * @return ds 対象データ
	 */
	public TChartItem getDataSource() {
		return ds;
	}

	/**
	 * 対象データの設定
	 * 
	 * @param ds 対象データ
	 */
	public void setDataSource(TChartItem ds) {
		this.ds = ds;
	}

	/**
	 * インデックス番号
	 * 
	 * @return インデックス番号
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * HeaderRendererを返す
	 * 
	 * @param table
	 * @return Renderer
	 */
	public TableCellRenderer getHeaderRenderer(TTable table) {
		return new TChartRenderer(this, table, true);
	}

	/**
	 * CellRendererを返す
	 * 
	 * @param table
	 * @return Renderer
	 */
	public TableCellRenderer getCellRenderer(TTable table) {
		return new TChartRenderer(this, table);
	}

	/**
	 * CellEditorを返す
	 * 
	 * @param table
	 * @return CellEditor
	 */
	public TableCellEditor getCellEditor(TTable table) {
		return null;
	}

	/**
	 * 行番号を取得します
	 * 
	 * @return 行番号
	 */
	public int getRowIndex() {
		return getIndex();
	}

	/**
	 * 行番号を設定します
	 * 
	 * @param rowIndex
	 */
	public void setRowIndex(int rowIndex) {
		this.index = rowIndex;
	}

	/**
	 * 水平方向のalign getter
	 * 
	 * @return 水平方向のalign
	 */
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * テーブルで利用されているかどうか
	 * 
	 * @return true:利用
	 */
	public boolean isTableCellEditor() {
		return tableCellEditor;
	}

	/**
	 * テーブルで利用するかどうか
	 * 
	 * @param tableCellEditor true:利用
	 */
	public void setTableCellEditor(boolean tableCellEditor) {
		this.tableCellEditor = tableCellEditor;
	}

	/**
	 * ヘッダーコンポの取得
	 * 
	 * @return headerComponent ヘッダーコンポ
	 */
	public TChartHeader getHeaderComponent() {
		return headerComponent;
	}

	/**
	 * ヘッダーコンポの設定
	 * 
	 * @param headerComponent ヘッダーコンポ
	 */
	public void setHeaderComponent(TChartHeader headerComponent) {
		this.headerComponent = headerComponent;
	}

	/**
	 * タイトル上段の日付フォーマットの取得
	 * 
	 * @return title1DateFormat タイトル上段の日付フォーマット
	 */
	public DateFormat getTitle1DateFormat() {
		return title1DateFormat;
	}

	/**
	 * タイトル上段の日付フォーマットの設定
	 * 
	 * @param title1DateFormat タイトル上段の日付フォーマット
	 */
	public void setTitle1DateFormat(DateFormat title1DateFormat) {
		this.title1DateFormat = title1DateFormat;
	}

	/**
	 * タイトル下段の日付フォーマットの取得
	 * 
	 * @return title2DateFormat タイトル下段の日付フォーマット
	 */
	public DateFormat getTitle2DateFormat() {
		return title2DateFormat;
	}

	/**
	 * タイトル下段の日付フォーマットの設定
	 * 
	 * @param title2DateFormat タイトル下段の日付フォーマット
	 */
	public void setTitle2DateFormat(DateFormat title2DateFormat) {
		this.title2DateFormat = title2DateFormat;
	}

	/**
	 * アイテムのヘッダータイトルDFの取得
	 * 
	 * @return itemHeaderDateFormat アイテムのヘッダータイトルDF
	 */
	public DateFormat getItemHeaderDateFormat() {
		return itemHeaderDateFormat;
	}

	/**
	 * アイテムのヘッダータイトルDFの設定
	 * 
	 * @param itemHeaderDateFormat アイテムのヘッダータイトルDF
	 */
	public void setItemHeaderDateFormat(DateFormat itemHeaderDateFormat) {
		this.itemHeaderDateFormat = itemHeaderDateFormat;
	}

	/**
	 * ヘッダ描画者の作成
	 * 
	 * @param table
	 * @param backGround
	 * @param foreGround
	 * @return ヘッダ描画者
	 */
	public TableCellRenderer createHeaderRenderer(TTable table, Color backGround, Color foreGround) {
		TChartHeader chartHeader = new TChartHeader(this);
		if (this.getBackground() == null) {
			chartHeader.setBackground(backGround);
		}
		if (this.getForeground() == null) {
			chartHeader.setForeground(foreGround);
		}
		chartHeader.setTitle1DateFormat(title1DateFormat);
		chartHeader.setTitle2DateFormat(title2DateFormat);
		setHeaderComponent(chartHeader);

		return chartHeader.getHeaderRenderer(table);
	}

	/**
	 * 薄いステップの取得
	 * 
	 * @return brighter 薄いステップ
	 */
	public double getBrighter() {
		return brighter;
	}

	/**
	 * 薄いステップの設定
	 * 
	 * @param brighter 薄いステップ
	 */
	public void setBrighter(double brighter) {
		this.brighter = brighter;
	}

	/**
	 * 厚いステップの取得
	 * 
	 * @return darker 厚いステップ
	 */
	public double getDarker() {
		return darker;
	}

	/**
	 * 厚いステップの設定
	 * 
	 * @param darker 厚いステップ
	 */
	public void setDarker(double darker) {
		this.darker = darker;
	}

	/**
	 * ステップの取得
	 * 
	 * @return steps ステップ
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * ステップの設定
	 * 
	 * @param steps ステップ
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}

	/**
	 * 左側線の取得
	 * 
	 * @return drawLeftLine 左側線
	 */
	public boolean isDrawLeftLine() {
		return drawLeftLine;
	}

	/**
	 * 左側線の設定
	 * 
	 * @param drawLeftLine 左側線
	 */
	public void setDrawLeftLine(boolean drawLeftLine) {
		this.drawLeftLine = drawLeftLine;
	}

	/**
	 * 左側線の取得
	 * 
	 * @return drawRightLine 左側線
	 */
	public boolean isDrawRightLine() {
		return drawRightLine;
	}

	/**
	 * 左側線の設定
	 * 
	 * @param drawRightLine 左側線
	 */
	public void setDrawRightLine(boolean drawRightLine) {
		this.drawRightLine = drawRightLine;
	}

	/**
	 * 左側線の取得
	 * 
	 * @return drawTopLine 左側線
	 */
	public boolean isDrawTopLine() {
		return drawTopLine;
	}

	/**
	 * 左側線の設定
	 * 
	 * @param drawTopLine 左側線
	 */
	public void setDrawTopLine(boolean drawTopLine) {
		this.drawTopLine = drawTopLine;
	}

	/**
	 * 左側線の取得
	 * 
	 * @return drawBottomLine 左側線
	 */
	public boolean isDrawBottomLine() {
		return drawBottomLine;
	}

	/**
	 * 左側線の設定
	 * 
	 * @param drawBottomLine 左側線
	 */
	public void setDrawBottomLine(boolean drawBottomLine) {
		this.drawBottomLine = drawBottomLine;
	}

	/**
	 * 透明の取得
	 * 
	 * @return transparent true:透明
	 */
	public boolean isTransparent() {
		return transparent;
	}

	/**
	 * 透明の設定
	 * 
	 * @param transparent true:透明
	 */
	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	/**
	 * ヘッダー＆フッター描画の取得
	 * 
	 * @return drawHeaderAndFooter ヘッダー＆フッター描画
	 */
	public boolean isDrawHeaderAndFooter() {
		return drawHeaderAndFooter;
	}

	/**
	 * ヘッダー＆フッター描画の設定
	 * 
	 * @param drawHeaderAndFooter ヘッダー＆フッター描画
	 */
	public void setDrawHeaderAndFooter(boolean drawHeaderAndFooter) {
		this.drawHeaderAndFooter = drawHeaderAndFooter;
	}

	/**
	 * 現在日付の取得
	 * 
	 * @return currentDate 現在日付
	 */
	public Date getCurrentDate() {
		return currentDate;
	}

	/**
	 * 現在日付の設定
	 * 
	 * @param currentDate 現在日付
	 */
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;

		if (headerComponent != null) {
			headerComponent.setCurrentDate(currentDate);
		}
	}

	/**
	 * 計算用幅の取得
	 * 
	 * @return calcWidth 計算用幅
	 */
	public int getCalcWidth() {
		return calcWidth;
	}

	/**
	 * 計算用幅の設定
	 * 
	 * @param calcWidth 計算用幅
	 */
	public void setCalcWidth(int calcWidth) {
		this.calcWidth = calcWidth;

		if (headerComponent != null) {
			headerComponent.setCalcWidth(calcWidth);
		}
	}

	/**
	 * アイテムヘッダータイトル(文字右上)の日付フォーマット
	 * 
	 * @param dt 日付
	 * @return アイテムヘッダー(文字右上)
	 */
	public String toItemHeaderTitle(Date dt) {
		if (dt == null) {
			return "";
		}

		synchronized (itemHeaderDateFormat) {
			return itemHeaderDateFormat.format(dt);
		}
	}

	/**
	 * @return 描画者
	 */
	protected TChartPainter createPainter() {
		return new TChartPainter();
	}

	/**
	 * 描画者の取得
	 * 
	 * @return painter 描画者
	 */
	public TChartPainter getPainter() {
		return painter;
	}

	/**
	 * 描画者の設定
	 * 
	 * @param painter 描画者
	 */
	public void setPainter(TChartPainter painter) {
		this.painter = painter;
	}

	/**
	 * true:土日背景色を描画するの取得
	 * 
	 * @return drawHolidayBackColor true:土日背景色を描画する
	 */
	public boolean isDrawHolidayBackColor() {
		return drawHolidayBackColor;
	}

	/**
	 * true:土日背景色を描画するの設定
	 * 
	 * @param drawHolidayBackColor true:土日背景色を描画する
	 */
	public void setDrawHolidayBackColor(boolean drawHolidayBackColor) {
		this.drawHolidayBackColor = drawHolidayBackColor;

		if (headerComponent != null) {
			headerComponent.setDrawHolidayBackColor(drawHolidayBackColor);
		}
	}

	/**
	 * 土曜日背景色の取得
	 * 
	 * @return satBackColor 土曜日背景色
	 */
	public Color getSatBackColor() {
		return satBackColor;
	}

	/**
	 * 土曜日背景色の設定
	 * 
	 * @param satBackColor 土曜日背景色
	 */
	public void setSatBackColor(Color satBackColor) {
		this.satBackColor = satBackColor;

		if (headerComponent != null) {
			headerComponent.setSatBackColor(satBackColor);
		}
	}

	/**
	 * 日曜日背景色の取得
	 * 
	 * @return sunBackColor 日曜日背景色
	 */
	public Color getSunBackColor() {
		return sunBackColor;
	}

	/**
	 * 日曜日背景色の設定
	 * 
	 * @param sunBackColor 日曜日背景色
	 */
	public void setSunBackColor(Color sunBackColor) {
		this.sunBackColor = sunBackColor;

		if (headerComponent != null) {
			headerComponent.setSunBackColor(sunBackColor);
		}
	}

	/**
	 * 土日描画RECTの取得
	 * 
	 * @return holidayRectangleList 土日描画RECT
	 */
	public List<TChartPainterRect> getHolidayRectangleList() {
		return holidayRectangleList;
	}

	/**
	 * 土日描画RECTの設定
	 * 
	 * @param holidayRectangleList 土日描画RECT
	 */
	public void setHolidayRectangleList(List<TChartPainterRect> holidayRectangleList) {
		this.holidayRectangleList = holidayRectangleList;

		if (headerComponent != null) {
			headerComponent.setHolidayRectangleList(holidayRectangleList);
		}
	}

	/**
	 * 背景色描画RECT
	 * 
	 * @param dtList
	 * @param color
	 * @return 背景色描画RECT
	 */
	public List<TChartPainterRect> createRectangleList(List<Date> dtList, Color color) {
		return createRectangleList(dtList, color, null, null);
	}

	/**
	 * 背景色描画RECT
	 * 
	 * @param dtList1
	 * @param color1
	 * @param dtList2
	 * @param color2
	 * @return 背景色描画RECT
	 */
	public List<TChartPainterRect> createRectangleList(List<Date> dtList1, Color color1, List<Date> dtList2,
		Color color2) {

		if (getBaseDt() == null || getMaxDays() == 0) {
			return null;
		}

		List<TChartPainterRect> list = new ArrayList<TChartPainterRect>();

		if (color1 != null && dtList1 != null) {
			for (Date dt : dtList1) {
				int posFrom = getPosition(dt);
				int posTo = getPosition(DateUtil.addDay(dt, 1));

				// 描画Bean作成
				TChartPainterRect bean = createTChartPainterRect(posFrom, posTo, color1);
				list.add(bean);
			}
		}

		if (color2 != null && dtList2 != null) {
			for (Date dt : dtList2) {
				int posFrom = getPosition(dt);
				int posTo = getPosition(DateUtil.addDay(dt, 1));

				// 描画Bean作成
				TChartPainterRect bean = createTChartPainterRect(posFrom, posTo, color2);
				list.add(bean);
			}
		}

		return list;
	}

	/**
	 * 土日背景色描画RECT
	 * 
	 * @param sep ステップ
	 * @param colorMap 曜日の場合にMap<曜日int, 背景色>を指定する
	 * @param alterColor 曜日ではない場合に交換色を指定する
	 * @return TChartPainterRect(from,to,背景色)
	 */
	public List<TChartPainterRect> createRectangleList(int sep, Map<Integer, Color> colorMap, Color alterColor) {

		if (getBaseDt() == null || getMaxDays() == 0 || sep < 1) {
			return null;
		}

		// 基本日付の曜日を取得する
		List<TChartPainterRect> list = new ArrayList<TChartPainterRect>();

		if (getSatBackColor() != null || getSunBackColor() != null) {

			boolean isAlter = false;

			for (int i = 0; i <= getMaxDays();) {

				Color color = null;

				if (alterColor != null && sep != 1) {
					// STEP > 1 且つ交換色あり

					if (isAlter) {
						// 偶数行
						color = alterColor;
						isAlter = false;
					} else {
						// 奇数行
						isAlter = true;
					}

				} else {
					if (colorMap == null) {
						return null;
					}

					Date from = DateUtil.addDay(getBaseDt(), i);
					int dayOfWeek = DateUtil.getDayOfWeek(from);

					// STEP = 1 設定された曜日のみ
					if (colorMap.containsKey(dayOfWeek) && colorMap.get(dayOfWeek) != null) {
						color = colorMap.get(dayOfWeek);
					}
				}

				if (color != null) {
					int posFrom = getPosition(i);
					int posTo = getPosition(i + sep);

					// 描画Bean作成
					TChartPainterRect bean = createTChartPainterRect(posFrom, posTo, color);
					list.add(bean);
				}

				i += sep;
			}
		}

		return list;
	}

	/**
	 * @param posFrom
	 * @param posTo
	 * @param color
	 * @return 描画Bean
	 */
	protected TChartPainterRect createTChartPainterRect(int posFrom, int posTo, Color color) {
		return new TChartPainterRect(posFrom, posTo, color);
	}

	/**
	 * 行選択中の取得
	 * 
	 * @return tableRowSelected 行選択中
	 */
	public boolean isTableRowSelected() {
		return tableRowSelected;
	}

	/**
	 * 行選択中の設定
	 * 
	 * @param tableRowSelected 行選択中
	 */
	public void setTableRowSelected(boolean tableRowSelected) {
		this.tableRowSelected = tableRowSelected;
	}

	/**
	 * 日単位で出力しているかどうかの取得
	 * 
	 * @return true 日単位
	 */
	public boolean isUnitDay() {
		return unit == 1;
	}

	/**
	 * true:LD/DC描画するの取得
	 * 
	 * @return drawLDDC true:LD/DC描画する
	 */
	public boolean isDrawLDDC() {
		return drawLDDC;
	}

	/**
	 * true:LD/DC描画するの設定
	 * 
	 * @param drawLDDC true:LD/DC描画する
	 */
	public void setDrawLDDC(boolean drawLDDC) {
		this.drawLDDC = drawLDDC;
	}

	/**
	 * 親スプレッドの取得
	 * 
	 * @return tbl 親スプレッド
	 */
	public TTable getTbl() {
		return tbl;
	}

	/**
	 * 親スプレッドの設定
	 * 
	 * @param tbl 親スプレッド
	 */
	public void setTbl(TTable tbl) {
		this.tbl = tbl;
	}

	/**
	 * 摘要明細表示の行数の取得
	 * 
	 * @return summaryDetailCount 摘要明細表示の行数
	 */
	public int getSummaryDetailCount() {
		return summaryDetailCount;
	}

	/**
	 * 摘要明細表示の行数の設定
	 * 
	 * @param summaryDetailCount 摘要明細表示の行数
	 */
	public void setSummaryDetailCount(int summaryDetailCount) {
		this.summaryDetailCount = summaryDetailCount;
	}

	/**
	 * DETAIL表現モードの取得
	 * 
	 * @return drawDetailMode DETAIL表現モード
	 */
	public boolean isDrawDetailMode() {
		return drawDetailMode;
	}

	/**
	 * DETAIL表現モードの設定
	 * 
	 * @param drawDetailMode DETAIL表現モード
	 */
	public void setDrawDetailMode(boolean drawDetailMode) {
		this.drawDetailMode = drawDetailMode;
	}

}
