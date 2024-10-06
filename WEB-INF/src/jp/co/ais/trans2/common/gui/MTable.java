package jp.co.ais.trans2.common.gui;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.math.*;
import java.util.List;

import javax.swing.*;
import javax.swing.event.*;
import javax.swing.table.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable.*;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * �}���`Table
 * 
 * @param <T>
 */
public class MTable<T extends TNumericField> extends TPanel implements TStorable {

	/** �e�[�u�� */
	public TTable tbl;

	/** �e�[�u���{�� */
	public BaseTable table;

	/** �t�b�^�[ */
	public MFooter footer;

	/** GC:�e�[�u�� */
	protected GridBagConstraints gcTable;

	/** GC:�t�b�^�[ */
	protected GridBagConstraints gcFooter;

	/** �t�B�[���hClass */
	protected Class<T> fieldClass;

	/** �����t�B�[���hClass */
	protected Class<? extends TTextField> textClass;

	/** �������t�t�B�[���hClass */
	protected Class<? extends THalfwayDateField> searchDateClass;

	/** ���t�t�B�[���hClass */
	protected Class<? extends TCalendar> calClass;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param clazz
	 */
	public MTable(Class clazz) {
		this(clazz, null, null, null);
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param fieldClass
	 * @param textClass
	 * @param searchDateClass
	 * @param calClass
	 */
	public MTable(Class fieldClass, Class textClass, Class searchDateClass, Class calClass) {
		this.fieldClass = fieldClass;
		this.textClass = textClass;
		this.searchDateClass = searchDateClass;
		this.calClass = calClass;

		init();
	}

	/**
	 * ��������
	 */
	public void init() {

		tbl = createBaseTable();
		table = tbl.table;
		footer = createFooter();

		TGuiUtil.setComponentHeight(footer, 20);

		setLayout(new GridBagLayout());

		gcTable = new GridBagConstraints();
		gcTable.anchor = GridBagConstraints.NORTHWEST;
		gcTable.fill = GridBagConstraints.BOTH;
		gcTable.gridx = 0;
		gcTable.gridy = 0;
		gcTable.weightx = 1.0d;
		gcTable.weighty = 1.0d;
		add(tbl, gcTable);

		gcFooter = new GridBagConstraints();
		gcFooter.anchor = GridBagConstraints.NORTHWEST;
		gcFooter.fill = GridBagConstraints.BOTH;
		gcFooter.gridx = 0;
		gcFooter.gridy = 1;
		gcFooter.weightx = 1.0d;
		add(footer, gcFooter);
	}

	/**
	 * �x�[�X�e�[�u��
	 * 
	 * @return �x�[�X�e�[�u��
	 */
	protected TTable createBaseTable() {
		return new TTable() {

			/**
			 * �e�[�u����ύX
			 */
			@Override
			protected void fireTableColumnChanged() {
				footer.resizeComponents();

				// ��ύX����
				adapter.fireTableColumnChanged();
			}

		};
	}

	/**
	 * �t�b�^�[��ݒ�<br>
	 * �ŏ���1:LABEL��ݒ肷��K�v
	 * 
	 * @param cols �S�ė�
	 * @param types 0:BLANK:�󗓁A1:LABEL:���x���\���p�A2:SUM:���v��
	 */
	public void setFooterColumns(Enum[] cols, int[] types) {
		footer.setColumns(cols, types);
		footer.init();
	}

	/**
	 * @param col
	 * @return �t�b�^�[���v�t�B�[���h
	 */
	public T getFooterCompAt(Enum col) {
		return getFooterCompAt(col.ordinal());
	}

	/**
	 * @param index
	 * @return �t�b�^�[���v�t�B�[���h
	 */
	public T getFooterCompAt(int index) {
		return (T) footer.compList.get(index);
	}

	/**
	 * @param col
	 * @return �t�b�^�[�e�L�X�g�t�B�[���h
	 */
	public TTextField getFooterTextAt(Enum col) {
		return getFooterTextAt(col.ordinal());
	}

	/**
	 * @param index
	 * @return �t�b�^�[�e�L�X�g�t�B�[���h
	 */
	public TTextField getFooterTextAt(int index) {
		return (TTextField) footer.compList.get(index);
	}

	/**
	 * @param col
	 * @return �t�b�^�[���t�t�B�[���h
	 */
	public TCalendar getFooterCalendarAt(Enum col) {
		return getFooterCalendarAt(col.ordinal());
	}

	/**
	 * @param index
	 * @return �t�b�^�[���t�t�B�[���h
	 */
	public TCalendar getFooterCalendarAt(int index) {
		return (TCalendar) footer.compList.get(index);
	}

	/**
	 * @param col
	 * @return �t�b�^�[�������t�t�B�[���h
	 */
	public THalfwayDateField getFooterSearchDateAt(Enum col) {
		return getFooterSearchDateAt(col.ordinal());
	}

	/**
	 * @param index
	 * @return �t�b�^�[�������t�t�B�[���h
	 */
	public THalfwayDateField getFooterSearchDateAt(int index) {
		return (THalfwayDateField) footer.compList.get(index);
	}

	/**
	 * �t�b�^�[������ <br>
	 * <br>
	 * <b>��ݒ�(setFooterColumns)���ɂ���K�v<b>
	 */
	public void initFooter() {
		footer.init();
	}

	/**
	 * �t�b�^�[
	 * 
	 * @return �t�b�^�[
	 */
	protected MFooter createFooter() {
		return new MFooter(tbl, fieldClass, textClass, searchDateClass, calClass);
	}

	/**
	 * @return JTable�Ƃ�Adapter
	 */
	protected TTableAdapter createTTableAdapter() {
		return tbl.createTTableAdapter();
	}

	/**
	 * @return �s�w�b�_�[�pTable
	 */
	protected JComponent createRowHeaderTable() {
		return tbl.createRowHeaderTable();
	}

	/**
	 * @return �\�[�^�[
	 */
	protected TableRowSorter<TableModel> createTableRowSorter() {
		return tbl.createTableRowSorter();
	}

	/**
	 * �\�[�g�@�\���O��
	 */
	public void setSortOff() {
		tbl.setSortOff();
	}

	/**
	 * true:TAB�͊O�Ɉړ����Ȃ��Afalse:�S��ʈړ��̎擾
	 * 
	 * @return tabTraverseCell true:TAB�͊O�Ɉړ����Ȃ��Afalse:�S��ʈړ�
	 */
	public boolean isTabTraverseCell() {
		return tbl.isTabTraverseCell();
	}

	/**
	 * true:TAB�͊O�Ɉړ����Ȃ��Afalse:�S��ʈړ��̐ݒ�
	 * 
	 * @param tabTraverseCell true:TAB�͊O�Ɉړ����Ȃ��Afalse:�S��ʈړ�
	 */
	public void setTabTraverseCell(boolean tabTraverseCell) {
		tbl.setTabTraverseCell(tabTraverseCell);
	}

	/**
	 * TAB�L�[�Ŏ��̃Z���Ɉړ�����悤�ɐݒ肷��.
	 */
	public void setTabTraverseCell() {
		setTabTraverseCell(true);
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
			addColumn(
				new TTableColumn(e, title, width, instance.getDefaultCellRendererHorizontalAlignment(), instance));
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

			addColumn(
				new TTableColumn(e, title, width, component.getDefaultCellRendererHorizontalAlignment(), component));
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
		tbl.addColumn(column);

		// TODO:�t�b�^�[
		// footer.addColumn(column);
	}

	/**
	 * �w�b�_�[�\���̍쐬
	 * 
	 * @param colHeader
	 * @return �w�b�_�[�\��
	 */
	protected TableCellRenderer createTableCellRenderer(TTableColumnHeader colHeader) {
		return tbl.createTableCellRenderer(colHeader);
	}

	/**
	 * ��^�C�g���̎擾
	 * 
	 * @param column
	 * @param lang
	 * @return ��^�C�g��
	 */
	protected String getColumnTitle(TTableColumn column, String lang) {
		return tbl.getColumnTitle(column, lang);
	}

	/**
	 * @param hr
	 * @return �e�[�u���w�b�_�[�̃Z�������_��
	 */
	protected HeaderRenderer createHeaderRenderer(TableCellRenderer hr) {
		return tbl.createHeaderRenderer(hr);
	}

	/**
	 * @param r
	 * @return �Z�������_��
	 */
	protected CellRenderer createCellRenderer(TableCellRenderer r) {
		return tbl.createCellRenderer(r);
	}

	/**
	 * Enum�ɂ�����TableColumn�I�u�W�F�N�g�� �Ԃ��܂��B
	 * 
	 * @param e �v�������
	 * @return TableColumn
	 */
	public TableColumn getColumn(Enum e) {
		return tbl.getColumn(e);
	}

	/**
	 * �e�[�u���𕜌�����
	 */
	public void restoreTable() {
		tbl.restoreTable();
	}

	/**
	 * �s�ǉ� List��ǉ�
	 * 
	 * @param list �f�[�^���X�g
	 */
	public void addRow(List list) {
		tbl.addRow(list);
	}

	/**
	 * �s�ǉ� Object�^�̔z���ǉ�
	 * 
	 * @param data �f�[�^
	 */
	public void addRow(Object[] data) {
		tbl.addRow(data);
	}

	/**
	 * �I������Ă���s���C������
	 * 
	 * @param list �f�[�^���X�g
	 */
	public void modifySelectedRow(List list) {
		tbl.modifySelectedRow(list);
	}

	/**
	 * �I������Ă���s���C������
	 * 
	 * @param data �C���f�[�^
	 */
	public void modifySelectedRow(Object[] data) {
		tbl.modifySelectedRow(data);
	}

	/**
	 * �w��s���C������
	 * 
	 * @param row �s
	 * @param list �f�[�^���X�g
	 */
	public void modifyRow(int row, List list) {
		tbl.modifyRow(row, list);
	}

	/**
	 * �w��s���C������
	 * 
	 * @param row �s
	 * @param data �C���f�[�^
	 */
	public void modifyRow(int row, Object[] data) {
		tbl.modifyRow(row, data);
	}

	/**
	 * �w��̍s���폜����
	 * 
	 * @param row �s�폜
	 */
	public void removeRow(int row) {
		tbl.removeRow(row);
	}

	/**
	 * �I������Ă���s���폜����
	 */
	public void removeSelectedRow() {
		tbl.removeSelectedRow();
	}

	/**
	 * �I������Ă���s���폜����(�����s)
	 */
	public void removeSelectedRows() {
		tbl.removeSelectedRows();
	}

	/**
	 * �I������Ă���s���폜����(�����s)
	 * 
	 * @param rows �I�𒆂̍s�ԍ�
	 */
	public void removeRows(int[] rows) {
		tbl.removeRows(rows);
	}

	/**
	 * �S�Ă̍s���폜����
	 */
	public void removeRow() {
		tbl.removeRow();
	}

	/**
	 * �e�[�u������Ԃ��B
	 * 
	 * @return �e�[�u�����
	 */
	public TTableInformation getTableInformation() {
		return tbl.getTableInformation();
	}

	/**
	 * �O��ۑ������e�[�u������Ԃ��B
	 * 
	 * @return �O��ۑ������e�[�u�����
	 */
	public TTableInformation getPreviousTableInformation() {
		return tbl.getPreviousTableInformation();
	}

	/**
	 * �e�[�u������ۑ�����
	 */
	public void saveTableInformation() {
		tbl.saveTableInformation();
	}

	/**
	 * �w��̃e�[�u������ۑ�����
	 * 
	 * @param tblInformation �e�[�u�����
	 */
	protected void saveTableInformation(TTableInformation tblInformation) {
		tbl.saveTableInformation(tblInformation);
	}

	/**
	 * �e�[�u�������N���A
	 */
	protected void clearTableInformation() {
		tbl.clearTableInformation();
	}

	/**
	 * �e�[�u������������
	 * 
	 * @param type �����������̃^�C�v
	 */
	protected void adjustTableInformation(AutoSizeType type) {
		tbl.adjustTableInformation(type);
	}

	/**
	 * @return tblKeyName��߂��܂��B
	 */
	public String getTableKeyName() {
		return tbl.getTableKeyName();
	}

	/**
	 * @param tblKeyName tblKeyName��ݒ肵�܂��B
	 */
	public void setTableKeyName(String tblKeyName) {
		tbl.setTableKeyName(tblKeyName);
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * �\�[�g������Ă����ꍇ�A�\�[�g��̍s�ԍ��Ŏ擾����
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�
	 * @return �w��s�̎w��J�����̒l
	 */
	public BigDecimal getDecimalValueAt(int row, Enum column) {
		return tbl.getDecimalValueAt(row, column);
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * �\�[�g������Ă����ꍇ�A�\�[�g��̍s�ԍ��Ŏ擾����
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	public BigDecimal getDecimalValueAt(int row, int column) {
		return tbl.getDecimalValueAt(row, column);
	}

	/**
	 * �w��s�̎w��J�����̒l��Ԃ��܂��B<BR>
	 * �\�[�g������Ă����ꍇ�A�\�[�g��̍s�ԍ��Ŏ擾����
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	public Object getValueAt(int row, Enum column) {
		return tbl.getValueAt(row, column);
	}

	/**
	 * �w��s�̎w��J�����̒l��Ԃ��܂��B<BR>
	 * �\�[�g������Ă����ꍇ�A�\�[�g��̍s�ԍ��Ŏ擾����
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	public Object getValueAt(int row, int column) {
		return tbl.getValueAt(row, column);
	}

	/**
	 * �I������Ă���s�̎w��J�����̒l��Ԃ��܂��B
	 * 
	 * @param column �l����肽���񎯕�enum
	 * @return �I������Ă���s�̎w��J�����̒l
	 */
	public Object getSelectedRowValueAt(Enum column) {
		return tbl.getSelectedRowValueAt(column);
	}

	/**
	 * �w��s�̎w��J�����̒l��Ԃ��܂��B
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	public Object getRowValueAt(int row, Enum column) {
		return tbl.getRowValueAt(row, column);
	}

	/**
	 * �w��s�̎w��J�����̒l��Ԃ��܂��B
	 * 
	 * @param row �s�ԍ�
	 * @param column ��ԍ�
	 * @return �w��s�̎w��J�����̒l
	 */
	public Object getRowValueAt(int row, int column) {
		return tbl.getRowValueAt(row, column);
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * �\�[�g������Ă����ꍇ�A�\�[�g��̍s�ԍ��Ŏ擾����<BR>
	 * ���\�[�g�A�����ւ��Ή�
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�
	 * @return �w��s�̎w��J�����̒l
	 */
	public BigDecimal getRowDecimalValueAt(int row, Enum column) {
		return tbl.getRowDecimalValueAt(row, column);
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * �\�[�g������Ă����ꍇ�A�\�[�g��̍s�ԍ��Ŏ擾����<BR>
	 * ���\�[�g�A�����ւ��Ή�
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	public BigDecimal getRowDecimalValueAt(int row, int column) {
		return tbl.getRowDecimalValueAt(row, column);
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * TableModel�<br>
	 * <b>�\�[�g��ɐ������擾�ł��Ȃ��s�����</b>
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�
	 * @return �w��s�̎w��J�����̒l
	 */
	@Deprecated
	public BigDecimal getModelDecimalValueAt(int row, Enum column) {
		return tbl.getModelDecimalValueAt(row, column);
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * TableModel�<br>
	 * <b>�\�[�g��ɐ������擾�ł��Ȃ��s�����</b>
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	@Deprecated
	public BigDecimal getModelDecimalValueAt(int row, int column) {
		return tbl.getModelDecimalValueAt(row, column);
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * TableModel�<br>
	 * <b>�\�[�g��ɐ������擾�ł��Ȃ��s�����</b>
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�
	 * @return �w��s�̎w��J�����̒l
	 */
	@Deprecated
	public Object getModelComboBoxValueAt(int row, Enum column) {
		return tbl.getModelComboBoxValueAt(row, column);
	}

	/**
	 * �w��s�̎w��J�����̒l��BigDecimal�ŕԂ��܂��B<BR>
	 * TableModel�<br>
	 * <b>�\�[�g��ɐ������擾�ł��Ȃ��s�����</b>
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	@Deprecated
	public Object getModelComboBoxValueAt(int row, int column) {
		return tbl.getModelComboBoxValueAt(row, column);
	}

	/**
	 * �w��s�̎w��J�����̒l��Ԃ��܂��B<BR>
	 * TableModel�<br>
	 * <b>�\�[�g��ɐ������擾�ł��Ȃ��s�����</b>
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	@Deprecated
	public Object getModelValueAt(int row, Enum column) {
		return tbl.getModelValueAt(row, column);
	}

	/**
	 * �w��s�̎w��J�����̒l��Ԃ��܂��B<BR>
	 * TableModel�<br>
	 * <b>�\�[�g��ɐ������擾�ł��Ȃ��s�����</b>
	 * 
	 * @param row �s�ԍ�
	 * @param column �l����肽���񎯕�enum
	 * @return �w��s�̎w��J�����̒l
	 */
	@Deprecated
	public Object getModelValueAt(int row, int column) {
		return tbl.getModelValueAt(row, column);
	}

	/**
	 * �X�v���b�h�V�[�g�̃_�u���N���b�N�C�x���g�ƃ{�^���C�x���g�̕R�t��
	 * 
	 * @param btn
	 */
	public void addSpreadSheetSelectChange(TButton btn) {
		tbl.addSpreadSheetSelectChange(btn);
	}

	/**
	 * �e�[�u���̍s���ړ�����B(�����s�ړ�)<br>
	 * start����end �܂ł�1�s�܂��͕����s���Ato�̈ʒu�Ɉړ�
	 * 
	 * @param start start����end �܂ł�1�s�܂��͕����s�����ւ�
	 * @param end start����end �܂ł�1�s�܂��͕����s�����ւ�
	 * @param to �ړ���̗�ԍ�
	 */
	public void moveRow(int start, int end, int to) {
		tbl.moveRow(start, end, to);
	}

	/**
	 * �e�[�u���̍s���ړ�����B
	 * 
	 * @param row �ړ��Ώۍs
	 * @param to �ړ���̗�ԍ�
	 */
	public void moveRow(int row, int to) {
		tbl.moveRow(row, to);
	}

	/**
	 * �w�b�_�[�̍�����ݒ肷��
	 * 
	 * @param height
	 */
	public void setHeaderRowHeight(int height) {
		tbl.setHeaderRowHeight(height);
	}

	/**
	 * �ΏۃZ����I������
	 * 
	 * @param index1
	 * @param index2
	 */
	public void setRowSelectionInterval(int index1, int index2) {
		tbl.setRowSelectionInterval(index1, index2);
	}

	/**
	 * �ΏۃZ����ǉ��I������
	 * 
	 * @param index1
	 * @param index2
	 */
	public void addRowSelectionInterval(int index1, int index2) {
		tbl.addRowSelectionInterval(index1, index2);
	}

	/**
	 * �ΏۃZ���̑I������������
	 * 
	 * @param index1
	 * @param index2
	 */
	public void removeRowSelectionInterval(int index1, int index2) {
		tbl.removeRowSelectionInterval(index1, index2);
	}

	/**
	 * ALL�I��
	 */
	public void selectAll() {
		tbl.selectAll();
	}

	/**
	 * �Ώۍs��I������
	 * 
	 * @param rowIndex �s�ԍ�
	 */
	public void setRowSelection(int rowIndex) {
		tbl.setRowSelection(rowIndex);
	}

	/**
	 * �Ώۍs��ǉ��I������
	 * 
	 * @param rowIndex �s�ԍ�
	 */
	public void addRowSelection(int rowIndex) {
		tbl.addRowSelection(rowIndex);
	}

	/**
	 * �Ώۍs�̑I������������
	 * 
	 * @param rowIndex �s�ԍ�
	 */
	public void removeRowSelection(int rowIndex) {
		tbl.removeRowSelection(rowIndex);
	}

	/**
	 * �\������Ă���s�����擾����
	 * 
	 * @return �s��
	 */
	public int getRowCount() {
		return tbl.getRowCount();
	}

	/**
	 * �s����Ԃ�(��\���s���܂�)
	 * 
	 * @return �s��
	 */
	public int getModelRowCount() {
		return tbl.getModelRowCount();
	}

	/**
	 * �s�̍���
	 * 
	 * @param height ����
	 */
	public void setRowHeight(int height) {
		tbl.setRowHeight(height);
	}

	/**
	 * �s�J�����̕�
	 * 
	 * @param width ��
	 */
	public void setRowColumnWidth(int width) {
		tbl.setRowColumnWidth(width);

		// gcFooter.insets.left = width;
		// add(footer, gcFooter);
		// TODO

		repaint();
	}

	/**
	 * �s�J�����̕�getter
	 * 
	 * @return �s�J�����̕�
	 */
	public int getRowColumnWidth() {
		return tbl.getRowColumnWidth();
	}

	/**
	 * �w�肳���Enum�̗񕝂̐ݒ�
	 * 
	 * @param e �w�肳���Enum
	 * @param width ��
	 */
	public void setColumnWidth(Enum e, int width) {
		tbl.setColumnWidth(e, width);
	}

	/**
	 * �w�肳���Enum�̗񕝂̎擾
	 * 
	 * @param e �w�肳���Enum
	 * @return PreferredWidth ��
	 */
	public int getColumnWidth(Enum e) {
		return tbl.getColumnWidth(e);
	}

	/**
	 * @param model
	 */
	public void setSelectionMode(int model) {
		tbl.setSelectionMode(model);
	}

	/**
	 * �I���s���擾����
	 * 
	 * @return �I���s
	 */
	public int getSelectedRow() {
		return tbl.getSelectedRow();
	}

	/**
	 * �I���s���擾����
	 * 
	 * @return �I���s
	 */
	public int[] getSelectedRows() {
		return tbl.getSelectedRows();
	}

	/**
	 * TableHeader���擾����
	 * 
	 * @return �w�b�_�[
	 */
	public JTableHeader getTableHeader() {
		return tbl.getTableHeader();
	}

	/**
	 * row��label�ɍ��Ԃ�U�邩�I�� ���f�t�H���g�ݒ�͍��Ԃ���
	 * 
	 * @param isView ���Ԃ���Ftrue /���ԂȂ��Ffalse
	 */
	public void setRowLabelNumber(boolean isView) {
		tbl.setRowLabelNumber(isView);
	}

	/**
	 * ColumnModel���擾����
	 * 
	 * @return ColumnModel
	 */
	public TableColumnModel getColumnModel() {
		return tbl.getColumnModel();
	}

	/**
	 * CellEditor���擾����
	 * 
	 * @return CellEditor
	 */
	public TableCellEditor getCellEditor() {
		return tbl.getCellEditor();
	}

	/**
	 * �t�H�[�J�X��
	 * 
	 * @return �t�H�[�J�X��
	 */
	public int getTabControlNo() {
		return tbl.getTabControlNo();
	}

	/**
	 * @param no
	 */
	public void setTabControlNo(int no) {
		tbl.setTabControlNo(no);
	}

	/**
	 * �t�H�[�J�X����
	 * 
	 * @return true:�t�H�[�J�X��
	 */
	public boolean isTabEnabled() {
		return tbl.isTabEnabled();
	}

	/**
	 * @param bool
	 */
	public void setTabEnabled(boolean bool) {
		tbl.setTabEnabled(bool);
	}

	@Override
	public void requestFocus() {
		tbl.requestFocus();
	}

	@Override
	public void setFocusTraversalPolicy(FocusTraversalPolicy policy) {
		tbl.setFocusTraversalPolicy(policy);
	}

	@Override
	public void transferFocus() {
		tbl.transferFocus();
	}

	@Override
	public void transferFocusBackward() {
		tbl.transferFocusBackward();
	}

	@Override
	public boolean requestFocusInWindow() {
		return tbl.requestFocusInWindow();
	}

	/**
	 * �����ւ��\���ǂ���
	 * 
	 * @param bln true:�\�Afalse:�Ȃ�
	 */
	public void setReorderingAllowed(boolean bln) {
		tbl.setReorderingAllowed(bln);
	}

	/**
	 * �s�I���ł��邩�ǂ���
	 * 
	 * @param bln true:����Afalse:�Ȃ�
	 */
	public void setRowSelectionAllowed(boolean bln) {
		tbl.setRowSelectionAllowed(bln);
	}

	/**
	 * �w�i�F���擾����
	 * 
	 * @param row
	 * @param isSelected
	 * @param hasFocus
	 * @return �w�i�F
	 */
	public Color getBackgroundColor(int row, boolean isSelected, boolean hasFocus) {
		return tbl.getBackgroundColor(row, isSelected, hasFocus);
	}

	/**
	 * �t�H���g�J���[���擾����
	 * 
	 * @param isSelected
	 * @param hasFocus
	 * @return �t�H���g�J���[
	 */
	public Color getCellFontColor(boolean isSelected, boolean hasFocus) {
		return tbl.getCellFontColor(isSelected, hasFocus);
	}

	/**
	 * �s��I��1�̔w�i�F���擾����
	 * 
	 * @return �s��I��1�̔w�i�F
	 */
	public Color getNotSelectedColor() {
		return tbl.getNotSelectedColor();
	}

	/**
	 * �s��I��2�̔w�i�F���擾����
	 * 
	 * @return �s��I��2�̔w�i�F
	 */
	public Color getNotSelectedColor2() {
		return tbl.getNotSelectedColor2();
	}

	/**
	 * �s�I�����̔w�i�F���擾����
	 * 
	 * @return �s�I�����̔w�i�F
	 */
	public Color getSelectedColor() {
		return tbl.getSelectedColor();
	}

	/**
	 * �s�I�����̃t�H���g�J���[���擾����
	 * 
	 * @return �s�I�����̔w�i�F
	 */
	public Color getSelectedFontColor() {
		return tbl.getSelectedFontColor();
	}

	/**
	 * ListSelectionModel���擾����
	 * 
	 * @return ListSelectionModel
	 */
	public ListSelectionModel getSelectionModel() {
		return tbl.getSelectionModel();
	}

	/**
	 * ���I����Ԃɂ���
	 */
	public void clearSelection() {
		tbl.clearSelection();
	}

	/**
	 * ListSelectionListener��ݒ�
	 * 
	 * @param listener ListSelectionListener
	 */
	public void addListSelectionListener(ListSelectionListener listener) {
		tbl.addListSelectionListener(listener);
	}

	/**
	 * rowHeight���擾����B
	 * 
	 * @return int rowHeight
	 */
	public int getRowHeight() {
		return tbl.getRowHeight();
	}

	/**
	 * Enter�L�[�Ń{�^�����s���s�����ǂ���
	 * 
	 * @param isEnterToButton true:�{�^�����s���s��(�A��������)
	 */
	public void setEnterToButton(final boolean isEnterToButton) {
		tbl.setEnterToButton(isEnterToButton);
	}

	/**
	 * �t�H�[�J�X���󂯓���邩�ǂ���
	 * 
	 * @param isFocusable
	 */
	@Override
	public void setFocusable(boolean isFocusable) {
		tbl.setFocusable(isFocusable);
	}

	/**
	 * �Z���I���������邩�ǂ���
	 * 
	 * @param cellSelectionEnabled
	 */
	public void setCellSelectionEnabled(boolean cellSelectionEnabled) {
		tbl.setCellSelectionEnabled(cellSelectionEnabled);
	}

	/**
	 * �e�[�u����CELL���ҏW�����ǂ���
	 * 
	 * @return isEditing
	 */
	public boolean isEditing() {
		return tbl.isEditing();
	}

	/**
	 * �e�[�u���̕ҏW���m�肷��
	 */
	public void stopCellEditing() {
		tbl.stopCellEditing();
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷��
	 * 
	 * @param strText �f�[�^
	 * @param rowIndex �s
	 * @param col �J����
	 * @deprecated �V�K�ł̎g�p�֎~<br>
	 *             setRowValueAt�𗘗p���邱��
	 */
	public void setValueAt(String strText, int rowIndex, int col) {
		tbl.setValueAt(strText, rowIndex, col);
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷��
	 * 
	 * @param obj �f�[�^
	 * @param rowIndex �s
	 * @param column �J����
	 * @deprecated �V�K�ł̎g�p�֎~<br>
	 *             setRowValueAt�𗘗p���邱��
	 */
	public void setValueAt(Object obj, int rowIndex, Enum column) {
		tbl.setValueAt(obj, rowIndex, column);
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷��
	 * 
	 * @param obj �f�[�^
	 * @param rowIndex �s
	 * @param col �J����
	 * @deprecated �V�K�ł̎g�p�֎~<br>
	 *             setRowValueAt�𗘗p���邱��
	 */
	public void setValueAt(Object obj, int rowIndex, int col) {
		tbl.setValueAt(obj, rowIndex, col);
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷��
	 * 
	 * @param strText �f�[�^
	 * @param rowIndex �s
	 * @param col �J����
	 */
	public void setModelValueAt(String strText, int rowIndex, int col) {
		tbl.setModelValueAt(strText, rowIndex, col);
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷��
	 * 
	 * @param obj �f�[�^
	 * @param rowIndex �s
	 * @param column �J����
	 */
	public void setModelValueAt(Object obj, int rowIndex, Enum column) {
		tbl.setModelValueAt(obj, rowIndex, column);
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷��
	 * 
	 * @param obj �f�[�^
	 * @param rowIndex �s
	 * @param col �J����
	 */
	public void setModelValueAt(Object obj, int rowIndex, int col) {
		tbl.setModelValueAt(obj, rowIndex, col);
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷�� ���s�\�[�g�A�����ւ��Ή�
	 * 
	 * @param strText �f�[�^
	 * @param row �s
	 * @param col �J����
	 */
	public void setRowValueAt(String strText, int row, int col) {
		tbl.setRowValueAt(strText, row, col);
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷�� ���s�\�[�g�A�����ւ��Ή�
	 * 
	 * @param obj �f�[�^
	 * @param row �s
	 * @param column �J����
	 */
	public void setRowValueAt(Object obj, int row, Enum column) {
		tbl.setRowValueAt(obj, row, column);
	}

	/**
	 * �w��s/�J�����f�[�^��ݒ肷�� ���s�\�[�g�A�����ւ��Ή�
	 * 
	 * @param obj �f�[�^
	 * @param row �s
	 * @param col �J����
	 */
	public void setRowValueAt(Object obj, int row, int col) {
		tbl.setRowValueAt(obj, row, col);
	}

	/**
	 * �ҏW�s/�J������ݒ肷��
	 * 
	 * @param row �s
	 * @param col �J����
	 */
	public void editCellAt(int row, int col) {
		tbl.editCellAt(row, col);
	}

	/**
	 * �w��̈ʒu�ɍs��ǉ�����
	 * 
	 * @param row �s
	 * @param data �f�[�^
	 */
	public void insertRow(int row, Object[] data) {
		tbl.insertRow(row, data); // �ǉ�
	}

	/**
	 * �w��̈ʒu�ɍs��ǉ�����
	 * 
	 * @param row �s
	 * @param list �f�[�^���X�g
	 */
	public void insertRow(int row, List list) {
		tbl.insertRow(row, list);
	}

	/**
	 * CellEditor���擾����
	 * 
	 * @param row �s
	 * @param col �J����
	 * @return CellEditor
	 */
	public TableCellEditor getCellEditor(int row, int col) {
		return tbl.getCellEditor(row, col);
	}

	/**
	 * CellRenderer���擾����
	 * 
	 * @param col �J����
	 * @return CellRenderer
	 */
	public TableCellRenderer getComponentRenderer(int col) {
		return tbl.getComponentRenderer(col);
	}

	/**
	 * �e�[�u�����f����Ԃ�
	 * 
	 * @return �e�[�u�����f��
	 */
	public TableModel getModel() {
		return tbl.getModel();
	}

	/**
	 * �w��J�������`�F�b�N�{�b�N�X�̏ꍇ�A�`�F�b�N����Ă���s����Ԃ�
	 * 
	 * @param e
	 * @return �`�F�b�N�s��
	 */
	public int getCheckedRowCount(Enum e) {
		return tbl.getCheckedRowCount(e);
	}

	/**
	 * �w��J�������`�F�b�N�{�b�N�X�̏ꍇ�A�`�F�b�N����Ă���ŏ��̍s�̃C���f�b�N�X��Ԃ�
	 * 
	 * @param e
	 * @return �w��J�������`�F�b�N�{�b�N�X�̏ꍇ�A�`�F�b�N����Ă���ŏ��̍s�̃C���f�b�N�X
	 */
	public int getFirstCheckedRowIndex(Enum e) {
		return tbl.getFirstCheckedRowIndex(e);
	}

	/**
	 * @see java.awt.Component#addFocusListener(java.awt.event.FocusListener)
	 */
	@Override
	public void addFocusListener(FocusListener listener) {
		tbl.addFocusListener(listener);
	}

	/**
	 * TTableColumn��Ԃ�
	 * 
	 * @return TTableColumn
	 */
	public List<TTableColumn> getTableColumn() {
		return tbl.getTableColumn();
	}

	/**
	 * �w��C���f�b�N�X��TTableColumn��Ԃ�
	 * 
	 * @param index �C���f�b�N�X
	 * @return TTableColumn
	 */
	public TTableColumn getTableColumnAt(int index) {
		return tbl.getTableColumnAt(index);
	}

	/**
	 * �w�b�_�[���N���b�N�����ۂɁA�`�F�b�N�{�b�N�X�̃J�����Ȃ��<br>
	 * �S�`�F�b�N�@�\��L���ɂ��邩
	 * 
	 * @return true:�L��
	 */
	public boolean isAllCheckWhenHeaderClicked() {
		return tbl.isAllCheckWhenHeaderClicked();
	}

	/**
	 * �w�b�_�[���N���b�N�����ۂɁA�`�F�b�N�{�b�N�X�̃J�����Ȃ��<br>
	 * �S�`�F�b�N�@�\��L���ɂ��邩
	 * 
	 * @param allCheckWhenHeaderClicked true:�L��
	 */
	public void setAllCheckWhenHeaderClicked(boolean allCheckWhenHeaderClicked) {
		tbl.setAllCheckWhenHeaderClicked(allCheckWhenHeaderClicked);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TStorable#saveComponent(jp.co.ais.trans2.common.gui.TStorableKey,
	 *      java.io.Serializable)
	 */
	public void saveComponent(TStorableKey key, Serializable obj) {
		tbl.saveComponent(key, obj);
	}

	/**
	 * �J������Ԃ̕���
	 */
	public void restoreComponent() {
		tbl.restoreComponent();
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TStorable#restoreComponent(jp.co.ais.trans2.common.gui.TStorableKey)
	 */
	public void restoreComponent(TStorableKey key) {
		tbl.restoreComponent(key);
	}

	/**
	 * �J�����ʒu����
	 * 
	 * @param tblInformation �e�[�u�����
	 */
	protected void restoreColumns(TTableInformation tblInformation) {
		tbl.restoreColumns(tblInformation);
	}

	/**
	 * �񕝕���
	 * 
	 * @param tblInformation �e�[�u�����
	 */
	public void restoreWidth(TTableInformation tblInformation) {
		tbl.restoreWidth(tblInformation);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TStorable#getKey()
	 */
	public TStorableKey getKey() {
		return tbl.getKey();
	}

	/**
	 * Sorter�擾
	 * 
	 * @return Sorter
	 */
	public TableRowSorter<? extends TableModel> getSorter() {
		return tbl.getSorter();
	}

	/**
	 * Sorter�ݒ�
	 * 
	 * @param sorter Sorter
	 */
	public void setSorter(TableRowSorter<? extends TableModel> sorter) {
		tbl.setSorter(sorter);
	}

	/**
	 * �\�[�g�\��
	 * 
	 * @return sortbl true:�\
	 */
	public boolean isSortable() {
		return tbl.isSortable();
	}

	/**
	 * �\�[�g�\��
	 * 
	 * @param sortbl true:�\
	 */
	public void setSortable(boolean sortbl) {
		tbl.setSortable(sortbl);
	}

	/**
	 * ���l��r�g�����ǂ���
	 * 
	 * @return useNumberComparator ���l��r�g�����ǂ���
	 */
	public boolean isUseNumberComparator() {
		return tbl.isUseNumberComparator();
	}

	/**
	 * ���l��r�g�����ǂ����̐ݒ�
	 * 
	 * @param flag ���l��r�g�����ǂ��� true:�g��<b>�E�񂹂̗�͐��l�Ǝb��</b>
	 */
	public void setUseNumberComparator(boolean flag) {
		tbl.setUseNumberComparator(flag);
	}

	/**
	 * �A�_�v�^�[�擾
	 * 
	 * @return adapter �A�_�v�^�[
	 */
	public TTableAdapter getAdapter() {
		return tbl.getAdapter();
	}

	/**
	 * �A�_�v�^�[�ݒ�
	 * 
	 * @param adapter �A�_�v�^�[
	 */
	public void setAdapter(TTableAdapter adapter) {
		tbl.setAdapter(adapter);
	}

	/**
	 * �w��J�����̏����_�ȉ�������ݒ� �������ւ��������ꍇ�s�����B�����g�p�֎~�Ƃ���B
	 * 
	 * @param column �J����
	 * @param digits �����_�ȉ�����
	 */
	public void setDecimalPointAt(Enum column, int digits) {
		tbl.setDecimalPointAt(column, digits);
	}

	/**
	 * �w��J�����̏����_�ȉ�������ݒ�
	 * 
	 * @param column �J����
	 * @param digits �����_�ȉ�����
	 */
	public void setDecimalPoint(Enum column, int digits) {
		tbl.setDecimalPoint(column, digits);
	}

	/**
	 * �s�I������Ă��邩�ǂ���
	 * 
	 * @return true:�I��
	 */
	public boolean isSelectedRow() {
		return tbl.isSelectedRow();
	}

	/**
	 * �X�N���[���o�[���ŏ�s�Ɉړ�����B
	 */
	public void setScrollTop() {
		tbl.setScrollTop();
	}

	/**
	 * �X�N���[���o�[���ŉ��s�Ɉړ�����B
	 */
	public void setScrollUnder() {
		tbl.setScrollUnder();
	}

	/**
	 * �X�N���[���o�[�����Ɉړ�����B
	 */
	public void setScrollLeft() {
		tbl.setScrollLeft();
	}

	/**
	 * �X�N���[���o�[�������ʒu�Ɉړ�����B
	 */
	public void setScrollDefault() {
		tbl.setScrollDefault();
	}

	/**
	 * �`�F�b�N�{�b�N�X�̕\���ύX��ǉ�.
	 * 
	 * @see javax.swing.JComponent#setEnabled(boolean)
	 */
	@Override
	public void setEnabled(boolean enable) {
		tbl.setEnabled(enable);

		// �t�b�^�[
	}

	/**
	 * �R���|�[�l���g�u�����N�\���p�̃A�_�v�^�[(���`�F�b�N�{�b�N�X����)
	 * 
	 * @param e �Ώۗ�
	 * @param adapter �A�_�v�^�[
	 */
	public void setComponentViewAdapter(Enum e, TComponentViewAdapter adapter) {
		tbl.setComponentViewAdapter(e, adapter);
	}

	/**
	 * �S�Ă̗���B��
	 */
	public void hideAllColumn() {
		tbl.hideAllColumn();
	}

	/**
	 * �S�Ă̗��\��
	 */
	public void showAllColumn() {
		tbl.showAllColumn();
	}

	/**
	 * ������Ԃ̎擾
	 * 
	 * @return �l
	 */
	public TTableInformation getInitTableInformation() {
		return tbl.getInitTableInformation();
	}

	/**
	 * �񕝎����v�Z�l�̎擾
	 * 
	 * @param column ��
	 * @param colWidth �ݒ��
	 * @return ��
	 */
	public int getAdjustColumnWidth(TableColumn column, int colWidth) {
		return tbl.getAdjustColumnWidth(column, colWidth);
	}

	/**
	 * �񕝎����v�Z�l�̎擾
	 * 
	 * @param column ��
	 * @param colWidth �ݒ��
	 * @param type �����������̃^�C�v
	 * @param colIndex �w���C���f�b�N�X
	 * @return ��
	 */
	public int getAdjustColumnWidth(TableColumn column, int colWidth, AutoSizeType type, int colIndex) {
		return tbl.getAdjustColumnWidth(column, colWidth, type, colIndex);
	}

	/**
	 * �w���̍ő啶����̎擾
	 * 
	 * @param column �w���I�u�W�F�N�g
	 * @param col �w���C���f�b�N�X
	 * @param type �����������̃^�C�v
	 * @return �w���̍ő啶����
	 */
	public int getMaxWidth(TableColumn column, int col, AutoSizeType type) {
		return tbl.getMaxWidth(column, col, type);
	}

	/**
	 * HTML�܂ݕ����̕�����
	 * 
	 * @param cellValue
	 * @return HTML�܂ݕ����̕�
	 */
	protected int getWidthWithHtml(String cellValue) {
		return tbl.getWidthWithHtml(cellValue);
	}

	/**
	 * �񕝎����v�Z<br>
	 * �����[���̏ꍇ�A�����v�Z���Ȃ�
	 */
	public void autosizeColumnWidth() {
		tbl.autosizeColumnWidth();
	}

	/**
	 * �񕝎����v�Z<br>
	 * �����[���̏ꍇ�A�����v�Z���Ȃ�
	 * 
	 * @param type �����������̃^�C�v
	 */
	public void autosizeColumnWidth(AutoSizeType type) {
		tbl.autosizeColumnWidth(type);
	}

	/**
	 * �񕝎����v�Z<br>
	 * 
	 * @param isZeroAdjust <br>
	 *            true:�����[���̏ꍇ�A�����v�Z���� false:�����[���̏ꍇ�A�����v�Z���Ȃ�
	 * @param type �����������̃^�C�v
	 */
	public void autosizeColumnWidth(boolean isZeroAdjust, AutoSizeType type) {
		tbl.autosizeColumnWidth(isZeroAdjust, type);
	}

	/**
	 * TTable��V.ScrollBar
	 * 
	 * @return V.ScrollBar
	 */
	public JScrollBar createVerticalScrollBar() {
		return tbl.createVerticalScrollBar();
	}

	/**
	 * �s�w�b�_�[�́u*�v�ŕ\�����邩�ǂ���
	 * 
	 * @return true:�I���s�̍s�ԍ��́u*�v�ŕ\��
	 */
	public boolean isShowRowHeaderStar() {
		return tbl.isShowRowHeaderStar();
	}

	/**
	 * �s�w�b�_�[�́u*�v�\���s�ԍ��̎擾
	 * 
	 * @return �s�w�b�_�[�́u*�v�\���s�ԍ�
	 */
	public int getRowHeaderStarIndex() {
		return tbl.getRowHeaderStarIndex();
	}

	/**
	 * �s�w�b�_�[�́u*�v�ŕ\�����邩�ǂ���
	 * 
	 * @param flag true:�I���s�̍s�ԍ��́u*�v�ŕ\��
	 */
	public void setShowRowHeaderStar(boolean flag) {
		tbl.setShowRowHeaderStar(flag);
	}

	/**
	 * �s�w�b�_�[�́u*�v�ŕ\�����邩�ǂ���
	 * 
	 * @param row �w��s�̍s�ԍ��́u*�v�ŕ\��
	 */
	public void setShowRowHeaderStar(int row) {
		tbl.setShowRowHeaderStar(row);
	}

	/**
	 * �s�^�C�g�����X�g�̎擾
	 * 
	 * @return rowHeaderMessage �s�^�C�g�����X�g
	 */
	public List<String> getRowHeaderMessage() {
		return tbl.getRowHeaderMessage();
	}

	/**
	 * �s�^�C�g�����X�g�̐ݒ�
	 * 
	 * @param rowHeaderMessage �s�^�C�g�����X�g
	 */
	public void setRowHeaderMessage(List<String> rowHeaderMessage) {
		tbl.setRowHeaderMessage(rowHeaderMessage);
	}

	/**
	 * �w��s��̃Z���̃t�H�[�J�X�𓖂Ă�
	 * 
	 * @param row �s
	 * @param column ��
	 */
	public void requestFocus(int row, int column) {
		tbl.requestFocus(row, column);
	}

	/**
	 * �w��s��̃Z���̃t�H�[�J�X�𓖂Ă�
	 * 
	 * @param row �s
	 * @param e ��Enum
	 */
	public void requestFocus(int row, Enum e) {
		tbl.requestFocus(row, e);
	}

	/**
	 * ��^�C�g���̎擾
	 * 
	 * @param e Enum
	 * @return ��^�C�g��
	 */
	public String getColumnTitle(Enum e) {
		return tbl.getColumnTitle(e);
	}

	/**
	 * ��ǉ����̐ݒ�̎擾
	 * 
	 * @return columnSetting ��ǉ����̐ݒ�
	 */
	public ColumnSetting getColumnSetting() {
		return tbl.getColumnSetting();
	}

	/**
	 * ��ǉ����̐ݒ�̐ݒ�
	 * 
	 * @param columnSetting ��ǉ����̐ݒ�
	 */
	public void setColumnSetting(ColumnSetting columnSetting) {
		tbl.setColumnSetting(columnSetting);
	}

	/**
	 * SPACE�����Ώۃ`�F�b�N�{�b�N�X��̒ǉ�
	 * 
	 * @param col SPACE�����Ώۃ`�F�b�N�{�b�N�X��
	 */
	public void addCheckBoxColumn(int col) {
		tbl.addCheckBoxColumn(col);
	}

	/**
	 * SPACE�����Ώۃ`�F�b�N�{�b�N�X�񃊃X�g�̎擾
	 * 
	 * @return checkBoxColumns SPACE�����Ώۃ`�F�b�N�{�b�N�X�񃊃X�g
	 */
	public List<Integer> getCheckBoxColumns() {
		return tbl.getCheckBoxColumns();
	}

	/**
	 * SPACE�����Ώۃ`�F�b�N�{�b�N�X�񃊃X�g�̐ݒ�
	 * 
	 * @param checkBoxColumns SPACE�����Ώۃ`�F�b�N�{�b�N�X�񃊃X�g
	 */
	public void setCheckBoxColumns(List<Integer> checkBoxColumns) {
		tbl.setCheckBoxColumns(checkBoxColumns);
	}

	/**
	 * SPACE�����Ώۃ`�F�b�N�{�b�N�X�񃊃X�g�̃N���A
	 */
	public void clearCheckBoxColumns() {
		tbl.clearCheckBoxColumns();
	}

	/**
	 * �\��t���@�\�g�����Btrue:�g���̎擾
	 * 
	 * @return useTablePaste �\��t���@�\�g�����Btrue:�g��
	 */
	public boolean isUseTablePaste() {
		return tbl.isUseTablePaste();
	}

	/**
	 * �\��t���@�\�g�����Btrue:�g���̐ݒ�
	 * 
	 * @param useTablePaste �\��t���@�\�g�����Btrue:�g��
	 */
	public void setUseTablePaste(boolean useTablePaste) {
		tbl.setUseTablePaste(useTablePaste);
	}
}
