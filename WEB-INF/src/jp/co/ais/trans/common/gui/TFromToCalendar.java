package jp.co.ais.trans.common.gui;

import java.awt.*;
import java.util.*;

import javax.swing.*;

/**
 * From�`To�̃J�����_�[
 */
public class TFromToCalendar extends TPanel {

	/** ���x�� */
	protected TLabel lblLabel;

	/** From */
	protected TPopupCalendar dtFrom;

	/** �`���x�� */
	protected TLabel lblKARA;

	/** To */
	protected TPopupCalendar dtTo;

	/** */
	protected int type = TPopupCalendar.TYPE_YMD;

	/**
	 * �R���X�g���N�^
	 */
	public TFromToCalendar() {
		super();

		initComponents();
	}

	/**
	 * �R���X�g���N�^
	 * 
	 * @param type �J�����_�[�^�C�v
	 */
	public TFromToCalendar(int type) {
		this.type = type;
	}

	/**
	 * ������.
	 */
	protected void initComponents() {
		GridBagConstraints gridBagConstraints;

		lblLabel = createLabelField();
		dtFrom = createCalendarField();
		lblKARA = createLabelField();
		dtTo = createCalendarField();

		setLayout(new GridBagLayout());

		// ���x��
		lblLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		lblLabel.setLabelFor(dtFrom);
		lblLabel.setText("Label"); // �_�~�[����

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 0;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 5, 0, 0);
		add(lblLabel, gridBagConstraints);

		// FROM
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 1;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 3, 0, 0);
		add(dtFrom, gridBagConstraints);

		// �`���x��
		lblKARA.setHorizontalAlignment(SwingConstants.CENTER);
		lblKARA.setLabelFor(dtTo);
		lblKARA.setLangMessageID("�`");

		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 2;
		gridBagConstraints.gridy = 0;
		gridBagConstraints.insets = new Insets(0, 2, 0, 2);
		add(lblKARA, gridBagConstraints);

		// TO
		gridBagConstraints = new GridBagConstraints();
		gridBagConstraints.gridx = 3;
		gridBagConstraints.gridy = 0;
		add(dtTo, gridBagConstraints);
	}

	/**
	 * ���x���t�B�[���h����
	 * 
	 * @return From�t�B�[���h
	 */
	protected TLabel createLabelField() {
		return new TLabel();
	}

	/**
	 * �J�����_�[�t�B�[���h����
	 * 
	 * @return From�t�B�[���h
	 */
	protected TPopupCalendar createCalendarField() {
		return new TPopupCalendar(type);
	}

	/**
	 * �R���g���[���ԍ��ݒ�
	 * 
	 * @param number �ԍ�
	 */
	public void setTabControlNo(int number) {
		dtFrom.setTabControlNo(number);
		dtTo.setTabControlNo(number);
	}

	/**
	 * ���x�������ݒ�
	 * 
	 * @param text ���x������
	 */
	public void setLabelText(String text) {
		lblLabel.setLangMessageID(text);
		lblLabel.setText(text);
	}

	/**
	 * �ҏW��ԕύX
	 * 
	 * @param isEdit true:�ҏW�Afalse:�ҏW�s��
	 */
	public void setEditable(boolean isEdit) {
		dtFrom.setEditable(isEdit);
		dtTo.setEditable(isEdit);
	}

	/**
	 * �����ԕύX
	 * 
	 * @param isEnabled true:����Afalse:����s��
	 */
	public void setEnabled(boolean isEnabled) {
		dtFrom.setEnabled(isEnabled);
		dtTo.setEnabled(isEnabled);
	}

	/**
	 * ���x���̕\��/��\���ؑ�
	 * 
	 * @param isVisible true:�\���Afalse:��\��
	 */
	public void setLabelVisible(boolean isVisible) {
		lblLabel.setVisible(isVisible);
	}

	/**
	 * ���x���̓��e�� X ���ɉ������z�u���@��ݒ肵�܂��B
	 * 
	 * @param alignment SwingConstants�萔
	 */
	public void setLabelHAlignment(int alignment) {
		lblLabel.setHorizontalAlignment(alignment);
	}

	/**
	 * ���x���R���|�[�l���g�擾
	 * 
	 * @return ���x���R���|�[�l���g
	 */
	public TLabel getLabel() {
		return lblLabel;
	}

	/**
	 * FROM�J�����_�[�R���|�[�l���g�擾
	 * 
	 * @return FROM�J�����_�[�R���|�[�l���g
	 */
	public TPopupCalendar getFromCalendar() {
		return dtFrom;
	}

	/**
	 * TO�J�����_�[�R���|�[�l���g�擾
	 * 
	 * @return TO�J�����_�[�R���|�[�l���g
	 */
	public TCalendar getToCalendar() {
		return dtTo;
	}

	/**
	 * From�l�擾
	 * 
	 * @return Date
	 */
	public Date getFromDate() {
		return dtFrom.getValue();
	}

	/**
	 * From�l�ݒ�
	 * 
	 * @param date Date
	 */
	public void setFromDate(Date date) {
		dtFrom.setValue(date);
	}

	/**
	 * To�l�擾
	 * 
	 * @return Date
	 */
	public Date getToDate() {
		return dtTo.getValue();
	}

	/**
	 * To�l�ݒ�
	 * 
	 * @param date Date
	 */
	public void setToDate(Date date) {
		dtTo.setValue(date);
	}

	/**
	 * ���͓��t�́uFROM <= TO�v���r����.
	 * 
	 * @return �uFROM <= TO�v�܂��́A�ǂ��炩���u�����N�Ȃ��true.
	 */
	public boolean isSmallerFrom() {
		Date fromDate = dtFrom.getValue();
		Date toDate = dtTo.getValue();

		if (fromDate == null || toDate == null) {
			return true;
		}

		return fromDate.compareTo(toDate) <= 0;
	}

	/**
	 * ���͒l���N���A����
	 */
	public void clear() {
		this.dtFrom.setValue(null);
		this.dtTo.setValue(null);
	}

	/**
	 * @see javax.swing.JComponent#requestFocus()
	 */
	@Override
	public void requestFocus() {
		this.dtFrom.requestFocus();
	}

	/**
	 * @see javax.swing.JComponent#requestFocusInWindow()
	 */
	@Override
	public boolean requestFocusInWindow() {
		return this.dtFrom.requestFocusInWindow();
	}
}
