package jp.co.ais.trans2.common.gui;

import javax.swing.table.TableCellEditor;
import javax.swing.table.TableCellRenderer;

/**
 * TTable�̃Z���ɃZ�b�g�ł���R���|�[�l���g�C���^�[�t�F�[�X<br>
 * 
 * @author AIS
 */
public interface TTableComponent {

	/**
	 * CellRenderer��Ԃ�
	 * 
	 * @param tbl
	 * @return Renderer
	 */
	public TableCellRenderer getCellRenderer(TTable tbl);

	/**
	 * CellEditor��Ԃ�
	 * 
	 * @param tbl
	 * @return CellEditor
	 */
	public TableCellEditor getCellEditor(TTable tbl);

	/**
	 * �s�ԍ����擾���܂�
	 * 
	 * @return �s�ԍ�
	 */
	public int getRowIndex();

	/**
	 * �s�ԍ���ݒ肵�܂�
	 * 
	 * @param rowIndex
	 */
	public void setRowIndex(int rowIndex);

	/**
	 * ����������align getter
	 * 
	 * @return ����������align
	 */
	public int getDefaultCellRendererHorizontalAlignment();

	/**
	 * �e�[�u���ŗ��p����Ă��邩�ǂ���
	 * 
	 * @return true:���p
	 */
	public boolean isTableCellEditor();

	/**
	 * �e�[�u���ŗ��p���邩�ǂ���
	 * 
	 * @param tableCellEditor true:���p
	 */
	public void setTableCellEditor(boolean tableCellEditor);

}
