package jp.co.ais.trans.common.ui;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;

/**
 * ���O�C���p�p�l�� �R���g���[��(���g�p)
 * 
 * @author liuguozheng
 */
public class LOGINPanelCtrl extends TPanelCtrlBase {

	/** �p�l�� */
	private LOGINPanel panel;

	/**
	 * �R���X�g���N�^.
	 */
	public LOGINPanelCtrl() {
		try {
			panel = new LOGINPanel(this);

		} catch (Exception e) {
			errorHandler(panel, e, "E00010");
		}
	}

	/**
	 * @see jp.co.ais.trans.common.client.TPanelCtrlBase#getPanel()
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * ��������
	 */
	void search() {

		// ��ЃR�[�h�`�F�b�N
		if (Util.isNullOrEmpty(panel.ctrlCompanyCode.getValue())) {
			// ��ЃR�[�h����͂��Ă�������
			super.showMessage(panel, "I00002", "C00684");
			panel.ctrlCompanyCode.requestTextFocus();
			return;
		}

		// ���[�U�[�R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(panel.ctrlUserCode.getValue())) {
			// ���[�U�[�R�[�h����͂��Ă�������
			super.showMessage(panel, "I00002", "C00589");
			panel.ctrlUserCode.requestTextFocus();
			return;
		}

		// �p�X���[�h�`�F�b�N
		if (Util.isNullOrEmpty(panel.ctrlPassword.getValue())) {
			// �p�X���[�h����͂��Ă�������
			super.showMessage(panel, "I00002", "C00597");
			panel.ctrlPassword.requestTextFocus();
			return;
		}
	}
}
