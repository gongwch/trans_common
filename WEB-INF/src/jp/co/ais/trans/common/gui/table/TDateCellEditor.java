package jp.co.ais.trans.common.gui.table;

import com.klg.jclass.table.*;

import jp.co.ais.trans.common.gui.*;

/**
 * TTable�p ���t�Z��Editor(�ȈՔ�)
 */
public class TDateCellEditor extends TCellEditor {

	/** �N(YYYY) */
	public static final int TYPE_Y = TDatePlainDocument.TYPE_Y;

	/** �N��(YYYY/MM) */
	public static final int TYPE_YM = TDatePlainDocument.TYPE_YM;

	/** �N����(YYYY/MM/DD) */
	public static final int TYPE_YMD = TDatePlainDocument.TYPE_YMD;

	/**
	 * �R���X�g���N�^.<br>
	 * ���t�^�C�v YYYY/MM/DD
	 * 
	 * @param table �Ώۃe�[�u��
	 */
	public TDateCellEditor(final TTable table) {
		this(table, TYPE_YMD);
	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param table �Ώۃe�[�u��
	 * @param type
	 */
	public TDateCellEditor(final TTable table, int type) {
		super(table, 0);

		setHorizontalAlignment(CellStyleModel.CENTER);

		// ���t�h�L�������g
		setDocument(new TDatePlainDocument(this, type));
	}

	/**
	 * �S�p���͖͂���(�Z�b�g����Ă�����)
	 * 
	 * @param ime �w��Ӗ��Ȃ�
	 */
	@Override
	public void setImeMode(boolean ime) {
		enableInputMethods(false);
	}
}
