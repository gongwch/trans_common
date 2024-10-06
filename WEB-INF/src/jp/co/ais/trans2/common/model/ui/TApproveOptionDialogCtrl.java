package jp.co.ais.trans2.common.model.ui;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

import jp.co.ais.trans2.common.client.*;

/**
 * ���[�N�t���[���F<br>
 * ���F���I�v�V�����I�����
 */
public class TApproveOptionDialogCtrl extends TController {

	/** �_�C�A���O */
	protected TApproveOptionDialog dialog;

	/**
	 * �R���X�g���N�^
	 * 
	 * @param dialog
	 */
	public TApproveOptionDialogCtrl(TApproveOptionDialog dialog) {
		this.dialog = dialog;
		init();
	}

	/** ���������� */
	protected void init() {
		addEvent();
	}

	/**
	 * ��ʃC�x���g��`
	 */
	protected void addEvent() {
		dialog.btnOK.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnOK_Clicked();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		dialog.btnCancel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCancel_Clicked();
				dialog.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * OK�{�^���������̏���
	 */
	protected void btnOK_Clicked() {
		dialog.option = JOptionPane.OK_OPTION;
		close();
	}

	/**
	 * �L�����Z���{�^���������̏���
	 */
	protected void btnCancel_Clicked() {
		close();
	}

	/**
	 * ��ʂ����
	 */
	protected void close() {
		dialog.setVisible(false);
	}

}
