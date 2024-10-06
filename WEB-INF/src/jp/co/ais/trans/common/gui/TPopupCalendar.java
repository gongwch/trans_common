package jp.co.ais.trans.common.gui;

import javax.swing.table.*;

import jp.co.ais.trans2.common.gui.*;
import jp.co.ais.trans2.common.gui.TTable;
import jp.co.ais.trans2.common.gui.table.*;

/**
 * �J�����_�[�t�B�[���h�R���|�[�l���g
 */
public class TPopupCalendar extends TCalendar implements TTableComponent {

	/** �V���A��UID */
	private static final long serialVersionUID = 3560600449562807948L;

	/** @deprecated TYPE_YM���g�p. */
	public static final int YEAR_MONTH = TCalendar.TYPE_YM;

	/** @deprecated TYPE_YMD���g�p */
	public static final int YEAR_MONTH_DATE = TCalendar.TYPE_YMD;

	/** �N */
	@SuppressWarnings("hiding")
	public static final int TYPE_Y = TCalendar.TYPE_Y;

	/** �N�� */
	@SuppressWarnings("hiding")
	public static final int TYPE_YM = TCalendar.TYPE_YM;

	/** �N���� */
	@SuppressWarnings("hiding")
	public static final int TYPE_YMD = TCalendar.TYPE_YMD;

	/** �N�������� */
	@SuppressWarnings("hiding")
	public static final int TYPE_YMDHM = TCalendar.TYPE_YMDHM;

	/** �N���������b */
	@SuppressWarnings("hiding")
	public static final int TYPE_YMDHMS = TCalendar.TYPE_YMDHMS;

	/**
	 * �R���X�g���N�^<br>
	 * �J�����_�[�^�C�v��TYPE_YMD
	 */
	public TPopupCalendar() {
		super();
	}

	/**
	 * �R���X�g���N�^.<br>
	 * �w�肵���^�C�v�ɏ]���ăJ�����_�[���\�z����
	 * <ol>
	 * <li>TYPE_Y : �N
	 * <li>TYPE_YM : �N��
	 * <li>TYPE_YMD : �N����
	 * <li>TYPE_TIMER : �N���� ������
	 * </ol>
	 * 
	 * @param calType �J�����_�[�^�C�v(TYPE_X)
	 */
	public TPopupCalendar(int calType) {
		super(calType);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellEditor(jp.co.ais.trans2.common.gui.TTable)
	 */
	@Override
	public TableCellEditor getCellEditor(TTable tbl) {

		tableCellEditor = true;
		return new TDateEditor(this, tbl);
	}

	/**
	 * @see jp.co.ais.trans2.common.gui.TTableComponent#getCellRenderer(jp.co.ais.trans2.common.gui.TTable)
	 */
	@Override
	public TableCellRenderer getCellRenderer(TTable tbl) {
		return new TDateRenderer(new TPopupCalendar(this.calendarType), tbl);
	}
}
