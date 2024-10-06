package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.font.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * チャート描画者
 */
public class TChartPainter {

	/**
	 * コンストラクター
	 */
	public TChartPainter() {
		init();
	}

	/**
	 * 初期化
	 */
	public void init() {
		// 処理なし
	}

	/**
	 * チャート描画
	 * 
	 * @param chart 対象チャート
	 * @param g
	 */
	public void paintChart(TChart chart, Graphics g) {

		g.setColor(chart.getBackground());
		g.fillRect(0, 0, chart.getWidth(), chart.getHeight());

		// 休日描画
		drawHoliday(chart, g);

		// 明細バー描画
		drawDetails(chart, g);

		// 現在日付線描画
		drawCurrentDate(chart, g);

	}

	/**
	 * 休日描画
	 * 
	 * @param chart
	 * @param g
	 */
	public void drawHoliday(TChart chart, Graphics g) {

		// 土日背景色
		if (!chart.isTableRowSelected() && chart.isDrawHolidayBackColor()) {
			List<TChartPainterRect> list = chart.getHolidayRectangleList();
			if (list != null) {
				for (TChartPainterRect bean : list) {
					g.setColor(bean.color);
					g.fillRect(bean.from, 0, bean.to - bean.from, chart.getHeight());
				}
			}
		}
	}

	/**
	 * 明細バー描画
	 * 
	 * @param chart
	 * @param g
	 */
	public void drawDetails(TChart chart, Graphics g) {

		g.setColor(chart.getBackground());

		// 描画用全部■
		List<Rectangle> rectangles = getRectangleList(chart);
		if (rectangles == null || rectangles.isEmpty()) {
			return;
		}

		// 摘要明細追加描画
		drawSummaryDetailItems(chart, g);

		int drawSelectedX = chart.ds.getSelectedX();
		Color foreColor = Color.black;

		for (int i = 0; i < rectangles.size(); i++) {
			Rectangle r = rectangles.get(i);
			TChartDetailItem bean = chart.ds.get(i);

			// 枠描画
			Color fillColor = bean.getFillColor();

			// メインバー
			drawDetailMainBar(g, drawSelectedX, r, fillColor);

			// 枠線
			drawDetailLines(chart, g, r, foreColor);
		}

		// 日単位追加描画
		if (!chart.isDrawDetailMode()) {
			drawDetailItems(chart, g);
		} else {
			drawDetailModeDetailItems(chart, g);
		}

		for (int i = 0; i < rectangles.size(); i++) {
			Rectangle r = rectangles.get(i);
			TChartDetailItem bean = chart.ds.get(i);

			// ヘッダー文字、フッター文字
			drawDetailHeaderAndFooter(chart, g, r, foreColor, bean);

			// 文字描画
			if (!chart.isDrawDetailMode()) {
				// DETAILモード以外はバー文字描画
				drawString(chart, g, bean.getForeColor(), r, bean.getLabel(), bean.getShortLabel());
			}
		}

		if (chart.isDrawDetailMode()) {
			// DETAILモード
			drawBarDetail(chart, g);
		}
	}

	/**
	 * バー明細モードの描画
	 * 
	 * @param chart
	 * @param g
	 */
	public void drawBarDetail(TChart chart, Graphics g) {

		// バー描画用全部■
		List<TChartBarInterface> list = chart.getBarList();
		if (list == null || list.isEmpty()) {
			return;
		}

		int drawHeight = chart.getDrawHeaderHeight();
		Color foreColor = Color.black;
		g.setColor(foreColor);

		for (int i = 0; i < list.size(); i++) {
			TChartBarInterface bean = list.get(i);
			Rectangle r = bean.getRectangle();

			int startY = r.y;
			String label = bean.getLabel();
			String labelShort = bean.getShortLabel();

			if (!chart.isDrawDetailMode() || getRectangleHeight() == -1) {

				// 左枠線
				g.drawLine(r.x, startY, r.x, r.y + r.height);

				// 右枠線
				g.drawLine(r.x + r.width, startY, r.x + r.width, r.y + r.height + drawHeight);
			} else {
				// 左枠線
				g.drawLine(r.x, startY, r.x, startY + getRectangleHeight() - 2);

				// 右枠線
				g.drawLine(r.x + r.width, startY, r.x + r.width, startY + getRectangleHeight() - 2);
			}

			// 文字
			Rectangle r1 = new Rectangle(r.x + 2, startY, r.width, drawHeight);
			drawString(chart, g, foreColor, r1, label, labelShort, SwingConstants.CENTER, SwingConstants.CENTER);
		}
	}

	/**
	 * メインバーの全部描画■の取得
	 * 
	 * @param chart
	 * @return メインバーの全部描画■
	 */
	public List<Rectangle> getRectangleList(TChart chart) {
		return chart.getRectangleList(getRectangleHeight());
	}

	/**
	 * メインバーの全部描画■高さの取得
	 * 
	 * @return メインバーの全部描画■高さ
	 */
	public int getRectangleHeight() {
		return -1;
	}

	/**
	 * 明細バー摘要明細追加描画
	 * 
	 * @param chart
	 * @param g
	 */
	public void drawSummaryDetailItems(TChart chart, Graphics g) {

		// 各期日はdsから取得
		// 各行タイトルはdsから取得

		if (chart.getSummaryDetailCount() == 0) {
			return;
		}

		List<Color> fillColorList = new ArrayList<Color>();
		List<Date> toList = new ArrayList<Date>();

		{
			// タイトル文字

			Rectangle r = chart.getSummaryDetailTitleRect();

			if (r != null) {
				List<TChartDetailItem> dayDetailList = chart.getDataSource().getSummaryDetailItemList();

				List<Color> foreColorList = new ArrayList<Color>();
				List<String> wordList = new ArrayList<String>();
				for (TChartDetailItem bean : dayDetailList) {
					fillColorList.add(bean.getFillColor());
					toList.add(bean.getTo());
					foreColorList.add(bean.getForeColor());
					wordList.add(bean.getLabel());
				}

				drawRightStringList(chart, g, foreColorList, r, wordList, chart.getSummaryDetailDrawRowHeight());
			}
		}

		{
			// 線
			List<Rectangle> rectangles = chart.getSummaryDetailRectList();

			if (rectangles != null) {
				for (int i = 0; i < rectangles.size(); i++) {
					Color color = (i >= 0 && i < fillColorList.size()) ? fillColorList.get(i) : Color.black;
					Date dt = (i >= 0 && i < toList.size()) ? toList.get(i) : null;
					Rectangle r = rectangles.get(i);

					if (r == null) {
						continue;
					}

					boolean isMinus = r.width <= 0;

					if (dt != null) {
						// 右側文字描画
						Rectangle strRect = new Rectangle(r);
						strRect.y = strRect.y - chart.getSummaryDetailDrawRowHeight() / 2 - 1;
						if (isMinus) {
							strRect.width = 35;
						} else {
							strRect.x = r.x + r.width;
							strRect.width = 35;
						}

						String str = chart.getTitle2DateFormat().format(dt);
						drawString(chart, g, color, strRect, str, str, true);
					}

					if (!isMinus) {
						// 線は幅ある場合だけ描画する

						g.setColor(color);
						g.drawLine(r.x, r.y - 2, r.x + r.width, r.y - 2);
						g.drawLine(r.x, r.y - 1, r.x + r.width, r.y - 1);

						// 太線表現
						g.setColor(getDoubleLineColor());
						g.drawLine(r.x, r.y, r.x + r.width, r.y);
					}
				}
			}
		}
	}

	/**
	 * 明細メインバーの描画
	 * 
	 * @param g
	 * @param drawSelectedX
	 * @param r
	 * @param fillColor
	 */
	public void drawDetailMainBar(Graphics g, int drawSelectedX, Rectangle r, Color fillColor) {

		boolean isSelected = false;

		// 選択中太枠表示
		if (drawSelectedX != -1) {
			if (r.getMinX() <= drawSelectedX && r.getMaxX() >= drawSelectedX) {
				isSelected = true;
			}
		}

		g.setColor(fillColor);
		g.fillRect(r.x, r.y, r.width, r.height);

		if (isSelected) {
			g.setColor(TTable.columnColor);
			g.drawLine(r.x + 1, r.y, r.x + 1, r.y + r.height - 1); // Left
			g.drawLine(r.x + 2, r.y, r.x + 2, r.y + r.height - 1);
			g.drawLine(r.x + r.width - 1, r.y, r.x + r.width - 1, r.y + r.height - 1);// Right
			g.drawLine(r.x + r.width - 2, r.y, r.x + r.width - 2, r.y + r.height - 1);
			g.drawLine(r.x, r.y + 1, r.x + r.width, r.y + 1); // Top
			g.drawLine(r.x, r.y + 2, r.x + r.width, r.y + 2);
			g.drawLine(r.x, r.y + r.height - 2, r.x + r.width, r.y + r.height - 2);// Bottom
			g.drawLine(r.x, r.y + r.height - 3, r.x + r.width, r.y + r.height - 3);
		}
	}

	/**
	 * 明細の枠線の描画
	 * 
	 * @param chart
	 * @param g
	 * @param r
	 * @param foreColor
	 */
	public void drawDetailLines(TChart chart, Graphics g, Rectangle r, Color foreColor) {

		// 枠描画
		g.setColor(foreColor);

		if (chart.isDrawLeftLine()) {
			if (chart.isDrawHeaderAndFooter()) {
				g.drawLine(r.x, chart.getDrawTopY(), r.x, chart.getDrawBottomY());
			} else {
				g.drawLine(r.x, r.y, r.x, r.y + r.height - 1);
			}
		}
		if (chart.isDrawRightLine()) {
			if (chart.isDrawHeaderAndFooter()) {
				g.drawLine(r.x + r.width, chart.getDrawTopY(), r.x + r.width, chart.getDrawBottomY());
			} else {
				g.drawLine(r.x + r.width, r.y, r.x + r.width, r.y + r.height - 1);
			}
		}
		if (chart.isDrawTopLine()) {
			g.drawLine(r.x, r.y, r.x + r.width, r.y);
		}
		if (chart.isDrawBottomLine()) {
			g.drawLine(r.x, r.y + r.height - 1, r.x + r.width, r.y + r.height - 1);
		}

	}

	/**
	 * 明細バー日単位追加描画
	 * 
	 * @param chart
	 * @param g
	 */
	public void drawDetailItems(TChart chart, Graphics g) {

		// 描画用全部■LD/DC
		List<TChartBarInterface> list = chart.getLDDCList();
		if (list == null || list.isEmpty()) {
			return;
		}

		int drawHeight = chart.getDrawHeaderHeight();
		Color foreColor = Color.black;
		g.setColor(foreColor);

		for (int i = 0; i < list.size(); i++) {
			TChartBarInterface bean = list.get(i);
			Rectangle r = bean.getRectangle();

			int startY = r.y;
			String label = bean.getLabel();
			String labelShort = bean.getShortLabel();
			String eta = DateUtil.toMDHMShortString(bean.getFrom());
			String etd = DateUtil.toMDHMShortString(bean.getTo());
			String etaShort = DateUtil.toMDShortString(bean.getFrom());
			String etdShort = DateUtil.toMDShortString(bean.getTo());

			// 左枠線
			g.drawLine(r.x, startY, r.x, r.y + r.height);

			// 右枠線
			g.drawLine(r.x + r.width, startY, r.x + r.width, r.y + r.height + drawHeight);

			// 左上文字
			Rectangle r1 = new Rectangle(r.x + 2, startY, r.width, drawHeight);
			drawString(chart, g, foreColor, r1, label, labelShort, SwingConstants.CENTER, SwingConstants.LEFT);

			// 左下文字
			Rectangle r2 = new Rectangle(r.x + 2, startY + drawHeight, r.width, drawHeight);
			drawString(chart, g, foreColor, r2, eta, etaShort, SwingConstants.CENTER, SwingConstants.LEFT);

			if (!eta.equals(etd)) {
				// 右上文字
				Rectangle r3 = new Rectangle(r.x + 2, startY + drawHeight * 2, r.width, drawHeight);
				drawString(chart, g, foreColor, r3, etd, etdShort, SwingConstants.CENTER, SwingConstants.RIGHT);
			}
		}
	}

	/**
	 * 明細バー日単位(DETAIL MODE)追加描画
	 * 
	 * @param chart
	 * @param g
	 */
	public void drawDetailModeDetailItems(TChart chart, Graphics g) {

		// バー描画用全部■
		List<TChartBarInterface> list = chart.getVCCList();
		if (list == null || list.isEmpty()) {
			return;
		}

		int drawHeight = chart.getDrawHeaderHeight();
		Color foreColor = Color.black;
		g.setColor(foreColor);
		int startY = list.get(0).getRectangle().y;

		for (int i = 0; i < list.size(); i++) {
			TChartBarInterface bean = list.get(i);
			Rectangle r = bean.getRectangle();

			String label = bean.getLabel();
			String labelShort = bean.getShortLabel();
			Color fillColor = bean.getFillColor();
			String eta = DateUtil.toMDShortString(bean.getFrom());
			String etd = DateUtil.toMDShortString(bean.getTo());

			String[] arr = split(label, "\t", 3);
			String[] arrShort = split(labelShort, "\t", 3);

			String lu1 = eta;
			String lu1Short = eta;
			String lu2 = etd;
			String lu2Short = etd;
			String ld1 = arr[0];
			String ld1Short = arrShort[0];
			String ld2 = arr[1];
			String ld2Short = arrShort[1];
			String ld3 = arr[2];
			String ld3Short = arrShort[2];

			// if (i == 0) {
			// // TODO: 1行目のみFROM-TO描画？

			Rectangle ru = new Rectangle(r.x + 2, startY, r.width, drawHeight);

			// 左上文字
			drawString(chart, g, foreColor, ru, lu1, lu1Short, SwingConstants.CENTER, SwingConstants.LEFT);

			// 左上文字
			drawString(chart, g, foreColor, ru, lu2, lu2Short, SwingConstants.CENTER, SwingConstants.RIGHT);

			startY += drawHeight;

			// }

			Rectangle rd = new Rectangle(r.x + 2, startY, r.width, drawHeight);

			if (fillColor != null) {
				// バー色があれば、描画する
				g.setColor(fillColor);
				g.fillRect(rd.x - 1, rd.y + 1, r.width - 1, rd.height - 2);
			}

			g.setColor(foreColor);

			// 右上文字
			drawString(chart, g, foreColor, rd, ld1, ld1Short, SwingConstants.CENTER, SwingConstants.LEFT);

			// 中央文字
			drawString(chart, g, foreColor, rd, ld2, ld2Short, SwingConstants.CENTER, SwingConstants.CENTER);

			// 右下文字
			drawString(chart, g, foreColor, rd, ld3, ld3Short, SwingConstants.CENTER, SwingConstants.RIGHT);

			// 下枠線
			g.setColor(foreColor);
			g.drawLine(r.x, startY + drawHeight, r.x + r.width, startY + drawHeight);

			startY += drawHeight;
		}
	}

	/**
	 * @param arr
	 * @return 文字配列
	 */
	protected static String[] add(String... arr) {
		return arr;
	}

	/**
	 * @param str
	 * @param sp
	 * @param count
	 * @return 文字配列
	 */
	protected static String[] split(String str, String sp, int count) {
		String[] arr = str.split(sp);

		String[] rt = new String[count];
		for (int i = 0; i < arr.length; i++) {
			if (i >= rt.length) {
				break;
			}
			rt[i] = arr[i];
		}

		return arr;
	}

	/**
	 * 明細のヘッダー/フッターの描画
	 * 
	 * @param chart
	 * @param g
	 * @param r
	 * @param foreColor
	 * @param bean
	 */
	public void drawDetailHeaderAndFooter(TChart chart, Graphics g, Rectangle r, Color foreColor, TChartDetailItem bean) {
		if (chart.isDrawHeaderAndFooter()) {
			// ヘッダー描画
			String title = chart.toItemHeaderTitle(bean.getTo());
			drawHeaderString(chart, g, foreColor, r, title);

			if (chart.isUnitDay() || chart.isDrawDetailMode()) {
				// 日単位はフッターなし
				// 明細モードはフッターなし
			} else {
				// フッター描画
				drawFooterString(chart, g, foreColor, r, bean.getFooterLabel());
			}
		}
	}

	/**
	 * 現在日付線描画
	 * 
	 * @param chart
	 * @param g
	 */
	public void drawCurrentDate(TChart chart, Graphics g) {
		// 現在日付線掛ける
		if (chart.getCurrentDate() != null) {
			int currentDateX = chart.getPosition(chart.getCurrentDate());

			g.setColor(Color.red);
			g.drawLine(currentDateX, 0, currentDateX, chart.getHeight());
		}
	}

	/**
	 * ヘッダー文字描画(右寄せ)
	 * 
	 * @param chart
	 * @param g
	 * @param foreColor
	 * @param r
	 * @param headerWord
	 */
	public void drawHeaderString(TChart chart, Graphics g, Color foreColor, Rectangle r, String headerWord) {
		Rectangle r2 = new Rectangle(r.x, r.y - chart.getDrawHeaderHeight(), r.width, chart.getDrawHeaderHeight());
		drawString(chart, g, foreColor, r2, headerWord, "", SwingConstants.CENTER, SwingConstants.RIGHT);
	}

	/**
	 * フッター文字描画(右寄せ)
	 * 
	 * @param chart
	 * @param g
	 * @param foreColor
	 * @param r
	 * @param footerWord
	 */
	public void drawFooterString(TChart chart, Graphics g, Color foreColor, Rectangle r, String footerWord) {
		Rectangle r2 = new Rectangle(r.x, r.y + r.height, r.width, chart.getDrawY());
		drawString(chart, g, foreColor, r2, footerWord, "", SwingConstants.CENTER, SwingConstants.RIGHT);
	}

	/**
	 * 中央寄せ表示
	 * 
	 * @param chart
	 * @param g
	 * @param foreColor
	 * @param r
	 * @param word
	 * @param shortWord
	 */
	public void drawString(TChart chart, Graphics g, Color foreColor, Rectangle r, String word, String shortWord) {
		drawString(chart, g, foreColor, r, word, shortWord, false);
	}

	/**
	 * 中央寄せ表示
	 * 
	 * @param chart
	 * @param g
	 * @param foreColor
	 * @param r
	 * @param word
	 * @param shortWord
	 * @param doubleLine
	 */
	public void drawString(TChart chart, Graphics g, Color foreColor, Rectangle r, String word, String shortWord,
		boolean doubleLine) {
		drawString(chart, g, foreColor, r, word, shortWord, SwingConstants.CENTER, SwingConstants.CENTER, doubleLine);
	}

	/**
	 * 中央寄せ表示
	 * 
	 * @param chart
	 * @param g
	 * @param foreColor
	 * @param r
	 * @param word
	 * @param shortWord
	 * @param valign 縦寄せ
	 * @param halign 横寄せ
	 */
	public void drawString(TChart chart, Graphics g, Color foreColor, Rectangle r, String word, String shortWord,
		int valign, int halign) {
		drawString(chart, g, foreColor, r, word, shortWord, valign, halign, false);
	}

	/**
	 * 中央寄せ表示
	 * 
	 * @param chart
	 * @param g
	 * @param foreColor
	 * @param r
	 * @param word
	 * @param shortWord
	 * @param valign 縦寄せ
	 * @param halign 横寄せ
	 * @param doubleLine 太字表現
	 */
	public void drawString(TChart chart, Graphics g, Color foreColor, Rectangle r, String word, String shortWord,
		int valign, int halign, boolean doubleLine) {
		Graphics2D g2 = (Graphics2D) g;

		int width2 = r.width;

		FontMetrics fm = chart.getFontMetrics(chart.getFont());
		String label = Util.avoidNull(word);

		if (fm != null) {
			if (fm.stringWidth(label) > width2) {
				label = shortWord;
			}
			if (fm.stringWidth(label) > width2) {
				width2 = fm.stringWidth(label);
			}
		}

		FontRenderContext frc = new FontRenderContext(null, true, true);

		Rectangle2D r2D = chart.getFont().getStringBounds(label, frc);
		int rWidth = (int) Math.round(r2D.getWidth());
		int rHeight = (int) Math.round(r2D.getHeight());
		int rX = (int) Math.round(r2D.getX());
		int rY = (int) Math.round(r2D.getY());

		int x2 = r.x;
		int y2 = r.y;

		if (halign == SwingConstants.CENTER) {
			x2 += (r.width / 2) - (rWidth / 2) - rX;
		} else if (halign == SwingConstants.RIGHT) {
			x2 += r.width - rWidth - 3;
		}

		if (valign == SwingConstants.CENTER) {
			y2 += (r.height / 2) - (rHeight / 2) - rY;
		}

		// 文字描画
		drawString(g2, chart.getFont(), foreColor, label, x2, y2, doubleLine);
	}

	/**
	 * 複数行表示
	 * 
	 * @param chart
	 * @param g
	 * @param foreColorList
	 * @param r
	 * @param wordList
	 * @param rowHeight
	 */
	public void drawRightStringList(TChart chart, Graphics g, List<Color> foreColorList, Rectangle r,
		List<String> wordList, int rowHeight) {
		Graphics2D g2 = (Graphics2D) g;

		FontRenderContext frc = new FontRenderContext(null, true, true);
		int y2 = r.y;

		for (int i = 0; i < wordList.size(); i++) {
			Color foreColor = foreColorList.get(i);
			String label = wordList.get(i);

			Rectangle2D r2D = chart.getFont().getStringBounds(label, frc);
			int rWidth = (int) Math.round(r2D.getWidth());

			int x2 = r.x + r.width - rWidth - 3;

			// 文字描画
			drawString(g2, chart.getFont(), foreColor, label, x2, y2, true);

			y2 += rowHeight;
		}
	}

	/**
	 * 文字描画
	 * 
	 * @param g2
	 * @param font
	 * @param foreColor
	 * @param txt
	 * @param x
	 * @param y
	 * @param doubleLine
	 */
	public void drawString(Graphics2D g2, Font font, Color foreColor, String txt, int x, int y, boolean doubleLine) {

		g2.setFont(font);
		g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);

		if (doubleLine) {
			// 太字表現
			g2.setColor(getDoubleLineColor());
			g2.drawString(txt, x + 1, y + 1);
		}

		g2.setColor(foreColor);
		g2.drawString(txt, x, y);
	}

	/**
	 * @return 太字表現文字色
	 */
	protected Color getDoubleLineColor() {
		return Color.black;
	}
}
