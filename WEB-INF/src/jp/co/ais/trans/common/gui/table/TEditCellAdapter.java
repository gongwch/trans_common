package jp.co.ais.trans.common.gui.table;

import java.util.*;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.gui.*;

/**
 * TTable用共通EditCellAdapter
 */
public abstract class TEditCellAdapter extends JCEditCellAdapter {

	/** 対象スプレッド */
	protected TTable table;

	/** 対象カラムIndex */
	protected List<Integer> columns;

	/** 修正が入ったかどうかフラグ */
	protected boolean isEdit = false;

	/** 編集されている行番号 */
	protected int editRow;

	/** 編集されている列番号 */
	protected int editCol;

	/**
	 * コンストラクタ.
	 * 
	 * @param table 対象スプレッド
	 * @param column 対象カラムIndex
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

		// 編集されている行番号
		editRow = evt.getRow();

		// 編集されている列番号
		editCol = evt.getColumn();

		isEdit = true;
	}

	/**
	 * @see JCEditCellAdapter#editCell(JCEditCellEvent)
	 */
	@Override
	public void afterEditCell(JCEditCellEvent evt) {
		super.afterEditCell(evt);

		// 編集があった場合のみ
		if (!isEdit) {
			return;
		}

		// 対象カラムのみ
		if (!columns.contains(editCol)) {
			return;
		}

		isEdit = false; // 初期化

		after(editRow, editCol);
	}

	/**
	 * 指定列のセルが編集されたにafterEditCell()から呼ばれるメソッド.<br>
	 * 
	 * @param row 行番号
	 * @param column 列番号
	 */
	protected abstract void after(int row, int column);
}