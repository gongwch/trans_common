package jp.co.ais.trans.common.gui.table;

import java.util.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.gui.*;

/**
 * TTable�p����EditCellAdapter
 */
public abstract class TEditCellAdapter extends JCEditCellAdapter {

	/** �ΏۃX�v���b�h */
	protected TTable table;

	/** �ΏۃJ����Index */
	protected List<Integer> columns;

	/** �C�������������ǂ����t���O */
	protected boolean isEdit = false;

	/** �ҏW����Ă���s�ԍ� */
	protected int editRow;

	/** �ҏW����Ă����ԍ� */
	protected int editCol;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param table �ΏۃX�v���b�h
	 * @param column �ΏۃJ����Index
	 */
	public TEditCellAdapter(TTable table, int... column) {
		this.table = table;

		columns = new ArrayList<Integer>(column.length);
		for (int i = 0; i < column.length; i++) {
			columns.add(column[i]);
		}
	}

	/**
	 * @see JCEditCellAdapter#editCell(JCEditCellEvent)
	 */
	@Override
	public void editCell(JCEditCellEvent evt) {
		super.editCell(evt);

		// �ҏW����Ă���s�ԍ�
		editRow = evt.getRow();

		// �ҏW����Ă����ԍ�
		editCol = evt.getColumn();

		isEdit = true;
	}

	/**
	 * @see JCEditCellAdapter#editCell(JCEditCellEvent)
	 */
	@Override
	public void afterEditCell(JCEditCellEvent evt) {
		super.afterEditCell(evt);

		// �ҏW���������ꍇ�̂�
		if (!isEdit) {
			return;
		}

		// �ΏۃJ�����̂�
		if (!columns.contains(editCol)) {
			return;
		}

		isEdit = false; // ������

		after(editRow, editCol);
	}

	/**
	 * �w���̃Z�����ҏW���ꂽ��afterEditCell()����Ă΂�郁�\�b�h.<br>
	 * 
	 * @param row �s�ԍ�
	 * @param column ��ԍ�
	 */
	protected abstract void after(int row, int column);
}