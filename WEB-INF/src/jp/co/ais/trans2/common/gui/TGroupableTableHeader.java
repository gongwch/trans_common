package jp.co.ais.trans2.common.gui;

import java.util.*;
import java.util.Map.Entry;

import javax.swing.table.*;

/**
 * �����e�[�u���w�b�_�[
 */
public class TGroupableTableHeader extends JTableHeader {

	/** �����񃊃X�g�̃}�b�v���s�ԍ��i0�`�C�����񃊃X�g�j�� */
	protected Map<Integer, List> groupColumnMap = null;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param model
	 */
	public TGroupableTableHeader(TableColumnModel model) {
		super(model);
		setUI(new TGroupableTableHeaderUI());
		setReorderingAllowed(false);
	}

	@Override
	public void updateUI() {
		setUI(new TGroupableTableHeaderUI());
	}

	@Override
	public void setReorderingAllowed(boolean b) {
		reorderingAllowed = false;
	}

	/**
	 * @param g
	 */
	public void addTGroupColumn(TGroupColumn g) {
		addTGroupColumn(0, g);
	}

	/**
	 * @param rowNo �s�ԍ�, 0�`
	 * @param g
	 */
	public void addTGroupColumn(int rowNo, TGroupColumn g) {
		if (groupColumnMap == null) {
			groupColumnMap = new HashMap<Integer, List>();
		}

		List groupColumnList = groupColumnMap.get(rowNo);

		if (groupColumnList == null) {
			groupColumnList = new ArrayList();
			groupColumnMap.put(rowNo, groupColumnList);
		}
		groupColumnList.add(g);
	}

	/**
	 * @param col
	 * @return ������O���[�v
	 */
	public List getGroupColumns(TableColumn col) {
		return getGroupColumns(0, col);
	}

	/**
	 * �����Z���̍s����Ԃ�
	 * 
	 * @return �����Z���̍s��
	 */
	public int getGroupCount() {
		if (groupColumnMap == null) {
			return 0;
		}

		return groupColumnMap.size();
	}

	/**
	 * @param rowNo �s�ԍ��A0�`
	 * @param col
	 * @return ������O���[�v
	 */
	public List getGroupColumns(int rowNo, TableColumn col) {
		List groupColumnList = groupColumnMap.get(rowNo);
		if (groupColumnList == null) {
			return null;
		}
		for (Object obj : groupColumnList) {
			if (obj instanceof TGroupColumn) {
				List list = ((TGroupColumn) obj).getGroupColumns(col, new ArrayList());
				if (list != null) {
					return list;
				}
			}
		}

		return null;
	}

	/**
	 * MARGIN�ݒ�(�`�掞)
	 */
	public void setColumnMargin() {
		for (Entry<Integer, List> entry : groupColumnMap.entrySet()) {
			List groupColumnList = entry.getValue();

			if (groupColumnList != null) {
				int columnMargin = getColumnModel().getColumnMargin();
				for (Object obj : groupColumnList) {
					if (obj instanceof TGroupColumn) {
						((TGroupColumn) obj).setColumnMargin(columnMargin);
					}
				}
			}
		}
	}

}
