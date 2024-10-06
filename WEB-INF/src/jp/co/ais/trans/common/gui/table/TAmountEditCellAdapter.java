package jp.co.ais.trans.common.gui.table;

import java.util.*;

import com.klg.jclass.table.data.*;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���z�pEditCellListener����.<br>
 * �Z���l��ύX���i���ړ��́j�A�w�菬���_�����Ő��l�t�H�[�}�b�g����.<br>
 * �J����Index���w�肵�Ă���ꍇ�A�����_������ύX�����ꍇ�ɓ����̒l��S�čăt�H�[�}�b�g����.
 */
public class TAmountEditCellAdapter extends TEditCellAdapter {

	/** �����_���� */
	protected int digit;

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param table �ΏۃX�v���b�h
	 * @param digit �����_����
	 * @param column �ΏۃJ����Index
	 */
	public TAmountEditCellAdapter(TTable table, int digit, int... column) {
		super(table, column);

		this.digit = digit;
	}

	/**
	 * @see jp.co.ais.trans.common.gui.table.TEditCellAdapter#after(int, int)
	 */
	@Override
	protected void after(int row, int column) {
		JCVectorDataSource ds = (JCVectorDataSource) table.getDataSource();

		if (ds.getCells().size() <= row) {
			return;
		}

		Vector rowVec = (Vector) ds.getCells().get(row);

		if (rowVec == null || rowVec.size() <= column) {
			return;
		}

		String num = (String) rowVec.get(column);

		if (!Util.isNullOrEmpty(num)) {
			// �Z���\���ύX
			num = num.replace(",", "");
			ds.setCell(row, column, NumberFormatUtil.formatNumber(num, digit));
		}
	}

	/**
	 * �����_�̐ݒ�.<br>
	 * �ΏۃJ����Index���ݒ肳��Ă���ꍇ�A�Y���Z���̒l��S�čăt�H�[�}�b�g����.
	 * 
	 * @param digit �����_����
	 */
	public void setDecimalPoint(int digit) {
		this.digit = digit;

		// �������l�̍ăt�H�[�}�b�g(�J����Index�w�莞�̂�)
		JCVectorDataSource ds = (JCVectorDataSource) table.getDataSource();

		for (int row = 0; row < ds.getCells().size(); row++) {
			Vector rowVec = (Vector) ds.getCells().get(row);
			if (rowVec == null) continue;

			for (int i = 0; i < columns.size(); i++) {
				if (rowVec.size() <= columns.get(i)) continue;

				String num = (String) rowVec.get(columns.get(i));
				if (Util.isNullOrEmpty(num)) continue;

				// �Z���\���ύX
				num = num.replace(",", "");
				ds.setCell(row, columns.get(i), NumberFormatUtil.formatNumber(num, digit));
			}
		}
	}
}