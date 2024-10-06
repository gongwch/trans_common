package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.master.ui.MG0027LockOutMasterPanel.SC;
import jp.co.ais.trans2.model.lockout.*;

/**
 * ���b�N�A�E�g�Ǘ��}�X�^�̃R���g���[��
 * 
 * @author AIS
 */
public class MG0027LockOutMasterPanelCtrl extends TController {

	/** �w����� */
	protected MG0027LockOutMasterPanel mainView = null;

	@Override
	public void start() {

		try {

			// �w����ʐ���
			createMainView();

			// �w����ʂ�����������
			initMainView();

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	@Override
	public TPanelBusiness getPanel() {
		return mainView;
	}

	/**
	 * �w����ʂ�����������
	 * 
	 * @throws Exception
	 */
	protected void initMainView() throws Exception {
		mainView.tbl.removeRow();
		List<LockOut> list = getList();

		// ���b�N�A�E�g�Ǘ��f�[�^���ꗗ�ɕ\������
		for (LockOut lock : list) {
			mainView.tbl.addRow(getData(lock));
		}

	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�ɉ�ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0027LockOutMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
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
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnSearch_Click() {

		try {
			// �ꗗ�ɍŐV�f�[�^���f
			initMainView();
			if (mainView.tbl.getRowCount() == 0) {
				showMessage(mainView.getParentFrame(), "I00022");// �ΏۂƂȂ�f�[�^��������܂���B
				return;
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [����]�{�^������
	 */
	protected void btnDelete_Click() {

		try {

			// �m�F���b�Z�[�W�\��
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {// �폜���܂����H
				return;
			}

			// �I�����ꂽ�s�f�[�^�擾�E��������
			List<LockOut> lockoutList = getSelected();
			if (lockoutList.isEmpty()) {
				// �ꗗ����f�[�^��I�����Ă��������B
				showMessage("I00043");
				return;
			}
			request(getModelClass(), "delete", lockoutList);

			// ������̈ꗗ�쐬
			initMainView();

			// �������b�Z�[�W
			showMessage(mainView.getParentFrame(), "I00013");

		} catch (Exception e) {
			errorHandler(e);
		}

	}

	/**
	 * Servlet�N���X��Ԃ�
	 * 
	 * @return LockOutManager
	 */
	protected Class getModelClass() {
		return LockOutManager.class;
	}

	/**
	 * �ꗗ�őI�������f�[�^���擾
	 * 
	 * @return bean
	 */
	protected List<LockOut> getSelected() {
		List<LockOut> dataList = new ArrayList<LockOut>();
		for (int row = 0; row < mainView.tbl.getRowCount(); row++) { // �e�[�u���̍s����for

			boolean isChecked = (Boolean) mainView.tbl.getRowValueAt(row, SC.UNLOCK);
			if (isChecked) {
				LockOut data = (LockOut) mainView.tbl.getRowValueAt(row, SC.ENTITY); // �ȉ��Atrue��data���Z�b�g����
				dataList.add(data);
			}

		}
		return dataList;

	}

	/**
	 * ���b�N�A�E�g�Ǘ��f�[�^��Ԃ�
	 * 
	 * @return List
	 * @throws Exception
	 */
	protected List<LockOut> getList() throws Exception {
		List<LockOut> list = (List<LockOut>) request(getModelClass(), "get");
		return list;
	}

	/**
	 * �ꗗ�ɕ\������f�[�^���Z�b�g
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getData(LockOut bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean);
		list.add(false);
		list.add(bean.getUserCode());
		list.add(bean.getUserNames());
		list.add(DateUtil.toYMDHMSString(bean.getLogFailure()));
		return list;
	}

}
