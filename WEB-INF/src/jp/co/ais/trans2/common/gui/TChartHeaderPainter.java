package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.util.*;

/**
 * �`���[�g�`���
 */
public class TChartHeaderPainter extends TChartPainter {

	/**
	 * �R���X�g���N�^�[
	 */
	public TChartHeaderPainter() {
		// �����Ȃ�
	}

	/**
	 * �`���[�g�`��
	 * 
	 * @param chart �Ώۃ`���[�g
	 * @param g
	 */
	@Override
	public void paintChart(TChart chart, Graphics g) {
		paintChart((TChartHeader) chart, g);
	}

	/**
	 * �`���[�g�`��
	 * 
	 * @param chart �Ώۃ`���[�g
	 * @param g
	 */
	public void paintChart(TChartHeader chart, Graphics g) {
		if (chart.baseDt == null || chart.maxDays == 0) {
			return;
		}

		if (chart.isTransparent()) {
			// true:����
			g.clearRect(0, 0, chart.getWidth(), chart.getHeight());
		} else {
			g.setColor(chart.getBackground());
			g.fillRect(0, 0, chart.getWidth(), chart.getHeight());
		}

		// �y���w�i�F
		if (chart.isDrawHolidayBackColor()) {
			List<TChartPainterRect> list = chart.getHolidayRectangleList();
			if (list != null) {
				for (TChartPainterRect bean : list) {
					g.setColor(bean.color);
					g.fillRect(bean.from, 0, bean.to - bean.from, chart.getHeight());
				}
			}
		}

		// �g�`��
		g.setColor(chart.getForeground());

		int y1 = chart.getDrawY() + 20;
		int y2 = chart.getDrawHeight();

		List<Integer> posList = new ArrayList<Integer>();
		List<Date> dtList = new ArrayList<Date>();

		int maxIndex = chart.maxDays / chart.unit;

		for (int i = 0; i <= maxIndex; i++) {
			int pos = chart.getHeaderPosition(i * chart.unit);

			if (!posList.contains(pos)) {
				posList.add(pos);

				Date dt = chart.getHeaderDate(i * chart.unit);
				dtList.add(dt);

			}
		}

		// ����
		g.drawLine(chart.getDrawX(), y2, chart.getDrawWidth(), y2);

		Date currentDt = null;
		for (int i = 0; i < posList.size(); i++) {

			Date dt = dtList.get(i);
			boolean higher = currentDt == null || !DateUtil.toYMString(currentDt).equals(DateUtil.toYMString(dt));

			int x = posList.get(i);

			g.drawLine(x, higher ? y1 - 12 : y1, x, y2);

			if (higher) {
				// �N����
				String[] title = chart.getTitle1(dt);

				drawString(chart, g, title[0], x, y1 - 12);
				drawString(chart, g, title[1], x, y1);

			} else {
				// ����
				String title = chart.getTitle2(dt);
				drawString(chart, g, title, x, y1);
			}

			currentDt = dt;
		}

		// ���ݓ��t���|����
		if (chart.getCurrentDate() != null) {
			double days = DateUtil.getDayCount(chart.baseDt, chart.getCurrentDate(), 1).doubleValue();
			int currentDateX = chart.getHeaderPosition(days);

			g.setColor(Color.red);
			g.drawLine(currentDateX, 0, currentDateX, chart.getHeight());
		}

	}

	/**
	 * �����`��
	 * 
	 * @param chart �Ώۃ`���[�g
	 * @param g
	 * @param s
	 * @param x
	 * @param y
	 */
	public void drawString(TChartHeader chart, Graphics g, String s, int x, int y) {
		if (chart.fm == null) {
			chart.fm = chart.getFontMetrics(chart.getFont());
		}

		if (chart.fm.stringWidth(s) + x > chart.getWidth()) {
			return;
		} else {
			Graphics2D g2 = (Graphics2D) g;
			g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
			g2.drawString(s, x, y);
		}
	}
}
