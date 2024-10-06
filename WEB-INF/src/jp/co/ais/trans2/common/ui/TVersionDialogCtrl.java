package jp.co.ais.trans2.common.ui;

import java.awt.event.*;

import jp.co.ais.trans2.common.client.*;

/**
 * �o�[�W�����\���_�C�A���OCTRL
 */
public class TVersionDialogCtrl extends TController {

	/** �_�C�A���O */
	protected TVersionDialog editView;

	/**
	 * �R���X�g���N�^�[
	 * 
	 * @param editView
	 */
	public TVersionDialogCtrl(TVersionDialog editView) {
		this.editView = editView;

		initEvents();
	}

	/**
	 * �C�x���g������
	 */
	protected void initEvents() {

		editView.btnOK.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				btnOK_Click();
			}
		});
	}

	/**
	 * OK�{�^����������
	 */
	protected void btnOK_Click() {
		editView.dispose();
	}

}
