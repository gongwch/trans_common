package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.log.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.model.executedlog.*;

/**
 * ���s���O�Q�Ƃ̃R���g���[��
 * 
 * @author AIS
 */
public class MG0028ExecutedLogPanelCtrl extends TController {

	/** �w����� */
	protected MG0028ExecutedLogPanel mainView = null;

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
	 * �w����ʂ̃t�@�N�g���B�V�K�ɉ�ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new MG0028ExecutedLogPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ�����������
	 */
	protected void initMainView() {
		mainView.ctrlDateFrom.setValue(Util.getCurrentDate());
		mainView.ctrlDateFrom.setAllowableBlank(false);
		mainView.ctrlDateTo.setValue(Util.getCurrentDate());
		mainView.ctrlDateTo.setAllowableBlank(false);
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

		// [�G�N�Z��]�{�^������
		mainView.btnExcel.addActionListener(new ActionListener() {

			public void actionPerformed(@SuppressWarnings("unused") ActionEvent e) {
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

			// �J�n�N����<=�I���N�����ɂ��Ă��������B
			if (Util.isSmallerThen(DateUtil.toYMDString(mainView.ctrlDateFrom.getValue()),
				DateUtil.toYMDString(mainView.ctrlDateTo.getValue())) == false) {
				showMessage(mainView, "I00067");
				mainView.ctrlDateFrom.requestFocus();
				return;
			}

			// ��������
			ExecutedLogSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N(���[�U�[)
			if (Util.isSmallerThen(condition.getUserFrom(), condition.getUserTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlUserRange.requestFocus();
				return;
			}

			// �J�n <= �I���`�F�b�N(�v���O����)
			if (Util.isSmallerThen(condition.getProgramFrom(), condition.getProgramTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlUserRange.requestFocus();
				return;
			}

			// �ꗗ���N���A
			mainView.tbl.removeRow();

			List<ExecutedLog> list = getList(condition);

			// ���������ɊY������f�[�^�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");// �ΏۂƂȂ�f�[�^��������܂���B
				return;
			}

			for (ExecutedLog log : list) {
				if (condition.isIsLogin() == true) {
					if (ExecutedLogger.LOGIN.equals(log.getProCode())) {
						log.setProName(getWord("C03187")); // ���O�C��

					} else if (ExecutedLogger.LOGOUT.equals(log.getProCode())) {
						log.setProName(getWord("C03188")); // ���O�A�E�g
					}
				}

				// ���������f�[�^���ꗗ�ɕ\������
				mainView.tbl.addRow(getRowData(log));
			}

			mainView.tbl.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w�����[�G�N�Z��]�{�^������
	 */
	protected void btnExcel_Click() {

		try {

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00011")) {
				return;
			}

			// �J�n�N����<=�I���N�����ɂ��Ă��������B
			if (Util.isSmallerThen(DateUtil.toYMDString(mainView.ctrlDateFrom.getValue()),
				DateUtil.toYMDString(mainView.ctrlDateTo.getValue())) == false) {
				showMessage(mainView, "I00067");
				mainView.ctrlDateFrom.requestFocus();
				return;
			}

			// �f�[�^���o
			ExecutedLogSearchCondition condition = getSearchCondition();

			// �J�n <= �I���`�F�b�N(���[�U�[)
			if (Util.isSmallerThen(condition.getUserFrom(), condition.getUserTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlUserRange.requestFocus();
				return;
			}

			// �J�n <= �I���`�F�b�N(�v���O����)
			if (Util.isSmallerThen(condition.getProgramFrom(), condition.getProgramTo()) == false) {
				showMessage(mainView, "I00068");
				mainView.ctrlUserRange.requestFocus();
				return;
			}

			byte[] data = (byte[]) request(getManagerClass(), "getExcel", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView.getParentFrame(), "I00022");// �ΏۂƂȂ�f�[�^��������܂���B
				return;
			}

			// �o��
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("C02911") + ".xls");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * Manager�N���X��Ԃ�
	 * 
	 * @return ExecutedLogManager
	 */
	protected Class getManagerClass() {
		return ExecutedLogManager.class;
	}

	/**
	 * ���������擾
	 * 
	 * @return ��������
	 */
	protected ExecutedLogSearchCondition getSearchCondition() {

		ExecutedLogSearchCondition condition = new ExecutedLogSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		condition.setDateFrom(mainView.ctrlDateFrom.getValue());
		condition.setDateTo(mainView.ctrlDateTo.getValue());
		condition.setUserFrom(mainView.ctrlUserRange.getCodeFrom());
		condition.setUserTo(mainView.ctrlUserRange.getCodeTo());
		condition.setProgramFrom(mainView.ctrlProgramRange.getCodeFrom());
		condition.setProgramTo(mainView.ctrlProgramRange.getCodeTo());
		if (mainView.checkProgram.isSelected()) {

			condition.setIsLogin(true);
		} else {
			condition.setIsLogin(false);
		}

		condition.setSort((Integer) mainView.ctrlSort.getSelectedItemValue());

		return condition;
	}

	/**
	 * ���s���O�Q�ƃf�[�^��Ԃ�
	 * 
	 * @param condition
	 * @return List
	 * @throws Exception
	 */
	protected List<ExecutedLog> getList(ExecutedLogSearchCondition condition) throws Exception {
		List<ExecutedLog> list = (List<ExecutedLog>) request(getManagerClass(), "get", condition);
		return list;
	}

	/**
	 * �ꗗ�ɕ\������f�[�^���Z�b�g
	 * 
	 * @param bean
	 * @return list
	 */
	protected List<Object> getRowData(ExecutedLog bean) {

		List<Object> list = new ArrayList<Object>();
		list.add(bean);
		list.add(DateUtil.toYMDHMSString(bean.getExcDate()));
		list.add(bean.getExcCode());
		list.add(bean.getExcNames());
		list.add(bean.getIpAddress());
		list.add(bean.getProCode());
		list.add(bean.getProName());
		list.add(bean.getStste());
		return list;
	}


}
