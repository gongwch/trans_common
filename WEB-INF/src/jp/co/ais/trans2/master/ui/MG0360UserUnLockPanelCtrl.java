package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.userunlock.*;

/**
 * MG0360UserUnLockPaneMaster - ���O�C�������}�X�^ - Main Controller
 * 
 * @author AIS
 */
public class MG0360UserUnLockPanelCtrl extends TController {

	/** �w����� */
	protected MG0360UserUnLockPanel mainView = null;

	/** �N���� */
	protected boolean isStart = false;

	@Override
	public void start() {
		try {

			isStart = true;

			// �w����ʐ���
			createMainView();

			// �w����ʏ�����
			initMainView();

			// ��ʂ��J�����ۂɌ���
			btnSearch_Click();

			isStart = false;

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʐ���, �C�x���g��`
	 */
	protected void createMainView() {
		mainView = new MG0360UserUnLockPanel();
		addMainViewEvent();
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param bln Boolean
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * �w����ʂ̃C�x���g��`
	 */
	protected void addMainViewEvent() {
		// [����]�{�^��
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [����]�{�^��
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
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

			// �ꗗ���N���A
			mainView.tbList.removeRow();

			// ���C���{�^������
			setMainButtonEnabled(false);

			// �f�[�^�擾
			List<UserUnLock> list = getList();

			// ���������ɊY������f�[�^�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				if (!isStart) {
					showMessage(mainView.getParentFrame(), "I00022");
				}
				return;
			}

			// ���������f�[�^���ꗗ�ɕ\������
			for (UserUnLock bean : list) {
				mainView.tbList.addRow(getRowData(bean));
			}

			// ���C���{�^���������\�ɂ��A1�s�ڂ�I������
			setMainButtonEnabled(true);
			mainView.tbList.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [����]�{�^������
	 */
	protected void btnDelete_Click() {
		try {

			// �폜�Ώۂ̃f�[�^�擾
			List<UserUnLock> list = getSelected();

			if (list == null) {
				return;
			}

			// ���s�m�Fү����
			if (!showConfirmMessage(mainView, "Q00016")) {
				return;
			}
			// �폜���s
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
	 * �ꗗ�ɕ\������f�[�^���Z�b�g
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(UserUnLock bean) {

		List<Object> list = new ArrayList<Object>();

		list.add(bean);
		list.add(false);// �`�F�b�N�{�b�N�X
		list.add(bean.getUSR_ID()); // ���[�U�[�R�[�h
		list.add(bean.getUSR_NAME()); // ���[�U�[����
		list.add(DateUtil.toYMDHMSString(bean.getPCI_CHECK_IN_TIME())); // ���O�C������

		return list;
	}

	/**
	 * �ꗗ�őI�������f�[�^���擾
	 * 
	 * @return bean
	 */
	protected List<UserUnLock> getSelected() {

		List<UserUnLock> list = new ArrayList<UserUnLock>();

		for (int row = 0; row < mainView.tbList.getRowCount(); row++) {// �X�v���b�h�̍s������]
			boolean isChecked = ((Boolean) mainView.tbList.getRowValueAt(row, MG0360UserUnLockPanel.SC.CHECK_BOX));// �I������Ă���Ώ�
			if (isChecked) {
				UserUnLock data = (UserUnLock) mainView.tbList.getRowValueAt(row, MG0360UserUnLockPanel.SC.bean);
				list.add(data);

			}
		}

		if (list.isEmpty()) {
			showMessage(mainView, "W00195"); // �ꗗ��1�s�ȏ�I�����Ă��������B
			return null;
		}

		return list;
	}

	/**
	 * ���������ɊY������f�[�^��Ԃ�
	 * 
	 * @return List
	 * @throws Exception
	 */
	protected List<UserUnLock> getList() throws Exception {

		List<UserUnLock> list = (List<UserUnLock>) request(getModelClass(), "get");
		return list;
	}

	/**
	 * �w��s�f�[�^���폜����
	 * 
	 * @param list
	 * @throws Exception
	 */
	protected void delete(List<UserUnLock> list) throws Exception {
		request(getModelClass(), "delete", list);
	}

	/**
	 * ���f����Ԃ�
	 * 
	 * @return ���f��
	 */
	protected Class getModelClass() {
		return UserUnLockManager.class;
	}
}
