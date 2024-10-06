package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.util.*;
import java.util.List;

import javax.swing.*;

import com.klg.jclass.table.*;
import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.config.*;
import jp.co.ais.trans.common.gui.*;

/**
 * �Œ�񂠂�e�[�u�� <br>
 * <b>TFixedTable</b>�𗘗p���Ă��������B
 * 
 * @deprecated
 */
public class TFrozenTable extends jp.co.ais.trans.common.gui.TTable {

	/** �w�b�_�[�̃J���[ */
	protected static Color columnColor = new Color(90, 90, 90);

	/** �w�b�_�[�̃t�H���g�J���[ */
	protected static Color columnFontColor = Color.WHITE;

	/** ���I�����̃J���[ */
	protected static Color notSelectedColor = new Color(255, 255, 255);

	/** ���I�����̃J���[(�ʐF) */
	protected static Color notSelectedColor2 = new Color(230, 240, 250);

	/** ���I�����̃t�H���g�J���[ */
	protected static Color cellFontColor = Color.BLACK;

	/** �s�̍��� */
	protected static int rowHeight = 25;

	/** �E�񂹃Z���X�^�C�� */
	protected static JCCellStyle rightStyle = null;

	/** �����񂹃Z���X�^�C�� */
	protected static JCCellStyle centerStyle = null;

	/** �J���� */
	protected List<TTableColumn> columns = null;

	/** �J�����C���f�b�N�X */
	protected Map<Enum, Integer> indexs = null;

	/**
	 * �R���X�g���N�^
	 */
	public TFrozenTable() {
		//
		initStyle();
	}

	/**
	 * TTable�f�t�H���g�ݒ�
	 */
	protected void initStyle() {

		// �����l�擾
		Color columnColor_ = ClientConfig.getTableLabelColor();
		Color columnFontColor_ = ClientConfig.getTableLabelFontColor();
		Color notSelectedColor_ = ClientConfig.getTableCellBackColor1();
		Color notSelectedColor2_ = ClientConfig.getTableCellBackColor2();
		Color cellFontColor_ = ClientConfig.getTableCellFontColor();
		int rowHeight_ = ClientConfig.getTableCellHeight();

		if (columnColor_ != null) {
			columnColor = columnColor_;
		}

		if (columnFontColor_ != null) {
			columnFontColor = columnFontColor_;
		}

		if (notSelectedColor_ != null) {
			notSelectedColor = notSelectedColor_;
		}

		if (notSelectedColor2_ != null) {
			notSelectedColor2 = notSelectedColor2_;
		}

		if (cellFontColor_ != null) {
			cellFontColor = cellFontColor_;
		}

		if (rowHeight_ != 0) {
			rowHeight = rowHeight_;
		}

		// �W���R���|
		TTextField comp = new TTextField();

		// �\��X�^�C��
		CellStyleModel labelStyle = this.getDefaultLabelStyle();
		labelStyle.setFont(comp.getFont());
		labelStyle.setForeground(columnFontColor);
		labelStyle.setBackground(columnColor);

		// �Z���X�^�C��
		CellStyleModel cellStyle = this.getDefaultCellStyle();
		Color[] cellBackColor = { notSelectedColor2, notSelectedColor };
		cellStyle.setForeground(cellFontColor);
		cellStyle.setRepeatBackgroundColors(cellBackColor);

		// �Z���{�[�_�[
		cellStyle.setCellBorder(new JCCellBorder(JCTableEnum.BORDER_THIN));

		// �s�̍���
		this.setPixelHeight(JCTableEnum.ALL, rowHeight);

		// �J�[�\��
		setTrackCursor(false);

		// �Z���X�^�C��������
		if (rightStyle == null) {
			rightStyle = new JCCellStyle(getDefaultCellStyle());
			rightStyle.setHorizontalAlignment(CellStyleModel.RIGHT);
		}

		if (centerStyle == null) {
			centerStyle = new JCCellStyle(getDefaultCellStyle());
			centerStyle.setHorizontalAlignment(CellStyleModel.CENTER);
		}
	}

	/**
	 * ������
	 */
	public void init() {

		String[] colMessageIDs = new String[columns.size()];
		int[] colWidths = new int[columns.size()];
		for (int i = 0; i < columns.size(); i++) {
			TTableColumn column = columns.get(i);
			colMessageIDs[i] = column.getTitle();
			int ceil = column.getWidth() % 12;
			colWidths[i] = (column.getWidth() - ceil) / 12 + (ceil > 0 ? 1 : 0);

			// �Z���X�^�C���ݒ�
			if (column.getHorizontalAlign() == SwingConstants.RIGHT) {
				setCellStyle(JCTableEnum.ALLCELLS, i, rightStyle);
			} else if (column.getHorizontalAlign() == SwingConstants.CENTER) {
				setCellStyle(JCTableEnum.ALLCELLS, i, centerStyle);
			}
		}

		// ��A�s�\��̃X�^�C���ݒ�
		initSpreadSheet(colMessageIDs, colWidths);

		// Scroll�ʒu�ݒ�
		setHorizSBPosition(JCTableEnum.POSITION_AT_SIDE);
		setVertSBPosition(JCTableEnum.POSITION_AT_SIDE);

		// �����\���f�[�^�̍\�z
		JCVectorDataSource ds = (JCVectorDataSource) getDataSource();
		if (ds == null) {
			ds = new JCVectorDataSource();
		}

		ds.setNumColumns(columns.size());
		ds.setNumRows(0);

		setDataSource(ds);

		// �\�[�g�\
		setSort(true);
	}

	/**
	 * �s�ǉ�
	 * 
	 * @param list �s�f�[�^
	 */
	public void addRow(List<Object> list) {
		JCVectorDataSource ds = (JCVectorDataSource) getDataSource();
		Vector<Vector> cells = ds.getCells();

		if (cells == null) {
			cells = new Vector<Vector>();
		}

		Vector<Object> row = new Vector<Object>();
		row.setSize(columns.size());

		for (int i = 0; i < list.size(); i++) {
			row.set(i, list.get(i));
		}

		int rowIndex = cells.size();
		cells.add(rowIndex, row);

		ds.setCells(cells);
		ds.setNumRows(cells.size());

		setDataSource(ds);
	}

	/**
	 * �S�Ă̍s�폜
	 */
	public void removeRow() {
		JCVectorDataSource ds = (JCVectorDataSource) getDataSource();
		ds.clearCells();
		ds.setNumRows(0);
		setDataSource(ds);
	}

	/**
	 * �s���̎擾
	 * 
	 * @return �s��
	 */
	public int getRowCount() {
		JCVectorDataSource ds = (JCVectorDataSource) getDataSource();
		return ds.getNumRows();
	}

	/**
	 * �I�����[�h�ݒ�<br>
	 * ListSelectionModel.MULTIPLE_INTERVAL_SELECTION:�����s�I�����[�h<br>
	 * �ȊO�A�s�I�����[�h
	 * 
	 * @param model
	 */
	public void setSelectionMode(int model) {
		switch (model) {
			case ListSelectionModel.MULTIPLE_INTERVAL_SELECTION: {
				super.setSelectMultiRange(true);
				break;
			}
			default: {
				super.setSelectionPolicy(JCTableEnum.SELECT_SINGLE);
			}

		}
	}

	/**
	 * ��̃^�C�g���A�����w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 */
	public void addColumn(Enum e, String title, int width) {
		addColumn(e, title, width, SwingConstants.LEFT);
	}

	/**
	 * ��̃^�C�g���A���A�\��/��\�����w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param visible �\��/��\��
	 */
	public void addColumn(Enum e, String title, int width, boolean visible) {
		addColumn(e, title, width, SwingConstants.LEFT, visible);
	}

	/**
	 * ��̃^�C�g���A�����w�肵�ė��ǉ�����
	 * 
	 * @param title ��̃^�C�g��
	 * @param width ��
	 */
	public void addColumn(String title, int width) {
		addColumn(null, title, width, SwingConstants.LEFT);
	}

	/**
	 * ��̃^�C�g���A�����w�肵�ė��ǉ�����
	 * 
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param horizontalAlign ���������\���ʒu
	 */
	public void addColumn(String title, int width, int horizontalAlign) {
		addColumn(null, title, width, horizontalAlign);
	}

	/**
	 * ��̃^�C�g���A���A�\��/��\�����w�肵�ė��ǉ�����
	 * 
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param visible �\��/��\��
	 */
	public void addColumn(String title, int width, boolean visible) {
		addColumn(null, title, width, SwingConstants.LEFT, visible);
	}

	/**
	 * ��̃^�C�g���A���A���������\���ʒu���w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param horizontalAlign ���������\���ʒu
	 */
	public void addColumn(Enum e, String title, int width, int horizontalAlign) {
		addColumn(new TTableColumn(e, title, width, horizontalAlign));
	}

	/**
	 * ��̃^�C�g���A���A���������\���ʒu�A�\��/��\�����w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param horizontalAlign ���������\���ʒu
	 * @param visible �\��/��\��
	 */
	public void addColumn(Enum e, String title, int width, int horizontalAlign, boolean visible) {
		addColumn(new TTableColumn(e, title, width, horizontalAlign, visible));
	}

	/**
	 * ��̃^�C�g���A���A�Z���ɃZ�b�g����R���|�[�l���g���w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param component ��ɃZ�b�g����R���|�[�l���g
	 */
	public void addColumn(Enum e, String title, int width, Class<? extends TTableComponent> component) {
		try {
			TTableComponent instance = component.newInstance();
			addColumn(new TTableColumn(e, title, width, instance.getDefaultCellRendererHorizontalAlignment(), instance));
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	/**
	 * ��̃^�C�g���A���A�Z���ɃZ�b�g����R���|�[�l���g���w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param component ��ɃZ�b�g����R���|�[�l���g
	 */
	public void addColumn(Enum e, String title, int width, TTableComponent component) {
		try {

			addColumn(new TTableColumn(e, title, width, component.getDefaultCellRendererHorizontalAlignment(),
				component));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ��̃^�C�g���A���A�Z���ɃZ�b�g����R���|�[�l���g�A�\��/��\�����w�肵�ė��ǉ�����
	 * 
	 * @param e �񎯕ʔԍ�
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param component ��ɃZ�b�g����R���|�[�l���g
	 * @param visible �\��/��\��
	 */
	public void addColumn(Enum e, String title, int width, TTableComponent component, boolean visible) {
		try {
			addColumn(new TTableColumn(e, title, width, component.getDefaultCellRendererHorizontalAlignment(),
				component, visible));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ��̃^�C�g���A���A�Z���ɃZ�b�g����R���|�[�l���g���w�肵�ė��ǉ�����
	 * 
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param component ��ɃZ�b�g����R���|�[�l���g
	 */
	public void addColumn(String title, int width, TTableComponent component) {
		try {
			addColumn(new TTableColumn(null, title, width, component.getDefaultCellRendererHorizontalAlignment(),
				component, true));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ��̃^�C�g���A���A�Z���ɃZ�b�g����R���|�[�l���g�A�\��/��\�����w�肵�ė��ǉ�����
	 * 
	 * @param title ��̃^�C�g��
	 * @param width ��
	 * @param component ��ɃZ�b�g����R���|�[�l���g
	 * @param visible �\��/��\��
	 */
	public void addColumn(String title, int width, TTableComponent component, boolean visible) {
		try {
			addColumn(new TTableColumn(null, title, width, component.getDefaultCellRendererHorizontalAlignment(),
				component, visible));
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	/**
	 * ���ǉ�����
	 * 
	 * @param column ��
	 */
	public void addColumn(TTableColumn column) {
		if (columns == null) {
			columns = new ArrayList<TTableColumn>();
		}

		if (indexs == null) {
			indexs = new HashMap<Enum, Integer>();
		}

		indexs.put(column.e, columns.size());
		columns.add(column);
	}

	/**
	 * �s�I��ݒ�
	 * 
	 * @param row �s�ԍ�
	 */
	public void setRowSelection(int row) {
		super.setRowSelection(row, row);
	}

	/**
	 * �w��s�̃Z���f�[�^�̎擾
	 * 
	 * @param row �s�ԍ�
	 * @param e ��Enum
	 * @return �Z���f�[�^
	 */
	public Object getRowValueAt(int row, Enum e) {
		TableDataModel model = getDataView();
		return model.getTableDataItem(row, indexs.get(e));
	}

	/**
	 * ���t�^�̃J�����C���f�b�N�X�w�� <br>
	 * YYYY/MM/DD�̌`����String�ϊ����A�\�������ꍇ�B
	 * 
	 * @param columns
	 */
	public void setDateStringColumn(int... columns) {
		for (int col : columns) {
			dateSortIndexList.add(col);
		}
	}

	/**
	 * Number�^�̃J�����C���f�b�N�X�w��<br>
	 * ������String�ɕϊ����A�\�������J�����̏ꍇ�B
	 * 
	 * @param columns
	 */
	public void setNumberStringColumn(int... columns) {
		for (int col : columns) {
			numberSortIndexList.add(col);
		}
	}

}
