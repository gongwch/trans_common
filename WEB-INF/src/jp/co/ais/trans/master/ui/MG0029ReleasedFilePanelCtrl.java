package jp.co.ais.trans.master.ui;

import jp.co.ais.trans.common.client.*;
import jp.co.ais.trans.common.gui.*;

/**
 * ���s���O�Q�ƃR���g���[��
 * 
 * @author roh
 */
public class MG0029ReleasedFilePanelCtrl extends TPanelCtrlBase {

	/** �p�l�� */
	private MG0029ReleasedFilePanel panel;

	/** �����T�[�u���b�g(�v���O�����j */
	private static final String TARGET_SERVLET = "MG0029ReleasedFileServlet";

	/**
	 * �p�l���擾
	 * 
	 * @return �p�l��
	 */
	public TPanelBusiness getPanel() {
		return this.panel;
	}

	/**
	 * �R���X�g���N�^
	 */
	public MG0029ReleasedFilePanelCtrl() {
		panel = new MG0029ReleasedFilePanel(this);
	}

	/**
	 * �G�N�Z���o��
	 */
	protected void exportToExcel() {

		// �����̑��s��₤
		if (showConfirmMessage(panel.getParentFrame(), "Q00011")) {
			try {
				// �G�N�Z���ŕ\������
				this.download(panel, TARGET_SERVLET);

			} catch (Exception ex) {
				errorHandler(panel, ex);
			}
		}

	}
}
