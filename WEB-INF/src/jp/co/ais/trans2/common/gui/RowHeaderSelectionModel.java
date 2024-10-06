package jp.co.ais.trans2.common.gui;

import javax.swing.*;
import javax.swing.event.*;

/**
 * �s�ԍ��\���pSelect���f��
 */
public class RowHeaderSelectionModel extends DefaultListSelectionModel implements ListSelectionListener {

	/** SelectModel */
	protected ListSelectionModel selectModel;

	/** TTable */
	protected TTable tbl;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param tbl �e�[�u��
	 */
	public RowHeaderSelectionModel(TTable tbl) {
		this.selectModel = tbl.table.getSelectionModel();
		this.tbl = tbl;

		selectModel.removeListSelectionListener(this);
		selectModel.addListSelectionListener(this);
	}

	// ListSelectionModel�C���^�[�t�F�[�X�Ő錾����Ă���S���\�b�h�ɑ΂��āA
	// selectModel�̃��\�b�h���ĂԂ悤�ɂ���i�Ϗ�����j

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
	 * ListSelectionListener�̃��\�b�h�ł���A�f�[�^�p�e�[�u���ōs�I�����ύX���ꂽ�Ƃ��ɌĂ΂��
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
			// �s�I����ԕύX
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