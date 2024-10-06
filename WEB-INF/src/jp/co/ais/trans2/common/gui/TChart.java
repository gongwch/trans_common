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
 * �`���[�g
 */
public class TChart extends JComponent implements TTableComponent, TTableColumnHeader {

	/** �ėpINDEX�L�[ */
	public int index;

	/** TableCellEditor���p�� */
	public boolean tableCellEditor = false;

	/** ��� */
	public Date baseDt = null;

	/** �ő���� */
	public int maxDays = 0;

	/** �ڐ���P�� */
	public int unit = 1;

	/** true:LD/DC�`�悷�� */
	public boolean drawLDDC = false;

	/** �Ώۃf�[�^ */
	public TChartItem ds = null;

	/** �w�b�_�[�R���| */
	public TChartHeader headerComponent = null;

	/** �^�C�g����i�̓��t�t�H�[�}�b�g */
	public DateFormat title1DateFormat = DateUtil.DATE_FORMAT_Y;

	/** �^�C�g�����i�̓��t�t�H�[�}�b�g */
	public DateFormat title2DateFormat = DateUtil.DATE_FORMAT_MD;

	/** �A�C�e���̃w�b�_�[�^�C�g��DF */
	public DateFormat itemHeaderDateFormat = new SimpleDateFormat("d");

	/** �����X�e�b�v */
	public double brighter = 30;

	/** �����X�e�b�v */
	public double darker = 30;

	/** �X�e�b�v */
	public int steps = 60;

	/** ������ */
	public boolean drawLeftLine = false;

	/** ������ */
	public boolean drawRightLine = false;

	/** ������ */
	public boolean drawTopLine = false;

	/** ������ */
	public boolean drawBottomLine = false;

	/** ���� */
	public boolean transparent = false;

	/** �w�b�_�[���t�b�^�[�`�� */
	public boolean drawHeaderAndFooter = true;

	/** ���ݓ��t */
	public Date currentDate = null;

	/** �v�Z�p�� */
	public int calcWidth = 1000;

	/** true:�y���w�i�F��`�悷�� */
	protected boolean drawHolidayBackColor = false;

	/** �y�j���w�i�F */
	protected Color satBackColor = Color.blue.brighter();

	/** ���j���w�i�F */
	protected Color sunBackColor = Color.red.brighter();

	/** �y���`��RECT */
	protected List<TChartPainterRect> holidayRectangleList = null;

	/** �s�I�� */
	protected boolean tableRowSelected = false;

	/** �`��ҍ쐬 */
	public TChartPainter painter = createPainter();

	/** �e�X�v���b�h */
	protected TTable tbl = null;

	/** �E�v���ו\���̍s�� */
	protected int summaryDetailCount = 0;

	/** �E�v���׍s�^�C�g���̕����� */
	protected int summaryDetailNameWidth = 30;

	/** DETAIL�\�����[�h */
	protected boolean drawDetailMode = false;

	/**
	 * ������
	 * 
	 * @param baseDt ���
	 * @param maxDays �ő����
	 * @param unit �ڐ���P��
	 */
	public TChart(Date baseDt, int maxDays, int unit) {
		this.baseDt = baseDt;
		this.maxDays = maxDays;
		this.unit = unit;

		// ������
		initComponents();
	}

	/**
	 * ������
	 */
	public void initComponents() {
		// ����
		this.setOpaque(false);
	}

	/**
	 * �`��
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);

		painter.paintChart(this, g);
	}

	/**
	 * @return �`��p�S����
	 */
	public List<Rectangle> getRectangleList() {
		return getRectangleList(getDrawHeight());
	}

	/**
	 * @param height
	 * @return �`��p�S����
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
	 * @return �o�[�`��p�S����
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
	 * @return �`��p��SummaryDetail�s�^�C�g��
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
	 * @return �`��p�S����SummaryDetail
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
	 * @return SummaryDetail�`�掞�̊J�nY
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
	 * �E�v���׍s�^�C�g���̕������̎擾
	 * 
	 * @return summaryDetailNameWidth �E�v���׍s�^�C�g���̕�����
	 */
	public int getSummaryDetailNameWidth() {
		return summaryDetailNameWidth;
	}

	/**
	 * �E�v���׍s�^�C�g���̕������̐ݒ�
	 * 
	 * @param summaryDetailNameWidth �E�v���׍s�^�C�g���̕�����
	 */
	public void setSummaryDetailNameWidth(int summaryDetailNameWidth) {
		this.summaryDetailNameWidth = summaryDetailNameWidth;
	}

	/**
	 * @return SummaryDetail�`�掞�̍���
	 */
	public int getSummaryDetailDrawRowHeight() {
		return 14;
	}

	/**
	 * @return �E�v���ו`�捂��
	 */
	public int getSummaryDetailDrawHeight() {
		int summaryHeight = 0;
		if (ds != null && ds.getSummaryDetailItemList() != null) {
			summaryHeight = ds.getSummaryDetailItemList().size() * getSummaryDetailDrawRowHeight();
		}
		return summaryHeight;
	}

	/**
	 * @return �`��p�S����LD/DC
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
	 * @return �`��p�S����VCC
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
	 * @return LDDC�`�掞�̊J�nY
	 */
	protected int getLDDCStartY() {

		int startY = 33;

		// TODO: �E�v���ו`�惂�[�h�Ή�
		startY += getSummaryDetailDrawHeight();

		return startY;
	}

	/**
	 * @return LDDC�`�掞�̍���
	 */
	protected int getLDDCHeight() {
		return 28;
	}

	/**
	 * @param bean
	 * @param kbn �敪
	 * @return �`���[�g�o�[���X�g�f�[�^
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
	 * �N���b�N���ꂽ�l�̎擾
	 * 
	 * @param x
	 * @return �N���b�N���ꂽ�l
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
	 * �N���b�N���ꂽ�l�̎擾
	 * 
	 * @param x
	 * @return �N���b�N���ꂽ�l
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
			// ���ߓK���̃f�[�^��Ԃ�
			return last.getObj();
		}

		return null;
	}

	/**
	 * �w����t�̍��W�̎擾
	 * 
	 * @param dt ���t
	 * @return ���W
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
	 * �w�����������̍��W�̎擾
	 * 
	 * @param days ����
	 * @return ���W
	 */
	public int getPosition(double days) {
		// ���������

		int pos = (int) (days / maxDays * getDrawWidth());

		if (pos > getDrawWidth()) {
			return getDrawWidth();
		}

		return pos;
	}

	/**
	 * @param x
	 * @return ���WX�ɑ΂�����t
	 */
	public Date getDate(int x) {

		int days = x * maxDays / getDrawWidth();
		Date dt = DateUtil.addDay(baseDt, days);

		return dt;
	}

	/**
	 * @return �`��X
	 */
	public int getDrawX() {
		return 2;
	}

	/**
	 * @return �`��Y
	 */
	public int getDrawY() {

		int y = 2;

		if (isDrawHeaderAndFooter()) {
			y = getDrawHeaderHeight();
		} else {
			y = 2;
		}

		// TODO: �E�v���ו`�惂�[�h�Ή�
		y += getSummaryDetailDrawHeight();

		return y;
	}

	/**
	 * @return �w�b�_�[�����`�掞�̍���
	 */
	public int getDrawHeaderHeight() {
		return 14;
	}

	/**
	 * @return �`��Y1
	 */
	public int getDrawTopY() {

		int topY = 0;

		// TODO: �E�v���ו`�惂�[�h�Ή�
		// topY += getSummaryDetailDrawHeight();

		return topY;
	}

	/**
	 * @return �`�敝
	 */
	public int getDrawWidth() {
		return getCalcWidth() - getDrawX() * 2;
	}

	/**
	 * @return �`�捂��
	 */
	public int getDrawHeight() {

		int height = getHeight() - getDrawY() * 2;

		// TODO: �E�v���ו`�惂�[�h�Ή�
		height += getSummaryDetailDrawHeight();

		return height;
	}

	/**
	 * @return �`��Y2
	 */
	public int getDrawBottomY() {
		if (isDrawHeaderAndFooter()) {
			return getHeight();
		} else {
			return getDrawHeight();
		}
	}

	/**
	 * �ڐ���P�ʂ̎擾
	 * 
	 * @return unit �ڐ���P��
	 */
	public int getUnit() {
		return unit;
	}

	/**
	 * �ڐ���P�ʂ̐ݒ�
	 * 
	 * @param unit �ڐ���P��
	 */
	public void setUnit(int unit) {
		this.unit = unit;

		if (headerComponent != null) {
			headerComponent.setUnit(unit);
		}
	}

	/**
	 * ����̎擾
	 * 
	 * @return baseDt ���
	 */
	public Date getBaseDt() {
		return baseDt;
	}

	/**
	 * ����̐ݒ�
	 * 
	 * @param baseDt ���
	 */
	public void setBaseDt(Date baseDt) {
		this.baseDt = baseDt;

		if (headerComponent != null) {
			headerComponent.setBaseDt(baseDt);
		}
	}

	/**
	 * �ő�����̎擾
	 * 
	 * @return maxDays �ő����
	 */
	public int getMaxDays() {
		return maxDays;
	}

	/**
	 * �ő�����̐ݒ�
	 * 
	 * @param maxDays �ő����
	 */
	public void setMaxDays(int maxDays) {
		this.maxDays = maxDays;

		if (headerComponent != null) {
			headerComponent.setMaxDays(maxDays);
		}
	}

	/**
	 * �Ώۃf�[�^�̎擾
	 * 
	 * @return ds �Ώۃf�[�^
	 */
	public TChartItem getDataSource() {
		return ds;
	}

	/**
	 * �Ώۃf�[�^�̐ݒ�
	 * 
	 * @param ds �Ώۃf�[�^
	 */
	public void setDataSource(TChartItem ds) {
		this.ds = ds;
	}

	/**
	 * �C���f�b�N�X�ԍ�
	 * 
	 * @return �C���f�b�N�X�ԍ�
	 */
	public int getIndex() {
		return this.index;
	}

	/**
	 * HeaderRenderer��Ԃ�
	 * 
	 * @param table
	 * @return Renderer
	 */
	public TableCellRenderer getHeaderRenderer(TTable table) {
		return new TChartRenderer(this, table, true);
	}

	/**
	 * CellRenderer��Ԃ�
	 * 
	 * @param table
	 * @return Renderer
	 */
	public TableCellRenderer getCellRenderer(TTable table) {
		return new TChartRenderer(this, table);
	}

	/**
	 * CellEditor��Ԃ�
	 * 
	 * @param table
	 * @return CellEditor
	 */
	public TableCellEditor getCellEditor(TTable table) {
		return null;
	}

	/**
	 * �s�ԍ����擾���܂�
	 * 
	 * @return �s�ԍ�
	 */
	public int getRowIndex() {
		return getIndex();
	}

	/**
	 * �s�ԍ���ݒ肵�܂�
	 * 
	 * @param rowIndex
	 */
	public void setRowIndex(int rowIndex) {
		this.index = rowIndex;
	}

	/**
	 * ����������align getter
	 * 
	 * @return ����������align
	 */
	public int getDefaultCellRendererHorizontalAlignment() {
		return SwingConstants.LEFT;
	}

	/**
	 * �e�[�u���ŗ��p����Ă��邩�ǂ���
	 * 
	 * @return true:���p
	 */
	public boolean isTableCellEditor() {
		return tableCellEditor;
	}

	/**
	 * �e�[�u���ŗ��p���邩�ǂ���
	 * 
	 * @param tableCellEditor true:���p
	 */
	public void setTableCellEditor(boolean tableCellEditor) {
		this.tableCellEditor = tableCellEditor;
	}

	/**
	 * �w�b�_�[�R���|�̎擾
	 * 
	 * @return headerComponent �w�b�_�[�R���|
	 */
	public TChartHeader getHeaderComponent() {
		return headerComponent;
	}

	/**
	 * �w�b�_�[�R���|�̐ݒ�
	 * 
	 * @param headerComponent �w�b�_�[�R���|
	 */
	public void setHeaderComponent(TChartHeader headerComponent) {
		this.headerComponent = headerComponent;
	}

	/**
	 * �^�C�g����i�̓��t�t�H�[�}�b�g�̎擾
	 * 
	 * @return title1DateFormat �^�C�g����i�̓��t�t�H�[�}�b�g
	 */
	public DateFormat getTitle1DateFormat() {
		return title1DateFormat;
	}

	/**
	 * �^�C�g����i�̓��t�t�H�[�}�b�g�̐ݒ�
	 * 
	 * @param title1DateFormat �^�C�g����i�̓��t�t�H�[�}�b�g
	 */
	public void setTitle1DateFormat(DateFormat title1DateFormat) {
		this.title1DateFormat = title1DateFormat;
	}

	/**
	 * �^�C�g�����i�̓��t�t�H�[�}�b�g�̎擾
	 * 
	 * @return title2DateFormat �^�C�g�����i�̓��t�t�H�[�}�b�g
	 */
	public DateFormat getTitle2DateFormat() {
		return title2DateFormat;
	}

	/**
	 * �^�C�g�����i�̓��t�t�H�[�}�b�g�̐ݒ�
	 * 
	 * @param title2DateFormat �^�C�g�����i�̓��t�t�H�[�}�b�g
	 */
	public void setTitle2DateFormat(DateFormat title2DateFormat) {
		this.title2DateFormat = title2DateFormat;
	}

	/**
	 * �A�C�e���̃w�b�_�[�^�C�g��DF�̎擾
	 * 
	 * @return itemHeaderDateFormat �A�C�e���̃w�b�_�[�^�C�g��DF
	 */
	public DateFormat getItemHeaderDateFormat() {
		return itemHeaderDateFormat;
	}

	/**
	 * �A�C�e���̃w�b�_�[�^�C�g��DF�̐ݒ�
	 * 
	 * @param itemHeaderDateFormat �A�C�e���̃w�b�_�[�^�C�g��DF
	 */
	public void setItemHeaderDateFormat(DateFormat itemHeaderDateFormat) {
		this.itemHeaderDateFormat = itemHeaderDateFormat;
	}

	/**
	 * �w�b�_�`��҂̍쐬
	 * 
	 * @param table
	 * @param backGround
	 * @param foreGround
	 * @return �w�b�_�`���
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
	 * �����X�e�b�v�̎擾
	 * 
	 * @return brighter �����X�e�b�v
	 */
	public double getBrighter() {
		return brighter;
	}

	/**
	 * �����X�e�b�v�̐ݒ�
	 * 
	 * @param brighter �����X�e�b�v
	 */
	public void setBrighter(double brighter) {
		this.brighter = brighter;
	}

	/**
	 * �����X�e�b�v�̎擾
	 * 
	 * @return darker �����X�e�b�v
	 */
	public double getDarker() {
		return darker;
	}

	/**
	 * �����X�e�b�v�̐ݒ�
	 * 
	 * @param darker �����X�e�b�v
	 */
	public void setDarker(double darker) {
		this.darker = darker;
	}

	/**
	 * �X�e�b�v�̎擾
	 * 
	 * @return steps �X�e�b�v
	 */
	public int getSteps() {
		return steps;
	}

	/**
	 * �X�e�b�v�̐ݒ�
	 * 
	 * @param steps �X�e�b�v
	 */
	public void setSteps(int steps) {
		this.steps = steps;
	}

	/**
	 * �������̎擾
	 * 
	 * @return drawLeftLine ������
	 */
	public boolean isDrawLeftLine() {
		return drawLeftLine;
	}

	/**
	 * �������̐ݒ�
	 * 
	 * @param drawLeftLine ������
	 */
	public void setDrawLeftLine(boolean drawLeftLine) {
		this.drawLeftLine = drawLeftLine;
	}

	/**
	 * �������̎擾
	 * 
	 * @return drawRightLine ������
	 */
	public boolean isDrawRightLine() {
		return drawRightLine;
	}

	/**
	 * �������̐ݒ�
	 * 
	 * @param drawRightLine ������
	 */
	public void setDrawRightLine(boolean drawRightLine) {
		this.drawRightLine = drawRightLine;
	}

	/**
	 * �������̎擾
	 * 
	 * @return drawTopLine ������
	 */
	public boolean isDrawTopLine() {
		return drawTopLine;
	}

	/**
	 * �������̐ݒ�
	 * 
	 * @param drawTopLine ������
	 */
	public void setDrawTopLine(boolean drawTopLine) {
		this.drawTopLine = drawTopLine;
	}

	/**
	 * �������̎擾
	 * 
	 * @return drawBottomLine ������
	 */
	public boolean isDrawBottomLine() {
		return drawBottomLine;
	}

	/**
	 * �������̐ݒ�
	 * 
	 * @param drawBottomLine ������
	 */
	public void setDrawBottomLine(boolean drawBottomLine) {
		this.drawBottomLine = drawBottomLine;
	}

	/**
	 * �����̎擾
	 * 
	 * @return transparent true:����
	 */
	public boolean isTransparent() {
		return transparent;
	}

	/**
	 * �����̐ݒ�
	 * 
	 * @param transparent true:����
	 */
	public void setTransparent(boolean transparent) {
		this.transparent = transparent;
	}

	/**
	 * �w�b�_�[���t�b�^�[�`��̎擾
	 * 
	 * @return drawHeaderAndFooter �w�b�_�[���t�b�^�[�`��
	 */
	public boolean isDrawHeaderAndFooter() {
		return drawHeaderAndFooter;
	}

	/**
	 * �w�b�_�[���t�b�^�[�`��̐ݒ�
	 * 
	 * @param drawHeaderAndFooter �w�b�_�[���t�b�^�[�`��
	 */
	public void setDrawHeaderAndFooter(boolean drawHeaderAndFooter) {
		this.drawHeaderAndFooter = drawHeaderAndFooter;
	}

	/**
	 * ���ݓ��t�̎擾
	 * 
	 * @return currentDate ���ݓ��t
	 */
	public Date getCurrentDate() {
		return currentDate;
	}

	/**
	 * ���ݓ��t�̐ݒ�
	 * 
	 * @param currentDate ���ݓ��t
	 */
	public void setCurrentDate(Date currentDate) {
		this.currentDate = currentDate;

		if (headerComponent != null) {
			headerComponent.setCurrentDate(currentDate);
		}
	}

	/**
	 * �v�Z�p���̎擾
	 * 
	 * @return calcWidth �v�Z�p��
	 */
	public int getCalcWidth() {
		return calcWidth;
	}

	/**
	 * �v�Z�p���̐ݒ�
	 * 
	 * @param calcWidth �v�Z�p��
	 */
	public void setCalcWidth(int calcWidth) {
		this.calcWidth = calcWidth;

		if (headerComponent != null) {
			headerComponent.setCalcWidth(calcWidth);
		}
	}

	/**
	 * �A�C�e���w�b�_�[�^�C�g��(�����E��)�̓��t�t�H�[�}�b�g
	 * 
	 * @param dt ���t
	 * @return �A�C�e���w�b�_�[(�����E��)
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
	 * @return �`���
	 */
	protected TChartPainter createPainter() {
		return new TChartPainter();
	}

	/**
	 * �`��҂̎擾
	 * 
	 * @return painter �`���
	 */
	public TChartPainter getPainter() {
		return painter;
	}

	/**
	 * �`��҂̐ݒ�
	 * 
	 * @param painter �`���
	 */
	public void setPainter(TChartPainter painter) {
		this.painter = painter;
	}

	/**
	 * true:�y���w�i�F��`�悷��̎擾
	 * 
	 * @return drawHolidayBackColor true:�y���w�i�F��`�悷��
	 */
	public boolean isDrawHolidayBackColor() {
		return drawHolidayBackColor;
	}

	/**
	 * true:�y���w�i�F��`�悷��̐ݒ�
	 * 
	 * @param drawHolidayBackColor true:�y���w�i�F��`�悷��
	 */
	public void setDrawHolidayBackColor(boolean drawHolidayBackColor) {
		this.drawHolidayBackColor = drawHolidayBackColor;

		if (headerComponent != null) {
			headerComponent.setDrawHolidayBackColor(drawHolidayBackColor);
		}
	}

	/**
	 * �y�j���w�i�F�̎擾
	 * 
	 * @return satBackColor �y�j���w�i�F
	 */
	public Color getSatBackColor() {
		return satBackColor;
	}

	/**
	 * �y�j���w�i�F�̐ݒ�
	 * 
	 * @param satBackColor �y�j���w�i�F
	 */
	public void setSatBackColor(Color satBackColor) {
		this.satBackColor = satBackColor;

		if (headerComponent != null) {
			headerComponent.setSatBackColor(satBackColor);
		}
	}

	/**
	 * ���j���w�i�F�̎擾
	 * 
	 * @return sunBackColor ���j���w�i�F
	 */
	public Color getSunBackColor() {
		return sunBackColor;
	}

	/**
	 * ���j���w�i�F�̐ݒ�
	 * 
	 * @param sunBackColor ���j���w�i�F
	 */
	public void setSunBackColor(Color sunBackColor) {
		this.sunBackColor = sunBackColor;

		if (headerComponent != null) {
			headerComponent.setSunBackColor(sunBackColor);
		}
	}

	/**
	 * �y���`��RECT�̎擾
	 * 
	 * @return holidayRectangleList �y���`��RECT
	 */
	public List<TChartPainterRect> getHolidayRectangleList() {
		return holidayRectangleList;
	}

	/**
	 * �y���`��RECT�̐ݒ�
	 * 
	 * @param holidayRectangleList �y���`��RECT
	 */
	public void setHolidayRectangleList(List<TChartPainterRect> holidayRectangleList) {
		this.holidayRectangleList = holidayRectangleList;

		if (headerComponent != null) {
			headerComponent.setHolidayRectangleList(holidayRectangleList);
		}
	}

	/**
	 * �w�i�F�`��RECT
	 * 
	 * @param dtList
	 * @param color
	 * @return �w�i�F�`��RECT
	 */
	public List<TChartPainterRect> createRectangleList(List<Date> dtList, Color color) {
		return createRectangleList(dtList, color, null, null);
	}

	/**
	 * �w�i�F�`��RECT
	 * 
	 * @param dtList1
	 * @param color1
	 * @param dtList2
	 * @param color2
	 * @return �w�i�F�`��RECT
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

				// �`��Bean�쐬
				TChartPainterRect bean = createTChartPainterRect(posFrom, posTo, color1);
				list.add(bean);
			}
		}

		if (color2 != null && dtList2 != null) {
			for (Date dt : dtList2) {
				int posFrom = getPosition(dt);
				int posTo = getPosition(DateUtil.addDay(dt, 1));

				// �`��Bean�쐬
				TChartPainterRect bean = createTChartPainterRect(posFrom, posTo, color2);
				list.add(bean);
			}
		}

		return list;
	}

	/**
	 * �y���w�i�F�`��RECT
	 * 
	 * @param sep �X�e�b�v
	 * @param colorMap �j���̏ꍇ��Map<�j��int, �w�i�F>���w�肷��
	 * @param alterColor �j���ł͂Ȃ��ꍇ�Ɍ����F���w�肷��
	 * @return TChartPainterRect(from,to,�w�i�F)
	 */
	public List<TChartPainterRect> createRectangleList(int sep, Map<Integer, Color> colorMap, Color alterColor) {

		if (getBaseDt() == null || getMaxDays() == 0 || sep < 1) {
			return null;
		}

		// ��{���t�̗j�����擾����
		List<TChartPainterRect> list = new ArrayList<TChartPainterRect>();

		if (getSatBackColor() != null || getSunBackColor() != null) {

			boolean isAlter = false;

			for (int i = 0; i <= getMaxDays();) {

				Color color = null;

				if (alterColor != null && sep != 1) {
					// STEP > 1 �������F����

					if (isAlter) {
						// �����s
						color = alterColor;
						isAlter = false;
					} else {
						// ��s
						isAlter = true;
					}

				} else {
					if (colorMap == null) {
						return null;
					}

					Date from = DateUtil.addDay(getBaseDt(), i);
					int dayOfWeek = DateUtil.getDayOfWeek(from);

					// STEP = 1 �ݒ肳�ꂽ�j���̂�
					if (colorMap.containsKey(dayOfWeek) && colorMap.get(dayOfWeek) != null) {
						color = colorMap.get(dayOfWeek);
					}
				}

				if (color != null) {
					int posFrom = getPosition(i);
					int posTo = getPosition(i + sep);

					// �`��Bean�쐬
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
	 * @return �`��Bean
	 */
	protected TChartPainterRect createTChartPainterRect(int posFrom, int posTo, Color color) {
		return new TChartPainterRect(posFrom, posTo, color);
	}

	/**
	 * �s�I�𒆂̎擾
	 * 
	 * @return tableRowSelected �s�I��
	 */
	public boolean isTableRowSelected() {
		return tableRowSelected;
	}

	/**
	 * �s�I�𒆂̐ݒ�
	 * 
	 * @param tableRowSelected �s�I��
	 */
	public void setTableRowSelected(boolean tableRowSelected) {
		this.tableRowSelected = tableRowSelected;
	}

	/**
	 * ���P�ʂŏo�͂��Ă��邩�ǂ����̎擾
	 * 
	 * @return true ���P��
	 */
	public boolean isUnitDay() {
		return unit == 1;
	}

	/**
	 * true:LD/DC�`�悷��̎擾
	 * 
	 * @return drawLDDC true:LD/DC�`�悷��
	 */
	public boolean isDrawLDDC() {
		return drawLDDC;
	}

	/**
	 * true:LD/DC�`�悷��̐ݒ�
	 * 
	 * @param drawLDDC true:LD/DC�`�悷��
	 */
	public void setDrawLDDC(boolean drawLDDC) {
		this.drawLDDC = drawLDDC;
	}

	/**
	 * �e�X�v���b�h�̎擾
	 * 
	 * @return tbl �e�X�v���b�h
	 */
	public TTable getTbl() {
		return tbl;
	}

	/**
	 * �e�X�v���b�h�̐ݒ�
	 * 
	 * @param tbl �e�X�v���b�h
	 */
	public void setTbl(TTable tbl) {
		this.tbl = tbl;
	}

	/**
	 * �E�v���ו\���̍s���̎擾
	 * 
	 * @return summaryDetailCount �E�v���ו\���̍s��
	 */
	public int getSummaryDetailCount() {
		return summaryDetailCount;
	}

	/**
	 * �E�v���ו\���̍s���̐ݒ�
	 * 
	 * @param summaryDetailCount �E�v���ו\���̍s��
	 */
	public void setSummaryDetailCount(int summaryDetailCount) {
		this.summaryDetailCount = summaryDetailCount;
	}

	/**
	 * DETAIL�\�����[�h�̎擾
	 * 
	 * @return drawDetailMode DETAIL�\�����[�h
	 */
	public boolean isDrawDetailMode() {
		return drawDetailMode;
	}

	/**
	 * DETAIL�\�����[�h�̐ݒ�
	 * 
	 * @param drawDetailMode DETAIL�\�����[�h
	 */
	public void setDrawDetailMode(boolean drawDetailMode) {
		this.drawDetailMode = drawDetailMode;
	}

}
