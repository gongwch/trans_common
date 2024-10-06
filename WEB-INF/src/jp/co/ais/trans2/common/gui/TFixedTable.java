package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.file.*;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * �Œ��e�[�u���R���|�[�l���g
 * 
 * @author AIS
 */
public class TFixedTable extends TTable {

	/** �Œ��Table */
	public BaseTable fixedTable;

	/** �Œ��Table�̏�����Ctrl */
	public TFixedTableInitial ctrl;

	/** ���f�[�� */
	public TableModel tblModel;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param ctrl
	 */
	public TFixedTable(TFixedTableInitial ctrl) {
		super();

		this.ctrl = ctrl;
		init(this.ctrl.getFrozenCols());
	}

	/**
	 * ��������
	 */
	@Override
	public void init() {
		// �����Ȃ�
	}

	/**
	 * @return �s�w�b�_�[�pTable
	 */
	@Override
	protected BaseTable createRowHeaderTable() {
		return new BaseTable();
	}

	/**
	 * @return �s�ԍ��X�v���b�h
	 */
	public BaseTable getRowHeaderTable() {
		return (BaseTable) rowHeaderComp;
	}

	/**
	 * ��Œ�ݒ�
	 * 
	 * @param frozenCols
	 */
	public void init(int frozenCols) {
		// �ʏ�e�[�u���̐ݒ�

		// ScrollPane����
		this.setOpaque(false);
		this.getViewport().setOpaque(false);

		table = createBaseTable();
		setViewportTable(table);
		table.adapter = createTTableAdapter();
		adapter = table.adapter;
		adapter.setTable(table);

		table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		table.setAutoCreateRowSorter(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		table.setDefaultEditor(Object.class, null);
		table.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		table.setSurrendersFocusOnKeystroke(true);

		table.setRowHeight(rowHeight);

		// �s�ԍ�
		tblModel = table.getModel();
		rowHeaderComp = createBaseTable(tblModel);
		rowHeaderView = getRowHeaderView();

		this.setRowHeaderView(rowHeaderComp);
		getRowHeader().setOpaque(false); // ����

		// �Œ��e�[�u��
		fixedTable = getRowHeaderTable();

		fixedTable.adapter = table.adapter;
		fixedTable.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
		fixedTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
		fixedTable.setDefaultEditor(Object.class, null);
		fixedTable.putClientProperty("terminateEditOnFocusLost", Boolean.TRUE);
		fixedTable.setSurrendersFocusOnKeystroke(true);

		fixedTable.setRowHeight(rowHeight);

		// ��ݒ�
		ctrl.initSpread(this);

		// �Œ��e�[�u����������
		JTableHeader header = fixedTable.getTableHeader();
		TableCellRenderer hr = header.getDefaultRenderer();

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) fixedTable.getColumnModel();

		for (int i = 0; i < columnModel.getColumnCount(); i++) {

			columns.get(i).setColumn(columnModel.getColumn(i));

			int width = columns.get(i).getWidth();
			boolean visible = columns.get(i).isVisible();

			if (width < 0 || !visible) {
				columnModel.getColumn(i).setMinWidth(0);
				columnModel.getColumn(i).setMaxWidth(0);
				columnModel.getColumn(i).setPreferredWidth(0);

			} else {
				columnModel.getColumn(i).setMinWidth(0);
				columnModel.getColumn(i).setPreferredWidth(width);
			}

			TableColumn col = fixedTable.getColumnModel().getColumn(i);
			TTableComponent colComponent = columns.get(i).getComponent();

			// �^�C�g��
			if (colComponent != null && colComponent instanceof TTableColumnHeader) {
				// �`���[�g�̏ꍇ�A�w�b�_�[�͓��ʑΉ�
				TTableColumnHeader colHeader = (TTableColumnHeader) colComponent;
				TableCellRenderer renderer = createTableCellRenderer(colHeader);
				col.setHeaderRenderer(renderer);
			} else {
				HeaderRenderer headerRenderer = createHeaderRenderer(hr);
				headerRenderer.setVerticalAlign(columns.get(i).getVerticalAlign());
				col.setHeaderRenderer(headerRenderer);
			}

			// �Z��
			DefaultTableCellRenderer r = new DefaultTableCellRenderer();
			r.setHorizontalAlignment(columns.get(i).getHorizontalAlign());
			columnModel.getColumn(i).setCellRenderer(createCellRenderer(r));
			if (colComponent != null) {
				if (colComponent.getCellRenderer(this) != null) {
					fixedTable.getColumnModel().getColumn(i).setCellRenderer(colComponent.getCellRenderer(this));
				}
				if (colComponent.getCellEditor(this) != null) {
					fixedTable.getColumnModel().getColumn(i).setCellEditor(colComponent.getCellEditor(this));
				}
			}
		}

		// �Œ��ݒ�

		DefaultTableModel model = (DefaultTableModel) table.getModel();

		fixedTable.setSelectionModel(table.getSelectionModel());
		for (int i = model.getColumnCount() - 1; i >= 0; i--) {
			if (i < frozenCols) {
				table.removeColumn(table.getColumnModel().getColumn(i));
				fixedTable.getColumnModel().getColumn(i).setResizable(false);
			} else {
				fixedTable.removeColumn(fixedTable.getColumnModel().getColumn(i));
			}
		}

		// �X�N���[���ݒ�
		fixedTable.setPreferredScrollableViewportSize(fixedTable.getPreferredSize());
		setRowHeaderView(fixedTable);
		setCorner(ScrollPaneConstants.UPPER_LEFT_CORNER, fixedTable.getTableHeader());
		getViewport().setBackground(Color.WHITE);
		getRowHeader().setBackground(Color.WHITE);

		// �A������
		getRowHeader().addChangeListener(new ChangeListener() {

			@Override
			public void stateChanged(ChangeEvent e) {
				JViewport vp = (JViewport) e.getSource();
				getVerticalScrollBar().setValue(vp.getViewPosition().y);
			}
		});

		// �ʏ�e�[�u���̃C�x���g�ݒ�

		addKeyEvent();
		addKeyListener();
		addMouseListener();

		sorter = createTableRowSorter();

		table.setRowSorter(sorter);
		fixedTable.setRowSorter(sorter); // �Œ蕔�̃\�[�g����

		// �E�N���b�N���j���[������
		initPopupMenu();
	}

	/**
	 * �x�[�X�e�[�u��
	 * 
	 * @param model
	 * @return �x�[�X�e�[�u��
	 */
	protected BaseTable createBaseTable(TableModel model) {
		return new BaseTable(model);
	}

	@Override
	public void removeRow() {
		table.setRowSorter(null);
		fixedTable.setRowSorter(null);

		try {
			super.removeRow();
		} catch (IndexOutOfBoundsException ex) {
			// �G���[����
		}

		sorter = createTableRowSorter();

		table.setRowSorter(sorter);
		fixedTable.setRowSorter(sorter);
	}

	@Override
	public void removeRow(int row) {
		table.setRowSorter(null);
		fixedTable.setRowSorter(null);

		try {
			super.removeRow(row);
		} catch (IndexOutOfBoundsException ex) {
			// �G���[����
		}

		sorter = createTableRowSorter();

		table.setRowSorter(sorter);
		fixedTable.setRowSorter(sorter);
	}

	/**
	 * Mouse Right Clicked
	 * 
	 * @param e
	 */
	@Override
	protected void onMouseRightClicked(MouseEvent e) {
		// ����]���̉E�N���b�N
		// ��U����
	}

	/**
	 * TableHeader MouseClicked
	 * 
	 * @param e
	 */
	@Override
	protected void onTableHeaderMouseClicked(MouseEvent e) {
		// #51334 �\���ݒ�N���A�@�\�̑Ή��A�ꕔ�������
		// �������������͂܂���Q�����邽�߁A����
		// if (SwingUtilities.isRightMouseButton(e)) {
		// // ��U����
		// return;
		// }

		super.onTableHeaderMouseClicked(e);

		// �Œ��̃w�b�_�N���b�N����
		if (ctrl.getFrozenCols() > 0) {
			// �`�F�b�N�{�b�N�X�̃w�b�_�[���N���b�N�����ꍇ�͑S�I��
			int fixedTblCol = fixedTable.getTableHeader().columnAtPoint(e.getPoint());

			DefaultTableColumnModel columnModel = (DefaultTableColumnModel) fixedTable.getColumnModel();

			// �w�b�_�[���N���b�N�O
			adapter.beforeHeaderClicked(fixedTblCol);

			if (fixedTblCol >= 0 && columnModel.getColumn(fixedTblCol).getCellEditor() instanceof TCheckBoxEditor
				&& getRowCount() > 0 && allCheckWhenHeaderClicked) {

				// �ҏW���̏ꍇ�͈�U�m��
				if (fixedTable.isEditing()) {
					fixedTable.getCellEditor().stopCellEditing();
				}

				// 1�s�ڂ̃`�F�b�N��Ԃ𔽓]�B���̍s�����̏�Ԃɏ]���A�ݒ肷��
				boolean isSelected = getNextSwitchBoolean(fixedTable, fixedTblCol);

				for (int i = 0; i < getRowCount(); i++) {
					boolean isEditable = fixedTable.isCellEditable(i, fixedTblCol);

					TCheckBoxEditor editor = null;
					if (columnModel.getColumn(fixedTblCol).getCellEditor() instanceof TCheckBoxEditor) {
						editor = (TCheckBoxEditor) columnModel.getColumn(fixedTblCol).getCellEditor();
						isEditable &= editor.isCellEditable(i, fixedTblCol);
					}

					if (isEditable) {
						fixedTable.setValueAt(isSelected, fixedTable.convertRowIndexToModel(i), fixedTblCol);

						if (editor != null) {
							TCheckBox chk = (TCheckBox) editor.getComponent();
							chk.setSelected(isSelected);
							chk.setIndex(i);
							if (chk.getActionListeners() != null || chk.getActionListeners().length != 0) {
								for (int lc = 0; lc < chk.getActionListeners().length; lc++) {
									chk.getActionListeners()[lc].actionPerformed(null);
								}
							}
						}
					}
				}

				fixedTable.repaint();
			}

			// �w�b�_�[���N���b�N��
			adapter.afterHeaderClicked(fixedTblCol);
		}
	}

	/**
	 * �E�N���b�N���j���[������
	 */
	@Override
	protected void initPopupMenu() {

		// �E�N���b�N���j���[������
		popupMenu = new JPopupMenu();

		JMenuItem clear = new JMenuItem(popupWords[0]);
		clear.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				clearTableInformation();
			}
		});

		popupMenu.add(clear);

		// #51334 �\���ݒ�N���A�@�\�̑Ή��A�ꕔ�������
		// �������������͂܂���Q�����邽�߁A����
		// JMenuItem autosizeHeaderAndContents = new JMenuItem(popupWords[1]);
		// autosizeHeaderAndContents.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// // ����������
		// adjustTableInformation(AutoSizeType.HeaderAndContents);
		//
		// }
		// });
		//
		// popupMenu.add(autosizeHeaderAndContents);
		//
		// JMenuItem autosizeHeader = new JMenuItem(popupWords[2]);
		// autosizeHeader.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// // ����������
		// adjustTableInformation(AutoSizeType.HeaderOnly);
		//
		// }
		// });
		//
		// popupMenu.add(autosizeHeader);
		//
		// JMenuItem autosizeContents = new JMenuItem(popupWords[3]);
		// autosizeContents.addActionListener(new ActionListener() {
		//
		// @Override
		// public void actionPerformed(ActionEvent e) {
		//
		// // ����������
		// adjustTableInformation(AutoSizeType.ContentsOnly);
		//
		// }
		// });
		//
		// popupMenu.add(autosizeContents);
	}

	/**
	 * �e�[�u�������N���A
	 */
	@Override
	protected void clearTableInformation() {
		FileUtil.removeTempolaryFile(getTableKeyName() + ".table");

		// �񏇕���
		if (ctrl.getFrozenCols() > 0) {
			restoreColumns(initTableInformation, fixedTable);
		}
		restoreColumns(initTableInformation, table);

		// �񕝕���
		if (ctrl.getFrozenCols() > 0) {
			restoreWidth(initTableInformation, fixedTable);
		}
		restoreWidth(initTableInformation, table);

		// �e�[�u�����ۑ�
		saveTableInformation();

		if (ctrl.getFrozenCols() > 0) {
			fixedTable.repaint();
		}
		table.repaint();
	}

	// /**
	// * �e�[�u������������
	// *
	// * @param type �����������̃^�C�v
	// */
	// @Override
	// protected void adjustTableInformation(AutoSizeType type) {
	//
	// // �񏇕���
	// if (ctrl.getFrozenCols() > 0) {
	// restoreColumns(initTableInformation, fixedTable);
	// }
	// restoreColumns(initTableInformation, table);
	//
	// // ����������
	// if (ctrl.getFrozenCols() > 0) {
	// autosizeColumnWidth(false, type, fixedTable);
	// }
	// autosizeColumnWidth(false, type, table);
	//
	// // �e�[�u�����ۑ�
	// saveTableInformation();
	// }

	/**
	 * �J������Ԃ̕���
	 */
	@Override
	public void restoreComponent() {
		restoreComponent(getKey());
	}

	/**
	 * @param key
	 */
	@Override
	public void restoreComponent(TStorableKey key) {
		try {
			if (Util.isNullOrEmpty(tableKeyName) && key != null) {
				tableKeyName = key.getKey();
			}

			if (Util.isNullOrEmpty(tableKeyName)) {
				return;
			}

			TTableInformation tableInformation = getPreviousTableInformation();

			if (tableInformation == null) {
				return;
			}

			// �񏇕���
			if (ctrl.getFrozenCols() > 0) {
				restoreColumns(initTableInformation, fixedTable);
			}
			restoreColumns(initTableInformation, table);

			// �񕝕���
			if (ctrl.getFrozenCols() > 0) {
				restoreWidth(initTableInformation, fixedTable);
			}
			restoreWidth(initTableInformation, table);

			if (ctrl.getFrozenCols() > 0) {
				fixedTable.repaint();
			}
			table.repaint();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * �J�����ʒu����
	 * 
	 * @param tableInformation �e�[�u�����
	 * @param tbl
	 */
	protected void restoreColumns(TTableInformation tableInformation, JTable tbl) {

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) tbl.getColumnModel();
		int columnCount = columnModel.getColumnCount();

		List<Integer> dispOrderList = tableInformation.getDisplayOrder();
		int addon = 0;

		if (ctrl.getFrozenCols() > 0 && tbl.equals(table)) {
			// �E�e�[�u��
			addon = ctrl.getFrozenCols();
		}

		if (dispOrderList != null && !dispOrderList.isEmpty() && dispOrderList.size() - addon >= columnCount) {
			for (int i = 0; i < columnCount; i++) {
				int to = i;
				int index = i + addon;

				int displayNo = dispOrderList.get(index);

				// ���ݒn����
				int j = 0;
				now: for (; j < columnCount; j++) {
					if (columnModel.getColumn(j).getModelIndex() == displayNo) {
						break now;
					}
				}

				tbl.moveColumn(j, to);
			}
		}
	}

	/**
	 * �񕝕���
	 * 
	 * @param tableInformation �e�[�u�����
	 * @param tbl
	 */
	public void restoreWidth(TTableInformation tableInformation, JTable tbl) {

		DefaultTableColumnModel columnModel = (DefaultTableColumnModel) tbl.getColumnModel();
		int columnCount = columnModel.getColumnCount();

		List<Integer> widthList = tableInformation.getWidthList();
		int addon = 0;

		if (ctrl.getFrozenCols() > 0 && tbl.equals(table)) {
			// �E�e�[�u��
			addon = ctrl.getFrozenCols();
		}

		if (widthList != null && !widthList.isEmpty() && widthList.size() + addon >= columnCount) {

			for (int i = 0; i < columnCount; i++) {
				int index = i + addon;

				int width = widthList.get(index);

				if (width < 0 || !columns.get(index).isVisible()) {
					columnModel.getColumn(i).setMinWidth(0);
					columnModel.getColumn(i).setMaxWidth(0);
					columnModel.getColumn(i).setPreferredWidth(0);

				} else {
					columnModel.getColumn(i).setMinWidth(0);
					columnModel.getColumn(i).setPreferredWidth(width);
				}

				if (!columns.get(index).isVisible()) {
					columns.get(index).setWidth(width);
				}
			}
		}
	}

	// /**
	// * �񕝎����v�Z<br>
	// *
	// * @param isZeroAdjust <br>
	// * true:�����[���̏ꍇ�A�����v�Z���� false:�����[���̏ꍇ�A�����v�Z���Ȃ�
	// * @param type �����������̃^�C�v
	// * @param tbl
	// */
	// public void autosizeColumnWidth(boolean isZeroAdjust, AutoSizeType type, JTable tbl) {
	//
	// DefaultTableColumnModel columnModel = (DefaultTableColumnModel) tbl.getColumnModel();
	// int columnCount = columnModel.getColumnCount();
	//
	// List<Integer> widthList = initTableInformation.getWidthList();
	// int addon = 0;
	//
	// if (ctrl.getFrozenCols() > 0 && tbl.equals(table)) {
	// // �E�e�[�u��
	// addon = ctrl.getFrozenCols();
	// }
	//
	// if (widthList != null && !widthList.isEmpty() && widthList.size() >= columnCount) {
	//
	// for (int i = 0; i < columnCount; i++) {
	//
	// int index = i + addon;
	//
	// int width = widthList.get(index);
	//
	// if (width < 0 || !columns.get(index).isVisible()) {
	// columnModel.getColumn(i).setMinWidth(0);
	// columnModel.getColumn(i).setMaxWidth(0);
	// columnModel.getColumn(i).setPreferredWidth(0);
	//
	// } else {
	// columnModel.getColumn(i).setMinWidth(0);
	//
	// if (isZeroAdjust || width != 0) {
	// // �񕝎����v�Z(useTitle=true�̏ꍇ�A��^�C�g���̍ő啝��)
	// width = getAdjustColumnWidth(columnModel.getColumn(i), width, type, index, tbl);
	// }
	// columnModel.getColumn(i).setPreferredWidth(width);
	// }
	//
	// if (!columns.get(index).isVisible()) {
	// columns.get(index).setWidth(width);
	// }
	// }
	// }
	// }
	//
	// /**
	// * �񕝎����v�Z�l�̎擾
	// *
	// * @param column ��
	// * @param colWidth �ݒ��
	// * @param type �����������̃^�C�v
	// * @param colIndex �w���C���f�b�N�X
	// * @param tbl
	// * @return ��
	// */
	// public int getAdjustColumnWidth(TableColumn column, int colWidth, AutoSizeType type, int colIndex, JTable tbl) {
	//
	// if (fm == null) {
	// fm = tbl.getFontMetrics(tbl.getFont());
	// }
	//
	// String title = null;
	// if (AutoSizeType.HeaderAdjust.equals(type)) {
	// // �p�ꃆ�[�U�̏ꍇ�A�񕝎����v�Z
	//
	// String header = Util.avoidNullNT(column.getHeaderValue());
	// header = header.replaceAll("\\<html\\>", "");
	// header = header.replaceAll("\\</html\\>", "");
	// header = header.replaceAll("\\<center\\>", "");
	// header = header.replaceAll("\\</center\\>", "");
	// header = header.replaceAll("\\<br\\>", "");
	//
	// title = header;
	// int width = getWidthWithHtml(title);
	//
	// // ����Ȃ��ꍇ�v�Z�l��߂�
	// return Math.max(colWidth, width);
	//
	// } else {
	// int width = getMaxWidth(column, colIndex, type, tbl);
	// return width;
	//
	// }
	//
	// }
	//
	// /**
	// * �w���̍ő啶����̎擾
	// *
	// * @param column �w���I�u�W�F�N�g
	// * @param col �w���C���f�b�N�X
	// * @param type �����������̃^�C�v
	// * @param tbl
	// * @return �w���̍ő啶����
	// */
	// public int getMaxWidth(TableColumn column, int col, AutoSizeType type, JTable tbl) {
	//
	// int addon = 0;
	//
	// if (ctrl.getFrozenCols() > 0 && tbl.equals(table)) {
	// // �E�e�[�u��
	// addon = ctrl.getFrozenCols();
	// }
	//
	// int colIndex = tbl.convertColumnIndexToModel(col - addon);// ����C���f�b�N�X
	// int addonWidth = fm.stringWidth("W"); // �f�t�H���g��
	//
	// String value = "";
	// if (AutoSizeType.HeaderAndContents.equals(type) || AutoSizeType.HeaderOnly.equals(type)) {
	// // �w�b�_�[�܂񂾏ꍇ�̂�
	// value = Util.avoidNullNT(column.getHeaderValue());
	// }
	//
	// int valueWidth = getWidthWithHtml(value);
	//
	// if (AutoSizeType.HeaderOnly.equals(type)) {
	// // �w�b�_�[�݂̂̏ꍇ�A�߂�(�l�͌v�Z���Ȃ�)
	// return addonWidth + valueWidth;
	// }
	//
	// for (int i = 0; i < getRowCount(); i++) {
	//
	// // �R���|���ǂ�������i�ݒ�l��String�ł͂Ȃ��ꍇ�A�ύX�Ȃ��j
	// if (getCellEditor(i, colIndex) instanceof TBaseCellEditor
	// || getCellEditor(i, colIndex) instanceof TableCellRenderer
	// || !(getRowValueAt(i, colIndex) instanceof String)) {
	// return initTableInformation.getWidthList().get(colIndex);
	// }
	//
	// // �l
	// String cellValue = Util.avoidNullNT(getRowValueAt(i, colIndex));
	// int cellWidth = getWidthWithHtml(cellValue);
	//
	// // ��Ԓ������擾
	// if (valueWidth < cellWidth) {
	// valueWidth = cellWidth;
	// }
	// }
	//
	// return addonWidth + valueWidth;
	// }

	/**
	 * �e�[�u�����ۑ��̂��߃}�E�X�C�x���g�ǉ�
	 */
	@Override
	protected void addMouseListener() {
		super.addMouseListener();

		// �e�[�u���w�b�_���X�i�[�o�^
		fixedTable.getTableHeader().addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {

				onTableHeaderMouseClicked(e);
			}

			@Override
			public void mousePressed(MouseEvent e) {
				if (fixedTable.isEditing()) {
					fixedTable.getCellEditor().stopCellEditing();
				}
			}

			@Override
			public void mouseReleased(MouseEvent e) {
				try {
					// �e�[�u�����ۑ�
					saveTableInformation();

				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		});
	}
}
