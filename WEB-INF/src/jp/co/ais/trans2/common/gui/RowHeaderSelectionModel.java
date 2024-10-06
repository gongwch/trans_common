package jp.co.ais.trans2.common.gui;

import javax.swing.*;
import javax.swing.event.*;

/**
 * 行番号表示用Selectモデル
 */
public class RowHeaderSelectionModel extends DefaultListSelectionModel implements ListSelectionListener {

	/** SelectModel */
	protected ListSelectionModel selectModel;

	/** TTable */
	protected TTable tbl;

	/**
	 * コンストラクタ.
	 * 
	 * @param tbl テーブル
	 */
	public RowHeaderSelectionModel(TTable tbl) {
		this.selectModel = tbl.table.getSelectionModel();
		this.tbl = tbl;

		selectModel.removeListSelectionListener(this);
		selectModel.addListSelectionListener(this);
	}

	// ListSelectionModelインターフェースで宣言されている全メソッドに対して、
	// selectModelのメソッドを呼ぶようにする（委譲する）

	@Override
	public void setSelectionInterval(int index0, int index1) {
		selectModel.setSelectionInterval(index0, index1);
	}

	@Override
	public void addSelectionInterval(int index0, int index1) {
		selectModel.addSelectionInterval(index0, index1);
	}

	@Override
	public void removeSelectionInterval(int index0, int index1) {
		selectModel.removeSelectionInterval(index0, index1);
	}

	@Override
	public void addListSelectionListener(ListSelectionListener x) {
		selectModel.addListSelectionListener(x);
	}

	@Override
	public void removeListSelectionListener(ListSelectionListener x) {
		selectModel.removeListSelectionListener(x);
	}

	/**
	 * ListSelectionListenerのメソッドであり、データ用テーブルで行選択が変更されたときに呼ばれる
	 */
	public void valueChanged(ListSelectionEvent e) {

		int fi = e.getFirstIndex();
		int li = e.getLastIndex();

		super.removeSelectionInterval(fi, li);

		for (int i = fi; i <= li; i++) {
			if (selectModel.isSelectedIndex(i)) {
				super.addSelectionInterval(i, i);
			}
		}

		if (getAdapter() != null) {
			// 行選択状態変更
			getAdapter().rowSelectionChanged();
		}
	}

	/**
	 * @return BaseTable
	 */
	protected TTableAdapter getAdapter() {
		if (tbl == null) {
			return null;
		}
		return tbl.adapter;
	}
}