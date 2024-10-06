package jp.co.ais.trans2.common.gui;

import java.util.*;

import jp.co.ais.trans.common.gui.*;

/**
 * ���t�͈̓t�B�[���h
 * 
 * @author AIS
 */
public class TDateRange extends TPanel {

	/** serialVersionUID */
	private static final long serialVersionUID = -5671715054170282481L;

	/** ���t�i�J�n�j */
	public TLabelPopupCalendar dateFrom;

	/** ���t�i�I���j */
	public TLabelPopupCalendar dateTo;

	/** �N */
	public static final int TYPE_Y = TCalendar.TYPE_Y;

	/** �N�� */
	public static final int TYPE_YM = TCalendar.TYPE_YM;

	/** �N���� */
	public static final int TYPE_YMD = TCalendar.TYPE_YMD;

	/** ���� */
	public static final int height = 20;

	/**
	 * �R���X�g���N�^.
	 */
	public TDateRange() {

		this(TYPE_YMD);

	}

	/**
	 * �R���X�g���N�^.
	 * 
	 * @param calType ���t�\���`��
	 */
	public TDateRange(int calType) {

		// �R���|�[�l���g������������
		initComponents(calType);

		// �R���|�[�l���g��z�u����
		allocateComponents();
	}

	/**
	 * �R���|�[�l���g������������<BR>
	 * 
	 * @param calType ���t�\���`��
	 */
	protected void initComponents(int calType) {
		dateFrom = new TLabelPopupCalendar(calType);
		dateTo = new TLabelPopupCalendar(calType);
	}

	/**
	 * �R���|�[�l���g��z�u����
	 */
	protected void allocateComponents() {

		setLayout(null);

		// ���t�i�J�n�j
		dateFrom.setLocation(0, 0);
		dateFrom.setSize(dateFrom.getCalendarSize() + dateFrom.getLabelSize() + 5, height);
		add(dateFrom);

		// ���t�i�I���j
		dateTo.setLabelSize(20);
		dateTo.setSize(dateTo.getCalendarSize() + dateTo.getLabelSize() + 5, height);
		dateTo.setLangMessageID("C01333");
		dateTo.setLocation(dateFrom.getWidth(), 0);
		add(dateTo);

		// �S�̂̃T�C�Y��ݒ�
		setSize();

	}

	/**
	 * �S�̂̃T�C�Y�ݒ�
	 */
	public void setSize() {
		setSize(dateFrom.getWidth() + dateTo.getWidth() + 5, 20);
	}

	/**
	 * FROM�ATO�A�S�̂̃T�C�Y���Đݒ�
	 */
	public void resize() {
		dateFrom.setSize(dateFrom.getCalendarSize() + dateFrom.getLabelSize() + 5, height);
		dateTo.setSize(dateTo.getCalendarSize() + dateTo.getLabelSize() + 5, height);
		dateTo.setLocation(dateFrom.getWidth(), 0);
		setSize(dateFrom.getWidth() + dateTo.getWidth() + 5, 20);
	}

	/**
	 * Tab���̐ݒ�
	 * 
	 * @param tabControlNo �^�u��
	 */
	public void setTabControlNo(int tabControlNo) {
		dateFrom.setTabControlNo(tabControlNo);
		dateTo.setTabControlNo(tabControlNo);
	}

	/**
	 * ���͓��t�́uFROM <= TO�v���r����.
	 * 
	 * @return �uFROM <= TO�v�܂��́A�ǂ��炩���u�����N�Ȃ��true.
	 */
	public boolean isSmallerFrom() {
		Date fromDate = dateFrom.getValue();
		Date toDate = dateTo.getValue();

		if (fromDate == null || toDate == null) {
			return true;
		}

		return fromDate.compareTo(toDate) <= 0;
	}

	/**
	 * ���t�i�J�n�j���擾����
	 * 
	 * @return ���t�i�J�n�j
	 */
	public TLabelPopupCalendar getDateFrom() {
		return dateFrom;
	}

	/**
	 * ���t�i�I���j���擾����
	 * 
	 * @return ���t�i�I���j
	 */
	public TLabelPopupCalendar getDateTo() {
		return dateTo;
	}

	/**
	 * ���t�i�J�n�j�l���擾����
	 * 
	 * @return ���t�i�J�n�j�l
	 */
	public Date getValueFrom() {
		return dateFrom.getValue();
	}

	/**
	 * ���t�i�I���j�l���擾����
	 * 
	 * @return ���t�i�I���j�l
	 */
	public Date getValueTo() {
		return dateTo.getValue();
	}

	/**
	 * �ҏW��Ԃ�ݒ肷��
	 * 
	 * @param isEditable
	 */
	public void setEditable(boolean isEditable) {
		dateFrom.setEditable(isEditable);
		dateTo.setEditable(isEditable);
	}

	/**
	 * �l��ݒ肷��
	 * 
	 * @param valueFrom �J�n���t�̒l
	 * @param valueTo �I�����t�̒l
	 */
	public void setValue(Date valueFrom, Date valueTo) {
		dateFrom.setValue(valueFrom);
		dateTo.setValue(valueTo);
	}

	/**
	 * ���x����ݒ肷��
	 * 
	 * @param ID ���b�Z�[�WID
	 * @param Width ���x���T�C�Y
	 */
	public void setLabelFrom(String ID, int Width) {
		dateFrom.setLangMessageID(ID);
		dateFrom.setLabelSize(Width);
		resize();
	}

	/**
	 * �N���A
	 */
	public void clear() {
		dateFrom.clear();
		dateTo.clear();
	}

}
