package jp.co.ais.trans2.master.ui;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

import jp.co.ais.trans.common.except.*;
import jp.co.ais.trans.common.gui.*;
import jp.co.ais.trans.common.util.*;
import jp.co.ais.trans2.master.ui.CM0060MLITRegionMasterPanel.SC;
import jp.co.ais.trans2.common.client.*;
import jp.co.ais.trans2.common.print.*;
import jp.co.ais.trans2.common.ui.*;
import jp.co.ais.trans2.model.mlit.region.*;

/**
 * �A�����э��}�X�^��ʃR���g���[��
 * 
 * @author AIS
 */
public class CM0060MLITRegionMasterPanelCtrl extends TController {

	/** ���샂�[�h */
	protected enum Mode {
		/** �V�K */
		NEW,
		/** �C�� */
		MODIFY,
		/** ���� */
		COPY
	}

	/** �p�l�� */
	protected CM0060MLITRegionMasterPanel mainView;

	/** �ҏW��� */
	protected CM0060MLITRegionMasterDialog editView;

	/** �C���O�̃f�[�^ */
	protected YJRegion editBean = null;

	/** ���샂�[�h�B���ݑ��쒆�̃��[�h��c�����邽�߂Ɏg�p����B */
	protected Mode mode = null;

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

	/**
	 * �p�l���擾
	 * 
	 * @return �A�����э��}�X�^�p�l��
	 */
	@Override
	public TPanelBusiness getPanel() {
		// �p�l����Ԃ�
		return mainView;
	}

	/**
	 * �w����ʂ̃t�@�N�g���B�V�K�Ɏw����ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createMainView() {
		mainView = new CM0060MLITRegionMasterPanel();
		addMainViewEvent();
	}

	/**
	 * �w����ʂ̃C�x���g��`�B
	 */
	protected void addMainViewEvent() {

		mainView.btnNew.addActionListener(new ActionListener() {

			// �V�K�{�^��������
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnNew_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnSearch.addActionListener(new ActionListener() {

			// �����{�^��������
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnSearch_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnModify.addActionListener(new ActionListener() {

			// �ҏW�{�^��������
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnModify_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnCopy.addActionListener(new ActionListener() {

			// ���ʃ{�^��������
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnCopy_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnDelete.addActionListener(new ActionListener() {

			// �폜�{�^��������
			public void actionPerformed(ActionEvent evt) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnDelete_Click();
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		mainView.btnListOutput.addActionListener(new ActionListener() {

			// ���X�g�o�̓{�^��������
			public void actionPerformed(ActionEvent e) {
				mainView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnExcel_Click();
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
	 * ���C���{�^���̉�������
	 * 
	 * @param bln enabled
	 */
	protected void setMainButtonEnabled(boolean bln) {
		mainView.btnModify.setEnabled(bln);
		mainView.btnCopy.setEnabled(bln);
		mainView.btnDelete.setEnabled(bln);
	}

	/**
	 * �V�K�o�^����
	 */
	protected void btnNew_Click() {

		try {
			// �ҏW��ʂ𐶐�
			createEditView();

			// �ҏW��ʂ�����������
			initEditView(Mode.NEW, null);

			// �ҏW��ʂ�\��
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ��������
	 */
	protected void btnSearch_Click() {

		try {
			// ���������`�F�b�N
			if (!checkMainView()) {
				return;
			}

			// �ꗗ���N���A���A���C���{�^���������s�\�ɂ���
			mainView.tbl.removeRow();
			setMainButtonEnabled(false);

			// YJRegion�����擾
			YJRegionSearchCondition condition = getSearchCondition();
			List<YJRegion> list = getList(condition);

			// ���������ɊY������YJRegion�����݂��Ȃ��ꍇ�A���b�Z�[�W��\�����ďI��
			if (list == null || list.isEmpty()) {
				showMessage(mainView, "I00022");
				return;
			}

			// YJRegion�����ꗗ�ɕ\������
			for (YJRegion bean : list) {
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
	 * [�ҏW]�{�^������
	 */
	protected void btnModify_Click() {

		try {
			if (!checkSelectedRow()) {
				return;
			}

			// �ҏW�Ώۂ�YJRegion���擾����
			YJRegion bean = getSelectedRowData();

			// �ҏW��ʂ𐶐����A�ҏW�Ώۂ�YJRegion�����Z�b�g����
			createEditView();
			initEditView(Mode.MODIFY, bean);

			// �ҏW��ʂ�\������
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
			if (!checkSelectedRow()) {
				return;
			}

			// ���ʑΏۂ�YJRegion���擾����
			YJRegion bean = getSelectedRowData();

			// �ҏW�O�f�[�^���폜
			editBean = null;

			// ���ʉ�ʂ𐶐����A���ʑΏۂ�YJRegion�����Z�b�g����
			createEditView();

			initEditView(Mode.COPY, bean);

			// ���ʉ�ʂ�\������
			editView.setLocationRelativeTo(null);
			editView.setVisible(true);

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʂ̃t�@�N�g���B�V�K�ɕҏW��ʂ𐶐����A�C�x���g���`����B
	 */
	protected void createEditView() {

		// �ҏW��ʂ𐶐�
		editView = new CM0060MLITRegionMasterDialog(getCompany(), mainView.getParentFrame(), true);

		// �ҏW��ʂ̃C�x���g��`
		addEditViewEvent();
	}

	/**
	 * [�폜]�{�^������
	 */
	protected void btnDelete_Click() {

		try {
			if (!checkSelectedRow()) {
				return;
			}

			// �m�F���b�Z�[�W
			if (!showConfirmMessage(mainView, "Q00007")) {
				return;
			}

			// �폜�Ώۂ�YJRegion���擾����
			YJRegion bean = getSelectedRowData();

			// �폜����
			request(getModelClass(), "deleteRegion", bean);

			// �폜�����s���ꗗ����폜
			mainView.tbl.removeSelectedRow();

			// �폜��������0���ɂȂ����烁�C���{�^���������s�\�ɂ���
			if (mainView.tbl.getRowCount() == 0) {
				setMainButtonEnabled(false);
			}

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �I���s�̃`�F�b�N
	 * 
	 * @return true:�G���[�Ȃ�
	 */
	protected boolean checkSelectedRow() {

		if (mainView.tbl.getSelectedRow() == -1) {
			// �ꗗ����f�[�^��I�����Ă��������B
			showMessage("I00043");
			return false;
		}
		return true;
	}

	/**
	 * �G�N�Z�����X�g�o��
	 */
	protected void btnExcel_Click() {

		try {
			// ���������`�F�b�N
			if (!checkMainView()) {
				return;
			}

			// �G�N�Z���f�[�^�̎擾
			YJRegionSearchCondition condition = getSearchCondition();
			byte[] data = (byte[]) request(getModelClass(), "getExcelRegion", condition);

			if (data == null || data.length == 0) {
				showMessage(mainView, "I00022");
				return;
			}

			// �v���r���[
			TPrinter printer = new TPrinter();
			printer.preview(data, getWord("YJ Region Master") + ".xls"); // YJRegion�}�X�^.xls

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * ���C����ʃ`�F�b�N
	 * 
	 * @return true:���Ȃ�
	 */
	protected boolean checkMainView() {

		// �J�n�R�[�h<=�I���R�[�h�ɂ��Ă��������B
		if (!mainView.ctrlRange.isSmallerFrom()) {
			showMessage(mainView, "I00068");
			mainView.ctrlRange.getFieldFrom().code.requestFocus();
			return false;
		}
		return true;
	}

	/**
	 * �ҏW��ʂ̃C�x���g��`�B
	 */
	protected void addEditViewEvent() {

		// [�m��]�{�^������
		editView.btnSettle.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnEditView_Settle_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});

		// [�߂�]�{�^������
		editView.btnClose.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
				btnEditView_Close_Click();
				editView.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
			}
		});
	}

	/**
	 * �ҏW���[�m��]�{�^������
	 */
	protected void btnEditView_Settle_Click() {

		try {
			// ���̓`�F�b�N
			if (!isInputRight()) {
				return;
			}

			// ���͂��ꂽYJRegion�����擾
			reflectEditBean();

			// �폜���o�^
			editBean = (YJRegion) request(getModelClass(), "entryRegionMaster", editBean);

			if (Mode.NEW == mode || Mode.COPY == mode) {
				// �V�K�o�^�̏ꍇ

				// �ǉ������ꗗ�ɔ��f����
				mainView.tbl.addRow(getRowData(editBean));

				// ���C���{�^���������\�ɂ���
				setMainButtonEnabled(true);

			} else if (Mode.MODIFY == mode) {
				// �C���̏ꍇ

				// �C�����e���ꗗ�ɔ��f����
				mainView.tbl.modifySelectedRow(getRowData(editBean));

			}
			// �ҏW��ʂ����
			btnEditView_Close_Click();

		} catch (Exception e) {
			errorHandler(e);
		}
	}

	/**
	 * �ҏW��ʂœ��͂����l���Ó������`�F�b�N����
	 * 
	 * @return �ҏW��ʂœ��͂����l���Ó����Btrue�̏ꍇ����Afalse�̏ꍇ�G���[����B
	 * @throws Exception
	 */
	protected boolean isInputRight() throws Exception {

		// �R�[�h�`�F�b�N
		if (Util.isNullOrEmpty(editView.ctrlItemCode.getValue())) {
			// �G���[���b�Z�[�W�o��
			showMessage(editView, "I00002", "Code"); // ���R�[�h
			editView.ctrlItemCode.requestFocus();
			return false;
		}

		// �V�K�A���ʂ̏ꍇ�͓���YJRegion�R�[�h�����ɑ��݂�����G���[
		if (Mode.NEW == mode || Mode.COPY == mode) {

			YJRegionSearchCondition condition = createCondition();
			condition.setCompanyCode(getCompanyCode());
			condition.setRegionCode(editView.ctrlItemCode.getValue());

			List<YJRegion> list = getList(condition);

			if (list != null && !list.isEmpty()) {
				showMessage(editView, "I00055", "Code"); // ���R�[�h
				editView.ctrlItemCode.requestTextFocus();
				return false;
			}
		}
		return true;
	}

	/**
	 * �ҏW��ʂœ��͂��ꂽYJRegion��Ԃ�
	 */
	protected void reflectEditBean() {

		if (editBean == null) {
			editBean = createEntity();
		}

		editBean.setKAI_CODE(getCompanyCode()); // ��ЃR�[�h
		editBean.setREGION_CODE(editView.ctrlItemCode.getValue()); // �R�[�h
		editBean.setREGION_NAME(editView.ctrlItemName.getValue()); // ����
		editBean.setREGION_REMARK(editView.ctrlRemark.getValue()); // Remark
	}

	/**
	 * �ҏW���[�߂�]�{�^������
	 */
	protected void btnEditView_Close_Click() {
		editView.setVisible(false);
	}

	/**
	 * �ҏW��ʂ�����������
	 * 
	 * @param editMode ���샂�[�h
	 * @param bean
	 */
	protected void initEditView(Mode editMode, YJRegion bean) {
		this.mode = editMode;

		switch (editMode) {
			case NEW:
				// �V�K
				editView.setTitle(getWord("Region") + " " + getWord("C01698")); // �V�K���

				editBean = null;// �ҏW�O�f�[�^���폜
				break;

			case MODIFY:
				// �ҏW
				editView.setTitle(getWord("Region") + " " + getWord("C00977")); // �ҏW���
				editView.ctrlItemCode.setEditable(false);
				editView.setEditMode();
				editBean = bean;// �ҏW�O�f�[�^��ێ�

				break;

			case COPY:
				// ����
				editView.setTitle(getWord("Region") + " " + getWord("C01738")); // ���ʉ��
				editBean = null;// �ҏW�O�f�[�^���N���A
				break;
		}
		setEditDialog(bean);
	}

	/**
	 * ��ʂ�YJRegion�����Z�b�g����
	 * 
	 * @param bean
	 */
	protected void setEditDialog(YJRegion bean) {

		if (bean == null) {
			return;
		}

		editView.ctrlItemCode.setValue(bean.getREGION_CODE()); // �R�[�h
		editView.ctrlItemName.setValue(bean.getREGION_NAME()); // ����
		editView.ctrlRemark.setValue(bean.getREGION_REMARK()); // Remark
	}

	/**
	 * �ꗗ�őI������Ă���YJRegion��Ԃ�
	 * 
	 * @return �ꗗ�őI������Ă���YJRegion
	 * @throws Exception
	 */
	protected YJRegion getSelectedRowData() throws Exception {

		YJRegionSearchCondition condition = createCondition();
		condition.setCompanyCode(TLoginInfo.getCompany().getCode());
		condition.setRegionCode((String) mainView.tbl.getSelectedRowValueAt(SC.code));

		List<YJRegion> list = getList(condition);

		if (list == null || list.isEmpty()) {
			// �I�����ꂽ�f�[�^�͑����[�U�[�ɂ��폜����܂����B
			throw new TException("I00193");
		}
		return list.get(0);

	}

	/**
	 * �{�^��(�V�K�A�폜�A�ҏW�A���ʁA���X�g�o��)���b�N
	 * 
	 * @param boo
	 */
	protected void lockBtn(boolean boo) {
		mainView.btnDelete.setEnabled(boo);
		mainView.btnModify.setEnabled(boo);
		mainView.btnCopy.setEnabled(boo);
		mainView.btnListOutput.setEnabled(boo);
	}

	/**
	 * �w����ʂœ��͂��ꂽYJRegion�̌���������Ԃ�
	 * 
	 * @return ��������
	 */
	protected YJRegionSearchCondition getSearchCondition() {
		YJRegionSearchCondition condition = createCondition();
		condition.setCompanyCode(getCompanyCode());
		condition.setRegionCodeFrom(mainView.ctrlRange.getCodeFrom());
		condition.setRegionCodeTo(mainView.ctrlRange.getCodeTo());

		return condition;
	}

	/**
	 * @return YJRegion
	 */
	protected YJRegion createEntity() {
		return new YJRegion();
	}

	/**
	 * @return ��������
	 */
	protected YJRegionSearchCondition createCondition() {
		return new YJRegionSearchCondition();
	}

	/**
	 * ���������ɊY�����郊�X�g��Ԃ�
	 * 
	 * @param condition ��������
	 * @return ���������ɊY�����郊�X�g
	 * @throws Exception
	 */
	protected List<YJRegion> getList(YJRegionSearchCondition condition) throws Exception {
		List<YJRegion> list = (List<YJRegion>) request(getModelClass(), "getRegion", condition);
		return list;
	}

	/**
	 * Manager�N���X��Ԃ�
	 * 
	 * @return �N���X
	 */
	protected Class getModelClass() {
		return YJRegionManager.class;
	}

	/**
	 * �G���e�B�e�B���ꗗ�ɕ\������`���ɕϊ����Ԃ�
	 * 
	 * @param bean �f�[�^
	 * @return �ꗗ�ɕ\������`���ɕϊ����ꂽYJRegion
	 */
	protected List<String> getRowData(YJRegion bean) {
		List<String> list = new ArrayList<String>();

		list.add(bean.getREGION_CODE()); // �R�[�h
		list.add(bean.getREGION_NAME()); // ����
		list.add(bean.getREGION_REMARK()); // Remark

		return list;
	}
}
