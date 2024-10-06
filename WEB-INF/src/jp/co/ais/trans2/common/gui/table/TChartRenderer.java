package jp.co.ais.trans2.common.gui.table;

import java.awt.*;

import javax.swing.*;

import jp.co.ais.trans2.common.gui.*;

/**
 * �`���[�g�ҏW�p�Z�������_��
 */
public class TChartRenderer extends TBaseCellRenderer {

	/** �����_���p�R���|�[�l���g */
	protected final TChart renderer;

	/** �w�b�_�\�� */
	protected boolean isHeader = false;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param renderer
	 * @param table
	 */
	public TChartRenderer(TChart renderer, TTable table) {
		this(renderer, table, false);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param renderer
	 * @param table
	 * @param isHeader
	 */
	public TChartRenderer(TChart renderer, TTable table, boolean isHeader) {
		super(table);

		// �����_���p�R���|�[�l���g�̐ݒ�
		this.renderer = renderer;
		this.isHeader = isHeader;
	}

	/**
	 * @see javax.swing.table.TableCellRenderer#getTableCellRendererComponent(javax.swing.JTable, java.lang.Object,
	 *      boolean, boolean, int, int)
	 */
	@Override
	public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus,
		int row, int column) {

		if (!this.isHeader) {
			// ����

			TChartItem item = (TChartItem) value;

			TChart chart = new TChart(this.renderer.getBaseDt(), this.renderer.getMaxDays(), this.renderer.getUnit());

			// �F�̐ݒ�
			chart.setBackground(tbl.getBackgroundColor(row, isSelected, hasFocus).brighter());
			chart.setForeground(tbl.getCellFontColor(isSelected, hasFocus));

			chart.setBrighter(this.renderer.getBrighter());
			chart.setDarker(this.renderer.getDarker());
			chart.setSteps(this.renderer.getSteps());
			chart.setDrawLeftLine(this.renderer.isDrawLeftLine());
			chart.setDrawRightLine(this.renderer.isDrawRightLine());
			chart.setDrawTopLine(this.renderer.isDrawTopLine());
			chart.setDrawBottomLine(this.renderer.isDrawBottomLine());
			chart.setCurrentDate(this.renderer.getCurrentDate());
			chart.setCalcWidth(this.renderer.getCalcWidth());
			chart.setItemHeaderDateFormat(this.renderer.getItemHeaderDateFormat());
			chart.setPainter(this.renderer.getPainter());
			chart.setDrawHolidayBackColor(this.renderer.isDrawHolidayBackColor());
			chart.setHolidayRectangleList(this.renderer.getHolidayRectangleList());
			chart.setSatBackColor(this.renderer.getSatBackColor());
			chart.setSunBackColor(this.renderer.getSunBackColor());
			chart.setTableRowSelected(isSelected);
			chart.setTbl(tbl);
			chart.setRowIndex(row);
			chart.setSummaryDetailCount(this.renderer.getSummaryDetailCount());
			chart.setSummaryDetailNameWidth(this.renderer.getSummaryDetailNameWidth());
			chart.setDrawLDDC(this.renderer.isDrawLDDC());
			chart.setDrawDetailMode(this.renderer.isDrawDetailMode()); // ���ו`�惂�[�h

			// �{�[�_�[�ݒ�
			setBorder(chart, isSelected, hasFocus);

			// �\������
			chart.setToolTipText(item.getToolTipText());
			chart.setDataSource(item);

			int rowHeight = tbl.getCurrentRowHeight();

			if (item.getSummaryDetailItemList() != null && item.getSummaryRowHeight() > 0) {
				rowHeight += item.getSummaryRowHeight();
			}

			if (chart.isDrawDetailMode() && item.getDetailModeRowHeight() > 0) {
				rowHeight += item.getDetailModeRowHeight();
			}

			tbl.setRowHeight(row, rowHeight);

			return chart;

		} else {
			// �w�b�_
			return this.renderer;
		}
	}

	/**
	 * �w�b�_�\���̎擾
	 * 
	 * @return isHeader �w�b�_�\��
	 */
	public boolean isHeader() {
		return isHeader;
	}

	/**
	 * �w�b�_�\���̐ݒ�
	 * 
	 * @param isHeader �w�b�_�\��
	 */
	public void setHeader(boolean isHeader) {
		this.isHeader = isHeader;
	}

}