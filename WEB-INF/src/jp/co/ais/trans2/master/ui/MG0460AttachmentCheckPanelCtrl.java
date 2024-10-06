package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.attach.verify.*;

/**
 * �Y�t���؉�ʃR���g���[��
 */
public class MG0460AttachmentCheckPanelCtrl extends TController {

	/** �w����� */
	protected MG0460AttachmentCheckPanel mainView;

	@Override
	public void start() {
		try {
			// ���C����ʐ���
			createMainView();
			// ���C����ʏ�����
			initMainView();
			// ���C����ʕ\��
			mainView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w����ʏ�����
	 */
	protected void createMainView() {
		this.mainView = new MG0460AttachmentCheckPanel();
	}

	/**
	 * �w����ʏ�����
	 */
	protected void initMainView() {
		addMainViewEvent();
	}

	/**
	 * ��ʃC�x���g��`
	 */
	protected void addMainViewEvent() {
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Clicked();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Clicked();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �����{�^���������̏���
	 */
	protected void btnSearch_Clicked() {
		try {
			// ���ʈꗗ��S�s����
			mainView.tblResult.removeRow();

			List<AttachmentVerifyResult> list = (List<AttachmentVerifyResult>) request(getModelClass(), "get");
			if (list == null || list.isEmpty()) {
				return;
			}
			// ���ʈꗗ�ɔ��f
			for (AttachmentVerifyResult bean : list) {
				List row = new ArrayList();
				row.add(bean.getKAI_CODE());
				row.add(bean.getTYPE().toString());
				row.add(bean.getKEY1());
				row.add(bean.getKEY2());
				row.add(bean.getKEY3());
				row.add(bean.getKEY4());
				row.add(bean.getFILE_NAME());
				row.add(bean.getSRV_FILE_NAME());
				row.add(bean.getMESSAGE());
				mainView.tblResult.addRow(row);
			}
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �G�N�Z���{�^���������̏���
	 */
	protected void btnExcel_Clicked() {
		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView.getParentFrame(), "Q00011")) {
				return;
			}

			// �G�N�Z���f�[�^�̎擾
			byte[] data = (byte[]) request(getModelClass(), "getExcel");

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			// ��s�����}�X�^
			printer.preview(data, getWord("�Y�t���،���") + "_" + DateUtil.toYMDHMSPlainString(new Date()) + ".xls");
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * @return �}�l�[�W���N���X
	 */
	protected Class getModelClass() {
		return AttachmentVerifyManager.class;
	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

}
