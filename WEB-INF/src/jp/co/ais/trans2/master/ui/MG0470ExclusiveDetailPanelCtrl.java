package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.exclusive.*;

/**
 * �r�����׈ꗗ�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0470ExclusiveDetailPanelCtrl extends TController {

	/** �w����� */
	protected MG0470ExclusiveDetailPanel mainView = null;

	/** �N���� */
	protected boolean isStart = false;

	/**
	 * @see jp.co.ais.trans2.common.client.TController#start()
	 */
	@Override
	public void start() {
		try {

			isStart = true;

			// �w����ʐ���
			createMainView();

			isStart = false;

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �p�l���擾
	 * 
	 * @return �p�l��
	 */
	@Override
	public TPanelBusiness getPanel() {
		return mainView;

	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0470ExclusiveDetailPanel();
		addMainViewEvent();

		btnSearch_Click();
	}

	/**
	 * �w����ʃ{�^���������̃C�x���g
	 */
	protected void addMainViewEvent() {
		// [����]�{�^������
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�G�N�Z��]�{�^������
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnSearch_Click() {

		try {

			// �ꗗ���N���A����
			mainView.tbl.removeRow();

			// ��������
			List<ExclusiveDetail> list = get();

			// �r�����ׂ����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				if (!isStart) {
					showMessage(mainView.getParentFrame(), "I00022");
				}
				return;
			}

			// �r�������ꗗ�ɕ\������
			for (ExclusiveDetail bean : list) {
				mainView.tbl.addRow(getRowData(bean));
			}

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * [�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {
		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// ��������
			List<ExclusiveDetail> list = get();

			// �r�����ׂ����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// �G�N�Z���f�[�^�̎擾
			byte[] data = (byte[]) request(getModelClass(), "getExcel");

			// �G�N�Z���^�C�g���Z�b�g
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C11951") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �r�������ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param bean �r�����
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽ�r�����
	 */
	protected List<Object> getRowData(ExclusiveDetail bean) {
		List<Object> list = new ArrayList<Object>();

		list.add(bean.getKAI_CODE()); // ��ЃR�[�h
		list.add(bean.getSHORI_KBN()); // �����敪
		list.add(bean.getHAITA_KEY()); // �r���L�[
		list.add(bean.getGYO_NO()); // �s�ԍ�
		list.add(DateUtil.toYMDHMString(bean.getINP_DATE())); // ��������
		list.add(bean.getPRG_ID()); // �v���O����ID
		list.add(bean.getPRG_NAME()); // �v���O��������
		list.add(bean.getUSR_ID()); // �r�����[�UID
		list.add(bean.getUSR_NAME()); // �r�����[�U����

		return list;
	}

	/**
	 * ���������ɊY������r���̃��X�g��Ԃ�
	 * 
	 * @return ���������ɊY������r���̃��X�g
	 * @throws Exception
	 */
	protected List<ExclusiveDetail> get() throws Exception {
		List<ExclusiveDetail> list = (List<ExclusiveDetail>) request(getModelClass(), "get");
		return list;
	}

	/**
	 * �C���^�t�F�[�X�N���X��Ԃ�
	 * 
	 * @return ExclusiveDetailManager
	 */
	protected Class getModelClass() {
		return ExclusiveDetailManager.class;
	}

}
