package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.gui.message.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.model.tag.*;

/**
 * MG0460TagMaster - �}�X�^ - Main Controller
 * 
 * @author AIS
 */
public class MG0460TagMasterPanelCtrl extends TController {

	/** �ꗗ��� */
	protected MG0460TagMasterPanel mainView = null;

	/** �ҏW��� */
	protected MG0460TagMasterDialog editView = null;

	/** ���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p���� */
	protected Mode mode = null;

	/** ���샂�[�h */
	protected enum Mode {

		/** �V�K */
		NEW,
		/** �C�� */
		MODIFY,
		/** ���� */
		COPY
	}

	/** �f�t�H���g���F */
	protected static Color DEFAULT_COLOR = Color.WHITE;

	@Override
	public void start() {
		try {

			// �w����ʐ���
			createMainView();

			// �w����ʏ�����
			initMainView();

			// �w����ʕ\��
			mainView.setVisible(true);

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
		mainView = new MG0460TagMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʏ�����
	 */
	protected void initMainView() {
		setMainButtonEnabled(false);
	}

	/**
	 * ���C���{�^���̉�������
	 * 
	 * @param bln Boolean
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * �w����ʂ̃C�x���g��`
	 */
	protected void addMainViewEvent() {
		// [�V�K]�{�^��
		mainView.btnNew.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [����]�{�^��
		mainView.btnSearch.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [�ҏW]�{�^��
		mainView.btnModify.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [����]�{�^��
		mainView.btnCopy.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [�폜]�{�^��
		mainView.btnDelete.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �w�����[�V�K]�{�^������
	 */
	protected void btnNew_Click() {
		try {

			// �ҏW��ʐ���
			createEditView();

			// �ҏW�ҏW��ʏ�����
			initEditView(Mode.NEW, null);

			// �ҏW��ʂ�\��
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �w�����[����]�{�^������
	 */
	protected void btnSearch_Click() {
		try {

			// ���������擾
			TagSearchCondition condition = getSearchCondition();

			// �ꗗ���N���A
			mainView.tbList.removeRow();

			// ���C���{�^������
			setMainButtonEnabled(false);

			// �f�[�^�擾
			List<Tag> list = getList(condition);

			// ���������ɊY������f�[�^�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView.getParentFrame(), "I00022");
				return;
			}

			// ���������f�[�^���ꗗ�ɕ\������
			for (Tag tag : list) {
				mainView.tbList.addRow(getRowData(tag));
			}

			// ���C���{�^������
			setMainButtonEnabled(true);

			mainView.tbList.setRowSelectionInterval(0, 0);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ���������擾
	 * 
	 * @return TagSearchCondition ��������
	 */
	protected TagSearchCondition getSearchCondition() {

		TagSearchCondition condition = new TagSearchCondition();

		condition.setCompanyCode(getCompanyCode());
		return condition;
	}

	/**
	 * [�ҏW]�{�^������
	 */
	protected void btnModify_Click() {
		try {

			if (!checkMainView()) {
				return;
			}

			// �ҏW�Ώۂ̃f�[�^�擾
			Tag bean = getSelected();

			// �ҏW��ʐ���
			createEditView();

			// �ҏW��ʂɑI���f�[�^���Z�b�g
			initEditView(Mode.MODIFY, bean);

			// �ҏW��ʕ\��
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [����]�{�^������
	 */
	protected void btnCopy_Click() {
		try {

			if (!checkMainView()) {
				return;
			}

			// ���ʑΏۂ̃f�[�^�擾
			Tag bean = getSelected();

			// ���ʉ�ʐ���
			createEditView();

			// �ҏW��ʂɑI���f�[�^���Z�b�g
			initEditView(Mode.COPY, bean);

			// ���ʉ�ʕ\��
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * [�폜]�{�^������
	 */
	protected void btnDelete_Click() {
		try {

			if (!checkMainView()) {
				return;
			}

			// �m�F���b�Z�[�W�\��:�������܂����H
			if (!showConfirmMessage(FocusButton.NO, "Q00007")) {
				return;
			}

			// �폜�Ώۂ̃f�[�^�擾
			Tag bean = getSelected();

			// �폜���s
			doDelete(bean);

			// �폜�����s���ꗗ����폜
			mainView.tbList.removeSelectedRow();

			// �폜������A�ꗗ�̃��R�[�h��0���̏ꍇ�A���C���{�^������
			if (mainView.tbList.getRowCount() == 0) {
				setMainButtonEnabled(false);
			}

			// �������b�Z�[�W
			showMessage(mainView.getParentFrame(), "I00013");

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �I���s�̃`�F�b�N
	 * 
	 * @return true:�G���[�Ȃ�
	 */

	protected boolean checkMainView() {

		if (mainView.tbList.getSelectedRow() == -1) {
			// �ꗗ����f�[�^��I�����Ă��������B
			showMessage("I00043");
			return false;
		}

		return true;
	}

	/**
	 * /** �ҏW��ʍ쐬
	 */
	protected void createEditView() {
		editView = new MG0460TagMasterDialog(mainView.getParentFrame(), true);
		addEditViewEvent();
	}

	/**
	 * �ҏW��ʂ̃C�x���g��`
	 */
	protected void addEditViewEvent() {
		// [�m��]�{�^������
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSettle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
		// [�߂�]�{�^������
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnClose_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnSettle_Click() {
		try {

			// ���̓f�[�^�`�F�b�N
			if (!isInputCorrect()) {
				return;
			}

			// ���̓f�[�^�擾
			Tag bean = getInputtedTagMaster();

			// �V�K�o�^�̏ꍇ
			switch (mode) {

				case NEW:

					// �V�K�o�^ - ���ʓo�^
					request(getModelClass(), "entry", bean);

					// �ǉ������ꗗ�ɔ��f
					mainView.tbList.addRow(getRowData(bean));

					// ���C���{�^������
					setMainButtonEnabled(true);

					break;

				case COPY:

					// �V�K�o�^ - ���ʓo�^
					request(getModelClass(), "entry", bean);

					// �ǉ������ꗗ�ɔ��f
					mainView.tbList.addRow(getRowData(bean));

					// ���C���{�^������
					setMainButtonEnabled(true);

					break;

				case MODIFY:

					// �C��
					request(getModelClass(), "modify", bean);

					// �C�������ꗗ�ɔ��f
					mainView.tbList.modifySelectedRow(getRowData(bean));

					break;
			}

			btnClose_Click();
		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW���[�߂�]�{�^������
	 */
	protected void btnClose_Click() {
		editView.setVisible(false);
	}

	/**
	 * �ҏW��ʏ�����
	 * 
	 * @param editMode
	 * @param bean
	 */
	protected void initEditView(Mode editMode, Tag bean) {

		this.mode = editMode;
		switch (editMode) {

			case NEW:

				editView.setTitle(getWord("C01698"));
				editView.ctrlCompanyColor.setColor(DEFAULT_COLOR);

				break;

			case MODIFY:
			case COPY:

				if (Mode.MODIFY == editMode) {

					editView.setTitle(getWord("C00977"));
					editView.ctrlCode.setEditable(false);

				} else {

					editView.setTitle(getWord("C01738"));

				}

				editView.ctrlCode.setValue(bean.getCode());
				editView.ctrlCompanyColor.setColor(bean.getColor());
				editView.ctrlTitle.setValue(bean.getTitle());
				break;
		}
	}

	/**
	 * �ҏW��ʂ̓��͒l�擾
	 * 
	 * @return Employee
	 */
	protected Tag getInputtedTagMaster() {

		Tag bean = new Tag();

		bean.setCode(editView.ctrlCode.getValue());
		bean.setColor(editView.ctrlCompanyColor.getColor());
		bean.setTitle(editView.ctrlTitle.getValue());
		return bean;
	}

	/**
	 * �ꗗ�ɕ\������f�[�^���Z�b�g
	 * 
	 * @param bean
	 * @return list
	 */
	protected List getRowData(Tag bean) {

		List list = new ArrayList();

		list.add(bean);
		list.add(bean.getCode());
		list.add("");
		list.add(bean.getTitle());

		return list;
	}

	/**
	 * �ꗗ�őI�������f�[�^���擾
	 * 
	 * @return bean
	 * @exception Exception
	 */
	protected Tag getSelected() throws Exception {

		Tag bean = (Tag) mainView.tbList.getSelectedRowValueAt(MG0460TagMasterPanel.SC.bean);

		TagSearchCondition condition = new TagSearchCondition();
		condition.setCompanyCode(getCompany().getCode());
		condition.setTagCode(bean.getCode());

		List<Tag> list = getList(condition);

		// ���b�Z�[�W�ǉ��K�v
		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			throw new TException(getMessage("I00193"));
		}
		return list.get(0);
	}

	/**
	 * ���������ɊY������f�[�^��Ԃ�
	 * 
	 * @param condition
	 * @return List
	 * @throws Exception
	 */
	protected List<Tag> getList(TagSearchCondition condition) throws Exception {
		List<Tag> list = (List<Tag>) request(getModelClass(), "get", condition);
		return list;
	}

	/**
	 * �w��s�f�[�^���폜����
	 * 
	 * @param bean
	 * @throws Exception
	 */
	protected void doDelete(Tag bean) throws Exception {
		request(getModelClass(), "delete", bean);
	}

	/**
	 * @return ���f��
	 */
	protected Class getModelClass() {
		return TagManager.class;
	}

	/**
	 * �ҏW��ʓ��͒l�̑Ó������`�F�b�N *
	 * 
	 * @return bool - true:run, false:stop
	 * @exception Exception
	 */

	protected boolean isInputCorrect() throws Exception {
		if (Util.isNullOrEmpty(editView.ctrlCode.getValue())) {
			showMessage(editView, "I00037", "CM0081");
			editView.ctrlCode.requestTextFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓����ЃR�[�h&&����Ј��R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {
			TagSearchCondition condition = new TagSearchCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setTagCode(editView.ctrlCode.getValue());

			List<Tag> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00084", "C00697");
				editView.ctrlCode.requestTextFocus();
				return false;
			}
		}
		return true;

	}
}