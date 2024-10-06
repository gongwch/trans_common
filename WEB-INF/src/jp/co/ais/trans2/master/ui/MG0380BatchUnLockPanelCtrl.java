package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.security.*;

/**
 * �o�b�`�r�������}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0380BatchUnLockPanelCtrl extends TController {

	/** �w����� */
	protected MG0380BatchUnLockPanel mainView = null;

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

			// �w����ʂ�����������
			initMainView();

			// ��ʂ��J�����ۂɌ���
			btnSearch_Click();

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
		mainView = new MG0380BatchUnLockPanel();
		addMainViewEvent();
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

		// [����]�{�^������
		mainView.btnRelieve.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnRelieve_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {
		// ���C���{�^���������s�\�ɂ���
		setMainButtonEnabled(false);

	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnSearch_Click() {

		try {

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);
			// ��������
			List<BatchUnLock> list = get();

			// ���������ɊY������Ȗڂ����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				if (!isStart) {
					showMessage(mainView.getParentFrame(), "I00022");
				}
				return;
			}

			// �r�������ꗗ�ɕ\������
			for (BatchUnLock bean : list) {
				mainView.tbl.addRow(getRowData(bean));
			}

			// ���C���{�^���������\�ɂ��A1�s�ڂ�I������
			setMainButtonEnabled(true);
			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * �w�����[�X�V]�{�^������
	 */
	protected void btnRelieve_Click() {

		try {

			// �ҏW�Ώۂ̔r���f�[�^���擾����
			List<BatchUnLock> list = getSelected();

			// �߂�l�𔻒�
			if (list == null) {
				return;
			}
			// ���s�m�Fү����
			if (!showConfirmMessage(mainView, "Q00016")) {
				return;
			}

			// �X�V����
			delete(list);

			// ���������̃��b�Z�[�W
			showMessage(mainView, "I00008");

			// �Č���
			btnSearch_Click();

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
	protected List<Object> getRowData(BatchUnLock bean) {
		List<Object> list = new ArrayList<Object>();

		list.add(bean);
		list.add(false);// �`�F�b�N�{�b�N�X
		list.add(bean.getBAT_ID()); // �o�b�`ID�i�v���O����ID�j
		list.add(bean.getPRG_NAME()); // �v���O��������
		list.add(bean.getUSR_ID()); // �r�����[�UID
		list.add(bean.getUSR_NAME()); // �r�����[�U����
		list.add(bean.getBAT_STR_DATE()); // �r�����s����

		return list;
	}

	/**
	 * �I�������ɊY������r���f�[�^�̃��X�g��Ԃ�
	 * 
	 * @param list �폜����
	 * @throws Exception
	 */
	protected void delete(List<BatchUnLock> list) throws Exception {

		request(getModelClass(), "delete", list);
	}

	/**
	 * ���������ɊY������r���̃��X�g��Ԃ�
	 * 
	 * @return ���������ɊY������r���̃��X�g
	 * @throws Exception
	 */
	protected List<BatchUnLock> get() throws Exception {

		List<BatchUnLock> list = (List<BatchUnLock>) request(getModelClass(), "get");
		return list;
	}

	/**
	 * �ꗗ�őI������Ă���r����Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���r��
	 */
	protected List<BatchUnLock> getSelected() {

		List<BatchUnLock> list = new ArrayList<BatchUnLock>();

		for (int row = 0; row < mainView.tbl.getRowCount(); row++) {// �X�v���b�h�̍s������]
			boolean isChecked = ((Boolean) mainView.tbl.getRowValueAt(row, MG0380BatchUnLockPanel.SC.CHECK_BOX));// �I������Ă���Ώ�
			if (isChecked) {
				BatchUnLock bean = (BatchUnLock) mainView.tbl.getRowValueAt(row, MG0380BatchUnLockPanel.SC.bean);
				list.add(bean);
			}
		}
		if (list.isEmpty()) {
			showMessage(mainView, "W00195"); // �ꗗ��1�s�ȏ�I�����Ă��������B
			return null;
		}
		return list;
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param mainEnabled enabled
	 */
	protected void setMainButtonEnabled(boolean mainEnabled) {
		mainView.btnRelieve.setEnabled(mainEnabled);
	}

	/**
	 * �C���^�t�F�[�X�N���X��Ԃ�
	 * 
	 * @return UserAuthMangager
	 */
	protected Class getModelClass() {
		return BatchUnLockManager.class;
	}

}
